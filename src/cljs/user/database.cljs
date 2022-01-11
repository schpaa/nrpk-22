(ns user.database
  (:require [db.core :as db]
            [tick.core :as t']
            [schpaa.debug :as l]
            [tick.core :as t]))

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