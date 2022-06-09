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
    {:filter "contrast(0.98) brightness(0.75) "}]          ;saturate(.5)
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
  :flex :justify-between :items-baseline
  {})

(o/defstyled ro :div                
  :flex :justify-start :items-center :gap-2)


(o/defstyled ro-js :div
  :flex :justify-start :items-baseline :gap-2
  {})

(o/defstyled text-truncate :div
  :truncate
  {:line-height 1})

(o/defstyled text :div 
  [:& :debug
   {:line-height 1
    ;:height "100%"
    :color "var(--text1)"}])

(o/defstyled title :div
  [:&
   {:font-size      "var(--font-size-3)"
    :letter-spacing "var(--font-letterspacing-0)"
    :font-weight    "var(--font-weight-5)"}])

(o/defstyled small :div
  [:&
   {:letter-spacing "0.02rem"
    :font-size      "var(--font-size-0)"
    :opacity        0.7
    :font-weight    "var(--font-weight-3)"}
   [:&.error {:color "red"}]])