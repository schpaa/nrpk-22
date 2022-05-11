(ns booking.lab
  (:require [reagent.core :as r]
            [kee-frame.core]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth]
            [schpaa.style.ornament :as sc]
            [schpaa.style.booking]
            [booking.data]
            [schpaa.style.menu]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [schpaa.style.dialog :refer [open-dialog-confirmbooking]]
            [booking.views]
            [clojure.set :as set]
            [booking.qrcode]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

;region temporary, perhaps for later

(rf/reg-event-db :lab/show-popover (fn [db]
                                     (tap> (:lab/show-popover db))
                                     (update db :lab/show-popover (fnil not false))))
(rf/reg-sub :lab/show-popover (fn [db] (get db :lab/show-popover false)))

;endregion

(defn time-input-validation [e]
  (into {} (remove (comp nil? val)
                   {:start-date (cond-> nil
                                  (empty? (:start-date e)) ((fnil conj []) "mangler")
                                  (and (not (empty? (:start-date e)))
                                       (some #{(tick.alpha.interval/relation
                                                 (t/date (:start-date e)) (t/date))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))

                    :start-time (cond-> nil
                                  (empty? (:start-time e)) ((fnil conj []) "mangler")
                                  (and (not (empty? (:start-date e)))
                                       (not (empty? (:start-time e)))
                                       (some #{(tick.alpha.interval/relation
                                                 (t/at (t/date (:start-date e)) (t/time (:start-time e)))
                                                 (t/date-time (t/now)))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))
                    :end-time   (cond-> nil
                                  (empty? (:end-time e)) ((fnil conj []) "mangler")
                                  (and (not (empty? (:start-date e)))
                                       (not (empty? (:end-time e)))
                                       (some #{(tick.alpha.interval/relation
                                                 (t/at (t/date (:end-date e)) (t/time (:end-time e)))
                                                 (t/at (t/date (:start-date e)) (t/time (:start-time e))))} [:precedes :meets])) ((fnil conj []) "er før 'fra kl'"))})))

(defn time-input-form [time-state]
  (r/with-let [lightning-visible (r/atom nil)]
    (let [toggle-lightning #(swap! lightning-visible (fnil not false))]
      [fork/form {:initial-values    {:start-date  (str (t/new-date))
                                      :start-time  (str (t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                      ;todo adjust for 22-23
                                      :end-time    (str (t/truncate (t/>> (t/time) (t/new-duration 3 :hours)) :hours))
                                      :end-date    (str (t/new-date))
                                      :id          0
                                      :description ""}
                  :form-id           "sample-form"

                  :prevent-default?  true
                  :state             time-state
                  :clean-on-unmount? true
                  :keywordize-keys   true
                  :on-submit         (fn [e] (tap> {:on-submit e}))
                  :validation        time-input-validation}

       (fn [{:keys [errors form-id handle-submit handle-change values set-values] :as props}]
         [:form.space-y-1.w-full
          {:id        form-id
           :on-submit handle-submit}
          [booking.views/time-input props false]])])))

(def type-data
  [{:type        5000
    :category    "Grønnlandskayakk"
    :material    "Epoxy"
    :stability   4
    :description "Ufattelig lang beskrivelse som går over flere linjer og som sier en hel drøss om hva dette er godt for og at du burde prøve den!"
    :brand       "Rebel Naja"
    :weight      "33 kg"
    :width       "50 cm"
    :length      "490 cm"}
   {:type      3100
    :category  "Havkayakk"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}
   {:type      3200
    :category  "Tomahawk"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}
   {:type      4100
    :category  "Testthing"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}])

(def card-data-v2
  [{:id 100 :loc "C3" :number "310" :type 5000}
   {:id 102 :loc "C4" :number "313" :type 5000}
   {:id 210 :loc "A2" :number "439" :type 3100}
   {:id 211 :loc "A2" :number "439" :type 3200}
   {:id 212 :loc "A2" :number "212" :type 3200}
   {:id 412 :loc "A2" :number "412" :type 4100}
   {:id 413 :loc "A2" :number "413" :type 4100}
   {:id 414 :loc "A2" :number "414" :type 4100}
   {:id 415 :loc "A2" :number "415" :type 4100}])

(def card-data
  [{:content  {:category    "Grønnlandskayakk"
               :number      "600"
               :location    "E5"
               :material    "Epoxy"
               :stability   4
               :description "Ufattelig lang beskrivelse som går over flere linjer og som sier en hel drøss om hva dette er godt for og at du burde prøve den!"
               :brand       "Rebel Naja"
               :weight      "33 kg"
               :width       "50 cm"
               :length      "490 cm"}

    :on-click #()
    :expanded true
    :selected false}
   {:content  {:category  "Havkayakk"
               :number    "310"
               :location  "C3"
               :material  "Plast"
               :stability 1
               ;:description ""
               :brand     "P3 Baffin Boreal"
               ;:weight      "23 kg"
               :width     "50 cm"
               :length    "490 cm"}

    :on-click #()
    :expanded true
    :selected false}
   {:content  {:category    "Havkayakk"
               :number      "310"
               :location    "C3"
               :material    "Epoxy"
               :stability   4
               :description "Passer best for de som liker å padle på vann og land."
               :brand       "P3 Baffin Boreal"
               :weight      "23 kg"
               :width       "50 cm"
               :length      "490 cm"}

    :on-click #()
    :expanded true
    :selected false}])

(rf/reg-event-fx :lab/qr-code-for-current-page
                 (fn [_ _]
                   (let [link @(rf/subscribe [:kee-frame/route])]
                     (booking.qrcode/show link))))

(defn render [r]
  (r/with-let [settings (r/atom {:setting-1 false
                                 :setting-2 1
                                 :selection #{2 5}})
               time-state (r/atom nil)
               state (r/atom {:expanded #{}
                              :selected #{100 415}})]
    [:div.xp-2.space-y-4.relative

     [:div                                                  ;.sticky.top-0.z-50
      [time-input-form time-state]]

     [:<>
      [sc/grid-wide {:class [:gap-2 :place-content-center]}
       (doall (for [[type data] (group-by :type card-data-v2)]
                [sc/surface-e {:class []}
                 [:div.space-y-1
                  [schpaa.style.booking/collapsable-type-card
                   {:on-click #(swap! state update :expanded (fn [e] (if (some #{type} e)
                                                                       (set/difference e #{type})
                                                                       (set/union e #{type}))))
                    :expanded (some #{type} (:expanded @state))
                    :content  (first (get (group-by :type type-data) type))}]
                  (for [{:keys [id] :as each} data]
                    [schpaa.style.booking/line-with-graph
                     {:selected (some #{id} (:selected @state))
                      :content  each
                      :on-click #(swap! state update :selected (fn [e] (if (some #{id} e)
                                                                         (set/difference e #{id})
                                                                         (set/union e #{id}))))}])]]))]

      (let [valid-registry? (and (empty? (time-input-validation (:values @time-state)))
                                 (not (empty? (:selected @state))))]
        [sc/row-end
         [hoc.buttons/cta
          {:disabled (not valid-registry?)
           :on-click #(open-dialog-confirmbooking time-state state card-data-v2 type-data)}
          "Book nå!"]])]]))

(rf/reg-fx :lab/open-new-blog-entry-dialog (fn [_] (schpaa.style.dialog/open-dialog-addpost)))

(rf/reg-event-fx :lab/just-create-new-blog-entry
                 (fn [_ _]
                   (tap> :lab/new-blog-entry)
                   {:fx [[:lab/open-new-blog-entry-dialog nil]]}))

(rf/reg-event-fx :lab/new-blog-entry
                 (fn [_ _]
                   (tap> :lab/new-blog-entry)
                   {:fx [[:dispatch [:app/navigate-to [:r.fileman-temporary]]]
                         [:lab/open-new-blog-entry-dialog nil]]}))

(rf/reg-sub :lab/has-chrome
            (fn [db]
              (get-in db [:settings :state :lab/toggle-chrome] true)))

(rf/reg-event-db :lab/toggle-chrome
                 (fn [db _]
                   (update-in db [:settings :state :lab/toggle-chrome] (fnil not false))))

;region access

; status -> anonymous, registered, waitinglist, member
; access -> admin, booking, nøkkelvakt

;at-least-registered
;(some #{(:role @user-state)} [:member :waitinglist :registered])

;:app/master-state-emulation

(rf/reg-sub :lab/master-state-emulation :-> :lab/master-state-emulation)
(rf/reg-event-db :lab/master-state-emulation (fn [db _]
                                               (update db :lab/master-state-emulation (fnil not false))))

(rf/reg-sub :lab/at-least-registered
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid] :as sim}] _]
              (if master-switch
                (and uid (some #{status} [:registered :waitinglist :member]))
                (some? (:uid ua)))))

(rf/reg-sub :lab/username-or-fakename
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid] :as sim}] _]
              (if master-switch
                uid
                (:email ua))))


(rf/reg-event-db :lab/set-sim-type (fn [db [_ arg]]
                                     (tap> {:lab/set-sim-type arg})
                                     (assoc-in db [:lab/sim :status] arg)))

(rf/reg-event-db :lab/set-sim (fn [db [_ arg opt]]
                                (cond
                                  (= :uid arg)
                                  (assoc-in db [:lab/sim :uid] (if (seq opt) opt nil))

                                  (= :booking arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:booking})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:booking}))

                                  (= :admin arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:admin})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:admin}))

                                  (= :nøkkelvakt arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:nøkkelvakt})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:nøkkelvakt}))
                                  :else
                                  db)))

(rf/reg-sub :lab/sim? :-> #(get % :lab/sim))

;deprecated
(rf/reg-sub :lab/user-state
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[ua sim] _]
              (if-let [s sim]
                (if (some #{(:status s)} [:member]) s s)
                ua)))

(rf/reg-sub :lab/uid
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [uid] :as sim}] _]
              (if master-switch
                uid
                (:uid ua))))

;todo: Assume we are always simulating

(rf/reg-sub :lab/status-is-none
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[_ {:keys [status access] :as sim}] _]))

;todo
(rf/reg-sub :lab/member
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[_ {:keys [status] :as sim}] _]
              (when sim (= :member status))))

(rf/reg-sub :lab/admin-access
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch {:keys [uid] :as ua} {:keys [status access] :as m}] _]
              (if master-switch
                (some? (some #{:admin} (or access [])))
                (let [u @(db/on-value-reaction {:path ["users" uid]})]
                  (if-let [x (booking.access/build-access-tuple u)]
                    (let [[s a] x]
                      (and (= s :member)
                           (= :admin (some #{:admin} (or a [])))))
                    false)))))

(rf/reg-sub :lab/booking
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch {:keys [uid] :as ua} {:keys [status access] :as m}] _]
              (if master-switch
                (and (= status :member)
                     (some? (some #{:booking} (or access []))))
                (let [u @(db/on-value-reaction {:path ["users" uid]})]
                  (if-let [x (booking.access/build-access-tuple u)]
                    (let [[s a] x]
                      (and (= s :member)
                           (= :booking (some #{:booking} (or a [])))))
                    false)))))

(rf/reg-sub :user-data
            (fn [[_ uid]]
              (when-let [id @(db.auth/user-info)]
                (db/on-value-reaction {:path ["users" id]})))
            (fn [u _]
              u))

(rf/reg-sub :lab/nokkelvakt
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access] :as m}] _]
              (if master-switch
                (and (= status :member)
                     (some? (some #{:nøkkelvakt} (or access []))))
                (let [uid (:uid ua)
                      u @(db/on-value-reaction {:path ["users" uid]})]
                  (tap> {:user u})
                  (if-let [[s a _ :as x] (booking.access/build-access-tuple u)]
                    (and (= s :member)
                         (= :nøkkelvakt (some #{:nøkkelvakt} (or a []))))
                    false)))))

(rf/reg-sub :lab/all-access-tokens
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid]}] _]
              (if master-switch
                [status access uid]
                (let [u @(db/on-value-reaction {:path ["users" (:uid ua)]})]
                  (booking.access/build-access-tuple u)))))

;endregion

(comment
  (let [status #{:bookings}]
    (some status [:booking])))

(rf/reg-sub :lab/toggle-userstate-panel :-> :lab/toggle-userstate-panel)

(rf/reg-event-db :lab/toggle-userstate-panel (fn [db _] (update db :lab/toggle-userstate-panel (fnil not false))))

(rf/reg-fx :lab/showuserinfo-fx (fn [data]
                                  (schpaa.style.dialog/open-user-info-dialog data)))

(rf/reg-event-fx :lab/show-userinfo (fn [_ [_ uid]] {:fx [[:lab/showuserinfo-fx uid]]}))
