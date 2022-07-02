(ns booking.styles
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.button2 :as scb2]))

(o/defstyled logothing :div
  :h-full :w-full
  [:&
   {:position      :relative
    :box-shadow    "var(--shadow-1)"
    ;:height        "var(--size-fluid-7)"
    ;:width         "var(--size-fluid-7)"
    :overflow      :hidden
    :border-radius "var(--radius-round)"}
   [:&.dark
    {:filter "contrast(0.98) brightness(0.75) "}]           ;saturate(.5)
   [:&.light
    {:filter "brightness(1)"}]])

(o/defstyled scroll-up-animation :div
  [:& {:display         :flex
       :justify-content :center
       :align-items     :center
       :padding-block   "var(--size-2)"
       :color           "var(--text0)"}
   ;:animation-timing-function "var(--ease-iout-4)"
   [:&.keepgoing
    {:animation "var(--animation-float)"}]
   [:&.fadeaway
    {:animation "1s var(--animation-fade-out) forwards"}]])

(o/defstyled logo-text :div
  {:color "var(--text0)"})

(o/defstyled centered :div
  :flex-center)

(o/defstyled attached-to-bottom :div
  ;:flex :justify-end :w-full
  [:&
   {:position       :fixed
    :pointer-events :none
    :bottom         "0rem"
    :left           "0rem"
    :right          "4rem"}
   [:at-media {:max-width "511px"}
    [:&.bottom-toolbar
     {:bottom "5rem"
      :right  "0rem"}]]])

(o/defstyled popup-frame :div
  [:& :pointer-events-auto
   {:margin-inline    "auto"
    :height           "auto"
    :border-radius    "var(--radius-0)"
    :width            "25rem"
    :box-shadow
    "0 0 0 10px var(--content),
     0 0 0 18px var(--toolbar),
     0 0 0 22px var(--floating),
     var(--shadow-6)"
    :background-color "var(--content)"}])

(o/defstyled timenav :div
  [:input
   ;sc/small-rounded
   scb2/focus-button
   {:border         "2px solid var(--text2)"
    :height         "2rem"

    :xpadding-block "0.25rem"
    :padding-inline "0.25rem"}
   [:focus]])

(o/defstyled ro-jb :div
  :flex :justify-between :items-baseline :w-full :h-full
  {})

(o/defstyled debug1 :div
  {:background "rgba(255 0  0 / 0.01)"})

(o/defstyled debug2 :div
  {:background "rgba(0 155 0 / 0.1)"})

(o/defstyled debug3 :div
  {:background "rgba(0 0 155 / 0.04)"})

(o/defstyled co0 :div
  [:& :flex :flex-col :justify-start :items-start :xh-full
   debug2
   [:&.frame {:border "1px solid black"}]])

(o/defstyled co co0
  debug2
  :space-y-4 :xw-full)

(o/defstyled co1 co0
  :space-y-1)

(o/defstyled co2 co0
  :space-y-2)

(o/defstyled col :div
  debug3
  :flex :space-y-8 :flex-col :justify-start :items-stretch)

(o/defstyled ro :div
  :flex :justify-start :items-center :gap-2 :h-full debug1)

(o/defstyled ro1 :div
  :flex :justify-start :items-center :gap-1 :h-full debug1)

(o/defstyled roe ro
  :justify-end :w-full debug1)

(o/defstyled rof :div
  ro :h-full :w-full)

(o/defstyled ro-js :div
  :flex :justify-start :items-baseline :gap-2
  {})

(o/defstyled ro-js-ie :div
  :flex :justify-start :items-end :gap-2
  {})

(o/defstyled ro-js-is :div
  :flex :justify-start :items-start :gap-2
  {})

(o/defstyled title :div
  [:&
   {:font-size      "var(--font-size-3)"
    :line-height    1.2
    :color          "var(--text1)"
    :letter-spacing "var(--font-letterspacing-0)"
    :font-weight    "var(--font-weight-5)"}])

(o/defstyled text-truncate text
  [:& :truncate
   {:line-height 1}])

(o/defstyled text :div
  [:& :whitespace-nowrap
   {:line-height 1.2
    :font-size   "var(--font-size-1)"
    :color       "var(--text0)"}
   [:&.bold {:font-weight "700"}]])

(o/defstyled small :div
  [:&
   {:letter-spacing "0.05rem"
    :padding-inline "0.25rem"
    :text-transform "uppercase"
    :line-height    1
    :font-size      "var(--font-size-0)"
    :font-weight    "var(--font-weight-4)"}
   [:&.error {:color "red"}]])

(o/defstyled dim :div
  [:&
   {;:letter-spacing "0.05rem"
    ;:text-transform "uppercase"
    :color       "var(--text2)"
    :line-height 1.1}
   ;:font-size      "var(--font-size-0)"
   ;:font-weight    "var(--font-weight-4)"}
   [:&.error {:color "red"}]])

(o/defstyled phone-link :a
  [:&])

(o/defstyled screen :div
  [:&
   :h-auto
   :overflow-hidden
   {:font-family      "Sans-serif"
    :background-color "rgba(0,0,0,0.1)"
    :xwidth           "50ch"}])

(o/defstyled surface :div
  [:& :p-4 :select-none :h-full
   {:box-shadow    "var(--shadow-inner-1)"
    ;:border "2px solid var(--text2)"
    :border-radius "var(--radius-0)"}
   [:&.floating
    {:background-color "var(--floating)"}]
   [:&.content
    {:background-color "var(--content)"}]
   [:&.emboss
    {:background-color "var(--floating-)" #_"rgba(255,255,255,1)"}]])

(o/defstyled big-rounded :div
  {:border-radius "var(--radius-2)"})

(o/defstyled keyword-tag :div
  [:& big-rounded :inline-block :h-auto]
  {:display        :flex
   :align-items    :center
   :font-size      "var(--font-size-0)"
   :font-weight    "var(--font-weight-4)"
   :padding-inline "var(--size-3)"
   :padding-block  "var(--size-1)"
   :background     "var(--content)"
   :color          "var(--text0)"
   :text-transform "uppercase"
   :letter-spacing "var(--font-letterspacing-2)"})

(o/defstyled center :div
  [:& :flex-center :h-full :w-full])

(o/defstyled ddaycontainer :div
  [:& {:background-color "var(--toolbar-)"}
   [:&:active
    {:background-color "var(--toolbar"}]])