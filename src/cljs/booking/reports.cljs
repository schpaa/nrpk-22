(ns booking.reports
  (:require [tick.core :as t]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [db.core :as db]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]))

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
     {:color      "var(--text1)"
      ;:vertical-align :middle
      :background "var(--content)"}
     [:&.online sc/text0]
     [:&.offline sc/text2]
     [:td :whitespace-nowrap
      {:height         "var(--size-8)"
       :padding-block  "var(--size-1)"
       :vertical-align :top
       :padding-inline "4px"}
      [:&.message-col
       :whitespace-normal
       {:width "100%"}]
      [:&.hcenter {:padding-inline "1rem"}]
      [:&.vcenter {:vertical-align :middle}]]
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

(defn saldo-setter []
  (r/with-let [act (r/atom nil)
               focus-field-id (r/atom nil)]
    (let [data (db/on-value-reaction {:path ["users"]})
          data (filter (fn [[k v]] (and (:godkjent v) (not (:utmeldt v)))) @data)]
      [:<>
       [table-controller-report
        [table-report
         [:<>
          [:thead
           [:tr
            [:th]
            [:th "Nøkkelnummer"]
            [:th "Timekrav '22"]
            [:th "Saldo '21"]
            [:th "Saldo '22"]
            [:th "Navn"]]]
          (into [:tbody]
                (for [[idx [k {:keys [uid nøkkelnummer navn saldo timekrav] :as v}]] (map-indexed vector (sort-by (comp :navn val) < data))
                      :let [e idx]]
                  [:tr
                   [:td.vcenter.hcenter
                    [scb/round-normal-listitem
                     [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                               :style    {:color "var(--text2)"}} ico/pencil]]]
                   [:td.vcenter (or nøkkelnummer "—")]
                   [:td.vcenter [sc/text1 {:class [:tabular-nums :xtext-right]} (or (when timekrav (str timekrav "t")) "—")]]
                   [:td.vcenter (r/with-let [value (r/atom saldo)
                                             set-fn (fn [k v] (db/database-update
                                                                {:path  ["users" (name k)]
                                                                 :value {:timestamp (str (t/now))
                                                                         :saldo     (js/parseInt v)}}))]
                                  [:div
                                   {:style {:overflow      :hidden
                                            :width         "4rem"
                                            :height        "2rem"
                                            :margin        0
                                            :padding       0

                                            :border-radius "var(--radius-0)"
                                            :border        "2px solid var(--toolbar)"}}
                                   (if (= e @focus-field-id)
                                     [:input {:auto-focus  true
                                              :ref         (fn [el] (if-not @act (reset! act el)))
                                              :type        :text
                                              :value       @value
                                              :on-key-down #(cond
                                                              (= 13 (.-keyCode %))
                                                              (do (set-fn k @value) (reset! focus-field-id nil))
                                                              (= 27 (.-keyCode %))
                                                              (do (reset! focus-field-id nil)))
                                              :on-change   #(do
                                                              (tap> {:key (.-keyCode %)})
                                                              (reset! value (-> % .-target .-value)))
                                              :on-blur     #(let [v (-> % .-target .-value)]
                                                              (if (empty? v)
                                                                (do #_(swap! light assoc-in [e :text] (str "uten-tittel-" e)))
                                                                (do (set-fn k @value) (reset! focus-field-id nil)))
                                                              (reset! act nil))
                                              :style       {;:background    "white"
                                                            :overflow       :hidden
                                                            :padding-inline "var(--size-2)"
                                                            ;:padding-block "var(--size-2)"
                                                            ;:border        "2px solid var(--toolbar)"
                                                            :height         "100%"}
                                              :class       [:-debug :m-0 :xpy-0 :cursor-text
                                                            :focus:outline-none :w-full]}]
                                     [sc/text1 {:on-click #(do
                                                             (reset! focus-field-id e)
                                                             (reset! value saldo)
                                                             (when @act
                                                               (do
                                                                 (tap> "Attempt focus")
                                                                 (.focus @act)))
                                                             #_(.stopPropagation %))
                                                :style    {;:padding-block     "var(--size-2)"
                                                           ;:overflow :clip
                                                           ;:background-color  "white"
                                                           ;:vertical-align :middle
                                                           :padding-inline    "var(--size-2)"
                                                           :display           :flex
                                                           :align-items       :center
                                                           :height            "100%"
                                                           ;:height            "2rem"

                                                           :xbackground-color "var(--toolbar)"}
                                                :class    [:tabular-nums :tracking-tight :cursor-text
                                                           :w-full]}
                                      (or (when saldo (str saldo "t")) "—")])])]
                   [:td.vcenter (let [eykts (count (->> @(db/on-value-reaction {:path ["calendar" (name uid)]})
                                                        (mapcat val)
                                                        (mapcat val)))
                                      saldo (js/parseInt (or saldo 0))
                                      timekrav (js/parseInt (or timekrav 0))
                                      saldo' (- (+ saldo (* 3 eykts)) timekrav)]
                                  [sc/text1-cl {:class [:tabular-nums :tracking-tight]
                                                :style {:color (if (or (pos? saldo') (zero? saldo')) "var(--green-5)" "var(--red-5)")}}
                                   (if (zero? saldo') 0 (str saldo' "t"))])]
                   [:td.vcenter [sc/link {:href (kee-frame.core/path-for [:r.dine-vakter {:id (name uid)}])} navn]]]))]]]])))


(def report-list
  [{:name    "Rapport: saldo for nøkkelvakter"
    :caption "Nøkkelvakt-saldo"
    :id      "saldo-setter"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "saldo-setter"}]])
    :f       saldo-setter}
   {:name    "Rapport: siste nye vakter"
    :caption "Nyeste registrerte vakter"
    :id      "siste-nye-vakter"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "siste-nye-vakter"}]])
    :f       siste-nye-vakter}
   {:name    "Rapport: brukere av booking på sjøbasen"
    :caption "Brukere av booking"
    :id      "brukere-av-booking"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "brukere-av-booking"}]])
    :f       brukere-av-booking}
   {:name    "Rapport: tilbakemeldinger"
    :caption "Tilbakemeldinger"
    :id      "tilbakemeldinger"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "tilbakemeldinger"}]])
    :f       tilbakemeldinger}])

(defn page [r]
  (let [{report-id :id} (-> r :path-params)
        result (filter #(= (:id %) report-id) report-list)]
    (if-let [result (first result)]
      {:always-panel     (fn [] [:div.w-full [sc/title1 (:name result)]])
       :render-fullwidth (fn []
                           [sc/col {:class [:w-full]}
                            ((:f result))])}
      {:render (fn [] [:div "?"])})))
