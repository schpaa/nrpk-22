(ns booking.spa
  (:require [booking.common-views :refer [page-boundary]]
            [booking.content.booking-blog :as content.booking-blog :refer [render]]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
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
            [user.views]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog :refer [open-dialog-signin open-dialog-sampleautomessage]]
            [schpaa.style.button2 :as scb2]
            [shadow.resource :refer [inline]]
            [times.api :as ta]
            [schpaa.time]
            [goog.style :as gs]
            [booking.aktivitetsliste]))

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

(o/defstyled details :details
  #_[:& :select-none
     [#{:openxx} :text-blue-500]
     [:>summary
      ;:flex
      #_[:&:hover :opacity-100]
      #_[:> [:* {:display :inline}]]]]

  ([{:keys [st header]} ch]
   #_^{;:class [:openxx]
       :open st}
   ;^{:open st}
   [:div
    [:div "asd"]
    #_[:summary
       "header"]]))

(defn collapsable-memory [tag header content]
  (r/with-let [st (schpaa.state/listen tag)]
    [:details.select-none {:open @st}
     [:summary {:on-click #(schpaa.state/toggle tag)
                :style    {:display :inline}} header]
     content]))

(o/defstyled listitem :div
  ([a b c]
   [sc/row'
    [sc/text {:class [:w-8 :shrink-0]} b]
    [scb2/outline-tight "Rediger"]
    [sc/subtext {:class [:line-clamp-3 :grow]} a]
    #_[sc/text c]]))

(defn error-page [r]
  ;[:div "error"]
  [page-boundary r
   [sc/col {:class [:p-1 :space-y-4 :mx-auto :max-w-xs :sm:max-w-md]}
    [:div.animate-pulse
     {:style {:display       :grid
              :min-height    "25vh"
              :place-content :center
              :font-size     "var(--font-size-7)"
              :font-family   "IBM Plex Sans"}}
     [:div.relative.w-24.h-24
      [:div.absolute.rounded-full.-inset-1.blur
       {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                :sgroup-hover:-inset-1 :duration-500]}]
      [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]

    [sc/text "Finner ikke noe på denne adressen! Det er sikkert bare en gammel lenke. Kanskje du finner det du leter etter i denne listen?"]
    (into [:div.space-y-1] (for [{:keys [id name icon disabled action keywords] :as e}
                                 (sort-by :name < schpaa.style.combobox/commands)]
                             [:div [sc/subtext-with-link {:on-click action} name]]))]])

(def routing-table
  {:r.welcome
   (fn [r]
     [page-boundary r
      [db.signin/login]
      [welcome]])

   :r.forsiden
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundary r
        {:whole [:iframe.w-full.h-screen
                 {:src "https://nrpk.no"}]}
        [sc/col {:class [:space-y-4 :max-w-md :mx-auto]}]]))

   :r.booking.oversikt
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundary r
        [content.overview/overview
         {:uids (:uid @user-auth)
          :data (or [{}]
                    (sort-by (comp :number val) < (logg.database/boat-db)))}]]))

   :r.new-booking
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundary r
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
       [page-boundary r
        ;[:div.-debugx.select-none [booking.common-views/header-control-panel]]
        {:whole [:div.p-4 [content.booking-blog/render
                           {:fsm  {}
                            :uid  (:uid @user-auth)
                            :path ["booking-posts" "articles"]}]]}]))

   :r.booking-blog-doc
   (fn [r]
     (let [id (some-> r :path-params :id)
           data (db/on-value-reaction {:path ["booking-posts" "articles" (name id)]})]
       [page-boundary r

        ;[l/ppre-x r id @data]
        [:div.max-w-xl.mx-auto.container
         [sc/markdown [schpaa.markdown/md->html (:content @data)]]]]))

   :r.user
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundary r
        ;[user.views/userstatus-form user-auth]
        ;[render-back-tabbar]
        [user.views/my-info {}]]))

   :r.logg
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [page-boundary r
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
     [page-boundary r
      [booking.lab/render r]])

   :r.designlanguage
   (fn [r]
     [page-boundary r
      [:div.pb-32
       [collapsable-memory
        :color-palette
        [sc/separator "Color and grayscale palette"]
        (into [:div.grid.gap-2
               {:style {:grid-template-columns "repeat(auto-fit,minmax(6rem,1fr))"}}]
              (map (fn [[styles tag]]
                     [sc/surface-b-sans-bg {:class []
                                            :style (conj styles {:box-shadow   "var(--inner-shadow-2)"
                                                                 :font-size    "var(--font-size-0)"
                                                                 :aspect-ratio "1/1"})} tag])
                   [[{:color      "var(--green-0)"
                      :background "var(--green-5)"} "call to action"]


                    [{:color      "var(--red-0)"
                      :background "var(--red-5)"} "dangerous and irreversible actions"]


                    [{:color      "var(--surface5)"
                      :background "var(--brand0)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--brand1)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--brand2)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--yellow-2)"} :highlighter]
                    [{:color      "var(--surface5)"
                      :background "var(--surface1)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface2)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface3)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface4)"} :tag]
                    [{:color      "var(--surface0)"
                      :background "var(--surface5)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface0)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface00)"} :tag]
                    [{:color      "var(--surface5)"
                      :background "var(--surface000)"} :tag]]))]

       [collapsable-memory
        :fonts
        [sc/separator "Fonts"]
        (let [f (fn [font-name font-size line-height e]
                  (let [line-height (or line-height "auto")
                        e (ta/format e font-name font-size line-height)]
                    [:div
                     {:style {:font-weight "400"
                              :font-family font-name
                              :font-size   font-size
                              :line-height line-height}}
                     [:div e]
                     [:div {:style {:font-style :italic}} e]
                     [:div {:style {:font-weight "100"}} e " 100"]
                     [:div {:style {:font-weight "900"}} e " 900"]
                     [:div (apply str (range 10))]
                     [:div [:strong (apply str (range 10))]]]))]

          [:<>
           [sc/col {:class [:space-y-8]}
            [sc/col {:class [:space-y-4]}
             (f "Inter" "16px" "20px" "%s %s/%s sans-serif")
             (f "Inter" "24px" "26px" "%s %s/%s sans-serif")
             (f "Montserrat" "18px" "22px" "%s %s/%s sans-serif")
             (f "Montserrat" "24px" "28px" "%s %s/%s sans-serif")
             (f "Montserrat" "32px" "38px" "%s %s/%s sans-serif")
             (f "Lora" "16px" "18px" "%s %s/%s serif")
             (f "Lora" "24px" "30px" "%s %s/%s serif")
             (f "Lora" "32px" "38px" "%s %s/%s serif")]]])]

       [collapsable-memory
        :markdown
        [sc/separator "Markdown styles"]
        [sc/surface-a
         (-> (inline "./markdown-example.md") schpaa.markdown/md->html sc/markdown)]]
       [collapsable-memory
        :styles
        [sc/separator "Styles"]
        (into [:div.space-y-1]
              (map (fn [c] [sc/surface-a {:class [:p-2]} c])
                   [[sc/small "sc/small"]
                    [sc/separator "sc/separator"]
                    [sc/col-fields
                     [sc/header-title "sc/header-title"]
                     [sc/header-title "sc/header-title"]
                     [sc/header-title "sc/header-title"]]
                    [sc/col
                     [sc/dialog-title "sc/dialog-title"]
                     [sc/dialog-title "sc/dialog-title"]
                     [sc/dialog-title "sc/dialog-title"]]
                    [sc/subtitle "sc/subtitle"]
                    [sc/subtext "sc/subtext"]
                    [sc/text "sc/text"]
                    [sc/field-label "sc/field-label"]
                    [sc/title "sc/title"]
                    [sc/pill "sc/pill"]
                    [sc/text "sc/row-fields"
                     [sc/row-fields
                      [sc/text "field"]
                      [sc/text "field"]
                      [sc/text "field"]]]
                    [sc/text "sc/row-stretch"
                     [sc/row-stretch
                      [sc/text "field"]
                      [sc/text "field"]
                      [sc/text "field"]]]
                    [sc/text "sc/row-end"
                     [sc/row-end
                      [sc/text "field"]
                      [sc/text "field"]
                      [sc/text "field"]]]
                    [sc/text "sc/row-wrap (gap-4)"
                     [sc/row-wrap
                      [sc/text "field"]
                      [sc/text "field"]
                      [sc/text "field"]]]
                    [sc/text "sc/row-gap (gap-2)"
                     [sc/row-gap
                      [sc/text "field"]
                      [sc/text "field"]
                      [sc/text "field"]]]]))]]])

   :r.terms
   (fn [r]
     [page-boundary r
      (-> (inline "./content/regler-utenom-vakt.md") schpaa.markdown/md->html sc/markdown)])

   :r.conditions
   (fn [r]
     [page-boundary r
      (-> (inline "./content/plikter-som-vakt.md") schpaa.markdown/md->html sc/markdown)])

   :r.booking.retningslinjer
   (fn [r]
     [page-boundary r
      (-> (inline "./content/retningslinjer.md") schpaa.markdown/md->html sc/markdown)])

   :r.booking-blog-new
   (fn [r]
     [page-boundary r
      (r/with-let [data (db/on-value-reaction {:path ["booking-posts" "articles"]})
                   receipts' (db/on-value-reaction {:path ["booking-posts" "receipts"]})
                   #_#_receipts (reduce (fn [a [k v]] (update a (-> v :articles :id) (fnil inc 0))) {} @receipts')]
        (when @receipts'
          (let [receipts (reduce (fn [a [k v]] (update a (-> v :articles :id) (fnil inc 0))) {} @receipts')]
            [:<>
             ;[l/ppre-x receipts]
             [sc/row-center
              [scb2/cta-large {:on-click schpaa.style.dialog/open-dialog-addpost} "Skriv et nytt innlegg"]]
             (when @data
               (into [:<>]
                     (for [[k {:keys [content date]}] @data]
                       [listitem
                        content
                        [:div (get receipts (name k))]
                        [schpaa.time/flex-date
                         {:time-class [:text-xs]}
                         (t/date-time (t/instant date))
                         (fn [current-time] (t/format "dd. MMMM 'kl' hh.mm.ss" (t/date-time current-time)))]])))
             #_[l/ppre-x receipts']])))])
   :r.calendar
   (fn [r]
     [page-boundary r
      ;[:div.-debugx.select-none [booking.common-views/header-control-panel]]
      [:div {:class [:gap-1]
             :style {:display               :grid
                     :grid-auto-rows        "3rem"
                     :grid-template-columns "repeat(7,minmax(1rem,1fr))"}}
       (for [e (range 7)]
         [:div.flex.flex-centerx.bg-white.p-1.self-end [sc/small (ta/week-name e :length 3 #_(t/>> (t/now) (t/new-duration e :days)))]])
       (for [e (concat (map - (reverse (range 1 4))) (range 30))]
         [:div.bg-white.p-1
          {:class [:duration-200 :hover:bg-gray-200 :hover:text-white]}
          (if (neg? e)
            [:div]
            [sc/small e])])]])

   :r.fileman-temporary
   (fn [r]
     (let [data (db/on-value-reaction {:path ["booking-posts" "articles"]})]
       [page-boundary r
        {:whole [booking.fileman/render (r/atom true) @data #_(for [e (range 15)] {:item e})]}]))

   :r.aktivitetsliste
   (fn [r]
     [page-boundary r
      [:div
       [:div.-debugx.select-none [booking.common-views/header-control-panel]]
       [booking.aktivitetsliste/render r]]])

   :r.booking.faq
   (fn [r]
     [page-boundary r
      (-> (inline "./content/faq.md") schpaa.markdown/md->html sc/markdown)])

   :r.booking
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundary r
        [sc/col {:class [:space-y-4 :max-w-md :mx-auto]}
         ;[l/ppre (.getPropertyValue (js/document.getComputedStyle ":root") "--brand0-light")]
         ;[l/ppre (. js/document documentElement -getComputedStyle getPropertyValue "--brand0-light")]
         #_[l/ppre (gs/getComputedStyle js/document.documentElement "--brand1")]
         #_(let [el (-> js/document.documentElement .-style)
                 prop (fn [] (ta/format "hsla(%d,35%,50%,1)" (rand-int 365)))]

             [:<>
              [scb/button-cta {:on-click #(let [p (prop)]
                                            (.setProperty el "--brand1" p)
                                            (.setProperty el "--buttoncta1" p))} "Randomize color"]])
         #_[l/ppre (gs/getComputedStyle js/document.documentElement "--brand1")]
         [:div
          (-> "./frontpage1.md"
              inline
              schpaa.markdown/md->html
              sc/markdown)]
         [sc/row-end
          [scb2/cta-large
           {:type "button" :on-click open-dialog-signin}
           "Begynn her"]]]


        ;[db.signin/login]]
        #_[:div [content.overview/overview]]]))
   #_(fn [r]
       [page-boundary r
        [:div "base page for booking/sjøbasen"]
        #_(-> (inline "./content/faq.md") schpaa.markdown/md->html sc/markdown)])

   :r.page-not-found
   error-page})

