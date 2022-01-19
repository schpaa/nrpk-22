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
            [booking.views.picker :refer [boat-picker list-line]]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

;INTENT our desired timeslot, who is available in this slow?

(defn pick-list [{:keys [offset slot selected on-click boat-db details? graph?] :as m}]
  (if (seq @selected)
    [:div.space-y-px
     {:class [:overflow-clip
              :first:rounded-t
              :last:rounded-b]}
     (for [id @selected]
       [list-line
        (conj m
              {:time-slot slot
               :remove?   true
               :on-click  #(on-click id)
               :id        id
               :data      (get boat-db id)})])]

    [:div.h-12.flex.items-center.justify-center.bg-white "Velg båt"]))

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

(defn confirmation [{:as props} {:keys [state selected boat-db] :as m}]
  (let [{:keys [sleepover start end]} (some-> state deref :values)
        slot (try
               (tick.alpha.interval/bounds start end)
               (catch js/Error _ nil))]
    [:div.flex.flex-col.flex-1
     ;fixme Fudging, to keep statusbar at bottom at all times
     {:style {:min-height "calc(100vh - 12.8rem)"}}
     [:div.space-y-4.p-4.text-xl
      (when slot (format "Fra %s til %s %s"
                         (t/format (t/formatter "d/M-yy hh:mm") start)
                         (t/format (t/formatter "d/M-yy hh:mm") end)
                         (if sleepover "(overnatter)")))
      (when slot
        ;todo a list where the elements remain when removed (but stay disabled) so you can undo removal
        [pick-list
         (conj m
               {:offset   (day-number-in-year start)
                :slot     slot
                :boat-db  (into {} boat-db)
                :selected selected
                :on-click #(swap! selected disj %)})])
      (fields/textarea
        (fields/full-field props)
        "Beskjed til tur-kamerater"
        :description)]]))

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
       [:form.bg-gray-300
        {:id        form-id
         :on-submit handle-submit}

        #_[views/rounded-view {:flat 1}
           (navigation booking-state my-state)]

        ;intent two states
        (rs/match-state booking-state
          [:s.booking :s.basic-booking-info]
          [boat-picker (conj main-m {:my-state my-state
                                     :compact? compact?
                                     :graph?   graph?
                                     :details? details?}) props]

          [:s.booking :s.confirm]
          [confirmation props
           (conj
             {:compact? compact?
              :graph?   graph?
              :details? details?}
             {:slot     nil
              :boat-db  boat-db
              :selected selected
              :state    my-state})]

          [:s.booking :s.initial]
          [:div "initial"]

          [:div "d?" booking-state])

        [:div.flex.items-center.sticky.bottom-0.xh-16.panelx.p-4 ;.border-t.border-gray-500.shadow-xl
         {:class "bg-gray-400/90"
          :style {:box-shadow "rgba(0, 0, 0, 0.35) 0px 5px 15px"}}

         (when (= booking-state [:s.booking :s.confirm]))
         [:div                                              ;.flex.justify-between.w-full.items-center
          #_[:div.select-none.font-bold.px-2
             {:on-click #(rf/dispatch [:app/next-detail])} (inc @(rf/subscribe [:app/details]))]]

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

(defn- booking-list-item [{:keys [accepted-user? today hide-name? insert-before insert-top-fn on-click insert-after]
                           :or   {today (t/new-date)}} item]
  (let [{:keys [navn book-for-andre sleepover description selected start end]} item
        relation' (relation start today)
        color-map (booking.views.picker/booking-list-item-color-map relation')]
    (let [navn (or navn "skult navn")
          day-name (times.api/day-name (t/date-time start))
          checkout-time start
          multiday (or sleepover (not (t/= (t/date (t/date-time start)) (t/date (t/date-time end)))))]

      [:div.grid
       {:style {:grid-template-columns "1fr min-content"}
        :class    (concat
                    (:bg color-map)
                    #_[:first:rounded-t :overflow-hidden])
        :on-click #(when on-click
                     (on-click item))}
       (when insert-top-fn
         [:div (insert-top-fn item)])
       [:div.flex
        ;{:class (:fg- color-map)}
        (when insert-before insert-before)
        [:div.grid.gap-y-2.gap-x-1.w-full.p-2
         {:style {:grid-template-columns "min-content min-content min-content min-content  min-content min-content 1fr"
                  :grid-auto-rows        ""}}
         [:div.uppercase.w-10.text-center.self-center (slot-view day-name)]

         [:<>
          [:div.debug.whitespace-nowrap (t/format "dd/MM" (t/date-time start))]
          [:div.debug (str (t/time (t/date-time checkout-time)))]]
         [:div.w-10.debug.self-center [icon/small (if multiday :moon-2 :minus)]]
         (try
           (if multiday
             [:<>
              [:div.debug.w-12 (str (t/time (t/date-time end)))]
              [:div.debug.w-12 (t/format "dd/MM" (t/date (t/date-time end))) " "]]
             [:<>
              [:div.debug.w-12 (str (t/time (t/date-time end)))]
              [:div.w-12]])
           (catch js/Error e (.-message e)))

         [:div.debug.truncate
          (when-not hide-name? #_(and hide-name? (not book-for-andre))
            [:div.flex.justify-end.truncate {:class (:fg- color-map)} navn])]

         [:div]
         [:div.debug.col-span-5.text-sm {:class (:fg color-map)} description]

         [:div.col-span-1.debug.w-full.text-right.truncate
          {:class (:fg color-map)}
          (number-view (first selected))]]]

       ;(open-details 1)
       (when accepted-user?
         (when (and insert-after (fn? insert-after))
           (insert-after nil)))])))

(defn open-details [id]
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-200"]
    :on-click #(schpaa.modal.readymade/details-dialog-fn id)}
   [icon/small :three-vertical-dots]])

(defn booking-list [{:keys [uid today data accepted-user? class]}]
  (r/with-let [show-all (r/atom false)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          my-data data
          data (cond->> (->> my-data
                             (filter (partial booking.views.picker/after-and-including today))
                             (sort-by :start <))
                        (not @show-all) (take 10))]
      [:div
       (into [:div.space-y-px.shadow
              {:class class}]
             (map (fn [e]
                    (let [idx (:id e)]
                      [booking-list-item
                       {:accepted-user? accepted-user?
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
                        :insert-after   open-details} e]))
                  data))

       [general-footer
        {:accepted-user? accepted-user?
         :data           data
         :show-all       show-all
         :key-fn         :id
         :edit-state     edit
         :markings       markings
         :c              c}]])))
