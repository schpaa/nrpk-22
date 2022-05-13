(ns nrpk.spa
  (:require [goog.events :as gevents]
            [re-frame.core :as rf]
            [kee-frame.router :refer [make-route-component]]
            [schpaa.debug :as l]))

(defonce _ (let [source (. js/window matchMedia "(prefers-color-scheme: dark)")
                 type gevents/EventType.CHANGE
                 dispatch #(schpaa.state/change :app/dark-mode (-> % .-target .-matches))]
             (gevents/listen source type dispatch)))

(rf/reg-sub :lab/screen-geometry
            (fn [_] (rf/subscribe [:breaking-point.core/mobile?]))
            (fn [a _]
              {:mobile?       a
               :menu-caption? @(schpaa.state/listen :app/toolbar-with-caption)
               :right-menu?   @(schpaa.state/listen :lab/menu-position-right)}))

(defn app-wrapper-clean
  [route-table]

  (let [route-name (rf/subscribe [:app/route-name])
        route (rf/subscribe [:kee-frame/route])
        {:keys [right-menu? mobile? menu-caption?] :as geo} @(rf/subscribe [:lab/screen-geometry])
        user-screenmode (schpaa.state/listen :app/dark-mode)
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if @user-screenmode "dark" "light"))
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--content)")

    [:div
     (let [marg (when-not mobile? (if menu-caption? "14rem" "4rem"))]
       (when-let [route @route]
         [:div.fixed.top-0.noprint.inset-x-0.h-16
          {:style {:margin-left  (when-not right-menu? marg)
                   :margin-right (when right-menu? marg)
                   :height       "4.2rem"
                   :background   "linear-gradient(180deg,rgba(229, 230, 230, 0.9) 0%,
                                                       rgba(229, 230, 230, 0.7) 4rem,
                                                       var(--content-transp) 100%)"
                   :z-index      "10"}}

          [booking.common-views/header-line route false 1 #_(.-y (dom/getDocumentScroll))]]))

     (if-let [page (get route-table @route-name)]
       (make-route-component page @route)
       [:div.bg-alt.fixed.inset-0.flex.items-center.justify-center "(subliminal :message)"])]))



