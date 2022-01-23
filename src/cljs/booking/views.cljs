(ns booking.views
  (:require [re-statecharts.core :as rs]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :as views :refer [goto-chevron general-footer
                                                       number-view
                                                       slot-view]]
            [schpaa.modal.readymade :refer [details-dialog-fn]]
            [times.api :refer [day-name day-number-in-year]]
            [tick.core :as t]
            [tick.alpha.interval :refer [relation]]
            [tick.locale-en-us]
            [booking.database]
            [fork.re-frame :as fork]
            [times.api :refer [format]]
            [eykt.fsm-helpers :refer [send]]
            [logg.database]
            [booking.views.picker :refer [boat-picker list-line
                                          has-selection available? convert]]
            [schpaa.icon :as icon]
            [db.core]
            [schpaa.debug :as l]
            [eykt.hov :as hov]
            [clojure.set :as set]))

(declare booking-list-item)

;INTENT our desired timeslot, who is available in this slow?

(defn- overlapping? [id time-slot offset]
  (let [booking-db (filter (partial has-selection id) (booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        overlapping? (pos? (count (filter (fn [{:keys [r?]}] (nil? r?)) status-list)))]
    overlapping?))

(defn pick-list
  "shows only the elements that are in selected"
  [{:keys [items on-click boat-db time-slot offset] :as m}]
  [:div.space-y-px
   (for [[id data] (filter (comp #(some #{%} items) key) boat-db)
         #_#_:when (some #{id} items)]
     [list-line
      (conj m
            {:overlap? (overlapping? id time-slot offset)
             :remove?  true
             :on-click #(on-click id)
             :id       id
             :data     data})])])

(rf/reg-sub :app/details (fn ([db] (get db :details 2))))

(rf/reg-event-db :app/next-detail (fn [db] (update db :details (fnil #(mod (inc %) 3) 0))))

(rf/reg-event-db :app/set-detail (fn [db [_ arg]] (update db :details (fnil #(mod arg 3) 0))))

(rf/reg-sub :app/accepted-user? (fn [db] true))

;region date-time

(defn- straight-date [instant]
  (str
    (t/day-of-month (t/date instant))
    "/"
    (t/int (t/month (t/date instant)))))

;endregion

;region boat-picking


;endregion

(defn booking-validation [{:keys [date start-time end-time sleepover] :as all}]
  (cond-> {}
    (= "" (str date))
    (assoc :date "Trenger start-dato")

    (and (not= "" (str date))
         (let [rel (relation (t/date date) (t/date))]
           (some #{rel} [:precedes :meets])))
    (assoc :date "Dato => Dagens dato")

    (and (not= "" (str date))
         (not= "" (str start-time))
         (let [rel (relation (t/at (t/date date) (t/time start-time)) (t/date-time))]
           (some #{rel} [:precedes :meets])))
    (assoc :start-time "Start-tid > Nå")

    (= "" (str start-time))
    (assoc :start-time "Trenger start-tid")

    (= "" (str end-time))
    (assoc :end-time "Trenger slutt-tid")

    (and
      (not sleepover)
      (not= "" (str start-time))
      (not= "" (str end-time))
      (= :precedes (relation (t/time end-time) (t/time start-time))))
    (assoc :end-time "Trenger slutt-tid < start-tid")))

(defn- push-button
  ([a disabled? c]
   [:button.btn.btn-free
    {:class    (if disabled? [:text-gray-300])
     :type     :button
     :disabled disabled?
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)]))

(defn- button-submit [a c]
  [:button.btn-narrow.btn-free.h-10.btn-cta
   {:type :submit}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn navigation [booking-state my-state]
  [:div.w-full
   ;[l/ppre-x booking-state]
   (rs/match-state booking-state
     [:s.booking :s.initial]
     [:div.flex.justify-between
      (push-button #(send :e.pick-boat) nil "Neste")
      [:div]]

     [:s.booking :s.basic-booking-info]
     [:div.flex.justify-between
      [:div]
      (push-button #(send :e.confirm) nil "Neste")]

     [:s.booking :s.confirm]
     [:div.flex.justify-between
      (push-button #(send :e.pick-boat) nil "Forrige")
      #_(button-submit nil "Bekreft booking")]

     [:div "uh?"])])

(defn brief-booking-info [state]
  (let [{:keys [sleepover start end]} (-> @state :values)
        slot (try
               (tick.alpha.interval/bounds
                 (-> @state :values :start)
                 (-> @state :values :end))
               (catch js/Error _ nil))
        h (when slot (t/hours (t/duration slot)))]
    [:div (when slot (format "Fra %s til %s %s, %s"
                             (t/format (t/formatter "d/M-yy hh:mm") start)
                             (t/format (t/formatter "d/M-yy hh:mm") end)
                             (if sleepover "(1 overnatting)")
                             (apply str (cond-> []
                                          (< 24 h) (conj "dager")))))]))

(def this-color-map
  {:bg  [:bg-gray-100 :dark:bg-gray-700]
   :bgp [:bg-gray-200 :dark:bg-gray-700]
   :bg2 [:bg-gray-100 :dark:bg-gray-800]
   :bg3 [:bg-gray-300 :dark:bg-gray-800]})

(defn confirmation [_props {:keys [state selected boat-db] :as m}]
  (let [;intent want a copy of selected, not a reference
        presented (r/atom @selected)
        cm this-color-map]

    (fn [{:as props} {:keys [state selected boat-db] :as m}]
      (let [{:keys [start end]} (some-> state deref :values)
            slot (try
                   (tick.alpha.interval/bounds start end)
                   (catch js/Error _ nil))]
        [:div.flex.flex-col.flex-1
         ;fixme Fudging, to keep statusbar at bottom at all times
         {:class (cm :bg3)
          :style {:min-height "calc(100vh - 12.8rem)"}}
         [:div.space-y-4
          (if (seq @selected)
            (when slot
              ;todo a list where the elements remain when removed (but stay disabled) so you can undo removal
              (let [offset (times.api/day-number-in-year start)
                    not-available (into #{} (filter #(overlapping? % slot offset) (set/union @selected @presented)))]
                [:div.space-y-2
                 {:class (cm :bg3)}
                 [booking-list-item
                  {:boat-db      boat-db
                   :appearance   #{:basic}
                   :insert-below (fn [] [:div.px-4.pb-4 (fields/textarea
                                                          (fields/full-field props)
                                                          "Beskjed til tur-kamerater"
                                                          :description)])}
                  {:selected  (set/difference @selected not-available)
                   :offset    offset
                   :time-slot slot
                   :start     start
                   :end       end
                   :navn      (:display-name @(rf/subscribe [:db.core/user-auth]))}]

                 (when-not (empty? not-available)
                   [:<>
                    [:div.px-4.space-y-1
                     [:div.font-semibold.text-base "Noe utstyr er ikke tilgjengelig"]
                     [:div.text-sm.text-gray-500 "Du kan endre tidspunktet for din booking for å gjøre disse tilgjengelige"]]
                    [pick-list
                     (conj m
                           {:insert-before (fn [id] (hov/toggle-selected' {:not? true #_(some #{id} @presented)}))
                            :insert-after  hov/open-details
                            ;todo: Make something that controls how list-line will appear
                            :appearance    #{:extra :unavailable :timeline}
                            :offset        offset
                            :time-slot     slot
                            :boat-db       boat-db
                            :items         not-available #_(set/union @selected @presented)
                            :on-click      (fn [e] (swap! presented
                                                          (fn [sel] (if (some #{e} sel)
                                                                      (set/difference sel #{e})
                                                                      (set/union sel #{e})))))})]])])))]]))))

(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))
        detail-level @(rf/subscribe [:app/details])
        graph? (< detail-level 1)
        details? (= detail-level 2)
        compact? (= detail-level 0)]
    [fork/form {:initial-values    {:start       (t/at (t/date "2022-01-17") (t/time "09:00"))
                                    :end         (t/at (t/date "2022-01-19") (t/time "21:00"))
                                    :sleepover   true
                                    :description "Ugh"}
                :state             my-state
                :prevent-default?  true
                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         #(on-submit (-> %
                                                   (assoc-in [:values :uid] uid)
                                                   (assoc-in [:values :selected] @selected)
                                                   :values))}
     (fn [{:keys [form-id handle-submit] :as props}]
       [:form
        {:id        form-id
         :on-submit handle-submit}
        [booking.time-navigator/time-navigator {:my-state my-state} props]

        ;intent two states
        (rs/match-state booking-state
          [:s.booking :s.basic-booking-info]
          [boat-picker
           props
           (conj main-m
                 {:my-state my-state
                  :compact? compact?
                  :graph?   graph?
                  :details? details?})]

          [:s.booking :s.confirm]
          [confirmation
           props
           (conj
             {:compact? compact?
              :graph?   graph?
              :details? details?}
             {:slot     nil
              :boat-db  boat-db
              :selected selected
              :state    my-state})]

          [:div "d?" booking-state])

        [:div.flex.items-center.sticky.bottom-0.xh-16.panelx.p-4
         {:class "bg-gray-400/90"
          :style {:box-shadow "rgba(0, 0, 0, 0.35) 0px 5px 15px"}}

         (when (= booking-state [:s.booking :s.confirm]))
         [:div]

         [rs/match-state booking-state
          [:s.booking :s.confirm]
          [:div.flex.justify-between.w-full.items-center
           (push-button #(send :e.pick-boat) nil "Forrige")
           [:button.btn.btn-free.xh-10.btn-ctax
            {:disabled (empty? @selected)
             :type     :submit}
            "Siste"
            #_"Bekreft booking"]]

          [:s.booking :s.basic-booking-info]
          [:div.flex.justify-between.w-full.items-center
           [:div]
           (push-button #(send :e.confirm) nil "Neste")]]]])]))

(defn- booking-list-item [{:keys [offset accepted-user? today hide-name? on-click
                                  insert-top-fn
                                  insert-before
                                  insert-after
                                  insert-below
                                  appearance
                                  time-slot
                                  boat-db]
                           :or   {today (t/new-date)}}
                          {:keys [navn sleepover description selected start end] :as item}]
  (let [details @(rf/subscribe [:app/details])
        relation (try (tick.alpha.interval/relation start today)
                      (catch js/Error _ nil))
        {:keys [bg fg fg-]} (booking.views.picker/booking-list-item-color-map relation)]
    (let [navn (or navn "skult navn")
          day-name (times.api/day-name (t/date-time start))
          checkout-time start
          multiday (or sleepover (not (t/= (t/date (t/date-time start)) (t/date (t/date-time end)))))]

      [:div.grid
       {:style    {:grid-template-columns "1fr min-content"}
        :class    (concat bg)
        :on-click #(when on-click (on-click item))}

       (when insert-top-fn
         [:div (insert-top-fn item)])
       [:div.flex
        (when insert-before insert-before)
        [:div.grid.gap-2.w-full.p-4
         {:class (concat bg fg)
          :style {:grid-template-columns "1.5rem 1fr min-content max-content 3rem"
                  :grid-auto-rows        ""}}

         ;[:div.col-span-5 relation]
         [:div.truncate.col-span-2
          (when-not hide-name? #_(and hide-name? (not book-for-andre))
            [:div.truncate {:class fg-} navn])]

         [:<>
          [:div.justify-self-end.whitespace-nowrap (t/format (str "'" day-name " 'd.MM") (t/date-time start))]
          [:div.debug.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time checkout-time)))]]

         [:div.self-center.justify-self-end
          (when multiday
            [icon/small :moon-2])]

         [:div.col-span-3]

         [:div.debug.whitespace-nowrap.self-start (t/format "'kl.' H.mm" (t/time (t/date-time end)))]

         [:div.self-start.justify-self-end
          (when multiday
            (t/format "d.MM" (t/date (t/date-time end))))]

         (case details
           0 [:div.col-span-5
              [:<>]]
           1 (when-not (empty? selected)
               [:div.col-span-5
                [:div.flex.gap-1.justify-start.line-clamp-2
                 {:class fg}
                 (for [id selected
                       :let [data (get (into {} boat-db) id)]
                       :while (some? data)]
                   [:div.mr-1.mb-1.inline-block
                    [:div.bg-white.px-1.py-px
                     (number-view (:number data))]])]])
           2 (when-not (empty? selected)
               [:div.col-span-5
                [:div.space-y-px
                 {:class [:first:rounded-t :overflow-clip :last:rounded-b]}
                 (for [id selected
                       :let [data (get (into {} boat-db) id)]
                       :while (some? data)]
                   [list-line {:id         id
                               :data       data
                               :offset     offset
                               :time-slot  time-slot
                               :appearance #{:basic :clear}
                               :overlap?   false}])]])
           [:<>])

         (if (or (some #{:description} appearance) (< 1 details))
           (if (some? description)
             [:div.debug.col-span-3.text-sm {:class fg} description]
             [:<>]))]]

       (when insert-below
         [:div.col-span-3 (insert-below)])

       (when accepted-user?
         (when (and insert-after (fn? insert-after))
           (insert-after (:id item))))])))

(defn booking-list [{:keys [uid today booking-data accepted-user? class boat-db]}]
  (r/with-let [show-all (r/atom false)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          data (->> booking-data
                    (filter (comp (partial booking.views.picker/after-and-including today) val))
                    (sort-by (comp :start val) <))]
      [:div
       (into [:div.space-y-px.shadow
              {:class class}]
             (map (fn [[k item]]
                    (let [idx (:id item)]
                      [booking-list-item
                       {:boat-db        boat-db
                        :accepted-user? accepted-user?
                        :today          today
                        :hide-name?     (not (some? uid))
                        :on-click       (fn [e]
                                          (swap! markings update idx (fnil not false))
                                          (.stopPropagation e))
                        :insert-before  (when @edit
                                          [:div.flex.items-center.px-2.bg-gray-400
                                           [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                             :handle-change #(swap! markings update idx (fnil not false))}
                                            "" nil]])
                        :insert-after   hov/open-booking-details-button}
                       item]))
                  data))
       [general-footer
        {:insert-before (fn []
                          [:div (schpaa.components.tab/tab
                                  (conj schpaa.components.tab/select-bar-bottom-config
                                        {:selected @(rf/subscribe [:app/details])
                                         :select   #()})
                                  [0 "S" #(rf/dispatch [:app/set-detail 0])]
                                  [1 "M" #(rf/dispatch [:app/set-detail 1])]
                                  [2 "L" #(rf/dispatch [:app/set-detail 2])])])
         :data          data
         :key-fn        key
         :edit-state    edit
         :markings      markings
         :c             c}]])))

