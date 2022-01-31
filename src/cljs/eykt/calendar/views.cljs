(ns eykt.calendar.views
  (:require [schpaa.debug :as l]
            [eykt.calendar.actions :as actions]
            [db.core :as db]
            [tick.core :as t]
            [re-frame.core :as rf]
            [times.api :refer [format]]
            [schpaa.button :as bu]))

(defn table [{:keys [base data]}]
  (let [uid @(rf/subscribe [::db/root-auth :uid])]
    [:<>
     ;[l/ppre-x base]
     (into [:div.gap-1
            {:style {:grid-template-columns "min-content min-content min-content 1fr 1fr 1fr"}}]
           (doall (for [each (filter (fn [[e]] (< 0 (:slots e))) data)
                        ;cartesian product!
                        [{:keys [description dt slots]} group] (group-by #(select-keys % [:dt :description :slots]) each)]

                    [:div.grid.text-xs.gap-12
                     {:style {:grid-template-columns "2rem 2rem 2rem min-content 5rem 10rem 1fr"}}
                     [:<>
                      (when dt [:div.self-center.justify-self-start (times.api/day-name dt :length 3)])
                      (when dt [:div.self-center.justify-self-end (t/format "d.MM" dt)])
                      [:div.self-center slots]
                      (for [{:keys [starttime endtime]} (sort-by :starttime < group)
                            :let [key (-> (t/date dt) (t/at starttime) str keyword)]]
                        [:<>
                         [:div.bg-whitewhite
                          (if (get (get base key) (keyword uid))
                            [bu/regular-button-small {:on-click #(actions/delete {:uid uid :timeslot key})} "rem"]
                            [bu/regular-button-small {:on-click #(actions/add {:uid uid :timeslot key})} "add"])]

                         [:div (str starttime) "–" (str endtime)]
                         (into [:div.grid.grid-cols-2.gap-x-2
                                {:style {:grid-template-columns "5rem 1fr"}}]
                               (map (fn [[index [uid' påmeldt-dato]]]
                                      (tap> påmeldt-dato)
                                      [:<>
                                       [:div.truncate {:class (if (= uid (name uid')) :text-alt :text-gray-500)} uid']
                                       [:div.truncate
                                        {:class (if (<= slots index) :text-gray-300)}
                                        (t/format "dd/MM HH:mm:ss" (t/date-time (t/instant påmeldt-dato)))]])
                                    (map-indexed vector (sort-by second < (get base key)))))])
                      [:div.truncate description]]])))]))

; region

(defn calendar-month-header [ldom]
  [:div (str (t/year ldom) " " (times.api/month-name ldom))])

(defn day-cell-with-content [{:keys [slots base lookup-date uid]}]
  [:div.-debug.text-xs.overflow-clip
   ;intent HEADER
   [:div.xp-1.col-span-full.h-8
    [:div.flex.h-full
     {:class [:text-xl
              :text-gray-900 :bg-gray-300 :justify-center :items-center]}
     (str (t/format "d" (:dt (first slots))))]]

   ;intent DESC
   [:div.col-span-full.bg-sky-300.text-black.h-10 (:description (first slots))]

   ;intent CONTENT
   (for [[starttime endtime slot] (sort-by :starttime (mapv (juxt :starttime :endtime :slots) slots))
         :let [lookup-date-time (str (-> (t/date (name lookup-date)) (t/at starttime)))]]
     [:<>
      [:div.bg-gray-300.col-span-full.overflow-hidden
       {:class [:place-content-center]}
       (t/format "HH:" starttime) "—" (t/format "HH:" endtime)]
      (let [cnt (count (get base (keyword lookup-date-time)))
            am-i-here? (get-in base [(keyword lookup-date-time) (keyword uid)])
            cnt (if am-i-here? (dec cnt) cnt)]
        (if am-i-here?
          [:<>
           (for [e (range cnt)]
             [:div
              {:class
               (if (< e cnt)
                 :bg-gray-400 :bg-white)}])
           [:div {:class [:bg-danger]}]
           (for [e (range (- (dec slot) cnt))]
             [:div {:class [:bg-white]}])]
          (for [e (range slot)]
            [:div
             {:class
              (if (< e cnt)
                :bg-gray-400 :bg-white)}])))])])


(defn calendar [{:keys [base data]}]
  (let [uid @(rf/subscribe [::db/root-auth :uid])]
    [:div.grid.gap-1
     {:style {:grid-template-columns "repeat(auto-fill,minmax(20rem,1fr))"}}
     (for [[[m ldom] e] (sort (group-by (juxt (comp t/month :dt)
                                              (comp t/last-day-of-month t/date :dt))
                                        (flatten (filter (fn [[e]] (< 0 (:slots e))) data))))
           :let [first-weekday-of-month (dec (t/int (t/day-of-week (t/first-day-of-month ldom))))
                 last-day-of-month (inc (t/day-of-month (t/last-day-of-month ldom)))
                 table (->>
                         (sort (group-by (juxt (comp t/day-of-month :dt) :dt) e))
                         (reduce (fn [a e] (assoc a (ffirst e) (last e))) {}))]]
       [:div
        [calendar-month-header ldom]

        [:div.grid.gap-px.bg-gray-200
         {:style {:grid-template-columns "repeat(7,1fr)"
                  :grid-auto-rows        "minmax(2rem,min-content)"}}
         (when (pos? first-weekday-of-month) [:div {:style {:grid-column-start first-weekday-of-month}}])
         (for [day-number (range 1 last-day-of-month)]
           (let [lookup-date (keyword (str (t/new-date (t/year ldom) (t/int m) day-number)))]
             (if-let [slots (get table day-number)]
               (if (< 0 (:slots (first slots)))
                 [:div.text-black.grid.gap-px
                  {:on-click #(tap> slots)
                   :style    {:grid-template-columns "repeat(auto-fit,minmax(20px,1fr))"
                              :grid-auto-rows        "minmax(8px,max-content)"}}
                  [day-cell-with-content
                   {:slots       slots
                    :base        base
                    :lookup-date lookup-date
                    :uid         uid}]]

                 [:div.bg-gray-100.p-px
                  {:class (if (get base lookup-date) :bg-danger :bg-white)}])
               [:div.p-1.text-xl.flex.h-8
                {:class [:justify-center :items-start (if (get base lookup-date) :bg-alt :bg-gray-300)]}
                day-number])))]])]))

;endregion

(defn hoc3
  "lookup startdatetime->enddatetime,slots,duration-in-minutes"
  [data]
  (->> data
       (filter (comp pos? :slots first))
       flatten
       (reduce (fn [a {:keys [dt starttime slots endtime]}]
                 (let [start (-> (t/date dt) (t/at starttime) str)
                       end (-> (t/date dt) (t/at endtime) str)]
                   (assoc a start
                            {:duration-minutes (-> (t/units (t/between start end)) :seconds (/ 60))
                             :slots            slots
                             :endtime          end})))
               {})))

(defn hoc2
  "in reality a filter to include only valid records, excludes the superfluous registrations"
  [lookup base]
  (->
    (fn [a [k vs]]
      (let [slots (get-in lookup [(name k) :slots] 1)]
        (assoc a k (->> (sort-by second < vs)               ;; første mann til mølla
                        (take slots)                        ;; ta bare de ƒørste
                        (vec)                               ;; omgjør til et map slik at ...
                        (into {})))))
    (reduce {} base)))

(defn- summary-proper [{:keys [base data]}]
  (let [lookup-duration (hoc3 data)
        filtered (hoc2 lookup-duration base)
        corrected (reduce (fn [a [k vs]]
                            (reduce (fn [a' [k'-uid v']]
                                      (update a' k'-uid conj k)) a vs))
                          {}
                          filtered)]
    [:div
     [:div "Summary proper"]
     (into [:div.grid.gap-x-2
            {:style {:grid-template-columns "1fr min-content min-content"}}]
           (-> (fn [[k vs]]
                 [:<>
                  [:div.truncate.xw-20 k]
                  [:div (count vs)]
                  [:div (format "%0.1f" (/ (reduce (fn [a e]
                                                     (+ a (-> (get lookup-duration (name e))
                                                              :duration-minutes))) 0 vs)
                                           60))]])
               (map corrected)))]))

(defn summary
  "some summary"
  [{:keys [root base data]}]
  (let [; lookup table dates->slots, should also have duration?
        lookup-duration (hoc3 data)
        ; check with the slots (required resources) of each date/time to filter out unneeded resources
        filtered (hoc2 lookup-duration base)
        ; compare the result of `corrected` with the contents of `root`
        corrected (reduce (fn [a [k vs]]
                            (reduce (fn [a' [k'-uid v']]
                                      (update a' k'-uid conj k)) a vs))
                          {}
                          filtered)]
    [:<>
     [:details {:open 1}
      [:summary "Details"]
      [:<>
       [l/ppre-x
        lookup-duration
        '---
        corrected]
       ;[l/ppre-x (flatten (filter (comp pos? :slots first) data))]
       ;[l/ppre-x base]
       ;[l/ppre-x root]
       ;[l/ppre-x filtered]
       #_[l/ppre-x corrected]]]
     (into [:div.grid.grid-cols-3]
           (-> (fn [[k vs]]
                 [:<>
                  [:div.truncate.w-20 k]
                  [:div (count vs)]
                  [:div (/ (reduce (fn [a e]
                                     (+ a (-> (get lookup-duration (name e))
                                              :duration-minutes))) 0 vs)
                           60)]])
               (map corrected)))]))
;todo Which of them is overflowed?
