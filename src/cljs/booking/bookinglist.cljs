;-[ ] below 100 lines?
(ns booking.bookinglist
  (:require ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [lambdaisland.ornament :as o]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog]
            [schpaa.style.ornament :as sc]
            [tick.core :as t]
            [times.api :as ta]))

(defn bookers-name [{:keys [uid navn]}]
  [:div.truncate
   (if navn
     navn
     (let [user @(db.core/on-value-reaction {:path ["users" uid]})]
       (if (:alias user)
         (:alias user)
         (if (:navn user)
           (:navn user)
           uid))))])

(defn date-row [start end]
  [:<>
   [sc/text-clear (t/format "dd.MM 'kl ' hh:mm" (t/date-time start))]
   [sc/text-clear (t/format "dd.MM 'kl ' hh:mm" (t/date-time end))]])

(defn boat-numbers [selected]
  [:div.inline-flex.gap-1 (map (fn [e] [sc/badge {:on-click #(rf/dispatch [:lab/modal-example-dialog2 true [:boatdetails e]])} (:number e)]) selected)])

(o/defstyled listitem-past-present-future :div
  [:& :text-xs :space-y-1]
  [:.present :text-white]
  [:.item :p-2 :space-y-1]
  ([item]
   (let [{:keys [owner present past start end alias description selected on-delete on-bookingdetails]} item]
     [(cond
        present sc/surface-bookingcard-highlighted
        past sc/surface-bookingcard-past
        :else sc/surface-bookingcard)
      [sc/col {:class [:item]}
       [sc/row {:class [:items-center :gap-4]}
        [scb/clear {:on-click on-bookingdetails} (sc/icon [:> solid/DotsHorizontalIcon])]
        [sc/row-stretch {:class (if present [:present])} (date-row start end)]
        (when owner [scb/clear {:on-click on-delete} (sc/icon [:> solid/XCircleIcon])])]
       [sc/subtext {:class [:text-black]} alias]
       [sc/subtext-p description]
       [sc/row-end [boat-numbers selected]]]])))

(defn fetch-boatdata-for [id]
  (get (into {} (logg.database/boat-db)) id))

(defn item-past-present-future [today uid past [id {:keys [start navn selected] :as item}]]
  [listitem-past-present-future (assoc item
                                  :past past
                                  :on-bookingdetails #(rf/dispatch [:lab/modal-example-dialog2 true [:bookingdetails item]])
                                  :on-delete #(rf/dispatch [:lab/confirm-delete true item])
                                  :present (t/= (t/date (t/date-time start)) (t/date today))
                                  :owner (= uid (:uid item))
                                  :alias (bookers-name {:uid  (:uid item)
                                                        :navn navn})
                                  :selected (sort-by :number < (map (fn [id] (fetch-boatdata-for id)) selected)))])

(defn filter-before [today data]
  (let [precedes (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:precedes]))))]
    (transduce (comp (precedes today)) conj [] data)))

(defn filter-today-and-after [today data]
  (let [preceded-by (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:preceded-by :meets :during]))))]
    (transduce (comp (preceded-by today)) conj [] data)))

(rf/reg-sub :lab/confirm-delete :-> (fn [db] (get db :lab/confirm-delete false)))

(rf/reg-sub :lab/confirm-delete-extra :-> (fn [db] (get db :lab/confirm-delete-extra)))

(rf/reg-event-db :lab/confirm-delete (fn [db [_ arg extra]] (if arg
                                                              (assoc db :lab/confirm-delete arg
                                                                        :lab/confirm-delete-extra extra)
                                                              (update db :lab/confirm-delete (fnil not true)))))

(defn booking-list [{:keys [uid today booking-data class details?]}]
  (let [today (t/new-date 2022 1 21)
        show-only-my-own? (-> (schpaa.state/listen :opt/show-only-my-own) deref)
        data (->> booking-data
                  (filter (fn [[id data]] (if show-only-my-own? true (= uid (:uid data)))))
                  (sort-by (comp :start val) <))]
    [:<>
     ;intent Setting up a modal-message
     [schpaa.style.dialog/modal-regular
      {:show       (rf/subscribe [:lab/modal-example-dialog2])
       :close-fn   #(rf/dispatch [:lab/modal-example-dialog2 false])
       :context    @(rf/subscribe [:lab/modal-example-dialog2-extra])
       :content-fn (fn [[context data]]
                     (cond
                       (= :bookingdetails context)
                       [sc/col
                        [sc/title-p "Booking details"]
                        (when-let [tm (some-> (:timestamp data) (t/instant) (t/zoned-date-time))]
                          [sc/text (ta/datetime-format tm)])
                        [sc/text (bookers-name data)]
                        (for [e (:selected data)
                              :let [data (fetch-boatdata-for e)
                                    {:keys [number aquired-year aquired-price]} (fetch-boatdata-for e)]]
                          #_[l/ppre-x data]
                          [sc/row {:class [:gap-4]}
                           [sc/text number]
                           [sc/text aquired-year]
                           [sc/text aquired-price]])
                        [l/ppre-x data]]
                       (= :boatdetails context)
                       [:div
                        [sc/title-p "Boat details"]
                        [l/ppre-x data]]))}]

     ;intent Setting up a modal-message for delete
     [schpaa.style.dialog/modal-confirm-delete
      {:content-fn (fn [{:keys [start selected] :as m}]
                     [sc/col {:class [:space-y-4 :w-full]}
                      [sc/row [sc/title-p "Bekreft sletting"]]
                      [sc/text "Dette kommer til å slette booking dato slik og slik. Er du sikker?"]
                      (l/ppre-x start selected m)])
       :context    @(rf/subscribe [:lab/confirm-delete-extra])
       :action     (fn [{:keys [start selected] :as m}] (tap> m))
       :on-close   (fn [] (tap> "closing after save"))
       :vis        (rf/subscribe [:lab/confirm-delete])
       :close      #(rf/dispatch [:lab/confirm-delete false])}]

     [:div.space-y-1
      (into [:div.space-y-1]
            (map (partial item-past-present-future today uid true) (filter-before today data)))

      (into [:div.space-y-1
             {:class class}]
            (map (partial item-past-present-future today uid false) (filter-today-and-after today data)))]]))