(ns booking.modals.boatinput.commands
  (:require [clojure.set :as set]))

;these are commands that can be called from 2 separate interfaces

(defn decrease-children [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn increase-children [c]
  (swap! c inc))

(defn decrease-juveniles [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn increase-juveniles [c]
  (swap! c inc))

(defn decrease-adults [c]
  (swap! c (fn [n] (if (pos? n) (dec n) 1))))

(defn increase-adults [c]
  (swap! c inc))

(defn new-boat [c-boat-list c-selected c-textinput]
  (swap! c-boat-list (fnil conj []) {:new true :number @c-textinput})
  (reset! c-selected @c-textinput)
  (reset! c-textinput nil))

(defn add-boat [st to-add lookup-result]
  (when-let [item lookup-result]
    (reset! to-add nil)
    (swap! st update :list (fnil conj []) item)
    (swap! st assoc :selected (-> item :number str))))

(defn delete-clicked [st c-textinput]
  (let [f (set (first (filter (fn [m] (= (:number m) (:selected st))) (:list st))))]
    (swap! st update :list set/difference f)
    (swap! st assoc :x 1)
    (reset! c-textinput nil)
    (swap! st dissoc :selected)))

(defn reset-command [st]
  (reset! st {:focus :phone}))

(defn backspace-clicked [st c-textinput]
  (swap! c-textinput (fn [s] (subs (str s) 0 (dec (count s)))))
  (swap! st dissoc :selected))

(defn moon-command [c]
  (swap! c (fnil not false)))

(defn litteral-key-command [c]
  (swap! c (fnil not false)))

