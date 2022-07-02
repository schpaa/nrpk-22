(ns nrpk.spa
  (:require [goog.events :as gevents]
            [re-frame.core :as rf]
            [lambdaisland.ornament :as o]
            [kee-frame.router :refer [make-route-component]]
            [kee-frame.core]
            [breaking-point.core]
            [schpaa.debug :as l]
            [booking.styles :as b]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as button]))

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


(o/defstyled fixed-inset-0 :div
  :inset-0 :fixed)

(def stagnant-message
  [fixed-inset-0 
   [b/center
    [b/co0
     [b/small "This is an icon and some text:"]
     [b/ro
      [button/just-icon {:class [:large]} ico/check]
      [b/title "Aa Bb Only titles and short stuff"]]
     [b/small "This is an icon and some text:"]
     [b/ro
      [button/just-icon {:class [:small]} ico/check]
      [b/text "Aa Bb  All text is built with this size"]]
     [b/small {:style {:opacity 0.5
                       :text-transform "unset"}} "Aa Bb This text is small because it's secondary and should not capture attention"]
     [b/co
      [b/small "This is icon and text as a widget"]
      [button/icon-and-caption {:class [:large]} ico/backspace "Large thing"]
      [button/icon-and-caption {;:style {:background "red"}
                                :class [:large :inverse]} ico/checkCircle "Small thing"]]]]])

(defn app-wrapper-clean [route-table]
  (let [route-name (rf/subscribe [:app/route-name])
        route (rf/subscribe [:kee-frame/route])
        user-screenmode (schpaa.state/listen :app/dark-mode)
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if @user-screenmode "dark" "light"))
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--toolbar)")

    (if-let [page (get route-table @route-name)]
      [:div (make-route-component page @route)]
      stagnant-message)))

