(ns schpaa.style.button2
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

(o/defstyled hover-shadow :div
  [:&:hover {:box-shadow "var(--shadow-4)"}])

(o/defstyled small :button
  {:min-width      "5rem"
   :padding-block  "var(--size-2)"
   :padding-inline "var(--size-1)"}
  {:flex      "1 1 0px"
   :flex-grow 1})

(o/defstyled large :div
  {:min-width      "7rem"
   :padding-block  "var(--size-3)"
   :padding-inline "var(--size-3)"})

(o/defstyled base :button
  [:& :flex :items-center :justify-center :select-none :cursor-pointer :duration-200
   {:border-radius "var(--radius-2)"
    :font-weight   "var(--font-weight-5)"
    #_#_:border "2px solid var(--surface1)"}
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
   {:border "2px solid var(--buttontext)"
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
   {:flex-grow 1}])

(o/defstyled cta-small cta
  [:& small])

(o/defstyled danger-small danger
  [:& small])





