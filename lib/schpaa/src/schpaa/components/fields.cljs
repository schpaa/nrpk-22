(ns schpaa.components.fields
  (:refer-clojure :exclude [time])
  (:require [schpaa.style :as st]))

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
  (assoc props :class [:w-44]))

(defn time-field [props]
  (assoc props :class [:w-28]))

(defn show-error [errors name]
  (let [e (get errors name)]
    [:div.text-xs.text-red-500.mt-1
     ;[:div "?"]
     (for [e' e]
       [:p (str e' #_(if (< 1 (count e)) (str " [" (dec (count e)) "]")))])]))

(defn save-ref [props ref]
  (assoc props
    :ref (fn [el]
           (if (nil? @ref)
             (do (reset! ref el)
                 (tap> ["SETTING REF " @ref]))
             (do
               (tap> ["SETTING REF NOT NEEDED" @ref]))))))

(defn placeholder [props text]
  (assoc props :placeholder text))

;;;

(defn label-colors [readonly?]
  (let [{:keys [bg fg fg- fg+ hd p p- he]} (st/fbg' 3)]
    (if readonly?
      fg-
      fg+
      #_#_[:dark:text-gray-600]
          [:dark:text-gray-600])))

(def field-error-classes [:border-red-400 :border-2 :text-black :focus:border-transparent])

(defn field-colors [readonly?]
  (let [{:keys [bg fg fg- fg+ hd p p- he]} (st/fbg' 3)]
    (if readonly?
      (concat bg fg-
              [:border-2
               :border-gray-200
               :focus:border-transparent
               :focus:outline-none :focus:ring-0
               ;"dark:border-gray-700/50"
               ;:bg-gray-200
               ;:dark:text-gray-300
               #_"dark:bg-gray-700/50"])
      (concat bg []
              ["dark:placeholder-gray-300"
               :focus:outline-none
               :focus:ring-0
               :rounded-sm
               :border-2
               :border-gray-300
               :focus:border-transparent
               :bg-white
               :text-black
               :dark:bg-gray-700
               :dark:focus:bg-gray-600
               :dark:text-gray-100]))))


(defn text [{:keys [ref class touched errors handle-change handle-blur values readonly?]} & {:keys [label name]}]
  (let [error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     [:label {:class (label-colors readonly?)
              :for name} label]
     [:input.h-10
      {:class     (concat [:form-input :rounded]
                          (field-colors readonly?)
                          (when error? field-error-classes))
       :name      name
       :ref ref
       :id        name
       :read-only readonly?
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn textarea [{:keys [auto-focus readonly? ref placeholder touched errors handle-change handle-blur values]} & {:keys [label name error-type]}]
  (let [error? (and (touched name) (get errors name))]
    [:div.flex-col.flex
     [:label {:class (label-colors readonly?)
              :for name} label]
     [:textarea
      {:style     {:min-height "2.5em"
                   :max-height "15rem"}
       :class     (concat [:form-textarea :rounded]
                          (field-colors readonly?)
                          (when error? field-error-classes))
       :name      name
       :ref ref
       :auto-focus auto-focus
       :placeholder placeholder
       :id        name
       :type      :text
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))


(defn error-marker []
  [:div.absolute.text-white.-top-2.-right-2
   [:div.flex.items-center.justify-center.h-5.bg-red-500.rounded-full.aspect-square
    [:div "!"]]])

(defn date [{:keys [naked? touched errors handle-change handle-blur values class readonly?]} &
            {:keys [label name error-type]}]
  (let [marker? (= :marker error-type)
        inline? (= :inline error-type)
        error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex.relative
     {:class class}
     (when (and marker? error?)
       (error-marker))
     (when-not naked?
       [:label {:class (label-colors readonly?)
                :for name} label])
     [:input.h-full
      {:class     (concat
                    [:form-input]
                    [:focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400]
                    (field-colors readonly?)
                    (when error? field-error-classes))
       :name      name
       :read-only readonly?
       :id        name
       :type      :date
       :value     (values name)
       :on-change handle-change}]
     (when inline?
       (when error?
         (show-error errors name)))]))


(defn time [{:keys [disabled? naked? readonly? touched errors handle-change handle-blur values class]}
            & {:keys [label name error-type]}]
  (let [error? (and touched (touched name) (get errors name))
        marker? (= :marker error-type)]
    [:div.flex-col.flex.relative
     {:class class}
     (when-not naked?
      (when label
        [:label {:class (label-colors readonly?)
                 :for   name} label]))
     (when (and marker? error?)
       (error-marker))
     [:input.h-full
      {:class     (concat [:form-input]
                          [:focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400]
                          (field-colors readonly?)
                          (when error? field-error-classes))
       :name      name
       :id        name
       :type      :time
       :format    "hh:mm"
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when (= :inline error-type)
       (when error?
         (show-error errors name)))]))

(defn checkbox [{:keys [readonly? disabled? touched errors handle-change handle-blur values class]} label name]
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



(defn radio [{:keys [readonly? disabled? touched errors handle-change handle-blur values class]} items name]
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
