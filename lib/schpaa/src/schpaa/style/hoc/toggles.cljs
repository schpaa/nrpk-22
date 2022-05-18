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
   [:&:disabled {:opacity 0.3}]])

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
  [:& :p-0 :m-0 :gap-2
   {:display     :inline-flex
    :align-items :center
    :height      "var(--size-7)"
    :font-weight "var(--font-weight-7)"
    :color       "var(--text)"}

   tiny-button

   [:&.inverse
    {:background-color "var(--text0) "
     :color            "var(--text0-copy)"}]

   [:&.message
    {:background-color "var(--yellow-5)"
     :color            "var(--gray-9)"}]

   [:&.cta
    {:background-color "var(--brand1)"
     :color            "var(--brand1-copy)"}
    [:&:enabled:hover {:background-color "var(--brand1-bright)"}]
    [:&:enabled:active {:background-color "var(--switchon)"}]
    [:&:disabled {:opacity 0.5}]]

   [:&.shadow {:box-shadow "var(--shadow-2),var(--shadow-1)"}]

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
               :padding-inline "var(--size-3)"}]
   [:&.wide {
             :min-width      "7rem"}]
   [:&.round :flex-center
    {:border-radius "var(--radius-round)"
     :aspect-ratio "1/1"
     :display      :flex}]
   [:&.large {:display         :flex
              :align-items     :center
              :justify-content :center
              :min-height      "3rem"
              :min-width       "3rem"}]

   [:&.clear {:color "var(--text1)"}]
   [:&.danger-outline
    {:color      "var(--red-6)"
     :box-shadow "0 0 0px 3px var(--red-6)"}]

   [:&.info-outline
    {:color      "var(--blue-6)"
     :box-shadow "0 0 0px 3px var(--blue-6)"}]

   [:&.outliner ;#{:&.outline :&.outline2}
    {:background-color "transparent"
     :border           "2px solid var(--text0)"
     :color            "var(--text0)"}]])


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
  [schpaa.style.switch/large-switch
   {:view-fn (or view-fn (fn [t c] [:div t c]))
    :caption caption
    :get     atoma
    :set     #(reset! atoma %)}])

(defn largeswitch-local'
  ;todo
  "used for checkin and checkout of boats, might need a redesign (colors mainly)"
  [{:keys [get set view-fn caption]}]
  [schpaa.style.switch/large-switch'
   {:view-fn (or view-fn (fn [t c _v] [:div t c]))
    :caption caption
    :get     get
    :set     set}])

(defn ls-sm "A small toggle that presists in localstorage" [tag caption]
  [schpaa.style.switch/small-switch
   {:tag     tag
    :caption (fn [] [:div {:style {:margin-inline-end "var(--size-1)"
                                   :font-size         "var(--font-size-1)"}} caption])}])

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

(o/defstyled pillbar :div
  [:& tiny-button-reg
   {:cursor      :default
    :box-shadow  "var(--shadow-1)"
    :user-select :none}
   [:&.inverse
    {:background-color "var(--text0)"
     :color            "var(--brand1-copy)"}]
   ["&:not(.inverse)"
    {:background-color "var(--content)"}]

   [:&.normals
    {:padding-inline             "var(--size-5)"
     :border-width               "2px"
     :min-width                  "10ch"
     :min-height                 "3rem"
     :font-weight                "var(--font-weight-5)"
     :font-size                  "var(--font-size-1)"
     :justify-content            "center"
     :border-bottom-right-radius 0
     :border-top-right-radius    0
     :border-bottom-left-radius  0
     :border-top-left-radius     0
     :border-color               "var(--gray-9)"
     :border-right               "none"}
    [:&:active {:background-color "var(--text2)"
                :color            "var(--text0-copy)"}]
    ["&:first-child"
     {:border-top-left-radius     "3rem"
      :border-bottom-left-radius  "3rem"
      :border-bottom-right-radius 0
      :border-top-right-radius    0
      :border-right               "none"}]
    ["&:last-child"
     {:border-bottom-right-radius "3rem"
      :border-top-right-radius    "3rem"
      :border-bottom-left-radius  0
      :border-top-left-radius     0
      :border-right               "2px solid var(--gray-9)"}]]

   [:&.small
    {:justify-content            "center"
     :font-size   "var(--font-size-0)"
     :font-weight "var(--font-weight-5)"
     :padding-inline             "var(--size-5)"
     :border-width               "2px"
     :min-width                  "10ch"
     :min-height                 "2rem"
     :border-bottom-right-radius 0
     :border-top-right-radius    0
     :border-bottom-left-radius  0
     :border-top-left-radius     0
     :border-color               "var(--gray-9)"
     :border-right               "none"}
    [:&:active {:background-color "var(--text2)"
                :color            "var(--text0-copy)"}]
    ["&:first-child"
     {:border-top-left-radius     "3rem"
      :border-bottom-left-radius  "3rem"
      :border-bottom-right-radius 0
      :border-top-right-radius    0
      :border-right               "none"}]
    ["&:last-child"
     {:border-bottom-right-radius "3rem"
      :border-top-right-radius    "3rem"
      :border-bottom-left-radius  0
      :border-top-left-radius     0
      :border-right               "2px solid var(--gray-9)"}]]])