(ns schpaa.components.header
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [schpaa.state :as state]
            [schpaa.style :as s]
            [schpaa.icon :as icon]
            [schpaa.components.views :as views]
            [schpaa.style :as st]
            [schpaa.debug :as l]))

(def header-id :header)

(defn icon-switch [menu-direction {:as icon-a} {:as icon-b}]
  [:div.relative.text-white.p-4
   [:div
    {:class (concat (:class icon-a))}
    [icon/touch (:icon icon-a)]]
   #_[:div.absolute.w-5.h-5
      {:class (concat ["top-2.5"] (if menu-direction
                                    (concat [:right-2] (:class icon-b))
                                    (concat [:left-2] (:class icon-b))))}
      [icon/adapt (:icon icon-b) 1.4]]])

(defn left-icon [{:keys [user-auth navigate-to-user navigate-to-home current-page]}]
  (let [{:keys [bg fg br]} (st/fbg' :header)
        frontpage? (or
                     (some #{(current-page)} [:r.new-booking :r.forsiden :r.boatlist])
                     (some #{(current-page)} [:r.forsiden :r.kalender :r.annet]))]
    (if frontpage?
      [:div.flex.flex.flex-center
       {:on-click #(navigate-to-user)
        :class    (concat [:rounded-md :hover:bg-gray-200 :m-2]
                          br fg)}
       [:div.relative
        [:div {:on-click #(navigate-to-user)}
         [icon/touch :circle-user]]
        (when-not (user-auth)
          [:div.absolute.inset-0.animate-slow-ping.text-alt
           {:on-click #(navigate-to-user)}
           [icon/touch :circle-filled]])]]

      [:div.flex.flex.flex-center
       {:on-click #(navigate-to-home)
        :class    (concat [:rounded-md :hover:bg-gray-200 :m-2]
                          br fg)}
       [:div
        {:on-click #(navigate-to-home)}
        [icon/touch :house]]])))

(defn right-icon [{:keys [menu-direction get-menuopen-fn toggle-menu-open]}]
  (let [{:keys [bg fg br]} (st/fbg' :header)]
    (when-not (get-menuopen-fn))
    [:div.xw-16.xh-16.flex.flex.flex-center
     {:class    (concat
                  [:rounded-md
                   :hover:bg-gray-200
                   :xgroup-hover:border-2
                   :m-2]
                  br
                  bg fg #_(if menu-direction [:rounded-bl-lg]
                                             [:rounded-br-lg]))
      :on-click #(do
                   (.stopPropagation %)
                   (toggle-menu-open))}
     (let [icon (-> @(rf/subscribe [:schpaa.components.widgets/get-tab]) st/color-map :icon)]
       [icon/touch (if (get-menuopen-fn)
                     nil
                     (or (keyword icon) :hamburger))])]))

(defn home-icon [current-page]
  (let [icon (if (or
                   (some #{(current-page)} [:r.new-booking :r.forsiden :r.boatlist])
                   (some #{(current-page)} [:r.forsiden :r.kalender :r.annet]))
               :house
               :circle-user)]
    [:div.w-5.h-5 {:class [:text-alt]} [icon/adapt icon 1.4]]))

(rf/reg-sub :app/name :-> :app/name)

(defn title [{:keys [current-page menu-direction current-page-title current-page-subtitle user-auth] :as m}]
  (let [{:keys [fg- fg fg+ p+ p- p]} (st/fbg' :header)
        sub-text (if (user-auth) (:display-name (user-auth)) "Registrer deg / Logg inn")
        app-name @(rf/subscribe [:app/name])
        title (current-page-title)
        [long-form short-form] (if (vector? title) title [title nil])
        sub-header (if (user-auth) (current-page-subtitle) "Velkommen")]
    (if menu-direction
      [:div.flex.items-center.antialiased
       {:class [:justify-start :text-lg :antialiased :leading-snug :font-bold]}
       [:div.leading-none.whitespace-nowrap
        {:class (concat [:text-left] fg p-)}
        [:div.py-0.flex.items-center.divide-dotted.divide-x-2.divide-gray-400
         {:class (concat fg p+ [:leading-5])}
         [:span.pr-2 (home-icon current-page)]
         [:span.pl-2 sub-header " · " app-name]]
        [:div.leading-5 {:class (concat fg+ p-)} sub-text]]]

      [:div.flex.items-center.antialiased
       {:class [:justify-end :text-lg :antialiased :leading-snug :font-bold]}
       [:div.leading-none.whitespace-nowrap
        {:class (concat [:text-right] fg p-)}
        [:div.py-0.flex.items-center.divide-dotted.divide-x-2.divide-gray-400
         {:class (concat fg p+ [:leading-5])}
         [:span.pr-2 app-name " · " sub-header]
         [:span.pl-2 (home-icon current-page)]]
        [:div.leading-5 {:class (concat fg+ p-)} sub-text]]])))

(defn render [{:keys [menu-direction] :as m}]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
    [:div.grid.w-full.group
     {:class bg
      :style {:grid-template-columns (if menu-direction "4rem 1fr 4rem" "4rem 1fr 4rem")
              :grid-auto-rows        "4rem"}}
     (into [:<>]
           (cond-> [[right-icon m]
                    [:div.self-center [title m]]
                    [left-icon m]]
             menu-direction reverse))]))
