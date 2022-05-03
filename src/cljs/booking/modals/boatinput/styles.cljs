(ns booking.modals.boatinput.styles
  (:require [lambdaisland.ornament :as o]))

(o/defstyled input-caption :div
  {:font-familys   "Oswald"
   :font-family    "Inter"
   ;:padding-left "var(--size-2)"
   :font-size      "var(--font-size-4)"
   :letter-spacing "var(--font-letterspacing-0)"
   :font-weight    "var(--font-weight-4)"})

(o/defstyled button-caption :div
  {:font-family "Inter"
   :font-size   "var(--font-size-5)"
   :font-weight "var(--font-weight-5)"})

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
   :border-radius "var(--radius-1)"
   :background    "var(--red-9)"
   :color         "var(--red-1)"}
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
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   ;:background    "var(--red-6)"
   ;:color         "var(--surface1)"
   :background    "var(--green-6)"
   :color         "var(--green-1)"}
  [:&:hover {:background "var(--green-5)"
             :color      "var(--green-1)"}]
  [:&:disabled {:color      "var(--text3)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--green-7)"
                      :color      "var(--green-1)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled numberpad-button :button
  :w-full :h-full :duration-100
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-0)"
   :background    "var(--floating)"
   :color         "var(--text0)"}
  [:&:hover {:background "var(--floating)"
             :color      "var(--text0)"}]
  [:&:disabled {:color      "var(--text3)"
                :background "var(--toolbar)"}]
  [:&:active:enabled {:background "var(--surface3)"
                      :color      "var(--surface000)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled up-down-button :div
  :h-full button-caption
  {;:outline "1px solid red"
   :box-shadow    "var(--inner-shadow-1)"
   :border-radius "var(--radius-1)"}
  [:.base
   {:border-radius "var(--radius-1)"}]



  [:.some {:opacity          1
           :xz-index         30
           :color            "black"
           :background-color "var(--yellow-5)"}]
  [:.zero {:opacity          1
           :color            "var(--text1)"
           :background-color "var(--floating)"}]

  [:&:active {:background-color "var(--content)"}]
  [:.overlay {:overflow :hidden}]
  ([{:keys [increase decrease value content]}]
   [:<> [:div.base
         {:style {:position  :relative
                  :flex-grow 1
                  :height    :100%
                  :overflow  :hidden}}

         [:div.overlay.absolute.top-0.inset-x-0.item.z-40x
          {:on-click decrease :style {:height "50%"}}]

         [:div.overlay.absolute.bottom-0.inset-x-0.item.z-40x
          {:on-click increase :style {:height "50%"}}]

         [:div.flex.items-end.justify-center.h-full.z-50x.pointer-events-none.base]

         [:div.absolute.inset-0.pointer-events-none.pb-2.-debug.flex.items-end.justify-center
          {:class (if (pos? value) :some :zero)}
          [:div.text-black.font-normal (content value)]]]]))

(o/defstyled toggle-button :div
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :aspect-ratio  "1/1"
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
  [:& :w-72 :mx-auto
   {:border                "0"
    :display               :grid
    :column-gap            "var(--size-2)"
    :row-gap               "var(--size-2)"
    :width                 "100%"
    :height                "100%"
    :grid-template-columns "repeat(4,1fr)"
    :grid-template-rows    "4rem 4rem auto 8rem 4rem repeat(5,4rem)"
    :xgrid-auto-rows       "auto"}
   [:&.mobile {:grid-template-areas [["child" "juvenile" "moon" "adult"]
                                     ["child " "juvenile" "key" "adult"]
                                     ["boats" "boats" "boats" "boats"]
                                     ;["boats" "boats""boats""boats"]
                                     ["boat" "boat" "boat" "boat"]
                                     ["direction" "direction" "direction" "add"]
                                     ["trash" "input" "input" "input"]
                                     ["." "numpad" "numpad" "numpad"]
                                     ["." "numpad" "numpad" "numpad"]
                                     ["restart" "numpad" "numpad" "numpad"]
                                     ["complete" "numpad" "numpad" "numpad"]]}]
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

