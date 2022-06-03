(ns booking.min-status
  (:require [db.core :as db]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [booking.dine-vakter :refer [messages vakter endringslogg tilbakemeldinger]]
            [booking.mine-dine-vakter :refer [saldo-header
                                              path-beskjederinbox
                                              path-beskjedersent
                                              path-endringslogg]]))

(defonce store (r/atom {:selector 1}))

(def selector (r/cursor store [:selector]))

(defn cached-datasource [uid]
  (filter (fn [[k v]] (and (= uid (:uid v))
                           (not (:deleted v))))
          @(db/on-value-reaction {:path ["cache-tilbakemeldinger"]})))

(defn selector-to-page-map [selector datum datas]
  (let [uid @(rf/subscribe [:lab/uid])
        ipad? ((fn is-master-input? [uid] (= uid @(db/on-value-reaction {:path ["system" "active"]}))) uid)]
    (case @selector
      1 [sc/col-space-8
         (when-not ipad?
           [widgets/disclosure
            {:class []}
            :oversikt/vakter
            "Vakter i '22"
            (vakter uid datas)
            "Ingen vakter"])]
      2 [sc/col-space-8
         [sc/title "Booking"]]
      3 [sc/col-space-8
         [messages uid @datum]
         [tilbakemeldinger uid (cached-datasource uid)]
         (widgets/disclosure {}
                             :oversikt/endringslogg
                             "Endringslogg"
                             (endringslogg uid (path-endringslogg uid))
                             "Det er ikke gjort noen endringer.")]
      [:div])))

;idiom: unwrapped lines are candidates for new constructs
(defn render [r]
  (if-let [uid @(rf/subscribe [:lab/uid])]
    (r/with-let [;todo extract
                 ipad? ((fn is-master-input? [uid] (= uid @(db/on-value-reaction {:path ["system" "active"]}))) uid)
                 datum (db/on-snapshot-docs-reaction {:path (path-beskjederinbox uid)})
                 #_#_sent (db/on-snapshot-docs-reaction {:path (path-beskjedersent uid)})]
      (when @datum
        (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
          (let [admin? @(rf/subscribe [:lab/admin-access])
                {:keys [saldo timekrav] :as user} (user.database/lookup-userinfo uid)
                datas (clojure.walk/stringify-keys @data)
                antall-økter #_(->> datas vals (map vals) (remove (comp map? val)) flatten count)
                (->> datas
                     vals
                     (map vals)
                     flatten
                     (map (comp first vals))
                     (remove (fn [m] (get m "cancel")))
                     count)
                fullførte-timer (or (* 3 antall-økter) 0)]
            [sc/col-space-8
             [sc/co
              [widgets/personal user]

              (when (and (or admin? (not ipad?)) saldo timekrav fullførte-timer)
                [saldo-header saldo timekrav fullførte-timer])]

             [widgets/pillbar selector [[1 "Vakter"]
                                        [2 "Booking"]
                                        [3 "Meldinger"]]]

             (selector-to-page-map selector datum datas)])




          [sc/title1 "Ingen definerte vakter"])))
    [widgets/no-access-view r]))
