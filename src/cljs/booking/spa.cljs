(ns booking.spa
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [booking.data :as data :refer [start-db routes]]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.components.tab :refer [tab]]
            [schpaa.time]
            [schpaa.darkmode]
            [nrpk.fsm-helpers :as state :refer [send]]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [booking.views]
            [user.views]
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
         [:div.p-4.max-w-md.mx-auto
          [user.views/userstatus-form
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
