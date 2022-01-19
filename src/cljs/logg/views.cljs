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
    [schpaa.icon :as icon]))

;todo This is nonsense, migrate to the readymade in booking.views.picker/insert-after
(defn open-details [id]
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-200"]
    :on-click #(details-dialog-fn id)}
   [icon/small :three-vertical-dots]])

(defn all-boats [{:keys [data]}]
  (r/with-let [edit (r/atom false)
               markings (r/atom {})]
              (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
                    detail-level @(rf/subscribe [:app/details])
                    c (count selected-keys)]
                [:div.w-full
                 (into [:div {:class [:overflow-clip
                                      :space-y-px
                                      :first:rounded-t
                                      :last:rounded-b]}]
                       (map (fn [[id data]]
                              (let [idx id]
                                [booking.views.picker/list-line
                                 (conj {:offset (times.api/day-number-in-year (t/date))}
                                       {:graph?        (or
                                                         (= detail-level 2)
                                                         (< detail-level 1))
                                        :details?      (= detail-level 2)
                                        :compact?      (= detail-level 0)
                                        :id            id
                                        :on-click      #(swap! markings update idx (fnil not false))
                                        :data          data
                                        :insert-before (when @edit
                                                         [:div.flex.items-center.px-2.bg-gray-500
                                                          [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                                            :handle-change #(swap! markings update idx (fnil not false))}
                                                           "" nil]])
                                        :insert-after  open-details})]))
                            data))
                 [general-footer
                  {:insert-before (fn []
                                    [:div (schpaa.components.tab/tab
                                            (conj schpaa.components.tab/select-bar-bottom-config
                                                  {:selected @(rf/subscribe [:app/details])
                                                   :select   #()})
                                            [0 "S" #(rf/dispatch [:app/set-detail 0])]
                                            [1 "M" #(rf/dispatch [:app/set-detail 1])]
                                            [2 "L" #(rf/dispatch [:app/set-detail 2])])])
                   :data          data
                   :key-fn        key
                   :edit-state    edit
                   :markings      markings
                   :c             c}]])))