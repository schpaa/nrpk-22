(ns booking.flextime
  (:require [tick.core :as t]
            [lambdaisland.ornament :as o]
            [tick.alpha.interval]
            [schpaa.state]
            [booking.data]))

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(o/defstyled time-format :div
  :cursor-pointer
  {:display :inline-block})

(defn flex-datetime [date formatted]
  (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
        on-click {:on-click #(do
                               (tap> ["toggle" @relative-time?])
                               (.stopPropagation %)
                               (toggle-relative-time))}]
    (tap> {:date-is date})
    (when date
      (if @relative-time?
        (if (t/<= (t/date date) (t/date))
          [arco.react/time-since {:times  [(if (t/date-time? date)
                                             (t/date-time date)
                                             (t/at (t/date date) (t/noon)))
                                           (t/now)]
                                  :config booking.data/arco-datetime-config}
           (fn [formatted-t]
             [time-format on-click (formatted :text formatted-t)])]
          [arco.react/time-to {:times  [(if (t/date-time? date)
                                          (t/date-time date)
                                          (t/at (t/date date) (t/noon)))
                                        (t/now)]

                               :config (conj {:stringify? true} booking.data/arco-datetime-config)}
           (fn [ft]
             ;(if (tick.alpha.interval/new-interval (t/date date) (t/date)))
             #_(let [formatted-t (if (and (= "dag" (:interval ft))
                                          (= 1 (:time ft)))
                                   "I morgen"
                                   (apply str (interpose " " [(:in ft) (:time ft) (:interval ft)])))])
             [time-format on-click (formatted :text ft)])])
        [time-format on-click (formatted :date date)]))))

(comment
  (let [date "2022-04-02"]
    (t/hours (t/duration (tick.alpha.interval/new-interval (t/date) (t/date date))))))
(comment
  (arco.core/time-to ["2019-12-28T11:00:20Z" "2019-12-27T11:00:20Z"]
                     {:stringify? false}))

#_(defn flex-datetime' [date formatted]
    (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
          on-click {:on-click #(do
                                 (tap> ["toggle" @relative-time?])
                                 (.stopPropagation %)
                                 (toggle-relative-time))}]
      (when date
        (if @relative-time?
          [sc/link on-click (formatted (ta/date-format-sans-year date))]
          [arco.react/time-to {:times  [(if (t/date-time? date)
                                          (t/date-time date)
                                          (t/at (t/date date) (t/midnight)))
                                        (t/now)]
                               :config booking.data/arco-datetime-config}
           (fn [formatted-t]
             [sc/link on-click (formatted formatted-t)])]))))