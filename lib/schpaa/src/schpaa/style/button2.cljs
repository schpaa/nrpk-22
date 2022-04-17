(ns schpaa.style.button2
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

(o/defstyled focus-button :div
  :focus:outline-none
  :duration-50
  [#{:&:enabled:focus-visible :&:enabled:active:hover}
   {:box-shadow "0 0 0 2px var(--content), 0 0 0 6px var(--brand2-weak)"}])

(o/defstyled hover-shadow :div
  [:&:enabled:hover {:box-shadow "var(--shadow-1)"}])

;region colors

(o/defstyled color-regular :div
  [:&
   {:background-color "var(--button)"
    :color            "var(--buttoncopy)"}
   [:&:enabled:active {:background-color "var(--button-bright)"}]
   [:&:disabled {:opacity 0.5}]])

;todo
(o/defstyled color-cta :div
  [:&
   {:background-color "var(--brand1)"
    :font-weight      "var(--font-weight-7)"
    :color            "var(--brand1-copy)"}
   [:&:disabled {:opacity 0.3
                 :filter  "saturate(0%)"}]
   [:&:enabled:active {:background-color "var(--brand1-bright)"}]])

(o/defstyled color-cta-outline :div
  [:&
   {:border "2px solid var(--brand1)"
    :color  "var(--text0)"}
   [:&:hover
    {:background-color "var(--brand1)"
     :color            "var(--brand1-copy)"}]
   [:&:disabled {:opacity 0.3
                 :filter  "saturate(0%)"}]
   [:&:enabled:active {:background-color "var(--brand1-bright)"}]])

(o/defstyled color-danger :div
  [:&
   {:background-color "var(--danger-bright)"
    :color            "var(--dangercopy)"}
   #_[:&:hover hover-shadow {:background-color "var(--danger)"}]
   [:&:active {:background-color "var(--danger-bright)"}]
   [:&:disabled {:filter  "grayscale(100)"
                 :opacity 0.3}]])

;endregion

;region size

(o/defstyled shape-regular :div
  [:& {:font-family    "Inter"
       :font-weight    "var(--font-weight-4)"
       :border-radius  "var(--radius-1)"
       :outline-offset 0
       :white-space    :nowrap
       :min-width      "7rem"
       :font-size      "var(--font-size-1)"
       :padding-block  "var(--size-3)"
       :padding-inline "var(--size-4)"}])

;endregion                                          

;--------------------------------------

(o/defstyled normal-regular :button
  [:& color-regular shape-regular focus-button])

(o/defstyled cta-regular :button
  [:& color-cta shape-regular focus-button
   {:font-weight "var(--font-weight-6)"}])

(o/defstyled cta-outline :button
  [:& color-cta-outline shape-regular focus-button])

(o/defstyled danger-regular :button
  [:& color-danger shape-regular focus-button])

(o/defstyled round :div
  [:&
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    :color         "var(--text2)"}]
  [:&:active {:color      "var(--text2)"
              :background "var(--button)"}]
  [:&:hover {:color "var(--text1)"}])

(o/defstyled round' :button
  [:& focus-button
   :flex :items-center :justify-center
   {:aspect-ratio     "1/1"
    :border-radius    "var(--radius-round)"
    :background-color "none"
    :color            "var(--text2)"}]
  [:&:active {:color "var(--text2)"}]
  [:&:hover {:color "var(--text1)"}])