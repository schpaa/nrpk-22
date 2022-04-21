(ns schpaa.style.hoc.toggles
  "small things in the control panel on top of every page"
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [schpaa.style.switch]
            [schpaa.style.button2 :as scb2]
            [re-frame.core :as rf]))

(o/defstyled tiny-button :div                               ;button
  [:& scb2/focus-button
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
         ch]]))

(comment
  (let [appearance #{:blue :narrow}]
    (cond-> (if (seq appearance) [] [:padded])
      (some #{:blue} appearance) (conj :blue)
      (some #{:narrow} appearance) (conj :narrow))))

(o/defstyled tiny-button-cta :button
  [:& tiny-button scb2/focus-button
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

(o/defstyled tiny-button-reg :button
  ;[:& :h-auto]
  [:& :p-0 :m-0 :gap-2
   {:display     :inline-flex
    :align-items :center
    :height      "var(--size-7)"}
   tiny-button
   [:&.cta
    {:background-color "var(--switchon)"
     :color            "var(--brand1-copy)"}
    [:&:enabled:hover {:background-color "var(--brand1-bright)"}]
    [:&:enabled:active {:background-color "var(--switchon)"}]
    [:&:disabled {:opacity 0.5}]]

   [:&.regular
    {:background-color "var(--button-bright)"
     :color            "var(--buttoncopy)"}
    [:&:enabled:hover {:background-color "var(--button-bright)"
                       :-box-shadow      "var(--shadow-3)"}]
    [:&:enabled:active {:transition-duration "100ms"
                        :-box-shadow         "var(--inner-shadow-3)"}]
    [:&:disabled {:opacity 0.5 :-box-shadow "var(--shadow-2)"}]]

   [:&.danger {:background-color "var(--danger-bright)"
               :color            "var(--dangercopy)"}
    [:&:enabled:hover {:background-color "var(--danger)"}]
    [:&:enabled:active {:background-color "var(--danger-bright)"}]
    [:&:disabled {:opacity 0.5}]]
   [:&.outlined
    {:background-color           "var(--toolbar)"
     :box-shadow                 "0"
     :color                      "var(--floating)"
     :border                     "none"
     :border-bottom-left-radius  "0"
     :border-bottom-right-radius "0"
     :border-top-left-radius     "var(--radius-2)"
     :border-top-right-radius    "var(--radius-2)"}]
   [:&.active
    {:background-color "var(--floating)"
     :color            "var(--text1)"}]

   [:&.padded {
               ;:align-items     :center
               ;:justify-content :center
               :min-width      "6rem"
               :padding-inline "var(--size-3)"}]

   [:&.pad-right {:min-width     "6rem"
                  :padding-left  "var(--size-2)"
                  :padding-right "var(--size-3)"}]
   [:&.justicon {:min-width      0
                 :padding-inline "var(--size-1)"}]
   [:&.narrow {:min-width      0
               :padding-inline "var(--size-3)"}]]
  #_([{:keys [class disabled] :as attr} & args]
     [:div attr args]))

(defn button-reg
  ([action caption]
   (button-reg action caption nil))
  ([action caption {:keys [disabled]}]
   (tiny-button-reg {:disabled disabled
                     :on-click action} caption)))

(defn button-cta [action caption]
  (tiny-button-cta {:on-click action} caption))

(defn switch-local "something that alters localstorage setting"
  ([atm caption]
   [switch-local {:disabled false} atm caption])
  ([attr atm caption]
   [schpaa.style.switch/small-switch-base
    attr
    {:get-value atm
     :set-value #(reset! atm %)
     :caption   (fn [] [:div {:style {:text-transform :uppercase
                                      :letter-spacing "var(--font-letterspacing-2)"
                                      :font-size      "var(--font-size-0)"}} caption])}]))

(defn largeswitch-local
  ;todo
  "used for checkin and checkout of boats, might need a redesign (colors mainly)"
  [{:keys [atoma view-fn caption]}]
  [schpaa.style.switch/large-switch-checkinout
   {:view-fn (or view-fn (fn [t c] [:div t c]))
    :caption caption
    :get     atoma
    :set     #(reset! atoma %)}])

(defn switch "something that alters localstorage setting" [tag caption]
  [schpaa.style.switch/small-switch
   {:tag     tag
    :caption (fn [] [:div {:style {:font-size "var(--font-size-0)"}} caption])}])

(defn local-toggle [tag caption view-fn]
  [schpaa.style.switch/large-switch
   {:view-fn view-fn
    :tag     tag
    :get     (rf/subscribe [tag])
    :set     #(rf/dispatch [tag %])
    :caption (fn [] [:div {:style {:font-size "var(--font-size-2)"}} caption])}])

(defn stored-toggle [tag caption view-fn]
  [schpaa.style.switch/large-switch
   {:view-fn view-fn
    :tag     tag
    ;:get     (rf/subscribe [tag])
    ;:set     #(rf/dispatch [tag %])
    :caption (fn [] [:div {:style {:font-size "var(--font-size-2)"}} caption])}])

(defn dark-light-toggle [tag caption view-fn]
  [schpaa.style.switch/dark-light-switch
   {:view-fn view-fn
    :tag     tag
    :caption (fn [] [:div {:style {:font-size "var(--font-size-2)"}} caption])}])

(defn small-switch-base [attr caption get-value set-value]
  [schpaa.style.switch/small-switch-base attr
   {:get-value get-value
    :set-value set-value
    :caption   (fn [] [:div {:style {:font-size "var(--font-size-0)"}} caption])}])

(defn small-switch-base2 [attr caption get-value set-value]
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