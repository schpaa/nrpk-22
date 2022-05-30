(ns booking.access
  (:require [cljs.test :refer [deftest is are]]
            [clojure.set :as set]
            [user.database :refer [lookup-userinfo]]
            [db.core :as db]))

(defn can-modify? [route users-access-tokens]
  (let [[_route-status-requires route-access-requires] (-> route :data :modify)]
    (some? (seq (set/intersection (second users-access-tokens) route-access-requires)))))

(defn build-access-tuple
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

(deftest access
  (is (= 1 1))
  (are [a b] (= a b)
    [:registered #{} "123"]
    (build-access-tuple {:uid "123"})))
