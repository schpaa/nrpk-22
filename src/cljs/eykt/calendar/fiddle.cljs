(ns eykt.calendar.fiddle
  (:require [tick.core :as t]
            [schpaa.style :as st]
            [times.api :as ta]))

(defn expand-into-slots [n slot-rule]
  slot-rule)

(def better-rules
  [(expand-into-slots 3 {:start "11:00"
                         :end   "14:00"
                         :day   t/SUNDAY})
   (expand-into-slots 3 {:start "14:00"
                         :end   "17:00"
                         :day   t/SUNDAY})])

(def target
  {})

(comment
  better-rules)


;(map #(week-number %) ())
(map (juxt identity #(ta/week-number (t/>> (t/new-date 2022 1 1) (t/new-period % :days)))) (range 32))

(comment
  (do
    (js/parseInt (t/format "D" (t/new-date 2022 2 2)))))



(defn first-day-at-week [week-number]
  (let [{:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)
        d (t/>> (ta/date-of-first-week (t/year (t/date)))
                (t/new-period (* (dec week-number) 7) :days))]
    [:div
     {:class bg}
     [:div "uke " week-number " " (str d) " " (str (t/day-of-week d))]]))

(comment
  ; 1 = sunday (7)
  ; find day-of-month of first day of first week
  (let [first-weekday-of-year (-> (t/new-date 2021 1 4)
                                  (t/day-of-week)
                                  t/int)

        #_#_t/int
            (#(+ % (if (< 4 %) (- 7 %) (- % 4))))]
    (if (< 4 first-weekday-of-year)
      (- 7 first-weekday-of-year)
      first-weekday-of-year)))

(comment
  (do
    (let [f (fn [y] (-> (t/new-date y 1 4)
                        (t/day-of-week)
                        t/int))]
      (map (juxt identity f) (range 2010 2023)))))

(comment
  (do
    (map (juxt identity f) (range 2010 2023))))

(comment
  (do
    (let [q (t/new-date 2022 1 3)
          dt (t/new-date 2022 1 1)
          doy (js/parseInt (t/format "D" q))
          dofw (date-of-first-week dt)
          md (t/day-of-month dofw)]
      (quot (+ doy
               (- 7
                  (t/int (t/day-of-week q))))
            7))))

