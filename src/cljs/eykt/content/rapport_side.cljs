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
            [schpaa.markdown :as markdown]
            [times.api :as ta]
            [db.core :as db]))

(defn- empty-list-message [msg]
  (let [{:keys [bg fg- fg fg+ p p- hd hd- hl]} (st/fbg' :void)]
    [:div.flex.flex-col.items-center.space-y-4
     {:class (concat fg+ hl)}
     [:div.text-center {:style {:max-width "66vw"} :class hd} msg]

     #_[:div.text-base.font-semibold.font-inter.font-light.leading-relaxed
        {:class (concat fg)}
        "message in the middle"]

     [:div
      {:class    (concat fg hd-)
       :on-click #(readymade/popup
                    {:dialog-type :message
                     :flags       #{:weak-dim :timeout}
                     :content     [[:div.text-xl "Takk, vi vet..."]]})}
      "Legg til en prøve"]]))

;region api-surface for db

(def rapport-state (r/atom {"test1" {:content "# Some content in markdown\nSecond line\n\nThird line"
                                     :style   :a
                                     :hidden  false
                                     :created (t/at (t/date) (t/time "11:00"))
                                     :updated (t/at (t/date) (t/time "14:00"))}
                            "test2" {:content "# Some content in markdown\n\nSecond line comes here"
                                     :hidden  true
                                     :style   :b
                                     :created (t/at (t/date) (t/time "11:00"))}
                            "test3" {:content "# Some content in markdown"
                                     :style   :c
                                     :created (t/at (t/date) (t/time "11:00"))}
                            "test4" {:content "# Some content in markdown"
                                     :style   :a
                                     :created (t/at (t/date) (t/time "11:00"))}
                            "test5" {:content "# Some content in markdown"
                                     :style   :a
                                     :created (t/at (t/date) (t/time "11:00"))}
                            "test6" {:content "# Some content in markdown"
                                     :style   :x
                                     :created (t/at (t/date) (t/time "11:00"))}}))

(defn get-content []
  (if-let [data (some-> (db/on-value-reaction {:path ["report" "articles"]}))]
    @data
    [{}]))

(defn store-item [st {:keys [data] :as event}]
  (let [{:keys [content]} data
        id (.-key (db/database-push {:path  ["report" "articles"]
                                     :value {:created (str (t/now))
                                             :content content}}))]
    (assoc-in st [:rapport :new-id] id)
    #_(let [id (random-uuid)
            {:keys [content]} data]
        (swap! rapport-state assoc id {:created (t/now)
                                       :content content})
        (assoc-in st [:rapport :new-id] id))))

(defn update-item [{:keys [data] :as event}]
  (tap> event)
  (let [{:keys [id content]} data]
    (swap! rapport-state #(merge-with into % (assoc % id {:updated (t/now)
                                                          :content content})))))

(defn set-item-to-visible [{:keys [data] :as event}]
  (tap> event)
  (let [id data]
    (swap! rapport-state #(merge-with into % (assoc % id {:hidden false})))))

(defn set-item-to-hidden [{:keys [data] :as event}]
  (tap> event)
  (let [id data]
    (swap! rapport-state #(merge-with into % (assoc % id {:hidden true})))))

(defn remove-item [{:keys [data] :as event}]
  (let [id data]
    (swap! rapport-state #(merge-with into % (assoc % id {:deleted true})))))

(defn restore-item [{:keys [data] :as event}]
  (let [id data]
    (swap! rapport-state #(merge-with into % (assoc % id {:hidden  false
                                                          :deleted false})))))

;endregion

;region fsm-helpers

(defn has-content-guard [state event]
  (pos? (count @(db/on-value-reaction {:path ["report" "articles"]})))
  #_(not (empty? (remove (fn [[_ v]] (:hidden v)) (get-content)))))

(defn confirmed [msg]
  (fn [_ _] (readymade/popup {:dialog-type :message
                              :flags       #{:popup :short-timeout}
                              :content     msg})))

(defn affirmed [msg]
  (fn [_ _] (readymade/popup {:dialog-type :error
                              :content     msg})))

;endregion

(def rapport-fsm
  {:initial :s.initial
   :states  {:s.initial                  {:entry (confirmed "startup")
                                          :on    {:e.waiting [{:guard  has-content-guard
                                                               :target :s.some-content}
                                                              {:target :s.empty-content}]
                                                  :e.startup [{:guard  has-content-guard
                                                               :target :s.some-content}
                                                              {:target :s.empty-content}]}}
             :s.wait-for-a-moment        {:entry [{:guard  has-content-guard
                                                   :target :s.some-content}
                                                  {:target :s.empty-content}
                                                  #_{:delay  500
                                                     :target :s.some-content}]}

             :s.empty-content            {:on {:e.add            {:target :s.adding}
                                               :e.start-managing {:target  :s.manage-content
                                                                  :actions (confirmed "managing started")}}}
             :s.some-content             {:on {:e.hide-entry     [{:guard   has-content-guard
                                                                   :actions [(confirmed "hide content")
                                                                             (fn [_ {:keys [data] :as event}]
                                                                               (set-item-to-hidden event))]
                                                                   :target  :s.some-content}
                                                                  {:target :s.empty-content}]

                                               :e.start-managing {:target  :s.manage-content
                                                                  :actions (confirmed "managing started")}
                                               :e.edit           {:actions (assign (fn [st {:keys [data] :as event}]
                                                                                     (assoc-in st [:rapport :currently-editing-id] data)))
                                                                  :target  :s.editing'}}}
             :s.manage-content           {:on {:e.show-entry   {:actions [(confirmed "content shown")
                                                                          (fn [_ {:keys [data] :as event}]
                                                                            (set-item-to-visible event))]}
                                               :e.hide-entry   {:actions [(confirmed "content hidden")
                                                                          (fn [_ {:keys [data] :as event}]
                                                                            (set-item-to-hidden event))]}
                                               :e.add          {:target :s.adding}
                                               :e.end-managing {:target  :s.ending-managing
                                                                :actions (confirmed "managing ended")}
                                               :e.edit         {:actions (assign (fn [st {:keys [data] :as event}]
                                                                                   (assoc-in st [:rapport :currently-editing-id] data)))
                                                                :target  :s.editing}
                                               :e.undelete     {:actions [(confirmed "undeleted old entry")
                                                                          (fn [_ event] (restore-item event))]
                                                                :target  :s.manage-content}
                                               :e.delete       {:actions [(affirmed "deleted old entry")
                                                                          (fn [_ event] (remove-item event))]
                                                                :target  :s.manage-content}}}
             ;todo Editing from the manager
             :s.editing                  {:on {:e.save   {:target  :s.return-from-edit-or-add
                                                          :actions [(fn [_ event]
                                                                      (update-item event))
                                                                    (assign (fn [st event]
                                                                              (let [k (-> event :data :id)]
                                                                                (readymade/popup {:dialog-type :message
                                                                                                  :flags       #{:popup :short-timeout}
                                                                                                  :content     "saved old entry"
                                                                                                  :footer      k}))
                                                                              (update st :rapport dissoc :currently-editing-id)))]}
                                               :e.cancel {:target  :s.return-from-edit-or-add
                                                          :actions (assign (fn [st _]
                                                                             (update st :rapport dissoc :currently-editing-id)))}}}
             ;todo When coming from the frontpage, ending editing must return to the frontpage, thus the state :s.editing'
             :s.editing'                 {:on {:e.save   [{:target  :s.return-from-edit-or-add' ;:s.storing'
                                                           :actions [(fn [_ event]
                                                                       (update-item event))
                                                                     (assign (fn [st event]
                                                                               (let [k (-> event :data :id)]
                                                                                 (readymade/popup {:dialog-type :message
                                                                                                   :flags       #{:popup :short-timeout}
                                                                                                   :content     "saved old entry"
                                                                                                   :footer      k}))
                                                                               (update st :rapport dissoc :currently-editing-id)))]}]

                                               :e.cancel {:target  :s.return-from-edit-or-add'
                                                          :actions (assign (fn [st _]
                                                                             (update st :rapport dissoc :currently-editing-id)))}}}
             :s.adding                   {:on {:e.save   {:target  :s.return-from-edit-or-add
                                                          :actions [;(confirmed "saved new entry")
                                                                    (assign (fn [st event]
                                                                              (store-item st event)))
                                                                    (assign (fn [st event]
                                                                              (let [k (-> st :rapport :new-id str)]
                                                                                (readymade/popup {:dialog-type :message
                                                                                                  :flags       #{:popup :short-timeout}
                                                                                                  :content     "saved new entry"
                                                                                                  :footer      k}))
                                                                              (update-in st [:rapport] dissoc :new-id)))]}
                                               :e.cancel :s.return-from-edit-or-add}}

             #_#_:s.storing {:after [{:delay  1000
                                      :target :s.return-from-edit-or-add}]}
             :s.ending-managing          {:always [{:guard  has-content-guard
                                                    :target [:> :rapport :s.some-content]}
                                                   {:target [:> :rapport :s.empty-content]}]}
             :s.return-from-edit-or-add  {:always [{:guard  has-content-guard
                                                    :target [:> :rapport :s.manage-content]}
                                                   {:target [:> :rapport :s.empty-content]}]}
             ;see :s.editing'
             :s.return-from-edit-or-add' {:always [{:guard  has-content-guard
                                                    :target [:> :rapport :s.some-content]}
                                                   {:target [:> :rapport :s.empty-content]}]}
             :s.ready                    {}
             :s.error                    {}}})

(defn no-content-message []
  (let [{:keys [bg bg+ fg+]} (st/fbg' :void)]
    [:div
     {:class (concat bg+ fg+)}
     [:div.space-y-px.flex.flex-col
      {:style {:min-height "calc(100vh - 7rem)"}}
      [:div.flex.flex-center.h-32 [schpaa.icon/spinning :spinner]]
      #_(if false                                           ;(seq eykt.calendar.core/rules')
          [:div.flex-1
           {:class bg}
           [:div [(-> schpaa.components.sidebar/tabs-data :bar-chart :content-fn)]]]
          [:div.flex-col.grow.flex.items-center.justify-center.space-y-8
           [empty-list-message
            "Yo, ingen har skrevet\u00ad noe ennå" "..."]
           [:div.flex.gap-4.flex-center
            [bu/regular-hollow-button {:on-click #(store-item {} {:data {:content "sample post"}})
                                       :class    [:rounded :h-12 :w-20]} :plus]
            #_[bu/regular-hollow-button {:on-click #(send :e.start-managing)
                                         :class    [:rounded :h-12 :w-20]} "Rediger"]]])
      #_[booking.views/last-bookings-footer {}]]]))

(defn rapport-side []
  (let [{:keys [bg bg+ fg+]} (st/fbg' :void)
        *fsm-rapport (:rapport @(rf/subscribe [::rs/state :main-fsm]))]
    [:div
     [:div.sticky.top-28
      [l/ppre-x
       #_(map (fn [[k v]] {(subs (str k) 0 6) v}) @rapport-state)
       ;@(rf/subscribe [::rs/state-full :main-fsm])
       *fsm-rapport]]
     (rs/match-state *fsm-rapport
       [:s.initial]
       (if-let [data (get-content)]
         (send :e.startup)
         (send :e.waiting))

       [:s.empty-content]
       [:div
        {:class (concat bg+ fg+)}
        [:div.space-y-px.flex.flex-col
         {:style {:min-height "calc(100vh - 7rem)"}}
         [:div.flex-col.grow.flex.items-center.justify-center.space-y-8
          [empty-list-message
           "Hei, ingen har laget en forside som passer" "..."]
          [:div.flex.gap-4.flex-center
           [bu/regular-hollow-button {:on-click #(send :e.add)
                                      :class    [:rounded :h-12 :w-20]} :plus]
           [bu/regular-hollow-button {:on-click #(send :e.start-managing)
                                      :class    [:rounded :h-12 :w-20]} "Rediger"]]]

         #_[booking.views/last-bookings-footer {}]]]

       [:s.adding]
       (let [{:keys [fg]} (st/fbg' :void)]
         [:div.p-4.space-y-4
          {:class fg}
          [:div *fsm-rapport]
          [:div.flex.gap-4
           [bu/regular-button {:on-click #(send :e.save {:content :new-entry})} "Lagre"]
           [bu/regular-button {:on-click #(send :e.cancel)} "Avbryt"]]])

       [:s.editing']
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

       [:s.some-content]
       (let [{:keys [fg bg bg-]} (st/fbg' :form)]
         (tap> "HERE")
         [:div.space-y-4
          ;{:class (concat fg bg)}
          (into [:div.flex.flex-col.space-y-px
                 {:class (concat [])}]
                (for [[k {:keys [style content created updated] :as v}] (remove (fn [[_ v]] (or (:deleted v) (:hidden v))) (get-content))]
                  [:div.prose.mx-auto.w-full
                   {:class (concat
                             [:p-4]
                             (case style
                               :a (concat
                                    bg-
                                    [:prose-h1:text-xl
                                     :prose-h1:font-oswald
                                     :prose-h3:text-base
                                     :prose-h3:font-bold
                                     :prose-h3:uppercase
                                     ;"prose-h3:text-alt"
                                     ;"prose-h3:dark:text-orange-300/90"
                                     :prose-p:leading-relaxed
                                     :prose-p:font-normal
                                     :prose-p:font-serif
                                     ;"prose-p:text-black/50"
                                     ;"prose-a:text-sky-400"
                                     :prose-a:leading-snug
                                     :prose-p:m-0
                                     :prose-p:mb-2
                                     :prose-p:p-0
                                     ;:prose-strong:text-white
                                     ;"prose-strong:bg-black/50"
                                     :prose-strong:py-1
                                     :prose-strong:px-2
                                     :prose-strong:rounded

                                     :prose-ul:list-decimal
                                     :prose-ul:list-outside])
                               :b (concat
                                    bg)
                               :c (concat
                                    bg-)
                               (concat
                                 [:bg-rose-500])))}
                   [:div
                    [:div.flex.justify-between
                     [:div "Sample time"]
                     #_[:div
                        (if update
                          (ta/time-format (t/time update)))
                        (if created
                          (ta/time-format (t/time created)))]
                     [:div.flex.gap-1
                      [bu/regular-button-small {:on-click #(send :e.edit k)} "Edit"]
                      [bu/regular-button-small {:on-click #(send :e.hide-entry k)} "Hide"]
                      [bu/regular-button-small {} "Delete"]]]
                    (markdown/md->html content)]]))

          [:div.flex.justify-between.gap-4.px-4
           [:div]
           [bu/regular-button {:class    [:rounded]
                               :on-click #(send :e.start-managing)} "Rediger"]]])

       [:s.manage-content]
       (let [{:keys [fg- fg fg+ bg+ bg- bg p+ p-]} (st/fbg' :form)
             show-deleted-posts (schpaa.state/listen :report/show-deleted-posts)]
         [:div.space-y-4.flex.flex-col.pt-4
          {:style {:min-height "calc(100vh - 7rem)"}
           :class (concat fg+ bg)}
          [:div.px-4 "Med innhold her:"]
          (into [:div.space-y-px.grow]
                (concat
                  (for [[k {:keys [content created deleted updated hidden] :as v}] (if @show-deleted-posts
                                                                                     (get-content)
                                                                                     (remove (fn [[k v]] (:deleted v)) (get-content)))]
                    [:div.flex.justify-between.gap-4.p-2
                     {:class (if (or hidden deleted) bg bg-)}
                     [:div.grow.flex.justify-between
                      ;[:div.w-12.whitespace-nowrap.truncate {:class (concat fg-)} (str k)]
                      [:div.w-32.truncate.grow {:class (concat (if (or hidden deleted) fg- fg+) (if deleted [:line-through]))} (str content)]]
                     ;[:div (when created (ta/short-time-format (t/time (str created))))]
                     ;[:div (when updated (ta/short-time-format (t/time (str updated))))]]
                     [:div.flex.gap-1
                      (when-not deleted (if hidden
                                          [bu/regular-button-small {:on-click #(send :e.show-entry k)} :eye]
                                          [bu/regular-button-small-toggled {:on-click #(send :e.hide-entry k)} :eye-off]))
                      (if @show-deleted-posts
                        (if deleted
                          [bu/danger-button-small {:on-click #(send :e.undelete k)} :rotate-left]
                          [bu/danger-button-small {:on-click #(send :e.delete k)} :cross-out])
                        [bu/danger-button-small {:on-click #(send :e.delete k)} :cross-out])

                      [bu/regular-button-small {:on-click #(send :e.edit k)} :edit-state]]])
                  [[:div.px-4 [bu/regular-button {:on-click #(send :e.add)} :plus]]]))
          (let [{:keys [fg- fg fg+ bg+ bg- bg p+ p-]} (st/fbg' :surface)]
            [:div.border-t.border-gray-500
             {:class bg}
             [:div.flex.justify-between.gap-4.p-2
              [bu/regular-button {:on-click #(send :e.add)} "Ny"]
              [bu/regular-button {:on-click #(send :e.end-managing)} "Avslutt"]]
             (let [e "Vis slettede"
                   e' :e']
               (schpaa.components.views/modern-checkbox'
                 {:set-details #(schpaa.state/change :report/show-deleted-posts %)
                  :get-details #(-> @show-deleted-posts)}
                 (fn [checkbox]
                   [:div.flex.items-center.justify-between.gap-4.w-full.h-16.p-4
                    [:div.space-y-0.text-rightx
                     [:div {:class (concat fg p+)} e]
                     #_[:div {:class (concat fg- p-)} e']]
                    checkbox])))])])

       [:s.storing]
       [:div "Lagrer, et øyeblikk"]

       [:s.wait-for-a-moment]
       [:div.flex.flex-center.h-screen
        ;[:div "vent litt"]
        [schpaa.icon/spinning :spinner]
        #_(if-let [v (some-> (db/database-get {:path ["report"]}) deref)]
            (send :e.))]


       (do
         (tap> [:>>>s *fsm-rapport])
         [:div.text-white.bg-black "OTHER " (str *fsm-rapport)]))]))
