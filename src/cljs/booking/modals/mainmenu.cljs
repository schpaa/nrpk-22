(ns booking.modals.mainmenu
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [headlessui-reagent.core]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu :as scm]
            [booking.modals.boatinput]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]))

;region

(rf/reg-event-db :lab/open-menu
                 (fn [db _]
                   (assoc db :lab/open-menu true)))

(rf/reg-event-db :lab/close-menu
                 (fn [db _]
                   (assoc db :lab/open-menu false)))

(rf/reg-sub :lab/menu-open
            (fn [db]
              (get db :lab/open-menu false)))

;endregion

(defn mainmenu-definitions []
  (let [at-least-registered? (rf/subscribe [:lab/at-least-registered])
        username-or-fakename (rf/subscribe [:lab/username-or-fakename])
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    (remove nil?
            [(if (= :r.oversikt current-page)
               [:item {:icon     ico/new-home
                       :style    {:color "var(--text1)"}
                       :label    "Forsiden"
                       :action   #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                       :disabled false
                       :value    #()}]
               [:item {:icon      ico/new-home
                       :style     {:color         "var(--gray-1)"
                                   :background    "var(--brand1)"
                                   :margin        "-4px"
                                   :padding       "4px"
                                   :border-radius "var(--radius-round)"}
                       :label     "Oversikt"
                       :highlight (= :r.oversikt current-page)
                       :action    #(rf/dispatch [:app/navigate-to [:r.oversikt]])
                       :disabled  false
                       :value     #()}])

             (when @at-least-registered?
               [:item {:icon      ico/user
                       :label     [sc/col
                                   [sc/text1 {:style {:font-size "var(--font-size-2)"}} "Mine opplysninger"]
                                   [sc/small1 @username-or-fakename]]
                       :highlight (= :r.user current-page)
                       :action    #(rf/dispatch [:app/navigate-to [:r.user]])
                       :disabled  false
                       :value     #()}])
             [:hr]

             (when @at-least-registered?
               [:toggle {:disabled  true
                         :stay-open true
                         :content   (fn [_]
                                      [:div.w-full
                                       [schpaa.style.hoc.toggles/stored-toggle :lab/toggle-chrome "Flere knapper"
                                        (fn [t c]
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                                           [sc/text1 c] t])]])}])

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.hoc.toggles/stored-toggle :lab/show-image-carousell "Vis bildekarusell"
                                      (fn [t c]
                                        [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                                         [sc/text1 c] t])]])}]

             #_[:toggle {:disabled  true
                         :stay-open true
                         :content   (fn [_]
                                      [:div.w-full
                                       [schpaa.style.hoc.toggles/stored-toggle :lab/image-carousell-autoscroll "Auto-scroll"
                                        (fn [t c]
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                                           [sc/text1 c] t])]])}]

             (when goog.DEBUG
               [:toggle {:disabled  true
                         :stay-open true
                         :content   (fn [_]
                                      [:div.w-full
                                       [schpaa.style.hoc.toggles/local-toggle :lab/master-state-emulation "State emu"
                                        (fn [t c]
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                                           [sc/text1 c] t])]])}])

             [:hr]

             [:item {:label     "Speilvend grensesnittet"
                     :action    #(schpaa.state/toggle :lab/menu-position-right)
                     :stay-open true}]

             [:item {:icons  (sc/icon ico/commandPaletteClosed)
                     :label  "Vis alle meldinger igjen"
                     :action #(schpaa.state/change :lab/skip-easy-login false)}]
             [:item {:icon      (sc/icon ico/commandPaletteClosed)
                     :style     {:color "var(--brand1)"}
                     :label     "Hva kan jeg gjøre?"
                     :stay-open true
                     :shortcut  "ctrl-k"
                     :action    #(rf/dispatch [:app/toggle-command-palette])}]
             (when @at-least-registered?
               [:hr])
             (when @at-least-registered?
               [:item {:label    "Logg ut"
                       :disabled false
                       :icon     (sc/icon ico/logout)
                       :action   #(rf/dispatch [:app/sign-out])}])
             [:space]
             [:div [:div.-m-1x
                    {:style {:background "var(--toolbar-)"}}
                    [schpaa.style.hoc.toggles/dark-light-toggle :app/dark-mode ""
                     (fn [t c]
                       [:div.flex.justify-between.items-center.w-full.gap-2.ml-12.mr-4.h-16
                        [sc/text1 c] t])]]]])))

(defn main-menu [switch?]
  (r/with-let [mainmenu-visible #_(r/atom false) (rf/subscribe [:lab/menu-open])]
    [scm/mainmenu-example-with-args
     {:data       (mainmenu-definitions)
      :dir        #{:down (if switch? :left :right)}
      :showing!   mainmenu-visible
      :close-menu #(rf/dispatch [:lab/close-menu])
      :button     (fn [open]
                    [hoc.buttons/round'
                     {:style {:cursor :pointer}
                      :class [:h-12]}
                     [sc/icon-large (if open ico/closewindow ico/menu)]])}]))

;region extract!

