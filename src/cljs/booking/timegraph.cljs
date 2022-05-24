(ns booking.timegraph
  (:require [tick.core :as t]
            [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [schpaa.debug :as l]))

(defn timegraph [{:keys [session-start session-end]} settings start end to now]
  (let [session-start (or session-start (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 11) (* 4 18)))
        session-end (or session-end (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 17) (* 4 21)))
        now (+ (* 4 (t/hour now))
               (quot (t/minute now) 15))
        start (if (and end
                       (t/< (t/date start) (t/date end)))
                0
                (+ (* 4 (t/hour start)) (quot (t/minute start) 15)))
        end (if-let [to' end]
              (if-let [x (some-> to' t/instant t/date-time)]
                (+ (* 4 (t/hour x)) (quot (t/minute x) 15)))
              nil)
        end (if (< now end) 10 end)
        field-color "var(--floating)"
        session-color "var(--selected)"
        time-color "var(--yellow-5)"
        timeline-color "var(--text3)"
        color (if (nil? end) "var(--blue-8)" "var(--text1) ")]

    [:div.h-8 {:on-click #(swap!
                            settings update :rent/graph-view-mode (fn [e] (mod (inc e) 2)))
               :style    {:overflow         :clip
                          :box-shadow       "var(--shadow-1)"
                          :border-radius    "var(--radius-1)"
                          :cursor           :pointer
                          :background-color field-color}}
     (let [;padding start and end
           v-start (case @(r/cursor settings [:rent/graph-view-mode])
                     0 (* 4 0)
                     1 (- session-start 4))
           v-end (case @(r/cursor settings [:rent/graph-view-mode])
                   0 96
                   1 (+ session-end 4))
           arrow-length (/ (- v-end v-start) 10)]
       [:svg
        {:viewBox             (l/strp v-start 0 (- v-end v-start) 8)
         :height              "100%"
         :width               "100%"
         :preserveAspectRatio "none"}
        [:rect {:fill    session-color
                :x       session-start
                :width   (- session-end session-start)
                :opacity 0.5
                :y       "0"
                :height  "8"}]
        (when (nil? end)
          [:path {:fill           color
                  :stroke-linecap :round
                  :d              (l/strp "M" now 2 "l" arrow-length 2 "l" (- arrow-length) 2 "z")}])
        [:path {:stroke         color
                ;:vector-effect  :non-scaling-stroke
                :stroke-width   (if (< 1 (count to)) 2 1)
                :stroke-linecap :round
                :d              (l/strp "M" start 4 "l" (- (if (< start end) end now) start) 0)}]
        [:line {:x2                96
                :x1                0
                :y2                "8"
                :y1                "8"
                :opacity           1
                :stroke-dashoffset "0"
                :stroke-dasharray  "4 4"
                :stroke-width      1
                :stroke            timeline-color}]])]))

(o/defstyled some-thing :div
  [:& :h-8
   {:overflow         :clip
    :cursor           :pointer
    :background-color "var(--floating)"}])

(defn hour-ticks [total-hours]
  (let [timeline-color "var(--text3)"]
    [:<>
     [:line {:x1                0
             :y1                7
             :x2                total-hours
             :y2                7
             :stroke-dashoffset 0
             :stroke-dasharray  "1 7"
             :stroke-width      4
             :stroke            timeline-color}]
     [:line {:x2                total-hours
             :x1                0
             :y2                0
             :y1                0
             :stroke-dashoffset 0
             :stroke-dasharray  "1 96"
             :stroke-width      5
             :stroke            timeline-color}]]))

(defn timegraph-multi [_idx {:keys [total-hours now session-start session-end settings ok]} xs]
  (let [[start end] (first xs)
        session-start (or session-start (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 11) (* 4 18)))
        session-end (or session-end (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 17) (* 4 21)))

        now (+ (* 4 (t/hour now))
               (quot (t/minute now) 15))
        end (if (< now end) 10 end)
        session-color "var(--green-5)"
        color (if (nil? end) "var(--blue-8)" "var(--text1) ")
        ;padding start and end
        v-start (case @(r/cursor settings [:rent/graph-view-mode])
                  0 (* 4 0)
                  1 (- session-start 4))
        v-end (case @(r/cursor settings [:rent/graph-view-mode])
                0 total-hours
                1 (+ session-end 4))
        arrow-length (/ (- v-end v-start) 10)]
    [some-thing {:on-click #(swap! settings update :rent/graph-view-mode (fn [e] (mod (inc e) 2)))}
     [:svg
      {:viewBox             (l/strp v-start 0 (- v-end v-start) 8)
       :height              "100%"
       :width               "100%"
       :preserveAspectRatio "none"}
      (hour-ticks total-hours)
      [:rect {:fill    (if ok session-color "var(--red-5)")
              :width   (- session-end session-start)
              ;:opacity 0.8
              :x       session-start
              :y       2
              :height  2}]
      (when (nil? end)
        [:path {:fill           color
                :stroke-linecap :round
                :d              (l/strp "M" now 2 "l" arrow-length 2 "l" (- arrow-length) 2 "z")}])
      (into [:<>]
            (for [[start end] xs
                  :let [start (if (and end (< start end)) start 0)]]
              [:path {:stroke         color
                      :stroke-width   2
                      :stroke-linecap :square
                      :d              (l/strp "M" start 5 "l" (- (if (< start end) end now) start) 0)}]))]]))

(comment
  (do
    (take 5 (iterate (fn [[a b]]
                       (let [a' (+ a (rand-int 10))
                             b' (+ a b (rand-int 10))]
                         [a' (+ a' b')])) [0 0]))))