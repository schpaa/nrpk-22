(ns schpaa.style.ornament
  (:require [lambdaisland.ornament :as o]))

(o/defstyled base-button :button
  :shadow-sm :focus:outline-none :outline-none

  {:display         :flex
   :justify-content :center
   :align-items     :center
   ;:flex            "1 1 0px"
   ;:min-width       "6rem"
   ;:min-height      "3rem"
   :font-size       "var(--font-size-1)"
   :font-weight     "var(--font-weight-5)"
   :padding-inline  "var(--size-2)"
   :padding-block   "var(--size-2)"
   :border-radius   "var(--radius-2)"}
  [:&:disabled :opacity-25])

(o/defstyled button :button
  :button :outline-none :focus:outline-none
  {:background-color "var(--surface000)"
   :color            "var(--surface5)"}
  base-button)

(o/defstyled button-round base-button
  :rounded-full
  :h-12 :w-12
  [:&:hover
   {:background-color "var(--surface5)"
    :color            "var(--surface000)"}]
  {:background-color "var(--surface000)"
   :color            "var(--surface5)"})
