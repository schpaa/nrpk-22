(ns booking.data
  (:require [cljs.reader :refer [read-string]]
            [schpaa.state]))

(goog-define VERSION "")
(goog-define DATE "")

(def TEMPERATURE-VERSION "2")
(def SHOW-BADGE false)

;region initial-state

(def start-db {})

(defn initialize
  [db ls-key]
  (tap> {:initialize-ls (.getItem js/localStorage ls-key)})
  (try
    (conj db
          (into (sorted-map)
                (some->> (.getItem js/localStorage ls-key)
                         (read-string))))
    (catch js/Error e {})))

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