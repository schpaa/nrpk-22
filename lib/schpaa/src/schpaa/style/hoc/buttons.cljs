(ns schpaa.style.hoc.buttons
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.input]
            [schpaa.style.hoc.toggles]))

;buttons
(def regular schpaa.style.button2/normal-regular)
(def cta schpaa.style.button2/cta-regular)
(def cta-outline schpaa.style.button2/cta-outline)
(def danger schpaa.style.button2/danger-regular)
(def round schpaa.style.button2/round)
(def round' schpaa.style.button2/round')

;buttons in controlpanels (small pill-styled)
(def pill schpaa.style.hoc.toggles/tiny-button-reg)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;todo in time, find a better namespace, role: composites
(defn icon-with-caption [icon caption]
  [:div.inline-flex.items-center
   {:class (when icon :gap-2)}
   (if icon [:span.w-5.h-5.relative icon]
            [:span.h-5.relative])
   caption])

(defn caption [caption']
  [sc/row-sc-g2
   [sc/icon-tiny nil]
   caption'])

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
(defn outline-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :line-height 1}
                 :class [:padded :outlined]}
                attr)
    (icon-with-caption nil caption)))

(defn reg-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         ;:gap         "1rem"
                         :line-height 1}

                 :class [:regular :padded]}
                attr)
    ;(update attr :class (fnil conj []) :regular :padded)
    (icon-with-caption nil caption)))

(defn danger-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         ;:gap         "1rem"
                         :line-height 1}

                 :class [:padded :danger]}
                attr)
    ;(update attr :class (fnil conj []) :regular :padded)
    (icon-with-caption nil caption)))

(defn cta-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         ;:gap         "1rem"
                         :line-height 1}

                 :class [:padded :cta]}
                attr)
    ;(update attr :class (fnil conj []) :regular :padded)
    (icon-with-caption nil caption)))

(defn reg-pill-icon [attr icon caption]
  (pill
    ;(update attr :class (fnil conj []) :regular :pad-right)
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         ;:gap         "1rem"
                         :line-height 1
                         :-outline    "1px red solid"}
                 :class [:regular :padded]}
                attr)
    (icon-with-caption icon caption)))

(defn reg-icon [attr icon]
  (pill
    ;(update attr :class (fnil conj []) :regular :pad-right)
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         ;:gap         "1rem"
                         :line-height 1
                         :-outline    "1px red solid"}
                 :class [:justicon]}
                attr)
    (sc/icon-small icon)))

(comment
  (let [attr {:class [:zzz]}]
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :gap         "1rem"
                         :line-height 1
                         :-outline    "1px red solid"}
                 :class [:regular :pad-right]}
                attr)))

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
