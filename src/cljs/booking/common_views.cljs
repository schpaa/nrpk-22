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

(defn main-menu []
  (r/with-let [mainmenu-visible (r/atom nil)]
    (let [toggle-mainmenu #(swap! mainmenu-visible (fnil not false))]
      [:<>
       [scm/mainmenu-example-with-args
        {:dir          :down
         :close-button (fn [open] [scb/small-corner {:on-click #()} [sc/icon [:> solid/XIcon]]])
         :data         (better-mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
         :button       (fn [open]
                         [scb/round-normal {:on-click toggle-mainmenu} [sc/icon [:> solid/MenuIcon]]])}]])))

(defn bottom-menu-definition [settings-atom]
  [[:header [sc/row {:class [:justify-between :items-end]}
             [sc/title "Top"]
             [sc/pill (or booking.data/VERSION "dev.x.y")]]]
   [:menuitem [(sc/icon-large [:> solid/BadgeCheckIcon])
               "Badge"
               nil
               true
               #()]]
   [:footer [sc/row-end {:class [:gap-4]} [sc/small "Terms"] [sc/small "Privacy"]]]])

(defn bottom-menu []
  (r/with-let [main-visible (r/atom false)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/naked-menu-example-with-args
       {:showing @main-visible
        :dir     #{:up :left}
        :data    (bottom-menu-definition (r/atom nil))
        :button  (fn [open]
                   [scb/round-normal {:on-click toggle-mainmenu} [sc/corner-symbol "?"]])}])))

#_(def vertical-toolbar
    [{:icon  outline/HomeIcon
      :style {:border-radius "var(--radius-round)"
              :padding       "var(--size-2)"
              :background    "var(--surface000)"}}
     {:icon  solid/UserIcon
      :style {}}
     {:icon  outline/ClockIcon
      :style {}}
     {:icon  solid/PlusIcon
      :style {:border-radius "var(--radius-round)"
              :padding       "var(--size-2)"
              :background    "var(--surface1)"}}])

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
    :badge     #(booking.content.booking-blog/count-unseen "piH3WsiKhFcq56lh1q37ijiGnqX2")
    :page-name :r.booking-blog}
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
      [sc/icon-large [:> icon]]]]))

(defn vertical-button [{:keys [icon style on-click page-name active-style! badge] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-16.flex.items-center.justify-center.relative
     {:on-click on-click}
     (when badge
       (let [b (badge)]
         (when (pos? b) [top-left-badge b])))
     [vert-button {:active (= page-name current-page)
                   :style  style}

      [sc/icon-large [:> icon]]]]))

(defn page-boundry [r & c]
  (let [page-title (-> r :data :header)]
    [err-boundary

     ;region modal dialog
     [schpaa.style.dialog/modal-generic
      {:context @(rf/subscribe [:lab/modal-example-dialog2-extra])
       :vis     (rf/subscribe [:lab/modal-example-dialog2])
       :close   #(rf/dispatch [:lab/modal-example-dialog2 false])}]
     ;endregion

     [:div.fixed.inset-0.flex
      ;vertical action-bar
      [:div.shrink-0.w-16.md:w-20.h-full.xs:flex.hidden.justify-around.items-center.flex-col.border-r
       {:style {:padding-top  "var(--size-0)"
                :box-shadow   "var(--inner-shadow-3)"
                :border-color "var(--surface0)"
                :background   "var(--surface0)"}}
       (into [:<>] (map vertical-button
                        (butlast vertical-toolbar)))
       [:div.flex-grow]
       [:div.pb-4 (vertical-button (last vertical-toolbar))]]

      [:div.flex-col.flex.h-full.w-full
       [:div.h-16.flex.items-center.w-full.border-b.px-4
        {:style {:background   "var(--surface000)"
                 :border-color "var(--surface0)"}}
        [sc/row-stretch
         [sc/header-title [sc/dim (or page-title "no-title")]]
         (main-menu)]]

       [:div.overflow-y-auto.h-full.py-8
        [:div.lg:max-w-6xl.md:max-w-3xl.mx-auto.px-4
         c
         [:div.h-32]
         [:div.absolute.bottom-24.xs:bottom-7.right-4
          [sc/row-end {:class [:pt-4]}
           (bottom-menu)]]]]
       [:div.h-24.w-full.xs:hidden.flex.justify-around.items-center
        {:style {:box-shadow "var(--inner-shadow-3)"
                 :background "var(--surface0)"}}
        (into [:<>] (map horizontal-button
                         horizontal-toolbar))]]]]))
