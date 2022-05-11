(ns booking.frontpage
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.styles :as bs]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [db.core :as db]
            [booking.common-views]
            [arco.react]
            [times.api :as ta]
            [tick.core :as t]
            [booking.carousell]
            [booking.flextime :refer [flex-datetime]]
            [booking.yearwheel]
            [booking.openhours]
            [booking.account]
            [booking.common-widgets :as widgets]))

;region

(o/defstyled antialiased :div
  [:* {:x-webkit-font-smoothing  :auto
       :x-moz-osx-font-smoothing :auto
       :-webkit-font-smoothing   :antialiased
       :-moz-osx-font-smoothing  :grayscale}])

;warning: Not a page-template
(o/defstyled front-page :div
  [:&
   {:position            :relative
    :width               "100vw"
    :height              "100vh"
    :background-position "center center"
    :background-repeat   "no-repeat"
    :background-size     "cover"}
   [:&.light {:background-image ["var(--background-image-light)"]}]
   [:&.dark {:background-image ["var(--background-image-dark)"]}]
   [:at-media {:max-width "511px"}
    [:&.bottom-toolbar
     {:height "calc(100vh - 8rem)"}]]
   [:at-media {:min-width "768px"}
    {:xwidth "768px"}]]
  #_[:at-supports {:height :100dvh}
     {:xheight "calc(100dvh)"}])

(o/defstyled fp-right-aligned-link sc/ingress'
  :text-right :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(o/defstyled fp-left-aligned-link sc/ingress'
  :text-left :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(o/defstyled frontpage-image-style :div.relative
  [:&
   {:z-index 0}
   [:img :bg-white {:object-fit      :cover
                    :object-position "center"
                    :width           "100%"
                    :min-height      "20rem"}]
   [:.light
    {:filter "contrast(0.3) brightness(1) grayscale(0.5)"}]
   [:.dark
    {:filter "opacity(0.1) brightness(.95) grayscale(.6)"}]
   [:.anonymous {:height "calc(100vh)"}]
   [:.registered {:height "calc(100vh)"}]
   [:at-supports {:height :100dvh}
    {:height "calc(100dvh - 15rem)"}]
   #_[:at-media {:max-width "511px"}
      [:.registered {:height "calc(100vh - 5rem)"}]
      [:.anonymous {:height "calc(100vh - 5rem)"}]]
   #_[:at-supports {:height "100dvh"}
      [:.anonymous {:height "calc(100dvh)"}]
      [:.registered {:height "calc(100dvh - 5rem)"}]]]
  ([{:keys [src class style]} & ch]
   [:<>
    [:img {:class   class
           :style   style
           :loading :lazy
           :src     src}]
    ch]))

(o/defstyled top-right-windowclosebutton :div
  {:position :absolute
   :top      "var(--size-2)"
   :right    "var(--size-2)"})

(o/defstyled image-caro-style :div
  [:&
   {:width "calc(100vw)"}
   [:&.toolbar
    {:width "calc(100vw - 4rem)"}]
   [:at-media {:max-width "512px"}
    [:&.toolbar
     {:width "calc(100vw)"}]]])

;endregion

(defn table-text [a b]
  [:<>
   [:div.justify-self-end [sc/text0 [antialiased {:style {:font-weight "var(--font-weight-7)"
                                                          :font-size   "var(--font-size-fluid-0)"}} a]]]
   [sc/text0 [antialiased {:style {:text-transform :uppercase
                                   :letter-spacing "0.025rem"
                                   :font-size      "var(--font-size-fluid-0)"}} b]]])

(defn- stats []
  (let [{:keys [online offline]} @(rf/subscribe [::db/presence-status])
        nv @(db/on-value-reaction {:path ["users"]})]
    [:div {:style {;:opacity               1
                   :display               :grid
                   :gap                   "var(--size-2)"
                   :grid-template-columns "4rem 1fr"}}

     [:div.justify-self-end [sc/ingress' {:style {:font-size "var(--font-size-fluid-1)"}} (str 4251)]]
     [sc/ingress' {:style {:font-size "var(--font-size-fluid-1)"}} "medlemmer"]
     [table-text (count (filter (comp :godkjent val) nv)) "nøkkelvakter"]
     [table-text (str (count (filter (comp :instruktør val) nv))) "instruktører"]]))

(defn- boats []
  (let [{:keys [online offline]} @(rf/subscribe [::db/presence-status])
        boats @(db/on-value-reaction {:path ["boad-item"]})]
    [:div {:style {;:opacity               1
                   :display               :grid
                   :gap                   "var(--size-2)"
                   :grid-template-columns "4rem 1fr"}}

     [:div.justify-self-end [sc/ingress' {:style {:font-size "var(--font-size-fluid-1)"}} (count boats)]]
     [sc/ingress' {:style {:font-size "var(--font-size-fluid-1)"}} "båter"]

     [table-text 42 "flattvannskajakker"]
     [table-text 54 "havkajakker"]
     [table-text 22 "standup padlebrett"]
     [table-text 11 "grønnlandskajakker"]
     [table-text 12 "kanoer"]
     [table-text 4 "skullere"]
     [table-text 2 "robåter"]]))

(def frontpage-images
  (map #(str "/img/caro/" %)
       ["img-0.jpg"
        "img-1.jpg"
        "img-13.jpg"
        "img-16.jpg"
        "img-12.jpg"
        "img-4.jpg"]))

(defn circular-logo-thing [dark-mode?]
  [:div {:style {:width        "6em"
                 :height       "6em"
                 :transform    "rotate(6deg)"
                 :aspect-ratio "1/1"}}
   [booking.styles/logothing {:class [(if dark-mode? :dark :light)]}
    [:img {:src   "/img/logo-n.png"
           :style {:position                  :absolute
                   :inset                     0
                   :object-fit                :contain
                   :border-radius             "var(--radius-round)"
                   :animation-name            "spin3"
                   :animation-timing-function "var(--ease-4)"
                   :animation-delay           "0s"
                   :animation-duration        "1.3s"
                   :animation-iteration-count 1
                   :animation-direction       :forward
                   :transform-origin          "center"}}]
    [:div
     {:style {:position  :absolute
              :inset     0
              :clip-path "circle(35% at 50% 50%)"}}
     (r/with-let [c (r/atom 0)
                  f (fn [] (swap! c inc))
                  tm (js/setInterval f 3000)]
       [:div {:style {:transition "opacity 1s"}}
        [:img {:style {:position      "absolute"
                       :width         "100%"
                       :height        "100%"
                       :inset         0
                       :border-radius "var(--radius-round)"
                       :opacity       1}
               :src   "/img/logo-n.png"
               #_(first (drop @c (cycle frontpage-images)))}]]

       (finally (js/clearInterval tm)))]]])

(defn scroll-up-animation []
  (let [know-how-to-scroll? (rf/subscribe [:lab/we-know-how-to-scroll?])]
    [bs/scroll-up-animation
     {:class [(if @know-how-to-scroll? :fadeaway :keepgoing)]}
     [:div.p-1
      {:style {:border-radius "var(--radius-round)"
               :box-shadow    "var(--shadow-6)"
               :background    "var(--toolbar)"
               :color         "var(--text1)"}}
      [sc/icon ico/more-if-you-scroll-down]]]))

(defn listitem' [date text]
  [:div.col-span-2.space-y-0
   (if date
     [booking.flextime/flex-datetime
      date
      (fn [type d]
        ;[l/ppre-x type d]
        (if (= :date type)
          [sc/datetimelink (ta/date-format-sans-year d)]
          [sc/datetimelink d]))])
   [:div.flex.items-baseline.gap-2
    [sc/fp-text text]]])

(defn config []
  {:dots             true
   :infinite         true
   :arrows           true
   :slidesToShow     1
   :pauseOnHover     true
   :inside?          true
   ;:autoplay         false
   ;:autoplaySpeed    1000  
   :afterChange      (fn [e] #_(rf/dispatch [::set-active-menu-page e]))
   ;:beforeChange     (fn [_ e] (rf/dispatch [::set-active-menu-page]))
   :touchMove        true
   :speed            500
   :initialSlide     1
   :fade             false
   :pauseOnDotsHover true})

(defn image-carousell []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        ;auto-scroll? (schpaa.state/listen :lab/image-carousell-autoscroll)
        left-side? (schpaa.state/listen :lab/menu-position-right)
        at-least-registered? (rf/subscribe [:lab/at-least-registered])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    [:div
     {:style (conj {:padding-block "var(--size-4)"
                    :filter        (if dark-mode? "brightness(0.75)" "")})}
     [image-caro-style {:class [(if (and @has-chrome? @at-least-registered?) :toolbar)]}
      [booking.carousell/render-carousell
       {:carousell-config {:initialSlide  0
                           :slidesToShow  3
                           :autoplay      true              ; @auto-scroll?
                           :autoplaySpeed 5500}
        :class            [:bg-alt :h-full :m-4]
        :view-config      (config)
        :contents
        (into [] (map (fn [e] [:div.outline-none
                               {:style {:overflow-y :hidden}}
                               [:img {:style  {:object-fit   :cover
                                               :height       "100%"
                                               :width        "100%"
                                               :aspect-ratio "3/2"
                                               :overflow-y   :hidden}
                                      :width  "100%"
                                      :height "100%"
                                      :src    e}]])) frontpage-images)}]]]))

(defn todays-numbers [c1 c2 a b]
  [sc/row-sc-g2 {:style {:-transform       "translateY(1rem) rotate(6deg)"
                         :opacity          0.33
                         :transform-origin "end bottom "}}
   [sc/col {:class [:gap-2]}
    [hoc.buttons/pill {:on-click #(rf/dispatch [:app/navigate-to [:r.båtliste.nøklevann]])
                       :class    [:narrow :shadow :center]
                       :style    {:border-radius    "var(--radius-1)"
                                  :background-color "var(--gray-9)"
                                  :color            "var(--gray-1)"}} a]
    [hoc.buttons/pill {:on-click #(rf/dispatch [:app/navigate-to [:r.utlan]])
                       :class    [:center :narrow :shadow]
                       :style    {:border-radius    "var(--radius-1)"
                                  :color            "var(--blue-1)"
                                  :background-color "var(--blue-9)"}} b]]
   [sc/surface-a
    {:style {:padding-inline "var(--size-3)"
             :padding-block  "var(--size-2)"
             :box-shadow     "var(--shadow-0),var(--shadow-2)"
             :background     "rgba(0,0,0,0.05)"}}
    [sc/col-space-2
     {:style {:align-items :end
              :cursor      :pointer}}
     [sc/title2 {:style {:color "var(--gray-9)"}} c1 "ºC"]
     [sc/title2 {:style {:color "var(--blue-9)"}} c2 "°C"]]]])

(o/defstyled small-grid :div
  {:display               :grid
   :font-size             "90%"
   :grid-column-gap       "1rem"
   :grid-row-gap          "1rem"
   :grid-template-columns "1fr min-content"
   :grid-template-areas   [["type" "graph"]
                           ["." "status"]]})

(o/defstyled large-grid :div
  {:display               :grid
   :padding               "4px"
   :font-size             "90%"
   :grid-column-gap       "1rem"
   :grid-template-columns "1fr min-content 1fr"
   :grid-template-areas   [["type" "graph" "status"]]})

(o/defstyled fancy-front :div
  [:&
   small-grid
   {:max-width         "calc(100% - 1rem)"
    :height            "auto"
    :padding           "4px"
    :-background-color "hsla(0,50%,50%,20%)"}

   [:at-media {:min-width "512px"}
    large-grid
    {:-background-color "blue"}]])

(defn logo-graph []
  [:div {:style {:align-self   :center
                 :justify-self :start}}
   (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
     [circular-logo-thing dark-mode?])])

(def logo-type
  [:div {:class [:flex :items-center :justify-end]
         :style {:transform        "rotate(-6deg)"
                 :position         :relative
                 :transform-origin "center right"}}
   [bs/logo-text {:style {:width        "auto"
                          :justify-self :end}}
    [sc/hero' {:style {:text-align  :right
                       :color       "var(--text0)"
                       :font-size   "1.92em"
                       :font-weight "var(--font-weight-5)"}} "Nøklevann"]
    [sc/ingress-cl {:style {:white-space :nowrap
                            :font-size   "1.2em"
                            :color       "var(--text1)"
                            :font-weight "var(--font-weight-4)"}} "ro– og padleklubb"]]])

(defn header-with-logo []
  (let [c (count (logg.database/boat-db))]
    (fancy-front
      [:div {:style {:align-self   :center
                     :justify-self :center
                     :grid-area    "graph"}} (logo-graph)]
      [:div {:style {
                     :align-self :center
                     :grid-area  "type"}} logo-type]
      [:div {:style {:align-self :center
                     :grid-area  "status"}} (todays-numbers "— " "— " c '—)])

    #_[:div.grid.gap-x-4.mx-2.-debug
       {:style {:grid-template-columns "1fr min-content"
                :grid-template-rows    "1fr 1fr"}}
       [:div {:class [:flex :items-center :justify-end]
              :style {:transform        "rotate(-6deg)"
                      :position         :relative
                      :transform-origin "center right"}}
        [bs/logo-text {:style {:width        "auto"
                               :justify-self :end}}
         [sc/hero' {:style {:text-align  :right
                            :color       "var(--text0)"
                            :font-size   "1.92em"
                            :font-weight "var(--font-weight-5)"}} "Nøklevann"]
         [sc/ingress-cl {:style {:white-space :nowrap
                                 :font-size   "1.2em"
                                 :color       "var(--text1)"
                                 :font-weight "var(--font-weight-4)"}} "ro– og padleklubb"]]]
       [:div {:style {:align-self   :center
                      :justify-self :start}}
        (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
          [circular-logo-thing dark-mode?])]
       (todays-numbers "— " "— " c '—)]))

(defn helpful-to-earlier-users []
  [sc/surface-c {:class [:-mx-4x]
                 :style {:position   :relative
                         :background "var(--textmarker-background)"
                         :box-shadow "var(--shadow-2)"
                         :padding    "var(--size-4)"}}
   [top-right-windowclosebutton
    [sc/icon {:style    {:color "var(--textmarker)"}
              :on-click #(schpaa.state/toggle :lab/skip-easy-login)} ico/closewindow]]

   [:div
    {:style {:position  :absolute
             :bottom    "-2rem"
             :right     "30%"
             :width     "2rem"
             :transform "scale(-1,-1)"}}

    [:svg {:height              "2rem"
           :width               "2rem"
           :preserveAspectRatio "none"
           :viewBox             "-1 -1 32 24"}

     [:path {:fill           "var(--textmarker-background)"
             :stroke         "none"
             :stroke-linecap "round" :stroke-linejoin "round" :d "M3 24 L0 0 L32 24 L3 24 "}]]]

   [sc/col-space-4
    [sc/subtext {:class [:pr-4]
                 :style {:font-weight "var(--font-weight-4)"
                         :color       "var(--textmarker)"
                         :line-height "var(--font-lineheight-4)"}}
     [:div.inline-block
      "Er du nøkkelvakt kan du logge inn med samme bruker som du brukte i Eykt. Når du logger inn kan du registrere båtutlån og booking (hvis du har våttkort grunnkurs hav) fra disse nettsidene."
      [:div.inline-block.px-2 [sc/icon-small {:style    {:color "var(--textmarker)"}
                                              :on-click #(rf/dispatch [:app/give-feedback {:comment-length 600
                                                                                           :caption        "Spørsmål eller tilbakemelding som gjelder innlogging (ta med e-post hvis du vil ha svar og du ikke er pålogget):"
                                                                                           :source         "frontpage/yellow-welcome-sticker"}])} ico/tilbakemelding]]]]]])

(defn please-login-and-register []
  [sc/row-ec
   [:div.grow]
   [hoc.buttons/attn
    {:on-click #(rf/dispatch [:app/login])}
    [sc/col {:style {:text-align :left}}
     [sc/ingress {:style {:line-height "1.2"
                          :font-weight "var(--font-weight-2)"
                          :xcolor      "var(--brand1)"}} "Logg inn"]
     [:div {:style {:color "var(--gray-3)"}} "& registrer deg"]]]])

(defn current-status []
  [:div.space-y-4
   ;[sc/row-bl [sc/fp-header "Status"] (sc/link {} "(se flere)")]
   [:div {:style {:display               "grid"
                  :gap                   "var(--size-4) var(--size-2)"
                  :grid-template-columns "1fr"}}
    [sc/fp-text "Nøklevann"]
    [sc/row-sc-g2-w {:class [:ml-4]}
     ;[sc/text1 "16° i vannet"]
     [sc/text1 "is på vannet,"]
     [sc/text1 "9° i luften"]
     [sc/text1 [flex-datetime (t/date) (fn [type d]
                                         (if (= :date type)
                                           [sc/subtext {:style {:text-decoration :none}} "Registrert " (ta/date-format-sans-year d)]
                                           [sc/subtext "Registrert for " d]))]]]
    #_[sc/row-sc-g2-w {:class [:ml-4]}
       [sc/text2 "ingen utlån i dag"]
       [sc/text1 "ingen utlån nå"]
       #_[sc/text2 "4 på overnatting"]]

    [sc/fp-text "Sjøbasen"]
    [sc/row-sc-g2-w {:class [:ml-4]}
     [sc/text1 "5° i vannet,"]
     [sc/text1 "8° i luften"]
     [sc/text1 [flex-datetime (t/<< (t/date) (t/new-period 5 :days))
                (fn [type d]
                  (if (= :date type)
                    [sc/subtext {:style {:text-decoration :none}} "Registrert " (ta/date-format-sans-year d)]
                    [sc/subtext "Registrert for " d]))]]]
    [sc/row-sc-g2-w {:class [:ml-4]}
     [sc/text2 "2 utlån i dag"]
     [sc/text1 "1 utlån nå"]]]])

(defn news-feed []
  (let [er-nokkelvakt? (rf/subscribe [:lab/nokkelvakt])]
    [:div.space-y-4

     [:div {:style {:display               "grid"
                    :gap                   "var(--size-4) var(--size-2)"
                    :grid-template-columns "1fr"}}

      [listitem' (t/at (t/date "2022-04-24") (t/time "18:00"))
       [sc/col-space-2
        [:div "Hele vaktlisten er nå tilgjengelig. Saldo fra fjoråret registreres fortløpende i dagene fram til nøkkelvaktmøtet."]

        [:div "Er det noe som ikke stemmer, send tilbakemelding fra nettsiden det gjelder, se "
         [widgets/auto-link :r.min-status]
         #_[sc/link {:style {:display :inline-block}
                     :href  (kee-frame.core/path-for [:r.min-status])} "her!"]]]]
      [listitem' (t/at (t/date "2022-04-20") (t/time "10:00"))
       [sc/col-space-2
        [:div "Nå kan du velge vakter som går fram til og med 3. juli. Etter 29.
        april fyller vi på med rest-vakter for de med utestående saldo.
        Resten av vaktlisten åpner når vi ser at alt virker som det skal (2-3 dager)."]
        [:div "Vaktlisten finner du "
         [sc/link {:style {:display :inline-block}
                   :href  (kee-frame.core/path-for [:r.nokkelvakt])} "her!"]]]]

      (when-not @er-nokkelvakt?
        [listitem' (t/at (t/date "2022-04-19") (t/time "19:00"))
         [sc/col-space-2
          [:div "Er du nøkkelvakt med nylaget konto (fordi Facebook ikke vil) og ser at du ikke er godkjent som nøkkelvakt?"]
          [:span "Dette må du "
           [sc/link {:style  {:display :inline-block}
                     :target "_blank"
                     :href   "https://nrpk.no"} "gjøre!"]]]])

      [listitem' (t/at (t/date "2022-04-18") (t/time "15:00"))
       [sc/col-space-2
        [:div "Hva skjer til hvilken tid? Oversikten finner du under Planlagt!"]]]

      (when @er-nokkelvakt?
        [listitem' (t/at (t/date "2022-04-14") (t/time "18:00"))
         [sc/col-space-2
          [:div "Fordi du er nøkkelvakt må du foreta den årlige sjekken om at informasjonen vi har om deg er oppdatert."]
          [:span "Dine opplysninger finner du "
           [sc/link {:style {:display :inline-block}
                     :href  (kee-frame.core/path-for [:r.user])} "her."]]]])
      (when @er-nokkelvakt?
        [listitem' (t/date "2022-04-13")
         [:span "Bruk båtloggen for å registrere "
          [sc/link {:style {:display :inline-block}
                    :href  (kee-frame.core/path-for [:r.utlan])} "utlån av båt."]]])]]))

;todo blog-system
;[listitem' (t/date "2022-04-11") "Båtarkivet er i bruk"]
;[listitem' (t/date "2022-03-04") "Ny layout og organisering av hjemmesiden."]
;[listitem' (t/date "2022-02-15") "Nøkkelvakter kan nå rapportere hms-hendelser og dokumentere materielle mangler og skader her."]
;[listitem' (t/date "2021-11-01") "Nye båter er kjøpt inn."]]]))

(defn debug-panel []
  [sc/row-sc-g2-w

   (when goog.DEBUG
     [hoc.buttons/regular {:on-click #(rf/dispatch [:app/sign-out])} "Sign out"])

   (when goog.DEBUG
     [hoc.buttons/regular {:on-click #(rf/dispatch [:app/successful-login])} "Sign in"])

   (when goog.DEBUG
     [hoc.buttons/regular {:on-click #(booking.account/open-dialog-confirmaccountdeletion)} "go"])])

(defn frontpage []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        show-image-carousell? (schpaa.state/listen :lab/show-image-carousell)
        reg? (rf/subscribe [:lab/at-least-registered])
        master-emulation (rf/subscribe [:lab/master-state-emulation])]
    [front-page {:class [(if dark-mode? :dark :light)
                         (if @reg? :bottom-toolbar)]}

     [:div.xmin-h-full.z-0.relative.mx-auto.pt-16           ;<--------- pt-16 !!!!!!!

      (when (and goog.DEBUG @master-emulation)
        [:div.max-w-lgx.mx-auto
         [:div.mx-4.py-4.pt-24
          [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
           booking.common-views/master-control-box]]])

      [:div {:style {:min-font-size "80%"
                     :font-size     "100%"
                     ;:margin-top    "1.4rem"
                     :padding-block "var(--size-10)"}}
       [header-with-logo]]

      (when @show-image-carousell?
        [image-carousell])

      (when (and (not @(schpaa.state/listen :lab/skip-easy-login))
                 (not @reg?))
        [:div.mx-4.pb-12
         [:div.max-w-lg.mx-auto.px-4 [helpful-to-earlier-users]]])

      [:div.mx-4.pb-8
       [sc/col-space-8 {:class [:mx-auto]
                        :style {:max-width booking.common-views/max-width}}
        [sc/col-space-8
         (when-not @reg?
           [please-login-and-register])
         #_(when goog.DEBUG (debug-panel))
         (widgets/disclosure {:large 1
                              :style {:padding-block "var(--size-2)"
                                      :margin-left   "var(--size-7)"}} :frontpage/news "Hva skjer?" [news-feed])
         (widgets/disclosure {:large 1
                              :style {:padding-block "var(--size-2)"
                                      :margin-left   "var(--size-7)"}} :frontpage/yearwheel :Planlagt [booking.yearwheel/yearwheel-feed])
         (widgets/disclosure {:large 1
                              :style {:padding-block "var(--size-2)"
                                      :margin-left   "var(--size-7)"}} :frontpage/openinghours "Åpningstider" [booking.openhours/opening-hours])]]]]]))

