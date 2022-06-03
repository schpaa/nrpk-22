(ns schpaa.style.button2
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

(o/defstyled focus-button :div
  :focus:outline-none
  ;:duration-50
  [#{:&:enabled:focus-visible
     :&:enabled:active:hover}
   {:box-shadow "0 0 0 3px var(--selected)"}])

(o/defstyled hover-shadow :div
  [:&:enabled:hover {:box-shadow "var(--shadow-1)"}])

;region colors

(o/defstyled color-regular :div
  [:&
   {:background-color "var(--button)"
    :color            "var(--buttoncopy)"}
   [:&:enabled:active
    {:background-color "var(--button-bright)"}]
   [:&:disabled
    {:opacity 0.5}]])

;todo
(o/defstyled color-cta :div
  [:&
   {:background-color "orange"
    :font-weight      "var(--font-weight-7)"
    :color            "black"}                              ;var(--brand1-copy)
   [:&:disabled {:opacity 0.1
                 :filter  "saturate(0%)"}]
   [:&:enabled:active {:background-color "var(--brand1-bright)"}]])

(o/defstyled color-attn :div
  [:&
   {:color            "var(--brand1-copy)"
    :background-color "var(--brand1)"
    ;:border           "2px solid var(--gray-2)"
    :font-weight      "var(--font-weight-7)"
    :box-shadow       "var(--shadow-2)"}
   [:&:disabled {:opacity 0.1
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
   {:background-color "red"                                 ;"var(--danger-bright)"
    :color            "var(--dangercopy)"}
   [:&:active {:background-color "var(--danger-bright)"}]
   [:&:disabled {:filter  "grayscale(100)"
                 :opacity 0.3}]])

;endregion

;region size

(o/defstyled shape-regular :div
  [:&
   {:font-family    "Inter"

    :font-weight    "var(--font-weight-4)"
    :border-radius  "var(--radius-1)"
    :outline-offset 0
    :white-space    :nowrap
    :min-width      "7rem"
    :font-size      "var(--font-size-1)"
    :padding-inline "var(--size-5)"
    :padding-block  "var(--size-3)"}
   [:&.small {:min-width      "5rem"
              :height         "2.5rem"
              :padding-inline "var(--size-3)"
              :font-size      "var(--font-size-0)"
              :border-radius  "var(--radius-0)"
              :padding-block  "var(--size-2)"}]])

;endregion                                          

;--------------------------------------




(o/defstyled normal-regular :button
  [:& color-regular shape-regular focus-button])

(o/defstyled cta-regular :button
  [:& shape-regular focus-button color-cta
   {:font-weight "var(--font-weight-6)"}
   ["&[type=submit]" color-cta
    {
     :font-weight "var(--font-weight-6)"}]])

(o/defstyled attn-regular :button
  [:& color-attn shape-regular focus-button
   {:font-weight "var(--font-weight-6)"}])

(o/defstyled cta-outline :button
  [:& color-cta-outline shape-regular focus-button])

(o/defstyled danger-regular :button
  [:& color-danger shape-regular focus-button])

(o/defstyled round :button
  [:& focus-button
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background-color "var(--content)"
    :color         "var(--text2)"}]
  [:&:hover {:color "var(--text1)"}]
  [:&.large {:min-width "3rem"}])

(o/defstyled round' :button
  [:& focus-button
   :flex :items-center :justify-center
   {:aspect-ratio     "1/1"
    :border-radius    "var(--radius-round)"
    :background-color "none"
    :color            "var(--text2)"}]
  [:&:active {:color "var(--text2)"}]
  [:&:hover {:color "var(--text1)"}])