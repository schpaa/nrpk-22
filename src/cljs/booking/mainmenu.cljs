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
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]))


(defn address-me-as [user]
  (booking.database/bookers-name user))

(defn mainmenu-definition [settings-atom]
  (let [at-least-registered? (rf/subscribe [:lab/at-least-registered])
        username-or-fakename (rf/subscribe [:lab/username-or-fakename])
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    (remove nil?
            [[:space]
             #_[:menuitem {:icon      ico/new-home
                           :label     "Forsiden"
                           :highlight (= :r.forsiden current-page)
                           :action    #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                           :disabled  false
                           :value     #()}]
             [:menuitem {:icon      ico/new-home
                         :label     "Oversikt"
                         :highlight (= :r.oversikt current-page)
                         :action    #(rf/dispatch [:app/navigate-to [:r.oversikt]])
                         :disabled  false
                         :value     #()}]

             (when @at-least-registered?
               [:menuitem {:icon      ico/user
                           :label     [sc/col
                                       [sc/text1 "Mine opplysninger"]
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
                                       [schpaa.style.hoc.toggles/toggle :lab/toggle-chrome "Flere knapper"
                                        (fn [t c]
                                          [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                           [sc/text1 c] t])]])}])

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.hoc.toggles/toggle :lab/show-image-carousell "Billedkarusell"
                                      (fn [t c]
                                        [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                         [sc/text1 c] t])]])}]

             [:toggle {:disabled  true
                       :stay-open true
                       :content   (fn [_]
                                    [:div.w-full
                                     [schpaa.style.switch/large-switch
                                      {:tag      :lab/more-contrast
                                       :caption  "Mer kontrast"
                                       :disabled true
                                       :view-fn  (fn [t c]
                                                   [:div.flex.justify-between.items-center.w-full.gap-2.h-12
                                                    [sc/text1 c] t])}]])}]

             [:hr]
             [:menuitem {:icons  (sc/icon ico/commandPaletteClosed)
                         :label  "Vis alle spørsmål"
                         :action #(schpaa.state/change :lab/skip-easy-login false)}]
             [:menuitem {:icon     (sc/icon ico/commandPaletteClosed)
                         :label    "Hva kan jeg gjøre?"
                         :shortcut "ctrl-k"
                         :action   #(rf/dispatch [:app/toggle-command-palette])}]
             (when @at-least-registered?
               [:menuitem {:label    "Logg ut"
                           :disabled false
                           :icon     (sc/icon ico/logout)
                           :action   #(rf/dispatch [:app/sign-out])}])
             [:space]
             [:div [:div.-m-1
                    {:style {:background "var(--toolbar-)"}}
                    [schpaa.style.hoc.toggles/dark-light-toggle :app/dark-mode ""
                     (fn [t c]
                       [:div.flex.justify-between.items-center.w-full.gap-2.ml-12.mr-4.h-16
                        [sc/text1 c] t])]]]])))

(defn boatinput-sidebar []
  (r/with-let [numberinput-visible (rf/subscribe [:lab/number-input])]
    (when @numberinput-visible
      [:div.w-auto
       [scm/boatinput-panel-from-right
        [booking.boatinput/sample false]]])))

(defn boatinput-menu []
  (r/with-let [numberinput-visible (rf/subscribe [:lab/number-input])
               mobile? false]                               ;(= :mobile @(rf/subscribe [:breaking-point.core/screen]))]
    #_[:div.xabsolute.inset-0x.xw-auto.xinset-x-0.right-0x
       #_{:style {:z-index        0
                  :pointer-events :none
                  :background     "blue"}}]
    [headlessui-reagent.core/transition
     (conj
       {:show  (or @numberinput-visible false)
        :style {:pointer-events :none
                :-background    :#0f05}}
       (if mobile?
         {:enter      "transition duration-200 ease-out"
          :enter-from "opacity-0 translate-y-full"
          :enter-to   "opacity-100 translate-y-0"
          :entered    "translate-y-0 "
          :leave      "transition  duration-200"
          :leave-from "opacity-100 translate-y-0"
          :leave-to   "opacity-0 translate-y-full"}
         {:enter      "transition duration-200 ease-out"
          :enter-from "opacity-0 translate-x-full"
          :enter-to   "opacity-100 translate-x-0"
          ;:entered    "translate-x-0"
          :leave      "transition  duration-300"
          :leave-from "opacity-100 translate-x-0"
          :leave-to   "opacity-0 translate-x-full"}))
     [:div.h-screen
      {;:on-click #(tap> "clack")
       :style (conj
                ;{:pointer-events :none}
                {}
                (if mobile?
                  {:display       :grid
                   :align-content :end}
                  {:pointer-events  :none

                   :display         :grid

                   ;:z-index         10
                   :justify-content :end

                   :align-content   :center}))}
      [:div.select-none
       {:class [:drop-shadow-2xl]
        :style (conj
                 {}
                 (if mobile? {:padding-block "var(--size-10)"
                              :overflow-y    :auto}
                             {:pointer-events            :none
                              ;:background                :red ;#f00a
                              :border-bottom-left-radius "var(--radius-3)"
                              :border-top-left-radius    "var(--radius-3)"
                              :box-shadow                "var(--shadow-6)"
                              ;:box-sizing :content-box
                              ;:padding-top     "var(--size-9)"
                              ;:padding-bottom  "var(--size-10)"
                              :overflow-y                :auto
                              :-margin-top               "var(--size-9)"
                              :-margin-bottom            "var(--size-10)"
                              :spadding-inline           "var(--size-4)"}))}
       [scm/boatinput-panel-from-right
        [booking.boatinput/sample mobile?]]]]]))

(rf/reg-event-db :lab/open-menu (fn [db _] (update db :lab/open-menu not)))
(rf/reg-sub :lab/open-menu :-> #(get % :lab/open-menu false))

(defn main-menu []
  (r/with-let [mainmenu-visible (rf/subscribe [:lab/open-menu]) #_(r/atom false)
               numberinput-visible (rf/subscribe [:lab/number-input])]
    (let [toggle-mainmenu #(rf/dispatch [:lab/open-menu]) #_#(swap! mainmenu-visible (fnil not false))
          toggle-numberinput #(rf/dispatch [:lab/close-number-input])]
      [scm/mainmenu-example-with-args
       {:data         (mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
        :showing      mainmenu-visible
        :dir          #{:left :down}
        :close-button (fn [open] [scb/corner {:on-click toggle-mainmenu} [sc/icon [:> solid/XIcon]]])
        :button       (fn [open]
                        [hoc.buttons/round'
                         {:style {:cursor            :pointer
                                  :-background-color (when @mainmenu-visible "var(--toolbar)")}
                          :class [:h-12] :on-click toggle-mainmenu}
                         (if @mainmenu-visible
                           [sc/icon-large ico/closewindow]
                           [sc/icon-large ico/menu])])}])))

;region extract!

(defn sub-menu [{:keys [dir data]}]
  (r/with-let [menu-visible (r/atom true)]
    (let [toggle-menu #(swap! menu-visible (fnil not false))]
      [scm/submenu
       {:showing      @menu-visible
        :dir          dir
        :close-button (fn [open] [scb/corner {:on-click toggle-menu} [sc/icon [:> solid/XIcon]]])
        :data         data
        :button       (fn [open]
                        [hoc.buttons/round {:class    [:h-10]
                                            :on-click toggle-menu}
                         [sc/icon [:div {:class [:duration-100 :transform (if open :rotate-180)]}
                                   [:> solid/ChevronDownIcon]]]])}])))