(ns booking.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [booking.data :as data :refer [start-db routes]]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.components.tab :refer [tab]]
            [schpaa.time]
            [schpaa.darkmode]
            [nrpk.fsm-helpers :as state :refer [send]]
            [db.core :as db]
            [db.signin]
            [eykt.content.pages]
            [booking.views]
            [user.views]
            [booking.hoc :as hoc]
            [shadow.resource :refer [inline]]
            [schpaa.style :as st]
            [booking.content.booking-blog :as content.booking-blog :refer [booking-blog]]
            [booking.content.new-booking :as content.new-booking]
            [booking.content.overview :as content.overview]
            [schpaa.debug :as l]
            [schpaa.button :as bu]
            [tick.core :as t]))

;test                                 
(defn view-info [{:keys [username]}]
  [:div username])

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

(defn user []
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])
        {:keys [bg fg- fg+ hd p p- he]} (st/fbg' :surface)]
    (fn []
      ;[:div.bg-alt.h-32.w-full "X"]
      (if-not @user-auth
        [:div.xp-4.xmax-w-md.mx-autox
         [rounded-view {:float 1} [db.signin/login]]]

        [:div.xw-full {:class bg}
         [:div.p-4.max-w-md.mx-auto
          [user.views/userstatus-form
           {:user-auth @user-auth
            :name      (:display-name @user-auth)}]]

         [:div
          [render-back-tabbar]

          [k/case-route (fn [route] (-> route :data :name))
           :r.user
           (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
             [:div.space-y-4
              {:class bg}
              [user.views/my-info]])

           :r.logg
           (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :void)]
             [:div.w-screenx
              [:div.space-y-px.flex.flex-col.w-full
               {:class  :min-h-screen
                :-style {:min-height "calc(100vh + 3rem)"}}
               [:div.flex-1.w-fullx
                {:class bg}
                [hoc/user-logg]]]
              [hoc/all-boats-footer {}]])

           :r.debug
           [hoc/debug]

           [:div @route]]]]))))

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
          (temp-add-15 uid)

          [bu/regular-button-small {:on-click #(db/database-push {:path  ["booking-posts" "articles"]
                                                                  :value {:uid     uid
                                                                          :content "old"
                                                                          :date    (str (t/<< (t/now) (t/new-duration (rand-int 240) :hours)))}})}
           [:div [:div "Add"] [:div "old"]]]
          [bu/regular-button-small {:on-click #(db/database-push {:path  ["booking-posts" "articles"]
                                                                  :value {:uid     uid
                                                                          :content "recent"
                                                                          :date    (str (t/>> (t/now) (t/new-duration (rand-int 3) :minutes)))}})}
           [:div [:div "Add"] [:div "+3"]]]]

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

(defn front []
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)
        user-auth (rf/subscribe [::db/user-auth])]
    (if @user-auth
      [:div
       [render-front-tabbar (:uid @user-auth)]

       [k/case-route (comp :name :data)
        :r.booking-blog
        [content.booking-blog/err-boundary
         [content.booking-blog/booking-blog (:uid @user-auth)]]

        ;:r.blog
        ;[(get-in schpaa.components.sidebar/tabs-data [:bar-chart :content-fn])]

        :r.new-booking
        (if-not @user-auth
          [views/rounded-view {}
           [:div.p-4.space-y-4
            [:h2 "Er du ny her?"]
            [:a {:href (k/path-for [:r.user])} "Logg inn først"]]]
          [content.new-booking/new-booking @user-auth])

        :r.forsiden
        [content.overview/overview]]]

      [welcome])))

(def route-table
  {:r.forsiden       front
   :r.new-booking    front
   :r.booking-blog   front
   ;:r.debug2         front
   ;:r.blog           front
   :r.user           user
   :r.logg           user
   :r.debug          user
   :r.page-not-found (fn [r] [:div "?"])})

