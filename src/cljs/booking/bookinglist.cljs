;-[ ] below 100 lines?
(ns booking.bookinglist
  (:require [tick.core :as t]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.dialog]
            [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [booking.views.picker :refer [list-line]]
            [schpaa.components.views :refer [number-view]]
            [nrpk.hov :as hov]
            [clojure.set :as set]
            [schpaa.components.fields :as fields]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.style :as st]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button :as scb]))

(defn- booking-list-item-color-map [relation]
  (case relation
    ;past
    :precedes {:br  [:border-l-8 :border-gray-700]
               :bg  ["dark:bg-black/30" "bg-gray-300" :text-gray-400]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}

    ;future
    :preceded-by {:br  [:border-l-8 :border-amber-500]
                  :bg  ["dark:bg-gray-800" "bg-gray-300"]
                  :fg  ["dark:text-gray-300" "text-gray-700"]
                  :fg- ["dark:text-gray-400" "text-gray-700/50"]}

    ;today
    {:br  [:border-l-8 :border-alt]
     :bg  ["dark:bg-alt-600" "bg-gray-100"]
     :fg  ["dark:text-black" "text-gray-700"]
     :fg- ["dark:text-white" :text-gray-400]}))

(defn time-segment-display [colormap {:keys [hide-name? navn start end relation]}]
  (let [{:keys [fg- p-]} colormap #_(st/fbg' :listitem)
        multiday (< 0 (t/days (t/duration (tick.alpha.interval/new-interval start end))))
        day-name (times.api/day-name (t/date-time start))
        end-day-name (times.api/day-name (t/date-time end))
        {:keys []} (booking-list-item-color-map relation)]
    (if multiday
      [:div.grid.gap-1.tabular-nums
       {:class (concat fg- p-)
        :style {:grid-template-columns "min-content min-content min-content 1fr"}}
       [:div day-name]
       [:div.justify-self-startx.whitespace-nowrap (t/format "d.MM" (t/date-time start))]
       [:div.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]
       [:div]
       [:div end-day-name]
       [:div.self-start.justify-self-start.whitespace-nowrap

        (t/format "d.MM" (t/date (t/date-time end)))]
       [:div.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time end)))]
       [:div]]

      ;;----------------------
      [:div.grid.gap-2.tabular-nums
       {:class (concat fg- p-)
        :style {:grid-template-columns "min-content min-content"
                :grid-auto-rows        ""}}
       [:div.justify-self-end.whitespace-nowrap

        (t/format (str "'" day-name " 'd.MM") (t/date-time start))
        " "
        (t/format "'kl. 'H.mm" (t/time (t/date-time start)))
        (t/format "'—'H.mm" (t/time (t/date-time end)))]])))


;region booking-list-item

(defn boat-number-list [colormap {:keys [fetch-boatdata-for selected]}]
  (when fetch-boatdata-for
    (let [items (sort (map (fn [id] (:number (fetch-boatdata-for id))) selected))]
      (if (< 2 (count items))
        [:div.flex.gap-1.flex-wrap
         (schpaa.components.views/show-more-number-view)
         (for [number (take 2 items)]
           (schpaa.components.views/number-view number))]
        [:div.flex.gap-1.flex-wrap
         (for [number items]
           (schpaa.components.views/number-view number))]))))

(defn bookers-name [colormap {:keys [uid navn]}]
  [:div.truncate
   (if navn
     navn
     (let [user @(db.core/on-value-reaction {:path ["users" uid]})]
       (if (:alias user)
         (:alias user)
         (if (:navn user)
           (:navn user)
           uid))))])

(defn- booking-list-item [{:keys [fetch-boatdata-for offset today on-click details? insert-before insert-below insert-after appearance time-slot]
                           :or   {today (t/new-date)}}
                          ;intent FOR EACH ITEM
                          {:keys [uid id navn insert-before-line-item description selected not-available start end] :as item}]
  (let [selected (map keyword selected)                     ;;selected must be a keyword
        relation (try (tick.alpha.interval/relation start today)
                      (catch js/Error _ nil))
        {:keys [br]} (booking-list-item-color-map relation)]

    [:div.flex

     (when insert-before
       (insert-before id))
     (if (some #{relation} [:precedes])

       ;past
       (let [{:keys [bg fg p] :as colormap} (st/fbg' :booking-listitem-past)]
         [:div.grid.gap-1.p-1.w-full
          {:style    {:grid-template-columns "1fr max-content"}
           :class    (concat fg bg br)
           :on-click #(when on-click (on-click item))}
          (bookers-name colormap item)
          (boat-number-list colormap {:fetch-boatdata-for fetch-boatdata-for
                                      :selected           selected})
          [:div.col-span-2
           [time-segment-display colormap item]]
          (if (some? description)
            [:div.col-span-3
             {:class (concat fg p)}
             description])])

       ;present and future
       (let [{:keys [bg fg fg+ p] :as colormap} (st/fbg' :booking-listitem)]
         [:div.grid.gap-1.p-1.w-full
          {:style    {:grid-template-columns "1fr max-content"}
           :class    (concat fg bg br)
           :on-click #(when on-click (on-click item))}

          (bookers-name colormap item)
          (if-not details?
            [:div.justify-self-end
             (boat-number-list colormap {:fetch-boatdata-for fetch-boatdata-for
                                         :selected           selected})]
            [:div])
          (when details?
            [:div.col-span-2.space-y-px
             (when-not (empty? selected)
               (doall (for [id selected
                            :let [data (when fetch-boatdata-for (fetch-boatdata-for id))]
                            :while (some? data)]
                        [list-line
                         {;:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                          ;:insert-before-line-item hov/open-details
                          :id         id
                          :data       data                  ;(get (into {} boat-db) id)
                          :offset     offset
                          :time-slot  time-slot
                          :appearance (set/union #{:basic :xclear :xhide-location :xextra} appearance)
                          :overlap?   false}])))])

          [:div.col-span-2
           [time-segment-display colormap item]]
          (if (some? description)
            [:div.col-span-3
             {:class (concat fg+ p)}
             description])]))
     (when insert-below
       [:div.col-span-3 (insert-below)])
     (if insert-after
       (insert-after id)
       [:div])]))

(defn booking-list-item-prime [uid [{:keys [start]} item]]
  (let [my-own? (= uid (:uid item))]
    [booking-list-item
     {:fetch-boatdata-for (fn [id] (get (into {} (logg.database/boat-db)) id))
      ;:details?           details?
      :boat-db            (logg.database/boat-db)

      ;:today              today
      :hide-name?         (not (some? uid))
      :insert-before      hov/open-booking-details-button
      #_#_:insert-after (fn [id]
                          (if (booking.views.picker/after-and-including today item)
                            (if my-own?
                              (hov/remove-booking-details-button
                                id
                                (filter (fn [{:keys [] :as item}] (= (:id item) id)) (booking.database/read)))
                              [:div.w-10.shrink-0.flex])
                            [:div.w-10.shrink-0.flex]))}
     item]))

;region

(defn date-row [start end]
  [:<>
   [sc/text-clear (t/format "dd.MM 'kl ' hh:mm" (t/date-time start))]
   [sc/text-clear (t/format "dd.MM 'kl ' hh:mm" (t/date-time end))]])

(o/defstyled listitem-past :div
  ([item]
   (let [{:keys [start end alias description selected]} item]
     [sc/surface-a {:class [:p-2 :space-y-1]
                    :style {:background "var(--surface0)"
                            :box-shadow "var(--inner-shadow-1)"}}
      [sc/col
       [sc/row {:class [:items-center]}
        [sc/row-stretch (date-row start end)]]
       [sc/text-1 alias]
       [sc/subtext-p description]
       [sc/row-end [:div.inline-flex.gap-1 (map sc/badge selected)]]]])))

(o/defstyled listitem-present-future :div
  [:&]
  [:.present :text-white]
  ([item]
   (let [{:keys [owner present start end alias description selected on-delete]} item]
     [sc/surface-a {:class [:p-2]
                    :style {:background (if present "var(--brand1)" "var(--surface000)")}}
      [sc/col {:class [:space-y-1]}
       [sc/row {:class [:items-center :gap-2]}
        [scb/clear (sc/icon [:> solid/DotsHorizontalIcon])]
        [sc/row-stretch {:class (if present [:present])} (date-row start end)]
        (when owner [scb/clear {:on-click on-delete} (sc/icon [:> solid/XCircleIcon])])]
       [sc/subtext {:class [:text-black]} alias]
       [sc/subtext-p description]
       [sc/row-end [:div.inline-flex.gap-1 (map (fn [e] [sc/badge {:on-click #(rf/dispatch [:lab/modal-example-dialog2 true e])} e]) selected)]]]])))

(defn fetch-boatdata-for [id]
  (get (into {} (logg.database/boat-db)) id))

(defn item-past [uid' [id {:keys [uid start navn selected] :as item}]]
  [listitem-past (assoc item :alias (bookers-name nil {:uid  uid
                                                       :navn navn})
                             :selected (sort (map (fn [id] (:number (fetch-boatdata-for id))) selected)))])

(defn item-present-future [today uid [id {:keys [start navn selected] :as item}]]
  [listitem-present-future (assoc item
                             :on-delete #(rf/dispatch [:lab/confirm-delete true item])
                             :present (t/= (t/date (t/date-time start)) (t/date today))
                             :owner (= uid (:uid item))
                             :alias (bookers-name nil {:uid  (:uid item)
                                                       :navn navn})
                             :selected (sort (map (fn [id] (:number (fetch-boatdata-for id))) selected)))])

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
                  (filter (fn [[id data]] (if show-only-my-own?
                                            true
                                            (= uid (:uid data)))))
                  (sort-by (comp :start val) <))]
    [:<>

     ;intent Setting up a modal-message
     [schpaa.style.dialog/modal-with-timeout
      {:!open?     (rf/subscribe [:lab/modal-example-dialog2])
       :close'     #(rf/dispatch [:lab/modal-example-dialog2 false])
       :context    @(rf/subscribe [:lab/modal-example-dialog2-extra])
       :content-fn (fn [{:keys [] :as m}] [:div.w-full
                                           [sc/title-p "Autoclosing message"]
                                           [l/ppre-x m]])}]

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
            (map (partial item-past uid) (filter-before today data)))

      (into [:div.space-y-1
             {:class class}]
            (map (partial item-present-future today uid) (filter-today-and-after today data)))]]))
