(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :as readymade]
            [schpaa.icon :as icon]
            [logg.database]
            [clojure.set :as set]
            [schpaa.debug :as l]))

(def color-map
  {:normal ["bg-gray-200/50" "hover:bg-gray-100" :text-black]
   :danger [:bg-red-500 :text-white :active:bg-red-600]})

(defn open-booking-details-button [id]
  [:div.w-10.flex.flex-center
   {:class    (concat (:normal color-map))
    :on-click #(do
                 (.stopPropagation %)
                 (readymade/booking-details-dialog-fn (readymade/modal-booking-details-dialogcontent id)))}
   [icon/small :three-vertical-dots]])

(defn remove-booking-details-button [id]
  [:div.w-10.flex.flex-center
   {:class    (concat (:danger color-map))
    :on-click #(do
                 (.stopPropagation %)
                 (readymade/are-you-sure {:title  [:div.space-y-2
                                                   [:h2 "Denne bookingen vil bli slettet!"]
                                                   [l/ppre (filter (fn [{:keys [] :as item}] (= (:id item) id)) (booking.database/read))]]
                                          :footer (str id)

                                          :action (fn [] (js/alert id))}))}

   [icon/small :cross-out]])

(defn open-details [id]
  [:div.w-10.flex.flex-center
   {:class    [:hover:bg-gray-300
               :bg-gray-200 :text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
    :on-click #(do ()
                 (.stopPropagation %)
                 (readymade/details-dialog-fn id))}
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
