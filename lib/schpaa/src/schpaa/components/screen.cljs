(ns schpaa.components.screen
  (:require [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [schpaa.components.header :as components.header]
            [schpaa.components.sidebar :as components.sidebar]
            [schpaa.components.widgets :as widgets]))

(defn- overlay [*menu-open?]
  [:div.mob:invisible.fixed.inset-0.z-200
   {:on-click #(when *menu-open?
                 (rf/dispatch [:toggle-menu-open]))
    :class    (if *menu-open?
                ["bg-gray-900/80" :duration-500 :pointer-events-auto]
                [:xduration-200 :bg-opacity-0 :duration-500 :pointer-events-none])}])

(defn render [{:keys [get-menuopen-fn get-writingmode-fn] :as accessors} web-content]
  [:div.flex.flex-col.min-h-screen
   {:class [(when (get-menuopen-fn) :mob:pr-80)
            :transform :duration-200
            :bg-gray-600
            :overflow-x-hidden
            :dark:bg-black
            :dark:text-gray-300]}
   [components.header/render accessors]
   [:div.flex.flex-col.flex-grow.xmax-w-3xl web-content]
   [overlay (get-menuopen-fn)]
   [:div.fixed.right-0
    {:class (concat [:transform :duration-200]
                    [:xs:border-l-4 :border-black :shadow-xl :z-200]
                    [:origin-top-right :xs:w-80 :border-l-0 :w-full]
                    [(if (get-menuopen-fn) :translate-x-0 :translate-x-full)])}
    [components.sidebar/render
     (conj accessors
           {:uid              (fn [] 123)
            :set-selected-tab (fn [e] (rf/dispatch [::widgets/set-tab e]))
            :get-selected-tab (fn [] @(rf/subscribe [::widgets/get-tab]))
            :current-tab      (fn [] 0)})]]])