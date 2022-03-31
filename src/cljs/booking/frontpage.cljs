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
      (finally (js/clearInterval tm)))]])

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

(o/defstyled fp-header :div
  {:margin-left    "-1px"

   :font-family    "Inter"
   :letter-spacing "var(--font-letterspacing-0)"
   :font-size      "var(--font-size-4)"}
  {:color       "var(--text1)"
   :font-weight "var(--font-weight-4)"})

(o/defstyled fp-topic sc/text0)
{:color       "var(--text0)"
 :font-weight "var(--font-weight-5)"}

(defn scroll-up-animation []
  (let [know-how-to-scroll? (rf/subscribe [:lab/we-know-how-to-scroll?])]
    [bs/scroll-up-animation
     {:class [(if @know-how-to-scroll? :fadeaway :keepgoing)]}
     [sc/icon ico/more-if-you-scroll-down]]))

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
        (if (t/<= date (t/date))
          [arco.react/time-since {:times  [(if (t/date-time? date)
                                             (t/date-time date)
                                             (t/at (t/date date) (t/midnight)))
                                           (t/now)]
                                  :config booking.data/arco-datetime-config}
           (fn [formatted-t]
             [:div on-click (formatted :text formatted-t)])]
          [arco.react/time-to {:times  [(if (t/date-time? date)
                                          (t/date-time date)
                                          (t/at (t/date date) (t/midnight)))
                                        (t/now)]
                               :config booking.data/arco-datetime-config}
           (fn [formatted-t]
             [:div on-click (formatted :text formatted-t)])])
        [:div on-click (formatted :date date)]))))

(o/defstyled fp-right-aligned-link sc/ingress'
  :text-right :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(o/defstyled fp-left-aligned-link sc/ingress'
  :text-left :whitespace-nowrap
  {:font-weight "400"
   :color       "var(--brand1)"})

(defn listitem [a b]
  [:div.col-span-2.space-y-1.flex.items-baseline.gap-2
   [:div..whitespace-nowrap [sc/ingress' {:style {:font-weight "400"}} b]]

   (if a
     [flex-datetime a (fn [type d]
                        (if (= :date type)
                          [:div.whitespace-nowrap.font-bold.flex.gap-1
                           [fp-right-aligned-link (some-> d t/day-of-month) "."]
                           [fp-left-aligned-link (some-> d ta/month-name)]]
                          [fp-right-aligned-link d]))])])

(defn listitem' [date text]
  [:div.col-span-2.space-y-1
   (if date
     [flex-datetime date (fn [type d]
                           (if (= :date type)
                             [sc/subtext {:style {:text-decoration :none}} (ta/date-format-sans-year d)]
                             #_[:div.whitespace-nowrap.font-bold.flex.gap-1
                                [fp-right-aligned-link (some-> d t/day-of-month) "."]
                                [fp-left-aligned-link (some-> d ta/month-name)]]
                             [sc/subtext d]))])

   [:div.flex.items-baseline.gap-2
    [sc/ingress' {:style {:line-height "var(--font-lineheight-3)"
                          :font-weight "400"}} text]]])



(defn frontpage []
  (let [dark-mode? @(schpaa.state/listen :app/dark-mode)
        at-least-registered? @(rf/subscribe [:lab/at-least-registered])]
    [:div
     [bg-light {:class [(if dark-mode? :dark :light)
                        (if at-least-registered? :bottom-toolbar)]}
      ;[:div.fixed.bottom-0.inset-x-0.h-24.bg-gradient-to-t.from-gray-400.z-10.pointer-events-none]
      [:div.min-h-full.z-0.relative
       [:div.mx-4
        [:div.max-w-lg.mx-auto
         (if true
           [:div {:class [:pt-32 :pb-20 :--debug2 :hover:rotate-0 :-rotate-6 :duration-500]
                  :style {:-transform            "rotate(-6deg)"
                          :transform-origin      "center right"
                          :display               :grid
                          :gap                   "var(--size-4)"
                          :grid-template-columns "1fr min-content 1fr"}}
            [:div {:class [:--debug4]
                   :style {:justify-self :end
                           ;:opacity 0.33
                           :align-self   :center}}
             [bs/logo-text {:class [:--debug3]
                            :style {:width        "auto"
                                    ;:font-family "Merriweather"
                                    :justify-self :end}}
              [sc/hero' {:style {:text-align  :right
                                 ;:font-family "Merriweather"
                                 ;:font-family "Merriweather"
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
           [:div.pt-40.pb-24 [bs/centered [:div.h-28.w-28 [circular-logo-thing dark-mode?]]]])]
        [:div.xrelative.space-y-12.w-full.mx-auto.max-w-lg.py-4.z-100

         (when-not at-least-registered?
           (when-not @(schpaa.state/listen :lab/skip-easy-login)
             [sc/surface-c {:class [:-mx-4]
                            :style {:position   :relative
                                    :background :#fffb
                                    :box-shadow "var(--shadow-2)"
                                    :padding    "var(--size-4)"}}
              [:div.absolute.top-2.right-2
               [sc/icon {:on-click #(schpaa.state/toggle :lab/skip-easy-login)} ico/closewindow]]
              [sc/col-space-2
               [sc/ingress' {:class [:pr-4]
                             :style {:font-weight "var(--font-weight-4)"
                                     :line-height "var(--font-lineheight-4)"}}
                "Er du nøkkelvakt kan du logge inn med samme bruker som du brukte i Eykt. Har du ikke bruker på Eykt, kan du logge inn allikevel og lage en."]
               [sc/row-ec
                [:div.grow]
                [hoc.buttons/cta {:on-click #(rf/dispatch [:app/login])} "Logg inn nå"]]]]))

         [:div.space-y-4
          [fp-header "Nyheter"]
          [:div.ml-4 {:style {:display               "grid"
                              :gap                   "var(--size-4) var(--size-2)"
                              :grid-template-columns "1fr"}}

           [listitem' (t/date) "Ny layout og organisering av hjemmesiden."]
           [listitem' (t/date) "Nye båter er kjøpt inn."]
           [listitem' (t/date) "Nøkkelvakter skal fra og med denne sesongen rapportere hms-hendelser og dokumentere materielle skader i Eykt/Økt."]]]
         [:div.space-y-4
          [fp-header "Hva skjer?"]
          [:div.ml-4 {:style {:display               "grid"
                              :gap                   "var(--size-4) var(--size-2)"
                              :grid-template-columns "min-content 1fr"}}
           [listitem (t/date "2022-04-04") "Nøkkelsjekk"]
           [listitem (t/date "2022-04-28") "Nøkkelvaktmøte"]
           [listitem (t/date "2022-05-08") "Sesongstart #35"]
           [listitem nil [sc/link {} "se mer i årshjulet"]]]]

         (when goog.DEBUG
           [schpaa.style.hoc.page-controlpanel/togglepanel :frontpage/master-panel "master-panel"
            booking.common-views/master-control-box])]]]

      [booking.common-views/after-content]

      [bs/attached-to-bottom
       {:class [(if at-least-registered? :bottom-toolbar) :z-20]}
       [scroll-up-animation]]]
     [:div.absolute.bottom-0.inset-x-0 [booking.common-views/bottom-tabbar]]]))
