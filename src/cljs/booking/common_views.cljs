(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog :refer [open-dialog-logoutcommand
                                         open-dialog-sampleautomessage
                                         open-dialog-sampleformmessage]]
            [booking.content.booking-blog]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [schpaa.style.button2 :as scb2]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [goog.events.KeyCodes :as keycodes]
            [times.api :as ta]
            [tick.core :as t]))

(defn better-mainmenu-definition [settings-atom]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    ;[icon label action disabled value]
    [[:menuitem {:icon      (sc/icon [:> solid/CollectionIcon])
                 :label     "Forsiden"
                 :color     "var(--forsiden)"
                 :highlight (= :r.forsiden current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/BookOpenIcon])
                 :label     "Hva er nytt?"
                 :color     "var(--hva-er-nytt)"
                 :highlight (= :r.booking-blog current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
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

     #_[:menuitem {:icon      nil                           ;(sc/icon nil #_[:> solid/ArrowRightIcon])
                   :label     "Auto-message"
                   :highlight false
                   :action    open-dialog-sampleautomessage
                   :disabled  false
                   :value     #()}]
     #_[:menuitem {:icon      nil                           ;(sc/icon nil #_[:> solid/ArrowRightIcon])
                   :label     "Form-message"
                   :highlight false
                   :action    open-dialog-sampleformmessage
                   :disabled  false
                   :value     #()}]
     #_[:menuitem {:icon      nil                           ;(sc/icon nil #_[:> solid/ArrowRightIcon])
                   :label     "Super-simple"
                   :highlight false
                   :action    #(rf/dispatch [:lab/modal-example-dialog2
                                             true
                                             #_{:click-overlay-to-dismiss true
                                                ;:auto-dismiss 5000
                                                :content-fn               (fn [{:keys [] :as context}]
                                                                            [sc/col {:class [:space-y-4 :w-full]}
                                                                             [sc/row [sc/title-p "Bekreft utlogging"]]
                                                                             [sc/text "Dette vil logge deg ut fra denne enheten."]
                                                                             [l/ppre context]])

                                                :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                                                :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                                                :on-primary-action        (fn [] (tap> "closing after save"))}])
                   :disabled  false
                   :value     #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon nil [:> solid/ArrowRightIcon])
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
        value (rf/subscribe [:lab/search-expression])       ;(r/atom "")
        search (rf/subscribe [:lab/in-search-mode?])
        #_#_toggle-mainmenu #(do
                               (rf/dispatch [:lab/toggle-search-mode])
                               ;(swap! search (fnil not false))
                               ;(reset! value "")
                               (tap> ["toggle-mainmenu" @a @search]))]
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
                                           :background     "var(--surface0)"}
                                          {:-aspect-ratio "1/1"})}
                                (when @search [:div [sc/icon [:> solid/SearchIcon]]])
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
         :close-button (fn [open] #_[scb/corner {:on-click toggle-mainmenu} [sc/icon [:> solid/XIcon]]])
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
(def vertical-toolbar
  [{:icon      outline/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      outline/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      outline/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.oversikt]])
    :page-name :r.oversikt}
   {:icon      outline/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(let [c (booking.content.booking-blog/count-unseen "piH3WsiKhFcq56lh1q37ijiGnqX2")]
                  (when (pos? c) c))
    :page-name :r.booking-blog}
   {:icon      outline/ColorSwatchIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.designlanguage]])
    ;:badge     (fn [_] "A")
    :special   true
    :page-name :r.designlanguage}

   {:icon      outline/HandIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.terms]])
    :special   true
    :page-name :r.terms}
   {:icon      outline/ClipboardListIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.conditions]])
    :special   true
    :page-name :r.conditions}
   {:icon      outline/ShieldCheckIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.retningslinjer]])
    :special   true
    :color     "red"
    :page-name :r.retningslinjer}
   {:icon      solid/PlusIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.debug]])
    :page-name :r.debug}])

(def horizontal-toolbar
  [{:icon      outline/HomeIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.forsiden]])
    :page-name :r.forsiden}
   {:icon      outline/UserCircleIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.user]])
    :page-name :r.user}
   {:icon      solid/PlusIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.debug]])
    :page-name :r.debug}
   {:icon      outline/BookOpenIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
    :badge     #(booking.content.booking-blog/count-unseen "piH3WsiKhFcq56lh1q37ijiGnqX2")
    :page-name :r.booking-blog}
   {:icon      outline/ClockIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.oversikt]])
    :page-name :r.oversikt}])

(def active-style {:border-radius "var(--radius-round)"
                   :padding       "var(--size-2)"
                   :background    "var(--surface000)"})

(o/defstyled vert-button :div
  [:&
   {:color "var(--text1)"}
   [:.normal {;:background    "var(--surface000)"
              :border-radius "var(--radius-round)"
              :color         "var(--surface3)"
              :padding       "var(--size-2)"}]
   [:.active {:box-shadow    "var(--shadow-3)"
              :background    "var(--surface000)"
              :border-radius "var(--radius-round)"
              :color         "var(--surface5)"
              :padding       "var(--size-2)"}]
   [:&:hover {:border-radius "var(--radius-round)"
              :background    "var(--surface1)"}]]
  ([attr & ch]
   [:div (conj attr {:class [(if (:active attr) :active :normal)]}) ch]))

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

      [sc/icon [:> icon]]]]))

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

(defn page-boundry [r & c]
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

          [:div.fixed.inset-0.flex

           ;vertical toolbar
           [:div.shrink-0.w-16.xl:w-20.h-full.sm:flex.hidden.justify-around.items-center.flex-col.border-r
            {:style {:padding-top  "var(--size-0)"
                     :box-shadow   "var(--inner-shadow-3)"
                     :border-color "var(--surface0)"
                     :background   "var(--surface0)"}}
            (into [:<>] (map vertical-button (butlast vertical-toolbar)))
            [:div.flex-grow]
            [:div.pb-4 (vertical-button (last vertical-toolbar))]]

           [:div.flex-col.flex.h-full.w-full
            [:div.h-16.flex.items-center.w-full.border-b.px-4
             {:style {:background   "var(--surface000)"
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
               [:div.h-full
                {:style {:background "var(--surface2)"}}
                [search-result]
                [:div.absolute.bottom-24.sm:bottom-7.right-4
                 [sc/row-end {:class [:pt-4]}
                  (bottom-menu)]]]
               [:div.max-w-md.mx-auto.px-4.py-8
                c
                [:div.py-8.h-32]
                [:div.absolute.bottom-24.sm:bottom-7.right-4
                 [sc/row-end {:class [:pt-4]}
                  (bottom-menu)]]])]

            ;horizontal toolbar
            [:div.h-20.w-full.sm:hidden.flex.justify-around.items-center
             {:style {:box-shadow "var(--inner-shadow-3)"
                      :background "var(--surface0)"}}
             (into [:<>] (map horizontal-button
                              horizontal-toolbar))]]]]))}))
