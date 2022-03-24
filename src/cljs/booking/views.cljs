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
            [nrpk.fsm-helpers :refer [send]]
            [logg.database]
            [booking.views.picker :as picker :refer [
                                                     boat-picker-footer
                                                     list-line
                                                     has-selection available? convert]]
            [booking.time-navigator :refer [step]]
            [schpaa.icon :as icon]
            [db.core]
            [schpaa.debug :as l]
            [nrpk.hov :as hov]
            [clojure.set :as set]
            [booking.bookinglist]
            [times.api :refer [day-number-in-year day-name]]
            [schpaa.style :as st]
            [schpaa.style.ornament :as sc]
            [schpaa.style.input :as sci]
            [schpaa.style.radio]
            [schpaa.style.hoc.page-controlpanel :as hoc.panel]))

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

;(defn booking-validation [{:keys [start-date start-time end-date end-time sleepover]}]
;  (try
;    (let []
;      (tap> {:start-date (some? start-date)})
;      (cond-> {}
;
;        (not (some? start-date))
;        (update :start-date (fnil conj []) "Trenger start-dato")
;
;
;        (update :end-date (fnil conj []) "Slutt-dato må komme etter start-dato")
;
;        (and (every? some? [start-date end-date])
;             ;(not= "" (str start-date))
;             (some #{(relation (t/date end-date) (t/date start-date))} [:precedes :meets]))
;        (update :end-date (fnil conj []) "Slutt-dato må være samme som start-dato eller senere")
;
;        (and
;          (some? end-date)
;          (some #{(relation (t/date end-date) (t/date))} [:precedes :meets]))
;        (update :end-date (fnil conj []) "Slutt-dato må være i dag eller senere")
;
;        (and (every? some? [start-date])
;             (not= "" (str start-date))
;             (some #{(relation (t/date start-date) (t/date))} [:precedes :meets]))
;        (update :start-date (fnil conj []) "Start-dato må være i dag eller senere")
;
;        (and (every? some? [start-date start-time end-time end-date])
;             (let [rel (relation (t/at (t/date start-date) (t/time start-time)) (t/date-time))]
;               (some #{rel} [:precedes :meets])))
;        (update :start-time (fnil conj []) "Start-tid må være nå eller senere")
;
;        (= "" (str start-time))
;        (update :start-time (fnil conj []) "Trenger start-tid")
;
;        (= "" (str end-time))
;        (update :end-time (fnil conj []) "Trenger slutt-tid")
;
;        (and
;          (not sleepover)
;          (every? some? [start-date start-time end-time end-date])
;          (some #{(relation (t/at (t/date end-date) (t/time end-time))
;                            (t/at (t/date start-date) (t/time start-time)))} [:precedes]))
;        (update :end-time (fnil conj []) "Slutt-tid må komme etter start-tid")
;
;        #_#_(and
;              (every? some? [start-time end-time])
;              (< (t/hours (tick.alpha.interval/new-interval (t/time end-time) (t/time start-time))) 1
;                 (update :end-time (fnil conj []) "Start og slutt er likt1")))
;
;                (let [t1 (t/at (t/date start-date) (t/time start-time))
;                      t2 (t/at (t/date end-date) (t/time end-time))]
;                  (and
;                    (= :precedes (relation t1 t2))
;                    (< (t/hours (t/duration (tick.alpha.interval/new-interval t1 t2))) 1)))
;        (update :end-time (fnil conj []) "Minste tid for booking er 1 time")
;
;        (and
;          (every? some? [start-time end-time start-date end-date])
;          (= :meets (some #{(relation (t/at (t/date end-date) (t/time end-time))
;                                      (t/at (t/date start-date) (t/time start-time)))} [:meets])))
;        (update :end-time (fnil conj []) "Start-tid og slutt-tid er like")))
;    (catch js/Error e
;      (tap> (.-message e))
;      {:general [(.-message e)]})))

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
   :bg2 [:bg-gray-50 :dark:bg-gray-800]
   :bg3 [:bg-gray-300 :dark:bg-gray-800]})

(defn error-item [text]
  (let [{:keys [bg fg+]} (st/fbg' :error)]
    [:div.p-2.space-y-1.flex.gap-4.items-baseline
     {:class (concat fg+ bg)}
     [:div.rounded-full.bg-rose-600.text-white.h-6.aspect-square.flex.items-center.justify-center "!"]
     [:div text]]))

(defn confirmation [_ {:keys [selected]}]
  (let [;intent want a copy of selected, not a reference
        {:keys [bg bg+ fg+]} (st/fbg' :void)
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
         {:class bg
          :style {:min-height "calc(100vh - 22rem)"}}
         [:div.space-y-4
          (if-not (empty? @selected)
            [:div.space-y-px
             (let [appearance #{:tall :timeline}]
               (doall (for [[id data] (sort-by (comp :number val) < boat-db)
                            :when (some #{id} @selected)
                            :while (some? data)]
                        [list-line
                         {:insert-after            (hov/remove-from-list-actions clicks-on-remove selected)
                          :insert-before-line-item hov/open-details
                          :id                      id
                          :data                    data
                          :offset                  offset
                          :time-slot               slot
                          :appearance              (set/union #{:basic} appearance)
                          :overlap?                (some #{id} not-available)}])))])

          ;intent ERRORS
          [:div.space-y-px
           (when (empty? @selected)
             (error-item "Ingen båter er valgt for booking!"))

           (when (some not-available @selected)
             (error-item
               [:div.space-y-1
                [:div.font-semibold.text-xl "OBS"]
                [:div "Noe av utstyret du har valgt er ikke tilgjengelig på det tidspunktet du ønsker!"]
                [:div "Tilpass tidspunktet for din booking eller fjern utstyret fra listen."]]))
           (for [[k v] (:errors props)
                 e v]
             (when-not (= k :general) (error-item e)))]]]))))

(defn stupid-bar [{:keys [selected not-available booking-state] :as props} s]
  [:<>
   [step 2 "Velg utstyr"
    :active (= s 1)
    :complete (not (empty? @selected))
    :on-click #(nrpk.fsm-helpers/send :e.pick-boat)]
   [step 3 "Bekreftelse"
    :active (= s 2)
    :final true
    :complete (and (nil? (:errors props)) (nil? (some not-available @selected)))
    :on-click #(nrpk.fsm-helpers/send :e.confirm)]])

(defn booking-footer [{:keys [selected boat-db booking-ready? booking-record]}]
  [:div.flex.justify-between.items-center.gap-2.px-4.py-2.sticky.bottom-0
   {:class [:bg-gray-400 :dark:bg-gray-800 :dark:text-white :text-black]}
   [:div.flex.gap-2.items-centers.shrink-1
    [:button.btn-small.btn-free.h-10.px-2 {:type     :button
                                           :disabled (empty? @selected)
                                           :on-click #(reset! selected #{})} "Ingen"]
    [:button.btn-small.btn-free.h-10.px-2 {:type     :button
                                           :on-click #(reset! selected (into #{} (keys boat-db)))} "Alle"]]
   (when (pos? (count @selected)) [:div (count @selected)])
   [:button.btn.btn-cta {:type     :button
                         :disabled (not booking-ready?)
                         :on-click #(schpaa.modal.readymade/confirm-booking booking-record)} "Book nå!"]])

(rf/reg-sub :bookinglist/details :-> :booking-list-details)

(rf/reg-sub :bookinglist/personal :-> :booking-list-personal)

(rf/reg-event-db :bookinglist/set-details (fn [db [_ args]]
                                            (assoc db :booking-list-details args)))

(rf/reg-event-db :bookinglist/set-personal (fn [db [_ args]]
                                             (assoc db :booking-list-personal args)))

(defn last-bookings-footer [{:keys [selected boat-db booking-ready?]}]
  (let [{:keys [bg fg fg+ p p-]} (st/fbg' :surface)]
    [:div.flex.justify-between.items-center.gap-x-2.px-4.sticky.bottom-0.h-16.border-t.border-gray-500
     {:class bg}
     (schpaa.components.views/modern-checkbox'
       {:set-details #(rf/dispatch [:bookinglist/set-details %])
        :get-details #(-> (rf/subscribe [:bookinglist/details]) deref)}
       (fn [checkbox]
         [:div.flex.items-center.gap-2
          checkbox
          [:div.space-y-0
           [:div {:class (concat p fg+)} "Detaljer"]
           [:div.hidden.xs:block {:class (concat p- fg)} "Vis alle detaljer"]]]))

     (schpaa.components.views/modern-checkbox'
       {:set-details #(schpaa.state/change :opt/show-only-my-own %)
        :get-details #(-> (schpaa.state/listen :opt/show-only-my-own) deref)}
       (fn [checkbox]
         [:div.flex.items-center.gap-2.w-full
          [:div.space-y-0
           [:div.text-right {:class (concat p fg+)} "Mine"]
           [:div.hidden.xs:block {:class (concat p- fg)} "Bare vis mine bookinger"]]
          checkbox]))]))

(defn main-2-tabs [{:keys [selected booking-ready? boat-db main-m] :as m} {:keys [state values] :as props}]
  (let [{:keys [bg bg+ fg fg- fg+ p p-]} (st/fbg' :surface)
        booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))]
    [:div
     [picker/boat-picker props (conj main-m {:my-state state})]
     [booking-footer {:booking-record {:start    (try (str (t/at (t/date (values :start-date)) (t/time (values :start-time)))) (catch js/Error _ nil))
                                       :end      (try (str (t/at (t/date (values :end-date)) (t/time (values :end-time)))) (catch js/Error _ nil))
                                       :selected @selected}
                      :selected       selected :boat-db boat-db :booking-ready? booking-ready?}]]))

(defn time-input [{:keys [set-handle-change errors form-id handle-submit handle-change values set-values] :as props} admin]
  (let [data [{:id   0
               :text #(str "Ingen")
               :fn   #(t/>> % (t/new-duration 0 :days))}
              {:id   1
               :text #(-> "1")
               :fn   #(try
                        (when % (t/>> % (t/new-period 1 :days)))
                        (catch js/Error _ nil))}
              {:id   2
               :text #(-> "2")
               :fn   #(when % (t/>> % (t/new-period 2 :days)))}]]

    [hoc.panel/togglepanel :booking/time-input "Dato for booking"
     (fn [] [:div.space-y-4
             [sc/row {:class [:gap-4 :flex-wrap]}
              [sc/col {:class [:w-40 :gap-1]}
               [sci/input (conj props {:type      "date"
                                       :value     (:start-date values)
                                       :on-change #(let [z (-> % .-target .-value)]
                                                     (do
                                                       (handle-change %)
                                                       (tap> {:id      (:id values)
                                                              "value"  %
                                                              "z"      z
                                                              "value'" (values :start-date)})
                                                       (let [v (if (seq z)
                                                                 (some-> z
                                                                         (t/date)
                                                                         ((:fn (get (zipmap (map :id data) data) (:id values))))
                                                                         str)
                                                                 nil)]
                                                         (tap> {"v" v})
                                                         (set-handle-change
                                                           {:path  :end-date
                                                            :value v}))))
                                       :errors    (:start-date errors)
                                       :name      :start-date}) :date [:w-40] "Fra dato" :start-date]]





              [sci/input props :time [:w-32] "Fra kl" :start-time]]


             [sc/row {:class [:gap-4 :flex-wrap]}

              [sc/col {:class [:gap-1]}
               [sc/label "Overnattinger"]
               [:div.w-48.space-y-2
                [schpaa.style.radio/radio-group-example
                 #(do
                    (tap> {:a %})
                    (set-values {:id %})
                    (let [v (if (seq (values :start-date))
                              (some-> (values :start-date)
                                      (t/date)
                                      ((:fn (get (zipmap (map :id data) data) %)))
                                      str))]
                      (tap> v)
                      (set-values {:end-date v})))

                 (try (some-> (values :start-date) t/date) (catch js/Error _ nil))
                 data]]]]
             [sc/row {:class [:gap-4 :flex-wrap]}
              [sci/input {:type      "date"
                          :read-only true
                          :value     (:end-date values)
                          :on-change handle-change
                          :errors    (:end-date errors)
                          :name      :end-date} :date [:w-40] "Til dato" :end-date]

              [sci/input props :time [:w-32] "Til kl" :end-time]]])]))


(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [admin false]
    [fork/form {:initial-touched   {:start-date  (str (t/new-date))
                                    :start-time  (str (t/truncate (t/>> (t/time) (t/new-duration 2 :hours)) :hours))
                                    ;todo adjust for 22-23
                                    :end-time    (str (t/truncate (t/>> (t/time) (t/new-duration 3 :hours)) :hours))
                                    :end-date    (str (t/new-date))
                                    :description ""}
                ;:validation        booking-validation
                :state             my-state
                :prevent-default?  true
                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         #(on-submit (-> %
                                                   (assoc-in [:values :uid] uid)
                                                   (assoc-in [:values :selected] @selected)
                                                   :values))}
     (fn [{:keys [errors form-id handle-submit values] :as props}]
       (let [presented (r/atom @selected)
             start (try (t/at (t/date (:start-date values)) (t/time (:start-time values))) (catch js/Error _ nil))
             end (try (t/at (t/date (:end-date values)) (t/time (:end-time values))) (catch js/Error _ nil))
             slot (try
                    (tick.alpha.interval/bounds start end)
                    (catch js/Error _ nil))
             offset (times.api/day-number-in-year start)
             booking-ready? (and (nil? errors) (not (empty? @selected)))
             not-available (into #{} (filter #(overlapping? % slot offset) (keys boat-db) #_@presented #_(set/union @selected @presented)))]
         [:div.p-4
          ;[l/ppre not-available]
          [:form
           {:id        form-id
            :on-submit handle-submit}
           [time-input props admin]
           [picker/boat-picker props (conj main-m {:not-available not-available
                                                   :my-state      my-state})]
           #_[booking-footer {:booking-record {:start    (try (str (t/at (t/date (values :start-date)) (t/time (values :start-time)))) (catch js/Error _ nil))
                                               :end      (try (str (t/at (t/date (values :end-date)) (t/time (values :end-time)))) (catch js/Error _ nil))
                                               :selected @selected}
                              :selected       selected :boat-db boat-db :booking-ready? booking-ready?}]]]))]))
