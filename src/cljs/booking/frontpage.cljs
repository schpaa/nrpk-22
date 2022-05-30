(ns booking.frontpage
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.styles :as bs]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [booking.page-layout]
            [arco.react]
            [times.api :as ta]
            [tick.core :as t]
            [booking.carousell]
            [booking.flextime :refer [flex-datetime]]
            [booking.yearwheel]
            [booking.openhours]
            [booking.account]
            [booking.common-widgets :as widgets]
            [booking.news :as news]
            [booking.toolbar]))

;; styles

(o/defstyled antialiased :div
  [:* {:x-webkit-font-smoothing  :auto
       :x-moz-osx-font-smoothing :auto
       :-webkit-font-smoothing   :antialiased
       :-moz-osx-font-smoothing  :grayscale}])

(o/defstyled page-with-background :div
  [:& :pt-16x
   {:background-position "center center"
    :background-repeat   "no-repeat"
    ;todo
    ;:background-color "transparent"
    :background-size     "cover"}
   [:&.light {:background-image "var(--background-image-light)"}]
   [:&.dark {:background-image "var(--background-image-dark)"}]]
  #_[:at-media {:max-width "511px"}
     [:&.light {:background-image "none"}]])

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

;;

(defn table-text [a b]
  [:<>
   [:div.justify-self-end [sc/text0 [antialiased {:style {:font-weight "var(--font-weight-7)"
                                                          :font-size   "var(--font-size-fluid-0)"}} a]]]
   [sc/text0 [antialiased {:style {:text-transform :uppercase
                                   :letter-spacing "0.025rem"
                                   :font-size      "var(--font-size-fluid-0)"}} b]]])

(def frontpage-images'
  (map #(str "/img/personas/" %)
       ["noun-persona-410775.png"
        "noun-persona-410777.png"
        "noun-persona-410780.png"
        "noun-persona-410782.png"
        "noun-persona-410783.png"
        "noun-persona-410786.png"
        "noun-persona-410788.png"
        "noun-persona-410790.png"
        "noun-persona-415635.png"
        "noun-persona-415668.png"
        "noun-persona-426505.png"
        "noun-persona-436706.png"
        "noun-persona-488544.png"
        "noun-persona-488556.png"
        "noun-persona-497844.png"
        "noun-persona-497847.png"
        "noun-persona-622291.png"
        "noun-persona-622293.png"
        "noun-persona-623584.png"
        "noun-persona-633475.png"
        "noun-persona-633483.png"
        "noun-persona-4144932.png"
        "noun-persona-4144944.png"
        "noun-persona-4144945.png"
        "noun-persona-4144951.png"
        "noun-persona-4144954.png"
        "noun-persona-4145149.png"
        "noun-persona-4145160.png"
        "noun-persona-4145168.png"
        "noun-persona-4145173.png"
        "noun-persona-4145177.png"
        "noun-persona-4145185.png"
        "noun-persona-4145187.png"
        "noun-persona-4145191.png"
        "noun-persona-4145197.png"
        "noun-persona-4145199.png"
        "noun-persona-4145214.png"]))

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
                                      :src    e}]])) news/frontpage-images)}]]]))

(defn todays-numbers [c1 c2 a b]
  [sc/row-sc-g2 {:style {:-transform       "translateY(1rem) rotate(6deg)"
                         :opacity          0.33
                         :transform-origin "end bottom "}}
   #_[sc/col {:class [:gap-2]}
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
   :grid-template-columns "1fr min-content 1fr"
   :grid-template-areas   [["type" "graph" "status"]]})

(o/defstyled large-grid :div
  {:display               :grid
   :padding               "4px"
   :font-size             "100%"
   :grid-column-gap       "1rem"
   :grid-template-columns "1fr min-content 1fr"
   :grid-template-areas   [["type" "graph" "status"]]})

(o/defstyled fancy-front :div
  [:&
   large-grid
   {:max-width         "calc(100% - 1rem)"
    :height            "auto"
    :padding-inline    "4px"
    :-background-color "hsla(0,50%,50%,20%)"}

   [:at-media {:max-width "511px"}
    small-grid
    {:-background-color "blue"}]])

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
  [:div {:style {:min-font-size "80%"
                 :font-size     "100%"
                 :padding-block "2rem"}}
   (fancy-front
     [:div {:style {:align-self   :center
                    :justify-self :start
                    :grid-area    "graph"}} (widgets/logo-graph)]
     [:div {:style {:align-self :center
                    :grid-area  "type"}} logo-type]
     [:div
      {:style {:width "min-content"}}
      [:div.inline-flex.flex-col.justify-center.space-y-2

       [sc/text1 "Åpningstider"]

       [sc/co
        [sc/small1 "tir, ons & tor"]
        [sc/text1 {:style {:line-height 1}
                   :class [:whitespace-nowrap]} "kl 18:00—21:00"]]

       [:div.mx-px
        {:style {:opacity    0.5
                 :border-top "1px solid var(--text1)"}}]

       [sc/co
        {:style {:line 1}}
        [sc/small1 "lør & søn"]
        [sc/text1 {:style {:line-height 1}
                   :class [:whitespace-nowrap]} "kl 11:00—17:00"]]]]

     #_[:div {:style {:align-self   :center
                      :justify-self :center
                      :grid-area    "status"}}
        (todays-numbers "_" "_" c '—)])])

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
     [sc/title1 {:style {:font-weight "var(--font-weight-7)"
                         :color       "var(--text0-copy)"}} "Logg inn"]
     [:div {:style {:color "var(--text0-copy)"}} "& registrer deg"]]]])

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

;todo blog-system
;[listitem' (t/date "2022-04-11") "Båtarkivet er i bruk"]
;[listitem' (t/date "2022-03-04") "Ny layout og organisering av hjemmesiden."]
;[listitem' (t/date "2022-02-15") "Nøkkelvakter kan nå rapportere hms-hendelser og dokumentere materielle mangler og skader her."]
;[listitem' (t/date "2021-11-01") "Nye båter er kjøpt inn."]]]))

(defn set-ref [a scroll-fn]
  (tap> {:set-ref a})
  (fn [el]
    (when-not @a
      (.addEventListener el "scroll" #(do (tap> "SCRO")
                                          (scroll-fn %)))
      (reset! a el))))

(defn news-section []
  [sc/col-space-1
   [sc/row-field
    (widgets/disclosure
      {:padded-heading []
       :large          1
       :style          {:padding-block "var(--size-2)"}}
      :frontpage/news
      [sc/row-sc-g4 {:style {:align-items :baseline}}
       [:p "Hva skjer?"]
       [widgets/auto-link {:style   {:font-family "Inter"}
                           :class   [:z-10 :small]
                           :caption "mer her"}
        :r.booking-blog]]
      [news/news-feed])]])

(defn planned-section []
  (widgets/disclosure
    {:padded-heading []
     :large          1
     :style          {:padding-block "var(--size-2)"
                      :xmargin-left  "2rem"}}
    :frontpage/yearwheel
    [sc/row-sc-g4 {:style {:align-items :baseline}}
     [:p "Årshjul"]
     [widgets/auto-link {:style   {:font-family "Inter"}
                         :class   [:z-10 :small]
                         :caption "mer her"} :r.yearwheel]]
    [booking.yearwheel/yearwheel-feed]))

;; here are some changes
(defn frontpage []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        show-image-carousell? (schpaa.state/listen :lab/show-image-carousell)
        reg? (rf/subscribe [:lab/at-least-registered])
        master-emulation (rf/subscribe [:lab/master-state-emulation])]
    [page-with-background
     {:style {:min-height "90vh"
              :height     "90vh"}
      :class [:-debugx
              (if dark-mode? :dark :light)
              (if @reg? :bottom-toolbar)]}

     [:div.min-h-full.z-0
      {:style {:min-height     "90vh"
               :padding-bottom "8rem"}}
      ;debug
      #_(when (and goog.DEBUG @master-emulation)
          [:div.max-w-lg.mx-auto
           [:div.mx-4.py-4x.pt-24x
            [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
             booking.page-layout/master-control-box]]])

      [header-with-logo]

      (when @show-image-carousell? [image-carousell])

      (when (and (not @(schpaa.state/listen :lab/skip-easy-login))
                 (not @reg?))
        [:div.mx-4.pb-12
         [:div.max-w-lg.mx-auto.px-4 [helpful-to-earlier-users]]])

      ;;disclosed units
      [sc/col-space-8
       {:style {:padding-inline "var(--size-0)"
                :max-width      "min(calc(100% - 1rem), calc(768px - 3rem))"
                :xwidth         "calc(768px - 5rem)"
                :xmax-width     "calc(768px - 5rem)"}
        :class [:mx-auto :min-h-full :xpb-8]}
       [sc/col-space-8
        (when-not @reg?
          [please-login-and-register])
        [news-section]
        [planned-section]]]]

     [widgets/after-content]
     [:div.sticky.bottom-0.noprint
      [booking.toolbar/bottom-toolbar]]]))
