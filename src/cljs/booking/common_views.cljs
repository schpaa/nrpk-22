(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reitit.core :as reitit]
            [kee-frame.error]
            [reagent.ratom]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.content.booking-blog]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [db.core :as db]
            [booking.fileman]
            [kee-frame.core :as k]
            [booking.routes]
            [schpaa.style.hoc.page-controlpanel :as hoc.panel]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons :refer [cta pill icon-with-caption icon-with-caption-and-badge]]
            [schpaa.debug :as l]
            [booking.data]
            [booking.access]
            [clojure.set :as set]
            [schpaa.style.input :as sci]
            [booking.common-widgets :refer [lookup-page-ref-from-name vertical-button horizontal-button no-access-view]]
            [booking.modals.boatinput]
            [booking.modals.feedback]
            [booking.modals.commandpalette]
            [booking.modals.slideout]
            [booking.modals.centered]
            [booking.modals.mainmenu :refer [main-menu]]
            [booking.account]
            [tick.core :as t]
            [clojure.string :as str]
            [booking.common-widgets :as widgets]
            [booking.fiddle]))

;region styles

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

(o/defstyled result-item :div
  {:display        :flex
   :align-items    :center
   :min-width      "12rem"
   :padding-inline "var(--size-4)"
   :padding-block  "var(--size-3)"
   :background     "var(--content)"
   :color          "var(--text1)"}
  [:&:hover {:background "var(--toolbar-)"}])

(o/defstyled header-top-frontpage :div
  :flex :items-center :w-full :px-2 :gap-2
  {:height "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

(o/defstyled header-top :div
  :flex :items-center :w-full :px-2 :gap-2
  {;:outline "1px solid red"
   :background "var(--content)"
   :height     "var(--size-9)"}
  [:at-media {:min-width "511px"}
   {:border-top-right-radius "var(--radius-3)"
    :xborder-right           "1px solid var(--toolbar-)"}])

;endregion

;region junk

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
        :button       (fn [_]
                        [scb/round-mainpage
                         {:on-click toggle-mainmenu}
                         [sc/corner-symbol "?"]])}])))

;endregion

;region toolbars

(defn vertical-toolbar-definitions []
  (let [uid @(rf/subscribe [:lab/uid])
        presence (rf/subscribe [::db/presence-status])
        admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        ipad? (= uid @(db/on-value-reaction {:path ["system" "active"]}))
        booking? (rf/subscribe [:lab/booking])
        left-side? (schpaa.state/listen :lab/menu-position-right)
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    ;(tap> [ipad?])
    [{:icon-fn      #(sc/icon-large ico/new-home)
      :default-page :r.forsiden

      :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class        #(if (= % :r.oversikt) :oversikt :selected)
      :page-name    #(some #{%} [:r.forsiden :r.oversikt])}

     (when ipad?
       {:icon-fn      (fn [] (sc/icon-large ico/vaktrapport))
        :caption      "Vaktrapport"
        :default-page :r.mine-vakter-ipad
        :class        #(if (= % :r.user) :oversikt :selected)
        :on-click     #(rf/dispatch [:app/navigate-to [:r.mine-vakter-ipad #_(if (= % :r.min-status) :r.user :r.min-status)]])
        #_#(rf/dispatch [:app/navigate-to [:r.min-status]])
        :page-name    :r.mine-vakter-ipad #_#(some #{%} [:r.min-status :r.user])})

     (when-not ipad?
       {:icon-fn      (fn [] (sc/icon-large ico/user))
        :caption      "Mine opplysninger"
        :default-page :r.min-status
        :class        #(if (= % :r.user) :oversikt :selected)
        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.min-status) :r.user :r.min-status)]])
        #_#(rf/dispatch [:app/navigate-to [:r.min-status]])
        :page-name    #(some #{%} [:r.min-status :r.user])})

     #_(when (or @admin? @booking?)
         {:icon-fn      (fn [] (sc/icon-large ico/booking))
          :caption      "Booking Sjøbasen"
          :default-page :r.booking
          :on-click     #(rf/dispatch [:app/navigate-to [:r.booking]])
          :page-name    :r.booking})

     (when (or @admin? @nokkelvakt ipad?)
       {:icon         ico/mystery1
        #_#_:badge (fn [] {:value 2
                           :attr  {:style {:background-color "var(--floating)"
                                           :color            "var(--text1)"}}})
        :disabled     false
        :caption      "Utlån Nøklevann"
        :default-page :r.utlan
        ;:page-name :r.utlan
        :page-name    #(some #{%} [:r.utlan :r.båtliste.nøklevann])
        :class        #(if (= % :r.utlan) :selected :oversikt)
        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.utlan) :r.båtliste.nøklevann :r.utlan)]])
        #_#_:on-click #(rf/dispatch [:app/navigate-to [:r.utlan]] #_[:lab/toggle-boatpanel])})

     (when (or @admin? @nokkelvakt #_(not ipad?))
       {:icon-fn      (fn [] (sc/icon-large ico/nokkelvakt))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.nokkelvakt]])
        :default-page :r.nokkelvakt
        :page-name    :r.nokkelvakt})

     (when (or @member? @admin? @registered?)
       {:icon-fn      (fn [] (sc/icon-large ico/yearwheel))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
        :default-page :r.yearwheel
        :page-name    :r.yearwheel})
     #_:space
     #_(when (or @admin? @nokkelvakt)
         {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/number-input])]
                              [centered-thing (sc/icon-large (if @left-side?
                                                               (if @st ico/panelOpen ico/panelClosed)
                                                               (if @st ico/panelClosed ico/panelOpen)))]))
          :special   true
          :centered? true
          :on-click  #(rf/dispatch [:lab/toggle-number-input])})
     :space
     (when @admin?
       {:tall-height  true
        :default-page :r.fileman-temporary
        :icon-fn      (fn [] (sc/icon-large ico/fileman))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
        :page-name    :r.fileman-temporary})
     (when (or @admin?)
       {:icon-fn      (fn [] (sc/icon-large ico/users))
        :badge        (fn [] {:attr  {:style {:background-color "var(--yellow-7)"
                                              :color            "var(--gray-9)"}}
                              :value (let [c (count (:online @presence))]
                                       (when (pos? c) c))})
        :default-page :r.users
        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.presence) :r.users :r.presence)]])
        :tall-height  true
        :class        #(if (= % :r.presence) :selected :oversikt)
        :page-name    #(some #{%} [:r.users :r.presence])})
     {:tall-height true
      ;:default-page :r.forsiden
      ;:page-name :r.forsiden
      :caption     "Hva kan jeg gjøre?"
      :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-large
                              (if @st ico/commandPaletteClosed ico/commandPaletteOpen))))
      :on-click    #(rf/dispatch [:app/toggle-command-palette])}
     {:tall-height true

      :icon-fn     (fn [] (let [with-caption? @(schpaa.state/listen :app/toolbar-with-caption)
                                st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-large
                              ico/switchHoriz)))
      ;with-caption? @(schpaa.state/listen :app/toolbar-with-caption)
      :on-click    #(schpaa.state/toggle :app/toolbar-with-caption)}]))

(defn horizontal-toolbar-definitions []
  (let [;uid (rf/subscribe [:lab/uid])
        admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]

    [{:icon-fn       #(-> ico/new-home)
      :short-caption "Hjem"
      :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class         #(if (= % :r.oversikt) :oversikt :active)
      :page-name     #(some #{%} [:r.forsiden :r.oversikt])}

     (when booking?
       {:icon          ico/booking
        :short-caption "Booking"
        :icon-disabled ico/booking-disabled
        :disabled      true})

     {:icon-fn       (fn [_] (let [st @(rf/subscribe [:lab/modal-selector])]
                               (if st ico/commandPaletteOpen ico/commandPaletteClosed)))
      :special       true
      :short-caption "Søk"
      :on-click      #(rf/dispatch [:app/toggle-command-palette])
      :page-name     :r.debug}


     (when (or @admin? @nokkelvakt)
       {:icon          ico/mystery1
        :disabled      false
        :caption       "Utlån Nøklevann"
        :short-caption "Utlån"
        :default-page  :r.utlan
        :page-name     #(some #{%} [:r.utlan :r.båtliste.nøklevann])
        :class         #(if (= % :r.utlan) :selected :oversikt)
        :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.utlan) :r.båtliste.nøklevann :r.utlan)]])})

     {:icon-fn       #(-> ico/user)
      :short-caption "Mine"
      :caption       "Mine opplysninger"
      :default-page  :r.min-status
      :class         #(if (= % :r.user) :oversikt :selected)
      :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.min-status) :r.user :r.min-status)]])
      :page-name     #(some #{%} [:r.min-status :r.user])}]))

(comment
  (do
    (lookup-page-ref-from-name :r.forsiden)))

(defn vertical-toolbar [left-side?]
  (let [has-chrome? (rf/subscribe [:lab/has-chrome])
        with-caption? @(schpaa.state/listen :app/toolbar-with-caption)
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        numberinput? (rf/subscribe [:lab/number-input])]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.h-full.sm:flex.hidden.relative.select-none
       {:class [(if with-caption? :w-56 :w-16)]
        :style {:box-shadow  (if @numberinput? "var(--shadow-5)" "var(--inner-shadow-2)")
                :backgrounds "var(--gray-9)"}}
       ;; force the toolbar to stay on top when boat-panel is displayed (?)
       (into [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.relative.xw-16.items-start
              {:style (conj
                        {:padding-top "var(--size-0)"})}]
             (map #(let [cap (when with-caption?
                               (let [p (:page-name %)]
                                 (if-some [caption (or (some->
                                                         (if (fn? p) (p current-page) p)
                                                         lookup-page-ref-from-name
                                                         :text)
                                                       (:caption %))]
                                   caption
                                   (when-let [dp (:default-page %)]
                                     (:text (lookup-page-ref-from-name dp))))))]
                     (if (keyword? %)
                       [:div.grow]
                       [vertical-button (assoc % :right-side (not left-side?)
                                                 :caption cap)]))

                  (remove nil? (vertical-toolbar-definitions))))])))

(defn bottom-toolbar []

  (let [;_search (rf/subscribe [:lab/is-search-running?])
        ;_has-chrome? (rf/subscribe [:lab/has-chrome])
        switch? @(schpaa.state/listen :lab/menu-position-right)]
    (booking.fiddle/render ((if switch? identity reverse) (horizontal-toolbar-definitions)))
    #_(when (and @(rf/subscribe [:lab/at-least-registered]))
        (into [sc/bottom-toolbar-style]
              (mapv horizontal-button
                    ((if switch? identity reverse)
                     (horizontal-toolbar-definitions)))))))


;endregion

;region this takes forever

#_(defn compute-pagetitles [r]
    {:link [:r.forsiden] :text "YAhoo"})

(defn compute-pagetitles [r]
  (let [path-fn (some-> r :data :path-fn)
        page-title (-> r :data :header)]
    {:text (cond
             (vector? page-title) (second page-title)
             :else [])
     #_(if (vector? page-title)
         (first (remove nil? [(if (keyword? (first page-title))
                                (lookup-page-ref-from-name (first page-title))
                                (first page-title))
                              (or (last page-title) "no-title")
                              (when path-fn [:div.truncate (path-fn r)])]))
         (or (if path-fn
               (path-fn r))
             (or page-title "no-title")))
     :link (if path-fn (path-fn {:id "Ri0icn4bbffkwB3sQ1NWyTxoGmo1"}) (some-> page-title
                                                                              ffirst
                                                                              lookup-page-ref-from-name
                                                                              :text))}))

(comment
  (do
    (compute-pagetitles {:data {:path-fn (fn [{:keys [id] :as m}]
                                           (:navn (user.database/lookup-userinfo id) "not found"))
                                :header  ["headers"]}})
    (compute-pagetitles {:data {:path-fn (fn [{:keys [id] :as m}]
                                           (:navn (user.database/lookup-userinfo id) "not found"))
                                :header  ["headers"]}})))

(defn see-also
  [{:keys [text link]}]
  (when link
    [sc/small0 "Se også "
     [sc/subtext-with-link
      {:class [:small :hover:opacity-100]
       :href  (k/path-for [link])}
      (:text (lookup-page-ref-from-name link))]]))

(defn location-block [links caption switch?]
  (let [text caption
        link (first links)]
    [:div.flex.flex-col.px-2.w-full
     {:on-click #(rf/dispatch [:app/navigate-to [link]])
      :style    {:z-index 9
                 :flex    "1 1 1"
                 :cursor  :pointer}
      :class    [(when-not switch? :text-right)]}
     [sc/col-space-1
      {:style {:justify-content :start}}
      [sc/title1 (or (if (fn? text)
                       '(text nil #_(some-> r :path-params))
                       (if (map? text) (:text text)))
                     text)]
      [:div
       {:style {:font-size "smaller"}}
       (let [z {:link link #_(some-> r :data :header ffirst)
                :text text #_(some-> r :data :header ffirst lookup-page-ref-from-name :text)}]
         (try (see-also z)
              (catch js/Error e {:CRASH/see-also z})))]]]))

(defn header-line [r frontpage v]
  #_(let [data [(-> r :data :header)]]
      (location-block r false))
  ;[l/pre r]
  (let [[links caption] (some-> r :data :header)
        switch? (schpaa.state/listen :lab/menu-position-right)
        location (location-block links caption @switch?)]
    [err-boundary
     [sc/row-fields {:class [:h-16 :items-center :w-full]
                     :style {:opacity             (- 1 (or v 0))
                             :background          (if frontpage "var(--toolbar)" "var(--content)")
                             :border-bottom-color (if frontpage "var(--toolbar-)" "var(--content)")
                             :border-bottom-style "solid"
                             :border-bottom-width "1px"}}

      #_(let [_login (fn []
                       (when-not @(rf/subscribe [:lab/at-least-registered])
                         [:<>
                          [:div.grow]
                          [schpaa.style.hoc.buttons/cta {:style    {:padding-inline "var(--size-4)"
                                                                    :width          "min-content"
                                                                    :box-shadow     "var(--shadow-1)"}
                                                         :class    [:flex :xflex-wrap :items-center :gap-x-1 :h-10 :xspace-y-0 :xshrink-0]
                                                         :on-click #(rf/dispatch [:app/login])}
                           "Logg inn"]]))
              location (location-block links caption @switch?)]
          (apply (if frontpage header-top-frontpage header-top)))

      (if @switch?
        (if frontpage
          [header-top-frontpage location [main-menu r]]
          [header-top location [main-menu r]])
        (if frontpage
          [header-top-frontpage [main-menu r] location]
          [header-top [sc/row-fields [main-menu r] location]]))]]))

(defn search-result []
  (let [data (range 12)]
    [:div.pt-px                                             ;.min-h-screen
     (into [:div.gap-px.grid.w-full
            {:style {:grid-template-columns "repeat(auto-fill,minmax(24rem,1fr))"}}]
           (for [e data]
             [result-item e]))]))

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
             [l/pre @(rf/subscribe [:lab/all-access-tokens])]
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

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])
        user-uid (rf/subscribe [:lab/uid])
        ipad? (= @user-uid @(db/on-value-reaction {:path ["system" "active"]}))]
    [:div.px-2.pt-2 {:style {:background-color "var(--toolbar-)"}}
     [:div.z-1.rounded-top
      {:style {:border-top-left-radius  "var(--radius-2)"
               :border-top-right-radius "var(--radius-2)"
               :background              "var(--gray-9)"}}
      [:div.mx-auto.max-w-xl.pt-8.pb-16
       [:div.mx-4
        [sc/col-space-4
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
           {:on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
           ico/tilbakemelding "Tilbakemelding"]]
         [sc/row-sc-g4-w
          [sc/col
           [sc/small1 (or booking.data/VERSION "version")]
           [sc/small1 (or booking.data/DATE "date")]]

          (when-not ipad?
            [hoc.buttons/reg-pill
             {:on-click #(do
                           (rf/dispatch [:app/navigate-to [:r.mine-vakter-ipad]])
                           (db/database-update {:path  ["system"]
                                                :value {"active" @user-uid}}))}
             "Bli til Båtlogg"])

          (when @(rf/subscribe [:lab/admin-access])
            [hoc.buttons/danger-pill
             {:on-click #(db/database-update {:path  ["system"]
                                              :value {"timestamp" booking.data/DATE}})}
             "Aktiver versjon"])]]]]]]))

(defn- name-badge []
  (let [account-email (rf/subscribe [:lab/username-or-fakename])]
    [:div.fixed.inset-x-0.text-white.z-20
     {:style {:height    "auto"
              :bottom    "0"
              :width     "min-content"
              :left      "50%"
              :transform "translateX(-50%)"}}
     [sc/small2 {:style {:color                   "var(--toolbar)"
                         :background              "var(--text1)"
                         :border-top-right-radius "var(--radius-0)"
                         :border-top-left-radius  "var(--radius-0)"
                         :padding-inline          "var(--size-2)"
                         :padding-block-start     "var(--size-1)"
                         :padding-block-end       "var(--size-2)"}}
      (str/trim (str @account-email))]]))

(defn page-boundary [r {:keys [frontpage] :as options} & contents]
  (let [switch? (schpaa.state/listen :lab/menu-position-right)
        admin? (rf/subscribe [:lab/admin-access])]

    (r/create-class
      {:display-name        "page-boundary"

       :component-did-mount (fn [_]
                              ;fix: existence of #inner-document
                              (when-let [el (.getElementById js/document "inner-document")]
                                (.focus el)))

       :reagent-render
       (fn [r {:keys [frontpage] :as options} & contents]
         [err-boundary

          [booking.modals.slideout/render]
          [booking.modals.centered/render]
          [booking.modals.boatinput/render-boatinput]
          [booking.modals.commandpalette/window-anchor]

          (let [content [:div.flex.flex-col
                         {:style {:flex "1 1 auto"}}
                         (when-not frontpage [header-line r false nil])

                         [:div.flex.flex-col.h-full
                          {:style {:height "100%"}
                           :class [:overflow-y-auto]}
                          [:<>
                           [:div
                            {:style {:background-color "var(--content)"
                                     :flex             "1 1 auto"}}
                            contents]]



                          [:div.sticky.bottom-0 {:style {:border-top  "1px solid var(--toolbar-)"
                                                         :xbox-shadow "var(--shadow-1)"}} [bottom-toolbar]]
                          (when-not frontpage [after-content])]
                         (when (or goog.DEBUG @admin?)
                           ;bottom name when admin (for debugging)
                           [:div.sticky.bottom-0 [name-badge]])]]
            [:div.fixed.inset-0.flex
             (if @switch?
               [:<> [vertical-toolbar true] content]
               [:<> content [vertical-toolbar false]])])])})))

(defn matches-access "" [r [status access :as all-access-tokens]]
  (let [[req-status req-access :as req-tuple] (-> r :data :access)]
    (if req-tuple
      (and (some #{status} (if (vector? req-status) req-status [req-status]))
           #_(if req-access (some access req-access) true))
      true)))

(comment
  (can-modify? {:data {:modify [:member #{:admin}]}} [nil #{:admin}])
  #_(some? (seq (set/intersection #{:as} #{:a}))))

(rf/reg-sub :lab/we-know-how-to-scroll? :-> :lab/we-know-how-to-scroll)

(rf/reg-event-db :lab/we-know-how-to-scroll (fn [db [_ arg]] (assoc db :lab/we-know-how-to-scroll arg)))

(defn set-ref [a scroll-fn]
  (fn [el]
    (when-not @a
      (.addEventListener el "scroll" scroll-fn)
      (reset! a el))))

(def max-width "54ch")

(defn +page-builder [r m]
  (let [version-timestamp (db/on-value-reaction {:path ["system" "timestamp"]})
        scrollpos (r/atom 0)
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
         (rf/dispatch [:lab/we-know-how-to-scroll false]))

       :reagent-render
       (fn [r {:keys [frontpage render render-fullwidth panel always-panel panel-title]}]
         [err-boundary
          (try
            (if-let [app-timestamp (some-> booking.data/DATE t/date-time)]
              (if-let [db-timestamp (some-> version-timestamp deref t/date-time)]
                (when (t/< app-timestamp db-timestamp)
                  (rf/dispatch [:modal.slideout/toggle
                                true
                                {:content-fn
                                 (fn [_] [sc/centered-dialog
                                          {:style {:position   :relative
                                                   :overflow   :auto
                                                   :z-index    10
                                                   :max-height "80vh"}}
                                          [sc/col-space-8
                                           [sc/dialog-title' "Oppfrisk nettsiden"]
                                           [sc/col-space-4
                                            [sc/text1 "Det finnes en oppdatering av denne nettsiden — vil du se den oppdaterte utgaven?"]

                                            (let [link @(rf/subscribe [:kee-frame/route])
                                                  addr (or (some-> link :path) "")
                                                  path (str (.-protocol js/window.location) "//" (.-host js/window.location) addr)]
                                              [:<>
                                               ;[l/ppre-x link]
                                               [sc/text1 path]]
                                              [sc/row-ec
                                               [hoc.buttons/cta {:on-click #(js/window.location.assign path)} "Ja, gjerne!"]])

                                            [schpaa.style.hoc.page-controlpanel/togglepanel-local
                                             "Mer informasjon"
                                             (fn [_]
                                               [sc/col-space-4
                                                [sc/text1 "Du får denne meldingen fordi dette nettstedet er under utvikling og oppdateringene skjer daglig og vi vil at du alltid skal bruke den siste utgaven."]
                                                [sc/text2 [:span "Hvis du bruker Windows kan du trykke " [sc/as-shortcut "ctrl-r"] " (eller " [sc/as-shortcut "\u2318-r"] " på MacOS) for å laste inn siden på nytt."]]
                                                [sc/text2 [:span "Du kan også klikke/trykke utenfor dette vinduet eller trykke på "] [sc/as-shortcut "ESC"] " hvis du ikke vil bli avbrutt med det du holdt på med."]])]
                                            ;[sc/small1 db-timestamp]
                                            #_[l/pre
                                               {:db-timestamp  (times.api/arrival-date db-timestamp)
                                                :app-timestamp (times.api/arrival-date app-timestamp)
                                                :need-reload?  (t/< app-timestamp db-timestamp)}]
                                            #_[sc/small0 "Denne meldingen vil komme opp hver gang du går til en ny lenke på dette nettstedet. side, inntil du ."]]]])}]))))
            (catch js/Error _))
          (let [pagename (some-> r :data :name)
                _user-account-email @(rf/subscribe [:lab/username-or-fakename])
                _numberinput (rf/subscribe [:lab/number-input])
                _left-aligned (schpaa.state/listen :activitylist/left-aligned)]
            (if frontpage
              [page-boundary r
               {:frontpage true}
               ;intent: removed overflow!
               (into [:div.overflow-y-autox.h-full
                      {:key "key-one"
                       :ref (set-ref a scroll-fn)}]
                     [(let [v (- 1 (/ (- (+ @scrollpos 0.001) 30) 70))]
                        [:div.sticky.top-0.z-10.relative
                         ;{:style {:opacity v}}
                         [booking.common-views/header-line r true v]])
                      [:div.-mt-16 {:style {:height "calc(100% - 4rem)"}}
                       [render r]]])]

              [page-boundary r
               {:frontpage false}
               (let [users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
                     have-access? (booking.common-views/matches-access r users-access-tokens)
                     modify? (booking.access/can-modify? r users-access-tokens)]
                 (if-not have-access?
                   [no-access-view r]
                   [sc/basic-page
                    [sc/col-space-2
                     {:class [:pt-8]
                      :style {:flex             "1 1 auto"
                              :background-color "var(--content)"}}

                     ;modification could happen here
                     (when (fn? panel)
                       [sc/container
                        [hoc.panel/togglepanel pagename (or panel-title "lenker & valg") panel modify?]])

                     (when always-panel
                       [sc/container
                        [always-panel modify?]])

                     (if render-fullwidth
                       [sc/container
                        {:style {:margin-right "unset"
                                 :margin-left  "unset" #_(if (and @numberinput @left-aligned)
                                                           ;; force view to align to the left
                                                           0
                                                           "unset")
                                 :width        "100%"
                                 :max-width    (if render-fullwidth "" "inherit")}}
                        [render-fullwidth r]]
                       [sc/container
                        [render r]])]]))]))])})))
