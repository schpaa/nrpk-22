(ns eykt.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [re-statecharts.core :as rs]
            ["body-scroll-lock" :as body-scroll-lock]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.modal :as modal]
            [schpaa.components.screen :as components.screen]
            [booking.spa]
            [eykt.content.pages]
            [eykt.calendar.core]
            [kee-frame.core :as k]))

(def route-table
  {:r.user        eykt.content.pages/user
   :r.mine-vakter eykt.content.pages/user
   :r.debug       eykt.content.pages/user
   :r.forsiden    eykt.content.pages/common
   :r.kalender    eykt.content.pages/common
   :r.annet       eykt.content.pages/common})