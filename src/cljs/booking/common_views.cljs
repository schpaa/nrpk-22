(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reagent.ratom]

            [cljs.core.async :refer [chan take! put! >! <! timeout]]
            [cljs.core.async :refer-macros [go-loop go]]

            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog :refer [open-dialog-logoutcommand
                                         open-dialog-sampleautomessage
                                         open-dialog-config
                                         open-dialog-sampleformmessage]]
            [booking.content.booking-blog]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            ["react-qr-code" :default QRCode]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [schpaa.style.button2 :as scb2]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [db.core :as db]
            [goog.events.KeyCodes :as keycodes]
    ;[goog.events :as gevent]
            [times.api :as ta]
            [tick.core :as t]
            [schpaa.style.combobox]
            [re-pressed.core :as rp]
            [booking.fileman]
            [schpaa.icon :as icon]
            [booking.boatinput]))

(defn mainmenu-definition [settings-atom]
  (let [userauth (rf/subscribe [::db/user-auth])
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    ;[icon label action disabled value]
    [[:div [:div.p-2 [sc/title {:style {:font-family "Merriweather"
                                        :font-weight 300
                                        :font-size   "var(--font-size-4)"}} "Hei Chris!"]]]
     [:menuitem {:filledicon (sc/icon [:> solid/HomeIcon])

                 :label      "Forsiden"
                 :color      "var(--red-6)"
                 :highlight  (= :r.forsiden current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                 :disabled   false
                 :value      #()}]
     ;[:space]
     [:menuitem {:filledicon (sc/icon [:> solid/CalendarIcon])
                 :label      "Kalender"
                 :color      "var(--hva-er-nytt)"
                 :highlight  (= :r.calendar current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.calendar]])
                 :disabled   false
                 :value      #()}]
     [:menuitem {:filledicon (sc/icon [:> solid/BookOpenIcon])
                 :label      "Innlegg"
                 :color      "var(--hva-er-nytt)"
                 :highlight  (= :r.booking-blog current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
                 :disabled   false
                 :value      #()}]
     [:hr]
     [:menuitem {:filledicon [:div {:class [:-m-1]
                                    :style {:padding          "var(--size-1)"
                                            :border-radius    "var(--radius-round)"
                                            :background-color "var(--brand1)"
                                            :color            :white}} (sc/icon [:> outline/CalculatorIcon])]
                 :label      "Utlån"
                 :color      "var(--brand1)"
                 :shortcut   "ctrl-l"
                 :action     #(rf/dispatch [:lab/open-number-input])}]
     #_[:menuitem {:filledicon (sc/icon [:> solid/TicketIcon])
                   :label      "Ny booking"
                   :color      "var(--new-booking)"
                   :highlight  (= :r.debug current-page)
                   :action     #(rf/dispatch [:app/navigate-to [:r.debug]])
                   :disabled   false
                   :value      #()}]
     [:menuitem {:filledicon (sc/icon [:> solid/ClockIcon])
                 :label      "Båtoversikt"
                 :shortcut   "ctrl-b"
                 :color      "var(--booking-oversikt)"
                 :highlight  (= :r.oversikt current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.oversikt]])
                 :disabled   false
                 :value      #()}]


     #_[:menuitem {:filledicon (sc/icon [:> solid/MapIcon])
                   :label      "Turlogg"
                   :highlight  false
                   :action     nil
                   :disabled   true
                   :value      #()}]

     [:menuitem {:filledicon (sc/icon [:> solid/ShieldCheckIcon])
                 :label      "Retningslinjer"
                 :color      "var(--blue-4)"
                 :highlight  (= :r.retningslinjer current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.retningslinjer]])
                 :disabled   false
                 :value      #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> outline/UserCircleIcon])
                 :label     "Mine opplysninger"
                 :color     "var(--blue-4)"
                 :highlight (= :r.user current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.user]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon     (sc/icon [:> outline/CollectionIcon])
                 :label    "Kommandoer"
                 :color    "var(--brand1)"
                 :shortcut "ctrl-k"
                 :action   #(rf/dispatch [:app/toggle-command-palette])}]
     [:menuitem {:label  "Innstillinger"
                 :icon   (sc/icon [:> outline/CogIcon])
                 :action #(rf/dispatch [:app/toggle-config])}]
     [:menuitem {:label    "Logg ut"
                 :disabled true
                 :icon     (sc/icon [:> outline/LogoutIcon])
                 :action   #(rf/dispatch [:app/toggle-config])}]

     #_[:space]
     #_[:div
        [:<>
         ;[l/ppre (:uid @userauth)]
         [:div.flex.items-center.gap-2.justify-between
          (if-not @userauth
            [scb2/cta-small "Meld på"]
            [:div.grow])
          (if @userauth
            [scb2/normal-small "Logg ut"]
            [scb2/normal-small "Logg in"])]]]]))

(defn set-focus [el a]
  (when-not @a
    (tap> "set ref")
    (reset! a el)
    #_(when el (.focus el))))

;region search

(rf/reg-event-db :lab/set-search-expr (fn [db [_ search-expr]]
                                        (assoc db :lab/search-expr search-expr)))

(rf/reg-event-db :lab/set-search-mode (fn [db [_ searchmode?]]
                                        (assoc db :lab/set-search-mode searchmode?)))

(rf/reg-event-db :lab/toggle-search-mode (fn [db]
                                           (update db :lab/set-search-mode (fnil not false))))

(rf/reg-event-db :lab/start-search (fn [db]
                                     (assoc db :lab/run-search true)))

(rf/reg-event-db :lab/stop-search (fn [db]
                                    (assoc db :lab/run-search false)))

(rf/reg-sub :lab/is-search-running? :-> :lab/run-search)
(rf/reg-sub :lab/in-search-mode? :-> :lab/set-search-mode)
(rf/reg-sub :lab/search-expression :-> :lab/search-expr)

;region number-input

(rf/reg-event-db :lab/open-number-input (fn [db]
                                          (assoc db :lab/number-input true)))

(rf/reg-event-db :lab/close-number-input (fn [db]
                                           (assoc db :lab/number-input false)))

(rf/reg-sub :lab/number-input :-> :lab/number-input)

;endregion

(o/defstyled experiment :div
  [:&
   :rounded-full
   {:color "var(--surface4)"}
   [:&:hover {
              :background "var(--surface0)"}]])

(o/defstyled experiment2 :div
  [:&
   :rounded-full
   {:color "var(--surface4)"}
   [:&:hover {:color "var(--red-5)"}]])

(o/defstyled experiment3 :div
  [:&
   {:color "var(--surface4)"}])



(defn search-menu []
  (let [a (r/atom nil)
        value (rf/subscribe [:lab/search-expression])
        enter-search #(rf/dispatch [:lab/set-search-mode true])
        exit-search #(do
                       (reset! a nil)
                       (rf/dispatch [:lab/set-search-mode false])
                       (rf/dispatch [:lab/stop-search])
                       (rf/dispatch [:lab/set-search-expr ""]))
        set-refs #(when @a
                    (.addEventListener @a "keydown"
                                       (fn [event]
                                         (cond
                                           (= keycodes/ESC event.keyCode)
                                           (exit-search)
                                           (= keycodes/ENTER event.keyCode)
                                           (rf/dispatch [:lab/start-search])))))
        search (rf/subscribe [:lab/in-search-mode?])]

    (r/create-class
      {:display-name         "search-widget"

       :component-did-update (fn [_]
                               (when @a (.focus @a)))

       :reagent-render
       (fn []
         [:div.h-10.duration-200.rounded-full
          {:class (into [:flex :gap-1]
                        [(if @search :bg-white)
                         (if @search :px-x1)
                         (if @search :w-full :w-10)])}

          (if @search
            [experiment3
             [sc/row' {:class    [:flex-center :w-10 :shrink-1]
                       :on-click #(.stopPropagation %)}
              [sc/icon [:> solid/SearchIcon]]]]
            [experiment
             [sc/row' (conj
                        {:class [:flex-center :w-10 :shrink-1]}
                        (when-not @search
                          {:on-click enter-search}))
              [sc/icon [:> solid/SearchIcon]]]])

          (when @search
            [:input.w-full.h-full.outline-none.focus:outline-none
             {:style       {:xflex "1 1 100%"}
              :placeholder "finn ressurser + <return>"
              :type        :text
              :on-blur     #(let [s (-> % .-target .-value)]
                              (if (empty? s)
                                (exit-search)))
              :ref         #(when-not @a
                              ;(set-focus % a)
                              (reset! a %)
                              (set-refs))}])

          (when @search
            [experiment2
             [sc/row' {:class    [:flex-center :w-10]
                       :on-click exit-search}
              [sc/icon [:> solid/XIcon]]]])])})))


;endregion









(defn main-menu []
  (r/with-let [mainmenu-visible (r/atom false)
               numberinput-visible (rf/subscribe [:lab/number-input])]
    (let [toggle-mainmenu #(swap! mainmenu-visible (fnil not false))
          toggle-numberinput #(rf/dispatch [:lab/close-number-input])]
      [:<>
       ;[l/ppre-x @numberinput-visible]
       (if @numberinput-visible
         [scm/numberinput
          {:showing      true
           :dir          #{:right :down}
           :close-button (fn [open] [scb/corner {:on-click toggle-numberinput} [sc/icon (icon/adapt :arrow-right-up)]])
           :data         (booking.boatinput/sample)
           :button       (fn [open]
                           [scb/round-normal {:class [:h-10] :on-click toggle-numberinput}
                            [sc/icon [:> solid/MenuIcon]]])}]
         [scm/mainmenu-example-with-args
          {:showing      @mainmenu-visible
           :dir          #{:right :down}
           :close-button (fn [open] [scb/corner {:on-click toggle-mainmenu} [sc/icon [:> solid/XIcon]]])
           :data         (mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
           :button       (fn [open]
                           [scb/round-normal {:class [:h-10] :on-click toggle-mainmenu}
                            [sc/icon [:> solid/MenuIcon]]])}])])))

(defn bottom-menu-definition [settings-atom]
  [[:header [sc/row' {:class [:justify-between :items-end :w-44 :px-2]}
             [sc/header-title "Booking"]
             [sc/pill (or "dev.3.12" booking.data/VERSION)]]]


   [:space]
   [:div [:div.px-2 [sc/small "Skrevet av meg for NRPK"]]]
   [:space]
   [:div [:div.px-2 [sc/row-center {:class [:py-4]}
                     [:div.relative.w-24.h-24
                      [:div.absolute.rounded-full.-inset-1.blur
                       {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                                :group-hover:-inset-1 :duration-500]}]
                      [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]]]
   [:footer [:div.p-2
             [sc/row-end {:class [:gap-1 :justify-end :items-center]}
              [sc/small "Vilkår"]
              [sc/small "&"]
              [sc/small "Betingelser"]]]]])

(defn bottom-menu []
  (r/with-let [main-visible (r/atom false)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/mainmenu-example-with-args
       {:close-button #()
        :showing      @main-visible
        :dir          #{:up :right}
        :data         (bottom-menu-definition (r/atom nil))
        :button       (fn [open]
                        [scb/round-mainpage {:on-click toggle-mainmenu} [sc/corner-symbol "?"]])}])))

;-[ ] todo: [:space] for extra space
(defn vertical-toolbar [uid]
  [{:icon      solid/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      solid/CalendarIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.calendar]])
    #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name :r.calendar}
   #_{:icon      solid/UserCircleIcon
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :page-name :r.user}
   {:icon      solid/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.oversikt]])
    :page-name :r.oversikt}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name :r.booking-blog}
   {:icon      solid/ShieldCheckIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.retningslinjer]])
    #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name :r.retningslinjer}
   nil
   {:tall-height true
    :icon        solid/FolderIcon
    :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
    #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name   :r.fileman-temporary}

   {:tall-height true
    :special     true
    :icon        solid/LightningBoltIcon
    :on-click    schpaa.style.dialog/open-selector
    ;:color     "red"
    :xpage-name  :r.debug}])

(defn horizontal-toolbar [uid]
  [{:icon      solid/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      solid/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      solid/LightningBoltIcon
    :special   true
    :on-click  schpaa.style.dialog/open-selector #_#(rf/dispatch [:app/navigate-to [:r.debug]])
    :page-name :r.debug}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(booking.content.booking-blog/count-unseen uid)
    :page-name :r.booking-blog}
   {:icon      solid/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.oversikt]])
    :page-name :r.oversikt}])

(def active-style {:border-radius "var(--radius-round)"
                   :padding       "var(--size-2)"
                   :background    "var(--surface000)"})

(o/defstyled vert-button :div
  [#{:.active :.item} {:border-radius "var(--radius-round)"}]
  [:.item
   [:&:hover {:color      "var(--surface3)"
              :padding    "var(--size-1)"
              :background "var(--surface00)"}]]
  [:>.normal {:color "var(--surface2)"}]

  [:>.active {:color      "var(--surface000)"
              :box-shadow "var(--shadow-3)"
              :background "var(--surface3)"
              :padding    "var(--size-2)"}]
  ([attr & ch]
   [:div (conj attr {:class (if (:active attr) [:active] [:item :normal])}) ch]))

(o/defstyled top-left-badge :div
  :rounded-full :flex :flex-center
  [:& :-top-1 :right-0 {:font-size    "var(--font-size-0)"
                        :font-weight  "var(--font-weight-5)"
                        :aspect-ratio "1/1"
                        :background   "var(--brand1)"
                        :color        "var(--brand0)"
                        :border       "var(--surface0) 3px solid"
                        :width        "1.7rem"
                        :height       "1.7rem"
                        :position     :absolute}]

  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))

(o/defstyled top-right-tight-badge :div
  :rounded-full :flex :flex-center
  [:& :top-0 :right-4 {:font-size    "var(--font-size-0)"
                       :font-weight  "var(--font-weight-5)"
                       :aspect-ratio "1/1"
                       :background   "var(--brand1)"
                       :color        "var(--brand0)"
                       :border       "var(--surface0) 3px solid"
                       :width        "1.7rem"
                       :height       "1.7rem"
                       :position     :absolute}]

  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))

(defn horizontal-button [{:keys [special icon on-click style page-name badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-full.flex.items-center.justify-center.relative
     {:on-click on-click}
     (when badge
       (let [b (badge)]
         (when (pos? b) [top-right-tight-badge b])))
     [vert-button {:active (= page-name current-page)
                   :style  style}
      [sc/icon-large {:style (if special {:color "var(--brand1)"})} [:> icon]]]]))

(defn vertical-button [{:keys [tall-height special icon style on-click page-name color badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.flex.items-center.justify-center.relative
     {:on-click on-click
      :style    {:height      (if tall-height "var(--size-10)" "var(--size-9)")
                 :xbackground (if special "var(--surface1)")}}
     (when badge
       (when-some [b (badge)]
         [top-left-badge b]))
     [vert-button {:active (= page-name current-page)
                   :style  (conj style (when (= page-name current-page) {:color color}))}
      [sc/icon-large {:style (if special {:color "var(--brand1)"})} [:> icon]]]]))

(defn compute-page-title [r]
  (let [path-fn (some-> r :data :path-fn)
        page-title (-> r :data :header)]
    (remove nil? [(or page-title "no-title")
                  (if path-fn
                    (path-fn r))])))

(o/defstyled result-item :div
  {:display        :flex
   :align-items    :center
   :min-width      "12rem"
   :padding-inline "var(--size-4)"
   :padding-block  "var(--size-3)"
   :background     "var(--surface000)"
   :color          "var(--text1)"}
  [:&:hover {:background "var(--surface0)"}])

(defn search-result []
  (let [data (range 12)]
    (into [:div.gap-px.grid.w-full
           {:style {:grid-template-columns "repeat(auto-fill,minmax(24rem,1fr))"}}]
          (for [e data]
            [result-item e]))))

(o/defstyled qr-code :div
  ;:space-y-4 :flex :justify-center :flex-col :items-center
  ;:border-2 :border-gray-500 :p-4 :rounded-sm
  [:>a :w-full :px-4 {:overflow      :hidden
                      :text-overflow :ellipsis
                      :white-space   :nowrap}]
  ([active-item]
   (let [addr active-item                                   ;(kee-frame.core/path-for [:r.topic {:id active-item}])
         path (str (.-protocol js/window.location) "//" (.-host js/window.location) addr)]
     [:<>
      [:> QRCode {:title "Whatever"
                  :size  128
                  :level "Q"
                  :value path}]

      ;[:div addr]
      ;[:div (.-host js/window.location)]
      ;[:div (.-protocol js/window.location)]
      #_[:a {:href path} path]])))

(defonce state (r/atom {:a false}))
(defonce ctrl (reagent.ratom/make-reaction #(or (get-in @state [:a]) "---")))

(defn handle-fx [event]
  ;(tap> {:event event})
  (swap! state update :a (fnil not false)))

(defonce _
         (do
           (def ch (chan))
           (go-loop []
                    (let [event (<! ch)]
                      (handle-fx event)
                      (recur)))
           #_(go-loop []
                      (<! (timeout 1000))
                      (>! ch 3)
                      (<! (timeout 2000))
                      (>! ch 2)
                      (recur))))

(def preferred-state (reagent.ratom/make-reaction #(= true (get @state :a))))

(defn chevron-updown-toggle [st toggle]
  [sc/dim [scb/round-floating
           {:on-click #(do
                         (.stopPropagation %)
                         (toggle))}
           [sc/icon-tiny
            [:> (if st outline/ChevronUpIcon outline/ChevronDownIcon)]]]])

(defn header-control-panel []
  (r/with-let [do-toggle #(put! ch :signal (fn [ev] (tap> ["just did" ev])))
               show-archived (r/atom false)]
    [sc/col {:style {:padding-block    "var(--size-1)"
                     :padding-inline   "var(--size-2)"
                     ;:border-radius    "var(--radius-1)"
                     ;:box-shadow       (when @preferred-state "var(--inner-shadow-2)")
                     :background-color (when @preferred-state "var(--surface000)")}}
     [sc/row'                                               ;:div.flex.justify-between.items-center
      {:on-click #(do
                    (do-toggle)
                    (.stopPropagation %))
       :class    [:x-debug]
       :style    {:padding "var(--size-1)"}}
      [sc/dim [sc/small {:style {;:font-family    "montserrat"
                                 :letter-spacing "var(--font-letterspacing-2)"
                                 :text-transform :uppercase
                                 :font-weight    "var(--font-weight-6)"}} "Visningsvalg"]]
      (chevron-updown-toggle @preferred-state do-toggle)]
     [:div {:class (into [:duration-200] (if @preferred-state [:h-24 :opacity-100] [:pointer-events-none :h-0 :opacity-0]))}
      [sc/col-space-2

       [sc/col {:class [:space-y-2]}
        #_[sc/row' {:class [:items-center]}
           [schpaa.style.switch/small-switch-example
            {:!value  show-archived
             :caption [sc/subtext "Vis arkiverte"]}]]

        [sc/row' {:class [:items-center]}
         [schpaa.style.switch/small-switch-example
          {:!value  show-archived
           :caption [sc/subtext "Vis skjulte"]}]]]
       [sc/dim
        [sc/row-stretch
         [sc/subtext "Se innhold"]
         [sc/subtext "Se slettede"]
         [sc/subtext "Se skjulte"]]]]]
     ;[sc/subtext "Som en liste"]
     ;[sc/subtext "Som blokker i et rutenett"]
     ;[sc/subtext "Alle poster ekspandert"]]]
     #_[sc/markdown [:code "Some options here"]]
     (when @preferred-state [:hr])]))

(defn page-boundary [r & c]
  (let [user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (r/create-class
      {:component-did-mount
       (fn []
         (tap> :component-did-mount)
         (.focus (.getElementById js/document "inner-document")))
       :reagent-render
       (fn [r & c]
         (let [page-title (-> r :data :header)]

           [err-boundary

            ;region modal dialog
            [schpaa.style.dialog/modal-generic
             {:context @(rf/subscribe [:lab/modal-example-dialog2-extra])
              :vis     (rf/subscribe [:lab/modal-example-dialog2])
              :close   #(rf/dispatch [:lab/modal-example-dialog2 false])}]
            ;endregion

            ;region add-selector
            [schpaa.style.dialog/modal-selector
             {:context @(rf/subscribe [:lab/modal-selector-extra])
              :vis     (rf/subscribe [:lab/modal-selector])
              :close   #(rf/dispatch [:lab/modal-selector false])}]
            ;endregion

            [:div.fixed.inset-0.flex

             ;vertical toolbar
             (when @has-chrome?
               [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.justify-around.items-center.flex-col.border-r
                {:style {:padding-top  "var(--size-0)"
                         :box-shadow   "var(--inner-shadow-3)"
                         :border-color "var(--surface1)"
                         :background   "var(--surface0)"}}
                (into [:<>] (map #(if (nil? %)
                                    [:div.grow]
                                    [vertical-button %]) (vertical-toolbar (:uid @user-auth))))])
             [:div.flex-col.flex.h-full.w-full

              ;header
              (let [st (schpaa.state/listen :top-toggle)
                    toggle #(schpaa.state/toggle :top-toggle)]
                [sc/col {:class [:border-b :duration-200]
                         :style {:background   "var(--surface00)"
                                 :border-color "var(--surface0)"}}
                 [:div.h-16.flex.items-center.w-full.px-4.shrink-0.truncates
                  [sc/row-std
                   (when-not @(rf/subscribe [:lab/in-search-mode?])
                     [sc/row' {:class [:grow :gap-4]}
                      [sc/header-title {:class [:truncate]}
                       [sc/row' {:class [:truncate]}
                        (interpose [:div.px-2.truncate "/"] (for [e (compute-page-title r)] [:div.truncate e]))]]])
                   [search-menu]
                   [main-menu]]]])


              [:div.overflow-y-auto.h-full.focus:outline-none
               {:style     {:background "var(--surface000)"}
                :id        "inner-document"
                :tab-index "0"}
               (if @(rf/subscribe [:lab/is-search-running?])
                 ;searchmode
                 [:div.h-full
                  {:style {:background "var(--surface2)"}}
                  [search-result]
                  [:div.absolute.bottom-24.sm:bottom-7.right-4
                   [sc/row-end {:class [:pt-4]}
                    (bottom-menu)]]]
                 ;content

                 [:div
                  (cond
                    (map? (first c))
                    (:whole (first c))
                    :else
                    [:div.h-full
                     [:div.max-w-md.mx-auto.py-4.space-y-4.px-4
                      ;[:div.-mx-2x.py-2 [header-control-panel]]
                      c
                      [:div.py-8.h-32]
                      [:div.absolute.right-4
                       {:class (if @has-chrome? [:bottom-24 :sm:bottom-7] [:bottom-7])}
                       [sc/row-end {:class [:pt-4]}
                        (bottom-menu)]]]])])]

              ;horizontal toolbar
              (when @has-chrome?
                [:div.h-20.w-full.sm:hidden.flex.justify-around.items-center.border-t
                 {:style {:box-shadow   "var(--inner-shadow-3)"
                          :border-color "var(--surface1)"
                          :background   "var(--surface0)"}}
                 (into [:<>] (map horizontal-button
                                  (horizontal-toolbar (:uid @user-auth))))])]]]))})))

(comment
  (defonce keydown-ch (chan))
  (defonce _d (gevent/listen js/document "keydown"

                             #(do
                                (put! keydown-ch (.-key %))
                                (set! (.-cancelBubble %) true)
                                (.stopImmediatePropagation %)
                                false)))

  (defonce keyup-ch (chan))
  (defonce _c (gevent/listen js/document "keyup"
                             #(do (put! keyup-ch (.-key %))
                                  (set! (.-cancelBubble %) true)
                                  (.stopImmediatePropagation %)
                                  false)))

  (def is-modifier? #{"Control" "Meta" "Alt" "Shift"})

  (defonce _a (do
                (def chord-ch (chan))
                (go-loop [modifiers []
                          pressed nil]
                         (when (and (seq modifiers) pressed)
                           (>! chord-ch (conj modifiers pressed)))
                         (let [[key ch] (alts! [keydown-ch keyup-ch])]
                           (condp = ch
                             keydown-ch (if (is-modifier? key)
                                          (recur (conj modifiers key) pressed)
                                          (recur modifiers key))
                             keyup-ch (if (is-modifier? key)
                                        (recur (filterv #(not= % key) modifiers)
                                               pressed)
                                        (recur modifiers nil)))))))

  (defonce _b (go-loop []
                       (let [chord (<! chord-ch)]
                         (if (= chord ["Meta" "k"])
                           (tap> "COMMAND KE")
                           (tap> chord))
                         (recur)))))

;region :app/toggle-config

(rf/reg-event-fx :app/toggle-config (fn [{db :db} _]
                                      (tap> :app/toggle-config)
                                      (open-dialog-config)))

(defonce _x (do
              (rf/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])

              (rf/reg-event-fx :app/toggle-command-palette
                               (fn [{db :db} _]
                                 (tap> :app/toggle-command-palette)
                                 {:fx [[:dispatch [:lab/modal-selector
                                                   (-> db :lab/modal-selector not)
                                                   {:content-fn (fn [c] [schpaa.style.combobox/combobox-example c])}]]]}))

              (rf/dispatch
                [::rp/set-keydown-rules
                 {:event-keys [[[:app/toggle-command-palette]
                                [{:metaKey true
                                  :keyCode keycodes/K}]]
                               [[:app/toggle-command-palette]
                                [{:ctrlKey true
                                  :keyCode keycodes/K}]]
                               [[:lab/toggle-search-mode]
                                [{:metaKey true
                                  :keyCode keycodes/F}]]
                               [[:lab/toggle-search-mode]
                                [{:ctrlKey true
                                  :keyCode keycodes/F}]]]

                  :prevent-default-keys
                  [{:ctrlKey true
                    :keyCode keycodes/K}
                   {:metaKey true
                    :keyCode keycodes/F}
                   {:metaKey true
                    :keyCode keycodes/K}
                   {:keyCode 71
                    :ctrlKey true}]}])))
