(ns schpaa.style.hoc.buttons
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.input]))

;buttons
(def regular schpaa.style.button2/normal-regular)
(def cta schpaa.style.button2/cta-regular)
(def danger schpaa.style.button2/danger-regular)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;todo in time, find a better namespace, role: composites
(defn icon-with-caption [icon caption]
  [sc/row-sc-g2
   [sc/icon-small icon]
   [sc/button-caption caption]])

