(ns schpaa.style.button
  (:require [lambdaisland.ornament :as o]))

;region basic

(o/defstyled round :div
  [:& :rounded-full :h-12 :w-12])

(o/defstyled base-button :button
  :shadow-sm :focus:outline-none :outline-none

  {:display         :flex
   :justify-content :center
   :align-items     :center
   ;:flex            "1 1 0px"
   ;:min-width       "6rem"
   :min-height      "3rem"
   :font-size       "var(--font-size-1)"
   :font-weight     "var(--font-weight-5)"
   :padding-inline  "var(--size-2)"
   :padding-block   "var(--size-2)"
   :border-radius   "var(--radius-2)"}
  [:&:disabled :opacity-25]
  [:&:hover {:background-color "var(--button3)"}])

(o/defstyled button-color :div
  {:background-color "var(--button1)"
   :color            "var(--button0)"}
  [:&:hover {:background-color "var(--button2)"}])

(o/defstyled danger base-button
  [:& :outline-none :focus:outline-none
   {:background-color "var(--buttondanger1)"
    :color            "var(--buttondanger0)"}]
  [:&:hover {:background-color "var(--buttondanger2)"}])

(o/defstyled normal base-button
  [:& :outline-none :focus:outline-none
   {:background-color "var(--buttonbase)"
    :color            "var(--buttontext)"}]
  [:&:hover {:background-color "var(--buttonbase-hover)"}]
  [:&:active {:background-color "var(--buttonbase-active)"}])

(o/defstyled clear base-button
  [:& :outline-none :focus:outline-none :shadow-none
   {;:background-color "var(--button1)"
    :color "var(--buttontext)"}]
  [:&:hover {:background-color "var(--buttonbase-hover)"}])

(o/defstyled branded-button :div
  {:border     "2px solid black"
   :box-shadow "var(--shadow-3)"
   :color      "var(--surface5)"
   :background "var(--surface0)"})

(o/defstyled cta base-button
  [:& :outline-none :focus:outline-none
   {:background-color "var(--buttoncta1)"
    :color            "var(--buttoncta0)"}]
  [:&:hover {:background-color "var(--buttoncta2)"}])

(o/defstyled button-cta base-button
  :button :outline-none :focus:outline-none
  [:& cta])

(o/defstyled button-danger base-button
  [:&
   {:background-color "var(--buttondanger1)"
    :color            "var(--buttondanger0)"}]
  [:&:hover {:background-color "var(--buttondanger2)"}])

(o/defstyled floating base-button
  [:& :outline-none :focus:outline-none
   {;:box-shadow "var(--shadow-4)"
    :border "2px solid var(--button00)"
    :color  "var(--button3)"}]
  [:&:hover {:background "var(--surface4)"
             :color      "var(--surface000)"}])

(o/defstyled normal-floating floating
  [:& branded-button])

(o/defstyled round-normal base-button
  [:& normal round])

(o/defstyled round-normal-floating floating
  [:& round
   branded-button
   #_{:border     "2px solid black"
      :background "var(--brand0)"
      :box-shadow "var(--shadow-3)"}])

(o/defstyled round-danger base-button
  [:& danger round])

(o/defstyled round-cta base-button
  [:& cta round])
