(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]))

(o/defstyled date :div
  [:input :p-2 sc/rounded :w-full]
  [:input.validation-error :bg-red-100
   [:&:focus :bg-white]]
  [:div.validation-error
   :rounded-sm
   {:outline "solid var(--red-3) 2px"}]

  ([attr]
   (let [errors? (some-> attr :errors (:name attr))]
     [:div
      {:class (when errors? [:validation-error])}
      [:input (conj attr
                    {:class (when errors? [:validation-error])})]])))