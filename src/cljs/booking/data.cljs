(ns booking.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]))

(def routes
  [["/" {:name :r.common :header "NRPK Booking" :subheader "Booking"}]
   ["/batlist" {:name :r.boatlist :header "Oversikt over båter" :subheader "Booking"}]
   ["/init" {:name :r.init :header "Init" :subheader "Booking"}]
   ["/innhold" {:name :r.content :header "Innhold" :subheader "Booking"}]
   ["/ny" {:name :r.new-booking :header "Ny booking" :subheader "Booking"}]
   ["/siste" {:name :r.last-booking :header "Siste booking" :subheader "Booking"}]
   ["/baksiden" {:name :r.back :header "Baksiden" :subheader "Booking"}]
   ["/om-meg" {:name :r.user :header "Om meg" :subheader "Booking"}]
   ["/logg" {:name :r.logg :header "Min logg" :subheader "Booking"}]
   ["/debug" {:name :r.debug :header "Feilsøking" :subheader "Booking"}]
   ["/debug2" {:name :r.debug2 :header "Feilsøking2" :subheader "Booking"}]
   ["/blog" {:name :r.blog :header "Blogg" :subheader "Booking"}]])

;region initial-state

(def start-db
  {:startup       true,
   :app/show-help {1 false, 2 false, 5 false, 6 false, 9 true}})

(def localstorage-key "nrpk-22-booking")

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