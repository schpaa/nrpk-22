(ns eykt.hoc
  "higher-order components"
  (:require [db.core :as db]
            [re-frame.core :as rf]
            [booking.views]
            [logg.views]
            [schpaa.modal :as modal]
            [eykt.fsm-helpers :refer [send]]
            [reagent.core :as r]
            [tick.core :as t]
            [schpaa.components.views :as views]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

;region booking

(defonce selected (r/atom #{}))

(defn new-booking []
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [booking.views/booking-form
     {:boat-db       (sort-by (comp :number val) < (logg.database/boat-db))
      :selected      selected
      :uid           (:uid @user-auth)
      :on-submit     #(send :e.complete %)
      :cancel        #(send :e.cancel-booking)
      :my-state      schpaa.components.views/my-state
      :booking-data' (sort-by :date > (booking.database/read))}]))



(defn last-active-booking []
  [:<>
   #_(when-let [u @(rf/subscribe [::db/user-auth])]
       [l/ppre (:uid u)])
   (let [today (t/date)
         data (->> (booking.database/read)
                   (filter (fn [{:keys [uid]}] (= uid (:uid @(rf/subscribe [::db/user-auth])))))
                   (filter (fn [{:keys [start]}] (t/<= today (t/date (t/date-time start)))))
                   (sort-by :start <)
                   first)]
     (if (some? data)
       [:<>
        #_(r/with-let [st (r/atom true)]
            (views/modern-checkbox st))


        [:h2 (:start data)]
        [:h2 (:end data)]
        [:div.flex.justify-between
         [:button.btn.btn-danger
          {:on-click #(modal/form-action
                        {:flags   #{:no-icon :no-crossout :-timeout}
                         :footer  "Du kan ikke angre dette"
                         :title   "Avlys booking"
                         :form-fn (fn [] [:div
                                          [:div.p-4 "Er du sikker på at du vil avlyse denne bookingen?"]
                                          [modal/just-buttons
                                           [["Behold" [:btn-free] (fn [] (send :e.hide))]
                                            ["Avlys" [:btn-danger] (fn []
                                                                     (tap> ["save settings " 123])
                                                                     (send :e.hide))]]]])})}
          "Avlys"]
         [:button.btn.btn-free.shadow-none {:on-click #(modal/form-action {:flags  #{:timeout :error :weak-dim}
                                                                           :icon   :squares
                                                                           :footer "footer"
                                                                           :title  "Bekreftet!"})} "Endre"]]]
       [:div "Du har ingen bookinger"]))
   #_[booking.views/pick-list
      {:selected (r/atom #{})
       :boat-db  (logg.database/boat-db)
       :day      1
       :slot     (tick.alpha.interval/bounds
                   (t/at (t/new-date 2022 1 3) (t/new-time 14 0))
                   (t/at (t/new-date 2022 1 4) (t/new-time 13 0)))
       :on-click #(js/alert "!")}]])

(defn all-active-bookings []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [:<>
     [:div.flex.justify-between.p-4.bg-gray-50
      [:button.btn.btn-free "Vis mine"]
      [:button.btn.btn-free "Vis alle"]]
     [booking.views/booking-list
      {:boat-db        (sort-by (comp :number val) < (logg.database/boat-db))
       :class          [:bg-gray-400]
       :accepted-user? @accepted-user?
       :booking-data   (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) #_(booking.database/read)
       :today          (t/new-date 2022 1 21)
       :uid            (:uid @user-auth)}]]))

(defn all-boats []
  [logg.views/all-boats
   {:data (sort-by (comp :number val) < (logg.database/boat-db))}])

;endregion

;region user

(defn user-logg []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [:div.select-none
     [booking.views/booking-list
      {:boat-db        (sort-by (comp :number val) < (logg.database/boat-db))
       :class          []
       :accepted-user? @accepted-user?
       :data           (booking.database/read)
       :today          (t/new-date 2022 1 4)
       :uid            (:uid @user-auth)}]]))


(defn debug []
  [:div.p-4.space-y-4.columns-3xs.gap-4                     ;.divide-x.divide-dashed.divide-black
   [:div (str devtools.version/current-version)]

   (for [e [:text-sm :text-base :text-xl :text-4xl]]
     [:div.font-sans
      {:class e}
      [:div e]
      [:div.font-thin "0123456789"]
      [:div.font-extralight "0123456789"]
      [:div.font-light "0123456789"]
      [:div.font-normal "0123456789"]
      [:div.font-medium "0123456789"]
      [:div.font-semibold "0123456789"]
      [:div.font-bold "0123456789"]
      [:div.font-extrabold "0123456789"]
      [:div.font-black "0123456789"]])])
;endregion