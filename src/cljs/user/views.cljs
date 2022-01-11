(ns user.views
  (:require [schpaa.debug :as l]
            [re-statecharts.core :as rs]
            [re-frame.core :as rf]
            [db.core :as db]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :refer [rounded-view]]
            [eykt.state :as state]
            [fork.re-frame :as fork]
            booking.views
            [tick.core :as t]))

;region prelim

(defn all-registered [{:keys [some-data*]}]
  (when @some-data*
    [:<>
     (for [[a b] (keep (juxt :input :shortname) (vals @some-data*))]
       [rounded-view {:dark true} [:div.flex.justify-between
                                   [:div a]
                                   [:div {:class (if-not b "text-black/25")} (or b "ingen")]]])]))

(defn logout-form [{:keys [name]}]
  [rounded-view {}
   [:div.flex.justify-between.items-center
    [:h2 name]
    [:button.btn.btn-danger {:on-click #(db/sign-out)} "Logg ut"]]])


(defn my-bookings [{:keys [uid bookings]}]
  (rounded-view
    {:dark 1}
    [:ul.space-y-px
     (for [e bookings]
       [booking.views/booking-list-item e])]))

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
    [fields/text (fields/large-field props) "Epost" :epost]
    [fields/text (fields/small-field props) "Data" :input]]
   [:div.flex.gap-4.flex-wrap

    [fields/date (-> props fields/date-field (assoc :readonly? true)) "Req booking" :request-booking]]

   (when-not readonly?
     [:div.flex.gap-4.justify-end
      [:button.btn.btn-free {:type     :button
                             :on-click #(state/send :e.cancel-useredit)} "Avbryt"]
      [:button.btn.btn-free.btn-cta.text-black {:disabled (not (some? dirty))
                                                :type     :submit} "Lagre"]])])

(defn my-info [{:keys []}]
  (let [*st-all (rf/subscribe [::rs/state :main-fsm])
        user-auth (rf/subscribe [::db/user-auth])
        uid (:uid @user-auth)
        s (db/on-snapshot-doc-reaction {:path ["users" uid]})]
    (fn []
      (let [path {:path ["users" uid]}
            loaded-data (db/on-value-reaction path)
            loaded-data' (-> (select-keys @loaded-data [:uid :last-update :navn :request-booking :alias :våttkortnr :telefon :epost :input])
                             (update :request-booking #(try
                                                         (str (t/date (times.api/str->datetime %)))
                                                         (catch js/Error _
                                                           %))))]
        [rounded-view {}
         [:div.flex.items-center.justify-between
          [:div.flex.flex-col
           [:h2 "Mine opplysninger"]
           [:h2.text-xs (if-let [tm (get @s "timestamp")]
                          (schpaa.time/x' tm)
                          "Ikke registrert")]]]
         ;[l/ppre-x loaded-data']
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
