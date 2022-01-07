(ns eykt.spa
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [eykt.data :as data :refer [screen-breakpoints start-db routes]]
            [schpaa.components.screen :as components.screen]
            ["body-scroll-lock" :as body-scroll-lock]
            [schpaa.debug :as l]
            [eykt.state :as state]
            [fork.re-frame :as fork]
            [db.core :as db]
            [db.signin]))

(defn rounded-box []
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        user-auth (rf/subscribe [::db/user-auth])]
    (fn []
      (if-not @user-auth
        [:div
         [:button.btn.btn-free {:on-click #()} "Login"]
         [db.signin/login]]
        [:div
         [l/ppre-x user-auth]
         [:button.btn.btn-free {:on-click #(db/sign-out)} "Sign out"]
         (rs/match-state (:user @*st-all)
           [:s.ready]
           [:div.bg-gray-100.rounded.p-3.space-y-4
            [:div.h-10.bg-gray-50.flex.items-center.-m-2.p-2 "min side"]
            [:div "1 extra"]
            [fork/form {:initial-values    {:input "hello"}
                        :prevent-default?  true
                        :clean-on-unmount? true
                        :keywordize-keys   true
                        :on-submit
                        #(rf/dispatch (state/send :e.store %))
                        #_#(js/alert % #_#_rf/dispatch [:submit-handler %])}
             (fn [{:keys [values handle-change handle-blur form-id handle-submit]}]
               [:form
                {:id        form-id
                 :on-submit handle-submit}
                [:div
                 [:p "Read back: " (values :input)]
                 [:div.flex.gap-4
                  [:input
                   {:name      :input
                    :value     (values :input)
                    :on-change handle-change
                    :on-blur   handle-blur}]
                  [:button.btn.btn-free {:type :submit} "Store"]
                  [:button.btn.btn-free {:type     :button
                                         :on-click #(rf/dispatch (state/send :e.restart))} "Reset"]]]])]
            [:div "2 info"]]
           [:s.initial]
           [:div
            [:div "Initial"]]

           [l/ppre-x @*st-all])]))))

(def route-table
  {:r.init    (fn [_]
                [:div "INIT"])
   :r.user    (fn [_]
                [rounded-box])
   :r.content (fn [_]
                [rounded-box])
   :r.common  (fn [_]
                [:div.space-y-4
                 [:div.flex.gap-4
                  [:a {:href (k/path-for [:r.back])} "Til baksiden"]
                  [:a {:href (k/path-for [:r.content])} "Innhold"]]
                 [:div.bg-gray-100.-mx-4.px-4.space-y-1
                  (for [e (range 10)]
                    [:div.h-10.bg-gray-200.-mx-4.px-4 e])]
                 [:div.bg-gray-300.-mx-4.px-4.space-y-4
                  (for [e (range 20)]
                    [:div e])]
                 [:div.-mx-4 [l/ppre-x @re-frame.db/app-db]]])
   :r.back    (fn [_]
                [:div.space-y-4

                 [:div.-mx-4 [l/ppre-x @re-frame.db/app-db]]])})


(rf/reg-sub :route-name
            :<- [:kee-frame/route]
            (fn [route _]
              (-> route :data :name)))

(rf/reg-sub :menu-open? :-> :menu-open?)

(rf/reg-sub :app/current-page
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :name)))

(rf/reg-sub :app/current-page-title
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :header)))

(rf/reg-event-db :toggle-menu-open
                 (fn [db]
                   (update db :menu-open? (fnil not false))))

(rf/reg-event-fx :app/navigate-to
                 [rf/trim-v]
                 (fn [_ [page]]
                   {:fx [[:navigate-to page]]}))

(defn- forced-scroll-lock
  [locked?]
  (let [body (aget (.getElementsByTagName js/document "body") 0)]
    (if locked?
      (.disableBodyScroll body-scroll-lock body)
      (.enableBodyScroll body-scroll-lock body))))

(defn dispatch-main []
  (let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
        menu-open? (rf/subscribe [:menu-open?])
        route-name @(rf/subscribe [:route-name])
        web-content (when-let [page (get route-table route-name)]
                      (kee-frame.router/make-route-component page @(rf/subscribe [:kee-frame/route])))]
    (forced-scroll-lock (and @mobile? @menu-open?))
    [components.screen/render
     {:current-page       (fn [] @(rf/subscribe [:app/current-page]))
      :toggle-menu-open   (fn [] (rf/dispatch [:toggle-menu-open]))
      :navigate-to-home   (fn [] (rf/dispatch [:app/navigate-to [:r.common]]))
      :navigate-to-user   (fn [] (rf/dispatch [:app/navigate-to [:r.user]]))
      :current-page-title (fn [] @(rf/subscribe [:app/current-page-title]))
      :get-menuopen-fn    (fn [] @(rf/subscribe [:menu-open?]))
      :get-writingmode-fn (fn [] false)}
     web-content
     nil]))

(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode @(rf/subscribe [::data/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if (= :dark user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans inter bg-gray-100 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])