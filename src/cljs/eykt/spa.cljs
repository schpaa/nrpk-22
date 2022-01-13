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
            [user.views]
            [tick.core :as t]))

(defn view-info [{:keys [username]}]
  [:div username])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

(defn tab [s & m]
  [:div.flex.gap-1.mx-1x
   (for [[page title] m]
     [:button.btn.flex-grow
      {:on-click #(rf/dispatch [:app/navigate-to [page]])
       :class    (if (= s page) :btn-tab :btn-tab-inactive)} title])])

(defn tab-b [s & m]
  [:div.flex.gap-1.mx-1x
   (for [[page title] m]
     [:button.btn.flex-grow
      {:on-click #(rf/dispatch [:app/navigate-to [page]])
       :class    (if (= s page) :btn-tab-b :btn-tab-inactive-b)} title])])

(defn home []
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])]
    (fn []
      [:div.space-y-4

       [:div
        [views/rounded-view {:tab 1}
         [:h2 "Søndag 3 August"]
         [:h2 "16:00 --> 21:00"]

         (booking.views/pick-list
           {:selected (r/atom #{501 502 403 401 201})
            :boat-db  (booking.database/boat-db)
            :day      1
            :slot     (tick.alpha.interval/bounds
                        (t/at (t/new-date 2022 1 3) (t/new-time 14 0))
                        (t/at (t/new-date 2022 1 4) (t/new-time 13 0)))
            :on-click #(js/alert "!")})]

        (tab-b @(rf/subscribe [:app/current-page])
             [:r.last-booking "Siste Booking"]
             [:r.new-booking "Ny Booking"])]

       (tab @(rf/subscribe [:app/current-page])
            [:r.new-booking "Ny Booking"]
            [:r.common "Mine Bookinger"])

       #_[:div.bg-gray-300.p-4
          [k/case-route (comp :name :data)
           :r.common
           [booking.views/my-booking-list
            {:data  (booking.database/read)
             :today (t/new-date 2022 1 14)
             :uid   (:uid @user-auth)}]

           :r.new-booking
           [booking.views/booking-form
            {:boat-db       (booking.database/boat-db)
             :selected      (r/atom #{})
             :uid           (:uid @user-auth)
             :on-submit     #(state/send :e.confirm-booking %)
             :cancel        #(state/send :e.cancel-booking)
             :my-state      schpaa.components.views/my-state
             :booking-data' (sort-by :date > (booking.database/read))}]



           [:div]]]])))

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
                    [home])
   :r.new-booking (fn [_]
                    [home])
   :r.last-booking (fn [_]
                     [home])
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

;region events and subs

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

;endregion

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