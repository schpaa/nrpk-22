(ns booking.page-controlpanel
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]))

(defn chevron-updown-toggle [st toggle title]
  [:div
   {:class    [:select-none :cursor-pointer]
    :on-click #(do
                 (.stopPropagation %)
                 (toggle))}
   [sc/row-sc-g2
    [sc/icon-tiny
     [:> (if st outline/ChevronUpIcon outline/ChevronDownIcon)]]
    [sc/small {:style {:color "var(--surface5)"}
               :class [:tracking-wider :uppercase]} title]]])

(defn standard [{:keys [content open? toggle title]}]
  [sc/col-space-2 {:class [:-debug]}
   (chevron-updown-toggle open? toggle title)
   (when open?
     (content))])
