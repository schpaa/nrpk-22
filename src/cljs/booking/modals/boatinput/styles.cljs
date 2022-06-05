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
   :font-weight "var(--font-weight-4)"})

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

(o/defstyled debug-1 :div
  {:outline "1px solid green"})

(o/defstyled push-button :button
  [:& :w-full :h-full
   {:display       "grid"
    :border-radius "var(--radius-0)"
    :background    "var(--floating)"
    :place-items   "center"}
   [:&.add {:color            "var(--content)"
            :background-color "var(--brand1)"}
    [:&:active:enabled {:color            "var(--green-1)"
                        :background-color "var(--brand1-lighter)"}]]

   [:&.new {:color            "var(--brand1-copy)"
            :background-color "var(--brand1)"}
    [:&:active:enabled {:background-color "var(--orange-8)"}]]

   [:&.await {:color            "var(--gray-0)"
              :background-color "var(--brand2)"}
    [:&:active:enabled {:background-color "var(--orange-8)"}]]]

  [:&.remove {:color            "var(--red-1)"
              :background-color "var(--red-7)"}
   [:&:active:enabled {:color      "var(--red-1)"
                       :background "var(--red-8)"}]]
  [:&:disabled {:opacity    0.2
                :color      "var(--text1)"
                :background "var(--toolbar-)"}]

  [:&:active:enabled {:background "var(--green-7)"
                      :color      "var(--green-1)"}])

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

(o/defstyled up-down-button :div
  [:& :flex :flex-col :justify-around :items-center :select-none
   {:position         :relative
    :sbox-shadow      "var(--inner-shadow-1)"
    :background-color "var(--floating)"                     ;"var(--vener)"

    :border-radius    "var(--radius-0)"}

   [:&.some {:opacity          1
             :color            "var(--selected-copy)"
             :background-color "var(--selected)"}]
   [:&.zero {:opacity           1
             :color             "var(--text1)"
             :abackground-color "var(--floating)"}]
   [:&:active {:background-color "var(--content)"}]
   [:.overlay {:overflow :hidden}]])

(o/defstyled center :div
  :shrink-0 :w-full :flex-center
  {:height :50%})

(o/defstyled toggle-button :div
  :w-full :h-full
  {:display       :grid
   :background    "var(--floating)"
   :place-content :center
   :border-radius "var(--radius-0)"})


(o/defstyled panel :div
  [:& :mx-auto :min-h-full
   {:display               :grid
    :column-gap            "var(--size-1)"
    :row-gap               "var(--size-1)"
    :min-width             "20rem"
    :max-width             "24rem"
    :height                "100%"

    :grid-template-rows    "min-content 1fr auto 4rem 4rem repeat(4,4rem)"}
   [:&.left-side {:grid-template-columns "repeat(4,1fr) min-content"
                  :grid-template-areas   [["boats     boats    boats     boats a"]
                                          ["status status status status a"]
                                          ["aboutyou aboutyou aboutyou    aboutyou b"]
                                          ["child    juvenile moon        adult c"]
                                          ["child    juvenile key         adult c"]
                                          ["numpad numpad numpad . ."]
                                          ["numpad numpad numpad w w"]
                                          ["numpad numpad numpad o o"]
                                          ["numpad numpad numpad o o"]]}]

   [:&.right-side {:grid-template-columns "min-content repeat(4,1fr)"
                   :grid-template-areas   [["a boats boats boats boats"]
                                           ["a status status status status"]
                                           ["b aboutyou aboutyou aboutyou aboutyou"]
                                           ["c adult moon juvenile child"]
                                           ["c adult key  juvenile child"]
                                           [". . numpad numpad numpad"]
                                           ["w w numpad numpad numpad"]
                                           ["o o numpad numpad numpad"]
                                           ["o o numpad numpad numpad"]]}]])

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(o/defstyled clear-field-button :button
  :flex-center :w-full :h-full
  [:&:disabled {:opacity 0.2}])