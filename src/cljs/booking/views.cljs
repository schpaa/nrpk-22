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
        (update :end-time (fnil conj []) "Slutt-tid må komme før start-tid")

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
  {:bg  [:bg-gray-100 :dark:bg-gray-700]
   :bgp [:bg-gray-200 :dark:bg-gray-700]
   :bg2 [:bg-gray-100 :dark:bg-gray-800]
   :bg3 [:bg-gray-300 :dark:bg-gray-800]})

(defn confirmation [_props {:keys [state selected boat-db] :as m}]
  (let [;intent want a copy of selected, not a reference
        presented (r/atom @selected)
        cm this-color-map
        clicks-on-remove (r/atom {})]

    (fn [{:keys [values] :as props} {:keys [state selected boat-db] :as m}]
      (let [start (try (t/at (t/date (:start-date values)) (t/time (:start-time values))) (catch js/Error _ nil))
            end (try (t/at (t/date (:end-date values)) (t/time (:end-time values))) (catch js/Error _ nil))
            ;{:keys [start end]} (some-> state deref :values)
            slot (try
                   (tick.alpha.interval/bounds start end)
                   (catch js/Error _ nil))]
        [:div.flex.flex-col.flex-1
         ;fixme Fudging, to keep statusbar at bottom at all times
         {:class (cm :bg3)
          :style {:min-height "calc(100vh - 12.8rem)"}}
         [l/ppre @clicks-on-remove]
         [:div.space-y-4
          (if-not (empty? @selected)
            (when slot
              ;todo a list where the elements remain when removed (but stay disabled) so you can undo removal
              (let [offset (times.api/day-number-in-year start)
                    not-available (into #{} (filter #(overlapping? % slot offset) (set/union @selected @presented)))]
                [:div.space-y-2
                 {:class (cm :bg3)}
                 [booking-list-item
                  {:boat-db      boat-db
                   :appearance   #{:basic :remove}

                   :time-slot    slot
                   :offset       offset
                   :insert-below (fn [] [:div.px-4.pb-4 (fields/textarea
                                                          (fields/full-field props)
                                                          "Beskjed til tur-kamerater"
                                                          :description)])}
                  {:selected                @selected       ;(set/difference @selected not-available)
                   :not-available           not-available
                   :start                   start
                   :end                     end
                   :navn                    (:display-name @(rf/subscribe [:db.core/user-auth]))
                   ;note For each line-item

                   ;(let [already? (get @clicks-on-remove id)]
                   ;  (if already?
                   ;    (swap! clicks-on-remove update id inc)
                   ;    (reset! clicks-on-remove {id 1})))

                   :insert-before-line-item (fn [id]
                                              ;todo click twice to remove
                                              [:div.flex.items-center
                                               [:div.xbtn.btn-narrowx.outline-none.focus:outline-none.border-none
                                                {:class    (if (pos? (get @clicks-on-remove id)) [:btn-danger] [:btn-free])
                                                 :on-click (fn [] (if (pos? (get @clicks-on-remove id))
                                                                    (do (swap! selected set/difference  #{id})
                                                                        (reset! clicks-on-remove {}))
                                                                    (reset! clicks-on-remove {id 1})))} (icon/small :cross-out)]])}]

                 (when-not (empty? not-available)
                   [:<>
                    [:div.px-4.space-y-1
                     [:div.font-semibold.text-base "Merk deg at noe utstyr er ikke tilgjengelig!"]
                     [:div.text-base.text-gray-500 "Ved å endre tidspunktet for din booking kan du gjøre disse tilgjengelige."]]
                    #_[pick-list
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
                                                                        (set/union sel #{e})))))})]])]))
            [:div "Nothing selected"])]]))))

(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [admin false
        booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))
        detail-level @(rf/subscribe [:app/details])
        graph? (< detail-level 1)
        details? (= detail-level 2)
        compact? (= detail-level 0)]
    [fork/form {
                :initial-touched   {:start       (t/truncate (t/date-time) :hours) #_(t/at (t/date "2022-01-17") (t/time "09:00"))
                                    :end         (t/truncate (t/date-time) :hours) #_(t/at (t/date "2022-01-19") (t/time "21:00"))

                                    :start-date  (t/date "2022-01-27")
                                    :start-time  (t/time "18:13" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                    :end-time    (t/time "23:00" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                    :end-date    (t/tomorrow)

                                    ;:sleepover   true
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
       [:form
        {:id        form-id
         :on-submit handle-submit}
        [:div.p-4.bg-amber-200.space-y-2.sticky.top-28
         ;[l/ppre errors]
         [:div.flex.gap-2
          [fields/date (-> props
                           fields/date-field
                           (assoc :handle-change #(let [v (-> % .-target .-value)
                                                        diff (if (values :sleepover) 1 0)
                                                        _ #_(comment
                                                              ;fixme There is a slight problem with this approach; when crossing month-boundaries, the start-date goes back to the first of the month while the end-date continues upwards. I think I'll just keep it as dumb as possible, since regular users wont see the end-date, they'll just relate to the sleepover-state. Users with higher access will ... (later)

                                                              (let [diff (when (every? (comp some? values) [:start-date :end-date])
                                                                           (let [end-date (values :end-date)]
                                                                             (try (some->> end-date
                                                                                           (tick.alpha.interval/new-interval (t/date v))
                                                                                           (t/duration)
                                                                                           (t/days)) (catch js/Error _ 0))))]))
                                                        (tap> [v diff])]

                                                    (set-values {:end-date (t/>> (t/date v) (t/new-period diff :days))})
                                                    (handle-change %)))) "start-date" :start-date]
          [fields/time (fields/time-field props) "start-time" :start-time]
          [fields/time (fields/time-field props) "end-time" :end-time]]
         (when admin [fields/date (fields/date-field props) "end-date" :end-date])
         [:label {:for "sleepover"} "Overnatting"]
         [:input.btn.btn-free                               ;.appearance-none
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

           #_#_:on-click #(if (values :sleepover)
                            (set-values {:sleepover false
                                         :end-date  (values :start-date)})
                            (set-values {:sleepover true
                                         :end-date  (t/>> (t/date (values :start-date)) (t/new-period 1 :days))}))
           :class     (if (values :sleepover) [:bg-black :text-gray-200] [:bg-gray-200 :text-black])}]

         #_[:div
            {:class (if (nil? errors) :text-black :text-red-500)}
            [:div (l/ppr (try (t/at (t/date (values :start-date)) (t/time (values :start-time))) (catch js/Error _ "missing pieces")))]
            [:div (l/ppr (try (t/at (t/date (values :end-date)) (t/time (values :end-time))) (catch js/Error _ "missing pieces")))]]]

        [booking.time-navigator/time-navigator {:my-state my-state
                                                :selected selected} props]

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


        #_[:div.flex.items-center.sticky.bottom-0.xh-16.panelx.p-4
           {:class "bg-gray-400/90"
            :style {:box-shadow "rgba(0, 0, 0, 0.35) 0px 5px 15px"}}

           (when (= booking-state [:s.booking :s.confirm]))
           [:div]

           #_[:div "FOOTER"]
           #_[rs/match-state booking-state
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
                          ;intent FOR EACH ITEM
                          {:keys [navn sleepover insert-before-line-item description selected not-available start end] :as item}]
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
        ;(when insert-before insert-before)
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

         [:div "details:" details]
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
                           {:insert-before-line-item (when insert-before-line-item
                                                       insert-before-line-item)
                            :id                      id
                            :data                    data
                            :offset                  offset
                            :time-slot               time-slot
                            :appearance              (set/union #{:basic :clear} (if (some #{id} not-available) #{:timeline :error}))
                            :overlap?                false}]))]])
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

