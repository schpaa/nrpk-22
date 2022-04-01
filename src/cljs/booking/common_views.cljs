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
            [schpaa.debug :as l]
            [booking.data]))


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
  [{:icon      ico/new-home
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}

   {:icon      ico/user
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}

   {:icon-fn  (fn [] (let [st (rf/subscribe [:lab/number-input])]
                       [:> (if @st solid/TicketIcon outline/TicketIcon)]))
    :special  false
    :on-click #(rf/dispatch [:lab/toggle-number-input])}

   {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/number-input])]
                        [:> (if @st solid/ClockIcon outline/ClockIcon)]))
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
    :page-name :r.booking.oversikt}

   {:icon     ico/yearwheel
    :special  false
    :on-click #(rf/dispatch [:lab/toggle-number-input])}

   {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                        [:> (if @st solid/InformationCircleIcon outline/InformationCircleIcon)]))
    :special   true
    :on-click  schpaa.style.dialog/open-selector
    :page-name :r.debug}])

(o/defstyled vert-button :div
  [:&
   {:color         "var(--text2)"
    :border-radius "var(--radius-round)"
    :padding       "var(--size-1)"}
   [:&:hover {:color   "var(--text1)"
              :padding "var(--size-1)"}]
   [:&.active {:color      "var(--text0)"
               :box-shadow "var(--shadow-3)"
               :background "var(--text1-copy)"
               :padding    "var(--size-2)"}]
   [:&.special {:color "var(--brand1)"}]])

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
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (= page-name current-page)]
    [:div.w-full.h-full.flex.items-center.justify-center.relative
     {:on-click on-click}
     (when badge
       (let [b (badge)]
         (when (pos? b) [top-right-tight-badge b])))
     [vert-button
      {:style style
       :class [(if active? :active)
               (if special :special)]}
      [sc/icon-large
       (if icon-fn (icon-fn) icon)]]]))

(defn vertical-button [{:keys [tall-height special icon icon-fn style on-click page-name color badge] :as m :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (= page-name current-page)]
    [:div.w-full.flex.items-center.justify-center.relative
     {:on-click on-click
      :style    {:height (if tall-height "var(--size-10)" "var(--size-9)")}}

     (when badge
       (when-some [b (badge)]
         [top-left-badge b]))

     [vert-button {:style style
                   :class [(if active? :active)
                           (if special :special)]}
      [sc/icon-large
       (if icon-fn (icon-fn) icon)]]]))

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
   :background     "var(--content)"
   :color          "var(--text1)"}
  [:&:hover {:background "var(--toolbar-)"}])

(defn search-result []
  (let [data (range 12)]
    [:div.pt-px                                             ;.min-h-screen
     (into [:div.gap-px.grid.w-full
            {:style {:grid-template-columns "repeat(auto-fill,minmax(24rem,1fr))"}}]
           (for [e data]
             [result-item e]))]))

(o/defstyled header-top-frontpage :div
  :flex :items-center :w-full :px-4 :gap-2
  {;:background "var(--content)"
   ;:background "transparent"
   :height "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

(o/defstyled header-top :div
  :flex :items-center :w-full :px-4 :gap-2
  {:background "var(--content)"
   ;:background "transparent"
   :height     "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

(defn master-control-box []
  (let [user-state (rf/subscribe [:lab/user-state])]
    (when true                                              ;@(rf/subscribe [:lab/toggle-userstate-panel])
      [:div
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
                 (rf/dispatch [:lab/set-sim :nøkkelvakt %]))]]]))])))

(defn header-line [r {:keys [frontpage]}]
  [err-boundary
   (let [user-state (rf/subscribe [:lab/user-state])]
     [sc/col
      [:div
       {:style (conj
                 {:border-bottom "1px solid var(--toolbar)"}
                 (when-not frontpage {:background "var(--toolbar)"}))}
       [(if frontpage header-top-frontpage header-top)      ;{:class [:-debug2]}
        (when goog.DEBUG
          [schpaa.style.hoc.toggles/large-toggle :lab/master-state-emulation])

        ;todo (if @(rf/subscribe [:lab/role-is-none]) ...)
        [:div.flex.flex-col
         (when-not @(rf/subscribe [:lab/in-search-mode?])
           (let [titles (compute-page-titles r)]
             [:div.truncate
              [:div.hidden.sm:block.grow
               (if (vector? titles)
                 [sc/row-sc-g2
                  (interpose [sc/text1 {:style {:font-size "var(--font-size-4)"}} "/"]
                             (for [[idx e] (map-indexed vector titles)
                                   :let [last? (= idx (dec (count titles)))]]
                               (if last?
                                 [sc/text1 {:style {:font-weight "var(--font-weight-6)"}} e]
                                 (let [{:keys [text link]} e]
                                   [sc/subtext-with-link {:style {:-background "var(--content)"}
                                                          :href  (k/path-for [link])} text]))))]
                 [sc/text1 titles])]
              [:div.xs:block.sm:hidden.grow
               (if (vector? titles)
                 [sc/col-space-1                            ;{:class [:truncate]}
                  (when (< 1 (count titles))
                    (let [{:keys [text link]} (first titles)]
                      [:div [sc/subtext-with-link {:style {:-background "var(--content)"
                                                           :margin-left "-2px"}
                                                   :href  (k/path-for [link])} text]]))
                  [sc/text1 {:style {:font-weight "var(--font-weight-6)"}} (last titles)]]
                 [sc/col
                  [sc/text1 titles]])]]))]
        ;[:div.grow]
        (if frontpage
          [:div.grow]
          (if-not @(rf/subscribe [:lab/at-least-registered])
            [:<>
             [:div.grow]
             [cta {:-style   {:padding-block  "var(--size-1)"
                              :padding-inline "var(--size-2)"
                              ;:min-width      "0rem"
                              :width          "min-content"
                              ;:font-weight    "var(--font-weight-6)"
                              :box-shadow     "var(--shadow-1)"}
                   :-class   [:flex :flex-wrap :items-center :gap-x-1 :h-12 :space-y-0 :shrink-0]
                   :on-click #(rf/dispatch [:app/login])}
              "Logg inn"
              #_[:div.whitespace-nowrap "& Registrer deg"]]]
            [:div.grow.flex.justify-end.items-center [search-menu]]))
        [main-menu]
        [schpaa.style.hoc.toggles/dark-light-toggle :app/dark-mode ""]]]])])

(defn right-tabbar []
  (let [numberinput? (rf/subscribe [:lab/number-input])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.relative
       [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col
        {:style {:z-index     1211
                 :padding-top "var(--size-0)"
                 :box-shadow  (if @numberinput? "var(--shadow-5)" "none")
                 :background  "var(--toolbar)"}}
        (into [:<>] (map #(if (keyword? %)
                            [:div.grow]
                            [vertical-button %]) (remove nil? (vertical-toolbar-right (:uid @user-auth)))))]
       [:div.absolute.right-16.xl:right-20.inset-y-0
        {:style {:width          "298px"
                 :pointer-events :none}}
        [boatinput-menu]]])))

(defn bottom-tabbar []
  (let [search (rf/subscribe [:lab/is-search-running?])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? (not @search) @(rf/subscribe [:lab/at-least-registered]))
      [:div.sm:hidden.flex.border-t
       {:style {;:position        :sticky
                :width           "100%"
                :justify-content :space-around
                :height          "var(--size-10)"
                :min-height      "var(--size-10)"
                :padding-inline  "var(--size-2)"
                ;:bottom          "0px"
                ;:z-index         10000
                :box-shadow      "var(--inner-shadow-3)"
                :border-color    "var(--toolbar-)"
                :background      "var(--toolbar)"}}
       (into [:<>] (map horizontal-button
                        (horizontal-toolbar (:uid @user-auth))))])))

(o/defstyled mainframe :div)

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])]
    [:div.z-1 {:style {:background "var(--gray-10)"}}
     [:div.mx-auto.max-w-lg.py-8
      [:div.text-white.mx-4
       [sc/col-space-2
        [sc/col-space-1
         [sc/text1-cl {:style {:font-weight "var(--font-weight-6)"}} "Postadresse"]
         [sc/text1-cl "Postboks 37, 0621 Bogerud"]
         [sc/text1-cl "Nøklevann ro- og padleklubb"]
         [:div.flex.justify-start.flex-wrap.gap-2
          [sc/subtext-with-link {:class [:dark]} "styret@nrpk.no"]
          [sc/subtext-with-link {:class [:dark]} "medlem@nrpk.no"]]]
        [sc/row-ec
         [hoc.buttons/reg-pill-icon
          {:on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
          ico/tilbakemelding "Tilbakemelding?"]]]]]]))

(defn page-boundary [r {:keys [frontpage] :as options} & contents]
  (let [user-auth (rf/subscribe [::db/user-auth])
        user-state (rf/subscribe [:lab/user-state])
        mobile? (= :mobile @(rf/subscribe [:breaking-point.core/screen]))
        numberinput? (rf/subscribe [:lab/number-input])
        has-chrome? (rf/subscribe [:lab/has-chrome])
        left-aligned (schpaa.state/listen :activitylist/left-aligned)
        a (r/atom nil)]
    (r/create-class
      {:display-name        "page-boundary"

       :component-did-mount (fn [_]
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
           [:div.flex.flex-col
            {:style {:flex "1 1 auto"}}
            (when-not frontpage
              [header-line r {:frontpage frontpage}])
            [:div.flex.flex-col.overflow-y-auto.h-full
             (if (and
                   @(rf/subscribe [:lab/is-search-running?])
                   @(rf/subscribe [:lab/in-search-mode?]))
               [:div
                {:style {:flex "1 0 auto"}}
                [:div
                 {:style {:background "var(--toolbar)"}}
                 [search-result]]
                [sc/row-center {:class [:py-4]}
                 [hoc.buttons/regular {:on-click #(do
                                                    ;(rf/dispatch [:lab/stop-search])
                                                    ;(rf/dispatch [:lab/set-search-mode false])
                                                    ;(rf/dispatch [:lab/set-search-expr ""])
                                                    (rf/dispatch [:lab/quit-search]))} "Lukk"]]
                [:div.absolute.bottom-8.sm:bottom-7.right-4
                 [sc/row-end {:class [:pt-4]}
                  (bottom-menu)]]]
               [:<>
                [:div
                 {:style {;:min-height "100%"
                          :background-color "var(--content)"
                          :flex             "1 1 auto"}}
                 contents]
                (when-not frontpage [after-content])])]

            [bottom-tabbar]]
           [right-tabbar]]])})))

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
     {:style {:min-height    "100vh"
              :padding-top   "var(--size-10)"
              :width         "min-content"
              :margin-inline "auto"}}
     [sc/col-c-space-2
      [sc/hero {:style {:white-space :nowrap
                        :color       "var(--text2)"}} "Beskyttet område"]
      [sc/icon {:style {:color "var(--text2)"}} ico/stengt]
      [sc/small1 {:style {:white-space :nowrap}} "Du har --> " (str @(rf/subscribe [:lab/all-access-tokens]))]
      [sc/small1 {:style {:white-space :nowrap}} "For å komme inn --> " (str required-access)]]]))

(rf/reg-sub :lab/we-know-how-to-scroll? :-> :lab/we-know-how-to-scroll)

(rf/reg-event-db :lab/we-know-how-to-scroll (fn [db [_ arg]] (assoc db :lab/we-know-how-to-scroll arg)))

(defn set-ref [a scroll-fn]
  (fn [el]
    (when-not @a
      ;(tap> ["addrefxxx" el])
      (.addEventListener el "scroll" scroll-fn)
      (reset! a el))))

(defn +page-builder [r m]
  (let [scrollpos (r/atom 0)
        scroll-fn (fn [e]
                    (let [v (-> e .-target .-scrollTop)]
                      (if (< 50 v)
                        (rf/dispatch [:lab/we-know-how-to-scroll true]))
                      (reset! scrollpos v)
                      (tap> ["scroll" (-> e .-target .-scrollTop)])))
        a (r/atom nil)]
    (r/create-class
      {:display-name
       "+page-builder"

       :component-will-unmount
       (fn [_]
         (when @a
           (.removeEventListener @a "scroll" scroll-fn)))

       :component-did-mount
       (fn [_]
         (tap> :component-did-mount)
         (rf/dispatch [:lab/we-know-how-to-scroll false]))

       :reagent-render
       (fn [r {:keys [frontpage render render-fullwidth panel always-panel]}]
         [err-boundary
          (let [pagename (some-> r :data :name)
                numberinput (rf/subscribe [:lab/number-input])
                left-aligned (schpaa.state/listen :activitylist/left-aligned)]
            (if frontpage
              [page-boundary r
               {:frontpage true}
               (into [:div.overflow-y-auto.h-full
                      {:key "key-one"
                       :ref (set-ref a scroll-fn)}]
                     [(let [v (- 1 (/ (- (+ @scrollpos 0.001) 30) 70))]
                        [:div.sticky.top-0.z-10.relative
                         {:style {:opacity v}}
                         [booking.common-views/header-line r {:frontpage true}]])
                      [:div.-mt-16
                       [render r]]])]

              [page-boundary r
               {:frontpage false}
               (let [have-access? (booking.common-views/matches-access r @(rf/subscribe [:lab/all-access-tokens]))]
                 (if-not have-access?
                   [:<>
                    [no-access-view r]
                    [after-content]]
                   [:<>
                    [sc/col-space-8
                     {:class [:py-8]
                      :style {:flex             "1 1 auto"
                              :background-color "var(--content)"}}

                     (when (fn? panel)
                       (when-some [p (panel)]
                         [:div.mx-4
                          [:div.mx-auto
                           {:style {:width     "100%"
                                    :max-width max-width}}
                           [hoc.panel/togglepanel pagename "innstillinger" panel]]]))
                     (when always-panel
                       [:div.mx-4
                        [:div.mx-auto
                         {:style {:width     "100%"
                                  :max-width max-width}}
                         [always-panel]]])
                     [:div.duration-200.grow
                      {:style {:margin-right :auto

                               :margin-left  (if (and render-fullwidth @numberinput @left-aligned)
                                               ;; force view to align to the left
                                               0
                                               :auto)
                               :width        "100%"
                               :max-width    max-width}}
                      [:div.mx-4.min-h-full.grow
                       (if render-fullwidth
                         [render-fullwidth r]
                         [render r])]]]]))]))])})))
