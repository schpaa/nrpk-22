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
                                              path-beskjederinbox]]))



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

(defn vakter [loggedin-uid data]
  (when (seq data)
    [sc/col-space-8 {:class []}
     [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
      (into [:<>]
            (for [[slot kv] data
                  :let [date-registered (some-> (get kv loggedin-uid) t/date-time times.api/arrival-date)]]
              [sc/row-sc-g4-w
               [button/reg-pill-icon
                {:class    [:message]
                 :disabled false}
                ico/bytte
                "Bytte"]
               [button/reg-pill-icon
                {:class    [:danger]
                 :disabled false}
                ico/trash
                "Avlys"]
               [sc/col
                [sc/text1 (some-> slot t/date-time times.api/arrival-date)]
                [sc/small1 "Registrert " date-registered]]]))]]))

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
          (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
            (let [user (user.database/lookup-userinfo uid)
                  nøkkelvakt? (:godkjent user)
                  data (mapcat val (clojure.walk/stringify-keys @data))
                  saldo (:saldo user)
                  timekrav (:timekrav user)
                  antall-eykter (when (some? saldo)
                                  (- saldo timekrav (- (* 3 (count (seq data))))))]
              [sc/col-space-8
               ;[l/pre user]
               [widgets/personal user
                (when admin?
                  [sc/row-bl
                   [sc/text1 "Identitet"]
                   (sc/as-identity uid)])]
               [beskjeder uid @inbox-messages]

               (when nøkkelvakt?
                 (when (seq data)
                   [sc/col-space-4
                    [sc/title1 "Påmeldte vakter '22"]
                    [vakter uid data]]))

               (when (and admin? nøkkelvakt?)
                 [booking.mine-dine-vakter/saldo-header saldo timekrav antall-eykter])

               (when admin?
                 [:div {:style {:padding-block "var(--size-4)"
                                :border-left   "4px solid var(--brand1)"}}
                  [endringslogg uid (path-endringslogg uid)]])])

            [sc/title1 "Ingen vakter"])))
      [widgets/no-access-view r])))