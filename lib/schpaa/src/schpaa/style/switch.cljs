(ns schpaa.style.switch
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [schpaa.style.button2 :as scb2]
            [schpaa.debug :as l]))

(defn dark-light-switch [{:keys [tag caption view-fn disabled]}]
  (r/with-let [get-value (schpaa.state/listen tag)
               set-value #(schpaa.state/toggle tag)]
    (let [enabled @get-value]
      [sc/row-sc
       [ui/switch-group
        (view-fn
          [ui/switch
           {:checked   enabled
            :on-change set-value
            :class     [:overflow-hidden
                        :relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer
                        :transition-colors :ease-in-out :duration-200 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white]
            :style     {:background (if enabled "var(--gray-8)" "var(--blue-9)")
                        :box-shadow "var(--inner-shadow-1),var(--inner-shadow-1),var(--shadow-2)"
                        :color      "var(--textmarker-background)"
                        :height     "30px"
                        :width      "56px"}}
           [:span.sr-only "Svitsj mellom lyst og mørkt modus"]
           [:span.pointer-events-none.inline-block.transform.ring-0.transition.ease-in-out.duration-300
            {:aria-hidden true
             :style       {:transform     (str "translate(" (if enabled 26 0) "px,0px)")
                           :border-radius "var(--radius-round)"
                           :background    "var(--gray-1)"
                           :height        "26px"
                           :width         "26px"}}
            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :height          "100%"
                      :transform       "translate(-24px,0)"}}
             [sc/icon [:> solid/MoonIcon]]]
            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :height          "100%"
                      :transform       "translate(28px,0)"}}
             [:div {:style {:animation-timing-function "var(--ease-1)"
                            :animation                 "var(--animation-spin) alternate-reverse"}}
              [sc/icon [:> solid/SunIcon]]]]]]
          (when caption
            [ui/switch-label {:style {:display     :flex
                                      :align-items :center
                                      :opacity     (if disabled 0.3 1)}} caption]))]])))

(defn large-switch [{:keys [tag caption disabled get set view-fn]}]
  (r/with-let [get-value (or get (schpaa.state/listen tag))
               set-value (or set #(schpaa.state/toggle tag))]
    (let [on? @get-value
          view-fn (or view-fn (fn [t c] [:div t c]))]
      [sc/row-sc-g2 {:style {:widthx      "100%"
                             :user-select :none}}
       [ui/switch-group
        ;{:class [(o/classname schpaa.style.button2/focus-button)]}
        (view-fn
          [ui/switch
           {:checked   on?
            :disabled  disabled
            :on-change set-value
            :class     [:overflow-hidden
                        :relative
                        :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer
                        ;:focus:outline-none
                        ;:focus-visible:ring-2 :focus-visible:ring-white
                        :transition-colors :ease-in-out :duration-100
                        (o/classname schpaa.style.button2/focus-button)]

            :style     {:background (if on? "var(--switchon)" "var(--switchoff)")
                        :opacity    (if disabled 0.3 1)
                        :box-shadow "var(--inner-shadow-1),var(--shadow-2)"
                        :color      "var(--gray-1)"
                        :height     "calc(var(--size-6))"
                        :width      "calc(var(--size-8) + 4px)"}}

           [:span.sr-only "switch"]
           [:span.pointer-events-none.inline-block.transform.ring-0.transition.ease-in-out.duration-100
            {:class       []
             :aria-hidden true
             :style       {:transform     (if on? "translate(var(--size-5),0px)" "translate(0px,0px)")
                           :border-radius "var(--radius-round)"
                           :background    "var(--gray-1)"
                           :height        "var(--size-5)"   ;26px
                           :width         "var(--size-5)"}}
            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :width           "100%"
                      :height          "100%"
                      :transform       "translate(calc(var(--size-5) * -1),0)"}}
             [sc/text1-cl "I"]]

            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :width           "100%"
                      :height          "100%"
                      :transform       "translate(var(--size-5),0)"}}
             [sc/text1-cl "O"]]]]
          (when caption
            [ui/switch-label {:passive false
                              :style   {:display     :flex
                                        :align-items :center
                                        :opacity     (if disabled 0.3 1)}} caption]))]])))

(defn large-switch' [{:keys [tag caption disabled get set view-fn]}]
  (let [on? (get)
        view-fn (or view-fn (fn [t c _v] [:div t c _v]))]
    [ui/switch-group
     (view-fn
       [ui/switch
        {:checked   (get)
         :disabled  disabled
         :on-change #(do
                       (tap> ["check" %])
                       (set %))
         :class     [:overflow-hidden
                     :relative
                     :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer
                     ;:focus:outline-none
                     ;:focus-visible:ring-2 :focus-visible:ring-white
                     :transition-colors :ease-in-out :duration-100
                     (o/classname schpaa.style.button2/focus-button)]

         :style     {:background (if on? "var(--red-9)" "var(--text2)")
                     :opacity    (if disabled 0.3 1)
                     :box-shadow "var(--inner-shadow-1),var(--shadow-2)"
                     :color      (if on? "var(--red-1)" "var(--text1)")
                     :height     "calc(var(--size-6))"
                     :width      "calc(var(--size-8) + 4px)"}}

        [:span.sr-only "switch"]
        [:span.pointer-events-none.inline-block.transform.ring-0.transition.ease-in-out.duration-100
         {:class       []
          :aria-hidden true
          :style       {:transform     (if on? "translate(var(--size-5),0px)" "translate(0px,0px)")
                        :border-radius "var(--radius-round)"
                        :background    "var(--gray-0)"
                        :color         "var(--gray-0)"
                        :height        "var(--size-5)"      ;26px
                        :width         "var(--size-5)"}}
         [:div
          {:style {:position        :absolute
                   :display         :flex
                   :align-items     :center
                   :justify-content :center
                   :width           "100%"
                   :height          "100%"
                   :transform       "translate(calc(var(--size-5) * -1),0)"}}
          [sc/icon-tiny ico/closewindow]]

         [:div
          {:style {:position        :absolute
                   :display         :flex
                   :align-items     :center
                   :justify-content :center
                   :width           "100%"
                   :height          "100%"
                   :transform       "translate(var(--size-5),0)"}}
          [sc/icon-tiny ico/check]]]]
       (when caption
         [ui/switch-label {:passive false
                           :style   {:display     :flex
                                     :align-items :center
                                     :opacity     (if disabled 0.3 1)}} caption])
       [l/pre on?])]))

(defn squared-switch [{:keys [tag caption disabled get set view-fn]}]
  (let [on? (get)
        view-fn (or view-fn (fn [t c _v] [:div t c _v]))]
      [ui/switch-group
       (view-fn 
         [ui/switch
          {:checked   (get)
           :disabled  disabled
           :on-change set
           :class     [:overflow-hidden
                       :relative
                       :inline-flex :flex-shrink-0
                       :border-2
                       :border-transparent
                       :cursor-pointer
                       :transition-colors
                       :ease-in-out
                       :duration-100]
           :style     {
                       :border-radius "var(--radius-0)"
                       :background    (if on? "var(--green-9)" "var(--gray-2)")
                       :opacity       (if disabled 0.3 1)
                       :box-shadow    "var(--inner-shadow-1),var(--shadow-2)"
                       :color         (if on? "var(--red-1)" "var(--text1)")
                       :height        "calc(var(--size-6))"
                       :width         "calc(var(--size-8) + 4px)"}}

          [:span.sr-only "switch"]
          [:span
           {:class       [:pointer-events-none
                          :inline-block
                          :transform
                          :ring-0
                          :transition
                          :ease-in-out
                          :duration-100]
            :aria-hidden true
            :style       {:transform     (if on? "translate(var(--size-5),0px)" "translate(0px,0px)")
                          :border-radius "var(--radius-0)"
                          :background    "var(--gray-00)"
                          :color         "var(--gray-00)"
                          :height        "var(--size-5)"
                          :width         "var(--size-5)"}}
           [:div
            {:class [ (o/classname schpaa.style.button2/focus-button)]
             :style {:position        "absolute"
                     :display         "flex"
                     :align-items     "center"
                     :justify-content "center"
                     :width           "100%"
                     :height          "100%"
                     :transform       "translate(calc(var(--size-5) * -1),0)"}}
            [sc/icon-tiny ico/check]]

           [:div

            {:class [(o/classname schpaa.style.button2/focus-button)]

             :style {:position        "absolute"
                     :display         "flex"
                     :align-items     "center"
                     :justify-content "center"
                     :width           "100%"
                     :height          "100%"
                     :transform       "translate(var(--size-5),0)"}}
            #_[sc/icon-tiny ico/closewindow]]]]
         (when caption
           [ui/switch-label {:passive false
                             :style   {:display     "flex"
                                       :align-items "center"
                                       :opacity     (if disabled 0.3 (if on? 1 0.5))}} caption]))]))


(defn large-switch-checkinout [{:keys [tag caption disabled get set view-fn]}]
  (r/with-let [get-value (or get (schpaa.state/listen tag))
               set-value (or set #(schpaa.state/toggle tag))]
    (let [on? @get-value
          view-fn (or view-fn (fn [t c] [:div t c]))]
      [sc/row-sc-g2 {:style {:width       "100%"
                             :user-select :none}}
       [ui/switch-group
        ;{:class [(o/classname schpaa.style.button2/focus-button)]}
        (view-fn
          [ui/switch
           {:checked   on?
            :disabled  disabled
            :on-change set-value
            :class     [:overflow-hidden
                        :relative
                        :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer
                        ;:focus:outline-none
                        ;:focus-visible:ring-2 :focus-visible:ring-white
                        :transition-colors :ease-in-out :duration-100
                        (o/classname schpaa.style.button2/focus-button)]

            :style     {:background (if on? "var(--switchon)" "var(--switchoff)")
                        :opacity    (if disabled 0.3 1)
                        :box-shadow "var(--inner-shadow-1),var(--shadow-2)"
                        :color      "var(--gray-1)"
                        :height     "calc(var(--size-6))"
                        :width      "calc(var(--size-9) + 4px)"}}

           [:span.sr-only "switch"]
           [:span.pointer-events-none.inline-block.transform.ring-0.transition.ease-in-out.duration-100
            {:class       []
             :aria-hidden true
             :style       {:transform     (if on? "translate(calc(var(--size-7) + 7px),0px)" "translate(0px,0px)")
                           :border-radius "var(--radius-round)"
                           :background    "var(--gray-1)"
                           :height        "var(--size-5)"   ;26px
                           :width         "var(--size-5)"}}
            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :width           "100%"
                      :height          "100%"
                      :transform       "translate(calc(var(--size-6) * -1 - 2px),0)"}}
             [sc/text1-cl "Inn"]]

            [:div
             {:style {:position        :absolute
                      :display         :flex
                      :align-items     :center
                      :justify-content :center
                      :width           "100%"
                      :height          "100%"
                      :transform       "translate(var(--size-7),0)"}}
             [sc/text1-cl "Ut"]]]]
          (when caption
            [ui/switch-label {:passive false
                              :style   {:display     :flex
                                        :align-items :center
                                        :opacity     (if disabled 0.3 1)}} caption]))]])))



(defn small-switch-example [{:keys [!value tag caption disabled]}]
  (r/with-let [get-value (if tag (schpaa.state/listen tag) !value)
               set-value (if tag #(schpaa.state/toggle tag) #(swap! !value (fnil not false)))]
    (let [enabled @get-value]
      [sc/row-sc
       [ui/switch-group
        [ui/switch
         {:checked   enabled
          :disabled  disabled
          :on-change set-value
          :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer :transition-colors :ease-in-out :duration-200 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75]
          :style     {:background (if enabled "var(--switchon)" "var(--switchoff)")
                      :opacity    (if disabled 0.3 1)
                      :border     "var(--surface00) solid 1px"
                      :height     "24px"
                      :width      "44px"}}
         [:span.sr-only "Use setting"]
         [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out.duration-200
          {:aria-hidden true
           :class       (if enabled :translate-x-5 :translate-x-0)
           :style       {:height      "18px"
                         :margin-top  "2px"
                         :margin-left "2px"
                         :width       "18px"}}]]
        [ui/switch-label {:style {:opacity (if disabled 0.3 1)}} caption]]])))

(defn small-switch [{:keys [tag caption]}]
  (r/with-let [get-value (schpaa.state/listen tag)
               set-value #(schpaa.state/toggle tag)]
    (let [enabled @get-value]
      [sc/thing-inside-shade
       [:div.flex.items-center.gap-2
        {:class [:cursor-pointer
                 :select-none]}
        [ui/switch-group
         [ui/switch
          {:checked   enabled
           :on-change set-value
           :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :transition-colors :ease-in-out :duration-200
                       :focus:outline-none]
           :style     {:background (if enabled "var(--switchon)" "var(--switchoff)")
                       :border     "var(--toolbar) solid 1px"
                       :height     "20px"
                       :width      "34px"}}
          [:span.sr-only "Use setting"]
          [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out.duration-200
           {:aria-hidden true
            :class       (if enabled :translate-x-4 :translate-x-0)
            :style       {:height      "16px"
                          :margin-top  "1px"
                          :margin-left "0px"
                          :width       "16px"}}]]
         [ui/switch-label (caption)]]]])))

(o/defstyled switch-label :div
  {:background [:rgba 0 255 0 0.5]
   ;:padding "1rem"
   :position   "absolute"})

(defn small-switch-base [attr {:keys [caption get-value set-value]}]
  (let [enabled (try @get-value (catch js/Error _ (get-value)))
        disabled (:disabled attr)]
    [sc/thing-inside-shade
     {:style (cond
               (:clear-bg attr) {:background-color "unset"})
      :class [(if disabled :disabled)]}
     [ui/switch-group
      [ui/switch
       {:disabled  (:disabled attr)
        :checked   enabled
        :on-change set-value
        :class     (conj [:relative
                          ;:bg-alt
                          :inline-flex
                          :focus:outline-none]
                         (o/classname scb2/focus-button))
        :style     {:transition-duration (or (:duratidon attr) "210ms")
                    :background          (if enabled "var(--switchon)" "var(--switchoff)")
                    :border-radius       "var(--radius-round)"
                    :border-width        "2px"
                    :border-color        "transparent"
                    :opacity             (if (:disabled attr) 0.25)
                    :height              "18px"
                    :width               "28px"}}
       [:span.sr-only "Use setting"]
       ;toggle top
       (when-not (:disabled attr)
         [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out
          {:aria-hidden true
           :style       {:transition-duration (or (:duratidon attr) "10ms")
                         :transform           (if enabled "translate(10px,0)" "translate(0,0)")
                         :height              "14px"
                         :margin-top          "0px"
                         :margin-left         "0px"
                         :width               "14px"}}])]
      [ui/switch-label
       {:passive false
        :style   {:line-height    "1rem"
                  :cursor         (when-not disabled :pointer)
                  :padding-inline "var(--size-2)"
                  :white-space    :nowrap}} (caption)]]]))
