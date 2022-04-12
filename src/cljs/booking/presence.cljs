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
            [db.core :as db]))

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
     [:td (or (user.database/lookup-username uid) "—")]
     [:td (f ugh)]
     [:td connections]]))

(defn tableline-offline [attr [k {:keys [lastOnline] :as v}]]
  (let [last-online-ms (cljs-time.coerce/to-long lastOnline)]
    [:tr attr
     [:td]
     [:td (or (user.database/lookup-username (name k)) "—")]
     [:td (f last-online-ms)]
     [:td]]))

(def header [:thead
             [:tr
              [:th ""]
              [:th "navn"]
              [:th "sist sett"]
              [:th "forbindelser"]]])

(defonce settings (r/atom {}))

(defn presence [r data]
  [table-controller
   [table-def
    [:<>
     header
     (into [:tbody]
           (concat
             (for [v (sort-by (comp :ugh last) > (:online @data))]
               [tableline-online {:class [:online]} v])
             (when @(r/cursor settings [:show-offline])
               (for [v (sort-by (comp :lastOnline last) > (:offline @data))]
                 [tableline-offline {:class [:offline]} v]))))]]])

(defn panel []
  [sc/col-space-8
   [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
    [hoc.toggles/switch-local (r/cursor settings [:show-offline]) "vis offline"]]])

(defn always []
  [schpaa.style.hoc.buttons/reg-pill-icon {:on-click #(db.core/database-set {:path ["presence"] :value {}})} ico/trash "Slett historikk"])
