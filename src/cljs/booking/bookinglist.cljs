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
                                      fetch-boatdata-for]]
            [schpaa.icon :as icon]
            [booking.ico :as ico]
            [schpaa.style.input :as sci]
            [arco.react]
            [booking.data]))

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
  [:div.inline-flex.gap-1 (mapv (fn [e] [sc/badge {:on-click #(js/alert "open-modal-boatinfo {:data ? :uid uid}")}
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

;region copied from yearwheel, find a new home

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(defn flex-datetime [date formatted]
  (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
        on-click {:on-click #(do
                               (tap> ["toggle" @relative-time?])
                               (.stopPropagation %)
                               (toggle-relative-time))}]
    (when date
      (if @relative-time?
        [sc/link on-click (formatted (ta/date-format-sans-year date))]
        [arco.react/time-since {:times  [(if (t/date-time? date)
                                           (t/date-time date)
                                           (t/at (t/date date) (t/midnight)))
                                         (t/now)]
                                :config booking.data/arco-datetime-config}
         (fn [formatted-t]
           [sc/link on-click (formatted formatted-t)])]))))

(def data (r/atom {}))

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)
   {:on-click #(do
                 (.stopPropagation %)
                 (tap> ["DELETE" k v])
                 (swap! data update k assoc :deleted (not deleted)))}
   #_#(db/database-update
        {:path  ["booking-posts" "articles" (name k)]
         :value {:deleted (not deleted)}})
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    (if deleted (icon/small :rotate-left) ico/trash)]])

(o/defstyled listitem :div
  [:& :cursor-pointer :pt-0 :pb-1]

  {:min-height "var(--size-4)"}
  [:&
   [:.deleted {:color           "red"
               :text-decoration :line-through
               :opacity         0.3}]
   [:&:hover {:background "var(--surface00)"}]]
  ([{:keys [class on-click] :as attr} & children]
   ^{:on-click on-click}
   [:<> [:div {:class class} children]]))


(o/defstyled line :div
  [:& :gap-2
   {:padding-left "2rem"
    :display      :inline-flex
    :text-indent  "-1rem"
    :flex-wrap    :wrap
    :align-items  :baseline
    :margin-top   "var(--size-1)"
    :color        "var(--text3)"
    :line-height  "var(--font-lineheight-3)"}])

(o/defstyled text :span
  {:color "var(--text3)"})
(o/defstyled emph :span
  {:color "var(--text1)"})
(o/defstyled dim :span
  {
   :color       "var(--text3)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-3)"})

(defn- listitem-softwrap [{:keys [id selected start uid sleepover timestamp navn description deleted end created deleted type] :as m}]
  (let [show-editing @(schpaa.state/listen :booking/editing)
        show-description (schpaa.state/listen :booking/show-description)
        relative-time? (schpaa.state/listen :app/show-relative-time-toggle)]

    [listitem {:class [(when deleted :deleted)]}

     ;[l/ppre-x m]

     (apply line
            (remove nil?
                    [(when show-editing (trashcan id m))

                     (when start
                       (dim (booking.flextime/relative-time (t/date-time start))))


                     (when end
                       (let [overnatting (not= (t/day-of-month (t/date-time start))
                                               (t/day-of-month (t/date-time end)))
                             distance (tick.alpha.interval/new-interval
                                        (t/date-time start)
                                        (t/date-time end))
                             days (t/days (t/duration distance))
                             hours (t/hours (t/duration distance))]
                         [:<>
                          (when overnatting [sc/text1 "overnatting"])
                          (if (zero? days)
                            [sc/text1 hours " timer"]
                            [sc/text1 (if (= 1 days) "1 dag" (str days " dager"))])
                          (when-not @relative-time?
                            (if (pos? days)
                              (dim (booking.flextime/relative-time (t/date-time end) times.api/arrival-date))
                              (dim (booking.flextime/relative-time (t/date-time end) times.api/time-format))))]

                         #_(dim (flex-datetime (t/date-time end) #(vector :span %)))))

                     #_(when type
                         (emph (or (some->> type (get sci/person-by-id) :name) "|?")))

                     ;(when sleepover [sc/text1 "Overnatting"])
                     (when navn [sc/text1 navn])
                     (when (pos? (count selected))
                       (into [:div {:style {:text-indent :unset
                                            :padding     :unset}}] (mapv (fn [e] [sc/badge-2 {
                                                                                              :on-click #(open-modal-boatinfo e)}
                                                                                  (:number e)]) selected)))
                     (when (and description @show-description)
                       [sc/small0 description])]))]))

;region

(defn booking-list [{:keys [uid today booking-data class details?]}]
  (let [show-archived (schpaa.state/listen :booking/show-archived)
        show-only-my-own? (schpaa.state/listen :booking/private-only)]
    (fn [{:keys [uid today booking-data class details?]}]
      (let [today (t/new-date 2022 1 21)
            ;show-only-my-own? (-> (schpaa.state/listen :opt/show-only-my-own) deref)
            data (->> booking-data
                      (filter (fn [[id data]] (if @show-only-my-own? (= uid (:uid data)) true)))
                      (sort-by (comp :start val) <))]
        [sc/col-space-8

         (when @show-archived
           (when-some [d (filter-before today data)]
             [sc/col-space-4
              [sc/hero "Arkivert"]
              (into [:div]
                    (map (fn [[id' {:keys [selected] :as m}]]
                           (listitem-softwrap (assoc m :type :archived
                                                       :id id'
                                                       :selected (sort-by :number < (map (fn [id] (fetch-boatdata-for id)) selected)))))
                         d))]))

         (when-some [d (filter-today today data)]
           [sc/col-space-4
            [sc/hero "I dag"]
            (into [:div]
                  (map (fn [[id' {:keys [selected] :as m}]]
                         (listitem-softwrap (assoc m :type :today
                                                     :id id'
                                                     :selected (sort-by :number < (map (fn [id] (fetch-boatdata-for id)) selected)))))
                       d))])

         (when-some [d (filter-after-today today data)]
           [sc/col-space-4
            [sc/hero "Neste"]
            (into [:div]
                  (map (fn [[id' {:keys [selected] :as m}]]
                         (listitem-softwrap (assoc m :type :future
                                                     :id id'
                                                     :selected (sort-by :number < (map (fn [id] (fetch-boatdata-for id)) selected)))))
                       d))])]
        #_[sc/grid-wide
           (concat (when @show-archived
                     (into [] (map (partial item-past-present-future today uid true)
                                   (filter-before today data))))

                   (into [] (map (partial item-past-present-future today uid false)
                                 (filter-today today data)))

                   (into [] (map (partial item-past-present-future today uid false)
                                 (filter-after-today today data))))]))))
