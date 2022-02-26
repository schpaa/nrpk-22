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
            [times.api :as ta]
            [schpaa.markdown :refer [md->html]]
            [shadow.resource :refer [inline]]
            [clojure.string :as str]))

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
         :ok      (fn [] (do (db/sign-out)
                             (rf/dispatch [:app/navigate-to [:r.forsiden]])
                             #_(schpaa.modal.readymade/message {:content "Du har logget ut"})))})}
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
       (regular-button {:disabled true
                        :on-click #(js/alert "!")} "Hjelp")]]]))

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
       [fields/select (fields/large-field props) :label "Våttkort" :name :våttkort :items {"0" "Jeg har ikke våttkort" "1" "Introkurs 3 timer" "2" "Grunnkurs 16 timer" "3" "Teknikk, sikkerhet eller grunnkurs-2" "4" "Aktivitetsleder, trener-1 eller høyere"}]]
      [:div.flex.gap-4.flex-wrap.justify-end
       [fields/text (-> props fields/large-field (assoc :readonly? true)) :label "Bruker-id" :name :uid]]]]))

(defn my-booking-form [{:keys [form-id handle-submit handle-change values set-values dirty readonly? values] :as props}]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
    [:form.space-y-8
     {:class     bg
      :id        form-id
      :on-submit handle-submit}
     ;[l/ppre-x values]
     [:div.space-y-4.p-4

      [:div.flex.gap-4.flex-wrap
       [fields/checkbox (-> props
                            fields/large-field
                            (assoc
                              :values #(some? (values :request-booking))
                              :handle-change #(let [v (-> % .-target .-checked)]
                                                (set-values {:request-booking (if v (str (t/now)) nil)})

                                                #_(handle-change %)))) :label "Jeg ønsker å bruke booking på sjøbasen" :name :request-booking]
       [fields/checkbox (fields/large-field props) :label "Jeg er ekspert og ønsker tilgang til de utfordrende båtene" :name :booking-expert]]

      [:div.flex.gap-4.flex-wrap
       [fields/text (fields/small-field props) :label "Våttkort-nr" :name :våttkortnr]]

      [:div.flex.gap-4.flex-wrap.justify-end
       [fields/date (-> props fields/date-field (assoc :readonly? true
                                                       :values #(if-let [v (values :request-booking)]
                                                                  (try (t/date (t/instant v)) (catch js/Error _ nil))
                                                                  "")))
        :label "Req booking" :name :request-booking]]]]))

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
       [fields/checkbox (fields/large-field props) :label "Jeg arbeider som instruktør for NRPK" :name :instruktør]
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
         (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)]
           [:div {:class (concat bg fg)}
            [:div.xs:px-4.px-2.pb-4
             [:div.prose.max-w-md.mx-auto
              (st/prose-markdown-styles
                (md->html (str/replace (inline "./sletting.md") "@dato" (ta/date-format removal-date))))]]])

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

             "Få epost!")]]]

        [top-bottom-view
         "calc(100vh - 17rem)"
         [:div.space-y-px
          [details :user/basic-section-open "Grunnleggende"
           [fork/form {:state               form-state
                       :prevent-default?    true
                       :clean-on-unmount?   false
                       :keywordize-keys     true
                       :component-did-mount (fn [{:keys [set-values]}]
                                              (let [data (conj (if @s (walk/keywordize-keys @s) {})
                                                               {;:uid             uid
                                                                #_#_:request-booking (str (t/date))})]
                                                (set-values data)))
                       :on-submit           #(send :e.store (assoc-in % [:values :uid] uid))}
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
          (when (= "eykt" @(rf/subscribe [:app/name]))
            [details
             :user/vakt-section-open
             "Nøkkelvakt"
             [fork/form {:state               form-state
                         :prevent-default?    true
                         :clean-on-unmount?   true
                         :keywordize-keys     true
                         :component-did-mount (fn [{:keys [set-values]}]
                                                (let [data (conj (if @s (walk/keywordize-keys @s) {})
                                                                 {:uid             uid
                                                                  :request-booking (str (t/date))})]
                                                  (set-values data)))
                         :on-submit           #(send :e.store (assoc-in % [:values :uid] uid))}
              my-vakt-form]])
          [sist-oppdatert s]]
         (let [fields [:uid :kort-reisevei :medlem-fra-år :navn :alias :telefon :epost :våttkort :våttkortnr :kort-reisevei :fødselsår :nøkkelnummer
                       :årstall-førstehjelpskurs :årstall-livredningskurs :helgevakt :vikar :booking-expert :request-booking :instruktør]
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
  (let [user-auth (rf/subscribe [::db/user-auth])
        uid (:uid @user-auth)]
    (fn []
      (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
        [:div.space-y-4
         {:class bg}
         (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :void)]
           [user-form uid])]))))


