(ns booking.lab
  (:require [reagent.core :as r]
            [kee-frame.core]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth]
            [schpaa.style.ornament :as sc]
            [schpaa.style.booking]
            [booking.data]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [schpaa.style.dialog :refer [open-dialog-confirmbooking]]
            [booking.views]
            [clojure.set :as set]
            [schpaa.style.button2 :as scb2]
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

(defn standard-menu-2 [data]
  [[(sc/icon-large [:> solid/TrashIcon]) "Fjern alle" #()]
   [(sc/icon-large [:> solid/CursorClickIcon]) "Siste valg" #()]
   nil
   [(sc/icon-large [:> solid/CogIcon]) "Book nå" #()]])

(defn complex-menu [settings]
  (let [show-1 #(swap! settings assoc :setting-1 %)
        select-2 #(swap! settings assoc :setting-2 %)]
    (concat [[(fn [v] (sc/icon-large (when v [:> solid/ShieldCheckIcon])))
              (fn [e] (if e "Long" "Short"))
              #(show-1 (not (:setting-1 @settings)))
              false
              #(:setting-1 @settings)]]
            [nil]
            (let [data [["small" 1]
                        ["medium" 2]
                        ["large" 3]]]
              (map (fn [[caption value]]
                     [(fn [v] (sc/icon-large (when (= value v) [:> outline/CheckIcon])))
                      caption
                      #(select-2 value)
                      false
                      #(:setting-2 @settings)])
                   data))
            [nil]
            [[(sc/icon-large [:> solid/BadgeCheckIcon])
              "Badge"
              nil
              true
              #()]
             [(sc/icon-large [:> solid/LightBulbIcon])
              "Bulba?"
              nil
              true
              #()]])))

(defn timeinput-shortcuts-definition [settings-atom]
  [[:header [sc/row {:class [:justify-between :items-end]}
             [sc/title1 "Top"]
             [sc/pill (or booking.data/VERSION "dev.x.y")]]]
   [:menuitem [(sc/icon-large [:> solid/BadgeCheckIcon])
               "Badge"
               nil
               true
               #()]]
   [:footer [sc/row-end {:class [:gap-4]} [sc/small1 "Terms"] [sc/small1 "Privacy"]]]])

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
                  :on-submit         (fn [e] (tap> e))
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

(rf/reg-sub :lab/modal-example-dialog2 :-> (fn [db] (get db :lab/modal-example-dialog2 false)))
(rf/reg-sub :lab/modal-example-dialog2-extra :-> (fn [db] (get db :lab/modal-example-dialog2-extra)))

(rf/reg-event-db :lab/modal-example-dialog2
                 (fn [db [_ arg extra]] (if arg
                                          (assoc db :lab/modal-example-dialog2 arg
                                                    :lab/modal-example-dialog2-extra extra)
                                          (update db :lab/modal-example-dialog2 (fnil not true)))))

(rf/reg-sub :lab/modal-selector-extra :-> (fn [db] (get db :lab/modal-selector-extra)))
(rf/reg-sub :lab/modal-selector :-> (fn [db] (get db :lab/modal-selector false)))
(rf/reg-event-db :lab/modal-selector
                 (fn [db [_ arg extra]]
                   (if (some? arg)
                     (assoc db :lab/modal-selector arg
                               :lab/modal-selector-extra extra)
                     (update db :lab/modal-selector (fnil not true)))))


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
                   {:fx [[:dispatch [:app/navigate-to [:r.booking-blog-new]]]
                         [:lab/open-new-blog-entry-dialog nil]]}))

(rf/reg-sub :lab/has-chrome (fn [db]
                              (get-in db [:settings :state :lab/toggle-chrome])))

(rf/reg-event-db :lab/toggle-chrome
                 (fn [db _]
                   (update-in db [:settings :state :lab/toggle-chrome] (fnil not false))))

(rf/reg-event-db :lab/set-sim-type (fn [db [_ arg]]
                                     (tap> arg)
                                     (assoc-in db [:lab/sim :role] arg)))

(rf/reg-event-db :lab/set-sim (fn [db [_ arg opt]]
                                (tap> [arg opt (:lab/sim db)])
                                (cond
                                  (= :booking arg)
                                  (if opt
                                    (update-in db [:lab/sim :status] (fnil set/union #{}) #{:booking})
                                    (update-in db [:lab/sim :status] (fnil set/difference #{}) #{:booking}))

                                  (= :admin arg)
                                  (if opt
                                    (update-in db [:lab/sim :status] (fnil set/union #{}) #{:admin})
                                    (update-in db [:lab/sim :status] (fnil set/difference #{}) #{:admin}))

                                  (= :nøkkelvakt arg)
                                  (if opt
                                    (update-in db [:lab/sim :status] (fnil set/union #{}) #{:nøkkelvakt})
                                    (update-in db [:lab/sim :status] (fnil set/difference #{}) #{:nøkkelvakt}))
                                  :else
                                  db)))

(rf/reg-sub :lab/sim? :-> #(get % :lab/sim))

(rf/reg-sub :lab/user-state
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[ua sim] _]
              (if-let [s sim]
                (if (some #{(:role s)} [:member]) s s)
                ua)))

(rf/reg-sub :lab/member
            :<- [:lab/sim?]
            (fn [{:keys [role status] :as sim} _]
              (if role
                (some #{role} [:member])
                false)))

(rf/reg-sub :lab/booking
            :<- [:lab/sim?]
            (fn [{:keys [role status] :as sim} _]
              (if status
                (= :booking (some status [:booking]))
                false)))

(rf/reg-sub :lab/nokkelvakt
            :<- [:lab/sim?]
            (fn [{:keys [role status] :as sim} _]
              (if status
                (= :nøkkelvakt (some status [:nøkkelvakt]))
                false)))

(rf/reg-sub :lab/admin
            :<- [:lab/sim?]
            (fn [{:keys [role status] :as sim} _]
              (if status
                (and
                  (some #{role} [:member])
                  (= :admin (some status [:admin])))
                false)))

(comment
  (let [status #{:bookings}]
    (some status [:booking])))
