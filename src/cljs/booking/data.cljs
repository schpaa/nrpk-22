(ns booking.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]
            [schpaa.state]))

(goog-define VERSION "?")

;region initial-state

(def start-db
  {:version  "3.0.24"
   :app/name "booking"
   :tab      :cloud
   #_#_:lab/toggle-chrome true})

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

(def booking-firebaseconfig
  ;eykt-22
  {:apiKey            "AIzaSyD2iU_6-1pre0lbYERk8jDdzMgrs-3QVlY",
   :authDomain        "nrpk-vakt.firebaseapp.com",
   :databaseURL       "https://nrpk-vakt.firebaseio.com",
   :projectId         "nrpk-vakt",
   :storageBucket     "nrpk-vakt.appspot.com",
   :messagingSenderId "461903557643",
   :appId             "1:461903557643:web:f51109ad017ed61a6c430e",
   :measurementId     "G-FYD8HLS5C1"})

;endregion

(def arco-datetime-config
  {:refresh-rate 1000
   :vocabulary   {:in     "om"
                  :ago    "siden"
                  :now    "nå"
                  :second ["sekund" "sekunder"]
                  :minute ["minutt" "minutter"]
                  :hour   ["time" "timer"]
                  :day    ["dag" "dager"]
                  :week   ["uke" "uker"]
                  :month  ["måned" "måneder"]
                  :year   ["år" "år"]}})