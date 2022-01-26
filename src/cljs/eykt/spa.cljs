(ns eykt.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
    ;[times.api :refer []]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [eykt.data :as data :refer [screen-breakpoints start-db routes]]
            [schpaa.modal :as modal]
            [eykt.fsm-helpers :refer [send]]
            [schpaa.components.screen :as components.screen]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.components.tab :refer [tab]]
            [schpaa.time]
            [schpaa.darkmode]
            [schpaa.icon :as icon]
            [schpaa.components.fields :as fields]
            [schpaa.debug :as l]
            ["body-scroll-lock" :as body-scroll-lock]
            [eykt.fsm-model :as state]
            [fork.re-frame :as fork]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [booking.views]
            [user.views]
            [tick.core :as t]
            [eykt.hoc :as hoc]))

(defn view-info [{:keys [username]}]
  [:div username])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

;region todo: extract these higher-order-components

;endregion

(defn user []
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])]
    (fn []
      (if-not @user-auth
        [:div.p-2
         [rounded-view {:float 1} [db.signin/login]]]
        [:div.bg-gray-200.dark:bg-gray-800
         [:div.p-4.max-w-md.mx-auto
          [user.views/logout-form
           {:user-auth @user-auth
            :name      (:display-name @user-auth)}]]

         [:div
          [tab {:selected @(rf/subscribe [:app/current-page])}
           [:r.user "Om meg" nil :icon :user]
           [:r.logg "Logg" nil :icon :circle]
           [:r.debug "Feilsøking" nil :icon :circle]]

          [k/case-route (fn [route] (-> route :data :name))
           :r.user
           [:div.space-y-4.dark:bg-gray-900.bg-gray-50
            [user.views/my-info]]

           :r.logg
           [hoc/user-logg]

           :r.debug
           [:div.space-y-4.dark:bg-gray-900.bg-gray-50
            [hoc/debug]]

           [:div "other " @route]]]]))))

(defn empty-list-message [msg]
  [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
   {:class "text-white/20"}
   [:div.text-2xl.font-black msg]
   [:div.text-xl.font-semibold "Ta kontakt med administrator"]])

(defn front []
  (let [user-auth (rf/subscribe [::db/user-auth])]
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
         {:class ["dark:bg-gray-900" "bg-gray-600"]}
         [:div.space-y-px.flex.flex-col
          {:style {:min-height "calc(100vh - 7rem)"}}
          (if (seq data)
            [:div.flex-1
             {:class (concat [:dark:bg-gray-700 :bg-gray-500])}
             [hoc/all-active-bookings {:data data}]]
            [empty-list-message "Booking-listen er tom"])
          [booking.views/last-bookings-footer {}]]])

      :r.boatlist
      (let [data (sort-by (comp :number val) < (logg.database/boat-db))]
        [:div
         {:class (concat [:dark:bg-gray-900 :bg-gray-600])}
         [:div.space-y-px.flex.flex-col
          {:style {:min-height "calc(100vh - 7rem)"}}
          (if (seq data)
            [:div.flex-1

             [hoc/all-boats
              {:data data
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
   :r.blog front})

;region events and subs

(rf/reg-sub :route-name
            :<- [:kee-frame/route]
            (fn [route _]
              (-> route :data :name)))

(rf/reg-sub :app/menu-open? :-> :menu-open?)

(rf/reg-sub :app/current-page
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :name)))

(rf/reg-sub :app/current-page-title
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :header)))

(rf/reg-event-db :toggle-menu-open
                 (fn [db]
                   (update db :menu-open? (fnil not false))))

(rf/reg-event-db :app/open-menu-at
                 (fn [db [_ tab]]
                   (assoc db :menu-open? true
                             :tab tab)))

(rf/reg-event-fx :app/navigate-to
                 [rf/trim-v]
                 (fn [_ [page]]
                   {:fx [[:navigate-to page]]}))

;endregion

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
       {:current-page       (fn [] @(rf/subscribe [:app/current-page]))
        :toggle-menu-open   (fn [] (rf/dispatch [:toggle-menu-open]))
        :navigate-to-home   (fn [] (rf/dispatch [:app/navigate-to [:r.common]]))
        :navigate-to-user   (fn [] (rf/dispatch [:app/navigate-to [:r.user]]))
        :current-page-title (fn [] @(rf/subscribe [:app/current-page-title]))
        :get-menuopen-fn    (fn [] @(rf/subscribe [:app/menu-open?]))
        :get-writingmode-fn (fn [] false)}
       web-content
       nil]]]))

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
