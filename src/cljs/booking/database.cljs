(ns booking.database
  (:require [db.core :as db]
            [tick.core :as t']
            [tick.core :as t]))

(def path ["booking2"])

(defn- str->datetime [dt]
  (let [y (subs dt 0 4)
        m (subs dt 4 6)
        d (subs dt 6 8)
        hh (subs dt 9 11)
        mm (subs dt 11 13)
        ss (subs dt 13 15)]
    (t'/at (t'/new-date y m d) (t'/new-time hh mm ss))))

(defn write [{:keys [uid value]}]
  (let [id (.-key (db/database-push {:path  path
                                     :value (assoc value
                                              :uid uid
                                              :timestamp (str (t'/instant)))}))]
    ;intent Perform a backup to the users doc
    (db/firestore-set {:path (conj path uid "trans" id) :value value})
    id))

(defn v1->v2 [{:keys [version timestamp uid date bid navn checkin checkout] :as m}]
  (let [version (or version 1)]
    ;(tap> m)
    ;{:version version}
    (case version
      2 {:version   2
         :timestamp (str (t/instant timestamp))
         :date      (str (t'/date date))
         :checkout  checkout
         :checkin   checkin
         :sleepover false
         :bid       bid
         :uid       uid
         :navn      navn}
      {:version   1
       :timestamp (str (t/instant (t'/at (t'/date (str->datetime date)) (t'/time (str->datetime date)))))
       :date      (str (t'/date (str->datetime date)))
       :checkout  (str (t'/time (str->datetime checkout)))
       :checkin   (str (t'/time (str->datetime checkin)))
       :sleepover false
       :bid       bid
       :uid       uid
       :navn      navn})))

(defn read []
  (transduce
    (comp
      (map val)
      (map v1->v2))
    conj
    []
    (if false
      @(db/on-value-reaction {:path ["booking"]})
      (concat (take 2 @(db/on-value-reaction {:path ["booking"]}))
              {:intern-a {:version   2
                          :checkout  (str (t'/new-time 10 0))
                          :checkin   (str (t'/new-time 10 0))
                          :sleepover false
                          :bid       "bid"
                          :uid       "uid"
                          :navn      "navn"
                          :date      (str (t'/new-date 2022 11 1))
                          :timestamp (str (t'/instant (t'/at (t'/new-date 2022 11 1)
                                                             (t'/new-time 10 0))))}}
              {:intern-b {:version   2
                          :checkout  (str (t'/new-time 10 0))
                          :checkin   (str (t'/new-time 10 0))
                          :sleepover false
                          :bid       "bid"
                          :uid       "uid"
                          :navn      "navn"
                          :date      (str (t'/new-date 2022 11 1))
                          :timestamp (str (t'/instant (t'/at (t'/new-date 2022 11 1)
                                                             (t'/new-time 10 0))))}}))))
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

(read)