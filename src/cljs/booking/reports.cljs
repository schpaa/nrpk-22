(ns booking.reports
  (:require [tick.core :as t]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [db.core :as db]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [re-frame.core :as rf]
            [booking.ico :as ico]))

(o/defstyled table-controller-report :div
  :overflow-x-auto
  {:width "calc(100vw - 4rem)"}
  [:at-media {:max-width "511px"}
   {:width "calc(100vw)"}])

(o/defstyled table-report :table
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
     {:color "var(--text1)"}
     [:&.online sc/text0]
     [:&.offline sc/text2]
     {:min-height "var(--size-6)"
      :background "var(--content)"}
     [:td :whitespace-nowrap
      {:padding-block  "var(--size-1)"
       :vertical-align :top
       :padding-inline "4px"}
      [:&.message-col
       :whitespace-normal
       {:width "100%"}]]
     #_["td:nth-child(2)"
        {:text-align :left}]
     #_["td:nth-child(4)"
        sc/text0
        #_{:max-width "10rem"}]]]])

(o/defstyled phonenumber :td
  :tabular-nums
  {:letter-spacing "var(--font-letterspacing-0)"})

(defn siste-nye-vakter [r]
  (let [ordered-log (->> @(db/on-value-reaction {:path ["calendar"]})
                         (mapcat val)
                         (mapcat val)
                         (reduce (fn [a [k v]]
                                   (conj a
                                         [(val (first v))
                                          (key (first v))
                                          k])) [])
                         (sort-by first >))]
    [:div
     [table-controller-report
      [table-report
       [:<>
        [:thead
         [:tr
          [:th "registrert"]
          [:th "navn"]
          [:th "vaktdato"]
          [:th "dag"]
          [:th "kl"]]]
        (into [:tbody]
              (for [[time user-id slot] ordered-log]
                [:tr {}
                 [:td.tabular-nums (times.api/very-compressed-date (t/date-time time))]
                 [:td.truncate (user.database/lookup-username (name user-id))]
                 [:td.tabular-nums (times.api/date-format-sans-year (t/date-time (name slot)))]
                 [:td.tabular-nums (times.api/day-name (t/date-time (name slot)) :length 3)]
                 [:td.tabular-nums (times.api/time-format-hour (t/date-time (name slot)))]]))]]]]))

(defn brukere-av-booking []
  (let [data @(db/on-value-reaction {:path ["users"]})
        data (filter (fn [[k v]] (:request-booking v)) data)
        data (map (fn [[k v]] (select-keys v [:navn :våttkortnr :telefon :booking-expert :dato-godkjent-booking])) data)]
    [table-controller-report
     [table-report
      [:<>
       [:thead
        [:tr
         [:th "Navn"]
         [:th "Våttkortnr"]
         [:th "Telefon"]
         [:th "Ekspert"]
         [:th "Dato godkjent"]]]
       (into [:tbody]
             (for [{:keys [navn våttkortnr telefon booking-expert dato-godkjent-booking]} data
                   :let [dato-godkjent-booking (if (empty? dato-godkjent-booking) nil dato-godkjent-booking)]]
               [:tr
                [:td navn]
                [:td.tabular-nums våttkortnr]
                [phonenumber telefon]
                [:td (if booking-expert "Ekspert")]
                [:td (some-> dato-godkjent-booking t/date times.api/date-format)]]))]]]))

(defn tilbakemeldinger []
  (r/with-let [data (db/on-snapshot-docs-reaction {:path ["tilbakemeldinger"]})]
    (let [data (map :data @data)]
      [:<>
       [table-controller-report
        [table-report
         [:<>
          [:thead
           [:tr
            [:th "Telefon/Navn"]
            [:th "Dato"]
            [:th "Kilde/Tekst"]]]
          (into [:tbody]
                (for [{:strs [uid kilde text timestamp]} data]
                  [:tr
                   [:td [sc/col-space-2
                         [sc/link {:href (str "tel:" (:telefon (user.database/lookup-userinfo uid)))}
                          (:telefon (user.database/lookup-userinfo uid))]
                         [sc/text1 (user.database/lookup-username uid)]]]

                   [:td (times.api/timestamp->local-datetime-str timestamp)]
                   [:td {:class [:message-col]}
                    [sc/col-space-2
                     [sc/text2 kilde]
                     [sc/text1 text]]]]))]]]])))

(def report-list
  [{:name   "Rapport: siste nye vakter"
    :id     "siste-nye-vakter"
    :access [:admin]
    :icon   ico/nokkelvakt
    :action #(rf/dispatch [:app/navigate-to [:r.reports {:id "siste-nye-vakter"}]])
    :f      siste-nye-vakter}
   {:name   "Rapport: brukere av booking på sjøbasen"
    :id     "brukere-av-booking"
    :access [:admin]
    :icon   ico/nokkelvakt
    :action #(rf/dispatch [:app/navigate-to [:r.reports {:id "brukere-av-booking"}]])
    :f      brukere-av-booking}
   {:name   "Rapport: tilbakemeldinger"
    :id     "tilbakemeldinger"
    :access [:admin]
    :icon   ico/nokkelvakt
    :action #(rf/dispatch [:app/navigate-to [:r.reports {:id "tilbakemeldinger"}]])
    :f      tilbakemeldinger}])

(defn page [r]
  (let [report-id (-> r :path-params :id)
        result (filter #(= (:id %) report-id) report-list)]
    (if-let [result (first result)]
      {:always-panel (fn [] [:div.w-full
                             [sc/title1 (:name result)]])
       :render-fullwidth
       (fn []
         [sc/col {:class [:w-full]}
          #_[sc/row {:class [:mx-auto :max-w-xl :px-4]}
             [sc/title1 (:name result)]]
          ((:f result))])}
      {:render (fn [] [:div "?"])})))
