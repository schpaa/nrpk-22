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
            [eykt.content.pages]
            [eykt.content.rapport-side]
            [kee-frame.router]
            [nrpk.fsm-helpers :refer [send]]
            [re-frame.core :as rf]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.darkmode]
            [schpaa.debug :as l]
            [shadow.resource :refer [inline]]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style :as st]
    ;[schpaa.style.booking]
            [schpaa.style.menu]
            [schpaa.style.ornament :as sc]
            [schpaa.time]
            [tick.core :as t]
            [user.views]
            [schpaa.style.dialog :refer [open-dialog-signin open-dialog-sampleautomessage]]
            [schpaa.style.button2 :as scb2]
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
            [schpaa.style.button :as scb]))

;region related to flex-date and how to display relative time

(rf/reg-sub :app/show-relative-time :-> :app/show-relative-time)

(rf/reg-event-db :app/show-relative-time-toggle (fn [db] (update db :app/show-relative-time (fnil not false))))

;region todo: extract these higher-order-components

;endregion

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
        (-> "./content/frontpage.md"
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
         (-> "./content/frontpage1.md"
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
         (-> "./content/frontpage2.md"
             inline
             schpaa.markdown/md->html
             st/prose-markdown-styles)]]]]]))

;region temp helpers

(o/defstyled listitem :div
  ([a b c]
   [sc/row'
    [sc/text1 {:class [:w-8 :shrink-0]} b]
    [hoc.buttons/regular "Rediger"]
    [sc/subtext {:class [:line-clamp-3 :grow]} a]
    #_[sc/text1 c]]))

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

    [sc/text1 "Finner ikke noe på denne adressen! Det er sikkert bare en gammel lenke. Kanskje du finner det du leter etter i denne listen?"]
    (into [:div.space-y-1] (for [{:keys [id name icon disabled action keywords] :as e}
                                 (sort-by :name < schpaa.style.combobox/commands)]
                             [:div [sc/subtext-with-link {:on-click action} name]]))]])


(defn opening-hours []
  (let [f (fn [[a b]] [sc/subtext-with-link
                       {:style {:margin-left "-2px"}
                        :href  (kee-frame.core/path-for [a])}
                       b])]
    [sc/col-space-1
     [sc/ingress {:style {:text-align :rights
                          :font-size  "var(--font-size-fluid-1)"
                          :xcolor     "white"}} "Åpningstider"]
     ;[sc/ingress "Sesongen er inndelt i perioder."]
     (let [data [["tirsdag" "18:00" "21:00"]
                 ["onsdag" "18:00" "21:00"]
                 ["torsdag" "18:00" "15:00"]
                 ["lørdag" "11:00" "17:00"]
                 ["søndag" "11:00" "17:00"]]]
       [:<>
        ;[sc/subtext "Sesongen åpner xx.xx og frem til xx vil dette være våre åpningstider"]
        [:div {:style {:display               :grid
                       :column-gap            "0.25rem"
                       :row-gap               "0.25rem"
                       :grid-template-columns "5rem min-content min-content min-content"
                       :place-content         :end}}
         (for [[day open close] data]
           [:<>
            [sc/subtext {:style {:font-size "var(--font-size-fluid-0)" :xcolor "white"}} day]
            [sc/subtext {:class [:tabular-nums] :style {:font-size "var(--font-size-fluid-0)" :xcolor "white"}} open]
            [sc/subtext {:class [:tabular-nums] :style {:font-size "var(--font-size-fluid-0)" :xcolor "white"}} "—"]
            [sc/subtext {:class [:tabular-nums] :style {:font-size "var(--font-size-fluid-0)" :xcolor "white"}} close]])]
        [:div {:style {:margin-top "12px"}}
         [sc/subtext-with-link
          {:href  ()
           :style {:white-space :normal
                   :margin-left "-3px"
                   :font-size   "var(--font-size-fluid-0)" :xcolor "white"}}
          "Om utvidet åpningstid i juni"]]
        #_(let [data [[1 :r.forsiden "Utvidet åpningstid i sommer"]
                      [4 :r.oversikt "Oversikt"]]]
            [sc/row-sc-g2-w (map (comp f rest) (sort-by first data))])])]))






(def routing-table
  {:r.welcome
   (fn [r]
     #_[page-boundary r
        [db.signin/login]
        [welcome]])

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
       ; :panel     (fn [] [:div "panel"])
       :render    booking.frontpage/frontpage}])

   :r.oversikt
   (fn oversikt [r]
     (let [user-auth (rf/subscribe [::db/user-auth])
           md #(sc/markdown (schpaa.markdown/md->html %))
           f (fn [[a b]] [sc/subtext-with-link {:style {:-margin-left "-2px"}
                                                :href  (kee-frame.core/path-for [a])} b])]
       [+page-builder r
        {:render
         (fn []
           ;[:div [sc/col-space-8 (for [e (range 100)] [:div e])]]
           [sc/col-space-8
            [sc/col-space-8

             [sc/col
              [sc/ingress-details {:open true} #_{:on-click #(schpaa.state/toggle :oversikt/nrpk)
                                                  :open     @(schpaa.state/listen :oversikt/nrpk)}

               ;todo: find a way to read the summary/details construct (without involving external state) and present [sc/icon ico/nextImage] instead
               [sc/hero-summary "NRPK"]
               [:img.w-24.h-24.float-right.m-4.-mt-12 {:src "/img/logo-n.png"}]
               [:span.clear-left "Medlemmer i Nøklevann ro- og padleklubb kan benytte klubbens materiell på Nøklevann. De som har våttkort grunnkurs hav har også tilgang til Sjøbasen som er selvbetjent og åpent året rundt.

               Nøklevann er betjent av nøkkelvakter og har derfor sesongbasert åpningstid."]]
              [sc/row-sc-g4-w
               (let [data [[3 :r.oversikt.organisasjon "Historie"]
                           [4 :r.oversikt.styret "Styret"]]]

                 (map (comp f rest) (sort-by first data)))]]

             [sc/col
              [sc/ingress-details
               [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/bli-medlem)
                                 :open     @(schpaa.state/listen :oversikt/bli-medlem)} "Bli medlem"]
               [:p "Alle som vil bli medlem i NRPK kan aller først registrere seg med ny konto her på hjemmesiden. Når ... kan du melde deg på innmeldingskurset."]]
              [sc/row-sc-g4-w
               (let [data [[1 :r.forsiden "Registrer deg her"]
                           [2 :r.forsiden "Hva årskontigenten dekker"]]]
                 (map (comp f rest) (sort-by first data)))]]

             [sc/col
              [sc/ingress-details
               [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/nøklevann)
                                 :open     @(schpaa.state/listen :oversikt/nøklevann)} "Nøklevann"]
               "Nøklevann ro- og padleklubb (NRPK) ble stiftet på Rustadsaga i februar 1988 etter et initiativ fra ledende personer innen ro- og padlemiljøet i Oslo. Initiativet ble tatt 21. desember 1987 og det er denne datoen som er blitt stående som stiftelsesdatoen. Ved inngangen på 2022 har klubben over 4200 medlemmer."]
              [sc/row-sc-g4-w
               (let [data [[:r.xxx "Hvilke båter på Nøklevann?"]
                           [:r.xxx "Utlånsaktivitet"]
                           [:r.xxx "HMS ved Nøklevann"]]]
                 (map f (sort-by second data)))]]

             [sc/col
              [sc/ingress-details
               [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/Sjøbasen)
                                 :open     @(schpaa.state/listen :oversikt/Sjøbasen)} "Sjøbasen"]
               "Sjøbasen er for medlemmer som har «Våttkort grunnkurs hav». Sjøbasen er selvbetjent, holder til på Ormsund Roklub og du må booke utstyr her."]
              [sc/row-sc-g4-w
               (let [data [[:r.booking.retningslinjer "Retningslinjer på sjøbasen"]
                           [:r.booking.faq "Ofte spurte spørsmål"]
                           [:r.booking.oversikt "Hvilke båter på Sjøbasen?"]
                           [:r.forsiden "Booking"]
                           [:r.xxx "HMS ved Sjøbasen"]]]
                 (map f (sort-by second data)))]]

             [sc/col
              [sc/ingress-details
               [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/Livredningskurs)
                                 :open     @(schpaa.state/listen :oversikt/Livredningskurs)} "Kurs"]
               "NRPK arrangerer hvert år livredningskurs med instruktør fra Norges Livredningsselskap. Kurset varer ca 2,5 timer og holdes på Holmlia bad, hvor klubben også har sine bassengtreninger med kajakk."]
              [sc/row-sc-g4-w
               (let [data [[:r.forsiden "Mer om livredningskurs"]
                           [:r.forsiden "Meld deg på innmeldingskurs"]]]
                 (map f (sort-by second data)))]]]

            [sc/col
             [sc/ingress-details
              [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/Nøkkelvakt)
                                :open     @(schpaa.state/listen :oversikt/Nøkkelvakt)} "Nøkkelvakt"]
              "Nøkkelvaktene er en gruppe frivillige medlemmer som betjener klubbens anlegg ved Nøklevann, hjelper medlemmer i åpningstiden og bidrar til sikkerheten i klubbens aktiviteter."]
             [sc/row-sc-g4-w
              (let [data [[:r.conditions "Plikter som nøkkelvakt"]
                          [:r.aktivitetsliste "Utlån på nøklevann"]
                          [:r.terms "Betingelser"]
                          [:r.kalender "Vaktkalender"]]]
                (map f (sort-by second data)))]]

            [sc/col
             [sc/ingress-details
              [sc/hero-summary {:on-click #(schpaa.state/toggle :oversikt/Årshjul)
                                :open     @(schpaa.state/listen :oversikt/Årshjul)} "Årshjulet i NRPK"]
              "Forslag til ingress?"]
             [sc/row-sc-g4-w
              (let [data [[:r.yearwheel "Oversikt 2022-23"]
                          [:r.yearwheel "Tidligere sesonger"]]]
                (map f (sort-by second data)))]]])}]))

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
        {:render user.views/my-info}]))

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
                             #_[l/ppre-x receipts']]))))}])
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
                        [hoc.toggles/switch :calendar/show-history "Vis historikk2"]
                        [hoc.toggles/switch :calendar/show-hidden "Vis skjulte2"]])

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

   :r.booking
   (fn [r]
     (let [user-auth (rf/subscribe [::db/user-auth])]
       [+page-builder r
        {:render (fn [_] [sc/col-space-2]
                   [:div
                    [sc/text0 "Sjøbasen oversikt"]
                    [sc/text1 "Sjøbasen oversikt"]
                    [l/ppre-x @(rf/subscribe [:lab/user-state])]
                    (let [admin-access? @(rf/subscribe [:lab/admin-access])]
                      (cond
                        admin-access?
                        [sc/col-space-8
                         [sc/title1 "ADMIN MODE"]
                         [sc/col (into [:div.space-y-px]
                                       (map user.database/booking-users-listitem
                                            (user.database/booking-users
                                              user.database/request-booking-xf
                                              user.database/not-yet-accepted-booking-xf)))]
                         [:hr]
                         [sc/col (into [:div.space-y-px]
                                       (map user.database/booking-users-listitem
                                            (user.database/booking-users
                                              user.database/request-booking-xf
                                              user.database/accepted-booking-xf)))]]))

                    #_(-> "./content/frontpage1.md"
                          inline
                          schpaa.markdown/md->html
                          sc/markdown)
                    [sc/row-end
                     [hoc.buttons/cta
                      {:type     "button"
                       :on-click open-dialog-signin}
                      "Begynn her"]]])}]))

   :r.yearwheel
   (fn [r]
     [+page-builder r {:panel        booking.yearwheel/panel
                       :always-panel booking.yearwheel/always-panel
                       :render       booking.yearwheel/render}])

   :r.nokkelvakt
   (fn [r]
     [+page-builder r
      {:render (fn [] [:div "ok"])}])


   :r.admin
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:div
                  (l/ppre-x (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                  [:div "admin"]])}])

   :r.signedout
   (fn [r]
     [+page-builder r
      {:render (fn []
                 [:div
                  (l/ppre-x (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
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
                              ["/img/personas/noun-persona-497844.png" :f "Line Stolpestad" "Styremedlem" ["Aktivitets\u00ADansvarlig"]]
                              ["/img/personas/noun-persona-426505.png" :m "Jan Gunnar Jakobsen" "Styremedlem" ["HMS"]]
                              ["/img/personas/noun-persona-4144954.png" :f "Ylva Eide" "Styremedlem" []]
                              ["/img/personas/noun-persona-415635.png" :f "Kjersti Selseth" "Vara" []]
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
                            :on-click #(rf/dispatch [:app/give-feedback {:navn navn :caption "Vær kort og konstruktiv. Meldingen blir lest men ikke besvart."}])}
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
        {:panel            booking.presence/panel
         :always-panel     booking.presence/always
         :render-fullwidth #(booking.presence/presence r data)}]))

   :r.users
   (fn [r]
     (r/with-let [x (r/atom {:kald-periode     false
                             :nøkkelnummer     false
                             :stjerne          false
                             :sluttet          false
                             :last-seen        true
                             :sorter-sist-sett true
                             :as-table         true
                             :timekrav         false
                             :sist-endret      true
                             :nøkkelvakter     false
                             :booking          false
                             :admin            false
                             :reverse          true})]
       (let [data (transduce (comp
                               (map val)
                               (map (fn [{:keys [timekrav] :as v}]
                                      (let [p (js/parseInt timekrav)]
                                        (assoc v :timekrav (if (js/isNaN p) 0 p)))))
                               (filter (fn [v] (and
                                                 (if @(r/cursor x [:admin])
                                                   (:admin v)
                                                   true)
                                                 (if @(r/cursor x [:booking])
                                                   (:booking-godkjent v)
                                                   true)
                                                 (if @(r/cursor x [:nøkkelvakter])
                                                   (:godkjent v)
                                                   true)
                                                 (if @(r/cursor x [:kald-periode])
                                                   (:kald-periode v)
                                                   true)
                                                 (if @(r/cursor x [:sluttet])
                                                   (:utmeldt v)
                                                   (not (:utmeldt v)))
                                                 (if @(r/cursor x [:stjerne])
                                                   (:stjerne v)
                                                   true)))))
                             conj [] @(db/on-value-reaction {:path ["users"]}))
             fields (remove nil? [(when @(r/cursor x [:sorter-sist-sett]) :timestamp-lastvisit-userpage)
                                  (when @(r/cursor x [:sist-endret]) :timestamp)
                                  (when @(r/cursor x [:timekrav]) :timekrav)])
             fields (if (empty? fields) [:navn] fields)
             data (sort-by (apply juxt fields) data)
             users (if @(r/cursor x [:reverse]) (reverse data) data)]

         (o/defstyled line :div)
         [+page-builder
          r
          (conj
            {:panel (fn []
                      [sc/col-space-8
                       [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
                        [sc/text0 "vis"]
                        [hoc.toggles/switch-local (r/cursor x [:nøkkelnummer]) "nøkkelnummer"]
                        [hoc.toggles/switch-local (r/cursor x [:last-seen]) "sist sett"]
                        [hoc.toggles/switch-local (r/cursor x [:as-table]) "som tabell"]]

                       [sc/col-space-1
                        [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
                         [sc/text0 "filter"]
                         [hoc.toggles/switch-local (r/cursor x [:sluttet]) "utmeldt"]
                         [hoc.toggles/switch-local (r/cursor x [:kald-periode]) "kald-periode"]
                         [hoc.toggles/switch-local (r/cursor x [:stjerne]) "stjerne"]
                         [hoc.toggles/switch-local (r/cursor x [:nøkkelvakter]) "nøkkelvakt"]
                         [hoc.toggles/switch-local (r/cursor x [:booking]) "booking"]
                         [hoc.toggles/switch-local (r/cursor x [:admin]) "administratorer"]]]

                       [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
                        [sc/text0 "sortering"]
                        [hoc.toggles/switch-local (r/cursor x [:sist-endret]) "sist endret"]
                        [hoc.toggles/switch-local (r/cursor x [:sorter-sist-sett]) "sist sett"]
                        [hoc.toggles/switch-local (r/cursor x [:timekrav]) "timekrav"]
                        [hoc.toggles/switch-local (r/cursor x [:reverse]) "omvendt"]]])}
            (if @(r/cursor x [:as-table])
              {:render-fullwidth
               (fn []
                 (o/defstyled table-controller :div
                   :overflow-x-auto
                   {:width "calc(100vw - 4rem)"}
                   [:at-media {:max-width "511px"}
                    {:width "calc(100vw)"}])
                 (o/defstyled table-def :table
                   :truncate
                   [:& {:width           "100%"
                        :border-collapse :collapse}
                    [:thead
                     {:height "3rem"}
                     [:tr
                      {:text-align :left
                       :color      "var(--text2)"}
                      [:th sc/small0
                       {:padding "2px"}]
                      ["th:nth-child(1)" {:min-width "2rem"}]

                      ["th:nth-child(2)" {}]]]

                    [:tbody
                     ["tr:nth-child(even)"
                      {:background "var(--floating)"}]
                     [:tr
                      {:height     "var(--size-6)"
                       :background "var(--content)"}
                      [:td sc/text2 :truncate
                       {:padding-inline "4px"}]
                      ["td:nth-child(2)"
                       {:text-align :right}]
                      ["td:nth-child(4)"
                       sc/text0
                       {:max-width "10rem"}]]]])
                 (let [f (fn [dt] [booking.flextime/flex-datetime
                                   (some-> dt t/instant t/date-time)
                                   (fn [type content]
                                     (cond
                                       (= :date type) (ta/time-format content)
                                       :else content))])]
                   [table-controller
                    [table-def
                     [:<>
                      [:thead
                       [:tr
                        [:th ""]
                        [:th "timer"]
                        [:th "nøkkelnummer"]
                        [:th "navn"]
                        [:th "sist besøkt"]
                        [:th "sist endret"]]]
                      (into [:tbody]
                            (for [{:keys [navn timekrav timestamp
                                          nøkkelnummer timestamp-lastvisit-userpage] :as v} users]
                              [:tr
                               [:td [scb/round-normal-listitem
                                     [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                                               :style    {:color "var(--text2)"}} ico/pencil]]]
                               [:td (if (pos? timekrav) (str timekrav "t") "—")]
                               [:td nøkkelnummer]
                               [:td navn]
                               [:td (f timestamp-lastvisit-userpage)]
                               [:td (f timestamp)]]))]]]))}

              {:render (fn []
                         (let [c (count users)]
                           [sc/col-space-2
                            ;[l/ppre-x fields]
                            [sc/header-title (str c " person" (if (< 1 c) "er"))]
                            [sc/col-space-2
                             (into [:ol]

                                   (for [v users]
                                     [:div.-debug2 {:style {:display               :grid

                                                            :padding-block         "var(--size-2)"
                                                            :column-gap            "4px"
                                                            :grid-template-columns "min-content 1fr"}}
                                      [scb/round-normal-listitem
                                       [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                                                 :style    {:color "var(--text2)"}} ico/pencil]]

                                      [:li {:style {:text-indent  "-2rem"
                                                    :padding-left "2rem"
                                                    :list-style   [:outside :none :disc]} #_{:display      :inline-flex
                                                                                             :list-style   [:outside :none :disc]
                                                                                             :padding-left "1rem"
                                                                                             :text-indent  "-1rem"
                                                                                             :align-items  :center
                                                                                             ;:flex-wrap    :wrap
                                                                                             :gap          "var(--size-2)"
                                                                                             :white-space  :normal}}
                                       (interpose [:span.mr-2 #_", "]
                                                  [;[:div.inline-block]


                                                   (when @(r/cursor x [:nøkkelnummer])
                                                     [sc/text-inline {:style {:white-space :nowrap
                                                                              :width       "4rem"}} (:nøkkelnummer v)])
                                                   (when @(r/cursor x [:last-seen])
                                                     [booking.flextime/flex-datetime
                                                      (some-> (:timestamp-lastvisit-userpage v) t/instant t/date-time)
                                                      (fn [type content]
                                                        [sc/text-inline {:style {:white-space :nowrap}}
                                                         (cond
                                                           (= :date type) (ta/time-format content)
                                                           :else content)])])
                                                   (when @(r/cursor x [:sist-endret])
                                                     [booking.flextime/flex-datetime
                                                      (some-> (:timestamp v) t/instant t/date-time)
                                                      (fn [type content]
                                                        [sc/text-inline {:style {:white-space :nowrap}}
                                                         (cond
                                                           (= :date type) (ta/time-format content)
                                                           :else content)])])

                                                   [sc/text-inline {:style {:xwidth "1rem"}} (or (:timekrav v) "—") (when (:timekrav v) "t")]
                                                   [sc/text-inline {:style {:white-space :normal}} "Her er en linje til"]
                                                   [sc/text-inline #_{:style {:white-space :normal}} (:navn v)]])]]))]]))}))])))

   :r.page-not-found
   error-page})


(comment
  (sort-by (juxt (comp js/parseInt :b) :a) <
           [{:a 1 :b "222"} {:a 2 :b "1"} {:b "111"}]))