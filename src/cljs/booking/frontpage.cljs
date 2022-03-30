(ns booking.frontpage
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [db.core :as db]
            [booking.common-views]
            [arco.react]
            [schpaa.debug :as l]
            [times.api :as ta]
            [tick.core :as t]))

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

(defn circular-logo-thing [dark-mode?]
  [:div.flex.justify-center
   [:div.bg-alt
    {:style {:position      :relative
             :filter        (if dark-mode?
                              "contrast(0.98) brightness(0.5) saturate(.5)"
                              "brightness(1)")
             :box-shadow    "var(--shadow-1)"
             :height        "var(--size-fluid-7)"
             :width         "var(--size-fluid-7)"
             ;:height        "var(--size-fluid-7)"
             :overflow      :hidden
             ;:aspect-ratio "1/1"
             ;:clip-path     "circle(50% at 50% 50%)"
             :border-radius "var(--radius-round)"}}
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
                  tm (js/setInterval f 3000)]
       [:<>
        [:img.bg-alt {:style {:position          :absolute
                              :inset             "0px"
                              :clip-path         "circle(36% at 50% 50%)"
                              :object-fit        :contain
                              :border-radius     "var(--radius-round)"
                              #_#_:animation (if (odd? @c)
                                               "var(--animation-slide-in-up) "
                                               "var(--animation-slide-in-left) ")
                              :-transition       "transform 500ms"
                              :-transform-origin "bottom left"
                              :-transform        "rotate(0deg)"}
                      :src   (first (drop @c (cycle ["/img/circle.jpg" "/img/logo-n.png"])))}]
        [:img.bg-alt {:style {:position          :absolute
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
                      :src   (first (drop @c (cycle ["/img/logo-n.png" "/img/circle.jpg"
                                                     "/img/frontpage/Bilde 28.03.2022 klokken 16.58.jpg"
                                                     "/img/frontpage/Bilde 28.03.2022 klokken 16.45.jpg"
                                                     "/img/frontpage/brygge.jpeg"
                                                     "/img/frontpage/Bilde 28.03.2022 klokken 16.59.jpg"])))}]]
       (finally (js/clearInterval tm)))]]])

(defn front-image []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
    [frontpage-image-style
     {:class [
              (if @(rf/subscribe [:lab/at-least-registered]) :registered :anonymous)
              (if dark-mode? :dark :light)]
      :src   frontpage-image}
     [:div.absolute.left-0.bottom-0.right-0.h-64
      {:style {:color      "red"
               :background "linear-gradient(0deg, var(--toolbar-) 0%, rgba(0,0,0,0) 100%)"
               :z-index    120}}]]))

(o/defstyled fp-header sc/hero'
  {:color       "var(--text0)"
   :font-weight "var(--font-weight-5)"})

(o/defstyled fp-topic sc/text0)
{:color       "var(--text0)"
 :font-weight "var(--font-weight-5)"}


(defn hanging-header []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
    [:div.absolute.max-w-lg.mx-auto
     {:style {:top   "20vh"
              :left  "1rem"
              :right "1rem"}}
     [sc/col-space-8

      [circular-logo-thing dark-mode?]

      [sc/col-space-1
       #_[:div
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
       #_[sc/hero' {:style {:line-height     "1"
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
      (when-not @(rf/subscribe [:lab/at-least-registered])
        [:div {:class [:w-full]}
         [sc/row-ec {:class [:mx-auto :max-w-md]}
          [hoc.buttons/cta
           {:on-click #(rf/dispatch [:app/login])
            :style    {:box-shadow     "var(--shadow-1)"
                       :padding-block  "var(--size-2)"
                       :padding-inline "var(--size-3)"}}
           [sc/col {:style {:color       "white"            ;"var(--text0-copy)"
                            :align-items :start}}
            [sc/text1-cl {:class [:align-left]} "Logg inn"]
            [sc/text1-cl "& register deg"]]]]])

      [:div.flex.flex-col.items-start.gap-4
       [:div.space-y-8.w-full
        #_[schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/stats "statistikk"
           (fn []
             [:div.space-y-8
              [sc/row-sc [stats]]
              [sc/row-sc [boats]]])]
        #_[schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
           booking.common-views/master-control-box]
        [fp-header "Hva skjer?"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-header "Nyheter"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]
        [fp-topic "fp-topic"]]]]]))

(defn scroll-up-animation []
  (let [know-how-to-scroll? (rf/subscribe [:lab/we-know-how-to-scroll?])]
    [:<>
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
      [sc/icon ico/more-if-you-scroll-down]]]))
;.to-transparent

(defn frontpage' []
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

    [booking.common-views/after-content]]
   #_[booking.common-views/bottom-tabbar]
   #_[:div.sticky.bottom-0.h-32.bg-white
      [:div "SAMPLE FOOTER"]]])

(o/defstyled bg-light :div
  #_{:position       :relative
     :height         "100vh"
     :z-index        0
     :pointer-events :auto}
  ;[:&:before]
  {:content               [:str ""]
   :height                "100vh"
   :background-position   "center center"
   :background-repeat     :no-repeat
   :background-size       :cover
   :background-blend-mode "normal"
   ;:filter                "contrast(0.498) brightness(0.98) opacity(0.5)"
   :background-image      [;"linear-gradient(to bottom, rgba(0,0,0,0) 30%, rgba(0,0,0,0.5) 50%,var(--content) 100%)"
                           "var(--background-image-light)"]}
  [:at-supports {:height :100dvh}
   {:height "calc(100dvh)"}])


(o/defstyled bg-whatever-dark :div
  {:position       :relative
   :height         "100vh"
   :z-index        0
   :pointer-events :auto}
  [:&:before
   {:content               [:str ""]
    :position              :absolute
    :left                  0
    :right                 0
    :top                   0
    :bottom                0
    :xbackground           "red"
    :background-position   "center center"
    :background-repeat     :no-repeat
    :background-size       :cover
    :background-blend-mode "normal"
    ;:filter                "contrast(0.98) brightness(0.5) saturate(0)"
    :background-image      [;"linear-gradient(green,var(--toolbar))"
                            ;"linear-gradient(to bottom, rgba(0,0,0,0), white)"
                            "var(--background-image-dark)"]}])

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(defn flex-datetime [date formatted]
  (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
        on-click {:on-click #(do
                               (tap> ["toggle" @relative-time?])
                               (.stopPropagation %)
                               (toggle-relative-time))}]
    (when date
      (if @relative-time?
        [sc/link on-click (formatted :date date)]
        [arco.react/time-to {:times  [(if (t/date-time? date)
                                        (t/date-time date)
                                        (t/at (t/date date) (t/midnight)))
                                      (t/now)]
                             :config booking.data/arco-datetime-config}
         (fn [formatted-t]
           [sc/link on-click (formatted :text formatted-t)])]))))

(o/defstyled fp-right-aligned-link sc/ingress'
  :text-right :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(o/defstyled fp-left-aligned-link sc/ingress'
  :text-left :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(defn listitem [a b]
  [:<>
   (if a
     [flex-datetime a (fn [type d]
                        (if (= :date type)
                          [:div.whitespace-nowrap.font-bold.flex.gap-1

                           [fp-right-aligned-link (some-> d t/day-of-month) "."]
                           [fp-left-aligned-link (some-> d ta/month-name)]]
                          [fp-right-aligned-link d]))]
     [:div])
   [:div.self-center [sc/ingress' {:style {:font-weight "400"}} b]]])

(defn frontpage []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
    [(if dark-mode? bg-whatever-dark bg-light)
     [:div.min-h-full.z-0.relative.-debug
      [:div.mx-4
       [:div.pt-40.pb-24 [circular-logo-thing dark-mode?]]
       [:div.relative.space-y-8.w-full.mx-auto.max-w-lg.py-4.z-100
        ;{:style {:background-color "red" #_"var(--content)"}}
        [fp-header "Hva skjer?"]
        ;[l/ppre-x @(rf/subscribe [:lab/at-least-registered])]
        [:div {:style {:display               "grid"
                       :gap                   "var(--size-4) var(--size-2)"
                       :grid-template-columns "min-content 1fr"}}
         [listitem (t/date "2022-04-04") "Nøkkelsjekk"]
         [listitem (t/date "2022-04-28") "Nøkkelvaktmøte"]
         [listitem (t/date "2022-05-08") "Sesongstart #35"]
         [listitem nil [sc/link {} "se flere"]]]
        [fp-header "Nyheter"]
        [listitem nil "<fp-topic>"]
        [listitem nil "<fp-topic>"]
        (when goog.DEBUG
          [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
           booking.common-views/master-control-box])]]]
     [booking.common-views/after-content]
     #_[:div.sticky.bottom-10]
     [booking.common-views/bottom-tabbar]]))

