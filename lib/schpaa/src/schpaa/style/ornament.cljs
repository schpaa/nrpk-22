(ns schpaa.style.ornament
  (:require [lambdaisland.ornament :as o]))

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
  [:&:disabled :opacity-25])

(o/defstyled danger' :div
  {:color "var(--buttondanger2)"})

(o/defstyled cta' :div
  {:background "var(--buttoncta2)"})


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
   {:background-color "var(--button1)"
    :color            "var(--button0)"}]
  [:&:hover {:background-color "var(--button2)"}])

(o/defstyled clear base-button
  [:& :outline-none :focus:outline-none :shadow-none
   {;:background-color "var(--button1)"
    :color "var(--button0)"}]
  [:&:hover {:background-color "var(--button2)"}])

(o/defstyled branded-button :div
  {:border     "2px solid black"
   :box-shadow "var(--shadow-3)"
   :color      "var(--surface5)"
   :background "var(--surface0)"})

(o/defstyled round :div
  [:& :rounded-full :h-12 :w-12])

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

(o/defstyled text :p
  {:font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-5)"})


(o/defstyled text-p :p
  {:font-size     "var(--font-size-1)"
   :line-height   "var(--font-lineheight-3)"
   :font-weight   "var(--font-weight-5)"
   :margin-bottom "var(--size-2)"})

(o/defstyled dim :div
  {:opacity 0.3})

(o/defstyled truncated :div
  {:overflow      :hidden
   :text-overflow :ellipsis
   :white-space   :nowrap})

(o/defstyled subtext :p
  [:& truncated
   {:font-size   "var(--font-size-0)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled subtext-p :p
  [:&
   {:font-size   "var(--font-size-0)"
    :line-height "var(--font-lineheight-4)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled title :h2
  [:& truncated :self-center
   {:font-size   "var(--font-size-3)"
    :font-weight "var(--font-weight-6)"}])

(o/defstyled hero-p :h2
  [:&
   {:font-size     "var(--font-size-6)"
    :font-weight   "var(--font-weight-1)"
    :margin-bottom "var(--size-4)"
    :color         "var(--text2)"}])

(o/defstyled title-p :h2
  [:&
   {:font-size     "var(--font-size-3)"
    :font-weight   "var(--font-weight-6)"
    :margin-bottom "var(--size-2)"}])

(o/defstyled subtitle :h2
  {:font-size     "var(--font-size-2)"
   :line-height   "var(--font-lineheight-3)"
   :font-weight   "var(--font-weight-2)"
   :margin-bottom "var(--size-2)"})

; region surface

(o/defstyled surface-a :div
  :p-2
  {:background    "var(--surface00)"
   :box-shadow    "var(--shadow-4)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-selected :div
  :p-2
  {:background    "var(--surface3)"
   :box-shadow    "var(--shadow-3)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-b :div
  :p-2
  {:background    "var(--surface1)"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-b-sans-bg :div
  :p-2
  {;:background "var(--surface1)"
   ;:box-shadow "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-d :div
  {:background    "var(--surface00)"
   ;:box-shadow "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-c :div
  {})                                                       ;:background "var(--surface00)"
;:box-shadow "var(--inner-shadow-1)"
;:border-radius "var(--radius-2)"})

; region grid

(o/defstyled grid-wide :div
  {:display               :grid
   :grid-auto-flow        "row dense"
   :column-gap            "4px"
   :row-gap               "4px"
   ;:grid-template-rows "masonry"
   :grid-template-columns "repeat(auto-fit,minmax(20rem,1fr))"
   #_#_:xgrid-auto-rows "3rem"})

;region icon

(o/defstyled icon :div
  :w-6 :h-6)

(o/defstyled icon-large :div
  :w-8 :h-8)

;region rows and cols

(o/defstyled col :div
  :flex :flex-col)

(o/defstyled row :div
  :flex :items-stretch)

(o/defstyled center :div
  :items-center)

;region

(o/defstyled inner-shadow :div
  {:box-shadow "var(--inner-shadow-1)"})

(o/defstyled small-padding :div
  {:padding "var(--size-2)"})

(o/defstyled rounded :div
  {:border-radius "var(--radius-2)"})

(o/defstyled small-rounded :div
  {:border-radius "var(--radius-1)"})

(o/defstyled selected-cell :div
  {:background "var(--brand1)"})

(o/defstyled normal-cell :div
  {:background "var(--surface1)"})

;region

(o/defstyled badge :div
  [:& rounded]
  :bg-black :text-white :font-oswald :px-2 :font-medium :h-full
  :text-2xl
  :flex :items-center)

(o/defstyled badge-2 :div
  [:& rounded]
  :bg-white :text-black :font-oswald :px-1 :font-medium :h-full
  :text-2xl
  :flex :items-center)

;region

(o/defstyled badge-date :div
  [:& small-rounded
   :px-2
   :bg-black :text-white :xfont-oswald :font-normal :h-full
   :text-base
   {:display "inline flow"}])

(o/defstyled badge-author :div
  [:& small-rounded
   :-ml-2
   :px-2
   :font-normal :h-full
   :text-base
   {:background "var(--surface4)"
    :color      "var(--surface000)"
    :display    "inline flow"}])
