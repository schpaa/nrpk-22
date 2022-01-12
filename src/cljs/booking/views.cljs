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
            [booking.database]
            [fork.re-frame :as fork]
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
       ;[:div.text-xs.text-red-500 date " " relation' " " (str today)]
       #_[:div.gap-2.h-12.items-center.p-2.space-y-2
          {:class (concat [:hidden :mob:flex] cell-class)}
          [:div.w-12.truncate.shrink-0 (straight-date date)]
          [:div.w-6 day-name]
          [:div.w-10.shrink-0.text-center checkout-time]
          [:div.w-8.flex.justify-center [:div.w-5.h-5 [icon/adapt (if (pos? (rand-int 2)) :arrow-right :moon-2)]]]
          [:div.w-10.shrink-0.text-center checkin-time]
          (when-not hide-name?
            [:div.grow.flex.justify-end
             {:class (:fg color-map)}
             navn])
          [:div.w-12.shrink-0.justify-end
           {:class (concat [:font-bold] (:fg color-map))}
           (if (and (vector? selected)
                    (< 1 (count selected)))
             [:div "KURS"]
             [:div (first selected)])]]

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

(defn my-booking-form [{:keys [state touched errors values set-values handle-change handle-blur form-id handle-submit dirty readonly?] :as props}]
  [:form.space-y-4
   {:id        form-id
    :on-submit handle-submit}
   [:div.flex.flex-col.gap-4
    ;[l/ppre @views/my-state]
    [:div.flex.gap-x-2.flex-wrap
     (fields/date (assoc (fields/normal-field props)
                    :handle-change (fn [evt]
                                     (let [v (-> evt .-target .-value)]
                                       (rf/dispatch [:exp/set-date-filter v]))
                                     (handle-change evt)))
                  "Dato" :date)

     (fields/button #(let [v (t'/>> (t'/date) (t'/new-period 0 :days))]
                       (rf/dispatch [:exp/set-date-filter v])
                       (set-values {:date v}))
                    nil "I dag")

     (fields/button #(set-values {:date (t'/>> (t'/date) (t'/new-period 1 :days))})
                    nil "I morgen")]

    [:div.flex.gap-x-2.flex-wrap
     (fields/time (fields/small-field props) "UT Kl" :start-time)

     (let [next-hour (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                         (t'/truncate :hours))]
       (fields/button #(set-values {:start-time next-hour}) nil (str next-hour)))

     (fields/button #(set-values {:start-time (t'/new-time 12 0)}) nil "12:")
     (fields/button #(set-values {:start-time (t'/new-time 15 0)}) nil "15:")
     (fields/button #(set-values {:start-time (t'/new-time 17 0)}) nil "17:")]

    (fields/checkbox props "Overnatting" :sleepover)

    [:div.flex.gap-x-2.flex-wrap
     (fields/time (fields/small-field props) "Tilbake kl" :end-time)
     (fields/button #(set-values {:end-time (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                                                (t'/truncate :hours))}) nil "Snarest")
     (fields/button #(set-values {:end-time (t'/new-time 17 0)}) nil "17:")
     (fields/button #(set-values {:end-time (t'/new-time 19 0)}) nil "19:")]

    (fields/textarea (fields/full-field props) "Beskjed til tur-kamerater" :description)]

   (let [ready? (and (empty? errors)
                     (seq @(values :selected)))]
     [:div.py-4
      (if readonly?
        [:div.flex.gap-4.justify-end
         [:button.btn.btn-free {:type     :button
                                :on-click #(state/send :e.restart)} "Tilbake"]
         [:button.btn.btn-free.btn-danger.text-black {;:disabled (not (some? dirty))
                                                      :type :submit} "Avlys booking"]]
        [:div.flex.gap-4.justify-end
         [:button.btn.btn-free {:type     :button
                                :on-click #(state/send :e.restart)} "Avbryt"]
         [:button.btn.btn-free.btn-cta.text-black {:disabled (not ready?)
                                                   :type     :submit} "Bekreft"]])])])

;region boat-picking

(defn list-line [{:keys [id  brand on-click remove? data]}]
  (let [{:keys [text brand ] } data]
    [:div.px-4.py-2.xh-12.flex.items-center.justify-between.gap-4
     {:class    (concat
                  [:first:rounded-t
                   :last:rounded-b]
                  ["bg-black/10"
                   "hover:bg-black/10"])

      :on-click #(on-click id)}
     [:div.font-bold id]
     [:div.flex.flex-col.flex-grow
      [:div.text-sm brand]
      [:div text]]
     (if remove? [icon/small :cross-out])]))

(defn pick-list [{:keys [selected on-click boat-db]}]
  (if (seq @selected)
    [:div.space-y-px
     (for [id @selected]
       [list-line
        {:remove?  true
         :on-click #(on-click id)
         :id       id
         :data  (get boat-db id)}])]

    [:div.h-12.flex.items-center.justify-center.bg-white "Velg båt"]))

(defn boat-list [{:keys [boat-db on-click]}]
  (if (seq boat-db)
    [:div.space-y-px
     (for [[id data] boat-db]
       [list-line
        {:on-click on-click
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
   {:type :button
    :on-click a}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn button-submit [a c]
  [:button.btn-narrow.btn-free.h-10.btn-cta
   {:type :submit
    :on-click a}
   (if (keyword? c) [schpaa.icon/small c] c)])

(defn adjust [f field]
  (fn [] (swap! views/my-state update-in [:values field] #(f (t/time %) (t/new-duration 1 :hours)))))

(defn navigation [booking-state]
  (rs/match-state booking-state
    [:s.booking :s.initial]
    [:div.flex.justify-between
     [:div]
     (button #(state/send :e.pick-boat) "Neste")]

    [:s.booking :s.boat-picker]
    [:div.flex.justify-between
     (button #(state/send :e.edit-basics) "Forrige")
     (button #(state/send :e.confirm) "Neste")]

    [:s.booking :s.confirm]
    [:div.flex.justify-between
     (button #(state/send :e.pick-boat) "Forrige")
     (button-submit #(state/send :e.confirm-booking) "Bekreft")]))

(defn time-navigator []
  [:div
   [:div.flex.flex-col.items-center.gap-1

    [:div.flex.gap-1
     (button #() :rotate-left)
     (button (adjust t/<< :start-time) :minus)
     (fields/time {:naked?        true
                   :values        (fn [] (-> @views/my-state :values :start-time))
                   :handle-change (fn [e] (let [v (-> e .-target .-value)]
                                            (swap! views/my-state assoc-in [:values :start-time] v)))} "" :name)
     (button (adjust t/>> :start-time) :plus)]

    [:div.flex.gap-1
     (button (adjust t/<< :end-time) :minus)
     (fields/time {:naked?        true
                   :values        (fn [] (-> @views/my-state :values :end-time))
                   :handle-change (fn [e] (let [v (-> e .-target .-value)]
                                            (swap! views/my-state assoc-in [:values :end-time] v)))} "" :name)
     (button (adjust t/>> :end-time) :plus)
     (button #() :rotate-right)]]])

(defn confirm-form [{:as props} {:keys [state selected boat-db]}]
  (let [{:keys [sleepover date start-time end-time]} (-> @state :values)]
    [:div.space-y-4
     ;[l/ppre-x @selected @state]
     [:div date]
     [:div start-time]
     [:div end-time]
     [:div (if sleepover "Overnatting")]
     (pick-list
       {:boat-db  boat-db
        :selected selected
        :on-click #(swap! selected disj %)})
     (fields/textarea (fields/full-field props) "Beskjed til tur-kamerater" :description)]))

(defn boat-picker [{:keys [uid cancel on-submit booking-data' my-state boat-db selected]}]
  [:div.space-y-4

   [time-navigator]

   [:div.space-y-4
    (pick-list
      {:boat-db  boat-db
       :selected selected
       :on-click #(swap! selected disj %)})
    (boat-list
      {:boat-db  (remove (fn [[k _v]] (some #{k} @selected)) boat-db)
       :selected selected
       :on-click #(swap! selected conj %)})]])

(defn initial-booking-state [{:keys [handle-change  set-values] :as props}]
  [:div.space-y-4
   [:div.flex.flex-col.gap-4
    [:div.flex.gap-x-2.flex-wrap
     (fields/date (assoc (fields/normal-field props)
                    :handle-change (fn [evt]
                                     (let [v (-> evt .-target .-value)]
                                       (rf/dispatch [:exp/set-date-filter v]))
                                     (handle-change evt)))
                  "Dato" :date)

     (fields/button #(let [v (t'/>> (t'/date) (t'/new-period 0 :days))]
                       (rf/dispatch [:exp/set-date-filter v])
                       (set-values {:date v}))
                    nil "I dag")

     (fields/button #(set-values {:date (t'/>> (t'/date) (t'/new-period 1 :days))})
                    nil "I morgen")]

    [:div.flex.gap-x-2.flex-wrap
     (fields/time (fields/small-field props) "UT Kl" :start-time)

     (let [next-hour (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                         (t'/truncate :hours))]
       (fields/button #(set-values {:start-time next-hour}) nil (str next-hour)))

     (fields/button #(set-values {:start-time (t'/new-time 12 0)}) nil "12:")
     (fields/button #(set-values {:start-time (t'/new-time 15 0)}) nil "15:")
     (fields/button #(set-values {:start-time (t'/new-time 17 0)}) nil "17:")]

    (fields/checkbox props "Overnatting" :sleepover)

    [:div.flex.gap-x-2.flex-wrap
     (fields/time (fields/small-field props) "Tilbake kl" :end-time)
     (fields/button #(set-values {:end-time (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                                                (t'/truncate :hours))}) nil "Snarest")
     (fields/button #(set-values {:end-time (t'/new-time 17 0)}) nil "17:")
     (fields/button #(set-values {:end-time (t'/new-time 19 0)}) nil "19:")]]])


(defn booking-form [{:keys [uid cancel on-submit booking-data' my-state boat-db selected] :as main-m}]
  [fork/form {:initial-values    {:date        (t/date "2022-10-12")
                                  :start-time  (t/time "11:00")
                                  :end-time    (t/time "14:00")
                                  :sleepover true
                                  :description "Ugh"}
              :validation        booking-validation
              :form-id           :booking-form
              :state             my-state
              :prevent-default?  true
              :clean-on-unmount? true
              :keywordize-keys   true
              :on-submit         #(on-submit (assoc-in % [:values :uid] uid))}
   (fn [{:keys [state touched errors values set-values handle-change handle-blur form-id handle-submit dirty readonly?] :as props}]
     (let [*st-all (rf/subscribe [::rs/state :main-fsm])
           booking-state (:booking @*st-all)]

       [:form.space-y-4
        {:id        form-id
         :on-submit handle-submit}

        ;intent Navigation
        [navigation booking-state]

        ;[l/ppre @views/my-state]

        (rs/match-state booking-state
          [:s.booking :s.initial]
          (initial-booking-state props)

          [:s.booking :s.boat-picker]
          (boat-picker main-m)

          [:s.booking :s.confirm]
          (confirm-form
            props
            {:boat-db boat-db
             :selected selected
             :state my-state})

          [:div "OTERH" booking-state]
          #_(rs/match-state (:booking @*st-all)
              [:s.booking :s.boat-picker]
              [:<>
               [:div.flex.gap-4
                [:button.btn.btn-free {:on-click #(state/send :e.show-bookings)} "Vis andres bookinger"]
                [:button.btn.btn-free {:on-click #(state/send :e.pick-boat)} "Vis båtliste"]]
               (views/rounded-view
                 {}
                 [:div.space-y-4
                  [:h2 "Utvalg"]
                  (boat-list
                    {:boat-db  (remove (fn [[k _v]] (some #{k} @selected)) boat-db)
                     :selected selected
                     :on-click #(swap! selected conj %)})])]

              [:s.booking :s.initial]
              [:div.space-y-4
               [:div.flex.justify-between]

               #_(r/with-let [f (rf/subscribe [:exp/filter])]
                   #_(let [data (->> booking-data'
                                     (filter (fn [{:keys [checkin]}]
                                               (if @f
                                                 (pos? (compare checkin @f))
                                                 true)))
                                     #_(reverse))]))
               (into [:div.space-y-px.shadow] (map #(booking-list-item {} (assoc % :on-click line-click))
                                                   (take 1 (reverse booking-data'))))]))]))])

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
  (let [my-data (filter (partial belong-to-me uid) data)]
    [:div

     #_[:div.flex.justify-end.p-4
        {:class ["dark:bg-gray-700" "bg-gray-300"]}
        [:button.btn.btn-free.btn-cta {:on-click #(state/send :e.book-now)} "Ny booking"]]
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
             [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"])]]))

     (r/with-let [show-history (r/atom true)
                  edit (r/atom true)
                  show-all (r/atom false)
                  markings (r/atom {})]

       (let [data (cond->> (->> my-data
                                (filter (partial before today))
                                (sort-by :date >))
                           (not @show-all) (take 5))]
         [:div
          [:div.py-4.flex.justify-end
           [:button.btn.btn-free {:on-click #(do
                                               (swap! show-history not)
                                               (if @show-history (reset! show-all false)))} (if @show-history "Skjul historikk" "Vis historikk")]]
          (if @show-history
            [:div
             (into [:div.space-y-px.shadow]
                   (map (fn [e]
                          (let [idx (:id e)]
                            (booking-list-item
                              {:today        today
                               :hide-name?   true
                               :on-click     #(swap! markings update idx not)
                               :insert-front (when @edit
                                               [:div.flex.items-center.px-2.bg-gray-400
                                                [fields/checkbox {:values        #(get @markings idx)
                                                                  :handle-change #(swap! markings update idx not)} "" :vis-eldre]])}
                              e)))
                        data))
             [:div.flex.justify-between.px-2.py-4.bg-gray-400.sticky.bottom-0
              [:div.flex.gap-2
               [:button.btn-small.btn-free {:on-click #(swap! edit not)} (if @edit "Ferdig" "Rediger")]
               (if @edit
                 (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
                       c (count selected-keys)]
                   [:button.btn-small.btn-danger
                    {:disabled (zero? c)
                     :on-click #(booking.database/delete selected-keys)}
                    (str "Slett " (when (pos? c) c))])
                 [:div])]
              (when-not @show-all
                [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"])]])]))]))
