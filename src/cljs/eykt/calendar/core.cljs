(ns eykt.calendar.core
  (:require [re-frame.core :as rf]
            [db.core :as db]
            [tick.core :as t]
            [times.api :as ta]
            [schpaa.time]
    ;[app.system.misc :as misc]
    ;["firebase/firestore" :refer [Timestamp]]
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
  [{:description "Dugnad2"
    :startdate   #time/date "2022-01-30"
    :enddate     #time/date "2022-03-03"
    :weekdays    all-week
    :times       [{:starttime #time/time "10:00" :endtime #time/time "12:00" :slots 1}
                  {:starttime #time/time "12:00" :endtime #time/time "14:10" :slots 3}]}
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

(defn expanded-times [r]
  (-> (fn [a {:keys [times] :as e}]
        (conj a (map #(merge (dissoc e :times) %) times)))
      (reduce [] r)
      flatten))

(defn expanded-days [r]
  (-> (fn [a {:keys [weekdays] :as e}]
        (conj a (map #(assoc (dissoc e :weekdays) :weekday %) weekdays)))
      (reduce [] r)
      flatten))

(def expand (comp expanded-times expanded-days))

(defn matches [dt {:keys [startdate enddate weekday] :as timerange}]
  (and
    (= weekday (t/day-of-week dt))
    (<= startdate dt enddate)))

(defn calculate [{:keys [utc-start utc-end rules] :as config} n]
  (let [dt (t/>> utc-start (t/new-duration n :days))
        r (filter #(matches dt %1) rules)]
    (if (and (t/<= dt utc-end) utc-end)
      (if (seq r)
        (mapv #(assoc (dissoc % :xstartdate :xenddate :weekday) :dt dt) r)
        [{:dt    dt
          :slots 0}])
      ;:weekday     (t/day-of-week dt)
      ;:description "?"
      ;:startdate   utc-start
      ;:enddate     utc-end
      ;:starttime   (t/time t/noon)
      ;:endtime     (t/time t/noon)})
      nil)))

(defn initial-name [config n]
  (lazy-seq
    (when-let [c (calculate config n)]
      (cons c (initial-name config (inc n))))))

(defn expand-date-range []
  (let [config {:rules     (expand rules')
                :utc-start (t/at (t/date "2022-01-01") "00:00")
                :utc-end   (t/at (t/date "2022-12-30") "00:00")}]

    #_(filter (fn [[e]] (< 0 (:slots e)))
              (initial-name config 0))
    (initial-name config 0)))

(defn routine
  "worked hard for this one, called on each update to transform db, must be fast
  or have limitation of data-scope"
  [input]
  (loop [xs input
         r {}]
    (if xs
      (let [[id vs] (first xs)]
        (recur (next xs)
               (reduce-kv (fn [a k v] (update a k #(assoc % id v)))
                          r vs)))
      r)))

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
