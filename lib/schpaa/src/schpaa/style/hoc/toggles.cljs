(ns schpaa.style.hoc.toggles
  "small things in the control panel on top of every page"
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [schpaa.style.switch]
            [schpaa.style.button2 :as scb2]))

(o/defstyled tiny-button :button
  [:&
   {:border-radius "var(--radius-round)"
    :white-space   :nowrap
    :font-size     "var(--font-size-0)"
    :min-height    "var(--size-6)"
    :font-weight   "var(--font-weight-6)"}

   [:&:disabled {:opacity 0.3}]]

  #_([attr & ch]
     ^{:disabled (:disabled attr)
       :class    [:narrow]
       :on-click (:on-click attr)}
     [:<> [:div.narrow {:class [:narrow]} "X" ch]]))

(defn common
  ([{:keys [appearance] :as attr} & ch]
   ^{:disabled (:disabled attr)
     :on-click (:on-click attr)}
   [:<> [(cond
           (some #{:cta} appearance) scb2/color-cta
           (some #{:danger} appearance) scb2/color-danger
           (some #{:blue} appearance) scb2/color-danger
           :else scb2/color-regular)
         {:class [:padded (some #{:pad-right} appearance) (some #{:narrow} appearance)]}
         #_{:class (cond-> (if (seq appearance) [] [:padded])
                     (some #{:blue} appearance) (conj :blue)
                     (some #{:narrow} appearance) (conj :narrow))} ch]]))

(comment
  (let [appearance #{:blue :narrow}]
    (cond-> (if (seq appearance) [] [:padded])
      (some #{:blue} appearance) (conj :blue)
      (some #{:narrow} appearance) (conj :narrow))))

(o/defstyled tiny-button-cta :button
  [:& tiny-button
   ;scb2/color-cta
   ;[:.padded]
   {:border-radius "var(--radius-round)"
    :white-space   :nowrap
    :font-size     "var(--font-size-0)"
    :min-height    "var(--size-6)"
    :font-weight   "var(--font-weight-6)"}
   [:.padded {:border-radius   "var(--radius-round)"
              :min-height      "var(--size-6)"
              :display         :flex
              :align-items     :center
              :justify-content :center
              :min-width       "6rem"
              :padding-inline  "var(--size-3)"}]

   {:min-width      "6rem"
    :padding-inline "var(--size-3)"}
   [:.narrow {:padding-inline "var(--size-2)"}]]
  ([attr & args]
   (apply common (cons (update attr :appearance (fnil conj #{}) :cta) args))))

(o/defstyled tiny-button-reg :div
  ;{:border-radius "var(--radius-round)"}
  ;[:&:enabled:active {:background-color "orange" #_"var(--brand1-bright)"}]
  ;
  ;[:.small {:font-size "var(--font-size-0)"}]

  [:&
   ;{:border "2px solid transparent"}
   [:>.cta tiny-button {:background-color "var(--brand1-bright)"
                        :color            "var(--brand1copy)"}
    [:&:enabled:hover {:background-color "var(--brand1)"}]
    [:&:enabled:active {:background-color "var(--brand1-bright)"}]
    [:&:disabled {:opacity 0.5}]]
   [:>.danger tiny-button {:background-color "var(--danger-bright)"
                           :color            "var(--dangercopy)"}
    [:&:enabled:hover {:background-color "var(--danger)"}]
    [:&:enabled:active {:background-color "var(--danger-bright)"}]
    [:&:disabled {:opacity 0.5}]]

   [:>.regular tiny-button {:transition-duration "200ms"
                            :background-color    "var(--button-bright)"
                            :color               "var(--buttoncopy)"}
    [:&:enabled:hover {
                       :background-color "var(--button-bright)"
                       :box-shadow       "var(--shadow-3)"}]
    [:&:enabled:active {:transition-duration "100ms"
                        :-background-color   "var(--button-bright)"
                        :box-shadow          "var(--inner-shadow-3)"}]
    [:&:disabled {:opacity 0.5 :-box-shadow "var(--shadow-2)"}]]

   [:>.outlined
    {;:border           "1px dashed #0004"
     :box-shadow       "var(--inner-shadow-3), var(--shadow-3)"
     :color            "var(--brand1copy)"
     :background-color "var(--brand1)"}
    [:&:enabled:hover {:color            "var(--brand1copy)"
                       :background-color "var(--brand1)"
                       :-box-shadow      "var(--shadow-6)"}]]]



  [:.padded {:min-height      "var(--size-6)"
             :display         :flex
             :align-items     :center
             :justify-content :center
             :min-width       "6rem"
             :padding-inline  "var(--size-3)"}]

  [:.pad-right {:min-width     "6rem"
                :padding-left  "var(--size-2)"
                :padding-right "var(--size-3)"}]
  [:.narrow {:min-width      0
             :padding-inline "var(--size-3)"}]


  ;[:.blue {:background :blue}]
  ([{:keys [class disabled] :as attr} & args]
   ;^{:disabled disabled}

   [:button attr #_{:class class :disabled disabled :on-click on-click} args]))


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

(defn small-switch-base [attr caption get-value set-value]
  [schpaa.style.switch/small-switch-base attr
   {:get-value get-value
    :set-value set-value
    :caption   (fn [] [:div {:style {:font-size "var(--font-size-0)"}} caption])}])

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