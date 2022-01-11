(ns booking.database
  (:require [db.core :as db]
            [db.auth]
            [times.api :as ta]
            [tick.core :as t']
            [tick.core :as t]
            [re-frame.core :as rf]))

(def path ["booking2"])

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
                      description
                      overnatting
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
       :navn       navn
       :version    version}
    {:timestamp  (str (t/instant (t/at (t/date (ta/str->datetime date)) (t/time (ta/str->datetime date)))))
     :date       (str (t/date (ta/str->datetime date)))
     :start-time (str (t/time (ta/str->datetime checkout)))
     :end-time   (str (t/time (ta/str->datetime checkin)))
     :sleepover  overnatting
     :selected   [bid]
     :uid        uid
     :navn       navn
     :version    version}))

(defn read []
  (transduce
    (comp
      (map val)
      (map v1->v2))
    conj
    []
    (if true
      @(db/on-value-reaction {:path ["booking2"]})
      (concat (take 2 @(db/on-value-reaction {:path ["booking"]}))
              {:intern-a {:date       (str (t/new-date 2022 1 15))
                          :start-time (str (t/new-time 10 0))
                          :end-time   (str (t/new-time 10 0))
                          :sleepover  false
                          :selected   [2]
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
                          :uid        "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"
                          :navn       "navn future"
                          :version    2
                          :timestamp  (str (t/instant (t/at (t/new-date 2022 11 1)
                                                            (t/new-time 10 0))))}}))))

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