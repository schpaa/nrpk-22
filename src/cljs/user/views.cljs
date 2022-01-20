(ns user.views
  (:require [schpaa.debug :as l]
            [re-statecharts.core :as rs]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth :refer [user-info]]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :refer [rounded-view]]
            [schpaa.modal :as modal]
            [eykt.fsm-helpers :refer [send]]
            [fork.re-frame :as fork]
            [user.database]
            [booking.views]
            [booking.views.picker]
            [tick.core :as t]))

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
  [:button.btn.btn-free
   {:on-click
    #(modal/form-action
       {:flags   #{:timeout}
        ;:footer  "Du kan ikke angre dette"
        :title   "Avlys booking"
        :form-fn (fn [] [:div
                         [:div.p-4 "Dette vil logge deg ut av kontoen på denne enheten."]
                         [modal/just-buttons
                          [["Avbryt" [:btn-free] (fn [] (send :e.hide))]
                           ["Logg ut" [:btn-cta] (fn []
                                                   (db/sign-out)
                                                   (send :e.hide))]]]])})}

   "Logg ut"])

(def removeaccount-command
  [:button.btn.btn-danger
   {:type :button
    :on-click
    #(modal/form-action
       {:flags   #{:tixmeout}
        :footer  [
                  [:div "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter. "]
                  [:div "Etter 14 dager slettes alle data."]]
        :title   "Slett konto"
        :form-fn (fn [] [:div
                         [:div.p-4 "Er du sikker på at du vil slette booking-kontoen din?"]
                         [modal/just-buttons
                          [["Avbryt" [:btn-free] (fn [] (send :e.hide))]
                           ["Ja, slett!" [:btn-danger] (fn []
                                                         (send :e.hide))]]]])})}
   "Slett konto"])


(defn logout-form [{:keys [user-auth name]}]
  (let [accepted? (true? (:booking-godkjent (user.database/lookup-userinfo (:uid user-auth))))
        use-booking? (true? (:bruke-booking (user.database/lookup-userinfo (:uid user-auth))))
        [status-color status-text] (cond
                                     accepted? [:text-alt "Godkjent booking"]
                                     use-booking? [:text-amber-500 "Godkjenning venter"]
                                     :else [:text-rose-500 "Ikke påmeldt"])]
    [:div.p-4.dark:bg-gray-700.bg-gray-50.shadow.rounded.space-y-4.text-base
     [:div.flex.justify-between.items-center
      name
      loggout-command]

     [:div.flex.justify-between.items-center.gap-2
      [:svg.w-4.h-4 {:class   status-color
                     :viewBox "0 0 10 10"}
       [:circle {:fill :currentColor
                 :cx   5 :cy 5 :r 4}]]
      [:div.flex-grow status-text]
      [:button.btn.btn-free {:disabled true
                             :on-click #(js/alert "!")} "Hjelp"]]]))


(defn my-bookings [{:keys [uid bookings]}]
  [:ul.space-y-px.shadow
   (for [e bookings]
     [booking.views/booking-list-item {} e])])

;endregion

(defn my-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  [:form.space-y-8
   {:id        form-id
    :on-submit handle-submit}
   [:div.space-y-4
    [:div.flex.gap-4.flex-wrap
     [fields/text (-> props fields/large-field (assoc :readonly? true)) "UID" :uid]]
    [:div.flex.gap-4.flex-wrap
     [fields/text (fields/large-field props) "Navn" :navn]
     [fields/text (fields/small-field props) "Alias" :alias]
     [fields/text (fields/small-field props) "Våttkort #" :våttkortnr]
     [fields/text (fields/normal-field props) "Telefon" :telefon]
     [fields/text (fields/large-field props) "Epost" :epost]]
    [:div.flex.gap-4.flex-wrap
     [fields/date (-> props fields/date-field (assoc :readonly? true)) "Req booking" :request-booking]]]

   (when-not readonly?
     [:div.flex.gap-4.justify-between
      removeaccount-command
      [:div.flex.gap-4
       [:button.btn.btn-free {:type     :button
                              :on-click #(send :e.cancel-useredit)} "Avbryt"]
       [:button.btn.btn-cta {:disabled (not (some? dirty))
                             :type     :submit} "Lagre"]]])])

(defn my-info [{:keys []}]
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
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
             [:h2.text-xs "Ikke registrert"])]]
         (rs/match-state (:user @*st-all)
           [:s.initial] [:div.space-y-8
                         (if loaded-data'
                           (my-form {:values loaded-data' :readonly? true})
                           [:div "Ingen data"])
                         [:div.flex.justify-between
                          [:button.btn.btn-danger
                           {:disabled true
                            :type     :button
                            :on-click #(send :e.edit)}
                           "Slett konto"]
                          [:button.btn.btn-free.btn-cta
                           {:type     :button
                            :on-click #(send :e.edit)}
                           "Rediger"]]]
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
            [l/ppre-x :state @*st-all]])]))))
