(ns schpaa.style.booking
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]))


(o/defstyled line :div
  [:.selected sc/small-padding sc/selected-cell sc/rounded]
  [:.normal sc/small-padding sc/normal-cell sc/rounded]
  ([{:keys [selected expanded on-click content]}]
   (let [{:keys [number location category]} content]
     (if expanded
       [:div {:class (if selected [:selected] [:normal])}
        [:div.flex.gap-2.items-center

         [sc/col
          (sc/clear (sc/icon [:> solid/MenuIcon]))]

         [sc/col {:class [:space-y-1 :grow :truncate]}
          [sc/col {:class [:space-y-1 :grow :truncate]}
           [sc/row {:class [:gap-2 :items-baseline :truncate]}
            [sc/badge number]
            [sc/badge-2 location]
            [sc/title category]]
           [sc/subtext "Bottoms out in a classification"]
           [sc/dim [sc/subtext "Bottoms out in a classification"]]
           [sc/dim [sc/subtext "Bottoms out in a classification"]]
           [sc/dim [sc/subtext "Bottoms out in a classification"]]]
          [sc/row                                           ;{:class [:gap-1 :w-full :grow]}
           [sc/surface-d {:class [:w-full :h-8]}]]]
         [:div {:on-click on-click}
          (if selected
            (sc/clear (sc/icon-large [:> solid/MinusCircleIcon]))
            (sc/clear (sc/icon-large [:> outline/PlusCircleIcon]))
            #_[sc/base-button [sc/icon-large [:> solid/XCircleIcon]]])]]]
       [:div {:class (if selected [:selected] [:normal])}
        [sc/center
         [sc/row {:class [:gap-1]}
          (sc/clear (sc/icon [:> solid/MenuIcon]))
          [sc/row {:class [:gap-2 :truncate :w-full]}
           [sc/badge number]
           [sc/badge-2 location]
           [sc/title category]]
          [:div {:on-click on-click}
           (if selected
             (sc/danger (sc/icon-large [:> solid/MinusCircleIcon]))
             (sc/cta (sc/icon-large [:> outline/PlusCircleIcon]))
             #_[sc/base-button [sc/icon-large [:> solid/XCircleIcon]]])]]]]))))
