(ns eykt.core
  (:require [re-frame.core :as rf]
            [kee-frame.core :as k]
            [reagent.dom :as rdom]
            [eykt.spa :as spa]))

(def root-element
  (js/document.getElementById "app"))

(defn start []
  (rdom/render [:div "eykt"] root-element))

(def localstorage-key "eykt-22")

(defn kee-start []
  (k/start! {:routes         spa/app-routes,
             :initial-db     (spa/initialize spa/start-db localstorage-key),
             :root-component [spa/app-wrapper [spa/dispatch-main]],
             :screen         spa/screen-breakpoints,
             :hash-routing?  false,
             :not-found      "/fant-ikke"}))

(defn ^:dev/after-load reload! []
  (js/console.log "Startup")
  (rf/clear-subscription-cache!)
  (kee-start))

(defn init! []
  (reload!))
