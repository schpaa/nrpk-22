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
            [tick.core :as t]
            [schpaa.components.fields :as fields]
            [schpaa.icon :as icon]))

(defn view-info [{:keys [username]}]
  [:div username])

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

(defn tab [{bottom? :bottom? locked? :locked? s :selected} & m]
  [:div.flex.gap-1.mx-1x
   (for [[page title] m]
     [:button.btn.flex-grow
      (conj
        (if bottom?
          {:class (if (= s page) :btn-tab-b :btn-tab-inactive-b)}
          {:class (if (= s page) :btn-tab :btn-tab-inactive)})
        (when-not locked?
          {:on-click #(rf/dispatch [:app/navigate-to [page]])}))
      title])])

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
           {:selected (r/atom #{501})
            :boat-db  (booking.database/boat-db)
            :day      1
            :slot     (tick.alpha.interval/bounds
                        (t/at (t/new-date 2022 1 3) (t/new-time 14 0))
                        (t/at (t/new-date 2022 1 4) (t/new-time 13 0)))
            :on-click #(js/alert "!")})]

        (tab {:bottom?  true
              :selected @(rf/subscribe [:app/current-page])}
             [:r.last-booking "Siste Booking"]
             [:r.new-booking "Ny Booking"])]

       (tab @(rf/subscribe [:app/current-page])
            [:r.new-booking "Ny Booking"]
            [:r.common "Mine Bookinger"])])))

;region todo: extract these higher-order-components

(defn new-booking []
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [booking.views/booking-form
     {:boat-db       (booking.database/boat-db)
      :selected      (r/atom #{501})
      :uid           (:uid @user-auth)
      :on-submit     #(state/send :e.confirm-booking %)
      :cancel        #(state/send :e.cancel-booking)
      :my-state      schpaa.components.views/my-state
      :booking-data' (sort-by :date > (booking.database/read))}]))

(defn last-active-booking []
  [views/rounded-view {:tab 1}
   [:h2 "Søndag 3 August"]
   [:h2 "16:00 --> 21:00"]

   (booking.views/pick-list
     {:selected (r/atom #{501})
      :boat-db  (booking.database/boat-db)
      :day      1
      :slot     (tick.alpha.interval/bounds
                  (t/at (t/new-date 2022 1 3) (t/new-time 14 0))
                  (t/at (t/new-date 2022 1 4) (t/new-time 13 0)))
      :on-click #(js/alert "!")})
   [:div.flex.justify-end
    [:button.btn.btn-danger "Avlys booking"]]])

(defn all-active-bookings []
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [:div
     #_[views/rounded-view
        {:info 1}
        [:div "Click on a line to create a new booking at the same date and time"]
        [:div "You will edit the booking if it belongs to you."]]
     [booking.views/booking-list
      {:data  (booking.database/read)
       :today (t/new-date 2022 1 4)
       :uid   (:uid @user-auth)}]]))


(defn general-footer
  "a footer for all lists where editing of some sort makes sense"
  [{:keys [edit-state markings c data]}]
  [:div.flex.justify-between.py-4.px-2.sticky.bottom-0
   {:class ["bg-black/20"]}
   [:div.gap-2.flex
    [:button.btn-small.btn-free {:on-click #(swap! edit-state not)} (if @edit-state "Ferdig" "Endre")]
    (when @edit-state
      [:button.btn-small.btn-free
       {:on-click #(reset! markings (reduce (fn [a e] (assoc a e true)) {} (map key data)))}
       "Merk alt"])
    (when @edit-state
      [:button.btn-small.btn-free
       {:disabled (zero? c)
        :on-click #(reset! markings {})}
       "Merk ingen"])
    (if @edit-state
      (let []
        [:button.btn-small.btn-danger
         {:disabled (zero? c)
          :on-click #(js/alert "!") #_#(do
                                         (booking.database/delete selected-keys)
                                         (reset! markings {}))}
         (str "Operasjon " (when (pos? c) c))])
      [:div])]
   #_(when-not @show-all
       [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"])])

(defn all-boats []
  (r/with-let [data (booking.database/boat-db)
               ;show-all (r/atom false)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          offset  (booking.views/day-number-in-year (t/date-time))
          #_#_slot    (tick.alpha.interval/bounds
                        (t/date-time)
                        (t/>> (t/date-time) (t/new-duration 3 :hours)))]
      [:div.w-full

       (into [:div {:class [:overflow-clip
                            :space-y-px
                            :first:rounded-t
                            :last:rounded-b]}]
             (map (fn [[id data]]
                    (let [idx id]
                      [booking.views/list-line
                       {:graph?        false
                        :id            id
                        :on-click      #(swap! markings update idx (fnil not false))
                        :data          data
                        :insert-behind [:div.w-16.flex.items-center.justify-center
                                        {:class    ["hover:bg-gray-500" "bg-black" "text-amber-500"]
                                         :on-click #(js/alert "!")}
                                        [icon/touch :chevron-right]]
                        :insert-front  (when @edit
                                         [:div.flex.items-center.px-2.bg-gray-400
                                          [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                            :handle-change #(swap! markings update idx (fnil not false))}
                                           "" nil]])}]))
                  data))

       [general-footer
        {:data       data
         :edit-state edit
         :markings   markings
         :c          c}]])))

;endregion

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

(defn front []
  (let [my-latest-booking {}]
    [:div.space-y-8
     (if (nil? my-latest-booking)
       [:<>
        (tab {:locked?  true
              :selected :r.new-booking}
             [:r.common "Min Siste Booking"]
             ;[:r.all "Alle"]
             [:r.new-booking "Ny Booking"])
        [new-booking]]
       [:div
        (tab {:selected @(rf/subscribe [:app/current-page])}
             [:r.common "Min Siste Booking"]
             ;[:r.all "Alle"]
             [:r.new-booking "Ny Booking"])
        [k/case-route (comp :name :data)
         :r.new-booking
         [views/rounded-view {} [new-booking]]
         :r.all
         [all-active-bookings]
         :r.common
         [:div.space-y-8
          [last-active-booking]
          [all-active-bookings]
          [all-boats]]]])]))

(def route-table
  {:r.init         (fn [_]
                     [:div "INIT"])
   :r.my-bookings  home
   :r.common       front
   :r.new-booking  front
   :r.all          front
   :r.last-booking home
   :r.user         (fn [_]
                     [rounded-box])
   :r.content      (fn [_]
                     [:h2 "Oversikt"])
   :r.boatlist     home
   :r.common-old   (fn [_]
                     [:div.p-3 (eykt.content.pages/new-designed-content {})])
   :r.back         (fn [_]
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