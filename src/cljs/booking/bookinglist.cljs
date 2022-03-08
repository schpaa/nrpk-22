(ns booking.bookinglist
  (:require ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [lambdaisland.ornament :as o]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog :refer [open-modal-boatinfo
                                         open-modal-bookingdetails
                                         open-modal-confirmdeletebooking]]
            [schpaa.style.ornament :as sc]
            [tick.core :as t]
            [times.api :as ta]
            [schpaa.style.switch]
            [reagent.core :as r]
            [booking.database :refer [bookers-details
                                      bookers-name
                                      fetch-boatdata-for]]))

(defn date-row [start end]
  (let [start (t/date-time start)
        end (t/date-time end)
        same-day? (t/= (t/date start)
                       (t/date end))]
    (if same-day?
      [:div.flex.gap-2.flex-wrap
       [sc/row {:class [:gap-2]}
        [sc/text-clear-large (t/format "dd.MM " start)]
        [sc/text-clear (t/format "' kl ' HH.mm" start)]]

       [sc/text-clear " — " (t/format "HH.mm" end)]]
      [:div.flex.gap-2.flex-wrap
       [sc/row {:class [:gap-2]}
        [sc/text-clear-large (t/format "dd.MM" start)]
        [sc/text-clear (t/format "' kl ' HH.mm" start)]]

       [sc/row {:class [:gap-2]}
        [sc/text-clear-large " — " (t/format "dd.MM" end)]
        [sc/text-clear (t/format "' kl ' HH.mm" end)]]])))

(defn boat-numbers [selected]
  [:div.inline-flex.gap-1 (map (fn [e] [sc/badge {:on-click #(open-modal-boatinfo e)}
                                        (:number e)]) selected)])

(o/defstyled listitem-past-present-future :div
  [:& :text-xs :space-y-1]
  [:.present :text-white]
  [:.headerline :bg-black-5 :-m-2 :mb-2 :p-2 :rounded-t]
  [:.item :p-2 :space-y-1]
  ([item]
   (let [{:keys [owner present past start end alias description selected on-delete on-bookingdetails]} item]
     [sc/col {:class [:item]}

      [sc/row {:class [:justify-between :h-full :items-center :gap-4 (if present :present) :headerline]}
       [scb/clear {:on-click on-bookingdetails} (sc/icon [:> solid/DotsHorizontalIcon])]
       [sc/row {:class [:justify-between :items-center :w-full :flex-grow]} (date-row start end)]
       (when owner [scb/danger-small {:on-click on-delete} (sc/icon [:> solid/XCircleIcon])])]

      [sc/subtext {:class [:text-black]} alias]
      [sc/subtext-p [:div.line-clamp-1 (or description [:div.h-7])]]
      [sc/row-end [boat-numbers selected]]])))

(defn item-past-present-future [today uid past [id {:keys [start navn selected] :as item}]]
  (let [present (t/= (t/date (t/date-time start)) (t/date today))
        past past]
    [(cond
       present sc/surface-bookingcard-highlighted
       past sc/surface-bookingcard-past
       :else sc/surface-bookingcard)
     [listitem-past-present-future
      (assoc item
        :past past
        :on-bookingdetails #(open-modal-bookingdetails item)
        :on-delete #(open-modal-confirmdeletebooking item)
        :present (t/= (t/date (t/date-time start)) (t/date today))
        :owner (= uid (:uid item))
        :alias (bookers-name {:uid  (:uid item)
                              :navn navn})
        :selected (sort-by :number < (map (fn [id] (fetch-boatdata-for id)) selected)))]]))

(defn filter-before [today data]
  (let [precedes (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:precedes]))))]
    (transduce (comp (precedes today)) conj [] data)))

(defn filter-today-and-after [today data]
  (let [preceded-by (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:preceded-by :meets :during]))))]
    (transduce (comp (preceded-by today)) conj [] data)))

(defn filter-today [today data]
  (let [preceded-by (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:meets :during]))))]
    (transduce (comp (preceded-by today)) conj [] data)))

(defn filter-after-today [today data]
  (let [preceded-by (fn [today] (filter (fn [[_ v]] (some #{(tick.alpha.interval/relation (:start v) today)} [:preceded-by :meets]))))]
    (transduce (comp (preceded-by today)) conj [] data)))

(defn booking-list [{:keys [uid today booking-data class details?]}]
  (let [show-archived (r/atom false)
        show-only-my-own? (r/atom false)]
    (fn [{:keys [uid today booking-data class details?]}]
      (let [today (t/new-date 2022 1 21)
            ;show-only-my-own? (-> (schpaa.state/listen :opt/show-only-my-own) deref)
            data (->> booking-data
                      (filter (fn [[id data]] (if @show-only-my-own? (= uid (:uid data)) true)))
                      (sort-by (comp :start val) <))]
        [:div.space-y-4
         [sc/col {:class [:space-y-4]}
          [sc/row {:class [:items-center :gap-4]}
           [schpaa.style.switch/switch-example
            {:!value  show-archived
             :caption [sc/text "Vis arkiverte"]}]]
          [sc/row {:class [:items-center :gap-4]}
           [schpaa.style.switch/switch-example
            {:!value  show-only-my-own?
             :caption [sc/text "Bare vis bookinger fra meg"]}]]]

         [sc/grid-wide
          (concat (when @show-archived
                    (into [] (map (partial item-past-present-future today uid true)
                                  (filter-before today data))))

                  (into [] (map (partial item-past-present-future today uid false)
                                (filter-today today data)))

                  (into [] (map (partial item-past-present-future today uid false)
                                (filter-after-today today data))))]]))))