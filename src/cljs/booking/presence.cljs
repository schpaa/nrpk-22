(ns booking.presence
  (:require [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [booking.lab]
            [re-frame.core :as rf]
            [times.api :as ta]
            [tick.core :as t]
            [booking.modals.feedback]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [reagent.core :as r]
            [db.core :as db]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as button]))

(defn f [dt] [booking.flextime/flex-datetime
              (some-> dt t/instant t/date-time)
              (fn [type content]
                (cond
                  (= :date type) (str
                                   (ta/tech-date-format content)
                                   " "
                                   (ta/time-format content))
                  :else content))])

(o/defstyled table-controller :div
  :overflow-x-auto
  {:width "calc(100vw - 4rem)"}
  [:at-media {:max-width "511px"}
   {:width "calc(100vw)"}])

(o/defstyled table-def :table
  [:&
   {:width           "100%"
    :padding-inline  "1rem"
    :border-collapse :collapse}
   [:thead
    {:height "3rem"}
    [:tr
     {:text-align :left}
     [:th sc/small0
      {:padding "var(--size-1)"}]]]
   [:tbody
    ["tr:nth-child(odd)"
     {:background "var(--floating)"}]
    [:tr
     {:color "green"}
     [:&.online sc/text0]
     [:&.offline sc/text2]
     {:height     "var(--size-6)"
      :background "var(--content)"}
     [:td :truncate
      {:padding-inline "4px"}]
     ["td:nth-child(2)"
      {:text-align :left}]
     ["td:nth-child(4)"
      sc/text0
      {:max-width "10rem"}]]]])

(defn tableline-online [attr [k {:keys [connections ugh] :as v}]]
  (let [uid (name k)]
    [:tr attr
     [:td (sc/icon {:on-click #(rf/dispatch [:app/open-send-message uid])} ico/melding)]
     [:td (widgets/user-link uid)]
     [:td (f ugh)]
     [:td connections]]))

(defn tableline-offline [attr [k {:keys [lastOnline] :as v}]]
  (let [last-online-ms (cljs-time.coerce/to-long lastOnline)
        uid (name k)]
    [:tr attr
     [:td (sc/icon {:on-click #(rf/dispatch [:app/open-send-message (name k)])} ico/melding)]
     [:td (widgets/user-link uid)]
     #_[:td (or (user.database/lookup-username (name k)) [sc/small2 k])]
     [:td (f last-online-ms)]
     [:td]]))

(def header
  [:thead
   [:tr
    [:th ""]
    [:th "navn"]
    [:th "sist sett"]
    [:th "forbindelser"]]])

(defonce settings (r/atom {}))

(defn render [r data]
  [booking.reports/table-controller-report'
   [booking.reports/table-report
    [:<>
     header
     (into [:tbody]
           (concat
             ;todo wtf ugh? (see db.core)
             (for [v (sort-by (comp :ugh last) > (:online @data))]
               [tableline-online {:class [:online]} v])
             (when @(r/cursor settings [:show-offline])
               (for [v (sort-by (comp str :lastOnline last) > (:offline @data))]
                 [tableline-offline {:class [:offline]} v]))))]]])

(defn always []
  [sc/row-sc-g4-w
   [button/icon-and-caption
    {:class [:danger-outline]
     :on-click #(db.core/database-set {:path ["presence"] :value {}})} ico/trash "Slett logg"]
   [hoc.toggles/switch-local (r/cursor settings [:show-offline]) "vis offline"]])

#_(defn panel []
    [sc/col
     [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
      [hoc.toggles/switch-local (r/cursor settings [:show-offline]) "vis offline"]]])
