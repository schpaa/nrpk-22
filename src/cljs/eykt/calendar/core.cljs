(ns eykt.calendar.core
  (:require [re-frame.core :as rf]
            [db.core :as db]
            [tick.core :as t]
            [times.api :as ta]
            [schpaa.time]
            [schpaa.debug :as l]
            [eykt.calendar.views :as views]
            [eykt.calendar.widgets :as widgets]
            [eykt.calendar.calculations :refer [calc]]
            [schpaa.style :as s]))

(rf/reg-event-db ::set-tab (fn [db [_ tab]]
                             (assoc db ::tab tab)))

(rf/reg-sub ::get-tab (fn [db] (get db ::tab :table)))

(def all-week [t/MONDAY t/TUESDAY t/WEDNESDAY t/THURSDAY t/FRIDAY t/SATURDAY t/SUNDAY])

(def short-rules
  [;intent This is not really a rule, since it is a one-time-happening
   {:group       :z1
    :description "UKE"
    :startdate   #time/date "2022-01-31"
    :enddate     #time/date "2022-05-13"
    :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY]
    :times       [{:starttime #time/time "18:00"
                   :endtime   #time/time "21:00"
                   :slots     2}]}
   {:group       :z2
    :description "HELG"
    :startdate   #time/date "2022-01-31"
    :enddate     #time/date "2022-05-13"
    :weekdays    [t/SATURDAY t/SUNDAY]
    :times       [{:starttime #time/time "11:00"
                   :endtime   #time/time "14:00"
                   :slots     3}
                  {:starttime #time/time "14:00"
                   :endtime   #time/time "17:00"
                   :slots     3}]}
   {:group       :c
    :description "aftermath is a long title"
    :startdate   #time/date "2022-01-31"
    :enddate     #time/date "2022-02-13"
    :weekdays    all-week                                   ;[t/MONDAY t/SUNDAY]
    :times       [{:starttime #time/time "06:00"
                   :endtime   #time/time "07:00"
                   :slots     1}]}
   #_{:group       :b
      :description "Party"
      :startdate   #time/date "2022-01-31"
      :enddate     #time/date "2022-02-13"
      ;:weekdays    [t/THURSDAY] ;t/SATURDAY t/SUNDAY
      :weekdays    [t/MONDAY t/SUNDAY]
      :times       [{:starttime #time/time "07:00"
                     :endtime   #time/time "10:00"
                     :slots     2}]}
   #_{:group       :a
      :description "SIMPLE test"
      :startdate   #time/date "2022-01-31"
      :enddate     #time/date "2022-02-13"
      :weekdays    [t/MONDAY t/SUNDAY]
      :times       [{:starttime #time/time "11:00"
                     :endtime   #time/time "14:00"
                     :slots     3}
                    {:starttime #time/time "14:00"
                     :endtime   #time/time "17:00"
                     :slots     3}
                    {:starttime #time/time "17:00"
                     :endtime   #time/time "17:16"
                     :slots     2}]}

   #_#_#_{:description "helg"
          :startdate   #time/date "2022-01-30"
          :enddate     #time/date "2022-03-03"
          :weekdays    [t/SATURDAY t/SUNDAY]
          :times       [{:starttime #time/time "11:00" :endtime #time/time "14:00" :slots 3}
                        {:starttime #time/time "14:00" :endtime #time/time "17:00" :slots 3}]}

       {:description "uke"
        :startdate   #time/date "2022-01-30"
        :enddate     #time/date "2022-03-03"
        :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY]
        :times       [{:starttime #time/time "18:00" :endtime #time/time "21:00" :slots 2}]}

       {:description "ONSDAGSGRUPPA"
        :startdate   #time/date "2022-01-30"
        :enddate     #time/date "2022-02-03"
        :weekdays    [t/WEDNESDAY]
        :times       [{:starttime #time/time "10:00" :endtime #time/time "12:00" :slots 1}
                      {:starttime #time/time "12:00" :endtime #time/time "14:10" :slots 3}]}])

(def long-rules'
  [#_{:description "Dugnad"
      :startdate   #time/date "2022-05-01"
      :enddate     #time/date "2022-05-04"
      :weekdays    all-week
      :times       [{:starttime #time/time "10:00" :endtime #time/time "12:00" :slots 8}
                    {:starttime #time/time "12:00" :endtime #time/time "14:00" :slots 8}
                    {:starttime #time/time "14:00" :endtime #time/time "16:00" :slots 8}]}
   #_{:description "Kald periode"
      :startdate   #time/date "2022-05-01"
      :enddate     #time/date "2022-05-30"
      :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY t/SATURDAY t/SUNDAY]
      :times       [{:starttime #time/time "11:00" :endtime #time/time "14:00" :slots 4}
                    {:starttime #time/time "15:00" :endtime #time/time "15:20" :slots 8}]}


   #_{:description "Forbønn"
      :startdate   #time/date "2022-01-01"
      :enddate     #time/date "2022-12-31"
      :weekdays    [t/MONDAY t/TUESDAY t/WEDNESDAY t/THURSDAY t/FRIDAY t/SATURDAY t/SUNDAY]
      :times       [{:starttime #time/time "06:00" :endtime #time/time "06:15" :slots 5}
                    {:starttime #time/time "06:15" :endtime #time/time "06:30" :slots 5}
                    {:starttime #time/time "06:30" :endtime #time/time "06:45" :slots 5}]}

   {:description "A"
    :startdate   #time/date "2022-05-14"
    :enddate     #time/date "2022-10-14"
    :weekdays    [t/SUNDAY t/SATURDAY]
    :times       [{:starttime #time/time "11:00" :endtime #time/time "14:00" :slots 2}
                  {:starttime #time/time "14:00" :endtime #time/time "17:00" :slots 2}
                  {:starttime #time/time "17:00" :endtime #time/time "17:10" :slots 2}
                  {:starttime #time/time "17:10" :endtime #time/time "18:00" :slots 5}]}

   {:description "B1"
    :startdate   #time/date "2022-05-14"
    :enddate     #time/date "2022-09-28"
    :weekdays    [t/TUESDAY t/WEDNESDAY t/THURSDAY]
    :times       [{:starttime #time/time "18:00" :endtime #time/time "21:00" :slots 2}]}

   {:description "utvidet sommeråpent"
    :startdate   #time/date "2022-07-01"
    :enddate     #time/date "2022-07-31"
    :weekdays    [t/TUESDAY t/THURSDAY]
    :times       [{:starttime #time/time "12:00" :endtime #time/time "15:00" :slots 2}
                  {:starttime #time/time "15:00" :endtime #time/time "18:00" :slots 2}]}
   {:description "begrenset høståpent"
    :startdate   #time/date "2022-05-29"
    :enddate     #time/date "2022-10-14"
    :weekdays    [t/TUESDAY t/THURSDAY]
    :times       [{:starttime #time/time "17:00" :endtime #time/time "20:00" :slots 2}]}])

(def rules' short-rules)

;region

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

(defn calculate [{:keys [utc-start utc-end rules] :as config} n]
  (let [dt (t/>> utc-start (t/new-period n :days))
        r (filter (partial matches dt) rules)]
    (if (and (t/<= dt utc-end) utc-end)
      (if (seq r)
        (mapv #(assoc (dissoc % :startdate :enddate :weekday) :dt dt) r)
        [{:dt dt}])
      nil)))

(defn iterate-dates [config n]
  (lazy-seq
    (when-let [c (calculate config n)]
      (cons c (iterate-dates config (inc n))))))

(defn expand-date-range []
  (let [config {:rules     (expand rules')
                :utc-start (t/at (t/date "2022-01-21") "00:00")
                :utc-end   (t/at (t/date "2022-03-14") "00:00")}]

    #_(filter (fn [[e]] (< 0 (:slots e)))
              (iterate-dates config 0))
    (iterate-dates config 0)))

;endregion

(defn routine
  "
  Takes input (from db) and dislocates the keys of the parent and the immediate child.
  Unsure where and how to use this, (if at all useful?)
  "
  [db-input]
  (loop [xs db-input
         r {}]
    (if xs
      (let [[id vs] (first xs)]
        (recur (next xs)
               (reduce-kv (fn [a k v] (update a k #(assoc % id v)))
                          r vs)))
      r)))

(defn rooo [data]
  (let [source (routine data)]
    (reduce (fn [a group-id] (update a group-id routine)) source (keys source))))

(defn z
  "?"
  [data]
  (reduce (fn [a e] (update a (str (:dt e)) update (:group e) (fnil conj []) e)) {} data))

(comment
  (do
    (let [data [{:description "SIMPLE test",
                 :starttime   #time/time"11:00",
                 :endtime     #time/time"14:00",
                 :ugh         "?",
                 :slots       3,
                 :dt          #time/date"2022-10-02"}
                {:description "SIMPLE test",
                 :starttime   #time/time"14:00",
                 :endtime     #time/time"17:00",
                 :slots       3,
                 :dt          #time/date"2022-10-02"}
                {:description "Party",
                 :starttime   #time/time"11:00",
                 :endtime     #time/time"14:00",
                 :ugh         "?",
                 :slots       2,
                 :dt          #time/date"2022-10-02"}]]
      (z data))))

(defn massage [data]
  (reduce (fn [a e-vecblock] (conj a (z e-vecblock))) {} data))

(comment
  (do
    (->> (iterate-dates {:rules     (expand rules')
                         :utc-start (t/date "2022-01-31")
                         :utc-end   (t/date "2022-02-06")} 0)
         (massage))))

(defn grab-for-graph [date-bounds]
  (->> (iterate-dates {:rules     (expand rules')
                       :utc-start (t/date (t/beginning date-bounds)) ;(t/date "2022-09-30")
                       :utc-end   (t/date (t/end date-bounds)) #_(t/date "2022-10-04")} 0)
       (massage)))


(comment
  (do
    (grab-for-graph
      (tick.alpha.interval/new-interval
        (t/date "2022-01-31")
        (t/date "2022-02-05")))))

(comment
  (do
    (expanded-days rules')))

(comment
  (do
    (let [listener @(db/on-value-reaction {:path ["calendar"]})]
      (routine listener))))

(def render-tab-data
  {:table    {:text "Tabell"}
   :calendar {:text "Kalender"}
   :summary  {:text "Summary"}
   :week     {:text "Uke"}
   :data-1   {:text "d1"}
   :data-2   {:text "d2"}})

(defn render [r]
  (let [uid (rf/subscribe [::db/root-auth :uid])
        current-tab (rf/subscribe [::get-tab])
        listener (db/on-value-reaction {:path ["calendar"]})
        color-map (s/color-map :main)]
    (fn [r]
      [:div.-mx-4.bg-gray-900
       {:class (get-in s/color-map [:main :content :bg2])}

       [:div                                                ;.sticky.top-0
        [widgets/tab-machine
         {:tabs-id          :basics
          :class            (get-in color-map [:normal :bg])
          :selected-classes (get-in color-map [:selected :tab])
          :normal-classes   (get-in color-map [:normal :tab])
          :current-tab      @(rf/subscribe [::widgets/get-tab :basics :table])}
         render-tab-data]]

       ;[:div @uid]
       [:div.p-4
        {:class (get-in s/color-map [:main :content :bg])}
        (case @(rf/subscribe [::widgets/get-tab :basics :table])
          :table
          [views/table
           {:base (routine @listener)
            :data (expand-date-range)}]

          :calendar
          [views/calendar
           {:base (routine @listener)
            :data (expand-date-range)}]

          :summary
          [views/summary
           {:root @listener
            :base (routine @listener)
            :data (expand-date-range)}]

          :week
          [:div
           [l/ppre (calc {:uid  (keyword @(rf/subscribe [::db/root-auth :uid]))
                          :base (expand-date-range)
                          :data @listener})]
           (let [lookup-duration-minutes (->> (expand-date-range)
                                              (filter (fn [[e]] (< 0 (:slots e))))
                                              (flatten)
                                              (map (fn [{:keys [dt starttime endtime]}]
                                                     (let [start (-> (t/date dt) (t/at starttime))
                                                           end (-> (t/date dt) (t/at endtime))]
                                                       {:start   (-> (t/date dt) (t/at starttime) str)
                                                        :end     (-> (t/date dt) (t/at endtime) str)
                                                        :dur-min (-> (/ (-> (t/units (t/between start end)) :seconds)
                                                                        60))})))
                                              (reduce (fn [a {:keys [start dur-min] :as e}]
                                                        (assoc a (keyword start) dur-min))
                                                      {}))
                 total-minutes (reduce + 0 (map lookup-duration-minutes
                                                (keys (get @listener (keyword @uid)))))]
             [:div
              [:div uid]
              [l/ppre-x
               (/ total-minutes 60)
               (mod total-minutes 60)
               (quot total-minutes 60)]
              [:div.h-1]
              [:div "ELSE"]
              #_[l/ppre (sort (group-by (juxt (comp t/month :dt)
                                              (comp t/last-day-of-month t/date :dt))
                                        (flatten (filter (fn [[e]] (< 0 (:slots e))) (expand-date-range)))))]])]
          :data-1
          [l/ppre-x (group-by #(select-keys % [:description :startdate :enddate])
                              (flatten (filter (fn [[e]] (< 0 (:slots e))) (expand-date-range))))]

          :data-2
          [l/ppre-x (group-by (juxt :description :startdate :enddate)
                              (flatten (filter (fn [[e]] (< 0 (:slots e))) (expand-date-range))))]

          [:div "?"])]])))
