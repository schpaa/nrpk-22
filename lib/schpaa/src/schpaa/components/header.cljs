(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]))

(def header-id :header)

(defn icon-switch [{:as icon-a} {:as icon-b}]
  [:div.relative.text-white.p-4
   [:div
    {:class (:class icon-a)}
    [icon/touch (:icon icon-a)]]
   [:div.absolute.top-4.-right-1.w-4.h-4
    {:class (:class icon-b)}
    [icon/adapt (:icon icon-b) 1.4]]])

(defn render [{:keys [current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  [:div.fixed.z-50.inset-x-0
   {:class (if (get-menuopen-fn) [:mob:mr-80])}
   [:div.max-w-3xl.mx-auto.block.bg-alt
    {
     :class (concat
              [:transform-gpu
               :transition-all
               :duration-500
               :xinset-x-0 :top-0])}

    [:div.flex.items-center.justify-between.gap-2.px-4.h-16.select-none.relative.w-full
     {:class    (concat
                  (if (get-writingmode-fn) s/hdr-writingmode-color s/hdr-color)
                  (if (get-menuopen-fn) [:mob:mr-80]))
      :on-click #(state/toggle header-id)}

     [:div.absolute.left-0.flex
      (if (some #{(current-page)} [:r.common :r.boatlist :r.new-booking])
        [:div {:on-click #(navigate-to-user)}
         [icon-switch
          {:icon :circle-user :class [:text-black  :dark:text-white]}
          {:icon :house :class [:text-alt  :dark:text-alt]}]]

        [:div {:on-click #(navigate-to-home)}
         [icon-switch
          {:icon :house :class [:text-black :dark:text-white]}
          {:icon :circle-user :class [:text-alt :dark:text-alt]}]])]

     (if (get-writingmode-fn)
       [:div.flex-grow.mx-12
        {:class (concat
                  (if (get-menuopen-fn)
                    [:mob:pr-80]
                    [:mob:mr-12])
                  [:text-center])}
        [:div {:class [:text-lg :antialiased :tracking-tighter :leading-snug :font-bold]} "Skriving"]
        [:div.text-center.text-sm.font-normal "dokument-a"]]

       [:div.flex-grow
        {:on-click #(rf/dispatch [:app/navigate-to [:r.personal-articlelist]])
         :class    (concat
                           [:text-lg :antialiased :tracking-tighter :leading-snug :font-bold])}
        ;[:div.text-center.font-semibold.tracking-wide.text-base (current-page-title)]
        [:div.text-center.text-sm.tracking-wide.uppercase.font-bold
         {:class ["text-gray-500"
                  "dark:text-gray-500"]}
         [:div {:class [:hidden :xs:block]} "NRPK · Sjøbasen Booking"]
         [:div {:class [:block :xs:hidden]} "NRPK · Booking"]]])

     (when-not (get-menuopen-fn)
       [:div.absolute.right-4
        {:class    s/sidebar-header-color
         :on-click #(do
                      (.stopPropagation %)
                      (toggle-menu-open))}
        [icon/touch :double-chevron-left]])]]])