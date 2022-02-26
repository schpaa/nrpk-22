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
            [lambdaisland.ornament :as o]
            [schpaa.components.sidebar :as components.sidebar]
            [schpaa.components.widgets :as widgets]
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
        #_[booking.spa/welcome]]])))

; region ui without headers and with a sidebar that is always visible

(o/defstyled nicer-tab' :div
  {:padding "var(--size-1)"}
  [:& :select-none]
  [:div.selected
   {:padding       "var(--size-1)"
    :border-radius "var(--radius-2)"
    :background    "var(--brand2)"
    :color         "var(--brand00)"}]
  [:div.regular
   {:padding       "var(--size-1)"
    :border-radius "var(--radius-2)"
    :color         "var(--surface5)"}
   [:&:hover {:color "var(--brand2)"}]]
  [:div.unavailable
   {:padding "var(--size-1)"
    :color   "var(--surface3)"}]
  [:div.item :space-y-1]
  ([{:keys [available selected icon route-name text]}]
   [:div.item.flex.items-center.flex-col
    (conj
      {:class (if available
                (if selected :selected :regular)
                :unavailable)}
      (when available {:on-click #(rf/dispatch [:app/navigate-to [route-name]])}))
    [:div {:class [:w-8 :h-8]} icon]
    [:div.text-center.text-xs text]]))

#_(defn nicer-tab
    [{:keys [selected icon route-name text]}]
    (let [selected- [:p-2 :bg-red-500]
          regular- [:p-2]]
      [:div {:style (if selected {:padding    "var(--size-1)"
                                  :background "var(--surface2)"}
                                 {:padding    "var(--size-2)"
                                  :background "var(--surface3)"})
             #_#_:class (if selected selected- regular-)}
       [:div.flex.flex-col
        {:on-click #(rf/dispatch [:app/navigate-to [route-name]])}
        [:> icon]
        [:div.text-center.text-xs text]]]))

(defn screen-render [{:keys [get-menuopen-fn menu-direction] :as m} web-content]
  (let [direction (if menu-direction :-translate-x-80 :translate-x-80)
        route (rf/subscribe [:kee-frame/route])]
    [:<>
     #_(when-not (= (some-> @route :data :name) :r.page-not-found)
         [:div.sticky.top-0.z-200
          {:class [:duration-200
                   (if (get-menuopen-fn) direction)]}
          [components.header/render m]])
     [:div {:class [:duration-200 (if (get-menuopen-fn) direction)]}
      web-content]
     [schpaa.components.screen/overlay (get-menuopen-fn)]
     [components.sidebar/render
      (conj m
            {:uid              (fn [] 123)
             :set-selected-tab (fn [e] (rf/dispatch [::widgets/set-tab e]))
             :get-selected-tab (fn [] @(rf/subscribe [::widgets/get-tab]))
             :current-tab      (fn [] 0)})]]))

(defn dispatch-main-sidebar [web-content]
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
      [screen-render
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

(defn app-wrapper-sidebar
  [route-table]
  (let [user-auth (rf/subscribe [::db/user-auth])
        route-name (rf/subscribe [:app/route-name])
        route @(rf/subscribe [:kee-frame/route])
        logged-in (some? @user-auth)
        data [(when logged-in {:login true :route-name :r.user :icon [:> solid/UserCircleIcon] :text [:div.whitespace-nowrap "om meg"]})
              (when-not logged-in {:login false :route-name :r.welcome :icon [:> solid/LoginIcon] :text "login"})
              (when-not logged-in :space)
              (when logged-in {:login true :route-name :r.new-booking :icon [:> solid/CalendarIcon] :text "booking"})
              (when logged-in {:login true :route-name :r.forsiden :icon [:> outline/TicketIcon] :text "siste"})
              {:login true :route-name :r.booking-blog :icon [:> outline/NewspaperIcon] :text "nytt"}
              {:login true :route-name :r.logg :icon [:> solid/ViewListIcon] :text "turlogg"}
              :space
              {:login true :route-name :r.debug :icon [:> outline/BeakerIcon] :text "debug"}
              :grow
              {:login false :route-name :r.welcome :icon (schpaa.icon/adapt :command 3) :text "bruk"}]

        tab (fn [d]
              (cond
                (= :grow d) [:div.flex-grow]
                (= :space d) [:div.h-10]
                :else (when-let [{:keys [login icon text route-name]} d]
                        (nicer-tab'
                          {:available  (or (not login) (and login (some? @user-auth))) ;(or (not (or login (some? @user-auth))))
                           :icon       icon
                           :text       text
                           :route-name route-name
                           :selected   (= route-name (-> route :data :name))}))))

        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--surface0)")
    [:div.fixed.inset-0
     [:div.grid.h-full
      {:style {:background            "var(--surface2)"
               :column-gap            "var(--size-0)"
               :grid-template-columns "min-content 1fr"
               :grid-auto-rows        "1fr"}}
      (into [:div.h-full.mr-1.flex.flex-col.overflow-y-scroll]
            (map tab data))
      [:div.bg-white.h-full.overflow-y-auto
       {:style {:background "var(--surface0)"}}
       [dispatch-main-sidebar
        (when-let [page (get route-table @route-name)]
          (make-route-component page route))]]]]))
