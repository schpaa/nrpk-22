(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [headlessui-reagent.core :as ui]
            [reagent.core :as r]
            [schpaa.style.ornament :as sc]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.debug :as l]
            [clojure.string :as string]
            [schpaa.style.button2]))

(o/defstyled checkbox' :input.form-checkbox
  [:& schpaa.style.button2/focus-button
   {:color      "var(--brand1)"
    :accent-color "red"
    :background "var(--floating)"}])

(o/defstyled labbel :label
  [:&:disabled {:color "red"}])

(defn checkbox [{:keys [errors values handle-change disabled] :as props} class label field-name]
  [:div.pt-4
   [sc/row-sc-g2
    [checkbox'
     {:type      :checkbox
      :style     {;:background    "var(--surface0)"
                  :accent-color  "red" ;"var(--brand1)"

                  :border-radius "var(--radius-1)"
                  #_#_:border "2px solid red"}
      :class     [:w-5 :h-5]
      :id        field-name
      :disabled  disabled
      :checked   (field-name values)
      :on-change handle-change
      :errors    (field-name errors)
      :name      field-name}]
    [labbel                                                 ;.p-0x.text-basex.xnormal-case
     {:style {;:font-weight 900
              :font-family "Inter"
              ;:opacity 0.5
              ;:color "Red"
              ;:background "black"
              :color       (if disabled "var(--text3)" "var(--text1)")
              :font-size   "var(--font-size-1)"}
      :class class
      :for   field-name} label]]])

#_(o/defstyled date :div
    [:input :p-2 :w-full :rounded :h-10
     {
      :background "var(--field)"
      :color      "var(--fieldcopy)"
      :border     "none"}
     [:&:focus :transition :duration-200
      {:background "var(--field-focus)"}]]
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
        [:input.form-inputx (conj attr
                                  {:class (when errors? [:validation-error])})]])))

(o/defstyled select' :select
  [:& :p-2 :w-full :h-10
   schpaa.style.button2/focus-button
   {:background-color "var(--field)"
    :color            "var(--text1)"
    :border-radius    "var(--radius-1)"
    :border           "none"}
   [:&:focus
    {:background "var(--field2)"}]
   [:&.on-bright
    {:background-color "var(--content)"}]]
  #_[:select.validation-error :outline-none
     {:background "var(--red-1)"}
     [:&:focus :transition :duration-200
      {:background "var(--field1)"
       :color      "var(--text1)"}]]
  [:div.validation-error
   :rounded
   {:outline "solid var(--red-3) 2px"}]
  #_([attr ch]
     (let [errors? (some-> attr :errors (:name attr))]
       [:div
        {:class (when errors? [:validation-error])}
        [:select.form-select (conj attr
                                   {:class (when errors? [:validation-error])})
         ch]])))

(defn select [{:keys [errors values handle-change] :as props} type class label field-name default-text items]
  (let [sorted false]
    [sc/label-field-col
     [sc/row {:class [:gap-2]}
      [sc/label label]
      (if (field-name errors)
        [sc/label-error (first (field-name errors))])]
     [select' {:type      type
               :class class
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

   :w-full
   :p-2
   ;:focus:outline-none
   ;:focus:ring-2
   ;:focus:ring-offset-1
   ;:focus:bg-black
   ;:outline-none
   :focus:outline-none
   :focus:ring-brand1
   :focus:ring-2
   :focus:ring-offset-1
   :focus:ring-offset-transparent
   :focus:bg-white
   :bg-white
   :active:bg-white

   (if has-errors? :xbg-rose-300 :xbg-gray-200)
   ;(if-not has-errors? :focus:bg-white)
   (if has-errors? :focus:ring-rose-300 :focus:ring-currentColor)])

(o/defstyled textarea'' :textarea  
  [:& schpaa.style.button2/focus-button
   {:border-radius "var(--radius-1)"
    :background    "var(--field)"
    :color         "var(--text1)"}
   [:&:focus {:background-color "var(--field-focus)"
              :color            "var(--fieldcopy)"}]])

(defn textarea [{:keys [errors values handle-change]} type attr label field-name]
  (let [has-errors? (field-name errors)]
    [sc/label-field-col {:class (conj (:class attr))}
     ;sc/col {:class (into [:gap-1 :h-auto :w-full :justify-between])}
     [sc/row {:class [:gap-1 :items-end :justify-between]}
      [sc/label label]
      (when errors
        (if (field-name errors)
          [sc/label-error (first (field-name errors))]))]
     [textarea'' (merge-with merge
                             {:style     {:min-height "5rem"}
                              :class     (common-class has-errors?)
                              :value     (get values field-name)
                              :on-change handle-change
                              :errors    (field-name errors)
                              :name      field-name}
                             attr)]]))

(o/defstyled input'' :input
  [:& :h-10 :w-full :p-2
   schpaa.style.button2/focus-button
   {:border-radius "var(--radius-1)"
    :background    "var(--toolbar))"
    :color         "var(--text2)"}
   [:&:focus
    {:background "var(--field-focus)"
     :color      "var(--fieldcopy)"}]
   [:&.on-bright
    {:background-color "var(--content)"}]
   [:&.on-error
    {:background-color "var(--red-2)"}]])

(defn input [{:keys [cursor
                     handle-blur
                     touched on-key-down errors values handle-change placeholder] :as props}
             type attr label fieldname]
  (let [fieldname (if fieldname fieldname #())
        has-errors? (and (touched fieldname)
                         (get errors fieldname))]
    [sc/col
     {:class (:class attr)}
     ;[l/pre fieldname (touched fieldname)]
     (when label
       [sc/row {:class [:gap-x-1]
                :style {:align-items :start}}
        [sc/label label]
        (when (and (touched fieldname) errors)
          (when (get errors fieldname)
            [sc/label-error (first (fieldname errors))]))])

     [input'' (conj {:type (or type :text)}
                    {:has-errors?  (and has-errors?)
                     :autoComplete "off"
                     :placeholder  placeholder
                     :autoFocus    (true? (:autofocus props))
                     :class        (concat (common-class has-errors?)
                                           (:class attr)
                                           (when has-errors? [:on-error]))
                     :style        (:style attr)
                     :value        (if cursor @cursor
                                              (fieldname values))
                     :on-blur      handle-blur
                     :on-change    #(if cursor
                                      (reset! cursor (.. % -target -value))
                                      (handle-change %))
                     :on-key-down  on-key-down
                     :name         fieldname}
                    props)]]))

;region combo

(def people (map-indexed (fn [idx e] {:id idx :name e}) ["Styremøte"
                                                         "Nøkkelvaktmøte"
                                                         "Dugnad"
                                                         "Sesongåpning"
                                                         "Sesongavslutning"
                                                         "Åpen dag"
                                                         "Innmeldingskurs"
                                                         "Våttkortkurs"
                                                         "Livredningskurs"
                                                         "Innlegg"
                                                         "HMS hendelse"
                                                         "Skade på materiell"
                                                         "Mangler på materiell"
                                                         "Salg av materiell"
                                                         "Annonsering av kurs"
                                                         "Åpent møte"
                                                         "Sommerskole"
                                                         "Administrasjon"
                                                         "Årsmøte"
                                                         "Annet"
                                                         "Frist"]))

(def person-by-id (zipmap (map :id people) people))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(o/defstyled combobox'' :div
  [:& schpaa.style.button2/focus-button
   {:border-radius "var(--radius-1)"
    :background-color    "var(--content)"}
   [:&:focus {:background-color "var(--floating)"}]
   #_[:&.on-bright {:background "var(--content)"}]])

(defn general-combobox [{:keys [class on-change value person-by-id people]}]
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
                     :class     [:outline-none]}
        [:div.relative.mt-1.h-12
         [:div.relative.mt-1.w-full.text-left.cursor-default.h-full.outline-none
          [combobox''
           [ui/combobox-input {:class         [:pl-3 :pr-7 :h-10
                                               (some-> (schpaa.style.button2/focus-button) last :class first)]
                               :style         {:border-radius "var(--radius-1)"
                                               :color         "var(--fieldcopy)"
                                               :background    :unset}
                               :display-value (fn [id] (:name (get person-by-id id)))
                               :on-change     #(reset! !query (string->query (.-value (.-target %))))}]]
          [ui/combobox-button {:style {:color "var(--text2)"}
                               :class [:focus:outline-none
                                       :outline-none
                                       "absolute inset-y-0 right-0 flex items-center pr-2 "]}
           [sc/icon-small {:aria-hidden true} [:> solid/ChevronDownIcon]]]]

         [ui/transition
          {;:show true
           ;:entered "bg-alt"
           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options
           {;:static true
            :style {:z-index       10
                    :border-radius "var(--radius-1)"

                    :box-shadow    "var(--shadow-6)"}
            :class (into class ["absolute py-1 mt-2 overflow-auto max-h-48 "])}
           [:<>
            (for [person filtered-people]
              ^{:key (:id person)}
              [ui/combobox-option
               {:value (:id person)
                :class (fn [{:keys [active]}]
                         (concat [:cursor-default :select-none :relative :py-2 :pl-10 :pr-4]
                                 ;todo !!
                                 (if active
                                   [:bg-focused-field]    
                                   [:bg-unfocused-field])))}
               (fn [{:keys [selected active]}]
                 [:<>
                  [:span.block.truncate {:class (if selected [:bg-black] [])}
                   (:name person)]
                  (when selected
                    [:span.absolute.inset-y-0.left-0.flex.items-center.pl-1
                     {:style (if active {:color "var(--text1)"} {:color "var(--text1)"})}
                     [sc/icon-small {:aria-hidden "true"} [:> solid/CheckIcon]]])])])]]]]]])))

(defn combobox-example [attr {:keys [class on-change value]}]
  (r/with-let [!selected (r/atom (person-by-id value))
               !query (r/atom "")
               !filtered-people (r/reaction
                                  (let [query @!query]
                                    (sort-by last (if (string/blank? query)
                                                    people
                                                    (filter #(string/includes? (string->query (:name %)) query)
                                                            people)))))]
    (let [selected @!selected
          filtered-people @!filtered-people]
      [:div
       #_{;:style {:background "red"}
          :xclass class}
       [ui/combobox {:value     (:id selected)
                     :on-change #(do
                                   (reset! !selected (get person-by-id %))
                                   (on-change %))
                     :class     [:outline-none]}
        [:div.relative
         [:div.relative.w-full.text-left.cursor-default.h-full.outline-none
          [combobox''
           {:xclass class
            :xstyle {:background-color "red"}}
           [ui/combobox-input {:class         (concat (:class attr)
                                                      [:pl-3
                                                       :w-full
                                                       ;:outline-none
                                                       (some-> (schpaa.style.button2/focus-button) last :class first)])
                               :style         {:border-radius "var(--radius-1)"
                                               :color         "var(--fieldcopy)"
                                               :height "2.45rem"
                                               :background-color    "unset"}
                               :display-value (fn [id] (:name (get person-by-id id)))
                               :on-change     #(reset! !query (string->query (.-value (.-target %))))}]]
          [ui/combobox-button {:style {:color "var(--text2)"}
                               :class [:focus:outline-none
                                       :outline-none
                                       "absolute inset-y-0 right-0 flex items-center pr-2 h-10"]}
           [sc/icon-small {:aria-hidden true} [:> solid/ChevronDownIcon]]]]

         [ui/transition
          {;:show true
           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options {;:static true
                                :style {:border-radius "var(--radius-1)"
                                        :color         "var(--text1)"
                                        :z-index 10
                                        :box-shadow    "var(--shadow-3)"
                                        :background    "var(--field2)"}

                                :class (into class [:w-56x
                                                    "absolute py-1 mt-2 overflow-auto max-h-48 "])}
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

(defn combobox [{:keys [errors value set-values handle-change] :as props} type class label field-name]
  [sc/label-field-col
   [sc/row {:class [:gap-1 :items-end :justify-start]}
    [sc/label label]
    (when errors
      (if (field-name errors)
        [sc/label-error (first (field-name errors))]))]
   [general-combobox (conj props
                           {:on-change handle-change
                            :class     class})]])