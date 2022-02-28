(ns schpaa.style.radio
  (:require [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            ["@heroicons/react/solid" :as solid]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]))

(def plans
  [{:name "Samme dag"}
   {:name "+1 dag"}
   {:name "+2 dager"}])

(def plans-by-name
  (zipmap (map :name plans) plans))

(def middot-props
  {:dangerouslySetInnerHTML {:__html "&middot;"}})

(defn radio-group-example [set-value start-date plans]
  (let [plans-by-name (zipmap (map :id plans) plans)]
    (r/with-let [!selected (r/atom (first plans))]
      (let [selected @!selected]
        [ui/radio-group
         {:value     (:id selected)
          :on-change #(do (tap> %)
                          (set-value %)
                          (reset! !selected (get plans-by-name % 0)))}
         [ui/radio-group-label {:class :sr-only} "Server size"]
         [:div.flex.gap-1.outline-none.focus:outline-none
          (for [[idx plan] (map-indexed vector plans)]
            ^{:key (:name plan)}
            [ui/radio-group-option
             {:value (:id plan)}
             (fn [{:keys [active checked]}]
               [:div.flex.px-4
                {:class (concat
                          [(when (zero? idx) :rounded-l)
                           (when (= idx (dec (count plans))) :rounded-r)
                           :relative
                           :h-10
                           :cursor-pointer
                           :outline-none
                           :focus:outline-none]
                          (when active
                            [:ring-2 :ring-offset-2 :ring-offset-blue-500 :ring-white :ring-opacity-60])
                          (if checked
                            [:text-white]
                            []))
                 :style (conj
                          {:box-shadow "var(--inner-shadow-1)"}
                          (if (and active checked)
                            {:background "var(--surface5)"}
                            (if checked
                              {:background "var(--surface5)"}

                              {:background "white"})))}
                [:div.flex.items-center
                 [:div.text-base
                  [ui/radio-group-label {:as :p.font-medium :class (if checked :text-white :text-gray-900)}
                   (if (and start-date (:fn plan))
                     (str ((:text plan) ((:fn plan) start-date)))
                     ((:text plan)))]

                  #_[ui/radio-group-description {:as :span.inline :class (if checked :text-sky-100 :text-gray-500)}]]]])])]]))))
