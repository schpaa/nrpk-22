(ns db.fsdb
  (:require [re-frame.core :as rf]
            ["firebase/firestore" :refer [getFirestore collection query where getDocs onSnapshot doc setDoc Timestamp addDoc
                                          orderBy limit
                                          serverTimestamp]]
            ;[schpaa.lib.debug :as l]
            ;[app.system.database.state :refer [app]]
            [reagent.ratom :as ratom]
            [cljs-time.core :as t]
            [reagent.core :as r]
            [cljs-bean.core :refer [->clj ->js]]
            #_[app.system.database.common :refer [database-ref
                                                  collection-ref
                                                  firestore-ref ->path
                                                  success-failure-dispatch]]))

(defn on-snapshot-doc-reaction
  "returns a reagent atom that will always have the latest value at 'path' in the Firebase database"
  [{:keys [path]}]
  ;{:pre [(vector? path)]}
  (let [path' (cond
                (string? path) path
                (vector? path) (apply str (interpose "/" path)))
        ref ^js (doc (getFirestore) path')
        reaction (r/atom nil)
        unsubscribe (onSnapshot ref (fn [d] (reset! reaction (js->clj (.data d)))))]
    (ratom/make-reaction
      (fn [] @reaction)
      :on-dispose #(unsubscribe))))

(defn on-snapshot-docs-reaction
  "returns a reagent atom that will always have the latest snapshot at 'collection-path' in the Firebase firestore"
  [{:keys [path]}]
  (let [path (apply str (interpose "/" path))
        ref (collection (getFirestore) path)
        q (query ref (orderBy "timestamp" "desc") #_(limit 2))
        reaction (r/atom nil)
        unsubscribe (onSnapshot q
                                (fn [querySnapshot]
                                  (let [vs (map (fn [e] {;todo Add more fields in the future
                                                         :id   (.-id e)
                                                         :data (js->clj (.data e))})
                                                (.-docs querySnapshot))]
                                    (reset! reaction vs))))]

    (ratom/make-reaction
      (fn [] @reaction)
      :on-dispose #(unsubscribe))))

(defn firestore-set
  "store a document"
  [{:keys [path value options] :or {options {:timestamp true
                                             :merge true}}}]
  (let [ref (apply doc (getFirestore) path)
        value (if (:timestamp options)
                (assoc value :timestamp (serverTimestamp) #_(.fromDate Timestamp (t/now)))
                value)]
    (setDoc ref (clj->js value) (clj->js options))))

(defn firestore-add
  "store a document with a new id"
  [{:keys [path value options] :or {options {:timestamp true
                                             :merge true}}}]
  (let [ref (doc (apply collection (getFirestore) path))
        value (if (:timestamp options)
                (assoc value :timestamp (serverTimestamp) #_(.fromDate Timestamp (t/now)))
                value)]
    (setDoc ref (clj->js value) (clj->js options))))
