(ns eykt.hoc
  "Eykt higher-order components"
  (:require [db.core :as db]
            [re-frame.core :as rf]
            [booking.views]
            [eykt.state :as state]
            [reagent.core :as r]
            [tick.core :as t]
            [schpaa.components.views :as views]
            [schpaa.icon :as icon]))

;region

(defonce selected (r/atom #{}))

(defn new-booking []
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [booking.views/booking-form
     {:boat-db       (logg.database/boat-db)
      :selected      selected
      :uid           (:uid @user-auth)
      :on-submit     #(state/send :e.complete %)
      :cancel        #(state/send :e.cancel-booking)
      :my-state      schpaa.components.views/my-state
      :booking-data' (sort-by :date > (booking.database/read))}]))

(defn last-active-booking []
  [:<>
   [:h2 "Søndag 3 August"]
   [:h2 "16:00 --> 21:00"]
   (booking.views/pick-list
     {:selected (r/atom #{473})
      :boat-db  (logg.database/boat-db)
      :day      1
      :slot     (tick.alpha.interval/bounds
                  (t/at (t/new-date 2022 1 3) (t/new-time 14 0))
                  (t/at (t/new-date 2022 1 4) (t/new-time 13 0)))
      :on-click #(js/alert "!")})
   [:div.flex.justify-between
    [:button.btn.btn-danger
     {:on-click #(apply eykt.state/send
                        (eykt.msg/are-you-sure?
                          {:on-confirm  (fn [] (tap> "confirmed!"))
                           :primary     "Ja, slett"
                           :alternative "Avbryt"
                           :title       "Avlys booking"
                           :text        [:div.leading-relaxed
                                         [:p.mb-2 "Dette vil slette bookingen."]
                                         [:p "Er du sikker du vil dette? Du kan ikke angre etterpå!"]]}))}
     "Avlys"]
    [:button.btn.btn-free.shadow-none "Endre"]]])

(defn all-active-bookings []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [booking.views/booking-list
     {:accepted-user? @accepted-user?
      :data           (booking.database/read)
      :today          (t/new-date 2022 1 4)
      :uid            (:uid @user-auth)}]))

(defn all-boats []
  [booking.views/all-boats {}])

;endregion

;region user

(defn user-logg []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [:div.select-none
     [booking.views/booking-list
      {:class          [:panel :px-4]
       :accepted-user? @accepted-user?
       :data           (booking.database/read)
       :today          (t/new-date 2022 1 4)
       :uid            (:uid @user-auth)}]]))


;endregion