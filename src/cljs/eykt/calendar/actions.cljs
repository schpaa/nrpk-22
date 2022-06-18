(ns eykt.calendar.actions
  (:require [db.core :refer [database-set database-update]]
            [tick.core :as t]))

(defn add
  "add to database at [calendar uid section timeslot]"
  [{:keys [uid section timeslot]}]
  (let [uid (if (keyword? uid) (name uid) uid)
        path ["calendar" uid section (name timeslot)]
        value {uid {:registered (str (t/date-time))}}]
    (database-update {:path  path
                      :value value})))

(defn check-can-change? [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot uid "registered"]
        uid' (keyword uid)]
       (when-some [last-write (db.core/on-value-reaction {:path path})]
         (let [_ (tap> {:last-write' @last-write})
               last-write-dt (or
                               (some-> @last-write t/date-time)
                               #_(try (some-> (get-in @last-write [uid']) t/date-time) (catch js/Error _ nil)))]
           (if last-write-dt
             (zero? (some-> (tick.alpha.interval/new-interval last-write-dt (t/date-time)) t/duration t/days))
             false)))))

(defn delete
  "delete from database at [calendar uid timeslot]"
  [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot]
        last-write (db.core/on-value-reaction {:path path})
        registered? (get-in @last-write [(keyword uid) :registered])
        _ (tap> {:last-write-raw @last-write})
        _ (tap> {:registered? registered?})
        last-write-dt (or
                        (some-> (get-in @last-write [(keyword uid) :registered]) t/date-time)
                        (some-> (get @last-write (keyword uid)) t/date-time))
        _ (tap> {:last-write-dt last-write-dt})
        since-last-write (when last-write-dt
                           (tick.alpha.interval/new-interval last-write-dt (t/date-time)))]
    (if (zero? (some-> since-last-write t/duration t/days))
      (database-set {:path path :value {}})
      (js/alert "Vakten er låst, se instruksjoner om bytting på siden; 'Min Status'."))))

(defn frafall [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot uid]
        last-write (db.core/on-value-reaction {:path path})
        ;last-write-dt (some-> @last-write :registered t/date-time)
        last-write-dt (or
                        (some-> (get-in @last-write [:registered]) t/date-time)
                        (some-> @last-write t/date-time))]
    (database-update {:path  path
                      :value {:registered    (str last-write-dt)
                              :cancel (str (t/date-time))}})))

(defn deltar [{:keys [uid section timeslot]}]
  (let [uid (some-> uid name)
        timeslot (some-> timeslot name)
        path ["calendar" uid section timeslot uid]]
    (database-update {:path  path
                      :value {:cancel nil}})))

(comment
  @a)