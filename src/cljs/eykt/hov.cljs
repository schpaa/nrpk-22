(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :refer [details-dialog-fn]]
            [schpaa.icon :as icon]))

(defn open-details [id]
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-300/50" "dark:bg-gray-800/50" "dark:text-gray-300"]
    :on-click #(do
                 (.stopPropagation %)
                 (details-dialog-fn id))}
   [icon/small :three-vertical-dots]])
