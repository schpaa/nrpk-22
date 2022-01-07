(ns article.helpers
  (:require [schpaa.icon :as icon]
            [schpaa.time :refer [x]]))

(defn tag [s]
  [:div.mb-8 [:div.hollow-pill.inline.border-black.py-1.dark:border-gray-400.dark:text-gray-400 s]])

(defn open-close [*open?]
  [:div.w-10.h-6.mt-1 {:class :w-6 #_(if indent :w-6 :w-6)}
   [:div.center
    [:div.w-6.h-6 {:class (concat
                            [:link-icon]
                            [:transition-transform :duration-300]
                            (if *open? [:rotate-90] [:rotate-180]))}
     (icon/adapt :triangle-right)]]])

(defn by-line [user-id timestamp]
  [:div
   [:div.flex.justifyx-end.gap-1.p-1
    {:class [:text-xs :xbg-gray-300 :xdark:bg-gray-700 :dark:text-gray-100 :text-gray-700]}
    [:span "av " [:a {:class [:hover:underline :hover:cursor-pointer]} user-id]]
    "—"
    (x timestamp)]])