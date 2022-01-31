(ns eykt.core
  (:require [re-frame.core :as rf]
            [kee-frame.core :as k]
            [eykt.spa :as spa]
            [re-statecharts.core :as rs]
            [eykt.data :as app-data]
            [schpaa.darkmode]
            [db.core :as db]
            [eykt.fsm-model]
            [nrpk.core]))

(defn kee-start []
  (k/start! {:routes         app-data/routes,
             :initial-db     app-data/initial-db,
             :screen         nrpk.core/screen-breakpoints
             :root-component [spa/app-wrapper]
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
