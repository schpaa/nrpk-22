(ns devcards.buttons
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [booking.styles :refer [ro ro-js screen surface text text-truncate
                                    title]]
            [devcards.core :as dc :include-macros true]
            [booking.modals.boatinput]
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
            [schpaa.style.input]
            [reagent.core :as r]
            [booking.temperature]
            [schpaa.style.switch]
            [db.core :as db]
            [booking.data :as app-data]))

(defcard-rg pill
  [:div
   [button/icon-and-caption
    {:class [:message]}
    ico/closewindow
    "Bli til Båtlogg"]])

(defcard-rg pill
  [:div
   [ro-js
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
   [ro-js
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
   [ro-js
    [button/icon-and-caption
     {:on-click #()
      :class    [:regular :large]}
     ico/closewindow
     "Bli til Båtlogg"]
    [text-truncate {:style {:position "relative"
                            :top      "14px"}} "some text between"]
    [button/icon-and-caption
     {:on-click #()
      :class    [:regular :small :self-end]}
     ico/closewindow
     "Bli til Båtlogg"]]])

(defcard-rg pill
  [sc/surface-a
   [ro-js {:class [:h-24]}
    [button/just-icon {:class [:regular :round :self-start]} ico/closewindow]
    [button/just-icon {:class [:regular :round :self-baseline]} ico/closewindow]
    [button/just-icon {:class [:large :cta :round :self-baseline]} ico/closewindow]
    [text {:style {:position "relative"
                   :top      "-3px"}
           :class [:self-baseline]} "text"]
    [button/just-icon {:class [:regular :round :self-baseline]} ico/closewindow]
    [button/just-icon {:class [:relative :round :self-baseline]} ico/closewindow]
    [text {:class [:self-start]} "some"]
    [text {:class [:self-end]} "between"]
    [button/just-icon {:class [:regular :round :self-end]} ico/closewindow]]])

(defcard-rg pill-with-just-caption
  [:div
   [sc/surface-a {:class [:h-32]}
    [ro-js {:class [:h-full]}
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

(def hf-ic {:style {:align-items      :center
                    :display          :flex
                    :background-color "var(--toolbar)"}
            :class [:h-full]})

(defcard-rg stability-name-category'
  (fn [_]
    [screen
     [ro {:class [:pl-4 :w-full :h-16 :bg-alt]}

      [:div "a"]

      [widgets/badge {:class [:small]} 2 "000" "X Y"]

      (widgets/stability-name-category'
        {:k          "123"
         :url?       false
         :reversed?  true
         :hide-flag? true}
        {:stability 2
         :expert    true
         :navn      "navnasdasd asd asd asd asd asd asd asd asd "
         :kind      "grkayak"})
      [surface hf-ic
       [ro-js
        [text "hello1"]
        [button/just-icon {} ico/chart]]]
      [surface hf-ic
       [ro
        [title "hello2"]
        [button/just-icon {:class [:large]} ico/chart]]]
      [surface (merge hf-ic {:style {:background "red" :color "white"}})
       [button/icon-and-caption
        {:class [:large]}
        ico/experimental
        [title "sublime :message"]]]
      [surface hf-ic
       [ro
        [button/icon-and-caption
         {:class    [:center :shadow-sm :bg-alt]
          :on-click #()}
         ico/chart
         "A"]
        [button/icon-and-caption
         {:class    [:xlight :center :text-alt :shadow-sm :dark :bg-alt]
          :on-click #()}
         ico/chart
         "A"]
        [button/icon-and-caption
         {:class    [:light :center :text-alt :shadow-sm :bg-alt]
          :on-click #()}
         ico/chart
         "A"]
        [button/icon-and-caption
         {:class    [:center :shadow-sm :bg-alt :text-white]
          :on-click #()}
         ico/chart
         "A"]]]]])
  {}
  {:frame   true
   :padding false
   :heading true})

(defcard-rg input
  [:div
   [button/textinput {:touched #(-> :as)
                      :cursor  (r/atom "123")
                      :values  {:b nil}} :a :b]])

(defonce wdata (r/atom {:vind 2
                        :luft 2}))

(defn dialog [c]
  [:div
   {:style {:width                      "512px"
            :max-width                  "min(calc(100vw - 2rem),512px)"
            :border-bottom-right-radius "var(--radius-2)"
            :border-bottom-left-radius  "var(--radius-2)"
            :box-shadow                 (apply str (interpose ","
                                                              [;"0 0 0px calc(var(--size-1) * 1) var(--toolbar-)"
                                                               "0 0 0px calc(var(--size-1) * 2) var(--toolbar)"
                                                               "var(--shadow-4)"]))}
    :class [:overflow-y-auto]}
   c])

(defcard-rg tempform
  (fn [a _]
    [:div
     [screen {:class [:p-4]}
      [dialog
       [booking.temperature/temperature-form-content
        {:write-fn #(let []
                      (js/alert %))}]]]])

  wdata
  {:inspect-data true})

(defcard-rg tempform
  (fn [a _]
    [:div
     [screen {:class [:p-4]}
      [dialog 
       [booking.temperature/temperature-form-content
        {:uid "uid"
         :write-fn #(let []
                      (js/alert %))}]]]])

  wdata
  {:inspect-data true})



(defcard-rg input-field 
  (fn [a _]
    [:div
     [screen {:class [:p-4]}
      [booking.modals.boatinput/boat-input
       (r/atom {:focus :boats
                :textfield {:boats "12"}})
       (r/atom :boats)
       (r/atom "")]]])

  wdata
  {:inspect-data true})