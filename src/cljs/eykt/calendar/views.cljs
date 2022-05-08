(ns eykt.calendar.views
  (:require [schpaa.debug :as l]
            [lambdaisland.ornament :as o]
            [eykt.calendar.actions :as actions]
            [times.api :as ta]
            [db.core :as db]
            [tick.core :as t]
            [re-frame.core :as rf]
            [times.api :refer [format]]
            [schpaa.button :as bu]
            [schpaa.style :as st]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [schpaa.style.ornament :as sc]))

(o/defstyled user-cell :div
  [:&
   :flex
   :w-24
   :items-center

   :truncate
   :h-10
   :p-2
   {:user-select   :none
    :border-radius "var(--radius-0)"}])


(o/defstyled avail-user-slot :div
  [:&
   user-cell
   :h-full
   {:color            "var(--text1)"

    :box-shadow       "var(--inner-shadow-1)"
    :background-color "var(--floating)"}])

(o/defstyled taken-user-slot :div
  [:&
   :h-full
   :truncate
   user-cell
   {:box-shadow       "var(--shadow-1)"
    :color            "var(--text1)"
    :background-color "var(--toolbar)"
    :cursor           :pointer}
   [:&.owner {:background "var(--brand1)"
              :color      "var(--brand1-copy)"}]
   [:&:active {:background "var(--toobar-)"}
    [:&.owner {:background "var(--brand2)"
               :color      "var(--brand2-copy)"}]]])

(o/defstyled time-slot :div
  :w-32
  :h-10
  :flex :items-end)

(defn invert [data]
  (->> (vals data)
       (reduce (fn [a kvs]
                 (let [z (reduce (fn [a' [k v]]
                                   (update a' k (fnil conj {}) v)) a kvs)]
                   (conj a z))) {})
       (map (fn [[k v]] [k (into [] (sort-by val < v))]))))

;region calendar elements/components

(defn- week-component [dt description]
  [sc/row-fields {:style {:width           "100%"
                          :align-items     :end
                          :justify-content :between}}
   [sc/title2 (booking.flextime/relative-time dt ta/calendar-date-format)]
   [sc/row-ec
    [sc/small1 description]
    [sc/small0 (str "UKE " (times.api/week-number dt))]]])

(defn- badge-text [this-uid owner?]
  (let [{:keys [alias navn]} (user.database/lookup-userinfo (name this-uid))
        badge-text (cond
                     (not (empty? alias)) alias
                     (not (empty? navn)) navn
                     :else "...")]
    [sc/text1 {:style {:color (when owner? :unset)}
               :class [:truncate]}
     badge-text]))

;endregion

(defn- occupied-slot [uid [this-uid]]
  (let [owner? (= this-uid uid)
        path (if owner? [:r.min-status] [:r.dine-vakter {:id this-uid}])]
    [taken-user-slot
     {:class    [(when owner? :owner)]
      :on-click #(rf/dispatch [:app/navigate-to path])}
     [badge-text this-uid owner?]]))

(defn command [uid base section slots-free starttime-key]
  [:div.h-10.flex.items-center
   ;fix: duplicated owner?
   (let [owner? (get-in base [section uid starttime-key])
         path {:uid uid :section section :timeslot starttime-key}]
     (if (or owner? (pos? slots-free))
       (if owner?
         [hoc.buttons/round-danger-pill
          {:class    [:round :shrink-0]
           :on-click #(actions/delete path)}
          (sc/icon ico/trash)]
         [hoc.buttons/round-cta-pill
          {:class    [:round :shrink-0]
           :type     :button
           :on-click #(actions/add path)}
          (sc/icon ico/plus)])
       [:div.w-8]))])

(defn table [{:keys [base data]}]
  (let [show-only-available? @(schpaa.state/listen :calendar/show-only-available)
        uid @(rf/subscribe [:lab/uid])
        kald-periode? (:kald-periode (user.database/lookup-userinfo uid))
        uid (keyword uid)]
    (into [:div.space-y-4]
          (for [each (filter (fn [[{:keys [slots kald-periode]}]] (and (pos? slots) (if kald-periode kald-periode? true))) data)
                ;todo superslow
                [{:keys [description dt slots section]} group] (group-by #(select-keys % [:dt :description :section :slots :kald-periode]) each)
                :let [dt (t/date dt)
                      alle-regs-i-denne-periodegruppen (invert (get base section) #_(clojure.walk/keywordize-keys (get base section)))]]
            ^{:key dt} (into [:div.w-full.space-y-2
                              [week-component dt description]]
                             (for [{:keys [starttime endtime]} (sort-by :starttime < group)
                                   :let [starttime-key (-> (t/at dt starttime) str keyword)
                                         starttime' (str (t/at dt (t/time starttime)))
                                         slots-on-this-eykt (first (filter (fn [[k _v]] (= (name k) starttime')) alle-regs-i-denne-periodegruppen))
                                         slots-free (- slots (count (second slots-on-this-eykt)))]
                                   :when (if show-only-available? (or (pos? slots-free) (get-in base [section uid starttime-key])) true)]
                               [:div.ml-4
                                [sc/col-space-2
                                 [sc/row-sc-g4-w {:style {:flex-wrap   :nowrap
                                                          :align-items "start"}}
                                  [sc/col-space-1 {:class [:justify-center :h-10]
                                                   :style {:white-space :nowrap
                                                           :width       "3.5rem"}}
                                   [sc/text2 "kl. " (t/hour starttime) "–" (t/hour endtime)]]
                                  [command uid base section slots-free starttime-key]
                                  ; for every line
                                  (into [sc/row-sc-g1-w {:style {:flex "1 0 0"}}]
                                        (concat
                                          (map #(occupied-slot uid %) (last slots-on-this-eykt))
                                          (map #(avail-user-slot "Ledig") (range slots-free))))]]]))))))




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
       [l/pre
        lookup-duration
        '---
        corrected]
       ;[l/ppre-x (flatten (filter (comp pos? :slots first) data))]
       ;[l/ppre-x base]
       ;[l/ppre-x root]
       ;[l/ppre-x filtered]
       #_[l/pre corrected]]]
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

(comment
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

  (comment
    (do
      (invert {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
               {:2022-05-08T11:00
                {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:19.398"},
                :2022-05-08T14:00
                {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:42:22.729"}},
               :testRi0icn4bbffkwB3sQ1NWyTxoGmo1
               {:2022-05-08T11:00
                {:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}}})))

  (comment
    (do
      #_(reduce (fn [a kv] (assoc a (key kv) (val kv)))
                {}
                [{:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}
                 {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T14:45:50.061"}])
      (reduce (fn [a kv] (assoc a (key kv) (val kv)))
              {}
              [{:testRi0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T12:40:53.392"}
               {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1 "2022-04-19T14:45:50.061"}]))))

