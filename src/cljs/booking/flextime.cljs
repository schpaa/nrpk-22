(ns booking.flextime
  (:require [tick.core :as t]
            [lambdaisland.ornament :as o]
            [tick.alpha.interval]
            [schpaa.state]
            [booking.data]
            [times.api]
            [arco.react]
            [schpaa.style.ornament :as sc]))

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(o/defstyled time-format :span
  :cursor-pointer
  {:-display :inline-block})

(defn flex-datetime [date formatted]
  (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
        on-click {:on-click #(do
                               (tap> ["toggle" @relative-time?])
                               (.stopPropagation %)
                               (toggle-relative-time))}]
    ;(tap> {:date-is date})
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

(defn relative-time
  ([tm]
   (relative-time tm times.api/datetime-format))
  ([tm datetimeformat-fn]
   (when-let [dt (if (t/date-time? tm)
                   (t/date-time tm)
                   (t/at (t/date tm) (t/midnight)))]
     (let [ignore-time? (not (t/date-time? tm))
           relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
           on-time-click {:on-click #(schpaa.state/toggle :app/show-relative-time-toggle)}
           format #(->
                     [sc/hidden-link on-time-click %])]
       (if @relative-time?
         (if (t/<= dt (t/date-time))
           (arco.react/time-since {:times  [dt (t/date-time)]
                                   :config (conj booking.data/arco-datetime-config {:stringify? false})}
                                  (fn [formatted-t]
                                    (format (str (if (= "nå" (:interval formatted-t))
                                                   "nå"
                                                   (str
                                                     (:time formatted-t) " "
                                                     (:interval formatted-t) " "
                                                     (:ago formatted-t)))))))
           (arco.react/time-to {:times  [dt (t/date-time)]
                                :config (conj booking.data/arco-datetime-config {:stringify? true})}
                               (fn [formatted-t]
                                 (format formatted-t))))
         (format (datetimeformat-fn dt)))))))

(comment
  (do [relative-time (t/now) times.api/datetime-format]))