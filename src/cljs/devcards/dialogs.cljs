(ns devcards.dialogs
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [devcards.core :as dc :include-macros true]
            ["highlight.js" :as hljs]
            [reagent.core]
            [schpaa.modal.readymade :refer []]
            [booking.bookinglist]
            [schpaa.state]
            [booking.views.picker]
            [booking.views]
            [devcards.buttons]
            [devcards.temperature]
            [devcards.experiment]
            [booking.data :as app-data]
            [db.core :as db]
            [booking.yearwheel :as sud]
            [booking.modals.boatinfo :as sud2]
            [schpaa.style.input :as sci]
            [fork.reagent :as fork]
            [reagent.core :as r]
            [booking.styles :as b]
            [schpaa.debug :as l]
            [tick.core :as t]))


(defcard-rg somecard
  [:div
   {:style {:font-size "100%"}}
   [sud/addpost-form
    {:data {:type    0
            :tldr    "kort beskrivelse"
            :content "# some markdown-content"
            :date    (str (t/today))}}]])

(defcard-rg boatinfo
  [:div
   {:style {:font-size "100%"}}
   [sud2/modal-boatinfo-windowcontent
    {:uid    "x"
     :admin? true
     :data   {;id work-log slot location description
              :slot     "3A"
              :work-log []
              :number   "XYZ"
              :id       "1" :boat-type "2"}}]])



;(defonce somestate (r/atom {}))

(def values [{:name "Per"
              :id   0}
             {:name "Arne"
              :id   1}])

(defn the-form-view-fn [st]
  [l/boundary
   [fork/form
    {:state               st
     :prevent-default?    true
     :clean-on-unmount?   true
     :keywordize-keys     true
     :component-did-mount (fn [{:keys [set-values]}] (set-values {:fieldname      "ja"
                                                                  :combobox-field 2}))}
    (fn [{:keys [form-id handle-change] :as props}]
      [:form {:id form-id}
       [b/ro
        [sci/input props :text {:class []} :label :fieldname]
        [:div.w-56
         [sci/combobox
          {}
          {:value-by-id   #(get (zipmap (map :id values) values) %)
           :items         values
           :value         0
           :name          :combobox-field
           :handle-change handle-change
           :class         [:someclass]
           :label         "Names"
           :fieldname     :combobox-field}]]]])]])

(defonce state (r/atom {:selected  1
                        :selected2 0}))


(defonce state2 (r/atom {}))

(defcard-rg combobox
  (fn [m _]
    [:div.bg-alt.p-4
     [the-form-view-fn m]])
  state2
  {:watch-atom   true
   :inspect-data true})

(defcard-rg combobox-basics
  (fn [st _]
    [the-form-view-fn st])
  state
  {:inspect-data true})

(def values2 [{:name "Nils"
               :id   0}
              {:name "Petter"
               :id   1}
              {:name "Jonas"
               :id   2}])

(defn view-fn [st]
  [:div.py-8.space-y-8

   [:div.w-56
    [l/pre-s (:selected @st)]
    [sci/combobox
     {}
     {:value       (:selected @st)
      :value-by-id #(get (zipmap (map :id values) values) % "|?|")
      :on-change   #(swap! st assoc :selected %)
      :items       values
      :class       []
      :label       "Some test-label"
      :fieldname   :combobox-field}]]

   [:div.w-56
    [l/pre-s (:selected2 @st)]
    [sci/combobox
     {:value     (:selected2 @st)
      :on-change #(swap! st assoc :selected2 %)
      :items     values2}
     {:class     []
      :label     "Some test-label"
      :fieldname :combobox-field2}]]])

(defcard-rg combobox-basics
  (fn [st _]
    [view-fn st])
  state
  {:inspect-data true})
