(ns nrpk.spa
  (:require [goog.events :as gevents]
            [re-frame.core :as rf]
            [kee-frame.router :refer [make-route-component]]
            [kee-frame.core]
            [breaking-point.core]))

(defonce _ (let [source (. js/window matchMedia "(prefers-color-scheme: dark)")
                 type gevents/EventType.CHANGE
                 dispatch #(schpaa.state/change :app/dark-mode (-> % .-target .-matches))]
             (gevents/listen source type dispatch)))

(rf/reg-sub :lab/screen-geometry
            :<- [:breaking-point.core/mobile?]
            (fn [a _]
              {:mobile?       a
               :hidden-menu?  (not @(schpaa.state/listen :lab/toggle-chrome))
               :menu-caption? @(schpaa.state/listen :app/toolbar-with-caption)
               :right-menu?   @(schpaa.state/listen :lab/menu-position-right)}))

(defn app-wrapper-clean
  [route-table]

  (let [route-name (rf/subscribe [:app/route-name])             
        route (rf/subscribe [:kee-frame/route])
        user-screenmode (schpaa.state/listen :app/dark-mode)
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if @user-screenmode "dark" "light"))
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--toolbar)")

    (if-let [page (get route-table @route-name)]
      [:div
       #_{:style {:color "white"
                  :background-color "var(--dim-brand1)"}}
       (make-route-component page @route)]
      [:div.bg-alt.fixed.inset-0.flex.items-center.justify-center "(subliminal :message)"])))
