(ns schpaa.state
  (:require [re-frame.core :as rf]
            [clojure.set :as set]
            [cljs.reader :refer [read-string]]))

;region

#_(def ls-key
    "zenit-innstillinger")

(goog-define ls-key "schpaa.state")

(defn todos->local-store
      "Puts todos into localStorage"
      [todos]
      (.setItem
        js/localStorage
        ls-key
        (str {:settings (:settings todos)})))

(rf/reg-cofx
  :local-store-todos
  (fn [cofx _]
      (assoc cofx :local-store-todos
             (into (sorted-map)
                   (some->> (.getItem js/localStorage ls-key)
                            (read-string))))))

(def ->local-store
  (rf/after todos->local-store))

(def localstorage-interceptors
  [->local-store])

;endregion

(rf/reg-event-db
  ::state
  [localstorage-interceptors]
  (fn [db [_ tag v]]
      (let [path [:settings :state tag]]
           (if v
             (assoc-in db path v)
             (update-in db path (fnil not false))))))

(rf/reg-sub
  ::state-toggle
  (fn [db [_ tag]]
      (get-in db [:settings :state tag])))

(defn listen
      [tag]
      (rf/subscribe [::state-toggle tag]))

;region help

(rf/reg-event-db
  ::mark-help-as-read
  [localstorage-interceptors]
  (fn [db [_ id]]
      (let [path [:settings :help]]
           (update-in db path (fn [e]
                                  (if (nil? e)
                                    #{id}
                                    (set/union e #{id})))))))

(rf/reg-event-db
  ::mark-help-to-restore
  [localstorage-interceptors]
  (fn [db [_ id]]
      (let [path [:settings :help]]
           (update-in db path (fn [e]
                                  (if (some? e)
                                    (set/difference e #{id})
                                    #{id}))))))


(rf/reg-sub
  ::state-help
  (fn [db [_ id]]
      (some? (some #{id} (get-in db [:settings :help])))))

(defn help:listen
      [id]
      (rf/subscribe [::state-help id]))

(defn help:mark-as-read [id]
      (rf/dispatch [::mark-help-as-read id]))

(defn help:restore-single [id]
      (rf/dispatch [::mark-help-to-restore id]))

(rf/reg-event-db
  ::restore-help
  [localstorage-interceptors]
  (fn [db [_ tag v]]
      (let [path [:settings :help]]
           (assoc-in db path nil))))

(defn help:restore-all []
      (rf/dispatch [::restore-help]))

;endregion

(defn toggle [tag]
      (rf/dispatch [::state tag]))

(rf/reg-event-db
  ::change-state
  [localstorage-interceptors]
  (fn [db [_ tag v]]
      (let [path [:settings :state tag]]
           (assoc-in db path v))))

(defn change [tag v]
      (rf/dispatch [::change-state tag v]))

(defn clear [tag]
      (rf/dispatch [::change-state tag nil]))

