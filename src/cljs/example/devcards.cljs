(ns example.devcards
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [devcards.core :as dc :include-macros true]
            ["highlight.js" :as hljs]
            [reagent.core]
            [schpaa.modal.readymade :refer []]
            [schpaa.modal :as modal :refer [form]]
            [booking.bookinglist]
            [schpaa.state]
            [tick.core :as t]
            [eykt.hov :as hov]))

(js/goog.exportSymbol "hljs" hljs)
(js/goog.exportSymbol "DevcardsSyntaxHighlighter" hljs)

(defn ^:export init! []
  (dc/start-devcard-ui!))

(defn reload! []
  (init!))

(defcard-rg two
  [:div.w-96
   [form
    {:dialog-type :message
     :header  [:div.p-4 "header"]
     :footer  [:div.p-4 "footer"]
     :form-fn (schpaa.modal.readymade/simple-ok-form)}]])

(defcard-rg three
  [:div.w-96
   [form {:header      [:h2.p-4 "header"]
          :dialog-type :form
          :form-fn
          (modal/ok-cancel-buttons
            {:on-ok     #(do
                           (eykt.fsm-helpers/send :e.hide)
                           #_(action))
             :on-cancel #(eykt.fsm-helpers/send :e.hide)})
          ;(fn [] [:div "Form"])
          :footer      [:div.p-4 "footer"]}]])

(defcard-rg four
  (let [my-own? true]
    [:div [booking.bookinglist/booking-list-item
           {:fetch-boatdata-for (fn [id] (get {:a1 {:number "A"}
                                               :a2 {:number "B"}} id {:number id})) ;(get (into {} boat-db) id)
            :today              (t/new-date 2022 1 21)
            :insert-after       (fn [id]
                                  [:div.flex
                                   (when my-own? (hov/remove-booking-details-button id []))
                                   (hov/open-booking-details-button id)])}
           {:start (t/>> (t/now) (t/new-duration 1 :hours))
            :end   (t/>> (t/now) (t/new-duration 114 :hours))
            :description "sample"
            :selected ["a1" "a2"]}]]))

