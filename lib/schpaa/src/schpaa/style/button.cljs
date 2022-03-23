(ns schpaa.style.button
  (:require [lambdaisland.ornament :as o]
            [garden.util :refer []]
            [garden.selectors :as s]
            [schpaa.debug :as l]))

;region basic

(o/defstyled round :div
  [:& :rounded-full :h-12 :w-12])

(o/defstyled base-button :button
  :shadow-sm                                                ;:focus:outline-none :outline-none

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
  #_[:&:hover {:background-color "var(--button3)"}])

(o/defstyled button-color :div
  {:background-color "var(--button1)"
   :color            "var(--button0)"}
  [:&:hover {:background-color "var(--button2)"}])

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

(o/defstyled cta :button
  [:& :outline-none :focus:outline-none
   more-base-button
   {:background-color "var(--buttoncta1)"
    :color            "var(--buttoncta0)"}
   [:&:hover {:background-color "var(--buttoncta2)"}]]
  ([{:keys [type on-click] :as attr :or {type "button"}} content]
   ^{:on-click on-click
     :type     type}
   [:<> content]))

(o/defstyled less-base-button :button
  :outline-none :focus:outline-none
  {:display         :flex
   :justify-content :center
   :align-items     :center
   ;:flex            "1 1 0px"

   ;:min-width       "6rem"
   ;:min-height      "3rem"
   :font-size       "var(--font-size-1)"
   :font-weight     "var(--font-weight-5)"
   ;:padding-inline  "var(--size-2)"
   ;:padding-block   "var(--size-2)"
   :border-radius   "var(--radius-2)"}
  [:&:disabled :opacity-25]
  #_[:&:hover {:background-color "var(--button3)"}])

(o/defstyled normal less-base-button
  [:&]
  :outline-none :focus:outline-none
  {:background-color "var(--buttonbase)"
   :color            "var(--buttontext)"}
  [:&:hover {:background-color "var(--buttonbase-hover)"}]
  [:&:active {:background-color "var(--buttonbase-active)"}]

  ([{:keys [type on-click] :as attr :or {type "button"}} content]
   ^{:on-click on-click
     :type     type}
   [:<> content]))

(o/defstyled small :div
  {:height "3rem"})

(o/defstyled clear base-button
  [:& :outline-none :focus:outline-none :shadow-none
   {;:background-color "var(--button1)"
    :xcolor "var(--buttontext)"}]
  [:&:hover {:background-color "var(--buttonbase-hover)"}])

(o/defstyled branded-button :div
  {:border     "1px solid var(--surface1)"
   :box-shadow "var(--shadow-3)"
   :color      "var(--surface5)"
   :background "var(--surface0)"})



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

(o/defstyled round-floating :div
  :duration-200
  {;:aspect-ratio "1/1"
   :display         :flex
   :justify-content :center
   :align-items     :center
   :border-radius   "var(--radius-round)"
   :padding         "var(--size-2)"
   :-background     "var(--surface1)"}
  [:&:hover {:background "var(--surface1)"}])

(o/defstyled round-normal :div                              ;base-button
  [:&
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--text3)"}]
  [:&:hover {:color      "var(--text2)"
             :background "var(--surface0)"}])

(o/defstyled round-normal-listitem :div                     ;base-button
  [:& :h-8 :w-8
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
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
  [:& :h-8 :w-8
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
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

(o/defstyled round-expander :div                            ;base-button
  [:& :h-10x
   :flex :items-center :justify-center
   {;:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
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

(o/defstyled round-normal-floating :div
  [:&
   :flex :items-center :justify-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
    :border        "1px solid var(--surface1)"
    :color         "var(--surface4)"}]
  [:&:hover {:background "var(--surface000)"}])

(o/defstyled round-dark-on-hover :div
  [:& :flex :flex-center
   :rounded-full
   {:color        "var(--surface4)"
    :aspect-ratio "1/1"}
   [#{:&:hover :&:active} {:background "var(--surface0)"}]])

(o/defstyled round-danger base-button
  [:& danger round])

(o/defstyled round-cta base-button
  [:& cta round])

(o/defstyled corner :button
  :w-10 :h-10 :rounded-full :border :flex :items-center :justify-center
  :focus:outline-none
  {:color        "var(--text3)"
   :aspect-ratio "1/1"}
  ;:border-style "dashed"
  ;:border-color "var(--surface1)"}
  [:&:hover {:background "var(--surface0)"}])

(o/defstyled small-corner :button
  :w-8 :h-8 :rounded-full :border :flex :items-center :justify-center
  :focus:outline-none
  {:color        "var(--surface4)"
   :background   "var(--surface1)"
   :aspect-ratio "1/1"
   :border-style "dashed"
   :border-color "var(--surface1)"}
  [:&:hover {:background "var(--surface1)"}])