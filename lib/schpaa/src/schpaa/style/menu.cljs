(ns schpaa.style.menu
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]
            [schpaa.debug :as l]
            [booking.ico :as ico]
            [re-frame.core :as rf]
            [reagent.core :as r]))

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

(o/defstyled menu-item :div
  {:color "var(--surface5)"}
  [:.icon-color {:color "var(--brand2)"}]
  [:.disabledcolor {:color "var(--surface1)"}]
  [:.activecolor {:background-color "var(--surface4)"
                  :color            "var(--surface000)"}
   [:.icon-color {:color "var(--brand1)"}]]
  [:.label :text-base :font-medium]
  ([icon label action disabled value]
   [ui/menu-item
    {:disabled disabled}
    (fn [{:keys [active disabled]}]
      [:button {:on-click #(action)
                :class    (into [:h-12 :rounded-sm :gap-4
                                 :group :flex :justify-start :items-center :w-full :px-2 :py-2 :text-sm]
                                [(when disabled :disabledcolor)
                                 (when active :activecolor)])}

       [:div.icon-color (if (fn? icon)
                          (icon (when value (value)))
                          icon)]
       [:div.label (if (ifn? label) (label (value)) label)]])]))

(o/defstyled menu-items :div
  :absolute
  :right-0 :w-56
  :focus:outline-none :outline-none
  ;:ring-1 :ring-black :ring-opacity-5  :overflow-clip
  [:& :p-1
   {:z-index       11000
    :border        "2px solid var(--surface1)"
    :background    "var(--surface00)"
    :border-radius "var(--radius-2)"
    :box-shadow    "var(--shadow-4)"}])

(o/defstyled menu-items-rounded :div
  [:& :p-4 :absolute :right-0 :w-56 :focus:outline-none :outline-none
   {:z-index       11000
    :border        "1px solid var(--surface0)"
    :background    "var(--surface00)"
    :border-radius "var(--radius-3)"
    :box-shadow    "var(--shadow-6)"}])

(o/defstyled menu-items-up menu-items
  :bottom-0 :mb-2)

(o/defstyled menu-items-down menu-items-rounded
  :top-12)

(o/defstyled mainmenu-items-down mainmenu-items-rounded
  :top-0)

(defn menu-example-with-args [{:keys [button dir data always-show]}]
  [:div.relative
   [ui/menu {:as :div.relative.inline-block.text-left.overflow-visible}
    [ui/menu-button {:as :div}
     (fn [{:keys [open]}]
       (button open))]

    [ui/transition
     {;:show       true;always-show
      :enter      "transition ease-out duration-100"
      :enter-from "opacity-0 "
      :enter-to   "opacity-100"
      :leave      "transition ease-in duration-300"
      :leave-from "opacity-100"
      :leave-to   "opacity-0 "}
     [ui/menu-items
      [(if (= :down dir) menu-items-down menu-items-up)
       (for [e data]
         (if-let [[icon caption action disabled value] e]
           [menu-item icon caption action disabled value]
           [:hr {:style {:background         "var(--surface000)"
                         :color              "var(--text2)"
                         :border-top         "1px solid var(--surface1)"
                         :border-right-width "0px"
                         :border-left-width  "0px"}}]))]]]]])

(o/defstyled mainmenu-item :div
  {:color "var(--surface5)"}
  [:.icon-color {:color "var(--brand2)"}]
  [:.disabledcolor {:color "var(--surface1)"}]
  [:.activecolor {:background-color "var(--surface4)"
                  :color            "var(--surface000)"}
   [:.icon-color {:color "var(--brand1)"}]]
  [:.label :text-base :font-medium]
  ([{:keys [icon label action disabled value highlight]}]
   [ui/menu-item
    {:disabled disabled}
    (fn [{:keys [active disabled]}]
      [:button {:on-click #(action)
                :class    (into [:h-12 :rounded-sm :gap-4
                                 :group :flex :justify-start :items-center :w-full :px-2 :py-2 :text-sm]
                                [(when disabled :disabledcolor)
                                 (when active :activecolor)])}

       [:div.icon-color (if (fn? icon)
                          (icon (when value (value)))
                          icon)]
       [:div.label (str active) (if (ifn? label) (label (value)) [sc/text1 (if highlight [sc/bold label] label)])]])]))

(o/defstyled mainmenu-items-rounded-very :div
  [:&
   :absolute :focus:outline-none :outline-none :overflow-hidden
   :min-w-max
   {:border        "2px solid var(--toolbar-)"
    :background    "var(--toolbar)"
    :border-radius "var(--radius-2)"
    :box-shadow    "var(--shadow-4)"}])

(o/defstyled boatinput-panel-from-right :div
  :p-1 :-mr-2 :pr-2
  [:&
   {:border                    "2px solid var(--surface2)"
    :background                "var(--toolbar)"
    :border-top-left-radius    "var(--radius-3)"
    :border-bottom-left-radius "var(--radius-3)"}])

(o/defstyled boatinput-panel-from-bottom :div
  :p-2
  [:& :mx-auto :focus:outline-none :outline-none :mt-4      ;:pointer-events-auto
   {:border                  "2px solid var(--surface1)"
    :width                   "282px"
    :background              "var(--surface0)"
    :border-top-left-radius  "var(--radius-3)"
    :border-top-right-radius "var(--radius-3)"
    :box-shadow              "var(--shadow-5)"}])

(o/defstyled mainmenu-item-normal :div
  [:& :inline-flex :-rounded-full :items-center :gap-4 :p-2 :pr-4 ;:duration-200
   ;{:color "red" #_"var(--surface4)"}
   [:&:hover
    ;:duration-200
    {:background "var(--toolbar-)"}]])

(o/defstyled mainmenu-item-disabled :div
  [:& :inline-flex :rounded-full :items-center :gap-4 :p-2
   {:color "var(--surface1)"}])

(o/defstyled mainmenu-item-active :div
  :focus:outline-none :active:outline-none
  [:& :inline-flex :rounded-full :items-center :gap-4 :p-2 :pr-4
   {:color      "red"                                       ;"var(--surface4)"
    :background "var(--surface0)"}
   [:&:active
    :duration-200
    {:background "var(--surface0)"}]])

(o/defstyled rounded-fill :div
  [:.item {;:background "var(--surface1)"
           :padding       "var(--size-1)"
           :border-radius "var(--radius-round)"}]
  ([{:keys [color background]} ch]
   [:div.item {:style {;:width "100% "
                       :color      color
                       :background background}} ch]))

(o/defstyled not-filled :div
  [:.item {;:background "var(--surface1)"
           :padding       "var(--size-1)"
           :border-radius "var(--radius-round)"}]
  ([{:keys [color background]} ch]
   [:div.item {:style {;:width "100% "
                       :color color}} ch]))

(o/defstyled menu :div
  :h-12 :flex :gap-4 :items-center :px-4 :cursor-pointer
  {:min-width   "24ch"
   :color       "var(--text1)"
   :font-family "Inter"
   :font-size   "var(--font-size-2)"}
  [:&.active
   {:background "var(--content)"}]
  [:&.disabled])

(defn mainmenu-item-mm [{:keys [filledicon action active disabled filled icon label highlight color shortcut] :as args}]
  [menu
   {:class [(cond active :active disabled :disabled :else nil)]}
   (if icon icon [:div.w-7.h-7])
   [:div.flex.justify-between.w-full.gap-4
    label
    (when shortcut
      [sc/as-shortcut shortcut])]])

(defn mainmenu-item-mm' [{:keys [on-click filledicon action active disabled filled icon label highlight color shortcut] :as args}]
  [menu
   {:style    {:background "var(--toolbar-)"}
    :on-click #(do
                 ;(rf/dispatch [:lab/open-menu false])
                 ;(action)
                 #_(tap> action))
    :class    [:disabled]}
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

(defn samplemenu [a b]
  [:div.h-12.w-12.p-1.bg-black.text-white a b])

(defn customthing [a & b]
  (into [:div.select-none.outline-none.w-full
         (conj {:style {:user-select      "none"
                        :margin           "2rem"
                        :background-color (if (:active a) "red" "white")}} a)]
        [[l/ppre-x (js->clj a) (js->clj b)]
         b]))

(o/defstyled custom-thang :div
  :flex :justify-between :w-full :h-12 :items-center :gap-4
  [:& {:user-select "none"
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


(defn mainmenu-example-with-args [{:keys [button close-button data showing!]}]
  (r/with-let [closer (r/atom nil)]
    [:div.select-none
     {:class [:drop-shadow-xl]}
     [:div.select-none.fixed.inset-0.bg-transparent.duration-500
      {:on-click #(do
                    (tap> "xxx")
                    (reset! showing! false)
                    (button true)
                    #_(when @closer (@closer) #_#_rf/dispatch [:lab/close-menu]))
       :class    [(if @showing! :pointer-events-auto :pointer-events-none)]
       :style    {:z-index    -1
                  :background (if @showing! :#0005 :#0000)}}]
     [ui/menu
      (fn [{:keys [open]}]
        [:<>
         [ui/menu-button
          {:as "div"}
          (button open)
          #_((fn [x]
               [:div
                {:on-click #(if @(rf/subscribe [:lab/menu-open])
                              (rf/dispatch [:lab/close-menu])
                              (rf/dispatch [:lab/open-menu]))
                 :style    {:z-index     1000
                            :user-select :none}}
                [:div.cursor-pointer
                 [:div (str @(rf/subscribe [:lab/menu-open]))]
                 [:div (str x)]]]) open)]

         [ui/transition
          {;:show       @showing!
           :class      "drop-shadow-xl "
           :enter      "ease-in duration-100"
           :enter-from "opacity-50  scale-75"
           :enter-to   "opacity-100  scale-100"
           :entered    ""
           :leave      "ease-out duration-200"
           :leave-from "opacity-100  scale-100"
           :leave-to   "opacity-0  scale-50"}
          [ui/menu-items
           {:static true
            :class  [:focus:outline-none :xborder-alt :xborder :w-full]}
           [mainmenu-items-rounded-very
            {:class [:space-y-1x :xml-32 :-mt-12]}
            (doall (for [[type {:keys [disabled action color content shortcut] :as args}] data]
                     (case type
                       :header [:div args]
                       :toggle [ui/menu-item
                                {:disabled disabled
                                 :class    [:outline-none]}
                                (fn [m]
                                  [:div.w-full
                                   [mainmenu-item-mm (conj m {:label [content]})]])]

                       :menuitem-close [mainmenu-item-mm'
                                        (assoc args
                                          :label [sc/text3 ""]
                                          :shortcut "esc"
                                          :active true
                                          :icon [:div {:style {:margin-left   "-6px"
                                                               :border-radius "var(--radius-1)"
                                                               :color         "var(--red-1)"
                                                               :background    "var(--red-8)"}}
                                                 (sc/icon-large ico/closewindow)])]

                       ;todo
                       :menuitem' [ui/menu-item
                                   {:as       "div"
                                    :class    [:outline-none]
                                    :on-click #(do
                                                 (tap> "...")
                                                 (rf/dispatch [:lab/close-menu]))}
                                   (fn [{:keys [active disabled]}]
                                     [custom-thang
                                      {:class [:px-3]}
                                      [:div.p-1x [sc/icon {:class [(if active :hovers :others)]} (:icon args)]]
                                      [sc/as-shortcut shortcut]])
                                   #_(clj->js args) #_[mainmenu-item-mm' (assoc args :icon (sc/icon (:icon args)))]]

                       :menuitem [ui/menu-item
                                  {:disabled disabled
                                   :class    [:outline-none]}
                                  (fn [{:keys [active disabled] :as m}]
                                    [:div                   ;.focus:outline-none {}
                                     {:on-click #(do
                                                   ;(rf/dispatch [:lab/close-menu])
                                                   (action))}
                                     [mainmenu-item-mm (conj m (assoc args :icon (sc/icon (:icon args))))]])]
                       :menuitem- [ui/menu-item
                                   {:disabled disabled}
                                   (fn [{:keys [active disabled] :as m}]
                                     [:div                  ;.focus:outline-none {:on-click action}
                                      [smaller-menuitem (conj args m)]])]
                       :hr [:div.py-2.mr-4 {:style {:color "red"}} [hr]]
                       :space [:div.py-1]
                       :footer [:div args]
                       :div args
                       nil)))]]]])]]))

(defn submenu [{:keys [button close-button dir data showing]}]
  (let [up? (some #{:up} dir)
        right? (some #{:right} dir)]
    [:div.select-none

     [ui/menu {:as :div #_.relative.inline-block.text-left}
      [ui/menu-button {:as :div}
       (fn [{:keys [open]}] (when button (button open)))]
      [ui/transition
       (merge-with merge
                   (standard-menu-animations dir)
                   {:show showing})
       [ui/menu-items
        {:class [:focus:outline-none]}
        [mainmenu-items-rounded-very
         {:style {:z-index 10010}
          :class [:space-y-1
                  (if up? :bottom-12 :top-24)
                  (if right? :-right-1 :-left-2)]}
         [:div.absolute.top-1.right-1
          [ui/menu-button {:as :div} (fn [{:keys [open]}]
                                       (close-button open))]]
         (doall (for [[type {:keys [disabled action color] :as args}] data]
                  (case type
                    :header [:div args]
                    :menuitem [ui/menu-item
                               {:disabled disabled
                                :class    [:outline-none]}
                               (fn [{:keys [active disabled] :as m}]
                                 [:div                      ;.focus:outline-none {}
                                  {:on-click action}
                                  [mainmenu-item-mm (conj args m)]])]
                    :menuitem- [ui/menu-item
                                {:disabled disabled}
                                (fn [{:keys [active disabled] :as m}]
                                  [:div                     ;.focus:outline-none {:on-click action}
                                   [smaller-menuitem (conj args m)]])]
                    :hr [:div.py-2 [hr]]
                    :space [:div.py-1]
                    :footer [:div args]
                    :div args
                    [:div "?"])))]]]]]))

(o/defstyled small-menu-items-rounded :div
  [:& :p-2x :absolute :focus:outline-none :outline-none
   {:border        "1px solid var(--surface1)"
    :background    "var(--surface000)"
    :border-radius "var(--radius-3)"
    :box-shadow    "var(--shadow-6)"}])

(defn small-menu [{:keys [button close-button dir data showing]}]
  (let [up? (some #{:up} dir)
        right? (some #{:right} dir)]
    [:div.z-100.select-none
     [ui/menu {:as :div.relative.inline-block.text-left}
      [ui/menu-button {:as :div}
       (fn [{:keys [open]}] (button open))]
      [ui/transition
       (merge-with merge
                   (standard-menu-animations dir)
                   {:show showing})
       [ui/menu-items
        {:class [:focus:outline-none]}
        [small-menu-items-rounded
         {:class [:space-y-px
                  (if up? :bottom-12 :-top-1)
                  (if right? :-right-1 :-left-2)]}
         [:div.absolute.top-1.right-1
          [ui/menu-button {:as :div} (fn [{:keys [open]}]
                                       (close-button open))]]
         (doall (for [[type {:keys [disabled action color] :as args}] data]
                  (case type
                    :header [:div args]
                    :menuitem [ui/menu-item
                               {:disabled disabled}
                               (fn [{:keys [active disabled] :as m}]
                                 [:div.focus:outline-none {:on-click action}
                                  [mainmenu-item-mm (conj m args)]])]
                    :small-menuitem [ui/menu-item
                                     {:disabled disabled}
                                     (fn [{:keys [active disabled] :as m}]
                                       [:div.focus:outline-none {:on-click action}
                                        [smaller-menuitem (conj m args)]])]
                    :hr [:div.py-2 [hr]]
                    :space [:div.py-1]
                    :footer [:div args]
                    :div args
                    [:div "?"])))]]]]]))
