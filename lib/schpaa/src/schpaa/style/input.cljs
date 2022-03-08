(ns schpaa.style.input
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]))

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