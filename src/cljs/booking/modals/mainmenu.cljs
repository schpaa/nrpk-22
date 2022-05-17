(ns booking.modals.mainmenu
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.dialog]
            [schpaa.style.menu :as scm]
            [booking.modals.boatinput]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [headlessui-reagent.core :as ui]))

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

;region menuitems

(defn item=forsiden-oversikt [r]
  (let [current-page (some-> r :data :name)]
    (if (= :r.oversikt current-page)
      [:item {:icon     ico/new-home
              :class    :taller
              :style    {:color "var(--text1)"}
              :label    "Forsiden"
              :action   #(rf/dispatch [:app/navigate-to [:r.forsiden]])
              :disabled false
              :value    #()}]
      [:item {:icon      ico/new-home
              :class     :taller
              :style     {:color         "var(--gray-1)"
                          :background    "var(--brand1)"
                          :margin        "-4px"
                          :padding       "4px"
                          :border-radius "var(--radius-round)"}
              :label     "Oversikt"
              :highlight (= :r.oversikt current-page)
              :action    #(rf/dispatch [:app/navigate-to [:r.oversikt]])
              :disabled  false
              :value     #()}])))

(defn item=mine-opplysninger [r]
  (let [current-page (some-> r :data :name)
        username-or-fakename (rf/subscribe [:lab/username-or-fakename])]
    [:item {:icon      ico/user
            ;:class :taller
            :label     [sc/col
                        [sc/text1 {:style {:font-size "var(--font-size-2)"}} "Mine opplysninger"]
                        [sc/small1 @username-or-fakename]]
            :highlight (= :r.user current-page)
            :action    #(rf/dispatch [:app/navigate-to [:r.user]])
            :disabled  false
            :value     #()}]))

(defn item=search []
  [:item {:icon      (sc/icon ico/commandPaletteClosed)
          :class     :taller
          :style     {:color "var(--brand1)"}
          :label     "Hva kan jeg gjøre?"
          :stay-open true
          :shortcut  "ctrl-k / \u2318-k"
          :action    #(rf/dispatch [:app/toggle-command-palette])}])

(defn item=more-buttons []
  [:toggle
   {:disabled  true
    :stay-open true
    :content   (fn [_]
                 [:div.w-full
                  [schpaa.style.hoc.toggles/stored-toggle :lab/toggle-chrome "Flere knapper"
                   (fn [t c]
                     [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                      [sc/text1 c] t])]])}])

(defn item=show-pictures []
  [:toggle {:disabled  true
            :stay-open true
            :content   (fn [_]
                         [:div.w-full
                          [schpaa.style.hoc.toggles/stored-toggle :lab/show-image-carousell "Vis bildekarusell"
                           (fn [t c]
                             [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                              [sc/text1 c] t])]])}])

(defn item=state []
  [:toggle {:disabled  true
            :stay-open true
            :content   (fn [_]
                         [:div.w-full
                          [schpaa.style.hoc.toggles/local-toggle :lab/master-state-emulation "State emu"
                           (fn [t c]
                             [:div.flex.justify-between.items-center.w-full.gap-2.h-12x
                              [sc/text1 c] t])]])}])

(defn item=reset-messages []
  [:item {:icons  (sc/icon ico/commandPaletteClosed)
          :label  "Gjennopprett meldinger"
          :action #(schpaa.state/change :lab/skip-easy-login false)}])

(defn item=logout []
  [:item {:label    "Logg ut"
          :disabled false
          :icon     (sc/icon ico/logout)
          :action   #(rf/dispatch [:app/sign-out])}])

(defn item=login []
  [:item {:label    "Logg inn"
          :disabled false
          :icon     (sc/icon ico/login)
          :action   #(rf/dispatch [:app/login])}])

(defn item=dark-light-mode-selector []
  [:div
   [:div
    {:style {:background "var(--toolbar-)"}}
    [sc/row-std {:style {:width "100%"}}
     [sc/title1 {:style {:margin-left "1rem"
                         :color       "var(--text0)"}} "eykt.nrpk.no"]
     [schpaa.style.hoc.toggles/dark-light-toggle :app/dark-mode ""
      (fn [t c]
        [:div.flex.justify-between.items-center.w-full.gap-2.ml-12.mr-4.h-16
         [sc/text1 c] t])]]]])

(defn item=switch-position []
  [:item {:label     "Speilvendt plassering"
          :action    #(schpaa.state/toggle :lab/menu-position-right)
          :stay-open true}])

;endregion

(defn mainmenu-definitions [r]
  {:pre [(map? r)]}
  (let [reg? @(rf/subscribe [:lab/at-least-registered])]
    (remove nil?
            [(item=dark-light-mode-selector)
             ;[:space]
             (item=forsiden-oversikt r)
             (when reg? (item=mine-opplysninger r))
             (item=search)
             [:hr]
             ;note: replaced with button on the toolbar
             (when reg? (item=switch-position))
             (item=reset-messages)
             (when reg? [:hr])
             (when reg? (item=logout))
             (when-not reg? (item=login))
             [:hr]
             (when reg? (item=more-buttons))
             (item=show-pictures)
             (when goog.DEBUG (item=state))

             [:space]
             #_(item=dark-light-mode-selector)])))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

(defn settings-dialog [inner]
  (r/with-let [!open? (r/atom false)
               open #(reset! !open? true)
               close #(reset! !open? false)]
    (let [open? @!open?]
      [:<>
       [:div.flex.items-center.justify-center.w-12
        [hoc.buttons/round
         {:on-click #(do
                       (open)
                       (.stopPropagation %))
          :type     :button
          :class    [:large]
          :style    {:color "var(--text0)"}}
         [sc/icon-large (if open? ico/closewindow ico/menu)]]]

       [ui/transition
        {:show open?}
        [ui/dialog {:on-close close
                    :class    "z-100"}
         [:div.fixed.inset-0.z-0.overflow-y-auto
          {:style {:z-index 2000}}
          [:div.min-h-screen.px-4.text-center
           (schpaa.style.dialog/standard-overlay)
           [:span.inline-block.h-screen.align-middle
            (assoc zero-width-space-props :aria-hidden true)]
           [ui/transition-child
            ;; .transform isn't needed for the animiation since Tailwind CSS
            ;; 3.0. But, it has the side-effect of creating a stacking context,
            ;; which is necessary. .isolate would be more correct, but we're
            ;; leaving .transform to stay close to headlessui.dev.
            {:class      "inline-block align-middle text-left transform"
             :enter      "ease-out duration-300"
             :enter-from "opacity-0 scale-95"
             :enter-to   "opacity-100 scale-100"
             :leave      "ease-in duration-200"
             :leave-from "opacity-100 scale-100"
             :leave-to   "opacity-0 scale-95"}
            ;; NOTE: if your dialog is long and you need to support scrolling
            ;; while the mouse is over the background, wrap this with
            ;; `ui/dialog-panel` and replace `ui/dialog-overlay` with
            ;; `ui/dialog-backdrop`.
            inner]]]]]])))


(defn main-menu [r]
  (r/with-let [mainmenu-visible (rf/subscribe [:lab/menu-open])]
    ;this is just a button                     
    [settings-dialog
     [scm/settings-floating
      {:data       (mainmenu-definitions r)
       :showing!   mainmenu-visible
       :close-menu #(rf/dispatch [:lab/close-menu])}]]))


;region extract!
