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
    nil))

(deftest access
  (let [uid "123"
        build-access-tuple #(build-access-tuple (into {:uid uid} %&))]
    (are [a b] (= a b)
               nil (booking.access/build-access-tuple {})
               [:member #{:booking} uid] (build-access-tuple {:booking-godkjent true})
               ;; with a valid uid:
               [:registered #{} uid] (build-access-tuple {})
               [:waitinglist #{} uid] (build-access-tuple {:waitinglist true})
               [:member #{:admin} uid] (build-access-tuple {:admin true})
               [:registered #{:nøkkelvakt} uid] (build-access-tuple {:nøkkelvakt true})
               [:registered #{} uid] (build-access-tuple {:nøkkelvakt false})
               [:registered #{:nøkkelvakt} uid] (build-access-tuple {:nøkkelvakt true}))))

