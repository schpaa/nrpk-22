(ns eykt.hov
  "higher order views"
  (:require [schpaa.modal.readymade :as readymade]
            [schpaa.icon :as icon]
            [logg.database]
            [clojure.set :as set]
            [schpaa.debug :as l]
            [schpaa.style :as st]))

(def color-map
  {:normal ["bg-gray-200/50" "hover:bg-gray-100" :text-black]
   :danger [:bg-red-500 :text-white :active:bg-red-600]})

(defn open-booking-details-button [id]
  (let [{:keys [bg fg]} (st/fbg' :button)]
    [:div.w-10.flex.flex-center
     {:class    (concat bg fg)
      :on-click #(do
                   (.stopPropagation %)
                   (readymade/message {:flags   #{}
                                       :content [(readymade/modal-booking-details-dialogcontent id)]}))}
     [icon/small :three-vertical-dots]]))

(defn remove-booking-details-button [id input-data]
  (let [{:keys [bg fg]} (st/fbg' :button-danger)]
    [:div.w-10.flex.flex-center
     {:class    (concat bg fg)
      :on-click #(do
                   (.stopPropagation %)
                   (readymade/ok-cancel
                     {:header  "Vil du virkelig slette denne bookingen?"
                      :content [[:div "some content"]
                                [l/ppre input-data]]
                      :ok      (fn [] (js/alert (l/ppr ["DELETE" id])))}))}
     [icon/small :cross-out]]))

(defn open-details [id]
  (let [{:keys [bg fg]} (st/fbg' :button)]
    [:div.w-10.flex.flex-center
     {:class    (concat bg fg)
      :on-click #(do ()
                     (.stopPropagation %)
                     (readymade/details-dialog-fn id))}
     [icon/small :three-vertical-dots]]))

(defn toggle-selected' [{:keys [on? not? on-click]}]
  [:div.w-10.flex.flex-center
   {:sclass   [:text-black "bg-gray-200" "dark:bg-gray-600" "dark:text-gray-300"]
    :on-click #(on-click)}
   [icon/small (if on? :checked (if not? :cross-out :circle))]])

;fixme common colormap, see hov/details
(defn remove-from-list-actions [clicks-on-remove selected]
  (let [{:keys [bg fg]} (st/fbg' :button-danger)]
    (fn [id]
      (letfn [(delete [] (do (swap! selected set/difference #{id})
                             (reset! clicks-on-remove {})))
              (confirm [] (reset! clicks-on-remove {id 1}))
              (reset [] (reset! clicks-on-remove {}))]
        (let [clicks (get @clicks-on-remove id)]
          [:div.flex.bg-gray-200.h-full
           (when (pos? clicks)
             [:div.flex.items-center.justify-center.border-none.w-full.px-2.rounded-none
              {:class    (select-keys (st/fbg' :button) [:fg :bg])
               :on-click #(reset)}
              (icon/small :cross-out)])
           [:div.flex.items-center.border-none.w-full.h-full.bg-altx.px-2.rounded-none
            {:class    (concat bg fg)
             :on-click (fn [] (if (pos? clicks)
                                (delete)
                                (confirm)))}
            (if (pos? clicks)
              (icon/small :checked)
              (icon/small :cross-out))]])))))
