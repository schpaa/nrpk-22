(ns eykt.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]))

(def routes
  [
   ["/min-side"
    {:name      :r.user
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Min side"}]

   ["/mine-vakter"
    {:name      :r.mine-vakter'
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Min side"}]

   ["/feilsoking"
    {:name      :r.debug
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Min side"}]

   ["/"
    {:name      :r.mine-vakter
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Kalender"}]

   ["/c2"
    {:name      :r.common2
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Kalender"}]

   ["/forsiden"
    {:name      :r.forsiden
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Forsiden"}]])

;region initial-state

(def start-db
  {:startup       true,
   :app/show-help {1 false, 2 false, 5 false, 6 false, 9 true}})

(def localstorage-key "nrpk-22-eykt")

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

(def eykt-firebaseconfig
  ;eykt-22
  {:apiKey            "AIzaSyD2iU_6-1pre0lbYERk8jDdzMgrs-3QVlY",
   :authDomain        "nrpk-vakt.firebaseapp.com",
   :databaseURL       "https://nrpk-vakt.firebaseio.com",
   :projectId         "nrpk-vakt",
   :storageBucket     "nrpk-vakt.appspot.com",
   :messagingSenderId "461903557643",
   :appId             "1:461903557643:web:d55f65e5806d7d106c430e",
   :measurementId     "G-TDX1PWHMCN"})

;endregion