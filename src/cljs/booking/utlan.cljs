(ns booking.utlan
  (:require [tick.core :as t]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [schpaa.icon :as icon]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]
            [booking.common-widgets :as widgets]
            [booking.database]
            [booking.ico :as ico]
            [logg.database]
            [db.core :as db]
            [schpaa.style.hoc.buttons :as button]))

;; store

(def store (r/atom {:selector :a}))

(def selector (r/cursor store [:selector]))

;region innlevering

(defn view-fn [{:keys [navn number kind slot] :as m} [[k v] st original] timestamp t]
  (let [innlevert (into #{} (keys original))]
    [:div {:style {:gap             "var(--size-2)"
                   :display         :flex
                   :align-items     :center
                   :justify-content :between
                   :width           "100%"}}
     t
     [widgets/badge
      {:class [:big (when-not v :in-use)]} number slot]
     [sc/co-field {:style {:flex "1"}}
      [sc/text2 navn]
      [sc/title1 (schpaa.components.views/normalize-kind kind)]

      (if @(r/cursor st [k])
        (if (some #{k} innlevert)
          (if-let [time (some->> k (get original) (t/instant) (t/date-time))]
            [sc/text1 "Innlevert " (booking.flextime/relative-time time times.api/arrival-date)])
          [sc/text1 "Innleveres nå"])
        (when-let [time (some->> timestamp (t/instant) (t/date-time))]
          [sc/text1 "Tatt ut " (booking.flextime/relative-time time times.api/arrival-date)]))]]))

(defn disp [{:keys [data on-close on-save]}]
  (let [{:keys [initial original timestamp]} data
        db (rf/subscribe [:db/boat-db])]
    (r/with-let [st (r/atom initial)]
      [sc/dropdown-dialog'
       [sc/col-space-8 {:class [:pt-8]}
        [sc/col-space-4
         (let [f (fn [[k v]]
                   (let [m (get @db k)
                         args [[k v] st original]]
                     (schpaa.style.hoc.toggles/largeswitch-local
                       {:atoma   (r/cursor st [k])
                        :view-fn (fn [t c] (view-fn m args timestamp t))})))]
           (map f @st))]
        [sc/row-ec {:class [:pb-6]}
         [:div.grow]
         [hoc.buttons/regular {:on-click on-close} "Avbryt"]
         [hoc.buttons/danger {:disabled (= initial @st)
                              #_(not (some true? (vals @st)))
                              :on-click #(on-save @st)} "Bekreft"]]]]
      (finally))))

(rf/reg-fx :rent/innlever-fx
           (fn [data]
             (let [k (:k data)
                   boats (:boats data)
                   path ["activity-22" (name k) "list"]
                   value (reduce (fn [a [k v]] (assoc a k (if (true? v) (str (t/now)) "")))
                                 {} boats)]
               (db.core/database-update
                 {:path  path
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
  (rf/dispatch
    [:modal.slideout/toggle
     true
     {:data       {:k         (:k data)
                   :timestamp (:timestamp data)
                   :all       data
                   :plain     (:boats data)
                   :initial   (reduce-kv (fn [a k v] (assoc a k (if (empty? v) false true))) {} (:boats data))
                   :original  (into {} (remove (comp empty? val) (:boats data)))}
      :action     #(rf/dispatch [:rent/innlever
                                 {:original (into {}
                                                  (remove (comp empty? val) (:boats data)))
                                  :boats    (:carry %)
                                  :k        (:k data)}])
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

;; machinery

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

(o/defstyled logg-listitem-overview :div
  [:&
   {;:outline               "1px solid red"
    :display               :grid
    :align-items           :center
    ;:border-radius         "var(--radius-1)"
    :column-gap            "var(--size-2)"
    :row-gap               "var(--size-1)"
    :grid-template-columns "min-content min-content 1fr 1fr"
    :grid-auto-rows        "auto"
    :grid-template-areas   [["time edit content graph"]]}
   #_[:&:hover
      {:background "rgba(0,0,0,0.05)"}]])

(o/defstyled logg-listitem-grid :div
  [:& :py-1
   {:display               :grid
    :column-gap            "var(--size-3)"
    :row-gap               "var(--size-2)"
    :grid-template-columns "5rem min-content min-content 1fr 1fr"
    :grid-auto-rows        "min-content"
    :align-items           :start
    :grid-template-areas   [["start-date edit content content content"]
                            ["badges start-time graph graph details "]]}])




(o/defstyled logg-listitem :div
  [:& :gap-2
   {:display     :flex
    :height      "auto"
    :flex-wrap   :wrap
    :align-items :start
    :color       "var(--text1) "}
   [:&.deleted {:color           "var(--text0) "
                :text-decoration :line-through
                :opacity         0.1}]])

;; ui toggles

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

;; components

(defn timegraph [start end to now]
  (let [session-start (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 11) (* 4 18))
        session-end (if (some #{(t/int (t/day-of-week start))} [6 7]) (* 4 17) (* 4 21))
        now (+ (* 4 (t/hour now))
               (quot (t/minute now) 15))
        start (if (and end
                       (t/< (t/date start) (t/date end)))
                0
                (+ (* 4 (t/hour start)) (quot (t/minute start) 15)))
        end (if-let [to' end]
              (if-let [x (some-> to' t/instant t/date-time)]
                (+ (* 4 (t/hour x)) (quot (t/minute x) 15)))
              nil)
        end (if (< now end) 10 end)
        field-color "var(--floating)"
        session-color "var(--selected)"
        time-color "var(--yellow-5)"
        timeline-color "var(--text3)"
        color (if (nil? end) "var(--blue-8)" "var(--text1) ")]

    [:div.h-8 {:on-click #(swap!
                            settings update :rent/graph-view-mode (fn [e] (mod (inc e) 2)))
               :style    {:overflow         :clip
                          :box-shadow       "var(--shadow-1)"
                          :border-radius    "var(--radius-1)"
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
        (when false
          [:path
           {:stroke  "none"
            :opacity 0.5
            :fill    time-color
            :d       (l/strp "M" 0 0 "L" (- now 1) 0 "l" 3 "8" "H" 0 "z")}])

        [:line {:x2                96
                :x1                0
                :y2                "8"
                :y1                "8"
                :opacity           1
                :stroke-dashoffset "0"
                :stroke-dasharray  "4 4"
                :stroke-width      1
                :stroke            timeline-color}]])]))

(defn- badges [deleted-item {:keys [deleted sleepover havekey] :as m}]
  ;note: Deleted-item is a partially applied sc/deleted-item
  [deleted-item
   [sc/row-sc-g2 {:style {:flex-wrap :nowrap}}
    (when havekey [sc/icon-tiny-frame ico/harnøkkel])
    (when sleepover [sc/icon-tiny-frame ico/moon-filled])]])

(defn- edit-bar [loggedin-uid edit-mode? k {:keys [uid deleted timestamp list] :as m}
                 all-returned?]
  [sc/row-sc-g2
   (when edit-mode?
     [widgets/trashcan'
      {:deleted? deleted
       :on-click (fn [] (db/database-update
                          {:path  ["activity-22" (name k)]
                           :value {:deleted (not deleted)}}))}
      (if deleted "Angre" "Slett")])

   (when-not deleted
     [hoc.buttons/reg-pill-icon
      {:class    [:narrow
                  (if (= uid loggedin-uid)
                    (if all-returned? :regular :cta)
                    :regular)]
       :on-click #(innlevering {:k         k
                                :timestamp timestamp
                                :boats     list})}
      ico/status
      (if all-returned? "ut" "inn")])

   [widgets/edit {:disabled true} #(rf/dispatch [:lab/toggle-boatpanel [k m]]) m]])

(defn agegroups-detail [{:keys [adults children juveniles] :as m}]
  (letfn [(f [n]
            [sc/text1 {:style {:justify-self :center
                               :font-weight  "var(--font-weight-5)"
                               :white-space  :nowrap}
                       :class [:tabular-nums]}
             (if (pos? n) n "—")])]
    [:div (:style {:grid-area "agegroups"})
     [:div
      {:style {:display               "grid"
               :grid-template-columns "repeat(3,1.4rem)"}}
      (f adults)
      (f juveniles)
      (f children)]]))

(comment
  (let [dt "2022-05-10T09:32:24.084Z"
        dt nil]
    (some-> dt t/instant)))

(defn- preformat-dates [start end]
  (let [dt (some-> end t/instant)
        a-date (when dt
                 (if-not (t/= (t/date dt) (t/date start))
                   (times.api/logg-date-format dt)))
        a-time (when dt
                 (times.api/time-format
                   (t/time dt)))]

    {:start-date (times.api/logg-date-format start)
     :start-time (times.api/time-format start)
     :end-date   (when dt
                   (when-not (t/= (t/date dt) (t/date start))
                     (times.api/logg-date-format dt)))
     :end-time   (when dt
                   a-time)}))

(defn passed-date-test?
  "docstring"
  [date arrived-datetime]
  (or (t/= (t/today) (t/date date))
      (and
        (nil? arrived-datetime)
        (t/< (t/<< (t/today) (t/new-period 2 :days)) (t/date date)))
      (and arrived-datetime
           (t/= (t/today) (t/date arrived-datetime)))))

(defn- boat-badges [db deleted-item::style boats class]
  [:div {:class [:gap-2]
         :style {:flex            "1 0 auto"
                 :display         "flex"
                 :flex-wrap       :wrap
                 :justify-content "end"
                 :align-items     "center"
                 :grid-area       "content"}}
   (let [prepared
         (fn [[id returned]]
           (when-let [id (some-> id keyword)]
             (let [datum (get db id)
                   number (:number datum)]
               {:id       id
                :datum    datum
                :slot     (:slot datum)
                :returned (not (empty? (str returned)))
                :number   number})))

         badge-view-fn
         (fn [{:keys [id returned number datum slot]}]
           [deleted-item::style
            [widgets/badge
             {:on-click #(open-modal-boatinfo {:data (assoc datum :id id)})
              :class    [class
                         :small
                         :whitespace-nowrap
                         (when-not (:boat-type datum) :active)
                         (when-not returned :in-use)]}
             (or number (str (some-> id name)))
             slot]])]
     (map badge-view-fn (sort-by :number < (map prepared (remove nil? boats)))))])

(defn g-area [grid-area content]
  [:div {:style {:grid-area grid-area}} content])

;todo REFACTOR!
(defn render [loggedin-uid]
  (when-let [db @(rf/subscribe [:db/boat-db])]
    (let [nitty-gritty @(rf/subscribe [:rent/common-show-details])
          show-deleted? @(rf/subscribe [:rent/common-show-deleted])
          ;show-timegraph? (= :b @selector) #_(rf/subscribe [:rent/common-show-timegraph])
          show-details? (= :b @selector) #_@(rf/subscribe [:rent/common-show-details])
          show-overview? (= :c @selector)
          data (rf/subscribe [:rent/list])
          data (if show-deleted? @data (remove (comp :deleted val) @data))]
      [sc/co
       (into [:<>]
             (for [[k {:keys [timestamp list deleted ref-uid phone] :as m}] data
                   :let [boats list                         ;(sort-by (comp :number val) < list)
                         date (some-> timestamp t/instant t/date-time)
                         all-returned? (every? (comp not empty? val) boats)
                         ;what is the least convoluted way to write this?
                         end::time-instant (let [[dt _] (sort < (vals boats))]
                                             (when-not (empty? dt)
                                               (t/instant dt)))
                         deleted-item::style (partial sc/deleted-item {:class [(when deleted :deleted)]})]
                   :when (cond
                           (= :b @selector) (and (passed-date-test? date end::time-instant)
                                                 (not all-returned?))
                           show-overview? (t/= (t/today) (t/date date))
                           show-details? (not all-returned?)
                           :else true)]
               [sc/zebra {:class [:px-4]}
                (if show-overview?
                  [logg-listitem-overview
                   (let [{:keys [start-date end-date start-time end-time]}
                         (preformat-dates date end::time-instant)]
                     [g-area "time"
                      [sc/title1 {:class [:tracking-tight :tabular-nums]} start-time]])
                   [g-area "edit" [edit-bar loggedin-uid @(rf/subscribe [:rent/common-edit-mode]) k m all-returned?]]
                   (when (t/= (t/date date) (t/today))
                     [g-area "graph" [timegraph date end::time-instant boats (t/date-time)]])
                   [g-area "content" [boat-badges db deleted-item::style boats :small]]]

                  [logg-listitem-grid
                   (when nitty-gritty
                     (when (some #{@selector} [:a :b])
                       [g-area "badges" [sc/row-sc-g4
                                         [agegroups-detail m]]]))

                   [:div {:style {:display     "flex"
                                  :align-items "center"
                                  :grid-area   "edit"}}

                    [edit-bar loggedin-uid @(rf/subscribe [:rent/common-edit-mode]) k m all-returned?]]

                   (when nitty-gritty
                     (when (some #{@selector} [:a :b])
                       [sc/row-sc-g2 {:style {:align-items     "center"
                                              :justify-content :end
                                              :grid-area       "details"}}
                        (if-not ref-uid [sc/text1 phone] [widgets/user-link ref-uid])
                        [badges deleted-item::style m]]))

                   ;graph
                   (when nitty-gritty
                     (if (and (t/= (t/date date) (t/today)) (= :b @selector))
                       [g-area "graph" [timegraph date end::time-instant boats (t/date-time)]]))

                   [g-area "content" [boat-badges db deleted-item::style boats]]

                   (let [{:keys [start-date end-date start-time end-time]} (preformat-dates date end::time-instant)]
                     (for [[areaname value style] [["start-date" start-date {;:height :1rem
                                                                             :place-self   :center
                                                                             :salign-items :center}]
                                                   (when nitty-gritty
                                                     ["start-time" (str "kl. " start-time

                                                                        (if end-date
                                                                          (str (str "-->" end-date "")
                                                                               " kl. " end-time)
                                                                          (when end-time
                                                                            (str "—" end-time)))) {}])
                                                   #_["end-date" end-date {:justify-content :end}]
                                                   #_["end-time" end-time {:justify-content :end}]]]
                       [:div.flex
                        {:style (merge-with conj {:align-items :center
                                                  :display     :flex
                                                  :grid-area   areaname} style)}
                        [sc/title1 {:class [:xtracking-tight]} value]]))])]))])))

(defn panel [{:keys []}]
  [sc/row-sc-g4-w {:style {:flex-wrap :wrap}}

   [sc/row-sc-g4-w
    ;[sc/text1 "Se også"]
    [widgets/auto-link :r.båtliste.nøklevann]
    #_[sc/link {:href (kee-frame.core/path-for [:r.dokumenter {:id "tidslinje-forklaring"}])} "Ofte stilte spørsmål"]
    [widgets/auto-link :r.booking]]

   [sc/row-sc-g2-w
    [hoc.toggles/switch-local {:disabled true} (r/cursor settings [:rent/show-details]) "Kompakt"]
    [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-timegraph]) "Tidslinje"]]

   [hoc.toggles/switch-local (r/cursor settings [:rent/show-deleted]) "vis slettede"]])

(defn always-panel []
  [:<>
   #_[sc/row-center {:class  [:py-4]
                     :stylex {:background-color "var(--content)"}}
      [hoc.buttons/cta-pill-icon
       {:on-click #(rf/dispatch [:lab/toggle-boatpanel nil])}
       ico/plus "Nytt utlån"]
      [hoc.buttons/danger-pill
       {:disabled true
        :on-click #(rf/dispatch [:lab/just-create-new-blog-entry])}
       "HMS Hendelse"]]


   [sc/row-center {:class [:py-4 :sticky :top-20]}
    [sc/row-center
     [sc/col-space-4
      [widgets/pillbar
       selector
       [[:a "Alle"]
        [:b "Ute"]
        #_[:c "Nytt utlån"]]]
      [sc/row-center
       [hoc.buttons/round-pill
        {:class    [:large :xoutline :message]
         :on-click #(rf/dispatch [:lab/toggle-boatpanel nil])}
        [sc/icon-large ico/plus]
        #_"Nytt utlån"]]]]]])

(rf/reg-event-fx :lab/remove-boatlogg-database
                 (fn [_ _]
                   (db/database-set {:path  ["activity-22"]
                                     :value {}})
                   (db/database-set {:path  ["boad-item"]
                                     :value {}})
                   {}))

(defn headline-extension []
  [sc/row-sc-g4
   [button/reg-pill {:class    [:clear]
                     :on-click #(swap! (r/cursor settings [:rent/show-details]) not)}
    [sc/icon-huge (if @(rf/subscribe [:rent/common-show-details]) ico/eye-off ico/eye)]]

   [button/reg-pill {:class    [:clear]
                     :on-click #(swap! (r/cursor settings [:rent/show-details]) not)}
    [sc/icon-huge ico/trash]]])
