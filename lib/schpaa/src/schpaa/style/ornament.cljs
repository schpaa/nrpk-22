(ns schpaa.style.ornament
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.button :refer []]
            [schpaa.style.button2 :as scb2]
            [schpaa.state]))

(o/defstyled danger' :div
  {:color "var(--buttondanger2)"})

(o/defstyled cta' :div
  {:background "var(--buttoncta2)"})

(o/defstyled bold :span
  [:& {:font-weight "var(--font-weight-8)"}])

(o/defstyled link :a
  [:& :cursor-pointer scb2/focus-button
   {:display               :inline-block
    ;:text-indent 0
    ;:padding-left 0
    :font-family           "unset"
    :font-weight           "var(--font-weight-3)"
    :text-decoration       "underline"
    :text-decoration-color "var(--brand2-weak)"
    :text-underline-offset "3px"
    :border-radius         "var(--radius-0)"
    :color                 "var(--brand2)"}
   [:&:hover {:transition-duration       "100ms"
              :transition-property       "outline"
              :text-decoration-thickness "2px"
              :text-decoration-color     "var(--text1)"
              :color                     "var(--text1)"}]
   [:&:active {:color "var(--brand2)"}]
   [:&.disabled {:text-decoration "none"
                 :cursor          :default
                 :color           "var(--text2)"}]])

(o/defstyled datetimelink :div
  [:& :cursor-pointer                                       ;scb2/focus-button
   {:display               :inline-block
    ;:text-indent 0
    ;:padding-left 0
    :text-indent           "0"
    :font-family           "unset"
    :font-weight           "var(--font-weight-3)"
    :font-size             "var(--font-size-1)"
    ;:text-decoration       "underline"
    ;:text-decoration-color "var(--brand2-weak)"
    :text-underline-offset "3px"
    :border-radius         "var(--radius-0)"
    :color                 "var(--brand2)"}
   [:&:hover {:transition-duration       "100ms"
              :transition-property       "outline"
              :text-decoration-thickness "2px"
              :text-decoration-color     "var(--text1)"
              :color                     "var(--text1)"}]
   [:&:active {:color "var(--brand2)"}]
   [:&.disabled {:text-decoration "none"
                 :cursor          :default
                 :color           "var(--text2)"}]])

(o/defstyled header-accomp-link :a
  [:& :cursor-pointer link
   scb2/focus-button
   {:font-family     "unset"
    :font-weight     "var(--font-weight-3)"
    :text-decoration "underline"
    :font-size       "var(--font-size-1)"
    :padding-block   "2px"
    :border-radius   "var(--radius-0)"
    :color           "var(--brand2)"}
   [:&.disabled
    {:text-decoration "none"
     :cursor          :default
     :color           "var(--text2)"}]
   #_#_[:&:hover {:transition-duration "100ms"
                  :transition-property "outline"
                  ;:text-decoration     "none"
                  :color               "var(--text1)"}]
           [:&:active {:transition-duration "100ms"
                       :transition-property "background-color"
                       ;:text-decoration     "none"
                       :color               "var(--brand2)"}]])

(o/defstyled hidden-link :a
  :cursor-pointer
  {:display         :inline-block
   :font-family     "unset"
   :font-weight     "var(--font-weight-3)"
   :text-decoration "none"}
  ;:padding-inline  "3px"
  ;:padding-block   "2px"

  [:&:hover {:text-decoration "underline"}])

(o/defstyled separator :div
  {:text-align     "center"
   ;:padding-block  "var(--size-4)"
   :font-family    "Montserrat"
   :text-transform "uppercase"})

(o/defstyled button-caption :div
  {:line-height "var(--font-lineheight-2)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-4)"})

(o/defstyled text :div
  [:&
   {:line-height "var(--font-lineheight-2)"
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled text-inline :span
  {:color       "var(--text1)"
   :line-height "var(--font-lineheight-2)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-4)"})

(o/defstyled text0 :div
  [:& text
   {:color "var(--text0)"}])

(o/defstyled text1 :div
  [:& text
   {:color "var(--text1)"}])

(o/defstyled text1-with :div
  [:& text
   {:display :inline-block
    :color   "var(--text1)"}])

(o/defstyled text1-cl :div
  [:& text])


(o/defstyled text-cl :div
  text)


(o/defstyled text2 :div
  [:& text
   {:color "var(--text2)"}])

(o/defstyled text3 :div
  text
  {:color "var(--text3)"})

(o/defstyled fp-header :div
  {:color          "var(--text1)"
   ;:margin-left    "-1px"
   :font-family    "Inter"
   :letter-spacing "var(--font-letterspacing-0)"
   :font-size      "var(--font-size-4)"
   :font-weight    "var(--font-weight-3)"})

(o/defstyled fp-text :div
  [:&
   {:color       "var(--text0)"
    :line-height "var(--font-lineheight-3)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-4)"}])


(o/defstyled fp-text-inline :span
  [:&
   {:color       "var(--text0)"
    :line-height "var(--font-lineheight-3)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled ingress :div
  {:line-height "var(--font-lineheight-4)"
   :color       "var(--text2)"
   :font-size   "var(--font-size-3)"
   :font-weight "var(--font-weight-2)"})

(o/defstyled ingress' :div
  [:&
   {:color       "var(--text0)"
    :font-size   "var(--font-size-2)"
    :font-weight "var(--font-weight-4)"}])

(o/defstyled ingress-cl :div
  {:font-size   "var(--font-size-3)"
   :font-weight "var(--font-weight-3)"})

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
  {:color       "var(--text2)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-3)"})

(o/defstyled subtext-inline :span
  {
   :color       "var(--text2)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-3)"})

(o/defstyled subtext1 :div
  {:color       "var(--text1)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-3)"})

(o/defstyled small-button-caption :div
  {:color       "var(--text1)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-4)"})

(o/defstyled subtext-cl :div
  [:&
   {;:font-family "IBM Plex Sans"
    :font-size   "var(--font-size-0)"
    :font-weight "var(--font-weight-5)"}])

(o/defstyled subtext-with-link link
  [:& link :w-auto
   {:display     :inline-block
    :white-space :nowrap
    :font-size   "var(--font-size-1)"
    :font-weight "var(--font-weight-4)"}
   [:&.dark {:color "var(--brand2)"}
    [:&:enabled:hover {:color "white"}]]
   [:&:enabled:hover {:color "var(--brand1)"}]])

(o/defstyled small :div
  [:&
   {:font-size   "var(--font-size-0)"
    :font-weight "var(--font-weight-3)"}])

(o/defstyled small-inline :span
  {:font-size   "var(--font-size-0)"
   :font-weight "var(--font-weight-3)"})

(o/defstyled small0 :div
  [:& small
   {:color "var(--text0)"}])

(o/defstyled small1 :div
  [:& small
   {:color "var(--text2)"}
   [:&:hover
    {:color "var(--text1)"}]])

(o/defstyled small1-inline :div
  [:& small
   {:display :inline-block
    :color   "var(--text2)"}
   [:&:hover
    {:color "var(--text1)"}]])

(o/defstyled small1' :div
  [:& small
   {:color "var(--text1)"}])


(o/defstyled small2 :div
  [:& small
   {:color "var(--text2)"}
   [:&:hover
    {:color "var(--text1)"}]])

(o/defstyled as-shortcut :p
  [:& truncated :uppercase
   {:color            "var(--text1)"
    :border-radius    "var(--radius-1)"
    :padding-inline   "var(--size-2)"
    :padding-block    "var(--size-1)"
    :background-color "var(--content)"
    :border           "1px solid var(--text2)"
    :font-size        "var(--font-size-0)"
    :font-weight      "var(--font-weight-4)"}])

(o/defstyled subtext-p :p
  [:& :whitespace-wrap
   {:color       "var(--text2)"
    :font-size   "var(--font-size-1)"
    :line-height "var(--font-lineheight-3)"
    :font-weight "var(--font-weight-2)"}])

(o/defstyled title :div
  {:font-size   "var(--font-size-3)"
   :font-weight "var(--font-weight-4)"})

(o/defstyled title1 :h2
  [:& truncated title
   {:color "var(--text1)"}])

(o/defstyled title2 :h2
  [:& truncated title
   {:color "var(--text2)"}])

(o/defstyled header-title :h2
  {:color       "var(--text1)"
   :font-family "Inter"
   ;:letter-spacing "var(--font-letterspacing-0)"
   :font-size   "var(--font-size-2)"
   :font-weight "var(--font-weight-5)"})

(o/defstyled header-title-cl :h2
  {
   :font-family "Inter"
   ;:letter-spacing "var(--font-letterspacing-0)"
   :font-size   "var(--font-size-2)"
   :font-weight "var(--font-weight-5)"})

(o/defstyled hero :h2
  [:&
   {:margin-left    "-1px"
    :color          "var(--text2)"
    :font-family    "Inter"
    :letter-spacing "var(--font-letterspacing-0)"
    :font-size      "var(--font-size-5)"
    :font-weight    "var(--font-weight-2)"}])

(o/defstyled hero' :h2
  [:&
   {:margin-left    "-1px"
    :color          "var(--text0)"
    :font-family    "Inter"
    :letter-spacing "var(--font-letterspacing-0)"
    :font-size      "var(--font-size-5)"
    :font-weight    "var(--font-weight-2)"}])

(o/defstyled hero-p :h2
  [:&
   {:font-size     "var(--font-size-5)"
    :line-height   "var(--font-lineheight-1)"
    :font-weight   "var(--font-weight-2)"
    :margin-bottom "var(--size-4)"
    :color         "var(--text2)"}])

(o/defstyled hero-summary :summary
  [:&
   {:user-select   :none
    :font-size     "var(--font-size-5)"
    :line-height   "var(--font-lineheight-1)"
    :font-weight   "var(--font-weight-2)"
    :margin-bottom "var(--size-2)"
    :color         "var(--text2)"}

   [:&:focus {:outline :none}]])


(o/defstyled ingress-details :details
  [:&
   {:color          "var(--text2)"
    :font-size      "var(--font-size-3)"
    :line-height    "var(--font-lineheight-4)"
    :font-weight    "var(--font-weight-3)"
    :-margin-bottom "var(--size-4)"}
   [:&>summary
    scb2/focus-button
    {:cursor              :pointer
     :display             :inline-block
     :list-style          :none
     :-webkit-user-select :none
     :user-select         :none}]
   ["&[open]>summary::before" {:content         [:str "\u25be"]
                               :opacity         0.75
                               :font-size       "120%"
                               :color           "var(--brand2)"
                               :display         :inline-flex
                               :justify-content :start
                               :align-items     :center
                               :width           "var(--size-4)"}]
   ["&>summary::-webkit-details-marker" {:display :none}]
   ["&>summary::marker" {:display :none}]
   ["&>summary::before" {:content         [:str "\u25b8"]
                         :opacity         0.75
                         :font-size       "120%"
                         :color           "var(--brand2)"
                         :display         :inline-flex
                         :justify-content :start
                         :align-items     :center
                         :width           "var(--size-4)"}]])


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
   :color       "var(--text2)"})

(o/defstyled label-error :span
  {:display     :inline-block
   :font-size   "var(--font-size-0)"
   :line-height "var(--font-lineheight-1)"
   :font-weight "var(--font-weight-5)"
   ;:margin-bottom "var(--size-2)"
   :color       "var(--red-6)"})


; region surface

(o/defstyled surface-instillinger :div
  :-m-4 :p-4
  {:background    "var(--floating)"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-1)"})

(o/defstyled surface-instillinger-closed :div
  :-m-4 :p-4                                                ;:div.-m-4.p-4
  {;:background     "var(--content)"
   ;:box-shadow    "var(--inner-shadow-1)"
   :-border-radius "var(--radius-1)"})

(o/defstyled surface-a :div
  :p-4
  {:background    "var(--toolbar)"
   :box-shadow    "var(--inner-shadow-2)"
   :border-radius "var(--radius-1)"})

(o/defstyled surface-b :div
  :p-4
  {:background    "var(--floating)"
   :box-shadow    "var(--inner-shadow-2)"
   :border-radius "var(--radius-1)"})

(o/defstyled surface-c :div
  ;:p-4
  [:&
   {:background    "var(--floating)"
    :box-shadow    "var(--inner-shadow-1)"
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

(o/defstyled surface-b-sans-bg :div
  :p-2
  {;:background "var(--surface1)"
   ;:box-shadow "var(--inner-shadow-1)"
   :border-radius "var(--radius-2)"})

(o/defstyled surface-d :div
  :p-2
  {:background    :#0001
   :box-shadow    "var(--inner-shadow-1),var(--shadow-1)"
   :border-radius "var(--radius-2)"})

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

(o/defstyled inline-icon :div
  :w-6 :h-6)

(o/defstyled icon-large :div
  :w-8 :h-8)

(o/defstyled icon-huge :div
  :w-10 :h-10)

;region rows and cols

(o/defstyled col :div
  :flex :flex-col)

(o/defstyled col-space-4 :div
  :flex :flex-col :space-y-4 :w-full)

(o/defstyled col-space-8 :div
  :flex :flex-col :space-y-8)

(o/defstyled col-space-16 :div
  :flex :flex-col :space-y-16)

(o/defstyled col-space-1 :div
  :flex :flex-col :space-y-1)

(o/defstyled col-space-2 :div
  :flex :flex-col :space-y-2)

(o/defstyled col-c-space-2 :div
  :flex :flex-col :items-center :space-y-2)

(o/defstyled col-fields :div
  :flex :flex-col :gap-2 :space-y-4)

(o/defstyled row-fields :div
  :flex :gap-2 :justify-between :shrink-1)

(o/defstyled row-wrap :div
  :flex :items-stretch :flex-wrap :gap-4)

(o/defstyled row-sc :div
  :flex :items-center :gap-4)

(o/defstyled row-sc-g1 :div
  :flex :items-center :gap-1)

(o/defstyled row-sc-g2 :div
  [:&
   {:gap         "0.5rem"
    :display     "flex"
    :align-items "center"}])

(o/defstyled row-sc-g3 :div
  {:gap         "1.5rem"
   :display     "flex"
   :align-items "start"})

(o/defstyled row-sc-g2-w :div
  {:gap         "1rem"
   :display     "flex"
   :align-items "center"
   :flex-wrap   "wrap"
   :whitespace  "normal"})

(o/defstyled row-sc-g4-w :div
  {:gap         "1rem"
   :display     "flex"
   :align-items "center"
   :flex-wrap   "wrap"})

(o/defstyled row-sba :div
  :flex :items-center :gap-1)

(o/defstyled row-ec :div
  :flex :items-center :justify-end :gap-3)

(o/defstyled row :div
  :flex :items-stretch)

(o/defstyled row-bl :div
  :items-baseline :gap-2
  :flex)

(o/defstyled row' :div
  :flex :items-center :h-full :gap-4 :w-full)

(o/defstyled row'' :div
  :flex :justify-start :h-full :w-full)

(o/defstyled row-center :div
  :flex-center :gap-4)

(o/defstyled row-std :div
  :shrink-0
  {:gap             "var(--size-3)"
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
  {:border-radius "var(--radius-1)"})

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
    :font-size       "var(--font-size-3)"
    :font-weight     "var(--font-weight-6)"
    :color           "var(--gray-9)"
    :letter-spacing  "var(--font-letterspacing-1)"}]
  [:div.normal {:background "var(--gray-1)"}]
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

(o/defstyled badge-2 :span
  [:& small-rounded :font-oswald
   {:cursor         :pointer
    :text-indent    :initial
    :font-size      "var(--font-size-2)"
    :font-weight    "var(--font-weight-5)"
    :padding-inline "var(--size-2)"
    :padding-block  "var(--size-1)"
    :display        :grid
    :place-content  :center
    :background     "var(--text0-copy)"
    :color          "var(--text0)"
    :letter-spacing "var(--font-letterspacing-1)"}
   [:&.active {:background "var(--yellow-7)"
               :color      "var(--gray-9)"}]
   [:&.big {:min-width "var(--size-8)"
            :font-size "var(--font-size-3)"}]
   [:&.small {:border-radius  "var(--radius-0)"
              :width          "var(--size-7)"
              :min-width      "var(--size-6)"
              :letter-spacing "var(--font-letterspacing-2)"
              :font-size      "var(--font-size-0)"}]])

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

(o/defstyled inner-dlg :div
  [:& :drop-shadow-2xl
   {:border-radius "var(--radius-2)"
    :box-shadow    "0px 0px 0px 2px var(--toolbar), 0px 0px 0px 6px var(--toolbar-)"}])

(o/defstyled dlg :div
  [:& :drop-shadow-2xl
   {:color            "var(--text1)"
    :background-color "var(--content)"
    :border-radius    "var(--radius-2)"}])

(o/defstyled centered-dialog dlg
  [:&
   {;:outline "4px solid red"
    :padding    "var(--size-5)"
    :overflow-y :auto}])

(o/defstyled dropdown-dialog dlg
  [:& :w-full
   {:padding     "var(--size-5)"
    :padding-top "var(--size-8)"}])

(o/defstyled dialog-selector' :div
  [:& :text-center
   {:-border           "4px solid var(--toolbar-)"
    :-border-radius    "var(--radius-2)"
    :box-shadow       #_"var(--shadow-6)"
    "0px 0px 0px 4px rgba(255,255,0,1),0px 0px 0px 8px rgba(0,0,0,1)"
    :color             "var(--text1)"
    :-background-color "red" #_"var(--toolbar)"}])

(o/defstyled dialog-title :div
  {:color       "var(--text2)"
   :font-family "Inter"
   :font-weight "var(--font-weight-5)"
   :font-size   "var(--font-size-3)"})

(o/defstyled dialog-title' :div
  {:color       "var(--text1)"
   ;:padding-block "var(--size-1)"
   :font-family "Inter"
   :font-weight "var(--font-weight-5)"
   :font-size   "var(--font-size-4)"})

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

;region markdown

(o/defstyled markdown' :div
  {:padding-block  "2rem"
   :padding-inline "1rem"
   :background     "var(--toolbar-)"
   :color          "var(--text3)"}
  [:&
   [:h2 ingress {:color "var(--text1)"}]
   [:p {:color       "var(--text1)"
        :line-height "var(--font-lineheight-4)"}]
   [:a link {:margin-left "-2px"}]])

(o/defstyled markdown :div
  [:* {:-webkit-font-smoothing   :auto
       :-moz-osx-font-smoothing  :auto
       :x-webkit-font-smoothing  :antialiased
       :x-moz-osx-font-smoothing :grayscale}]
  [#{:h1 :h2 :h3 :li :p.answer :p}
   [:code {:font-family    "unset"
           :color          "var(--textmarker)"
           :background     "var(--textmarker-background)"
           :padding-inline "var(--size-1)"
           :padding-block  "var(--size-1)"}]]
  [:pre
   {:overflow-x  :auto
    :line-height "var(--font-lineheight-1)"}
   [:code {:font-family "IBM Plex Mono"
           :font-size   "var(--font-size-1)"}]]

  [#{:p :ul :ol} {:color          "var(--text1)"
                  :font-size      "var(--font-size-1)"
                  :font-weight    "var(--font-weight-2)"
                  :font-family    "Merriweather"
                  :line-height    "var(--font-lineheight-4)"
                  :padding-bottom "var(--size-3)"}]
  [:a link]
  [#{:h1 :h2 :h3 :h5 :h6} {:color "var(--text2)"}]
  [#{:h3 :h4}
   {:font-weight "var(--font-weight-5)"
    :font-family "Inter"
    :color       "var(--text1)"
    :line-height "var(--font-lineheight-3)"
    :margin-top  "var(--size-3)"
    :font-size   "var(--font-size-2)"}]
  [#{:h1 :h2 :h5 :h6} {:font-family "Inter"}]
  [:h1 {:padding-top "var(--size-4)"
        :font-weight "var(--font-weight-1)"}]
  [:h1
   {:font-size     "var(--font-size-5)"
    :line-height   "var(--font-lineheight-0)"
    :font-weight   "var(--font-weight-2)"
    :margin-bottom "var(--size-3)"}


   [:a link]]
  [:h2
   {:font-size   "var(--font-size-3)"
    :line-height "var(--font-lineheight-4)"
    :font-weight "var(--font-weight-3)"}
   [:a link]]

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

    :padding-left  "1rem"
    :padding-right "3rem"}
   [:li {:list-style-type "none"
         :position        "relative"
         :padding-bottom  "var(--size-2)"}
    ["&::before" {:display     :inline
                  :color       "var(--text1)"
                  :left        "-1rem"
                  :font-weight "var(--font-weight-6)"

                  :position    "absolute"
                  :content     [:str "\u2022"]}]]]
  [:ol
   {:counter-reset   "li"
    :list-style-type "none"
    :padding-left    "2.3rem"}

   [:.answer {:padding-top    :8px
              :padding-bottom :none
              :margin-bottom  :none
              :margin-right   "var(--size-4)"
              :font-size      "var(--font-size-1)"
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
                             :top               "10px"
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

  [:div.qa
   [:li {:color "var(--brand1)"}]
   ["li p" {:margin-top    "var(--size-3)"
            :padding-right "var(--size-4)"
            :color         "var(--text1)"}
    ["&::before" {:content [:str "—"]}]]])

(o/defstyled thing-inside-shade :div
  ;todo redo these colors
  [:&

   {:user-select    :none
    :display        :flex
    :align-items    :center
    :min-height     "var(--size-5)"
    :padding-inline "var(--size-1)"
    :padding-block  "var(--size-0)"
    :border-radius  "var(--radius-round)"
    :background     "var(--content)"
    :color          "var(--text2)"}
   [:&:enabled:active {:background "var(--content)"}]
   [:&:focus-within {:background "var(--content)"
                     :color      "var(--text0)"}]
   [:&:enabled:hover {:background "var(--toolbar)"
                      :color      "var(--text1)"}]
   [:&:disabled {:opacity 0.5}]])

;region

(defn fp-summary-detail [tag header & content]
  [ingress-details
   {:style {:margin-left "calc(var(--size-2) * -1)"
            :color       "var(--text2)"}
    :open  (when tag @(schpaa.state/listen tag))}
   [hero-summary
    [fp-header {:on-click #(schpaa.state/toggle tag)
                :style    {:display       :inline-block
                           :margin-bottom "var(--size-2)"
                           :align-items   :start}} header]]
   [:div {:style {:margin-left   "var(--size-8)"
                  :margin-bottom "var(--size-6)"}}
    content]])

(defn fp-summary-detail-always-show-links [tag header content & links]
  [col-space-4
   [ingress-details
    {:style {:margin-left "calc(var(--size-2) * -1)"
             :color       "var(--text2)"}
     :open  (if tag @(schpaa.state/listen tag) 1)}
    [hero-summary
     [fp-header {:on-click #(schpaa.state/toggle tag)
                 :style    {:display     :inline-block
                            :align-items :start}} header]]
    [:div {:style {:margin-left "var(--size-4)"}}
     content]]
   [:div
    {:style {:margin-left   "12px"
             :margin-bottom (when links "var(--size-6)")}}
    links]])


(o/defstyled label-field-col :div
  [:& col :gap-2])

(o/defstyled bottom-toolbar-style :div
  {:display :none}
  [:at-media {:max-width "511px"}
   [:&
    {:border-bottom-left-radius  "var(--radius-2)"
     :border-buttom-right-radius "var(--radius-2)"
     :width                      "100%"
     :display                    :flex
     :justify-content            :space-around
     :height                     "var(--size-10)"
     :min-height                 "var(--size-10)"
     :padding-inline             "var(--size-2)"
     :box-shadow                 "var(--shadow-2)"
     :border-top                 "1px solid var(--toolbar-)"
     :background                 "var(--toolbar)"}]])

(o/defstyled toolbar-button :button
  [:& scb2/focus-button

   {:color         "var(--text2)"
    :border-radius "var(--radius-round)"
    :padding       "var(--size-2)"}
   [:&:disabled {:color "var(--text3)"}]
   [:&:enabled:hover {:color "var(--text1)"}]
   [:&.active scb2/focus-button
    {:color      "var(--text1)"
     :background "var(--content)"
     :box-shadow "var(--shadow-1)"}]
   [:&.oversikt scb2/focus-button {:color      "var(--gray-0)"
                                   :background "var(--brand1)"
                                   :box-shadow "var(--shadow-1)"}
    [:&:enabled:hover {:color "var(--gray-1)"}]]

   [:&.special {:color "var(--brand1)"}]])

(o/defstyled top-left-badge :div
  [:&
   {:left                    "50%"
    :transform               "translate(-50%,0)"
    :bottom                  "-12px"
    :display                 :flex
    :justify-content         :center
    :align-items             :center
    :border-top-left-radius  "var(--radius-2)"
    :border-top-right-radius "var(--radius-2)"
    :font-size               "var(--font-size-1)"
    :font-weight             "var(--font-weight-5)"
    ;:aspect-ratio    "1/1"
    :background              "black" #_"var(--brand1)"
    :color                   "white"                        ;var(--text0)"
    :width                   "auto"
    :padding-inline          "var(--size-2)"
    :padding-block           "var(--size-1)"
    :position                :absolute}]
  )

(o/defstyled top-right-tight-badge :div
  :rounded-full :flex :flex-center
  [:& :top-0 :right-4 {:font-size    "var(--font-size-0)"
                       :font-weight  "var(--font-weight-5)"
                       :aspect-ratio "1/1"
                       :background   "var(--brand1)"
                       :color        "var(--brand1-copy)"
                       :border       "var(--surface0) 3px solid"
                       :width        "1.7rem"
                       :height       "1.7rem"
                       :position     :absolute}]

  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))