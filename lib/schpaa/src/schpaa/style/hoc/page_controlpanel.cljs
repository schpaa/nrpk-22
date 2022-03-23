(ns schpaa.style.hoc.page-controlpanel
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
    [sc/icon-tiny {:style {:color "var(--surface5)"}
                   :class [:duration-200 (if st :rotate-90)]}
     [:> outline/ChevronRightIcon]]
    [sc/small1 {:style {:color "var(--surface5)"}
                :class [:tracking-wider :uppercase]} title]]])

(defn- togglepanel' [{:keys [content open? toggle title]}]
  [sc/col {:class []}
   (chevron-updown-toggle open? toggle title)
   (when open?
     (content))])

(defprotocol PerstateP
  (toggle [t])
  (listen [t]))

(defrecord Perstate [tag]
  PerstateP
  (toggle [t]
    #(schpaa.state/toggle tag))
  (listen [t]
    (schpaa.state/listen tag)))

(defn togglepanel [tag caption content]
  (let [perstate (Perstate. tag)]
    [togglepanel'
     {:open?   @(listen perstate)
      :toggle  (toggle perstate)
      :title   caption
      :content (fn [] [sc/col-space-2 {:style {:padding-block "1rem"}} (content)])}]))

