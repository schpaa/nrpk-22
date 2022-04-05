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
            [schpaa.debug :as l]
            [times.api :as ta]
            [tick.core :as t]
            [booking.carousell]
            [booking.flextime :refer [flex-datetime]]
            [booking.yearwheel]))

(o/defstyled antialiased :div
  #_[:* {:x-webkit-font-smoothing  :auto
         :x-moz-osx-font-smoothing :auto
         :-webkit-font-smoothing   :antialiased
         :-moz-osx-font-smoothing  :grayscale}])

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
     ;[f (count (filter (fn [[k {:keys [godkjent våttkort]}]] (and godkjent (< 0 våttkort))) nv)) "med våttkort"]
     [table-text (str (count (filter (comp :instruktør val) nv))) "instruktører"]

     ;[:div.justify-self-end [sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}}(str (count (filter (fn [[k {:keys [request-booking]}]] (and request-booking)) nv)))]]
     ;[sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}} "havpadlere"]
     #_[sc/text1 {:style {:color "var(--yellow-2)"}} (count online) ", " (count offline)]]))

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
     [table-text 2 "robåter"]

     ;[:div.justify-self-end [sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}} (count (filter (fn [[k {:keys [godkjent våttkort]}]] (and godkjent (< 0 våttkort))) nv))]]
     ;[sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}} "med våttkort"]
     ;[f (str (count (filter (comp :instruktør val) nv)))]
     ;[sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}} "instruktører"]
     ;[:div.justify-self-end [sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}}(str (count (filter (fn [[k {:keys [request-booking]}]] (and request-booking)) nv)))]]
     ;[sc/text0 {:style {:font-size "var(--font-size-fluid-0)"}} "havpadlere"]
     #_[sc/text1 {:style {:color "var(--yellow-2)"}} (count online) ", " (count offline)]]))

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

(def frontpage-image
  (map #(str "/img/caro/" %)
       ["img-0.jpg"
        "img-1.jpg"
        "img-13.jpg"
        "img-16.jpg"
        "img-12.jpg"
        ;"img-3.jpg"
        "img-4.jpg"
        ;"img-2.jpg"
        ;"img-5.jpg"
        ;"img-6.jpg"
        ;"img-7.jpg"
        "img-8.jpg"]
       #_["brygge.jpeg"
          ;"DSCF0075.JPG"
          "DSCF0051.JPG"
          "DSCF2668.jpeg"
          "Bilde 28.03.2022 klokken 16.58.jpg"
          "Bilde 28.03.2022 klokken 17.09.jpg"
          "animal-muppet.png"
          "Bilde 28.03.2022 klokken 16.59.jpg"
          "Bilde 28.03.2022 klokken 16.49.jpg"
          "Bilde 28.03.2022 klokken 16.45.jpg"]))

(defn circular-logo-thing [dark-mode?]
  [booking.styles/logothing {:class [(if dark-mode? :dark :light)]}

   [:img {:style {:position                  :absolute
                  :inset                     0
                  :object-fit                :contain
                  :border-radius             "var(--radius-round)"
                  :animation-name            "spin3"
                  :animation-timing-function "var(--ease-4)"
                  :animation-delay           "0s"
                  :animation-duration        "5s"
                  :animation-iteration-count 1
                  :animation-direction       :forward
                  :transform-origin          "center"}
          :src   "/img/logo-n.png"}]
   [:div
    {:style {:position  :absolute
             :inset     0
             ;:inset            "auto auto 0px auto"
             :clip-path "circle(35% at 50% 50%)"}}
    [:div.bg-white.w-full.h-full]

    (r/with-let [c (r/atom 0)
                 f (fn [] (swap! c inc))
                 tm 0 #_(js/setInterval f 3000)]
      [:<>
       [:img.bg-alt {:style {:position          :absolute
                             :inset             "0px"
                             ;:clip-path         "circle(36% at 50% 50%)"
                             :object-fit        :contain
                             :border-radius     "var(--radius-round)"
                             #_#_:animation (if (odd? @c)
                                              "var(--animation-slide-in-up) "
                                              "var(--animation-slide-in-left) ")
                             :transition        "opacity 500ms"
                             :opacity           (if (odd? @c) 0 1)
                             ;:transition-property "opacity"
                             :-transform-origin "bottom left"
                             :-transform        "rotate(0deg)"}
                     :src   (first (drop @c (cycle ["/img/logo-n.png" "/img/logo-m.png"])))}]
       #_[:img.bg-alt {:style {:position          :absolute
                               :inset             "0px"
                               :clip-path         "circle(36% at 50% 50%)"
                               :object-fit        :contain
                               :border-radius     "var(--radius-round)"
                               #_#_:animation (if (odd? @c)
                                                "var(--animation-slide-in-up) "
                                                "var(--animation-slide-in-left) ")
                               :-transition       "transform 500ms"
                               :-transform-origin "bottom left"
                               :-transform        (if (odd? @c) "rotate(90deg)" "rotate(-90deg)")}
                       :src   (first (drop @c (cycle ["/img/logo-n.png" "/img/logo-m.png"
                                                      "/img/frontpage/Bilde 28.03.2022 klokken 16.58.jpg"
                                                      "/img/frontpage/Bilde 28.03.2022 klokken 16.45.jpg"
                                                      "/img/frontpage/brygge.jpeg"
                                                      "/img/frontpage/Bilde 28.03.2022 klokken 16.59.jpg"])))}]]
      (finally (js/clearInterval tm)))]])


(o/defstyled fp-topic sc/text0)
{:color       "var(--text0)"
 :font-weight "var(--font-weight-5)"}

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

(o/defstyled bg-light :div
  [:& {:position            :relative
       :height              "100vh"
       :background-position "center center"
       :background-repeat   "no-repeat"
       :background-size     "cover"

       ;:background-blend-mode "normal"
       #_#_:background-image "var(--background-image-light)"}
   [:&.light {:background-image ["var(--background-image-light)"]}]
   [:&.dark {:background-image ["var(--background-image-dark)"]}]
   [:at-media {:max-width "511px"}
    [:&.bottom-toolbar
     {:height "calc(100vh - 5rem)"}]]]
  [:at-supports {:height :100dvh}
   {:height "calc(100dvh)"}])


(o/defstyled fp-right-aligned-link sc/ingress'
  :text-right :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(o/defstyled fp-left-aligned-link sc/ingress'
  :text-left :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(defn listitem [date type-text tldr]
  [:div.col-span-2.inline-block.gap-2
   {:style {:color       "var(--text0)"
            :line-height "var(--font-lineheight-3)"
            :font-size   "var(--font-size-2)"
            :font-weight "var(--font-weight-4)"}}
   [:span {:style {:color "var(--text1)"}} type-text " - "]
   (when tldr
     [:span {:style {:color "var(--text0)"}} tldr])
   (when date
     [:span " "
      [flex-datetime date
       (fn [type d]
         (if (= :date type)
           [sc/subtext-inline {:style {:text-decoration :none}} (ta/date-format-sans-year d)]
           [sc/subtext-inline d]))]])])

(defn listitem' [date text]
  [:div.col-span-2.space-y-0
   (if date
     [flex-datetime date (fn [type d]
                           (if (= :date type)
                             [sc/subtext {:style {:text-decoration :none}} (ta/date-format-sans-year d)]
                             [sc/subtext d]))])
   [:div.flex.items-baseline.gap-2
    [sc/fp-text text]]])

(defn config []
  {:dots             true
   :infinite         true
   :arrows           true
   :slidesToShow     1
   :pauseOnHover     true
   :inside?          true
   :autoplay         true
   ;:autoplaySpeed    1000  
   :afterChange      (fn [e] #_(rf/dispatch [::set-active-menu-page e]))
   ;:beforeChange     (fn [_ e] (rf/dispatch [::set-active-menu-page]))
   :touchMove        true
   :speed            300
   :initialSlide     1
   :fade             false
   :pauseOnDotsHover true})

(o/defstyled image-caro-style :div
  [:&
   {:width "calc(100vw)"}
   [:&.toolbar
    {:width "calc(100vw - 4rem)"}]
   [:at-media {:max-width "512px"}
    [:&.toolbar
     {:width "calc(100vw)"}]]])

(defn image-carousell []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        at-least-registered? @(rf/subscribe [:lab/at-least-registered])]
    [:div
     {:style (conj {:padding-block "var(--size-4)"
                    :filter        (if dark-mode? "brightness(0.75)" "")})}
     [image-caro-style {:class [(if at-least-registered? :toolbar)]}
      [booking.carousell/render-carousell
       {:carousell-config {:initialSlide  0
                           :slidesToShow  2
                           :autoplay      true
                           :autoplaySpeed 5500}
        :class            [:bg-alt :h-full :m-4]
        :view-config      (config)
        :contents
        (into [] (map (fn [e] [:div.outline-none
                               {:style {;:object-fit   :cover
                                        ;:height       "100%"
                                        ;:width        "100%"
                                        ;:aspect-ratio "1/1"
                                        :overflow-y :hidden}}
                               [:img {:style  {:object-fit   :cover
                                               :height       "100%"
                                               :width        "100%"
                                               :aspect-ratio "3/2"
                                               :overflow-y   :hidden}
                                      :width  "100%"
                                      :height "100%"
                                      :src    e}]])) frontpage-image)}]]]))

(defn header-with-logo []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
    (if true
      [:div {:class [:pt-24 :pb-20 :--debug2 :xhover:rotate-0 :-rotate-6 :duration-500]
             :style {:-transform            "rotate(-6deg)"
                     :transform-origin      "center right"
                     :display               :grid
                     :gap                   "var(--size-4)"
                     :grid-template-columns "1fr min-content 1fr"}}
       [:div {:class [:--debug4]
              :style {:justify-self :end
                      :align-self   :center}}
        [bs/logo-text {:class [:--debug3]
                       :style {:width        "auto"
                               :justify-self :end}}
         [sc/hero' {:style {:text-align  :right
                            :color       "var(--text1)"
                            :font-weight "var(--font-weight-4)"}} "Nøklevann"]
         [sc/ingress-cl {:style {:white-space :nowrap
                                 :color       "var(--text1)"
                                 :font-weight "var(--font-weight-3)"}} "ro– og padleklubb"]]]
       [:div.h-24.w-auto.border-l.border-dashed
        {:style {:border-width "1px"
                 :border-color (if dark-mode? "var(--brand1)" "var(--brand1)")}}]

       [:div {:class [:--debug3]
              :style {:align-self   :center
                      :justify-self :start}}
        [:div.h-20.w-20 [circular-logo-thing dark-mode?]]]]
      [:div.pt-40.pb-24 [bs/centered [:div.h-28.w-28 [circular-logo-thing dark-mode?]]]])))

(o/defstyled top-right-windowclosebutton :div
  {:position :absolute
   :top      "var(--size-2)"
   :right    "var(--size-2)"})

(o/defstyled bottom-right-userfeedback :div
  {:position :absolute
   :bottom   "var(--size-2)"
   :right    "var(--size-2)"
   :color    "var(--text0)"})

(defn helpful-to-earlier-users []
  [sc/surface-c {:class [:-mx-4]
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

   [sc/col-space-2
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
   [hoc.buttons/cta {:style    {:box-shadow "var(--shadow-2)"}
                     :on-click #(rf/dispatch [:app/login])}
    [sc/col {:style {:text-align :left}}
     [sc/ingress' {:style {:font-weight "var(--font-weight-6)"
                           :color       "var(--gray-0)"}} "Logg inn"]
     [sc/text {:style {:color "var(--gray-0)"}} "& registrer deg"]]]])

(defn news-feed []
  [:div.space-y-4
   [:div.flex.items-baseline.gap-2 [sc/fp-header "Nyheter"] (sc/link {} "(se flere nyheter)")]
   [:div.ml-4 {:style {:display               "grid"
                       :gap                   "var(--size-4) var(--size-2)"
                       :grid-template-columns "1fr"}}
    [listitem' (t/date) "Ny layout og organisering av hjemmesiden."]
    [listitem' (t/date) "Nye båter er kjøpt inn."]
    [listitem' (t/date) "Nøkkelvakter kan nå rapportere hms-hendelser og dokumentere materielle mangler og skader her."]]])

(defn yearwheel-feed []
  (let [data (take 5 (sort-by (comp :date second) < (booking.yearwheel/get-all-events false)))]
    [:div.space-y-4
     {:style {:padding-bottom "var(--size-10)"}}
     [:div.flex.items-baseline.gap-2 [sc/fp-header "Hva skjer?"] (sc/link {:href (kee-frame.core/path-for [:r.yearwheel])} "(se også i årshjulet)")]
     ;[l/ppre-x data]
     [:div.ml-4 {:style {:display               "grid"
                         :gap                   "var(--size-2) var(--size-2)"
                         :grid-template-columns "min-content 1fr"}}
      (for [[id {:keys [date type-text tldr]}] data]
        [listitem (t/date date) type-text tldr])]]))
;[listitem (t/date "2022-04-04") "Nøkkelvaktens infosjekk"]
;[listitem (t/date "2022-04-28") "Nøkkelvaktmøte"]
;[listitem (t/date "2022-05-06") "Dugnad"]
;[listitem (t/date "2022-05-08") "Sesongstart '22"]]]))

(defn frontpage []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        hide-image-carousell? (schpaa.state/listen :lab/hide-image-carousell)
        at-least-registered? (rf/subscribe [:lab/at-least-registered])
        master-emulation (rf/subscribe [:lab/master-state-emulation])]
    [:div
     [bg-light {:style {}
                :class [(if dark-mode? :dark :light)
                        (if @at-least-registered? :bottom-toolbar)]}

      [:div.min-h-full.z-0.relative

       [:div.max-w-lg.mx-auto
        [:div.mx-4.py-4.pt-24
         (when (and goog.DEBUG @master-emulation)
           [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
            booking.common-views/master-control-box])]]

       [:div.mx-4
        [:div.max-w-lg.mx-auto
         [header-with-logo]]]

       (when-not @hide-image-carousell?
         [image-carousell])

       [:div.mx-4
        [:div.space-y-12.w-full.mx-auto.max-w-lg.py-4
         (when (and (not @(schpaa.state/listen :lab/skip-easy-login))
                    (not @at-least-registered?))
           [helpful-to-earlier-users])

         (when-not @at-least-registered?
           [please-login-and-register])

         [news-feed]
         [yearwheel-feed]]]]

      [bs/attached-to-bottom
       {:class [(if @at-least-registered? :bottom-toolbar) :z-20]}
       [scroll-up-animation]]
      [booking.common-views/after-content]]]))
