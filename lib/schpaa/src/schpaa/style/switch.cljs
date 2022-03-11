(ns schpaa.style.switch
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]))

(defn switch-example [{:keys [!value caption]}]
  ;(r/with-let [!enabled (r/atom true)])
  (let [enabled @!value]
    [ui/switch-group
     [ui/switch
      {:checked   enabled
       :on-change #(do (tap> "click") (swap! !value not))
       :class     [:relative :inline-flex :flex-shrink-0 :border-2 :border-transparent :rounded-full :cursor-pointer :transition-colors :ease-in-out :duration-200 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75
                   #_(if enabled :bg-green-500 :bg-gray-500)]
       :style     {:background (if enabled "var(--brand1)" "var(--surface1)")
                   :height     "34px"
                   :width      "62px"}}
      [:span.sr-only "Use setting"]
      [:span.pointer-events-none.inline-block.rounded-full.bg-white.shadow-lg.transform.ring-0.transition.ease-in-out.duration-200 ;; .transform, .transition, .ease-in-out unneeded since Tailwind CSS 3.0
       {:aria-hidden true
        :class       (if enabled :translate-x-7 :translate-x-0)
        :style       {:height "30px"
                      :width  "30px"}}]]
     [ui/switch-description caption]]))