(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reitit.core :as reitit]
    ;[clerk.core]
            [goog.dom :as dom]
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
  [:& :flex :items-center :w-full :px-2 :gap-2
   {:background "var(--blue-2)"
    :height     "var(--size-9)"}])

(o/defstyled header-top :div
  [:& :flex :items-center :w-full :px-2 :gap-2
   {:height            "var(--size-9)"
    ;:z-index 10000
    :position          :relative
    :xbackground-color "var(--floating)"}])

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
        right-side? (schpaa.state/listen :lab/menu-position-right)
        with-caption? (schpaa.state/listen :app/toolbar-with-caption)
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
     {:tall-height       true
      :opposite-icon-fn  (fn [] (let [st @right-side?]
                                  (sc/icon-large
                                    {:style {:color "var(--brand2)"}}
                                    (if st ico/arrowLeft ico/arrowRight))))
      :opposite-on-click (fn [] (schpaa.state/toggle :lab/menu-position-right))
      :icon-fn           (fn [] (let [with-caption? @(schpaa.state/listen :app/toolbar-with-caption)]
                                  (sc/icon-large
                                    (if with-caption?
                                      (if right-side?
                                        ico/panelOpen
                                        ico/panelClosed)
                                      (if right-side?
                                        ico/panelClosed
                                        ico/panelOpen)))))

      :on-click          #(schpaa.state/toggle :app/toolbar-with-caption)}]))

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

(defn vertical-toolbar [right-side?]
  (let [has-chrome? (rf/subscribe [:lab/has-chrome])
        with-caption? @(schpaa.state/listen :app/toolbar-with-caption)
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.h-full.sm:flex.hidden.relative.select-none
       {:class [(if with-caption? :w-56 :w-16)]
        :style {:box-shadow "var(--inner-shadow-2)"
                :background "var(--toolbar)"}}
       ;; force the toolbar to stay on top when boat-panel is displayed (?)
       (into [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.relative.items-start
              {:style {:padding-top "var(--size-0)"}}]
             (map (fn [{:keys [opposite-icon-fn] :as e}]
                    (let [cap (when with-caption?
                                (let [p (:page-name e)]
                                  (if-some [caption (or (some->
                                                          (if (fn? p) (p current-page) p)
                                                          lookup-page-ref-from-name
                                                          :text)
                                                        (:caption e))]
                                    caption
                                    (when-let [dp (:default-page e)]
                                      (:text (lookup-page-ref-from-name dp))))))]
                      (if (keyword? e)
                        [:div.grow]
                        [vertical-button (assoc e :right-side right-side?
                                                  :with-caption? with-caption?
                                                  :caption cap)])))
                  (remove nil? (vertical-toolbar-definitions))))])))

(defn bottom-toolbar []
  (let [switch? @(schpaa.state/listen :lab/menu-position-right)
        data ((if switch? reverse identity) (horizontal-toolbar-definitions))]
    [booking.fiddle/render data]))

;endregion

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
  ;todo what about this?
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
    [sc/small0 {:style {:white-space :nowrap
                        :font-size   "var(--font-size-0)"
                        :font-weight "var(--font-weight-4)"}} "Se også "
     [sc/subtext-with-link
      {:class [:small :hover:opacity-100]
       :href  (k/path-for [link])}
      (:text (lookup-page-ref-from-name link))]]))

(defn location-block [r links caption right-menu?]
  (let [link (first links)]
    [:div.flex.flex-col.p-2.w-auto.hover:bg-white
     {:on-click #(rf/dispatch [:app/navigate-to [link]])
      :style    {:z-index 9
                 :flex    "1 1 1"
                 :cursor  :pointer}
      :class    [(if right-menu? :text-right :text-left)]}
     [sc/col-space-1
      {:style {:justify-content :start}}
      [sc/title1 {:style {:font-weight "var(--font-weight-4)"}} (or (if (fn? caption)
                                                                      (caption (some-> r :path-params))
                                                                      (if (map? caption) (:text caption) caption))
                                                                    caption)]
      (let [z {:link link
               :text caption}]
        (try (see-also z)
             (catch js/Error e {:CRASH/see-also z})))]]))

(def scrollpos (r/atom 0))

(defn header-line [r frontpage v]
  (let [[links caption] (some-> r :data :header)
        {:keys [right-menu? mobile? menu-caption?] :as geo} @(rf/subscribe [:lab/screen-geometry])
        location [location-block r links caption right-menu?]]
    [:div.w-full.flex.justify-between
     {:class [:h-16 :items-center]}
     ;[l/pre (times.api/format "%0.2f" (/ (- (+ @scrollpos 0.001) 30) 70))]
     (let [items [location
                  [sc/small {:style {:color "var(--brand1)"}} (when v (times.api/format "%0.3f" v))]
                  [:div.grow]
                  [:div.w-12 [main-menu r]]]
           ;:div.relative.-debug2.h-screen.w-screen
           items (if right-menu? (reverse items) items)]
       (if frontpage
         [header-top-frontpage {:style {:background-color "yellow"}} (into [:<>] items)]
         [header-top (into [:<>] items)]))]))

(defn header [v]
  (let [route (rf/subscribe [:kee-frame/route])
        scroll-pos (rf/subscribe [:scroll-pos])
        {:keys [right-menu? mobile? menu-caption?] :as geo} @(rf/subscribe [:lab/screen-geometry])
        marg (when-not mobile? (if menu-caption? "14rem" "4rem"))]
    (when-let [route @route]
      [:div.fixed.top-0.noprint.inset-x-0.h-16
       {:style {:margin-left  (when-not right-menu? marg)
                :margin-right (when right-menu? marg)
                :height       "8rem"
                :background   "linear-gradient(180deg,var(--content-transp-top) 0%,
                                                      var(--content-transp-bottom) 4rem,
                                                      var(--content-transp) 100%)"}}
       [booking.common-views/header-line route false v]])))

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

(defn- name-badge []
  (let [account-email (rf/subscribe [:lab/username-or-fakename])]
    [:div.absolute.text-white.z-20
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

(defn new-version-available::dialog [_]
  [sc/dropdown-dialog'
   {:style {:position   :relative
            :overflow   :auto
            :z-index    20
            :max-height "80vh"}}
   [sc/col-space-8 {:class [:py-6 :-mx-6 :px-4 :mb-1]
                    :style {:background-color           "var(--toolbar)"
                            :border-bottom-left-radius  "var(--radius-1)"
                            :border-bottom-right-radius "var(--radius-1)"}}
    [sc/title {:class [:bold]} "Oppfrisk nettsiden"]
    [sc/col-space-4
     [sc/text1 "Det finnes en oppdatering av denne nettsiden — vil du laste ned den oppdaterte utgaven?"]

     [schpaa.style.hoc.page-controlpanel/togglepanel-local "Mer informasjon"
      (fn [_]
        [sc/col-space-4
         [sc/text1 "Du får denne meldingen fordi dette nettstedet er under utvikling og oppdateringene skjer daglig og vi vil at du alltid skal bruke den siste utgaven."]
         [sc/text2 [:span "Hvis du bruker Windows kan du trykke " [sc/as-shortcut "ctrl-r"] " (eller " [sc/as-shortcut "\u2318-r"] " på MacOS) for å laste inn siden på nytt."]]
         [sc/text2 [:span "Du kan også klikke/trykke utenfor dette vinduet eller trykke på "] [sc/as-shortcut "ESC"] " hvis du ikke vil bli avbrutt med det du holdt på med."]])]

     (let [link @(rf/subscribe [:kee-frame/route])
           addr (or (some-> link :path) "")
           path (str (.-protocol js/window.location) "//" (.-host js/window.location) addr)]
       [sc/text1 path]
       [sc/row-ec
        [hoc.buttons/cta {:on-click #(js/window.location.assign path)} "Ja takk!"]])]]])
(declare set-ref scroll-fn)

(defonce a (r/atom nil))

(defn check-latest-version []
  (let [version-timestamp (db/on-value-reaction {:path ["system" "timestamp"]})]
    (try
      (if-let [app-timestamp (some-> booking.data/DATE t/date-time)]
        (if-let [db-timestamp (some-> version-timestamp deref t/date-time)]
          (when true                                        ;(t/< app-timestamp db-timestamp)
            (widgets/open-slideout new-version-available::dialog)
            #_(rf/dispatch [:modal.slideout/show {:content-fn new-version-available::dialog}]))))
      (catch js/Error _))))

(defn page-boundary [r {:keys [frontpage]} & contents]
  (let [menu-right? (schpaa.state/listen :lab/menu-position-right)
        with-caption? (schpaa.state/listen :app/toolbar-with-caption)
        admin? (rf/subscribe [:lab/admin-access])]
    (r/create-class
      {:display-name        "page-boundary"
       :component-did-mount (fn [_]
                              (set-ref
                                js/document
                                #_(.getElementById js/document "inner-document") a scroll-fn))
       :reagent-render
       (fn [r _ & contents]
         [:<>
          ;popups
          [:div.noprint
           [booking.modals.boatinput/render-boatinput]
           [booking.modals.centered/render]
           [booking.modals.slideout/render]
           [booking.modals.commandpalette/window-anchor]]

          (let [marg (if @menu-right? (if @with-caption? :sm:mr-56 :sm:mr-16)
                                      (if @with-caption? :sm:ml-56 :sm:ml-16))
                field-width (if @with-caption? :sm:w-56 :sm:w-16)]
            (if @menu-right?
              [:div
               [:div#inner-document {:class [marg]} contents]
               [:div.fixed.inset-y-0.top-0.bottom-0.bg-alt.hidden.sm:block.noprint
                {:class [field-width]
                 :style {:right 0}}
                [vertical-toolbar true]]]
              [:div
               [:div#inner-document {:class [marg]} contents]
               [:div.fixed.top-0.bottom-0.bg-altx.left-0.hidden.sm:block.noprint
                {:class [field-width :print:hidden]
                 :style {:left 0}}
                [vertical-toolbar false]]]))

          [booking.common-views/header @scrollpos]])})))

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

(defonce z (atom nil))

(defn set-ref [element a scroll-fn]
  (when-not @a
    ;(tap> {:set-ref-on @a})
    (.addEventListener element "scroll"
                       (fn [e]
                         (reset! z e)
                         (tap> {:azz (.. e -target)} #_(.-scrollY element) #_(.. e -target -scrollTop)  #_(-> e .-target .-scrollTop))
                         (scroll-fn e)))
    (reset! a element)
    (tap> {:set-ref a})))

(def max-width "54ch")

(defn scroll-fn [e]
  (let [v (.. js/window -scrollY)]
    #_(if (< 50 v)
        (do))
    ;(rf/dispatch [:lab/we-know-how-to-scroll true])
    ;(rf/dispatch [:lab/close-menu])))
    (reset! scrollpos v)
    (tap> ["scroll-pos" v])))

(defn- height-calculation! []
  (let [h @(rf/subscribe [:breaking-point.core/mobile?])]
    (tap> {:h h})
    (str "calc(100vh - " (if h 11 4) "rem)")))

(defn- render-frontpage [r {:keys [render]} v]
  [render r])

(defn- render-normal [r {:keys [frontpage render render-fullwidth panel always-panel panel-title] :as m} admin?]
  (let [pagename (some-> r :data :name)
        users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        have-access? (booking.common-views/matches-access r users-access-tokens)
        modify? (booking.access/can-modify? r users-access-tokens)]
    (if-not have-access?
      [no-access-view r]
      [:<>
       (cond
         render
         [sc/col-space-8
          {:style {:padding-top   "8rem"
                   :margin-inline "auto"
                   :min-height    "100vh"
                   :max-width     "min(calc(100% - 2rem), 56ch)"}}
          (when (fn? panel)
            [hoc.panel/togglepanel pagename (or panel-title "lenker & valg") panel modify?])
          (when always-panel
            [:div {:style {:xz-index -10}} [always-panel modify?]])
          [render r m]]

         render-fullwidth
         [sc/col-space-8
          {:style {:padding-top "8rem"
                   :width       "100%"
                   :min-height  "100vh"
                   :max-width   "100%"}}
          (when (fn? panel)
            [:div.mx-auto
             [:div.-debug {:style {:margin-left "1rem"
                                   :width       "50ch"
                                   :max-width   "min(calc(100% - 2rem), 36ch)"}}
              [hoc.panel/togglepanel pagename (or panel-title "lenker & valg") panel modify?]]])
          [:div.z-0 (when always-panel
                      [always-panel modify?])]
          [render-fullwidth]])

       [widgets/after-content]

       (when (or goog.DEBUG @admin?)
         [:div.fixed.bottom-0.noprint
          {:style {:z-index   10
                   :left      :50%
                   :transform "translateX(-50%)"}}
          [name-badge]])

       [:div.sticky.bottom-0.noprint [bottom-toolbar]]])))

(defn +page-builder
  "


    Takes a route and a map with a description of what goes into a page and gives
    a hiccup description of said page.

    See `booking.spa/routing-table`

    -> The map consists of these keys:
    frontpage        --  is this the frontpage? Special case that gives special
                         treatment to the header among other things.
    render           --  fn that returns hiccup
    render-fullwidth --  fn that returns hiccup that ignores margins


  "
  [r {:keys [frontpage render] :as m}]
  (let [admin? (rf/subscribe [:lab/admin-access])]
    (let [v (- 1 (/ (- (+ @scrollpos 0.001) 30) 70))]
      [:<>
       [check-latest-version]
       [page-boundary r {:frontpage frontpage}
        (if frontpage
          [render r]
          [render-normal r m admin?])]])))