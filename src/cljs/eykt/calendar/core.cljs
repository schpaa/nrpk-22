(ns eykt.calendar.core
  (:require [db.core :as db]
            [tick.core :as t]
            [schpaa.time]
            [eykt.calendar.views :as views]))

(def all-week [t/MONDAY t/TUESDAY t/WEDNESDAY t/THURSDAY t/FRIDAY t/SATURDAY t/SUNDAY])

(def short-rules
  [{:section      "z1"
    :kald-periode true
    :description  "Kald periode ukedag"
    :startdate    #time/date "2022-05-08"
    :enddate      #time/date "2022-06-05"
    :weekdays     [t/TUESDAY t/WEDNESDAY t/THURSDAY]
    :times        [{:starttime #time/time "18:00"
                    :endtime   #time/time "21:00"
                    :slots     2}]}
   {:section      "z2"
    :kald-periode true
    :description  "Kald periode helg"
    :startdate    #time/date "2022-05-08"
    :enddate      #time/date "2022-06-05"
    :weekdays     [t/SATURDAY t/SUNDAY]
    :times        [{:starttime #time/time "11:00"
                    :endtime   #time/time "14:00"
                    :slots     3}
                   {:starttime #time/time "14:00"
                    :endtime   #time/time "17:00"
                    :slots     3}]}


   {:section     "z3"
    :description "Sommer ukedag"
    :startdate   #time/date "2022-06-06"
    :enddate     #time/date "2022-07-03"
    :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY]
    :times       [{:starttime #time/time "18:00"
                   :endtime   #time/time "21:00"
                   :slots     2}]}
   {:section     "z4"
    :description "Sommer helg"
    :startdate   #time/date "2022-06-06"
    :enddate     #time/date "2022-07-03"
    :weekdays    [t/SATURDAY t/SUNDAY]
    :times       [{:starttime #time/time "11:00"
                   :endtime   #time/time "14:00"
                   :slots     3}
                  {:starttime #time/time "14:00"
                   :endtime   #time/time "17:00"
                   :slots     3}]}


   {:section     "z5"
    :description "Utvidet åpningstid ukedag"
    :startdate   #time/date "2022-07-04"
    :enddate     #time/date "2022-08-14"
    :weekdays    [t/TUESDAY t/THURSDAY]
    :times       [{:starttime #time/time "12:00"
                   :endtime   #time/time "15:00"
                   :slots     2}
                  {:starttime #time/time "15:00"
                   :endtime   #time/time "18:00"
                   :slots     2}
                  {:starttime #time/time "18:00"
                   :endtime   #time/time "21:00"
                   :slots     2}]}

   {:section     "z5-1"
    :description "Utvidet åpningstid ukedag"
    :startdate   #time/date "2022-07-04"
    :enddate     #time/date "2022-08-14"
    :weekdays    [t/WEDNESDAY]
    :times       [{:starttime #time/time "18:00"
                   :endtime   #time/time "21:00"
                   :slots     2}]}

   {:section     "z6"
    :description "Utvidet åpningstid helg"
    :startdate   #time/date "2022-07-04"
    :enddate     #time/date "2022-08-14"
    :weekdays    [t/SATURDAY t/SUNDAY]
    :times       [
                  {:starttime #time/time "11:00"
                   :endtime   #time/time "14:00"
                   :slots     3}
                  {:starttime #time/time "14:00"
                   :endtime   #time/time "17:00"
                   :slots     3}]}

   {:section     "z7"
    :description "Sensommer ukedag"
    :startdate   #time/date "2022-08-16"
    :enddate     #time/date "2022-09-04"
    :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY]
    :times       [{:starttime #time/time "18:00"
                   :endtime   #time/time "21:00"
                   :slots     2}]}
   {:section     "z8"
    :description "Sensommer helg"
    :startdate   #time/date "2022-08-16"
    :enddate     #time/date "2022-09-04"
    :weekdays    [t/SATURDAY t/SUNDAY]
    :times       [{:starttime #time/time "11:00"
                   :endtime   #time/time "14:00"
                   :slots     3}
                  {:starttime #time/time "14:00"
                   :endtime   #time/time "17:00"
                   :slots     3}]}

   {:section     "z9"
    :description "Høst ukedag"
    :startdate   #time/date "2022-09-06"
    :enddate     #time/date "2022-10-09"
    :weekdays    [t/WEDNESDAY]
    :times       [{:starttime #time/time "17:00"
                   :endtime   #time/time "20:00"
                   :slots     2}]}

   {:section     "z10"
    :description "Høst helg"
    :startdate   #time/date "2022-09-06"
    :enddate     #time/date "2022-10-09"
    :weekdays    [t/SATURDAY t/SUNDAY]
    :times       [{:starttime #time/time "11:00"
                   :endtime   #time/time "14:00"
                   :slots     3}
                  {:starttime #time/time "14:00"
                   :endtime   #time/time "17:00"
                   :slots     3}]}])

(defn expanded-times [r]
  (-> (fn [a {:keys [times] :as e}]
        (conj a (map #(merge (dissoc e :times) %) times)))
      (reduce [] r)
      flatten))

(defn expanded-days [r]
  (-> (fn [a {:keys [weekdays] :as e :or {weekdays all-week}}]
        (conj a (map #(assoc (dissoc e :weekdays) :weekday %) weekdays)))
      (reduce [] r)
      flatten))

(def expand (comp expanded-times expanded-days))

(defn matches [dt {:keys [startdate enddate weekday] :as timerange}]
  (and
    (= weekday (t/day-of-week dt))
    (if (not-any? some? [startdate enddate])
      true
      (if-not enddate
        (t/= startdate dt)
        (t/<= startdate dt enddate)))))

(defn calculate [{:keys [utc-start utc-end rules]} n]
  (let [dt (t/>> utc-start (t/new-period n :days))
        r (filter #(matches (t/date dt) %) rules)]
    (if (and (t/<= dt utc-end) utc-end)
      (if (seq r)
        (mapv #(assoc (dissoc % :startdate :enddate :weekday) :dt dt) r)
        [{:dt dt}])
      nil)))

(defn iterate-dates [config n]
  (lazy-seq
    (when-let [c (calculate config n)]
      (cons c (iterate-dates config (inc n))))))

#_(defn expand-date-range [config]
    (let [config {:rules     (expand short-rules)
                  :utc-start (t/at (t/date "2022-05-07") (t/time "00:00"))
                  :utc-end   (t/at (t/date "2022-10-09") (t/time "00:00"))}]
      (iterate-dates config 0)))

(defn routine
  "Takes input (from db) and dislocates the keys of the parent and the immediate child.
  Unsure where and how to use this, (if at all useful?)"
  [db-input]
  (loop [xs db-input
         r {}]
    (if xs
      (let [[id vs] (first xs)]
        ;(tap> ["rooo4" id vs])
        (recur (next xs)
               (reduce-kv (fn [a k v] (update a (if (keyword? k) (name k) k) #(assoc % id v)))
                          r vs)))
      r)))

(defn render [r]
  (let [listener (db/on-value-reaction {:path ["calendar"]})]
    (fn [r]
      [views/table
       {:base (routine @listener)
        :data (iterate-dates
                {:rules     (expand short-rules)
                 :utc-start (t/at (t/date "2022-05-07") (t/time "00:00"))
                 :utc-end   (t/at (t/date "2022-10-09") (t/time "00:00"))}
                0)}])))
