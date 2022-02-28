(ns schpaa.style.popover
  (:require [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]))

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
  :bg-white :p-4                                            ;:w-64 :h-64  :overflow-hidden
  [:& {;:z-index       100
       :box-shadow     "var(--shadow-6)"
       :-border-radius "var(--radius-2)"}]
  ([& ch]
   (into [:div.space-y-1] ch)))

(defn timeout-panel [visible? open data]
  [ui/popover-panel
   {;:unmount true
    :static true
    :class  "absolute -right-1 top-0 "}
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
         (for [{:keys [id content]} data]
           content)
         [l/ppre-x data @r]
         [schpaa.style.button/normal {:on-click #(do (swap! visible? not))} "close"]))

     (finally
       (tap> @r)
       (js/clearTimeout @r)
       (tap> "FINAL")))])

(defn popover-example [data]
  (r/with-let [visible? (r/atom true)]
    [ui/popover {:open  true
                 :class :relatives}
     (fn [{:keys [open]}]
       [:<>

        [ui/popover-button {:style {:padding "1rem"
                                    :z-index 19001}
                            :class [:absolute :top-1 :right-1
                                    :text-white :group :bg-orange-700 :px-3 :py-2 :rounded-md :inline-flex
                                    :items-center :text-base :font-medium
                                    :hover:text-opacity-100
                                    ;:focus:outline-none
                                    ;:focus-visible:ring-2
                                    ;:focus-visible:ring-white
                                    :focus-visible:ring-opacity-75
                                    #_(when open :text-opacity-90)]}
         [:span "Solutions"]
         [l/ppre-x visible?]
         [:> solid/ChevronDownIcon
          {:class       [:ml-2 :h-5 :w-5 :text-orange-300 :group-hover:text-opacity-80 :transition :ease-in-out :duration-150
                         (when open :text-opacity-70)]
           :aria-hidden true}]]

        [ui/transition
         {:appear true
          :show   @visible?}

         [overlay]

         [ui/transition-child
          {
           :enter      "transition transform ease-in duration-700"
           :enter-from :-translate-y-full
           :enter-to   "translate-y-0"
           :leave      "transition transform ease-in duration-700"
           :leave-from "translate-y-0"
           :leave-to   "-translate-y-64"}

          [timeout-panel visible? open data]]]])]))