(ns booking.modals.boatinput.styles
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]))

(o/defstyled input-caption :div
  {:font-family    "Inter"
   :font-size      "var(--font-size-5)"
   :letter-spacing "var(--font-letterspacing-1)"
   :font-weight    "var(--font-weight-6)"})

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
  [:& :w-full :h-full :outline-none :focus:outline-none
   {:display       "grid"
    :border-radius "var(--radius-0)"
    :background    "var(--floating)"
    :place-items   "center"}
   [:&.add {:color            "var(--content)"
            :background-color "var(--brand1)"}
    [:&:active:enabled {:color            "var(--green-1)"
                        :background-color "var(--brand1-lighter)"}]]

   [:&.add2 {:color            "var(--brand1)"
             :background-color "var(--floating)"}
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
  [:& ;:min-h-full
   {:display               "grid"
    :margin                0
    :padding               0
    ;:height                "100%"
    :column-gap            "var(--size-1)"
    :min-width             "20rem"
    :max-width             "20rem"
    :grid-template-columns "auto"
    :grid-template-rows
    "min-content
    0.5rem 4rem 0.25rem 4rem

    0.5rem min-content
    0.5rem min-content
    1rem
    minmax(5rem,1fr)
    1rem
    4rem 0.25rem
    4rem 0.25rem
    4rem 0.25rem
    4rem
    "}

   [:&.left
    {:grid-template-columns "min-content repeat(4,minmax(0,1fr))"
     :grid-template-areas   [
                             ["in in in in in"]


                             [".  .  .  .  . "]
                             ["c1 ad mo ju ch"]
                             ["c1 ad .  ju ch"]
                             ["c1 ad ky ju ch"]

                             [".  .  .  .  . "]
                             ["b1 ay ay ay ay"] ;-

                             [".  .  .  .  . "]
                             ["a1 bo bo bo bo"]


                             [".  .  .  .  . "]
                             ["st st st st st"]
                             [".  .  .  .  . "]
                             ["np np np np . "]
                             ["np np np np . "]
                             ["np np np np co"]
                             ["np np np np . "]
                             ["np np np np aw"]
                             ["np np np np . "]
                             ["np np np np re"]]}
    [:&.alt
     {:grid-template-rows "min-content
       0.5rem
       auto
       0.5rem
       4rem 0.24rem
       4rem 0.24rem
       4rem 0.24rem
       4rem
       "
      :grid-template-areas [["in in in in in"]
                            [".  .  .  .  . "]
                            ["st st st st st"]
                            [".  .  .  .  . "]
                            ["np np np np . "]
                            ["np np np np . "]
                            ["np np np np co"]
                            ["np np np np . "]
                            ["np np np np aw"]
                            ["np np np np . "]
                            ["np np np np re"]]}]]

   [:&.right
    {:grid-template-columns "repeat(4,minmax(0,1fr)) min-content"
     :grid-template-areas   [;[". mode mode mode mode"]
                             ["in in in in in"]
                             [".  .  .  .  . "]
                             ["bo bo bo bo a1"]
                             [".  .  .  .  . "]
                             ["ay ay ay ay b1"]
                             [".  .  .  .  . "]
                             ["ch ju mo ad c1"]
                             ["ch ju .  ad c1"]
                             ["ch ju ky ad c1"]
                             [".  .  .  .  . "]
                             ["st st st st st"]
                             [".  .  .  .  . "]
                             [".  np np np np"]
                             [".  np np np np"]
                             ["co np np np np"]
                             [".  np np np np"]
                             ["aw np np np np"]
                             [".  np np np np"]
                             ["re np np np np"]]}]])

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(o/defstyled clear-field-button :button
  :flex-center :w-full :h-full
  [:&:disabled {:opacity 0.2}])