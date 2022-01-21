(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :refer [details-dialog-fn]]
            [schpaa.icon :as icon]))

(defn open-details [id]
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
    :on-click #(do
                 (.stopPropagation %)
                 (details-dialog-fn id))}
   [icon/small :three-vertical-dots]])

(defn toggle-selected [id]
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-300/50" "dark:bg-gray-800/50" "dark:text-gray-300"]
    :on-click #()}
   [icon/small :checked-circle]])

(defn toggle-selected' [{:keys [on? not? on-click]}]
  [:div.w-10.flex.flex-center
   {:sclass    [:text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
    :on-click #(on-click)}
   [icon/small (if on? :checked (if not? :cross-out :circle))]])