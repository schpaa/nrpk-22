(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]))

(o/defstyled checkbox :div
  [:input :p-2 :w-full :rounded :h-5 :w-5
   {:background "var(--surface0)"}
   [:&:focus :transition :duration-200
    {:background "var(--surface000)"}]]
  [:input.validation-error :outline-none
   {:background "var(--red-1)"}
   [:&:focus :transition :duration-200
    {:background "var(--surface000)"}]]
  [:div.validation-error
   :rounded
   {:outline "solid var(--red-3) 2px"}]
  ([attr ch]
   (let [errors? (some-> attr :errors (:name attr))]
     [sc/row {:class [(when errors? :validation-error)]}
      [:input.flex.items-center.shrink-0
       (conj attr {:class (when errors? [:validation-error])})]
      ch])))

(o/defstyled date :div
  [:input :p-2 :w-full :rounded :h-10
   {:background "var(--surface0)"
    :border     "none"
    :box-shadow "var(--inner-shadow-2)"}
   [:&:focus :transition :duration-200
    {:background "var(--surface00)"}]]
  [:input.validation-error :outline-none
   {:background "var(--red-1)"}
   [:&:focus :transition :duration-200
    {:background "var(--surface000)"}]]
  [:div.validation-error
   :rounded
   {:outline "solid var(--red-3) 2px"}]
  ([attr]
   (let [errors? (some-> attr :errors (:name attr))]
     [:div
      {:class (when errors? [:validation-error])}
      [:input.form-input (conj attr
                               {:class (when errors? [:validation-error])})]])))

(o/defstyled select :div
  [:select :p-2 :w-full :rounded :h-10
   {:background "var(--surface0)"
    :border     "none"}
   [:&:focus :transition :duration-200
    {:background "var(--surface00)"}]]
  [:select.validation-error :outline-none
   {:background "var(--red-1)"}
   [:&:focus :transition :duration-200
    {:background "var(--surface000)"}]]
  [:div.validation-error
   :rounded
   {:outline "solid var(--red-3) 2px"}]
  ([attr ch]
   (let [errors? (some-> attr :errors (:name attr))]
     [:div
      {:class (when errors? [:validation-error])}
      [:select.form-select (conj attr
                                 {:class (when errors? [:validation-error])})
       ch]])))

(defn common-class [has-errors?]
  [:h-auto
   :p-2
   :focus:outline-none
   :focus:ring-2
   :focus:ring-offset-1
   (if has-errors? :focus:bg-white)
   (if has-errors? :bg-rose-300 :bg-gray-200)
   (if-not has-errors? :focus:bg-white)
   (if has-errors? :focus:ring-rose-300 :focus:ring-blue-500)])

(defn common-style [has-errors?]
  (conj
    {:xbackground   (if has-errors? "var(--red-2)" "var(--surface0)")

     :border-radius "var(--radius-1)"}
    #_(when has-errors?
        {:background "var(--red-2)"})))

#_(o/defstyled textarea' :textarea
    [:&
     #_[:&:focus
        :outline-none
        :ring-2
        :border-blue-500
        :ring-red-500]


     #_{;:background    "var(--surface0)"
        :padding       "var(--size-2)"
        :border-radius "var(--radius-1)"
        ;:border        "none"
        :box-shadow    "var(--inner-shadow-2)"}
     [:&:focus
      :transition :duration-200
      {:background "var(--surface00)"}]]
    #_([{:keys [rows value]} ch]
       ^{:rows  rows
         :value value}
       [:div {:class [:h-auto
                      :outline-none
                      :ring-2
                      :border-blue-500
                      :ring-offset-2
                      :ring-red-500]} ch]))

(defn textarea [{:keys [errors values handle-change] :as props} type class label field-name]
  (let [has-errors? (field-name errors)]
    [sc/col {:class (into [:gap-1 :h-auto] class)}
     [sc/row {:class [:gap-1 :items-end]}
      [sc/label label]
      (when errors
        (if (field-name errors)
          [sc/label-error (first (field-name errors))]))]
     [:textarea {:style     (conj {:min-height "3.6rem"
                                   :max-height "8rem"}
                                  (common-style has-errors?))
                 :class     (common-class has-errors?)
                 :rows      4
                 :value     (field-name values)
                 :on-change handle-change
                 :errors    (field-name errors)
                 :name      field-name}]]))

#_(o/defstyled input' :div
    [:input
     {:padding    "var(--size-2)"
      :background "var(--surface0)"}
     [:&:focus {:dbackground "var(--surface000)"}]
     [:.dummy {:background "blue"}
      [:&:focus {:xbackground "var(--surface0)"}]]]
    ([attr]
     [:<>
      ;[l/ppre attr]
      [:input.w-full (merge-with merge attr {:class [(if true #_(:has-errors? attr) :dummy)]})]]))

(defn input [{:keys [errors values handle-change] :as props} type class label field-name]
  (let [has-errors? (field-name errors)]
    [sc/col {:class (concat [:gap-1] class)}
     [sc/row {:class [:gap-1]}
      [sc/label label]
      (when errors
        (if (field-name errors)
          [sc/label-error (first (field-name errors))]))]
     [:input {:type        type
              :has-errors? has-errors?
              :class       (common-class has-errors?)
              :style       (common-style has-errors?)
              :value       (field-name values)
              :on-change   handle-change
              ;:errors    (field-name errors)
              :name        field-name}]]))