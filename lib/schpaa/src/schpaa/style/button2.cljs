(ns schpaa.style.button2
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

(o/defstyled hover-shadow :div
  [:&:enabled:hover {:box-shadow "var(--shadow-1)"}])

;region colors

(o/defstyled color-regular :div
  :outline-none
  [:&
   {:background-color "var(--button-bright)"
    :color            "var(--buttoncopy)"}
   [:&:enabled:hover hover-shadow {:background-color "var(--button)"}]
   [:&:enabled:active {:background-color "var(--button-bright)"}]
   [:&:disabled {:opacity 0.5}]])

(o/defstyled color-cta :div
  [:&
   {:background-color "var(--brand1-bright)"
    :color            "var(--brand1copy)"}
   [:&:enabled:hover hover-shadow {:background-color "var(--brand1)"}]
   [:&:enabled:active {:background-color "var(--brand1-bright)"}]
   [:&:disable {:opacity 0.5}]])

(o/defstyled color-danger :div
  [:&
   {:background-color "var(--danger-bright)"
    :color            "var(--dangercopy)"}
   [:&:hover hover-shadow {:background-color "var(--danger)"}]
   [:&:active {:background-color "var(--danger-bright)"}]])

;endregion

;region size

(o/defstyled shape-regular :div
  :outline-none
  {:border-radius  "var(--radius-1)"
   :outline-offset 0
   :white-space    :nowrap
   :min-width      "7rem"
   :font-size      "var(--font-size-1)"
   :padding-block  "var(--size-3)"
   :padding-inline "var(--size-4)"})

;endregion

;--------------------------------------

(o/defstyled normal-regular :button
  [:& color-regular shape-regular])

(o/defstyled cta-regular :button
  [:& color-cta shape-regular])

(o/defstyled danger-regular :button
  [:& color-danger shape-regular])

(o/defstyled round :div
  [:&
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    :color         "var(--text3)"}]
  [:&:active {:color      "var(--buttoncopy)"
              :background "var(--button)"}])