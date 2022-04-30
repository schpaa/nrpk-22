(ns booking.hoc
  "higher-order components"
  (:require [db.core :as db]
            [re-frame.core :as rf]
            [booking.views]
            [logg.views]
            [reagent.core :as r]
            [tick.core :as t]
            [booking.bookinglist]
            [schpaa.style :as st]))

(defonce selected (r/atom #{}))

(defn all-boats-footer [_]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
    (r/with-let [opt2 (r/atom false)]
      [:div.flex.justify-between.items-center.gap-x-2.px-4.sticky.bottom-0.h-16
       {:class (concat fg bg)}
       (schpaa.components.views/modern-checkbox'
         {:set-details #(schpaa.state/change :opt1 %)
          :get-details #(-> (schpaa.state/listen :opt1) deref)}
         (fn [checkbox]
           [:div.flex.items-center.gap-2
            checkbox
            [:div.text-base.font-normal.space-y-0
             [:div {:class (concat fg+ p)} "Detaljer"]
             [:div {:class (concat fg p-)} "Vis alle detaljer"]]]))

       (schpaa.components.views/modern-checkbox'
         {:disabled?   true
          :set-details #(reset! opt2 %)
          :get-details #(-> opt2 deref)}
         (fn [checkbox]
           [:div.flex.items-center.gap-2.w-full
            {:class (concat fg-)}
            [:div.text-base.font-normal.space-y-0
             [:div.text-right {:class p} "Grupper"]
             [:div {:class p-} "Grupper etter merke"]]
            checkbox]))])))

(defn user-logg []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])
        boat-db (sort-by (comp :number val) < (logg.database/boat-db))
        data-map (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read))]
    [:div.select-none
     [booking.bookinglist/booking-list
      {:boat-db        boat-db
       :class          []
       :accepted-user? @accepted-user?
       :booking-data   data-map
       :today          (t/new-date 2022 1 29)
       :uid            (:uid @user-auth)}]]))

