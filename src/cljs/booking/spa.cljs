(ns booking.spa
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [re-statecharts.core :as rs]
            [kee-frame.router]
            [kee-frame.core :as k]
            [cljs.pprint :refer [pprint]]
            [booking.data :as data :refer [start-db]]
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
            [booking.content.overview :as content.overview]
            [schpaa.debug :as l]
            [schpaa.button :as bu]
            [tick.core :as t]
            [eykt.content.rapport-side]
            [booking.content.blog-support :refer [err-boundary]]

            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.menu]
            [schpaa.style.booking]
            [clojure.set :as set]
            [schpaa.style.button :as scb]))


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

(defn page-boundry [& c]
  [err-boundary
   [:div.max-w-mdx.mr-auto
    c]])

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
     [page-boundry
      [db.signin/login]
      [welcome]])

   :r.forsiden
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry
        ;[render-front-tabbar (:uid @user-auth)]
        [content.overview/overview]]))

   :r.new-booking
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundry
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
       [page-boundry
        ;[render-front-tabbar (:uid @user-auth)]
        [content.booking-blog/booking-blog
         {:fsm  {}
          :uid  (:uid @user-auth)
          :path ["booking-posts" "articles"]}]]))

   :r.user
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundry
        [user.views/userstatus-form user-auth]
        ;[render-back-tabbar]
        [user.views/my-info]]))

   :r.logg
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundry
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
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       (r/with-let [settings (r/atom {:setting-1 false
                                      :setting-2 1
                                      :selection #{2 5}})]

         (let []
           [page-boundry
            [:div.right-4.p-4.space-y-4
             ;[l/ppre-x @settings]

             [sc/grid-wide
              [schpaa.style.booking/line
               {:content  {:category    "Havkayakk"
                           :number      "310"
                           :location    "C3"
                           :material    "Epoxy"
                           :stability   4
                           :description "Passer best for de som liker å padle på vann og land."
                           :brand       "P3 Baffin Boreal"
                           :weight      "23 kg"
                           :width       "50 cm"
                           :length      "490 cm"}

                :on-click #()
                :expanded true
                :selected false}]
              [schpaa.style.booking/line
               {:content  {:category  "Havkayakk"
                           :number    "310"
                           :location  "C3"
                           :material  "Plast"
                           :stability 1
                           ;:description ""
                           :brand     "P3 Baffin Boreal"
                           ;:weight      "23 kg"
                           :width     "50 cm"
                           :length    "490 cm"}

                :on-click #()
                :expanded true
                :selected false}]
              [schpaa.style.booking/line
               {:content  {:category    "Grønnlandskayakk"
                           :number      "600"
                           :location    "E5"
                           :material    "Epoxy"
                           :stability   4
                           :description "Ufattelig lang beskrivelse som går over flere linjer og som sier en hel drøss om hva dette er godt for og at du burde prøve den!"
                           :brand       "Rebel Naja"
                           :weight      "33 kg"
                           :width       "50 cm"
                           :length      "490 cm"}

                :on-click #()
                :expanded true
                :selected false}]]

             [:div.flex.justify-end.items-center



              [schpaa.style.menu/menu-example-with-args
               {:dir         :down
                :data        (complex-menu settings)
                :always-show false
                :button      (fn [open]
                               [scb/normal-floating
                                ;{:class [:w-32]}
                                [sc/row {:class [:gap-4 :px-2]}
                                 [sc/text "Visning"]
                                 [sc/icon [:> (if open outline/XIcon outline/ChevronDownIcon)]]]])}]]]

            [:div.px-4.space-y-4
             [sc/grid-wide
              (doall (for [e (range 5)]
                       [schpaa.style.booking/line {:content  {:category "Grønnlandskayakk"
                                                              :number   e
                                                              :location (str "A" e)}
                                                   :on-click #(if (some #{e} (:selection @settings))
                                                                (swap! settings update :selection set/difference #{e})
                                                                (swap! settings update :selection set/union #{e}))
                                                   :expanded (= 2 (:setting-2 @settings))
                                                   :selected (some #{e} (:selection @settings))}]))]

             (let [section [:div.p-4.space-y-4
                            [:div.gap-1.flex.gap-4.flex-wrap

                             (scb/round-normal [:> solid/HeartIcon])
                             (scb/round-cta [:> solid/HeartIcon])
                             (scb/round-danger [:> solid/HeartIcon])

                             [scb/normal "normal button"]
                             [scb/button-cta "cta button"]
                             [scb/button-danger "danger button"]]

                            [sc/col
                             [sc/hero-p "Some Title Here"]
                             [sc/title-p "Some Title Here"]
                             [sc/row {:class [:gap-1]}
                              [sc/badge-author "Author of this post"]
                              [sc/badge-date {:class [:tabular-nums]} "1 uke siden"]]
                             [sc/subtitle "subtitle lorem ipsum dolores etceterum among many lines and so forth"]
                             [sc/text-p "text subtext subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum"]
                             [sc/subtext-p "subtext subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum"]]]]

               [:<>
                [sc/surface-a section]
                ;[sc/surface-b section]
                [sc/surface-c section]])]


            [:div.fixed.-absolute.bottom-2.right-4
             [:div.flex.justify-end.items-center
              [schpaa.style.menu/menu-example-with-args
               {:dir         :up
                :data        (complex-menu settings)        ;(standard-menu-2 (r/atom nil))
                :always-show false
                :button      (fn [open]
                               [scb/round-normal-floating
                                [sc/icon [:> (if open solid/DotsVerticalIcon
                                                      solid/DotsHorizontalIcon)]]])}]]]]))))

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
