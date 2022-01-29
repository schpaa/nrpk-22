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
    [eykt.hov :as hov]
    [schpaa.debug :as l]))

(defn all-boats [{:keys [data details?]}]
  (r/with-let [edit (r/atom false)
               markings (r/atom {})]
    [:div.w-full
     (into [:div {:class [:space-y-px]}]
           (map (fn [[id data]]
                  (let [idx id]
                    [booking.views.picker/list-line
                     (conj {:offset (times.api/day-number-in-year (t/date))}
                           {:details?                details?
                            :id                      id
                            :appearance              (if details? #{:timeline :tall :extra})
                            :on-click                #(swap! markings update idx (fnil not false))
                            :data                    data
                            :insert-before-line-item hov/open-details})]))
                data))]))


