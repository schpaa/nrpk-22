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
                 #_#_sent (db/on-snapshot-docs-reaction {:path (path-beskjedersent uid)})]
      (when @datum
        (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
          (let [admin? @(rf/subscribe [:lab/admin-access])
                {:keys [saldo timekrav] :as user} (user.database/lookup-userinfo uid)
                datas (clojure.walk/stringify-keys @data)
                data datas ;(mapcat val datas)
                antall-økter (->> data vals (map vals) flatten count)
                fullførte-timer (or
                                  (* 3 antall-økter) 0

                                  #_(when (some? saldo)
                                      (- saldo timekrav (+ (* 3 antall-økter)))) 0)]
            [sc/col-space-8
             ;[l/pre (->> data vals (map vals) flatten count)]
             [beskjeder uid @datum]
             ;[beskjeder uid @sent]
             ;[l/pre (path-beskjedersent uid) @sent]
             #_(for [{:keys [id data]} @sent]
                 [l/pre id])
             [tilbakemeldinger uid (cached-datasource uid)]
             [l/pre antall-økter fullførte-timer data]
             (when-not ipad?
               (widgets/disclosure {}
                                   :oversikt/vakter
                                   "Vakter i '22"
                                   (vakter uid datas)
                                   "Ingen vakter"))
             [widgets/personal user]
             (when (and (or admin? (not ipad?)) saldo timekrav fullførte-timer)
               [saldo-header saldo timekrav fullførte-timer])
             (widgets/disclosure {}
                                 :oversikt/endringslogg
                                 "Endringslogg"
                                 (endringslogg uid (path-endringslogg uid))
                                 "Det er ikke gjort noen endringer.")])


          [sc/title1 "Ingen definerte vakter"])))
    [widgets/no-access-view r]))
