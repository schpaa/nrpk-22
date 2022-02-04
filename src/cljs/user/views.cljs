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
            [schpaa.modal.readymade :as readymade]))

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
                                             (user.database/write (:values data))
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

(def removeaccount-command
  (danger-button
    {:type :button
     :on-click
     #(readymade/ok-cancel
        {:flags           #{:wide}
         :button-captions (fn [id] (get {:ok     "Slett konto!"
                                         :cancel "Avbryt"} id))
         :ok              (fn [] (js/alert "!"))
         :footer          [[:div "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter."]
                           [:div "Etter 14 dager slettes alle data."]]
         :content         "Er du sikker på at du vil slette booking-kontoen din?"})}
    "Slett konto"))

(defn userstatus-form [{:keys [user-auth name]}]
  (let [{:keys [bg fg+]} (st/fbg' :form)
        accepted? (true? (:booking-godkjent (user.database/lookup-userinfo (:uid user-auth))))
        use-booking? (true? (:bruke-booking (user.database/lookup-userinfo (:uid user-auth))))
        [status-color status-text] (cond
                                     accepted? [:text-alt "Godkjent booking"]
                                     use-booking? [:text-amber-500 "Godkjenning venter"]
                                     :else [:text-rose-500 "Ikke påmeldt"])]
    [:div.p-4.shadow.rounded.space-y-4
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

(defn my-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
    [:form.space-y-8
     {:id        form-id
      :on-submit handle-submit}
     [:div.space-y-4
      [:div.flex.gap-4.flex-wrap
       [fields/text (-> props fields/large-field (assoc :readonly? true)) :label "UID" :name :uid]]
      [:div.flex.gap-4.flex-wrap
       [fields/text (fields/large-field props) :label "Navn" :name :navn]
       [fields/text (fields/small-field props) :label "Alias" :name :alias]
       [fields/text (fields/small-field props) :label "Våttkort #" :name :våttkortnr]
       [fields/text (fields/normal-field props) :label "Telefon" :name :telefon]
       [fields/text (fields/large-field props) :label "Epost" :name :epost]]
      [:div.flex.gap-4.flex-wrap
       [fields/date (-> props fields/date-field (assoc :readonly? true)) :label "Req booking" :name :request-booking]]]

     (when-not readonly?
       [:div.flex.gap-4.justify-between
        removeaccount-command
        [:div.flex.gap-4
         (regular-button {:type     :button
                          :on-click #(send :e.cancel-useredit)} "Avbryt")
         (regular-button {:type     :submit
                          :disabled (not (some? dirty))} "Lagre")
         #_[:button.btn.btn-cta {:disabled (not (some? dirty))
                                 :type     :submit} "Lagre"]]])]))

(defn my-info [{:keys []}]
  (let [{:keys [bg fg- fg fg+ hd p p- he]} (st/fbg' :form)
        *st-all (rf/subscribe [::rs/state :main-fsm])
        user-auth (rf/subscribe [::db/user-auth])
        uid (:uid @user-auth)
        s (db/on-value-reaction {:path ["users" uid]})]
    (fn []
      (let [path {:path ["users" uid]}
            loaded-data (db/on-value-reaction path)
            loaded-data' (-> (select-keys @loaded-data [:uid :last-update :navn :request-booking :alias :våttkortnr :telefon :epost :input])
                             (update :request-booking
                                     #(try
                                        (str (t/date (times.api/str->datetime %)))
                                        (catch js/Error _ %))))]
        [:div.p-4.space-y-4
         [:div.flex.items-center.justify-between
          [:div.flex.flex-col
           (if-let [tm (:timestamp @s)]
             (try [:div.text-sm "Sist oppdatert " [:span (schpaa.time/y (t/date-time (t/instant tm)))]]
                  (catch js/Error e (.-message e)))
             [:h2.text-xs {:class fg} "Ikke registrert"])]]

         (rs/match-state (:user @*st-all)
           [:s.initial] [:div.space-y-8
                         (if loaded-data'
                           (my-form {:values loaded-data' :readonly? true})
                           [:div "Ingen data"])
                         [:div.flex.justify-between
                          (danger-button {:disabled true} "Slett konto")
                          (regular-button {:type     :button
                                           :on-click #(send :e.edit)} "Rediger")]]

           [:s.editing] [fork/form {:initial-values    loaded-data'
                                    :prevent-default?  true
                                    :clean-on-unmount? true
                                    :keywordize-keys   true
                                    :on-submit         #(send :e.store (assoc-in % [:values :uid] uid))}
                         my-form]
           [:s.store] [rounded-view
                       [:div "Lagrer, et øyeblikk"]]

           [:div
            [:h2 "unhandled state"]
            [l/ppre-x @*st-all]])]))))
