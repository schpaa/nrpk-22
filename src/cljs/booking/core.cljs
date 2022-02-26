(ns booking.core
  (:require
    [re-frame.core :as rf]
    [kee-frame.core :as k]
    ;; passively registers the fsm
    [re-statecharts.core :as rs]
    [booking.spa :as spa]
    [booking.data :as app-data]
    [booking.fsm-model]
    [schpaa.darkmode]
    [db.core :as db]
    [nrpk.core]
    [nrpk.spa]))

(def routes
  [["/" {:name :r.forsiden :header "NRPK Booking" :subheader "Forsiden"}]
   ["/blog" {:name :r.booking-blog :header "Oversikt over båter" :subheader "Forsiden"}]
   ["/ny" {:name :r.new-booking :header "Ny booking" :subheader "Forsiden"}]
   ["/debug" {:name :r.debug :header "Feilsøking" :subheader "Baksiden"}]
   ["/turlogg" {:name :r.logg :header "Min logg" :subheader "Baksiden"}]
   ["/om-meg" {:name :r.user :header "Om meg" :subheader "Baksiden"}]
   ["/velkommen" {:name :r.welcome :header "Om meg" :subheader "Baksiden"}]
   ["/not-found" {:name :r.page-not-found :header ":r.page-not-found" :subheader ":r.page-not-found"}]])

(defn kee-start []
  (k/start! {:routes         routes,
             :initial-db     app-data/initial-db,
             :screen         nrpk.core/screen-breakpoints
             :root-component [nrpk.spa/app-wrapper-sidebar spa/routing-table]
             :not-found      "/not-found",
             :hash-routing?  false}))

(defn ^:dev/after-load reload! []
  (js/console.log "loaded!")
  (rf/clear-subscription-cache!)
  (kee-start)
  (rf/dispatch [:app/setup-handlers]))

(defn init! []
  (db/init! {:config app-data/booking-firebaseconfig})
  (reload!)
  (rf/dispatch [::rs/transition :main :e.restart]))
