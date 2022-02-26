(ns schpaa.style.menu
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]))

(o/defstyled menu-item :div
  {:background "var(--surface000)"
   :color      "var(--surface5)"}
  [:.icon-color {:color "var(--surface3)"}]
  [:button.active-color {:background-color "var(--surface2)"
                         :color            "var(--surface000)"}]
  ([icon label action]
   [ui/menu-item
    (fn [{:keys [active]}]
      [:button {:on-click #(action)
                :class    (into [:h-12 :rounded-sm :gap-4
                                 :group :flex :justify-start :items-center :w-full :px-2 :py-2 :text-sm]
                                (if active [:active-color] [:xtext-gray-900]))}
       [:> icon
        {:class       [:h-6 :w-6 :ml-1 (if active :text-green-400 :icon-color)]
         :aria-hidden true}]
       label])]))

(o/defstyled menu-items :div
  :absolute :right-1 :overflow-visible :w-56 :rounded-sm :ring-1 :ring-black :ring-opacity-5 :focus:outline-none
  {:outline "2px var(--surface1) solid"})

(o/defstyled menu-items-up menu-items
  :bottom-12 :mb-2)

(o/defstyled menu-items-down menu-items
  :top-12 :mt-2)

(defn menu-example-with-args [{:keys [dir data always-show]}]
  [:div.relative
   [ui/menu {:as :div.relative.inline-block.text-left.overflow-visible}
    [ui/menu-button {:as :div}
     (fn [{:keys [open]}]
       [sc/button-round
        [:> (if open solid/DotsVerticalIcon
                     solid/DotsHorizontalIcon) {:class       [:h-5 :w-5]
                                                :aria-hidden true}]])]
    [ui/transition
     {;:show       always-show
      :enter      "transition ease-out duration-100"
      :enter-from "opacity-0 "
      :enter-to   "opacity-100"
      :leave      "transition ease-in duration-75"
      :leave-from "opacity-100"
      :leave-to   "opacity-0 "}
     [ui/menu-items
      [(if (= :down dir) menu-items-down menu-items-up)
       (for [e data]
         (if-let [[icon caption action] e]
           [menu-item icon caption action]
           [:hr {:style {:background         "var(--surface000)"
                         :color              "var(--text2)"
                         :border-top         "1px solid var(--surface1)"
                         :border-right-width "0px"
                         :border-left-width  "0px"}}]))]]]]])