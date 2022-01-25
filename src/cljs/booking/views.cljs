(ns booking.views
  (:require [re-statecharts.core :as rs]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :as views :refer [goto-chevron general-footer
                                                       number-view
                                                       slot-view]]
            [schpaa.modal.readymade :refer [details-dialog-fn]]
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
            [booking.time-navigator :refer [step]]
            [schpaa.icon :as icon]
            [db.core]
            [schpaa.debug :as l]
            [eykt.hov :as hov]
            [clojure.set :as set]
            [times.api :refer [day-number-in-year day-name]]))

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

(comment
  (do
    (some #{(tick.alpha.interval/relation (t/time) (t/time))} [:meets])))


(comment
  (do
    (let [t1 (t/time)
          t2 (t/>> t1 (t/new-duration 1 :minutes))]
      (and
        (= :precedes (relation t1 t2))
        (< (t/hours (t/duration (tick.alpha.interval/new-interval t1 t2))) 1)))))



(defn booking-validation [{:keys [start-date start-time end-date end-time sleepover]}]
  (try
    (let []
      (tap> {:start-date (some? start-date)})
      (cond-> {}
        (not (some? start-date))
        (update :start-date (fnil conj []) "Trenger start-dato")

        (and
          (every? some? [start-date end-date])
          (some #{(relation (t/date end-date) (t/date start-date))} [:precedes :meets]))
        (update :start-date (fnil conj []) "Start-dato må komme før slutt-dato")

        (and
          (every? some? [start-date end-date])
          (some #{(relation (t/date end-date) (t/date start-date))} [:precedes :meets]))
        (update :end-date (fnil conj []) "Slutt-dato må komme etter start-dato")

        (and
          (some? end-date)
          (some #{(relation (t/date end-date) (t/date))} [:precedes :meets]))
        (update :end-date (fnil conj []) "Slutt-dato må være i dag eller senere")

        (and (every? some? [start-date])
             (not= "" (str start-date))
             (some #{(relation (t/date start-date) (t/date))} [:precedes :meets]))
        (update :start-date (fnil conj []) "Start-dato må være i dag eller senere")

        (and (every? some? [start-date start-time end-time end-date])
             (let [rel (relation (t/at (t/date start-date) (t/time start-time)) (t/date-time))]
               (some #{rel} [:precedes :meets])))
        (update :start-time (fnil conj []) "Start-tid må være nå eller senere")

        (= "" (str start-time))
        (update :start-time (fnil conj []) "Trenger start-tid")

        (= "" (str end-time))
        (update :end-time (fnil conj []) "Trenger slutt-tid")

        (and
          (not sleepover)
          (every? some? [start-date start-time end-time end-date])
          (some #{(relation (t/at (t/date end-date) (t/time end-time))
                            (t/at (t/date start-date) (t/time start-time)))} [:precedes]))
        (update :end-time (fnil conj []) "Slutt-tid må komme etter start-tid")

        #_#_(and
              (every? some? [start-time end-time])
              (< (t/hours (tick.alpha.interval/new-interval (t/time end-time) (t/time start-time))) 1))
            (update :end-time (fnil conj []) "Start og slutt er likt1")

        (let [t1 (t/at (t/date start-date) (t/time start-time))
              t2 (t/at (t/date end-date) (t/time end-time))]
          (and
            (= :precedes (relation t1 t2))
            (< (t/hours (t/duration (tick.alpha.interval/new-interval t1 t2))) 1)))
        (update :end-time (fnil conj []) "Minste tid for booking er 1 time")

        (and
          (every? some? [start-time end-time start-date end-date])
          (= :meets (some #{(relation (t/at (t/date end-date) (t/time end-time))
                                      (t/at (t/date start-date) (t/time start-time)))} [:meets])))
        (update :end-time (fnil conj []) "Start-tid og slutt-tid er like")))
    (catch js/Error e
      (tap> (.-message e))
      {:general [(.-message e)]})))

(comment
  (booking-validation {:start-time (t/time) :end-time (t/time)}))


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
  {:bg  [:bg-gray-400 :dark:bg-gray-700]
   :bgp [:bg-gray-200 :dark:bg-gray-700]
   :bg2 [:bg-gray-100 :dark:bg-gray-800]
   :bg3 [:bg-gray-300 :dark:bg-gray-800]})


(defn confirmation [_ {:keys [selected]}]
  (let [;intent want a copy of selected, not a reference
        presented (r/atom @selected)
        clicks-on-remove (r/atom {})]

    (fn [{:keys [values] :as props} {:keys [selected boat-db]}]
      (let [start (try (t/at (t/date (:start-date values)) (t/time (:start-time values))) (catch js/Error _ nil))
            end (try (t/at (t/date (:end-date values)) (t/time (:end-time values))) (catch js/Error _ nil))
            slot (try
                   (tick.alpha.interval/bounds start end)
                   (catch js/Error _ nil))
            offset (times.api/day-number-in-year start)
            not-available (into #{} (filter #(overlapping? % slot offset) (set/union @selected @presented)))]
        [:div.flex.flex-col.flex-1
         ;fixme Fudging, to keep statusbar at bottom at all times
         {:class (this-color-map :bg)
          :style {:min-height "calc(100vh - 12.8rem)"}}
         [:div.space-y-4
          (if-not (empty? @selected)
            [:div.space-y-2
             [booking-list-item
              {:boat-db      boat-db
               :appearance   #{:basic :remove}
               :time-slot    slot
               :offset       offset
               :insert-above (fn [] [:div.p-4 (fields/textarea
                                                (fields/full-field props)
                                                "Beskrivelse (valgfritt)"
                                                :description)])}
              ;intent: for each item
              {:selected                @selected
               :not-available           not-available
               :start                   start
               :end                     end
               :navn                    (:display-name @(rf/subscribe [:db.core/user-auth]))
               :insert-before-line-item (hov/remove-from-list-actions clicks-on-remove selected)}]])

          [:div.xp-4.space-y-px
           (when (some not-available @selected)
             [:div.p-2.space-y-1.bg-rose-600.text-white
              [:div.font-semibold.text-xl "OBS"]
              [:div.text-base.space-y-2
               [:div "Noe av utstyret du har valgt er ikke tilgjengelig på det tidspunktet du ønsker!"]
               [:div "Tilpass tidspunktet for din booking eller fjern utstyret fra listen."]]])
           (for [[k v] (:errors props)
                 e v]
             [:div.text-white.bg-rose-600.p-2  e])]]]))))

(defn stupid-bar [{:keys [selected not-available booking-state] :as props} s]
  [:<>
   [step 2 "Velg utstyr"
    :active (= s 1)
    :complete (not (empty? @selected))
    :on-click #(eykt.fsm-helpers/send :e.pick-boat)]
   [step 3 "Bekreftelse"
    :active (= s 2)
    :final true
    :complete (and (nil? (:errors props)) (nil? (some not-available @selected)))
    :on-click #(eykt.fsm-helpers/send :e.confirm)]])

(defn booking-footer [{:keys [selected boat-db booking-ready?]}]
  [:div.flex.justify-between.items-centers.gap-2.px-4.py-2.sticky.bottom-0
   {:class [:bg-gray-300]}
   [:button.btn-small.btn-free.h-8 {:type     :button
                                    :on-click #()} "detailjer"]
   [:div.flex.gap-2.items-centers.shrink-1
    [:button.btn-small.btn-free.h-8 {:type     :button
                                     :on-click #(reset! selected #{})} "ingen"]
    [:button.btn-small.btn-free.h-8 {:type     :button
                                     :on-click #(reset! selected (into #{} (keys boat-db)))} "alle"]]
   [:button.btn.btn-cta.grow-1 {:type     :button
                                :disabled (not booking-ready?)
                                :on-click #()} "Book nå!"]])

(defn main-2-tabs [{:keys [selected booking-ready? boat-db main-m] :as m} {:keys [state] :as props}]
  (let [booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))]
    [:<>
     [:div.sticky.top-60.z-50

      [:div.grid.grid-cols-2.xpy-4.xpx-4.xgap-x-4.gap-y-2.bg-gray-100.z-10.border-b.border-gray-400
       (rs/match-state booking-state
         [:s.booking :s.basic-booking-info]
         [stupid-bar m 1]
         [:s.booking :s.confirm]
         [stupid-bar m 2])]]

     (rs/match-state booking-state
       [:s.booking :s.basic-booking-info]
       [:<>
        [boat-picker
         props
         (conj main-m
               {:my-state state})]
        [booking-footer {:selected selected :boat-db boat-db :booking-ready? false #_booking-ready?}]]
       [:s.booking :s.confirm]
       [:<>
        [confirmation
         props
         {:slot     nil
          :boat-db  boat-db
          :selected selected
          :state    state}]
        [booking-footer {:selected selected :boat-db boat-db :booking-ready? booking-ready?}]]
       [:div "d?" booking-state])]))

(defn time-input [{:keys [errors form-id handle-submit handle-change values set-values] :as props} admin]
  [:div.p-4.bg-gray-100.space-y-2.sticky.top-28.z-50
   ;[booking.time-navigator/step 1 "Tidspunkt" :complete (nil? (:errors props))]
   [:div.grid.w-full.gap-2
    {:style {:grid-template-columns "1fr 1fr"}}
    [fields/date (-> props
                     booking.time-navigator/naked
                     fields/date-field
                     (assoc :handle-change #(let [v (-> % .-target .-value)
                                                  diff (if (values :sleepover) 1 0)]
                                              (set-values {:end-date (t/>> (t/date v) (t/new-period diff :days))})
                                              (handle-change %))))
     :name :start-date
     :label "Start dato"
     :error-type :marker]
    [fields/time (-> props
                     booking.time-navigator/naked
                     fields/time-field)
     :name :start-time
     :label "kl. ut"
     :error-type :marker]

    [:div.flex.items-center.gap-2
     (r/with-let [st (r/atom false)]
       [views/modern-checkbox st "Overnatting"])
     #_#_[:label {:for "sleepover"} "Overnatting"]
         [:input                                            ;.btn.btn-free
          {:type      :checkbox
           :id        "sleepover"
           :checked   (if (or
                            (when (every? (comp some? values) [:start-date :end-date])
                              (some #{(tick.alpha.interval/relation (values :start-date) (values :end-date))} [:precedes :meets]))
                            (values :sleepover))
                        true false)
           :name      :sleepover
           :on-change #(let [v (-> % .-target .-checked)]
                         (tap> v)
                         (if v
                           (set-values {:sleepover true
                                        :end-date  (t/>> (t/date (values :start-date)) (t/new-period 1 :days))})
                           (set-values {:sleepover false
                                        :end-date  (values :start-date)})))
           :class     (if (values :sleepover) [:bg-black :text-gray-200] [:bg-gray-200 :text-black])}]]

    [fields/time (-> props
                     booking.time-navigator/naked
                     fields/time-field)
     :name :end-time
     :label "kl. inn"
     :error-type :marker]]

   (when admin
     [fields/date (fields/date-field props)
      :name :end-date

      :error-type :marker])])

(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [admin false
        detail-level @(rf/subscribe [:app/details])]
    [fork/form {:initial-touched   {:start-date  (t/date "2022-01-26")
                                    :start-time  (t/time "08:00" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                    :end-time    (t/time "08:20" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                    :end-date    (t/date "2022-01-26")
                                    :description ""}
                :validation        booking-validation
                :state             my-state
                :prevent-default?  true
                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         #(on-submit (-> %
                                                   (assoc-in [:values :uid] uid)
                                                   (assoc-in [:values :selected] @selected)
                                                   :values))}
     (fn [{:keys [errors form-id handle-submit handle-change values set-values] :as props}]
       (let [presented (r/atom @selected)
             start (try (t/at (t/date (:start-date values)) (t/time (:start-time values))) (catch js/Error _ nil))
             end (try (t/at (t/date (:end-date values)) (t/time (:end-time values))) (catch js/Error _ nil))
             slot (try
                    (tick.alpha.interval/bounds start end)
                    (catch js/Error _ nil))
             offset (times.api/day-number-in-year start)
             booking-ready? (and (nil? errors) (not (empty? @selected)))
             not-available (into #{} (filter #(overlapping? % slot offset) (set/union @selected @presented)))]
         [:form
          {:id        form-id
           :on-submit handle-submit}
          [time-input props admin]
          [main-2-tabs {:my-state          my-state
                        :selected       selected
                        :main-m         main-m
                        :boat-db        boat-db
                        :booking-ready? booking-ready?
                        :not-available  not-available} props]]))]))

(defn time-segment-display [{:keys [hide-name? multiday navn start end relation]}]
  (let [day-name (times.api/day-name (t/date-time start))
        {:keys [bg fg fg-]} (booking.views.picker/booking-list-item-color-map relation)]
    [:div.grid.gap-2.w-full.p-4
     {:class (concat bg fg)
      :style {:grid-template-columns "1.5rem 1fr min-content max-content 3rem"
              :grid-auto-rows        ""}}
     [:div.truncate.col-span-2
      (when-not hide-name?
        [:div.truncate {:class fg-} navn])]
     [:<>
      [:div.justify-self-end.whitespace-nowrap (t/format (str "'" day-name " 'd.MM") (t/date-time start))]
      [:div.debug.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]]
     [:div.self-center.justify-self-end
      (when multiday
        [icon/small :moon-2])]
     [:div.col-span-3]
     [:div.debug.whitespace-nowrap.self-start (t/format "'kl.' H.mm" (t/time (t/date-time end)))]
     [:div.self-start.justify-self-end
      (when multiday
        (t/format "d.MM" (t/date (t/date-time end))))]]))

(defn- booking-list-item [{:keys [offset today hide-name? on-click
                                  insert-top-fn
                                  insert-before
                                  insert-after
                                  insert-below
                                  insert-above
                                  appearance
                                  time-slot
                                  boat-db]
                           :or   {today (t/new-date)}}
                          ;intent FOR EACH ITEM
                          {:keys [navn sleepover insert-before-line-item description selected not-available start end] :as item}]
  (let [details @(rf/subscribe [:app/details])
        relation (try (tick.alpha.interval/relation start today)
                      (catch js/Error _ nil))
        {:keys [bg fg fg-]} (booking.views.picker/booking-list-item-color-map relation)
        navn (or navn "skult navn")
        multiday (or sleepover (not (t/= (t/date (t/date-time start)) (t/date (t/date-time end)))))]

    [:div.grid.w-full
     {:style    {:grid-template-columns "min-content 1fr min-content"}
      :class    (concat bg)
      :on-click #(when on-click (on-click item))}
     (when insert-top-fn
       [:div.col-span-3 '(insert-top-fn item)])

     (when insert-above [:div.col-span-3 (insert-above)])
     [:<>
      [:div]
      [:div
       #_[time-segment-display {:start      start
                                :end        end
                                :relation   relation
                                :hide-name? hide-name?
                                :multiday   multiday
                                :navn       navn}]

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
               (doall (for [id selected
                            :let [data (get (into {} boat-db) id)]
                            :while (some? data)]
                        [list-line
                         {:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                          :insert-after  hov/open-details
                          :id                      id
                          :data                    data
                          :offset                  offset
                          :time-slot               time-slot
                          :appearance              (set/union #{:basic :clear :timeline} (if (some #{id} not-available) #{:error}))
                          :overlap?                false}]))]])
         [:<>])

       (if (or (some #{:description} appearance) (< 1 details))
         (if (some? description)
           [:div.debug.col-span-3.text-sm {:class fg} description]
           [:<>]))]

      [:div #_"BACK"]
      #_(when (and insert-after (fn? insert-after))
          (insert-after (:id item)))]
     (when insert-below
       [:div.col-span-3 (insert-below)])]))

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
