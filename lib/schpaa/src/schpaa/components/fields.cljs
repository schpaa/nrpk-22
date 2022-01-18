(ns schpaa.components.fields
  (:refer-clojure :exclude [time]))

(def field-error-classes [:border-red-400 :border :text-black])

(defn small-field [props]
  (assoc props :class [:w-32]))

(defn full-field [props]
  (assoc props :class [:w-full]))

(defn large-field [props]
  (assoc props :class [:w-64]))

;todo send-server-request
(defn normal-field [props]
  (assoc props :class [:w-40]))

(defn date-field [props]
  (assoc props :class [:w-40]))

(defn time-field [props]
  (assoc props :class [:w-36]))

(defn show-error [errors name]
  [:div.text-xs.text-red-500.mt-1 (get errors name)])

(defn text [{:keys [class touched errors handle-change handle-blur values readonly?]} label name]
  (let [error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     [:label {:for name} label]
     [:input.h-10
      {:class     (concat [:form-input :rounded]
                          (when readonly? [:border-none :bg-gray-200])
                          (when error? field-error-classes))
       :name      name
       :id        name
       :read-only readonly?
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn textarea [{:keys [placeholder touched errors handle-change handle-blur values]} label name]
  (let [error? (and (touched name) (get errors name))]
    [:div.flex-col.flex
     [:label {:for name} label]
     [:textarea
      {:style     {:min-height "2.5em"
                   :max-height "5rem"}
       :class     (concat [:form-input :rounded]
                          (when error? field-error-classes))
       :name      name
       :placeholder placeholder
       :id        name
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn date [{:keys [naked? touched errors handle-change handle-blur values class readonly?]} label name]
  (let [error? (and touched (touched name) (get errors name))]
    (if naked?
      [:input.h-full
       {:class     (concat
                     [:form-input :rounded]
                     class
                     (when readonly? [:border-none :bg-gray-200])
                     (when error? field-error-classes))
        :name      name
        :read-only readonly?
        :id        name
        :type      :date
        :value     (values name)
        :on-change handle-change}]
      [:div.flex-col.flex
       {:class class}
       [:label {:for name} label]
       [:input.h-10.field-w-max
        {:class     (concat
                      [:form-input :rounded]
                      (when readonly? [:border-none :bg-gray-200])
                      (when error? field-error-classes))
         :name      name
         :read-only readonly?
         :id        name
         :type      :date
         :value     (values name)
         :on-change handle-change}]
       (when error?
         (show-error errors name))])))

(defn time [{:keys [disabled? naked? touched errors handle-change handle-blur values class]} label name]
  (let [error? (and touched (touched name) (get errors name))]
    (if naked?
      [:input.h-full.xfield-w-max
       {:class     (concat [:form-input :rounded]
                           class
                           (when error? field-error-classes))
        :name      name
        :disabled disabled?
        :id        name
        :type      :time
        :format    "hh:mm"
        :value     (values name)
        :on-change handle-change
        :on-blur   handle-blur}]
      [:div.flex-col.flex
       {:class class}
       (when label
         [:label {:for name} label])
       [:input.h-10.xfield-w-max
        {:class     (concat [:form-input :rounded]
                            (when error? field-error-classes))
         :name      name
         :id        name
         :type      :time
         :format    "hh:mm"
         :value     (values name)
         :on-change handle-change
         :on-blur   handle-blur}]
       (when error?
         (show-error errors name))])))

(defn checkbox [{:keys [disabled? touched errors handle-change handle-blur values class]} label name]
  [:div.flex.gap-2.items-center.select-none.ml-1
   {:class class}
   [:input
    {:class     (concat [:form-checkbox :rounded :w-5 :h-5])
     :disabled disabled?
     :name      name
     :id        name
     :type      :checkbox
     :checked   (values name)
     :on-change handle-change}]
   [:label.m-0.p-0.text-blackx.text-base.normal-case
    {:class (if disabled? "text-gray-500/50" "text-gray-500")
     :disabled true
     :for name} label]])



(defn radio [{:keys [disabled? touched errors handle-change handle-blur values class]} items name]
  (for [[k v] items]
    [:div.flex.items-center.gap-2 {:class [:ml-1]}
     [:input {:class [:form-radio :w-5 :h-5]
              :name      name
              :id        (str name "-" k)
              :checked   (= k (values name))
              :type      :radio
              :value     k
              :on-change handle-change}]
     [:label.normal-case
      {:class (concat
                [:whitespace-normal :text-black :m-0 :leading-normal :p-0]
                [:pl-1x :text-base :xtracking-wider :xfont-medium])
       :for   (str name "-" k)} v]]))

(defn button [on-click label caption]
  [:div.flex-col.flex
   (if label
     [:label {:for name} label]
     [:div.h-6])
   [:button.btn-narrow.btn-free
    {:class    ["bg-black/10" :text-xs]
     :type     :button
     :on-click on-click} caption]])
