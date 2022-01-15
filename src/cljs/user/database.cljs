(ns user.database
  (:require [db.core :as db]
            [tick.core :as t']
            [schpaa.debug :as l]
            [tick.core :as t]
            [re-frame.core :as rf]))

(defn write [{:keys [uid values] :as m}]
  (js/alert (l/ppr m))
  (let [tm (str (t/instant))
        uid (-> m :uid)
        values (-> m
                   (dissoc :uid)
                   (assoc :timestamp tm
                          :version 2))]
    (db/firestore-set {:path ["users3" uid "log" tm] :value values})
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