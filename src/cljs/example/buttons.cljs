(ns example.buttons
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [devcards.core :as dc :include-macros true]
            [reagent.core]
            ["highlight.js" :as hljs]
            [breaking-point.core :as bp]
            [nrpk.core]
            [nrpk.spa]
            [booking.lab]
            [schpaa.modal.readymade :refer []]
            [booking.bookinglist]
            [schpaa.state]
            [booking.views.picker]
            [booking.views]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [schpaa.style.ornament :as sc]
            [re-frame.core :as rf]
            [booking.common-widgets :as widgets]))

(js/goog.exportSymbol "hljs" hljs)
(js/goog.exportSymbol "DevcardsSyntaxHighlighter" hljs)

(defn ^:export init! []
  (dc/start-devcard-ui!)
  (rf/dispatch-sync [::bp/set-breakpoints nrpk.core/screen-breakpoints]))

;;todo enable this!!
(defn reload! [] (init!))

(defcard-rg danger-pill
  [:div
   [hoc.buttons/reg-pill
    {:class    [:inverse :pad-right :narrow]
     :on-click #()}
    "Bli til Båtlogg"]])

(defcard-rg round-pill
  [:div
   [hoc.buttons/round-pill
    {:class    [:message :narrow]
     :on-click #()}
    "Bli til Båtlogg"]])

(defcard-rg round-pill2
  [:div
   [hoc.buttons/regoutline-pill-icon
    {:class    [:narrow]
     :on-click #()}
    [sc/icon ico/menu]
    "Bli til Båtlogg nu"]])





