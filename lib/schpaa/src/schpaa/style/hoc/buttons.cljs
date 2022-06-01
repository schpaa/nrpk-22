(ns schpaa.style.hoc.buttons
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.input]
            [schpaa.style.button2]
            [schpaa.style.hoc.toggles]
            [re-frame.core :as rf]
            [schpaa.style.input :as sci]))

;buttons
(def regular schpaa.style.button2/normal-regular)
;(def panelbuttonz schpaa.style.button2/panelbutton)
(def attn schpaa.style.button2/attn-regular)
(def cta schpaa.style.button2/cta-regular)
(def cta-outline schpaa.style.button2/cta-outline)
(def danger schpaa.style.button2/danger-regular)
(def round schpaa.style.button2/round)
(def round' schpaa.style.button2/round')

;buttons in controlpanels (small pill-styled)
(def pill schpaa.style.hoc.toggles/tiny-button-reg)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;inputfields
(defn textinput [{:keys [class] :as attr} label field]
  (cond
    (:cursor attr)
    (sci/input attr :text {:class (conj class :on-bright :w-32)} label field)
    :else
    (sci/input {} :text {:class (conj class :on-bright)} label field)))

;todo in time, find a better namespace, role: composites
(defn icon-and-caption
  ([icon caption]
   (icon-and-caption {} icon caption))
  ([attr icon caption]
   (pill
     (merge-with into
                 {:style {:width       "auto"
                          :display     "flex"
                          :align-items "center"
                          :line-height 1}
                  :class [ :h-full
                          :pad-right
                          ;(if-not caption :round (if (string? icon) :pad-left :pad-right))
                          ;(when caption :wide)
                          :uppercase
                          :tracking-wider]}
                 attr)

     (if (and icon caption)
       [:div {:class [:flex :w-full :shrink-0 :gap-1]
              :style {:justify-content :space-between
                      :align-items     :center}}

        (if (some #{:large} (:class attr))
          [sc/icon-large icon]
          [sc/icon-small icon])
        [:div.mt-px.truncate.grow.x-debug.self-justify-startx.justify-self-startx.self-startx.w-full.text-left
         {:style {:width   "100%"
                  :-flex    "1 0 100%"}} caption]]))))


(defn just-caption
  ([attr caption]
   (pill
     (merge-with into
                 {:style  {;:display     :flex
                           ;:align-items :center
                           :line-height 1}
                  :class  [:w-fullx :h-full :uppercase :centerx]
                  :sclass [:uppercase :xpad-left :xtracking-wider :narrow :center]}
                 attr)
     [:div.mt-px caption])))

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

(defn just-icon [attr icon]
  (pill
    (merge-with into
                {:class [:shrink-0 :round]
                 :style {}}
                attr)
    (if (->> attr :class (some #{:large}))
      [sc/icon-large icon]
      [sc/icon-small icon])))

(defn just-large-icon [attr icon]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :line-height 1}
                 :class [:large]}
                attr)
    [sc/icon-large icon]))

(defn round-pill [attr caption]
  (pill
    (merge-with into
                {:type  "button"
                 :style {:display     :flex

                         :align-items :center
                         :line-height 1}
                 :class []}
                attr)
    caption))

(defn round-danger-pill [attr caption]
  (pill
    (merge-with into
                {:type  :button
                 :style {:display     :flex
                         :align-items :center
                         :line-height 1}
                 :class [:round :danger]}
                attr)
    (icon-and-caption nil caption)))

(defn round-cta-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :line-height 1}
                 :class [:round :cta :shrink-0]}
                attr)
    (icon-and-caption nil caption)))

(defn cta-pill [attr caption]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :line-height 1}

                 :class [:padded :cta]}
                attr)
    (icon-and-caption nil caption)))

(defn reg-icon [attr icon]
  (pill
    (merge-with into
                {:style {:display     :flex
                         :align-items :center
                         :line-height 1}
                 :class [:round]}
                attr)
    (sc/icon-small icon)))

;often used
(defn reg-pill-icon
  ([attr icon]
   (reg-pill-icon attr icon nil))
  ([attr icon caption]
   (let [{:keys [always-wide]} (:class attr)
         {:keys [mobile?]} @(rf/subscribe [:lab/screen-geometry])]
     (pill
       (merge-with into
                   {:style {:display        :flex
                            :text-transform :uppercase
                            :align-items    :center
                            :line-height    1}
                    :class [:shrink-0 (if always-wide :pad-right (if mobile? :round :pad-right))]}
                   attr)
       (icon-and-caption
         {}
         icon
         (if always-wide
           caption
           (when-not mobile? caption)))))))


(defn pill-icon-caption [attr icon caption]
  (pill
    (merge-with into
                {:style {:display        :flex
                         :text-transform :uppercase
                         :align-items    :center
                         :line-height    1}
                 :class [:pad-right]}
                attr)
    (icon-and-caption icon caption)))

(defn regoutline-pill-icon [attr icon caption]
  (pill
    (update attr :class (fnil conj []) :outlined :regular :padded)
    (icon-and-caption icon caption)))

(defn cta-pill-icon [attr icon caption]
  (pill
    (update attr :class (fnil conj []) :cta :pad-right)
    (icon-and-caption icon caption)))
