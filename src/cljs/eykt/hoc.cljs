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
            [schpaa.debug :as l]
            [booking.bookinglist]
            [eykt.hov :as hov]))

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

(defn last-active-booking [{:keys [uid] :as m}]
  [l/ppre-x ((juxt map? vector? list?) m)]
  [:<>
   (let [today (t/date)
         data (->> (booking.database/read)
                   (filter (fn [{:keys [] :as item}] (= (:uid item) uid)))
                   (filter (fn [{:keys [start]}] (t/<= today (t/date (t/date-time start)))))
                   (sort-by :start <)
                   first)]
     (if (some? data)
       [:<>
        ;[:h2 (:start data)]
        ;[:h2 (:end data)]

        #_[booking.bookinglist/booking-list-item
           {:boat-db      (sort-by (comp :number val) < (logg.database/boat-db))
            ;:accepted-user? accepted-user?
            :details? true
            :today        today
            :hide-name?   false ;(not (some? uid))
            #_#_:on-click (fn [e]
                            (swap! markings update id (fnil not false))
                            (.stopPropagation e))
            #_#_:insert-before (when @edit
                                 [:div.flex.items-center.px-2.bg-gray-400
                                  [fields/checkbox {:values        (fn [_] (get-in @markings [id] false))
                                                    :handle-change #(swap! markings update id (fnil not false))}
                                   "" nil]])
            :insert-after hov/open-booking-details-button}
           data]


        #_[:div.flex.justify-between
           [:div]
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
            "Avlys booking"]
           #_[:button.btn.btn-free.shadow-none {:on-click #(modal/form-action {:flags  #{:timeout :error :weak-dim}
                                                                               :icon   :squares
                                                                               :footer "footer"
                                                                               :title  "Bekreftet!"})} "Endre"]]]
       [:div "Du har ingen bookinger"]))])

(defn all-active-bookings [{:keys [data]}]
  (let [user-auth @(rf/subscribe [::db/user-auth])
        accepted-user? @(rf/subscribe [:app/accepted-user?])]
    [booking.bookinglist/booking-list
     {:boat-db        data
      :details?       @(rf/subscribe [:bookinglist/details])
      :accepted-user? accepted-user?
      :booking-data   (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) #_(booking.database/read)
      :today          (t/new-date 2022 1 26)
      :uid            (:uid user-auth)}]))



(defn all-boats [{:keys [details? data]}]
  [logg.views/all-boats
   {:details? details?
    :data data}])

(defn all-boats-footer [_]
  (r/with-let [opt2 (r/atom false)]
    [:div.flex.justify-between.items-center.gap-x-2.px-4.sticky.bottom-0.h-16
     {:class [:bg-gray-300 :dark:bg-gray-800 :dark:text-gray-500 :text-black]}
     (schpaa.components.views/modern-checkbox'
       {:set-details #(schpaa.state/change :opt1 %)
        :get-details #(-> (schpaa.state/listen :opt1) deref)}
       (fn [checkbox]
         [:div.flex.items-center.gap-2
          checkbox
          [:div.text-base.font-normal.space-y-0
           [:div.font-medium "Detaljer"]
           [:div.text-xs "Vis alle detaljer"]]]))

     (schpaa.components.views/modern-checkbox'
       {:disabled? true
        :set-details #(reset! opt2 %)
        :get-details #(-> opt2 deref)}
       (fn [checkbox]
         [:div.flex.items-center.gap-2.w-full.opacity-20
          [:div.text-base.font-normal.space-y-0
           [:div.font-medium.text-right "Grupper"]
           [:div.text-xs "Grupper etter merke"]]
          checkbox]))]))

;endregion

;region user

(defn user-logg []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [:div.select-none
     [booking.bookinglist/booking-list
      {:boat-db        (sort-by (comp :number val) < (logg.database/boat-db))
       :class          []
       :accepted-user? @accepted-user?
       :data           (booking.database/read)
       :today          (t/new-date 2022 1 4)
       :uid            (:uid @user-auth)}]]))


(defn debug []
  [:div.p-4.space-y-4.columns-3xs.gap-4                     ;.divide-x.divide-dashed.divide-black


   (for [e [:text-sm :text-base :text-xl :text-4xl]]
     [:div.xtabular-nums.slashed-zero.lining-enums.ordinal.font-sans.lining-nums
      {:class e}
      [:div e]
      [:div "1st"]
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