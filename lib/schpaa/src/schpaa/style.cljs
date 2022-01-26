(ns schpaa.style)

(def hdr-color [:dark:bg-gray-800 :bg-gray-200 :xdark:bg-opacity-50])

(def hdr-writingmode-color [:dark:bg-gray-800 :bg-gray-200 :xdark:bg-opacity-50])

(def hdr-writingmode-footer-color [:dark:bg-gray-900 :bg-gray-800 :xdark:bg-opacity-50])

(def hdr-text-color [:dark:text-gray-200
                     :text-gray-800])

(def std-color [:dark:bg-gray-700 :bg-gray-100])

(def std-header-color-inverted [:dark:text-gray-300
                                :text-gray-200])

(def std-header-color [:dark:text-gray-200
                       :text-gray-800])

(def std-text-color [:dark:text-gray-500
                     :text-gray-400])

(def sidebar-color [:dark:bg-gray-900 :bg-gray-100])

(def sidebar-color-toolbar [:dark:bg-gray-800 :bg-altx :bg-white])

(def sidebar-text-color-toolbar [:dark:text-white :text-black])

(def sidebar-text-selected-color-toolbar [:dark:bg-alt :bg-alt :dark:text-white :text-white])

(def sidebar-header-color [:dark:text-gray-400
                           :text-gray-700])

(def sidebar-text-color [:dark:text-gray-300
                         :text-gray-500])

(def std-not-selected-tab
  ["dark:bg-white/10" "dark:text-white/30"
   "bg-black/10" "text-black/30" :hover:text-black :hover:underline])

(def std-selected-tab
  [#_"bg-black/25" :text-white "dark:bg-black/50"])

(def color-map
  ;todo Can integrant be used for self-referential color?
  {:list      {:icon     :compass
               :normal   {:fg  [:text-alt]
                          :bg  []
                          :tab std-not-selected-tab}
               ;todo: This is the active branch, nomenclature could improve, `selected` is not meaningful here
               :selected {:fg  [:text-alt-100]
                          :bg  ["bg-alt-500" "dark:bg-alt-600" :text-white]
                          :tab std-selected-tab}
               :content  {:h2 [;:tracking-wide
                               :font-sans
                               :text-black
                               :font-medium
                               :text-sm
                               :hover:underline
                               :cursor-default
                               :uppercase]
                          :fg ["text-black/80"]}}
   :bar-chart {:icon     :command
               :normal   {:fg  [:text-orange-500]
                          :bg  []
                          :tab std-not-selected-tab}
               :selected {:fg  [:text-white]
                          :bg  ["bg-orange-600" "dark:bg-orange-600"]
                          :tab std-selected-tab}}
   :cloud     {:icon     :cloud
               :normal   {:fg  [:text-amber-500]
                          :bg  []
                          :tab std-not-selected-tab}
               :selected {:fg  [:text-white]
                          :bg  ["bg-amber-500" "dark:bg-amber-600"]
                          :tab std-selected-tab}}
   :brygge    {:icon     :brygge
               :normal   {:fg  [:text-sky-600]
                          :bg  []
                          :tab std-not-selected-tab}
               :selected {:fg  [:text-white]
                          :bg  ["bg-sky-500" "dark:bg-sky-600"]
                          :tab std-selected-tab}}
   :cross-out {:icon :cross-out}
   :main      {:content  {:bg2 ["bg-gray-300" "dark:bg-gray-700"]
                          :bg  std-selected-tab #_[:mx-4 "bg-black/30" :text-white "dark:bg-black/50"]  #_[:bg-gray-300 :dark:bg-gray-800]}
               :normal   {:bg  [:bg-gray-300 :dark:bg-gray-700]
                          :tab std-not-selected-tab}
               :selected {:bg  [:bg-blue-500]
                          :tab std-selected-tab #_[:bg-gray-500 :text-gray-700]}}})
