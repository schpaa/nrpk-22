(ns logg.database
  (:require [db.core :as db]
            [booking.database]))

(comment
  (do
    (defn read []
      (transduce
        (comp
          (map (fn [[k {:keys [checkout checkin
                               overnatting
                               båtnr barn voksne nøkkel] :as v}]]
                 (assoc {}
                   :dato (str (subs checkout 4 6) "/" (subs checkout 6 8))
                   :start (if checkout (subs checkout 9 13) "?")
                   :slutt (if checkin (subs checkin 9 13)
                                      "?")
                   #_#_:varighet (/ (-
                                      (try (js/parseInt (subs checkin 9 16)) (catch js/Error _ 0))
                                      (try (js/parseInt (subs checkout 9 16)) (catch js/Error _ 0)))
                                    1)
                   :overnatting (or overnatting false)
                   :båtnr båtnr
                   :nøkkel (or nøkkel false)
                   :voksne (if (seq voksne)
                             (js/parseInt voksne)
                             0)
                   :barn (if (seq barn)
                           (js/parseInt barn)
                           0)))))
        conj
        []
        @(db/on-value-reaction {:path ["activity"]})))
    #_(map (juxt :start :slutt) (take 200 (read)))

    (println (apply str (interpose "," '[dato start slutt varighet overnatting båtnr nøkkel voksne barn])))

    (doseq [{:keys [dato start slutt varighet overnatting båtnr nøkkel voksne barn]} (read)]
      (println (apply str (interpose "," [dato start slutt varighet (if overnatting "Y" "N") båtnr (if nøkkel "Y" "N") voksne barn]))))))

(comment
  (do
    (defn seq-output [{:keys [båtnr dato start slutt]}]
      (println (apply str (interpose "," [båtnr dato start slutt]))))

    (println (apply str (interpose "," '[båtnr dato start slutt])))

    (->> (remove #(vector? (:båtnr %))
                 (map (fn [{:keys [date selected start-time end-time sleepover] :as v}]
                        (assoc {}
                          :båtnr (first selected)
                          :dato (str (subs date 5 7) (subs date 8 10))
                          :start (str (subs start-time 0 2) (subs start-time 3 5))
                          :slutt (str (subs end-time 0 2) (subs end-time 3 5)))) (booking.database/read)))
         (map seq-output))))

(comment
  (do
    (let [type-db @(db/on-value-reaction {:path ["boat-brand"]})]
      (transduce
        (comp
          (map val)
          (filter (comp #(= % "1") :location))
          (map (fn [{:keys [boat-type] :as e}]
                 (conj e (get type-db (keyword boat-type))))))
        conj
        []
        @(db/on-value-reaction {:path ["boad-item"]})))))

(comment
  (do
    (defn boat-db []
      (let [type-db @(db/on-value-reaction {:path ["boat-brand"]})]
        (transduce
          (comp
            (filter (comp #(= % "1") :location val))
            (map (fn [[id {:keys [boat-type] :as v}]]
                   [id (conj v (get type-db (keyword boat-type)))])))
          conj
          {}
          @(db/on-value-reaction {:path ["boad-item"]}))))
    (boat-db)))

(defn boat-db []
  (let [type-db @(db/on-value-reaction {:path ["boat-brand"]})]
    (into {}
      (comp
        (filter (comp #(= % "1") :location val))
        (map (fn [[id {:keys [boat-type] :as v}]]
               (tap> "WTF?")
               [id (conj v (get type-db (keyword boat-type)))])))
      @(db/on-value-reaction {:path ["boad-item"]}))))