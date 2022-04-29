(ns booking.dine-vakter
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [tick.core :as t]
            [schpaa.debug :as l]
            [booking.mine-dine-vakter]))



(defn reply-to-msg [loggedin-uid uid msg-id]
  ;(js/alert "!")
  (rf/dispatch [:app/open-send-reply loggedin-uid uid msg-id]))



(defn date-header [rec]
  (when-let [ts (get rec "timestamp")]
    [sc/small1 (times.api/compressed-date (times.api/timestamp->local-datetime-str' ts))]))

(defn endringslogg [path]
  (r/with-let [data (db/on-snapshot-docs-reaction {:path path})]
    (when-not (empty? @data)
      [sc/col-space-8
       ;[sc/title1 "Endringslogg"]
       [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
        (into [:<>]
              (for [{:keys [data id]} @data
                    :let [rec (some-> data)]]
                (if-let [beskrivelse (get-in rec ["after" "endringsbeskrivelse"])]
                  [sc/col-space-2
                   (date-header rec)
                   [sc/text1 beskrivelse]]
                  [sc/col-space-2
                   (date-header rec)
                   [sc/text0 #_{:style {:color "var(--yellow-3)"}} (str (some-> (get-in data ["after"])))]])))]])))

(defn vakter [loggedin-uid data]
  (when-not (empty? data)
    [sc/col-space-8 {:class []}

     [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
      (into [:<>]
            (for [[slot kv] data]
              [sc/row-sc-g4-w
               [schpaa.style.hoc.buttons/reg-pill
                {:class    [:narrow]
                 :disabled true}
                "Ta over"]
               [sc/col
                [sc/text1 (some-> slot t/date-time times.api/arrival-date)]
                [sc/small1 "Registrert " (some-> (get kv loggedin-uid) t/date-time times.api/arrival-date)]]]))]]))

(defn beskjeder [loggedin-uid datum]
  (let [admin? false
        path (fn [id] (when id (vector "beskjeder" id "inbox")))
        storage-path (sc/as-shortcut {:style {:text-transform "unset"}} (apply str (interpose "/" (path loggedin-uid))))
        delete-message (fn [msg-id value] (db/firestore-set {:path (conj (path loggedin-uid) msg-id) :value value}))
        data (remove (fn [{:keys [id data]}] (get data "deleted" false)) datum)]
    (if (empty? data)
      [sc/col-space-4
       [sc/title2 "Ingen beskjeder"]
       (when admin?
         [sc/text storage-path])]
      [sc/col-space-4
       [sc/title1 "Beskjeder"]
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
                  [hoc.buttons/round-cta-pill {:on-click #(reply-to-msg loggedin-uid uid msg-id)} [sc/icon ico/tilbakemelding]]
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
    (if (empty? data)
      [sc/col-space-4
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
    (if-some [user-id (-> r :path-params :id)]
      (r/with-let [inbox-messages (db/on-snapshot-docs-reaction {:path ["beskjeder" user-id "inbox"]})]
        (when @inbox-messages
          (if-let [data (db/on-value-reaction {:path ["calendar" user-id]})]
            (let [user (user.database/lookup-userinfo user-id)
                  nøkkelvakt? (:nøkkelvakt user)
                  data (mapcat val (clojure.walk/stringify-keys @data))
                  saldo (:saldo user)
                  timekrav (:timekrav user)
                  antall-eykter (when (some? saldo)
                                  (- saldo timekrav (- (* 3 (count (seq data))))))]
              [sc/col-space-8
               [widgets/personal user-id user]
               [beskjeder user-id @inbox-messages]
               (when nøkkelvakt?
                 [vakter user-id data])
               (when (and admin? nøkkelvakt?)
                 [booking.mine-dine-vakter/header saldo timekrav antall-eykter])
               (when admin?
                 [endringslogg ["users" user-id "endringslogg"]])
               (when admin?
                 [sc/row-bl
                  [sc/small1 "Identitet"]
                  (sc/as-identity user-id)])])

            [sc/title1 "Ingen definerte vakter"])))
      [widgets/no-access-view r])))