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
    (when date
      (if @relative-time?
        (if (t/<= (t/date date) (t/date))
          [arco.react/time-since {:times  [(if (t/date-time? date)
                                             (t/date-time date)
                                             (t/at (t/date date) (t/midnight)))
                                           (if (t/date-time? date)
                                             (t/now)
                                             (t/at (t/date) (t/midnight)))]
                                  :config (conj {:stringify? true} booking.data/arco-datetime-config)}
           (fn [formatted-t]
             [sc/datetimelink on-click (formatted :text formatted-t)])]
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
             [sc/datetimelink on-click (formatted :text ft)])])
        [sc/datetimelink on-click (formatted :date date)]))))

(comment
  (let [date "2022-04-02"]
    (t/hours (t/duration (tick.alpha.interval/new-interval (t/date) (t/date date))))))
(comment
  (arco.core/time-to ["2019-12-28T11:00:20Z" "2019-12-27T11:00:20Z"]
                     {:stringify? false}))



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
           format #(vector sc/datetimelink-sans-size (conj on-time-click {:class [:regular]}) %)]
       (if @relative-time?
         (if (t/<= dt (t/date-time))
           (arco.react/time-since {:times  [dt (t/date-time)]
                                   :config (conj booking.data/arco-datetime-config {:stringify? false})}
                                  (fn [formatted-t]
                                    (format (str (if (= "n??" (:interval formatted-t))
                                                   "n??"
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