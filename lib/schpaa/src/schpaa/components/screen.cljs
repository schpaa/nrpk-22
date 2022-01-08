(ns schpaa.components.screen
  (:require [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [schpaa.components.header :as components.header]
            [schpaa.components.sidebar :as components.sidebar]))

(defn- overlay [*menu-open?]
  [:div.mob:invisible.fixed.inset-0.z-100
   {:on-click #(when *menu-open?
                 (rf/dispatch [:toggle-menu-open]))
    :class    (if *menu-open?
                [:bg-gray-900 :bg-opacity-50 :duration-500 :pointer-events-auto]
                [:duration-200 :bg-opacity-0 :pointer-events-none])}])

(defn render [{:keys [get-menuopen-fn get-writingmode-fn] :as accessors} web-content editor]
  [:<>
   [:div.flex.justify-between.min-h-screen.bg-gray-200
    {:class []}
    [:div.flex.flex-col.flex-grow
     {:class [:transform-gpu]}

     [components.header/render accessors]

     (if (get-writingmode-fn)
       [:div {:style {:margin-top     "4rem"
                      :xmargin-bottom "4rem"}}
        editor
        [:div.h-16.flex.justify-between.items-center
         #_{:class s/hdr-writingmode-footer-color}
         [:div.flex-shrink-0.w-16.h-full
          [:div.center [icon/touch :square]]]
         [:div.flex-shrink-0.w-16.h-full.self-center
          [:div.center [icon/touch :square]]]]]

       [:div.p-3.mt-16 web-content])]

    [:div.flex-shrink-0
     {:class (concat (if (get-menuopen-fn) ["translate-x-0"]
                                           ["translate-x-full"])
                     (if (get-menuopen-fn) [:mob:w-80 :w-80
                                            :mob:drop-shadow
                                            :drop-shadow-2xl] [:mob:w-0 :w-80])
                     [:transform-gpu
                      :overflow-hidden
                      :origin-top-right
                      :z-200
                      :duration-200
                      :fixed
                      :top-0
                      :right-0
                      :w-full
                      :xs:w-80
                      :bottom-0
                      :rounded-l
                      :h-full
                      :mob:relative
                      :mob:sticky :mob:top-0 :mob:bottom-0
                      :mob:min-h-screen])}
     [components.sidebar/render accessors]]]
   [overlay (get-menuopen-fn)]])