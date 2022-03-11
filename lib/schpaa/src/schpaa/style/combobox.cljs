(ns schpaa.style.combobox
  (:require [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [db.core :as db]
            [clojure.string :as string]
            [schpaa.debug :as l]
            [schpaa.style.ornament :as sc]))

(def people [{:id "1", :name "Wade Cooper"}
             {:id "2", :name "Avlys booking"}
             {:id "3", :name "Kok kaffe" :disabled true}
             {:id "4", :name "Avlys kurs"}
             {:id "5", :name "Book båter for kurs"}
             {:id "6", :name "Båtlisten på Nøklevann"}
             {:id "7", :name "Båtlisten på sjøbasen"}
             {:id "8", :name "Endre mine opplysninger"}
             {:id "9", :name "Skrive nytt innlegg"}
             {:id "10", :name "Book båt"}
             {:id "11", :name "Lage nytt båtnummer" :disabled true}])

(def person-by-id (zipmap (map :id people) people))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(defn listitem [{:keys [notfound selected active disabled] :as attr} value]
  [:div.gap-2
   {:style {:display           :flex
            :align-items       :center
            :border-left       "4px solid "
            :border-left-color (if active "var(--brand1)" "transparent")
            :padding           "var(--size-4)"
            :font-family       "Montserrat"
            :font-weight       500
            :background        (if active "var(--surface0)" "var(--surface000)")}
    :class [:px-4 :truncate (if disabled :text-gray-300)]}
   [sc/icon-tiny (when-not (or notfound disabled) [:> outline/ArrowRightIcon])]
   #_[:span.block.xtruncate {:class [(if selected :xfont-medium :font-normalx)
                                     (if disabled :text-gray-300)]}]
   [:div value]

   #_(when selected
       [:span.absolute.inset-y-0.left-0.flex.items-center.pl-3
        {:class (if active :text-white :text-teal-600)}
        [:> solid/CheckIcon {:class "w-5 h-5" :aria-hidden "true"}]])])

(o/defstyled placeholder :div
  [:input
   {:font-family "Montserrat"
    :font-weight 500

    :font-size   "var(--font-size-2)"
    :color       "var(--surface5)"}
   ["&::placeholder"
    {:font-size "var(--font-size-2)"
     :color     "var(--surface1)"}]])

(defn combobox-example []
  (r/with-let [!selected (r/atom nil #_(first people))
               !query (r/atom "")
               !filtered-people (r/reaction
                                  (let [query @!query]
                                    (sort-by :name (if (string/blank? query)
                                                     people
                                                     (filter #(string/includes? (string->query (:name %)) query)
                                                             people)))))]
    (let [selected @!selected
          filtered-people @!filtered-people]
      [:div.w-96
       {:style {:height "24rem"}}
       [ui/combobox {:value     (:id selected)
                     :on-change #(reset! !selected (get person-by-id %))}
        [:div.relative.mt-1

         [:div.relative.w-full.text-left.bg-white.rounded-lg.xshadow-md.cursor-default.focus:outline-none.focus-visible:ring-2.focus-visible:ring-opacity-75.focus-visible:ring-white.focus-visible:ring-offset-teal-300.focus-visible:ring-offset-2.sm:text-sm.overflow-hidden
          [:div.flex.items-center.justify-between

           [placeholder [ui/combobox-input {:style         {:padding "var(--size-4)"}
                                            :class         [:outline-none :focus:outline-none :w-full]
                                            :placeholder   "Søk etter en kommando..."
                                            :display-value (fn [id] (:name (get person-by-id id)))
                                            :on-change     #(reset! !query (string->query (.-value (.-target %))))}]]
           [ui/combobox-button {:class "absolutex inset-y-0 right-0 flex items-center pr-2"}]]]

         [ui/transition
          {:show       true
           :leave      "transition ease-in duration-100"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [ui/combobox-options
           {:static true
            :style  {:max-height "20rem"}
            :class  "absolute w-full snap-y  xmt-1 overflow-auto text-base
              xmax-h-72 ring-1 ring-black ring-opacity-5 focus:outline-none "}
           [:<>
            (if (seq filtered-people)
              (for [person filtered-people]
                ^{:key (:id person)}
                [ui/combobox-option
                 {:disabled (:disabled person)
                  :value    (:id person)
                  :class    [:snap-start]
                  :xclass   (fn [{:keys [active disabled]}]
                              (concat [:cursor-default :select-none :relative]
                                      (if disabled [:text-red-500]
                                                   (if active
                                                     [:text-white :bg-black]
                                                     [:text-gray-900]))))}
                 (fn [{:keys [selected active disabled] :as attr}]
                   (listitem attr (:name person)))])

              (listitem {:notfound true} (str "Finner ikke " (str @!query))))]]]]]])))