(ns eykt.calendar.actions
  (:require [db.core :refer [database-set database-update]]
            [tick.core :as t]))

(defn add
  "add to database at [calendar uid timeslot]"
  [{:keys [uid section timeslot]}]
  ;(js/alert "not allowed!")
  (let [uid (if (keyword? uid) (name uid) uid)
        path ["calendar" uid section]
        value {(name timeslot) {uid (str (t/date-time))}}]
    (database-update
      {:path  path
       :value value})))

(defonce a (atom nil))

(defn delete
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid section timeslot]}]
  (let [path ["calendar" (name uid) section (some-> timeslot name)]
        last-write (db.core/on-value-reaction {:path path})
        last-write-dt (some-> (get @last-write uid) t/date-time)
        since-last-write (tick.alpha.interval/new-interval last-write-dt (t/date-time))
        #_(js/alert (str
                      path
                      @last-write))]
    (reset! a @last-write)
    ;(js/alert (str last-write-dt))
    (if (zero? (t/days (t/duration since-last-write)))
      (database-set {:path path :value {}})
      (js/alert "Vakten er låst, se instruksjoner om bytting på siden; 'Min Status'."))))

(comment
  @a)