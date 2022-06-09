(ns devcards.temperature
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
            [devcards.uuc :as uuc]))

(defcard-rg fuckitisslow
            [:div.h-24.w-24
             {:style {:font-size "130%"}}
             [widgets/temperature {:air 15 :water 1 :wind :high}]])

(defcard-rg fuckitisslow
            [:div.h-12.w-12
             {:style {:font-size "80%"}}
             [widgets/temperature {:air 23 :water 12 :wind :mid}]])

(defcard-rg fuckitisslow
            [:div.h-10.w-10
             {:style {:font-size "60%"}}
             [widgets/temperature {:air 1 :water 51 :wind :low}]])
