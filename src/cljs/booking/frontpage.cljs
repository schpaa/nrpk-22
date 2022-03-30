(ns booking.frontpage
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [db.core :as db]
            [booking.common-views]
            [schpaa.debug :as l]))

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

(o/defstyled frontpage-image-hack' :div
  [:&
   {:z-index 0}
   [:img {:object-fit      :cover
          :object-position "center"
          :width           "100%"
          :min-height      "20rem"}]
   [:.light
    {:filter "opacity(0.4) brightness(.95) grayscale(0.5)"}]
   [:.dark
    {:filter "opacity(0.1) brightness(.95) grayscale(.6)"}]
   [:.anonymous {:height "calc(100vh)"}]
   [:.registered {:height "calc(100vh)"}]
   [:at-supports {:height :100dvh}
    {:height "calc(100dvh - 5rem)"}]
   [:at-media {:max-width "511px"}
    [:.registered {:height "calc(100vh - 5rem)"}]
    [:.anonymous {:height "calc(100vh)"}]]
   [:at-supports {:height "100dvh"}
    [:.anonymous {:height "calc(100dvh)"}]
    [:.registered {:height "calc(100dvh - 5rem)"}]]]
  ([{:keys [src class style]}]
   [:<> [:img {:class   class
               :style   style
               :loading :lazy
               :src     src}]]))

(def frontpage-image (str "/img/frontpage/"
                          (nth ["brygge.jpeg"
                                ;"DSCF0075.JPG"
                                "DSCF0051.JPG"
                                "DSCF2668.jpeg"
                                "Bilde 28.03.2022 klokken 16.58.jpg"
                                "Bilde 28.03.2022 klokken 17.09.jpg"
                                "animal-muppet.png"
                                "Bilde 28.03.2022 klokken 16.59.jpg"
                                "Bilde 28.03.2022 klokken 16.49.jpg"
                                "Bilde 28.03.2022 klokken 16.45.jpg"] 0)))

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])]
    [:div.py-4.mx-auto.max-w-lg.px-4
     ;[l/ppre-x route]
     [:div [hoc.buttons/reg-pill-icon
            {:on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
            ico/tilbakemelding "Har du en tilbakemelding?"]]]))

(defn front-image []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
    [frontpage-image-hack'
     {;:style {:z-index -1}
      :class [(if @(rf/subscribe [:lab/at-least-registered]) :registered :anonymous)
              (if dark-mode? :dark :light)]
      :src   frontpage-image}]))

(defn hanging-header []
  [:div.absolute.max-w-lg.mx-auto
   {:style {:top   "21vh"
            :left  "1rem"
            :right "1rem"}}
   [sc/col-space-8
    [sc/col-space-1
     [:div
      [sc/ingress' {:style {;:animation-duration        "2s"
                            ;:animation                 "var(--animation-float)"
                            ;:animation-timing-function "var(--ease-elastic-2)"
                            :line-height      "auto"
                            :transform        "rotate(-3deg) translate(-8px, 2px)"
                            :transform-origin "top left"
                            :margin-bottom    "4px"
                            :text-transform   :uppercase
                            :font-size        "var(--font-size-fluid-0)"}}
       [:span
        {:style {:font-weight    "var(--font-weight-5)"
                 :background     "var(--toolbar)"
                 :color          "var(--brand1)"
                 :padding-inline "var(--size-1"
                 :font-size      "var(--font-size-fluid-0)"
                 :padding-block  "var(--size-1)"
                 :border-radius  "var(--radius-0)"}}
        [:span {:style {:font-size "var(--font-size-fluid-0)"}} "Velkommen til NRPK"]]]]
     [sc/hero' {:style {:line-height     "1"
                        ;:color           :#000
                        :font-family     "Lora"
                        ;:font-size   "var(--font-size-8)"
                        :font-size       "var(--font-size-fluid-3)"
                        :-letter-spacing "-0.075rem"}}
      [:div {:style {:font-weight #_"100" "var(--font-weight-2)"}} "Nøklevann"]
      [sc/ingress' {:style {:white-space         :nowrap
                            :font-weight #_"100" "var(--font-weight-3)"
                            ;:color           :#000
                            :font-size           "var(--font-size-fluid-2)" #_"var(--font-size-6)"}}
       "ro- og padleklubb"]]]
    #_[:div.flex.flex-col.items-start.gap-4
       [:div.space-y-8.w-full
        [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/stats "statistikk"
         (fn []
           [:div.space-y-8
            [sc/row-sc [stats]]
            [sc/row-sc [boats]]])]]]
    (when-not @(rf/subscribe [:lab/at-least-registered])
      [:div {:class [:w-full]}
       [sc/row-ec {:class [:mx-auto :max-w-md]}
        [hoc.buttons/cta
         {:on-click #(rf/dispatch [:app/login])
          :style    {:box-shadow     "var(--shadow-1)"
                     :padding-block  "var(--size-2)"
                     :padding-inline "var(--size-3)"}}
         [sc/col {:style {:color       "white"              ;"var(--text0-copy)"
                          :align-items :start}}
          [sc/text1-cl {:class [:align-left]} "Logg inn"]
          [sc/text1-cl "& register deg"]]]]])
    ]])

(defn scroll-up-animation []
  (let [know-how-to-scroll? (rf/subscribe [:lab/we-know-how-to-scroll?])]
    [:div
     {:style {:display         :flex
              :justify-content :center
              :align-items     :center
              :padding-block   "var(--size-2)"
              :color           "var(--text0)"
              ;:animation-timing-function "var(--ease-iout-4)"
              :animation       (if @know-how-to-scroll?
                                 "2000ms var(--animation-fade-out) forwards, var(--animation-scale-down) forwards"
                                 "2000ms var(--animation-float)")}}
     [sc/icon ico/more-if-you-scroll-down]]))

(defn frontpage []
  [:div.h-full.relative
   {:style {:background "var(--content)"
            :height     "100vh"
            :style      {:z-index -1}}}

   [:div.relative
    [front-image]
    [:div.absolute
     {:style {:inset "auto 0 0 0"}}
     [scroll-up-animation]]]
   ;image
   #_[:div.m-4
      [sc/col-space-8
       [sc/col-space-2
        [sc/ingress "Etablert i 1987. Ved inngangen på 2022 har klubben over 4200 medlemmer."]
        (let [data [[1 :r.forsiden "Bli medlem"]
                    [3 :r.forsidens "Organisasjon"]
                    [2 :r.forsiden "Registrer deg og logg inn"]
                    [4 :r.oversikt "Oversikt"]]]
          [sc/row-sc-g2-w (map (comp f rest) (sort-by first data))])]]]
   [hanging-header]
   [:div
    (sc/markdown'
      [sc/col-space-2
       (schpaa.markdown/md->html "## Postadresse\nNøklevann ro- og padleklubb<br/>Postboks 37, 0621 Bogerud<br/>[styret@nrpk.no](mailto:styret@nrpk.no)<br/>[medlem@nrpk.no](mailto:medlem@nrpk.no)")])

    [after-content]]
   [booking.common-views/bottom-tabbar]
   #_[:div.sticky.bottom-0.h-32.bg-white
      [:div "SAMPLE FOOTER"]]])