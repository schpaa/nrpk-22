(ns schpaa.style.ornament
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.button :refer []]))


(o/defstyled danger' :div
  {:color "var(--buttondanger2)"})

(o/defstyled cta' :div
  {:background "var(--buttoncta2)"})

(o/defstyled bold :span
  [:& {:font-weight "var(--font-weight-8)"}])

(o/defstyled link :a
  :cursor-pointer
  {:font-family     "unset"
   :font-weight     "var(--font-weight-3)"
   :text-decoration "underline"
   :padding-inline  "3px"
   :padding-block   "2px"
   :color           "var(--brand1)"}
  [:&:hover {:transition-duration "200ms"
             :transition-property "color"
             :text-decoration     "none"
             :border-radius       "var(--radius-1)"
             :color               :white
             :background          "var(--brand1)"}])

(o/defstyled separator :div
  {:text-align     "center"
   :padding-block  "var(--size-4)"
   :font-family    "Montserrat"
   :text-transform "uppercase"})

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
  {:opacity 0.3})

(o/defstyled truncated :div
  {:overflow      :hidden
   :text-overflow :ellipsis
   :white-space   :nowrap})

(o/defstyled subtext :div
  [:&
   {:color       "var(--text3)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-3)"}])

(o/defstyled subtext-with-link :a
  [:& link
   {;:color           "var(--text3)"
    ;:text-decoration "underline"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled small :p
  [:& truncated :tabular-nums
   {;:color       "var(--text1)"
    :font-size   "var(--font-size-0)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled as-shortcut :p
  [:& truncated :uppercase
   {;:color       "var(--text1)"
    :border-radius    "var(--radius-1)"
    :padding-inline   "var(--size-2)"
    :padding-block    "var(--size-1)"
    :background-color "white"
    :border           "1px solid var(--surface1)"
    :font-size        "var(--font-size-0)"
    :font-weight      "var(--font-weight-4)"}])

(o/defstyled subtext-p :p
  [:& :whitespace-wrap
   {:color       "var(--text3)"
    :font-size   "var(--font-size-1)"
    :line-height "var(--font-lineheight-3)"
    :font-weight "var(--font-weight-2)"}])

(o/defstyled title :h2
  [:& truncated                                             ;:self-center
   {:color       "var(--text2)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-5)"}])

(o/defstyled header-title :h2
  {:color       "var(--text1)"
   :font-family "Inter"
   ;:letter-spacing "var(--font-letterspacing-0)"
   :font-size   "var(--font-size-2)"
   :font-weight "var(--font-weight-5)"})

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
    :font-weight   "var(--font-weight-6)"
    :margin-bottom "var(--size-2)"}])

(o/defstyled header :h2
  {:font-size   "var(--font-size-3)"
   :font-weight "var(--font-weight-7)"})

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

(o/defstyled icon-tiny :div
  :w-4 :h-4)

(o/defstyled icon-small :div
  :w-5 :h-5)

(o/defstyled icon :div
  :w-6 :h-6)

(o/defstyled icon-large :div
  :w-8 :h-8)

;region rows and cols

(o/defstyled col :div
  :flex :flex-col)

(o/defstyled col-space-2 :div
  :flex :flex-col :space-y-3)

(o/defstyled col-space-1 :div
  :flex :flex-col :space-y-1)

(o/defstyled col-fields :div
  :flex :flex-col :gap-2)

(o/defstyled row-fields :div
  :flex :gap-2 :justify-between :shrink-1)

(o/defstyled row-wrap :div
  :flex :items-stretch :flex-wrap :gap-4)

(o/defstyled row-sc :div
  :flex :items-center :gap-4)

(o/defstyled row-ec :div
  :flex :items-center :justify-end :gap-3)

(o/defstyled row :div
  :flex :items-stretch)

(o/defstyled row' :div
  :flex :items-center :h-full :gap-4 :w-full)

(o/defstyled row'' :div
  :flex :justify-start :h-full :w-full)

(o/defstyled row-center :div
  :flex-center :gap-4)

(o/defstyled row-std :div
  :shrink-0
  {:gap             "var(--size-2)"
   :display         :flex
   :align-items     :center
   :width           "100%"
   :justify-content :space-between})

(o/defstyled row-gap :div
  :flex :items-center :gap-2)

(o/defstyled row-end :div
  :flex :items-center :justify-end :xgap-1)

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

(o/defstyled badge :button
  {:box-shadow "var(--shadow-2)"}
  [:div :font-oswald :h-full :w-full
   {:display         :inline-flex
    :justify-content :center
    :align-items     :center
    :border-radius   "var(--radius-1)"
    :font-size       "var(--font-size-4)"
    :font-weight     "var(--font-weight-6)"
    ;:padding-inline "var(--size-2)"

    :color           "var(--surface5)"
    :letter-spacing  "var(--font-letterspacing-1)"}]
  [:div.normal {:background "white" #_"var(--surface000)"}]

  [:div.selected {:background "var(--yellow-5)"}]
  ([{:keys [selected on-click]} ch]
   ^{:on-click on-click}
   [:<> [:div {:class (if selected :selected :normal)} ch]]))

(o/defstyled badge-big :div

  [:& :font-oswald :h-10 :flex-center]
  {:border-radius  "var(--radius-1)"
   :font-size      "var(--font-size-5)"
   :font-weight    "var(--font-weight-6)"
   :padding-block  "var(--size-3)"
   :padding-inline "var(--size-2)"
   :box-shadow     "var(--inner-shadow-2)"
   :background     :white                                   ;"var(--surface000)"
   :color          "var(--surface4)"
   :letter-spacing "var(--font-letterspacing-1)"})

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
  [:& big-rounded :inline-block :h-auto]
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
  :px-4x :xmin-w-fit :min-w-md :shadow-xl :rounded-b-xl :space-y-4
  {:border           "1px solid var(--surface1)"
   :color            "var(--text1)"
   :background-color "var(--surface00)"
   :box-shadow       "var(--shadow-6)"})

(o/defstyled selector :div
  :min-w-xsx :xmax-w-md :h-autox
  [:&
   {:border           "1px solid var(--surface1)"
    :box-shadow       "var(--shadow-6)"
    :color            "var(--text1)"
    :background-color "var(--surface000)"}])

(o/defstyled dialog-title :div
  :font-medium :leading-6
  {:color "var(--text1)"})

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

(o/defstyled li-colours :span
  {:color        "var(--surface000)"
   :background   "var(--brand1)"
   :border-color "none" #_"var(--red-5)"})

(o/defstyled markdown :div
  [:* {:-webkit-font-smoothing   :auto
       :-moz-osx-font-smoothing  :auto
       :x-webkit-font-smoothing  :antialiased
       :x-moz-osx-font-smoothing :grayscale}]
  [#{:h1 :h2 :h3 :li :p.answer :p}
   [:code {:font-family    "unset"
           :background     "var(--yellow-2)"
           :padding-inline "var(--size-1)"
           :padding-block  "var(--size-1)"}]]
  [:pre
   {;:outline     "1px red solid"
    :overflow-x  :auto
    :line-height "var(--font-lineheight-1)"}
   [:code {:font-family "IBM Plex Mono"
           :font-size   "var(--font-size-1)"}]]

  [#{:p :ul :ol} {:color          "var(--text1)"
                  :font-size      "var(--font-size-1)"
                  :font-weight    "var(--font-weight-1)"
                  :line-height    "var(--font-lineheight-4)"
                  :padding-bottom "var(--size-3)"}]
  [:a link #_{:font-family     "unset"
              :font-weight     "var(--font-weight-4)"
              :text-decoration :underline
              :color           "var(--link-1)"}]
  [#{:h1 :h2 :h3 :h5 :h6} {:color "var(--text3)"}]
  [#{:h3 :h4}
   {:font-weight "var(--font-weight-7)"
    :font-family "Inter"
    ;:font-family "Merriweather"
    :color       "var(--brand1)"
    :line-height "var(--font-lineheight-3)"
    :margin-top  "var(--size-3)"
    :font-size   "var(--font-size-2)"}]
  [#{:h1 :h2 :h5 :h6} :-tracking-tight {:font-family "Inter"}]
  ;[:h4 :tracking-wide {:font-family "Inter"}]
  [:h1 {:padding-top "var(--size-4)"
        :font-weight "var(--font-weight-1)"}]
  [:h1
   {:font-size     "var(--font-size-5)"
    :line-height   "var(--font-lineheight-0)"
    :font-weight   "var(--font-weight-2)"
    ;:letter-spacing "var(--font-letterspacing-1)"
    :margin-bottom "var(--size-3)"
    :color         "var(--text3)"}

   [:a link #_{:font-weight     "var(--font-weight-2)"
               :text-decoration :underline}]]
  [:h2
   {:font-size   "var(--font-size-3)"
    :line-height "var(--font-lineheight-4)"
    ;:margin-bottom "var(--size-1)"
    ;:letter-spacing "var(--font-letterspacing-0)"
    :color       "var(--text2)"
    :font-weight "var(--font-weight-2)"}
   [:a link #_{:font-weight     "var(--font-weight-4)"
               :text-decoration :underline}]]


  [:blockquote {:color          "var(--text3)"
                :background     "var(--surface00)"
                :padding-block  "var(--size-4)"
                :padding-inline "var(--size-4)"
                :margin-inline  "var(--size-4)"
                :margin-block   "var(--size-4)"
                :font-style     :italic
                :border-left    "solid var(--surface0) var(--size-2)"
                :quotes         ["201C" "201D" "2018" "2019"]}
   [:p {:display :inline}]

   ["&::before" {:color         "var(--brand1)"
                 :content       "open-quote"
                 :-margin-right "1rem"}]
   ["&::after" {:content "close-quote"
                :color   "var(--brand1)"}]]

  [:ul
   {:list-style    "inside square"
    ;:padding-inline "var(--size-2)"
    :padding-left  "1rem"
    :padding-right "3rem"}
   [:li {:list-style-type "none"
         :position        "relative"
         :padding-bottom  "var(--size-2)"}
    ["&::before" {:display     :inline
                  :color       "var(--text1)"
                  :left        "-1rem"
                  :font-weight "var(--font-weight-6)"
                  ;:margin-right "var(--size-3)"
                  :position    "absolute"
                  :content     [:str "\u2022"]}]]]
  [:ol
   {:counter-reset   "li"
    :list-style-type "none"
    ;:font-weight     "var(--font-weight-3)"
    ;:padding-block  "var(--size-5)"
    :padding-left    "2.3rem"}

   ;["li > p" :text-red-400]
   [:.answer {:padding-top    :8px
              :padding-bottom :none
              :margin-bottom  :none
              :margin-right   "var(--size-4)"

              :font-size      "var(--font-size-1)"
              ;:font-family    "Inter"
              :font-weight    600}]


   [:li {:list-style-type "none"
         :padding-block   "var(--size-2)"
         :position        "relative"}

    ["&::before" li-colours {:display           :inline-flex
                             :font-weight       "var(--font-weight-6)"
                             :align-items       :center
                             :justify-content   :center
                             :font-family       "Inter"
                             :font-size         "var(--font-size-1)"
                             :content           "counter(li)"
                             :counter-increment "li"
                             :border-radius     "50%"
                             :height            "26px"
                             :width             "26px"
                             :left              "-2.2rem"
                             :top               "10px"      ;"calc(11px + var(--size-2))"
                             :position          "absolute"}]]]

  [:&
   {:font-family   "Inter"
    :margin-bottom "var(--size-3)"}
   [:strong {:font-weight "var(--font-weight-6)"}]
   [:div
    {:font-size   "var(--font-size-3)"
     :color       "var(--text1)"
     :font-family "Merriweather"
     :line-height "var(--font-lineheight-4)"
     :font-weight "var(--font-weight-5)"}]]

  #_[:span.question {:color         "var(--brand1)"
                     :padding-right "var(--size-10)"}]
  [:div.qa
   [:li {:color "var(--brand1)"}]
   ["li p" {:margin-top    "var(--size-3)"
            :padding-right "var(--size-4)"
            :color         "var(--text1)"}
    ["&::before" {:content [:str "—"]}]]])


