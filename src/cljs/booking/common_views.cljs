(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [reagent.ratom]

            [cljs.core.async :refer [chan take! put! >! <! timeout]]
            [cljs.core.async :refer-macros [go-loop go]]

            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog :refer [open-dialog-logoutcommand
                                         open-dialog-sampleautomessage
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
            [re-pressed.core :as rp]))

(defn better-mainmenu-definition [settings-atom]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    ;[icon label action disabled value]
    [[:menuitem {:icon      (sc/icon [:> solid/HomeIcon])
                 :label     "Forsiden"
                 :color     "var(--red-6)"
                 :highlight (= :r.forsiden current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/CalendarIcon])
                 :label     "Kalender"
                 :color     "var(--hva-er-nytt)"
                 :highlight (= :r.calendar current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.calendar]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ShieldCheckIcon])
                 :label     "Retningslinjer"
                 :color     "var(--blue-4)"
                 :highlight (= :r.retningslinjer current-page)
                 :action    nil
                 :disabled  false
                 :value     #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> solid/PlusIcon])
                 :label     "Ny booking"
                 :color     "var(--new-booking)"
                 :highlight (= :r.debug current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.debug]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ClockIcon])
                 :label     "Bookingoversikt"
                 :color     "var(--booking-oversikt)"
                 :highlight (= :r.oversikt current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.oversikt]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/MapIcon])
                 :label     "Turlogg"
                 :highlight false
                 :action    nil
                 :disabled  true
                 :value     #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> solid/BookOpenIcon])
                 :label     "Hva er nytt?"
                 :color     "var(--hva-er-nytt)"
                 :highlight (= :r.booking-blog current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
                 :disabled  false
                 :value     #()}]
     #_[:hr]
     #_[:menuitem {:icon      (sc/icon nil [:> solid/ArrowRightIcon])
                   :label     "Logg ut..."
                   :color     "var(--logg-ut)"
                   :highlight false
                   :action    #(open-dialog-logoutcommand)

                   :disabled  false
                   :value     #()}]
     [:space]
     [:div
      [:div.flex.items-center.gap-2.justify-between
       [scb2/normal-small "Logg in"]
       [scb2/cta-small "Meld på"]]]]))

(defn set-focus [el a]
  (tap> "set ref")
  (when-not @a
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

(defn search-menu []
  (let [a (r/atom nil)
        value (rf/subscribe [:lab/search-expression])
        search (rf/subscribe [:lab/in-search-mode?])]

    (r/create-class
      {:display-name         "search-widget"
       :component-did-update (fn [_]
                               (when @a (.focus @a)))
       :component-did-mount  (fn [c]
                               (when @a (.focus @a))
                               (.addEventListener @a "keydown"
                                                  (fn [event]
                                                    (do
                                                      (tap> [event.keyCode])
                                                      (if (= keycodes/ESC event.keyCode)
                                                        (do
                                                          (tap> "ESC")
                                                          (rf/dispatch [:lab/set-search-mode false])
                                                          (rf/dispatch [:lab/stop-search])
                                                          (rf/dispatch [:lab/set-search-expr ""]))))
                                                    (if (= keycodes/ENTER event.keyCode)
                                                      (do
                                                        (tap> "ENTER")
                                                        (rf/dispatch [:lab/start-search]))))))

       :reagent-render       (fn []
                               [scb/round-expander
                                {:class (into [:h-10 :flex :items-center :duration-200]
                                              [(if @search :px-2)
                                               (if @search :w-full :w-10)])
                                 :style (if @search
                                          {:-padding-block "var(--size-4)"
                                           :background     "var(--surface000)"}
                                          {:-aspect-ratio "1/1"})}
                                (when @search [sc/icon [:> solid/SearchIcon]])
                                [:input.w-full.h-full.px-2
                                 {:class       [:bg-transparent
                                                :focus:outline-none
                                                (if @search :flex :hidden)]
                                  :ref         #(when-not @a
                                                  (set-focus % a))
                                  :placeholder "søk"
                                  :value       @value
                                  :on-blur     (fn [e] (let [s (-> e .-target .-value)]
                                                         (if (nil? (seq s))
                                                           (do
                                                             (tap> "blur")
                                                             (rf/dispatch [:lab/set-search-expr ""])
                                                             (rf/dispatch [:lab/set-search-mode false])
                                                             (rf/dispatch [:lab/stop-search])))))
                                  :on-change   #(let [s (-> % .-target .-value)]
                                                  (.stopPropagation %)
                                                  (rf/dispatch [:lab/set-search-expr s]))
                                  :type        :text}]

                                [:div [sc/icon
                                       {:class    [:shrink-0]
                                        :on-click #(if @search
                                                     (do
                                                       (rf/dispatch [:lab/set-search-mode false])
                                                       (rf/dispatch [:lab/stop-search])
                                                       (rf/dispatch [:lab/set-search-expr ""]))
                                                     (do
                                                       (rf/dispatch [:lab/set-search-mode true])
                                                       (when-let [r @a]
                                                         (tap> "attempt focus")
                                                         (.focus r))))}

                                       (if @search
                                         [:div.hover:text-red-500 [:> solid/XIcon]]
                                         [:> solid/SearchIcon])]]])})))

;endregion

(defn main-menu []
  (r/with-let [mainmenu-visible (r/atom false)]
    (let [toggle-mainmenu #(swap! mainmenu-visible (fnil not false))]
      [:<>
       [scm/mainmenu-example-with-args
        {:showing      @mainmenu-visible
         :dir          #{:right :down}
         :close-button (fn [open] [scb/corner {:on-click toggle-mainmenu} [sc/icon [:> solid/XIcon]]])
         :data         (better-mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
         :button       (fn [open]
                         [scb/round-normal {:on-click toggle-mainmenu}
                          (if open
                            [sc/icon [:> solid/XIcon]]
                            [sc/icon [:> solid/MenuIcon]])])}]])))

(defn bottom-menu-definition [settings-atom]
  [[:header [sc/row {:class [:justify-between :items-end :w-44]}
             [sc/header-title "Booking"]
             [sc/pill (or "dev.3.12" booking.data/VERSION)]]]


   [:space]
   [:div [sc/small "Skrevet av meg for NRPK"]]
   [:space]
   [:footer [sc/row-end {:class [:gap-1 :justify-end :items-center]}
             [sc/small "Vilkår"]
             [sc/small "&"]
             [sc/small "Betingelser"]]]])

(defn bottom-menu []
  (r/with-let [main-visible (r/atom false)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/mainmenu-example-with-args
       {:close-button #()
        :showing      @main-visible
        :dir          #{:up :right}
        :data         (bottom-menu-definition (r/atom nil))
        :button       (fn [open]
                        [scb/round-normal {:on-click toggle-mainmenu} [sc/corner-symbol "?"]])}])))

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
   {:icon      solid/UserCircleIcon
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
   {:icon      solid/PlusIcon
    :on-click  schpaa.style.dialog/open-selector
    :page-name :r.debug}])

(def horizontal-toolbar
  [{:icon      solid/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      solid/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      solid/PlusIcon
    :on-click  schpaa.style.dialog/open-selector #_#(rf/dispatch [:app/navigate-to [:r.debug]])
    :page-name :r.debug}
   {:icon      solid/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(booking.content.booking-blog/count-unseen "piH3WsiKhFcq56lh1q37ijiGnqX2")
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
  :rounded-full :bg-pink-500 :text-white :px-1 :text-sm     ; :border-dashed :border-black :border
  [:& :top-0 :left-1 {:min-width  "1.3rem"
                      :height     "1.2rem"
                      :position   :absolute
                      :box-shadow "var(--shadow-5)"}]
  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))

(o/defstyled top-right-tight-badge :div
  :rounded-full :bg-pink-500 :text-white :px-1 :text-sm     ; :border-dashed :border-black :border
  [:& :top-2 :right-3 {:min-width  "1.3rem"
                       :height     "1.2rem"
                       :position   :absolute
                       :box-shadow "var(--shadow-5)"}]
  ([badge]
   [:<>
    [:div.flex.items-center.justify-center badge]]))

(defn horizontal-button [{:keys [icon on-click style page-name badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-full.flex.items-center.justify-center.relative
     {:on-click on-click}
     (when badge
       (let [b (badge)]
         (when (pos? b) [top-right-tight-badge b])))
     [vert-button {:active (= page-name current-page)
                   :style  style}
      [sc/icon [:> icon]]]]))

(defn vertical-button [{:keys [special icon style on-click page-name color badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-16.flex.items-center.justify-center.relative
     {:on-click on-click
      :style    {:background (if special "var(--surface1)")}}
     (when badge
       (when-some [b (badge)]
         [top-left-badge b]))
     [vert-button {:active (= page-name current-page)
                   :style  (conj style (when (= page-name current-page) {:color color}))}
      [sc/icon-large [:> icon]]]]))

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
           {:on-click toggle}
           [sc/icon-tiny
            [:> (if st outline/ChevronUpIcon outline/ChevronDownIcon)]]]])

(defn header-control-panel []
  (let [do-toggle #(put! ch :signal (fn [ev] (tap> ["just did" ev])))]
    [sc/col {:style {:padding-block    "var(--size-4)"
                     :padding-inline   "var(--size-2)"
                     :border-radius    "var(--radius-1)"
                     :box-shadow       (when @preferred-state "var(--inner-shadow-2)")
                     :background-color (when @preferred-state "var(--surface000)")}}
     [:div {:class (into [:duration-200] (if @preferred-state [:h-24 :opacity-100] [:pointer-events-none :h-0 :opacity-0]))}
      [sc/dim [sc/col {:class [:space-y-2]}
               [sc/subtext "Som en liste"]
               [sc/subtext "Som blokker i et rutenett"]
               [sc/subtext "Alle poster ekspandert"]]]
      #_[sc/markdown [:code "Some options here"]]]
     [sc/row-end                                            ;:div.flex.justify-between.items-center
      {:class [:x-debug]
       :style {:padding "var(--size-1)"}}
      [sc/dim [sc/small {:style {:font-family    "montserrat"
                                 :letter-spacing "var(--font-letterspacing-1)"
                                 :font-weight    "var(--font-weight-6)"}} "Visningsvalg"]]
      (chevron-updown-toggle @preferred-state do-toggle)]]))

(defn page-boundry [r & c]
  (let [user-auth (rf/subscribe [::db/user-auth])]
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
             [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.justify-around.items-center.flex-col.border-r
              {:style {:padding-top  "var(--size-0)"
                       :box-shadow   "var(--inner-shadow-3)"
                       :border-color "var(--surface1)"
                       :background   "var(--surface000)"}}
              (into [:<>] (map vertical-button (butlast (vertical-toolbar (:uid @user-auth)))))
              [:div.flex-grow]
              [:div.pb-4 (vertical-button (last (vertical-toolbar (:uid @user-auth))))]]

             [:div.flex-col.flex.h-full.w-full
              ;header
              [:div.h-16.flex.items-center.w-full.border-b.px-4.shrink-0
               {:style {:background   "var(--surface00)"
                        :border-color "var(--surface0)"}}
               [sc/row-std
                [sc/header-title {:class [:grow]}
                 (when-not @(rf/subscribe [:lab/in-search-mode?])
                   [sc/row {:class [:truncate]}
                    (interpose [:div.px-2.truncate "/"] (for [e (compute-page-title r)]
                                                          [:div.truncate e]))])]
                [search-menu]
                (main-menu)]]

              [:div.overflow-y-auto.h-full.focus:outline-none
               {:id        "inner-document"
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
                  {:style {:background "var(--surface000)"}}
                  [:div.max-w-md.mx-auto.py-4.space-y-4.px-2

                   ;[:div.-mx-2x.py-2 [header-control-panel]]
                   c
                   [:div.py-8.h-32]
                   [:div.absolute.bottom-24.sm:bottom-7.right-4
                    [sc/row-end {:class [:pt-4]}
                     (bottom-menu)]]]])]

              ;horizontal toolbar
              [:div.h-20.w-full.sm:hidden.flex.justify-around.items-center.border-t
               {:style {:box-shadow   "var(--inner-shadow-3)"
                        :border-color "var(--surface1)"
                        :background   "var(--surface000)"}}
               (into [:<>] (map horizontal-button
                                horizontal-toolbar))]]]]))})))

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

(do
  (rf/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])

  (rf/reg-event-fx :app/open-command-palette
                   (fn [{db :db} _]
                     {:fx [[:dispatch [:lab/modal-selector
                                       true
                                       {:content-fn (fn [c] [schpaa.style.combobox/combobox-example c])}]]]}))

  (rf/dispatch
    [::rp/set-keydown-rules
     {:event-keys [[[:app/open-command-palette]
                    [{:metaKey true
                      :keyCode keycodes/K}]]
                   [[:app/open-command-palette]
                    [{:ctrlKey true
                      :keyCode keycodes/K}]]]
      :prevent-default-keys
      [{:metaKey true
        :keyCode keycodes/K}
       {:keyCode 71
        :ctrlKey true}]}]))
