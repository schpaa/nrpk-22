(ns booking.spa
  (:require [booking.page-layout :refer [page-boundary +page-builder]]
            [booking.content.booking-blog :as content.booking-blog :refer [render]]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [booking.content.overview :as content.overview]
            [booking.hoc :as hoc]
            [booking.lab]
            [booking.views]
            [db.core :as db]
            [db.signin]
            [eykt.content.rapport-side]
            [kee-frame.router]
            [nrpk.fsm-helpers :refer [send]]
            [re-frame.core :as rf]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.darkmode]
            [schpaa.debug :as l]
            [shadow.resource :refer [inline]]
            [schpaa.style :as st]
            [schpaa.style.menu]
            [schpaa.style.ornament :as sc]
            [tick.core :as t]
            [user.views]
            [schpaa.style.dialog :refer [open-dialog-sampleautomessage]]
            [times.api :as ta]
            [schpaa.time]
            [booking.aktivitetsliste]
            [booking.yearwheel]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.design-debug]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [user.database]
            [booking.ico :as ico]
            [booking.personas]
            [booking.frontpage]
            [booking.presence]
            [booking.modals.commandpalette]
            [booking.utlan]
            [booking.reports]
            [booking.modals.boatinput]
            [booking.booking]
            [booking.mine-dine-vakter]
            [booking.boatlist]
            [booking.dine-vakter]
            [booking.min-status]
            [booking.oversikt]
            [booking.users]
            [eykt.calendar.core]
            [booking.temperature]
            [booking.oversikt.styret :as bos]))

;; shortcuts

(defn page [r {:keys [render render-fullwidth] :as c}]
  [+page-builder r (assoc c :render (when render #(render r))
                            :render-fullwidth (when render-fullwidth #(render-fullwidth r)))])

;region related to flex-date and how to display relative time

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

;region temp helpers

(o/defstyled listitem :div
  ([a b c]
   [sc/row'
    [sc/text1 {:class [:w-8 :shrink-0]} b]
    [hoc.buttons/regular "Rediger"]
    [sc/subtext {:class [:line-clamp-3 :grow]} a]
    #_[sc/text1 c]]))

(defn error-page [r]
  [sc/col {:class [:p-1 :space-y-4 :xmx-auto :xmax-w-xs :xsm:max-w-md]}
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
     [:div.relative [:img.object-cover {:src "/img/logo-n2.jpg"}]]]]

   [sc/text1 "Finner ikke noe p?? denne adressen! Det er kanskje en gammel lenke. Se om du finner det du leter etter i denne listen:"]
   (into [:div.space-y-1] (for [{:keys [id name icon disabled action keywords] :as e}
                                (sort-by :name < (remove :private (booking.modals.commandpalette/commands)))]
                            [:div [sc/subtext-with-link {:on-click action} name]]))])

(def routing-table
  {:r.welcome
   (fn [r]
     [l/pre r])

   :r.dokumenter
   (fn [r]
     [+page-builder r
      {:render (fn []
                 (let [doc-id (-> r :path-params :id)]
                   [:div
                    (case doc-id
                      "huskeliste-ved-n??kkelvakt" (-> (inline "./oversikt/huskeliste-ved-n??kkelvakt.md") schpaa.markdown/md->html sc/markdown)
                      "tidslinje-forklaring" (-> (inline "./oversikt/tidslinje-forklaring.md") schpaa.markdown/md->html sc/markdown)
                      "hms-h??ndbok" (-> (inline "./oversikt/hms-h??ndbok.md") schpaa.markdown/md->html sc/markdown)
                      "vaktinstruks" (-> (inline "./content/vaktinstruks.md") schpaa.markdown/md->html sc/markdown)
                      "regler-utenom-vakt" (-> (inline "./content/regler-utenom-vakt.md") schpaa.markdown/md->html sc/markdown)
                      "hms-hendelse" (-> (inline "./oversikt/hms-hendelse.md") schpaa.markdown/md->html sc/markdown)
                      "ved-underbemanning" (-> (inline "./oversikt/ved-underbemanning.md") schpaa.markdown/md->html sc/markdown)
                      "vaktrapportskjemaet" (-> (inline "./oversikt/vaktrapportskjemaet.md") schpaa.markdown/md->html sc/markdown)
                      "sikkerhetsutstyr-ved-n??klevann" (-> (inline "./oversikt/sikkerhetsutstyr-ved-n??klevann.md") schpaa.markdown/md->html sc/markdown)
                      "kommer" (-> (inline "./oversikt/kommer.md") schpaa.markdown/md->html sc/markdown)
                      [:div doc-id])]))}])

   :r.forsiden-iframe
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [page-boundary r
        {:whole [:iframe.w-full.h-screen
                 {:src "https://nrpk.no"}]}
        [sc/col {:class [:space-y-4 :max-w-md :mx-auto]}]]))

   :r.forsiden
   (fn forsiden [r]
     [+page-builder r
      {:frontpage true
       :render    booking.frontpage/frontpage}])

   :r.booking.oversikt
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [+page-builder r
        {:panel  content.overview/panel
         :render (fn [_] [content.overview/overview
                          {:uids (:uid @user-auth)
                           :data (or [{}]
                                     (sort-by (comp :number val) < (logg.database/boat-db)))}])}]))

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



   :r.booking-blog-doc
   (fn [r]
     (let [id (some-> r :path-params :id)
           data (db/on-value-reaction {:path ["booking-posts" "articles" (name id)]})]
       [+page-builder r
        ;[l/ppre-x r id @data]
        {:render (fn [] [:div.max-w-xl.mx-auto.container
                         [sc/markdown [schpaa.markdown/md->html (:content @data)]]])}]))

   :r.user
   (fn [r]
     (let [user-auth @(rf/subscribe [::db/user-auth])]
       [+page-builder r
        {;:always-panel user.views/always-panel
         :render user.views/my-info}]))

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


   :r.designlanguage
   (fn [r]
     [+page-builder r
      {:render (fn [] [booking.design-debug/render])}])


   :r.terms
   (fn [r]
     [+page-builder r
      {:render (fn [] (-> (inline "./content/regler-utenom-vakt.md") schpaa.markdown/md->html sc/markdown))}])

   :r.conditions
   (fn [r]
     [+page-builder r
      {:render (fn [] (-> (inline "./content/plikter-som-vakt.md") schpaa.markdown/md->html sc/markdown))}])

   :r.booking.retningslinjer
   (fn [r]
     [+page-builder r
      {:render (fn [] (-> (inline "./content/retningslinjer.md") schpaa.markdown/md->html sc/markdown))}])

   :r.booking-blog-new
   (fn [r]
     [+page-builder r
      {:render (fn [] (r/with-let [data (db/on-value-reaction {:path ["booking-posts" "articles"]})
                                   receipts' (db/on-value-reaction {:path ["booking-posts" "receipts"]})
                                   #_#_receipts (reduce (fn [a [k v]] (update a (-> v :articles :id) (fnil inc 0))) {} @receipts')]
                        (when @receipts'
                          (let [receipts (reduce (fn [a [k v]] (update a (-> v :articles :id) (fnil inc 0))) {} @receipts')]
                            [:<>
                             ;[l/ppre-x receipts]
                             [sc/row-center
                              [hoc.buttons/cta {:on-click schpaa.style.dialog/open-dialog-addpost} "Skriv et nytt innlegg"]]
                             (when @data
                               (into [:<>]
                                     (for [[k {:keys [content date]}] @data]
                                       [listitem
                                        content
                                        [:div (get receipts (name k))]
                                        #_[schpaa.time/flex-date
                                           {:time-class [:text-xs]}
                                           (t/date-time (t/instant date))
                                           (fn [current-time] (t/format "dd. MMMM 'kl' hh.mm.ss" (t/date-time current-time)))]])))
                             #_[l/pre receipts']]))))}])
   :r.calendar
   (fn [r]
     [+page-builder r
      {:always-panel (fn [c]
                       [sc/row-sc-g2-w
                        [hoc.toggles/button-cta #(js/alert "!") "act!"]
                        [hoc.toggles/button-reg #(js/alert "!") "now!"]
                        [hoc.toggles/button-reg #(js/alert "!") "hurry!"]])
       :panel        (fn [c]
                       [sc/row-sc-g2-w
                        [hoc.toggles/ls-sm :calendar/show-history "Vis historikk2"]
                        [hoc.toggles/ls-sm :calendar/show-hidden "Vis skjulte2"]])

       :render       (fn [_] [:div {:class [:gap-1]
                                    :style {:display               :grid
                                            :grid-template-columns "repeat(7,minmax(1rem,1fr))"
                                            :grid-template-rows    "1rem"
                                            :grid-auto-rows        "4rem"}}

                              (for [e (range 7)]
                                [:div.flex.p-1.self-end.w-full
                                 {:style {:background "red"
                                          :color      "yellow"}}
                                 [sc/small1 (ta/week-name e :length 3)]])
                              (for [e (concat (map - (reverse (range 1 4))) (range 30))]
                                [:div.p-1
                                 {:style {:background "blue"
                                          :color      "white"}
                                  :class [:duration-200 :hover:bg-gray-200 :hover:text-white]}
                                 (if (neg? e)
                                   [:div]
                                   [sc/small1 e])])])}])

   :r.fileman-temporary
   (fn [r]
     (let [data (db/on-value-reaction {:path ["booking-posts" "articles"]})]
       [+page-builder r
        {:panel        booking.fileman/panel
         :always-panel booking.fileman/always-panel
         :render       (fn [] [booking.fileman/render (r/atom true) @data])}]))

   :r.aktivitetsliste
   (fn [r]
     ;todo: () or [], and does it matter here?
     [+page-builder r
      {:render-fullwidth booking.aktivitetsliste/render
       :panel            booking.aktivitetsliste/panel
       :always-panel     booking.aktivitetsliste/always-panel}])

   :r.booking.faq
   (fn [r]
     [+page-builder r
      {:render (fn [_] (-> (inline "./content/faq.md") schpaa.markdown/md->html sc/markdown))}])


   :r.yearwheel
   (fn [r]
     [+page-builder r {;:panel           booking.yearwheel/panel
                       :headline-plugin booking.yearwheel/headline-plugin
                       ;:always-panel    booking.yearwheel/always-panel
                       :render          booking.yearwheel/render}])

   :r.nokkelvakt
   (fn [r]
     ;:calendar/show-only-available
     (let [user (rf/subscribe [::db/user-auth])
           db (rf/subscribe [:db/boat-db])
           boat-types (rf/subscribe [:db/boat-type])]

       [+page-builder r
        {:always-panel (fn [] [sc/col-space-4
                               [sc/row-sc-g2-w
                                ;[hoc.buttons/cta-pill {:disabled false :class [:narrow]} "V??r/Sommer"]
                                ;[hoc.buttons/reg-pill {:disabled false :class [:narrow]} "Utvidet ??pningstid"]
                                ;[hoc.buttons/reg-pill {:disabled true :class [:narrow]} "Sensommer"]
                                ;[hoc.buttons/reg-pill {:disabled true :class [:narrow]} "H??st"]
                                [hoc.toggles/ls-sm :calendar/show-only-available "Skjul komplette ??kter"]]])

         :render       (fn []
                         (if-let [uid (:uid @user)]
                           [sc/col-space-4
                            [eykt.calendar.core/render r]]
                           [l/pre @user]))}]))


   :r.admin
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:div
                  (l/pre (booking.page-layout/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                  [:div "admin"]])}])

   :r.signedout
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:div
                  (l/pre (booking.page-layout/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                  [:div "Du har alts?? logget ut"]])}])


   :r.oversikt.styret
   (fn [r]
     [+page-builder r
      {:render bos/render}])

   :r.oversikt.organisasjon
   (fn [r]
     [+page-builder
      r
      {:render (fn []
                 (do
                   [:<>
                    (-> (inline "./oversikt/organisasjon.md") schpaa.markdown/md->html sc/markdown)]))}])

   :r.presence
   (fn [r]
     (let [data (rf/subscribe [::db/presence-status])]
       [+page-builder r
        {;:panel            booking.presence/panel
         :always-panel booking.presence/always
         :render       #(booking.presence/render r data)}]))


   #_(fn r []
       [+page-builder r
        {:aa               {}
         :render-fullwidth #(booking.users/render r data)}])

   :r.utlan              (fn [r]
                           (let [uid @(rf/subscribe [:lab/uid])]
                             [+page-builder r
                              {:headline-plugin booking.utlan/headline-plugin
                               :render          booking.utlan/render-new}]))

   :r.debug              (fn [r] (page r {:always-panel     booking.lab/always-panel
                                          :render-fullwidth booking.lab/render}))
   :r.oversikt           (fn [r] (page r {:render booking.oversikt/render}))
   ;todo Fordi n??r man skal bytte er det greit ?? alltid ha ett sted hvor dette kan skje
   :r.dine-vakter        (fn [r] (page r {:render booking.dine-vakter/render}))
   :r.min-status         (fn [r] (page r {:render-halfwidth true
                                          :render           booking.min-status/render}))
   :r.mine-vakter-ipad   (fn [r] (page r {:render booking.min-status/render}))
   :r.reports            (fn [r] (page r (booking.reports/page r)))
   :r.b??tliste.n??klevann (fn [r] (page r {:headline-plugin booking.lab/headline-plugin
                                          :always-panel    booking.lab/always-panel
                                          :render          booking.lab/render}
                                       #_(booking.boatlist/page r)))
   :r.experimental
   (fn [r]
     (page r {:renderx
              (fn []
                [:div.-debug3.px-4
                 (for [e (range 10)] [sc/text1 e])])
              :headline-plugin
              (letfn [(degrees-celsius [c]
                        [:span c [:sup "??c"]])
                      (centerline [a b]
                        [sc/row {:style {:align-items :baseline
                                         :width       "100%"}
                                 :class [:gap-1]}
                         [:div {:style {:flex         "1"
                                        :text-align   :right
                                        :justify-self :end}} a]
                         [:div {:style {:flex         "1"
                                        :justify-self :start}} b]])]
                (fn [] [[:div
                         {:style {:padding-block "0.25rem"}}
                         [sc/col
                          {:style {:width "min-content"}
                           :class [:p-0 :m-0 :-debug2x]}
                          (centerline [sc/text1 "luft"] [sc/title (degrees-celsius 23)])
                          (centerline [sc/text1 "vann"] [sc/title (degrees-celsius 13)])]]]))}))
   :r.b??tliste.sj??basen
   (fn [r] (page r {:render (fn []
                              [sc/col
                               (for [e (range 30)]
                                 [sc/text1 {:class [:tabular-nums]}
                                  (let [n (.toString e 2)
                                        c (- 8 (count n))]
                                    (apply str n (take c (repeatedly (constantly " 0 ")))))])])}))
   :r.booking            (fn [r] (page r {:render (booking.booking/page)}))

   #_#_:r.temperature (fn [r] (page r {:headline-plugin booking.utlan/headline-plugin
                                       :render          (fn [] [booking.temperature/render r])}))
   :r.users
   (fn [r] (page r {:render-fullwidth booking.users/render
                    :always-panel     booking.users/always-panel
                    :panel            booking.users/panel}))
   :r.booking-blog
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       (fn [r]
         (page r {:always-panel content.booking-blog/always-panel
                  :srender      (fn [r] [content.booking-blog/render
                                         {:fsm  {}
                                          :uid  (:uid @user-auth)
                                          :path ["booking-posts" "articles"]}])}))))
   :r.page-not-found     (fn [r] (page r {:render (fn [] [error-page r])}))})

(comment
  (do
    (sort-by (juxt (comp js/parseInt :b) :a) <
             [{:a 1 :b " 222 "} {:a 2 :b " 1 "} {:b " 111 "}])))

(comment
  (do
    (let [a 1]
      a)))
