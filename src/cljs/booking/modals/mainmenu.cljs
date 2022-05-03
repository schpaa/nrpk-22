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
              :value     #()}])))

(defn item=mine-opplysninger [r]
  (let [current-page (some-> r :data :name)
        username-or-fakename (rf/subscribe [:lab/username-or-fakename])]
    [:item {:icon      ico/user
            :label     [sc/col
                        [sc/text1 {:style {:font-size "var(--font-size-2)"}} "Mine opplysninger"]
                        [sc/small1 @username-or-fakename]]
            :highlight (= :r.user current-page)
            :action    #(rf/dispatch [:app/navigate-to [:r.user]])
            :disabled  false
            :value     #()}]))

(defn item=search []
  [:item {:icon      (sc/icon ico/commandPaletteClosed)
          :style     {:color "var(--brand1)"}
          :label     "Hva kan jeg gjøre?"
          :stay-open true
          :shortcut  "ctrl-k / \u2318-k"
          :action    #(rf/dispatch [:app/toggle-command-palette])}])

(defn item=more-buttons []
  [:toggle {:disabled  true
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
          :label  "Vis alle meldinger igjen"
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
  [:div [:div.-m-1x
         {:style {:background "var(--toolbar-)"}}
         [schpaa.style.hoc.toggles/dark-light-toggle :app/dark-mode ""
          (fn [t c]
            [:div.flex.justify-between.items-center.w-full.gap-2.ml-12.mr-4.h-16
             [sc/text1 c] t])]]])

(defn item=switch-position []
  [:item {:label     "Flytt verktøylinjen hit"
          :action    #(schpaa.state/toggle :lab/menu-position-right)
          :stay-open true}])

;endregion

(defn mainmenu-definitions [r]
  {:pre [(map? r)]}
  (let [reg? @(rf/subscribe [:lab/at-least-registered])]
    (remove nil?
            [(item=forsiden-oversikt r)
             (when reg? (item=mine-opplysninger r))
             (item=search)
             [:hr]
             (when reg? (item=more-buttons))
             (item=show-pictures)
             (when goog.DEBUG (item=state))
             [:hr]
             ;note: replaced with button on the toolbar
             (when reg? (item=switch-position))
             (item=reset-messages)
             (when reg? [:hr])
             (when reg? (item=logout))
             (when-not reg? (item=login))
             [:space]
             (item=dark-light-mode-selector)])))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

(defn settings-dialog [inner]
  (r/with-let [!open? (r/atom false)
               open #(reset! !open? true)
               close #(reset! !open? false)]
    (let [open? @!open?]
      [:<>
       [:div.flex.items-center.justify-center
        ;{:style {:z-index 1000}}
        [hoc.buttons/round
         {:on-click #(do
                       (open)
                       (.stopPropagation %))
          :type     :button
          :class    [:large]
          :style    {:color "var(--text0)"}}
         [sc/icon (if open? ico/closewindow ico/menu)]]]

       [ui/transition
        {;; :appear true ;; appear is irrelevant for us because open? defaults to false, unlike on headlessui.dev
         :show open?}

        [ui/dialog {:on-close close
                    :class    "z-100"}
         #_[:div.fixed.inset-0.pointer-events-auto {:on-click #(tap> "CLCIK")
                                                    :class    "bg-black/30" :aria-hidden "true"}]
         [:div.fixed.inset-0.z-0.overflow-y-auto
          [:div.min-h-screen.px-4.text-center
           ;; NOTE: the structure of this HTML is delicate and has subtle
           ;; interactions to keep the modal centered. The structure we use is
           ;; slightly different from the headlessui.dev example. There, the
           ;; Transition.Child elements are rendered as fragments. Here, since
           ;; we don't support fragments, we move some of the structural styles
           ;; to the transition-child elements, which seems to have the same
           ;; effect.
           (schpaa.style.dialog/standard-overlay)
           #_[ui/transition-child
              {:enter      "ease-out duration-300"
               :enter-from "opacity-0"
               :enter-to   "opacity-100"
               :leave      "ease-in duration-200"
               :leave-from "opacity-100"
               :leave-to   "opacity-0"}
              [ui/dialog-overlay {:class "fixed inset-0 bg-gray-500 bg-opacity-75 "}]]
           ;; Trick browser into centering modal contents.
           ;; This is the "ghost element" technique, described here
           ;; https://css-tricks.com/centering-in-the-unknown/ as well as
           ;; elsewhere.
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
            inner
            #_[:div.max-w-md.p-6.my-8.bg-white.shadow-xl.rounded-2xl
               [ui/dialog-title {:as :div.text-lg.font-medium.leading-6.text-gray-900}
                "Payment successful"]
               [:div.mt-2
                [:p.text-sm.text-gray-500 "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]
               [:div.mt-4
                [:button.inline-flex.justify-center.px-4.py-2.text-sm.font-medium.text-blue-900.bg-blue-100.border.border-transparent.rounded-md.hover:bg-blue-200.focus:outline-none.focus-visible:ring-2.focus-visible:ring-offset-2.focus-visible:ring-blue-500
                 {:type     "button"
                  :on-click close}
                 "Got it, thanks!"]]]]]]]]])))

(defn main-menu [r]
  (r/with-let [mainmenu-visible (rf/subscribe [:lab/menu-open])]
    ;this is just a button

    [settings-dialog
     [scm/settings-floating
      {:data       (mainmenu-definitions r)
       :showing!   mainmenu-visible
       :close-menu #(rf/dispatch [:lab/close-menu])
       :button     (fn [open]
                     [hoc.buttons/round'
                      {:style {:cursor :pointer}
                       :class [:w-12]}
                      [sc/icon (if open ico/cog-open ico/cog)]])}]]
    #_[:div.fixed.inset-0
       [kee-frame.error/boundary
        (fn default-error-body [[err info]]
          (js/console.log "An error occurred: " err)
          [:div {:style {:color :red}} [:code {:style {:font-size "smaller"}} (pr-str info)]])
        [:div
         [ui/transition
          {:show true}
          [ui/dialog
           {:xon-close #()
            :xas       "div"
            :xopen     true}]
          [ui/transition-child
           {}
           [ui/dialog-backdrop {:class "fixed inset-0 bg-gray-500 bg-opacity-75"}]]]]



        [scm/settings-floating
         {:data       (mainmenu-definitions r)
          :showing!   mainmenu-visible
          :close-menu #(rf/dispatch [:lab/close-menu])
          :button     (fn [open]
                        [hoc.buttons/round'
                         {:style {:cursor :pointer}
                          :class [:w-12]}
                         [sc/icon (if open ico/cog-open ico/cog)]])}]]]))

;region extract!
