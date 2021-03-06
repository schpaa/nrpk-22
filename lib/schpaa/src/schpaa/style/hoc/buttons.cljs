(ns schpaa.style.hoc.buttons
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.input]
            [schpaa.style.button2]
            [schpaa.style.hoc.toggles]
            [re-frame.core :as rf]
            [schpaa.style.input :as sci]
            [schpaa.debug :as l]
            [booking.styles :as b]))

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
(def pill schpaa.style.hoc.toggles/base-button)

;checkbox
(def checkbox schpaa.style.input/checkbox)

;inputfields
(defn textinput [{:keys [cursor class] :as attr} label field]
  (cond
    cursor
    (sci/input attr (:type attr :text) {:class (conj class :on-bright :w-32)} label field)
    :else
    (sci/input attr (:type attr :text) {:class (conj class :on-bright)} label field)))

(defn dateinput [{:keys [class] :as attr} label field]
  (sci/input attr (:type attr :date) {:class (conj class :on-bright :w-40)} label field))

(defn timeinput [{:keys [class] :as attr} label field]
  (sci/input attr (:type attr :time) {:class (conj class :on-bright :w-24)} label field))

;todo in time, find a better namespace, role: composites
(defn icon-and-caption
  ([icon caption]
   (icon-and-caption {} icon caption))
  ([attr icon caption]
   (let [flipped? (= :flip (some #{:flip} (:class attr)))
         large? (some #{:large} (:class attr))]
     (pill
       (merge-with into
                   {:style {:display     "flex"
                            :align-items "baseline"
                            ;:outline "1px solid red"
                            :flex-shrink 0
                            :line-height 1}
                    :class (concat
                             (if caption [:pad-right :pad-left] (if icon [:xpad-right]))
                             [:h-full (when-not large? :uppercase) :tracking-wide])}
                   attr)

       (if (or icon caption)
         (let [icon (when icon
                      (if large?
                        [:div.w-8 icon]
                        [:div.w-4 icon]))
               caption [(if large? b/text b/small)
                        {:class [(when large? [:truncate])]
                         :style (when large? {:width "100%"})}
                        caption]
               attr {:class [:flex
                             (if large? :gap-2 :gap-1)]
                     :style {:justify-content :space-between
                             :align-items     :center}}]
           (into [:div attr]
                 ((if-not flipped? reverse identity) [caption icon]))))))))


(defn just-caption
  ([attr caption]
   (pill
     (merge-with into
                 {:style {;:xpadding-top "0.125rem"
                          :line-height 1}
                  :class [:uppercase :narrow]}
                 attr)
     [:div.truncate caption])))

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
  (let [large? (some->> attr :class (some #{:large}))]
    (if large?
      [:div {:class [:w-8]} [sc/icon-large icon]]
      [:div {:class [:w-5]} [sc/icon-small icon]]))
  #_(if (some #{:large} (:class attr))
      [sc/icon-large icon]
      [sc/icon-small icon])
  ;:outline      "1px solid red"
  #_[:div.w-4.h-4 icon]
  #_(pill
      (merge-with into
                  {:class [:shrink-1s :round]
                   :style {:position     :relative
                           :saspect-ratio "1/1"}}
                  attr)
      (if (some #{:large} (:class attr))
        [sc/icon-large icon]
        [sc/icon-small icon])))

(defn just-large-icon [attr icon]
  (pill
    (merge-with into
                {:style {:display      :flex
                         :align-items  :center
                         :xline-height 1}
                 :class [:large]}
                attr)
    [sc/icon-large icon]))

;often used
(defn ^:deprecatedx reg-pill-icon
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

(defn combobox [label]
  [sc/col
   [sc/label label]
   [sci/combobox-example {:class [:on-bright]}]])