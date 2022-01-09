(ns schpaa.components.views
  (:require [schpaa.debug :as l]
            [re-frame.db]
            [tick.core :as t']
            [times.api :as ta]
            [fork.re-frame :as fork]
            [reagent.core :as r]
            [eykt.state :as state]
            [re-frame.core :as rf]
            [schpaa.components.fields :as fields]
            [tick.alpha.interval]
            [schpaa.icon :as icon]))

(defn- dark-rounded-view [& content]
  [:div.rounded.p-3.space-y-4
   {:class ["bg-gray-400/50"]}
   (into [:<>] (map identity content))])

(defn rounded-view [opt & content]
  (if (:dark opt)
    [dark-rounded-view content]
    [:div.rounded.p-3.space-y-4
     {:class [:dark:bg-gray-800
              :shadow
              :bg-gray-100
              :dark:text-gray-500]}
     (into [:<>] (map identity content))]))

(defn- str->datetime [dt]
  (let [y (subs dt 0 4)
        m (subs dt 4 6)
        d (subs dt 6 8)
        hh (subs dt 9 11)
        mm (subs dt 11 13)
        ss (subs dt 13 15)]
    (t'/at (t'/new-date y m d) (t'/new-time hh mm ss))))

(defn- straight-from-datetime [instant]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week instant)))]
    (str
      (when (< d 7)
        (subs (nth day-names (dec (t'/int (t'/day-of-week instant)))) 0 3))
      ", "
      (t'/time instant)
      " "
      (t'/day-of-month (t'/date instant))
      "/"
      (t'/int (t'/month (t'/date instant))))))

(defn- straight-to-datetime [instant]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week instant)))]
    (str
      (when (< d 7)
        (subs (nth day-names (dec (t'/int (t'/day-of-week instant)))) 0 3))
      ", "
      (t'/time instant)
      " "
      (t'/day-of-month (t'/date instant))
      "/"
      (t'/int (t'/month (t'/date instant))))))

(defn- straight-datetime [instant]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week instant)))]
    (str
      (when (< d 7)
        (subs (nth day-names (dec (t'/int (t'/day-of-week instant)))) 0 3))
      ", "
      (t'/time instant)
      " "
      (t'/day-of-month (t'/date instant))
      "/"
      (t'/int (t'/month (t'/date instant))))))

(defn- day-name [wd]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week wd)))]
    (subs (nth day-names d) 0 2)))

(defn- straight-date [instant]
  (let [day-names ["søndag" "lørdag" "mandag" "tirsdag" "onsdag" "torsdag" "fredag" "?"]
        d (dec (t'/int (t'/day-of-week instant)))]
    (str
      (t'/day-of-month (t'/date instant))
      "/"
      (t'/int (t'/month (t'/date instant))))))

(defonce my-state (r/atom {}))

(defn- booking-list-item [{:keys [checkin checkout bid navn date] :as item}]
  ;(when (or (nil? checkin) (nil? checkout)))
  (let [checkout-dt (t'/date (str->datetime checkout))
        relation (tick.alpha.interval/relation
                   checkout-dt
                   (t'/new-date 2021 9 14))
        color-map (case relation
                    :preceded-by {:bg  ["dark:bg-gray-500" "bg-gray-100"]
                                  :fg  ["dark:text-black"]
                                  :fg- ["dark:text-black/40" "text-black/40"]}

                    :precedes {:bg  ["dark:bg-black/30"]
                               :fg  ["dark:text-gray-500/50" "text-black/30"]
                               :fg- ["dark:text-white/30"]}
                    {:bg  ["dark:bg-gray-700" "bg-gray-300"]
                     :fg  ["dark:text-black" "text-sky-600"]
                     :fg- ["dark:text-white"]})]
    [:div.select-none.flex.flex-wrap.p-2.gap-x-2
     {:style    {:min-height "3rem"}
      :class    (concat
                  (:bg color-map)
                  (:fg color-map)
                  (if (not= relation :precedes)
                    [:cursor-pointer
                     "hover:bg-white/90"
                     "dark:hover:bg-white/10"]))
      :on-click #(swap! my-state (fn [e]
                                   (-> e
                                       (assoc-in [:values :date] (t'/date (str->datetime checkout)))
                                       (assoc-in [:values :start-time] (t'/time (str->datetime checkout)))
                                       (assoc-in [:values :end-time] (t'/time (str->datetime checkin))))))}
     [:div.flex.items-center.justify-between.gap-x-2
      {:class (:fg- color-map)}
      [:div.w-12.truncate.shrink-0 (straight-date (str->datetime date))]
      [:div.w-6 (day-name (str->datetime date))]
      [:div.w-10.shrink-0.text-center (str (t'/time (str->datetime checkout)))]
      [:div.w-8.flex.justify-center [:div.w-5.h-5 [icon/adapt (if (pos? (rand-int 2)) :arrow-right :moon-2)]]]
      [:div.w-10.shrink-0.text-center (str (t'/time (str->datetime checkin)))]
      [:div.w-12.flex.justify-center
       {:class (concat [:font-bold] (:fg color-map))}
       (if (vector? bid)
         [:div "KURS"]
         [:div bid])]]
     [:div.flex.flex-wrap.gap-x-2.items-center

      [:div.grow.line-clamp-2.truncate
       {:class (:fg color-map)}
       (or navn "skult navn")]]]))

(defn booking-list [{:keys [booking-data anon-booking-data valid-user?] :as accessors}]
  (if (valid-user?)
    (when booking-data
      (into [:div.space-y-1] (reverse (map booking-list-item (booking-data)))))
    (when anon-booking-data
      (into [:div.space-y-1] (reverse (map booking-list-item (anon-booking-data)))))))

(defn booking-header [{:keys [book-now]}]
  (rounded-view
    {}
    [:h2.h-10.flex.items-center "Booking-header med betingelser etc"]
    [:div.flex.justify-end
     [:button.btn.btn-free.btn-cta {:on-click #(book-now)} "Book båt"]]))

(rf/reg-sub :exp/filter (fn [db]
                          (ta/instant->str (:date-filter db))))

(rf/reg-event-db :exp/set-date-filter (fn [db [_ arg]]
                                        (tap> arg)
                                        (assoc db :date-filter arg)))

(defn small-field [props]
  (assoc props :class [:w-28]))

(defn normal-field [props]
  (assoc props :class [:w-40]))

;todo send-server-request
(defn my-booking-form []
  (fn [{:keys [values set-values handle-change handle-blur form-id handle-submit dirty readonly?]
        :as   props}]
    [:div

     [:form.space-y-4
      {:id        form-id
       :on-submit handle-submit}
      [:div.flex.flex-col.gap-4

       [:div.flex.gap-x-4.flex-wrap
        (fields/date (assoc (normal-field props)
                       :handle-change (fn [evt]
                                        (let [v (-> evt .-target .-value)]
                                          (rf/dispatch [:exp/set-date-filter v]))
                                        (handle-change evt)))
                     "Dato" :date)

        #_#_#_(fields/button #(let [v (str (t'/new-date 2021 10 11))]
                                (rf/dispatch [:exp/set-date-filter v])
                                (set-values {:date v}))
                             nil "Check 1")
            (fields/button #(let [v (str (t'/new-date 2021 10 23))]
                              (rf/dispatch [:exp/set-date-filter v])
                              (set-values {:date v}))
                           nil "Check 2")
            (fields/button #(let [v (str (t'/new-date 2021 10 26))]
                              (rf/dispatch [:exp/set-date-filter v])
                              (set-values {:date v}))
                           nil "Check 3")
        (fields/button #(let [v (t'/>> (t'/date) (t'/new-period 0 :days))]
                          (rf/dispatch [:exp/set-date-filter v])
                          (set-values {:date v}))
                       nil "I dag")

        (fields/button #(set-values {:date (t'/>> (t'/date) (t'/new-period 1 :days))})
                       nil "I morgen")]

       [:div.flex.gap-x-4.flex-wrap
        (fields/time (small-field props) "Start" :start-time)

        (let [next-hour (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                            (t'/truncate :hours))]
          (fields/button #(set-values {:start-time next-hour}) nil (str next-hour)))

        (fields/button #(set-values {:start-time (t'/new-time 12 0)}) nil "12:")
        (fields/button #(set-values {:start-time (t'/new-time 15 0)}) nil "15:")
        (fields/button #(set-values {:start-time (t'/new-time 17 0)}) nil "17:")]

       [:div.flex.gap-x-4.flex-wrap
        (fields/time (small-field props) "Slutt" :end-time)

        (fields/button #(set-values {:end-time (-> (t'/>> (t'/time) (t'/new-duration 1 :hours))
                                                   (t'/truncate :hours))}) nil "Snarest")

        (fields/button #(set-values {:end-time (t'/new-time 17 0)}) nil "17:00")
        (fields/button #(set-values {:end-time (t'/new-time 19 0)}) nil "19:00")]]

      (if readonly?
        [:div.flex.gap-4.justify-end
         [:button.btn.btn-free {:type     :button
                                :on-click #(state/send :e.restart)} "Tilbake"]
         [:button.btn.btn-free.btn-danger.text-black {;:disabled (not (some? dirty))
                                                      :type :submit} "Avlys booking"]]
        [:div.flex.gap-4.justify-end
         [:button.btn.btn-free {:type     :button
                                :on-click #(state/send :e.restart)} "Avbryt"]
         [:button.btn.btn-free.btn-cta.text-black {:disabled (not (some? dirty))
                                                   :type     :submit} "Lagre"]])]]))



(defn booking-form [{:keys [uid cancel ok my-form booking-data']}]
  (let [f (rf/subscribe [:exp/filter])
        loaded-data (r/atom {:date       "2001-10-12"
                             :control    "Control"
                             :start-time "23:11"})]
    [:<>
     (rounded-view
       {}
       [:h2.h-10.flex.items-center "Booking form"]
       [fork/form {:initial-values    @loaded-data
                   :form-id           :booking-form
                   :state             my-state
                   ;:path [:some-froim]
                   :prevent-default?  true
                   :clean-on-unmount? true
                   :keywordize-keys   true
                   :on-submit         #(ok)  #_#(state/send :e.store (assoc-in % [:values :uid] (uid)))}
        (my-form {:readonly? false})])

     (r/with-let [f (rf/subscribe [:exp/filter])]
       (let [data (->> booking-data'
                       (filter (fn [{:keys [checkin]}]
                                 (if @f
                                   (pos? (compare checkin @f))
                                   true)))
                       (reverse))]
         (into [:div.space-y-px] (map booking-list-item data))))
     #_#_[l/ppre-x @my-state]
         [l/ppre-x @re-frame.db/app-db]]))

