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
    [sc/icon-tiny {:style {:transition          "1s"
                           :transition-property "transform"
                           :color               "var(--text1)"
                           :transform           (if st "rotate(45deg)" "rotate(0deg)")}}
     [:> outline/ChevronRightIcon]]
    [sc/small1 {:style {:margin-left    "4px"
                        :color          "var(--text0)"
                        :text-transform "uppercase"
                        :letter-spacing "var(--font-letterspacing-3)"}}
     title]]])

(defn- togglepanel' [{:keys [content open? toggle title]}]
  [(if open? sc/surface-instillinger :div.-m-4.p-4)
   [sc/col
    [chevron-updown-toggle open? toggle title]
    (when open?
      (content))]])

(defprotocol PerstateP
  (toggle [t])
  (listen [t]))

(defrecord Perstate [tag]
  PerstateP
  (toggle [t]
    #(schpaa.state/toggle tag))
  (listen [t]
    (schpaa.state/listen tag)))

(defn togglepanel
  ([tag caption content]
   [togglepanel tag caption content false])
  ([tag caption content modify?]
   (let [perstate (Perstate. tag)]
     [(if @(listen perstate) sc/surface-instillinger sc/surface-instillinger-closed)
      [sc/col
       [chevron-updown-toggle @(listen perstate) (toggle perstate) caption]
       (when @(listen perstate)
         [sc/col-space-2
          {:style {:padding-block  "var(--size-4)"
                   :padding-inline "var(--size-4)"}}
          (content modify?)])]])))


