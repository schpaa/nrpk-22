(ns eykt.hoc
  "higher-order components"
  (:require [db.core :as db]
            [re-frame.core :as rf]
            [booking.views]
            [logg.views]
            [eykt.fsm-helpers :refer [send]]
            [reagent.core :as r]
            [tick.core :as t]
            [booking.bookinglist]
            [schpaa.style :as st]
            [schpaa.debug :as l]
            [schpaa.button :as bu]
            [schpaa.modal :as modal]
            [schpaa.modal.readymade :as readymade]))

;region booking

(defonce selected (r/atom #{}))

(defn new-booking []
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [booking.views/booking-form
     {:boat-db       (sort-by (comp :number val) < (logg.database/boat-db))
      :selected      selected
      :uid           (:uid @user-auth)
      :on-submit     #(send :e.complete %)
      :cancel        #(send :e.cancel-booking)
      :my-state      schpaa.components.views/my-state
      :booking-data' (sort-by :date > (booking.database/read))}]))

(defn last-active-booking [{:keys [uid] :as m}]
  [:<>
   (let [today (t/date)
         data (->> (booking.database/read)
                   (filter (fn [{:keys [] :as item}] (= (:uid item) uid)))
                   (filter (fn [{:keys [start]}] (t/<= today (t/date (t/date-time start)))))
                   (sort-by :start <)
                   first)]
     (if (some? data)
       [:<>]
       [:div "Du har ingen bookinger"]))])

(defn all-active-bookings [{:keys [data]}]
  (let [user-auth @(rf/subscribe [::db/user-auth])
        accepted-user? @(rf/subscribe [:app/accepted-user?])]
    [booking.bookinglist/booking-list
     {:boat-db        data
      :details?       @(rf/subscribe [:bookinglist/details])
      :accepted-user? accepted-user?
      :booking-data   (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) #_(booking.database/read)
      :today          (t/new-date 2022 1 26)
      :uid            (:uid user-auth)}]))

(defn all-boats [{:keys [details? data]}]
  [logg.views/all-boats
   {:details? details?
    :data     data}])

(defn all-boats-footer [_]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
    (r/with-let [opt2 (r/atom false)]
      [:div.flex.justify-between.items-center.gap-x-2.px-4.sticky.bottom-0.h-16
       {:class (concat fg bg)}
       (schpaa.components.views/modern-checkbox'
         {:set-details #(schpaa.state/change :opt1 %)
          :get-details #(-> (schpaa.state/listen :opt1) deref)}
         (fn [checkbox]
           [:div.flex.items-center.gap-2
            checkbox
            [:div.text-base.font-normal.space-y-0
             [:div {:class (concat fg+ p)} "Detaljer"]
             [:div {:class (concat fg p-)} "Vis alle detaljer"]]]))

       (schpaa.components.views/modern-checkbox'
         {:disabled?   true
          :set-details #(reset! opt2 %)
          :get-details #(-> opt2 deref)}
         (fn [checkbox]
           [:div.flex.items-center.gap-2.w-full
            {:class (concat fg-)}
            [:div.text-base.font-normal.space-y-0
             [:div.text-right {:class p} "Grupper"]
             [:div {:class p-} "Grupper etter merke"]]
            checkbox]))])))

;endregion

;region user

(defn user-logg []
  (let [user-auth (rf/subscribe [::db/user-auth])
        accepted-user? (rf/subscribe [:app/accepted-user?])]
    [:div.select-none

     [booking.bookinglist/booking-list
      {:boat-db        (sort-by (comp :number val) < (logg.database/boat-db))
       :class          []
       :accepted-user? @accepted-user?
       :data           (booking.database/read)
       ;:today          (t/new-date 2022 1 4)
       :uid            (:uid @user-auth)}]]))


(comment
  (do
    (fbg 0 0 [:space-y-4])))

(defn debug []
  [:div

   (letfn [(open-message []
             (readymade/message
               {:header  "some message22"
                :content "Hi there"
                :footer  "Footer"}))
           (open-popup [type]
             (readymade/popup {:type    type
                               :content [[:div.grid.h-24
                                          [:div.self-start "Pop from space"]]]}))
           (open-dialog []
             (readymade/ok-cancel
               {:flags           #{:weak-dim :-timeout}
                :button-captions (fn [id] (get {:ok     "Okay then"
                                                :cancel "Back out"} id))
                :buttons         #{:ok :cancel}
                :ok              #(js/alert "!")
                :title           "Hi there"
                :content         [[:div "Hang out"]
                                  [:div "Just a message to ya"]]}))]

     [:div.flex.p-4.gap-4.flex-wrap
      [bu/regular-button {:on-click #(open-popup :message)} "Message"]
      [bu/regular-button {:on-click #(open-popup :error)} "Error"]
      [bu/regular-button {:on-click open-message} "Message"]
      [bu/regular-button {:on-click open-dialog} "Dialog"]
      [bu/regular-button {:on-click readymade/open-complex} "Complex"]])

   [:div.grid.gap-1.py-1
    {:class ["bg-black/50"]
     :style {:grid-template-columns "repeat(auto-fit,minmax(15rem,1fr))"}}
    ;intent dark panel

    (for [b '(:void :surface :listitem :dialog :error :button-cta :button :button-danger :form :field :field-label)]
      (let [{:keys [bg bg+ fg- fg fg+ hd p p- he]} (st/fbg' b)]
        [:div
         [:div.p-4.space-y-1 {:class bg}
          [:div {:class (concat fg- he)} b]
          [:div.flex.justify-between {:class (concat fg hd)} [:div "fg hd"] [:div "Normal header"]]
          [:div.flex.justify-between {:class (concat p fg+)} [:div "p fg+"] [:div "strong"]]
          [:div.flex.justify-between {:class (concat p fg)} [:div "p fg"] [:div "neutral normal"]]
          [:div.flex.justify-between {:class (concat p fg-)} [:div "p fg-"] [:div "disabled or unimportant"]]]
         [:div.p-4 {:class bg+}
          [:div.flex.justify-between {:class (concat p- fg+)} [:div "p- fg+"] [:div "strong"]]
          [:div.flex.justify-between {:class (concat p- fg)} [:div "p- fg"] [:div "neutral normal"]]
          [:div.flex.justify-between {:class (concat p- fg-)} [:div "p- fg-"] [:div "disabled or unimportant"]]]]))]

   [:div.antialiased.grid.gap-1
    {:class ["bg-black/50"]
     :style {:grid-template-columns "repeat(auto-fit, minmax(15rem,1fr))"}}
    (for [b '(:void :surface 2 3)
          :let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' b)]]
      [:div.p-2.space-y-2 {:class bg}
       [:div {:class (concat fg- p-)} "Never mix strong and weak, they define semantics"]
       [:div.h-4]
       [:div {:class (concat he fg-)} "Primary HEADER"]
       [:div {:class (concat hd fg+)} "Normal headers can wrap lines"]
       [:div {:class (concat p fg+)} "Primary text can contain " [:span.font-extrabold "bold"] " words too, but it does not happen very often."]
       [:div {:class (concat p fg+)} "Too many variations on a page can " [:span.italic "clutter"] " the appearance and make it hard to..."]
       [:div {:class (concat p- fg)} "Secondary text goes like this " [:span.font-extrabold "with"] " bold words too spanning several lines demonstrated by this specific text. It is sometimes smaller in size too but not much smaller, just a point or two."]])]])

;endregion
