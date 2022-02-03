(ns eykt.content.rapport-side
  (:require [schpaa.style :as st]
            [schpaa.modal.readymade :as readymade]
            [schpaa.button :as bu]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [statecharts.core :refer [assign]]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.debug :as l]
            [reagent.core :as r]
            [tick.core :as t]
            [times.api :as ta]))

(defn- empty-list-message [msg]
  (let [{:keys [bg fg- fg fg+ p p- hd hd- hl]} (st/fbg' :void)]
    [:div.flex.flex-col.items-center.space-y-4
     {:class (concat fg+ hl)}
     [:div.text-center {:style {:max-width "66vw"} :class hd} msg]

     [:div.text-base.font-semibold.font-inter.font-light.leading-relaxed
      {:class (concat fg)}
      "message in the middle"]

     [:div
      {:class    (concat fg hd-)
       :on-click #(readymade/popup
                    {:dialog-type :message
                     :flags       #{:weak-dim :timeout}
                     :content     [[:div.text-xl "Takk, vi vet..."]]})}
      "Ta kontakt med administrator"]]))

(defonce rapport-state (r/atom {"test" {:content "Some content"
                                        :created (t/at (t/date) (t/time "11:00"))}}))

(defn has-content-guard [state event]
  (not (empty? @rapport-state)))

(defn store-item [{:keys [data] :as event}]
  (let [id (random-uuid)
        {:keys [content]} data]
    (swap! rapport-state assoc id {:created (t/now)
                                   :content content})))

(defn update-item [{:keys [data] :as event}]
  (tap> event)
  (let [{:keys [id content]} data]
    (swap! rapport-state #(merge-with into % (assoc % id {:updated (t/now)
                                                          :content content})))))

(defn remove-item [{:keys [data] :as event}]
  (tap> event)
  (let [id data]
    (swap! rapport-state dissoc id)))

(defn confirmed [msg]
  (fn [_ _] (readymade/popup {:dialog-type :message
                              :content     msg})))

(defn affirmed [msg]
  (fn [_ _] (readymade/popup {:dialog-type :error
                              :content     msg})))

(def rapport-fsm
  {:initial :s.initial
   :states  {:s.initial          {:entry (confirmed "ughs")
                                  :on    {:e.startup [{:guard  has-content-guard
                                                       :target [:> :rapport :s.view]}
                                                      {:target [:> :rapport :s.empty]}]}}
             :s.empty            {:on {:e.add {:target :s.adding}}}
             :s.view             {:on {:e.start-managing {:target  :s.with-content
                                                          :actions (confirmed "managing started")}}}
             :s.with-content     {:on {:e.add          {:target :s.adding}
                                       :e.end-managing {:target  :s.view
                                                        :actions (confirmed "managing ended")}
                                       :e.edit         {:actions (assign (fn [st {:keys [data] :as event}]
                                                                           (assoc-in st [:rapport :currently-editing-id] data)))
                                                        :target  :s.editing}
                                       :e.delete       {:actions [(affirmed "deleted old entry")
                                                                  (fn [_ event] (remove-item event))]
                                                        :target  :s.return-from-edit}}}
             :s.editing          {:on {:e.save   {:target  :s.storing
                                                  :actions [(confirmed "saved old entry")
                                                            (fn [_ event]
                                                              (update-item event))]}
                                       :e.cancel {:target  :s.return-from-edit
                                                  :actions (assign (fn [st _]
                                                                     (update st :rapport dissoc :currently-editing-id)))}}}
             :s.adding           {:on {:e.save   {:target  :s.storing
                                                  :actions [(confirmed "saved new entry")
                                                            (fn [st event]
                                                              ;todo store the item and get back an id
                                                              (store-item event))]}
                                       :e.cancel :s.return-from-edit}}
             :s.storing          {:after [{:delay  10
                                           :target :s.return-from-edit}]}
             :s.return-from-edit {:always [{:guard  has-content-guard
                                            :target [:> :rapport :s.with-content]}
                                           {:target [:> :rapport :s.empty]}]}
             :s.ready            {}
             :s.error            {}}})

(defn rapport-side []
  (let [{:keys [bg bg+ fg+]} (st/fbg' :void)
        *fsm-rapport (:rapport @(rf/subscribe [::rs/state :main-fsm]))]
    [:div
     [l/ppre-x
      #_(map (fn [[k v]] {(subs (str k) 0 4) v}) @rapport-state)
      #_(rf/subscribe [::rs/state-full :main-fsm])
      *fsm-rapport]
     (rs/match-state *fsm-rapport
       [:s.initial]
       (send :e.startup)

       [:s.empty]
       [:div
        {:class (concat bg+ fg+)}
        [:div.space-y-px.flex.flex-col
         {:style {:min-height "calc(100vh - 7rem)"}}
         (if false                                          ;(seq eykt.calendar.core/rules')
           [:div.flex-1
            {:class bg}
            [:div [(-> schpaa.components.sidebar/tabs-data :bar-chart :content-fn)]]]
           [:div.flex-col.grow.flex.items-center.justify-center.space-y-8
            [empty-list-message
             "Hei, ingen har laget en forside som passer" "..."]
            [:div.flex.gap-4.flex-center
             [bu/regular-hollow-button {:on-click #(send :e.add)
                                        :class    [:rounded :h-12 :w-20]} :plus]]])

         #_[booking.views/last-bookings-footer {}]]]

       [:s.adding]
       (let [{:keys [fg]} (st/fbg' :void)]
         [:div.p-4.space-y-4
          {:class fg}
          [:div *fsm-rapport]
          [:div.flex.gap-4
           [bu/regular-button {:on-click #(send :e.save {:content :new-entry})} "Lagre"]
           [bu/regular-button {:on-click #(send :e.cancel)} "Avbryt"]]])

       [:s.editing]
       (let [{:keys [fg]} (st/fbg' :void)
             id (-> (rf/subscribe [::rs/state-full :main-fsm]) deref :rapport :currently-editing-id)]
         [:div.p-4.space-y-4
          {:class fg}
          [:div *fsm-rapport]
          [:div (str id)]
          [:div.flex.gap-4
           [bu/regular-button {:on-click #(send :e.save {:id      id
                                                         :content :edited-entry})} "Lagre"]
           [bu/regular-button {:on-click #(send :e.cancel)} "Avbryt"]]])

       [:s.view]
       (let [{:keys [fg]} (st/fbg' :void)]
         [:div.p-4.space-y-4
          {:class fg}

          (into [:div]
                (for [[k {:keys [content created updated] :as v}] @rapport-state]
                  [:div content]))
          [:div.flex.justify-between.gap-4
           [:div]
           [bu/regular-button {:on-click #(send :e.start-managing)} "Rediger"]]])

       [:s.with-content]
       (let [{:keys [fg]} (st/fbg' :void)]
         [:div.p-4.space-y-4
          {:class fg}
          [:div "Med innhold her:"]
          [bu/regular-button-small {:on-click #(readymade/popup {:content "go"})} "popup"]
          [bu/regular-button-small {:on-click #(readymade/message {:content "go"})} "message"]
          (into [:div.space-y-1]
                (for [[k {:keys [content created updated] :as v}] @rapport-state]
                  [:div.flex.justify-between.gap-10
                   [:div.grow.flex.justify-between
                    [:div.w-16.whitespace-nowrap.truncate (str k)]
                    [:div (str content)]
                    [:div (when created (ta/short-time-format (t/time created)))]
                    [:div (when updated (ta/short-time-format (t/time updated)))]]
                   [:div.flex.gap-1
                    [bu/danger-button-small {:on-click #(send :e.delete k)} "Slett"]
                    [bu/regular-button-small {:on-click #(send :e.edit k)} "Red"]]]))
          [:div.flex.justify-between.gap-4
           [bu/regular-button {:on-click #(send :e.add)} "Ny"]
           [bu/regular-button {:on-click #(send :e.end-managing)} "Avslutt"]]])

       [:s.storing]
       [:div "Lagrer, et øyeblikk"]

       [:div.text-white.bg-black "OTHER " (str *fsm-rapport)])]))
