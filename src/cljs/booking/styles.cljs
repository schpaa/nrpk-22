(ns booking.styles
  (:require [lambdaisland.ornament :as o]))

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
    {:-filter "contrast(0.98) brightness(0.75) "}]          ;saturate(.5)
   [:&.light
    {:-filter "brightness(1)"}]])

(o/defstyled scroll-up-animation :div
  [:& {:display         :flex
       :justify-content :center
       :align-items     :center
       :padding-block   "var(--size-2)"
       :color           "var(--text0)"}
   ;:animation-timing-function "var(--ease-iout-4)"
   [:&.keepgoing
    {:animation "2000ms var(--animation-float)"}]
   [:&.fadeaway
    {:animation "2000ms var(--animation-fade-out) forwards, var(--animation-scale-down) forwards"}]])

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


