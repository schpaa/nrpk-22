(ns schpaa.style.hoc.buttons
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.input]
            [schpaa.style.hoc.toggles]))

;buttons
(def regular schpaa.style.button2/normal-regular)
(def cta schpaa.style.button2/cta-regular)
(def danger schpaa.style.button2/danger-regular)
(def round schpaa.style.button2/round)

;buttons in controlpanels (small pill-styled)
(def cta-pill schpaa.style.hoc.toggles/tiny-button-cta)
(def pill schpaa.style.hoc.toggles/tiny-button-reg)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;todo in time, find a better namespace, role: composites
(defn icon-with-caption [icon caption]
  [sc/row-sc-g2
   [sc/icon-small icon]
   caption])

(defn icon-with-caption-and-badge [icon caption badge]
  [sc/row-sc-g2 {:style {:position :relative}}
   [sc/icon-small icon]
   caption
   [:div {:style {:border-radius  "var(--radius-round)"
                  :background     "black"
                  :color          :white
                  :padding-inline "var(--size-2)"
                  :height         "100%"}}
    badge]])

;shortcuts
(defn reg-pill [attr caption]
  (pill
    (update attr :class (fnil conj []) :regular :padded)
    caption))

(defn reg-pill-icon [attr icon caption]
  (pill
    (update attr :class (fnil conj []) :regular :pad-right)
    (icon-with-caption icon caption)))

(defn regoutline-pill-icon [attr icon caption]
  (pill
    (update attr :class (fnil conj []) :outlined :regular :padded)
    (icon-with-caption icon caption)))

(defn cta-pill-icon [attr icon caption]
  (pill
    (update attr :class (fnil conj []) :cta :pad-right)
    (icon-with-caption icon caption)))

(comment
  (do
    (reg-pill-icon {:class [:test]} :a :b)))
