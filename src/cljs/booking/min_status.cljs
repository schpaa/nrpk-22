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
                                              path-endringslogg]]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]))

(defonce store (r/atom {:selector 1}))

(def selector (r/cursor store [:selector]))

(defn cached-datasource [uid]
  (filter (fn [[k v]] (and (= uid (:uid v))
                           (not (:deleted v))))
          @(db/on-value-reaction {:path ["cache-tilbakemeldinger"]})))

(defn selector-to-page-map [selector datum datas fullførte-timer]
  (let [uid @(rf/subscribe [:lab/uid])
        admin? @(rf/subscribe [:lab/admin-access])
        ipad? ((fn is-master-input? [uid] (= uid @(db/on-value-reaction {:path ["system" "active"]}))) uid)]
    (case @selector
      1 (let [{:keys [saldo timekrav] :as user} (user.database/lookup-userinfo uid)]
          [sc/col-space-8
           (when-not ipad?
             [widgets/disclosure
              {:class []}
              :oversikt/vakter
              "Vakter i '22"
              (vakter uid datas)
              "Ingen vakter"])
           (when (and (or admin? (not ipad?)) saldo timekrav fullførte-timer)
             [saldo-header saldo timekrav fullførte-timer])])
      2 [sc/col-space-8
         [messages uid @datum]
         [tilbakemeldinger uid (cached-datasource uid)]
         (widgets/disclosure {}
                             :oversikt/endringslogg
                             "Endringslogg"
                             (endringslogg uid (path-endringslogg uid))
                             "Det er ikke gjort noen endringer.")]
      3
      [sc/co
       [sc/row-center
        [button/icon-and-caption
         {:style    {:box-shadow "var(--shadow-2)"}
          :class    [:cta]
          :on-click #(case @selector
                       :nøklevann (rf/dispatch [:lab/toggle-boatpanel nil])
                       :sjøbasen nil #_(reset! step 1))}
         ico/plus
         "Ny booking"]]
       [booking.lab/render {}]]
      [:div])))

;idiom: unwrapped lines are candidates for new constructs
(defn render [r]
  (if-let [uid @(rf/subscribe [:lab/uid])]
    (r/with-let [datum (db/on-snapshot-docs-reaction {:path (path-beskjederinbox uid)})]
      (when @datum
        (if-let [data (db/on-value-reaction {:path ["calendar" uid]})]
          (let [datas (clojure.walk/stringify-keys @data)
                ;;(->> datas vals (map vals) (remove (comp map? val)) flatten count)
                antall-eykt (->> datas
                                 vals
                                 (map vals)
                                 flatten
                                 (map (comp first vals))
                                 (remove (fn [m] (get m "cancel")))
                                 count)
                completed-hours (or (* 3 antall-eykt) 0)
                user (user.database/lookup-userinfo uid)]
            [sc/col-space-8
             [widgets/personal user]
             [widgets/pillbar selector [[1 "Vakter"]
                                        [2 "Meldinger"]
                                        [3 "Booking"]]]
             (selector-to-page-map selector datum datas completed-hours)])
          [sc/title1 "Ingen definerte vakter"])))
    [widgets/no-access-view r]))
