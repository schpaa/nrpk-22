(ns booking.content.booking-blog
  (:require [schpaa.style :as st]
            [db.core :as db]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [nrpk.database]
            [tick.core :as t]
            [times.api :as ta]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.debug :as l]
            [eykt.content.rapport-side]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]))

(defn not-yet-seen [last-visit-dt [k v]]
  (if last-visit-dt
    (try
      (if-let [article-dt (some-> (:date v) t/instant)]
        (not (t/<= article-dt last-visit-dt))
        false)
      (catch js/Error e (prn (.-message e)) false))
    true))

(defn not-yet-seen' [last-visit-dt {:keys [date]}]
  (if last-visit-dt
    (try
      (if-let [article-dt (some-> date t/instant)]
        (not (t/<= article-dt last-visit-dt))
        false)
      (catch js/Error e (prn (.-message e)) false))
    true))

(defn last-seen [uid]
  (let [path ["booking-posts" "receipts" (name uid)]
        last-seen (db/on-value-reaction {:path path})]
    @last-seen
    #_(some-> @last-seen (last) (val) (t/instant))))

(defn all-articles-by-date [all]
  (let [f (fn [a [k v]]
            (conj a {:date    (:date v)
                     :content (:content v)
                     :id      (name k)}))]
    (sort-by :date > (reduce f [] all))))

(defn count-unseen [uid]                                    ;"piH3WsiKhFcq56lh1q37ijiGnqX2"
  (when uid
    (let [last-visit (-> (last-seen uid) :articles :date)
          all (db/on-value-reaction {:path ["booking-posts" "articles"]})
          unseen (remove (fn [m] (not-yet-seen' last-visit m)) (all-articles-by-date @all))]
      (- (count @all) (count unseen)))))

(comment
  (do
    (let [last-visit (-> (last-seen "piH3WsiKhFcq56lh1q37ijiGnqX2") :articles :date)
          all (db/on-value-reaction {:path ["booking-posts" "articles"]})
          unseen (filter (fn [m] (not-yet-seen' last-visit m)) (all-articles-by-date @all))]

      (- (count @all) (count unseen))
      #_{:all     (all-articles-by-date @all)
         :not-yet unseen})))

(comment
  (do
    (not-yet-seen
      (-> (last-seen "piH3WsiKhFcq56lh1q37ijiGnqX2") :articles :date)
      ;(t/at (t/new-date 2021 1 1) (t/new-time 1 1 1))
      [:x {:date (t/at (t/new-date 2023 1 1) (t/new-time 1 1 1))}])))

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

(defn lineitem [{:keys [content item-id uid' kv date-of-last-seen id-of-last-seen date uid] :as m}]
  [sc/surface-a {:style {:border-radius "var(--radius-2)"}}
   (let [bg (if (pos? (compare (str item-id) (str id-of-last-seen))) :bg-white :bg-transparent)]
     [:div

      (eykt.content.rapport-side/preview "b" date content)

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
  (into [:div.space-y-1]
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
           [:div.flex.flex-center.h-12 {:on-click #(swap! pointer (fn [e] (+ e 5)))
                                        :class    ["bg-black/10" :rounded]} "Vis eldre"]])))

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

(defn remove-receipts [uid]
  (db/database-set {:path  ["booking-posts" "receipts" uid "articles"]
                    :value {}}))

(defn add-article []
  (db/database-push {:path  ["booking-posts" "articles"]
                     :value {:date (str (t/now)) :content "some content for the new stuff"}}))

(defn generate [uid]
  (db/database-push {:path  ["booking-posts" "receipts" uid "articles"]
                     :value {:date (str (t/now)) :id "A"}})
  (db/database-push {:path  ["booking-posts" "articles"]
                     :value {:date (str (t/now)) :content "A"}}))

(defn bottom-menu-definition [settings-atom]
  (let [uid (:uid @settings-atom)]
    [#_[:header [sc/row {:class [:justify-between :items-end]}
                 [sc/title "Top"]
                 [sc/pill (or booking.data/VERSION "dev.x.y")]]]
     ;[icon label action disabled value]
     #_[:menuitem [nil "Slett kvitteringer" #() false 0]]
     [:menuitem {:icon     (sc/icon [:> solid/TrashIcon])
                 :label    "Slett kvitteringer"
                 :color    "var(--blue-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 :action   #(remove-receipts uid)
                 :disabled false
                 :value    #()}]
     [:menuitem {:icon     (sc/icon [:> solid/PlusIcon])
                 :label    "Ny artikkel"
                 :color    "var(--green-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 :action   #(add-article)
                 :disabled false
                 :value    #()}]
     [:menuitem {:icon     (sc/icon [:> solid/BeakerIcon])
                 :label    "Generer"
                 :color    "var(--red-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 :action   #()
                 :disabled true
                 :value    #()}]]))


(defn bottom-menu [uid]
  (r/with-let [main-visible (r/atom true)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/naked-menu-example-with-args
       {:showing @main-visible
        :dir     #{:up :right}
        :data    (bottom-menu-definition (r/atom {:uid uid}))
        :button  (fn [open]
                   [scb/round-normal {:on-click toggle-mainmenu} [sc/icon [:> solid/DotsHorizontalIcon]]])}])))

(defn render [{:keys [path uid fsm]}]
  ;intent When opening this section, mark as read!
  (let [pointer (r/atom 5)
        datas (db/on-value-reaction {:path path})]
    (r/create-class
      {:display-name
       "booking-blog"

       :component-did-update
       (fn [this old-argv old-state snapshot]
         #_(let [datas (db/on-value-reaction {:path path})]
             (tap> [:component-did-update @datas])
             (if (empty? @datas)
               (send :e.empty)
               (send :e.initial))))

       :component-did-mount
       (fn [_]
         ;-[ ] set receipts/uid/articles/date and id
         (mark-last-seen uid)
         ;(js/alert "!")
         ;(tap> {:datas @datas})
         ;(rf/dispatch [::rs/start])
         #_(tap> ":component-did-mount"))

       :reagent-render
       (fn [{:keys [path uid]}]
         (let [data (sort-by (comp :number val) < (logg.database/boat-db))
               {date-of-last-seen :date id :id} @(db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
               date-of-last-seen (some-> date-of-last-seen t/instant t/date-time)
               list-of-posts (db/on-value-reaction {:path path})
               *fsm-rapport (:blog @(rf/subscribe [::rs/state :main-fsm]) :s.startup)]
           [sc/col {:class [:space-y-4]}
            #_[l/ppre-x
               @(rf/subscribe [::rs/state :main-fsm])
               date-of-last-seen]

            ;[l/ppre-x @list-of-posts]
            ;[:div *fsm-rapport]
            ;[l/ppre-x uid]

            #_(when uid
                [l/ppre-x (last-seen uid)])

            (rs/match-state *fsm-rapport
              [:s.startup]
              [initial-page {:path              path
                             :uid               uid
                             :id                id
                             :pointer           pointer
                             :date-of-last-seen date-of-last-seen}]

              [:s.empty]
              [sc/col
               [l/ppre-x @list-of-posts]
               [:div "EMPTY"]]

              [:s.initial]
              [initial-page {:path              path
                             :uid               uid
                             :id                id
                             :pointer           pointer
                             :date-of-last-seen date-of-last-seen}]

              [:div "other " *fsm-rapport])

            [:div.absolute.bottom-24.xs:bottom-7.left-4.xs:left-20.md:left-24
             [sc/row-end {:class [:pt-4]}
              (bottom-menu uid)]]]))})))
 