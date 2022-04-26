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
                                                   [sc/text1 "Innlevert " (booking.flextime/relative-time time times.api/arrival-date)])
                                                 [sc/text1 "I ferd med å lever inn nå"])
                                               (when-let [time (some->> timestamp (t/instant) (t/date-time))]
                                                 [sc/text1 "Tatt ut " (booking.flextime/relative-time time times.api/arrival-date)]))]])})))]
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

(defonce settings
         (r/atom {:rent/show-details    false
                  :rent/show-timegraph  false
                  :rent/graph-view-mode 0
                  :rent/show-deleted    false}))

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
  [:&
   {:display               :grid
    :border-radius         "var(--radius-1)"
    :padding-inline        "var(--size-2)"
    :margin-inline         "calc(-1 * var(--size-2))"
    :column-gap            "var(--size-3)"
    :row-gap               "0"
    :grid-template-columns "min-content 48px 1fr"
    :grid-template-areas   [["." "." "details"]
                            ["edit" "badges" "content"]]}
   [:&.test {:background-color "rgba(242, 121, 53, 0.4)"}]

   [:&:hover
    {:background "rgba(0,0,0,0.05)"}]])

(o/defstyled listitem' :div
  [:& :gap-2
   {:display     :flex
    :flex-wrap   :wrap
    :align-items :start
    :color       "var(--text1) "}
   [:&.deleted {:color           "var(--text0) "
                :text-decoration :line-through
                :opacity         0.3}]])

(rf/reg-sub :rent/common-show-deleted
            (fn [_]
              (and @(schpaa.state/listen :r.utlan)
                   @(r/cursor settings [:rent/show-deleted]))))

(rf/reg-sub :rent/common-show-details
            (fn [_]
              (and                                          ;@(schpaa.state/listen :r.utlan)
                @(r/cursor settings [:rent/show-details]))))

(rf/reg-sub :rent/common-show-timegraph
            (fn [_]
              (and                                          ;@(schpaa.state/listen :r.utlan)
                @(r/cursor settings [:rent/show-timegraph]))))

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
      alias #_" uten alias ")))

(defn timegraph [from arrived-datetime to now]
  (let [session-start (* 4 18)
        session-end (* 4 21)
        now (+ (* 4 (t/hour now)) (quot (t/minute now) 15))
        start (if (and
                    (seq arrived-datetime)
                    (t/< (t/date from) (t/date (t/instant arrived-datetime))))
                0
                (+ (* 4 (t/hour from)) (quot (t/minute from) 15)))
        end (if-let [to' arrived-datetime #_(first (vals to))]
              (if-not (empty? to')
                (if-let [x (some-> to' t/instant t/date-time)]
                  (+ (* 4 (t/hour x)) (quot (t/minute x) 15)))
                nil)
              nil)
        end (if (< now end) 10 end)
        field-color "var(--floating)"
        session-color "var(--pink-3)"
        time-color "var(--yellow-5)"
        timeline-color "var(--text2)"
        color (if (nil? end) "var(--blue-8)" "var(--text1) ")]

    [:div.h-8 {:on-click #(swap!
                            settings update :rent/graph-view-mode (fn [e] (mod (inc e) 2)))
               :style    {:overflow         :clip
                          :box-shadow       "var(--shadow-3),var(--shadow-2)"
                          :border-radius    "var(--radius-0)"
                          :cursor           :pointer
                          :background-color field-color}}
     (let [v-start (case @(r/cursor settings [:rent/graph-view-mode])
                     0 (* 4 0)
                     1 (- session-start 4))
           v-end (case @(r/cursor settings [:rent/graph-view-mode])
                   0 96
                   1 (+ session-end 4))

           arrow-length (/ (- v-end v-start) 15)]
       [:svg
        {:viewBox             (l/strp v-start 0 (- v-end v-start) 8)
         :height              "100%"
         :width               "100%"
         :preserveAspectRatio "none"}
        [:rect {:fill    session-color
                :x       session-start
                :width   (- session-end session-start)
                :opacity 0.5
                :y       "0"
                :height  "8"}]
        (when (nil? end)
          [:path {:fill           color
                  :stroke-linecap :round
                  :d              (l/strp "M" now 2 "l" arrow-length 2 "l" (- arrow-length) 2 "z")}])

        (when true
          ;(< start now)
          [:path {:stroke         color
                  ;:vector-effect  :non-scaling-stroke
                  :stroke-width   (if (< 1 (count to)) 2 1)
                  :stroke-linecap :round
                  :d              (l/strp "M" start 4 "l" (- (if (< start end) end now) start) 0)}])
        [:path
         {:stroke  "none"
          :opacity 0.5
          :fill    time-color
          :d       (l/strp "M" 0 0 "L" (- now 1) 0 "l" 3 "8" "H" 0 "z")}]

        [:line {:x2                96
                :x1                0
                :y2                "8"
                :y1                "8"
                :opacity           1
                :stroke-dashoffset "0"
                :stroke-dasharray  "4 4"
                :stroke-width      1
                :stroke            timeline-color}]])]))

(defn- badges [{:keys [sleepover havekey] :as m}]
  [sc/row-ec-g1 {:style {:grid-area "badges"
                         :flex-wrap :nowrap}
                 :class [:h-12x :place-content-center]}
   (when sleepover [sc/icon-tiny-frame ico/moon])
   (when havekey [sc/icon-tiny-frame ico/nokkelvakt])])

(defn- edit [loggedin-uid edit-mode? k {:keys [uid deleted timestamp list] :as m}]
  [:div {:class [:h-12x :flex :items-center :gap-2]
         :style {:grid-area "edit"
                 :display   :flex}}
   (when edit-mode?
     [widgets/trashcan (fn [_] (db/database-update
                                 {:path  ["activity-22" (name k)]
                                  :value {:deleted (not deleted)}})) m])
   (if edit-mode?
     [widgets/edit {:disabled false} #(rf/dispatch [:lab/toggle-boatpanel]) m])
   (if (= uid loggedin-uid)
     [hoc.buttons/cta-pill {:class    [:narrow]
                            :on-click #(innlevering {:k         k
                                                     :timestamp timestamp
                                                     :boats     list})} "Inn"]
     [hoc.buttons/reg-pill {:class    [:narrow]
                            :on-click #(innlevering {:k         k
                                                     :timestamp timestamp
                                                     :boats     list})} "Inn"])])

(defn render [loggedin-uid]
  (when-let [db @(rf/subscribe [:db/boat-db])]
    (let [admin? (rf/subscribe [:lab/admin-access])
          show-deleted? @(rf/subscribe [:rent/common-show-deleted])
          show-timegraph? @(rf/subscribe [:rent/common-show-timegraph])
          show-details? @(rf/subscribe [:rent/common-show-details])
          edit-mode? @(rf/subscribe [:rent/common-edit-mode])
          data (rf/subscribe [:rent/list])
          lookup-id->number (into {} (->> (remove (comp empty? :number val) db)
                                          (map (juxt key (comp :number val)))))]
      [sc/col-space-8
       (into [sc/col-space-1]
             (for [[k {:keys [test uid adults children havekey juveniles sleepover timestamp list deleted nøkkel] :as m}]
                   (if show-deleted?
                     @data (remove (comp :deleted val) @data))
                   :let [boats list
                         date (t/date-time (t/instant timestamp))]]
               [command-grid
                {:class [(when test :test)]}
                [:div.w-full.truncate
                 {:class [:whitespace-nowrap :gap-1]
                  :style {:color           "var(--text1) "
                          :grid-area       " time "
                          :display         :flex
                          :justify-content :between}}]
                [badges m]
                (when show-details?
                  [sc/row-sc-g4-w {:style {:grid-area "details"}
                                   :class [:pt-1]}
                   [sc/text2 {:style {:font-weight "var(--font-weight-5)"}
                              :class [:tabular-nums]}
                    (if (pos? adults) adults "–")
                    "/"
                    (if (pos? juveniles) juveniles "–")
                    "/"
                    (if (pos? children) children "–")]
                   [sc/text2 {:class []} (user.database/lookup-alias uid)]])
                [edit loggedin-uid @(rf/subscribe [:rent/common-edit-mode]) k m]
                (into [listitem' {:class [(if deleted :deleted)
                                          :w-full :py-2]
                                  :style {:flex            "1 0 auto"
                                          :justify-content :between
                                          :align-items     :center
                                          :opacity         (if deleted 0.2 1)
                                          :grid-area       "content"}}]
                      (let [arrived-datetime (first (sort < (vals boats)))]
                        (concat
                          [(if (and show-timegraph?
                                    (or (t/= (t/today) (t/date date))
                                        (and (seq arrived-datetime)
                                             (t/= (t/today) (t/date (t/instant arrived-datetime))))
                                        (and
                                          (t/< (t/<< (t/today) (t/new-period 2 :days)) (t/date date))
                                          (nil? arrived-datetime))))
                             [:div.w-56 [timegraph date arrived-datetime boats (t/date-time)]]
                             [sc/col {:class [:tabular-nums :w-44]}
                              [sc/row-fields
                               [sc/text1 {:class [:tracking-tight]} (times.api/logg-date-format date)]
                               [:div.grow]
                               [sc/text1 {:class [:tracking-tight]} "kl. " (times.api/time-format date)]]
                              (when (seq arrived-datetime)
                                (let [dt (t/instant arrived-datetime)]
                                  [sc/row-fields
                                   (when-not (t/= (t/date dt) (t/date date))
                                     [sc/text1 {:class [:tracking-tight]
                                                :style {:white-space :nowrap}} (times.api/logg-date-format dt)])
                                   [:div.grow]
                                   [sc/text1 {:class [:tracking-tight :w-full :h-auto :self-center :text-right]}
                                    (if (seq arrived-datetime)
                                      (str "kl. " (times.api/time-format (t/time dt)))
                                      (str arrived-datetime))]]))])]
                          [(map (fn [[id returned]]
                                  (let [returned (not (empty? returned))
                                        number (get lookup-id->number (keyword id) (str " ? " id))]
                                    (sc/badge-2 {:class    [:big (if-not returned :in-use)]
                                                 :on-click #(dlg/open-modal-boatinfo
                                                              {:uid  loggedin-uid
                                                               :data (get db (keyword id))})} number)))
                                (remove nil? boats))])))]))])))

(defn panel [{:keys []}]
  [sc/row-sc-g4-w {:style {:flex-wrap :wrap}}

   [sc/row-sc-g4-w
    ;[sc/text1 "Se også"]
    [widgets/auto-link :r.båtliste]
    [sc/link {:href (kee-frame.core/path-for [:r.dokumenter {:id "tidslinje-forklaring"}])} "Ofte stilte spørsmål"]
    [widgets/auto-link :r.booking]]

   [sc/row-sc-g2-w
    [hoc.toggles/switch-local {:disabled true} (r/cursor settings [:rent/show-details]) "Kompakt"]
    [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-details]) "Detaljer"]
    [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-timegraph]) "Tidslinje"]]

   [hoc.toggles/switch-local (r/cursor settings [:rent/show-deleted]) "vis slettede"]])

(defn commands []
  [sc/col-space-4
   [sc/row-sc-g2-w
    [hoc.buttons/cta-pill-icon {:on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/plus "Nytt utlån"]
    [hoc.buttons/danger-pill {:disabled true
                              :on-click #(rf/dispatch [:lab/just-create-new-blog-entry])} "HMS Hendelse"]]])



