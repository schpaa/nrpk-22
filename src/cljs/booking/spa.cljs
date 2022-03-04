(ns booking.spa
  (:require [booking.common-views :refer [main-menu page-boundry]]
            [booking.content.booking-blog :as content.booking-blog :refer [render]]
            [booking.content.overview :as content.overview]
            [booking.hoc :as hoc]
            [booking.lab]
            [booking.views]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [eykt.content.rapport-side]
            [kee-frame.router]
            [nrpk.fsm-helpers :refer [send]]
            [re-frame.core :as rf]
            [schpaa.button :as bu]
            [schpaa.components.tab :refer [tab]]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.darkmode]
            [schpaa.debug :as l]
            [shadow.resource :refer [inline]]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style :as st]
            [schpaa.style.booking]
            [schpaa.style.menu]
            [schpaa.style.ornament :as sc]
            [schpaa.style.popover]
            [schpaa.time]
            [tick.core :as t]
            [user.views]))


;region related to flex-date and how to display relative time

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

;region todo: extract these higher-order-components

;endregion

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

(defn register-back "to :active-front" [page]
  (rf/dispatch [:app/register-entry :active-back page]))

(defn render-back-tabbar []
  [:div.sticky.top-16.z-200
   [tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.user "Om meg" register-back :icon :user]
    [:r.logg "Turlogg" register-back :icon :circle]
    [:r.debug "Feilsøking" register-back :icon :circle]]])

(defn register-front "to :active-front" [page]
  (rf/dispatch [:app/register-entry :active-front page]))

(defn temp-add-10 [uid]
  [bu/regular-button-small {:on-click #(db/database-set {:path  ["booking-posts" "receipts" uid]
                                                         :value {"articles" (str (t/<< (t/now) (t/new-duration 151 :hours)))}})} "10"])

(defn temp-add-13 [uid]
  [bu/regular-button-small {:on-click #(db/database-set {:path  ["booking-posts" "receipts" uid]
                                                         :value {"articles" (str (t/<< (t/now) (t/new-duration 13 :hours)))}})} "13"])

(defn temp-add-15 [uid]
  [bu/regular-button-small {:on-click #(db/database-set {:path ["booking-posts" "receipts" uid] :value {}})} "15"])

(defn render-front-tabbar [uid]
  (let [vr (db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
        {date-of-last-seen :date id :id} @(db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
        path ["booking-posts" "articles"]
        list-of-posts (db/on-value-reaction {:path path})]
    (fn [uid]
      (let [unseen (count (filter (fn [[k {:keys [] :as v}]] (pos? (compare (name k) (:id @vr)))) @list-of-posts))]
        [:<>
         [l/ppre-x
          unseen
          date-of-last-seen
          id]

         [:div.p-2.flex.gap-2
          (temp-add-10 uid)
          (temp-add-13 uid)
          (temp-add-15 uid)]

         [:div.sticky.top-16.z-200
          [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
           [:r.new-booking "Ny booking" register-front :icon :ticket]
           [:r.forsiden "Siste" register-front :icon :clock]
           [:r.booking-blog
            (fn [] [:div.flex.gap-2
                    [:div "Nytt"]
                    (let [unseen (count (filter (fn [[k {:keys [] :as v}]] (pos? (compare (name k) (:id @vr)))) @list-of-posts))]
                      (when (pos? unseen)
                        [:div
                         [:div.rounded-full.bg-gray-100.xaspect-square.h-6.px-3.w-auto.flex.flex-center.text-black.font-semibold.text-sm
                          unseen]]))])

            #(do
               (register-front %)
               (booking.content.booking-blog/mark-last-seen uid))

            :icon
            :chat-square]]]]))))

(defn logo-type []
  [:div.text-center.inset-0
   {:class [:drop-shadow-md
            :leading-normal
            :font-oswald
            :font-normal
            :text-3xl
            :tracking-tight
            :dark:text-alt
            ;:bg-clip-text
            ;:bg-gradient-to-r :from-rose-400 :via-sky-600 :to-alt
            :xtext-transparent]}
   [:span "booking."]
   [:span.text-gray-500 "nrpk.no"]])

(defn welcome []
  (let [{:keys [bg bg+ bg- fg]} (st/fbg' :surface)]
    [:<>
     [:div.px-4.space-y-8.relative.xs:hidden
      {:style {:min-height "calc(100vh - 3rem)"}
       :class (concat fg bg)}

      [:div.max-w-xs.mx-auto.opacity-75
       [:div.group.transition.space-y-2.sticky.top-12.pt-16
        {:class bg}
        [:div.flex.flex-center
         [:div.relative.w-24.h-24
          [:div.absolute.rounded-full.-inset-1.blur
           {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                    :sgroup-hover:-inset-1 :duration-500]}]
          [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]
        (logo-type)]

       [:div.max-w-xs.mx-auto
        (-> "./frontpage.md"
            inline
            schpaa.markdown/md->html
            st/prose-markdown-styles)]]]

     [:div.px-4.space-y-8.relative.hidden.xs:block
      {:style {:min-height "calc(100vh - 4rem)"}
       :class (concat fg bg)}
      [:div.max-w-xsx.mx-auto

       [:div.grid.gap-x-10.max-w-md.mx-auto.space-y-8.pt-12
        {:style {:grid-template-columns "min-content 1fr"}}

        [:div.prose.col-span-2
         (-> "./frontpage1.md"
             inline
             schpaa.markdown/md->html
             st/prose-markdown-styles)]

        [:div.group.transition.space-y-2.self-center
         {:class bg}
         [:div.flex.flex-center
          [:div.relative.w-24.h-24
           [:div.absolute.rounded-full.-inset-2.blur
            {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                     :sgroup-hover:-inset-1 :duration-500]}]
           [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]
         (logo-type)]

        [:div.prose.mx-auto.col-span-1.self-center
         (-> "./frontpage2.md"
             inline
             schpaa.markdown/md->html
             st/prose-markdown-styles)]]]]]))

(declare menu-example-with-args standard-menu standard-menu-2)

;region temp helpers

(defn complex-menu [settings]
  (let [show-1 #(swap! settings assoc :setting-1 %)
        select-2 #(swap! settings assoc :setting-2 %)]
    (concat [[(fn [v] (sc/icon-large (when v [:> solid/ShieldCheckIcon])))
              (fn [e] (if e "Long" "Short"))
              #(show-1 (not (:setting-1 @settings)))
              false
              #(:setting-1 @settings)]]
            [nil]
            (let [data [["small" 1]
                        ["medium" 2]
                        ["large" 3]]]
              (map (fn [[caption value]]
                     [(fn [v] (sc/icon-large (when (= value v) [:> outline/CheckIcon])))
                      caption
                      #(select-2 value)
                      false
                      #(:setting-2 @settings)])
                   data))
            [nil]
            [[(sc/icon-large [:> solid/BadgeCheckIcon])
              "Badge"
              nil
              true
              #()]
             [(sc/icon-large [:> solid/LightBulbIcon])
              "Bulba?"
              nil
              true
              #()]])))

(def routing-table
  {:r.welcome
   (fn [r]
     [page-boundry r
      [db.signin/login]
      [welcome]])

   :r.forsiden
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry r
        [:div '[content.overview/overview]]]))

   :r.oversikt
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry r
        [content.overview/overview
         {:uid  (:uid @user-auth)
          :data (or [{}]
                    (sort-by (comp :number val) < (logg.database/boat-db)))}]]))

   :r.new-booking
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry r
        ;[render-front-tabbar (:uid @user-auth)]
        [booking.views/booking-form
         {:boat-db       (sort-by (comp :number val) < (logg.database/boat-db))
          :selected      hoc/selected
          :uid           (:uid user-auth)
          :on-submit     #(send :e.complete %)
          :cancel        #(send :e.cancel-booking)
          :my-state      schpaa.components.views/my-state
          :booking-data' (sort-by :date > (booking.database/read))}]]))

   :r.booking-blog
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry r
        [content.booking-blog/render
         {:fsm  {}
          :uid  (:uid @user-auth)
          :path ["booking-posts" "articles"]}]]))

   :r.user
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundry r
        [user.views/userstatus-form user-auth]
        ;[render-back-tabbar]
        [user.views/my-info]]))

   :r.logg
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundry r
        [user.views/userstatus-form user-auth]
        ;[render-back-tabbar]
        (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :void)]
          [:div.max-w-md.mr-auto
           [:div.space-y-px.flex.flex-col.w-full
            {:class  :min-h-screen
             :-style {:min-height "calc(100vh + 3rem)"}}
            [:div.flex-1.w-fullx
             {:class bg}
             [hoc/user-logg]]]
           [hoc/all-boats-footer {}]])]))

   :r.debug
   (fn [r]
     [page-boundry r
      [booking.lab/render r]])

   :r.page-not-found (fn [r] [:div "?"])})


(defn standard-menu [data]
  [[solid/TrashIcon "Fjern alle" #(reset! data (reduce (fn [a [k v]] (assoc-in a [k :st] false)) @data @data))]
   [solid/CursorClickIcon "Velg alle" #(reset! data (reduce (fn [a [k v]] (assoc-in a [k :st] true)) @data @data))]
   nil
   [solid/PencilIcon "Rediger" #()]
   nil
   [solid/DuplicateIcon "Dupliser" #()]])

(defn standard-menu-2 [data]
  [[(sc/icon-large [:> solid/TrashIcon]) "Fjern alle" #()]
   [(sc/icon-large [:> solid/CursorClickIcon]) "Siste valg" #()]
   nil
   [(sc/icon-large [:> solid/CogIcon]) "Book nå" #()]])
