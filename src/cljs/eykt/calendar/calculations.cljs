(ns eykt.calendar.calculations
  (:require [tick.core :as t]))

(defn calc [{:keys [uid base data]}]
  (let [lookup-duration-minutes
        (->> base
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
                                       (keys data)))]
    {:hours   (quot total-minutes 60)
     :minutes (mod total-minutes 60)}))



