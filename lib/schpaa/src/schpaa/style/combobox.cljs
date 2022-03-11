(ns schpaa.style.combobox
  (:require [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [db.core :as db]
            [clojure.string :as string]
            [schpaa.debug :as l]
            [schpaa.style.ornament :as sc]
            [re-frame.core :as rf]))

(def commands
  [{:id "0", :name "Registrer skade på utstyr" :icon [:> outline/PencilIcon]}
   {:id "1", :name "Registrer hms-hendelse" :icon [:> outline/PencilIcon]}
   {:id "2", :name "Avlys booking"}
   {:id "3", :name "Kok kaffe" :disabled true}
   {:id "4", :name "Avlys kurs" :disabled true :icon [:> outline/LockClosedIcon]}
   {:id "5", :name "Reserver båter for kurs" :disabled true :icon [:> outline/ArrowRightIcon]}
   {:id "6", :name "Gå til båtlisten på Nøklevann"}
   {:id "7", :name "Gå til båtlisten på Sjøbasen"}
   {:id "8", :name "Endre mine opplysninger" :icon [:> outline/UserCircleIcon] :action #(rf/dispatch [:app/navigate-to [:r.user]])}
   {:id     "9",
    :name   "Skriv nytt innlegg"
    :icon   [:> outline/PencilIcon]
    :action #(rf/dispatch [:lab/new-blog-entry])}
   {:id "10", :name "Book båt" :icon [:> outline/TicketIcon]}
   {:id "11", :name "Lage nytt båtnummer" :disabled true}
   {:id "12", :name "Gå til designspråk/mal" :icon [:> outline/ColorSwatchIcon] :action #(rf/dispatch [:app/navigate-to [:r.designlanguage]])}
   {:id "13", :name "Gå til forsiden" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.forsiden]])}
   {:id "14", :name "Gå til betingelser" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.terms]])}
   {:id "15", :name "Gå til vilkår" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.conditions]])}
   {:id "16", :name "Gå til retningslinjer" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.retningslinjer]])}
   {:id "17", :name "Vaktkalender" :icon [:> outline/CalendarIcon] :action #(rf/dispatch [:app/navigate-to [:r.calendar]])}])

(def command-by-id (zipmap (map :id commands) commands))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(defn listitem [{:keys [notfound selected action active disabled icon] :as attr} value]
  [:div.gap-3.select-none.truncate.w-full
   {:class [:snap-start]
    :style {:display         :flex
            :color           (if disabled "var(--surface1)" "var(--surface5)")
            :align-items     :center
            :justify-content :start
            :border-left     (str "4px solid " (if active "var(--brand1)" "transparent"))
            :height          :3rem
            :padding-inline  "var(--size-3)"
            :font-family     "Montserrat"
            :font-weight     500
            :background      (if active "var(--surface000)" "var(--surface00)")}}
   [sc/icon-small {:class [:shrink-0]} (when-not (or disabled) icon)]
   [:div.truncate value]])

(o/defstyled placeholder :div
  [:input.w-full
   {:font-family "Montserrat"
    :font-weight 500
    :font-size   "var(--font-size-2)"
    :color       "var(--surface5)"}
   ["&::placeholder"
    {:font-size "var(--font-size-2)"
     :color     "var(--surface1)"}]])

(defn combobox-example [context]
  (r/with-let [!query (r/atom "")
               !filtered-people (r/reaction
                                  (let [query @!query]
                                    (sort-by :name (if (string/blank? query)
                                                     commands
                                                     (filter #(string/includes? (string->query (:name %)) query)
                                                             commands)))))
               !selected (r/atom nil)]
    (let [selected @!selected
          filtered-people @!filtered-people]
      [:div
       {:class [:truncate]
        :style {;:width  "100%"
                :xwidth    "auto"
                :max-width "25rem"
                :height    :100% #_"25rem"}}
       [ui/combobox {:value     (:id commands)
                     :on-change #(let [action (:on-click context)]
                                   (when action
                                     (action (get command-by-id %))))}
        [:div.relative.w-full
         [:div.relative.w-full
          [:div.flex.items-center.justify-between.w-full
           [placeholder [ui/combobox-input {:style         {:width          "25rem"
                                                            :height         :4rem
                                                            :padding-inline "var(--size-4)"
                                                            :color          "var(--text1)"
                                                            :background     "var(--surface000)"}
                                            :class         [:outline-none :focus:outline-none]
                                            :placeholder   "Søk etter handling / ressurs..."
                                            :display-value (fn [id] (:name (get command-by-id id)))
                                            :on-change     #(reset! !query (string->query (.-value (.-target %))))}]]]]
         [ui/transition
          {:show       true

           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options
           {:static true
            :style  {:scroll-behavior :smooth
                     :overflow-y      :auto
                     :height          "15rem"
                     ;:width           "100%"
                     :max-width       "28rem"}
            :class  [:snap-y :truncate]}
           (if (seq filtered-people)
             (for [command filtered-people]
               ^{:key (:id command)}
               [ui/combobox-option
                {:disabled (:disabled command)
                 :value    (:id command)
                 :class    [:outline-none]}
                (fn [{:keys [selected active disabled] :as attr}]
                  (listitem (assoc attr :icon (command :icon)) (:name command)))])

             (listitem {:notfound true :icon [:div.text-red-500 [:> solid/HeartIcon]]} (str "Finner ikke " (str @!query))))]]]]])))