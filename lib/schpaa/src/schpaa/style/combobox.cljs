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
   {:id "9", :name "Skrive nytt innlegg" :icon [:> outline/PencilIcon] :action #(rf/dispatch [:app/navigate-to [:r.xxx]])}
   {:id "10", :name "Book båt" :icon [:> outline/TicketIcon]}
   {:id "11", :name "Lage nytt båtnummer" :disabled true}
   {:id "12", :name "Gå til designspråk/mal" :icon [:> outline/ColorSwatchIcon] :action #(rf/dispatch [:app/navigate-to [:r.designlanguage]])}
   {:id "13", :name "Gå til forsiden" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.forsiden]])}
   {:id "14", :name "Gå til betingelser" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.terms]])}
   {:id "15", :name "Gå til vilkår" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.conditions]])}
   {:id "16", :name "Gå til retningslinjer" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.retningslinjer]])}])

(def command-by-id (zipmap (map :id commands) commands))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(defn listitem [{:keys [notfound selected action active disabled icon] :as attr} value]
  [:div.gap-3.select-none
   {
    :style {:display           :flex
            :align-items       :center
            :border-left       "4px solid "
            :border-left-color (if active "var(--brand1)" "transparent")
            :padding           "var(--size-4)"
            :font-family       "Montserrat"
            :font-weight       500
            :background        (if active "var(--surface0)" "var(--surface000)")}
    :class [:px-4 :truncate (if disabled :text-gray-300)]}
   [sc/icon-small (when-not (or disabled) icon)]
   value])

(o/defstyled placeholder :div
  [:input
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
      [:div.w-96
       {:style {:height          "24rem"
                :scroll-behavior :smooth}}
       [ui/combobox {:value     (:id commands #_selected)
                     :on-change #(let [action (:on-click context)]
                                   (when action
                                     (action (get command-by-id %)))
                                   #_(reset! !selected (get command-by-id %)))}
        [:div.relative
         [:div.relative
          [:div.flex.items-center.justify-between.w-full
           [placeholder [ui/combobox-input {:style         {:padding "var(--size-4)"}
                                            :class         [:outline-none :focus:outline-none :w-96]
                                            :placeholder   "Søk etter en kommando..."
                                            :display-value (fn [id] (:name (get command-by-id id)))
                                            :on-change     #(reset! !query (string->query (.-value (.-target %))))}]]]]

         [ui/transition
          {:show       true
           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options
           {:static true
            :style  {:max-height "20rem"}
            :class  "absolute w-full snap-y  xmt-1 overflow-auto text-base xmax-h-72 ring-1 ring-black ring-opacity-5 focus:outline-none "}
           (if (seq filtered-people)
             (for [command filtered-people]
               ^{:key (:id command)}
               [ui/combobox-option
                {:disabled (:disabled command)
                 :value    (:id command)
                 :class    [:snap-start]}
                (fn [{:keys [selected active disabled] :as attr}]
                  (listitem (assoc attr :icon (command :icon)) (:name command)))])

             (listitem {:notfound true :icon [:div.text-red-500 [:> solid/HeartIcon]]} (str "Finner ikke " (str @!query))))]]]]])))