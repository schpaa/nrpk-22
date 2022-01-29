(ns booking.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
    ;[times.api :refer []]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [booking.data :as data :refer [screen-breakpoints start-db routes]]
            [schpaa.modal :as modal]

            [schpaa.components.screen :as components.screen]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.components.tab :refer [tab]]
            [schpaa.time]
            [schpaa.darkmode]
            [schpaa.icon :as icon]
            [schpaa.components.fields :as fields]
            [schpaa.debug :as l]
            ["body-scroll-lock" :as body-scroll-lock]
            [nrpk.fsm-helpers :as state :refer [send]]
            [fork.re-frame :as fork]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [booking.views]
            [user.views]
            [tick.core :as t]
            [booking.hoc :as hoc]
            [schpaa.style :as st]))

(defn view-info [{:keys [username]}]
  [:div username])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

;region todo: extract these higher-order-components

;endregion

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

(defn user []
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])
        {:keys [bg fg- fg+ hd p p- he]} (st/fbg' :surface)]
    (fn []
      ;[:div.bg-alt.h-32.w-full "X"]
      (if-not @user-auth
        [:div.xp-4.xmax-w-md.mx-autox
         [rounded-view {:float 1} [db.signin/login]]]

        [:div.xw-full {:class bg}
         [:div.xp-4.xmax-w-md.mx-autox
          #_[user.views/userstatus-form
             {:user-auth @user-auth
              :name      (:display-name @user-auth)}]]

         [:div
          [tab {:selected @(rf/subscribe [:app/current-page])}
           [:r.user "Om meg" nil :icon :user]
           [:r.logg "Turlogg" nil :icon :circle]
           [:r.debug "Feilsøking" nil :icon :circle]]

          [k/case-route (fn [route] (-> route :data :name))
           :r.user
           (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
             [:div.space-y-4
              {:class bg}
              [user.views/my-info]])

           :r.logg
           (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :void)]
             [:div.w-screenx
              [:div.space-y-px.flex.flex-col.w-full
               {:class  :min-h-screen
                :-style {:min-height "calc(100vh + 3rem)"}}
               [:div.flex-1.w-fullx
                {:class bg}
                [hoc/user-logg]]]
              [hoc/all-boats-footer {}]])

           :r.debug
           [hoc/debug]

           [:div @route]]]]))))

(defn front []
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)
        user-auth (rf/subscribe [::db/user-auth])]
    [:div

     [tab {:item     ["w-1/3"]
           :selected @(rf/subscribe [:app/current-page])}
      ;[:r.blog "Info" nil :icon :command]
      [:r.new-booking "Booking" nil :icon :spark]
      [:r.common "Siste" nil :icon :clock]
      [:r.boatlist "Båtliste" nil :icon :list]
      #_[:r.debug2 "Debug" nil :icon :eye]]

     [k/case-route (comp :name :data)
      :r.blog [(get-in schpaa.components.sidebar/tabs-data [:bar-chart :content-fn])]

      :r.debug2
      [:div "Stuff"]

      :r.new-booking
      (if-not @user-auth
        [views/rounded-view {}
         [:div.p-4.space-y-4
          [:h2 "Er du ny her?"]
          [:a {:href (k/path-for [:r.user])} "Logg inn først"]]]
        [hoc/new-booking])

      :r.common
      (let [data (sort-by (comp :number val) < (logg.database/boat-db))]
        [:div
         {:class bg}
         [:div.space-y-px.flex.flex-col
          {:style {:min-height "calc(100vh - 7rem)"}}
          (if (seq data)
            [:div.flex-1
             {:class bg}
             [hoc/all-active-bookings {:data data}]]
            [empty-list-message "Booking-listen er tom"])
          [booking.views/last-bookings-footer {}]]])

      :r.boatlist
      (let [data (sort-by (comp :number val) < (logg.database/boat-db))]
        [:div
         {:class bg}
         [:div.space-y-px.flex.flex-col
          {:style {:min-height "calc(100vh - 7rem)"}}
          (if (seq data)
            [:div.flex-1
             [hoc/all-boats
              {:data     data
               :details? @(schpaa.state/listen :opt1)}]]
            [empty-list-message "Båt-listen er tom"])
          [hoc/all-boats-footer {}]]])]]))

(def route-table
  {:r.common      front
   :r.new-booking front
   :r.boatlist    front
   :r.user        user
   :r.logg        user
   :r.debug       user
   :r.debug2      front
   :r.blog        front})

(defn- forced-scroll-lock
  [locked?]
  (let [body (aget (.getElementsByTagName js/document "body") 0)]
    (if locked?
      (.disableBodyScroll body-scroll-lock body)
      (.enableBodyScroll body-scroll-lock body))))

(defn dispatch-main []
  (let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
        menu-open? (rf/subscribe [:app/menu-open?])
        route-name @(rf/subscribe [:route-name])
        web-content (when-let [page (get route-table route-name)]
                      (kee-frame.router/make-route-component page @(rf/subscribe [:kee-frame/route])))
        s (rf/subscribe [::rs/state-full :main-fsm])]
    (forced-scroll-lock (or (and @mobile? @menu-open?)
                            (or (:modal @s) (:modal-forced @s))))
    [:div
     [modal/overlay-with
      {:modal-dim (:modal-dim @s)
       :modal?    (or (:modal @s)
                      (:modal-forced @s))
       ;intent No dismiss-fx on click when forced, must click on a button
       :on-close  (if
                    (or (:modal-dirty @s) (:modal-forced @s))
                    nil
                    #(send :e.hide))}
      [:div
       [modal/render
        {:show?     (or (:modal @s) (:modal-forced @s))
         :config-fn (:modal-config-fn @s)}]
       [components.screen/render
        {:current-page          (fn [] @(rf/subscribe [:app/current-page]))
         :toggle-menu-open      (fn [] (rf/dispatch [:toggle-menu-open]))
         :navigate-to-home      (fn [] (rf/dispatch [:app/navigate-to [:r.common]]))
         :navigate-to-user      (fn [] (rf/dispatch [:app/navigate-to [:r.user]]))
         :current-page-title    (fn [] @(rf/subscribe [:app/current-page-title]))
         :current-page-subtitle (fn [] @(rf/subscribe [:app/current-page-subtitle]))
         :get-menuopen-fn       (fn [] @(rf/subscribe [:app/menu-open?]))}
        web-content]]]]))


(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode (rf/subscribe [:app/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if (= :dark @user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans bg-gray-600 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])
