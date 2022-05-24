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
            [schpaa.style.hoc.buttons :as button]
            [booking.timegraph :refer [timegraph]]))

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
      {:class [:big :work-log (when-not v :in-use)]} number slot]
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
      [sc/dropdown-dialog' {:style {:padding "1rem"}}
       [sc/col-space-8
        [sc/col-space-4
         (let [f (fn [[k v]]
                   (let [m (get @db k)
                         args [[k v] st original]]
                     (schpaa.style.hoc.toggles/largeswitch-local
                       {:atoma   (r/cursor st [k])
                        :view-fn (fn [t c] (view-fn m args timestamp t))})))]
           (map f @st))]
        [sc/row-ec {:class [:pb-6x]}
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
   {
    :display               :grid
    :column-gap            "var(--size-2)"
    :row-gap               "var(--size-1)"
    :grid-template-columns "min-content min-content 1fr 1fr"
    :grid-auto-rows        "auto"
    :grid-template-areas   [["time edit content graph"]]}])

(o/defstyled logg-listitem-grid :div
  [:& :py-1
   {:display               :grid
    :column-gap            "var(--size-3)"
    :row-gap               "var(--size-1)"
    :grid-template-columns "5rem 1fr 1fr min-content 1fr"
    :grid-template-rows    "minmax(2rem,1fr) min(min-content,2rem)"

    :grid-template-areas   [["start-date edit content content content"]
                            ["badges start-time start-time details details "]
                            [". graph . . ."]]}])

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
              (and                                          ;@(schpaa.state/listen :r.utlan)
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

(defn- badges [deleted-item {:keys [deleted sleepover havekey] :as m}]
  ;note: Deleted-item is a partially applied sc/deleted-item
  [deleted-item
   [sc/row-sc-g2 {:class [:pr-2]
                  :style {:flex-wrap :nowrap}}
    (when havekey [sc/icon-tiny-frame ico/harnøkkel])
    (when sleepover [sc/icon-tiny-frame ico/moon-filled])]])

(defn- edit-bar [loggedin-uid edit-mode? k {:keys [uid deleted timestamp list] :as m}
                 all-returned?]
  (let [{:keys [mobile?] :as geo} @(rf/subscribe [:lab/screen-geometry])]
    [sc/row-sc-g2 {:style {:align-self "start"}}
     (if edit-mode?
       [widgets/trashcan'
        {:deleted? deleted
         :on-click (fn [] (db/database-update
                            {:path  ["activity-22" (name k)]
                             :value {:deleted (not deleted)}}))}
        (if deleted "Angre" "Slett")]
       (when-not deleted
         [widgets/in-out'
          {:on-click #(innlevering {:k         k
                                    :timestamp timestamp
                                    :boats     list})}
          (if all-returned? "ut" "inn")]))
     [widgets/edit {:disabled false}
      #(rf/dispatch [:lab/toggle-boatpanel [k m]])
      m]]))

(defn agegroups-detail [{:keys [adults children juveniles] :as m}]
  (letfn [(f [n]
            [sc/text1 {:style {:font-weight  "var(--font-weight-5)"
                               :white-space  :nowrap}
                       :class [:tabular-nums]}
             (if (pos? n) n "—")])]
    [sc/row {:class [:h-full :pl-2]
             :style {:align-items :start
                     :grid-area  "agegroups"}}
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
         (fn [{:keys [id work-log returned number datum slot]}]
           [deleted-item::style
            ;(tap> {:id id :datum datum})
            [widgets/badge
             {:on-click #(if datum
                           ;(tap> [:widgets.badge id datum])
                           (open-modal-boatinfo {:data (assoc datum :id id)})
                           (open-modal-boatinfo {:data (assoc datum :id id)}))
              :class    [class
                         :small
                         #_(when (pos? (->> datum :work-log (remove (comp :deleted val)) count))
                             :work-log)
                         :whitespace-nowrap
                         (when-not (:boat-type datum) :active)
                         (when-not returned :in-use)]}

             (when-let [data (:work-log datum)]
               (when-some [n (seq (remove (comp :deleted val) data))]
                 (count n)))
             (or number (str (some-> id name (subs 0 3))))
             slot]])]
     (map badge-view-fn (sort-by :number < (map prepared (remove nil? boats)))))])

(defn g-area [grid-area content]
  [:div.pt-1 {:style {:grid-area grid-area}} content])

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
      (into [:div.flex.flex-col]
            (for [[k {:keys [timestamp list deleted ref-uid phone] :as m}] data
                  :let [boats list                          ;(sort-by (comp :number val) < list)
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
              (when @selector
                [sc/zebra {:class []}
                 (if show-overview?
                   [logg-listitem-overview
                    (let [{:keys [start-date end-date start-time end-time]}
                          (preformat-dates date end::time-instant)]
                      [g-area "time"
                       [sc/title1 {:style {:align-self :center}
                                   :class [:tracking-tight :tabular-nums :-debug]} start-time]])
                    [g-area "edit"
                     [edit-bar loggedin-uid @(rf/subscribe [:rent/common-edit-mode]) k m all-returned?]]
                    (when nitty-gritty
                      (when (t/= (t/date date) (t/today))
                        [g-area "graph" [timegraph {}  settings date end::time-instant boats (t/date-time)]]))
                    [g-area "content" [boat-badges db deleted-item::style boats :small]]]

                   [logg-listitem-grid
                    (when nitty-gritty
                      (when (some #{@selector} [:a :b])
                        [g-area "badges" [agegroups-detail m]]))

                    [:div {:style {:display     "flex"
                                   :align-items "start"
                                   :grid-area   "edit"}}
                     [edit-bar loggedin-uid @(rf/subscribe [:rent/common-show-deleted]) k m all-returned?]]

                    ;graph
                    (when nitty-gritty
                      (if (and (t/= (t/date date) (t/today)) (= :b @selector))
                        [g-area "graph" [timegraph {}  settings date end::time-instant boats (t/date-time)]]))

                    [g-area "content" [:div.px-2 [boat-badges db deleted-item::style boats]]]

                    (let [{:keys [start-date end-date start-time end-time]} (preformat-dates date end::time-instant)]
                      [:<>
                       [sc/text1 {:style {:align-self :center
                                          :padding-left "8px"
                                          :grid-area "start-date"}} start-date]
                       (if nitty-gritty

                         [sc/text1 {:style {:grid-column "2/-1"
                                            :display     :flex
                                            :height      "2rem"
                                            :align-items :center}}
                          [sc/text1 start-time]
                          (if-not end-time "-->" "—")
                          [sc/text1 end-time]

                          [:div.grow]
                          [sc/row-sc-g2 {:class [:truncate]
                                         :style {:align-items     "center"
                                                 :justify-content :end
                                                 :grid-area       "details"}}
                           (if-not ref-uid [sc/text1 phone] [widgets/user-link ref-uid])
                           [badges deleted-item::style m]]])])])]))))))

(defn panel [{:keys []}]
  [sc/row-sc-g4-w {:style {:flex-wrap :wrap}}

   [sc/row-sc-g4-w
    ;[sc/text1 "Se også"]
    [widgets/auto-link nil :r.båtliste.nøklevann]
    #_[sc/link {:href (kee-frame.core/path-for [:r.dokumenter {:id "tidslinje-forklaring"}])} "Ofte stilte spørsmål"]
    [widgets/auto-link nil :r.booking]]

   [sc/row-sc-g2-w
    [hoc.toggles/switch-local {:disabled true} (r/cursor settings [:rent/show-details]) "Kompakt"]
    [hoc.toggles/switch-local {:disabled false} (r/cursor settings [:rent/show-timegraph]) "Tidslinje"]]

   [hoc.toggles/switch-local (r/cursor settings [:rent/show-deleted]) "vis slettede"]])

(defn always-panel []
  [:<>
   [sc/row-center {:class [:sticky :pointer-events-none :noprint]
                   :style {:z-index 1000
                           :top     "8rem"}}
    [sc/row-center
     [sc/col-space-8
      {:class [:pointer-events-auto]}
      [widgets/pillbar
       {:class [:small]
        :style {:box-shadow "var(--shadow-2)"}}
       selector
       [[:b "Ute"]
        [:a "Alle"]]]
      [sc/row-center

       [hoc.buttons/pill
        {:style    {:box-shadow "var(--shadow-2)"}
         :class    [:large :cta]
         :on-click #(rf/dispatch [:lab/toggle-boatpanel nil])}
        [sc/icon ico/plus]]]]]]])

(rf/reg-event-fx :lab/remove-boatlogg-database
                 (fn [_ _]
                   (db/database-set {:path  ["activity-22"]
                                     :value {}})
                   (db/database-set {:path  ["boad-item"]
                                     :value {}})
                   {}))

(defn headline-plugin []
  (let [delete-mode? @(rf/subscribe [:rent/common-show-deleted])
        details-mode? @(rf/subscribe [:rent/common-show-details])]
    [[button/reg-pill
      {:class    [:large (if details-mode? :message :clear)]
       :on-click #(swap! (r/cursor settings [:rent/show-details]) not)}
      [sc/icon-large ico/more-details]]
     [button/reg-pill
      {:class    [:large
                  (if delete-mode? :danger :clear)]
       :on-click #(swap! (r/cursor settings [:rent/show-deleted]) not)}
      [sc/icon-large ico/trash]]]))