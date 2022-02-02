(ns schpaa.style)

(def std-header-color [:dark:text-gray-200 :text-gray-800])

(def std-text-color [:dark:text-gray-500 :text-gray-400])

(def sidebar-color [:dark:bg-gray-900 :bg-gray-100])

(def sidebar-text-color-toolbar [:dark:text-white :text-black])

(def sidebar-header-color [:dark:text-gray-400 :text-black])

(def std-not-selected-tab
  ["dark:bg-white/10" "dark:text-white/30"
   "bg-black/10" "text-black/30" :hover:text-black :hover:underline])

(def std-selected-tab [:text-white "dark:bg-black/50"])

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
               :content  {:h2 [:font-sans
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
                          :bg  std-selected-tab}
               :normal   {:bg  [:bg-gray-300 :dark:bg-gray-700]
                          :tab std-not-selected-tab}
               :selected {:bg  [:bg-blue-500]
                          :tab std-selected-tab}}})

(defn fbg'
  ([panel]
   (let [p- [:text-xs :leading-6]                           ;intent minor unimportant text
         p [:text-base :leading-normal :tracking-tight :antialiased] ;intent normal
         hd [:text-xl :font-bold :tracking-tight :leading-snug]
         he [:text-4xl :font-thin]                          ;intent larger header - hero, ?
         defaults {:hd hd
                   :he he
                   :p  p
                   :p- p-
                   :p+ [:text-lg :leading-8]}
         color-map {;intent Utils
                    :calender-table        (conj
                                             {
                                              :fg  [:text-gray-500 :dark:text-white]
                                              :fg+ [:text-black :font-semibold :dark:text-amber-400]
                                              :fg- [:text-gray-500]
                                              :bg  [:bg-gray-300 :dark:bg-gray-700]
                                              :bg- [:bg-gray-200 :dark:bg-gray-800]
                                              :bg+ [:bg-gray-400 :dark:bg-gray-500]}
                                             defaults)
                    :dialog                (conj defaults {:fg+ [:text-black]
                                                           :bg  [:bg-alt]})

                    :side-button           (conj
                                             {:bg  [:bg-gray-300 :hover:bg-gray-100 :dark:bg-gray-400 :dark:hover:bg-gray-500]
                                              :fg  [:text-gray-900]
                                              :fg- [:text-gray-300 :dark:text-gray-900]
                                              :bg- [:bg-gray-100 :dark:bg-gray-600]}
                                             defaults)

                    ;todo Create one for fields
                    :button-danger         (conj
                                             {:fg  [:text-rose-100]
                                              :bg  [:bg-alt :hover:bg-rose-600 :dark:bg-rose-600 :dark:hover:bg-rose-700]
                                              :fg- [:text-gray-300 :dark:text-gray-900]
                                              :fg+ [:text-gray-100 :font-semibold]
                                              :bg- [:dark:bg-gray-600]}
                                             defaults)
                    :button                (conj
                                             {:bg  [:bg-gray-200 :hover:bg-gray-300 :dark:bg-gray-400 :dark:hover:bg-gray-500]
                                              :fg  [:text-gray-900 :dark:text-gray-300]
                                              :fg- [:text-gray-300 :dark:text-gray-900]
                                              :bg- [:bg-gray-100 :dark:bg-gray-600]
                                              :br  [:border-gray-300 :border-2 :dark:border-gray-500]
                                              :bgc [:bg-transparent]
                                              :brb [:border-transparent :border-2]}
                                             defaults)
                    :button-cta            (conj
                                             {:bg  [:bg-alt :hover:bg-alt-600]
                                              :fg  [:text-white]
                                              :bg- [:bg-gray-100 :dark:bg-gray-600]
                                              :fg- [:text-gray-300 :dark:text-gray-900]}
                                             defaults)
                    :error                 (conj
                                             {:bg  [:bg-red-300 :dark:bg-red-400]
                                              :fg+ [:text-black :dark:text-white]}
                                             defaults)
                    ;intent Empty backgrounds where lists appears and void in general
                    :void                  (conj
                                             {:fg- [:text-gray-600 :dark:text-gray-600]
                                              :fg  [:text-gray-500 :dark:text-gray-500]
                                              :fg+ [:text-gray-300 :dark:text-gray-300]
                                              :bg  [:bg-gray-800 :dark:bg-gray-900]
                                              :bg+ [:bg-gray-700 :dark:bg-gray-800]}
                                             defaults)

                    ;intent HEADERS, tab-bars footers
                    :surface               (conj
                                             {:fg+ [:text-gray-900 :dark:text-gray-300]
                                              :fg  [:text-gray-700 :dark:text-gray-500]
                                              :fg- ["text-gray-600/50" "dark:text-gray-500/50"]
                                              :bg  [:bg-gray-300 :dark:bg-gray-800]
                                              :bg+ [:bg-gray-400 :dark:bg-gray-700]}
                                             defaults)

                    :tabbar                (conj
                                             {:br+ [:border-alt :border-b-4] ;selected bottom border
                                              :br  [:border-gray-400] ;lined bottom border
                                              :fg+ [:text-black :dark:text-gray-300] ;selected tab text
                                              :fg  [:text-gray-500 :dark:text-gray-500] ;other tab text
                                              :fg- ["text-gray-600/50" "dark:text-gray-500/50"] ;disabled tab text
                                              :bg  [:bg-gray-300 :dark:bg-gray-800]
                                              :bg+ [:bg-gray-400 :dark:bg-gray-700]}
                                             defaults)

                    ;intent ? elements? list-items?
                    :listitem              (conj
                                             {:fg- [:text-gray-400 "dark:text-black/50"] ;intent unimportant / disabled?
                                              :fg  [:text-gray-700 :dark:text-sky-400] ;intent normal
                                              :fg+ [:text-gray-900 :dark:text-sky-200] ;intent important
                                              :bg  [:bg-gray-200 :dark:bg-sky-700]
                                              :bg- [:bg-gray-600 :dark:bg-sky-900]
                                              :bg+ [:bg-pink-300 :dark:bg-sky-800]} ;intent SELECTED or highlighted
                                             defaults)

                    :booking-listitem      (conj
                                             {:fg- [:text-gray-400 "dark:text-black/50"] ;intent unimportant / disabled?
                                              :fg  [:text-gray-700 :dark:text-sky-400] ;intent normal
                                              :fg+ [:text-gray-900 :dark:text-sky-200] ;intent important
                                              :bg  [:bg-gray-100 :dark:bg-sky-700]
                                              :bg- [:bg-gray-600 :dark:bg-sky-900]
                                              :bg+ [:bg-pink-300 :dark:bg-sky-800]} ;intent SELECTED or highlighted
                                             defaults)

                    :booking-listitem-past (conj
                                             {:fg- [:text-gray-500 "dark:text-black/50"] ;intent unimportant / disabled?
                                              :fg  [:text-gray-300 :dark:text-sky-400] ;intent normal
                                              :fg+ [:text-gray-900 :dark:text-sky-200] ;intent important
                                              :bg  [:bg-gray-600 :dark:bg-sky-700]
                                              :bg- [:bg-gray-600 :dark:bg-sky-900]
                                              :bg+ [:bg-pink-300 :dark:bg-sky-800]} ;intent SELECTED or highlighted
                                             defaults)

                    ;intent FORMS and generic input areas and areas containing INFO
                    :form                  (conj {:fg+ [:text-gray-900 :dark:text-gray-300]
                                                  :fg- [:text-gray-400 :dark:text-gray-600]
                                                  :fg  [:text-gray-500 :dark:text-gray-400]
                                                  :bg- [:bg-gray-200 :dark:bg-gray-700]
                                                  :bg  [:bg-gray-100 :dark:bg-gray-700]
                                                  :bg+ [:bg-gray-300 :dark:bg-gray-500]}
                                                 defaults)
                    :field                 (conj {:fg+ [:text-gray-500 :dark:text-gray-300] ;field-label
                                                  :fg- [:text-gray-700 :dark:text-gray-300] ;intent disabled/readonly
                                                  :fg  [:text-gray-500 :dark:text-gray-300]
                                                  :br- [:border-transparent :dark:border-transparent] ;disabled border
                                                  :br  [:border-gray-500 :dark:border-gray-500] ;enabled border
                                                  :bg- [:bg-gray-200 :dark:bg-gray-800] ;intent disabled/readonly
                                                  :bg  [:bg-gray-100 :dark:bg-gray-600]
                                                  :bg+ [:bg-gray-300 :dark:bg-gray-500]}
                                                 defaults)
                    :field-label           (conj {:fg  [:text-gray-600 :dark:text-gray-300] ;field-label
                                                  :fg- [:text-gray-400 :dark:text-gray-300]} ;intent disabled/readonly
                                                 defaults)}

         #_#_color-map (reduce-kv
                         (fn [db k v]
                           (prn k v)
                           (assoc db k (update v :he (fnil conj []) he)
                                     k (update v :hd (fnil conj []) hd)))
                         {} color-map)]
     (get color-map panel))))