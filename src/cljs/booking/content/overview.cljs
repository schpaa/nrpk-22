(ns booking.content.overview
  (:require [nrpk.fsm-helpers :as state :refer [send]]
            [booking.hoc :as hoc]
            [logg.database]
            [schpaa.components.views]
            [booking.database]
            [booking.views]
            [schpaa.style :as st]))

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

(defn overview []
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :void)
        data (sort-by (comp :number val) < (logg.database/boat-db))]
    [:div
     {:class bg}
     [:div.space-y-px.flex.flex-col
      {:style {:min-height "100vh" #_"calc(100vh - 7rem)"}}
      (if (seq data)
        [:div.flex-1
         {:class bg}
         [hoc/all-active-bookings {:data data}]]
        [empty-list-message "Booking-listen er tom"])
      [booking.views/last-bookings-footer {}]]]))