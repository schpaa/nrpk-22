(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn better-mainmenu-definition [settings-atom]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    ;[icon label action disabled value]
    [[:menuitem {:icon      (sc/icon [:> solid/CollectionIcon])
                 :label     "Forsiden"
                 :highlight (= :r.forsiden current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/NewspaperIcon])
                 :label     "Hva er nytt?"
                 :highlight (= :r.booking-blog current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
                 :disabled  false
                 :value     #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> solid/PlusIcon])
                 :label     "Ny booking"
                 :highlight (= :r.debug current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.debug]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ClockIcon])
                 :label     "Bookingoversikt"
                 :highlight false
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
     [:menuitem {:icon      (sc/icon [:> solid/ShieldCheckIcon])
                 :label     "Regler"
                 :highlight false
                 :action    nil
                 :disabled  true
                 :value     #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> solid/ArrowRightIcon])
                 :label     "Auto-message"
                 :highlight false
                 :action    #(rf/dispatch [:lab/modal-example-dialog2 true])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ArrowRightIcon])
                 :label     "Form-message"
                 :highlight false
                 :action    #(rf/dispatch [:lab/modal-example-dialog true])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ArrowRightIcon])
                 :label     "Open popover"
                 :highlight false
                 :action    nil
                 :disabled  true
                 :value     #()}]]))

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
  (r/with-let [main-visible (r/atom nil)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/naked-menu-example-with-args
       {:dir    :up
        :data   (bottom-menu-definition (r/atom nil))
        :button (fn [open]
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
   {:icon      outline/ShieldCheckIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
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
   {:icon      outline/ShieldCheckIcon
    :on-click  #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
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

(defn horizontal-button [{:keys [icon on-click style page-name] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-full.flex.items-center.justify-center
     {:on-click on-click}
     [vert-button {:active (= page-name current-page)
                   :style  style}
      [sc/icon-large [:> icon]]]]))

(defn vertical-button [{:keys [icon style on-click page-name active-style!] :or {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    [:div.w-full.h-16.flex.items-center.justify-center
     {:on-click on-click}
     [vert-button {:active (= page-name current-page)
                   :style  style}
      [sc/icon-large [:> icon]]]]))

(defn page-boundry [r & c]
  (let [page-title (-> r :data :header)]
    [err-boundary
     [:div.fixed.inset-0.flex
      ;vertical action-bar
      [:div.shrink-0.w-16.md:w-20.h-full.xs:flex.hidden.justify-around.items-center.flex-col.border-r
       {:style {:padding-top  "var(--size-0)"
                :box-shadow   "var(--inner-shadow-3)"
                :border-color "var(--surface0)"
                :background   "var(--surface0)"}}
       (map vertical-button
            (butlast vertical-toolbar))
       [:div.flex-grow]
       [:div.h-20 (vertical-button (last vertical-toolbar))]]

      [:div.flex-col.flex.h-full.w-full
       [:div.h-16.flex.items-center.w-full.border-b.px-4
        {:style {:background   "var(--surface000)"
                 :border-color "var(--surface0)"}}
        [sc/row-stretch
         [sc/header-title (or page-title "no-title")]
         (main-menu)]]

       [:div.overflow-y-auto.h-full.py-8
        [:div.lg:max-w-6xl.md:max-w-3xl.max-w-sm.mx-auto.p-4
         c
         [:div.absolute.bottom-24.xs:bottom-4.right-4
          [sc/row-end {:class [:pt-4]}
           (bottom-menu)]]]]
       [:div.h-24.w-full.xs:hidden.flex.justify-around.items-center
        {:style {:box-shadow "var(--inner-shadow-3)"
                 :background "var(--surface0)"}}
        (map horizontal-button
             horizontal-toolbar)]]]]))
