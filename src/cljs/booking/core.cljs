(ns booking.core
  (:require
    [re-frame.core :as rf]
    [kee-frame.core :as k]
    [kee-frame.scroll]
    [db.core :as db]
    [booking.modals.feedback]
    [booking.spa]
    [booking.data :as app-data]
    [booking.fsm-model]
    [schpaa.darkmode]
    [nrpk.core]
    [nrpk.spa]
    [booking.routes]
    [booking.keyboard]
    [booking.fiddle]
    [booking.lab]
    [eykt.calendar.views]))


(defn kee-start []
  (k/start! {:routes         booking.routes/routes
             :initial-db     app-data/initial-db
             :screen         nrpk.core/screen-breakpoints
             :root-component [nrpk.spa/app-wrapper-clean booking.spa/routing-table]
             :not-found      "/not-found"
             :hash-routing?  false}))

(defn kee-start' [] 
  (k/start! {:routes         [["/"
                               {:name   :r.default
                                :header [[:r.default] "Forsiden"]}]]
             :initial-db     app-data/initial-db 
             :screen         nrpk.core/screen-breakpoints
             :root-component [nrpk.spa/app-wrapper-clean {:r.booking booking.fiddle/render}]
             :not-found      "/not-found"
             :hash-routing?  false})) 

(defn ^:dev/after-load reload! []
  (js/console.log "loaded!")
  (rf/clear-subscription-cache!)
  (kee-start) 
  (rf/dispatch [:app/setup-handlers]))

(defn init! []
  (db/init! {:ports  {:emulator-auth 18010
                      :functions     18011
                      :firestore     18012
                      :database      18013
                      :hosting       18014
                      :storage       18015
                      :pubsub        18085}
             :config app-data/booking-firebaseconfig})
  (reload!)
  (booking.keyboard/define-shortcuts))
