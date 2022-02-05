(ns user.database
  (:require [db.core :as db]
            [tick.core :as t']
            [schpaa.debug :as l]
            [tick.core :as t]
            [re-frame.core :as rf]
            [clojure.walk :as walk]))

(defn mark-account-for-removal [uid]
  (let [removal-date (t/>> (t/date) (t/new-period 14 :days))
        path ["users" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date (str removal-date)}})))

(defn mark-account-for-restore [uid]
  (let [removal-date (t/>> (t/date) (t/new-period 14 :days))
        path ["users" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date nil}})))


(defn write [diff {:keys [uid values] :as m}]
  (let [tm (str (t/instant))
        uid (-> m :uid)
        values (-> m
                   (dissoc :uid)
                   (assoc :timestamp tm
                          :version 2))]
    (db/firestore-set {:path ["users3" uid "log" tm] :value (walk/stringify-keys diff)})
    (db/database-update {:path ["users" uid] :value values}))
  #_(let [id (.-key (db/database-push {:path  path
                                       :value (assoc value
                                                :uid uid
                                                :version 2
                                                :timestamp (str (t'/instant)))}))]
      ;intent Perform a backup to the users doc
      (db/firestore-set {:path (conj path uid "trans" id) :value value})
      id))


(comment
  (let [values (-> data :values)
        uid (-> values :uid)
        values (dissoc values :uid)]
    (db/firestore-set {:path ["users2" uid] :value values})
    (db/database-update {:path ["users" uid] :value values})
    (tap> values)
    st))

(defn lookup-userinfo [uid]
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    @(db/on-value-reaction {:path ["users" uid]})
    nil))

(defn lookup-username [uid]
  ;(tap> ["U"])
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))
    nil))