(ns booking.access
  (:require [clojure.set :as set]
            [user.database :refer [lookup-userinfo]]))

(defn can-modify? [route users-access-tokens]
  (let [[route-status-requires route-access-requires] (-> route :data :modify)]
    (some? (seq (set/intersection (second users-access-tokens) route-access-requires)))
    #_(some? (some route-access-requires (second users-access-tokens)
                   #_[l/ppre-x "undecided"]))))

(defn compute-access-tokens
  "looks up :uid and returns a tuple of [status access] (for now, I won't include user-auth as the third item)"
  [{:keys [uid]}]
  (let [user (lookup-userinfo uid)]
    [(cond
       (nil? uid) :none
       (:waitinglist user) :waitinglist
       (or (:godkjent user)
           (:medlem user)
           (:booking-godkjent user)) :member
       :else :registered)
     (into #{} (remove nil?
                       [(when (:admin user) :admin)
                        (when (:booking-godkjent user) :booking)
                        (when (:nøkkelvakt user) :nøkkelvakt)]))]))

