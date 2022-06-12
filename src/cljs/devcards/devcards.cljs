(ns devcards.devcards
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [devcards.core :as dc :include-macros true]
            ["highlight.js" :as hljs]
            [reagent.core]
            [schpaa.modal.readymade :refer []]
            [booking.bookinglist]
            [schpaa.state]
            [booking.views.picker]
            [booking.views]
            [devcards.buttons]
            [devcards.temperature]
            [devcards.experiment]
            [booking.data :as app-data]
            [db.core :as db]))

(js/goog.exportSymbol "hljs" hljs)
(js/goog.exportSymbol "DevcardsSyntaxHighlighter" hljs)

(defn ^:export init! []
  (db/init! {:config app-data/booking-firebaseconfig})
  (dc/start-devcard-ui! {}))

(defn reload! []
  (init!))
