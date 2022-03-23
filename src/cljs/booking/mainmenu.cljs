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
            [schpaa.debug :as l]))


(defn address-me-as [user]
  (booking.database/bookers-name user))

(defn mainmenu-definition [settings-atom]
  (let [userauth (rf/subscribe [::db/user-auth])
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    ;[icon label action disabled value]
    [[:div [:div.p-2 [sc/title1 {:style {:font-family "Merriweather"
                                         :font-weight 300
                                         :font-size   "var(--font-size-4)"}} (str "Hei " (address-me-as @userauth))]]]
     [:menuitem {:filledicon (sc/icon [:> solid/HomeIcon])

                 :label      "Forsiden"
                 :color      "var(--red-6)"
                 :highlight  (= :r.forsiden current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.forsiden]])
                 :disabled   false
                 :value      #()}]
     ;[:space]
     [:menuitem {:filledicon (sc/icon [:> solid/CalendarIcon])
                 :label      "Kalender"
                 :color      "var(--hva-er-nytt)"
                 :highlight  (= :r.calendar current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.calendar]])
                 :disabled   false
                 :value      #()}]
     [:menuitem {:filledicon (sc/icon [:> solid/BookOpenIcon])
                 :label      "Innlegg"
                 :color      "var(--hva-er-nytt)"
                 :highlight  (= :r.booking-blog current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.booking-blog]])
                 :disabled   false
                 :value      #()}]
     [:hr]
     [:menuitem {:filledicon [:div {:class [:-m-1]
                                    :style {:padding          "var(--size-1)"
                                            :border-radius    "var(--radius-round)"
                                            :background-color "var(--brand1)"
                                            :color            :white}} (sc/icon [:> outline/CalculatorIcon])]
                 :label      "Utlån"
                 :color      "var(--brand1)"
                 :shortcut   "cmd-l"
                 :action     #(rf/dispatch [:lab/open-number-input])}]
     #_[:menuitem {:filledicon (sc/icon [:> solid/TicketIcon])
                   :label      "Ny booking"
                   :color      "var(--new-booking)"
                   :highlight  (= :r.debug current-page)
                   :action     #(rf/dispatch [:app/navigate-to [:r.debug]])
                   :disabled   false
                   :value      #()}]
     [:menuitem {:filledicon (sc/icon [:> solid/ClockIcon])
                 :label      "Båtoversikt"
                 :shortcut   "cmd-b"
                 :color      "var(--booking-oversikt)"
                 :highlight  (= :r.booking.oversikt current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])
                 :disabled   false
                 :value      #()}]


     #_[:menuitem {:filledicon (sc/icon [:> solid/MapIcon])
                   :label      "Turlogg"
                   :highlight  false
                   :action     nil
                   :disabled   true
                   :value      #()}]

     [:menuitem {:filledicon (sc/icon [:> solid/ShieldCheckIcon])
                 :label      "Retningslinjer"
                 :color      "var(--blue-4)"
                 :highlight  (= :r.booking.retningslinjer current-page)
                 :action     #(rf/dispatch [:app/navigate-to [:r.booking.retningslinjer]])
                 :disabled   false
                 :value      #()}]
     [:hr]
     [:menuitem {:icon      (sc/icon [:> outline/UserCircleIcon])
                 :label     "Mine opplysninger"
                 :color     "var(--blue-4)"
                 :highlight (= :r.user current-page)
                 :action    #(rf/dispatch [:app/navigate-to [:r.user]])
                 :disabled  false
                 :value     #()}]
     [:menuitem {:icon     (sc/icon [:> outline/CollectionIcon])
                 :label    "Kommandoer"
                 :color    "var(--brand1)"
                 :shortcut "cmd-k"
                 :action   #(rf/dispatch [:app/toggle-command-palette])}]
     [:menuitem {:label    "Innstillinger"
                 :icon     (sc/icon [:> outline/CogIcon])
                 :shortcut "cmd-;"
                 :action   #(rf/dispatch [:app/toggle-config])}]
     [:menuitem {:label    "Logg ut"
                 :disabled true
                 :icon     (sc/icon [:> outline/LogoutIcon])
                 :action   #(rf/dispatch [:app/toggle-config])}]

     #_[:space]
     #_[:div
        [:<>
         ;[l/ppre (:uid @userauth)]
         [:div.flex.items-center.gap-2.justify-between
          (if-not @userauth
            [scb2/cta-small "Meld på"]
            [:div.grow])
          (if @userauth
            [scb2/normal-small "Logg ut"]
            [scb2/normal-small "Logg in"])]]]]))

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
       {:style (conj
                 {}
                 (if mobile? {:padding-block "var(--size-10)"
                              :overflow-y    :auto}
                             {:pointer-events            :none
                              :background                :none ;#f00a
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

(defn main-menu []
  (r/with-let [mainmenu-visible (r/atom false)
               numberinput-visible (rf/subscribe [:lab/number-input])]
    (let [toggle-mainmenu #(swap! mainmenu-visible (fnil not false))
          toggle-numberinput #(rf/dispatch [:lab/close-number-input])]
      [scm/mainmenu-example-with-args
       {:showing      @mainmenu-visible
        :dir          #{:right :down}
        :close-button (fn [open] [scb/corner {:on-click toggle-mainmenu} [sc/icon [:> solid/XIcon]]])
        :data         (mainmenu-definition (r/atom {:toggle-mainmenu toggle-mainmenu}))
        :button       (fn [open]
                        [scb/round-normal {:class [:h-10] :on-click toggle-mainmenu}
                         [sc/icon [:> solid/MenuIcon]]])}])))

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
                        [scb/round-normal {:class    [:h-10]
                                           :on-click toggle-menu}
                         [sc/icon [:div {:class [:duration-100 :transform (if open :rotate-180)]}
                                   [:> solid/ChevronDownIcon]]]])}])))