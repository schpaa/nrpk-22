(ns schpaa.style.hoc.toggles
  "small things in the control panel on top of every page"
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [schpaa.style.switch]
            [schpaa.style.button2 :as scb2]))

(o/defstyled tiny-button :button
  {:border-radius  "var(--radius-round)"
   :white-space    :nowrap
   :min-height     "var(--size-7)"
   :font-size      "var(--font-size-0)"
   :min-width      "6rem"
   :padding-inline "var(--size-4)"}
  [:&:disabled {:opacity 0.3}]

  ([attr & ch]
   ^{:disabled (:disabled attr)
     :on-click (:on-click attr)}
   [:<> [:div ch]]))

(o/defstyled tiny-button-cta tiny-button
  [:& scb2/cta-color])

(o/defstyled tiny-button-reg tiny-button
  [:& scb2/reg-color])

(defn button-reg
  ([action caption]
   (button-reg action caption nil))
  ([action caption {:keys [disabled]}]
   (tiny-button-reg {:disabled disabled
                     :on-click action} caption)))

(defn button-cta [action caption]
  (tiny-button-cta {:on-click action} caption))

(defn switch [tag caption]
  [schpaa.style.switch/small-switch
   {:tag     tag
    :caption (fn [] [:div {:style {:font-size "var(--font-size-0)"}} caption])}])

;todo refactor this into a function, like switch
(o/defstyled twostate :button
  [:& sc/thing-inside-shade]
  {:font-size "var(--font-size-0)"}
  ([{:keys [class on-click alternate icon caption] :as attr} & ch]
   ^{:on-click on-click
     :class    class}
   [:<>
    [sc/row-sc-g2
     (when icon
       [sc/icon-small {:class (if alternate [] [])} (icon alternate)])
     (when caption
       [:div (caption alternate)])]]))