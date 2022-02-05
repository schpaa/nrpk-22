(ns user.views
  (:require [schpaa.debug :as l]
            [re-statecharts.core :as rs]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth :refer [user-info]]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.modal :as modal]
            [nrpk.fsm-helpers :refer [send]]
            [fork.re-frame :as fork]
            [user.database]
            [booking.views]
            [booking.views.picker]
            [booking.bookinglist]
            [tick.core :as t]
            [schpaa.style :as st]
            [schpaa.button :refer [danger-button regular-button cta-button]]
            [schpaa.button :as bu]
            [schpaa.modal.readymade :as readymade]
            [eykt.content.rapport-side :refer [top-bottom-view map-difference]]
            [schpaa.state]
            [clojure.walk :as walk]
            [reagent.core :as r]
            [times.api :as ta]))

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
  (regular-button
    {:on-click
     #(readymade/ok-cancel
        {:type    :form
         :flags   #{:timeout :weak-dim}
         :content "Dette vil logge deg ut av kontoen på denne enheten."
         :ok      (fn [] (db/sign-out))})}
    "Logg ut"))

(defn removeaccount-command [uid]
  (let [removal-date (some-> (db/on-value-reaction {:path ["users" uid]}) deref :removal-date)]
    (if (and (some? removal-date)
             (t/< (t/date) (t/date removal-date)))
      (regular-button {:on-click #(readymade/ok-cancel
                                    {:flags           #{:wide}
                                     :button-captions (fn [id] (get {:ok     "Gjennopprett nå!"
                                                                     :cancel "Nei, forresten"} id))
                                     :ok              (fn [] (user.database/mark-account-for-restore uid))
                                     #_#_:footer [[:div "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter."]
                                                  [:div "Etter 14 dager slettes alle data."]]
                                     :content         "Er du sikker på at du vil gjennopprette kontoen din?"})}
                      "Gjennopprett konto" #_(times.api/format "Gjennopprett (innen %s)" (ta/short-date-format removal-date)))
      (danger-button
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

(defn userstatus-form [{:keys [user-auth name]}]
  (let [{:keys [bg fg+]} (st/fbg' :form)
        accepted? (true? (:booking-godkjent (user.database/lookup-userinfo (:uid user-auth))))
        use-booking? (true? (:bruke-booking (user.database/lookup-userinfo (:uid user-auth))))
        [status-color status-text] (cond
                                     accepted? [:text-alt "Godkjent booking"]
                                     use-booking? [:text-amber-500 "Godkjenning venter"]
                                     :else [:text-rose-500 "Ikke påmeldt"])]
    [:div.p-4.shadow.rounded.space-y-2
     {:class (concat bg fg+)}
     [:div.flex.justify-between.items-center
      name
      loggout-command]

     [:div.flex.justify-between.items-center.gap-2
      [:svg.w-4.h-4 {:class   status-color
                     :viewBox "0 0 10 10"}
       [:circle {:fill :currentColor
                 :cx   5 :cy 5 :r 4}]]
      [:div.flex-grow status-text]
      (regular-button {:disabled true
                       :on-click #(js/alert "!")} "Hjelp")]]))

(defn my-bookings [{:keys [uid bookings]}]
  [:ul.space-y-px.shadow
   (for [e bookings]
     [booking.bookinglist/booking-list-item {} e])])

;endregion

(defn sist-oppdatert [data]
  [:div.h-16.flex.items-center.px-4
   (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :void)]
     [:div.flex.flex-col
      {:class (concat fg p-)}
      (if-let [tm (:timestamp @data)]
        (try [:div "Sist oppdatert " [:span (schpaa.time/y (t/date-time (t/instant tm)))]]
             (catch js/Error e (.-message e)))
        [:div "Ikke registrert"])])])

(defn my-basics-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
    [:form.space-y-8
     {:class     bg
      :id        form-id
      :on-submit handle-submit}
     [:div.space-y-4.p-4
      [:div.flex.gap-4.flex-wrap
       [fields/text (fields/large-field props) :label "Fullt navn" :name :navn]
       [fields/text (fields/small-field props) :label "Alias" :name :alias]
       [:div.flex.gap-4
        [fields/text (fields/small-field props) :label "Telefon" :name :telefon]
        [fields/text (fields/large-field props) :label "Epost" :name :epost]]]
      [:div.flex.gap-4
       [fields/select (fields/normal-field props) :label "Våttkort" :name :våttkort :items {"0" "Nei" "1" "Ja" "2" "Ja 2" "3" "Ja 3"}]]
      [:div.flex.gap-4.flex-wrap.justify-end
       [fields/text (-> props fields/large-field (assoc :readonly? true)) :label "Bruker-id" :name :uid]]]]))

(defn my-booking-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
    [:form.space-y-8
     {:class     bg
      :id        form-id
      :on-submit handle-submit}
     [:div.space-y-4.p-4
      [:div.flex.gap-4.flex-wrap
       [fields/text (fields/small-field props) :label "Våttkort #" :name :våttkortnr]]
      [:div.flex.gap-4.flex-wrap.justify-end
       [fields/date (-> props fields/date-field (assoc :readonly? true)) :label "Req booking" :name :request-booking]]]]))

(defn my-vakt-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
    [:form.space-y-8
     {:class     bg
      :id        form-id
      :on-submit handle-submit}
     [:div.space-y-8.p-4

      [:div.flex.gap-4.flex-wrap
       [fields/text (fields/small-field props) :label "Medlem fra år" :name :medlem-fra-år]
       [fields/text (fields/small-field props) :label "Fødselsår" :name :fødselsår]
       [fields/text (fields/small-field props) :label "Førstehjelp" :name :årstall-førstehjelpskurs]
       [fields/text (fields/normal-field props) :label "Livredning" :name :årstall-livredningskurs]]

      [:div.flex.gap-4.flex-wrap
       [fields/checkbox (fields/large-field props) :label "Foretrekker helgevakt" :name :helgevakt]
       [fields/checkbox (fields/large-field props) :label "Kan stille som vikar" :name :vikar]
       [fields/checkbox (fields/large-field props) :label "Kort reisevei" :name :kort-reisevei]]

      [:div.flex.gap-4.flex-wrap.justify-end
       [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Timekrav" :name :timekrav]
       [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Nøkkelnummer" :name :nøkkelnummer]
       [fields/date (-> props fields/date-field (assoc :readonly? true)) :label "Godkjent" :name :godkjent]]]]))

(defn details [tag summary content]
  (let [st (schpaa.state/listen tag)
        {:keys [bg bg- fg- fg fg+ hd p p- he]} (st/fbg' :form)]
    [:details
     {:open @st}
     [:summary.h-12.px-4.pt-3.relative.select-none
      {:class    (concat bg- fg+)
       :on-click #(schpaa.state/change tag (not @st))}
      [:div.flex.h-12.items-center.px-4.absolute.-top-0.left-4 summary]]
     content]))

(defn user-form [uid]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :form)
        path {:path ["users" uid]}
        form-state (r/atom {})
        s (if uid
            (db/on-value-reaction {:path ["users" uid]})
            (atom {}))]
    (fn [uid]
      (if-let [removal-date (some-> s deref :removal-date t/date)]
        [top-bottom-view
         "calc(100vh - 17rem)"
         [:div.p-4.space-y-4 {:class (concat bg fg+)}
          [:div (ta/format "Du har fortalt oss at du ønsker å slette denne kontoen. Dette skjer etter %s. Du kan også gjennopprette kontoen hvis du angrer." (ta/date-format removal-date))]

          [:div "Vi kan samle sammen alle data vi har lagret om deg og sende deg en e-post med innholdet."]]
         [:div.flex.justify-between.h-12.px-4
          (removeaccount-command uid)
          [:div.flex.gap-4

           (bu/cta-button
             {:type     :button
              :on-click #(readymade/message {:dialog-type :form
                                             :flags       #{:force :wide :timeout}
                                             :footer      nil
                                             :header      "Samler sammen"
                                             :content     [[:div.space-y-4
                                                            [:div "Vi samler sammen alle data, dette kan ta litt tid – når vi er ferdige vil du få en e-post med en lenke du kan trykke på for å laste alt ned."]
                                                            [:div "Du vil motta den på " [:span.font-semibold (some-> s deref :epost)]]]]})}

             "Motta mine data!")]]]

        [top-bottom-view
         "calc(100vh - 17rem)"
         [:div.space-y-px
          [details
           :user/basic-section-open
           "Grunnleggende"
           [fork/form {:state                form-state
                       :prevent-default?     true
                       :clean-on-unmount?    true
                       :keywordize-keys      true
                       :xcomponent-did-mount (fn [{:keys [set-values]}]
                                               (let [data (conj (if @s (walk/keywordize-keys @s) {})
                                                                {:uid             uid
                                                                 :request-booking (str (t/date))})]
                                                 (set-values data)))
                       :on-submit            #(send :e.store (assoc-in % [:values :uid] uid))}
            my-basics-form]]
          [details
           :user/booking-section-open
           "Booking"
           [fork/form {:state                form-state
                       :prevent-default?     true
                       :clean-on-unmount?    false
                       :keywordize-keys      true
                       :xcomponent-did-mount (fn [{:keys [set-values]}]

                                               (let [data (conj (if @s (walk/keywordize-keys @s) {})
                                                                {:uid             uid
                                                                 :request-booking (str (t/date))})]
                                                 (set-values data)))
                       :on-submit            #(send :e.store (assoc-in % [:values :uid] uid))}
            my-booking-form]]
          [details
           :user/vakt-section-open
           "Nøkkelvakt"
           [fork/form {:state               form-state
                       :prevent-default?    true
                       :clean-on-unmount?   false
                       :keywordize-keys     true
                       :component-did-mount (fn [{:keys [set-values]}]

                                              (let [data (conj (if @s (walk/keywordize-keys @s) {})
                                                               {:uid             uid
                                                                :request-booking (str (t/date))})]
                                                (set-values data)))
                       :on-submit           #(send :e.store (assoc-in % [:values :uid] uid))}
            my-vakt-form]]
          [sist-oppdatert s]]
         (let [fields [:kort-reisevei :medlem-fra-år :navn :alias :telefon :epost :våttkort :våttkortnr :kort-reisevei :fødselsår
                       :årstall-førstehjelpskurs :årstall-livredningskurs :helgevakt :vikar]
               diff (map-difference (select-keys (:values @form-state) fields)
                                    (select-keys (walk/keywordize-keys @s) fields))]
           [:<>
            ;[l/ppre-x diff]
            #_[l/ppre-x diff (select-keys (walk/keywordize-keys @s) fields)
               (select-keys (:values @form-state) fields)]

            [:div.flex.justify-between.h-12.px-4
             (removeaccount-command uid)
             [:div.flex.gap-4
              (when-not (empty? diff)
                (bu/regular-button {:type     :button
                                    :on-click #(swap! form-state assoc :values (walk/keywordize-keys @s))} "Tilbakestill"))
              (bu/regular-button {:type     :button
                                  :on-click #(do
                                               (user.database/write diff (assoc diff :uid uid))
                                               (readymade/popup {:dialog-type :message
                                                                 :content     "Lagret"}))
                                  :disabled (empty? diff)}
                                 "Lagre")]]])]))))

(defn my-info [{:keys []}]
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        user-auth (rf/subscribe [::db/user-auth])
        uid (:uid @user-auth)
        s (if uid
            (db/on-value-reaction {:path ["users" uid]})
            (atom {}))]
    (fn []
      (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :void)
            path {:path ["users" uid]}
            loaded-data (db/on-value-reaction path)
            loaded-data' (-> (select-keys @loaded-data [:uid :last-update :navn :request-booking :alias :våttkortnr :telefon :epost :input])
                             (update :request-booking
                                     #(try
                                        (str (t/date (times.api/str->datetime %)))
                                        (catch js/Error _ %))))]
        [user-form uid]
        #_(top-bottom-view
            "calc(100vh - 17rem)"

            [:div
             (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :form)]
               [:div.p-4.space-y-4
                {:class bg}
                (rs/match-state (:user @*st-all)
                  [:s.initial] [:div.space-y-8
                                (if loaded-data'
                                  (my-form {:values loaded-data' :readonly? true})
                                  [:div "Ingen data"])
                                #_[:div.flex.justify-between
                                   (danger-button {:disabled true} "Slett konto")
                                   (regular-button {:type     :button
                                                    :on-click #(send :e.edit)} "Rediger")]]

                  [:s.editing]
                  [user-form uid my-form]

                  [:s.store] [rounded-view
                              [:div "Lagrer, et øyeblikk"]]

                  [:div
                   [:h2 "unhandled state"]
                   [l/ppre-x @*st-all]])])
             [:div.h-16.flex.items-center.px-4
              (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :void)]
                [:div.flex.flex-col
                 {:class (concat fg p-)}
                 (if-let [tm (:timestamp @s)]
                   (try [:div "Sist oppdatert " [:span (schpaa.time/y (t/date-time (t/instant tm)))]]
                        (catch js/Error e (.-message e)))
                   [:div "Ikke registrert"])])]]
            (let [dirty false]
              (rs/match-state (:user @*st-all)

                [:s.initial]
                [:div
                 #_(if loaded-data'
                     (my-form {:values loaded-data' :readonly? true})
                     [:div "Ingen data"])
                 [:div.flex.justify-between.px-4.h-12
                  (danger-button {:disabled true} "Slett konto")
                  (regular-button {:type     :button
                                   :on-click #(send :e.edit)} "Rediger")]]

                [:s.editing]
                (when-not false                             ;readonly?
                  [:div.flex.justify-between.h-12.px-4
                   removeaccount-command
                   [:div.flex.gap-4
                    (bu/regular-button {:type     :button
                                        :on-click #(send :e.cancel-useredit)} "Avbryt")
                    (bu/regular-button {:type     :submit
                                        :disabled (not (some? dirty))} "Lagre")]]))))))))

