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



(defn delete
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot]
        last-write (db.core/on-value-reaction {:path path})
        last-write-dt (some-> (get @last-write uid) t/date-time)
        since-last-write (when last-write-dt
                           (tick.alpha.interval/new-interval last-write-dt (t/date-time)))]
    (if (zero? (or (some-> since-last-write t/duration t/days) 0))
      (database-set {:path path :value {}})
      (js/alert "Vakten er låst, se instruksjoner om bytting på siden; 'Min Status'."))))

(defn frafall [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot uid]
        last-write (db.core/on-value-reaction {:path path})
        last-write-dt (some-> @last-write :registered t/date-time)]
    (database-update {:path  path
                      :value {:registered (str last-write-dt)
                              :cancel     (str (t/date-time))}})))

(defn deltar [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot uid]]
    (database-update {:path  path
                      :value {:cancel nil}})))

(comment
  @a)