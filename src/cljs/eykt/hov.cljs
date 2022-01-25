(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :refer [details-dialog-fn
                                            modal-booking-title
                                            booking-details-dialog-fn]]
            [schpaa.icon :as icon]
            [logg.database]
            [clojure.set :as set]))

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
   {:class    [:hover:bg-gray-300
               :bg-gray-200 :text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
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

;fixme common colormap, see hov/details
(defn remove-from-list-actions [clicks-on-remove selected]
  (fn [id]
    (letfn [(delete [] (do (swap! selected set/difference #{id})
                           (reset! clicks-on-remove {})))
            (confirm [] (reset! clicks-on-remove {id 1}))
            (reset [] (reset! clicks-on-remove {}))]
      (let [clicks (get @clicks-on-remove id)]
        [:div.flex.bg-gray-200.h-full
         [:div.flex.items-center.border-none.w-full.h-full.bg-altx.px-2.rounded-none
          {:class    (if (pos? clicks) [:btn-danger] [:btn-free])
           :on-click (fn [] (if (pos? clicks)
                              (delete)
                              (confirm)))}
          (if (pos? clicks)
            (icon/small :checked)
            (icon/small :cross-out))]
         (when (pos? clicks)
           [:div.flex.items-center.justify-center.border-none.btn-cta.w-full.px-2.rounded-none
            {:on-click #(reset)}
            (icon/small :arrow-left)])]))))
