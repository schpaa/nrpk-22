(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]))

(def header-id :header)

(defn render [{:keys [current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  [:div.h-16
   {:class (concat
             (when (get-menuopen-fn) [:mob:mr-80])
             [:transform-gpu
              :duration-200
              :z-10
              :fixed :inset-x-0 :top-0])}
   [:div.relative
    [:div.flex.items-center.justify-between.gap-2.px-4.h-16.select-none.xshadow-sm.relative
     {:class    (if (get-writingmode-fn) s/hdr-writingmode-color s/hdr-color)
      :on-click #(state/toggle header-id)}

     [:div.absolute.left-4.flex.gap-4
      [:div {:class    (if (= current-page :r.common) [] [:text-red-500])
             :on-click #(navigate-to-home)}
       [icon/touch :house]]
      [:div {:on-click #(navigate-to-user)}
       [icon/touch :circle-user]]]

     (if (get-writingmode-fn)
       [:div.flex-grow.mx-12
        {:class (concat
                  (if-not (get-menuopen-fn) [:mob:mr-12])
                  [:text-center])}
        [:div {:class [:text-lg :antialiased :tracking-tighter :leading-snug :font-bold]} "Skriving"]
        [:div.text-center.text-sm.font-normal "dokument-a"]]

       [:div.flex-grow
        {:on-click #(rf/dispatch [:app/navigate-to [:r.personal-articlelist]])
         :class    [:text-lg :antialiased :tracking-tighter :leading-snug :font-bold]}
        [:div.text-center "EYKT-22"]
        [:div.text-center.text-sm.font-normal (current-page-title)]])

     (when-not (get-menuopen-fn)
       [:div.absolute.right-4
        {:class    s/sidebar-header-color
         :on-click #(do
                      (.stopPropagation %)
                      (toggle-menu-open))}
        [icon/touch :double-chevron-left]])]]])