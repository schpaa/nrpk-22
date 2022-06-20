(ns devcards.boatinfo
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [booking.modals.boatinfo :as sud]               ; system under design
            [tick.core :as t]
            [fork.reagent :as fork]
            [reagent.core :as r]
            [schpaa.style.input :as sci]
            [booking.styles :as b]
            [schpaa.debug :as l]))

(def uid "RX12902139012309123")

(defcard-rg incomplete
  [:div
   (sud/worklog-card {} {:description "Description"
                         :uid         uid
                         :complete    false
                         :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg incomplete-long-description
  ;"-long-description"
  [:div
   (sud/worklog-card {}
                     {:description "Description -long-description -long-description -long-description -long-description -long-description -long-description -long-description"
                      :uid         uid
                      :extra       false
                      :keywords    false
                      :complete    false
                      :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg completed
  [:div
   (sud/worklog-card {} {:description "Description"
                         :uid         uid
                         :complete    true
                         :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defonce st (r/atom {}))

(defn default-form-wrapper [c]
  [fork/form
   {:prevent-default?    true
    :keywordize-keys     true
    ;:validation          #(validation-fn %)
    :component-did-mount (fn [{:keys [set-values set-untouched set-touched]}]
                           #_(set-values @gunk))
    :on-submit           (fn [{:keys [values]}]
                           (let [data (assoc values
                                        :timestamp (str (t/now))
                                        :uid uid)]
                             #_(when write-fn
                                 (write-fn data))
                             #_(on-close)))
    ;:state            st
    :initial-values      {}}
   (fn [{:keys [form-id] :as props}]
     [:form.p-0 {:id form-id}
      [b/co4
       [sci/input props :text {:class []} :props "props"]
       [sci/combobox
        (conj props {:items [{:id 1 :name "name"} {:id 2 :name "name2"}]})
        [] :props "Props"]
       (when c (c props))]])])


(defn datasource []
  (transduce (comp
               (map (fn [[k v]] (assoc v :id k))))
             conj
             []
             @(db.core/on-value-reaction {:path ["boat-brand"]})))

(defonce state (r/atom {}))

(defcard-rg data
  (fn [st _]
    [:div {:style {:background-color "var(--yellow-2)"
                   :padding          "1rem"}}
     (default-form-wrapper
       (fn [props]
         (sud/boat-form (assoc props
                          :values {:boat-type "sample-boat-type"
                                   :id "sample-id"
                                   :items (map (fn [{:keys [id navn]}] {:id id :name navn}) (datasource))}))))])
  state
  {:inspect-values true})


