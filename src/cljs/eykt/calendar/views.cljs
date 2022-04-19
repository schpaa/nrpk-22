(ns eykt.calendar.views
  (:require [schpaa.debug :as l]
            [eykt.calendar.actions :as actions]
            [times.api :as ta]
            [db.core :as db]
            [tick.core :as t]
            [re-frame.core :as rf]
            [times.api :refer [format]]
            [schpaa.button :as bu]
            [schpaa.style :as st]))

(comment
  (let [data {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
              {:2022-05-08T11:00
               {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:19.398"},
               :2022-05-08T14:00
               {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:22.729"}},
              :testRi0icn4bbffkwB3sQ1NWyTxoGmo1
              {:2022-05-08T11:00
               {:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}}}]
    ;(routine data)
    (->> (vals data)
         (reduce (fn [a kvs]
                   ;(prn kvs)
                   (let [z (reduce (fn [a' [k v]]
                                     ;(prn "> " k v)
                                     (update a' k (fnil conj []) v)) a kvs)]
                     ;(prn "z: " z)
                     (conj a z))) {})
         (map (fn [[k v]] [k (vec (sort-by vals > v))]))
         (into {}))))

(comment
  (let [data {"Kald periode helg"
              {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
               {:2022-05-08T11:00
                {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:19.398"},
                :2022-05-08T14:00
                {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:22.729"}},
               :testRi0icn4bbffkwB3sQ1NWyTxoGmo1
               {:2022-05-08T11:00
                {:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}}},
              "Kald periode ukedag"
              {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
               {:2022-05-11T18:00
                {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:43:17.358"}}}}]
    (reduce-kv (fn [m k v]
                 (prn (vals v))
                 (assoc m k
                          (reduce-kv (fn [m' k' v']
                                       (prn v')
                                       m' #_(assoc m' k' v')) {}
                                     (vals v)))

                 #_(assoc m k (reduce-kv (fn [m k v] m) (get m k) (vals v))))
               {} data)))

(do
  (defn invert [data]
    (->> (vals data)
         (reduce (fn [a kvs]
                   ;(prn kvs)
                   (let [z (reduce (fn [a' [k v]]
                                     (prn "> " k (seq v))
                                     (update a' k (fnil conj {}) v)) a kvs)]
                     ;(prn "z: " z)
                     (conj a z))) {})
         (map (fn [[k v]] [k (into [] (sort-by val < v))]))))
  (invert {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
           {:2022-05-08T11:00
            {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:19.398"},
            :2022-05-08T14:00
            {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:22.729"}},
           :testRi0icn4bbffkwB3sQ1NWyTxoGmo1
           {:2022-05-08T11:00
            {:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}}}))

(comment
  (do
    #_(reduce (fn [a kv] (assoc a (key kv) (val kv)))
              {}
              [{:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}
               {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T14:45:50.061"}])
    (reduce (fn [a kv] (assoc a (key kv) (val kv)))
            {}
            [{:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}
             {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T14:45:50.061"}])))


(defn table [{:keys [base data]}]
  (let [uid @(rf/subscribe [::db/root-auth :uid])
        uid (keyword uid)
        {:keys [bg+ bg fg+ fg fg- p- p]} (st/fbg' :calender-table)]

    [:div.space-y-2.bg-white
     ;[l/ppre-x base]
     ;[l/ppre-x (vals base)]
     ;[l/ppre-x ">>> " (invert base)]

     #_[l/ppre-x (reduce-kv (fn [m k v]
                              #_(reduce (fn [m kv] m) v v)
                              (assoc m k (reduce (fn [m kv] m) (get m k) (vals v))))
                            {} base)]
     #_[l/ppre-x (reduce-kv (fn [m k v]
                              (update m k #(reduce-kv (fn [m' k' v'] assoc m' k' v') {} (vals v))))
                            {} base)]
     ;[l/ppre-x data]

     #_(into [:div.space-y-1]
             (for [e (keys base)
                   :let [e (keyword e)]]
               (schpaa.components.views/modern-checkbox'
                 {:set-details #(schpaa.state/change [:opt1 e] %)
                  :get-details #(-> (schpaa.state/listen [:opt1 e]) deref)}
                 (fn [checkbox]
                   [:div.flex.items-center.justify-end.gap-2.w-full.px-2
                    [:div.text-base.font-normal.space-y-0
                     [:div {:class (concat fg p)} e]]
                    checkbox]))))

     ;[l/ppre-x base]
     [:div.space-y-4.bg-white
      (doall (for [each (filter (fn [[e]] (pos? (:slots e))) data)
                   [{:keys [description dt slots] :as b} group] (group-by #(select-keys % [:dt :description :slots]) each)]
               (let [alle-regs-i-denne-periodegruppen
                     (invert (filter (fn [[k v]] true #_(= 1 (key v))) (clojure.walk/keywordize-keys (get base description))))]
                 [:div {:class fg-}
                  [:div (ta/date-format (t/date dt))]
                  [:div description]
                  ;[l/ppre-x b group] ;sier noe om det definerte
                  ;[l/ppre-x #_(t/at (t/date dt) (t/time (:starttime group))) group] ;sier noe om det definerte
                  ;[l/ppre-x (get base description)]
                  ;[l/ppre-x (vals (get base description))]
                  ;[l/ppre-x "::" dt]
                  ;[l/ppre-x ">>" alle-regs-i-denne-periodegruppen]

                  ;;for hver økt pr dag
                  (doall (for [{:keys [starttime endtime]} (sort-by :starttime < group)
                               :let [key (-> (t/date dt) (t/at starttime) str keyword)
                                     slots-taken (sort-by second < (get-in base [description key]))
                                     slots-on-this-eykt (first (filter (fn [[k v]]
                                                                         (= (name k)
                                                                            (str (t/at (t/date dt) (t/time starttime)))))
                                                                       alle-regs-i-denne-periodegruppen))
                                     count-slots-on-this-eykt (count (second slots-on-this-eykt))
                                     n (- slots count-slots-on-this-eykt)]]

                           [:div.space-y-1

                            #_[l/ppre-x
                               ;(map name (keys alle-regs-i-denne-periodegruppen))
                               slots-on-this-eykt
                               count-slots-on-this-eykt]
                            ;[l/ppre-x "TM>> " (t/at (t/date dt) (t/time starttime))]
                            ;[l/ppre-x slots count-slots-on-this-eykt slots-on-this-eykt]
                            [:div "Plasser: " slots " (" (if (pos? n) (str n " ledig") "ingen ledige") ")"]
                            ;[l/ppre [description key uid]]
                            [:div.flex.gap-2
                             ;(str (t/at (t/date dt) (t/time starttime))) " :: "
                             [:div (str starttime) "–" (str endtime)]

                             (if (get-in base [description uid key])
                               [bu/danger-button-small {:type     :button
                                                        :on-click #(actions/delete {:uid uid :group description :timeslot key})} "fjern"]
                               (if (pos? n)
                                 [bu/regular-button-small {:type     :button
                                                           :on-click #(actions/add {:uid uid :group description :timeslot key})} "legg til"]))

                             #_[l/ppre-x ">> "
                                ;slots-on-this-eykt
                                (last slots-on-this-eykt)]
                             (into [:div.flex.gap-2]
                                   (concat
                                     (map (fn [[idx [a påmeldt-dato]]]
                                            ;[l/ppre-x idx k v]
                                            [:div.flex.gap-2.w-16.overflow-clip
                                             {:class (concat bg (if (< (dec slots) idx) [:text-red-500] fg))}
                                             [:div {:class (if (= (keyword uid) a) (concat bg+ fg+))}
                                              [:div.truncate a]
                                              [:div {:class p-} (ta/datetime-format (t/date-time påmeldt-dato))]]])
                                          (map-indexed vector (last slots-on-this-eykt)))
                                     (map (fn [e] [:div.w-16 {:class bg} "ledig"])
                                          (range n))))]]))])))]]))

(comment
  (let [data [{:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}
              {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T14:45:50.061"}]]
    (map vec data)))

; region

(defn calendar-month-header [ldom]
  (let [{:keys [hd bg]} (st/fbg' :form)]
    [:div.sticky.top-28.h-12
     {:class (concat bg hd)}
     (str (t/year ldom) " " (times.api/month-name ldom))]))

(defn day-cell-with-content [{:keys [slots base lookup-date uid]}]
  (let [{:keys [hd bg bg+ bg- fg p p- hd]} (st/fbg' :form)]
    [:div.text-xs.overflow-clip
     ;intent HEADER
     [:div.col-span-full.h-8
      [:div.flex.h-full
       {:class (concat p bg fg [:justify-center :items-center])}
       (str (t/format "d" (:dt (first slots))))]]

     ;intent DESC
     [:div.col-span-full
      {:class (concat p- bg-)}
      (:description (first slots))]

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
               [:div {:class [:bg-white]} e])]
            (for [e (range slot)]
              [:div
               {:class
                (if (< e cnt)
                  :bg-gray-400 :bg-white)}])))])]))

(defn calendar [{:keys [base data]}]
  ;(tap> {:data data})
  [:div
   ;[l/ppre-x base]
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
                 day-number])))]])])])

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
