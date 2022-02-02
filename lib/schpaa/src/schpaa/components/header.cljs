(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]
            [schpaa.components.views :as views]
            [schpaa.style :as st]))

(def header-id :header)

(defn icon-switch [menu-direction {:as icon-a} {:as icon-b}]
  [:div.relative.text-white.p-4
   [:div
    {:class (:class icon-a)}
    [icon/touch (:icon icon-a)]]
   [:div.absolute.top-4.w-4.h-4
    {:class (if menu-direction
              (concat [:-right-1] (:class icon-b))
              (concat [:-left-1] (:class icon-b)))}
    [icon/adapt (:icon icon-b) 1.4]]])

(defn left-icon [{:keys [menu-direction current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
    (if (some #{(current-page)} [:r.forsiden :r.mine-vakter :r.common2])
      [:div {:on-click #(navigate-to-user)}
       (icon-switch
         menu-direction
         {:icon :circle-user :class fg+}
         {:icon :house :class [:text-alt :dark:text-alt]})]
      [:div {:on-click #(navigate-to-home)}
       (icon-switch
         menu-direction
         {:icon :house :class fg+}
         {:icon :circle-user :class [:text-alt :dark:text-alt]})])))

(defn right-icon [{:keys [menu-direction current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  (let [{:keys [bg fg- fg fg+ p+ hd p p- he]} (st/fbg' :surface)]
    (when-not (get-menuopen-fn))
    [:div.w-16.h-16.flex.flex.flex-center
     {:class    (concat bg fg+)
      :on-click #(do
                   (.stopPropagation %)
                   (toggle-menu-open))}
     [icon/touch (if (get-menuopen-fn)
                   (if menu-direction
                     :arrow-right
                     :arrow-left)
                   :hamburger)]]))

(defn title [{:keys [current-page-title current-page-subtitle]}]
  (let [{:keys [bg fg- fg fg+ p+ hd p p- he]} (st/fbg' :surface)]
    [:div.flex-grow
     {:on-click #(rf/dispatch [:app/navigate-to [:r.personal-articlelist]])
      :aclass   (concat
                  [:text-lg :antialiased :tracking-tighter :leading-snug :font-bold])}
     (let [title (current-page-title)
           [long-form short-form] (if (vector? title) title [title nil])
           sub-header (current-page-subtitle)]
       [:div.text-center.leading-none.whitespace-nowrap
        {:class (concat fg- p-)}
        [:div {:class [:hidden :xs:block]} long-form]
        [:div {:class [:block :xs:hidden]} short-form]
        (when sub-header [:div {:class (concat fg+ p+)} sub-header])])]))

(defn render [{:keys [menu-direction] :as m}]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
    [:div.grid.w-full
     {:class bg
      :style {:grid-template-columns "4rem 1fr 4rem"
              :grid-auto-rows        "4rem"}}
     (into [:<>]
           (cond-> [[:div [right-icon m]]
                    [:div.self-center [title m]]
                    [left-icon m]]
             menu-direction reverse))]))
                          
