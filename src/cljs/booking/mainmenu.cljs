(ns booking.mainmenu
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [db.core :as db]
    ;styles
            [schpaa.style.ornament :as sc]
            [headlessui-reagent.core]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.boatinput]
            [schpaa.debug :as l :refer [strp]]
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

(defn mainmenu-definition []
  (let [at-least-registered? (rf/subscribe [:lab/at-least-registered])
        username-or-fakename (rf/subscribe [:lab/username-or-fakename])
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    (remove nil?
            [#_[:header {:icon     ico/closewindow
                         :label    ""
                         :shortcut "ESC"
                         :action   #(rf/dispatch [:lab/close-menu])}]

             (if (= :r.oversikt current-page)
               [:item {:icon     ico/new-home
                       :style    {:color "var(--text1)"}
                       ;:background    "var(--brand1)"
                       ;:margin        "-4px"
                       ;:padding       "4px"
                       ;:border-radius "var(--radius-round)"}
                       :label    "Forsiden"
                       ;:highlight (= :r.oversikt current-page)
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
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                           [sc/text1 c] t])]])}])

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.hoc.toggles/stored-toggle :lab/show-image-carousell "Billedkarusell"
                                      (fn [t c]
                                        [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                         [sc/text1 c] t])]])}]

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.hoc.toggles/stored-toggle :lab/image-carousell-autoscroll "Auto-scroll"
                                      (fn [t c]
                                        [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                         [sc/text1 c] t])]])}]

             (when goog.DEBUG
               [:toggle {:disabled  true
                         :stay-open true
                         :content   (fn [_]
                                      [:div.w-full
                                       [schpaa.style.hoc.toggles/local-toggle :lab/master-state-emulation "State emu"
                                        (fn [t c]
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                           [sc/text1 c] t])]])}])

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.switch/large-switch
                                      {:tag      :lab/more-contrast
                                       :caption  "Større kontrast"
                                       :disabled true
                                       :view-fn  (fn [t c]
                                                   [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                                    [sc/text1 c] t])}]])}]

             [:hr]

             [:item {:label     "Speilvend"
                     :action    #(schpaa.state/toggle :lab/menu-position-right)
                     :stay-open true}]

             [:item {:icons  (sc/icon ico/commandPaletteClosed)
                     :label  "Vis alle spørsmål"
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

(defn boatinput-menu [left-side]
  (r/with-let [numberinput-visible (rf/subscribe [:lab/number-input])
               mobile? false]                               ;(= :mobile @(rf/subscribe [:breaking-point.core/screen]))]

    [headlessui-reagent.core/transition
     (conj
       {:show  (or @numberinput-visible false)
        :style {:pointer-events :none}}
       (if mobile?
         {:enter      "transition duration-200 ease-out"
          :enter-from "opacity-0 translate-y-full"
          :enter-to   "opacity-100 translate-y-0"
          :entered    "translate-y-0 "
          :leave      "transition  duration-200"
          :leave-from "opacity-100 translate-y-0"
          :leave-to   "opacity-0 translate-y-full"}
         {:enter      "transition duration-200 ease-out"
          :enter-from (strp "opacity-0 " (if left-side "-translate-x-full" "translate-x-full"))
          :enter-to   (strp "opacity-100" (if left-side "translate-x-0" "translate-x-0"))
          :leave      "transition duration-300"
          :leave-from (strp "opacity-100" (if left-side "translate-x-0" "translate-x-0"))
          :leave-to   (strp "opacity-0" (if left-side "-translate-x-full" "translate-x-full"))}))
     [:div.h-screen.grid
      [:div
       {:class [(some-> (sc/inner-dlg) last :class first)]
        :style (conj
                 {:place-self :center}
                 (if mobile?
                   {:display       :grid
                    :align-content :end}
                   {:pointer-events :none}))}
       [:div.select-none
        {:style (conj
                  {:border-radius "var(--radius-2)"}
                  (if mobile? {:padding-block "var(--size-10)"
                               :overflow-y    :auto}
                              {:pointer-events :none
                               :box-shadow     "var(--shadow-6)"
                               :overflow-y     :auto}))}
        (if left-side
          [scm/boatinput-panel-from-left
           [booking.boatinput/sample mobile? true]]
          [scm/boatinput-panel-from-right
           [booking.boatinput/sample mobile? false]])]]]]))

(defn main-menu [switch?]
  (r/with-let [mainmenu-visible #_(r/atom false) (rf/subscribe [:lab/menu-open])]
    [scm/mainmenu-example-with-args
     {:data       (mainmenu-definition)
      :dir        #{:down (if switch? :left :right)}
      :showing!   mainmenu-visible
      :close-menu #(rf/dispatch [:lab/close-menu])
      :button     (fn [open]
                    [hoc.buttons/round'
                     {:style {:cursor :pointer}
                      :class [:h-12]
                      #_#_:on-click #(do
                                       (tap> {"ZAP" open})
                                       #_(if-not open
                                           (rf/dispatch [:lab/open-menu])
                                           #_(rf/dispatch [:lab/close-menu])))}

                     [sc/icon-large (if open ico/closewindow ico/menu)]])}]))

;region extract!

