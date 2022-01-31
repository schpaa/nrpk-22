(ns eykt.core
  (:require [re-frame.core :as rf]
            [kee-frame.core :as k]
            [re-statecharts.core :as rs]
            [eykt.spa]
            [eykt.data :as app-data]
            [eykt.fsm-model]
            [schpaa.darkmode]
            [db.core :as db]
            [nrpk.core]
            [nrpk.spa]))

(defn kee-start []
  (k/start! {:routes         app-data/routes,
             :initial-db     app-data/initial-db,
             :screen         nrpk.core/screen-breakpoints
             :root-component [nrpk.spa/app-wrapper eykt.spa/route-table]
             :not-found      "/fant-ikke",
             :hash-routing?  false}))

(defn ^:dev/after-load reload! []
  (js/console.log "loaded!")
  (rf/clear-subscription-cache!)
  (kee-start)
  (rf/dispatch [:app/setup-handlers]))

(defn init! []
  (db/init! {:config app-data/eykt-firebaseconfig})
  (reload!)
  (rf/dispatch [::rs/transition :main :e.restart]))
