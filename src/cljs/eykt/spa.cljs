(ns eykt.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [re-statecharts.core :as rs]
            ["body-scroll-lock" :as body-scroll-lock]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.modal :as modal]
            [schpaa.components.screen :as components.screen]
            [schpaa.style :as st]
            [booking.spa]
    ;;
            [eykt.content.pages]
    ;[schpaa.components.tab]
    ;[kee-frame.core :as k]
    ;[booking.hoc :as hoc]
            [eykt.calendar.core]
            [schpaa.debug :as l]
            [kee-frame.core :as k]))

(def route-table
  {:r.mine-vakter eykt.content.pages/user
   :r.debug       eykt.content.pages/user
   :r.user        eykt.content.pages/user
   :r.common      eykt.content.pages/common
   :r.common2     eykt.content.pages/common
   :r.common3     eykt.content.pages/common})
