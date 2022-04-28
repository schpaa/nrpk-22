(ns booking.mine-vakter
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [booking.dine-vakter :refer [beskjeder vakter endringslogg]]
            [tick.core :as t]))

(defn render [r]
  (if-let [loggedin-uid @(rf/subscribe [:lab/uid])]
    (do
      (r/with-let [datum (db/on-snapshot-docs-reaction {:path ["users" loggedin-uid "inbox"]})]
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
               [booking.mine-dine-vakter/header
                {:saldo    saldo
                 :timekrav timekrav
                 :z        z}]
               [vakter loggedin-uid data]
               [endringslogg ["beskjeder" loggedin-uid "endringslogg"]]
               [beskjeder loggedin-uid @datum]])

            [sc/title1 "Ingen definerte vakter"]))))
    [widgets/no-access-view r]))