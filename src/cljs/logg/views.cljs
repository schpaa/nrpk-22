(ns logg.views
  "todo Mark a boat as your preferred"
  (:require
    [reagent.core :as r]
    [logg.database]
    [re-frame.core :as rf]
    [schpaa.components.views :refer [goto-chevron general-footer]]
    [schpaa.components.fields :as fields]
    [schpaa.modal.readymade :refer [details-dialog-fn]]
    [booking.views.picker]
    [tick.core :as t]
    [schpaa.icon :as icon]
    [eykt.hov :as hov]))

(defn all-boats [{:keys [data details?]}]
  (r/with-let [edit (r/atom false)
               markings (r/atom {})]
              (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
                    detail-level @(rf/subscribe [:app/details])
                    c (count selected-keys)]
                [:div.w-full
                 (into [:div {:class [:bg-gray-300
                                      "dark:bg-gray-800"
                                      :space-y-px]}]
                       (map (fn [[id data]]
                              (let [idx id]
                                [booking.views.picker/list-line
                                 (conj {:offset (times.api/day-number-in-year (t/date))}
                                       {:details?      details?
                                        :id            id
                                        :appearance (if details? #{:timeline :tall :extra})
                                        :on-click      #(swap! markings update idx (fnil not false))
                                        :data          data
                                        :insert-before (when @edit
                                                         [:div.flex.items-center.px-2.bg-gray-500
                                                          [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                                            :handle-change #(swap! markings update idx (fnil not false))}
                                                           "" nil]])
                                        :insert-after  hov/open-details})]))
                            data))])))

