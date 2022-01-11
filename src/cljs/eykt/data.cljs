(ns eykt.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]))

(def routes
  [["/" {:name :r.common :header "Forsiden"}]
   ["/batliste" {:name :r.boatlist :header "Båtliste"}]
   ["/init" {:name :r.init :header "Init"}]
   ["/innhold" {:name :r.content :header "Innhold"}]
   ["/min-side" {:name :r.user :header "Min side"}]
   ["/mine-bookinger" {:name :r.my-bookings :header "Mine bookinger"}]
   ["/baksiden" {:name :r.back :header "Baksiden"}]])

(def screen-breakpoints
  {:breakpoints [:mobile 640 :tablet 992 :small-monitor 1200 :large-monitor],
   :debounce-ms 166})

#_(reg-sub :app/user-screenmode
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

;region firebase

(def firebaseconfig
  ;eykt-22
  {:apiKey "AIzaSyD2iU_6-1pre0lbYERk8jDdzMgrs-3QVlY",
   :authDomain "nrpk-vakt.firebaseapp.com",
   :databaseURL "https://nrpk-vakt.firebaseio.com",
   :projectId "nrpk-vakt",
   :storageBucket "nrpk-vakt.appspot.com",
   :messagingSenderId "461903557643",
   :appId "1:461903557643:web:f51109ad017ed61a6c430e",
   :measurementId "G-FYD8HLS5C1"})

;endregion