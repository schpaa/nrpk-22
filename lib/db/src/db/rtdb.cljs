(ns db.rtdb
  (:require [reagent.ratom :as ratom]
            [reagent.core :as r]
            [cljs-bean.core :refer [->clj ->js]]
    ;[promesa.core :as p]
            ["firebase/database" :as rtdb :refer [ServerValue increment
                                                  getDatabase ref onValue off set push get]]))

(defn on-value-reaction
  "returns a reagent atom that will always have the latest value at 'path' in the Firebase database"
  [{:keys [path] :as args}]
  (let [ref ^js (ref (getDatabase) (apply str (interpose "/" path)))
        reaction (r/atom nil)
        callback (fn [^js snap] (reset! reaction (some-> snap (.val) ->clj)))]
    (onValue ref callback)
    (ratom/make-reaction
      (fn [] @reaction)
      :on-dispose #(off ref "value" callback))))

(defn ref-get [{:keys [path] :as args}]
  (let [path (apply str (interpose "/" path))
        value (r/atom nil)]
    (-> (rtdb/get (ref (getDatabase) path) (->js value))
        (.then (fn [^js snapshot]
                 (if (.exists snapshot)
                   (let [result (->clj (.val snapshot))]
                     ;(tap> ["GIT IT>" result])
                     (reset! value result))
                   (do
                     ;(tap> ["error" (.val snapshot) path])
                     (reset! value nil))))))
    (ratom/make-reaction
      (fn [] @value)
      :on-dispose #(tap> "disposed a ref-get"))))

;todo error-handling
(defn ref-set [{:keys [path value] :as args}]
  ;(tap> (clj->js value))
  (let [path (apply str (interpose "/" path))]
    (rtdb/set (ref (getDatabase) path) (->js value))))

(defn ref-update [{:keys [path value] :as args}]
  ;(tap> (clj->js value))
  (let [path (apply str (interpose "/" path))]
    (rtdb/update (ref (getDatabase) path) (->js value))))

(defn database-update-increment [{:keys [path field delta] :as args}]
  (ref-update {:path  path
               :value {field (increment delta)}})
  #_(let [path (apply str (interpose "/" path))]
      (rtdb/update (ref (getDatabase) path) (->js {field (increment delta)}))))

(defn ref-push [{:keys [path value] :as args}]
  ;(tap> (clj->js value))
  (let [path (apply str (interpose "/" path))]
    (rtdb/push (ref (getDatabase) path) (->js value))))

(comment
  (do
    (ref-update {:path  ["boat-brand" "-Me9qGEbhySlJwJtsDV0"]
                 :value {:star-count (increment 1)}})))
