(ns booking.database
  (:require [db.core :as db]
            [db.auth]
            [times.api :as ta]
            [tick.core :as t']
            [tick.core :as t]
            [re-frame.core :as rf]
            [schpaa.debug :as l]))

(def path ["booking"])

(defn write [{:keys [uid value] :as m}]
  (let [id (.-key (db/database-push {:path  path
                                     :value (assoc value
                                              :uid uid
                                              :version 2
                                              :timestamp (str (t'/instant)))}))]
    ;intent Perform a backup to the users doc

    (js/alert (l/ppr m))
    (db/firestore-set {:path (conj path uid "trans" id) :value value})
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
       :date           (when date (str (t/date date)))
       :start-time     (when start-time start-time)
       :end-time       (when end-time end-time)

       :start          (if-not start (str (t/at (t/date date) start-time)) start)
       :end            (if-not end (str (t/at (t/date date) end-time)) end)

       :description    description
       :sleepover      sleepover
       :selected       selected
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
      @(db/on-value-reaction {:path ["booking"]})
      (concat #_(take 4 @(db/on-value-reaction {:path ["booking"]}))
        {:intern-a {:date       (str (t/new-date 2022 1 5))
                    :start-time (str (t/new-time 0 0))
                    :end-time   (str (t/new-time 23 0))
                    :end        (str (t/at (t/new-date 2022 1 7) (t/new-time 23 0)))
                    :sleepover  false
                    :selected   [232]
                    :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn       "navns"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-a {:date       (str (t/new-date 2021 1 5))
                    :start-time (str (t/new-time 10 0))
                    :end-time   (str (t/new-time 15 0))
                    :end        (str (t/at (t/new-date 2022 1 10) (t/new-time 20 0)))

                    :sleepover  false
                    :selected   [232]
                    :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn       "navns"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-a {:date       (str (t/new-date 2021 1 15))
                    :start-time (str (t/new-time 10 0))
                    :end-time   (str (t/new-time 20 0))
                    :start      (str (t/at (t/new-date 2022 1 15) (t/new-time 11 0)))
                    :end        (str (t/at (t/new-date 2022 1 15) (t/new-time 22 0)))
                    :sleepover  false
                    :selected   [232]
                    :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn       "navns"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-b {:start      (str (t/at (t/new-date 2022 1 4) (t/new-time 18 0)))
                    :end        (str (t/at (t/new-date 2022 1 10) (t/new-time 2 0)))
                    :sleepover  false
                    :selected   [501]
                    :deleted    false
                    :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}

        {:intern-b {:start      (str (t/at (t/new-date 2022 1 1) (t/new-time 23 0)))
                    :end        (str (t/at (t/new-date 2022 1 3) (t/new-time 12 0)))
                    :sleepover  false
                    :selected   [501]
                    :deleted    false
                    :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-c {
                    :start      (str (t/at (t/new-date 2022 1 10) (t/new-time 11 0)))
                    :end        (str (t/at (t/new-date 2022 1 11) (t/new-time 15 0)))
                    :sleepover  false
                    :selected   [502]
                    :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-c2 {;:date       (str (t/new-date 2021 1 1))
                     ;:start-time (str (t/new-time 10 0))
                     ;:end-time   (str (t/new-time 10 0))
                     :start      (str (t/at (t/new-date 2022 1 6) (t/new-time 11 0)))
                     :end        (str (t/at (t/new-date 2022 1 12) (t/new-time 12 0)))

                     :sleepover  false
                     :selected   [503]
                     :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                     :navn       "navn future"
                     :version    2
                     :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                       (t/new-time 10 0))))}}
        {:intern-d {:start      (str (t/at (t/new-date 2022 1 13) (t/new-time 9 0)))
                    :end        (str (t/at (t/new-date 2022 1 15) (t/new-time 12 0)))

                    :sleepover  false
                    :selected   [504]
                    :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-e {:start      (str (t/at (t/new-date 2022 1 5) (t/new-time 11 0)))
                    :end        (str (t/at (t/new-date 2022 1 7) (t/new-time 12 0)))

                    :sleepover  false
                    :selected   [401]
                    :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                      (t/new-time 10 0))))}}
        {:intern-f {
                    :start      (str (t/at (t/new-date 2022 1 13) (t/new-time 11 0)))
                    :end        (str (t/at (t/new-date 2022 1 23) (t/new-time 12 0)))

                    :sleepover   false
                    :selected    [501]
                    :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn        "navn future"
                    :description "Onsdagspadling, men på en fredag, vel møtt!"
                    :version     2
                    :timestamp   (str (t/instant (t/at (t/new-date 2022 11 1)
                                                       (t/new-time 10 0))))}}
        {:intern-f-before {:start      (str (t/at (t/new-date 2021 12 30) (t/new-time 21 0)))
                           :end        (str (t/at (t/new-date 2022 1 23) (t/new-time 12 0)))
                           :sleepover   false
                           :selected    [508]
                           :uid         "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                           :navn        "navn future"
                           :version     2
                           :description "Første turen i år, hvem har lyst å bli med? Vi skal grille på munkholmen så ta med mat og kull"
                           :timestamp   (str (t/instant (t/at (t/new-date 2022 11 1)
                                                              (t/new-time 10 0))))}}
        {:intern-f-after {
                          :start      (str (t/at (t/new-date 2022 1 1) (t/new-time 11 0)))
                          :end        (str (t/at (t/new-date 2022 1 1) (t/new-time 15 0)))

                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
        {:intern-g {:start      (str (t/at (t/new-date 2022 4 1) (t/new-time 11 0)))
                    :end        (str (t/at (t/new-date 2022 4 30) (t/new-time 12 0)))
                    :description "Bursdagspadlings"
                    :sleepover  false
                    :selected   [999]
                    :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                    :navn       "navn future"
                    :version    2
                    :timestamp  (str (t/instant (t/at (t/new-date 2022 4 20)
                                                      (t/new-time 10 0))))}}))))

(comment
  (read))

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

(defn lookup-username [uid]
  ;(tap> ["U"])
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))
    nil))

(comment
  (do
    (into []
          (comp
            (filter (comp #(pos? (compare % "20210921T200000")) :checkout val))
            (map val))
          @(db/on-value-reaction {:path ["booking"]}))))


(defn delete [ks]
  (doseq [k ks]
    (let [path (conj path (name k))]
      (tap> path)
      (db/database-update {:path  path
                           :value {:deleted true}}))))

(defn boat-db []
  {501 {:id 501
        :brand "Wildermoose"
        :text  "Hunting peck"
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