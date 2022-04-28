(ns booking.dine-vakter
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [tick.core :as t]
            [schpaa.debug :as l]))

(defn send-msg [loggedin-uid]
  ;(js/alert "!")
  (rf/dispatch [:app/open-send-message loggedin-uid]))

(defn personal [loggedin-uid {:keys [telefon epost uid] :as user}]
  [sc/row-sc-g2-w
   [sc/row-sc-g1-w {:style {:color "var(--text1)"}}
    [sc/text1 "Telefon"] telefon
    [sc/link {:href (str "tel:" telefon)} "Ring"]
    [:span "/"]
    [sc/link {:href (str "sms:" telefon)} "SMS"]
    [:span "/"]
    (hoc.buttons/round-cta-pill {:on-click #(send-msg loggedin-uid)} (sc/icon ico/tilbakemelding))]
   (when (seq epost)
     [sc/text1 "E-post " [sc/link {:href (str "mailto:" epost)} epost]])])

(defn date-header [rec]
  (when-let [ts (get rec "timestamp")]
    [sc/text2 (times.api/compressed-date (times.api/timestamp->local-datetime-str' ts))]))

(defn endringslogg [path]
  (r/with-let [data @(db/on-snapshot-docs-reaction {:path path})]
    (if (empty? data)
      [sc/title2 "Ingen endringer"]
      [sc/col
       [sc/title1 "Endringslogg"]
       (into [:<>]
             (for [{:keys [data id]} data
                   :let [rec (some-> data)]]
               (if-let [beskrivelse (get-in rec ["after" "endringsbeskrivelse"])]
                 [:<>
                  (date-header rec)
                  [sc/text1 beskrivelse]]
                 [:<>
                  (date-header rec)
                  [l/pre (some-> (get-in data ["after"]))]])))])))

(defn vakter [loggedin-uid data]
  (if (empty? data)
    [sc/title2 "Ingen vakter"]
    [sc/col-space-8
     [sc/title1 "Vakter i '22"]
     [sc/col-space-4 {:style {:margin-inline "var(--size-3)"}}
      (into [:<>]
            ;[[l/ppre-x data]]
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
  (let [path (fn [id] (when id (vector "beskjeder" loggedin-uid "inbox" id)))
        delete-message (fn [uid value] (db/firestore-set {:path (path uid) :value value}))
        data (remove (fn [{:keys [id data]}] (get data "deleted" false)) datum)]
    (if (empty? data)
      [sc/title2 "Ingen beskjeder"]
      [sc/col-space-4
       [sc/title1 "Beskjeder"]
       (into [sc/col-space-4]
             (for [{msg-id :id msg-data :data} (sort-by #(get-in % [:data "timestamp"]) > data)]
               (let [{:strs [deleted timestamp uid text]} msg-data]
                 ^{:key (str msg-id)} [sc/row-sc-g4-w {:class [:px-4]}
                                       ;[l/pre deleted]
                                       [widgets/trashcan
                                        (fn [id] (delete-message id {:deleted (not deleted)}))
                                        {:deleted deleted :id msg-id}]
                                       [hoc.buttons/round-cta-pill {:on-click #(js/alert "!")} [sc/icon ico/tilbakemelding]]
                                       [sc/col-space-2
                                        [sc/col-space-1
                                         [sc/text2 (if (= uid loggedin-uid)
                                                     "Fra deg selv"
                                                     [:div [:span "Fra "] [sc/link {:href (kee-frame.core/path-for [:r.dine-vakter {:id uid}])} (user.database/lookup-username uid)]])]
                                         (when timestamp
                                           [sc/small2 (booking.flextime/relative-time (times.api/timestamp->local-datetime-str' timestamp))])]
                                        [sc/text1 text]]])))])))

(defn render [r]
  (let [admin? @(rf/subscribe [:lab/admin-access])]
    (if-some [loggedin-uid (-> r :path-params :id)]
      (r/with-let [datum (db/on-snapshot-docs-reaction {:path ["beskjeder" loggedin-uid "inbox"]})]
        (when @datum
          (if-let [data (db/on-value-reaction {:path ["calendar" loggedin-uid]})]
            (let [user (user.database/lookup-userinfo loggedin-uid)
                  nøkkelvakt? (:nøkkelvakt user)
                  data (mapcat val (clojure.walk/stringify-keys @data))
                  saldo (:saldo user)
                  timekrav (:timekrav user)
                  z (when (some? saldo)
                      (- saldo timekrav (- (* 3 (count (seq data))))))]
              [sc/col-space-8
               [personal loggedin-uid user]

               (when admin?
                 [sc/row-sc-g2
                  [sc/text1 "Identitet"]
                  (sc/as-shortcut loggedin-uid)])

               (when admin?
                 (when nøkkelvakt?
                   [booking.mine-dine-vakter/header
                    {:saldo    saldo
                     :timekrav timekrav
                     :z        z}]))

               (when nøkkelvakt?
                 [vakter loggedin-uid data])

               (when admin?
                 [endringslogg ["users" loggedin-uid "endringslogg"]])

               [beskjeder loggedin-uid @datum]])

            [sc/title1 "Ingen definerte vakter"])))
      [widgets/no-access-view r])))