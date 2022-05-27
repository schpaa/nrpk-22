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
            [booking.common-widgets :as widgets]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as button]))

(js/goog.exportSymbol "hljs" hljs)
(js/goog.exportSymbol "DevcardsSyntaxHighlighter" hljs)

(defn ^:export init! []
  (rf/clear-subscription-cache!)
  (dc/start-devcard-ui!)
  (rf/dispatch-sync [::bp/set-breakpoints nrpk.core/screen-breakpoints]))

;;todo enable this!!
(defn reload! [] (init!))

(defcard-rg pill
  [:div
   [button/icon-with-caption
    {:on-click #()
     :class    [:message]}
    ico/closewindow
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [button/icon-with-caption
    {:on-click #()
     :class    [:inverse]}
    ico/closewindow nil]])

(defcard-rg pill
  [:div
   [button/icon-with-caption
    {:on-click #()
     :class    [:inverse]}
    ico/closewindow
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [button/icon-with-caption
    {:on-click #()
     :class    [:large]}
    ico/closewindow
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [hoc.buttons/pill
    {:class    [:message :narrow :uppercase :tracking-widest]
     :on-click #()}
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [hoc.buttons/icon-with-caption
    {:class    [:regular :large]
     :on-click #()}
    ico/stjerne
    nil]])

(defcard-rg pill
  [:div
   [hoc.buttons/pill
    {:class    [ :frame :pad-right :uppercase]
     :on-click #()}
    [sc/icon-small ico/commandPaletteClosed]
    "Bli til Båtlogg nu"]])





