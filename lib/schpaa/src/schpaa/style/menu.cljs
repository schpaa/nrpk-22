(ns schpaa.style.menu
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            [schpaa.debug :as l]
            [booking.ico :as ico]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

(defn standard-menu-animations [dir]
  (let [up? (some #{:up} dir)
        right? (some #{:right} dir)
        origin-enter "origin-top-right"
        origin-leave "origin-top-right"
        dur "duration-200 "
        enter-from-translate (if up? "translate-y-0" "-translate-y-24")
        enter-to-translate (if up? "-translate-y-2" "-translate-y-20")
        leave-from-translate (if up? "-translate-y-10" "-translate-y-10")
        leave-to-translate (if up? "-translate-y-10" "-translate-y-32")
        enter-opacity-from "opacity-50"
        enter-opacity-to "opacity-100"
        leave-opacity-from "opacity-100"
        leave-opacity-to "opacity-0"
        f (fn [& el] (apply str (interpose " " el)))]
    {:appear     true
     ;:unmount true

     :enter      (f " transition ease-out " dur origin-enter)
     :enter-from (f enter-opacity-from enter-from-translate)
     :enter-to   (f enter-opacity-to enter-to-translate)
     :entered    (if up? " -translate-y-2 " " -translate-y-20 ")

     :leave      (f " transition-opacity  ease-in " dur origin-leave)
     :leave-from (f leave-opacity-from leave-from-translate)
     :leave-to   (f leave-opacity-to leave-to-translate)}))

(o/defstyled mainmenu-items-rounded-very :div
  [:&
   :absolute :focus:outline-none :outline-none :overflow-hidden
   :min-w-max
   {:border        "4px solid var(--toolbar-)"
    :background    "var(--toolbar)"
    :border-radius "var(--radius-3)"
    :box-shadow    "var(--shadow-4)"}])

(o/defstyled boatinput-panel-from-right :div
  [:&                                                       ;:p-1 :-mr-2 :pr-2
   {;:border                    "2px solid var(--surface2)"
    :background                 "var(--toolbar)"
    ;:overflow :hidden
    :-border-top-left-radius    "var(--radius-3)"
    :-border-bottom-left-radius "var(--radius-3)"}])

(o/defstyled boatinput-panel-from-left :div
  ;inside the panel
  [:& :xml-32
   {:background "var(--toolbar)"}])

(o/defstyled menu :div
  :flex :gap-4 :items-center :px-4
  {:min-height    "var(--size-8)"
   :padding-block "var(--size-2)"
   :min-width     "24ch"
   :cursor        :pointer
   :color         "var(--text1)"
   :font-family   "Inter"
   :font-size     "var(--font-size-2)"}
  [:&.active
   {:background "var(--content)"}]
  [:&.disabled
   {:cursor :default}])

(defn mainmenu-item-mm [{:keys [active disabled icon label shortcut style]}]
  [menu
   {:class [(cond active :active
                  disabled :disabled :else nil)]}
   (if icon [:div {:style style} icon] [:div.w-7.h-7])
   [:div.flex.justify-between.w-full.gap-4
    [(if disabled sc/text3 sc/text1) {:style {:font-size "var(--font-size-2)"}} label]
    (when shortcut
      [sc/as-shortcut shortcut])]])

(defn mainmenu-item-mm2 [{:keys [active disabled icon label shortcut]}]
  [menu
   {:class [(cond active :active
                  disabled :disabled :else nil)]}
   (if icon icon [:div.w-7.h-7])
   [:div.flex.justify-between.w-full.gap-4
    label
    (when shortcut
      [sc/as-shortcut shortcut])]])

;region smaller menu

(o/defstyled smaller-menuitem- :div
  :flex :w-full)

(o/defstyled smaller-menuitem-normal smaller-menuitem-
  {:color "var(--surface4)"}
  [:&:hover
   :duration-200
   {:background "var(--surface0)"}])

(o/defstyled smaller-menuitem-disabled smaller-menuitem-
  {:color "var(--surface1)"})

(o/defstyled smaller-menuitem-active smaller-menuitem-
  {:color      "var(--surface4)"
   :background "var(--surface0)"})

(defn smaller-menuitem [{:keys [active disabled icon label highlight]}]
  [:div {:style {:max-width "13rem"}}
   [(cond
      highlight smaller-menuitem-normal
      active smaller-menuitem-active
      disabled smaller-menuitem-disabled
      :else smaller-menuitem-normal)
    {:class [:justify-start :items-center :gap-2 :h-12 :px-2]}
    [sc/icon icon]
    [sc/subtext {;:on-click #(do (tap> "CLICK"))
                 :style (if disabled {:color "var(--surface1)"}
                                     {:color "var(--surface4)"})
                 :class [:truncate (if highlight :font-bold)]} label]]])

;endregion

(o/defstyled hr :hr
  {
   :margin-left        "var(--size-8)"
   ;:background         "var(--surface000)"
   ;:color              "var(--text1)"
   ;:border-style "dashed"
   :border-top         "1px dashed var(--text3)"
   :border-right-width "0px"
   :border-left-width  "0px"})

(o/defstyled custom-thang :div
  :flex :justify-between :w-full :items-center :gap-4
  [:& {:height      "44px"
       :user-select "none"
       :outline     "none"
       :color       "var(--text1)"
       :background  "var(--toolbar-)"}
   [:.hovers
    {:color            "var(--text1)"
     :background-color "var(--content)"
     :border-radius    "var(--radius-0)"}]
   [:.others
    {:color            "var(--text1)"
     :background-color "none"}]])


(defn mainmenu-example-with-args [{:keys [button close-button data showing! dir]}]
  [:div.select-none.z-50
   {:class [:drop-shadow-xl]}
   [:div.select-none.fixed.inset-0.bg-transparent.duration-500
    {:on-click #(do
                  (tap> "xxx")
                  ;(reset! showing! false)
                  (button true))

     :class    [(if @showing! :pointer-events-auto :pointer-events-none)]
     :style    {:z-index    -1
                :background (if @showing! :#0005 :#0000)}}]
   [ui/menu
    (fn [{:keys [open]}]
      [:<>
       [ui/menu-button
        {:as    "button"
         :class [(o/classname hoc.buttons/round')]}
        (button open)]
       [ui/transition
        {;:show       true;@showing!
         ;:show       true
         :class      (str "drop-shadow-2xl " (if (some #{:right} dir) "-ml-2" "-mr-2"))
         :enter      "ease-in duration-100"
         :enter-from "opacity-50  scale-75"
         :enter-to   "opacity-100  scale-100"

         :entered    "z-100 "

         :leave      "ease-out duration-200"
         :leave-from "opacity-100  scale-100"
         :leave-to   "opacity-0  scale-50"}
        [ui/menu-items
         {:static true
          :class  [:focus:outline-none :w-full]}
         [mainmenu-items-rounded-very
          {:style (conj {}
                        (if
                          (some #{:right} dir)
                          {:left      "var(--size-2)"
                           :max-width "25ch"}
                          {:right "var(--size-2)"})

                        #_(if (some #{:down} dir)
                            {:margin-top "0px"}
                            {:bottom "48px"}))
           :class []}
          (doall (for [[type {:keys [disabled action content shortcut] :as args}] data]
                   (case type
                     :toggle [ui/menu-item
                              {:disabled disabled
                               :class    [:outline-none]}
                              (fn [m]
                                [:div [mainmenu-item-mm2 (conj m {:label [content]})]])]

                     :item [ui/menu-item
                            {:disabled disabled
                             :class    [:outline-none]}
                            (fn [{:keys [active disabled] :as m}]
                              [:div
                               {:on-click action}
                               [mainmenu-item-mm (conj m (assoc args :icon (sc/icon (:icon args))))]])]

                     :hr [:div.py-2.mr-4 {:style {:color "red"}} [hr]]
                     :space [:div.py-1]
                     :footer [:div args]
                     :div args
                     nil)))]]]])]])
