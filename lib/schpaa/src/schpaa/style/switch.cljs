(ns schpaa.style.switch
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]))

(defn switch-example [{:keys [tag caption]}]
  (r/with-let [get-value (schpaa.state/listen tag)
               set-value #(schpaa.state/toggle tag)]
    (let [enabled @get-value]
      ;(r/with-let [!enabled (r/atom true)])

      [sc/row-sc
       [ui/switch-group
        [ui/switch
         {:checked   enabled
          :on-change set-value
          :class     [:overflow-hidden
                      :relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer
                      :transition-colors :ease-in-out :duration-200 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white]
          :style     {:background "var(--gray-9)" #_(if enabled "var(--brand1)" "var(--surface1)")
                      :box-shadow "var(--inner-shadow-1),var(--shadow-2)"
                      :color      "var(--textmarker-background)"
                      :height     "30px"
                      :width      "58px"}}
         #_[:div.absolute.inset-y-0.flex.items-center
            {:style (conj (if enabled {:left 2} {:right 2})
                          (if-not enabled {;:animation-direction "alternate-reverse" ;var(--animation-pulse)
                                           :animation-timing-function "var(--ease-1)"
                                           :animation                 "var(--animation-spin) alternate-reverse"})
                          {:translate ""})}
            [sc/icon (if enabled [:> solid/MoonIcon] [:> solid/SunIcon])]]

         [:span.sr-only "Svitsj mellom lyst og mørkt modus"]
         [:span.pointer-events-none.inline-block.transform.ring-0.transition.ease-in-out.duration-300
          {:aria-hidden true
           :class       (if enabled :translate-x-7 :translate-x-0)
           :style       {:transform     ""
                         :border-radius "var(--radius-round)"
                         :background    "var(--gray-1)"
                         :height        "26px"
                         :width         "26px"}}
          [:div.absolute.flex.items-center.justify-center.h-full.-translate-x-7 [sc/icon [:> solid/MoonIcon]]]
          [:div.absolute.flex.items-center.justify-center.h-full.translate-x-7
           [:div {:style {:animation-timing-function "var(--ease-1)"
                          :animation                 "var(--animation-spin) alternate-reverse"}}
            [sc/icon [:> solid/SunIcon]]]]]]

        #_[ui/switch-label caption]]])))

(defn small-switch-example [{:keys [!value tag caption]}]
  (r/with-let [get-value (if tag (schpaa.state/listen tag) !value)
               set-value (if tag #(schpaa.state/toggle tag) #(swap! !value (fnil not false)))]
    (let [enabled @get-value]
      [sc/row-sc
       [ui/switch-group
        [ui/switch
         {:checked   enabled
          :on-change set-value
          :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer :transition-colors :ease-in-out :duration-200 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75]
          :style     {:background (if enabled "var(--brand1)" "var(--surface1)")
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
        [ui/switch-label caption]]])))

(defn small-switch [{:keys [tag caption]}]
  (r/with-let [get-value (schpaa.state/listen tag)
               set-value #(schpaa.state/toggle tag)]
    (let [enabled @get-value]
      [sc/thing-inside-shade
       [:div.flex.items-center.gap-1
        {:class [:cursor-pointer
                 :select-none]}
        [ui/switch-group
         [ui/switch
          {:checked   enabled
           :on-change set-value
           :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :transition-colors :ease-in-out :duration-200
                       :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75]
           :style     {:background (if enabled "var(--switchon)" "var(--switchoff)")
                       :border     "var(--toolbar) solid 1px"
                       :height     "18px"
                       :width      "34px"}}
          [:span.sr-only "Use setting"]
          [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out.duration-200
           {:aria-hidden true
            :class       (if enabled :translate-x-4 :translate-x-0)
            :style       {:height      "14px"
                          :margin-top  "1px"
                          :margin-left "1px"
                          :width       "14px"}}]]
         [ui/switch-label (caption)]]]])))

(defn small-switch-base [attr {:keys [tag caption get-value set-value]}]
  #_(r/with-let [get-value (schpaa.state/listen tag)
                 set-value #(schpaa.state/toggle tag)])
  (let [enabled @get-value]
    [sc/thing-inside-shade
     {:disabled (:disabled attr)}
     [:div.flex.items-center.gap-1
      {:class [:cursor-pointer
               :select-none]}
      [ui/switch-group
       [ui/switch
        {:disabled  (:disabled attr)
         :checked   enabled
         :on-change set-value
         :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :transition-colors :ease-in-out :duration-200
                     :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75]
         :style     {:background (if enabled "var(--switchon)" "var(--switchoff)")
                     :opacity    (if (:disabled attr) 0.25)
                     :border     "var(--toolbar) solid 1px"
                     :height     "18px"
                     :width      "34px"}}
        [:span.sr-only "Use setting"]
        (when-not (:disabled attr)
          [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out.duration-200
           {:aria-hidden true
            :class       (if enabled :translate-x-4 :translate-x-0)
            :style       {:height      "14px"
                          :margin-top  "1px"
                          :margin-left "1px"
                          :width       "14px"}}])]
       [ui/switch-label (caption)]]]]))
