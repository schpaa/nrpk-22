(ns user.views
  (:require [clojure.string :as str]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [shadow.resource :refer [inline]]
            [times.api :as ta]
            [arco.react]
            [user.database]
            [nrpk.fsm-helpers :refer [send]]
            [eykt.content.rapport-side :refer [top-bottom-view map-difference]]
            [booking.views.picker]
            [booking.views]
            [booking.bookinglist]
            [booking.ico :as ico]
            [db.core :as db]
            [schpaa.modal.readymade :as readymade]
            [schpaa.markdown :refer [md->html]]
            [schpaa.debug :as l]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.modal :as modal]
            [schpaa.style :as st]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.dialog]
            [eykt.content.rapport-side :refer [map-difference]]))

(defn confirm-registry []
  #_(apply send
           (modal/form-action
             {:primary "Ok"
              :title   "Bekreftet"
              :text    [:div.leading-normal
                        [:p "Registreringen er fullført. Velkommen!"]]})))

(def user-machine
  {:initial :s.initial
   :on      {:e.restart         {:target [:> :user :s.initial]}
             :e.edit            {:target [:> :user :s.editing]}
             :e.cancel-useredit {:target [:> :user :s.initial]}
             :e.store           {:target  [:> :user :s.store]
                                 :actions [(fn [st {:keys [data] :as _event}]
                                             ;(user.database/write (:values data))
                                             (confirm-registry)
                                             st)]}}
   :states  {:s.initial {}
             :s.editing {}
             :s.store   {:after [{:delay  1000
                                  :target :s.initial}]}
             :s.ready   {}
             :s.error   {}}})

;region prelim

(defn all-registered [{:keys [some-data*]}]
  (when @some-data*
    [:<>
     (for [[a b] (keep (juxt :input :shortname) (vals @some-data*))]
       [rounded-view {:dark true} [:div.flex.justify-between
                                   [:div a]
                                   [:div {:class (if-not b "text-black/25")} (or b "ingen")]]])]))

(def loggout-command
  (hoc.buttons/regular
    {:on-click
     #(readymade/ok-cancel
        {:type    :form
         :flags   #{:timeout :weak-dim}
         :content "Dette vil logge deg ut av kontoen på denne enheten."
         :ok      (fn [] (do (db/sign-out)
                             (rf/dispatch [:app/navigate-to [:r.forsiden]])
                             #_(schpaa.modal.readymade/message {:content "Du har logget ut"})))})}
    "Logg ut"))

(defn removeaccount-command [uid]
  (let [removal-date (some-> (db/on-value-reaction {:path ["users" uid]}) deref :removal-date)]
    (if (and (some? removal-date)
             (t/< (t/date) (t/date removal-date)))
      (hoc.buttons/regular {:on-click #(readymade/ok-cancel
                                         {:flags           #{:wide}
                                          :button-captions (fn [id] (get {:ok     "Gjennopprett nå!"
                                                                          :cancel "Nei, forresten"} id))
                                          :ok              (fn [] (user.database/mark-account-for-restore uid))
                                          #_#_:footer [[:div "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter."]
                                                       [:div "Etter 14 dager slettes alle data."]]
                                          :content         "Er du sikker på at du vil gjennopprette kontoen din?"})}
                           "Gjennopprett konto" #_(times.api/format "Gjennopprett (innen %s)" (ta/short-date-format removal-date)))
      (hoc.buttons/regular
        {:type :button
         :on-click
         #(readymade/ok-cancel
            {:flags           #{:wide}
             :button-captions (fn [id] (get {:ok     "Slett konto!"
                                             :cancel "Avbryt"} id))
             :ok              (fn [] (user.database/mark-account-for-removal uid))
             :footer          [[:div "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter."]
                               [:div "Etter 14 dager slettes alle data."]]
             :content         "Er du sikker på at du vil slette booking-kontoen din?"})}
        "Slett konto"))))

(defn userstatus-form [user-auth]
  (let [name (:display-name user-auth)
        {:keys [bg fg+]} (st/fbg' :form)
        accepted? (true? (:booking-godkjent (user.database/lookup-userinfo (:uid user-auth))))
        use-booking? (true? (:bruke-booking (user.database/lookup-userinfo (:uid user-auth))))
        [status-color status-text] (cond
                                     accepted? [:text-alt "Godkjent booking"]
                                     use-booking? [:text-amber-500 "Godkjenning venter"]
                                     :else [:text-rose-500 "Ikke påmeldt"])]
    [:div.p-4.max-w-md.mx-auto
     [:div.p-4.shadow.rounded.space-y-2
      {:class (concat bg fg+)}
      [:div.flex.justify-between.items-center name loggout-command]
      [:div.flex.justify-between.items-center.gap-2
       [:svg.w-4.h-4 {:class   status-color
                      :viewBox "0 0 10 10"}
        [:circle {:fill :currentColor
                  :cx   5 :cy 5 :r 4}]]
       [:div.flex-grow status-text]
       (hoc.buttons/regular {:disabled true
                             :on-click #(js/alert "!")} "Hjelp")]]]))

;endregion

(defn sist-oppdatert [uid values]
  (let [user-info @(rf/subscribe [::db/user-auth])
        #_#_tm (some->> (:timestamp values) (t/instant) (t/date-time))]
    [sc/col-space-8
     ;(when goog.DEBUG [l/ppre-x values])
     [sc/col-space-1
      (when-let [tm (some->> (:timestamp-lastvisit-userpage values) (t/instant) (t/date-time))]
        (booking.flextime/flex-datetime
          tm
          (fn [format content]
            [:div.inline-block
             [sc/text1-with
              "Sist sett "
              (if (= :date format)
                [sc/datetimelink (ta/datetime-format content)]
                [sc/datetimelink content])]])
          #_(fn [x] [sc/text2 [sc/row-sc-g2-w [sc/text1 "Sist oppdatert " tm]]])))
      (when-let [tm (some->> (:timestamp values) (t/instant) (t/date-time))]
        (booking.flextime/flex-datetime
          tm
          (fn [format content]
            [:div.inline-block
             [sc/text1-with
              "Sist oppdatert "
              (if (= :date format)
                [sc/datetimelink (ta/datetime-format content)]
                [sc/datetimelink content])]])))

      [sc/small1 (:navn values)]
      [sc/small1 (:epost values)]
      [sc/small1 uid]
      #_(when goog.DEBUG
          [:<>
           [l/pre
            (when-let [u (db/on-value-reaction {:path ["users" uid]})]
              [(select-keys @u [:admin :godkjent :medlem :booking-godkjent :waitinglist :navn])
               (booking.access/build-access-tuple @u)])]
           [l/pre @(rf/subscribe [:user-data uid])]
           [l/pre uid @(rf/subscribe [:lab/nokkelvakt uid])]])]]))

;duplicated

(def default-form-values
  {:navn                     ""
   :telefon                  ""
   :alias                    ""
   :epost                    ""
   :våttkort                 "0"
   :våttkortnr               ""
   :medlem-fra-år            ""
   :fødselsår                ""
   :årstall-førstehjelpskurs ""
   :årstall-livredningskurs  ""
   :instruktør               false
   :helgevakt                false
   :vikar                    false
   :kort-reisevei            false
   :request-booking          false
   :booking-expert           false
   :godkjent                 false
   :nøkkelnummer             ""
   :dato-godkjent-nøkkelvakt ""
   :dato-godkjent-booking    ""
   :timekrav                 "12"
   :godkjent-booking         false
   :dato-mottatt-nøkkel      ""
   :dato-innlevert-nøkkel    ""
   :admin                    false
   :utmeldt                  false
   :kald-periode             false
   :stjerne                  false})

(rf/reg-event-fx :save-this-form-test (fn [{db :db} [_ {:keys [values path reset]}]]
                                        (reset {:initial-values values :values values})
                                        {:db (-> db (fork/set-submitting path true))}))

(defn ^:deprecated aux [uid removal-date s]
  [sc/col
   [:div
    [:div.xs:px-4
     [:div.prose.max-w-md.mx-auto
      (st/prose-markdown-styles
        (md->html (str/replace (inline "./sletting.md") "@dato" (ta/date-format removal-date))))]]]

   [:div.flex.justify-between.h-12.px-4
    (removeaccount-command uid)
    [:div.flex.gap-4

     (hoc.buttons/cta
       {:type     :button
        :on-click #(readymade/message {:dialog-type :form
                                       :flags       #{:force :wide :timeout}
                                       :footer      nil
                                       :header      "Samler sammen"
                                       :content     [[:div.space-y-4
                                                      [:div "Vi samler sammen alle data, dette kan ta litt tid – når vi er ferdige vil du få en e-post med en lenke du kan trykke på for å laste alt ned."]
                                                      [:div "Du vil motta den på " [:span.font-semibold (some-> s deref :epost)]]]]})}

       "Få epost!")]]])

(defn user-form [r uid]
  (let [path ["users" uid]
        admin? (rf/subscribe [:lab/admin-access])
        form-state (r/atom {})
        s (if uid
            (db/on-value-reaction {:path path})
            (atom {}))]
    (fn [r uid]
      (let [initial-values (merge default-form-values
                                  (select-keys
                                    @s
                                    [;generelt
                                     :timestamp
                                     ;:timestamp-lastvisit-userpage
                                     :uid
                                     :navn :telefon :alias :epost :våttkort
                                     ;booking
                                     :våttkortnr :request-booking :booking-expert
                                     ;nøkkelvakt
                                     :medlem-fra-år :fødselsår
                                     :årstall-førstehjelpskurs :årstall-livredningskurs
                                     :instruktør :helgevakt :vikar :kort-reisevei
                                     ;status
                                     :godkjent-booking :dato-godkjent-booking
                                     :godkjent :dato-godkjent-nøkkelvakt
                                     :nøkkelnummer :dato-mottatt-nøkkel
                                     :dato-innlevert-nøkkel
                                     :admin :kald-periode :stjerne :utmeldt
                                     :timekrav])

                                  {:uid uid})]
        [:<>
         ;(when goog.DEBUG [l/ppre-x path initial-values])
         (if-let [removal-date (some-> s deref :removal-date t/date)]
           [aux uid removal-date s]
           [fork/form {:state               form-state
                       :initial-values      initial-values
                       :prevent-default?    true
                       :clean-on-unmount?   true
                       :keywordize-keys     true
                       :component-did-mount (fn [{:keys [set-values disable values] :as p}]
                                              (db/database-update {:path  ["beskjeder" uid]
                                                                   :value {:timestamp-lastvisit-userpage (str (t/now))}})
                                              (disable :booking-expert)
                                              (tap> {:user-form/component-did-mount (:navn initial-values)})
                                              (set-values initial-values))

                       :on-submit           (fn [{:keys [state values] :as x}]
                                              (let [author (:uid @(rf/subscribe [::db/user-auth]))]
                                                (user.forms/save-edit-changes
                                                  uid
                                                  author
                                                  (select-keys
                                                    (:initial-values @state)
                                                    (keys (map-difference values (:initial-values @state))))
                                                  (map-difference values (:initial-values @state))
                                                  (or (:endringsbeskrivelse values)
                                                      (apply str (interpose ", " (map name (keys (map-difference values (:initial-values @state)))))))))

                                              (db/database-update {:path  ["users" uid]
                                                                   :value (assoc values :timestamp (str (t/now)))})
                                              (rf/dispatch [:save-this-form-test x]))}
            (fn [{:keys [form-id values set-values state handle-submit reset dirty] :as props}]
              [:form.select-none
               {:id        form-id
                :on-submit #(handle-submit %)}
               [sc/col-space-8
                (into [:div]
                      (interpose [:div.py-6]
                                 (remove nil? [[user.forms/generalinformation-panel props]
                                               [user.forms/booking-panel props]
                                               (when true #_(= "eykt" @(rf/subscribe [:app/name]))
                                                 [user.forms/nokkelvakt-panel props])
                                               (when @admin?
                                                 [user.forms/status-panel props])
                                               (when @admin?
                                                 [user.forms/changelog-panel uid props])])))
                [:div
                 [sc/row-ec {:class [:py-4]}
                  (when-not (empty? dirty)
                    [hoc.buttons/regular {:type     :button
                                          :on-click #(set-values initial-values)
                                          :disabled (empty? dirty)} "Tilbakestill"])
                  [hoc.buttons/cta {:type     :submit
                                    :disabled (empty? dirty)} "Lagre"]]]
                [sist-oppdatert uid values]]])])]))))

(defn my-info [r]
  (let [uid (rf/subscribe [:lab/uid])]
    (fn [r]
      (if @uid
        [:div
         [user-form r @uid]]
        [:div "nope"]))))

(defn always-panel []
  [sc/row-sc-g2-w
   ;[widgets/auto-link :r.min-status]
   #_[schpaa.style.hoc.buttons/cta-pill-icon {:on-click #(rf/dispatch [:app/navigate-to [:r.min-status]])} ico/nokkelvakt "Mine vakter"]
   #_[schpaa.style.hoc.buttons/reg-pill-icon {} ico/plus "Skade på materiell"]])