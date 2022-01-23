(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :refer [details-dialog-fn
                                            modal-booking-title
                                            booking-details-dialog-fn]]
            [schpaa.icon :as icon]
            [logg.database]))

(def color-map
  {:normal ["bg-gray-200/50" "hover:bg-gray-100" :text-black]})

(defn open-booking-details-button [id]
  [:div.w-10.flex.flex-center
   {:class    (concat (:normal color-map))
    :on-click #(do
                 (.stopPropagation %)
                 (booking-details-dialog-fn (modal-booking-title id)))}
   [icon/small :three-vertical-dots]])

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
   {:sclass   [:text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
    :on-click #(on-click)}
   [icon/small (if on? :checked (if not? :cross-out :circle))]])