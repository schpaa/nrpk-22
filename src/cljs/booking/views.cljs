(ns booking.views
  (:require [re-statecharts.core :as rs]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [eykt.state :as state]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :as views]
    ;todo extract common functions and relocate
            [tick.core :as t']
            [tick.core :as t]
            [tick.alpha.interval :refer [relation]]
            [tick.locale-en-us]
            [booking.database]
            [fork.re-frame :as fork]
            [times.api :refer [format]]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(rf/reg-sub :app/accepted-user? (fn [db] false))

(defn day-number-in-year [dt]
  (let [year (t/int (t/year dt))
        month (t/int (t/month dt))]
    (+ (t/day-of-month dt)
       (reduce (fn [a m] (+ a (t/day-of-month (t/last-day-of-month (t/new-date year m 1)))))
               0
               (range 1 month)))))

(defn- day-name [wd]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week wd)))]
    (subs (nth day-names d) 0 2)))

(defn- straight-date [instant]
  (str
    (t'/day-of-month (t'/date instant))
    "/"
    (t'/int (t'/month (t'/date instant)))))

(defn- convert [dt]
  (when-let [dt (t/date-time dt)]
    (+ (* 24 (day-number-in-year (t/date dt)))
       (t/hour dt)
       #_(* 24 (f dt)))))

;;

(defn booking-list-item-color-map [relation]
  (case relation
    :preceded-by {:bg  ["dark:bg-gray-400" "bg-gray-300"]
                  :fg  ["dark:text-black" "text-black"]
                  :fg- ["dark:text-black/40" "text-black/40"]}

    :precedes {:bg  ["dark:bg-black/30" "bg-gray-300"]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}
    {:bg  ["dark:bg-sky-500" "bg-sky-200"]
     :fg  ["dark:text-black" "text-sky-600"]
     :fg- ["dark:text-white"]}))

(defn- booking-list-item [{:keys [accepted-user? today hide-name? insert-before insert-top-fn on-click insert-after]
                           :or   {today (t/new-date)}} item]
  (let [{:keys [navn book-for-andre sleepover description selected start end]} item
        relation' (relation start today)
        color-map (booking-list-item-color-map relation')]
    (let [navn (or navn "skult navn")
          day-name (day-name (t/date-time start))
          checkout-time start
          multiday (or sleepover (not (t/= (t/date (t/date-time start)) (t/date (t/date-time end)))))]
      [:div
       {:class    (concat
                    (:bg color-map)
                    [:first:rounded-t :overflow-hidden])
        :on-click #(when on-click
                     (on-click item))}

       (when insert-top-fn
         [:div (insert-top-fn item)])

       [:div.flex
        {:class (:fg- color-map)}
        (when insert-before insert-before)
        [:div.grid.gap-y-2.gap-x-1.w-full.p-2
         {:style {:grid-template-columns "min-content min-content min-content min-content  min-content min-content 1fr"
                  :grid-auto-rows        ""}}
         [:div.w-10.debug.self-center.rounded.text-center.px-1.mr-1
          {:class ["bg-black/50" "text-white"]}
          [:span.font-bold.tabular-nums.uppercase day-name]]
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

         [:div.col-span-1.debug.w-full.text-right.font-black
          {:class (:fg color-map)}
          (first selected)]]
        (when accepted-user?
          (when insert-after insert-after))]])))

;region boat-picking

(defn- available? [slot boat]
  (try
    (tick.alpha.interval/relation boat slot)
    (catch js/Error t nil #_(.-message t))))

(defn- draw-graph [{:keys [window slot list]}]
  (let [{:keys [from to]} slot
        {:keys [width _offset]} window]
    [:<>

     [:svg.w-full.rounded.h-6
      {:class               :bg-white
       :viewBox             (str "0 0 " width " 10")
       :width               "100"
       ;:height              "auto"
       :preserveAspectRatio "none"}
      [:g {:stroke :none}
       [:line {:vector-effect :non-scaling-stroke
               :stroke        :blue
               :x1            (+ (max from to))
               :y1            0
               :x2            (+ (max from to))
               :y2            10}]
       [:rect {:fill   "#0004"
               :x      from
               :width  (- to from)
               :y      0
               :height 10}]
       (into [:<>] (map (fn [{:keys [start end r?]}]
                          [:rect {:fill   (if r? "#0a08" "#a008")
                                  :x      start
                                  :width  (- end start)
                                  :y      5
                                  :height 5}])
                        list))
       [:path.text-sky-400 {:vector-effect :non-scaling-stroke
                            :stroke-width  2
                            :stroke        :currentColor
                            :d             (str
                                             (apply str "M 0 9 " (repeat 4 " m 12 0 v -3 v 3 m 12 0"))
                                             (apply str "M 0 9 " (repeat 4 " m 6 0 h 12 m 6 0")))}]]]]))

(defn has-selection [id x]
  (= id (first (:selected x))))

(defn list-line [{:keys [offset slot id on-click remove? data insert-before graph?
                         insert-after] :or {graph? true}}]
  (let [window {:width  (* 24 5)
                :offset (* 24 offset)}
        offset (* 24 (dec offset))
        slot' (when slot
                {:from (- (convert (t/beginning slot)) offset)
                 :to   (- (convert (t/end slot)) offset)})
        booking-db (filter (partial has-selection id)
                           (booking.database/read))]
    (let [{:keys [text brand location warning?]} data]
      [:div.flex
       (when insert-before insert-before)
       [:div.flex.flex-col.w-full
        {:class    (concat
                     [:shadow]

                     (if remove?
                       ["bg-amber-300/20"
                        "hover:bg-amber-300/50"]
                       ["bg-pink-400/20"
                        "hover:bg-pink-300/50"]))
         :on-click #(on-click id)}

        [:div.grid.gap-2.p-1.w-full
         {:style {:grid-template-columns "3rem 2rem 1fr min-content"
                  :grid-auto-rows        "auto"}}
         [:div.font-bold.text-xl.place-self-center id]
         [:div.self-center (count booking-db)]
         [:div.text-xl.self-center brand]
         (if remove? [icon/small :cross-out])
         [:div.place-self-center.text-sm location]
         [:div]
         [:div.col-span-2.self-center.text-sm text]
         [:div.col-span-2]
         (when graph?
           [:<>
            [:div.col-span-1
             (when booking-db
               (draw-graph
                 {:window window
                  :list   (map (fn [{:keys [start end]}]
                                 {:r?
                                  (some #{(available? slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                                  :start (- (convert start) offset)
                                  :end   (- (convert end) offset)})
                               booking-db)
                  :slot   slot'}))]
            [:div.place-self-center [icon/small :circle-question-filled]]])]]
       (when insert-after insert-after)])))

;INTENT our desired timeslot, who is available in this slow?

(defn pick-list [{:keys [offset slot selected on-click boat-db]}]
  (if (seq @selected)
    [:div.space-y-1
     (for [id @selected]
       [list-line
        {:offset   offset
         :slot     slot
         :remove?  true
         :on-click #(on-click id)
         :id       id
         :data     (get boat-db id)}])]

    [:div.h-12.flex.items-center.justify-center.bg-white "Velg båt"]))

(defn boat-list [{:keys [offset slot boat-db on-click]}]
  (if (seq boat-db)
    [:div.space-y-px
     (for [[id data] boat-db]
       [list-line
        {:offset   offset
         :slot     slot
         :on-click on-click
         :id       id
         :data     data}])]

    [:div.h-12 "Alle er valgt"]))

;endregion

(defn goto-chevron [location]
  [:div.w-12.flex.items-baseline.justify-center.py-1
   {:class    ["hover:bg-gray-500" "bg-black/50" "text-white"]
    :on-click #(js/alert location)}
   [icon/touch :chevron-right]])

(defn booking-validation [{:keys [date start-time end-time sleepover] :as all}]
  (cond-> {}
    (= "" (str date))
    (assoc :date "Trenger start-dato")

    (and (not= "" (str date))
         (let [rel (relation (t'/date date) (t'/date))]
           (some #{rel} [:precedes :meets])))
    (assoc :date "Dato => Dagens dato")

    (and (not= "" (str date))
         (not= "" (str start-time))
         (let [rel (relation (t'/at (t'/date date) (t'/time start-time)) (t'/date-time))]
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
      (= :precedes (relation (t'/time end-time) (t'/time start-time))))
    (assoc :end-time "Trenger slutt-tid < start-tid")))

(defn button
  ([a disabled? c]
   [:button.bg-gray-50.shadow-inside
    {:class    (if disabled? [:text-gray-300])
     :type     :button
     :disabled disabled?
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)])
  ([a c]
   [:button.bg-gray-50.shadow-inside
    {:type     :button
     :disabled false
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)]))

(defn push-button
  ([a disabled? c]
   [:button.btn.btn-free
    {:class    (if disabled? [:text-gray-300])
     :type     :button
     :disabled disabled?
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)]))

(defn button-submit [a c]
  [:button.btn-narrow.btn-free.h-10.btn-cta
   {:type :submit}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn adjust' [state f field d]
  (fn []
    (let [has-time? (try (t/time (get-in @state [:values field])) (catch js/Error _ false))]
      (if has-time?
        (swap! state
               #(-> %
                    (update-in [:values :start] (fn [e] (f (t/date-time e) (t/new-duration 1 d))))
                    (update-in [:values :end] (fn [e] (f (t/date-time e) (t/new-duration 1 d))))))
        (swap! state
               update-in [:values field] #(f (t/date %) (t/new-period 1 d)))))))

(defn navigation [booking-state my-state]
  [:div.w-full
   [l/ppre-x booking-state]
   (rs/match-state booking-state
     [:s.booking :s.initial]
     [:div.flex.justify-between
      (push-button #(state/send :e.pick-boat) nil "Neste")
      [:div]]

     [:s.booking :s.boat-picker]
     [:div.flex.justify-between
      (push-button #(state/send :e.confirm) nil "Neste")
      [:div]]

     [:s.booking :s.confirm]
     [:div.flex.justify-between
      (push-button #(state/send :e.pick-boat) nil "Forrige")
      (button-submit nil "Bekreft booking")]

     [:div "uh?"])])

(defn time-navigator [{:keys [state] :as props}]
  [:div.space-y-4
   [:div.grid.gap-4
    {:style {:grid-template-columns "3fr 2fr"
             :grid-auto-rows        "auto"}}
    [:div.grid.gap-1
     {:style {:grid-template-columns "repeat(4,1fr)"
              :grid-auto-rows        "3rem"}}
     (button (fn [] (let [dt (t/date-time)]
                      (tap> dt)
                      (swap! state #(-> %
                                        (update-in [:values :start] (constantly dt))
                                        (update-in [:values :end] (constantly dt))))))
             :rotate-left)
     [:div.col-span-3
      (fields/date
        (assoc props
          :class [:w-full]
          :naked? true
          :values (fn [] (some-> @state :values :start t/date))
          :handle-change (fn [e] (let [date-str (-> e .-target .-value)
                                       sleepover (get-in @state [:values :sleepover])
                                       end (t/date-time (t/at (t/date date-str) (t/time (get-in @state [:values :end]))))
                                       dt (t/>> end (t/new-period (if sleepover 1 0) :days))
                                       _ (tap> end)
                                       ;
                                       time-str (try
                                                  (some-> @state
                                                          (get-in [:values :start])
                                                          t/time)
                                                  (catch js/Error _ nil))
                                       end-time-str (try
                                                      (some-> @state
                                                              (get-in [:values :end])
                                                              t/time)
                                                      (catch js/Error _ nil))]
                                   (swap! state #(-> %
                                                     (assoc-in [:values :start]
                                                               (if time-str
                                                                 (t/at (t/date date-str) time-str)
                                                                 (t/date date-str)))
                                                     (assoc-in [:values :end]
                                                               (if time-str
                                                                 (t/at (if sleepover
                                                                         (t/date dt)
                                                                         (t/date end)) end-time-str)
                                                                 (t/date dt))))))))

        "test" :name)]

     [:div]
     (button (adjust' state t/<< :start :days) :chevron-left)
     (button (adjust' state t/>> :start :days) :chevron-right)
     [:div]]


    (let [disabled? (not (some? (some-> @state :values :start)))]
      [:div.grid.gap-1
       {:style {:grid-template-columns "repeat(4,1fr)"
                :grid-auto-rows        "3rem"}}
       [:div.col-span-4 (fields/time {:naked?        true
                                      :class         [:w-full]
                                      :values        (fn [] (some-> @views/my-state :values :start t/time))
                                      :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                                   date-time-str (get-in @views/my-state [:values :start])]
                                                               (swap! views/my-state assoc-in [:values :start]
                                                                      (t/at (t/date date-time-str) time-str))))}
                                     "" :name)]
       (let [
             f (fn [h] (let [time (t/new-time h 0)]
                         (swap! state update-in [:values :start] #(t/at (t/date %) time))))]
         [:<>
          (button #(f 9) disabled? "09")
          (button #(f 12) disabled? "12")
          (button #(f 15) disabled? "15")
          (button #(f 17) disabled? "17")])])

    [:div.flex.items-center
     (fields/checkbox
       (assoc props
         :handle-change (fn [evt]
                          (let [v (-> evt .-target .-checked)
                                end (t/date-time (get-in @state [:values :start]))
                                dt (t/>> end (t/new-period (if v 1 0) :days))]
                            (swap! state #(-> %
                                              (assoc-in [:values :end] dt)
                                              (assoc-in [:values :sleepover] v))))))

       "Overnatting" :sleepover)]
    (let [disabled? (not (some? (some-> @state :values :start)))]
      [:div.grid.gap-1
       {:style {:grid-template-columns "repeat(4,1fr)"
                :grid-auto-rows        "3rem"}}

       [:div.col-span-4 (fields/time {:naked?        true
                                      :disabled?     disabled?
                                      :class         [:w-full]
                                      :values        (fn [] (some-> @views/my-state :values :end t/time))
                                      :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                                   date-time-str (get-in @views/my-state [:values :end])]
                                                               (swap! views/my-state assoc-in [:values :end]
                                                                      (t/at (t/date date-time-str) time-str))))}
                                     "" :name)]
       (let [f (fn [h] (let [time (t/new-time h 0)
                             current-time (t/time (get-in @state [:values :end]))]
                         (swap! state update-in [:values :end] #(t/at (t/date %) time))))]
         [:<>
          (button #(f 9) disabled? "09")
          (button #(f 12) disabled? "12")
          (button #(f 15) disabled? "15")
          (button #(f 17) disabled? "17")])])]])

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

(defn confirmation [{:as props} {:keys [state selected boat-db]}]
  (let [{:keys [sleepover start end]} (-> @state :values)
        slot (try
               (tick.alpha.interval/bounds
                 (-> @state :values :start)
                 (-> @state :values :end))
               (catch js/Error _ nil))]
    [:div.space-y-4
     (let [h (when slot (t/hours (t/duration slot)))]
       [:div (when slot (format "Fra %s til %s %s, %s"
                                (t/format (t/formatter "d/M-yy hh:mm") start)
                                (t/format (t/formatter "d/M-yy hh:mm") end)
                                (if sleepover "(1 overnatting)")
                                (apply str (cond-> []
                                             (< 24 h) (conj "dager")))))])
     [:div (if sleepover "Overnatting")]
     (pick-list
       {:offset   (day-number-in-year start)
        :slot     slot
        :boat-db  boat-db
        :selected selected
        :on-click #(swap! selected disj %)})
     (fields/textarea (fields/full-field props) "Beskjed til tur-kamerater" :description)]))

(defn boat-picker [{:keys [uid cancel on-submit booking-data' my-state boat-db selected]} props]
  (comment
    {:doc
     "Find all entries with a relation of :precedes or preceded-by, all the
     other relations must fail. Since there isn't any allowance for abutting
     entries (1 hour minimum between each booking), :met-by and :meets thus
     are rejected."})
  (let [start (get-in @my-state [:values :start])
        end (get-in @my-state [:values :end])
        offset (if start (day-number-in-year (t/date start)) 0)
        slot (try
               (tick.alpha.interval/bounds start end)
               (catch js/Error _ nil))]
    [:div.space-y-4
     [views/rounded-view {:flat 1} [time-navigator props]]
     [views/rounded-view {:flat 1}
      [:div.space-y-4
       (pick-list
         {:offset   offset
          :slot     slot
          :boat-db  boat-db
          :selected selected
          :on-click #(swap! selected disj %)})
       (boat-list
         {:offset   offset
          :slot     slot
          :boat-db  (remove (fn [[k _v]] (some #{k} @selected)) boat-db)
          :selected selected
          :on-click #(swap! selected conj %)})]]]))

(defn after-and-including [today {:keys [start]}]
  (let [p (tick.alpha.interval/relation today start)]
    (some #{p} [:equals :starts :during :meets :precedes])))

(defn general-header [& content]
  [:div.h-16x.flex.p-3.items-center.sticky.top-32x.mt-3.z-10
   {:class [:panel]}
   content])

(defn general-footer
  "a footer for all lists where editing of some sort makes sense"
  [{:keys [accepted-user? edit-state markings c data key-fn show-all]}]
  [:div.flex.justify-between.p-4.sticky.bottom-0.z-0
   {:class [:panel]}
   [:div.gap-2.flex
    [:button.btn-small {:on-click #(swap! edit-state not)} (if @edit-state "Ferdig" "Endre")]
    (when @edit-state
      [:button.btn-small
       {:on-click #(reset! markings (reduce (fn [a e] (assoc a e true)) {} (map key-fn data)))}
       "Merk alt"])
    (when @edit-state
      [:button.btn-small
       {:disabled (zero? c)
        :on-click #(reset! markings {})}
       "Merk ingen"])
    (if @edit-state
      (let []
        [:button.btn-small.btn-menu
         {:disabled (zero? c)
          :on-click #(js/alert "!") #_#(do
                                         (booking.database/delete selected-keys)
                                         (reset! markings {}))}
         [:div.flex.gap-2.items-center (icon/small :chevron-down) (str "Operasjon " (when (pos? c) c))]])
      [:div])]
   (when accepted-user?
     (when-not @show-all
       [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"]))])

(defn booking-list [{:keys [uid today data accepted-user? class]}]
  (r/with-let [show-all (r/atom false)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          my-data data
          data (cond->> (->> my-data
                             (filter (partial after-and-including today))
                             (sort-by :start <))
                        (not @show-all) (take 10))]
      [:div.py-4.panel
       (into [:div.space-y-px.shadow
              {:class class}]
             (map (fn [e]
                    (let [idx (:id e)]
                      (booking-list-item
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
                         :insert-after   (when accepted-user?
                                           (goto-chevron (str "location/" idx)))} e)))
                  data))

       [general-footer
        {:accepted-user? accepted-user?
         :data       data
         :show-all show-all
         :key-fn     :id
         :edit-state edit
         :markings   markings
         :c          c}]])))

(defn all-boats [{:keys []}]
  (r/with-let [data (booking.database/boat-db)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)]
      [:div.w-full
       [general-header
        [:h2 "Alle båter"]]

       (into [:div {:class [:overflow-clip
                            :space-y-px
                            :first:rounded-t
                            :last:rounded-b]}]
             (map (fn [[id data]]
                    (let [idx id]
                      [booking.views/list-line
                       {:graph?        false
                        :id            id
                        :on-click      #(swap! markings update idx (fnil not false))
                        :data          data
                        :insert-before (when @edit
                                         [:div.flex.items-center.px-2.bg-gray-500
                                          [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                            :handle-change #(swap! markings update idx (fnil not false))}
                                           "" nil]])
                        :insert-after  (goto-chevron idx)}]))
                  data))
       [general-footer
        {:data       data
         :key-fn     key
         :edit-state edit
         :markings   markings
         :c          c}]])))

(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))]
    [fork/form {:initial-values    {:start       (t/at (t/date "2022-01-12") (t/time "09:00"))
                                    :end         (t/at (t/date "2022-01-13") (t/time "17:00"))
                                    :sleepover   true
                                    :description "Ugh"}
                ;:validation        booking-validation
                ;:form-id           :booking-form
                :state             my-state
                :prevent-default?  true
                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         #(on-submit (-> %
                                                   (assoc-in [:values :uid] uid)
                                                   (assoc-in [:values :selected] @selected)
                                                   :values))}
     (fn [{:keys [form-id handle-submit] :as props}]
       [:form.space-y-4x.bg-white
        {:id        form-id
         :on-submit handle-submit}

        [views/rounded-view {:flat 1}
         (navigation booking-state my-state)]

        (rs/match-state booking-state
          [:s.booking :s.boat-picker]
          [boat-picker main-m props]

          [:s.booking :s.confirm]
          [confirmation props
           {:slot     nil
            :boat-db  boat-db
            :selected selected
            :state    my-state}]

          [:s.booking :s.initial]
          [:div "initial"]

          [:div "d?" booking-state])

        [:div.flex.items-center.sticky.bottom-0.xh-16.panel.p-4
         (navigation booking-state my-state)]])]))