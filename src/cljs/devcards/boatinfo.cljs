(ns devcards.boatinfo
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [booking.modals.boatinfo :as sud2]
            [tick.core :as t]))

(def uid "RX12902139012309123")

(defcard-rg incomplete
            [:div
             (sud2/worklog-card {} {:description "Description"
                                    :uid uid
                                    :complete false
                                    :timestamp (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg incomplete-long-description
  ;"-long-description"
  [:div
   (sud2/worklog-card {}
                      {:description "Description -long-description -long-description -long-description -long-description -long-description -long-description -long-description"
                       :uid uid
                       :extra false
                       :keywords false
                       :complete false
                       :timestamp (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg completed
            [:div
             (sud2/worklog-card {} {:description "Description"
                                    :uid uid
                                    :complete true
                                    :timestamp (t/now)} 'boat-item-id 'worklog-entry-id)])
