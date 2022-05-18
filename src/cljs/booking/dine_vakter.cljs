(ns booking.dine-vakter
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]
            [tick.core :as t]
            [schpaa.debug :as l]
            [booking.mine-dine-vakter :refer [path-endringslogg
                                              path-beskjederinbox]]
            [eykt.calendar.actions :as actions]))



(defn reply-to-msg [loggedin-uid uid msg-id]
  ;(js/alert "!")
  (rf/dispatch [:app/open-send-reply loggedin-uid uid msg-id]))



(defn date-header [rec]
  (when-let [ts (get rec "timestamp")]
    [sc/small1 (times.api/compressed-date (times.api/timestamp->local-datetime-str' ts))]))

(defn endringslogg [uid path]
  (r/with-let [data (db/on-snapshot-docs-reaction {:path path})]
    (let [;fix forced first entry; uid assignment
          data (cons {:id   "0"
                      :data {"by-uid"    uid
                             "reason"    {:a 1}
                             "after"     {"endringsbeskrivelse" uid}
                             "timestamp" nil}}
                     @data)]
      [sc/col-space-8
       [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
        (into [:<>]
              (for [{:keys [data _id]} data
                    :let [rec data]]
                [:div
                 ;{:style {:break-inside :avoid-page}} 
                 (if-let [beskrivelse (get-in rec ["after" "endringsbeskrivelse"])]
                   [sc/col-space-2
                    (date-header rec)
                    [sc/text1 beskrivelse]]
                   [sc/col-space-2
                    (date-header rec)
                    [sc/text0 (str (some-> (get-in rec ["after"])))]])]))]])))

(defn- within-undo-limits? [now date]
  ;(tap> [now date (tick.alpha.interval/new-interval (t/instant date) now)])
  (when-some [date date]
    (< (t/hours (t/duration (tick.alpha.interval/new-interval (t/instant date) now))) 24)))

(defn- frafall [_])

(defn vakter [uid data::stringified]
  (let [admin? (rf/subscribe [:lab/admin-access])]
    (when (seq data::stringified)
      (let [within-undo-limits? (partial within-undo-limits? (t/now))
            collector (sort-by :date-proper <
                               (for [[section vs] data::stringified
                                     [timeslot uid-registered-tuple] vs
                                     [_userid dt] uid-registered-tuple
                                     :let [status (if (map? dt) :cancel :ok)
                                           date-registered (when-not (map? dt) (some-> dt t/date-time))
                                           date-proper (some-> timeslot t/date-time)
                                           completed? (when date-proper (t/< (t/instant date-proper) (t/now)))]]
                                 {:status          (clojure.walk/keywordize-keys (if (map? dt) dt {:ok dt}))
                                  :section         section
                                  :timeslot        timeslot
                                  :date-registered date-registered
                                  :date-proper     date-proper
                                  :completed?      (and completed? (= :ok status))}))]
        [sc/col-space-8 {:class []}
         [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
          (into [:<>]
                (->> collector
                     (map (fn [{:keys [status date-registered date-proper completed? section timeslot]}]
                            [:div
                             ;[l/pre uid-registered-tuple]
                             [sc/row-sc-g4-w
                              ;[l/pre data::stringified]
                              (if completed?
                                [:<>]
                                (if (:cancel status)
                                  [button/reg-pill-icon
                                   {:class    [:regular]
                                    :disabled true}
                                   ico/exclamation
                                   "Frafalt"]
                                  (if (within-undo-limits? date-registered)
                                    [button/reg-pill-icon
                                     {:on-click #(actions/delete {:uid uid :section section :timeslot timeslot})
                                      :class    [:danger]
                                      :disabled false}
                                     ico/trash
                                     "Avlys"]
                                    [button/reg-pill-icon
                                     {:class    [:message]
                                      :disabled true}
                                     ico/bytte
                                     "Bytte"])))
                              (when (and @admin?
                                         (not (within-undo-limits? date-registered))
                                         (not completed?)
                                         (not (:cancel status)))
                                [button/reg-pill-icon
                                 {:on-click #(actions/frafall {:uid uid :section section :timeslot timeslot})
                                  :class    [:danger]
                                  :disabled false}
                                 ico/thumbsdown
                                 "Frafall"])
                              (when (and @admin? (not completed?) (:cancel status))
                                [button/reg-pill-icon 
                                 {:on-click #(actions/deltar {:uid uid :section section :timeslot timeslot})
                                  :class    [:cta :outliner]
                                  :disabled false}
                                 ico/thumbsup
                                 "Deltar"])
                              [sc/col
                               [sc/text1 {:class [(when completed? :line-through)]} (some-> date-proper times.api/arrival-date)]
                               [sc/small1 "Registrert "
                                (if (:cancel status)
                                  (some-> date-registered times.api/arrival-date)
                                  (some-> date-registered times.api/arrival-date))]]]]))))]]))))


(defn beskjeder [loggedin-uid datum]
  (let [admin? false
        path (fn [id] (when id (vector "beskjeder" id "inbox")))
        storage-path (sc/as-shortcut {:style {:text-transform "unset"}} (apply str (interpose "/" (path loggedin-uid))))
        delete-message (fn [msg-id value] (db/firestore-set {:path (conj (path loggedin-uid) msg-id) :value value}))
        data (remove (fn [{:keys [id data]}] (get data "deleted" false)) datum)]
    (if (empty? data)
      [sc/col-space-4
       [sc/title1 "Ingen beskjeder"]
       (when admin?
         [sc/text storage-path])]
      [sc/col-space-4
       [sc/title "Beskjeder"]
       (when admin?
         [sc/text storage-path])
       (into [sc/col-space-4]
             (for [{msg-id :id msg-data :data} (sort-by #(get-in % [:data "timestamp"]) > data)]
               (let [{:keys [deleted timestamp uid text]} (clojure.walk/keywordize-keys msg-data)]
                 ^{:key (str msg-id)}
                 [sc/row-sc-g4-w {:class [:px-3]}
                  [widgets/trashcan
                   (fn [msg-id] (delete-message msg-id {:deleted (not deleted)}))
                   {:deleted deleted :id msg-id}]
                  [button/round-cta-pill {:on-click #(reply-to-msg loggedin-uid uid msg-id)} [sc/icon ico/tilbakemelding]]
                  [sc/col-space-2
                   [sc/col-space-1
                    [sc/text2 (if (= uid loggedin-uid)
                                "Fra deg selv"
                                [:div [:span "Fra "] [sc/link {:href (kee-frame.core/path-for [:r.dine-vakter {:id uid}])} (user.database/lookup-username uid)]])]
                    (when timestamp
                      [sc/small2 (booking.flextime/relative-time (times.api/timestamp->local-datetime-str' timestamp))])]
                   [sc/text1 text]]])))])))

(defn tilbakemeldinger [loggedin-uid datum]
  (let [admin? @(rf/subscribe [:lab/admin-access])
        path (fn [id] (when id (vector "tilbakemeldinger" id "feedback")))
        storage-path (sc/as-shortcut {:style {:text-transform "unset"}} (apply str (interpose "/" (path loggedin-uid))))
        delete-message (fn [msg-id value]
                         (db/database-update {:path  ["cache-tilbakemeldinger" (name msg-id)]
                                              :value value}))
        data datum]
    (if-not (empty? data)
      #_[sc/col-space-4
         [sc/title2 "Ingen tilbakemeldinger"]
         #_(when admin?
             [sc/small2 storage-path])]

      [sc/col-space-4
       [sc/title1 "Tilbakemeldinger"]

       (when admin?
         [sc/small2 storage-path])

       (into [sc/col-space-4]
             (for [[id {:keys [timestamp deleted text]}] (sort-by (comp :timestamp val) < data)]
               ^{:key (str id)}
               [sc/row-sc-g4-w {:class [:px-4]}
                [widgets/trashcan
                 (fn [msg-id] (delete-message msg-id {:deleted (not deleted)}))
                 {:deleted deleted :id id}]
                [sc/col-space-2
                 [sc/col-space-1
                  (when timestamp
                    [sc/small2 (booking.flextime/relative-time (t/instant timestamp))])]
                 [sc/text1 text]]]))])))

(defn render [r]
  (let [admin? @(rf/subscribe [:lab/admin-access])]
    (if-let [uid (some-> r :path-params :id)]
      (r/with-let [inbox-messages (db/on-snapshot-docs-reaction {:path (path-beskjederinbox uid)})]
        (when @inbox-messages
          (if-let [datas (db/on-value-reaction {:path ["calendar" uid]})]
            (let [user (user.database/lookup-userinfo uid)
                  nøkkelvakt? (:godkjent user)
                  data::stringified (clojure.walk/stringify-keys @datas)
                  saldo (:saldo user)
                  timekrav (:timekrav user)
                  #_#_temp (->> data::stringified
                                vals
                                (map vals)
                                flatten
                                (map (comp first vals))
                                (remove (fn [m]
                                          (tap> {:k2 m})
                                          (get m "cancel")))

                                #_flatten
                                #_(into []))

                  antall-økter (->> data::stringified
                                    vals

                                    (map vals)
                                    flatten
                                    (map (comp first vals))
                                    (remove (fn [m]
                                              ;(tap> {:k2 m})
                                              (get m "cancel")))
                                    count)
                  fullførte-timer (* 3 antall-økter) #_(or (when (some? saldo))
                                                           (- saldo timekrav (- (* 3 (count (seq data::stringified))))))]
              [:div
               ;[l/pre temp]
               [sc/col-space-8
                [widgets/personal user
                 (when admin?
                   [sc/row-bl
                    [sc/text1 "Identitet"]
                    (sc/as-identity uid)])]
                [beskjeder uid @inbox-messages]
                (when nøkkelvakt?
                  (when (seq data::stringified)
                    [sc/col-space-4
                     [sc/title1 "Påmeldte vakter '22"]
                     [vakter uid data::stringified]]))
                (when (and admin? nøkkelvakt?)
                  [booking.mine-dine-vakter/saldo-header saldo timekrav fullførte-timer])
                (when admin?
                  [:div {:style {:padding-block "var(--size-4)"
                                 :border-left   "4px solid var(--brand1)"}}
                   [endringslogg uid (path-endringslogg uid)]])]])

            [sc/title1 "Ingen vakter"])))
      [widgets/no-access-view r])))