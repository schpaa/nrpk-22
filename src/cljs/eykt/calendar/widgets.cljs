(ns eykt.calendar.widgets
  (:require [re-frame.core :as rf]))

(rf/reg-event-db ::set-tab (fn [db [_ tab-id tab]]
                             (assoc db tab-id tab)))

(rf/reg-sub ::get-tab (fn [db [_ tab-id default]] (get db tab-id default)))

(defn render-tabs [{:keys [selected-classes
                           on-click
                           normal-classes
                           current-tab tabs-id id] :as opts}
                   {:keys [text data]}]
  [:button.h-12                                             ;.mt-1.-mb-1
   {:class    (concat
                (if (= id current-tab)
                  (concat
                    selected-classes
                    [:cursor-default
                     :btn-tab-sel])
                  (concat
                    normal-classes
                    [:btn-tab :cursor-pointer])))
    :on-click #(do
                 (rf/dispatch [::set-tab tabs-id id])
                 (when on-click (on-click data)))} text])

(defn tab-machine [{:keys [selected-classes normal-classes render
                           class
                           on-click current-tab render tabs-id]
                    :as   opts
                    :or   {render render-tabs}}
                   data]
  [:<>
   (into [:div.flex.gap-2.pt-4.px-3.overflow-x-auto         ;.mb-1
          {:class class}]
         (map (fn [[id m]]
                (let [item-data (get-in data [id :data])]
                  [render (assoc opts :id id) m]))
              data))
   #_[:div.border-t-2.border-black.-mt-1x.xz-0.-mx-4 {:style {:margin-top "-2px"}}]
   #_[:div {:class [:border-t-2 :border-black :-mt-0]}]])