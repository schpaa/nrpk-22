(ns user.views
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [lambdaisland.ornament :as o]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [shadow.resource :refer [inline]]

            [times.api :as ta]

            [user.database]
            [nrpk.fsm-helpers :refer [send]]
            [eykt.content.rapport-side :refer [top-bottom-view map-difference]]

            [booking.views.picker]
            [booking.views]
            [booking.bookinglist]
            [booking.ico :as ico]

            [db.core :as db]
            [db.auth :refer [user-info]]

            [schpaa.modal.readymade :as readymade]
    ;[schpaa.state]
            [schpaa.markdown :refer [md->html]]
            [schpaa.components.fields :as fields]
            [schpaa.debug :as l]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.modal :as modal]
            [schpaa.style :as st]
            [schpaa.style.ornament :as sc]
            [schpaa.style.input :as sci :refer [input]]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.dialog]))

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

(defn sist-oppdatert [data]
  (let [user-info @(rf/subscribe [::db/user-auth])]
    [:div.flex.items-center.px-4
     [sc/dim
      [sc/col {:class [:space-y-2]}
       (if-let [tm (:timestamp @data)]
         (try [:div "Sist oppdatert " [:span (schpaa.time/y (t/date-time (t/instant tm)))]]
              (catch js/Error e (.-message e)))
         [sc/small1 "Sist oppdatert: aldri"])
       [sc/small1 (:email user-info)]
       [sc/small1 (:display-name user-info)]
       [sc/small1 (:uid user-info)]]]]))

;duplicated

(defn my-basics-form [{:keys [errors handle-change form-id handle-submit dirty readonly? values] :as props}]
  [:form
   {:id        form-id
    :on-submit handle-submit}
   [:div
    [sc/row-sc-g4-w
     [sc/row-sc-g4-w
      [input props :text [:w-56] "Fullt navn" :navn]
      [input props :text [:w-32] "Alias" :alias]]
     [sc/row-sc-g4-w
      [input props :text [:w-32] "Telefon" :telefon]
      [input props :text [:w-70] "E-post" :epost]]

     [sci/select props :våttkort [] "Våttkort" :våttkort "Velg"
      {"0" "Jeg har ikke våttkort"
       "1" "Introkurs 3 timer"
       "2" "Grunnkurs 16 timer"
       "3" "Teknikk, sikkerhet eller grunnkurs-2"
       "4" "Aktivitetsleder, trener-1 eller høyere"}]]]])

(defn my-booking-form [{:keys [form-id handle-submit] :as props}]
  [:form
   {:id        form-id
    :on-submit handle-submit}
   [:div.space-y-4
    [:div.flex.gap-4.flex-wrap
     [input props :text [:w-32] "Våttkort-nr" :våttkortnr]]
    [:div.flex.gap-4.flex-wrap
     [hoc.buttons/checkbox props [] "Jeg ønsker å bruke booking på sjøbasen" :request-booking]
     [hoc.buttons/checkbox props [] "Jeg ønsker tilgang til de utfordrende båtene" :booking-expert]]]])

(defn my-vakt-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  [:form.space-y-8
   {:id        form-id
    :on-submit handle-submit}
   [sc/col-space-8

    [sc/row-sc-g4-w
     [input props :text [:w-32] "Medlem fra år" :medlem-fra-år]
     [input props :text [:w-32] "Fødselsår" :fødselsår]
     [input props :text [:w-32] "Førstehjelp" :årstall-førstehjelpskurs]
     [input props :text [:w-32] "Livredning" :årstall-livredningskurs]]

    [sc/row-sc-g4-w
     [hoc.buttons/checkbox props [] "Jeg arbeider som instruktør for NRPK" :instruktør]
     [hoc.buttons/checkbox props [] "Foretrekker helgevakt" :helgevakt]
     [hoc.buttons/checkbox props [] "Kan stille som vikar" :vikar]
     [hoc.buttons/checkbox props [] "Kort reisevei" :kort-reisevei]]

    #_[:div.flex.gap-4.flex-wrap.justify-end
       [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Timekrav" :name :timekrav]
       [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Nøkkelnummer" :name :nøkkelnummer]
       [fields/date (-> props fields/date-field (assoc :readonly? true)) :label "Godkjent" :name :godkjent]]]])

(o/defstyled summary :summary
  :flex :items-center :pl-3 :h-12 :relative :select-none
  {:-border-top "1px solid var(--surface0)"})

(defn user-form [uid]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :form)
        path {:path ["users" uid]}
        form-state (r/atom {})
        s (if uid
            (db/on-value-reaction {:path ["users" uid]})
            (atom {}))]
    (fn [uid]
      (if-let [removal-date (some-> s deref :removal-date t/date)]
        [sc/col
         (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :surface)])
         [:div {:class (concat bg fg)}
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

             "Få epost!")]]]

        (into [:div]
              (interpose [:div.py-6]
                         [[togglepanel :user-form/grunnleggende "Grunnleggende"
                           (fn []
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
                              my-basics-form])]
                          [togglepanel :user-form/booking "Booking"
                           (fn []
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
                              my-booking-form])]
                          (when true #_(= "eykt" @(rf/subscribe [:app/name]))
                            [togglepanel :user-form/nøkkelvakt "Nøkkelvakt"
                             (fn []
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
                                my-vakt-form])])]))))))



(defn my-info []
  (let [user-auth @(rf/subscribe [::db/user-auth])
        uid (:uid user-auth)]
    (fn []
      [sc/col-space-8
       [user-form uid]
       [:div.p-4x
        [sc/row-ec
         [hoc.buttons/danger
          {:on-click #(schpaa.style.dialog/open-dialog-confirmaccountdeletion)}
          (hoc.buttons/icon-with-caption ico/trash "Slett konto...")]
         [hoc.buttons/regular {:disabled 1} "Lagre"]]]
       [sist-oppdatert (r/atom nil)]])))
