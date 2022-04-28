(ns booking.mine-vakter
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [booking.dine-vakter :refer [beskjeder vakter endringslogg tilbakemeldinger]]
            [tick.core :as t]))

(defn render [r]
  (let [admin? @(rf/subscribe [:lab/admin-access])]

    (if-let [loggedin-uid @(rf/subscribe [:lab/uid])]
      (r/with-let [ipad? (= loggedin-uid @(db/on-value-reaction {:path ["system" "active"]}))
                   datum (db/on-snapshot-docs-reaction {:path ["beskjeder" loggedin-uid "inbox"]})]
        (when @datum
          (if-let [data (db/on-value-reaction {:path ["calendar" loggedin-uid]})]
            (let [user (user.database/lookup-userinfo loggedin-uid)
                  data (clojure.walk/stringify-keys @data)
                  data (mapcat val data)
                  saldo (:saldo user)
                  timekrav (:timekrav user)
                  z (when (some? saldo)
                      (- saldo timekrav (- (* 3 (count (seq data))))))]
              [sc/col-space-8
               (when-not ipad? [booking.mine-dine-vakter/header
                                {:saldo    saldo
                                 :timekrav timekrav
                                 :z        z}])
               [beskjeder loggedin-uid @datum]
               [tilbakemeldinger loggedin-uid (filter (fn [[k v]] (and (= loggedin-uid (:uid v))
                                                                       (not (:deleted v))))
                                                      @(db/on-value-reaction {:path ["cache-tilbakemeldinger"]}))]
               (when-not ipad? [vakter loggedin-uid data])
               (when admin?
                 [endringslogg ["beskjeder" loggedin-uid "endringslogg"]])])

            [sc/title1 "Ingen definerte vakter"])))
      [widgets/no-access-view r])))