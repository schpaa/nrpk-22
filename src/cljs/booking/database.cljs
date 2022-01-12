(ns booking.database
  (:require [db.core :as db]
            [db.auth]
            [times.api :as ta]
            [tick.core :as t']
            [tick.core :as t]
            [re-frame.core :as rf]))

(def path ["booking"])

(defn write [{:keys [uid value]}]
  (let [id (.-key (db/database-push {:path  path
                                     :value (assoc value
                                              :uid uid
                                              :version 2
                                              :timestamp (str (t'/instant)))}))]
    ;intent Perform a backup to the users doc
    (db/firestore-set {:path (conj path uid "trans" id) :value value})
    id))

(defn v1->v2 [{:keys [version timestamp uid date bid navn checkin checkout
                      deleted
                      description
                      overnatting
                      book-for-andre
                      sleepover
                      start-time end-time selected] :as m}]
  (case (or version 1)
    2 {:timestamp  timestamp
       :date       (str (t/date date))
       :start-time start-time
       :description description
       :end-time   end-time
       :sleepover  sleepover
       :selected   selected
       :uid        uid
       :book-for-andre  book-for-andre
       :navn       navn
       :deleted deleted
       :version    version}
    {:timestamp  (str (t/instant (t/at (t/date (ta/str->datetime date)) (t/time (ta/str->datetime date)))))
     :date       (str (t/date (ta/str->datetime date)))
     :start-time (str (t/time (ta/str->datetime checkout)))
     :end-time   (str (t/time (ta/str->datetime checkin)))
     :sleepover  overnatting
     :description description
     :selected   [bid]
     :uid        uid
     :navn       navn
     :book-for-andre  book-for-andre
     :deleted deleted
     :version    version}))

(defn read []
  (transduce
    (comp
      (map (fn [[k v]] (assoc (v1->v2 v) :id k)))
      (remove :deleted))
    conj
    []
    (if false
      @(db/on-value-reaction {:path ["booking"]})
      (concat (take 4 @(db/on-value-reaction {:path ["booking"]}))
              {:intern-a {:date       (str (t/new-date 2022 1 15))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [232]
                          :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                          :navn       "navns"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-b {:date       (str (t/new-date 2022 2 3))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :deleted false
                          :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-c {:date       (str (t/new-date 2021 2 3))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-c2 {:date       (str (t/new-date 2021 1 1))
                           :start-time (str (t/new-time 10 0))
                           :end-time   (str (t/new-time 10 0))
                           :sleepover  false
                           :selected   [501]
                           :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                           :navn       "navn future"
                           :version    2
                           :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                             (t/new-time 10 0))))}}
              {:intern-d {:date       (str (t/new-date 2022 1 3))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-e {:date       (str (t/new-date 2022 1 5))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-f {:date       (str (t/new-date))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :description "Onsdagspadling, men på en fredag, vel møtt!"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}
              {:intern-f-before {:date       (str (t/<< (t/new-date) (t/new-period 1 :days)))
                                 :start-time (str (t/new-time 10 0))
                                 :end-time   (str (t/new-time 10 0))
                                 :sleepover  false
                                 :selected   [501]
                                 :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                                 :navn       "navn future"
                                 :version    2
                                 :description "Første turen i år, hvem har lyst å bli med? Vi skal grille på munkholmen så ta med mat og kull"
                                 :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                                   (t/new-time 10 0))))}}
              {:intern-f-after {:date       (str (t/>> (t/new-date) (t/new-period 1 :days)))
                                :start-time (str (t/new-time 10 0))
                                :end-time   (str (t/new-time 10 0))
                                :sleepover  false
                                :selected   [501]
                                :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                                :navn       "navn future"
                                :version    2
                                :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                                  (t/new-time 10 0))))}}
              {:intern-g {:date       (str (t/new-date 2022 4 29))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [501]
                          :uid        "F4tA4hUnFZd7jcTJVmNE6ZSoQ8t2"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
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
;-MmspM7AnhWGdp8kw8BK
(defn delete [ks]
    (doseq [k ks]
      (let [path (conj path (name k))]
        (tap> path)
        (db/database-update {:path  path
                             :value {:deleted true}}))))