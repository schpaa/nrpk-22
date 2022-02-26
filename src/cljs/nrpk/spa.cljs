(ns nrpk.spa
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            ["body-scroll-lock" :as body-scroll-lock]
            [schpaa.modal :as modal]
            [schpaa.components.screen :as components.screen]
            [nrpk.fsm-helpers :as state :refer [send]]
            [kee-frame.router :refer [make-route-component]]
            [schpaa.debug :as l]
            [db.core :as db]))

(defn- forced-scroll-lock
  [locked? target]
  (let [body (.getElementById js/document (or target "body"))]
    (if locked?
      (.disableBodyScroll body-scroll-lock body)
      (do
        (.enableBodyScroll body-scroll-lock body)
        (.clearAllBodyScrollLocks body-scroll-lock)))))

(defn dispatch-main [web-content]
  (let [s (rf/subscribe [::rs/state-full :main-fsm])
        expose-app-db @(schpaa.state/listen :app/expose-app-db)
        get-menuopen-fn (rf/subscribe [:app/menu-open?])]
    [:div.h-full
     (when expose-app-db
       [l/ppre-x (dissoc @re-frame.db/app-db :kee-frame/route :re-statecharts.core/fsm-state)])
     [modal/overlay-with
      {:short-timeout (:modal-short-timeout @s)             ;;todo When showing a popup with short-timeout (< 2 seconds auto-closes), just bypass the click to dismiss
       :modal-dim     (:modal-dim @s)
       :modal?        (or (:modal @s)
                          (:modal-forced @s))
       ;When forced, a click on the  background will noe dismiss the modal
       ;the user must click on a button in the modal to dismiss it
       :on-close      (when-not (or (:modal-dirty @s)
                                    (:modal-forced @s))
                        #(send :e.hide))}
      ;;content
      [modal/render
       {:show?     (or (:modal @s) (:modal-forced @s))
        :config-fn (:modal-config-fn @s)}]
      [components.screen/render
       {:user-auth             (fn [] @(rf/subscribe [:db.core/user-auth]))
        :current-page          (fn [] @(rf/subscribe [:app/current-page]))
        :toggle-menu-open      (fn [] (rf/dispatch [:toggle-menu-open]))
        :navigate-to-home      (fn [] (rf/dispatch [:app/navigate-to @(rf/subscribe [:app/previous :active-front :r.forsiden])]))
        :navigate-to-user      (fn [] (rf/dispatch [:app/navigate-to @(rf/subscribe [:app/previous :active-back :r.user])]))
        :current-page-title    (fn [] @(rf/subscribe [:app/current-page-title]))
        :current-page-subtitle (fn [] @(rf/subscribe [:app/current-page-subtitle]))
        :get-menuopen-fn       (fn [] @(rf/subscribe [:app/menu-open?]))
        :menu-direction        @(schpaa.state/listen :app/menu-direction) #_@(rf/subscribe [:app/menu-direction])}
       web-content]]]))

;; the wrapper is what defines the overall UI.

(defn app-wrapper [route-table]
  (let [route-name (rf/subscribe [:app/route-name])
        route-entry (rf/subscribe [:kee-frame/route])
        user-screenmode (rf/subscribe [:app/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)
        menu-open? (rf/subscribe [:app/menu-open?])
        s (rf/subscribe [::rs/state-full :main-fsm])
        user-auth (rf/subscribe [::db/user-auth])]
    (.setAttribute html "class" (if (= :dark @user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans bg-gray-600 dark:bg-gray-800 ") ;fixme "min-h-screen overflow-x-hidden"
    (forced-scroll-lock (or @menu-open?
                            (and (or (:modal @s)
                                     (:modal-forced @s))
                                 (not (:modal-short-timeout @s)))) "maint")
    (if @user-auth
      [dispatch-main
       (when-let [page (get route-table @route-name)]
         (make-route-component page @route-entry))]
      [dispatch-main
       [:div
        [db.signin/login]
        [booking.spa/welcome]]])))

; region

#_(o/defstyled nicer-tab :div
               [:& :select-none]
               [:div.selected :p-2 :bg-red-500]
               [:div.regular :p-2]
               ([{:keys [selected]} ch]
                [:<>
                 [:div {:class (if selected :selected :regular)} ch]]))

(defn nicer-tab
  [{:keys [selected icon route-name text]}]
  (let [selected- [:p-2 :bg-red-500]
        regular- [:p-2]]
    [:div {:style (if selected {:padding    "var(--size-1)"
                                :background "var(--surface1)"})
           #_#_:class (if selected selected- regular-)}
     [:div.flex.flex-col
      {:on-click #(rf/dispatch [:app/navigate-to [route-name]])}
      [:> icon]
      [:div.text-center.text-xs text]]]))

(defn app-wrapper-sidebar
  [route-table]
  (let [route-name (rf/subscribe [:app/route-name])
        route @(rf/subscribe [:kee-frame/route])
        data [{:id -1 :route-name :r.new-booking :icon solid/PlusCircleIcon :text "booking"}
              {:id 1 :route-name :r.baksiden :icon solid/ClockIcon :text "siste"}

              {:id 4 :route-name :r.user :icon solid/UserCircleIcon :text "bruker"}
              {:id 0 :route-name :r.forsiden :icon solid/CalendarIcon :text "nytt"}
              {:id 2 :route-name :r.oppsett :icon solid/CogIcon :text "oppsett"}
              {:id 3 :route-name :r.oppsett :icon solid/ViewListIcon :text "utstyr"}]
        data-get (fn [id] (get (zipmap (map :id data) data) id))
        tab (fn [id] (let [{:keys [icon text route-name]} (data-get id)]
                       (nicer-tab
                         {:icon       icon
                          :text       text
                          :route-name route-name
                          :selected   (= route-name (-> route :data :name))})))]

    [:div.fixed.inset-0
     [:div.grid.h-full
      {:style {:column-gap            "var(--size-0)"
               :grid-template-columns "min-content 1fr"
               :grid-auto-rows        "1fr"}}
      (into [:div.h-full.p-px.space-y-2] (map tab (keys (zipmap (map :id data) data))))
      [:div.bg-white.h-full.overflow-y-auto
       [dispatch-main
        (when-let [page (get route-table @route-name)]
          (make-route-component page route))]]]]))




