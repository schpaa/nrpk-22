(ns eykt.content.mine-vakter
  (:require [db.core :as db]
            [schpaa.style :as st]
            [tick.core :as t]
            eykt.calendar.core))

(defn mine-vakter [{:keys [uid]}]
  (fn [{:keys [uid]}]
    (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :form)
          source (db/on-value-reaction {:path ["calendar"]})

          data (filter (fn [[k v]] (contains? v (keyword uid))) @source)
          rules (group-by :group eykt.calendar.core/short-rules)

          straightened-rules (fn [a [k v]]
                               (if (< 1 (count (seq v)))
                                 (reduce (fn [a [k' v]] (conj a [k k' v])) a v)
                                 (conj a (vec (flatten [k (first v)])))))
          prep-data (into {} (map (fn [[k v]] [k (reduce straightened-rules [] (first (vals v)))]) data))
          lookup (fn [x] (reduce (fn [a e]
                                   (let [f (fn [itm]
                                             (t/hours (t/duration (tick.alpha.interval/new-interval
                                                                    (t/time (:starttime itm))
                                                                    (t/time (:endtime itm))))))]
                                     (assoc a (str (:starttime e)) (f e)))) {} x))
          sum (reduce (fn [a [date rule-refs]]
                        (+ a (reduce (fn [a [group starttime clicked-time]]
                                       (tap> (name starttime))
                                       (+ a (get (lookup (:times (first (get rules group))))
                                                 (name starttime)))) 0 rule-refs)))
                      0 prep-data)]

      [:div.p-4.space-y-4.text-white
       {:class (concat fg bg)}
       [:div "en liste over mine vakter"]
       [:div {:class (concat hd fg+)} sum " timer"]
       ;(l/ppre-x (filter (fn [[k v]] (= k uid) ) listener))

       #_(let [sum (reduce (fn [a e] a) 0 data)] [l/ppre-x data])

       (into [:div] (for [[date vs] data]
                      [:div
                       [:div date]

                       ;[l/ppre-x ">> " (vals vs)]

                       #_[:div.flex.gap-2 (map #(vector :div %) (keys (first (vals vs))))]

                       (for [e (keys (first (vals vs)))
                             z (vals vs)
                             :let [x (:times (first (get-in rules [e])))
                                   lookup (reduce (fn [a e]
                                                    (let [f (fn [itm]
                                                              (t/hours (t/duration (tick.alpha.interval/new-interval
                                                                                     (t/time (:starttime itm))
                                                                                     (t/time (:endtime itm))))))]
                                                      (assoc a (str (:starttime e)) (f e)))) {} x)]]
                         [:<>
                          ;[l/ppre-x x]
                          ;[l/ppre-x (get lookup "11:00" "?")]
                          ;[:div.h-1]
                          ;[l/ppre-x lookup]
                          ;[:div.text-sky-500  "+" e]
                          [:div.text-sky-500 (reduce (fn [a e]
                                                       (+ a (get lookup (name e)))) 0
                                                     (map key (into {} (vals z))))]
                          #_[:div.text-red-500 "k " (str (mapv keys (vals z)))]])]))])))