(ns devcards.buttons
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
            [schpaa.style.hoc.buttons :as button]
            [booking.styles :as b]))

(defcard-rg pill
  [:div
   [button/icon-and-caption
    {:class [:message]}
    ico/closewindow
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [b/ro-js
    [button/just-icon {:class [:inverse]} ico/closewindow]
    [button/just-icon {:class [:danger]} ico/closewindow]
    [button/just-icon {:class [:frame]} ico/closewindow]
    [button/just-icon {:class [:selected]} ico/closewindow]
    [button/just-icon {:class [:danger-outline]} ico/closewindow]
    [button/just-icon {:class [:cta-outline]} ico/closewindow]
    [button/just-icon {:class [:cta]} ico/closewindow]
    [button/just-icon {:class [:message]} ico/closewindow]
    [button/just-icon {:class [:message :square-top]} ico/closewindow]
    [button/just-icon {:class [:message :square-bottom]} ico/closewindow]
    [button/just-icon {:class [:square-top :square-bottom]} ico/closewindow]]])

(defcard-rg pill
  [:div
   [b/ro-js
    [button/icon-and-caption
     {:on-click #()
      :class    [:square-top :small :cta-outline]}
     ico/closewindow
     "icon and caption in a frame"]
    [button/icon-and-caption
     {:on-click #()
      :class    [:square-bottom :small :cta]}
     ico/closewindow
     "icon and caption in a frame"]]])

(defcard-rg pill
  [:div
   [b/ro-js
    [button/icon-and-caption
     {:on-click #()
      :class    [:regular :large]}
     ico/closewindow
     "Bli til Båtlogg"]
    [b/text-truncate {:style {:position "relative"
                              :top      "14px"}} "some text between"]
    [button/icon-and-caption
     {:on-click #()
      :class    [:regular :small :self-end]}
     ico/closewindow
     "Bli til Båtlogg"]]])

(defcard-rg pill
  [sc/surface-a
   [b/ro-js {:class [:h-24]}
    [button/just-icon {:class [:regular :round :self-start]} ico/closewindow]
    [button/just-icon {:class [:regular :round :self-baseline]} ico/closewindow]
    [button/just-icon {:class [:large :cta :round :self-baseline]} ico/closewindow]
    [b/text {:style {:position "relative"
                     :top      "-3px"}
             :class [:self-baseline]} "text"]
    [button/just-icon {:class [:regular :round :self-baseline]} ico/closewindow]
    [button/just-icon {:class [:relative :round :self-baseline]} ico/closewindow]
    [b/text {:class [:self-start]} "some"]
    [b/text {:class [:self-end]} "between"]
    [button/just-icon {:class [:regular :round :self-end]} ico/closewindow]]])

(defcard-rg pill-with-just-caption
  [:div
   [sc/surface-a {:class [:h-32]}
    [b/ro-js {:class [:h-full]}
     [button/just-caption
      {:class [:square-top :small :square-bottom]
       :style {:max-width  "10rem"
               :outline    "1px solid red"
               :color      :white
               :background "var(--orange-6)"}}

      "Bli til Båtlogg er en lang knapp, hva er for langt?"]
     [button/icon-and-caption
      {:class [:square-bottom :small :self-baseline]
       :style {:height     "3rem"
               :outline    "1px solid red"
               :max-width  "10rem"
               :color      :white
               :background "var(--blue-6)"}}
      ico/chart
      "Bli til Båtlogg er en lang knapp, hva er for langt?"]
     [button/icon-and-caption
      {:class [:relative :square-top :small :self-baseline]
       :style {:max-width  "7rem"
               :top        "-1rem"
               :color      :white
               :background "var(--orange-6)"}}
      ico/undo
      "Bli til Båtlogg er en lang knapp, hva er for langt?"]]]])

(defcard-rg pill
  [:div
   [sc/row-field
    [button/just-icon
     {:class    [:regular :large]
      :on-click #()}
     ico/stjerne]

    [button/just-caption {:class [:message :frame :narrow]} "test"]

    [button/just-icon {:class [:regular :large]} ico/exclamation]

    [button/just-icon
     {:class    [:regular :large]
      :on-click #()}
     ico/stjerne]]])


(defcard-rg pill
  [:div
   [button/icon-and-caption
    {:class    [:frame :pad-right :uppercase]
     :on-click #()}
    ico/commandPaletteClosed
    "Bli til Båtlogg nu"]])

(defcard-rg pill
  [:div
   [button/icon-and-caption
    {:class    [:cta]
     :on-click #()}
    ico/commandPaletteClosed
    "Bli til Båtlogg nu"]])

(defcard-rg pill
  [:div
   [button/icon-and-caption
    {:class    [:danger-outline]
     :on-click #()}
    ico/commandPaletteClosed
    "Bli til Båtlogg nu"]])

(defn screen [& c]
  [:div {:class [:font-sans :h-64 :w-96  :overflow-hidden :bg-white]} c])

(defcard-rg stability-name-category'
  (fn [_]
    [screen
     [b/ro {:class [:p-4 :w-full :h-12x :bg-alt]}
      
      [:div "a"]

      [widgets/badge {:class [ :small]} 2 "000" "X Y"]

      (widgets/stability-name-category'
        {:k          "123"
         :url?       false
         :reversed?  true
         :hide-flag? true}
        {:stability 2
         :expert    true
         :navn      "navnasdasd asd asd asd asd asd asd asd asd "
         :kind      "grkayak"})

      [button/icon-and-caption
       {:class    [:cta-outline]
        :on-click #()}
       ico/chart
       "utfør"]]])
  {}
  {:frame false
   :padding false
   :heading false})

