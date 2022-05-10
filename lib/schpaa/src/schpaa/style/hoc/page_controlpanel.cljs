(ns schpaa.style.hoc.page-controlpanel
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [reagent.core :as r]))

(defn chevron-updown-toggle [st toggle caption]
  [:div
   {:class    [:select-none :cursor-default]
    :on-click #(do
                 (tap> "chevron-updown-toggle")
                 (toggle)
                 (.stopPropagation %))}
   [sc/row-sc-g2
    [sc/icon-tiny {:style {:transition          "100ms"
                           :transition-property "transform"
                           :color               "var(--text1)"
                           :transform           (if st "rotate(90deg)" "rotate(0deg)")}}
     [:> outline/ChevronRightIcon]]
    [sc/text0 {:style {:margin-left    "4px"
                       :color          (if st "var(--text2)" "var(--text0)")
                       ;:font-size      "var(--font-size-0)"
                       :font-size      "80%"
                       :font-weight    "var(--font-weight-4)"
                       :text-transform "uppercase"
                       :letter-spacing "var(--font-letterspacing-4)"}}
     caption]]])

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
   [togglepanel {} tag caption content false])
  ([tag caption content modify?]
   [togglepanel {} tag caption content modify?])
  ([attr tag caption content modify?]
   (let [perstate (Perstate. (if (= (:open attr) 0)
                               (r/atom false)
                               (if (nil? tag) (r/atom false) tag)))
         open? @(listen perstate)]
     [sc/surface-instillinger
      {:class [:cursor-default (if open? :open)
               ;:-debug2
               :-ml-4
               :pr-4
               :mr-8
               :w-full]}
      [:div.h-12.px-4.x-mx-4.x-mt-4.items-center.flex
       (assoc attr :on-click (toggle perstate))
       [chevron-updown-toggle @(listen perstate) (toggle perstate) caption]]
      (when @(listen perstate)
        [sc/col-space-1
         {:style {:padding "var(--size-4)"}}
         (content modify?)])])))

(defn togglepanel-local
  ([caption content]
   [togglepanel-local caption content false])
  ([caption content modify?]
   (r/with-let [perstate (r/atom false)]
     [(if @perstate sc/surface-instillinger sc/surface-instillinger-closed)
      [sc/col
       [chevron-updown-toggle @perstate #(swap! perstate not) caption]
       (when @perstate
         [sc/col-space-4
          {:style {:padding-block  "var(--size-4)"
                   :padding-inline "var(--size-1)"}}
          (content modify?)])]])))