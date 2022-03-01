(ns booking.content.booking-blog
  (:require [schpaa.style :as st]
            [db.core :as db]
            [reagent.core :as r]
            [nrpk.database]
            [tick.core :as t]
            [times.api :as ta]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.debug :as l]
            [eykt.content.rapport-side]
            [schpaa.style.ornament :as sc]))

(defn not-yet-seen [last-visit-dt [k v]]
  (if last-visit-dt
    (try
      (if-let [article-dt (some-> (:date v) t/instant)]
        (not (t/<= article-dt last-visit-dt))
        false)
      (catch js/Error e (prn (.-message e)) false))
    true))

(defn last-seen [uid]
  (let [path ["booking-posts" "receipts" (name uid)]
        last-seen (db/on-value-reaction {:path path})]
    (some-> @last-seen (last) (val) (t/instant))))

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

(defn mark-last-seen [uid]
  (when-not (nil? uid)
    (let [path ["booking-posts" "receipts" uid "articles"]
          last-seen (last @(db/on-value-reaction {:path ["booking-posts" "articles"]}))]
      (when last-seen
        (db/database-update {:path  path
                             :value {:id   (key last-seen)
                                     :date (str (t/now))}})))))

(defn mark-last-seen' [id uid date]
  (when-not (nil? uid)
    (let [path ["booking-posts" "receipts" uid]
          last-seen (first (last @(db/on-value-reaction {:path ["booking-posts" "articles"]})))]
      (when last-seen
        (db/database-update {:path  path
                             :value {"articles" {:id   id
                                                 :date date}}})))))

(defn preview [{:keys [content date uid]}]
  (eykt.content.rapport-side/preview "b" date content))

(defn lineitem [{:keys [content item-id uid' kv date-of-last-seen id-of-last-seen date uid] :as m}]
  ;(tap> [item-id id-of-last-seen])

  [sc/surface-c
   (let [bg (if (pos? (compare (str item-id) (str id-of-last-seen))) :bg-white :bg-transparent)]
     [:div
      ;{:class bg}
      (preview m)

      #_(if (some? uid')
          [:div.grid.gap-2 {:style {:grid-template-columns "min-content min-content 1fr 1fr 1fr "}}
           [:div {:on-click #(mark-last-seen' item-id uid (str (t/now)))} "set"]
           ;[:div (nrpk.database/get-username-memoed uid')]
           [:div (compare item-id id-of-last-seen)]
           [:div (str (not-yet-seen (t/now) kv))]

           ;[:div (str (t/now))]

           #_[:div (t/date-time (t/instant date))]
           (let [beginning date-of-last-seen
                 end (t/date-time (t/instant date))]
             (if (and beginning end)
               (if (t/<= end beginning)
                 [:div {:class ["text-black/20"]}
                  (t/hours (t/duration (tick.alpha.interval/new-interval end beginning)))]
                 [:div {:class ["text-black/100"]}
                  (t/hours (t/duration (tick.alpha.interval/new-interval beginning end)))])
               [:div ">?"]))
           [:div.tabular-nums (apply ta/format "%02d -- %02d:%02d" ((juxt t/day-of-month t/hour t/minute) (t/date-time (t/instant date))))]]
          [:div
           (if (some? uid')
             (str "> " (last-seen (keyword uid)))
             (str "." uid'))
           (if (not-yet-seen (t/now) kv)
             [:div.flex.gap-4
              [:div "not yet"]
              [:div date]]
             [:div "yet"])])])])

(defn initial-page [{:keys [path date-of-last-seen id uid pointer]}]
  (let []                                                   ;{:keys [bg fg- fg+ fg hd p p- he]} (st/fbg' :surface)]
    [:div
     ;{:class bg}
     (into [:div.p-2.space-y-2]
           (concat
             (for [[k {:keys [content date] :as e} :as kv] (take @pointer (reverse @(db/on-value-reaction {:path path})))
                   :let [uid' (:uid e)]]
               (lineitem (assoc (select-keys e [])
                           :content content
                           :item-id (name k)
                           :date-of-last-seen date-of-last-seen
                           :id-of-last-seen id
                           :uid uid
                           :kv kv
                           :date date
                           :uid' (:uid e))))
             [[:div.h-1]
              [:div.flex.flex-center.h-16 {:on-click #(swap! pointer (fn [e] (+ e 5)))
                                           :class    ["bg-black/10" :rounded]} "Vis eldre"]]))]))

(def fsm
  {:initial :s.startup
   :on      {:e.empty {:target [:. :s.empty]}
             :e.run   {:target [:. :s.initial]}}
   :states  {:s.startup {} #_{:on {:e.startup [{:guard  (fn [st ev]
                                                          (let [data (-> ev :data :data)]
                                                            (tap> [:A data (some? data)])
                                                            (some? data)))
                                                :target :s.initial}
                                               {:target :s.empty}]}}
             :s.initial {}
             :s.empty   {}}})

(defn booking-blog [{:keys [path uid fsm]}]
  ;intent When opening this section, mark as read!
  (let [pointer (r/atom 5)
        datas (db/on-value-reaction {:path path})]
    (r/create-class
      {:display-name
       "booking-blog"

       :component-did-update
       (fn [this old-argv old-state snapshot]
         (let [datas (db/on-value-reaction {:path path})]
           (tap> [:component-did-update @datas])
           (if (empty? @datas)
             (send :e.empty)
             (send :e.initial))))

       :component-did-mount
       (fn [_]
         ;(tap> {:datas @datas})

         ;(rf/dispatch [::rs/start])
         #_(tap> ":component-did-mount"))

       :reagent-render
       (fn [{:keys [path uid]}]
         (let [{:keys [bg fg- fg+ fg hd p p- he]} (st/fbg' :form)
               data (sort-by (comp :number val) < (logg.database/boat-db))
               {date-of-last-seen :date id :id} @(db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
               date-of-last-seen (some-> date-of-last-seen t/instant t/date-time)
               list-of-posts (db/on-value-reaction {:path path})
               *fsm-rapport (:blog @(rf/subscribe [::rs/state :main-fsm]) :s.startup)]
           [:div
            ;[l/ppre datas]
            ;[:div *fsm-rapport]

            (rs/match-state *fsm-rapport
              [:s.startup]
              [initial-page {:path              path
                             :uid               uid
                             :id                id
                             :pointer           pointer
                             :date-of-last-seen date-of-last-seen}]

              [:s.empty]
              [:div "EMPTY"]

              [:s.initial]
              [initial-page {:path              path
                             :uid               uid
                             :id                id
                             :pointer           pointer
                             :date-of-last-seen date-of-last-seen}]

              [:div "other " *fsm-rapport])]))})))

