(ns schpaa.style.hoc.page-controlpanel
  (:require [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [reagent.core :as r]
            [schpaa.debug :as l]))

(defn chevron-updown-toggle [st toggle caption]
  [:div.h-full.flex.items-center.w-full.px-3
   {:class    [:select-none :cursor-default]
    :on-click #(do
                 (toggle)
                 (.stopPropagation %))}
   [sc/row-sc-g2
    [sc/icon-tiny {:style {:transition          "100ms"
                           :z-index             0
                           :position            :relative
                           :transition-property "transform"
                           :color               "var(--brand1)" #_(if st "var(--text2)" "var(--text1)")
                           :transform           (if st "rotate(90deg)" "rotate(0deg)")}}
     ico/chevronDoubleRight]
    [sc/small0 {:style {:color          (if st "var(--text2)" "var(--text1)")
                        :text-transform "uppercase"
                        :font-weight    "var(--font-weight-5)"
                        :letter-spacing "var(--font-letterspacing-4)"}}
     caption]]])

(defprotocol PerstateP
  (toggle [t])
  (listen [t]))

(defrecord Perstate [tag]
  PerstateP
  (toggle [_]
    (schpaa.state/toggle tag))
  (listen [_]
    (schpaa.state/listen tag)))

(defn header [perstate attr caption]
  (let [open? (listen perstate)]
    [:div.h-12.items-center.flex
     #_(conj attr
             {:style {:background-color (if @open? "var(--toolbar)" "var(--floating)")}})
     [chevron-updown-toggle @open? #(toggle perstate) caption]]))

(defn togglepanel
  ([tag caption content]
   [togglepanel {} tag caption content false])
  ([tag caption content modify?]
   [togglepanel {} tag caption content modify?])
  ([attr tag caption content modify?]
   (let [perstate (Perstate. (if (= (:open attr) 0)
                               (r/atom false)
                               (if (nil? tag)
                                 (r/atom false)
                                 tag)))
         open? @(listen perstate)]
     [sc/surface-instillinger
      {:class [(if open? :open) :cursor-default :overflow-clip :w-full]}
      [header perstate attr caption]
      (when @(listen perstate)
        [sc/col-space-1
         {:style {:padding "var(--size-4)"}}
         (content modify?)])])))

(defn togglepanel-local
  ([caption content]
   [togglepanel-local caption content false])
  ([caption content modify?]
   (r/with-let [attr {:open 0}
                tag nil
                perstate (Perstate. (if (zero? (:open attr))
                                      (r/atom false)
                                      (if (nil? tag)
                                        (r/atom false)
                                        tag)))
                open? (listen perstate)]
     [sc/surface-instillinger
      {:class [(if @open? :open) :cursor-default :overflow-clip :w-full]}
      [header perstate attr caption]
      ;[l/pre @open? @(listen perstate)]
      (when @open?
        [sc/col-space-1
         {:style {:padding "var(--size-4)"}}
         (content modify?)])])

   #_(r/with-let [perstate (r/atom false)]
       [(if @perstate sc/surface-instillinger sc/surface-instillinger-closed)
        [sc/col
         [chevron-updown-toggle @perstate #(swap! perstate not) caption]
         (when @perstate
           [sc/col-space-4
            {:style {:padding-block  "var(--size-4)"
                     :padding-inline "var(--size-1)"}}
            (content modify?)])]])))