(ns booking.access
  (:require [clojure.set :as set]))

(defn can-modify? [route users-access-tokens]
  (let [[route-status-requires route-access-requires] (-> route :data :modify)]
    (some? (seq (set/intersection (second users-access-tokens) route-access-requires)))
    #_(some? (some route-access-requires (second users-access-tokens)
                   #_[l/ppre-x "undecided"]))))