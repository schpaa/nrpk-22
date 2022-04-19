(ns booking.utlan
  (:require [tick.core :as t]
            [shadow.resource :refer [inline]]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [schpaa.style.dialog :as dlg :refer [open-modal-boatinfo]]
            [re-frame.core :as rf]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.database]
            [booking.routes :refer [shortcut-link]]
            [booking.ico :as ico]
            [logg.database]
            [reagent.core :as r]
            [db.core :as db]
            [reitit.core :as reitit]
            [clojure.set :as set]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.common-widgets :as widgets]))

;region
;(logg.database/boat-db)

(defonce data (r/atom {:utlån []}))

(defn- util:prep [{:keys [boats] :as m}]
  (assoc m :boats (mapv booking.database/fetch-id-from-number- boats)))

(defn prepare []
  (swap! data update :utlån (comp vec concat)
         (mapv util:prep [{:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [484 481 195 601 702 140 146 155 433 707]}
                          {:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [484 481]}
                          {:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [477]}])))

(comment
  (do
    (reset! data {:utlån []})

    (swap! data update :utlån (comp vec concat)
           (mapv util:prep [{:id    (str (random-uuid))
                             :date  (t/at (t/today) (t/time "03:45"))
                             :boats [484 481 195 601 702 140 146 155 433 707]}
                            {:id    (str (random-uuid))
                             :date  (t/at (t/today) (t/time "03:45"))
                             :boats [484 481]}]))))

#_(defonce data
           (r/atom {:utlån   (map (fn [{:keys [boats] :as m}]
                                    (assoc m :boats (mapv booking.database/fetch-id-from-number- boats)))
                                  [{:id          (str (random-uuid))
                                    :date        (t/at (t/today) (t/time "03:45"))
                                    :in-progress true
                                    :boats       [484 481 195 601 702 140 146 155 433 707]}
                                   {:id          (str (random-uuid))
                                    :date        (t/at (t/date "2022-04-03") (t/noon))
                                    :in-progress true
                                    :boats       [495 491 428]}
                                   {:id    (str (random-uuid))
                                    :date  (t/at (t/date "2022-04-04") (t/noon))
                                    :boats [496 492]}])
                    :booking [{:id          (str (random-uuid))
                               :date-out    (t/at (t/date "2022-04-02") (t/noon))
                               :date-in     (t/at (t/date "2022-04-03") (t/noon))
                               :in-progress true
                               :boats       [123]}
                              {:id          (str (random-uuid))
                               :date-out    (t/at (t/date "2022-05-03") (t/noon))
                               :date-in     (t/at (t/date "2022-05-04") (t/noon))
                               :in-progress true
                               :boats       [123 124]}]}))

;endregion

(comment
  (softlistwrapper
    {:data  (:utlån data)
     :items (fn [{:keys [date]}]
              [[sc/link {:on-click #(tap> "inn")} "Innlever"]
               (into [:<>]
                     (->> (remove nil? boats)
                          (mapv (fn [id]
                                  (let [data (booking.database/fetch-boatdata-for id)
                                        number (:number data)]
                                    (sc/badge-2 {:on-click #(schpaa.style.dialog/open-modal-boatinfo data)} number))))))
               [:div.inline-block date]])}))

;region innlevering

(defn disp [{:keys [data on-close on-save]}]
  (let [{:keys [initial original plain timestamp]} data
        innlevert (into #{} (keys original))
        db (rf/subscribe [:db/boat-db])
        _id->number #(->> % (get @db) :number)]
    (r/with-let [st2 (r/atom initial)
                 st (r/atom initial)]
      [sc/dropdown-dialog
       [sc/col-space-8
        [sc/col-space-4
         (let [f (fn [[k v] st original]
                   (let [{:keys [number kind navn]} (get @db k)]
                     (schpaa.style.hoc.toggles/largeswitch-local
                       {:atoma   (r/cursor st [k])
                        :view-fn (fn [t c] [:div {:style {:gap             "var(--size-2)"
                                                          :display         :flex
                                                          :align-items     :center
                                                          :justify-content :between
                                                          :width           "100%"}}
                                            t
                                            [sc/badge-2 {:class [:big (when-not v :in-use)]} number]
                                            [sc/col {:style {:flex "1"}}
                                             [sc/text1 (schpaa.components.views/normalize-kind kind)]
                                             [sc/small1 navn]
                                             (if @(r/cursor st [k])
                                               (if (some #{k} innlevert)
                                                 (if-let [time (some->> k (get original) (t/instant) (t/date-time))]
                                                   [sc/text1 "Sjekket ut " (booking.flextime/relative-time time times.api/arrival-date)])
                                                 [sc/text1 "I ferd med å sjekke ut nå"])
                                               (when-let [time (some->> timestamp (t/instant) (t/date-time))]
                                                 [sc/text1 "Sjekket inn " (booking.flextime/relative-time time times.api/arrival-date)]))]])})))]
           (map #(f % st original) @st))]
        [sc/row-ec
         [:div.grow]
         [hoc.buttons/regular {:on-click on-close} "Avbryt"]
         [hoc.buttons/danger {:disabled (= initial @st)
                              #_(not (some true? (vals @st)))
                              :on-click #(on-save @st)} "Bekreft"]]]]
      (finally))))

(rf/reg-fx :rent/innlever-fx (fn [data]
                               ;(js/alert data)
                               #_{:original {:-MeAT-V3h7pZiV0_18_R "2022-04-16T19:23:28.171Z"},
                                  :boats    {:-MeAExJR02ZiHQ8T1ZU- true, :-MeAHLYBCltT8Epkv8TC true, :-MeAT-V3h7pZiV0_18_R true},
                                  :k        :-N-np7VVTe1e2RHR6P8d}
                               (let [k (:k data)
                                     boats (:boats data)
                                     value (reduce (fn [a [k v]] (assoc a k (if (true? v) (str (t/now)) ""))) {} boats)]
                                 #_(js/alert value)
                                 (db.core/database-update
                                   {:path  ["activity-22" (name k) "list"]
                                    :value value}))))

(comment
  (do
    (reduce (fn [a [k v]] (assoc a k (cond
                                       (true? v) (str (t/now))
                                       :else v)))
            {:-MeAT-V3h7pZiV0_18_R "2022-04-16T19:23:28.171Z"}
            {:-MeAExJR02ZiHQ8T1ZU- false, :-MeAHLYBCltT8Epkv8TC "false", :-MeAT-V3h7pZiV0_18_R false})))

(rf/reg-event-fx :rent/innlever (fn [_ [_ data]]
                                  {:fx [[:rent/innlever-fx data]]}))

(defn innlevering
  "presents a window with a list of all entries, all selected by default and
  a confirmation or cancel to click. Each entry can be deselected to avoid ..."
  [data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data       {:k         (:k data)
                              :timestamp (:timestamp data)
                              :plain     (:boats data)
                              :initial   (reduce-kv (fn [a k v] (assoc a k (if (empty? v) false true))) {} (:boats data))
                              :original  (into {} (remove (comp empty? val) (:boats data)))}
                 :action     #(rf/dispatch [:rent/innlever {:original (into {} (remove (comp empty? val) (:boats data)))
                                                            :boats    (:carry %)
                                                            :k        (:k data)}]) #_#(tap> {"action (carry)" (:carry %)
                                                                                             "k"              (:k data)})
                 ;called after the dialog has closed and after `action` is called (if defined)
                 ;todo Rename to `after-close`, action above is the primary
                 ;:on-primary-action #(tap> {"on-primary-action" %})
                 ;:on-close   #(tap> "on-close")
                 ;placed in function to allow displaying changes when hot-reloaded
                 :content-fn #(disp %)}]))

(comment
  (do
    (let [data {:a false :b false :c false}
          st (atom data)]
      #_(swap! st #(reduce (fn [a [k v]] (update a k (fnil not false))) % %))
      ;(not-any? true? (vals @st))
      (some true? (vec (vals @st))))))

;endregion

(defonce settings (r/atom {}))

(o/defstyled round-normal-listitem :div
  [:& :h-8 :w-8 :cursor-pointer :-mb-2
   :justify-self-center
   :items-center :justify-center
   {:display       :inline-flex
    :outline       "1px solid red"
    ;:text-indent 0
    :aspect-ratio  "1/1"
    :border-radius "var(--radius-round)"
    ;:background    "var(--surface000)"
    ;:border        "1px solid var(--surface1)"
    :color         "var(--surface4)"}]
  [:&:active {:background "var(--surface1)"}]
  [:&:hover {:background "white" #_"var(--surface000)"}]
  [:&.deleted {:background "red"}])

(defn trashcan [k {:keys [deleted] :as v}]
  [hoc.buttons/reg-icon
   {:class    (if deleted [:regular] [:danger])
    :on-click #(db/database-update
                 {:path  ["activity-22" (name k)]
                  :value {:deleted (not deleted)}})}
   (if deleted (icon/adapt :rotate-left) ico/trash)

   #_[round-normal-listitem
      {:class    [(if deleted :deleted)]
       :on-click #(db/database-update
                    {:path  ["activity-22" (name k)]
                     :value {:deleted (not deleted)}})}
      [sc/icon-small
       {:style {:color "var(--text1)"}}
       (if deleted (icon/small :rotate-left) ico/trash)]]])

(o/defstyled inline-item :div
  [:&
   :inline-flex
   :items-center
   :justify-center

   ;:gap-4
   {:height         "4rem"

    ;:max-height     "4rem"
    ;:outline        "1px solid yellow"
    :xmargin-right  "0.5rem"
    :xmargin-bottom "0.5rem"}])


(o/defstyled command-grid :div
  {:display               :grid
   ;:background            "rgba(0,0,0,0.05)"
   :column-gap            "var(--size-3)"
   :row-gap               "var(--size-2)"
   :grid-template-columns "min-content 1fr"
   :grid-template-areas   [["." "time"]
                           ["edit" "content"]]})

(o/defstyled listitem' :div
  [:& :gap-2
   {:display     :flex
    :flex-wrap   :wrap
    :align-items :start
    :color       "var(--text1)"}
   [:&.deleted {:color           "var(--text0)"
                :text-decoration :line-through
                :opacity         0.3}]])

(rf/reg-sub :rent/common-show-deleted
            (fn [_]
              (and @(schpaa.state/listen :r.utlan)
                   @(r/cursor settings [:rent/show-deleted]))))

(rf/reg-sub :rent/common-edit-mode
            (fn [db]
              (and @(schpaa.state/listen :r.utlan)
                   #_@(r/cursor settings [:rent/edit]))))

(defn user-alias [this-uid]
  (let [alias (user.database/lookup-alias (name this-uid))]
    (if (empty? alias)
      (let [name (user.database/lookup-username (name this-uid))]
        (if (empty? name)
          this-uid
          name))
      alias #_"uten alias")))

(defn render [loggedin-uid]
  (when-let [db @(rf/subscribe [:db/boat-db])]
    (let [admin? (rf/subscribe [:lab/admin-access])
          show-deleted? @(rf/subscribe [:rent/common-show-deleted])
          edit-mode? @(rf/subscribe [:rent/common-edit-mode])
          data (rf/subscribe [:rent/list])
          lookup-id->number (into {} (->> (remove (comp empty? :number val) db)
                                          (map (juxt key (comp :number val)))))]
      [sc/col-space-8
       (into [:div {:style {:gap            "var(--size-2)"
                            :display        :flex
                            :flex-direction :column}}]
             (doall (for [[k {:keys [uid timestamp list id deleted] :as m}] (if show-deleted?
                                                                              @data
                                                                              (remove (comp :deleted val) @data))
                          :let [boats list
                                date (t/date-time (t/instant timestamp))
                                ddate (booking.flextime/relative-time date times.api/arrival-date)]]

                      [command-grid
                       [:div.w-full
                        {:class []
                         :style {:grid-area       "time"
                                 :display         :flex
                                 :justify-content :between}}
                        [sc/subtext {:class [:truncate]} [:div.truncate.w-32 (user-alias uid)]]
                        [:div.grow]
                        [:div ddate]]
                       [:div {:class [:h-8 :pt-px :flex :items-center :gap-3]
                              :style {:grid-area "edit"
                                      :display   :flex}}
                        (when edit-mode?
                          [widgets/trashcan (fn [_] (db/database-update
                                                      {:path  ["activity-22" (name k)]
                                                       :value {:deleted (not deleted)}})) m])
                        (if edit-mode?
                          [widgets/edit {:disabled (not goog.DEBUG)} #(rf/dispatch [:lab/toggle-boatpanel]) m]
                          #_[hoc.buttons/reg-icon {:class    [:regular]
                                                   :disabled (not goog.DEBUG)
                                                   :on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/pencil]
                          (if (= uid loggedin-uid)
                            [hoc.buttons/cta-pill {:class    [:narrow]
                                                   :on-click #(innlevering {:k         k
                                                                            :timestamp timestamp
                                                                            :boats     boats})} "Inn"]
                            [hoc.buttons/reg-pill {:class    [:narrow]
                                                   :on-click #(innlevering {:k         k
                                                                            :timestamp timestamp
                                                                            :boats     boats})} "Inn"]))]
                       (into [listitem' {:class [(if deleted :deleted)]
                                         :style {:opacity   (if deleted 0.2 1)
                                                 :grid-area "content"}}]
                             (map (fn [[id returned]]
                                    (let [returned (not (empty? returned))
                                          number (get lookup-id->number (keyword id) (str "?" id))]
                                      (sc/badge-2 {:class    [(if-not returned :in-use)]
                                                   :on-click #(dlg/open-modal-boatinfo
                                                                {:uid  loggedin-uid
                                                                 :data (get db (keyword id))})} number)))
                                  (remove nil? boats)))])))]

      #_(when @(rf/subscribe [:lab/booking])
          [sc/col-space-8
           [sc/col
            (-> (inline "./oversikt/sjøbasen.md") schpaa.markdown/md->html sc/markdown)
            [sc/row-sc-g4-w
             [sc/link {:on-click #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])} "Aktivitet i dag og framover"]
             [sc/link {:on-click #(rf/dispatch []) :class [:disabled]} "Båtoversikt"]
             [sc/link {:on-click #(rf/dispatch [])} "Ny booking"]
             [sc/link {:on-click #(rf/dispatch []) :class [:disabled]} "Mine bookinger"]]]
           [:div (for [e (:booking @data)]
                   [l/ppre-x e])]]))))

;(defonce _ (rf/subscribe [:r.utlan]))

(defn panel [{:keys [on-close]}]
  [sc/col-space-8
   [sc/row-sc-g1 {:style {:flex-wrap :wrap}}
    ;[hoc.toggles/switch-local (r/cursor settings [:rent/edit]) "rediger"]
    [hoc.toggles/switch-local (r/cursor settings [:rent/show-deleted]) "vis slettede"]]])

(defn commands []
  (let [data (rf/subscribe [:rent/list])]

    [sc/row-sc-g2

     [hoc.buttons/cta-pill-icon {:on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/plus "Nytt utlån"]
     [hoc.buttons/reg-pill {:disabled true
                            :on-click #()} "Båtoversikt"]
     [hoc.buttons/reg-pill {:disabled true
                            :on-click #()} "Mine utlån"]
     ;[shortcut-link :r.booking]
     #_[hoc.buttons/reg-pill {:on-click prepare} "Prep!"]
     #_[sc/link {:on-click #(rf/dispatch [:lab/toggle-boatpanel])} "Nytt utlån"]]
    #_(l/ppre-x @data)))