(ns schpaa.style)

;(def hdr-color [:dark:bg-gray-800 :bg-gray-200 :xdark:bg-opacity-50])

;(def hdr-writingmode-color [:dark:bg-gray-800 :bg-gray-200 :xdark:bg-opacity-50])

;(def hdr-writingmode-footer-color [:dark:bg-gray-900 :bg-gray-800 :xdark:bg-opacity-50])

;(def hdr-text-color [:dark:text-gray-200]   :text-gray-800)

;(def std-color [:dark:bg-gray-700 :bg-gray-100])

;(def std-header-color-inverted [:dark:text-gray-300   :text-gray-200)

(def std-header-color [:dark:text-gray-200
                       :text-gray-800])

(def std-text-color [:dark:text-gray-500
                     :text-gray-400])

(def sidebar-color [:dark:bg-gray-900 :bg-gray-100])

;(def sidebar-color-toolbar [:dark:bg-gray-800 :bg-altx :bg-white])

(def sidebar-text-color-toolbar [:dark:text-white :text-black])

;(def sidebar-text-selected-color-toolbar [:dark:bg-alt :bg-alt :dark:text-white :text-white])

(def sidebar-header-color [:dark:text-gray-400
                           :text-black])

;(def sidebar-text-color [:dark:text-gray-300 :text-gray-500])

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

;;;;;;;;;;;;;;;;;;;;;

#_(defn fbg
    "for something"
    ([bg]
     (fbg nil bg nil))
    ([fg bg]
     (fbg fg bg nil))
    ([fg bg r]
     (let [fg (when fg
                (case fg
                  ;weak
                  0 (case bg
                      0 [:text-gray-400 :dark:text-gray-400 :font-extralight]
                      1 [:text-gray-400 :dark:text-gray-400 :font-extralight]
                      2 [:text-gray-700 :dark:text-gray-900 :font-extralight]
                      3 [:text-gray-500 :dark:text-gray-900 :font-light]
                      [])
                  ;strong
                  1 (case bg
                      0 [:text-gray-300 :dark:text-gray-300 :font-medium]
                      1 [:text-gray-300 :dark:text-gray-300 :font-medium :leading-snug]
                      2 [:text-gray-700 :dark:text-gray-500 :font-medium]
                      3 [:text-gray-700 :dark:text-gray-400 :font-normal]
                      [])))
           bg (case bg
                0 [:bg-gray-900 :dark:bg-gray-900]
                1 [:bg-gray-700 :dark:bg-gray-800]
                2 [:bg-gray-400 :dark:bg-gray-700]
                3 [:bg-gray-50 :dark:bg-gray-600]
                [])]


       {:class (concat fg bg r)})))

(defn fbg'
  ([panel]
   (let [p- [:text-sm :leading-relaxed]                     ;intent minor unimportant text
         p [:text-base :leading-normal :tracking-tight :antialiased] ;intent normal
         hd [:text-xl :font-extrabold :tracking-tight :leading-snug]
         he [:text-4xl :font-thin]                          ;intent larger header - hero, ?
         color-map {;intent Empty backgrounds and lists, void
                    :void    {:fg- [:text-gray-600 :dark:text-gray-600]
                              :fg  [:text-gray-500 :dark:text-gray-500]
                              :fg+ [:text-gray-300 :dark:text-gray-300]
                              :bg  [:bg-gray-900 :dark:bg-gray-900]
                              :bg+ [:bg-gray-800 :dark:bg-gray-800]
                              :hd  hd
                              :he  he
                              :p   p
                              :p-  p-}
                    ;intent HEADERS, tab-bars footers
                    :surface {:fg+ [:text-gray-900 :dark:text-gray-300]
                              :fg  [:text-gray-700 :dark:text-gray-500]
                              :fg- ["text-gray-600/50" "dark:text-gray-500/50"]
                              :bg  [:bg-gray-300 :dark:bg-gray-800]
                              :bg+ [:bg-gray-400 :dark:bg-gray-700]
                              :hd  hd
                              :he  he
                              :p   p
                              :p-  p-}

                    ;intent ? elements? list-items?
                    2        {:fg- [:text-pink-500 "dark:text-black/50"] ;intent unimportant / disabled?
                              :fg  ["text-black/90" :dark:text-sky-400] ;intent normal
                              :fg+ ["text-black/80" :dark:text-sky-200] ;intent important
                              :bg  [:bg-pink-200 :dark:bg-sky-700]
                              :bg+ [:bg-pink-300 :dark:bg-sky-800] ;intent SELECTED or highlighted
                              :hd  hd
                              :he  he
                              :p   p
                              :p-  p-}
                    ;intent FORMS and generic input areas and areas containing INFO
                    3        {:fg+ [:text-gray-900 :dark:text-gray-300]
                              :fg- [:text-gray-400 :dark:text-gray-600]
                              :fg  [:text-gray-500 :dark:text-gray-400]
                              :bg  [:bg-gray-100 :dark:bg-gray-700]
                              :bg+ [:bg-gray-300 :dark:bg-gray-500]
                              :hd  hd
                              :he  he
                              :p   p
                              :p-  p-}}
         #_#_color-map (reduce-kv
                         (fn [db k v]
                           (prn k v)
                           (assoc db k (update v :he (fnil conj []) he)
                                     k (update v :hd (fnil conj []) hd)))
                         {} color-map)]
     (get color-map panel))))