(ns eykt.data
  (:require [re-frame.core :refer [reg-sub]]
            [cljs.reader :refer [read-string]]))

(def routes
  [["/" {:name      :r.common
         :header    ["Long header form" "Short form"]
         :subheader "Subheader"}]])

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