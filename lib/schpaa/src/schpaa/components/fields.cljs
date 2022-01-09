(ns schpaa.components.fields
  (:refer-clojure :exclude [time]))

(defn text [{:keys [handle-change handle-blur values]} label name]
  [:div.flex-col.flex
   [:label {:for name} label]
   [:input.h-10.field-w-max
    {:class     [:form-input :active:border-2 :border-none :rounded]
     :name      name
     :id        name
     :type      :text
     :value     (values name)
     :on-change handle-change
     :on-blur   handle-blur}]])

(defn date [{:keys [handle-change handle-blur values class]} label name]
  [:div.flex-col.flex
   {:class class}
   [:label {:for name} label]
   [:input.h-10.field-w-max
    {:class     [:form-input :active:border-2 :border-none :rounded]
     :name      name
     :id        name
     :type      :date
     :value     (values name)
     :on-change handle-change
     #_#_:on-blur #(let [v (-> % .-target .-value)]
                     (tap> ["blured" v])
                     (rf/dispatch [:exp/set-date-filter v])
                     (handle-blur))}]])


(defn time [{:keys [handle-change handle-blur values class]} label name]
  [:div.flex-col.flex
   {:class class}
   [:label {:for "zz"} label]
   [:input.h-10.field-w-max
    {:class     [:form-input :active:border-2 :border-none :rounded]
     :name      name
     :id        name
     :type      :time
     :format    "hh:mm"
     :value     (values name)
     :on-change handle-change
     :on-blur   handle-blur}]])

(defn button [on-click label caption]
  [:div.flex-col.flex
   (if label
     [:label {:for name} label]
     [:div.h-6])
   [:button.btn-narrow.btn-free
    {:class    ["bg-black/50"]
     :type     :button
     :on-click on-click} caption]])