(ns logg.views
  "todo Mark a boat as your preferred"
  (:require
    [reagent.core :as r]
    [logg.database]
    [re-frame.core :as rf]
    [schpaa.components.views :refer [goto-chevron general-footer]]
    [schpaa.components.fields :as fields]))

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
                                [booking.views/list-line
                                 (conj {:graph?        (or
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
                                        :insert-after  (goto-chevron idx)})]))
                            data))
                 [general-footer
                  {:insert-before (fn []
                                    [:div.select-none.font-bold.px-2
                                     {:on-click #(rf/dispatch [:app/next-detail])}
                                     (str @(rf/subscribe [:app/details]))])
                   :data          data
                   :key-fn        key
                   :edit-state    edit
                   :markings      markings
                   :c             c}]])))