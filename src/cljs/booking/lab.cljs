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
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.common-widgets :as widgets]
            [schpaa.debug :as l]
            [clojure.string :as str]
            [booking.access]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]))

(defonce store (r/atom {:selector :a
                        :current-filter nil
                        :category #{"kano" "surfski" "sup"}}))

(def selector (r/cursor store [:selector]))
(def category (r/cursor store [:category]))
(def current-filter (r/cursor store [:current-filter]))

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
          {:class     [(when @lightning-visible :shadow-sm)]
           :id        form-id
           :on-submit handle-submit}
          [booking.views/time-input props false]])])))

(rf/reg-event-fx :lab/qr-code-for-current-page
                 (fn [_ _]
                   (when-let [link @(rf/subscribe [:kee-frame/route])]
                     (booking.qrcode/show link))))

(defonce settings (r/atom nil))

(def show-stars (r/cursor settings [:rent/show-details]))

(defn headline-plugin []
  (let [delete-mode? @(rf/subscribe [:rent/common-show-deleted])
        details-mode? @(rf/subscribe [:rent/common-show-details])]
    (remove nil?
            [(when-let [uid @(rf/subscribe [:lab/uid])]
               (when-let [data @(db/on-value-reaction {:path ["users" uid "starred"]})]
                 (when-let [c (remove (comp false? val) data)]
                   (when (pos? (count c))
                     [sc/row-sc-g2
                      ;[sc/title1 {:class [:ml-2 :text-right]} (count c)]
                      [:div.relative
                       [button/reg-pill
                        {:class    [:large (if details-mode? :message :clear)]
                         :style    {:background-color (if @show-stars "var(--yellow-6)")}
                         :on-click #(swap! show-stars (fnil not false))}

                        [sc/icon-large {:style {:color (when @show-stars "var(--gray-9)")}}
                         ico/stjerne]]
                       [:div.absolute.-bottom-2.right-0.pointer-events-none
                        [:div.px-1x.h-4.grid.place-content-center
                         {:style {:min-width "1.3rem"
                                  :background "var(--text2)"
                                  :border-radius "var(--radius-round)"}}
                         [sc/small {:style {:color "var(--floating)"}
                                    :class [ :bold]} (count c)]]]]]))))
             [button/reg-pill
              {:class    [:large
                          (if delete-mode? :danger :clear)]
               :on-click #(swap! (r/cursor settings [:rent/show-deleted]) not)}
              [sc/icon-large ico/trash]]])))

(defn always-panel []
  [:<>
   [sc/col-space-8
    [sc/col
     [sc/row-center {:class [:z-10]}
      [widgets/pillbar {:class [:small]}
       current-filter
       [[:a "Vis alle"]
        [:b "Bruk filter"]]]]
     (when (= :b @current-filter)
       [sc/surface-ab
        {:style {:width "100%"
                 :max-width "768px"
                 :margin-inline "auto"
                 :margin-top  "-1rem"
                 :padding-top "2rem"}}
        [sc/row-center
         [widgets/matrix
          {:class [:small :flex-wrap]}
          category
          schpaa.components.views/kind-table]]])]]
   [:div.sticky.top-16.z-10
    [sc/row-center {:class []}
     [widgets/pillbar
      selector
      [[:a "Type"]
       [:b "Plassering"]
       [:c "Merke"]]]]]])


(defmulti render-list (fn [a r] a))

(defmethod render-list :default [_ r]
  [sc/surface-a
   [sc/row-center
     [sc/text "Hjelp"]]])

(defmethod render-list :a [_ r]
  (let [keys (when-let [uid @(rf/subscribe [:lab/uid])]
               (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))]
    (let [data (sort-by (comp second first) <
                        (group-by (comp :kind val)
                                  (remove (comp nil? :boat-type val)
                                          (filter (fn [[k v]] (and (= "0" (:location v))
                                                                   (if @show-stars
                                                                     (some #{k} keys)
                                                                     true)))
                                                  @(rf/subscribe [:db/boat-db])))))]
      [:div
       (into [:div]
             (for [[kind & r] data
                   :let [undefined? (empty? kind)]]
               [widgets/disclosure
                {:padded-heading true}
                (or kind "noname")
                [sc/title1
                 (or (schpaa.components.views/normalize-kind kind)
                     "Udefinert")]
                (for [{:keys [navn] :as z} r]
                  [:div.gap-1.w-full
                   {:style {:display               :grid
                            :grid-auto-rows        "auto"
                            :grid-template-columns (if undefined?
                                                     "1fr"
                                                     "repeat(auto-fill,minmax(16rem,1fr)")}}
                   (for [[[_boat-type _navn] r] (sort-by (comp last first) (group-by (comp (juxt :boat-type :navn) val) z))]
                     [:div.flex.flex-col.gap-2.w-full
                      {:style {:padding          "var(--size-2)"
                               :background-color "var(--floating)"
                               :border-radius    "var(--radius-0)"}}
                      (when-not undefined?
                        [widgets/stability-name-category (dissoc (-> r first val) :kind)])
                      ;todo toggle visibility via headerplugin
                      #_[:div.flex.flex-wrap.gap-1
                         (for [v (sort-by :number (map val r))
                               :let [{:keys [number slot _navn _description]} v]]
                           [widgets/badge
                            {:class    [:small]
                             :on-click #(booking.modals.boatinfo/open-modal-boatinfo {:data v})}
                            (subs (str number) 0 3)
                            slot])]])])]))])))

(defmethod render-list :b [_ r]
  (let [data (sort-by (comp (juxt first last) #(str/split % #" ") first) <
                      (group-by (comp first #(str/split % #" ") :slot val)
                                (into {}
                                      (map (fn [[k v]] [k v])
                                           (remove (comp nil? :boat-type val)
                                                   (filter (fn [[k v]] (not= "1" (:location v)))
                                                           @(rf/subscribe [:db/boat-db])))))))]
    [:<>
     (for [[[a] b] data]
       [sc/co
        [sc/row {:style {:min-height  "var(--size-10)"
                         :align-items :end}}
         [sc/title1 (if a (str
                            (str "Stativ " a)
                            (case (str a)
                              "1" ", nederst"
                              "4" ", øverst"
                              "")) "Ukjent")]]
        [:div
         {:style {:display               :grid
                  :grid-gap              "var(--size-1)"
                  :grid-template-columns "repeat(auto-fill,minmax(18rem,1fr)"}}
         (for [[k {:keys [navn number slot description kind] :as v}] (sort-by (comp :slot val) b)]
           [sc/row-sc-g2 {:style {:padding          "var(--size-1)"
                                  :background-color "var(--floating)"}}
            [widgets/badge {:class    [:small]
                            :on-click #(booking.modals.boatinfo/open-modal-boatinfo {:data v})}
             number
             slot]

            [widgets/stability-name-category v]])]])]))

(defmethod render-list :c [_ r]
  (let [starred-keys (when-let [uid @(rf/subscribe [:lab/uid])]
                       (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))
        data (sort-by (comp :navn val) <
                      (filter (fn [[k _v]]
                                (if @show-stars
                                  (some #{k} starred-keys)
                                  true))
                              @(rf/subscribe [:db/boat-type])))]
    [:div.grid.gap-1 {:style {:grid-template-columns "repeat(auto-fill,minmax(17rem,1fr))"}}
     (for [[k v] data]
       [sc/co
        {:style {:padding          "var(--size-1)"
                 :background-color "var(--floating)"
                 :border-radius    "var(--radius-0)"}}
        [widgets/stability-name-category' {:reversed? true
                                           :url?      true
                                           :k         k} v]
        [widgets/dimensions-and-material v]
        [sc/text1 (:description v)]
        [l/pre k]])]))

(defn render [r]
  [:div.px-2 (render-list @selector r)])

(comment
  (do
    (let [keys (when-let [uid @(rf/subscribe [:lab/uid])]
                 (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))])))

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
