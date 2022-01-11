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

(defn home []
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        route (rf/subscribe [:app/current-page])]
    [:div.space-y-4

     #_(views/booking-header
         {:book-now #(state/send :e.book-now)})

     [:div
      (rs/match-state (:booking @*st-all)
        [:s.initial]
        (r/with-let [data (db/on-value-reaction {:path ["booking"]})]
          [:<>

           (into [:div.space-y-px.shadow]
                 (map booking.views/booking-list-item
                      (sort-by :date > (booking.database/read))))]

         [:s.booking])
        (let [user-auth (rf/subscribe [::db/user-auth])]
          [booking.views/booking-form
           {:on-submit     #(state/send :e.confirm-booking %)
            :cancel        #(state/send :e.cancel-booking)
            :uid           (:uid @user-auth)
            :my-state      schpaa.components.views/my-state
            :booking-data' (sort-by :date > (booking.database/read))}])

        [:s.boat-picker]
        [:div ":s.boat-picker"]

        [:s.confirm-booking]
        [views/booking-confirmed
         {:values (:last-booking @(rf/subscribe [::rs/state-full :main-fsm]))}]

        [l/ppre @*st-all])

      [:div.flex.gap-4
       [:button.btn {:class    (when (= :r.common @route) [:btn-tab])
                     :on-click #(rf/dispatch [:app/navigate-to [:r.common]])} "Alle bookinger"]
       [:button.btn {:class    (when (= :r.boatlist @route) [:btn-tab])
                     :on-click #(rf/dispatch [:app/navigate-to [:r.boatlist]])} "Båtliste"]]

      [k/case-route (comp :name :data)
       :r.common [:div
                  [:div.flex.justify-end.p-4
                   {:class ["dark:bg-gray-700" "bg-white"]}
                   [:button.btn.btn-free.btn-cta {:on-click #(state/send :e.book-now)} "Book båt"]]
                  (into [:div.space-y-px.shadow]
                        (map booking.views/booking-list-item
                             (sort-by :date > (booking.database/read))))]
       :r.boatlist [:div
                    {:class ["dark:bg-gray-700" "bg-white"]}
                    [:div.p-4 "boatlist"]
                    (let [boat-db {1 {:text "a"}
                                   2 {:text "b"}
                                   3 {:text "c"}
                                   4 {:text "d"}
                                   5 {:text "e"}
                                   6 {:text "f"}}
                          selected (r/atom #{})]
                      (booking.views/boat-list
                        {:boat-db  (remove (fn [[k _v]] (some #{k} @selected)) boat-db)
                         :selected selected
                         :on-click #(swap! selected conj %)}))]

       [:div "else"]]]]))

(defn rounded-box []
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])]
    (fn []
      (if-not @user-auth
        [rounded-view {} [db.signin/login]]
        [:div.space-y-4
         [user.views/logout-form {:name (:display-name @user-auth)}]

         [:div
          [:div.flex.gap-4
           [:button.btn {:class    (when (= :r.user @route) [:btn-tab])
                         :on-click #(rf/dispatch [:app/navigate-to [:r.user]])} "Mine opplysninger"]
           [:button.btn {:class    (when (= :r.my-bookings @route) [:btn-tab])
                         :on-click #(rf/dispatch [:app/navigate-to [:r.my-bookings]])} "Mine bookinger"]]

          [k/case-route (fn [route] (-> route :data :name))
           :r.user
           [:div.space-y-4
            [user.views/my-info]]

           :r.my-bookings
           [:div
            ;[:h2 "Mine bookinger"]
            [user.views/my-bookings
             {:uid      (:uid @user-auth)
              :bookings (booking.database/bookings-for (:uid @user-auth))}]]

           [:div "other " @route]]]]))))


(def route-table
  {:r.init        (fn [_]
                    [:div "INIT"])
   :r.my-bookings (fn [_]
                    [rounded-box])
   :r.user        (fn [_]
                    [rounded-box])
   :r.content     (fn [_]
                    [:h2 "Oversikt"])
   :r.boatlist    (fn [_]
                    [home])
   :r.common      (fn [_]
                    [home])

   :r.common-old  (fn [_]
                    [:div.p-3 (eykt.content.pages/new-designed-content {})])

   :r.back        (fn [_]
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