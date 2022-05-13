(ns nrpk.spa
  (:require [goog.events :as gevents]
            [re-frame.core :as rf]
            [kee-frame.router :refer [make-route-component]]))

(defonce _ (let [source (. js/window matchMedia "(prefers-color-scheme: dark)")
                 type gevents/EventType.CHANGE
                 dispatch #(schpaa.state/change :app/dark-mode (-> % .-target .-matches))]
             (gevents/listen source type dispatch)))

(defn app-wrapper-clean
  [route-table]
  (let [route-name (rf/subscribe [:app/route-name])
        route (rf/subscribe [:kee-frame/route])
        user-screenmode (schpaa.state/listen :app/dark-mode)
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if @user-screenmode "dark" "light"))
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--content)")
    (if-let [page (get route-table @route-name)]
      (make-route-component page @route)
      [:div.bg-alt.fixed.inset-0.flex.items-center.justify-center "Subliminal message"])))



