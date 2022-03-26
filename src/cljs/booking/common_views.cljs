(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reitit.core :as reitit]
            [reagent.ratom]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog :refer [open-dialog-logoutcommand
                                         open-dialog-sampleautomessage
                                         open-dialog-config
                                         open-dialog-sampleformmessage]]
            [booking.content.booking-blog]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [db.core :as db]
            [schpaa.style.combobox]
            [booking.fileman]
            [booking.boatinput]
            [booking.mainmenu :refer [main-menu boatinput-menu boatinput-sidebar]]
            [booking.search :refer [search-menu]]
            [kee-frame.core :as k]
            [booking.routes]
            [schpaa.style.hoc.page-controlpanel :as hoc.panel]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons :refer [cta pill icon-with-caption icon-with-caption-and-badge]]
            [schpaa.debug :as l]))


(defn set-focus [el a]
  (when-not @a
    (tap> "set ref")
    (reset! a el)
    #_(when el (.focus el))))

;region number-input

(rf/reg-event-db :lab/toggle-number-input (fn [db]
                                            (update db :lab/number-input (fnil not false))))

(rf/reg-event-db :lab/open-number-input (fn [db]
                                          (assoc db :lab/number-input true)))

(rf/reg-event-db :lab/close-number-input (fn [db]
                                           (assoc db :lab/number-input false)))

(rf/reg-sub :lab/number-input :-> :lab/number-input)

;endregion

;region

(rf/reg-event-fx :app/toggle-config (fn [{db :db} _]
                                      (open-dialog-config)))

(rf/reg-event-fx :app/toggle-command-palette
                 (fn [{db :db} _]
                   (tap> :app/toggle-command-palette)
                   {:fx [[:dispatch [:lab/modal-selector
                                     (-> db :lab/modal-selector not)
                                     {:content-fn (fn [c] [schpaa.style.combobox/combobox-example c])}]]]}))

;endregion

(defn bottom-menu-definition [settings-atom]
  [[:header [sc/row' {:class [:justify-between :items-end :w-44 :px-2]}
             [sc/header-title "Booking"]
             [sc/pill (or "dev.3.12" booking.data/VERSION)]]]


   [:space]
   [:div [:div.px-2 [sc/small1 "Skrevet av meg for NRPK"]]]
   [:space]
   [:div [:div.px-2 [sc/row-center {:class [:py-4]}
                     [:div.relative.w-24.h-24
                      [:div.absolute.rounded-full.-inset-1.blur
                       {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                                :group-hover:-inset-1 :duration-500]}]
                      [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]]]
   [:footer [:div.p-2
             [sc/row-end {:class [:gap-1 :justify-end :items-center]}
              [sc/small1 "Vilkår"]
              [sc/small1 "&"]
              [sc/small1 "Betingelser"]]]]])

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

;region

(o/defstyled sidebar :div
  :cursor-pointer :m-0 :p-0 :w-8 :h-8 :grid
  {:place-content  :center
   :text-transform "lowercase"
   :line-height    1
   :font-family    "IBM Plex Sans"
   :font-size      "var(--font-size-5)"
   :font-weight    "var(--font-weight-5)"})

(defn vertical-toolbar
  "to add a badge (of unseen posts for instance):

  #(let [c (booking.content.booking-blog/count-unseen uid)]
    (when (pos? c) c))
  "
  [uid]
  [#_{:icon      outline/HomeIcon
      :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
      :page-name :r.forsiden}
   {:icon      solid/CalendarIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.calendar]])
    :page-name :r.calendar}
   {:icon      solid/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
    :page-name :r.booking.oversikt}
   {:icon      solid/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name :r.booking-blog}
   #_{:icon      solid/ShieldCheckIcon
      :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.retningslinjer]])
      :page-name :r.booking.retningslinjer}
   {:icon-fn   (fn [] [sidebar "n"])
    :on-click  #(rf/dispatch [:app/navigate-to [:r.aktivitetsliste]])
    :page-name :r.aktivitetsliste}
   nil
   {:tall-height true
    :icon        solid/FolderIcon
    :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
    :page-name   :r.fileman-temporary}])

(o/defstyled centered-thing :div
  :flex-center
  #_{:border-radius "var(--radius-round)"
     :padding       "var(--size-1)"
     :color         :white
     :background    "var(--brand1)"})

(o/defstyled centered-thing-red :div
  :flex-center
  {:border-radius "var(--radius-round)"
   :padding       "var(--size-2)"
   :color         :white
   :background    "var(--gray-4)"})

(defn vertical-toolbar-right [uid]
  (let [admin? (rf/subscribe [:lab/admin])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    [{:icon-fn   #(sc/icon-large ico/new-home)
      ;:special    true
      :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
      :page-name :r.forsiden}

     #_{:icon-fn   (fn [] (sc/icon-large ico/old-home))
        ;:special   true
        :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden-iframe]])
        :page-name :r.forsiden-iframe}

     {:icon-fn   (fn [] (sc/icon-large ico/user))
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :page-name :r.user}

     (when (and @member? @booking?)
       {:icon-fn   (fn [] (sc/icon-large ico/booking))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.booking]])
        :page-name :r.booking})



     (when (and @member? @nokkelvakt)
       {:icon-fn   (fn [] (sc/icon-large ico/nokkelvakt))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.nokkelvakt]])
        :page-name :r.nokkelvakt})

     #_{:icon-fn   (fn [] [sidebar "N"])
        :on-click  #(rf/dispatch [:app/navigate-to [:r.welcome]])
        :page-name :r.welcome}
     #_{:icon-fn   (fn [] [sidebar "S"])
        :on-click  #(rf/dispatch [:app/navigate-to [:r.booking]])
        :page-name :r.booking}
     (when (or @member? @admin?)
       {:icon-fn   (fn [] (sc/icon-large ico/yearwheel))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
        :page-name :r.yearwheel})

     :space
     {:icon-fn  (fn [] (let [st (rf/subscribe [:lab/number-input])]
                         [centered-thing
                          (sc/icon-large
                            [:> (if @st solid/ChevronRightIcon
                                        solid/ChevronLeftIcon)])]))
      :special  true
      :on-click #(rf/dispatch [:lab/toggle-number-input])}

     :space
     (when @admin?
       {:tall-height true
        :icon        solid/FolderIcon

        :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
        #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                      (when (pos? c) c))
        :page-name   :r.fileman-temporary})
     {:tall-height true
      :special     true
      :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-huge
                              [:> (if @st solid/InformationCircleIcon
                                          outline/InformationCircleIcon)])))
      :on-click    schpaa.style.dialog/open-selector}]))

(defn horizontal-toolbar [uid]
  [{:icon-fn  (fn [] (let [st (rf/subscribe [:lab/number-input])]
                       (if @st solid/CalculatorIcon outline/CalculatorIcon)))
    :special  true
    :on-click #(rf/dispatch [:lab/toggle-number-input])}

   {:icon      solid/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                        (if @st solid/LightningBoltIcon outline/LightningBoltIcon)))
    :special   true
    :on-click  schpaa.style.dialog/open-selector #_#(rf/dispatch [:app/navigate-to [:r.debug]])
    :page-name :r.debug}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(booking.content.booking-blog/count-unseen uid)
    :page-name :r.booking-blog}
   #_{:icon      solid/ClockIcon
      :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
      :page-name :r.booking.oversikt}
   {:icon      solid/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}])

(def active-style {:border-radius "var(--radius-round)"
                   :padding       "var(--size-2)"
                   :background    "var(--surface000)"})

(o/defstyled vert-button :div
  [#{:.active :.item} {:border-radius "var(--radius-round)"}]
  [:.item
   [:&:hover {:color       "var(--text2)"
              :padding     "var(--size-1)"
              :xbackground "var(--surface00)"}]]
  [:>.normal {:color "var(--text3)"}]

  [:>.active {:color      "var(--text1-copy)"
              :box-shadow "var(--shadow-3)"
              :background "var(--text1)"
              :padding    "var(--size-2)"}]
  ([attr & ch]
   [:div (conj attr {:class (if (:active attr) [:active] [:item :normal])}) ch]))

(o/defstyled top-left-badge :div
  :rounded-full :flex :flex-center
  [:& :-top-1 :right-0 {:font-size    "var(--font-size-0)"
                        :font-weight  "var(--font-weight-5)"
                        :aspect-ratio "1/1"
                        :background   "var(--brand1)"
                        :color        "var(--brand1-copy)"
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
                       :color        "var(--brand1-copy)"
                       :border       "var(--surface0) 3px solid"
                       :width        "1.7rem"
                       :height       "1.7rem"
                       :position     :absolute}]

  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))

(defn horizontal-button [{:keys [icon-fn special icon on-click style page-name badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-full.flex.items-center.justify-center.relative
     {:on-click on-click}
     (when badge
       (let [b (badge)]
         (when (pos? b) [top-right-tight-badge b])))
     [vert-button {:active (= page-name current-page)
                   :style  style}
      (if icon-fn
        [sc/icon-large
         {:style (if special {:color "var(--brand1)"})}
         [:> (icon-fn)]]
        [sc/icon-large
         {:style (if special {:color "var(--brand1)"})}
         [:> icon]])]]))

(defn vertical-button [{:keys [tall-height special icon icon-fn style on-click page-name color badge] :as m :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (= page-name current-page)]
    [:div.w-full.flex.items-center.justify-center.relative
     {:on-click on-click
      :style    {:height (if tall-height "var(--size-10)" "var(--size-9)")}}

     (when badge
       (when-some [b (badge)]
         [top-left-badge b]))
     [vert-button {:active active?
                   :style  (conj style (when (= page-name current-page) {:color color}))}
      (if icon-fn
        [:div
         {:style (if special {:color "var(--brand1)"})}
         (icon-fn)]
        [(if active? sc/icon sc/icon-large)
         {:style (if special {:color "var(--brand1)"})}
         [:> icon]])]]))

(defn lookup-page-ref-from-name [link]
  {:pre [(keyword? link)]}
  {:link link
   :text (or (some->> link
                      (reitit/match-by-name (reitit/router booking.routes/routes))
                      :data
                      :header)
             "wtf?")})

(comment
  (do
    (some-> (reitit/match-by-name (reitit/router booking.routes/routes) :r.forsiden)
            :data :header)))

(defn compute-page-titles [r]
  (let [path-fn (some-> r :data :path-fn)
        page-title (-> r :data :header)]
    (if (vector? page-title)
      (vec (remove nil? [(if (keyword? (first page-title))
                           (lookup-page-ref-from-name (first page-title))
                           (first page-title))
                         (or [:div.truncate (last page-title)] "no-title")
                         (if path-fn
                           [:div.truncate (path-fn r)])]))
      (or (if path-fn
            (path-fn r))
          (or page-title "no-title")))))

(comment
  (do
    (compute-page-titles {:data {:header ["stuff"]}})))

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

(defn header-line [r]
  [err-boundary
   (let [user-state (rf/subscribe [:lab/user-state])]
     [sc/col {:class [:border-b :duration-200]
              :style {:background   "var(--toolbar)"
                      :border-color "var(--toolbar-)"}}
      [:div
       #_[l/ppre-x {:lab/at-least-registered @(rf/subscribe [:lab/at-least-registered])
                    :lab/admin-access        @(rf/subscribe [:lab/admin-access])}]
       (when @(rf/subscribe [:lab/toggle-userstate-panel])
         [sc/surface-d {:class [:m-4]}
          (r/with-let [bookingx (rf/subscribe [:lab/booking])
                       nokkelvakt (rf/subscribe [:lab/nokkelvakt])
                       admin (rf/subscribe [:lab/admin])
                       st (r/atom {:admin      admin
                                   :nøkkelvakt nokkelvakt
                                   :booking    bookingx})]
            (let [registered (some #{(:status @user-state)} [:member])
                  status (:status @user-state)
                  f-icon (fn [action token content]
                           [pill
                            {:class    [:regular :pad-right (when (= token status) :outlined)]
                             :on-click #(rf/dispatch action)}
                            content])]
              [:div.relative
               [:div.absolute.top-1.right-1 {:style {:color "var(--text1)"}} (sc/icon-small {:on-click #(rf/dispatch [:lab/toggle-userstate-panel])} ico/closewindow)]
               ;[l/ppre-x @user-state bookingx]
               [sc/row-sc-g2-w

                (f-icon [:lab/set-sim-type :none] :none [icon-with-caption ico/anonymous "anonym"])
                (f-icon [:lab/set-sim-type :registered] :registered [icon-with-caption ico/user "registrert"])
                (f-icon [:lab/set-sim-type :waitinglist] :waitinglist [icon-with-caption-and-badge ico/waitinglist "venteliste" 123])
                (f-icon [:lab/set-sim-type :member] :member [icon-with-caption ico/member "medlem"])

                [schpaa.style.hoc.toggles/small-switch-base
                 {:disabled (not registered)}
                 "admin"
                 (r/cursor st [:admin])
                 #(do
                    (swap! st update-in [:admin] (constantly %))
                    (rf/dispatch [:lab/set-sim :admin %]))]

                [schpaa.style.hoc.toggles/small-switch-base
                 {:disabled (not registered)}
                 "booking"
                 (r/cursor st [:booking])
                 #(do
                    (swap! st update-in [:booking] (constantly %))
                    (rf/dispatch [:lab/set-sim :booking %]))]

                [schpaa.style.hoc.toggles/small-switch-base
                 {:disabled (not registered)}
                 "nøkkelvakt"
                 (r/cursor st [:nøkkelvakt])
                 #(do
                    (swap! st update-in [:nøkkelvakt] (constantly %))
                    (rf/dispatch [:lab/set-sim :nøkkelvakt %]))]]]))])

       [:div.h-16.flex.items-center.w-full.px-4.shrink-0.grow
        [sc/row-std
         (when-not @(rf/subscribe [:lab/in-search-mode?])
           (let [titles (compute-page-titles r)]
             [:<>
              [:div.hidden.sm:block.grow
               [:<>
                (if (vector? titles)
                  [:div.flex.items-center.gap-1
                   (interpose [:div.text-2xl.opacity-20 "/"]
                              (for [[idx e] (map-indexed vector titles)
                                    :let [last? (= idx (dec (count titles)))]]
                                (if last?
                                  [sc/text1 e]
                                  (let [{:keys [text link]} e]
                                    [sc/subtext-with-link {
                                                           :href (k/path-for [link])} text]))))]
                  [sc/text1 titles])]]
              [:div.xs:block.sm:hidden.grow
               (if (vector? titles)
                 [sc/col-space-1
                  (when (< 1 (count titles))
                    (let [{:keys [text link]} (first titles)]
                      [:div [sc/subtext-with-link {:style {:margin-left "-2px"}
                                                   :href  (k/path-for [link])} text]]))
                  [sc/text1 (last titles)]]
                 [sc/col
                  [sc/text1 titles]])]]))

         ;todo (if @(rf/subscribe [:lab/role-is-none]) ...)
         (if-not @(rf/subscribe [:lab/at-least-registered])
           [cta {:on-click #(rf/dispatch [:app/login])} "Logg inn & Registrer deg"]
           [search-menu])
         [main-menu]]]]])])

(defn page-boundary [r {:keys [frontpage] :as options} & contents]
  (let [user-auth (rf/subscribe [::db/user-auth])
        user-state (rf/subscribe [:lab/user-state])
        mobile? (= :mobile @(rf/subscribe [:breaking-point.core/screen]))
        numberinput? (rf/subscribe [:lab/number-input])
        has-chrome? (rf/subscribe [:lab/has-chrome])
        left-aligned (schpaa.state/listen :activitylist/left-aligned)]
    (r/create-class
      {:display-name "booking-page-boundary"

       :component-did-mount
       (fn [_]
         (when-let [el (.getElementById js/document "inner-document")]
           (.focus el)))

       :reagent-render
       (fn [r {:keys [frontpage] :as options} & contents]
         [err-boundary
          ;region modal dialog (top)
          [schpaa.style.dialog/modal-generic
           {:context @(rf/subscribe [:lab/modal-example-dialog2-extra])
            :vis     (rf/subscribe [:lab/modal-example-dialog2])
            :close   #(rf/dispatch [:lab/modal-example-dialog2 false])}]
          ;endregion

          ;region modal dialog (top)
          [schpaa.style.dialog/modaldialog-centered
           {:context @(rf/subscribe [:lab/modaldialog-context])
            :vis     (rf/subscribe [:lab/modaldialog-visible])
            :close   #(rf/dispatch [:lab/modaldialog-visible false])}]
          ;endregion

          ;region command-palette
          [schpaa.style.dialog/modal-selector
           {:context @(rf/subscribe [:lab/modal-selector-extra])
            :vis     (rf/subscribe [:lab/modal-selector])
            :close   #(rf/dispatch [:lab/modal-selector false])}]
          ;endregion
          [:div.fixed.inset-0.flex
           ;region vertical toolbar left side
           (when-not true                                   ;frontpage
             (when @has-chrome?
               [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.justify-around.items-center.flex-col.border-r
                {:style {:padding-top  "var(--size-0)"
                         :box-shadow   "var(--inner-shadow-3)"
                         :border-color "var(--surface0)"
                         :background   "var(--surface00)"}}
                (into [:<>] (map #(if (nil? %)
                                    [:div.grow]
                                    [vertical-button %]) (vertical-toolbar (:uid @user-auth))))]))
           ;endregion
           [:div.flex-col.flex.h-full.w-full
            (when-not frontpage
              [header-line r])

            ;region content
            (if frontpage
              [:div.h-full
               {:style     {:background "var(--content)"}
                :id        "inner-document"
                :tab-index "0"}
               contents]
              [:div.overflow-y-auto.h-full.focus:outline-none.grow
               {:style     {:background "var(--content)"}
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
                 (if (map? (first contents))
                   [:div.w-auto (:whole (first contents))]
                   [:div.h-full.xmx-4.w-full
                    contents
                    [:div.py-8.h-32]]))])
            ;endregion

            ;region horizontal toolbar on small screens
            (when (and @has-chrome? (some #{(:role @user-state)} [:member :waitinglist :registered]))
              [:div.h-20.w-full.sm:hidden.flex.justify-around.items-center.border-t.sticky.bottom-0
               {:style {:z-index      1000
                        :box-shadow   "var(--inner-shadow-3)"
                        :border-color "var(--toolbar-)"
                        :background   "var(--toolbar)"}}
               (into [:<>] (map horizontal-button
                                (horizontal-toolbar (:uid @user-auth))))])]
           ;endregion

           (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]) #_(some #{(:role @user-state)} [:member :waitinglist :registered]))
             [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.relative
              [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.border-l
               {:style {:z-index      1211
                        :padding-top  "var(--size-0)"
                        :box-shadow   (if @numberinput? "var(--shadow-5)"
                                                        "var(--inner-shadow-1)")
                        :border-color "var(--toolbar-)"
                        :background   "var(--toolbar)"}}
               (into [:<>] (map #(if (keyword? %)
                                   [:div.grow]
                                   [vertical-button %]) (remove nil? (vertical-toolbar-right (:uid @user-auth)))))]
              [:div.absolute.right-16.xl:right-20.inset-y-0
               {:style {:width          "298px"
                        :pointer-events :none}}
               [boatinput-menu]]])]])})))

(def max-width "50ch")

(defn matches-access "" [r [status access :as all-access-tokens]]
  (let [[req-status req-access :as req-tuple] (-> r :data :access)]
    (if req-tuple
      (and (= status req-status)
           (if req-access (some access req-access) true))
      true)))

(defn no-access-view [r]
  (let [required-access (-> r :data :access)]
    [:div
     {:style {:padding-top   "var(--size-10)"
              :width         "min-content"
              :margin-inline "auto"}}
     [sc/col-c-space-1
      [sc/hero {:style {:white-space :nowrap}} "Ikke åpent"]
      [sc/small1 "Du har --> " (str @(rf/subscribe [:lab/all-access-tokens]))]
      [sc/small1 "For å komme inn --> " (str required-access)]]]))

(defn +page-builder [r {:keys [frontpage render render-fullwidth panel always-panel]}]
  (let [pagename (some-> r :data :name)
        numberinput (rf/subscribe [:lab/number-input])
        left-aligned (schpaa.state/listen :activitylist/left-aligned)]
    (if frontpage
      [page-boundary r
       {:frontpage true}
       [render r]]
      [page-boundary r
       {:frontpage false}
       (let [have-access? (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens]))]
         (if-not have-access?
           [no-access-view r]
           [sc/col-space-8 {:class [:py-8]}
            (when panel
              [:div.mx-4
               [:div.mx-auto
                {:style {:width     "100%"
                         :max-width max-width}}
                [hoc.panel/togglepanel pagename "innstillinger" panel]]])

            (when always-panel
              [:div.mx-4
               [:div.mx-auto
                {:style {:width     "100%"
                         :max-width max-width}}
                [always-panel]]])
            [:div.mx-4
             [:div.duration-200
              {:style {:margin-right :auto
                       :margin-left  (if (and render-fullwidth @numberinput @left-aligned)
                                       ;; force view to align to the left
                                       0
                                       :auto)
                       :width        "100%"
                       :max-width    max-width}}
              (if render-fullwidth
                [render-fullwidth r]
                [render r])]]]))])))