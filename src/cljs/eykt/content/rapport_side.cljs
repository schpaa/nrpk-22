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
            [db.core :as db]
            [fork.re-frame :as fork]
            [schpaa.components.fields :as fields]
            [schpaa.components.sidebar]
            [clojure.set :as set]))

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

(defn get-content [source-path]
  (if-let [data (some-> (db/on-value-reaction {:path source-path}))]
    @data
    [{}]))

(defn store-item [st {:keys [data source-path] :as event}]
  (tap> ["store-item" event])
  (let [{:keys [content]} data
        id (.-key (db/database-push {:path  source-path
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
  (let [{:keys [id content source-path]} data]
    (db/database-update {:path (conj source-path (name id)) :value {:updated (str (t/now))
                                                                    :content content}})
    #_(swap! rapport-state #(merge-with into % (assoc % id {:updated (t/now)
                                                            :content content})))))

(defn set-item-to-visible [{:keys [data] :as event}]
  (tap> event)
  (let [{:keys [id source-path]} data]
    (db/database-update {:path (conj source-path (name id)) :value {:hidden false}})
    #_(swap! rapport-state #(merge-with into % (assoc % id {:hidden false})))))

(defn set-item-to-hidden [{:keys [data] :as event}]
  (tap> event)
  (let [{:keys [id source-path]} data]
    (db/database-update {:path (conj source-path (name id)) :value {:hidden true}})
    #_(swap! rapport-state #(merge-with into % (assoc % id {:hidden true})))))

(defn delete-item [{:keys [data] :as event}]
  (let [{:keys [id source-path]} data]
    (db/database-update {:path (conj source-path (name id)) :value {:deleted true}})
    #_(swap! rapport-state #(merge-with into % (assoc % id {:deleted true})))))

(defn restore-item [{:keys [data] :as event}]
  (let [{:keys [id source-path]} data]
    (db/database-update {:path (conj source-path (name id)) :value {:deleted false}})
    #_(swap! rapport-state #(merge-with into % (assoc % id {:hidden  false
                                                            :deleted false})))))

;endregion

;region fsm-helpers

(defn has-content-guard [state event]
  (let [path (-> event :data :source-path)]
    (pos? (count @(db/on-value-reaction {:path path})))))

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
   :states  {:s.initial                  {:entry (confirmed "starter opp")
                                          :on    {:e.waiting [{:guard  has-content-guard
                                                               :target :s.some-content}
                                                              {:target :s.empty-content}]
                                                  :e.startup [{:guard  has-content-guard
                                                               :target :s.some-content}
                                                              {:target :s.empty-content}]}}
             :s.wait-for-a-moment        {:entry [{:guard  has-content-guard
                                                   :target :s.some-content}
                                                  {:target :s.empty-content}]}

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
                                                                  :target  :s.editing'}
                                               :e.add            {:target :s.adding-at-front}
                                               :e.delete         {:actions [(affirmed "deleted old entry")
                                                                            (fn [_ event] (delete-item event))]}}}

             :s.manage-content           {:on {:e.show-entry   {:actions [(confirmed "content shown")
                                                                          (fn [_ event]
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
                                                                          (fn [_ event] (delete-item event))]}}}
             ;todo Editing from the manager
             :s.editing                  {:on {:e.save   {:target  :s.return-from-edit-or-add
                                                          :actions [(fn [_ event]
                                                                      (update-item event))
                                                                    (assign (fn [st event]
                                                                              (let [k (-> event :data :id)]
                                                                                (readymade/popup {:dialog-type :message
                                                                                                  :flags       #{:popup :short-timeout}
                                                                                                  :content     "saved old entry"
                                                                                                  :footer      [k]}))
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
                                                                                                   :footer      [k]}))
                                                                               (update st :rapport dissoc :currently-editing-id)))]}]

                                               :e.cancel {:target  :s.return-from-edit-or-add'
                                                          :actions (assign (fn [st _]
                                                                             (update st :rapport dissoc :currently-editing-id)))}}}
             :s.adding-at-front          {:on {:e.save   {:target  :s.return-from-edit-or-add'
                                                          :actions [;(confirmed "saved new entry")
                                                                    (assign (fn [st event]
                                                                              (store-item st event)))
                                                                    (assign (fn [st event]
                                                                              (let [k (-> st :rapport :new-id str)]
                                                                                (readymade/popup {:dialog-type :message
                                                                                                  :flags       #{:popup :short-timeout}
                                                                                                  :content     "saved new entry"
                                                                                                  :footer      [k]}))
                                                                              (update-in st [:rapport] dissoc :new-id)))]}
                                               :e.cancel :s.return-from-edit-or-add'}}

             :s.adding                   {:on {:e.save   {:target  :s.return-from-edit-or-add
                                                          :actions [;(confirmed "saved new entry")
                                                                    (assign (fn [st event]
                                                                              (store-item st event)))
                                                                    (assign (fn [st event]
                                                                              (let [k (-> st :rapport :new-id str)]
                                                                                (readymade/popup {:dialog-type :message
                                                                                                  :flags       #{:popup :short-timeout}
                                                                                                  :content     "saved new entry"
                                                                                                  :footer      [k]}))
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
      ;[:div.flex.flex-center.h-32 [schpaa.icon/spinning :spinner]]
      (if false                                             ;(seq eykt.calendar.core/rules')
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

(defn top-bottom-view
  ([content footer]
   (top-bottom-view "calc(100vh - 7rem)" content footer))
  ([min-height content footer]
   (let [{:keys [bg- bg bg+ fg+]} (st/fbg' :void)]
     [:div.flex.flex-col
      {:class bg
       :style {:min-height min-height}}
      [:div.grow content]
      (let [{:keys [bg bg+ fg+ br]} (st/fbg' :tabbar)]
        [:div.sticky.bottom-0.pb-4.pt-2
         {:class (concat bg br [:border-t])}
         [:div footer]])])))

(defn map-difference [m1 m2]
  (let [ks1 (set (keys m1))
        ks2 (set (keys m2))
        ks1-ks2 (set/difference ks1 ks2)
        ks2-ks1 (set/difference ks2 ks1)
        ks1*ks2 (set/intersection ks1 ks2)]
    (merge (select-keys m1 ks1-ks2)
           (select-keys m2 ks2-ks1)
           (select-keys m1
                        (remove (fn [k] (= (m1 k) (m2 k)))
                                ks1*ks2)))))

(defn preview [style dt content]
  (let [common [:xs:px-2 :px-2 :pb-8 :my-px]
        date [:div.sticky.top-32.relative
              [:div.absolute.-top-4x.right-0.bg-black.text-white.px-2.py-1.text-xs (ta/date-format (t/instant dt))]]]
    (case style
      "c"
      [:div.bg-white.dark:bg-black
       {:class common}
       date
       [:div.max-w-2xl.mx-auto.prose
        {:class [:prose-stone
                 :dark:prose-invert
                 :prose-h2:mb-2
                 :prose-headings:font-black
                 :prose-headings:text-rose-500
                 :prose-h1:text-xl
                 :prose-h2:text-xl
                 :prose-h3:text-lg
                 :prose-p:font-serif
                 :prose-li:font-sans
                 "prose-li:text-black/50"
                 "prose-li:italic"]}
        (schpaa.markdown/md->html content)]]
      "b"
      [:div.bg-amber-100.dark:bg-gray-900
       {:class common}
       date
       [:div.mx-auto.max-w-2xl.prose
        {:class [:prose-stone :dark:prose-invert
                 :prose-h2:mb-2
                 :prose-headings:font-black
                 :prose-headings:text-alt
                 :prose-headings:py-0
                 :prose-headings:my-0
                 :prose-h1:text-4xl
                 :prose-h2:text-4xl
                 :prose-h3:text-lg
                 :prose-p:font-lora
                 :prose-p:text-base
                 :prose-li:font-sans
                 "prose-li:text-black/50"
                 "prose-li:italic"]}
        (schpaa.markdown/md->html content)]]
      ;"c"
      [:div.bg-sky-100.dark:bg-gray-800.relative
       {:class common}
       date
       [:div.max-w-2xl.mx-auto.prose.z-10
        {:class [:dark:prose-invert
                 :prose-headings:text-sky-600
                 :prose-gray
                 :prose-h2:mb-2
                 :prose-headings:font-black
                 :prose-h1:text-4xl
                 :prose-h1:my-0
                 :prose-h1:py-0
                 :prose-h2:my-0
                 :prose-h2:py-0
                 :prose-h2:text-4xl
                 :prose-h3:text-lg
                 :prose-p:font-serif
                 ;:prose-p:columns-2xs
                 :prose-li:font-sans
                 "prose-li:text-black/50"
                 "prose-li:italic"]}
        (schpaa.markdown/md->html content)]]
      #_[:div
         [:div (str style)]
         [:div.prose (schpaa.markdown/md->html content)]])))

;region form-input

(defn test-func [{:keys [form-id values handle-submit errors handle-change set-values] :as props}]
  [:form {:class     [:space-y-4]
          :form-id   form-id
          :on-submit handle-submit}
   ;[l/ppre-x values]
   ;[:div {:class p-} (str form-id)]

   (when (some? (values :report-type))
     [:div.flex.gap-4
      [fields/select (-> props fields/regular-field)
       :label "Rapport av"
       :name :report-type
       :items {"a" "vær" "b" "hsm/hendelse" "c" "materiell skade" "d" "materiell mangler" "e" "til info"}
       :default-text "hva?"
       :error-type :marker]])

   (when (not= (values :report-type) "a")
     [fields/text props :name :header :label "Overskrift"])

   [:div.flex.gap-4
    [fields/date (-> props fields/date-field) :name :created-date :label "Gjelder dato"]
    [fields/time (-> props fields/time-field) :name :created-time :label "Klokke"]]

   [:div.flex.gap-4
    (when (not= (values :report-type) "a")
      [:div.flex.gap-4
       [fields/select (-> props fields/small-field)
        :label "Utseende"
        :name :style
        :items {"a" "vanlig" "b" "flott" "c" "fin" "d" "info"}
        :default-text "velg stil"
        :error-type :marker]])

    (when (not= (values :report-type) "a")
      [fields/checkbox props :label "Forhåndsvis" :name :preview])]

   (when (not= (values :report-type) "a")
     [:div {:xclass (if (values :preview) :hidden)}
      [fields/textarea (-> props (assoc
                                   :naked? false
                                   :handle-blur #(do
                                                   (when (empty? (values :header))
                                                     (set-values {:header (first (clojure.string/split-lines (values :content)))})))
                                   ;:handle-change #(handle-change %)
                                   :class [:x-mx-2 :x-mb-4 :xs:mx-0 :xs:mb-0 :rounded-none :xs:rounded]))
       :name :content
       :label "Innhold"
       :error-type :marker]])
   (if (values :preview)
     [:div
      [:div {:class (concat [:uppercase] (fields/label-colors false))} "Innhold"]
      [:div.px-2 [preview (values :style) (values :content)]]])

   (when (= (values :report-type) "a")
     [:div.flex.gap-4
      [fields/number (-> props fields/small-field) :label "Vann temp" :name :water-temp]
      [fields/number (-> props fields/small-field) :label "Luft temp" :name :air-temp]])])

(defn editing-form [source-path id]
  (let [form-id (random-uuid)
        form-state (r/atom nil)
        id (if (keyword? id) (name id) id)
        {:keys [p p- fg bg]} (st/fbg' :form)
        data (if id
               (db/on-value-reaction {:path (conj source-path id)})
               (atom {:created (str (t/now))}))
        initial-state @data]
    (fn [id]
      [:div.overflow-clip
       [top-bottom-view
        [:div.py-4.px-2.xs:px-4
         {:class (concat fg bg)}
         ;[:div {:class p-} id]
         [:div
          [fork/form {:form-id             form-id
                      :state               form-state
                      :keywordize-keys     true
                      :clear-on-umount     false
                      #_#_:validation (fn [_] {:created-time ["wtf?"]
                                               :created-date ["wtf?"]
                                               :style        ["wtf?"]
                                               :content      ["missing" "wrong"]})
                      :component-did-mount (fn [{:keys [set-values]}]
                                             (set-values (conj @data
                                                               {:style        "a"
                                                                :report-type  "e"
                                                                :preview      false
                                                                ;:content      "# This is it\n\nDoes it work?"
                                                                :created-time (str (t/truncate (t/time (t/instant (:created @data))) :minutes))
                                                                :created-date (str (t/date (t/instant (:created @data))))})))
                      :prevent-default?    true}
           test-func]]]
        [:div
         ;[l/ppre-x (map-difference initial-state (dissoc (:values @form-state) :style :created-time :created-date))]
         [:div.flex.justify-between.items-center.px-4.gap-4.h-12
          ;intent
          [bu/cta-button {:disabled (empty? (map-difference initial-state (dissoc (:values @form-state) :style :created-time :created-date)))
                          :on-click #(send :e.save {:id      id
                                                    ;todo Fill out more fields as we go
                                                    :content (-> @form-state :values :content)})} "Lagre"]
          [bu/regular-button {:on-click #(send :e.cancel)} "Avbryt"]]]]])))

;endregion

(defn rapport-side [{:keys [source-path]}]
  (let [{:keys [bg bg+ fg+]} (st/fbg' :void)
        *fsm-rapport (:rapport @(rf/subscribe [::rs/state :main-fsm]) :none)]
    [:div
     [:div.sticky.top-28]
     (rs/match-state *fsm-rapport
       [:s.initial]
       (if-let [data (get-content source-path)]
         (send :e.startup {:source-path source-path})
         (send :e.waiting {:source-path source-path}))

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

       :s.adding
       [editing-form source-path nil]

       :s.adding-at-front
       [editing-form source-path nil]

       [:s.editing']
       [editing-form source-path (-> (rf/subscribe [::rs/state-full :main-fsm]) deref :rapport :currently-editing-id)]

       [:s.editing]
       [editing-form source-path (-> (rf/subscribe [::rs/state-full :main-fsm]) deref :rapport :currently-editing-id)]

       [:s.some-content]
       (top-bottom-view
         (let [{:keys [fg bg bg-]} (st/fbg' :report)]
           (into [:div.flex.flex-col
                  {:class (concat [])}]
                 (for [[k {:keys [header style content created updated] :as v}]
                       (sort-by (comp :created val) > (remove (fn [[_ v]] (or (:deleted v) (:hidden v))) (get-content source-path)))]
                   #_[:div
                      {:class (concat bg fg [])}
                      [:div
                       #_[:div.flex.justify-between.items-center.h-12.gap-2
                          [:div.grow header]
                          [:div.flex.justify-between.w-24
                           (if updated
                             [:div (ta/time-format (t/time (t/instant updated)))])
                           (if created
                             [:div (ta/time-format (t/time (t/instant created)))])]
                          [:div.flex.gap-0
                           [bu/listitem-button-small {:on-click #(send :e.hide-entry k)} :eye]
                           [bu/listitem-button-small {:on-click #(send :e.delete k)} :cross-out]
                           [bu/listitem-button-small {:on-click #(send :e.edit k)} :edit-state]]]
                       [:div                                ;.relative.w-full
                        ;[:div.sticky.top-28.w-32.ml-auto.bg-black.text-white.xmt-8 "?"]
                        #_[:div.absolute.top-0.inset-x-0.ml-auto.mr-4x.w-32.bg-black.text-white]]]]

                   [:div.space-y-px (preview style created content)])))

         [:div.flex.justify-between.gap-4.px-4
          [:div (let [{:keys [fg- fg fg+ bg+ bg- bg p p+ p-]} (st/fbg' :surface)]
                  (schpaa.components.views/modern-checkbox'
                    {:set-details #(do
                                     (if %
                                       (send :e.start-managing)
                                       (send :e.end-managing))
                                     (schpaa.state/change :report/managing-mode %))
                     :get-details #(-> @(schpaa.state/listen :report/managing-mode))}
                    (fn [checkbox]
                      [:div.flex.items-center.justify-between.gap-4.w-full.h-12
                       checkbox
                       [:div.space-y-0
                        [:div {:class (concat fg p)} "Rediger innhold"]]])))]

          [bu/regular-button {:on-click #(send :e.add)} :plus]

          #_[bu/regular-button {:class    [:rounded]
                                :on-click #(send :e.start-managing)} "Rediger"]])

       [:s.manage-content]
       (let [show-deleted-posts (schpaa.state/listen :report/show-deleted-posts)
             sort-order (schpaa.state/listen :report/sort-order)
             data (if @show-deleted-posts
                    (get-content source-path)
                    (remove (fn [[k v]] (:deleted v)) (get-content source-path)))]
         (top-bottom-view
           (let [{:keys [fg- fg fg+ bg+ bg- bg p+ p-]} (st/fbg' :listitem)]
             [:div.space-y-4.flex.flex-col
              (into [:div.space-y-px.grow]
                    (concat
                      (for [[k {:keys [content created deleted updated hidden] :as v}]
                            (sort-by (case @sort-order
                                       1 (comp (juxt :updated :created) val)
                                       2 (comp (juxt :updated :created) val)
                                       (comp (juxt :created) val))
                                     (case @sort-order
                                       1 >
                                       2 <
                                       <) data)]
                        [:div.flex.justify-between
                         (nrpk.hov/open-details 1)
                         [:div.flex.grow.justify-between.items-center.gap-4.p-2.h-16.truncate
                          {:class (if (or hidden deleted) bg- bg)}
                          [:div.flex.flex-col.truncate
                           [:div.truncate
                            {:class (concat (if (or hidden deleted) fg- fg+) (if deleted [:line-through]))}
                            (str content)]
                           [:div {:class (concat fg- p-)} (str k)]]
                          ;[:div {:class p-} (when created (ta/short-time-format (t/time (t/instant created))))]
                          ;[:div {:class p-} (when updated (ta/short-date-format (t/date (t/instant updated))))]
                          [:div.flex.gap-0
                           (when-not deleted (if hidden
                                               [bu/listitem-button-small {:on-click #(send :e.show-entry {:id k :source-path source-path})} :eye]
                                               [bu/listitem-button-small {:on-click #(send :e.hide-entry {:id k :source-path source-path})} :eye-off]))
                           (if @show-deleted-posts
                             (if deleted
                               [bu/listitem-button-small {:on-click #(send :e.undelete k)} :rotate-left]
                               [bu/listitem-button-danger-small {:on-click #(send :e.delete k)} :cross-out])
                             [bu/listitem-button-danger-small {:on-click #(send :e.delete k)} :cross-out])

                           [bu/listitem-button-small {:on-click #(send :e.edit k)} :edit-state]]]])
                      (let [{:keys [fg- fg p-]} (st/fbg' :void)]
                        [[:div.flex.flex-center.h-16 {:class (concat p- fg)} "Antall poster " (count data)]
                         [:div.pb-8]])))])

           (let [{:keys [fg- fg fg+ bg+ bg- bg p p+ p-]} (st/fbg' :surface)]
             [:div.px-4
              (schpaa.components.views/modern-selectbox'
                {:set-details #(schpaa.state/change :report/sort-order %)
                 :get-details #(-> @sort-order)}
                (fn [checkbox]
                  [:div.flex.items-center.justify-start.gap-4.w-full.h-12
                   checkbox
                   [:div.space-y-0
                    [:div {:class (concat fg p)} (case @sort-order
                                                   1 "Siste oppdaterte er nederst"
                                                   0 "Siste opprettede er nederst"
                                                   "Siste oppdaterte er øverst")]]]))
              (schpaa.components.views/modern-checkbox'
                {:set-details #(schpaa.state/change :report/show-deleted-posts %)
                 :get-details #(-> @show-deleted-posts)}
                (fn [checkbox]
                  [:div.flex.items-center.justify-start.gap-4.w-full.h-12
                   checkbox
                   [:div.space-y-0.text-right
                    [:div {:class (concat fg p)} "Vis poster som er slettet"]]]))

              [:div.flex.justify-between.gap-2
               (schpaa.components.views/modern-checkbox'
                 {:set-details #(do
                                  (if %
                                    (send :e.start-managing)
                                    (send :e.end-managing))
                                  (schpaa.state/change :report/managing-mode %))
                  :get-details #(-> @(schpaa.state/listen :report/managing-mode))}
                 (fn [checkbox]
                   [:div.flex.items-center.justify-between.gap-4.w-full.h-12
                    checkbox
                    [:div.space-y-0
                     [:div {:class (concat fg p)} "Rediger innhold"]]]))
               [bu/regular-button {:on-click #(send :e.add)} :plus]
               #_[bu/regular-button {:on-click #(send :e.end-managing)} "Avslutt"]]])))

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
