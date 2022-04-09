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
            [booking.data]
            [booking.access]
            [clojure.set :as set]
            [schpaa.style.input :as sci]))

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
  (let [admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    [#_{:icon-fn   (fn [] (if @(rf/subscribe [:lab/open-menu])
                            (sc/icon-large ico/closewindow)
                            (sc/icon-large ico/menu)))
        :on-click  #(rf/dispatch [:lab/open-menu])
        :page-name :r.user}

     {:icon-fn   #(sc/icon-large ico/new-home)
      :on-click  #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      ;:on-click-active #(rf/dispatch [:app/navigate-to [:r.oversikt]])
      :class     #(if (= % :r.oversikt) :oversikt :active)
      :page-name #(some #{%} [:r.forsiden :r.oversikt])}

     {:icon-fn   (fn [] (sc/icon-large ico/user))
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :page-name :r.user}
     (when (or @admin? @booking?)
       {:icon-fn   (fn [] (sc/icon-large ico/booking))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.booking]])
        :page-name :r.booking})

     (when (or @admin? @nokkelvakt)
       {:icon-fn   (fn [] (sc/icon-large ico/nokkelvakt))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.nokkelvakt]])
        :page-name :r.nokkelvakt})

     (when (or @member? @admin? @registered?)
       {:icon-fn   (fn [] (sc/icon-large ico/yearwheel))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
        :page-name :r.yearwheel})

     :space

     (when (or @admin? @nokkelvakt)
       {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/number-input])]
                            [centered-thing (sc/icon-large (if @st ico/panelOpen ico/panelClosed))]))
        :special   true
        :centered? true
        :on-click  #(rf/dispatch [:lab/toggle-number-input])})
     :space
     (when @admin?
       {:tall-height true
        :icon-fn     (fn [] (sc/icon-large ico/fileman))

        :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
        #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                      (when (pos? c) c))
        :page-name   :r.fileman-temporary})

     (when (or @admin?)
       {:icon-fn     (fn [] (sc/icon-large ico/users))
        :on-click    #(rf/dispatch [:app/navigate-to [(if (= % :r.users) :r.presence :r.users)]])
        :tall-height true
        :class       #(if (= % :r.users) :active :oversikt)
        :page-name   #(some #{%} [:r.users :r.presence])})

     {:tall-height true
      :special     true
      :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-large
                              (if @st ico/commandPaletteOpen ico/commandPaletteClosed))))
      :on-click    schpaa.style.dialog/open-selector}]))

(defn horizontal-toolbar [uid]
  (let [admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    [{:icon-fn   #(sc/icon-large ico/new-home)
      :on-click  #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      ;:on-click-active #(rf/dispatch [:app/navigate-to [:r.oversikt]])
      :class     #(if (= % :r.oversikt) :oversikt :active)
      :page-name #(some #{%} [:r.forsiden :r.oversikt])}

     {:icon     ico/mystery2
      :disabled true}

     #_{:icon-fn  (fn [_] (when (or @admin? @nokkelvakt)
                            (let [st (rf/subscribe [:lab/number-input])]
                              (if @st ico/panelOpen ico/panelClosed))))
        :special  false
        :on-click #(rf/dispatch [:lab/toggle-number-input])}

     {:icon-fn   (fn [_] (let [st @(rf/subscribe [:lab/modal-selector])]
                           (sc/icon-large (if st ico/commandPaletteOpen ico/commandPaletteClosed))))
      :special   true
      :on-click  schpaa.style.dialog/open-selector
      :page-name :r.debug}

     {:icon     ico/mystery1
      :disabled true}
     #_{:icon      ico/booking
        :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
        :page-name :r.booking.oversikt}

     {:icon      ico/user
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :disabled  (not (some? @registered?))
      :page-name :r.user}]))

(o/defstyled toolbar-button :button
  :outline-none
  [:&
   {:color         "var(--text2)"
    :border-radius "var(--radius-round)"
    :padding       "var(--size-2)"}
   [:&:disabled {:color "var(--text3)"}]
   [:&:enabled:hover {:color "var(--text1)"}]
   [:&.active {:color      "var(--text1)"
               :background "var(--content)"
               :box-shadow "var(--shadow-1)"}]
   [:&.oversikt {:color      "var(--gray-0)"
                 :background "var(--brand1)"
                 :box-shadow "var(--shadow-1)"}
    [:&:enabled:hover {:color "var(--gray-1)"}]]

   [:&.special {:color "var(--brand1)"}]])


(o/defstyled top-left-badge :div
  :rounded-full :flex :flex-center :-top-1 :right-0
  [:&
   {:font-size    "var(--font-size-0)"
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

#_(defn horizontal-button [{:keys [icon-fn special icon on-click style page-name badge] :or {style {}}}]
    (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
          active? (= page-name current-page)]
      [:div.w-full.h-full.flex.items-center.justify-center.relative
       {:on-click on-click}
       (when badge
         (let [b (badge)]
           (when (pos? b) [top-right-tight-badge b])))
       [toolbar-button
        {:style style
         :class [(if active? :active)
                 (if special :special)]}
        [sc/icon-large
         (if icon-fn (icon-fn) icon)]]]))

(defn vertical-button [{:keys [centered? tall-height special icon icon-fn class style on-click page-name badge disabled]
                        :or   {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [:div
     {:style (if centered?
               {:pointer-events :none
                :inset          0
                :display        :grid
                :place-content  :center
                :position       :absolute}
               {:display         :flex
                :align-items     :start
                :justify-content :center
                :height          (if tall-height "var(--size-10)" "var(--size-9)")})}
     [:div.w-full.h-full.flex.flex-col.items-center.justify-around
      {:style    {:pointer-events :auto
                  :height         "var(--size-9)"}
       :on-click #(on-click current-page)}

      (when badge
        (when-some [b (badge)]
          [top-left-badge b]))

      [toolbar-button {:disabled disabled
                       :style    style
                       :class    [(if active? (or (when class (class current-page)) :active))
                                  (if special :special)]}
       [sc/icon-large
        {}
        (if icon-fn
          (icon-fn current-page)
          icon)]]]]))

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
  :flex :items-center :w-full :px-2 :gap-2
  {;:background "var(--content)"
   ;:background "transparent"
   :height "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

(o/defstyled header-top :div
  :flex :items-center :w-full :px-2 :gap-2
  {:background "var(--content)"
   ;:background "transparent"
   :height     "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

(defn master-control-box []
  (let [user-state (rf/subscribe [:lab/user-state])
        user-uid (rf/subscribe [:lab/uid])]
    (when true                                              ;@(rf/subscribe [:lab/toggle-userstate-panel])
      [:div
       (r/with-let [bookingx (rf/subscribe [:lab/booking])
                    nokkelvakt (rf/subscribe [:lab/nokkelvakt])
                    admin (rf/subscribe [:lab/admin-accessn])
                    sim-uid (r/atom "")
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
            [sc/col-space-4
             [l/ppre-x @(rf/subscribe [:lab/all-access-tokens])]
             [sci/input {:values    {:label @(rf/subscribe [:lab/uid]) #_@sim-uid}
                         :on-change #(rf/dispatch [:lab/set-sim :uid (-> % .-target .-value)]
                                                  #_(reset! sim-uid (-> % .-target .-value)))} :text [] "UID" :label]
             [sc/row-sc-g2-w
              (f-icon [:lab/set-sim-type :none] :none [icon-with-caption ico/anonymous "anonym"])
              (f-icon [:lab/set-sim-type :registered] :registered [icon-with-caption ico/user "registrert"])
              (f-icon [:lab/set-sim-type :waitinglist] :waitinglist [icon-with-caption-and-badge ico/waitinglist "påmeldt" 123])
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
                  (rf/dispatch [:lab/set-sim :nøkkelvakt %]))]]]]))])))

(defn header-line [r frontpage]
  [err-boundary
   [:div
    {:style (when-not frontpage {:background    "var(--toolbar)"
                                 :border-bottom "1px solid var(--toolbar)"})}

    [(if frontpage header-top-frontpage header-top)
     [:div.flex.flex-col.truncate.px-2
      (when-not @(rf/subscribe [:lab/in-search-mode?])
        (let [titles (compute-page-titles r)]
          [:div
           ;small/stacked
           [:div
            (if (vector? titles)
              [sc/col
               {:class [:truncate]}
               [sc/title1 {:style {:font-weight "var(--font-weight-5)"}} (last titles)]
               (when (< 1 (count titles))
                 (let [{:keys [text link]} (first titles)]
                   [sc/subtext-with-link {:href (k/path-for [link])} text]))]
              [sc/col
               [sc/title1 titles]])]]))]

     ;todo (if @(rf/subscribe [:lab/role-is-none]) ...)
     [:div.grow]
     (if frontpage
       [:div.grow]
       (if-not @(rf/subscribe [:lab/at-least-registered])
         [:<>
          [:div.grow]
          [schpaa.style.hoc.buttons/cta {:style    {:padding-inline "var(--size-4)"
                                                    :width          "min-content"
                                                    :box-shadow     "var(--shadow-1)"}
                                         :class    [:flex :xflex-wrap :items-center :gap-x-1 :h-10 :xspace-y-0 :xshrink-0]
                                         :on-click #(rf/dispatch [:app/login])}
           "Logg inn"]]))
     [main-menu]]]])



(defn right-tabbar []
  (let [numberinput? (rf/subscribe [:lab/number-input])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.relative
       ;; force the toolbar to stay on top when boat-panel is displayed
       (into [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.relative
              {:style {:z-index     1211
                       :padding-top "var(--size-0)"
                       :box-shadow  (if @numberinput? "var(--shadow-5)" "none")
                       :background  "var(--toolbar)"}}]
             (map #(if (keyword? %)
                     [:div.grow]
                     [vertical-button %])
                  (remove nil? (vertical-toolbar-right (:uid @user-auth)))))
       [:div.absolute.right-16.xl:right-20.inset-y-0
        {:style {:width          "298px"
                 :pointer-events :none}}
        [boatinput-menu]]])))

(defn bottom-tabbar []
  (let [search (rf/subscribe [:lab/is-search-running?])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? (not @search) @(rf/subscribe [:lab/at-least-registered]))
      [:div.sm:hidden.flex.gap-0.border-t
       {:style {:width           "100%"
                :justify-content :space-around
                :height          "var(--size-10)"
                :min-height      "var(--size-10)"
                :padding-inline  "var(--size-2)"
                :box-shadow      "var(--inner-shadow-3)"
                :border-color    "var(--toolbar-)"
                :background      "var(--toolbar)"}}
       (into [:<>] (map vertical-button
                        (horizontal-toolbar (:uid @user-auth))))])))

(o/defstyled mainframe :div)

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])
        access-tokens @(rf/subscribe [:lab/all-access-tokens])]
    [:div.z-1 {:style {:background "var(--gray-10)"}}
     [:div.mx-auto.max-w-lg.py-8
      [:div.mx-4
       [sc/col-space-4
        (when goog.DEBUG
          [l/ppre-x access-tokens])

        [sc/col-space-1
         [sc/title {:style {:color "var(--gray-4)"}} "Postadresse"]
         [sc/col-space-1
          {:style {:user-select :contain
                   :color       "var(--gray-5)"}}
          [sc/text1-cl "Nøklevann ro- og padleklubb"]
          [sc/text1-cl "Postboks 37, 0621 Bogerud"]
          [:div.flex.justify-start.flex-wrap.gap-4
           [sc/subtext-with-link {:class [:dark]
                                  :href  "mailto:styret@nrpk.no"} "styret@nrpk.no"]
           [sc/subtext-with-link {:class [:dark]
                                  :href  "mailto:medlem@nrpk.no"} "medlem@nrpk.no"]]]]
        [sc/row-ec
         [hoc.buttons/reg-pill-icon
          {:style    {:background    "var(--gray-8)"
                      :border-radius "var(--radius-round)"
                      :box-shadow    "var(--shadow-1)"
                      :border        "1px solid var(--gray-8)"
                      :color         "var(--gray-0)"}
           :on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
          ico/tilbakemelding "Tilbakemelding"]]
        [sc/col
         [sc/small1 (or booking.data/VERSION "version")]
         [sc/small1 (or booking.data/DATE "date")]]
        #_[:a {:href "/img/bg/bg-dark-1.jpg" :download "my-download.pdf"} "Download File"]]]]]))

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
          [schpaa.style.dialog/command-palette
           {:context @(rf/subscribe [:lab/modal-selector-extra])
            :vis     (rf/subscribe [:lab/modal-selector])
            :close   #(rf/dispatch [:lab/modal-selector false])}]
          ;endregion
          [:div.fixed.inset-0.flex
           [right-tabbar]
           [:div.flex.flex-col
            {:style {:flex "1 1 auto"}}
            (when-not frontpage
              [header-line r false])
            [:div.flex.flex-col.xoverflow-y-auto.h-full
             {:class (when-not frontpage [:overflow-y-auto])}
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
                 {:style {:background-color "var(--content)"
                          :flex             "1 1 auto"}}
                 contents]
                (when-not frontpage [after-content])])]

            [bottom-tabbar]]]])})))

(def max-width "60ch")

(defn matches-access "" [r [status access :as all-access-tokens]]
  (let [[req-status req-access :as req-tuple] (-> r :data :access)]
    (if req-tuple
      (and (some #{status} (if (vector? req-status) req-status [req-status]))
           #_(if req-access (some access req-access) true))
      true)))

(comment
  (can-modify? {:data {:modify [:member #{:admin}]}} [nil #{:admin}])
  #_(some? (seq (set/intersection #{:as} #{:a}))))

(defn no-access-view [r]
  (let [required-access (-> r :data :access)]
    [:div.max-w-lg.mx-4
     {:style {:padding-inline "var(--size-4)"
              :padding-top    "var(--size-10)"
              :margin-inline  "auto"}}
     [sc/col-space-4
      [sc/hero {:style {:white-space :nowrap
                        :text-align  :center
                        :color       "var(--text2)"}} "Ingen tilgang"]
      [sc/row-center [sc/icon {:style {:text-align :center
                                       :color      "var(--text3)"}} ico/stengt]]
      [sc/small1 {:style {:white-space :nowrap}} "Du har --> " (str @(rf/subscribe [:lab/all-access-tokens]))]
      [sc/small1 {:style {:white-space :nowrap}} "Du trenger --> " (str required-access)]
      [l/ppre-x
       (matches-access r @(rf/subscribe [:lab/all-access-tokens]))
       (-> r :data :access)
       @(rf/subscribe [:lab/all-access-tokens])]

      [sc/text "For å se denne siden må du som minimum:"]
      [sc/text2                                             ;{:style {:color "var(--brand1)"}}
       (cond
         (some #{:registered} (first required-access)) "Være innlogget og ha registrert deg med grunnleggende informasjon om deg selv."
         (some #{:waitinglist} (first required-access)) "Være påmeldt innmeldingskurs."
         :else #_(some #{:member} (first required-access)) "Være medlem i NRPK.")]

      #_(case (first required-access)
          :registered "innlogget og ha registrert grunnleggende informasjon om deg selv."
          :member "medlem i NRPK."
          :waitinglist "påmeldt innmeldingskurset til NRPK."
          (str "?" (first required-access)))

      #_[sc/text "Er det noe som er uklart må du gjerne sende oss en tilbakemelding; helt nederst på alle sider er det en knapp du kan bruke."]]]))

(rf/reg-sub :lab/we-know-how-to-scroll? :-> :lab/we-know-how-to-scroll)

(rf/reg-event-db :lab/we-know-how-to-scroll (fn [db [_ arg]] (assoc db :lab/we-know-how-to-scroll arg)))

(defn set-ref [a scroll-fn]
  (fn [el]
    (when-not @a
      (.addEventListener el "scroll" scroll-fn)
      (reset! a el))))

(defn +page-builder [r m]
  (let [scrollpos (r/atom 0)
        scroll-fn (fn [e]
                    (let [v (-> e .-target .-scrollTop)]
                      (if (< 50 v)
                        (do
                          (rf/dispatch [:lab/we-know-how-to-scroll true])
                          (rf/dispatch [:lab/close-menu])))
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
         ;(tap> :component-did-mount)
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
               ;intent: removed overflow!
               (into [:div.overflow-y-auto.h-full
                      {:key "key-one"
                       :ref (set-ref a scroll-fn)}]
                     [(let [v (- 1 (/ (- (+ @scrollpos 0.001) 30) 70))]
                        [:div.sticky.top-0.z-10.relative
                         {:style {:opacity v}}
                         [booking.common-views/header-line r true]])
                      [:div.-mt-16 {:style {:height "calc(100% - 4rem)"}}
                       [render r]]])]

              [page-boundary r
               {:frontpage false}
               (let [users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
                     have-access? (booking.common-views/matches-access r users-access-tokens)
                     modify? (booking.access/can-modify? r users-access-tokens)]
                 (if-not have-access?
                   [no-access-view r]
                   [sc/col-space-8
                    {:class [:py-8]
                     :style {:flex             "1 1 auto"
                             :background-color "var(--content)"}}

                    ;modification could happen here
                    (when (fn? panel)
                      (when-some [p (panel)]
                        [:div
                         [:div.mx-4
                          [:div.mx-auto
                           {:style {:width     "100%"
                                    :max-width max-width}}
                           #_(when goog.DEBUG
                               [:div.pb-4 [l/ppre-x
                                           {:users-access-tokens users-access-tokens
                                            :r                   (-> r :data :modify)
                                            :can-modify?         modify?}]])
                           [hoc.panel/togglepanel pagename "innstillinger" panel modify?]]]]))

                    (when always-panel
                      [:div.mx-4
                       [:div.mx-auto
                        {:style {:width     "100%"
                                 :max-width max-width}}
                        [always-panel modify?]]])

                    [:div.duration-200.grow
                     {:style {:margin-right :auto
                              :margin-left  (if (and render-fullwidth @numberinput @left-aligned)
                                              ;; force view to align to the left
                                              0
                                              :auto)
                              :width        "100%"
                              :max-width    (if render-fullwidth "" max-width)}}
                     [:div.min-h-full.grow
                      (if render-fullwidth
                        [render-fullwidth r]
                        [:div.mx-4 [render r]])]]]))]))])})))
