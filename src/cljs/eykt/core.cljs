(ns eykt.core
  (:require [re-frame.core :as rf]
            [kee-frame.core :as k]
            [eykt.spa :as spa]
            [eykt.data :as app-data]))

(defn kee-start []
  (k/start! {:routes         app-data/routes,
             :initial-db     app-data/initial-db,
             :screen         app-data/screen-breakpoints
             :root-component spa/root-component
             :hash-routing?  false,
             :not-found      "/fant-ikke"}))

(defn ^:dev/after-load reload! []
  (js/console.log "Startup")
  (rf/clear-subscription-cache!)
  (kee-start))

(defn init! []
  (reload!))
