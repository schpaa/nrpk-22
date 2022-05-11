(ns booking.spa
  (:require [booking.common-views :refer [page-boundary +page-builder]]
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
            [schpaa.style.button :as scb]
            [booking.modals.commandpalette]
            [booking.utlan]
            [booking.common-widgets :as widgets]
            [booking.reports]
            [booking.modals.boatinput]
            [booking.booking]
            [booking.mine-dine-vakter]
            [booking.boatlist]
            [booking.dine-vakter]
            [booking.min-status]
            [booking.oversikt]
            [booking.users]
            [eykt.calendar.core]))

;; shortcuts

(defn page [r c]
  [+page-builder r c])

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
     [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]

   [sc/text1 "Finner ikke noe på denne adressen! Det er kanskje en gammel lenke. Se om du finner det du leter etter i denne listen:"]
   (into [:div.space-y-1] (for [{:keys [id name icon disabled action keywords] :as e}
                                (sort-by :name < booking.modals.commandpalette/commands)]
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
                      "huskeliste-ved-nøkkelvakt" (-> (inline "./oversikt/huskeliste-ved-nøkkelvakt.md") schpaa.markdown/md->html sc/markdown)
                      "tidslinje-forklaring" (-> (inline "./oversikt/tidslinje-forklaring.md") schpaa.markdown/md->html sc/markdown)
                      "hms-håndbok" (-> (inline "./oversikt/hms-håndbok.md") schpaa.markdown/md->html sc/markdown)
                      "vaktinstruks" (-> (inline "./content/vaktinstruks.md") schpaa.markdown/md->html sc/markdown)
                      "regler-utenom-vakt" (-> (inline "./content/regler-utenom-vakt.md") schpaa.markdown/md->html sc/markdown)
                      "hms-hendelse" (-> (inline "./oversikt/hms-hendelse.md") schpaa.markdown/md->html sc/markdown)
                      "ved-underbemanning" (-> (inline "./oversikt/ved-underbemanning.md") schpaa.markdown/md->html sc/markdown)
                      "vaktrapportskjemaet" (-> (inline "./oversikt/vaktrapportskjemaet.md") schpaa.markdown/md->html sc/markdown)
                      "sikkerhetsutstyr-ved-nøklevann" (-> (inline "./oversikt/sikkerhetsutstyr-ved-nøklevann.md") schpaa.markdown/md->html sc/markdown)
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

   :r.booking-blog
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [+page-builder r
        {:always-panel content.booking-blog/always-panel
         :render       (fn [] [content.booking-blog/render
                               {:fsm  {}
                                :uid  (:uid @user-auth)
                                :path ["booking-posts" "articles"]}])}]))

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
        {:always-panel user.views/always-panel
         :render       user.views/my-info}]))

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
     [+page-builder r
      {:render (fn [] [booking.lab/render r])}])

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
     [+page-builder r {:panel        booking.yearwheel/panel
                       :always-panel booking.yearwheel/always-panel
                       :render       booking.yearwheel/render}])

   :r.nokkelvakt
   (fn [r]
     ;:calendar/show-only-available
     (let [user (rf/subscribe [::db/user-auth])
           db (rf/subscribe [:db/boat-db])
           boat-types (rf/subscribe [:db/boat-type])]

       [+page-builder r
        {:always-panel (fn [] [sc/col-space-4
                               [sc/row-sc-g2-w
                                ;[hoc.buttons/cta-pill {:disabled false :class [:narrow]} "Vår/Sommer"]
                                ;[hoc.buttons/reg-pill {:disabled false :class [:narrow]} "Utvidet åpningstid"]
                                ;[hoc.buttons/reg-pill {:disabled true :class [:narrow]} "Sensommer"]
                                ;[hoc.buttons/reg-pill {:disabled true :class [:narrow]} "Høst"]
                                [hoc.toggles/ls-sm :calendar/show-only-available "Skjul komplette økter"]]])

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
                  (l/pre (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                  [:div "admin"]])}])

   :r.signedout
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:div
                  (l/pre (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                  [:div "Du har altså logget ut"]])}])


   :r.oversikt.styret
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:<>
                  (-> (inline "./oversikt/styret.md") schpaa.markdown/md->html sc/markdown)
                  (let [data [["/img/personas/noun-persona-436706.png" :m "Ulf Pedersen" "Styreleder" ["Nøkkelvakt\u00adansvarlig"]]
                              ["/img/personas/noun-persona-622291.png" :m "Tormod Tørstad" "Nestleder" ["Anleggs\u00ADansvarlig for Sjøbasen" "Utstyrs\u00ADansvarlig"]]
                              ["/img/personas/noun-persona-633483.png" :m "Stein-Owe Hansen" "Styremedlem" ["Sekretær"]]
                              ["/img/personas/noun-persona-410775.png" :m "Adrian A. Mitchell" "Styremedlem" ["Kasserer"]]
                              ["/img/personas/noun-persona-622293.png" :m "Chris P. Schreiner" "Styremedlem" ["Hjemmesiden" "Booking" "Båtlogg" "Eykt"]]
                              ["/img/personas/noun-persona-4144954.png" :f "Line Stolpestad" "Styremedlem" ["Aktivitets\u00ADansvarlig"]]
                              ["/img/personas/noun-persona-426505.png" :m "Jan Gunnar Jakobsen" "Vara" []]
                              ["/img/personas/noun-persona-497844.png" :f "Ylva Eide" "Styremedlem" []]
                              ["/img/personas/noun-persona-415635.png" :f "Kjersti Selseth" "Styremedlem" []]
                              ["/img/personas/noun-persona-488544.png" :m "Ole Håbjørn Larsen" "Vara" ["Anleggs\u00ADansvarlig Nøklevann"]]]]
                    (o/defstyled card :div.relative
                      :p-4
                      {:min-height       "13ch"
                       :background-color "var(--floating)"
                       :border-radius    "var(--radius-1)"
                       :box-shadow       "var(--shadow-1)"})

                    (o/defstyled image-container :div.relative
                      :overflow-hidden
                      {:border-radius "var(--radius-round)"
                       ;:border        "2px solid red"
                       :aspect-ratio  "1/1"
                       :height        "var(--size-10)"
                       :clip-path     "circle(100%)"
                       :overflow      :hidden})

                    (o/defstyled image :img
                      :p-2 :overflow-hidden
                      {:transition       "750ms ease-in"
                       :transform-origin "bottom left"
                       :background       "var(--textmarker-background)"
                       :color            "var(--text3)"
                       :aspect-ratio     "1/1"
                       :height           "var(--size-10)"
                       :box-shadow       "var(--inner-shadow-1)"
                       :border-radius    "var(--radius-round)"}
                      [:&:hover
                       {;:transition       "2000ms"

                        :transform "rotate(-70deg) "}])

                    [:div {:style {:display               :grid
                                   :gap                   "8px 8px"
                                   :grid-template-columns "repeat(auto-fit,minmax(20ch,1fr))"}}
                     (for [[idx [url gender navn role ansvar]] (map-indexed vector data)]
                       [card
                        [:div                               ;.flex.items-start.justify-between.gap-4
                         [:div.float-right.pl-2.-mr-2.-mt-2
                          [image-container
                           [image {:src (if url
                                          url
                                          (first (nth (filter (comp #(= gender %) second) booking.personas/faces) idx)))}]
                           [:img {:style {:position   :absolute
                                          :inset      "0"
                                          :margin     :auto
                                          :width      "90%"
                                          :z-index    -1
                                          :object-fit :contain}
                                  :src   "/img/tidslinje_3.png"}]]]
                         [:div.clear-left.space-y-1.mr-3
                          [sc/small2 {:style {:font-family "Merriweather"}} role]
                          [sc/text1 navn]
                          [sc/small1
                           (interpose ", "
                                      (map (fn [e]
                                             [:span {:style {:line-height "var(--font-lineheight-3)"}} e])
                                           ansvar))]]]
                        [:div.absolute.bottom-2.right-2
                         (sc/icon-small
                           {:style    {:color "var(--text3)"}
                            :on-click #(rf/dispatch [:app/give-feedback {:navn navn :caption "Vær kort og konstruktiv. Meldingen blir ikke nødvendigvis besvart."}])}
                           ico/tilbakemelding)]])])])}])
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
         :always-panel     booking.presence/always
         :render-fullwidth #(booking.presence/render r data)}]))

   :r.users
   (fn [r] (page r {:render-fullwidth booking.users/render
                    :always-panel     booking.users/always-panel
                    :panel            booking.users/panel}))
   #_(fn r []
       [+page-builder r
        {:aa               {}
         :render-fullwidth #(booking.users/render r data)}])

   :r.utlan
   (fn [r]
     (let [uid @(rf/subscribe [:lab/uid])]
       [+page-builder r
        {:panel        booking.utlan/panel
         :always-panel booking.utlan/always-panel
         :render       #(booking.utlan/render uid)}]))

   :r.oversikt           (fn [r] (page r {:render booking.oversikt/render}))
   ;todo Fordi når man skal bytte er det greit å ha et sted hvor dette skjer
   :r.dine-vakter        (fn [r] (page r {:render booking.dine-vakter/render}))
   :r.min-status         (fn [r] (page r {:render booking.min-status/render}))
   :r.mine-vakter-ipad   (fn [r] (page r {:render booking.min-status/render}))
   :r.reports            (fn [r] (page r (booking.reports/page r)))
   :r.båtliste.nøklevann (fn [r] (page r (booking.boatlist/page r)))
   :r.båtliste.sjøbasen  (fn [r] (page r {:render (fn [] [sc/col
                                                          (for [e (range 30)]
                                                            [sc/text1 {:class [:tabular-nums]}
                                                             (let [n (.toString e 2)
                                                                   c (- 8 (count n))]
                                                               (apply str n (take c (repeatedly (constantly " 0 ")))))])])}))
   :r.booking            (fn [r] (page r (booking.booking/page r)))
   :r.page-not-found     (fn [r] (page r {:render (fn [] [error-page r])}))})

(comment
  (sort-by (juxt (comp js/parseInt :b) :a) <
           [{:a 1 :b " 222 "} {:a 2 :b " 1 "} {:b " 111 "}]))
