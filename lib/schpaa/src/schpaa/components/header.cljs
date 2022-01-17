(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]))

(def header-id :header)

(defn icon-switch [{:as icon-b} {:as icon-a}]
  [:div.relative.text-white.p-4
   [:div
    {:class (:class icon-a)}
    [icon/touch (:icon icon-a)]]
   [:div.absolute.top-4.-right-1.w-4.h-4
    {:class (:class icon-b)}
    [icon/adapt (:icon icon-b) 1.4]]])

(defn render [{:keys [current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  [:div.fixed.h-16x.z-50
   {:class (concat
             ;(when (get-menuopen-fn) [:mob:mr-80])
             [:transform-gpu
              :transition-transform
              :duration-500
              :inset-x-0 :top-0])}

   #_[:div.bg-white.text-4xl.inter.font-inter
      [:div.font-thin "0123456789"]
      [:div.font-extralight "0123456789"]
      [:div.font-light "0123456789"]
      [:div.font-normal "0123456789"]
      [:div.font-medium "0123456789"]
      [:div.font-semibold "0123456789"]
      [:div.font-bold "0123456789"]
      [:div.font-extrabold "0123456789"]
      [:div.font-black "0123456789"]]

   [:div.flex.items-center.justify-between.gap-2.px-4.h-16.select-none.relative
    {:class    (concat
                 (if (get-writingmode-fn) s/hdr-writingmode-color s/hdr-color)
                 (if (get-menuopen-fn) [:mob:mr-80]))
     :on-click #(state/toggle header-id)}

    [:div.absolute.left-0.flex;.duration-200.transition-colors.transition-transform
     (if (some #{(current-page)} [:r.common :r.boatlist :r.new-booking])
       [:div {:on-click #(navigate-to-user)}
        [icon-switch
         {:icon :circle-user :class [:text-black :dark:text-white]}
         {:icon :house :class [:text-alt :dark:text-alt]}]]

       [:div {:on-click #(navigate-to-home)}
        [icon-switch
         {:icon :house :class [:text-alt :dark:text-alt]}
         {:icon :circle-user :class [:text-black :dark:text-white]}]])]

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
       [:div.text-center.font-bold.tracking-wide
        (current-page-title)]
       [:div.text-center.text-sm.tracking-normal.uppercase
        {:class ["text-black/40"
                 "dark:text-white/50"]}
        [:div {:class [:hidden :xs:block]} "Booking på NRPK Sjøbasen"]
        [:div {:class [:block :xs:hidden]} "NRPK Sjøbasen"]]])

    (when-not (get-menuopen-fn)
      [:div.absolute.right-4
       {:class    s/sidebar-header-color
        :on-click #(do
                     (.stopPropagation %)
                     (toggle-menu-open))}
       [icon/touch :double-chevron-left]])]])