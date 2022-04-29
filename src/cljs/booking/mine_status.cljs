(ns booking.mine-status
  (:require [db.core :as db]
            [headlessui-reagent.core :as ui]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [booking.dine-vakter :refer [beskjeder vakter endringslogg tilbakemeldinger]]
            [booking.mine-dine-vakter :refer [header]]
            [schpaa.debug :as l]
            [booking.ico :as ico]))

(defn cached-datasource [uid] (filter (fn [[k v]] (and (= uid (:uid v))
                                                       (not (:deleted v))))
                                      @(db/on-value-reaction {:path ["cache-tilbakemeldinger"]})))


;idiom: unwrapped lines are candidates for new constructs
(defn render [r]
  (if-let [uid @(rf/subscribe [:lab/uid])]
    (r/with-let [ipad? (= uid @(db/on-value-reaction {:path ["system" "active"]}))
                 datum (db/on-snapshot-docs-reaction {:path ["beskjeder" uid "inbox"]})]
      (when @datum
        (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
          (let [admin? @(rf/subscribe [:lab/admin-access])
                user (user.database/lookup-userinfo uid)
                data (clojure.walk/stringify-keys @data)
                data (mapcat val data)
                saldo (:saldo user)
                timekrav (:timekrav user)
                antall-eykter (when (some? saldo) (- saldo timekrav (- (* 3 (count (seq data))))))]
            [sc/col-space-8
             [widgets/personal uid user]
             (when (and (or admin? (not ipad?)) saldo timekrav antall-eykter)
               [header saldo timekrav antall-eykter])
             [:div.ml-6 [beskjeder uid @datum]]
             [:div.ml-6 [tilbakemeldinger uid (cached-datasource uid)]]
             (when-not ipad?
               (widgets/disclosure :oversikt/vakter "Vakter i '22"
                                   (vakter uid data)
                                   "Ingen vakter"))
             (widgets/disclosure :oversikt/endringslogg "Endringslogg"
                                 (endringslogg ["beskjeder" uid "endringslogg"])
                                 "Det er ikke gjort noen endringer.")

             (when admin?
               [sc/row-bl
                [sc/small1 "Identitet"]
                (sc/as-identity uid)])])
          [sc/title1 "Ingen definerte vakter"])))
    [widgets/no-access-view r]))