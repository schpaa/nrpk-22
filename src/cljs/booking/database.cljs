(ns booking.database
  (:require [db.core :as db]
            [db.auth]
            [times.api :as ta]
            [tick.core :as t']
            [tick.core :as t]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [logg.database]))

(def path ["booking2"])

(defn write [{:keys [uid value] :as m}]
  (let [value (assoc value
                :uid uid
                :version 2
                :timestamp (str (t'/instant)))
        id (.-key (db/database-push {:path  path
                                     :value value}))]
    ;intent Perform a backup to the users doc

    ;(js/alert (l/ppr m))
    (db/firestore-set {:path (conj path uid "transactions" id) :value value})
    id))

(defn v1->v2 [{:keys [version timestamp uid date bid navn checkin checkout
                      deleted
                      description
                      overnatting
                      book-for-andre
                      sleepover
                      start
                      end
                      start-time end-time selected] :as m}]
  (case (or version 1)
    2 {:timestamp      timestamp
       ;:date           (when date (str (t/date date)))
       ;:start-time     (when start-time start-time)
       ;:end-time       (when end-time end-time)

       :start          (if-not start (str (t/at (t/date date) start-time)) start)
       :end            (if-not end (str (t/at (t/date date) end-time)) end)

       :description    description
       :sleepover      sleepover
       :selected       (map keyword selected)
       :uid            uid
       :book-for-andre book-for-andre
       :navn           navn
       :deleted        deleted
       :version        version}
    {:timestamp      (str (t/instant (t/at (t/date (ta/str->datetime date)) (t/time (ta/str->datetime date)))))
     :date           (str (t/date (ta/str->datetime date)))
     :start-time     (str (t/time (ta/str->datetime checkout)))
     :end-time       (str (t/time (ta/str->datetime checkin)))
     :start          (str (t/at (t/date date) start-time))
     :end            (str (t/at (t/date date) end-time))

     :sleepover      overnatting
     :description    description
     :selected       [bid]
     :uid            uid
     :navn           navn
     :book-for-andre book-for-andre
     :deleted        deleted
     :version        version}))

(defn upgrade [m]
  (-> m
      (update :start (fn [e]
                       (if (some? e)
                         e
                         (str (t/at (t/date (:date m)) (:start-time m))))))
      (update :end (fn [e]
                     (if (some? e)
                       e
                       (str (t/at (t/date (:date m)) (:end-time m))))))))

(comment
  (do
    (let [m {:intern-a {:date       (str (t/new-date 2022 10 12))
                        :start-time (str (t/new-time 20 0))
                        :end-time   (str (t/new-time 23 0))
                        :end        (t/at (t/new-date 2022 10 16) (t/new-time 10 00))
                        :sleepover  false
                        :selected   [232]
                        :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                        :navn       "navns"
                        :version    2
                        :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                          (t/new-time 10 0))))}}]
      (map upgrade (map (comp first vals) [m m m])))))

(defn read []
  (transduce
    (comp
      (map (fn [[k v]] (assoc (v1->v2 v) :id k)))
      (map upgrade)
      (remove :deleted))
    conj
    []
    (if false
      @(db/on-value-reaction {:path path})
      (concat #_(take 4 @(db/on-value-reaction {:path ["booking"]}))
        {:xx {:start     (str (t/at (t/new-date 2022 1 25) (t/new-time 14 0)))
              :end       (str (t/at (t/new-date 2022 1 27) (t/new-time 19 0)))
              :selected  [:-MeAwP8CBm620L9pzVHY]
              :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
              :navn      "Arne Johannessen"
              :version   2
              :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                               (t/new-time 10 0))))}}
        {:xx2 {:start     (str (t/at (t/new-date 2022 1 23) (t/new-time 10 0)))
               :end       (str (t/at (t/new-date 2022 1 23) (t/new-time 16 0)))
               :selected  [:-MeAwP8CBm620L9pzVHY]
               :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
               :navn      "Jens Brekhus"
               :version   2
               :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                (t/new-time 10 0))))}}
        {:intern-a {
                    :start     (str (t/at (t/new-date 2022 1 24) (t/new-time 0 0)))
                    :end       (str (t/at (t/new-date 2022 1 25) (t/new-time 23 0)))
                    :selected  [:-Mi1VEvPzB7CwUKRFnuM]
                    :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn      "Laura Opdal"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-a {
                    :start     (str (t/at (t/new-date 2022 1 15) (t/new-time 0 0)))
                    :end       (str (t/at (t/new-date 2022 1 25) (t/new-time 23 0)))
                    :selected  [:-Mi1SV8ndQjj8eWDnU4e]
                    :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn      "Peter Pan"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-ab {:date        (str (t/new-date 2021 1 5))
                     :start       (str (t/at (t/new-date 2022 1 22) (t/new-time 8 0)))
                     :end         (str (t/at (t/new-date 2022 1 22) (t/new-time 20 0)))
                     :sleepover   false
                     :selected    [:-Mg5wRGchF2DP-S8n627]
                     :uid         "piH3WsiKhFcq56lh1q37ijiGnqX2"
                     ;:navn      "navns"
                     :description "Lang beskrivelse, hva skal vi i dag og hvor går turen?"
                     :version     2
                     :timestamp   (str (t/instant (t/at (t/new-date 2022 11 1)
                                                        (t/new-time 10 0))))}}
        {:intern-ac {:start     (str (t/at (t/new-date 2022 1 21) (t/new-time 23 0)))
                     :end       (str (t/at (t/new-date 2022 1 22) (t/new-time 22 0)))
                     :sleepover false
                     :selected  [:-Mg5wRGchF2DP-S8n627]
                     :uid       "piH3WsiKhFcq56lh1q37ijiGnqX2"
                     ;:navn      "navns"
                     :version   2
                     :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-b {:start     (str (t/at (t/new-date 2022 1 4) (t/new-time 18 0)))
                    :end       (str (t/at (t/new-date 2022 1 10) (t/new-time 2 0)))
                    :sleepover false
                    :selected  []
                    :deleted   false
                    :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn      "navn future3"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}

        {:intern-b {:start     (str (t/at (t/new-date 2022 1 21) (t/new-time 23 0)))
                    :end       (str (t/at (t/new-date 2022 1 23) (t/new-time 12 0)))
                    :sleepover false
                    :selected  []
                    :deleted   false
                    :uid       "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn      "navn future2"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-c {
                    :start     (str (t/at (t/new-date 2022 1 20) (t/new-time 11 0)))
                    :end       (str (t/at (t/new-date 2022 1 21) (t/new-time 15 0)))
                    :sleepover false
                    :selected  []
                    :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn      "navn future1"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-c2 {;:date       (str (t/new-date 2021 1 1))
                     ;:start-time (str (t/new-time 10 0))
                     ;:end-time   (str (t/new-time 10 0))
                     :start     (str (t/at (t/new-date 2022 1 6) (t/new-time 11 0)))
                     :end       (str (t/at (t/new-date 2022 1 12) (t/new-time 12 0)))

                     :sleepover false
                     :selected  []
                     :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                     :navn      "navn futureq"
                     :version   2
                     :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-d {:start     (str (t/at (t/new-date 2022 1 23) (t/new-time 9 0)))
                    :end       (str (t/at (t/new-date 2022 1 25) (t/new-time 12 0)))

                    :sleepover false
                    :selected  []
                    :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn      "Zlodan Mitch"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-e {:start     (str (t/at (t/new-date 2022 1 25) (t/new-time 11 0)))
                    :end       (str (t/at (t/new-date 2022 1 27) (t/new-time 12 0)))

                    :sleepover false
                    :selected  []
                    :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn      "Nina Nurtures"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                     (t/new-time 10 0))))}}
        {:intern-f {
                    :start       (str (t/at (t/new-date 2022 1 24) (t/new-time 18 0)))
                    :end         (str (t/at (t/new-date 2022 1 24) (t/new-time 21 0)))

                    :sleepover   false
                    :selected    []
                    :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn        "Arne Bo"
                    :description "Onsdagspadling, vel møtt!"
                    :version     2
                    :timestamp   (str (t/instant (t/at (t/new-date 2022 11 1)
                                                       (t/new-time 10 0))))}}
        {:intern-f-before {:start       (str (t/at (t/new-date 2022 1 22) (t/new-time 21 0)))
                           :end         (str (t/at (t/new-date 2022 1 25) (t/new-time 12 0)))
                           :sleepover   false
                           :selected    [:-Mi1VEvPzB7CwUKRFnuM]
                           :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                           :navn        "Herodes Falsk"
                           :version     2
                           :description "Første turen i år, hvem har lyst å bli med? Vi skal grille på munkholmen så ta med mat og kull"
                           :timestamp   (str (t/instant (t/at (t/new-date 2022 11 1)
                                                              (t/new-time 10 0))))}}
        {:intern-f-after {
                          :start     (str (t/at (t/new-date 2022 3 1) (t/new-time 11 0)))
                          :end       (str (t/at (t/new-date 2022 3 2) (t/new-time 15 0)))

                          :sleepover false
                          :selected  []
                          :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn      "Furte Guri"
                          :version   2
                          :timestamp (str (t/instant (t/at (t/new-date 2022 11 1)
                                                           (t/new-time 10 0))))}}
        {:intern-g {:start       (str (t/at (t/new-date 2022 2 1) (t/new-time 11 0)))
                    :end         (str (t/at (t/new-date 2022 2 23) (t/new-time 12 0)))
                    :description "Langtidsleie, prøveprosjekt i samarbeid med Volda Høgskole linje for Naturopplevelser på tom mage."
                    :sleepover   false
                    :selected    []
                    :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn        "Odd Marit"
                    :version     2
                    :timestamp   (str (t/instant (t/at (t/new-date 2022 4 20)
                                                       (t/new-time 10 0))))}}
        {:intern-g {:start     (str (t/at (t/new-date 2022 1 17) (t/new-time 12 0)))
                    :end       (str (t/at (t/new-date 2022 1 25) (t/new-time 17 0)))
                    ;:description "Bursdagspadlings"
                    ;:sleepover  true
                    :selected  [:-Mg5wRGchF2DP-S8n627]
                    :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn      "Peter Sekel"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 4 20)
                                                     (t/new-time 10 0))))}}
        {:intern-g {:start     (str (t/at (t/new-date 2022 1 22) (t/new-time 12 0)))
                    :end       (str (t/at (t/new-date 2022 1 23) (t/new-time 17 0)))

                    :selected  [500 501]
                    :uid       "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn      "Peter Wessel"
                    :version   2
                    :timestamp (str (t/instant (t/at (t/new-date 2022 4 20)
                                                     (t/new-time 10 0))))}}
        {:intern-g {:start       (str (t/at (t/new-date 2022 1 21) (t/new-time 12 0)))
                    :end         (str (t/at (t/new-date 2022 1 21) (t/new-time 17 0)))
                    :selected    [:-Mi1VEvPzB7CwUKRFnuM
                                  :-Mg5wRGchF2DP-S8n627]
                    :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn        "Arne Bo"
                    :description "Bursdagspadlings"
                    :version     2
                    :timestamp   (str (t/instant (t/at (t/new-date 2022 4 20)
                                                       (t/new-time 10 0))))}}))))

(comment
  (do
   (read)))

(comment
  (comment
    (do
      (let [uid "dummy-uid-for-testing"
            id (write {:uid   uid
                       :path  path
                       :value {:uid  uid
                               :open :hearted}})]
        (tap> id))))
  (comment
    (t'/date-time)
    (t'/offset-date-time)
    (str (t'/instant)))
  (comment
    (let [tm "2022-01-10T15:32:05.722Z" #_(str (t'/instant))]
      (prn tm)
      (prn (t'/instant tm))
      (prn (t'/date (t'/instant tm)))
      (prn (t'/zone (t'/instant tm))))))

(defn bookings-for [uid]
  (sort-by :date > (filter (comp #(= % uid) :uid) (read))))

;fixme (?) this actually works with low impact!
;(defonce u (db.auth/user-info))

(defn delete [ks]
  (doseq [k ks]
    (let [path (conj path (name k))]
      (tap> path)
      (db/database-update {:path  path
                           :value {:deleted true}}))))

(defn boat-db []
  {501 {:id       501
        :brand    "Wildermoose"
        :text     "Hunting peck"
        :warning? true
        :location "A3/4"}
   232 {:text "b"}
   504 {:text  "c"
        :brand "Levi's"}
   500 {:text  "500"
        :brand "Levi's"}
   502 {:text  "c"
        :brand "Levi's"}
   603 {:text  "c"
        :brand "Levi's"}
   703 {:text  "c"
        :brand "Levi's"}
   803 {:text  "c"
        :brand "Levi's"}
   903 {:text  "c"
        :brand "Levi's"}
   999 {:text  "c"
        :brand "Levi's"}})

;region shortcuts

(defn bookers-details [uid]
  @(db.core/on-value-reaction {:path ["users" uid]}))

(defn bookers-name [{:keys [uid navn]}]
  (if navn
    navn
    (let [user @(db.core/on-value-reaction {:path ["users" uid]})]
      (if (:alias user)
        (:alias user)
        (if (:navn user)
          (:navn user)
          uid)))))

(defn fetch-id-from-number-' [number]

  (let [data (rf/subscribe [:db/boat-db])
        result (get (into {}
                          (map (fn [[k v]] [(:number v) k])
                               @data))
                    (str number))]
    result))

(defn fetch-id-from-number- [number]
  (let [data (rf/subscribe [:db/boat-db])
        result (get (into {}
                          (map (fn [[k v]] [(:number v) k])
                               @data))
                    (str number))]
    result))

;todo ?
;(logg.database/boat-db)

(def fetch-id-from-number (memoize fetch-id-from-number-))

(defn fetch-boatdata-for- [id]
  (let [data (rf/subscribe [:db/boat-db])
        result (get (into {} @data) id "?")]
    result))

(defn fetch-boatdata-for-' [id data]
  (let [;data (rf/subscribe [:db/boat-db])
        result (get (into {} data) id "?")]
    result))

(def fetch-boatdata-for
  (memoize fetch-boatdata-for-))
