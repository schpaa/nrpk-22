(ns booking.common-views
  (:require [booking.content.blog-support :refer [err-boundary]]
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
                  [scb/corner {:on-click toggle-mainmenu} [sc/corner-symbol "?"]])}])))

(defn page-boundry [r & c]
  (let [page-title (-> r :data :header)]
    [err-boundary
     [:div.p-1
      [:div.lg:max-w-6xl.md:max-w-3xl.max-w-sm.mx-auto
       ;{:style {:outline "red 1px solid"}}
       [sc/row-stretch {:class [:py-4 :pb-8 :mx-auto :items-baseline]}
        [sc/hero-p (or page-title "no-title")]
        (main-menu)]
       c
       [sc/row {:class [:pt-4]}
        (bottom-menu)]]]]))
