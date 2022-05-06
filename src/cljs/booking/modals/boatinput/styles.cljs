(ns booking.modals.boatinput.styles
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]))

(o/defstyled input-caption :div
  {:font-family    "Inter"
   :font-size      "var(--font-size-5)"
   :letter-spacing "var(--font-letterspacing-0)"
   :font-weight    "var(--font-weight-5)"})

(o/defstyled button-caption :div
  {:font-family "Inter"
   :font-size   "var(--font-size-5)"
   :font-weight "var(--font-weight-6)"})

(o/defstyled button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   :background    "var(--surface4)"
   :color         "var(--surface1)"}
  [:&:hover {:background "var(--surface5)"}]
  [:&:disabled {:color      "var(--surface2)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--surface3)"
                      :color      "var(--surface2)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled delete-button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-0)"
   ;:background    "var(--red-9)"
   :color         "var(--red-5)"}
  [:&:hover {:background "var(--red-8)"
             :color      "var(--red-2)"}]
  [:&:disabled {:color      "var(--text3)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--red-7)"
                      :color      "var(--red-1)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled add-button :button
  [:& :w-full :h-full
   {:display       :grid
    :place-content :center
    :border-radius "var(--radius-0)"}
   ;:background    "var(--brand1)"}
   ;:color         "var(--surface1)"
   [:&.add {;:background "var(--green-6)"
            :color "var(--green-1)"}]
   #_[:&:hover {:background "var(--green-5)"
                :color      "var(--green-1)"}]
   [:&:disabled {:opacity    0.2
                 :color      "var(--text1)"
                 :background "var(--toolbar)" #_"var(--surface0)"}]
   [:&:active:enabled {:background "var(--green-7)"
                       :color      "var(--green-1)"}]]
  #_([{:keys [ref on-click disabled enabled style]} ch]
     ^{:ref      ref
       :on-click on-click
       :disabled (if (some? enabled) (not enabled) disabled)}
     [:<> [:div ch]]))

(o/defstyled numberpad-button :button
  [:& :w-full :h-full :duration-100
   {:display          :grid
    :background-color "var(--floating)"
    :place-content    :center
    :border-radius    "var(--radius-0)"
    :color            "var(--text1)"}
   [:&                                                      ;.button-pad {:background "var(--floating)"}
    [:&:hover {:background "var(--floating)"
               :color      "var(--text0)"}]
    [:&:active:enabled {:background "var(--surface3)"
                        :color      "red" #_"var(--surface000)"}]]
   [:&:disabled {:color      "var(--text)"
                 :opacity    0.2
                 :background "var(--toolbar-)"}]]
  #_([{:keys [ref on-click disabled enabled style]} ch]
     ^{:ref      ref
       :on-click on-click
       :disabled (if (some? enabled) (not enabled) disabled)}
     [:<> [:div ch]]))

(o/defstyled up-down-button' :div
  [:& :flex :flex-col :justify-around :items-center :select-none
   {:position         :relative
    :box-shadow       "var(--inner-shadow-1)"
    :background-color "var(--floating)"
    :border-radius    "var(--radius-1)"}

   #_[:div {;:margin "8px"
            :background-color "red"}]
   [:.some {:opacity    1
            :color      "var(--selected-copy)"
            :background "var(--selected)"}]
   [:.zero {:opacity          1
            :color            "var(--text1)"
            :background-color "var(--floating)"}]
   [:&:active {:background-color "var(--content)"}]
   [:.overlay {:overflow :hidden}]])

(o/defstyled center :div
  :shrink-0 :w-full :flex-center
  {:height :50%})

(defn up-down-button [{:keys [increase decrease value content]}]
  (let [value (js/parseInt value)]
    [up-down-button'
     {:style {:position  :relative
              :display   :flex
              :flex-grow 1
              :height    :100%
              :overflow  :hidden}}
     [:div {:class [:flex :opacity-50 :h-20 :items-end]} content]
     [:div.inset-0.absolute.flex.flex-col.justify-between
      {:style {:color "var(--text2)"}}
      [center
       {:class    [:absolute :top-0 :pb-10]
        :on-click decrease}
       (when (pos? value)
         [sc/icon-small ico/minus])]
      [center
       {:class    [:absolute :bottom-0 :pt-10]
        :on-click increase}
       (if (zero? value)
         [sc/icon-small ico/plus]
         [sc/text1 value])]]]))

(o/defstyled toggle-button :div
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :xaspect-ratio "1/1"
   :border-radius "var(--radius-1)"}
  ([{:keys [on-click value on-style off-style content]}]
   ^{:on-click on-click}
   [:<> [:div (if value
                {:style (when on-style on-style)
                 :class (when-not on-style :on)}
                {:style (when off-style off-style)
                 :class [(when-not off-style :off)]})
         content]]))

(o/defstyled panel :div
  [:& :mx-auto
   {:display               :grid
    :column-gap            "var(--size-2)"
    :row-gap               "var(--size-3)"
    :min-width             "20rem"
    :max-width             "20rem"
    :height                "100%"
    :grid-template-columns "repeat(5,1fr)"
    :grid-template-rows    "4rem 4rem auto auto repeat(4,4rem)"}
   [:&.mobile {:grid-template-areas [#_[". welcome" "welcome" "welcome" "welcome"]
                                     ["check-a  child    juvenile moon        adult"]
                                     ["check-a  child    juvenile key         adult"]
                                     ["check-b  aboutyou aboutyou aboutyou    aboutyou"]
                                     ["check-c    boats    boats    boats     boats"]
                                     [". .          numpad   numpad   numpad"]
                                     ["prev   next  numpad   numpad   numpad"]
                                     [".      .     numpad   numpad   numpad"]
                                     ["complete .   numpad   numpad   numpad"]]}]

   [:&.right-side {:grid-template-areas [["child" "juvenile" "moon" "adult"]
                                         ["child " "juvenile" "key" "adult"]
                                         ["boat" "boat" "boat" "boat"]
                                         ["boat" "boat" "boat" "boat"]
                                         ["trash" "input" "input" "add"]
                                         ["boats" "numpad" "numpad" "numpad"]
                                         ["boats" "numpad" "numpad" "numpad"]
                                         ["restart" "numpad" "numpad" "numpad"]
                                         ["complete" "numpad" "numpad" "numpad"]]}]
   [:&.left-side {:grid-template-areas [["adult" "moon" "juvenile" "child"]
                                        ["adult" "key" "juvenile" "child"]
                                        ["boat" "boat" "boat" "boat"]
                                        ["boat" "boat" "boat" "boat"]
                                        ["add" "input" "input" "trash"]
                                        ["numpad" "numpad" "numpad" "boats"]
                                        ["numpad" "numpad" "numpad" "boats"]
                                        ["numpad" "numpad" "numpad" "restart"]
                                        ["numpad" "numpad" "numpad" "complete"]]}]])

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))
