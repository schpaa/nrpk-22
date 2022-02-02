(ns nrpk.spa
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            ["body-scroll-lock" :as body-scroll-lock]
            [schpaa.modal :as modal]
            [schpaa.components.screen :as components.screen]
            [nrpk.fsm-helpers :as state :refer [send]]))

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
        get-menuopen-fn (rf/subscribe [:app/menu-open?])]
    [:div.h-full
     [modal/overlay-with
      {:modal-dim (:modal-dim @s)
       :modal?    (or (:modal @s)
                      (:modal-forced @s))
       ;When forced, a click on the  background will noe dismiss the modal
       ;the user must click on a button in the modal to dismiss it
       :on-close  (when-not (or (:modal-dirty @s) (:modal-forced @s))
                    #(send :e.hide))}
      ;;content
      [modal/render
       {:show?     (or (:modal @s) (:modal-forced @s))
        :config-fn (:modal-config-fn @s)}]
      [components.screen/render
       {:current-page          (fn [] @(rf/subscribe [:app/current-page]))
        :toggle-menu-open      (fn [] (rf/dispatch [:toggle-menu-open]))
        :navigate-to-home      (fn [] (rf/dispatch [:app/navigate-to [:r.mine-vakter]]))
        :navigate-to-user      (fn [] (rf/dispatch [:app/navigate-to [:r.user]]))
        :current-page-title    (fn [] @(rf/subscribe [:app/current-page-title]))
        :current-page-subtitle (fn [] @(rf/subscribe [:app/current-page-subtitle]))
        :get-menuopen-fn       (fn [] @(rf/subscribe [:app/menu-open?]))
        :menu-direction        @(rf/subscribe [:app/menu-direction])}
       web-content]]]))

(defn app-wrapper [route-table]
  (let [route-name (rf/subscribe [:route-name])
        route-entry (rf/subscribe [:kee-frame/route])
        user-screenmode (rf/subscribe [:app/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)
        mobile? (rf/subscribe [:breaking-point.core/mobile?])
        menu-open? (rf/subscribe [:app/menu-open?])
        s (rf/subscribe [::rs/state-full :main-fsm])]
    (.setAttribute html "class" (if (= :dark @user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans bg-gray-600 dark:bg-gray-800 ") ;fixme "min-h-screen overflow-x-hidden"
    (forced-scroll-lock (or @menu-open?
                            (or (:modal @s)
                                (:modal-forced @s))) "maint")
    [dispatch-main
     (when-let [page (get route-table @route-name)]
       (kee-frame.router/make-route-component page @route-entry))]))

(comment
  (defn testx []
    (r/with-let [toggle (r/atom true)]
                (forced-scroll-lock @toggle "maint")
                [:div

                 [:div#maint.px-2 {:class (concat
                                            [:right-0 :z-30 :transform :duration-200]
                                            (if @toggle
                                              [:translate-x-0 :pointer-events-auto]
                                              [:translate-x-full])
                                            ["bg-black text-white"
                                             :overflow-y-auto
                                             "fixed z-20 w-80 h-screen"])}
                  [:div {:class [:sticky :top-0 :bg-black :h-16]} "navigation"]
                  [:div {:class [:bg-rose-300]}
                   (into [:div] (map #(vector :div %) (range 100)))]]

                 [:div.select-none.text-xl.relative
                  ;{:class (if @toggle [:-translate-x-80])}

                  [:div.fixed.inset-0.bg-black.z-20
                   {:class    (if @toggle
                                [:opacity-50 :pointer-events-auto]
                                [:opacity-0 :pointer-events-none])
                    :on-click #(swap! toggle not)}]

                  [:div {:on-click #(swap! toggle not)
                         :class    [:bg-white :h-16 "sticky top-0"]} "header"]

                  [:div {:on-click #(swap! toggle not)
                         :class    [:bg-amber-200 :h-32]} "middle"]

                  [:div {:class [:bg-sky-300 :h-16 "sticky top-16"]} "tabbar"]

                  [:div {:class [:bg-pink-300]}
                   [:div "content"]
                   (into [:div] (map #(vector :div %) (range 10)))]

                  [:div {:class [:bg-pink-200]}
                   [:div "content"]
                   (into [:div] (map #(vector :div %) (range 100)))]]])))