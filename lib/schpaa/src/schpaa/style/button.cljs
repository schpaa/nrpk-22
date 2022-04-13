(ns schpaa.style.button
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

;region basic

(o/defstyled base-button :button
  :shadow-sm
  {:display         :flex
   :justify-content :center
   :align-items     :center
   :min-height      "3rem"
   :font-size       "var(--font-size-1)"
   :font-weight     "var(--font-weight-5)"
   :padding-inline  "var(--size-2)"
   :padding-block   "var(--size-2)"
   :border-radius   "var(--radius-2)"}
  [:&:disabled :opacity-25]
  #_[:&:hover {:background-color "var(--button3)"}])

(o/defstyled more-base-button :button
  :shadow-sm
  {:display         :flex
   :justify-content :center
   :align-items     :center
   ;:flex            "1 1 0px"

   :min-width       "6rem"
   ;:min-height      "3rem"
   :font-size       "var(--font-size-1)"
   :font-weight     "var(--font-weight-5)"
   ;:padding-inline  "var(--size-2)"
   ;:padding-block   "var(--size-2)"
   :border-radius   "var(--radius-2)"}
  [:&:disabled :opacity-25]
  #_[:&:hover {:background-color "var(--button3)"}])

(o/defstyled danger :button
  [:& more-base-button
   :outline-none :focus:outline-none
   {:background "var(--buttondanger1)"
    :color      "var(--buttondanger0)"}
   [:&:hover {:background-color "var(--buttondanger2)"}]]
  ([{:keys [type on-click] :as attr :or {type "button"}} content]
   ^{:on-click on-click
     :type     type}
   [:<> content]))

(o/defstyled danger-small :button
  [:& more-base-button
   :outline-none :focus:outline-none
   {:min-width  "3rem"
    :background "var(--buttondanger1)"
    :color      "var(--buttondanger0)"}
   [:&:hover {:background-color "var(--buttondanger2)"}]]
  ([{:keys [type on-click] :as attr :or {type "button"}} content]
   ^{:on-click on-click
     :type     type}
   [:<> content]))



(o/defstyled clear base-button
  [:& :outline-none :focus:outline-none :shadow-none
   {;:background-color "var(--button1)"
    :xcolor "var(--buttontext)"}]
  [:&:hover {:background-color "var(--buttonbase-hover)"}])



(o/defstyled round-normal-listitem :div                     ;base-button
  [:& :h-8 :w-8 :cursor-pointer :-mb-2
   :flex :items-center :justify-center
   {:outline       "1px solid red"
    :aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--surface4)"}]
  [:&:active {:background "var(--surface1)"}]
  [:&:hover {:background "white" #_"var(--surface000)"}])

(o/defstyled static-listitem :div                           ;base-button
  [:& :h-8 :w-8
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    :color         "var(--surface4)"}])

(o/defstyled round-danger-listitem :div                     ;base-button
  [:& :h-8 :w-8 :cursor-pointer :-mb-2
   :flex :items-center :justify-center
   {:outline       "1px solid red"
    :aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--surface4)"}]
  [:&:active {:background "var(--surface1)"}]
  [:&:hover {:background "var(--red-4)"}])

(o/defstyled round-mainpage :div
  [:& :h-10
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    :background    "var(--surface0)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--surface4)"}]
  [:&:hover {:background "var(--surface0)"}])


(o/defstyled round-dark :div
  [:& :w-10 :h-10
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    :background    "var(--red-6)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--red-0)"}]
  [:&:hover {:background "var(--red-5)"}])


(o/defstyled corner :button
  :w-10 :h-10 :rounded-full :flex :items-center :justify-center
  :focus:outline-none
  {:color        "var(--text1)"
   :aspect-ratio "1/1"}
  ;:border-style "dashed"
  ;:border-color "var(--surface1)"}
  [:&:hover {:background "var(--surface0)"}])

