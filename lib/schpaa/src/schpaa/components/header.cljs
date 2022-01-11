(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]))

(def header-id :header)

(defn render [{:keys [current-page-title navigate-to-user navigate-to-home get-writingmode-fn current-page get-menuopen-fn toggle-menu-open]}]
  [:div.h-16.shadow-sm
   {
    :class (concat
             ;(when (get-menuopen-fn) [:mob:mr-80])
             [:transform-gpu
              :duration-200
              :z-10
              :fixed :inset-x-0 :top-0])}
   #_[:div.relative.overflow-x-auto.flex.justify-between
      {:style {:widtxh "32remx"
               :min-width "40rem"}}
      [:div "A"]
      [:div "B"]
      [:div "C"]]
   [:div.flex.items-center.justify-between.gap-2.px-4.h-16.select-none.relative
    {:class    (if (get-writingmode-fn) s/hdr-writingmode-color s/hdr-color)
     :on-click #(state/toggle header-id)}
    [:div.absolute.left-0.flex
     [:div {:class    (if (some #{(current-page)} [:r.common :r.boatlist]) [:bg-white :dark:bg-gray-600 :text-red-500 :dark:text-red-500] ["text-red-500/50"])
            :on-click #(navigate-to-home)}
      [:div.p-4 [icon/touch :house]]]
     [:div {:class    (if (some #{(current-page)} [:r.user :r.my-bookings]) [:bg-white :dark:bg-gray-600 :text-black :dark:text-gray-200] ["text-black/50" :dark:text-gray-400])
            :on-click #(navigate-to-user)}
      [:div.p-4 [icon/touch :circle-user]]]]
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
       [:div.text-center.text-sm.font-normal.tracking-wide {:class ["text-black/50"
                                                                    "dark:text-white/50"]} (current-page-title)]])

    (when-not (get-menuopen-fn)
      [:div.absolute.right-4
       {:class    s/sidebar-header-color
        :on-click #(do
                     (.stopPropagation %)
                     (toggle-menu-open))}
       [icon/touch :double-chevron-left]])]])