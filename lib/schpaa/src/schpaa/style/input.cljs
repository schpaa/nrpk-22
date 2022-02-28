(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]))

(o/defstyled date :div
  [:input :p-2 :w-full :rounded-sm]
  [:input.validation-error :outline-none
   {:background "var(--red-1)"}
   [:&:focus :bg-white :transition :duration-200]]
  [:div.validation-error
   :rounded-sm
   {:outline "solid var(--red-3) 2px"}]

  ([attr]
   (let [errors? (some-> attr :errors (:name attr))]
     [:div
      {:class (when errors? [:validation-error])}
      [:input (conj attr
                    {:class (when errors? [:validation-error])})]])))