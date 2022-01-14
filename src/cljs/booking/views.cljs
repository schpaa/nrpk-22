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
    :preceded-by {:bg  ["dark:bg-gray-500" "bg-gray-50"]
                  :fg  ["dark:text-black" "text-black"]
                  :fg- ["dark:text-black/40" "text-black/40"]}

    :precedes {:bg  ["dark:bg-black/30" "bg-gray-300"]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}
    {:bg  ["dark:bg-gray-700" "bg-sky-100"]
     :fg  ["dark:text-black" "text-sky-600"]
     :fg- ["dark:text-white"]}))

(defn- booking-list-item [{:keys [today hide-name? insert-front insert-top-fn on-click insert-behind]
                           :or   {today (t/new-date)}} item]
  (let [{:keys [navn book-for-andre sleepover description selected start end]} item
        relation' (relation start today)
        color-map (booking-list-item-color-map relation')]
    (let [cell-class (concat
                       (:fg color-map)
                       (if (not= relation' :precedes)
                         (when on-click
                           [:cursor-pointer
                            "hover:bg-white/90"
                            "dark:hover:bg-white/10"])))
          navn (or navn "skult navn")
          day-name (day-name (t/date-time start))
          checkout-time start
          checkin-time end
          start-date (straight-date (t/date-time start))]
      [:div
       {:class    (concat
                    (:bg color-map)
                    [:first:rounded-t
                     ;:last:rounded-b
                     :overflow-hidden])
        :on-click #(when on-click
                     (on-click item))}

       (when insert-top-fn
         [:div (insert-top-fn item)])

       [:div.flex
        (when insert-front insert-front)
        [:div.grid.gap-2.w-full.p-2
         {:style {:grid-template-columns "4rem 2rem 4rem 2rem 8rem 1fr 2rem"
                  :grid-auto-rows        ""}}
         [:div.text-right.whitespace-nowrap (t/format "dd/MM-YY" (t/date-time start))]
         [:div day-name]
         [:div (str (t/time (t/date-time checkout-time)))]
         [:div
          [:div.w-5.h-5 [icon/small (if sleepover :moon-2 :minus)]]]
         (try
           (if (not (t/= (t/date (t/date-time start))
                         (t/date (t/date-time end))))
             [:div (t/format "dd/MM-YY" (t/date (t/date-time end))) " "
              (str (t/time (t/date-time end)))]
             [:div (str (t/time (t/date-time end)))])
           (catch js/Error e (.-message e)))

         [:div.w-full.text-right.font-bold (first selected)]

         [:div]
         [:div]

         [:div.col-span-4.text-right.debug

          (when-not false #_(and hide-name? (not book-for-andre))
            [:div.flex.w-full.flex-wrap.gap-x-2.items-center.justify-end
             [:div.growx.line-clamp-2x.w-64x.flex.justify-endx
              {:class (:fg- color-map)}
              navn]])]

         [:div][:div]
         [:div.col-span-4.text-sm {:class (:fg- color-map)} description]]
        (when insert-behind insert-behind)]])))

;region boat-picking

(defn- available? [slot boat]
  (try
    (tick.alpha.interval/relation boat slot)
    (catch js/Error t nil #_(.-message t))))

(defn- draw-graph [{:keys [window slot list]}]
  (let [{:keys [from to]} slot
        {:keys [width _offset]} window]
    [:<>

     [:svg.w-full.rounded
      {:class               :bg-white
       :viewBox             (str "0 0 " width " 10")
       :width               "100"
       :height              "auto"
       :preserveAspectRatio "none"}
      [:g {:stroke :none}
       [:line {:stroke :blue
               :x1     (+ (max from to) 1)
               :y1     0
               :x2     (+ (max from to) 1)
               :y2     10}]
       [:rect {:fill   "#0008"
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
       [:path.text-sky-400 {:stroke :currentColor
                            :d      (str
                                      (apply str "M 0 9 " (repeat 4 " m 12 0 v -3 v 3 m 12 0"))
                                      (apply str "M 0 9 " (repeat 4 " m 6 0 h 12 m 6 0")))}]]]]))


(defn has-selection [id x]
  (= id (first (:selected x))))

(defn list-line [{:keys [offset slot id on-click remove? data insert-front graph?
                         insert-behind] :or {graph? true}}]
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
       (when insert-front insert-front)
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
       (when insert-behind insert-behind)])))

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
    {:type      :button
     :disabled false
     :on-click  a}
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
   {:type     :submit}
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
  (let [s (get-in @my-state [:values :start])
        offset (if s (day-number-in-year (t/date s)) 0)
        slot (try
               (tick.alpha.interval/bounds
                 (-> @my-state :values :start)
                 (-> @my-state :values :end))
               (catch js/Error _ nil))]
    [:div.space-y-4
     [time-navigator props]
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
         :on-click #(swap! selected conj %)})]
     (comment
       {:doc
        "Find all entries with a relation of :precedes or preceded-by, all the
        other relations must fail. Since there isn't any allowance for abutting
        entries (1 hour minimum between each booking), :met-by and :meets thus
        are rejected."})]))

(defn after-and-including [today {:keys [start]}]
  (let [p (tick.alpha.interval/relation today start)]
    (some #{p} [:equals :starts :during :meets :precedes])))

(defn booking-list [{:keys [today data]}]
  (r/with-let [show-all (r/atom false)
               edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          my-data data
          data (cond->> (->> my-data
                             (filter (partial after-and-including today))
                             (sort-by :start <))
                        (not @show-all) (take 3))]
      [:div
       (into [:div.space-y-px.shadow]
             (map (fn [e]
                    (let [idx (:id e)]
                      (booking-list-item
                        {:today        today
                         :hide-name?   true
                         :on-click     (fn [e]
                                         (swap! markings update idx (fnil not false))
                                         (.stopPropagation e))
                         :insert-behind [:div.w-16.flex.items-center.justify-center
                                         {:class    ["hover:bg-gray-500" "bg-black" "text-amber-500"]
                                          :on-click #(js/alert "!")}
                                         [icon/touch :chevron-right]]
                         :insert-front (when @edit
                                         [:div.flex.items-center.px-2.bg-gray-400
                                          [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                            :handle-change #(swap! markings update idx (fnil not false))}
                                           "" nil]])} e)))
                  data))

       [:div.flex.justify-between.py-4.px-2.bg-gray-400.sticky.bottom-0
        [:div.gap-2.flex
         [:button.btn-small.btn-free {:on-click #(swap! edit not)} (if @edit "Ferdig" "Endre")]
         (when @edit
           [:button.btn-small.btn-free {:on-click #(reset! markings (reduce (fn [a e] (assoc a e true)) {} (map :id data)))} "Merk alt"])
         (when @edit
           [:button.btn-small.btn-free {:disabled (zero? c)
                                        :on-click #(reset! markings {})} "Merk ingen"])
         (if @edit
           (let []
             [:button.btn-small.btn-danger
              {:disabled (zero? c)
               :on-click #(do
                            (booking.database/delete selected-keys)
                            (reset! markings {}))}
              (str "Slett " (when (pos? c) c))])
           [:div])]
        (when-not @show-all
          [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"])]])))

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
       [:form.space-y-4
        {:id        form-id
         :on-submit handle-submit}
        (navigation booking-state my-state)
        (rs/match-state booking-state
          [:s.booking :s.boat-picker]
          [:div [boat-picker main-m props]]
          [:s.booking :s.confirm]
          [confirmation props
           {:slot     nil
            :boat-db  boat-db
            :selected selected
            :state    my-state}]
          [:s.booking :s.initial]
          [:div "initial"]

          [:div "d?" booking-state])

        [:div.flex.items-center.sticky.bottom-0.h-16.bg-gray-100
         (navigation booking-state my-state)]])]))
