(ns eykt.spa
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            ["body-scroll-lock" :as body-scroll-lock]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.modal :as modal]
            [schpaa.components.screen :as components.screen]
            [schpaa.style :as st]))

;;

(def route-table
  {:r.common (fn [r] (let [{:keys [fg bg]} (st/fbg' :void)]
                       [:div
                        {:class (concat fg bg)}
                        [:div "COMMON"]]))})

;;

(defn- forced-scroll-lock
  [locked?]
  (let [body (aget (.getElementsByTagName js/document "body") 0)]
    (if locked?
      (.disableBodyScroll body-scroll-lock body)
      (.enableBodyScroll body-scroll-lock body))))

(defn dispatch-main []
  (let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
        menu-open? (rf/subscribe [:app/menu-open?])
        route-name @(rf/subscribe [:route-name])
        web-content (when-let [page (get route-table route-name)]
                      (kee-frame.router/make-route-component page @(rf/subscribe [:kee-frame/route])))
        s (rf/subscribe [::rs/state-full :main-fsm])]
    (forced-scroll-lock (or (and @mobile? @menu-open?)
                            (or (:modal @s) (:modal-forced @s))))
    [:div
     [modal/overlay-with
      {:modal-dim (:modal-dim @s)
       :modal?    (or (:modal @s)
                      (:modal-forced @s))
       ;intent No dismiss-fx on click when forced, must click on a button
       :on-close  (if
                    (or (:modal-dirty @s) (:modal-forced @s))
                    nil
                    #(send :e.hide))}
      [:div
       [modal/render
        {:show?     (or (:modal @s) (:modal-forced @s))
         :config-fn (:modal-config-fn @s)}]
       [components.screen/render
        {:current-page          (fn [] @(rf/subscribe [:app/current-page]))
         :toggle-menu-open      (fn [] (rf/dispatch [:toggle-menu-open]))
         :navigate-to-home      (fn [] (rf/dispatch [:app/navigate-to [:r.common]]))
         :navigate-to-user      (fn [] (rf/dispatch [:app/navigate-to [:r.user]]))
         :current-page-title    (fn [] @(rf/subscribe [:app/current-page-title]))
         :current-page-subtitle (fn [] @(rf/subscribe [:app/current-page-subtitle]))
         :get-menuopen-fn       (fn [] @(rf/subscribe [:app/menu-open?]))
         :get-writingmode-fn    (fn [] false)}
        web-content]]]]))

(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode (rf/subscribe [:app/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if (= :dark @user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans bg-gray-600 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])
