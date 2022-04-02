(ns times.api
  (:require [tick.core :as t :refer []]
            [tick.alpha.interval :as i :refer [extend bounds meets? overlaps?]]
            [tick.locale-en-us]
            [cljs.test :refer [testing deftest is are]]
            [goog.string :as gstring]
            [goog.string.format]

            [clojure.string :as str]
            [schpaa.style.ornament :as sc]))

(comment
  (do
    [(-> (t/time "11:00") (t/on "1918-11-11"))
     (-> (t/date "1911-11-11") (t/at "11:01"))
     (-> (t/time "11:00") (t/on "1918-11-11") (t/offset-by 1))
     (-> (t/now) (t/offset-by 1))
     (-> (t/date-time))
     (t/date (t/instant "1999-12-31T00:00:00Z"))
     (->
       (t/instant "2000-12-13T00:00:00Z")                   ;(t/instant "1999-12-31T00:59:59Z")
       ;(t/in "UTC")
       (t/time))
     (t/now)

     (t/zoned-date-time (t/now))]))

(defn count-generic
  "Gives a map of the countdown with units of time as keys."
  [{:keys [duration past-event? opts]}]
  (let [{:keys [rounding-at progressive-rounding?]} opts
        years (long (t/divide duration (t/new-duration 365 :days)))
        weeks (long (t/divide duration (t/new-duration 7 :days)))
        days (t/days (t/- duration
                          (t/new-duration (* weeks 7) :days)))
        hours (t/hours (t/- duration
                            (t/new-duration (+ days (* weeks 7)) :days)))
        minutes (t/minutes (t/- duration
                                (t/new-duration (+ days (* weeks 7)) :days)
                                (t/new-duration hours :hours)))
        seconds (t/seconds (t/- duration
                                (t/new-duration (+ days (* weeks 7)) :days)
                                (t/new-duration hours :hours)
                                (t/new-duration minutes :minutes)))
        millis (t/millis (t/- duration
                              (t/new-duration (+ days (* weeks 7)) :days)
                              (t/new-duration hours :hours)
                              (t/new-duration minutes :minutes)
                              (t/new-duration seconds :seconds)))]
    (let [rounding-enum {:days 1 :hours 2 :minutes 3 :seconds 4 :milliseconds 5}
          rounding-index (if progressive-rounding?
                           (cond
                             (pos? weeks) 0
                             (pos? days) 1
                             (pos? hours) 2
                             (pos? minutes) 3
                             (pos? seconds) 4
                             :else 6)                       ;todo ?? not 5?
                           (rounding-enum rounding-at 5))]
      (if past-event?
        {:counting     true
         :rounded?     (or (some? rounding-at) progressive-rounding?)
         :years        years
         :weeks        weeks
         :days         (when (<= 1 rounding-index) days)
         :hours        (when (<= 2 rounding-index) hours)
         :minutes      (when (<= 3 rounding-index) minutes)
         :seconds      (when (<= 4 rounding-index) seconds)
         :milliseconds (when (<= 5 rounding-index) millis)}
        {:counting false}))))

(defn format [s & args]
  (apply gstring/format s args))

(defn instant->str [d]
  (try
    (when d
      (if-let [dt (t/date d)]
        (format
          "%s%02d%02dT000000"
          (t/year dt)
          (t/int (t/month dt))
          (t/day-of-month dt))
        nil))
    (catch js/Error _)))

(comment
  (instant->str (t/new-date 2021 3 4)))

(defn duration->text
  ([event]
   (duration->text event nil))
  ([event opts]
   (if (t/= event (t/date-time))
     "nå"
     (let [{:keys [rounded? years weeks days hours minutes seconds]}
           (if (t/< event (t/date-time))
             (count-generic
               {:past-event? (t/< event (t/instant))
                :duration    (t/duration
                               {:tick/beginning event
                                :tick/end       (t/instant)})
                :opts        opts})
             (count-generic
               {:past-event? (t/< (t/instant) event)
                :duration    (t/duration
                               {:tick/beginning (t/instant)
                                :tick/end       event})
                :opts opts}))]

       ;todo At this stage we have lost information since the truncation happens inside countup/countdown-generic
       ;todo extract the truncation-procedure and call it from here
       (let [result (cond-> []
                      (pos? years) (conj (format "%d år" years))
                      (pos? weeks) (conj (format "%d uke%s" weeks (if (= weeks 1) "" "r")))
                      (pos? days) (conj (format "%d dag%s" days (if (= days 1) "" "er")))
                      (pos? hours) (conj (format "%d time%s" hours (if (= hours 1) "" "r")))
                      (pos? minutes) (conj (format "%d minutt%s" minutes (if (= minutes 1) "" "er")))
                      (pos? seconds) (conj (format "%d sekund%s" seconds (if (= seconds 1) "" "er"))))]
         (str (when rounded? "ca ") (str/trim (apply str (interpose " " [(apply str (interpose " " (butlast result)))
                                                                         (when (< 1 (count result)) "og")
                                                                         (last result)])))))))))

(deftest duration-rounding
  (let [start (t/instant (-> (t/date "2000-01-01") t/noon))]
    (t/with-clock start
      (testing "countdown to christmax with rounding"
        (are [x rounding y] (= y (duration->text (t/>> start (t/new-duration x :seconds)) {:rounding-at rounding}))
                            (+ 123456) nil "1 dag 10 timer 17 minutter og 36 sekunder"
                            (+ 123456) :minutes "ca 1 dag 10 timer og 17 minutter"
                            (+ 123456) :hours "ca 1 dag og 10 timer"))
      (testing "countdown to christmax with progressive rounding"
        (are [x y] (= y (duration->text (t/>> start (t/new-duration x :seconds)) {:progressive-rounding? true}))
                   (+ 10000) "ca 2 timer"
                   (+ 1000) "ca 16 minutter"
                   (+ 130) "ca 2 minutter")))))

(deftest duration->text-test
  (let [start (t/instant (-> (t/date "2000-01-01") t/noon))]
    (t/with-clock start
      (testing "countdown to christmax"
        (are [x y] (= y (duration->text (t/>> start (t/new-period x :days))))
                   1 "1 dag"
                   2 "2 dager"
                   13 "1 uke og 6 dager"
                   15 "2 uker og 1 dag"
                   14 "2 uker"
                   16 "2 uker og 2 dager"
                   30 "4 uker og 2 dager")
        (are [x y] (= y (duration->text (t/>> start (t/new-duration x :hours))))
                   10 "10 timer"
                   24 "1 dag"
                   25 "1 dag og 1 time"
                   50 "2 dager og 2 timer")
        (are [x y] (= y (duration->text (t/>> start (t/new-duration x :minutes))))
                   10 "10 minutter"
                   61 "1 time og 1 minutt")
        (are [x y] (= y (duration->text (t/>> start (t/new-duration x :seconds))))
                   1 "1 sekund"
                   10 "10 sekunder"
                   (+ (* 60 60) 1) "1 time og 1 sekund"
                   (+ 123456) "1 dag 10 timer 17 minutter og 36 sekunder")))))

(deftest duration->text-test-backwards
  (let [start (t/instant (-> (t/date "2000-01-01") t/noon))]
    (t/with-clock start
      (testing "countdown to christmax"
        (are [x y] (= y (duration->text (t/<< start (t/new-period x :days))))
                   1 "1 dag"
                   2 "2 dager"
                   13 "1 uke og 6 dager"
                   15 "2 uker og 1 dag"
                   14 "2 uker"
                   16 "2 uker og 2 dager"
                   30 "4 uker og 2 dager")
        (are [x y] (= y (duration->text (t/<< start (t/new-duration x :hours))))
                   10 "10 timer"
                   24 "1 dag"
                   25 "1 dag og 1 time"
                   50 "2 dager og 2 timer")
        (are [x y] (= y (duration->text (t/<< start (t/new-duration x :minutes))))
                   10 "10 minutter"
                   61 "1 time og 1 minutt")
        (are [x y] (= y (duration->text (t/<< start (t/new-duration x :seconds))))
                   1 "1 sekund"
                   10 "10 sekunder"
                   (+ (* 60 60) 1) "1 time og 1 sekund"
                   (+ 123456) "1 dag 10 timer 17 minutter og 36 sekunder")))))

(comment
  (do
    (let [start (t/offset-by (t/instant (-> (t/date "2000-02-01") t/midnight)) 1)]
      (t/with-clock start
        (-> (t/>> start (t/new-duration (rand-int 4) :days))
            (t/truncate :days)
            duration->text)))))

(comment
  (do
    (let [start (t/offset-by (t/instant (-> (t/date "2000-02-01") t/midnight)) 1)]
      (t/with-clock start
        (-> (t/>> start (t/new-duration (rand-int 1000000) :seconds))
            (t/truncate :days)
            duration->text)))))

(comment
  (do
    (t/>> (t/instant (t+ (t/date "2000-01-01") (t/new-duration 10 :hours)))
          (t/new-period 1 :days)))
  (do
    (t/>> (-> (t/date "2000-01-01") (t/at (t/new-time 11 0))) (t/new-duration 10 :hours))))

(comment
  (do
    (let [x 10]
      (duration->text (t/>> (t/instant (-> (t/date "2000-01-01") t/midnight))
                            (t/new-duration x :hours))))))

(comment
  (do
    (let [future1 (t/instant (-> (t/new-date 2021 12 23) t/noon))]
      (t/with-clock (t/instant (-> (t/new-date 2021 12 9) t/noon))
        (duration->text future1)))))

(comment
  (do
    (t/>> (t/date "2000-01-01") (t/new-period 1 :days))))

(comment
  (do
    ;counting down to a future event
    (countdown-generic (t/instant (-> (t/new-date 2021 12 24) (t/at (t/new-time 11 0))))))
  (do
    ;counting up from a past event
    (count-generic (t/instant (-> (t/new-date 2021 12 1) (t/at (t/new-time 11 0)))))))

(def now t/now)

(defn relative-local-time [{:keys [past-prefix past-postfix future-prefix] :as opts
                            :or   {past-prefix   "for"
                                   past-postfix  "siden"
                                   future-prefix "om"}} i]
  (let [past? (t/< i (t/date-time))]
    (apply str (interpose " "
                          [(if past? past-prefix future-prefix)
                           (-> i
                               (t/instant)
                               (duration->text opts))
                           (if past? past-postfix "")]))))

#_(defn local-time [ms]
    (-> ms
        (t/new-duration :millis)
        (t/instant)
        ;(t/date-time)
        (duration->text)))

(defn ms->local-time [ms]
  (-> ms
      (t/new-duration :millis)
      (t/instant)
      (t/date-time)))

(defn ms->local-time-str [ms]
  (->> ms
      ms->local-time
      (t/format "HH:mm")))

(defn ms->local-datetime-str [ms]
  (->> ms
       ms->local-time
       (t/format "d.M.YY HH:mm")))

(defn timestamp->local-datetime-str [timestamp]
  (->> (.toDate timestamp)
       ms->local-time
       (t/format "d.M.YY HH:mm")))

(comment
  (do
    (relative-local-time (t/>> (t/instant) (t/new-duration 10 :minutes))))
  (do
    ;Don’t forget you can create instants from durations - this is often needed when you get Unix times (e.g. JWT OAuth2 tokens)
    ;instants seems to always be in utc
    (-> 1638789549988
        (t/new-duration :millis)
        (t/instant)
        duration->text)))

(defn str->datetime [dt]
  (let [y (subs dt 0 4)
        m (subs dt 4 6)
        d (subs dt 6 8)
        hh (subs dt 9 11)
        mm (subs dt 11 13)
        ss (subs dt 13 15)]
    (t/at (t/new-date y m d) (t/new-time hh mm ss))))

(defn day-number-in-year [dt]
  (try (let [year (t/int (t/year dt))
             month (t/int (t/month dt))]
         (+ (t/day-of-month dt)
            (reduce (fn [a m] (+ a (t/day-of-month (t/last-day-of-month (t/new-date year m 1)))))
                    0
                    (range 1 month))))
       (catch js/Error _ nil)))

(defn week-name [d & {:keys [length]}]
  (let [day-names ["mandag" "tirsdag" "onsdag" "torsdag" "fredag" "lørdag" "søndag" "?"]]
    (if (some? length)
      (subs (nth day-names d) 0 length)
      (nth day-names d))))

(defn day-name [wd & {:keys [length]}]
  (let [day-names ["mandag" "tirsdag" "onsdag" "torsdag" "fredag" "lørdag" "søndag" "?"]
        d (dec (t/int (t/day-of-week wd)))]
    (if (some? length)
      (subs (nth day-names d) 0 length)
      (nth day-names d))))

(defn month-name [wd & {:keys [length]}]
  (let [month-names '[januar februar mars april mai juni juli august september oktober november desember ?]
        d (dec (t/int (t/month wd)))]
    (if (some? length)
      (subs (str (nth month-names d)) 0 length)
      (nth month-names d))))

(defn short-time-format [wt]
  (format ":%d.%02d" (t/minute wt) (t/second wt)))

(defn time-format [wt]
  (format "%d.%02d" (t/hour wt) (t/minute wt)))

(defn tech-date-format [d]
  (format "%04d-%02d-%02d" (t/year) (t/int (t/month d)) (t/day-of-month d)))

(defn date-format [d]
  (format "%s %d. %s %d" (day-name (t/int (t/day-of-week d))) (t/day-of-month d) (month-name d) (t/year)))

(defn date-format-sans-year [d]
  (format "%d. %s" (t/day-of-month d) (month-name d)))

(defn short-date-format [d]
  (format "%02d.%02d" (t/day-of-month d) (t/int (t/month d))))

(defn datetime-format [d]
  (str
    (date-format d)
    " kl "
    (time-format d)))

(defn date-of-first-week
  "return the date of the first week of the year"
  [dt]
  (let [yd (t/new-date (t/year dt) 1 1)
        dow (t/int (t/day-of-week yd))]
    (if (<= dow 4) (t/<< yd (t/new-period (dec dow) :days))
                   (t/>> yd (t/new-period (- 8 dow) :days)))))

(defn yearday-number [dt]
  (js/parseInt (t/format "D" dt)))

(defn week-number [dt]
  (let [doy (yearday-number dt)]
    (quot (+ doy (- 7 (t/int (t/day-of-week dt)))) 7)))

(defn calc-first-day-at-week [week-number]
  (t/>> (date-of-first-week (t/year (t/date)))
        (t/new-period (* (dec week-number) 7) :days)))

(defn relative-time [tm]
  (when-let [tm (some-> tm t/instant)]
    (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
          on-time-click {:on-click #(schpaa.state/toggle :app/show-relative-time-toggle)}
          format #(->                                       ;sc/row-sc-g2
                    ;[sc/text2 "Sist oppdatert:"]
                    [sc/hidden-link on-time-click %])]
      [:<>
       (if @relative-time?
         (format (datetime-format tm))
         (arco.react/time-since {:times  [(str tm)]
                                 :config booking.data/arco-datetime-config}
                                (fn [formatted-t]
                                  (format formatted-t))))])))