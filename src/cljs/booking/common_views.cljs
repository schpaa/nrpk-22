(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reitit.core :as reitit]
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
            [booking.common-widgets :refer [vertical-button]]
            [booking.modals.boatinput]
            [booking.modals.feedback]
            [booking.modals.commandpalette]
            [booking.modals.slideout]
            [booking.modals.centered]
            [booking.modals.mainmenu :refer [main-menu]]
            [booking.account]
            [tick.core :as t]))

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
  {:background "var(--content)"
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
        :button       (fn [open]
                        [scb/round-mainpage {:on-click toggle-mainmenu} [sc/corner-symbol "?"]])}])))

;endregion

;region toolbars

(defn vertical-toolbar-definitions []
  (let [admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        left-side? (schpaa.state/listen :lab/menu-position-right)
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    [{:icon-fn   #(sc/icon-large ico/new-home)
      :on-click  #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class     #(if (= % :r.oversikt) :oversikt :active)
      :page-name #(some #{%} [:r.forsiden :r.oversikt])}

     {:icon-fn   (fn [] (sc/icon-large ico/user))
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :page-name :r.user}

     #_(when (or @admin? @booking?)
         {:icon-fn   (fn [] (sc/icon-large ico/booking))
          :on-click  #(rf/dispatch [:app/navigate-to [:r.booking]])
          :page-name :r.booking})

     (when (or @admin? @nokkelvakt)
       {:icon      ico/mystery1
        :badge     (fn [_] (-> nil))
        :disabled  false
        :page-name :r.utlan
        :on-click  #(rf/dispatch [:app/navigate-to [:r.utlan]] #_[:lab/toggle-boatpanel])})

     (when (or @admin? @nokkelvakt)
       {:icon-fn   (fn [] (sc/icon-large ico/nokkelvakt))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.nokkelvakt]])
        :page-name :r.nokkelvakt})

     (when (or @member? @admin? @registered?)
       {:icon-fn   (fn [] (sc/icon-large ico/yearwheel))
        :on-click  #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
        :page-name :r.yearwheel})
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
       {:tall-height true
        :icon-fn     (fn [] (sc/icon-large ico/fileman))
        :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
        :page-name   :r.fileman-temporary})
     (when (or @admin?)
       {:icon-fn     (fn [] (sc/icon-large ico/users))
        :on-click    #(rf/dispatch [:app/navigate-to [(if (= % :r.users) :r.presence :r.users)]])
        :tall-height true
        :class       #(if (= % :r.users) :active :oversikt)
        :page-name   #(some #{%} [:r.users :r.presence])})
     {:tall-height true
      :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-large
                              (if @st ico/commandPaletteOpen ico/commandPaletteClosed))))
      :on-click    #(rf/dispatch [:app/toggle-command-palette])}]))

(defn horizontal-toolbar-definitions [uid]
  (let [admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]

    [{:icon-fn   #(sc/icon-large ico/new-home)
      :on-click  #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class     #(if (= % :r.oversikt) :oversikt :active)
      :page-name #(some #{%} [:r.forsiden :r.oversikt])}

     {:xicon    ico/mystery2
      :disabled true}

     {:icon-fn   (fn [_] (let [st @(rf/subscribe [:lab/modal-selector])]
                           (sc/icon-large (if st ico/commandPaletteOpen ico/commandPaletteClosed))))
      :special   true
      :on-click  #(rf/dispatch [:app/toggle-command-palette])
      :page-name :r.debug}

     {:icon      ico/mystery1
      :badge     (fn [_] (-> nil))
      :disabled  false
      :page-name :r.utlan
      :on-click  #(rf/dispatch [:app/navigate-to [:r.utlan]] #_[:lab/toggle-boatpanel])}

     {:icon      ico/user
      :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
      :disabled  (not (some? @registered?))
      :page-name :r.user}]))

(defn vertical-toolbar [left-side?]
  (let [numberinput? (rf/subscribe [:lab/number-input])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.w-full.relative

       ;; force the toolbar to stay on top when boat-panel is displayed
       (into [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.relative
              ;{:style {:box-shadow "var(--inner-shadow-3)"}}
              {:style {:z-index     1211
                       :padding-top "var(--size-0)"
                       :box-shadow  (if @numberinput? "var(--shadow-5)" "var(--inner-shadow-2)")
                       :background  "var(--toolbar)"}}]
             (map #(if (keyword? %)
                     [:div.grow]
                     [vertical-button %])
                  (remove nil? (vertical-toolbar-definitions))))
       #_(if left-side?
           [:div.absolute.left-4.inset-0.z-10.-debug
            {:style {:width          "298px"
                     :pointer-events :none}}
            [boatinput-menu {:position :left-side}]]
           [:div.absolute.left-4.inset-0.z-10.-debug
            {:style {:width          "298px"
                     :pointer-events :none}}
            [boatinput-menu {:position :right-side}]])])))

(defn bottom-toolbar []
  (let [;search (rf/subscribe [:lab/is-search-running?])
        user-auth (rf/subscribe [::db/user-auth])
        has-chrome? (rf/subscribe [:lab/has-chrome])]
    (when (and @has-chrome? #_(not @search) @(rf/subscribe [:lab/at-least-registered]))
      #_[sc/bottom-toolbar-style
         ;:div.sm:hidden.flex.gap-0.border-t
         #_{:xstyle {:width           "100%"
                     :justify-content :space-around
                     :height          "var(--size-10)"
                     :min-height      "var(--size-10)"
                     :padding-inline  "var(--size-2)"
                     :box-shadow      "var(--inner-shadow-2)"
                     :border-color    "var(--toolbar-)"
                     :background      "var(--toolbar)"}}]

      (into [sc/bottom-toolbar-style]
            (mapv vertical-button
                  (horizontal-toolbar-definitions (:uid @user-auth))))
      #_[:div.absolute.mx-auto.inset-y-0.overflow-y-auto
         {:style {:width          "298px"
                  :pointer-events :none}}
         [boatinput-menu {:position :mobile}]])))

;endregion

(defn lookup-page-ref-from-name [link]
  {:pre [(keyword? link)]}
  {:link link
   :text (or (some->> link
                      (reitit/match-by-name (reitit/router booking.routes/routes))
                      :data
                      :header)
             "wtf?")})

(defn compute-pagetitles [r]
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

(defn header-line [r frontpage v]
  (let [switch? (schpaa.state/listen :lab/menu-position-right)]
    [err-boundary
     [:div.relative

      [:div.w-full.h-16.absolute.inset-x-0
       {:style {:opacity             (- 1 v)
                :background          (if frontpage "var(--toolbar)" "var(--content)")
                :border-bottom-color (if frontpage "var(--toolbar-)" "var(--content)")
                :border-bottom-style "solid"
                :border-bottom-width "1px"}}]

      (let [login (fn []
                    (when-not @(rf/subscribe [:lab/at-least-registered])
                      [:<>
                       [:div.grow]
                       [schpaa.style.hoc.buttons/cta {:style    {:padding-inline "var(--size-4)"
                                                                 :width          "min-content"
                                                                 :box-shadow     "var(--shadow-1)"}
                                                      :class    [:flex :xflex-wrap :items-center :gap-x-1 :h-10 :xspace-y-0 :xshrink-0]
                                                      :on-click #(rf/dispatch [:app/login])}
                        "Logg inn"]]))
            location (fn [] [:div.flex.flex-col.truncatex.px-2.w-auto.z-100
                             {:class [(when-not @switch? :text-right)]}
                             (let [titles (compute-pagetitles r)]
                               (if (vector? titles)
                                 [sc/col {:style {:justify-content :start}}
                                  [sc/title1 {:class [:truncate]
                                              :style {:font-weight "var(--font-weight-5)"}} (last titles)]
                                  (when (< 1 (count titles))
                                    (let [{:keys [text link]} (first titles)]
                                      [:div [sc/subtext-with-link {:href (k/path-for [link])} text]]))]
                                 [sc/col
                                  [sc/title1 titles]]))])]
        [(if frontpage header-top-frontpage header-top)
         (if @switch?
           [:<>
            [location]
            [:div.grow]
            (if frontpage
              [:div.grow]
              [login])
            [main-menu @switch?]]
           [:<>
            [main-menu @switch?]
            (if frontpage
              [:div.grow]
              [login])
            [:div.grow]
            [location]])])]]))

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

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])]
    [:div.z-1 {:style {:background "var(--gray-10)"}}
     [:div.mx-auto.max-w-xl.py-8
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
         (when @(rf/subscribe [:lab/admin-access])
           [hoc.buttons/danger-pill
            {:on-click #(db/database-update {:path  ["system"]
                                             :value {"timestamp" booking.data/DATE}})}
            "Aktiver versjon"])]]]]]))


(defn page-boundary [r {:keys [frontpage] :as options} & contents]
  (let [switch? (schpaa.state/listen :lab/menu-position-right)
        numberinput2? (rf/subscribe [:lab/number-input2])
        user-auth (rf/subscribe [::db/user-auth])
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

          [booking.modals.slideout/render]
          [booking.modals.centered/render]
          [booking.modals.boatinput/render-boatinput]
          [booking.modals.commandpalette/window-anchor]

          (let [content [:div.flex.flex-col
                         {:style {:flex "1 1 auto"}}
                         (when-not frontpage
                           [header-line r false])
                         [:div.flex.flex-col.xoverflow-y-auto.h-full
                          {:class (when-not frontpage [:overflow-y-auto])}
                          (if false #_(and
                                        @(rf/subscribe [:lab/is-search-running?])
                                        @(rf/subscribe [:lab/in-search-mode?]))
                            [:div
                             {:style {:flex "1 0 auto"}}
                             [:div
                              {:style {:background "var(--toolbar)"}}
                              [search-result]]
                             [sc/row-center {:class [:py-4]}
                              [hoc.buttons/regular {:on-click #(rf/dispatch [:lab/quit-search])} "Lukk"]]
                             [:div.absolute.bottom-8.sm:bottom-7.right-4
                              [sc/row-end {:class [:pt-4]}
                               (bottom-menu)]]]
                            [:<>
                             [:div
                              {:style {:background-color "var(--content)"
                                       :flex             "1 1 auto"}}
                              contents]
                             (when-not frontpage [after-content])])]

                         #_(when @numberinput2?
                             [:div.xs:hidden.flex.w-screen.bg-white
                              [boatinput-menu {:position :mobile}]])


                         [bottom-toolbar]]]
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
      ;[sc/small1 {:style {:white-space :nowrap}} "Du har --> " (str @(rf/subscribe [:lab/all-access-tokens]))]
      ;[sc/small1 {:style {:white-space :nowrap}} "Du trenger --> " (str required-access)]
      #_[l/ppre-x
         (matches-access r @(rf/subscribe [:lab/all-access-tokens]))
         (-> r :data :access)
         @(rf/subscribe [:lab/all-access-tokens])]

      [sc/title2 "For at vi skal kunne vise deg denne siden må du"]
      [sc/text1
       (cond
         (some #{:registered} (first required-access)) "Være innlogget og ha registrert deg med grunnleggende informasjon om deg selv."
         (some #{:waitinglist} (first required-access)) "Være påmeldt innmeldingskurs."
         :else #_(some #{:member} (first required-access)) "Være medlem i NRPK.")]
      [sc/text1
       (cond
         (some #{:nøkkelvakt} (last required-access)) "Være godkjent som nøkkelvakt med den kontoen du har logget inn med."
         (some #{:admin} (last required-access)) "Ha administrators rettigheter.")]

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
                                            #_[l/ppre-x
                                               {:db-timestamp  (times.api/arrival-date db-timestamp)
                                                :app-timestamp (times.api/arrival-date app-timestamp)
                                                :need-reload?  (t/< app-timestamp db-timestamp)}]
                                            #_[sc/small0 "Denne meldingen vil komme opp hver gang du går til en ny lenke på dette nettstedet. side, inntil du ."]]]])}]))))
            (catch js/Error _))
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
                   [sc/col-space-8
                    {:class [:py-8]
                     :style {:flex             "1 1 auto"
                             :background-color "var(--content)"}}

                    ;modification could happen here
                    (when (fn? panel)
                      ;(when-some [p (panel)])
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
                         [hoc.panel/togglepanel pagename (or panel-title "innstillinger") panel modify?]]]])

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
                     [:div.min-h-full.grow                  ;.max-w-xl ?
                      (if render-fullwidth
                        [render-fullwidth r]
                        [:div.mx-4 [render r]])]]]))]))])})))
