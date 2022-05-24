(ns booking.common-views
  (:require
    [goog.dom :as dom]
    [kee-frame.error]
    [reagent.ratom]
    [lambdaisland.ornament :as o]
    [schpaa.style.ornament :as sc]
    [booking.content.booking-blog]
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
    [booking.account]
    [tick.core :as t]
    [clojure.string :as str]
    [booking.common-widgets :as widgets]
    [booking.fiddle]
    [booking.toolbar :as toolbar]
    [booking.header]))

;; state

(defonce a (r/atom nil))

(def scrollpos (r/atom nil))

(defn scroll-fn [_]
  (reset! scrollpos (.. js/window -scrollY)))

(defn set-ref [element a scroll-fn]
  (when-not @a
    (.addEventListener element "scroll" (fn [e] (scroll-fn e)))
    (reset! a element)))

;;

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

(defn check-latest-version []
  (let [version-timestamp (db/on-value-reaction {:path ["system" "timestamp"]})]
    (try
      (if-let [app-timestamp (some-> booking.data/DATE t/date-time)]
        (if-let [db-timestamp (some-> version-timestamp deref t/date-time)]
          (when (t/< app-timestamp db-timestamp)
            (widgets/open-slideout new-version-available::dialog))))
      (catch js/Error _))))

;; page layout

(defn page-boundary [r {:keys [headline-plugin]} & contents]
  (let [geo (rf/subscribe [:lab/screen-geometry])]
    (r/create-class
      {:display-name        "page-boundary"
       :component-did-mount (fn [_]
                              (set-ref js/document a scroll-fn))
       :reagent-render
       (fn [r _ & contents]
         (let [{:keys [right-menu? mobile? menu-caption? hidden-menu?]} @geo]
           [:<>
            ;popups
            [:div.noprint
             [booking.modals.boatinput/render-boatinput]
             [booking.modals.centered/render]
             [booking.modals.slideout/render]
             [booking.modals.commandpalette/window-anchor]]

            (cond
              hidden-menu?
              (let [marg :sm:mx-0]
                [:div
                 [:div#inner-document {:class [marg]} contents]])

              right-menu?
              (let [marg (if menu-caption?
                           :sm:mr-56
                           :sm:mr-16)
                    field-width (when-not hidden-menu?
                                  (if menu-caption?
                                    :sm:w-56
                                    :sm:w-16))]
                [:div
                 [:div#inner-document {:class [marg]} contents]
                 [:div.fixed.inset-y-0.top-0.bottom-0.bg-alt.hidden.sm:block.noprint
                  {:class [field-width]
                   :style {:right 0}}
                  [toolbar/vertical-toolbar true]]])

              :else
              (let [marg (if menu-caption?
                           :sm:ml-56
                           :sm:ml-16)
                    field-width (when-not hidden-menu?
                                  (if menu-caption?
                                    :sm:w-56
                                    :sm:w-16))]
                [:div
                 [:div#inner-document {:class [marg]} contents]
                 [:div.fixed.top-0.bottom-0.bg-altx.left-0.hidden.sm:block.noprint
                  {:class [field-width :print:hidden]
                   :style {:left 0}}
                  [toolbar/vertical-toolbar false]]]))

            [booking.header/header r @scrollpos headline-plugin]]))})))

(defn matches-access "" [r [status _access :as _all-access-tokens]]
  (let [[req-status _req-access :as req-tuple] (-> r :data :access)]
    (if req-tuple
      (some #{status} (if (vector? req-status) req-status [req-status]))
      true)))

(defn- render-frontpage [r {:keys [render]} v]
  [:div {:style {:padding-block "8rem"}} [render r]])

(defn- full-width [r {:keys [render-halfwidth render-fullwidth panel always-panel panel-title]}]
  (let [users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        modify? (booking.access/can-modify? r users-access-tokens)
        pagename (some-> r :data :name)]
    [sc/col-space-8
     {:style {:min-height  "90vh"
              :padding-top "8rem"
              :width       (if render-halfwidth "75%" "100%")
              :max-width   (if render-halfwidth "75%" "100%")}}
     (when (fn? panel)
       [:div.mx-auto
        [:div.-debug {:style {:margin-left "1rem"
                              :width       "50ch"
                              :max-width   (if render-halfwidth
                                             "min(calc(100% - 2rem), 56ch)"
                                             "min(calc(100% - 2rem), 36ch)")}}
         [hoc.panel/togglepanel pagename (or panel-title "lenker & valg") panel modify?]]])
     (when always-panel
       [always-panel modify?])
     [render-fullwidth]]))

(defn- normal-width [r {:keys [render panel always-panel panel-title] :as m} modify?]
  (let [users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        modify? (booking.access/can-modify? r users-access-tokens)
        pagename (some-> r :data :name)]
    [sc/col-space-8
     {:style {:padding-block "8rem"
              :margin-inline "auto"
              :min-height    "90vh"
              :max-width     "min(calc(100% - 1rem), calc(768px - 3rem))"}}
     (when (fn? panel)
       [hoc.panel/togglepanel pagename (or panel-title "lenker & valg") panel modify?])
     (when always-panel
       [always-panel modify?])
     [render r m]]))

(defn- render-normal [r {:keys [render render-halfwidth render-fullwidth] :as m} admin?]
  (let [users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        have-access? (booking.common-views/matches-access r users-access-tokens)]
    (if-not have-access?
      [no-access-view r]
      [:<>
       (cond
         render
         [normal-width r m]

         render-halfwidth
         [full-width r m]

         render-fullwidth
         [full-width r m]

         :render-not-defined
         [sc/title1 {:style {:color          "var(--red-5)"
                             :min-height     "90vh"
                             :padding-block  "8rem"
                             :padding-inline "1rem"}} :Render-not-defined])

       [widgets/after-content]

       (when (or goog.DEBUG @admin?)
         [:div.fixed.bottom-0.noprint
          {:style {:z-index   10
                   :left      :50%
                   :transform "translateX(-50%)"}}
          [name-badge]])

       [:div.sticky.bottom-0.noprint [toolbar/bottom-toolbar]]])))

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
  [r {:keys [frontpage render headline-plugin] :as m}]
  (let [admin? (rf/subscribe [:lab/admin-access])]
    [:<>
     [check-latest-version]
     [page-boundary r {:frontpage frontpage :headline-plugin headline-plugin}
      (if frontpage
        [render r]
        [render-normal r m admin?])]]))
