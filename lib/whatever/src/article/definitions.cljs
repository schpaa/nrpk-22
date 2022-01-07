(ns article.definitions
  (:require [schpaa.debug :as l]
            [schpaa.style :as s]
            [times.api :as t]
            [schpaa.time]
            [re-frame.core :as rf]))

; generic procedures

(def debug?
  ^boolean js/goog.DEBUG)

(defonce view-registry (atom {}))

(def registry view-registry)

(defn register-view! [view-key predicate]
  (swap! view-registry assoc view-key predicate))

(defn map-current-view [data]
  (->> @view-registry
       seq
       (some (fn [[view-key predicate]]
               (when (predicate data)
                 view-key)))))

(register-view! :art/text-only
  (fn [{:strs [kind]}] (= "a" kind)))

#_(register-view!
    :fancy-view
    (fn [{:strs [kind]}] (= "b" kind)))

#_(register-view!
    :art/portrait
    (fn [{:strs [orientation]}] (= "p" orientation)))

#_(register-view!
    :art/embedded
    (fn [{:strs [kind]}] (= "c" kind)))