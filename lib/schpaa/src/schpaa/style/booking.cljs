(ns schpaa.style.booking
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [booking.views.picker :refer [draw-graph]]
            [tick.core :as t]
            [schpaa.debug :as l]
            [schpaa.icon :as icon]))

(defn stability-str [st]
  (case st
    1 "Nybegynner"
    2 "Stabil"
    3 "Ustabil"
    4 "Utfordrende"
    nil))

(o/defstyled line :div
  [:& :min-w-fit]
  [:.selected sc/small-padding sc/selected-cell sc/rounded]
  [:.normal sc/small-padding sc/normal-cell sc/rounded]
  ([{:keys [selected expanded on-click content]}]
   (let [selected selected
         {:keys [number location category brand weight length width description material stability]} content]
     (if expanded
       [sc/col {:class [(if selected :selected :normal) :h-full :justify-between]}
        [sc/col {:class [:space-y-2 :grow :p-2]}
         [sc/col {:class [:truncate]}
          [sc/row {:class [:gap-2 :items-center :justify-between :truncate]}
           [sc/badge number]
           [:div.grow.truncate [sc/title {:class (when selected [:text-white])} category]]
           [sc/badge-2 location]]]
         [sc/col {:class [:px-2 :py-2 :space-y-2 :grow]}
          [sc/text-1 {:class (when selected [:text-white])}
           (interpose [:span.w-6.inline-flex.justify-center.items-center "•"]
                      (filter some? [(when brand [sc/bold brand])
                                     (stability-str stability)]))]

          [sc/text description]

          [sc/text-1 {:class (when selected [:text-white])}
           (interpose [:span.w-6.inline-flex.justify-center.items-center "/"]
                      (filter some? [material weight length width]))]]
         [sc/row {:class [:items-center :gap-2]}
          (scb/clear (sc/icon-large [:> solid/DotsHorizontalIcon]))
          ;;todo find a surface h
          [sc/surface-d {:class [:w-full :h-12 :overflow-clip]}
           [draw-graph {:date      (t/date (t/now))
                        :window    {:width (* 24 4) :offset 1}
                        :time-slot {:from 32 :to 41}
                        :list      [{:start 1 :end 10 :r? true}
                                    {:start 15 :end 40 :r? false}
                                    {:start 78 :end 180 :r? true}]}]]
          [:div {:on-click on-click}
           (if selected
             (scb/danger (sc/icon-large [:> solid/MinusCircleIcon]))
             (scb/clear (sc/icon-large [:> solid/PlusCircleIcon])))]]]]

       [:div {:class (if selected [:selected] [:normal])}
        [sc/row {:class [:gap-2 :items-center]}
         (scb/clear (sc/icon-large [:> solid/DotsHorizontalIcon]))
         [sc/row {:class [:gap-2 :truncate :w-full]}
          [sc/badge number]
          [sc/title {:class [(when selected :text-white) :grow]} category]
          [sc/badge-2 location]]
         [:div {:on-click on-click}
          (if selected
            (scb/danger (sc/icon-large [:> solid/MinusCircleIcon]))
            (scb/clear (sc/icon-large [:> solid/PlusCircleIcon])))]]]))))


(o/defstyled collapsable-type-card :div
  ([{:keys [selected expanded on-click content]}]
   (let [{:keys [number location category brand weight length width description material stability]} content]
     [sc/surface-b-sans-bg {:class [:p-1]
                            :style {:border-radius "var(--radius-2)"}}

      [sc/row {:class [:p-1 :gap-2]}
       (scb/clear {:on-click on-click} (sc/icon-large (if expanded
                                                        [:> solid/ChevronDownIcon]
                                                        [:> solid/ChevronRightIcon])))
       [sc/col {:class [:grow :space-y-px]}
        [sc/text-1 brand]
        [sc/subtext category]]]

      (when expanded
        [sc/col {:class [:p-2 :space-y-2 :grow]}
         [sc/text description]
         [sc/text-1 {:class (when selected [:text-white])}
          (interpose [:span.w-6.inline-flex.justify-center.items-center "/"]
                     (filter some? [material weight length width]))]])])))

(o/defstyled line-with-graph :div
  [:.selected {:background "var(--brand1)"}]
  [:.normal {:background "var(--surface1)"}]
  ([{:keys [content on-click selected]}]
   (let [{:keys [number loc]} content]
     [sc/surface-b-sans-bg {:class [:p-2 (if selected :selected :normal)]
                            :style {:border-radius "var(--radius-2)"}}
      [sc/row {:class [:items-center :gap-2]}
       [sc/badge number]
       [sc/surface-d {:class [:w-full :h-12 :overflow-clip]}
        [draw-graph {:date      (t/date (t/now))
                     :window    {:width (* 24 4) :offset 1}
                     :time-slot {:from 32 :to 41}
                     :list      [{:start 1 :end 10 :r? true}
                                 {:start 15 :end 40 :r? false}
                                 {:start 78 :end 180 :r? true}]}]]
       [sc/badge-2 loc]
       [:div {:on-click on-click}
        (if selected
          (scb/clear {:class [:text-white]} (sc/icon-large [:> solid/CheckCircleIcon]))
          (scb/clear (sc/icon-large (icon/adapt :circle 3))))]]])))

(o/defstyled selection-with-badges :div
  [:.selected {:background "var(--brand1)"}]
  ;[:.normal {:background "var(--surface1)"}]
  ([{:keys [content on-click selected]}]
   (let [{:keys [number loc brand category]} content]
     [sc/surface-b {:class []
                    :style {:border-radius "var(--radius-2)"}}
      [sc/row {:class [:items-center :gap-2]}
       [sc/badge number]
       [sc/col {:class [:grow :space-y-px]}
        [sc/text-1 brand]
        [sc/subtext category]]

       #_[sc/surface-d {:class [:w-full :h-12 :overflow-clip]}
          [draw-graph {:date      (t/date (t/now))
                       :window    {:width (* 24 4) :offset 1}
                       :time-slot {:from 32 :to 41}
                       :list      [{:start 1 :end 10 :r? true}
                                   {:start 15 :end 40 :r? false}
                                   {:start 78 :end 180 :r? true}]}]]
       [sc/badge-2 loc]
       [:div {:on-click on-click}
        (scb/danger (sc/icon-large [:> solid/XCircleIcon]))]]])))

