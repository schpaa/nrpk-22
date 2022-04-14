(ns booking.access
  (:require [clojure.set :as set]
            [user.database :refer [lookup-userinfo]]
            [db.core :as db]))

(defn can-modify? [route users-access-tokens]
  (let [[route-status-requires route-access-requires] (-> route :data :modify)]
    (some? (seq (set/intersection (second users-access-tokens) route-access-requires)))
    #_(some? (some route-access-requires (second users-access-tokens)
                   #_[l/ppre-x "undecided"]))))

(defn compute-access-tokens'
  "looks up :uid and returns a tuple of [status access uid]"
  [{:keys [uid] :as user}]
  (if uid
    (let [{:keys [waitinglist admin godkjent medlem booking-godkjent]} user]
      [(cond
         waitinglist :waitinglist
         (or admin godkjent medlem booking-godkjent) :member
         :else :registered)
       (into #{} (remove nil?
                         [(when (:admin user) :admin)
                          (when (:booking-godkjent user) :booking)
                          (when (:nøkkelvakt user) :nøkkelvakt)]))
       uid])
    [:none nil nil]))

