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
            [schpaa.components.views :as views :refer [rounded-view]]
            ["body-scroll-lock" :as body-scroll-lock]
            [schpaa.debug :as l]
            [eykt.state :as state]
            [fork.re-frame :as fork]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [schpaa.time]
            [schpaa.darkmode]
            [booking.views]
            [user.views]))

(defn view-info [{:keys [username]}]
  [:div username])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

(defn rounded-box []
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])]
    (fn []
      (if-not @user-auth
        [rounded-view {} [db.signin/login]]
        [:div.space-y-4
         [user.views/logout-form {:name (:display-name @user-auth)}]

         [:div.flex.gap-4
          [:button.btn.btn-free {:on-click #(rf/dispatch [:app/navigate-to [:r.user]])} "Mine opplysninger"]
          [:button.btn.btn-free {:on-click #(rf/dispatch [:app/navigate-to [:r.my-bookings]])} "Mine bookinger"]]

         [k/case-route (fn [route] (-> route :data :name))
          :r.user [:div.space-y-4

                   [user.views/my-info]

                   #_[user.views/all-registered
                      {:some-data* (db/on-value-reaction {:path ["some-path"]})}]]
          :r.my-bookings
          [:div
           [:h2 "Mine bookinger"]
           [user.views/my-bookings
            {:uid      (:uid @user-auth)
             :bookings (booking.database/bookings-for (:uid @user-auth))}]]

          [:div "other " @route]]]))))


(def route-table
  {:r.init       (fn [_]
                   [:div "INIT"])
   :r.my-bookings (fn [_]
                    [rounded-box])
   :r.user       (fn [_]
                   [rounded-box])
   :r.content    (fn [_]
                   [:h2 "Oversikt"])
   :r.common     (fn [_]
                   (let [*st-all (rf/subscribe [::rs/state :main-fsm])]
                     [:div.space-y-4
                      ;[l/ppre @*st-all]

                      (rs/match-state (:booking @*st-all)
                        [:s.initial]
                        (r/with-let [data (db/on-value-reaction {:path ["booking"]})]
                          [:<>
                           (views/booking-header
                             {:book-now #(state/send :e.book-now)})
                           (schpaa.components.views/rounded-view
                             {:dark 1}
                             (into [:div.space-y-px]
                                   (map booking.views/booking-list-item
                                        (sort-by :date > (booking.database/read)))))])


                        [:s.booking]
                        (let [user-auth (rf/subscribe [::db/user-auth])]
                          [booking.views/booking-form
                           {:on-submit     #(state/send :e.confirm-booking %)
                            :cancel        #(state/send :e.cancel-booking)
                            :uid           (:uid @user-auth)
                            :my-state schpaa.components.views/my-state
                            :booking-data' (sort-by :date > (booking.database/read))}])

                        [:s.boat-picker]
                        [:div ":s.boat-picker"]

                        [:s.confirm-booking]
                        [views/booking-confirmed
                         {:values (:last-booking @(rf/subscribe [::rs/state-full :main-fsm]))}]


                        [l/ppre @*st-all])]))
   :r.common-old (fn [_]
                   [:div
                    #_[:div.w-screen.p-3.h-16.whitespace-nowrap
                       {:class []}
                       [:div.relative.w-full.flex.gap-6.items-center.overflow-x-auto
                        {:class [:snap-x
                                 :bg-gray-300 :text-black]}

                        [:div {:class [:snap-center :shrink-0]}
                         [:div {:class [:shrink-0 :w-4 :sm:w-48]}]]

                        [:a {:class [:snap-center]
                             :href  (k/path-for [:r.back])} "Til baksiden"]

                        [:div.underline.cursor-pointer
                         {:class    [:snap-center]
                          :on-click #(rf/dispatch [:app/open-menu-at :list])
                          :href     (k/path-for [:r.content])} "Innhold"]

                        (into [:<>] (mapv (fn [e] [:div.underline.cursor-pointer
                                                   {:class    [:snap-center
                                                               :w-80
                                                               :h-32
                                                               :bg-alt
                                                               :first:pl-8
                                                               :last:pr-8]
                                                    :on-click #(rf/dispatch [:app/open-menu-at :list])
                                                    :href     (k/path-for [:r.content])} "Annet " e])
                                          (range 6)))
                        [:div {:class [:snap-center :shrink-0]}
                         [:div {:class [:shrink-0 :w-4 :sm:w-48]}]]]]
                    [:div.p-3 (eykt.content.pages/new-designed-content {})]
                    #_[:div.bg-gray-100.-mx-4.px-4.space-y-1
                       (for [e (range 10)]
                         [:div.h-10.bg-gray-200.-mx-4.px-4 e])]
                    #_[:div.bg-gray-300.-mx-4.px-4.space-y-4
                       (for [e (range 20)]
                         [:div e])]
                    #_[:div.-mx-4 [l/ppre-x @re-frame.db/app-db]]])
   :r.back       (fn [_]
                   [:div.space-y-4
                    [:div.-m-4 [l/ppre-x @re-frame.db/app-db]]])})

(rf/reg-sub :route-name
            :<- [:kee-frame/route]
            (fn [route _]
              (-> route :data :name)))

(rf/reg-sub :app/menu-open? :-> :menu-open?)

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

(rf/reg-event-db :app/open-menu-at
                 (fn [db [_ tab]]
                   (assoc db :menu-open? true
                             :tab tab)))

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
        menu-open? (rf/subscribe [:app/menu-open?])
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
      :get-menuopen-fn    (fn [] @(rf/subscribe [:app/menu-open?]))
      :get-writingmode-fn (fn [] false)}
     web-content
     nil]))

(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode (rf/subscribe [:app/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if (= :dark @user-screenmode) "dark" ""))
    (.setAttribute body "class" "font-sans inter bg-gray-100 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])