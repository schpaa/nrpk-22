(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reitit.core :as reitit]
            [reagent.ratom]
    ;[cljs.core.async :refer-macros [go-loop go]]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog :refer [open-dialog-logoutcommand
                                         open-dialog-sampleautomessage
                                         open-dialog-config
                                         open-dialog-sampleformmessage]]
            [booking.content.booking-blog]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
    ;["react-qr-code" :default QRCode]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [db.core :as db]
    ;[goog.events.KeyCodes :as keycodes]
    ;[times.api :as ta]
    ;[tick.core :as t]
            [schpaa.style.combobox]

            [booking.fileman]
    ;[schpaa.icon :as icon]
            [booking.boatinput]
            [booking.mainmenu :refer [main-menu boatinput-menu boatinput-sidebar]]
            [booking.search :refer [search-menu]]
    ;[schpaa.debug :as l]
            [kee-frame.core :as k]
            [booking.routes]
            [schpaa.style.hoc.page-controlpanel :as hoc.panel]
            [schpaa.style.hoc.toggles :as hoc.toggles]
    ;todo: problem with codelocality
    #_[booking.yearwheel]))

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

;region

(o/defstyled sidebar :div
  :cursor-pointer
  {:text-transform "lowercase"
   :font-family    "IBM Plex Sans"
   :font-size      "var(--font-size-5)"
   :font-weight    "var(--font-weight-5)"})

(defn vertical-toolbar
  "to add a badge (of unseen posts for instance):

  #(let [c (booking.content.booking-blog/count-unseen uid)]
    (when (pos? c) c))
  "
  [uid]
  [{:icon      solid/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      solid/CalendarIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.calendar]])
    :page-name :r.calendar}
   {:icon      solid/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      solid/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
    :page-name :r.booking.oversikt}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(let [c (booking.content.booking-blog/count-unseen uid)]
                  (when (pos? c) c))
    :page-name :r.booking-blog}
   {:icon      solid/ShieldCheckIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking.retningslinjer]])
    :page-name :r.booking.retningslinjer}
   {:icon-fn   (fn [] [sidebar "A"])
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
   :background    "var(--red-6)"})



(defn vertical-toolbar-right [uid]
  [#_{:icon-fn (fn []
                 [main-menu])}

   {;:tall-height true
    :icon-fn    (fn [] [centered-thing-red
                        (sc/icon-large
                          [:> outline/HomeIcon])])
    :special    true
    :on-click   #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :xpage-name :r.forsiden}

   {:icon-fn   (fn [] [sidebar "N"])
    :on-click  #(rf/dispatch [:app/navigate-to [:r.welcome]])
    :page-name :r.welcome}

   {:icon-fn   (fn [] [sidebar "S"])
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking]])
    :page-name :r.booking}
   {:icon-fn   (fn [] [sidebar "Å"])
    :on-click  #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
    :page-name :r.yearwheel}

   nil
   {:icon-fn  (fn [] (let [st (rf/subscribe [:lab/number-input])]
                       [centered-thing
                        (sc/icon-large
                          [:> (if @st solid/ChevronRightIcon
                                      solid/ChevronLeftIcon)])]))
    :special  true
    :on-click #(rf/dispatch [:lab/toggle-number-input])}

   #_{:tall-height true
      :icon        solid/FolderIcon

      :on-click    #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
      #_#_:badge #(let [c (booking.content.booking-blog/count-unseen uid)]
                    (when (pos? c) c))
      :page-name   :r.fileman-temporary}
   nil
   {:tall-height true
    :special     true
    :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                          (sc/icon-huge
                            [:> (if @st solid/InformationCircleIcon
                                        outline/InformationCircleIcon)])))
    :on-click    schpaa.style.dialog/open-selector}])

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

(defn vertical-button [{:keys [tall-height special icon icon-fn style on-click page-name color badge] :or {style {}}}]
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



;region header-control-panel related

(defn chevron-updown-toggle [st toggle]
  [sc/dim [scb/round-floating
           {:on-click #(do
                         (.stopPropagation %)
                         (toggle))}
           [sc/icon-tiny
            [:> (if st outline/ChevronUpIcon outline/ChevronDownIcon)]]]])

(def preferred-state (r/atom true))

(defn header-control-panel []
  (r/with-let [toggle (schpaa.state/listen :header-activity)
               do-toggle #(schpaa.state/toggle :header-activity)
               show-archived (r/atom false)]
    [sc/col {:style {:padding-block    "var(--size-1)"
                     :padding-inline   "var(--size-2)"

                     :background-color (when @toggle "var(--surface000)")}}
     [sc/row'
      {:on-click #(do
                    (do-toggle)
                    (.stopPropagation %))
       :class    [:x-debug]
       :style    {:padding "var(--size-1)"}}
      [sc/dim
       [sc/small {:style {:letter-spacing "var(--font-letterspacing-2)"
                          :text-transform :uppercase
                          :font-weight    "var(--font-weight-6)"}}
        "Visningsvalg"]]
      (chevron-updown-toggle @toggle do-toggle)]
     [:div {:class (into [:duration-200] (if @toggle [:h-32 :opacity-100] [:pointer-events-none :h-0 :opacity-0]))}
      [sc/col-space-2
       [sc/col {:class [:space-y-2]}
        [sc/row' {:class [:items-center]}
         [schpaa.style.switch/small-switch-example
          {:!value  show-archived
           :caption [sc/subtext "Vis skjulte"]}]]
        [sc/row' {:class [:items-center]}
         [schpaa.style.switch/small-switch-example
          {:!value  show-archived
           :caption [sc/subtext "Vis skjulte"]}]]
        [sc/row' {:class [:items-center]}
         [schpaa.style.switch/small-switch-example
          {:!value  show-archived
           :caption [sc/subtext "Vis skjulte"]}]]]
       #_[sc/dim
          [sc/row-stretch
           [sc/subtext "Se innhold"]
           [sc/subtext "Se slettede"]
           [sc/subtext "Se skjulte"]]]]]
     (when @toggle [:hr])]))

;endregion

(defn header-line [r]
  (let [st (schpaa.state/listen :top-toggle)
        toggle #(schpaa.state/toggle :top-toggle)]
    [sc/col {:class [:border-b :duration-200]
             :style {:background   "var(--surface00)"
                     :border-color "var(--surface0)"}}
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
                                [sc/title e]
                                (let [{:keys [text link]} e]
                                  [sc/subtext-with-link {:href (k/path-for [link])} text]))))]
                [sc/title titles])]]
            [:div.xs:block.sm:hidden.grow
             (if (vector? titles)
               [sc/col-space-1
                (when (< 1 (count titles))
                  (let [{:keys [text link]} (first titles)]
                    [:div [sc/subtext-with-link {:href (k/path-for [link])} text]]))
                [sc/title (last titles)]]
               [sc/col
                [sc/title titles]])]]))

       [search-menu]
       [main-menu]]]]))


(defn page-boundary [r & contents]
  (let [user-auth (rf/subscribe [::db/user-auth])
        mobile? (= :mobile @(rf/subscribe [:breaking-point.core/screen]))
        numberinput? (rf/subscribe [:lab/number-input])
        has-chrome? (rf/subscribe [:lab/has-chrome])
        left-aligned (schpaa.state/listen :activitylist/left-aligned)]
    (r/create-class
      {:display-name "booking-page-boundary"
       :component-did-mount
       (fn [_]
         (tap> "page-boundary component-did-mount")
         (when-let [el (.getElementById js/document "inner-document")]
           (.focus el)))
       :reagent-render
       (fn [r & contents]
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

           (when @has-chrome?
             [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.justify-around.items-center.flex-col.border-r
              {:style {:padding-top  "var(--size-0)"
                       :box-shadow   "var(--inner-shadow-3)"
                       :border-color "var(--surface0)"
                       :background   "var(--surface00)"}}
              (into [:<>] (map #(if (nil? %)
                                  [:div.grow]
                                  [vertical-button %]) (vertical-toolbar (:uid @user-auth))))])
           ;vertical toolbar
           [:div.flex-col.flex.h-full.w-full
            [header-line r]

            ;content
            [:div.overflow-y-auto.h-full.focus:outline-none.grow
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
               (if (map? (first contents))
                 [:div.w-auto (:whole (first contents))]
                 [:div.h-full.xmx-4.w-full
                  contents
                  [:div.py-8.h-32]]))]

            ;horizontal toolbar
            (when @has-chrome?
              [:div.h-20.w-full.sm:hidden.flex.justify-around.items-center.border-t
               {:style {:box-shadow   "var(--inner-shadow-3)"

                        :border-color "var(--surface0)"
                        :background   "var(--surface00)"}}
               (into [:<>] (map horizontal-button
                                (horizontal-toolbar (:uid @user-auth))))])]
           (when @has-chrome?
             [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.relative
              [:div.absolute.right-0.inset-y-0.w-full.h-full.flex.flex-col.border-l
               {:style {:z-index      1211
                        :padding-top  "var(--size-0)"
                        :box-shadow   (if @numberinput? "var(--shadow-5)"
                                                        "var(--inner-shadow-1)")
                        :border-color "var(--surface0)"
                        :background   "var(--surface00)"}}
               (into [:<>] (map #(if (nil? %)
                                   [:div.grow]
                                   [vertical-button %]) (vertical-toolbar-right (:uid @user-auth))))]
              [:div.absolute.right-16.xl:right-20.inset-y-0
               {:style {:width          "298px"
                        :pointer-events :none}}
               [boatinput-menu]]])
           #_[:div.hidden.sm:block
              [:div.pl-4.h-fullx.flex.items-center.justify-end.h-full
               {:style {:box-shadow   "var(--inner-shadow-3)"
                        :border-color "var(--surface0)"
                        :background   "var(--surface00)"}}
               [:div [boatinput-sidebar]]]]]])})))

(def max-width "40ch")

(defprotocol PerstateP
  (toggle [t])
  (listen [t]))

(defrecord Perstate [tag]
  PerstateP
  (toggle [t]
    #(schpaa.state/toggle tag))
  (listen [t]
    (schpaa.state/listen tag)))

(defn +page-builder [r {:keys [render render-fullwidth panel always-panel]}]
  (let [pagename (some-> r :data :name)
        perstate (Perstate. pagename)
        numberinput (rf/subscribe [:lab/number-input])
        left-aligned (schpaa.state/listen :activitylist/left-aligned)]
    [page-boundary r
     [sc/col-space-8 {:class [:py-8]}
      (when panel
        [:div.mx-4
         [:div.mx-auto
          {:style {:width     "100%"
                   :max-width max-width}}
          [hoc.panel/togglepanel
           {:open?   @(listen perstate)
            :toggle  (toggle perstate)
            :title   "kontrollpanel"
            :content (fn [c] [panel])}]]])
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
          [render r])]]]]))