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
                 :label     "Mine bookinger"
                 :highlight false
                 :action    nil
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
                 :label     "Autoclosing dialog"
                 :highlight false
                 :action    #(rf/dispatch [:lab/modal-example-dialog2 true])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon      (sc/icon [:> solid/ArrowRightIcon])
                 :label     "Form dialog"
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
    (let [toggle-mainmenu #(do (tap> "TOGGLE!") (swap! mainmenu-visible (fnil not false)))]
      [:<>
       [scm/mainmenu-example-with-args
        {:close-button (fn [open] [scb/small-corner {:on-click #(do (tap> "toggle") #_((:toggle-mainmenu @settings-atom)))} [sc/icon [:> solid/XIcon]]])
         :data         (better-mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
         :button       (fn [open]
                         [scb/round-normal {:on-click toggle-mainmenu} [sc/icon [:> solid/MenuIcon]]])}]])))

(defn page-boundry [r & c]
  (let [page-title (-> r :data :header)]
    [err-boundary
     [sc/row-stretch {:class [:px-4 :py-8 :mx-auto :items-baseline]}
      [sc/hero-p (or page-title "no-title")]
      (main-menu)]
     [:div.max-w-mdx.mr-auto
      c]]))
