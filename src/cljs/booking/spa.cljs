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
            [user.database]))

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
   (fn [r]
     (let [f (fn [[a b]] [sc/subtext-with-link
                          {:style {:margin-left "-2px"}
                           :href  (kee-frame.core/path-for [a])}
                          b])
           md #(sc/markdown (schpaa.markdown/md->html %))
           user-state (rf/subscribe [:lab/user-state])
           some-state (some #{(:role @user-state)} [:member :waitinglist :registered])]
       [+page-builder r
        {:frontpage true
         :panel     (fn [] [:div "panel"])
         :render    (fn []
                      [:div.overflow-y-auto.h-full.relative
                       {:style {:background "var(--content)"}}

                       [:div.sticky.top-0.z-10 [booking.common-views/header-line r]]

                       [:div.relative.w-full.z-0

                        [:img {:style {:filter          "contrast(0.275)"
                                       :object-fit      :cover
                                       :object-position "center center"
                                       :width           "100%"
                                       :height          (str "calc(100vh - " (if some-state "4rem" "4rem") ")")
                                       :min-height      "20rem"}

                               :src   "/img/brygge.jpeg"}]
                        ;logo
                        [:div
                         {:style {:position      :absolute

                                  :display       :grid
                                  :place-content :center
                                  :top           "0"
                                  :right         "0"
                                  :bottom        "0"
                                  :left          "0"}}

                         [:img {:style {:width     "25vw"
                                        :max-width "10rem"}
                                :src   "/img/logo-n.png"}]]

                        [:div.absolute.bottom-4.left-4
                         [sc/hero {:style {:color          "white"
                                           :font-size      "var(--font-size-fluid-2)"
                                           :letter-spacing "0"}} "Velkommen til NRPK"]]

                        [:div.absolute.left-4.top-4.w-auto
                         {:style {:background     "var(--surface00)"
                                  :border-radius  "var(--radius-1)"
                                  :padding-inline "var(--size-3)"
                                  :padding-block  "var(--size-3)"}}
                         [opening-hours]]]

                       [:div.m-4
                        [sc/col-space-8 {:class [:xpy-4]}
                         [sc/col-space-2

                          [sc/ingress "Etablert i 1987. Ved inngangen på 2022 har klubben over 4200 medlemmer."]
                          (let [data [[1 :r.forsiden "Bli medlem"]
                                      [3 :r.forsidens "Organisasjon"]
                                      [2 :r.forsiden "Registrer deg og logg inn"]
                                      [4 :r.oversikt "Oversikt"]]]
                            [sc/row-sc-g2-w (map (comp f rest) (sort-by first data))])]]]
                       [:div
                        (sc/markdown' (schpaa.markdown/md->html "## Postadresse\nNøklevann ro- og padleklubb<br/>Postboks 37, 0621 Bogerud<br/>[styret@nrpk.no](mailto:styret@nrpk.no)<br/>[medlem@nrpk.no](mailto:medlem@nrpk.no)"))]])}]))

   :r.oversikt
   (fn forsiden [r]
     (let [user-auth (rf/subscribe [::db/user-auth])
           md #(sc/markdown (schpaa.markdown/md->html %))
           f (fn [[a b]] [sc/subtext-with-link {:style {:margin-left "-2px"}
                                                :href  (kee-frame.core/path-for [a])} b])]
       [+page-builder r
        {:render
         (fn []
           [:<>

            [:div
             [:div

              [sc/col-space-8 {:class [:xpy-8]}
               [sc/col-space-8
                [sc/col-space-1
                 (md "# NRPK\n## Nøklevann ro- og padleklubb tilbyr medlemmene å benytte klubbens materiell på Nøklevann i klubbens åpningstider. I tillegg har vi et tilbud til de som har våttkort grunnkurs hav å padle på Oslofjorden.")
                 [sc/row-sc-g2-w
                  (let [data [[1 :r.forsiden "Bli medlem"]

                              [3 :r.oversikt.organisasjon "Organisasjon"]]]
                    (map (comp f rest) (sort-by first data)))]]

                [sc/col-space-1
                 (md "# Nøklevann\n## Nøklevann ro- og padleklubb (NRPK) ble stiftet på Rustadsaga i februar 1988 etter et initiativ fra ledende personer innen ro- og padlemiljøet i Oslo. Initiativet ble tatt 21. desember 1987 og det er denne datoen som er blitt stående som stiftelsesdatoen. Ved inngangen på 2022 har klubben over 4200 medlemmer.")
                 [sc/row-sc-g2-w
                  (let [data [[:r.xxx "Båtlisten på Nøklevann"]
                              [:r.forsiden "Utlånsaktivitet"]]]
                    (map f (sort-by second data)))]]]
               [sc/col-space-1
                [md "# Bli medlem\n ## Alle som vil bli medlem i NRPK kan aller først registrere seg med ny konto her på hjemmesiden. Når ... kan du melde deg på innmeldingskurset."]

                [sc/row-sc-g2-w
                 (let [data [[:r.forsiden "Registrer deg her"]]]
                   (map f (sort-by second data)))]]

               [sc/col-space-1
                [md "# Livredningskurs\n## NRPK arrangerer hvert år livredningskurs med instruktør fra Norges Livredningsselskap. Kurset varer ca 2,5 timer og holdes på Holmlia bad, hvor klubben også har sine bassengtreninger med kajakk."]
                [sc/row-sc-g2-w
                 (let [data [[:r.forsiden "Meld deg på innmeldingskurs"]
                             [:r.forsiden "Mer om livredningskurs"]]]



                   (map f (sort-by second data)))]]
               [sc/col-space-1
                (md "# Sjøbasen\n## NRPK’s sjøbase er for medlemmer som har «Våttkort grunnkurs hav». Sjøbasen er selvbetjent, holder til på Ormsund Roklub og du må booke utstyr her.")
                [sc/row-sc-g2-w
                 (let [data [[:r.forsiden "Retningslinjer på sjøbasen"]
                             [:r.forsiden "Ofte spurte spørsmål"]
                             [:r.forsiden "Båtlisten på Sjøbasen"]
                             [:r.forsiden "Oversikt"]
                             [:r.forsiden "Booking"]]]
                   (map f (sort-by second data)))]]
               [sc/col-space-1
                [md "# Årshjul\n## Ingress"]
                [sc/row-sc-g2-w
                 (let [data [[:r.forsiden "Oversikt 2022-23"]
                             [:r.forsiden "Legg til nytt i årshjulet"]]]
                   (map f (sort-by second data)))]]
               [sc/col-space-1
                [md "# Nøkkelvakt\n## Nøkkelvaktene er en gruppe frivillige medlemmer som betjener klubbens anlegg ved Nøklevann, hjelper medlemmer i åpningstiden og bidrar til sikkerheten i klubbens aktiviteter."]
                [sc/row-sc-g2-w
                 (let [data [[:r.forsiden "Plikter som nøkkelvakt"]
                             [:r.forsiden "Mine nøkkelvakt-opplysninger"]
                             [:r.forsiden "Utlån på nøkkelvann"]
                             [:r.forsiden "Betingelser"]
                             [:r.forsiden "Vaktkalender"]]]
                   (map f (sort-by second data)))]]]]]])}]))

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
       [page-boundary r

        ;[l/ppre-x r id @data]
        [:div.max-w-xl.mx-auto.container
         [sc/markdown [schpaa.markdown/md->html (:content @data)]]]]))

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
     (+page-builder r {:panel        booking.yearwheel/header
                       :always-panel booking.yearwheel/always-panel
                       :render       booking.yearwheel/render}))

   :r.nokkelvakt
   (fn [r]
     (+page-builder r
                    {:render (fn [] [:div "ok"])}))


   :r.admin
   (fn [r]
     (+page-builder r
                    {:render (fn []
                               [:div
                                (l/ppre-x (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                                [:div "admin"]])}))

   :r.signedout
   (fn [r]
     (+page-builder r
                    {:render (fn []
                               [:div
                                (l/ppre-x (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens])))
                                [:div "Du har altså logget ut"]])}))

   :r.oversikt.organisasjon
   (fn [r]
     (+page-builder
       r
       {:render (fn []
                  (do
                    [:<>
                     (-> (inline "./oversikt/organisasjon.md") schpaa.markdown/md->html sc/markdown)
                     (let [data [[nil "Ulf Pedersen" ["Styreleder" "HMS" "Nøkkelvakt\u00adansvarlig"]]
                                 [nil "Tormod Tørsdad" ["Nestleder" "Anleggs\u00ADansvarlig for Sjøbasen" "Utstyrs\u00ADansvarlig"]]
                                 [nil "Stein-Owe Hansen" ["Sekretær"]]
                                 [nil "Line Stolpestad" ["Aktivitets\u00ADansvarlig" "Midlertidig anleggs\u00ADansvarlig Nøklevann"]]
                                 [nil "Adrian Mitch" ["Kasserer"]]
                                 [nil "Chris Schreiner" ["Hjemmesiden" "Booking" "Båtlogg" "Eykt"]]
                                 [nil "Kjersti Selseth" []]
                                 [nil "Ylva Morken Eide" []]
                                 [nil "Jan Gunnar Jacobsen" []]
                                 [nil "Ole H. Larsen" []]]]
                       (o/defstyled card :div
                         :p-4
                         {:min-height       "12ch"
                          :background-color :#fffe
                          :border-radius    "var(--radius-1)"
                          :box-shadow       "var(--inner-shadow-1),var(--shadow-2)"})

                       (o/defstyled image :img
                         {:background    :#0002
                          ;:border "none"
                          :aspect-ratio  "1/1"
                          :height        "var(--size-9)"
                          :box-shadow    "var(--inner-shadow-2)"
                          :border-radius "var(--radius-round)"})
                       [:div {:style {:display               :grid
                                      :gap                   "8px 8px"
                                      :grid-template-columns "repeat(auto-fit,minmax(21ch,1fr))"}}
                        (for [[url navn ansvar] data]
                          [card
                           [:div.flex.items-start.gap-4
                            [image
                             {:src url}]
                            [sc/col-space-1
                             [sc/text0 navn]
                             (map (fn [e] [sc/small1 {:style {:font-family "Merriweather"}} e]) ansvar)]]])])]))}))

   :r.page-not-found
   error-page})

