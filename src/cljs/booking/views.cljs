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
    ;["@js-joda/locale_no-no" :as js-joda-locale]
            [booking.database]
            [fork.re-frame :as fork]
            [times.api :refer [format]]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(defn- day-name [wd]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week wd)))]
    (subs (nth day-names d) 0 2)))

(defn- straight-date [instant]
  (str
    (t'/day-of-month (t'/date instant))
    "/"
    (t'/int (t'/month (t'/date instant)))))

(defn- str->datetime [dt]
  (let [y (subs dt 0 4)
        m (subs dt 4 6)
        d (subs dt 6 8)
        hh (subs dt 9 11)
        mm (subs dt 11 13)
        ss (subs dt 13 15)]
    (t'/at (t'/new-date y m d) (t'/new-time hh mm ss))))

(defn line-click [{:keys [date start-time end-time sleepover]}]
  (swap! views/my-state (fn [e]
                          (-> e
                              (assoc-in [:values :sleepover] sleepover)
                              (assoc-in [:values :date] (t'/date date))
                              (assoc-in [:values :start-time] (t'/time start-time))
                              (assoc-in [:values :end-time] (t'/time end-time))))))

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

(defn- booking-list-item [{:keys [today hide-name? insert-front insert-top-fn on-click]
                           :or   {today (t/new-date)}} item]
  (let [{:keys [version date bid navn checkin checkout
                book-for-andre
                sleepover
                uid
                description
                selected start-time end-time]} item
        checkout-dt (t'/at (t/date date) (t/time start-time))
        relation' (relation checkout-dt today)
        color-map (booking-list-item-color-map relation')]
    (let [cell-class (concat
                       (:fg color-map)
                       (if (not= relation' :precedes)
                         (when on-click
                           [:cursor-pointer
                            "hover:bg-white/90"
                            "dark:hover:bg-white/10"])))
          navn (or navn "skult navn")
          day-name (day-name date)
          checkout-time (str start-time)
          checkin-time (str end-time)]
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
        [:div.w-full
         {:class [:visible :mob:hidden]}
         [:div.select-none.flex.flex-col.p-2.gap-x-2.space-y-2
          {:style {:min-height "3rem"}
           :class cell-class}
          [:div.flex.items-center.justify-between.gap-x-2
           {:class (:fg- color-map)}
           [:div.w-12.truncate.shrink-0 (straight-date date)]
           [:div.w-12 day-name]
           [:div.w-12.shrink-0.xtext-center checkout-time]
           [:div.w-12.xflex.xjustify-start
            [:div.w-5.h-5 [icon/small (if sleepover :moon-2 :minus)]]]
           [:div.w-12.shrink-0.xtext-center checkin-time]
           [:div.flex-grow.w-full.flex.justify-end
            {:class (concat [:font-bold] (:fg color-map))}
            (if (and (vector? selected)
                     (< 1 (count selected)))
              [:div "KURS"]
              [:div (str (first selected))])]]
          (when-not (and hide-name? (not book-for-andre))
            [:div.flex.w-full.flex-wrap.gap-x-2.items-center.justify-end
             [:div.growx.line-clamp-2x.w-64x.flex.justify-endx
              {:class (:fg color-map)}
              navn]])
          [:div.text-sm.ml-12.pl-2 description]]]]])))

;region boat-picking

(defn- available? [slot boat]
  (try

    (tick.alpha.interval/relation boat slot)
    (catch js/Error t nil #_(.-message t))))

(defn- convert [dt]
  (let [f #(t/days (t/between (t/new-date 2022 1 1) %))]
    (when-let [dt (t/date-time dt)]
      (+ (* 24 (t/day-of-month dt))
         (t/hour dt)
         (* 24 (f dt))))))

(defn- draw-graph [{:keys [window slot list]}]
  (let [{:keys [from to]} slot
        {:keys [width _offset]} window]
    [:svg.w-full.rounded
     {:class               :bg-white                        ;(if r? :bg-white "bg-amber-200/50")
      :viewBox             (str "0 0 " width " 10")
      :width               "100"
      :height              "auto"
      :preserveAspectRatio "none"}
     [:g {:stroke :none}
      [:rect {:fill   "#0008"
              :x      from
              :width  (- to from)
              :y      0
              :height 10}]
      (into [:<>] (map (fn [{:keys [start end r?]}]
                         [:rect {:fill   (if r? "#a0a8" "#a008")
                                 :x      start
                                 :width  (- end start)
                                 :y      5
                                 :height 5}])
                       list))]]))

(defn has-selection [id x]
  (= id (first (:selected x))))

(defn list-line [{:keys [day slot id on-click remove? data]}]
  (let [window {:width  (* 24 @(rf/subscribe [:app/width]))
                :offset (* 24 day)}
        offset (:offset window)
        slot' {:from (- (convert (t/beginning slot)) (:offset window))
               :to   (- (convert (t/end slot)) (:offset window))}
        booking-db (filter (partial has-selection id)
                           (booking.database/read))]
    (let [{:keys [text brand location warning?]} data]
      [:div.flex.flex-col
       {:class    (concat
                    [:first:rounded-t
                     :shadow
                     :last:rounded-b]
                    ["bg-pink-300/20"
                     "hover:bg-pink-300/50"])
        :on-click #(on-click id)}
       [:div.grid.gap-2.p-2.w-full
        {:style {:grid-template-columns "3rem 2rem 1fr min-content"
                 :grid-auto-rows "auto"}}
        [:div.font-bold.text-xl.place-self-center id]
        [:div.self-center  (count booking-db)]
        [:div.text-xl.self-center brand]
        (if remove? [icon/small :cross-out])
        [:div.place-self-center.text-sm location]
        [:div]
        [:div.col-span-2.self-center.text-sm text]

        #_[:div.px-4.py-2.xh-12.flex.items-center.justify-between.gap-4
           [:div.font-bold.text-lg id]
           (count booking-db)
           [:div.flex.flex-col.flex-grow
            [:div.text-base brand]
            [:div text]]
           (if remove? [icon/small :cross-out])]
        [:div.col-span-2]
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
        [:div.place-self-center [icon/small :circle-question-filled]]]])))                          ;INTENT our desired timeslot, who is available in this slow?

(defn pick-list [{:keys [day slot selected on-click boat-db]}]
  (if (seq @selected)
    [:div.space-y-1
     (for [id @selected]
       [list-line
        {:day      day
         :slot     slot
         :remove?  true
         :on-click #(on-click id)
         :id       id
         :data     (get boat-db id)}])]

    [:div.h-12.flex.items-center.justify-center.bg-white "Velg båt"]))

(defn boat-list [{:keys [day slot boat-db on-click]}]
  (if (seq boat-db)
    [:div.space-y-px
     (for [[id data] boat-db]
       [list-line
        {:day      day
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

(defn button [a c]
  [:button.btn-narrow.btn-free.h-10
   {:type     :button
    :on-click a}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn button-submit [a c]
  [:button.btn-narrow.btn-free.h-10.btn-cta
   {:type     :submit
    :on-click a}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn adjust [f field]
  (fn [] (swap! views/my-state
                update-in [:values field] #(f (t/date-time %) (t/new-duration 1 :hours)))))

(defn adjust' [f field d]
  (fn [] (swap! views/my-state
                update-in [:values field] #(f (t/date-time %) (t/new-duration 1 d)))))

(defn navigation [booking-state]
  (rs/match-state booking-state
    [:s.booking :s.initial]
    [:div.flex.justify-between
     [:div]
     (button #(state/send :e.pick-boat) "Neste")]

    [:s.booking :s.boat-picker]
    [:div.flex.justify-between
     [:div]
     ;(button #(state/send :e.edit-basics) "Forrige")
     (button #(state/send :e.confirm) "Neste")]

    [:s.booking :s.confirm]
    [:div.flex.justify-between
     [:div]
     (button #(state/send :e.pick-boat) "Forrige")]

    [:div "uh?"]))

;region events and subs

(rf/reg-event-db :app/inc-window-width (fn [db [_ arg]]
                                         (update db :window-width (fnil inc 0))))

(rf/reg-event-db :app/dec-window-width (fn [db [_ arg]]
                                         (if (< 10 (:window-width db))
                                           (update db :window-width (fnil dec 0))
                                           db)))

(rf/reg-event-db :app/set-window-width (fn [db [_ arg]]
                                         (assoc db :window-width arg)))

(rf/reg-event-db :app/inc-window-offset (fn [db [_ arg]]
                                          (update db :window-offset #(+ % (or arg 1)))))

(rf/reg-event-db :app/dec-window-offset (fn [db [_ arg]]
                                          (update db :window-offset #(- % (or arg 1)))))

(rf/reg-event-db :app/set-window-offset (fn [db [_ arg]]
                                          (assoc db :window-offset arg)))

(rf/reg-sub :app/offset (fn [db] (:window-offset db 10)))

(rf/reg-sub :app/width (fn [db] (:window-width db 10)))

;endregion

(defn window-adjuster []
  [:div.grid.grid-cols-2.gap-1
   [:div.flex.justify-start.gap-1.items-center
    (button #(rf/dispatch [:app/set-window-offset -24]) "Forrige uke")
    (button #(rf/dispatch [:app/set-window-offset 0]) "Nå + 2 uker")
    (button #(rf/dispatch [:app/set-window-offset (* 24 4)]) "1 måned")]
   [:div.flex.justify-start.gap-1.items-center
    (button #(rf/dispatch [:app/set-window-width 0]) "Kort")
    (button #(rf/dispatch [:app/set-window-width 48]) "Langt")]
   [:div (format "%d dager" (/ @(rf/subscribe [:app/offset]) 24))]
   [:div (format "%d noe" (/ @(rf/subscribe [:app/width]) 24))]])

(defn time-navigator [props]
  [:div.space-y-4
   [:div.grid.grid-cols-2.gap-1
    [:div.flex.gap-1
     (button (adjust' t/<< :start :days) :minus)
     (fields/date (assoc (fields/date-field props)
                    :naked? true
                    :values (fn [] (-> @views/my-state :values :start t/date))
                    :handle-change (fn [e] (let [date-str (-> e .-target .-value)
                                                 time-str (t/time (get-in @views/my-state [:values :start]))]
                                             (swap! views/my-state assoc-in [:values :start]
                                                    (t/at (t/date date-str) time-str)))))
                  "test" :name)
     (button (adjust' t/>> :start :days) :plus)]

    [:div.flex.gap-1
     (button (adjust t/<< :start) :minus)
     (fields/time {:naked?        true
                   :values        (fn [] (-> @views/my-state :values :start t/time))
                   :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                date-time-str (get-in @views/my-state [:values :start])]
                                            (swap! views/my-state assoc-in [:values :start]
                                                   (t/at (t/date date-time-str) time-str))))}
                  "" :name)
     (button (adjust t/>> :start) :plus)]
    [:div.flex.items-center
     (fields/checkbox
       (assoc props
         :handle-change (fn [evt]
                          (let [v (-> evt .-target .-checked)
                                end (t/date-time (get-in @(:state props) [:values :end]))]
                            (swap! @(:state props) update-in [:values]
                                   assoc :sleepover v
                                   :end (t/>> end (t/new-period (if (get-in @(:state props) [:values :sleepover]) 1 0) :days))))))
       "Overnatting" :sleepover)]

    [:div.flex.gap-1
     (button (adjust t/<< :end) :minus)
     (fields/time {:naked?        true
                   :values        (fn [] (-> @views/my-state :values :end t/time))
                   :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                date-time-str (get-in @views/my-state [:values :end])]
                                            (swap! views/my-state assoc-in [:values :end]
                                                   (t/at (t/date date-time-str) time-str))))}
                  "" :name)
     (button (adjust t/>> :end) :plus)]]])

(defn confirmation [{:as props} {:keys [state selected boat-db]}]
  (let [{:keys [sleepover date start end]} (-> @state :values)
        slot (tick.alpha.interval/bounds
               (-> @state :values :start)
               (-> @state :values :end))]
    [:div.space-y-4
     ;[l/ppre-x @selected @state]

     (let [h (t/hours (t/duration (tick.alpha.interval/bounds start end)))]
       [:div (format "Fra %s til %s %s, %s"
                     (t/format (t/formatter "d/M-yy hh:mm") start)
                     (t/format (t/formatter "d/M-yy hh:mm") end)
                     (if sleepover "(1 overnatting)")
                     (apply str (cond-> []
                                  (< 24 h) (conj "dager"))))])
     [:div (str (t/hours (t/duration (tick.alpha.interval/bounds start end))))]
     [:div (if sleepover "Overnatting")]
     (pick-list
       {:day      @(rf/subscribe [:app/offset])
        :slot     slot
        :boat-db  boat-db
        :selected selected
        :on-click #(swap! selected disj %)})
     (fields/textarea (fields/full-field props) "Beskjed til tur-kamerater" :description)]))

(defn boat-picker [{:keys [uid cancel on-submit booking-data' my-state boat-db selected]} props]
  (let [;date (-> @my-state :values :date)
        slot (tick.alpha.interval/bounds
               (-> @my-state :values :start)
               (-> @my-state :values :end))]
    [:div.space-y-4
     [time-navigator props]
     [window-adjuster]
     [:div.space-y-4
      (pick-list
        {:day      @(rf/subscribe [:app/offset])
         :slot     slot
         :boat-db  boat-db
         :selected selected
         :on-click #(swap! selected disj %)})
      (boat-list
        {:day      @(rf/subscribe [:app/offset])
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

(defn initial-booking-state [{:keys [handle-change set-values state] :as props}]
  [:div.space-y-4
   [:div.flex.flex-col.gap-4

    [:div.flex.gap-x-2.flex-wrap
     ;todo
     (fields/date (assoc (fields/date-field props)
                    :values (fn [] (str (t/date (get-in @state [:values :start]))))
                    :handle-change (fn [evt]
                                     (let [v (-> evt .-target .-value)
                                           time (t/time (get-in @state [:values :start]))
                                           time' (t/time (get-in @state [:values :end]))
                                           sleepover (get-in @state [:values :sleepover])]
                                       (rf/dispatch [:exp/set-date-filter v])
                                       (swap! state update-in [:values]
                                              assoc :start (t/at (t/date v) time)
                                              :end (t/at (t/>> (t/date v) (t/new-period (if sleepover 1 0) :days)) time')))))
                  "Dato" :start)

     #_(fields/button #(let [v (t'/>> (t'/date) (t'/new-period 0 :days))]
                         (rf/dispatch [:exp/set-date-filter v])
                         (set-values {:date v}))
                      nil "I dag")

     #_(fields/button #(set-values {:date (t'/>> (t'/date) (t'/new-period 1 :days))})
                      nil "I morgen")]

    [:div.flex.gap-x-2.flex-wrap
     ;todo
     (fields/time (assoc (fields/time-field props)
                    :values (fn [] (str (t/time (get-in @state [:values :start]))))
                    :handle-change (fn [evt]
                                     (let [v (-> evt .-target .-value)
                                           date (t/date (get-in @state [:values :start]))]
                                       (rf/dispatch [:exp/set-date-filter v])
                                       (swap! state update-in [:values] assoc :start (t/at date (t/time v))))))
                  "UT Kl" :start)

     #_(let [next-hour (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                           (t'/truncate :hours))]
         (fields/button #(set-values {:start-time next-hour}) nil (str next-hour)))

     #_(fields/button #(set-values {:start-time (t'/new-time 12 0)}) nil "12:")
     #_(fields/button #(set-values {:start-time (t'/new-time 15 0)}) nil "15:")
     #_(fields/button #(set-values {:start-time (t'/new-time 17 0)}) nil "17:")]

    (fields/checkbox
      (assoc props
        :handle-change (fn [evt]
                         (let [v (-> evt .-target .-checked)
                               end (t/date-time (get-in @state [:values :end]))]
                           (swap! state update-in [:values]
                                  assoc :sleepover v
                                  :end (t/>> end (t/new-period (if (get-in @state [:values :sleepover]) 1 0) :days))))))
      "Overnatting" :sleepover)

    [:div.flex.gap-x-2.flex-wrap
     ;todo
     (fields/time (assoc (fields/time-field props)
                    :values (fn [] (str (t/time (get-in @state [:values :end]))))
                    :handle-change (fn [evt]
                                     (let [v (-> evt .-target .-value)
                                           date (t/date (get-in @state [:values :start]))]
                                       (rf/dispatch [:exp/set-date-filter v])
                                       (swap! state
                                              update-in [:values]
                                              assoc :end (t/at (t/>> date (t/new-period (if (get-in @state [:values :sleepover]) 1 0) :days)) (t/time v))))))

                  "Tilbake kl" :end)
     #_(fields/button #(set-values {:end-time (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                                                  (t'/truncate :hours))}) nil "Snarest")
     #_(fields/button #(set-values {:end-time (t'/new-time 17 0)}) nil "17:")
     #_(fields/button #(set-values {:end-time (t'/new-time 19 0)}) nil "19:")]]])

(comment
  ;todo shortcut for this!
  (let [date "2022-01-10" start-time "13:20"
        rel (relation (t'/at (t'/date date) (t'/time start-time)) (t'/date-time))]
    [rel (t'/at (t'/date date) (t'/time start-time))]))

(defn belong-to-me [uid' {:keys [uid]}]
  (= uid' uid))

(defn after-and-including [today {:keys [date]}]
  (let [p (tick.alpha.interval/relation today date)]
    (some #{p} [:equals :starts :during :meets :precedes])))

(defn before [today {:keys [date]}]
  (let [p (tick.alpha.interval/relation date today)]
    (some #{p} [:precedes :meets])))

(defn my-booking-list [{:keys [uid today data]}]
  [:div "my-booking-list"]
  #_(let [my-data (filter (partial belong-to-me uid) data)]
      (r/with-let [show-all (r/atom false)
                   edit (r/atom false)
                   markings (r/atom {})]
        (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
              c (count selected-keys)
              my-data (filter (partial belong-to-me uid) data)
              data (cond->> (->> my-data
                                 (filter (partial after-and-including today))
                                 (sort-by :date <))
                            (not @show-all) (take 3))]
          [:<>
           (into [:div.space-y-px.shadow]
                 (map (fn [e]
                        (let [idx (:id e)]
                          (booking-list-item
                            {:today        today
                             :hide-name?   true
                             :on-click     (fn [e]
                                             (swap! markings update idx (fnil not false))
                                             (.stopPropagation e))
                             :insert-front (when @edit
                                             [:div.flex.items-center.px-2.bg-gray-400
                                              [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                                :handle-change #(swap! markings update idx (fnil not false))}
                                               "" nil]])} e)))
                      (reverse data)))

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
              [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"])]]))))

(defn booking-form [{:keys [uid on-submit my-state boat-db selected] :as main-m}]
  (let [booking-state (:booking @(rf/subscribe [::rs/state :main-fsm]))]
    [fork/form {#_#_:initial-values {:start       (t/at (t/date "2022-01-10") (t/time "08:00"))
                                     :end         (t/at (t/date "2022-01-10") (t/time "18:00"))
                                     :sleepover   true
                                     :description "Ugh"}
                :validation        booking-validation
                :form-id           :booking-form
                :state             my-state
                :prevent-default?  true
                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         #(on-submit (assoc-in % [:values :uid] uid))}
     (fn [{:keys [form-id handle-submit] :as props}]

       [:form.space-y-4
        {:id        form-id
         :on-submit handle-submit}
        [navigation booking-state]

        (rs/match-state booking-state
          [:s.booking :s.boat-picker]
          (boat-picker main-m props)

          [:s.booking :s.confirm]
          [confirmation
           props
           {:boat-db  boat-db
            :selected selected
            :state    my-state}]

          [:div "?"])

        (rs/match-state booking-state
          [:s.booking :s.boat-picker]
          [:div.flex.justify-between
           [:div]
           (button-submit #(state/send :e.confirm-booking) "Bekreft")]
          [:div "?"])])]))
