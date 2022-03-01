(ns schpaa.style.popover
  (:require [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [re-frame.core :as rf]))

(defn overlay []
  [ui/transition-child
   {:enter      "ease-out duration-500"
    :enter-from "opacity-0"
    :enter-to   "opacity-100"
    :leave      "ease-in duration-500"
    :leave-from "opacity-100"
    :leave-to   "opacity-0"}
   [ui/popover-overlay {:on-click #(tap> "wtf") #_#(reset! visible? false)
                        :xstyle   {:z-index 1000}
                        :class    [:fixed :inset-0 :bg-gray-900 :bg-opacity-80]}]])

(o/defstyled popover :div
  [:&
   :bg-white :p-2 :space-y-4 :select-none
   {:box-shadow    "var(--shadow-6)"
    :border-radius "var(--radius-2)"}]
  #_([& ch]
     (into [:div.space-y-4] ch)))

(defn timeout-panel [visible? open data]
  [ui/popover-panel
   {;:unmount true
    :static false
    :class  "absolute right-0 top-0"}
   (r/with-let [r (r/atom nil)
                closer (r/atom nil)
                _ (reset! r (js/setTimeout #(do
                                              (tap> "CLSOING")
                                              (tap> @r)
                                              (@closer)) 15000))]
     (fn [{:keys [close]}]
       (reset! closer close)
       (popover
         [sc/title "Stuff"]
         [l/ppre-x close open]
         (for [{:keys [id content]} data]
           content)
         ;[l/ppre-x data @r]
         [schpaa.style.button/normal {:on-click close #_#(do
                                                           (tap> "CLSOING?")
                                                           (close)
                                                           #_(swap! visible? not))} "close"]))

     (finally
       (tap> @r)
       (js/clearTimeout @r)
       (tap> "FINAL")))])

(defn popover-button [visible? open]
  [ui/popover-button {:style {:z-index 19001}}
   [schpaa.style.button/normal
    [sc/row {:class [:gap-2]}
     [:span "Popover"]
     (if open
       [sc/icon [:> solid/XIcon]]
       [sc/icon [:> solid/ChevronDownIcon]])]]])

(defn popover-example [visible data]

  [:<>
   ;[l/ppre-x visible]
   [ui/popover {:open  visible
                :class :relative}
    (fn [{:keys [open close]}]
      [:<>
       #_[popover-button visible open]

       [ui/transition
        {:appear true
         :show   visible}
        [overlay]

        (when visible
          [ui/transition-child
           {:show       visible
            :enter      "transition  ease-in duration-200"
            :enter-from " opacity-0"
            :enter-to   " opacity-100"
            :leave      "transition  ease-in duration-200"
            :leave-from " opacity-100"
            :leave-to   " opacity-0"}
           #_[timeout-panel visible open data]
           [ui/popover-panel
            {:static true
             :class  "absolute right-0 top-0"}
            (fn [{:keys [close]}]
              (popover
                [sc/title "Stuff"]
                ;[l/ppre-x visible open (rf/subscribe [:lab/show-popover])]
                (for [{:keys [id content]} data]
                  content)
                [popover-button visible open]
                [schpaa.style.button/normal {:on-click #(do (tap> "CLOSE")
                                                            (close)
                                                            (rf/dispatch [:lab/show-popover]))} "Close"]))]])]])]])

(def solutions
  [{:name        "Insights"
    :description "Measure actions your users take"
    :href        "##"
    :icon        outline/LightBulbIcon}
   {:name        "Automations"
    :description "Create your own targeted content"
    :href        "##"
    :icon        outline/CogIcon}
   {:name        "Reports"
    :description "Keep track of your growth"
    :href        "##"
    :icon        outline/ChartBarIcon}])

(defn popover-example2 []
  [:div.w-full.max-w-sm.px-4
   {:style {:height "400px"}}
   [ui/popover {:class :relative}
    (fn [{:keys [open]}]
      [:<>
       [ui/popover-button {:class [:text-white :group :bg-orange-700 :px-3 :py-2 :rounded-md :inline-flex :items-center :text-base :font-medium :hover:text-opacity-100 :focus:outline-none :focus-visible:ring-2 :focus-visible:ring-white :focus-visible:ring-opacity-75
                                   (when open :text-opacity-90)]}
        [:span "Solutions"]
        [:> solid/ChevronDownIcon
         {:class       [:ml-2 :h-5 :w-5 :text-orange-300 :group-hover:text-opacity-80 :transition :ease-in-out :duration-150
                        (when open :text-opacity-70)]
          :aria-hidden true}]]
       [ui/popover-overlay {:on-click #(tap> "wtf") #_#(reset! visible? false)
                            :xstyle   {:z-index 1000}
                            :class    [:fixed :inset-0 :bg-gray-900 :bg-opacity-80]}]
       [ui/transition
        {:enter      "transition ease-out duration-200"
         :enter-from "opacity-0 translate-y-1"
         :enter-to   "opacity-100 translate-y-0"
         :leave      "transition ease-in duration-150"
         :leave-from "opacity-100 translate-y-0"
         :leave-to   "opacity-0 translate-y-1"}
        [ui/popover-panel
         {:class "absolute z-10 w-screen max-w-sm px-4 mt-3 transform -translate-x-1/2 left-1/2 sm:px-0 lg:max-w-3xl"}
         [:div.overflow-hidden.rounded-lg.shadow-lg.ring-1.ring-black.ring-opacity-5
          [:div.relative.grid.gap-8.bg-white.p-7.lg:grid-cols-2
           (for [{:keys [name description href icon]} solutions]
             ^{:key name}
             [:a.flex.items-center.p-2.-m-3.transition.duration-150.ease-in-out.rounded-lg.hover:bg-gray-50.focus:outline-none.focus-visible:ring.focus-visible:ring-orange-500.focus-visible:ring-opacity-50
              {:href href}
              [:div.flex.items-center.justify-center.flex-shrink-0.text-white
               [:div.w-10.h-10.sm:h-12.sm:w-12.rounded-md.bg-orange-100.flex.items-center.justify-center
                [:> icon {:class [:text-orange-800 :w-8 :h-8 :sm:w-10 :sm:h-10] :aria-hidden true}]]]
              [:div.ml-4
               [:p.text-sm.font-medium.text-gray-900 name]
               [:p.text-sm.text-gray-500 description]]])]
          [:div.p-4.bg-gray-50
           [:a.flow-root.px-2.py-2.transition.duration-150.ease-in-out.rounded-md.hover:bg-gray-100.focus:outline-none.focus-visible:ring.focus-visible:ring-orange-500.focus-visible:ring-opacity-50
            {:href "##"}
            [:span.flex.items-center
             [:span.text-sm.font-medium.text-gray-900 "Documentation"]]
            [:span.block.text-sm.text-gray-500 "Start integrating products and tools"]]]]]]])]])