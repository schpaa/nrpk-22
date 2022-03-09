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
   {;:letter-spacing "var(--font-letterspacing-1)"
    :color       "var(--text2)"
    :line-height "var(--font-lineheight-2)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled checkbox-text :p
  [:&
   {;:letter-spacing "var(--font-letterspacing-1)"
    :margin-top  "var(--size-1)"
    :color       "var(--text1)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-5)"}])

(o/defstyled text-clear :p
  [:&
   {;:letter-spacing "var(--font-letterspacing-1)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled text-clear-large :p
  [:&
   {;:letter-spacing "var(--font-letterspacing-1)"
    :font-size   "var(--font-size-3)"
    :font-weight "var(--font-weight-6)"}])

(o/defstyled text-1 :p
  [:&
   {:letter-spacing "var(--font-letterspacing-1)"
    :color          "var(--text1)"
    :font-size      "var(--font-size-2)"
    :font-weight    "var(--font-weight-4)"}])


(o/defstyled text-p :p
  {:color          "var(--text1)"
   :letter-spacing "var(--font-letterspacing-1)"
   :font-size      "var(--font-size-1)"
   :line-height    "var(--font-lineheight-3)"
   :font-weight    "var(--font-weight-4)"
   :padding-block  "var(--size-2)"})

(o/defstyled dim :span
  {:opacity 0.5})

(o/defstyled truncated :div
  {:overflow      :hidden
   :text-overflow :ellipsis
   :white-space   :nowrap})

(o/defstyled subtext :div
  [:&
   {:color       "var(--text3)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled small :p
  [:& truncated :tabular-nums
   {;:color       "var(--text1)"
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
   {:color       "var(--text2)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-6)"}])

(o/defstyled header-title :h2
  [:& truncated :self-center
   {;:color       "var(--text1)"
    :font-size   "var(--font-size-3)"
    :font-weight "var(--font-weight-3)"}])

(o/defstyled hero :h1
  [:& :self-center
   {:color       "var(--text1)"
    :font-size   "var(--font-size-5)"
    :font-weight "var(--font-weight-6)"}])

(o/defstyled hero-p :h2
  [:&
   {:font-size     "var(--font-size-5)"
    :line-height   "var(--font-lineheight-1)"
    :font-weight   "var(--font-weight-2)"
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
   :line-height "var(--font-lineheight-1)"
   :font-weight "var(--font-weight-5)"
   :margin-left "var(--size-2)"
   ;:margin-bottom "var(--size-2)"
   :xcolor      "var(--red-5)"})

(o/defstyled label-error :span
  {:display     :inline-block
   :font-size   "var(--font-size-0)"
   :line-height "var(--font-lineheight-1)"
   :font-weight "var(--font-weight-5)"
   ;:margin-bottom "var(--size-2)"
   :color       "var(--red-6)"})



; region surface

(o/defstyled surface-a :div
  [:& :p-4
   {:background    "var(--surface000)"
    :box-shadow    "var(--shadow-4)"
    :border-radius "var(--radius-2)"}])

(o/defstyled surface-u :div
  [:&
   {:background    "var(--surface000)"
    :box-shadow    "var(--shadow-4)"
    :border-radius "var(--radius-2)"
    :border        "1px solid var(--surface1)"}])

(o/defstyled surface-e :div
  [:& :overflow-hidden
   {:background    "var(--surface000)"
    :box-shadow    "var(--inner-shadow-1)"
    :border        "1px solid var(--surface1)"
    :border-radius "var(--radius-2)"}])

(o/defstyled surface-selected :div
  :p-2
  {:background    "var(--surface3)"
   :box-shadow    "var(--shadow-3)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-b :div

  {:background    "var(--surface1)"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-1)"})

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
  [:& :overflow-hidden
   {:box-shadow    "var(--inner-shadow-1)"
    :background    "var(--surface3)"
    :border-radius "var(--radius-2)"}])

(o/defstyled surface-bookingcard-past :div
  {:background    "var(--surface1)"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-bookingcard-highlighted :div
  {:background    "var(--brand1)"
   :box-shadow    "var(--shadow-4)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-bookingcard :div
  {:background    "var(--surface000)"
   :box-shadow    "var(--shadow-4)"
   :border-radius "var(--radius-2)"})

; region grid

(o/defstyled grid-wide :div
  {:display               :grid
   :grid-auto-flow        "row dense"
   :column-gap            "8px"
   :row-gap               "8px"
   ;:grid-template-rows "masonry"
   :grid-template-columns "repeat(auto-fill,minmax(20rem,1fr))"
   :-grid-auto-rows       "1fr"})

;region icon

(o/defstyled icon-small :div
  :w-5 :h-5)

(o/defstyled icon :div
  :w-6 :h-6)

(o/defstyled icon-large :div
  :w-8 :h-8)

;region rows and cols

(o/defstyled col :div
  :flex :flex-col)

(o/defstyled col-fields :div
  :flex :flex-col :gap-2)

(o/defstyled row-fields :div
  :flex :gap-2 :justify-between :shrink-1)

(o/defstyled row-wrap :div
  :flex :items-stretch :flex-wrap :gap-4)

(o/defstyled row :div
  :flex :items-stretch)

(o/defstyled row-end :div
  :flex :items-stretch :justify-end :gap-4)

(o/defstyled row-stretch :div
  [:& :flex :items-stretch :justify-between :w-full])

(o/defstyled center :div
  :items-center)

;region

(o/defstyled inner-shadow :div
  {:box-shadow "var(--inner-shadow-1)"})

(o/defstyled small-padding :div
  {:padding "var(--size-2)"})

(o/defstyled big-rounded :div
  {:border-radius "var(--radius-4)"})

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
   :font-size      "var(--font-size-3)"
   :font-weight    "var(--font-weight-5)"
   :padding-inline "var(--size-2)"
   :background     "var(--surface5)"
   :color          "var(--surface000)"
   :letter-spacing "var(--font-letterspacing-2)"})

(o/defstyled badge-2 :div
  [:& small-rounded :font-oswald :h-10
   {:display        :flex
    :align-items    :center
    :font-size      "var(--font-size-3)"
    :font-weight    "var(--font-weight-5)"
    :padding-inline "var(--size-1)"
    :background     "var(--surface000)"
    :color          "var(--surface5)"
    :letter-spacing "var(--font-letterspacing-2)"}])

;region pills

(o/defstyled pill :div
  [:& big-rounded :h-auto]
  {:display        :flex
   :align-items    :center
   :font-size      "var(--font-size-0)"
   :font-weight    "var(--font-weight-4)"
   :padding-inline "var(--size-2)"
   :padding-block  "var(--size-0)"
   :background     "var(--surface1)"
   :color          "var(--surface4)"
   :letter-spacing "var(--font-letterspacing-2)"})

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
  :p-4 :min-w-fit :max-w-md :shadow-xl :rounded-b-md :space-y-4
  {:color            "var(--text1)"
   :background-color "var(--surface000)"})

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

(o/defstyled corner-symbol :span
  :text-2xl :flex :justify-center :items-center :leading-none :font-normal :font-oswald)

(o/defstyled markdown :div

  [:blockquote
   [:p
    {:font-family    "sans-serif"
     :font-size      "var(--font-size-3)"
     :line-height    "var(--font-lineheight-4)"
     :padding-bottom "var(--size-2)"
     :font-weight    "var(--font-weight-2)"
     :color          "var(--text2)"}]]
  [:a {:color "var(--brand1)"}]

  [:h3 {:font-family "sans-serif"
        :padding-top "var(--size-2)"
        ;:padding-bottom "var(--size-0)"
        :font-weight "var(--font-weight-6)"
        :font-size   "var(--font-size-2)"
        :line-height "var(--font-lineheight-4)"
        :color       "var(--text1)"}]

  [:li {:list-style-type     :decimal
        :list-style-position :inside
        :font-size           "var(--font-size-1)"
        :line-height         "var(--font-lineheight-4)"
        :padding-bottom      "var(--size-3)"}
   ["&::marker"
    {
     :font-size   "var(--font-size-2)"
     :font-weight "var(--font-weight-6)"}]]

  [:h1 {:font-family   "sans-serif"
        :font-size     "var(--font-size-5)"
        :line-height   "var(--font-lineheight-1)"
        :font-weight   "var(--font-weight-2)"
        :margin-bottom "var(--size-2)"
        :color         "var(--text2)"}]

  [:h2 {:font-family   "sans-serif"
        :font-size     "var(--font-size-4)"
        :padding-top   "var(--size-4)"
        :line-height   "var(--font-lineheight-1)"
        :font-weight   "var(--font-weight-1)"
        :margin-bottom "var(--size-2)"
        :color         "var(--text2)"}]
  [:&
   {:font-family "sans-serif"}
   [:div
    {:font-size      "var(--font-size-2)"
     :font-family    "serif"
     :line-height    "var(--font-lineheight-4)"
     :padding-bottom "var(--size-2)"
     :font-weight    "var(--font-weight-4)"
     :color          "var(--text1)"}]])
  
