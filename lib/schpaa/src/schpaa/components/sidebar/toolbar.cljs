(ns schpaa.components.sidebar.toolbar
  (:require [schpaa.style :as s]
            [re-frame.core :as rf]
            [schpaa.components.widgets :as widgets]
            [schpaa.icon :as icon]))

(def tab-list [:list :bar-chart :cloud :brygge])

(defn- button
  [color-map selected-tab-id on-click tab-id]
  (let [[selected' normal icon] ((juxt :selected :normal :icon) (get color-map tab-id))]
    [:div.w-16.h-16.aspect-square.xdebug.flex.items-center.justify-center
     {:on-click #(on-click tab-id)
      :class    (flatten ((juxt :fg :bg) (if (= tab-id selected-tab-id) selected' normal)))}
     (icon/touch icon)]))

(defn toolbar
  [{:keys [color-map get-selected-tab set-selected-tab]}]
  (let [button' (partial button color-map (get-selected-tab) #(set-selected-tab %))]
    [:div.flex-shrink-0.scroll-y.overscroll-none.relative.h-16.bg-white
     {:class (concat
               [:mob:mb-0]
               s/sidebar-text-color-toolbar)}
     [:div.absolute.top-0.inset-x-0.overflow-hidden
      [:div.flex.text-2xl.font-serif.justify-between.items-center.w-full.h-16
       (into [:<>] (map button' tab-list))
       [button color-map (get-selected-tab) #(rf/dispatch [:toggle-menu-open]) :cross-out]]]]))
