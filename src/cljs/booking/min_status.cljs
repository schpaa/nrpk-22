(ns booking.min-status
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [booking.dine-vakter :refer [beskjeder vakter endringslogg tilbakemeldinger]]
            [booking.mine-dine-vakter :refer [saldo-header
                                              path-beskjederinbox
                                              path-beskjedersent
                                              path-endringslogg]]
            [schpaa.debug :as l]))

(defn cached-datasource [uid]
  (filter (fn [[k v]] (and (= uid (:uid v))
                           (not (:deleted v))))
          @(db/on-value-reaction {:path ["cache-tilbakemeldinger"]})))



;idiom: unwrapped lines are candidates for new constructs
(defn render [r]
  (if-let [uid @(rf/subscribe [:lab/uid])]
    (r/with-let [ipad? ((fn is-master-input? [uid] (= uid @(db/on-value-reaction {:path ["system" "active"]}))) uid)
                 datum (db/on-snapshot-docs-reaction {:path (path-beskjederinbox uid)})
                 sent (db/on-snapshot-docs-reaction {:path (path-beskjedersent uid)})]
      (when @datum
        (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
          (let [admin? @(rf/subscribe [:lab/admin-access])
                {:keys [saldo timekrav] :as user} (user.database/lookup-userinfo uid)
                data (clojure.walk/stringify-keys @data)
                data (mapcat val data)
                antall-eykter (when (some? saldo)
                                (- saldo timekrav (- (* 3 (count (seq data))))))]
            [sc/col-space-8
             [beskjeder uid @datum]
             ;[beskjeder uid @sent]
             [l/pre (path-beskjedersent uid) @sent]
             (for [{:keys [id data]} @sent]
               [l/pre id])
             [tilbakemeldinger uid (cached-datasource uid)]
             (when-not ipad?
               (widgets/disclosure :oversikt/vakter "Vakter i '22"
                                   (vakter uid data)
                                   "Ingen vakter"))
             [widgets/personal user]
             (when (and (or admin? (not ipad?)) saldo timekrav antall-eykter)
               [saldo-header saldo timekrav antall-eykter])
             (widgets/disclosure :oversikt/endringslogg
                                 "Endringslogg"
                                 (endringslogg uid (path-endringslogg uid))
                                 "Det er ikke gjort noen endringer.")

             #_(when admin?
                 [sc/row-bl
                  [sc/small1 "Identitet"]
                  (sc/as-identity uid)])])
          [sc/title1 "Ingen definerte vakter"])))
    [widgets/no-access-view r]))