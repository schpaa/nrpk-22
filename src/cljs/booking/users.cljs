(ns booking.users
  (:require [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [times.api :as ta]
            [schpaa.style.button :as scb]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [tick.core :as t]
            [db.core :as db]
            [clojure.string :as string]))

(defonce state (r/atom {:kald-periode     false
                        :nøkkelnummer     false
                        :stjerne          false
                        :sluttet          false
                        :last-seen        true
                        :sorter-sist-sett true
                        :as-table         true
                        :timekrav         false
                        :sist-endret      true
                        :nøkkelvakter     false
                        :booking          false
                        :admin            false
                        :reverse          true}))

(o/defstyled table-def :table
  :truncate
  [:& {:width           "100%"
       :border-collapse :collapse}
   [:thead
    {:height "3rem"}
    [:tr
     {:text-align :left
      :color      "var(--text2)"}
     [:th sc/small0
      {:padding "2px"}]
     ["th:nth-child(1)" {:min-width "2rem"}]

     ["th:nth-child(2)" {}]]]

   [:tbody
    ["tr:nth-child(even)"
     {:background "var(--floating)"}]
    [:tr
     {:height     "var(--size-6)"
      :background "var(--content)"}
     [:td sc/text2 :truncate
      {:padding-inline "4px"}]
     ["td:nth-child(2)"
      {:text-align :right}]
     ["td:nth-child(4)"
      sc/text0
      {:max-width "10rem"}]]]])

(defn panel []
  [sc/col-space-4
   [sc/row-sc-g2-w
    [sc/text0 "visning"]
    [hoc.toggles/switch-local (r/cursor state [:nøkkelnummer]) "nøkkelnummer"]
    [hoc.toggles/switch-local (r/cursor state [:last-seen]) "sist sett"]]

   [sc/col-space-1
    [sc/row-sc-g2-w
     [sc/text0 "filter"]
     [hoc.toggles/switch-local (r/cursor state [:sluttet]) "utmeldt"]
     [hoc.toggles/switch-local (r/cursor state [:kald-periode]) "kald-periode"]
     [hoc.toggles/switch-local (r/cursor state [:stjerne]) "stjerne"]
     [hoc.toggles/switch-local (r/cursor state [:nøkkelvakter]) "nøkkelvakt"]
     [hoc.toggles/switch-local (r/cursor state [:booking]) "booking"]
     [hoc.toggles/switch-local (r/cursor state [:admin]) "administratorer"]]]

   [sc/row-sc-g2-w
    [sc/text0 "sortering"]
    [hoc.toggles/switch-local (r/cursor state [:sist-endret]) "sist endret"]
    [hoc.toggles/switch-local (r/cursor state [:sorter-sist-sett]) "sist sett"]
    [hoc.toggles/switch-local (r/cursor state [:timekrav]) "timekrav"]
    [hoc.toggles/switch-local (r/cursor state [:reverse]) "omvendt"]]])

(defonce settings (r/atom nil))

(def selected (r/cursor settings [:selected]))

(defn always-panel []
  [:<>
   [sc/col-space-8 {:style {:margin-inline "auto"
                            :max-width "min(calc(100% - 2rem),calc(768px - 4rem)"}}
    [sc/row-sc-g4-w
     [widgets/auto-link nil [:r.reports {:id "saldo-setter"}] booking.reports/report-list]
     [widgets/auto-link nil [:r.reports {:id "siste-nye-vakter"}] booking.reports/report-list]
     [widgets/auto-link nil [:r.reports {:id "brukere-av-booking"}] booking.reports/report-list]
     [widgets/auto-link nil [:r.reports {:id "tilbakemeldinger"}] booking.reports/report-list]
     [widgets/auto-link nil [:r.reports {:id "oppmøte"}] booking.reports/report-list]]]
   [:div {:class [:sticky :top-20]}
    [sc/row-center
     [widgets/pillbar
      {:class [:small :z-10]}
      selected
      [[:a "Nøkkelvakter"]
       [:b "Booking"]
       [:c "Uregistrerte"]
       [:d "Tilbakemeldinger"]]]]]])

(def admin::c (r/cursor state [:admin]))

(defn write-csv
  "Takes a file (path, name and extension) and
   csv-data (vector of vectors with all values) and
   writes csv file."
  [ csv-data]
  (apply str (interpose "\n" (for [e csv-data]
                               (string/join "," e)))))

(defn maps->csv-data
 "Takes a collection of maps and returns csv-data
   (vector of vectors with all values)."
  [maps]
  (let [columns (-> maps first keys)
        headers (mapv name columns)
        rows (mapv #(mapv % columns) maps)]
    (into [headers] rows)))

(defn write-csv-from-maps
  "Takes a file (path, name and extension) and a collection of maps
   transforms data (vector of vectors with all values)
   writes csv file."
  [ maps]
  (->> maps maps->csv-data write-csv))

(comment
  (do
    (write-csv-from-maps [{:navn "Arne" :age 1}{:navn "Bjarne" :age 2}])))

(comment
  "create a csv represenation"
  (do
    (let [ensure-number (fn [p] (let [n (js/parseInt p)] (if (js/isNaN n) 0 n)))
          godkjent-og-ikke-utmeldt (fn [{:keys [godkjent utmeldt timekrav]}] (and godkjent (not utmeldt) (some? timekrav)))
          eksporterte-felt #(select-keys % [:uid :navn :timekrav :saldo :fødselsår])
          convert-timekrav (fn [{:keys [timekrav saldo] :as v}] (assoc v :ny (- saldo timekrav)))
          ensure-timekrav (fn [{:keys [timekrav] :as v}] (assoc v :timekrav (ensure-number timekrav)))]
      (let [data (transduce (comp
                              (map val)
                              (filter godkjent-og-ikke-utmeldt)
                              (map eksporterte-felt)
                              (map ensure-timekrav)
                              (map convert-timekrav))
                            conj [] @(db/on-value-reaction {:path ["users"]}))
            fields (mapv key (first data))]
        (println (count data))
        (println (write-csv-from-maps data))))))




(defn render [r]
  (let [data (transduce (comp
                          (map val)
                          (map (fn [{:keys [timekrav] :as v}]
                                 (if timekrav
                                   (let [p (js/parseInt timekrav)]
                                     (assoc v :timekrav (if (js/isNaN p) 0 p)))
                                   v)))
                          (filter (fn [v] (and
                                            (if @admin::c
                                              (:admin v)
                                              true)
                                            (if @(r/cursor state [:booking])
                                              (:booking-godkjent v)
                                              true)
                                            (if @(r/cursor state [:nøkkelvakter])
                                              (:godkjent v)
                                              true)
                                            (if @(r/cursor state [:kald-periode])
                                              (:kald-periode v)
                                              true)
                                            (if @(r/cursor state [:sluttet])
                                              (:utmeldt v)
                                              (not (:utmeldt v)))
                                            (if @(r/cursor state [:stjerne])
                                              (:stjerne v)
                                              true)))))
                        conj [] @(db/on-value-reaction {:path ["users"]}))
        fields (remove nil? [(when @(r/cursor state [:sorter-sist-sett]) :timestamp-lastvisit-userpage)
                             (when @(r/cursor state [:sist-endret]) :timestamp)
                             #_(when @(r/cursor state [:timekrav]) :timekrav)])
        fields (if (empty? fields) [:navn] fields)
        data (sort-by (apply juxt fields) data)
        users (if @(r/cursor state [:reverse]) (reverse data) data)]
    (let [f (fn [dt] [booking.flextime/flex-datetime
                      (some-> dt t/instant t/date-time)
                      (fn [type content]
                        (cond
                          (= :date type) (ta/time-format content)
                          :else content))])]
      [booking.reports/table-controller-report'
       [booking.reports/table-report
        [:<>
         [:thead
          [:tr
           [:th ""]
           [:th "timer"]
           [:th "nøkkelnummer"]
           [:th "navn"]
           [:th "sist besøkt"]
           [:th "sist endret"]]]
         (into [:tbody]
               (for [{:keys [timekrav timestamp uid nøkkelnummer timestamp-lastvisit-userpage] :as v} users]
                 [:tr
                  [:td [scb/round-normal-listitem
                        [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                                  :style    {:color "var(--text2)"}} ico/pencil]]]
                  [:td (if (pos? timekrav) (str timekrav "t") "—")]
                  [:td nøkkelnummer]
                  [:td (widgets/user-link uid)]
                  [:td (f timestamp-lastvisit-userpage)]
                  [:td (f timestamp)]]))]]])))