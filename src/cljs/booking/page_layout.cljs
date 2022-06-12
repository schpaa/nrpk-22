(ns booking.page-layout
  (:require
    [clojure.string :as str]
    [goog.dom :as dom]
    [tick.core :as t]
    [kee-frame.error]
    [reagent.core :as r]
    [re-frame.core :as rf]

    [db.core :as db]

    [schpaa.style.ornament :as sc]
    [schpaa.style.hoc.page-controlpanel :as hoc.panel]
    [schpaa.style.hoc.buttons :as field]
    [schpaa.debug :as l]
    [schpaa.style.input :as sci]

    [booking.access]
    [booking.account]
    [booking.common-widgets :as widgets]
    [booking.content.booking-blog]
    [booking.data]
    [booking.fiddle]
    [booking.fileman]
    [booking.header]
    [booking.ico :as ico]
    [booking.modals.boatinput]
    [booking.modals.feedback]
    [booking.modals.commandpalette]
    [booking.modals.slideout]
    [booking.modals.centered]
    [booking.routes]
    [booking.toolbar]))

;; scrolling

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
                        [field/pill
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
              (f-icon [:lab/set-sim-type :none] :none [field/icon-and-caption ico/anonymous "anonym"])
              (f-icon [:lab/set-sim-type :registered] :registered [field/icon-and-caption ico/user "registrert"])
              (f-icon [:lab/set-sim-type :waitinglist] :waitinglist [field/icon-with-caption-and-badge ico/waitinglist "påmeldt" 123])
              (f-icon [:lab/set-sim-type :member] :member [field/icon-and-caption ico/member "medlem"])

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
  [sc/dialog-dropdown
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
        [field/cta {:on-click #(js/window.location.assign path)} "Ja takk!"]])]]])

(defn check-latest-version []
  (let [version-timestamp (db/on-value-reaction {:path ["system" "timestamp"]})]
    (try
      (if-let [app-timestamp (some-> booking.data/DATE t/date-time)]
        (if-let [db-timestamp (some-> version-timestamp deref t/date-time)]
          (when (t/< app-timestamp db-timestamp)
            (widgets/open-slideout new-version-available::dialog))))
      (catch js/Error _))))

;; page layout

(defn page-boundary
  "core layout"
  [r {:keys [headline-plugin]} & contents]
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
             [booking.modals.boatinput/boatinput-dialog]
             [booking.modals.centered/render]
             [booking.modals.slideout/render]
             [booking.modals.commandpalette/window-anchor]]

            (cond
              hidden-menu?
              (let [marg :sm:mx-0]
                [:div#inner-document
                 {:style {:background "var(--content)"}
                  :class [marg]} contents])

              (and right-menu? (not hidden-menu?))
              (let [marg (if menu-caption?
                           :sm:mr-56
                           :sm:mr-20)
                    field-width (when-not hidden-menu?
                                  (if menu-caption?
                                    :sm:w-56
                                    :sm:w-20))]
                [:div
                 [:div#inner-document
                  {:class [marg]
                   :style {:background "var(--content)"}}
                  contents]
                 [:div.fixed.inset-y-0.top-0.bottom-0.hidden.sm:block.noprint
                  {:class [field-width]
                   :style {:right            0
                           :background-color "var(--toolbar)"}}
                  [booking.toolbar/vertical-toolbar true]]])

              :else
              (let [marg (if menu-caption?
                           :sm:ml-56
                           :sm:ml-16)
                    field-width (when-not hidden-menu?
                                  (if menu-caption?
                                    :sm:w-56
                                    :sm:w-16))]
                [:div
                 [:div#inner-document
                  {:style {:background "var(--content)"
                           :box-shadow "var(--inner-shadow-2)"}
                   :class [marg]}
                  contents]
                 [:div.fixed.top-0.bottom-0.left-0.hidden.sm:block.noprint
                  {:class [field-width :print:hidden]
                   :style {:left 0}}
                  [booking.toolbar/vertical-toolbar false]]]))

            [booking.header/header r @scrollpos headline-plugin]]))})))


(defn matches-access "" [r [status _access :as _all-access-tokens]]
  (let [[req-status _req-access :as req-tuple] (-> r :data :access)]
    ;todo
    (if goog.DEBUG
      true
      (if req-tuple
        (some #{status} (if (vector? req-status) req-status [req-status]))
        true))))

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
     {:style {:padding-block "6rem"
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
        have-access? (booking.page-layout/matches-access r users-access-tokens)]
    (if-not have-access?
      [widgets/no-access-view r]
      [:<>
       (cond
         render
         [normal-width r m]

         render-halfwidth
         [full-width r m]

         render-fullwidth
         [full-width r m]

         :render-not-defined
         (r/with-let [st (r/atom {})
                      field-1 (r/cursor st [:field-1])
                      field-2 (r/cursor st [:field-2])
                      field-3 (r/cursor st [:field-3])]
           [:div {:class [:pt-24 :mx-auto]
                  :style {:max-width "min(calc(100% - 1rem), calc(768px - 3rem))"}}
            [sc/surface-a {:class [:mx-2]}
             [sc/co {:style {:color      "var(--red-5)"
                             :row-gap    "1rem"
                             :height     "24rem"
                             :min-height "calc(80vh - 6rem)"
                             :padding    "0"}}
              [sc/title {:style {:height "100%"}}
               (str :error/render-not-defined)]
              [sc/row-field {:class []
                             :style {:justify-content :start
                                     :flex-wrap       :wrap :row-gap "1rem"}}
               [field/textinput
                {:cursor field-1
                 :class  [:on-bright]}
                "INPUT"
                :field-name]
               [field/textinput
                {:cursor field-2}
                :label
                :field-name]
               [field/textinput
                {:cursor  field-3
                 :xerrors {:field-name ["mangler"]}}
                "Årsak"
                :field-name]
               [field/textinput
                {:xerrors     {:field-name ["?"]}
                 :placeholder "tests"}
                "Antall"
                :field-name]

               [field/combobox "Komboboks"]]

              ;[:div {:style {:font-size "60%"}} [l/pre r]]
              [sc/text "Move along nothing to see"]]]]))

       [widgets/after-content]

       (when booking.data/SHOW-BADGE
         [:div.fixed.bottom-0.noprint
          {:style {:z-index   10
                   :left      :50%
                   :transform "translateX(-50%)"}}
          [name-badge]])

       [:div.sticky.bottom-0.noprint.z-100
        [booking.toolbar/bottom-toolbar]]])))

(defn +page-builder
  "Takes a route and a map with a description of what goes into a page and returns
   a hiccup description of said page.

   See `booking.spa/routing-table`

   -> The map consists of these keys:
   frontpage        --  is this the frontpage? Special case that gives special
                        treatment to the header among other things.
   render           --  fn that returns hiccup
   render-fullwidth --  fn that returns hiccup that ignores the left/right-margin
 "
  [r {:keys [frontpage render headline-plugin] :as m}]
  (let [admin? (rf/subscribe [:lab/admin-access])]
    [:<>
     [check-latest-version]
     [page-boundary r {:frontpage frontpage
                       :headline-plugin headline-plugin}
      (if frontpage
        [render r]
        [render-normal r m admin?])]]))
