(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [headlessui-reagent.core :as ui]
            [reagent.core :as r]
            [schpaa.style.ornament :as sc]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.debug :as l]
            [clojure.string :as string]))

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

(o/defstyled select' :div
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


(defn select [{:keys [errors values handle-change] :as props} type class label field-name default-text items]
  (let [sorted false]
    [sc/col {:class (into [:gap-1] class)}
     [sc/row {:class [:gap-2]}
      [sc/label label]
      (if (field-name errors)
        [sc/label-error (first (field-name errors))])]
     [select' {:type      type
               :value     (field-name values)
               :on-change handle-change
               :errors    (field-name errors)
               :name      field-name}
      (cons [:option {:style {:display "none"} :disabled 1 :selected 1 :value ""} default-text]
            (for [[idx e] (if sorted (sort-by val items) items)]
              [:option {:value (str idx) :default-value (if (= (str idx) (values name)) (str idx))}
               e]))]]))

(defn common-class [has-errors?]
  [:h-auto
   :p-2
   ;:focus:outline-none
   ;:focus:ring-2
   ;:focus:ring-offset-1
   ;:focus:bg-black
   :outline-none
   :focus:outline-none
   :focus:ring-brand1
   :focus:ring-2
   :focus:ring-offset-1
   :focus:ring-offset-transparent
   :focus:bg-white
   :active:bg-white


   (if has-errors? :xbg-rose-300 :xbg-gray-200)
   ;(if-not has-errors? :focus:bg-white)
   (if has-errors? :focus:ring-rose-300 :focus:ring-currentColor)])


(o/defstyled textarea'' :textarea
  {:border-radius "var(--radius-1)"
   :background    "var(--field2)"
   :color         "var(--text1)"}
  [:&:enabled:focus {:background "var(--field1)"
                     :xoutline   "2px solid var(--brand1)"
                     :color      "var(--text1)"}])

(defn textarea [{:keys [errors values handle-change] :as props} type class label field-name]
  (let [has-errors? (field-name errors)]
    [sc/col {:class (into [:gap-1 :h-auto] class)}
     [sc/row {:class [:gap-1 :items-end]}
      [sc/label label]
      (when errors
        (if (field-name errors)
          [sc/label-error (first (field-name errors))]))]
     [textarea'' {:style     (conj {:min-height "3.6rem"})
                  :class     (common-class has-errors?)
                  :rows      8
                  :value     (field-name values)
                  :on-change handle-change
                  :errors    (field-name errors)
                  :name      field-name}]]))

(o/defstyled input'' :input
  :h-10 :outline-none
  {:border-radius "var(--radius-1)"
   :background    "var(--field2)"
   :color         "var(--text1)"}
  [:&:focus
   {:background "var(--field1)"
    :color      "var(--text1)"
    :soutline   "2px solid var(--brand1)"}])

(defn input [{:keys [errors values handle-change] :as props} type class label field-name]
  (let [has-errors? (field-name errors)]
    [sc/col {:class (concat [:gap-1] class)}
     [sc/row {:class [:gap-1]}
      [sc/label label]
      (when errors
        (if (field-name errors)
          [sc/label-error (first (field-name errors))]))]
     [input'' {:type        type
               :has-errors? has-errors?
               :class       (common-class has-errors?)
               ;:style       (common-style has-errors?)
               :value       (field-name values)
               :on-change   handle-change
               ;:errors    (field-name errors)
               :name        field-name}]]))

;region combo

(def people [{:id 1, :name "Innlegg"}
             {:id 2, :name "HMS hendelse"}
             {:id 3, :name "Skade på materiell"}
             {:id 4, :name "Mangler på materiell"}
             {:id 6, :name "Salg av materiell"}
             {:id 5, :name "Annonsering av kurs"}
             {:id 7, :name "Annonsering av møte"}
             {:id 8 :name "Styremøte"}
             {:id 9 :name "Nøkkelvaktmøte"}
             {:id 10 :name "Sommerskole"}
             {:id 11 :name "Sesongåpning"}])

(def person-by-id (zipmap (map :id people) people))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(o/defstyled combobox'' :div
  {;:height "2.5rem"
   :border-radius "var(--radius-1)"
   :background    "var(--surface0)"}
  [:&:focus {:background :white
             :outline    "2px solid var(--brand1)"}])

(defn combobox-example [{:keys [class on-change value]}]

  (r/with-let [!selected (r/atom (person-by-id value))
               !query (r/atom "")
               !filtered-people (r/reaction
                                  (let [query @!query]
                                    (if (string/blank? query)
                                      people
                                      (filter #(string/includes? (string->query (:name %)) query)
                                              people))))]
    (let [selected @!selected
          filtered-people @!filtered-people]
      [:div
       {:class class}
       [ui/combobox {:value     (:id selected)
                     :on-change #(do
                                   (reset! !selected (get person-by-id %))
                                   (on-change %))
                     :class     [#_:focus:ring-currentColor
                                 ;:focus:ring-2
                                 :outline-none
                                 #_:focus:outline-none
                                 #_:focus:ring-offset-1]}
        [:div.relative.mt-1.h-12
         [:div.relative.mt-1.w-full.text-left.cursor-default.overflow-hiddenx.h-full.outline-none
          {:class []}

          [ui/combobox-input {
                              :class         (into class
                                                   ["border-none pl-3 pr-10 h-10"
                                                    :outline-none
                                                    :focus:outline-none
                                                    :focus:ring-brand1
                                                    :focus:ring-2
                                                    :focus:ring-offset-1
                                                    :focus:ring-offset-transparent
                                                    ;:focus:bg-white
                                                    ;:active:bg-white
                                                    :bg-unfocused-field])
                              :style         {:border-radius "var(--radius-1)"
                                              :color         "var(--text1)"
                                              :background    "var(--field2)"}

                              :display-value (fn [id] (:name (get person-by-id id)))
                              :on-change     #(reset! !query (string->query (.-value (.-target %))))}]
          [ui/combobox-button {:style {:color "var(--text2)"}
                               :class [:focus:outline-none
                                       :outline-none
                                       "absolute inset-y-0 right-0 flex items-center pr-2 h-10 "]}
           [sc/icon-small {:aria-hidden true} [:> solid/ChevronDownIcon]]]]

         [ui/transition
          {;:show true
           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options {;:static true
                                :style {:border-radius "var(--radius-1)"
                                        :color         "var(--brand1)"
                                        :box-shadow    "var(--shadow-2)"
                                        :background    "var(--field1)"}

                                :class (into class ["absolute py-1 mt-2 overflow-auto max-h-48 "])}
           [:<>
            (for [person filtered-people]
              ^{:key (:id person)}
              [ui/combobox-option
               {:value (:id person)
                :class (fn [{:keys [active]}]
                         (concat [:cursor-default :select-none :relative :py-2 :pl-10 :pr-4]
                                 (if active
                                   [:bg-focused-field]
                                   [:bg-unfocused-field])))}
               (fn [{:keys [selected active]}]
                 [:<>
                  [:span.block.truncate {:class (if selected :font-medium :font-normal)}
                   (:name person)]
                  (when selected
                    [:span.absolute.inset-y-0.left-0.flex.items-center.pl-3
                     {:style (if active {:color "var(--text1)"} {:color "var(--text3)"})}
                     [sc/icon-small {:aria-hidden "true"} [:> solid/CheckIcon]]])])])]]]]]])))
