(ns schpaa.components.fields
  (:refer-clojure :exclude [time])
  (:require [schpaa.style :as st]))

(defn number-field [props]
  (assoc props :class [:w-20]))

(defn small-field [props]
  (assoc props :class [:w-32]))

(defn full-field [props]
  (assoc props :class [:w-full]))

(defn regular-field [props]
  (assoc props :class [:w-48]))

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
  (let [{:keys [fg- fg p]} (st/fbg' :field-label)]
    (concat (if readonly? fg- fg) p [:ml-2])))

(defn checkbox-colors [readonly?]
  (let [{:keys [fg- fg p]} {:fg [:text-info-700 :dark:text-info-300]}]
    (concat (if readonly? fg- fg) p [:ml-2])))

(def field-error-classes [:border-red-400 :border-2 :text-black :focus:border-transparent])

(defn field-colors [readonly?]
  (let [{:keys [bg+ bg bg- fg br br- fg- fg+ hd p p- he]} (st/fbg' :field)]
    (if readonly?
      (concat fg- bg- br-
              [;:border-2
               :shadow-inset
               :outline-none :border-none
               :focus:border-transparent
               :focus:outline-none :focus:ring-0])
      (concat bg fg []
              [;"dark:placeholder-gray-300"
               :outline-none
               :focus:outline-none
               :focus:ring-2
               :focus:ring-rounded-sm
               :focus:ring-offset-0
               :focus:ring-alt
               :rounded
               :shadow-sm
               :border-transparent
               ;:border-gray-500
               :focus:border-transparent
               #_:focus:border-transparent]))))
;:dark:bg-gray-700
;:dark:focus:bg-gray-600
;:dark:text-gray-100]))))


(defn text [{:keys [auto-focus placeholder naked? ref class touched errors handle-change handle-blur values readonly?]} & {:keys [label name]}]
  (let [error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     (when-not naked?
       [:label {:class (label-colors readonly?)
                :for   name} label])
     [:input.h-10x
      {:class       (concat [:form-input :rounded]
                            (when-not naked? (field-colors readonly?))
                            class
                            (when error? field-error-classes))
       :name        name
       :placeholder placeholder
       :auto-focus  auto-focus
       :ref         ref
       :id          name
       :read-only   readonly?
       :type        :text
       :value       (values name)
       :on-change   handle-change
       :on-blur     handle-blur}]
     (when error?
       (show-error errors name))]))

(defn number [{:keys [naked? ref class touched errors handle-change handle-blur values readonly?]} & {:keys [label name]}]
  (let [error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex
     {:class class}
     (when-not naked?
       [:label {:class (label-colors readonly?)
                :for   name} label])
     [:input.h-10
      {:class     (concat [:form-input :rounded]
                          (when-not naked? (field-colors readonly?))
                          class
                          (when error? field-error-classes))
       :name      name
       :ref       ref
       :id        name
       :read-only readonly?
       :type      :number
       :value     (values name)
       :on-change handle-change
       :on-blur   handle-blur}]
     (when error?
       (show-error errors name))]))

(defn error-marker []
  [:div.absolute.text-white.-top-0.-right-0
   [:div.flex.items-center.justify-center.h-4.bg-red-500.rounded-full.aspect-square
    [:div "!"]]])

(defn textarea [{:keys [naked? class auto-focus readonly? ref placeholder touched errors handle-change handle-blur values]}
                & {:keys [label name error-type] :or {error-type :marker}}]
  (let [marker? (= :marker error-type)
        inline? (= :inline error-type)
        error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex.relative
     (when (and marker? error?)
       (error-marker))
     (when-not naked?
       [:label {:class (label-colors readonly?)
                :for   name} label])
     [:textarea
      {:style       {:min-height "10rem"
                     :max-height "30rem"}
       :class       (concat [:form-textarea :rounded]
                            class
                            (field-colors readonly?)
                            (when error? field-error-classes))
       :name        name
       :ref         ref
       :auto-focus  auto-focus
       :placeholder placeholder
       :id          name
       :type        :text
       :value       (values name)
       :on-change   handle-change
       :on-blur     handle-blur}]
     (when (and inline? error?)
       (show-error errors name))]))

(defn date [{:keys [naked? touched errors handle-change handle-blur values class readonly?]} &
            {:keys [label name error-type] :or {error-type :marker}}]
  (let [marker? (= :marker error-type)
        inline? (= :inline error-type)
        error? (and touched (touched name) (get errors name))]
    [:div.flex-col.flex.relative
     {:class class}
     #_(when (and marker? error?)
         (error-marker))
     (when-not naked?
       [:label {:class (label-colors readonly?)
                :for   name} label])
     [:input.h-full
      {:class     (concat
                    [:form-input]
                    ;[:focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400]
                    (field-colors readonly?)
                    (when error? field-error-classes))
       :name      name
       :read-only readonly?
       :id        name
       :type      :date
       :value     (values name)
       :on-change handle-change}]
     #_(when inline?
         (when error?
           (show-error errors name)))]))

(defn time [{:keys [disabled? naked? readonly? touched errors handle-change handle-blur values class]}
            & {:keys [label name error-type] :or {error-type :marker}}]
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
      {:class     (concat [:form-input :outline-none :ring-none]
                          ;[:focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400]
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

(defn checkbox [{:keys [readonly? disabled? touched errors handle-change handle-blur values class]}
                & {:keys [label name]}]
  [:div.flex.gap-2.items-center.select-none.ml-1
   {:class class}
   [:input
    {:class     (concat [:form-checkbox :rounded-sm :outline-none :w-5 :h-5]
                        (field-colors readonly?))
     :disabled  disabled?
     :name      name
     :id        name
     :type      :checkbox
     :checked   (values name)
     :on-change handle-change}]
   [:label.p-0.text-base.normal-case
    {:class    (concat
                 (checkbox-colors readonly?)
                 #_[(if disabled? "text-gray-500/50" "text-info-500")])
     :disabled true
     :for      name} [:div label]]])

(defn select [{:keys [error? readonly? naked? disabled? touched errors handle-change handle-blur values class]}
              & {:keys [items name label sorted default-text error-type] :or {error-type :marker}}]
  (let [marker? (= :marker error-type)
        inline? (= :inline error-type)
        error? (and touched (touched name) (get errors name))]
    [:div.flex.flex-col.space-between.relative
     ;{:class label-focused}
     (when-not naked?
       (when label
         [:label {:class (label-colors readonly?)
                  :for   name} label]))
     (when (and marker? error?)
       (error-marker))
     [:select {:class     (concat [:form-select :border-nones]
                                  class
                                  (field-colors readonly?)
                                  (when error? field-error-classes))
               :disabled  disabled?
               :name      name
               :id        name
               ;:type      :text
               :value     (values name "")
               :on-change handle-change}
      (cons [:option {:style {:display "none"} :disabled 1 :selected 1 :value ""} default-text]
            (for [[idx e] (if sorted (sort-by val items) items)]
              [:option {:value (str idx) :default-value (if (= (str idx) (values name)) (str idx))}
               e]))]]))

(defn radio [{:keys [readonly? disabled? touched errors handle-change handle-blur values class]} & {:keys [items name]}]
  (into [:div] (for [[k v] items]
                 [:div.flex.items-center.gap-2 {:class [:ml-1]}
                  [:input {:class     [:form-radio :w-5 :h-5]
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
                    :for   (str name "-" k)} v]])))

(defn button [on-click label caption]
  [:div.flex-col.flex
   (if label
     [:label {:for name} label]
     [:div.h-6])
   [:button.btn-narrow.btn-free
    {:class    ["bg-black/10" :text-xs]
     :type     :button
     :on-click on-click} caption]])

