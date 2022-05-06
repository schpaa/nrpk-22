(ns booking.reports
  (:require [tick.core :as t]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [db.core :as db]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            [schpaa.style.input :as sci]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.dine-vakter]))

(o/defstyled table-controller-report :div
  [:& :overflow-x-auto
   {:width "calc(100vw - 14rem)"}
   [:&.narrow-toolbar {:width "calc(100vw - 4rem)"}]]
  [:at-media {:max-width "511px"}
   {:width "calc(100vw)"}
   [:&.narrow-toolbar {:width "calc(100vw)"}]])

(defn table-controller-report' [& c]
  (let [narrow-toolbar (not @(schpaa.state/listen :app/toolbar-with-caption))]
    [table-controller-report {:class [(when narrow-toolbar :narrow-toolbar)]} c]))

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
      {:padding-inline "4px"
       :padding-block  "var(--size-1)"}
      [:&.narrow {:min-width "5rem"
                  :xoutline  "1px solid red"}]]]]

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
       :vertical-align :middle
       :padding-inline "8px"}
      [:&.narrow {:min-width "13rem"
                  :width     "13rem"}]
      [:&.message-col
       :whitespace-normal
       {:width "100%"}]
      [:&.hcenter {:padding-inline "1rem"}]

      [:&.vcenter {
                   :vertical-align :middle}]]]]])

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
     [table-controller-report'
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
                 [:td.truncate (widgets/user-link (name user-id))]
                 [:td.tabular-nums (times.api/date-format-sans-year (t/date-time (name slot)))]
                 [:td.tabular-nums (times.api/day-name (t/date-time (name slot)) :length 3)]
                 [:td.tabular-nums (times.api/time-format-hour (t/date-time (name slot)))]]))]]]]))

(defn brukere-av-booking []
  (let [data @(db/on-value-reaction {:path ["users"]})
        data (filter (fn [[k v]] (:request-booking v)) data)
        data (map (fn [[k v]] (select-keys v [:uid :navn :våttkortnr :telefon :booking-expert :dato-godkjent-booking])) data)]
    [table-controller-report'
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
             (for [{:keys [navn uid våttkortnr telefon booking-expert dato-godkjent-booking]} data
                   :let [dato-godkjent-booking (if (empty? dato-godkjent-booking) nil dato-godkjent-booking)]]
               [:tr
                [:td (widgets/user-link uid)]
                [:td.tabular-nums våttkortnr]
                [phonenumber telefon]
                [:td (if booking-expert "Ekspert")]
                [:td (some-> dato-godkjent-booking t/date times.api/date-format)]]))]]]))

(defn tilbakemeldinger []
  (r/with-let [loggedin-uid @(rf/subscribe [:lab/uid])
               data' (db/on-value-reaction {:path ["cache-tilbakemeldinger"]})]
    (let [data (map :data @data')]
      [:<>
       ;[l/pre @data']
       [table-controller-report'
        [table-report
         [:<>
          [:thead
           [:tr
            [:th]
            [:th "Navn"]

            [:th "Dato/Kilde/Tilbakemelding"]]]
          (into [:tbody]
                (for [[_ {:keys [uid kilde text timestamp] :as m}] (sort-by (comp :timestamp val) > @data')
                      :let [{:keys [telefon navn]} (user.database/lookup-userinfo uid)]]
                  [:tr
                   [:td [hoc.buttons/round-cta-pill
                         {:on-click #(booking.dine-vakter/reply-to-msg loggedin-uid uid nil #_msg-id)}
                         [sc/icon ico/tilbakemelding]]]
                   [:td {:style {:vertical-align "top"}} (widgets/user-link uid)]
                   [:td {:class [:message-col]}
                    [sc/col-space-2
                     [sc/small2 (when timestamp (times.api/date-format (t/instant timestamp)))]
                     [sc/text2 kilde]
                     [sc/text1 text]]]]))]]]])))

(defn saldo-setter []
  (let [set-sort (fn [sort-order field]
                   (swap! sort-order conj {field :-}))
        sortable-header (fn [sort-order field c]
                          [:th.cursor-default
                           {:on-click #(set-sort sort-order field)}
                           [sc/small (or c field)]])]
    (r/with-let [sort-order (r/atom '({:navn :-}))
                 act (r/atom nil)
                 focus-field-id (r/atom nil)]
      (let [data (db/on-value-reaction {:path ["users"]})
            data (filter (fn [[k v]] (and (:godkjent v) (not (:utmeldt v)))) @data)
            sortable-header (partial sortable-header sort-order)]
        [:<>
         [l/pre (distinct @sort-order)]
         [table-controller-report'
          [table-report
           [:<>
            [:thead
             [:tr
              [:th]
              [sortable-header :nøkkelnummer]
              [:th "Timekrav '22"]
              [:th "Balanse"]
              [:th "Saldo '21"]
              [:th "Saldo '22"]
              [:th "Økter '22"]
              [sortable-header :navn]]]
            (into [:tbody]
                  (for [[idx [k {:keys [eykts uid nøkkelnummer navn balance saldo timekrav] :as v}]]
                        (map-indexed vector
                                     (sort-by (comp (juxt (comp :nøkkelnummer) :balance :eykts :navn) val) <
                                              (into {}
                                                    (map (fn [[k v]]
                                                           (let [uid k
                                                                 saldo (js/parseInt (:saldo v))
                                                                 saldo (if (js/isNaN saldo) 0 saldo)
                                                                 timekrav (js/parseInt (:timekrav v))
                                                                 timekrav (if (js/isNaN timekrav) 0 timekrav)
                                                                 eykts (count (->> @(db/on-value-reaction {:path ["calendar" (name uid)]})
                                                                                   (mapcat val)
                                                                                   (mapcat val)))]
                                                             [k (assoc v
                                                                  :eykts eykts
                                                                  :saldo saldo
                                                                  :nøkkelnummer (or (:nøkkelnummer v) "")
                                                                  :timekrav timekrav
                                                                  :balance (+ (- saldo timekrav) (* 3 eykts)))]))
                                                         data))))
                        :let [#_#_eykts (count (->> @(db/on-value-reaction {:path ["calendar" (name uid)]})
                                                    (mapcat val)
                                                    (mapcat val)))
                              saldo' (- (+ saldo (* 3 eykts)) timekrav)]]
                    [:tr
                     [:td.vcenter.hcenter
                      [scb/round-normal-listitem
                       [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                                 :style    {:color "var(--text2)"}} ico/pencil]]]
                     [:td.vcenter (or nøkkelnummer "—")]
                     [:td.vcenter [sc/text1 {:class [:tabular-nums :xtext-right]} (or (when timekrav (str timekrav "t")) "—")]]
                     [:td.vcenter [sc/text1 {:class [:tabular-nums :xtext-right]} balance]]
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
                                     (if (= idx @focus-field-id)
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
                                                               (reset! focus-field-id idx)
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
                     [:td.vcenter [sc/text1-cl {:class [:tabular-nums :tracking-tight]
                                                :style {:color (if (or (pos? saldo') (zero? saldo'))
                                                                 "var(--green-5)"
                                                                 "var(--red-5)")}}
                                   (if (zero? saldo') 0 (str saldo' "t"))]]
                     [:td (str (* 3 eykts) "t")]
                     [:td.vcenter [sc/link {:href (kee-frame.core/path-for [:r.dine-vakter {:id (name uid)}])} navn]]]))]]]]))))

(defn oppmøte []
  (r/with-let [focused (r/atom nil)
               search (r/atom "")]
    (let [data (db/on-value-reaction {:path ["users"]})
          data (filter (fn [[k v]]
                         (and (:godkjent v)
                              (not (:utmeldt v))
                              (let [p (try
                                        (re-pattern (str "(?i)" @search))
                                        (catch js/Error _ nil))]
                                (when p
                                  (or
                                    (some? (re-find p (:navn v)))
                                    (some? (re-find p (str (:telefon v))))))))) @data)]
      [:<>
       [:div.sticky.top-0.z-10
        [:div.h-auto.p-4
         {:style {
                  :background "var(--content)"}}
         [:div.relative
          [sci/input {:placeholder   "Søk"
                      :errors        []
                      :autoFocus     1
                      :on-focus      #(reset! focused true)
                      :on-blur       #(reset! focused false)
                      :values        {:search @search}
                      :on-key-down   #(case (.-keyCode %)
                                        27 (reset! search "")
                                        13 (when (= 1 (count data))
                                             (let [[uid d] (first data)
                                                   uid (name uid)]
                                               (tap> uid)
                                               (tap> d)
                                               (db/database-update {:path  ["users" uid]
                                                                    :value {:attended (not (:attended d))}})))


                                        (tap> (.-keyCode %)))
                      :handle-change #(reset! search (.. % -target -value))}
           :search
           {:class [:relative]
            :style {
                    :background "var(--field)"
                    :box-shadow "var(--shadow-2)"
                    :color      "var(--fieldcopy)"}}

           nil
           :search]
          [:div.absolute.top-0.pointer-events-none
           {:style {:top       "50%"
                    :right     (if (seq @search) "32px" "8px")
                    :transform "translateY(-50%)"}}
           (when @focused
             [sc/row-sc-g2
              (when (= 1 (count data)) [sc/as-shortcut "ENTER => oppmøtt"])
              (when (some? @search) [sc/as-shortcut "esc => nytt søk"])])]]]]
       (if-not (pos? (count data))
         [sc/row-center {:style {:height "var(--size-10)"}}
          [sc/title2 (str "Ingen passet med ") [:span {:style {:color "var(--text1)"}} @search]]]
         [table-controller-report'
          [table-report
           [:<>
            [:thead
             [:tr
              [:th.narrow]
              [:th.narrow "Oppmøte"]
              [:th.narrow "Saldo '22"]

              [:th "Navn"]]]
            (into [:tbody]
                  (for [[idx [k {:keys [uid nøkkelnummer navn saldo timekrav attended] :as v}]] (map-indexed vector (sort-by (comp :navn val) < data))
                        :let [e idx]]
                    [:tr
                     [:td.vcenter.hcenter
                      [scb/round-normal-listitem
                       [sc/icon {:on-click #(rf/dispatch [:lab/show-userinfo v])
                                 :style    {:color "var(--text2)"}} ico/pencil]]]
                     [:td.vcenter [schpaa.style.hoc.toggles/small-switch-base
                                   {:clear-bg true
                                    :duration "1110ms"}
                                   (if attended "deltar" "—")
                                   #(-> attended)
                                   #(db/database-update {:path  ["users" uid]
                                                         :value {:attended %}})]]
                     #_[:td.vcenter (or nøkkelnummer "—")]

                     [:td.w-64.vcenter (let [eykts (count (->> @(db/on-value-reaction {:path ["calendar" (name uid)]})
                                                               (mapcat val)
                                                               (mapcat val)))
                                             saldo (js/parseInt (or saldo 0))
                                             timekrav (js/parseInt (or timekrav 0))
                                             saldo' (- (+ saldo (* 3 eykts)) timekrav)]
                                         [sc/text1-cl {:class [:tabular-nums :tracking-tight]
                                                       :style {:color (if (or (pos? saldo') (zero? saldo')) "var(--green-5)" "var(--red-5)")}}
                                          (if (zero? saldo') 0 (str saldo' "t"))])]
                     [:td.vcenter.w-full [sc/link {:href (kee-frame.core/path-for [:r.dine-vakter {:id (name uid)}])} navn]]]))]]])])))

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
   {:name    "Inboks for tilbakemelding"
    :caption "Tilbakemeldinger"
    :id      "tilbakemeldinger"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "tilbakemeldinger"}]])
    :f       tilbakemeldinger}
   {:name    "Rapport: oppmøte"
    :caption "Oppmøte"
    :id      "oppmøte"
    :access  [:admin]
    :icon    ico/nokkelvakt
    :action  #(rf/dispatch [:app/navigate-to [:r.reports {:id "oppmøte"}]])
    :f       oppmøte}])

(defn page [r]
  (let [{report-id :id} (-> r :path-params)
        result (filter #(= (:id %) report-id) report-list)]
    (if-let [result (first result)]
      {:always-panel     (fn [] [:div.w-full [sc/hero-p (:name result)]])
       :render-fullwidth (fn []
                           [sc/col {:class [:w-full]}
                            ((:f result))])}
      {:render (fn [] [:div "?"])})))
