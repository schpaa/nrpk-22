(ns booking.booking
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.common-widgets :as widgets]
            [tick.core :as t]
            [schpaa.style.dialog :as dlg]
            [db.core :as db]
            [booking.ico :as ico]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.utlan]))

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
    [hoc.buttons/cta-pill-icon {:disabled true
                                :on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/plus "Ny booking"]
    [hoc.buttons/danger-pill {:disabled true
                              :on-click #(rf/dispatch [:lab/just-create-new-blog-entry])} "HMS Hendelse"]]
   #_[sc/row-sc-g2-w
      [hoc.toggles/switch-local {:disabled true} (r/cursor settings [:rent/show-details]) "Kompakt"]
      [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-details]) "Detaljer"]
      [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-timegraph]) "Tidslinje"]]
   [sc/row-sc-g4-w
    [sc/text1 "Se også"]
    [widgets/auto-link :r.båtliste]
    [widgets/auto-link :r.booking.faq]
    #_[sc/link {:href (kee-frame.core/path-for [:r.dokumenter {:id "tidslinje-forklaring"}])} "Ofte stilte spørsmål"]
    [widgets/auto-link :r.utlan]]])

(defn panel [{:keys []}]
  [sc/col-space-8
   [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
    [hoc.toggles/switch-local (r/cursor settings [:booking/show-deleted]) "vis slettede"]]])

(defn basic [loggedin-uid {:keys [uid]}]
  (if (= uid loggedin-uid)
    [hoc.buttons/cta-pill {:class    [:narrow]
                           :on-click #()} "Avlys"]
    [hoc.buttons/reg-pill {:class    [:narrow]
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
   (when sleepover [sc/icon-tiny-frame ico/moon])
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

(defn page [r]
  (let [user-auth (rf/subscribe [::db/user-auth])
        loggedin-uid @(rf/subscribe [:lab/uid])]
    {:panel-title  "Tittel"
     :panel        panel
     :always-panel commands
     :render
     (fn []
       (let [data (rf/subscribe [:booking/list])
             show-deleted? @(rf/subscribe [:booking/common-show-deleted])
             db @(rf/subscribe [:db/boat-db])
             lookup-id->number (into {} (->> (remove (comp empty? :number val) db)
                                             (map (juxt key (comp :number val)))))]
         [sc/col-space-8
          (into [sc/col-space-1]
                (for [[k {:keys [test start end alias list deleted] :as m}]
                      (if show-deleted?
                        @data
                        @data
                        #_(remove (comp :deleted val) @data))
                      :let [boats list
                            start (t/date-time start)
                            end (t/date-time end)]]

                  [booking.utlan/command-grid
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
                   (into [booking.utlan/listitem'
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
                             [sc/row-fields
                              [sc/text1 {:class [:tracking-tight]} (times.api/logg-date-format start)]
                              [:div.grow]
                              [sc/text1 {:class [:tracking-tight]} "kl. " (times.api/time-format start)]]
                             (when end
                               (let [dt end]
                                 [sc/row-fields
                                  (when-not (t/= (t/date dt) (t/date start))
                                    [sc/text1 {:class [:tracking-tight]
                                               :style {:white-space :nowrap}} (times.api/logg-date-format dt)])
                                  [:div.grow]
                                  [sc/text1 {:class [:tracking-tight :w-full :h-auto :self-center :text-right]}
                                   (if end
                                     (str "kl. " (times.api/time-format (t/time dt)))
                                     (str end))]]))]]
                           [(map (fn [[id returned]]
                                   (let [returned (not (empty? returned))
                                         number (get lookup-id->number (keyword id) (str " ? " id))]
                                     (sc/badge-2 {:class    [:big #_(if-not returned :in-use)]
                                                  :on-click #(dlg/open-modal-boatinfo
                                                               {:uid  loggedin-uid
                                                                :data (get db (keyword id))})} number)))
                                 (remove nil? boats))]))]))]))}))