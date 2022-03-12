(ns schpaa.style.button2
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

(o/defstyled hover-shadow :div
  [:&:hover {:box-shadow "var(--shadow-2)"}])

(o/defstyled small :button
  {:white-space    :nowrap
   :min-width      "6rem"
   :padding-block  "var(--size-2)"
   :padding-inline "var(--size-4)"}
  {:-flex      "1 1 0px"
   :-flex-grow 1})

(o/defstyled tight :button
  {:white-space    :nowrap
   :padding-block  "var(--size-1)"
   :padding-inline "var(--size-2)"
   :border-radius  "var(--radius-1)"})


(o/defstyled large :div
  {:min-width      "7rem"
   :font-size      "var(--font-size-2)"
   :font-weight    "var(--font-weight-6)"
   :box-shadow     "var(--shadow-4)"
   :padding-block  "var(--size-3)"
   :padding-inline "var(--size-3)"})

(o/defstyled base :button
  [:& :flex :items-center :justify-center :select-none :cursor-pointer :duration-200
   {:border-radius "var(--radius-2)"
    :font-weight   "var(--font-weight-5)"}
   [:&:disabled :opacity-25]])

(o/defstyled normal base
  [:&
   {:background-color "var(--buttonbase)"
    :color            "var(--buttontext)"}
   [:&:hover hover-shadow {:background-color "var(--buttonbase-hover)"}]
   [:&:active {:background-color "var(--buttonbase-active)"}]])

(o/defstyled cta base
  [:&
   {:background-color "var(--buttoncta1)"
    :color            "var(--buttoncta0)"}
   [:&:hover hover-shadow {:background-color "var(--buttoncta2)"}]
   [:&:active {:background-color "var(--buttoncta1)"}]
   [:&:disable {:opacity 0.5}]])

(o/defstyled danger base
  [:&
   {:background-color "var(--buttondanger1)"
    :color            "var(--buttondanger0)"}
   [:&:hover hover-shadow {:background-color "var(--buttondanger2)"}]
   [:&:active {:background-color "var(--buttondanger1)"}]])

(o/defstyled outline base
  [:&
   {:border "2px solid var(--buttonbase-active)"
    :color  "var(--buttontext)"}
   [:&:hover hover-shadow {:background-color "var(--buttonbase)"}]
   [:&:active {:background-color "var(--buttonbase-active)"}]])

(o/defstyled outline-large outline
  [:& large])

(o/defstyled normal-large normal
  [:& large])

(o/defstyled cta-large cta
  [:& large])

(o/defstyled danger-large danger
  [:& large])

(o/defstyled normal-small normal
  [:& small
   {:-flex-grow 1}])

(o/defstyled normal-tight normal
  [:& tight])

(o/defstyled outline-tight outline
  [:& tight])

(o/defstyled cta-small cta
  [:& small])

(o/defstyled danger-small danger
  [:& small])





