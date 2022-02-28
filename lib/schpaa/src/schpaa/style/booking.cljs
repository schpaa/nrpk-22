(ns schpaa.style.booking
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]))

(defn stability-str [st]
  (case st
    1 "Nybegynner"
    2 "Stabil"
    3 "Ustabil"
    4 "Utfordrende"
    nil))

(o/defstyled line :div
  [:.selected sc/small-padding sc/selected-cell sc/rounded]
  [:.normal sc/small-padding sc/normal-cell sc/rounded]
  ([{:keys [selected expanded on-click content]}]
   (let [{:keys [number location category brand weight length width description material stability]} content]
     (if expanded
       [sc/col {:class [(if selected :selected :normal) :h-full :justify-between]}
        [sc/col {:class [:space-y-4 :grow]}
         [sc/col {:class [:space-y-1 :truncate]}
          [sc/row {:class [:gap-2 :items-center :justify-between :truncate]}
           [sc/badge number]
           [:div.grow.truncate [sc/title category]]
           [sc/badge-2 location]]]
         [sc/col {:class [:px-4 :space-y-2 :grow]}
          [sc/text
           (interpose [:span.w-8.inline-flex.justify-center.items-center "•"]
                      (filter some? [brand (stability-str stability) description]))]
          [sc/text [sc/dim
                    (interpose [:span.w-8.inline-flex.justify-center.items-center "/"]
                               (filter some? [material weight length width]))]]]
         [sc/row {:class [:items-center :gap-2]}
          (scb/normal (sc/icon-large [:> solid/DotsHorizontalIcon]))
          [sc/surface-d {:class [:w-full :h-12]}]
          [:div {:on-click on-click}
           (if selected
             (scb/danger (sc/icon-large [:> solid/MinusCircleIcon]))
             (scb/cta (sc/icon-large [:> outline/PlusCircleIcon]))
             #_[sc/base-button [sc/icon-large [:> solid/XCircleIcon]]])]]]]
       [:div {:class (if selected [:selected] [:normal])}
        [sc/center
         [sc/row {:class [:gap-1]}
          (scb/normal (sc/icon-large [:> solid/DotsHorizontalIcon]))
          [sc/row {:class [:gap-2 :truncate :w-full]}
           [sc/badge number]
           [sc/badge-2 location]
           [sc/title category]]
          [:div {:on-click on-click}
           (if selected
             (scb/danger (sc/icon-large [:> solid/MinusCircleIcon]))
             (scb/cta (sc/icon-large [:> outline/PlusCircleIcon]))
             #_[scb/base-button [sc/icon-large [:> solid/XCircleIcon]]])]]]]))))
