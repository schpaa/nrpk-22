(ns user.views
  (:require [schpaa.debug :as l]
            [re-statecharts.core :as rs]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth :refer [user-info]]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :refer [rounded-view]]
            [eykt.state :as state]
            [fork.re-frame :as fork]
            user.database
            booking.views
            [tick.core :as t]
            [schpaa.icon :as icon]))

;region prelim

(defn all-registered [{:keys [some-data*]}]
  (when @some-data*
    [:<>
     (for [[a b] (keep (juxt :input :shortname) (vals @some-data*))]
       [rounded-view {:dark true} [:div.flex.justify-between
                                   [:div a]
                                   [:div {:class (if-not b "text-black/25")} (or b "ingen")]]])]))

(defn logout-form [{:keys [user-auth name]}]
  (let [accepted? (true? (:booking-godkjent (user.database/lookup-userinfo (:uid user-auth))))
        use-booking? (true? (:bruke-booking (user.database/lookup-userinfo (:uid user-auth))))
        [status-color status-text] (cond
                                     accepted? [:text-alt "Godkjent booking"]
                                     use-booking? [:text-amber-500 "Godkjenning venter"]
                                     :else [:text-rose-500 "Ikke påmeldt"])]
    [rounded-view {:float 1}
     [:div.flex.justify-between.items-center
      [:h2 name]
      [:button.btn.btn-danger {:on-click #(db/sign-out)} "Logg ut"]]
     [:div.flex.justify-between.items-center.gap-4
      [:svg.w-4.h-4 {:class   status-color
                     :viewBox "0 0 10 10"}
       [:circle {:fill :currentColor
                 :cx   5 :cy 5 :r 5}]]
      [:h2.flex-grow status-text]
      [:button.btn.btn-free {:on-click #(js/alert "!")} "Ping"]]]))


(defn my-bookings [{:keys [uid bookings]}]
  [:ul.space-y-px.shadow
   (for [e bookings]
     [booking.views/booking-list-item {} e])])

;endregion

(defn my-form [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  [:form.space-y-4
   {:id        form-id
    :on-submit handle-submit}
   [:div.flex.gap-4.flex-wrap
    [fields/text (-> props fields/large-field (assoc :readonly? true)) "UID" :uid]]
   [:div.flex.gap-4.flex-wrap
    [fields/text (fields/large-field props) "Navn" :navn]
    [fields/text (fields/small-field props) "Alias" :alias]
    [fields/text (fields/small-field props) "Våttkort #" :våttkortnr]
    [fields/text (fields/normal-field props) "Telefon" :telefon]
    [fields/text (fields/large-field props) "Epost" :epost]]
    ;[fields/text (fields/small-field props) "Data" :input]]
   [:div.flex.gap-4.flex-wrap

    [fields/date (-> props fields/date-field (assoc :readonly? true)) "Req booking" :request-booking]]

   (when-not readonly?
     [:div.flex.gap-4.justify-end
      [:button.btn.btn-free {:type     :button
                             :on-click #(state/send :e.cancel-useredit)} "Avbryt"]
      [:button.btn.btn-cta {:disabled (not (some? dirty))
                            :type     :submit} "Lagre"]])])

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
        [rounded-view {:flat 1}
         [:div.flex.items-center.justify-between
          [:div.flex.flex-col
           (if-let [tm (:timestamp @s)]
             (try [:h2.text-xs "Oppdatert " [:span (schpaa.time/y (t/date-time (t/instant tm)))]]
                  (catch js/Error e (.-message e)))
             [:h2.text-xs "Ikke registrert"])]]
         (rs/match-state (:user @*st-all)
           [:s.initial] [:<>
                         (if loaded-data'
                           (my-form {:values loaded-data' :readonly? true})
                           [:div "Ingen data"])
                         [:div.flex.justify-end
                          [:button.btn.btn-free.btn-cta
                           {:type     :button
                            :on-click #(state/send :e.edit)}
                           "Rediger"]]]
           [:s.editing] [fork/form {:initial-values    loaded-data'
                                    :prevent-default?  true
                                    :clean-on-unmount? true
                                    :keywordize-keys   true
                                    :on-submit         #(state/send :e.store (assoc-in % [:values :uid] uid))}
                         my-form]
           [:s.store] [rounded-view
                       [:div "Lagrer, et øyeblikk"]]

           [:div
            [:h2 "unhandled state"]
            [l/ppre-x :state @*st-all]])]))))
