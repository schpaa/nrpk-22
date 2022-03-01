(ns schpaa.style.ornament
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.button :refer []]))


(o/defstyled danger' :div
  {:color "var(--buttondanger2)"})

(o/defstyled cta' :div
  {:background "var(--buttoncta2)"})

(o/defstyled bold :span
  [:& {:font-weight "var(--font-weight-8)"}])

(o/defstyled text :p
  [:&
   {:letter-spacing "var(--font-letterspacing-1)"
    :color          "var(--text2)"
    :font-size      "var(--font-size-1)"
    :font-weight    "var(--font-weight-4)"}])


(o/defstyled text-p :p
  {:color          "var(--text1)"
   :letter-spacing "var(--font-letterspacing-1)"
   :font-size      "var(--font-size-1)"
   :line-height    "var(--font-lineheight-3)"
   :font-weight    "var(--font-weight-4)"
   :padding-block  "var(--size-2)"})

(o/defstyled dim :span
  {:opacity 0.3})

(o/defstyled truncated :div
  {:overflow      :hidden
   :text-overflow :ellipsis
   :white-space   :nowrap})

(o/defstyled subtext :p
  [:& truncated
   {:color       "var(--text3)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled small :p
  [:& truncated :tabular-nums
   {:color       "var(--text1)"
    :font-size   "var(--font-size-0)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled subtext-p :p
  [:& :whitespace-wrap
   {:color       "var(--text3)"
    :font-size   "var(--font-size-1)"
    :line-height "var(--font-lineheight-4)"
    :font-weight "var(--font-weight-2)"}])

(o/defstyled title :h2
  [:& truncated :self-center
   {:color       "var(--text1)"
    :font-size   "var(--font-size-3)"
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
    :font-weight   "var(--font-weight-7)"
    :margin-bottom "var(--size-2)"}])

(o/defstyled subtitle :h2
  {:font-size     "var(--font-size-2)"
   :line-height   "var(--font-lineheight-3)"
   :font-weight   "var(--font-weight-2)"
   :margin-bottom "var(--size-2)"})

(o/defstyled label :span
  {:display     :inline-block
   :font-size   "var(--font-size-0)"
   :line-height "var(--font-lineheight-3)"
   :font-weight "var(--font-weight-7)"
   ;:margin-bottom "var(--size-2)"
   :xcolor      "var(--red-5)"})

(o/defstyled label-error :span
  {:display     :inline-block
   :font-size   "var(--font-size-0)"
   :line-height "var(--font-lineheight-3)"
   :font-weight "var(--font-weight-4)"
   ;:margin-bottom "var(--size-2)"
   :color       "var(--red-6)"})



; region surface

(o/defstyled surface-a :div
  [:& :p-4
   {:background    "var(--surface00)"
    ;:box-shadow    "var(--shadow-4)"
    :border-radius "var(--radius-2)"}])

(o/defstyled surface-selected :div
  :p-2
  {:background    "var(--surface3)"
   :box-shadow    "var(--shadow-3)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-b :div
  :p-2
  {:background    "var(--surface1)"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-1)"})

(o/defstyled surface-b-sans-bg :div
  :p-2
  {;:background "var(--surface1)"
   ;:box-shadow "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-d :div
  :p-2
  {:background    "var(--surface00)"
   ;:box-shadow "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-c :div
  [:& :overflow-hidden
   {:box-shadow    "var(--inner-shadow-1)"
    :background    "var(--surface3)"
    :border-radius "var(--radius-2)"}])

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

(o/defstyled row-wrap :div
  :flex :items-stretch :flex-wrap :gap-4)

(o/defstyled row :div
  :flex :items-stretch)

(o/defstyled row-stretch :div
  [:& :flex :items-stretch :justify-between :w-full])

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
  {:border-radius "var(--radius-2)"})

(o/defstyled selected-cell :div
  {:background "var(--brand1)"})

(o/defstyled normal-cell :div
  {:background "var(--surface1)"})

;region badges

(o/defstyled badge :div
  [:& small-rounded :font-oswald :h-10]
  {:display        :flex
   :align-items    :center
   :font-size      "var(--font-size-4)"
   :font-weight    "var(--font-weight-5)"
   :padding-inline "var(--size-2)"
   :background     "var(--surface5)"
   :color          "var(--surface000)"
   :letter-spacing "var(--font-letterspacing-2)"})

(o/defstyled badge-2 :div
  [:& small-rounded :font-oswald :h-10
   {:display        :flex
    :align-items    :center
    :font-size      "var(--font-size-4)"
    :font-weight    "var(--font-weight-5)"
    :padding-inline "var(--size-1)"
    :background     "var(--surface000)"
    :color          "var(--surface5)"
    :letter-spacing "var(--font-letterspacing-2)"}])

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

; region

(o/defstyled dialog :div
  :p-4 :min-w-xs :max-w-md :shadow-xl :rounded-b-md :space-y-4 :w-full
  {:color            "var(--text1)"
   :background-color "var(--surface00)"})

(o/defstyled dialog-title :div
  :font-medium :leading-6
  {:color "var(--text1)"}
  #_([title]
     [ui/dialog-title {:as :div}
      title]))

(o/defstyled field-label :div
  :flex :flex-col :text-sm :w-full
  {:color "var(--text2)"})

(o/defstyled field-label-short :div
  :flex :flex-col :text-sm :w-auto
  {:color "var(--text2)"})

(o/defstyled field-label-date :div
  :flex :flex-col :text-sm :w-40
  {:color "var(--text2)"})

(o/defstyled validation-error :div
  :flex :flex-col :space-y-px :m-px
  [:div :p-px :rounded {:color      "var(--red-0)"
                        :background "var(--red-6)"}]
  ([err fld]
   (into [:<>] (map (fn [e] [:div e]) (get err fld)))))
