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

(defn rounded-view [& content]
  [:div.bg-gray-100.rounded.p-3.space-y-4
   (into [:<>] (map identity content))])

(defn rounded-view' [& content]
  [:div.rounded.p-3.space-y-4
   {:class ["bg-gray-400/50"]}
   (into [:<>] (map identity content))])

(defn view-info [{:keys [username]}]
  [:div username])

(defn rounded-box []
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        some-data (db/on-value-reaction {:path ["some-path"]})
        user-auth (rf/subscribe [::db/user-auth])]

    (fn []
      (let [path {:path ["some-path" (:uid @user-auth)]}
            loaded-data (db/on-value-reaction path)]
        (if-not @user-auth
          [:div
           ;[:button.btn.btn-free {:on-click #()} "Login"]
           [rounded-view [db.signin/login]]]
          [:div.space-y-4
           ;[l/ppre (:uid @user-auth)]
           ;[l/ppre-x @some-data (:uid @some-data)]
           ;(:user @*st-all)

           (rs/match-state (:user @*st-all)
             [:s.editing]
             [:div
              [rounded-view
               [:div.flex.items-center.justify-between
                [view-info {:username (:display-name @user-auth)}]
                [:button.btn.btn-free.bg-red-500.text-white {:on-click #(db/sign-out)} "Logg ut"]]

               ;[:div.h-10.bg-gray-50.flex.items-center.-m-2.p-4 "min side"]
               [fork/form {:initial-values    {:input (:input @loaded-data)}
                           :prevent-default?  true
                           :clean-on-unmount? true
                           :keywordize-keys   true
                           :on-submit
                           #(state/send :e.store (assoc-in % [:values :uid] (:uid @user-auth)))
                           #_#(js/alert % #_#_rf/dispatch [:submit-handler %])}
                (fn [{:keys [values handle-change handle-blur form-id handle-submit dirty]}]
                  [:form.space-y-4
                   {:id        form-id
                    :on-submit handle-submit}
                   [:div.flex.gap-4.justify-between
                    [:div.flex-col.flex
                     [:label {:for "z"} "Data"]
                     [:input.flex-grow.h-10
                      {:name      :input
                       :id        "z"
                       :type      :text
                       :value     (values :input)
                       :on-change handle-change
                       :on-blur   handle-blur}]]]
                   [:div.flex.gap-4.justify-end
                    [:button.btn.btn-free {:type     :button
                                           :on-click #(rf/dispatch (state/send :e.restart))} "Avbryt"]
                    [:button.btn.btn-free.btn-cta.text-white {:disabled (not (some? dirty))
                                                              :type     :submit} "Lagre"]]])]]]

             [:s.store]
             [rounded-view
              [:div "Saving"]]

             [:s.initial]
             [rounded-view
              [:div.flex.items-center.justify-between
               [view-info {:username (:display-name @user-auth)}]
               [:button.btn.btn-free.bg-red-500.text-white {:on-click #(db/sign-out)} "Logg ut"]]

              [:div (:input @loaded-data)]

              [:div.flex.justify-end
               [:button.btn.btn-free
                {:type     :button
                 :on-click #(state/send :e.edit)}
                "Rediger"]]]
             [l/ppre-x :state @*st-all])
           (for [e (keep :input (vals @some-data))]
             [rounded-view' e])])))))

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