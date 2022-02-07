(ns eykt.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]
            [schpaa.state]))

(def routes
  [["/min-side"
    {:name      :r.user
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Baksiden"}]

   ["/mine-vakter"
    {:name      :r.mine-vakter
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Baksiden"}]

   ["/feilsoking"
    {:name      :r.debug
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Baksiden"}]


   ["/"
    {:name      :r.forsiden
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Forsiden"}]

   ["/kalender"
    {:name      :r.kalender
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Forsiden"}]

   ["/annet"
    {:name      :r.annet
     :header    ["Eykt Vaktliste" "Eykt"]
     :subheader "Forsiden"}]])

;region initial-state

(def start-db
  {:version  "3.0.23"
   :app/name "eykt"
   :tab      :cloud})

(defn initialize
  [db ls-key]
  (conj db
        (into (sorted-map)
              (some->> (.getItem js/localStorage ls-key)
                       (read-string)))))

(def initial-db
  (initialize start-db schpaa.state/ls-key))

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