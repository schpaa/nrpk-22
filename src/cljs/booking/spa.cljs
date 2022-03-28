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
            [booking.personas]))

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

(o/defstyled frontpage-image-hack-registered :img
  {:filter          "contrast(0.375) brightness(0.5)"       ; blur(2px) grayscale(50%)
   :object-fit      :cover
   :object-position "center center"
   :width           "100%"
   :min-height      "20rem"
   :height          "calc(100vh - 9rem)"}
  [:at-media {:min-width "511px"}
   {:height "calc(100vh - 4rem)"}]
  [:at-supports {:height :100dvh}
   {:height "calc(100dvh - 9rem)"}
   [:at-media {:min-width "511px"}
    {:height "calc(100dvh - 4rem)"}]])

(o/defstyled frontpage-image-hack' :img
  {:filter          "opacity(20%) contrast(0.375)"          ;"contrast(0.9375) brightness(0.95)"       ; blur(2px) grayscale(50%)
   :object-fit      :cover
   :object-position "center center"
   :width           "100%"
   :min-height      "20rem"
   :height          "calc(100vh - 0rem)"}
  [:at-media {:min-width "511px"}
   {:height "calc(100vh - 4rem)"}]
  [:at-supports {:height :100dvh}
   {:height "calc(100dvh - 8rem)"}
   [:at-media {:min-width "511px"}
    {:height "calc(100dvh - 4rem)"}]])

(defn- stats []
  (let [{:keys [online offline]} @(rf/subscribe [::db/presence-status])
        nv @(db/on-value-reaction {:path ["users"]})]
    [:div {:style {:opacity               0.66
                   :display               :grid
                   :column-gap            "4px"
                   :row-gap               "4px"
                   :grid-template-columns "4rem 1fr"}}

     [:div.justify-self-end [sc/ingress' (str 4251)]]
     [sc/ingress' "medlemmer"]
     [:div.justify-self-end [sc/text0 (count (filter (comp :godkjent val) nv))]]
     [sc/text0 "nøkkelvakter"]
     [:div.justify-self-end [sc/text0 (count (filter (fn [[k {:keys [godkjent våttkort]}]] (and godkjent (< 0 våttkort))) nv))]]
     [sc/text0 "med våttkort"]
     [:div.justify-self-end [sc/text0 (str (count (filter (comp :instruktør val) nv)))]]
     [sc/text0 "instruktører"]
     [:div.justify-self-end [sc/text0 (str (count (filter (fn [[k {:keys [request-booking]}]] (and request-booking)) nv)))]]
     [sc/text0 "havpadlere"]
     #_[sc/text1 {:style {:color "var(--yellow-2)"}} (count online) ", " (count offline)]]))

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

                       ;[:div.sticky.top-0.z-10 [booking.common-views/header-line r]]

                       ;image
                       [:div.relative.w-full
                        [(if @(rf/subscribe [:lab/at-least-registered])
                           frontpage-image-hack-registered
                           frontpage-image-hack') {:src "/img/brygge.jpeg"}]
                        #_[:div.absolute
                           {:style {:top   "21vh"
                                    :right "1rem"}}
                           [stats]]

                        [:div.absolute
                         {:style {:top   "21vh"
                                  :left  "1rem"
                                  :right "1rem"}}
                         [sc/col-space-8 {:class []}
                          [:div
                           [:div
                            [sc/ingress' {:style {:line-height      "auto"
                                                  :transform        "rotate(-3deg) translate(-8px, 2px)"
                                                  :transform-origin "top left"
                                                  :margin-bottom    "4px"
                                                  :text-transform   :uppercase
                                                  :font-size        "var(--font-size-fluid-0)"}}
                             [:span.bg-alt.text-white
                              {:style {:font-weight    "var(--font-weight-5)"
                                       :padding-inline "var(--size-2"
                                       :padding-block  "var(--size-1)"
                                       :border-radius  "var(--radius-0)"}} "Velkommen til NRPK"]]]
                           [sc/hero' {:style {:line-height     "1"
                                              ;:color           :#fffd
                                              :font-family     "Inter"
                                              :font-size       "var(--font-size-fluid-2)"
                                              :-letter-spacing "0"}}
                            [:div "Nøklevann"]
                            [:div {:style {:white-space :nowrap}} "ro- og padleklubb"]]]
                          [sc/row-ec [stats]]]]]

                       [:div.m-4
                        [sc/col-space-8
                         [sc/col-space-2

                          [sc/ingress "Etablert i 1987. Ved inngangen på 2022 har klubben over 4200 medlemmer."]
                          (let [data [[1 :r.forsiden "Bli medlem"]
                                      [3 :r.forsidens "Organisasjon"]
                                      [2 :r.forsiden "Registrer deg og logg inn"]
                                      [4 :r.oversikt "Oversikt"]]]
                            [sc/row-sc-g2-w (map (comp f rest) (sort-by first data))])]]]
                       [:div
                        (sc/markdown'
                          [sc/col-space-2
                           (schpaa.markdown/md->html "## Postadresse\nNøklevann ro- og padleklubb<br/>Postboks 37, 0621 Bogerud<br/>[styret@nrpk.no](mailto:styret@nrpk.no)<br/>[medlem@nrpk.no](mailto:medlem@nrpk.no)")
                           [:div.pb-16 [hoc.buttons/reg-pill-icon
                                        {:on-click #(rf/dispatch [:app/give-feedback {:source "forside"}])}
                                        ico/tilbakemelding "Har du en tilbakemelding?"]]])]])}]))

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
                              [3 :r.oversikt.organisasjon "Organisasjon"]
                              [4 :r.oversikt.styret "Styret"]]]
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
     [+page-builder r {:panel        booking.yearwheel/header
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
                  (let [data [["/img/personas/noun-persona-436706.png" :m "Ulf Pedersen" "Styreleder" ["HMS" "Nøkkelvakt\u00adansvarlig"]]
                              ["/img/personas/noun-persona-622291.png" :m "Tormod Tørstad" "Nestleder" ["Anleggs\u00ADansvarlig for Sjøbasen" "Utstyrs\u00ADansvarlig"]]
                              ["/img/personas/noun-persona-633483.png" :m "Stein-Owe Hansen" "Styremedlem" ["Sekretær"]]
                              ["/img/personas/noun-persona-410775.png" :m "Adrian Mitchell" "Styremedlem" ["Kasserer"]]
                              ["/img/personas/noun-persona-622293.png" :m "Chris Schreiner" "Styremedlem" ["Hjemmesiden" "Booking" "Båtlogg" "Eykt"]]
                              ["/img/personas/noun-persona-497844.png" :f "Line Stolpestad" "Styremedlem" ["Aktivitets\u00ADansvarlig" "Midlertidig anleggs\u00ADansvarlig Nøklevann"]]
                              ["/img/personas/noun-persona-426505.png" :m "Jan Gunnar Jakobsen" "Styremedlem" []]
                              ["/img/personas/noun-persona-4144954.png" :f "Ylva Eide" "Styremedlem" []]
                              ["/img/personas/noun-persona-415635.png" :f "Kjersti Selseth" "Vara" []]
                              ["/img/personas/noun-persona-488544.png" :m "Ole Håbjørn Larsen" "Vara" []]]]
                    (o/defstyled card :div.relative
                      :p-4
                      {:min-height       "13ch"
                       :background-color "var(--floating)"
                       :border-radius    "var(--radius-1)"
                       :box-shadow       "var(--shadow-1)"})

                    (o/defstyled image :img
                      :p-2
                      {:background    "var(--textmarker-background)"
                       ;:border "none"
                       :color         "var(--text3)"
                       :aspect-ratio  "1/1"
                       :height        "var(--size-10)"
                       :box-shadow    "var(--inner-shadow-1)"
                       :border-radius "var(--radius-round)"})
                    [:div {:style {:display               :grid
                                   :gap                   "8px 8px"
                                   :grid-template-columns "repeat(auto-fit,minmax(18ch,1fr))"}}
                     (for [[idx [url gender navn role ansvar]] (map-indexed vector data)]
                       [card
                        [:div                               ;.flex.items-start.justify-between.gap-4
                         [:div.float-right.pl-2.-mr-2.-mt-2
                          [image {:src (if url
                                         url
                                         (first (nth (filter (comp #(= gender %) second) booking.personas/faces) idx)))}]]
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
                           {:style {:color "var(--text3)"}}
                           ico/user)]])])])}])
   :r.oversikt.organisasjon
   (fn [r]
     (+page-builder
       r
       {:render (fn []
                  (do
                    [:<>
                     (-> (inline "./oversikt/organisasjon.md") schpaa.markdown/md->html sc/markdown)]))}))

   :r.page-not-found
   error-page})

