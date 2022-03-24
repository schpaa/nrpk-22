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
(def regular-pill schpaa.style.hoc.toggles/tiny-button-reg)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;todo in time, find a better namespace, role: composites
(defn icon-with-caption [icon caption]
  [sc/row-sc-g2
   [sc/icon-small icon]
   caption])

