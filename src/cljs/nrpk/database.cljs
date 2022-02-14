(ns nrpk.database
  (:require [db.core :as db]
            [schpaa.debug :as l]
            [re-frame.core :as rf]
            [tick.core :as t]
            [times.api :as ta]))

;region

(defn get-username [uid]
  (or (some-> (db/on-value-reaction {:path ["users" uid]}) deref :alias) uid))

(def get-username-memoed (memoize get-username))

;endregion

;todo does not belong here
(defn cloud-content []
  (let [path ["presence"]
        data @(db/on-value-reaction {:path path})
        presence-state @(rf/subscribe [::db/presence-status])]
    [:div.space-y-1.p-2
     [:div (ta/format "%d offline" (count (:offline presence-state)))]
     ;[l/ppre-x (:offline presence-state)]

     (for [[k v] (:offline presence-state)]
       (let [instant (t/instant (js/Date. (:lastOnline v)))
             date (ta/date-format instant)
             time (ta/time-format instant)]
         [l/ppre-x (get-username (name k)) date time]))

     [:div (ta/format "%d online" (count (:online presence-state)))]
     [l/ppre-x (:online presence-state)]

     (for [[k v] (:online presence-state)]
       [l/ppre-x k (get-username (name k)) (:connections v)])]))
