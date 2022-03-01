(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]))

(o/defstyled date :div
  [:input :p-2 :w-full :rounded :h-10
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
  ([attr]
   (let [errors? (some-> attr :errors (:name attr))]
     [:div
      {:class (when errors? [:validation-error])}
      [:input (conj attr
                    {:class (when errors? [:validation-error])})]])))