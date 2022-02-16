(ns booking.content.booking-blog
  (:require [schpaa.style :as st]
            [booking.hoc :as hoc]
            [db.core :as db]
            [schpaa.debug :as l]
            [reagent.core :as r]
            [nrpk.database]
            [tick.core :as t]
            [times.api :as ta]))

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

(comment
  (do
    (not-yet-seen (t/instant (t/date-time))
                  [:a {:date "2022-02-15T09:19:27.939Z"}])))

(defn compute-unseen-posts' [uid list-last-seen]
  (let [dt (last-seen uid)
        f (filter #(not-yet-seen dt %) list-last-seen)]
    (count f)))

(defn compute-unseen-posts [uid]
  (let [dt (last-seen uid)
        path ["booking-posts" "articles"]
        last-seen (db/on-value-reaction {:path path})
        f (filter #(not-yet-seen dt %) @last-seen)]
    (tap> @last-seen)
    (count f)))

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

(defn err-boundary
  [& children]
  (let [err-state (r/atom nil)]
    (r/create-class
      {:display-name        "ErrBoundary"
       :component-did-catch (fn [err info]
                              (reset! err-state [err info]))
       :reagent-render      (fn [& children]
                              (if (nil? @err-state)
                                (into [:<>] children)
                                (let [[_ info] @err-state]
                                  [l/ppre-x info])))})))

(defn lineitem [{:keys [item-id uid' kv date-of-last-seen id-of-last-seen date uid]}]
  (tap> [item-id id-of-last-seen])
  (let [bg (if (pos? (compare (str item-id) (str id-of-last-seen))) :bg-white :bg-transparent)]
    [:div
     {:class bg}
     (if (some? uid')
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
          [:div "yet"])])]))

(defn booking-blog [uid]
  ;intent When opening this section, mark as read!
  (r/create-class
    {:display-name
     "booking-blog"

     :component-did-mount
     (fn [_]
       #_(mark-last-seen uid))

     :reagent-render
     (fn [uid]
       (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)
             data (sort-by (comp :number val) < (logg.database/boat-db))
             {date-of-last-seen :date id :id} @(db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
             ;_ (tap> (t/date-time (t/instant date-of-last-seen)))
             date-of-last-seen (some-> date-of-last-seen t/instant t/date-time)
             path ["booking-posts" "articles"]
             list-of-posts (db/on-value-reaction {:path path})]
         [:div
          {:class bg}
          ;(count (filter #(booking.content.booking-blog/not-yet-seen date-of-last-seen %) @list-of-posts))
          #_[l/ppre-x
             {:last-seen            (last-seen uid)
              :compute-unseen-posts (compute-unseen-posts uid)}]
          ;[l/ppre @(db/on-value-reaction {:path ["booking-posts" "articles"]})]
          (into [:div.p-4]
                (for [[k {:keys [content date] :as e} :as kv] @(db/on-value-reaction {:path ["booking-posts" "articles"]})
                      :let [uid' (:uid e)]]
                  ;[:div "X"]
                  (lineitem (assoc (select-keys e [])
                              :item-id (name k)
                              :date-of-last-seen date-of-last-seen
                              :id-of-last-seen id
                              :uid uid
                              :kv kv
                              :date date
                              :uid' (:uid e)))))]))}))

(comment
  (tick.alpha.interval/new-interval (t/at (t/new-date 2022 2 12) (t/new-time 10 0))
                                    (t/date-time (t/now))))

;[booking-blog]

;(comment
;  #_[:div.space-y-px.flex.flex-col
;     {:style {:min-height "calc(100vh - 7rem)"}}
;     (if (seq data)
;       [:div.flex-1
;        [hoc/all-boats
;         {:data     data
;          :details? @(schpaa.state/listen :opt1)}]]
;       [empty-list-message "Båt-listen er tom"])
;     [hoc/all-boats-footer {}]])