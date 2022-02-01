(ns eykt.calendar.actions
  (:require [db.core :refer [database-set database-update]]
            [tick.core :as t]))

(comment
  (do
    (-> (db.core/database-set
          {:path  ["calendar"]
           :value {"CHRIS" {"2022-05-14T11:00" (str (t/>> (t/now) (t/new-duration 2 :minutes)))
                            "2022-05-14T14:00" (str (t/now))
                            "2022-05-03T09:00" (str (t/now))}
                   "JAHN"  {"2022-05-14T11:00" (str (t/>> (t/now) (t/new-period 2 :weeks)))}
                   "LISE"  {"2022-05-14T11:00" (str (t/>> (t/now) (t/new-period 2 :weeks)))}
                   "LONER" {"2022-05-14T11:00" (str (t/now))}
                   "PETER" {"2022-05-11T09:00" (str (t/now))}
                   "JON"   {"2022-05-15T11:00" (str (t/now))
                            "2022-05-21T11:00" (str (t/now))}
                   "ARNE"  {"2022-06-04T11:00" (str (t/now))
                            "2022-06-04T12:00" (str (t/now))
                            "2022-06-04T13:00" (str (t/now))}}})
        (.then (fn [] (tap> "completed!"))))))

(defn delete
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid group timeslot]}]
  (let [path ["calendar" (name uid) group (name timeslot)]]
    (database-set {:path path :value {}})))

(defn add
  "add to database at [calendar uid timeslot]"
  [{:keys [uid group timeslot]}]
  (let [path ["calendar" (name uid) group]]
    (database-update {:path path :value {(name timeslot) (str (t/now))}})))

(defn add'
  "add to database at [calendar uid timeslot]"
  [{:keys [uid group timeslot dateslot]}]
  (let [path ["calendar" dateslot (name uid) group]]
    (database-update {:path path :value {(name timeslot) (str (t/now))}})))

(defn delete'
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid group timeslot dateslot]}]
  (let [path ["calendar" dateslot (name uid) group timeslot]]
    (database-set {:path path :value {}})))

;region

;intent helpers, development for further usage

(defn xxx
  "takes a (torv.pages.calendar/expand-date-range) and gives a list of all date/time-slots
  in a sorted order"
  [data]
  (->> data
       (filter (fn [[e]] (< 0 (:slots e))))
       (flatten)
       (map (fn [{:keys [dt starttime]}]
              (-> (t/date dt) (t/at starttime))))))

(comment
  (do
    (reduce
      (fn [a e] (assoc a (str e) (str (t/now))))
      {}
      (xxx (torv.pages.calendar/expand-date-range)))))

(defn- prepare-for-writing [data]
  (reduce
    (fn [a e] (assoc a (str e) (str (t/now))))
    {}
    (xxx data)))

(defn add-all
  "add to database at [calendar uid timeslot]"
  [{:keys [uid date-range]}]
  (let [path ["calendar" (name uid)]]
    ;(tap> (xxx date-range))
    (database-update {:path path :value (prepare-for-writing date-range)})))

(defn delete-all
  "add to database at [calendar uid timeslot]"
  [{:keys [uid date-range]}]
  (let [path ["calendar" (name uid)]]
    ;(tap> (xxx date-range))
    (database-set {:path path :value nil #_(prepare-for-writing date-range)})))

;end-region