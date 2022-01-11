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
    :preceded-by {:bg  ["dark:bg-gray-500" "odd:bg-gray-100" "even:bg-gray-50"]
                  :fg  ["dark:text-black" "text-black"]
                  :fg- ["dark:text-black/40" "text-black/40"]}

    :precedes {:bg  ["dark:bg-black/30" "odd:bg-gray-100" "even:bg-gray-50"]
               :fg  ["dark:text-gray-500/50" "text-black/20"]
               :fg- ["dark:text-white/30"]}
    {:bg  ["dark:bg-gray-700" "odd:bg-gray-100" "even:bg-gray-50"]
     :fg  ["dark:text-black" "text-sky-600"]
     :fg- ["dark:text-white"]}))

(defn- booking-list-item [item]
  (let [{:keys [version date bid navn checkin checkout
                uid
                description
                selected start-time end-time on-click]} item
        checkout-dt (t'/at (t/date date) (t/time start-time))
        relation' (relation checkout-dt (t'/new-date 2021 10 20))
        color-map (booking-list-item-color-map relation')]
    #_(if (and date end-time)
        [l/ppre-x "X" version date start-time end-time
         ;(t/at date checkout)
         #_(t'/at (t'/date (str date)) (t'/time (str checkout)))]
        [:div "none"])

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
                     :last:rounded-b
                     :overflow-hidden])
        :on-click #(when on-click (on-click item))}
       [:div.gap-2.h-12.items-center.p-2
        {:class (concat [:hidden :mob:flex] cell-class)}
        [:div.w-12.truncate.shrink-0 (straight-date date)]
        [:div.w-6 day-name]
        [:div.w-10.shrink-0.text-center checkout-time]
        [:div.w-8.flex.justify-center [:div.w-5.h-5 [icon/adapt (if (pos? (rand-int 2)) :arrow-right :moon-2)]]]
        [:div.w-10.shrink-0.text-center checkin-time]
        [:div.grow.flex.justify-end
         {:class (:fg color-map)}
         navn]
        [:div.w-12.shrink-0.justify-end
         {:class (concat [:font-bold] (:fg color-map))}
         (if (and (vector? selected)
                  (< 1 (count selected)))
           [:div "KURS"]
           [:div (first selected)])]]
       [:div
        {:class [:visible :mob:hidden]}
        [:div.select-none.flex.flex-col.p-2.gap-x-2
         {:style {:min-height "3rem"}
          :class cell-class}
         [:div.flex.items-center.justify-between.gap-x-2
          {:class (:fg- color-map)}
          [:div.w-12.truncate.shrink-0 (straight-date date)]
          [:div.w-6 day-name]
          [:div.w-10.shrink-0.text-center checkout-time]
          [:div.w-8.flex.justify-center [:div.w-5.h-5 [icon/adapt (if (pos? (rand-int 2)) :arrow-right :moon-2)]]]
          [:div.w-10.shrink-0.text-center checkin-time]
          [:div.flex-grow.w-full.flex.justify-end
           {:class (concat [:font-bold] (:fg color-map))}
           (if (and (vector? selected)
                    (< 1 (count selected)))
             [:div "KURS"]
             [:div (first selected)])]]
         [:div.flex.w-full.flex-wrap.gap-x-2.items-center.justify-end
          [:div.growx.line-clamp-2x.w-64x.flex.justify-endx
           {:class (:fg color-map)}
           navn]]
         [:div.text-xs description]]]])))

;(defn booking-list [{:keys [booking-data anon-booking-data valid-user?] :as accessors}]
;  (if (valid-user?)
;    (when booking-data
;      (into [:div.space-y-1] (reverse (map #(booking-list-item
;                                              (assoc % :on-click line-click)) (booking-data)))))
;    (when anon-booking-data
;      (into [:div.space-y-1] (reverse (map #(booking-list-item
;                                              (assoc % :on-click line-click)) (anon-booking-data)))))))

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

(defn list-line [{:keys [id text on-click remove?]}]
  [:div.px-4.h-12.flex.items-center.justify-between.gap-4
   {:class    ["bg-white/90"
               "hover:bg-black/10"]
    :on-click #(on-click id)}
   [:div id]
   [:div.grow text]
   (if remove? [icon/small :cross-out])])

(defn pick-list [{:keys [selected on-click boat-db]}]
  (if (seq @selected)
    [:div.space-y-px
     (for [id @selected]
       [list-line
        {:remove?  true
         :on-click #(on-click id)
         :id       id
         :text     (:text (get boat-db id))}])]
    [:div.h-12 "Velg båt"]))

(defn boat-list [{:keys [boat-db on-click]}]
  (if (seq boat-db)
    [:div.space-y-4
     [:div.space-y-px
      (for [[id {:keys [text]}] boat-db]
        [list-line
         {:on-click on-click
          :id       id
          :text     text}])]]
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

(defn booking-form [_]
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        boat-db {1 {:text "a"}
                 2 {:text "b"}
                 3 {:text "c"}
                 4 {:text "d"}
                 5 {:text "e"}
                 6 {:text "f"}}
        selected (r/atom #{})
        loaded-data (r/atom {:selected selected} #_{:date       "2001-10-12"
                                                    :control    "Control"
                                                    :start-time "23:11"})]
    (fn [{:keys [uid cancel on-submit booking-data' my-state]}]
      [:<>
       (views/rounded-view
         {}
         [:h2 "Registrer booking"]
         [fork/form {:initial-values    @loaded-data
                     :validation        booking-validation
                     :form-id           :booking-form
                     :state             my-state
                     :prevent-default?  true
                     :clean-on-unmount? true
                     :keywordize-keys   true
                     :on-submit         #(on-submit (assoc-in % [:values :uid] uid))}
          my-booking-form])
       [:<>
        (views/rounded-view
          {}
          [:div.space-y-4
           [:h2 "Valgt"]
           (pick-list
             {:boat-db  boat-db
              :selected selected
              :on-click #(swap! selected disj %)})])

        (rs/match-state (:booking @*st-all)
          [:s.booking :s.boat-picker]
          [:<>
           [:div.flex.justify-between
            [:button.btn.btn-free {:on-click #(state/send :e.show-bookings)} "Vis andres bookinger"]]
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
           [:div.flex.justify-between
            [:button.btn.btn-free {:on-click #(state/send :e.pick-boat)} "Vis båtliste"]]
           #_(r/with-let [f (rf/subscribe [:exp/filter])]
               #_(let [data (->> booking-data'
                                 (filter (fn [{:keys [checkin]}]
                                           (if @f
                                             (pos? (compare checkin @f))
                                             true)))
                                 #_(reverse))]))
           (schpaa.components.views/rounded-view
             {:dark 1}
             (into [:div.space-y-px] (map #(booking-list-item (assoc % :on-click line-click))
                                          (take 1 (reverse booking-data')))))])]])))

(comment
  ;todo shortcut for this!
  (let [date "2022-01-10" start-time "13:20"
        rel (relation (t'/at (t'/date date) (t'/time start-time)) (t'/date-time))]
    [rel (t'/at (t'/date date) (t'/time start-time))]))
