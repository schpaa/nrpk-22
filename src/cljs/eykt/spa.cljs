(ns eykt.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [times.api :refer []]
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
            [db.signin]
            [schpaa.time]))

(defn rounded-view [& content]
  [:div.bg-gray-100.rounded.p-3.space-y-4
   (into [:<>] (map identity content))])

(defn rounded-view' [& content]
  [:div.rounded.p-3.space-y-4
   {:class ["bg-gray-400/50"]}
   (into [:<>] (map identity content))])

(defn view-info [{:keys [username]}]
  [:div username])

(defn my-form [{:keys [values handle-change handle-blur form-id handle-submit dirty readonly?]}]
  [:form.space-y-4
   {:id        form-id
    :on-submit handle-submit}
   [:div.flex.flex-col.gap-4
    [:div.flex-col.flex
     [:label {:for "z"} "Data"]
     [:input.h-10.field-w-max
      {:class     [:form-input :active:border-2 :border-none :rounded]
       :name      :input
       :id        "z"
       :type      :text
       :value     (values :input)
       :on-change handle-change
       :on-blur   handle-blur}]]
    [:div.flex-col.flex
     [:label {:for "x"} "Kallenavn"]
     [:input.w-auto.h-10.field-w-max.focus:border-2.border-none
      {:class     [:form-input :active:border-2 :border-none :rounded]
       :name      :shortname
       :id        "x"
       :type      :text
       :value     (values :shortname)
       :on-change handle-change
       :on-blur   handle-blur}]]]
   (when-not readonly?
     [:div.flex.gap-4.justify-end
      [:button.btn.btn-free {:type     :button
                             :on-click #(rf/dispatch (state/send :e.restart))} "Avbryt"]
      [:button.btn.btn-free.btn-cta.text-white {:disabled (not (some? dirty))
                                                :type     :submit} "Lagre"]])])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)
(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

(defn rounded-box []
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        some-data (db/on-value-reaction {:path ["some-path"]})
        user-auth (rf/subscribe [::db/user-auth])]

    (fn []
      (let [uid (:uid @user-auth)
            path {:path ["some-path" uid]}
            loaded-data (db/on-value-reaction path)]

        (if-not @user-auth
          [rounded-view [db.signin/login]]
          [:div.space-y-4

           [rounded-view
            [:div.flex.justify-between.items-center
             [:h2 (:display-name @user-auth)]
             [:button.btn.btn-danger {:on-click #(db/sign-out)} "Logg ut"]]]

           (r/with-let [s (db/on-snapshot-doc-reaction {:path ["users2" uid]})]

               [rounded-view
                [:div.flex.items-center.justify-between
                 [:div.flex.flex-col
                  [:h2 "Mine opplysninger"]
                  [:h2.text-xs (if-let [tm (get @s "timestamp")]
                                 (schpaa.time/x' tm)
                                 "Ikke registrert")]]]
                (rs/match-state (:user @*st-all)
                  [:s.initial] [:<>
                                (if @loaded-data
                                  (my-form {:values @loaded-data :readonly? true})
                                  [:div "Ingen data"])
                                [:div.flex.justify-end
                                 [:button.btn.btn-free.btn-cta
                                  {:type     :button
                                   :on-click #(state/send :e.edit)}
                                  "Rediger"]]]

                  [:s.editing] [fork/form {:initial-values    @loaded-data
                                           :prevent-default?  true
                                           :clean-on-unmount? true
                                           :keywordize-keys   true
                                           :on-submit         #(state/send :e.store (assoc-in % [:values :uid] (:uid @user-auth)))}
                                my-form]

                  [:s.store] [rounded-view
                              [:div "Lagrer, et øyeblikk"]]

                  [l/ppre-x :state @*st-all])])

           (when @some-data
             (for [[a b] (keep (juxt :input :shortname) (vals @some-data))]
               [rounded-view' [:div.flex.justify-between
                               [:div a]
                               [:div {:class (if-not b "text-black/25")} (or b "ingen")]]]))])))))

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