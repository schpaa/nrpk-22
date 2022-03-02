(ns schpaa.style.menu
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]))

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
  :bottom-12 :mb-2)

(o/defstyled menu-items-down menu-items-rounded
  :top-12 :xmt-2)

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

(defn naked-menu-example-with-args [{:keys [button dir data always-show]}]
  [:div.relative
   [ui/menu {:as :div.relative.inline-block.text-left.overflow-visible}
    [ui/menu-button {:as :div}
     (fn [{:keys [open]}]
       (button open))]

    [ui/transition
     {;:show       true                                      ;always-show #_(or always-show false); true ;always-show
      :enter      "transition ease-out duration-100"
      :enter-from "opacity-0 "
      :enter-to   "opacity-100"
      :leave      "transition ease-in duration-300"
      :leave-from "opacity-100"
      :leave-to   "opacity-0 "}

     [ui/menu-items
      [(if (= :down dir) menu-items-down menu-items-up)
       (for [[type args] data]
         (case type
           :header [:div args]
           :menuitem (apply menu-item args)
           :footer [:div args]
           [:div "?"])
         #_(if-let [[icon caption action disabled value] e]
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
       [:div.label (str active) (if (ifn? label) (label (value)) [sc/text (if highlight [sc/bold label] label)])]])]))

(o/defstyled mainmenu-items-rounded :div
  [:& :-top-4x :p-4 :absolute :right-0 :w-56 :focus:outline-none :outline-none
   {:z-index       11000
    :border        "1px solid var(--surface1)"
    :background    "var(--surface000)"
    :border-radius "var(--radius-3)"
    :box-shadow    "var(--shadow-6)"}])

(o/defstyled mainmenu-item-normal :div
  [:& :inline-flex :rounded-full :w-auto :items-center :gap-4 :p-2 :pr-4 :duration-200
   {:color       "var(--surface4)"
    :xbackground "var(--surface000)"}]
  [:&:hover
   :duration-200
   {:background "var(--surface0)"}])

(o/defstyled mainmenu-item-disabled :div
  [:& :inline-flex :rounded-full :w-auto :items-center :gap-4 :p-2
   {:color "var(--surface1)"}])

(o/defstyled mainmenu-item-active :div
  [:& :inline-flex :rounded-full :w-auto :items-center :gap-4 :p-2 :pr-4
   {:color      "var(--surface4)"
    :background "var(--surface1)"}])

(defn mainmenu-item-mm [active disabled {:keys [icon label highlight] :as args}]
  [(cond
     active mainmenu-item-active
     disabled mainmenu-item-disabled
     :else mainmenu-item-normal
     #_(if active
         [mainmenu-item-active icon label]
         (if disabled
           [mainmenu-item-disabled icon label]
           [mainmenu-item-normal icon label])))
   icon [:div {:on-click #(do (tap> "CLICK"))
               :class    [(if highlight :font-bold)]} label]])

(defn mainmenu-example-with-args [{:keys [button close-button dir data always-show]}]
  [:div.z-50
   [ui/menu {:as :div.relative.inline-block.text-left #_.relative.inline-block.text-left.overflow-visible}
    [ui/menu-button {:as :div}
     (fn [{:keys [open]}]
       (button open))]

    [ui/transition
     {:xshow true}
     (let [origin "origin-top-right"
           dur "duration-100"]
       [ui/transition-child
        {:appear     true
         :enter      (apply str (interpose " " ["transition ease-out" dur origin]))
         :enter-from "opacity-0  -rotate-45 "
         :enter-to   "opacity-100  rotate-0"
         :leave      (apply str (interpose " " ["transition ease-in" dur origin]))
         :leave-from "opacity-100 rotate-0"
         :leave-to   "opacity-0  -rotate-45"}
        [ui/menu-items
         {:class [:-mt-12]}
         [mainmenu-items-rounded
          {:class [:x-debug :space-y-1]}
          [:div.absolute.top-2.right-2
           [ui/menu-button {:as :div} (fn [{:keys [open]}]
                                        (close-button open))]]
          (doall (for [[type {:keys [disabled action] :as args}] data]
                   (case type
                     :header [:div args]
                     :menuitem [ui/menu-item
                                {:disabled disabled}
                                (fn [{:keys [active disabled]}]
                                  [:div {:on-click action}
                                   [mainmenu-item-mm active disabled args]])]
                     :hr [:hr {:style {:margin-block       "var(--size-2)"
                                       :background         "var(--surface000)"
                                       :color              "var(--text2)"
                                       :border-top         "1px solid var(--surface1)"
                                       :border-right-width "0px"
                                       :border-left-width  "0px"}}]
                     :footer [:div args]
                     [:div "?"])))]]])]]])