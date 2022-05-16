(ns eykt.calendar.actions
  (:require [db.core :refer [database-set database-update]]
            [tick.core :as t]))

(defn add
  "add to database at [calendar uid section timeslot]"
  [{:keys [uid section timeslot]}]
  (let [uid (if (keyword? uid) (name uid) uid)
        path ["calendar" uid section]
        value {(name timeslot) {uid (str (t/date-time))}}]
    (database-update {:path  path
                      :value value})))

(defonce a (atom nil))

(defn delete
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid section timeslot]}]
  (let [path ["calendar" (some-> uid name) section (some-> timeslot name)]
        last-write (db.core/on-value-reaction {:path path})
        last-write-dt (some-> (get @last-write uid) t/date-time)
        since-last-write (when last-write-dt
                           (tick.alpha.interval/new-interval last-write-dt (t/date-time)))]
    (reset! a @last-write)
    (if (zero? (or (some-> since-last-write t/duration t/days) 0))
      (database-set {:path path :value {}})
      (js/alert "Vakten er låst, se instruksjoner om bytting på siden; 'Min Status'."))))

(comment
  @a)