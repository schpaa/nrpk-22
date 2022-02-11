(ns schpaa.components.screen
  (:require [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [schpaa.components.header :as components.header]
            [schpaa.components.sidebar :as components.sidebar]
            [schpaa.components.widgets :as widgets]
            [schpaa.debug :as l]))

(defn- overlay [*menu-open?]
  [:div.xmob:invisible.fixed.inset-0.z-200
   {:on-click #(when *menu-open?
                 (rf/dispatch [:toggle-menu-open]))
    :class    (if *menu-open?
                ["bg-gray-900/40" :duration-500 :pointer-events-auto]
                [:xduration-200 :bg-opacity-0 :duration-500 :pointer-events-none])}])

(defn render [{:keys [get-menuopen-fn menu-direction] :as m} web-content]
  (let [direction (if menu-direction :-translate-x-80 :translate-x-80)
        route (rf/subscribe [:kee-frame/route])]
    [:<>
     (when-not (= (some-> @route :data :name) :r.page-not-found)
       [:div.sticky.top-0.z-200
        {:class [:duration-200
                 (if (get-menuopen-fn) direction)]}
        [components.header/render m]])
     [:div {:class [:duration-200 (if (get-menuopen-fn) direction)]}
      web-content]
     [overlay (get-menuopen-fn)]
     [components.sidebar/render
      (conj m
            {:uid              (fn [] 123)
             :set-selected-tab (fn [e] (rf/dispatch [::widgets/set-tab e]))
             :get-selected-tab (fn [] @(rf/subscribe [::widgets/get-tab]))
             :current-tab      (fn [] 0)})]]))
