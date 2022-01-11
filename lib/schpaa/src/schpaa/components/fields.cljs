(ns schpaa.components.fields
  (:refer-clojure :exclude [time]))

(def field-error-classes [:border-red-400 :border :text-black])

(defn small-field [props]
  (assoc props :class [:w-28]))

(defn full-field [props]
  (assoc props :class [:w-full]))

;todo send-server-request
(defn normal-field [props]
  (assoc props :class [:w-40]))

(defn show-error [errors name]
  [:div.text-xs.text-red-500.mt-1 (get errors name)])

(defn text [{:keys [touched errors handle-change handle-blur values]} label name]
  (let [error? (and (touched name) (get errors name))]
    [:div.flex-col.flex
     [:label {:for name} label]
     [:input.h-10
      {:class     (concat [:form-input   :rounded]
                          (when error? field-error-classes))
       :name      name
       :id        name
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn textarea [{:keys [touched errors handle-change handle-blur values]} label name]
  (let [error? (and (touched name) (get errors name))]
    [:div.flex-col.flex
     [:label {:for name} label]
     [:textarea
      {:style {:min-height "2.5em"
               :max-height "5rem"}
       :class     (concat [:form-input   :rounded]
                          (when error? field-error-classes))
       :name      name
       :id        name
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn date [{:keys [touched errors handle-change handle-blur values class]} label name]
  (let [error? (and (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     [:label {:for name} label]
     [:input.h-10.field-w-max
      {:class     (concat
                    [:form-input   :rounded]
                    (when error? field-error-classes))
       :name      name
       :id        name
       :type      :date
       :value     (values name)
       :on-change handle-change}]
     (when error?
       (show-error errors name))]))

(defn time [{:keys [touched errors handle-change handle-blur values class]} label name]
  (let [error? (and  (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     [:label {:for name} label]
     [:input.h-10.field-w-max
      {:class     (concat [:form-input   :rounded]
                          (when error? field-error-classes))
       :name      name
       :id        name
       :type      :time
       :format    "hh:mm"
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn checkbox [{:keys [touched errors handle-change handle-blur values class]} label name]
  [:div.flex.gap-2.items-center.select-none.ml-1
   {:class class}
   [:input.h-5.w-5
    {:class     (concat [:form-checkbox   :rounded])
     :name      name
     :id        name
     :type      :checkbox
     :format    "hh:mm"
     :checked     (values name)
     :on-change handle-change
     :on-blur   handle-blur}]
   [:label.m-0.p-0.text-black.text-base.normal-case {:for name} label]])

(defn button [on-click label caption]
  [:div.flex-col.flex
   (if label
     [:label {:for name} label]
     [:div.h-6])
   [:button.btn-narrow.btn-free
    {:class    ["bg-black/10" :text-xs]
     :type     :button
     :on-click on-click} caption]])
