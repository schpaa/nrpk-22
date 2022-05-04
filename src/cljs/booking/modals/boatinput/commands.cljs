(ns booking.modals.boatinput.commands
  (:require [clojure.set :as set]))

;these are commands that can be called from 2 separate interfaces

(defn- decrease-children [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn- increase-children [c]
  (swap! c inc))

(defn- decrease-juveniles [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn- increase-juveniles [c]
  (swap! c inc))

(defn- decrease-adults [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn- increase-adults [c]
  (swap! c inc))

(defn- add-command [st]
  (if (<= 3 (count (:item @st)))
    (swap! st (fn [st'] (-> st'
                            ;(assoc :selected (:item %))
                            (update :list (fnil conj #{}) (:item-data st'))
                            ;;clear the item
                            ((fn [e] (if true (dissoc e :item) identity))))))))

(defn- delete-clicked [st]
  (swap! st (fn [st']
              (-> st'
                  (dissoc :item :selected)
                  (update :list set/difference
                          #{(first (filter (fn [m] (= (:number m) (:selected st')))
                                           ;fix !!!!
                                           (:list st')))})))))

(defn reset-command [st]
  (reset! st nil))

(defn backspace-clicked [st]
  (swap! st #(-> %
                 (update :item (fn [s] (subs s 0 (dec (count s)))))
                 (dissoc :selected))))

(defn moon-command [c]
  (swap! c (fnil not false)))

(defn key-command [c]
  (swap! c (fnil not false)))

