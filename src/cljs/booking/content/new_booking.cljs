(ns booking.content.new-booking
  (:require [nrpk.fsm-helpers :as state :refer [send]]
            [booking.hoc :as hoc]
            [logg.database]
            [schpaa.components.views]
            [booking.database]
            [booking.views]))

(defn new-booking [user-auth]
  [booking.views/booking-form
   {:boat-db       (sort-by (comp :number val) < (logg.database/boat-db))
    :selected      hoc/selected
    :uid           (:uid user-auth)
    :on-submit     #(send :e.complete %)
    :cancel        #(send :e.cancel-booking)
    :my-state      schpaa.components.views/my-state
    :booking-data' (sort-by :date > (booking.database/read))}])