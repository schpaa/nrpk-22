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

   [:&.remove {:color            "var(--red-1)"
               :background-color "var(--red-7)"}
    [:&:active:enabled {:color      "var(--red-1)"
                        :background "var(--red-8)"}]]
   [:&:disabled {:opacity    0.2
                 :color      "var(--text1)"
                 :background "var(--toolbar-)"}]

   [:&:active:enabled {:background "var(--green-7)"
                       :color      "var(--green-1)"}]])

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
  [:& :mx-auto
   {:display               :grid
    :column-gap            "var(--size-1)"
    :row-gap               "var(--size-1)"
    :min-width             "24rem"
    :max-width             "24rem"
    :height                "100%"
    :grid-template-columns "repeat(5,1fr)"
    :grid-template-rows    "4rem 4rem auto auto repeat(4,3.5rem)"}
   #_[:&.mobile {:grid-template-areas [["check-a  child    juvenile moon        adult"]
                                       ["check-a  child    juvenile key         adult"]
                                       ["check-b  aboutyou aboutyou aboutyou    aboutyou"]
                                       ["check-c    boats    boats    boats     boats"]
                                       ["next .          numpad   numpad   numpad"]
                                       ["prev   .  numpad   numpad   numpad"]
                                       ["complete       .     numpad   numpad   numpad"]
                                       ["complete .   numpad   numpad   numpad"]]}]

   [:&.right-side {:grid-template-areas [["check-a  child    juvenile moon        adult"]
                                         ["check-a  child    juvenile key         adult"]
                                         ["check-b  aboutyou aboutyou aboutyou    aboutyou"]
                                         ["check-c  boats     boats    boats     boats"]
                                         ["complete complete  numpad   numpad   numpad"]
                                         ["complete complete  numpad   numpad   numpad"]
                                         ["complete complete  numpad   numpad   numpad"]
                                         ["complete complete  numpad   numpad   numpad"]]}]
   [:&.left-side {:grid-template-areas [["adult    moon    juvenile child      check-a"]
                                        ["adult    key     juvenile child      check-a"]
                                        ["aboutyou  aboutyou  aboutyou   aboutyou  check-b"]
                                        ["boats     boats     boats      boats     check-c"]
                                        ["numpad    numpad    numpad     complete  complete"]
                                        ["numpad    numpad    numpad     complete  complete"]
                                        ["numpad    numpad    numpad     complete  complete"]
                                        ["numpad    numpad    numpad     complete  complete"]]}]])

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(o/defstyled clear-field-button :button
  :flex-center :w-full :h-full
  [:&:disabled {:opacity 0.2}])