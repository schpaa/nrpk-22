(ns booking.booking
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.common-widgets :as widgets]
            [tick.core :as t]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]
            [db.core :as db]
            [booking.ico :as ico]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.utlan]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as button]
            [booking.styles :as b]
            [clojure.string :as str]))

(defonce settings
         (r/atom {:rent/show-details    false
                  :rent/show-timegraph  false
                  :rent/graph-view-mode 0
                  :booking/show-deleted false}))

(rf/reg-sub :booking/common-show-deleted
            (fn [_]
              (and @(schpaa.state/listen :r.booking)
                   @(r/cursor settings [:booking/show-deleted]))))

(rf/reg-sub :booking/common-show-details
            (fn [_]
              (and                                          ;@(schpaa.state/listen :r.utlan)
                @(r/cursor settings [:booking/show-details]))))

(rf/reg-sub :booking/common-edit-mode
            (fn [db]
              (and @(schpaa.state/listen :r.booking)
                   #_@(r/cursor settings [:rent/edit]))))

(defn commands []
  [sc/col-space-4
   [sc/row-sc-g2-w
    [button/icon-and-caption {:disabled true
                              :on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/plus "Ny booking"]
    [button/just-caption {:disabled true
                          :on-click #(rf/dispatch [:lab/just-create-new-blog-entry])} "HMS Hendelse"]]
   #_[sc/row-sc-g2-w
      [hoc.toggles/switch-local {:disabled true} (r/cursor settings [:rent/show-details]) "Kompakt"]
      [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-details]) "Detaljer"]
      [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-timegraph]) "Tidslinje"]]])

(defn panel [{:keys []}]
  [sc/col-space-8
   [sc/row-sc-g4-w
    ;[sc/text1 "Se også"]
    ;[widgets/auto-link :r.båtliste.nøklevann]
    [widgets/auto-link nil :r.booking.faq]
    #_[sc/link {:href (kee-frame.core/path-for [:r.dokumenter {:id "tidslinje-forklaring"}])} "Ofte stilte spørsmål"]
    [widgets/auto-link nil :r.utlan]]
   [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
    [hoc.toggles/switch-local (r/cursor settings [:booking/show-deleted]) "vis slettede"]]])

(defn basic [loggedin-uid {:keys [uid]}]
  (if (= uid loggedin-uid)
    [button/just-caption {:class    [:regular :normal]
                          :on-click #()} "Avlys"]
    [button/just-caption {:class    [:normal]
                          :on-click #()} "Bli med"]))

(defn edit [loggedin-uid edit-mode? k {:keys [uid deleted timestamp list] :as m}]
  [:<>
   #_{:class [:h-12x :flex :items-center :gap-2]
      :style {:grid-area "edit"
              :display   :flex}}

   [widgets/trashcan (fn [id] (db/database-update
                                {:path  ["booking" id]
                                 :value {:deleted (not deleted)}}))
    (assoc m :id (name k))]
   [widgets/edit {:disabled false} #(rf/dispatch [:lab/toggle-boatpanel]) m]])

(defn- badges [{:keys [sleepover havekey] :as m}]
  [sc/row-ec-g1 {:style {:grid-area "badges"
                         :flex-wrap :nowrap}
                 :class [:h-12x :place-content-center]}
   (when sleepover [sc/icon-tiny-frame ico/moon-filled])
   (when havekey [sc/icon-tiny-frame ico/nokkelvakt])])

;region data-source

(defn- date->str [v]
  (-> v
      (cljs-time.coerce/from-string)
      (cljs-time.coerce/to-long)
      (times.api/ms->local-time)
      (t/<< (t/new-duration 2 :hours))
      ;t/instant
      ;t/date-time
      str))

(rf/reg-sub :booking/list
            (fn [_ _]
              ;(sort-by (comp :timestamp val) >)
              (transduce
                (comp
                  (map (fn [[k v]] [k (if (= 2 (:version v 1))
                                        ;todo This is NOT utc but local-time, so add 2 hours
                                        v
                                        {:version   2
                                         :timestamp (date->str (str (:date v) "Z"))
                                         :deleted   (:deleted v)
                                         :start     (date->str (str (:checkin v) "Z"))
                                         :end       (date->str (str (:checkout v) "Z"))
                                         ;:bid       (:bid v)
                                         :uid       (if (:book-for-andre v) nil (:uid v))
                                         :alias     (:navn v)
                                         :list
                                         (into {}
                                               (mapv (fn [k]
                                                       [(or (:id (booking.modals.boatinput/lookup k)) k) ""]) [(str (:bid v))]))})]))
                  #_(filter (fn [[k v]]
                              ;(tap> {:date' (t/date-time (:timestamp v))})
                              (t/= (t/date (t/date-time (t/instant (:timestamp v))))
                                   (t/today))))

                  ;(map identity)
                  #_(filter (fn [[k v]]
                              (t/<= (t/<< (t/today) (t/new-period 3 :days))
                                    (t/date (t/instant (:timestamp v)))))))
                conj
                []
                (filter (fn [[k v]] (pos? (compare (name k) "-N-YkbBWynjRcIXbeiPi")))
                        @(db.core/on-value-reaction {:path ["booking"]})))))

;endregion

(defn logg-item [k {:keys [deleted alias loggedin-uid start end boats db] :as m}]
  [booking.utlan/logg-listitem-grid
   {:class [(when test :test)]}

   [badges m #_(assoc m :sleepover true)]

   (when @(rf/subscribe [:booking/common-edit-mode])
     [:div {:class [:h-12x :flex :items-center :gap-2]
            :style {:grid-area "edit"
                    :display   :flex}}
      [edit loggedin-uid true k m]
      (when-not deleted [basic loggedin-uid m])])

   [sc/text2 {:style {:grid-area "details"}} alias]
   #_[sc/text1 {:class [:-debug]
                :style {:place-self :center
                        :grid-area  "badges"}} "badges"]
   (into [booking.utlan/logg-listitem
          {:class [(if deleted :deleted)
                   :x-debug
                   :w-full :py-2]
           :style {:flex            "1 0 auto"
                   :justify-content :between
                   :align-items     :center
                   :opacity         (if deleted 0.2 1)
                   :grid-area       "content"}}]
         (concat
           [[sc/col {:class [:tabular-nums :w-44]}
             [sc/row-field
              [sc/text1 {:class [:tracking-tight]} (times.api/logg-date-format start)]
              [:div.grow]
              [sc/text1 {:class [:tracking-tight]} "kl. " (times.api/time-format start)]]
             (when end
               (let [dt end]
                 [sc/row-field
                  (when-not (t/= (t/date dt) (t/date start))
                    [sc/text1 {:class [:tracking-tight]
                               :style {:white-space :nowrap}} (times.api/logg-date-format dt)])
                  [:div.grow]
                  [sc/text1 {:class [:tracking-tight :w-full :h-auto :self-center :text-right]}
                   (if end
                     (str "kl. " (times.api/time-format (t/time dt)))
                     (str end))]]))]]
           [(map (fn [[id [number slot]]]
                   (widgets/badge {:class    [:big #_(if-not returned :in-use)]
                                   :on-click #(open-modal-boatinfo
                                                {:uid  loggedin-uid
                                                 :data (get db (keyword id))})}
                                  number
                                  slot))
                 (remove nil? boats))]))])

(defn datasource [source]
  (let [not-deleted (comp not :deleted)
        only-version-2 (comp (partial <= 2) :version)
        attach-id #(assoc (second %) :id (first %))
        after-today (comp #(t/< (t/<< (t/today) (t/new-period 10 :weeks)) (t/date %)) t/date-time :timestamp)]
    (transduce (comp
                 (map attach-id)
                 (filter only-version-2)
                 (filter not-deleted)
                 (filter after-today))
               conj
               []
               source)))

(comment
  (fn []
    (let []))                                               ;show-deleted? @(rf/subscribe [:booking/common-show-deleted])
  ;db @(rf/subscribe [:db/boat-db])
  lookup-id->number (into {} (->> (remove (comp empty? :number val) db)
                                  (map (fn [[k v]] [k [(:number v) (:slot v)]])))))

(defn as-name [uid]
  (user.database/lookup-username uid))

(defn as-phone [uid]
  (:telefon (user.database/lookup-userinfo uid)))

(defn from-to [start-time end-time]
  (let [more-than-1-day? (not (t/= (t/date start-time) (t/date end-time)))]
    [b/co0
     [:div.flex.gap-1 {:style {}
                       :class [:w-32
                               :shrink-0]}
      [b/text (times.api/time-format start-time)]
      [b/text "->"]
      (if more-than-1-day?
        [b/co1
         [b/text (times.api/day-month end-time)]
         [b/text (times.api/time-format (t/time end-time))]]
        [b/text (times.api/time-format (t/time end-time))])]]))

(def state (r/atom {:detail-level {}}))

(def detail-level (r/cursor state [:detail-level]))

;<script src="https://gist.github.com/martinklepsch/c090f114a0b9d95b0b4ae5809ef22f3b.js"></script>

(comment
  (let [dat [1 2 3]
        r (into {} (map (juxt identity (constantly true)) (range (count dat))))]
    [r
     (not-any? (comp true? val) r)]))

(rf/reg-sub :db/boat-db
            (fn [_]
              [(db/on-value-reaction {:path ["boat-brand"]})
               (db/on-value-reaction {:path ["boad-item"]})])
            (fn [[brands items] _]
              (into {}
                    (comp
                      (map (fn [[id {:keys [boat-type] :as item}]]
                             [id (conj (get brands (keyword boat-type))
                                       item)])))
                    items)))

(defn page []
  (let [db (rf/subscribe [:db/boat-db])
        lookup-id->number (into {} (->> (remove (comp empty? :number val) @db)
                                        (map (fn [[k v]] [k [(:number v) (:slot v)]]))))
        raw (concat [[:a {:version   2
                          :list      ["1" "2" "3" "4"]
                          ;:uid "123"
                          :alias     "Alias"
                          :timestamp (t/now) :start (t/now)
                          :end       (t/at (t/tomorrow) t/noon)}]
                     [:c {:version   2
                          ;:uid "123"
                          :alias     "Alias"
                          :list      ["1" "2"]
                          :timestamp (t/now) :start (t/now)
                          :end       (t/at (t/tomorrow) t/noon)}]
                     [:b {:version   2
                          ;:uid "123"
                          :alias     "Alias"
                          :list      ["1"]
                          :timestamp (t/now) :start (t/now)
                          :end       (t/at (t/tomorrow) t/noon)}]]

                    @(rf/subscribe [:booking/list]))
        dat (->> raw
                 (datasource)
                 (group-by (comp t/date t/date-time :start)))]
    (fn []
      [b/co
       (button/just-caption
         {:class    [:small :frame :flip]
          :on-click #(reset! detail-level
                             (if (not-any? (comp true? val) @detail-level)
                               (into {} (map (juxt identity (constantly true)) (range (count dat))))
                               (into {} (map (juxt identity (constantly false)) (range (count dat))))))}
         ;ico/selector
         (if (some #{true} (vals @detail-level)) "Vis alt" "Vis minimalt"))
       [l/pre @detail-level]
       [:<> (for [[idx [g e]] (map-indexed vector dat)]
              [b/ro-js {:on-click #(swap! detail-level update idx (fnil not false))
                        :class    [:h-auto :w-full]
                        :style    {:align-items "stretch"}}
               [:div.flex.items-stretch.w-full.gap-1
                [:div.self-stretch.flex.items-stretch
                 [widgets/booking-date g]]
                [b/co1 {:class [:w-full]}
                 (case (get @detail-level idx)
                   true [b/ro1
                         {:class [:w-full :p-1]
                          :style {:background-color "var(--floating)"}}

                         (for [{:keys [list] :as e} e
                               [idx [a b]] (map-indexed vector list)
                               :let []]
                           [widgets/simple-badge {:class [:truncate :self-baseline]}
                            (+ 100 idx)])]

                   (for [e e
                         :let [{:keys [uid alias timestamp list start end]} e
                               registered (times.api/day-month (t/date-time timestamp))
                               start-time (t/date-time start)
                               end-date (t/date-time end)
                               items (quote 1)]]
                     [b/ro {:class [:w-full :p-1]
                            :style {:background-color   "var(--floating)"
                                    :line-height        1.2
                                    :padding-inline-end "0.5rem"
                                    :align-items        "start"}}
                      [b/co1 (for [[idx [e _]] (map-indexed vector list)
                                   :let [e (name e)]]
                               [widgets/simple-badge {:class [:truncate :self-baseline]}
                                (+ 100 idx)
                                "A2"])]

                      [b/ro {:class [:py-2]}
                       [:div.self-start.-debugx (from-to start-time end-date)]
                       [b/co {:style {:height "min-content"}
                              :class [:self-start]}
                        [b/ro1
                         [b/dim {:class [:shrink-0]} registered]
                         [b/text
                          {:class [:w-full :whitespace-nowrap :truncate]
                           :style {:text-align "left"
                                   :flex       "1"
                                   :width      "100%"}}
                          (str/trim (if uid (or (as-name uid) "??") (or alias "?")))]]
                        [b/dim (if uid
                                 (widgets/user-link uid)
                                 "---")]]]]))]]])]])

    #_[sc/col-space-8
       [sc/col-space-1
        (into [:<>]
              (for [[k {:keys [start end list] :as m}]
                    (if show-deleted?
                      @data
                      @data)]
                [logg-item k (assoc m
                               :k k
                               :db db
                               :boats (map (fn [[id _returned]] (get lookup-id->number (keyword id) (str " ? " id))) list)
                               :loggedin-uid loggedin-uid
                               :start (t/date-time start)
                               :end (t/date-time end))]))]]))
