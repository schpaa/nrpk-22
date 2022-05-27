(ns booking.header
  (:require [re-frame.core :as rf]
            [lambdaisland.ornament :as o]
            [kee-frame.core :as k]
            [schpaa.style.ornament :as sc]
            [booking.modals.mainmenu :refer [main-menu]]
            [booking.common-widgets :as widgets]))

;; styles

(o/defstyled header-overlay :div
  [:& :fixed :top-0 :noprint :inset-x-0 :h-16 :pointer-events-none
   {:height     "8rem"
    :background "linear-gradient(180deg,var(--content-transp-top) 0%, var(--content-transp-bottom) 5rem, var(--content-transp) 100%)"}])

(o/defstyled header-top :div
  [:&
   :gap-2
   {;:background "red"
    :display     "flex"
    :position    "relative"
    :align-items "center"
    :width       "100%"
    :margin      "auto"
    :max-width   "calc(768px - 3rem)"
    :height      "var(--size-9)"}])

(o/defstyled headerline :div
  [:&
   :truncate :pointer-events-auto
   {:width           "100%"
    :height          "4rem"
    :display         "flex"
    :justify-content "space-between"
    :align-items     "center"}])

;a clickable region
(o/defstyled location :div
  [:& :p-2 :truncate :space-y-1
   {:display         "flex"
    :flex-direction  "column"
    :justify-content "start"
    :width           "auto"
    :border-radius   "var(--radius-1)"
    :cursor          "pointer"
    :flex            "1 1 1"}
   [:&:active
    {:background "var(--text0-copy)"}]])

;; widgets

(defn see-also [{:keys [text link]}]
  (when link
    (let [caption (:text (widgets/lookup-page-ref-from-name link))
          href (k/path-for [link])]
      [sc/small0 {:class [:truncate]
                  :style {:white-space :nowrap
                          :font-weight "var(--font-weight-4)"}}
       "Se også "
       [sc/subtext-with-link
        {
         :class [:small :mb-1]
         :href  href}
        caption]])))


(defn location-block [r links caption right-menu?]
  (let [link (first links)]
    [location
     {:on-click #(rf/dispatch [:app/navigate-to [link]])
      :class    [(if right-menu? :mr-2x :ml-2x)
                 (if right-menu? :text-right :text-left)]}
     [sc/title1
      (or (if (fn? caption)
            (caption (some-> r :path-params))
            (if (map? caption)
              (:text caption "?caption")
              caption))
          caption)]
     (let [z {:link link
              :text caption}]
       (try (see-also z)
            (catch js/Error e {:CRASH/see-also z})))]))

(defn right-menu? []
  (when-let [s (rf/subscribe [:lab/screen-geometry])]
    (:right-menu? @s)))

(defn header-line [r headline-plugin scroll-pos]
  (let [[links caption] (some-> r :data :header)
        items (concat [[location-block r links caption (right-menu?)]
                       [:div.grow]]

                      (when goog.DEBUG
                        ;todo no use for scroll-pos yet
                        [[:div.flex.items-center.justify-center.mx-4.tabular-nums
                          [sc/small1 (when scroll-pos (times.api/format "%04d" scroll-pos))]]])

                      (when headline-plugin
                        (when-some [headline (seq (headline-plugin))]
                          [[:div.flex.px-3.pb-2.gap-3.-mt-2.pt-3
                            {:style {:border-radius           "var(--radius-2)"
                                     :border-top-right-radius 0
                                     :border-top-left-radius  0
                                     :background-color        "rgba(0,0,0,0.08)"}}
                            (into [:<>] headline)]]))

                      [[:div.mx-2 [main-menu r]]])
        items (if (right-menu?) (reverse items) items)]
    [headerline [header-top items]]))

(defn header [r scroll-pos headline-plugin]
  (let [{:keys [hidden-menu? right-menu? mobile? menu-caption?]} @(rf/subscribe [:lab/screen-geometry])
        marg (when-not hidden-menu?
               (when-not mobile? (if menu-caption? "14rem" "4rem")))]
    [header-overlay
     {:style {:margin-left  (when-not right-menu? marg)
              :margin-right (when right-menu? marg)}}
     [header-line r headline-plugin scroll-pos]]))
