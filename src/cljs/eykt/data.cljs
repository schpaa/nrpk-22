(ns eykt.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]))

(def routes
  [["/" {:name :r.common :header "Forsiden"}]
   ["/init" {:name :r.init :header "Init"}]
   ["/baksiden" {:name :r.back :header "Baksiden"}]])

(def screen-breakpoints
  {:breakpoints [:mobile 640 :tablet 992 :small-monitor 1200 :large-monitor],
   :debounce-ms 166})

(reg-sub ::user-screenmode
         (fn [db]
           (get-in db [:settings :pref-screenmode] :auto)))

;region initial-state

(def start-db
  {:startup       true,
   :app/show-help {1 false, 2 false, 5 false, 6 false, 9 true}})

(def localstorage-key "eykt-22")

(defn initialize
  [db ls-key]
  (conj db
        (into (sorted-map)
              (some->> (.getItem js/localStorage ls-key)
                       (read-string)))))

(def initial-db
  (initialize start-db localstorage-key))

;endregion