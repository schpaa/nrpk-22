(ns schpaa.components.views
  (:require [schpaa.debug :as l]
            [re-frame.db]
            [tick.core :as t']
            [times.api :as ta]
            [fork.re-frame :as fork]
            [reagent.core :as r]
            [eykt.state :as state]
            [re-frame.core :as rf]
            [schpaa.components.fields :as fields]
            [tick.alpha.interval]
            [schpaa.icon :as icon]
    ;todo remove dep
            [re-statecharts.core :as rs]))

(defn- dark-rounded-view [& content]
  [:div.xrounded.px-4.py-2.space-y-4.-mx-4
   {:class ["bg-gray-400"]}
   (into [:<>] (map identity content))])

(defn- tab-rounded-view [& content]
  [:div.rounded-t-lg.px-3.py-4.space-y-4
   {:class ["bg-gray-100" "dark:bg-gray-700"]}
   (into [:<>] (map identity content))])

(defn rounded-view [opt & content]
  (if (:tab opt)
    [tab-rounded-view content]
    (if (:dark opt)
      [dark-rounded-view content]
      [:div.rounded.p-3.space-y-4
       {:class [:dark:bg-gray-800
                :shadow
                :bg-gray-100
                :dark:text-gray-500]}
       (into [:<>] (map identity content))])))

(defonce my-state (r/atom {}))

(defn booking-header [{:keys [book-now]}]
  (rounded-view
    {}
    [:h2.h-10.flex.items-center "Booking-header med betingelser etc"]
    [:div.flex.justify-end
     [:button.btn.btn-free.btn-cta {:on-click #(book-now)} "Book båt"]]))

(rf/reg-sub :exp/filter (fn [db]
                          (ta/instant->str (:date-filter db))))

(rf/reg-event-db :exp/set-date-filter (fn [db [_ arg]]
                                        (tap> arg)
                                        (assoc db :date-filter arg)))

(defn booking-confirmed [{:keys [values]}]
  (rounded-view
    {}
    [:div "Confirmed"]
    [l/ppre values]

    [:div.flex.gap-4.justify-between
     ;(fields/button #(state/send :e.restart) nil "s")
     [:button.btn.btn-danger
      {:type     :button
       :on-click #(state/send :e.cancel-booking values)} "Annuler"]
     [:button.btn.btn-cta.text-black
      {:type     :button
       :on-click #(state/send :e.restart)} "Bekreft"]]))
