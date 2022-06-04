(ns booking.utlan
  (:require [tick.core :as t]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [booking.graph]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]
            [booking.common-widgets :as widgets]
            [booking.database]
            [booking.ico :as ico]
            [logg.database]
            [db.core :as db]
            [schpaa.style.button2]
            [schpaa.style.hoc.buttons :as button]
            [booking.timegraph :refer [timegraph]]
            [fork.reagent :as fork]
            [schpaa.style.input :as sci]
            [schpaa.style.hoc.buttons :as field]))

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

(defn dialog-innlevering [{:keys [data on-close on-save]}]
  (let [{:keys [initial original timestamp]} data
        db (rf/subscribe [:db/boat-db])]
    (r/with-let [st (r/atom initial)]
      [sc/dialog-dropdown {:style {:padding "1rem"}}
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
         [button/just-caption {:class    [:regular :normal]
                               :on-click on-close} "Avbryt"]
         [button/just-caption {:class    [:cta :normal]
                               :disabled (= initial @st)
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
      :content-fn #(dialog-innlevering %)}]))

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
    :grid-auto-rows        "min-content"
    :grid-template-areas   [["time edit content graph"]]}])

(o/defstyled logg-listitem-grid :div
  [:& :p-0 :w-full
   {:display               :grid
    :column-gap            "var(--size-3)"
    :row-gap               "var(--size-1)"
    :padding-block         "var(--size-1)"
    :grid-template-columns "5rem 1fr 1fr min-content 1fr"
    :grid-template-rows    "min-content min-content"        ;"minmax(2rem,1fr) min(min-content,2rem)"

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
  (when (or havekey sleepover)
    [deleted-item
     [sc/row-sc-g2 {:class []
                    :style {:flex-wrap :nowrap}}
      (when havekey [sc/icon-tiny-frame ico/harnøkkel])
      (when sleepover [sc/icon-tiny-frame ico/moon-filled])]]))

(defn- edit-bar [edit-mode? k {:keys [uid deleted timestamp list] :as m}
                 all-returned?]
  (let [{:keys [mobile?] :as geo} @(rf/subscribe [:lab/screen-geometry])]
    [sc/row-sc-g2 {:style {:align-self  "start"
                           :align-items "center"}
                   :class [:h-8]}
     (if edit-mode?
       [widgets/trashcan'
        {:deleted? deleted
         :class    [:border-red]
         :on-click (fn [] (db/database-update
                            {:path  ["activity-22" (name k)]
                             :value {:deleted (not deleted)}}))}
        (if deleted "Angre sletting" "Slett")]

       (when-not deleted
         [widgets/in-out
          all-returned?
          {:on-click #(innlevering {:k         k
                                    :timestamp timestamp
                                    :boats     list})}
          (if all-returned? "ut" "inn")]))
     [widgets/edit {:disabled false}
      #(rf/dispatch [:lab/toggle-boatpanel [k m]])
      m]]))

(defn agegroups-detail [{:keys [adults children juveniles] :as m}]
  (letfn [(f [n]
            [sc/text1 {:style {:font-weight "var(--font-weight-5)"
                               :white-space :nowrap}
                       :class [:tabular-nums]}
             (if (pos? n) n "—")])]
    [sc/row {:class [:h-10 :pl-2]
             :style {:align-items :center
                     :grid-area   "agegroups"}}
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

(defn badge-view-fn [class {:keys [id work-log returned number datum slot]}]
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
   slot])

(o/defstyled boats-placeholder :div
  [:& :flex :flex-wrap                                      ;:w-full :h-full
   {;:flex            "1 1 auto"
    :background       "red"
    :display          :flex
    :row-gap          "1rem"
    :outline          "11px solid red"
    :xjustify-content "end"
    :xalign-items     "center"}])

(defn- boat-badges [db boats]
  (letfn [(prepared [[id returned]]
            ;we are latching over db
            (when-let [id (some-> id keyword)]
              (let [datum (get db id)
                    number (:number datum)]
                {:id       id
                 :datum    datum
                 :slot     (:slot datum)
                 :returned (not (empty? (str returned)))
                 :number   number})))]
    (sort-by :number < (map prepared (remove nil? boats)))))

(defn g-area [grid-area content]
  [:div {:style {:grid-area grid-area}} content])

(defn item-line [{:keys [date end-time-instant k m all-returned? boats deleted db details?]}]
  (let [{:keys [ref-uid phone]} m
        item-wrapper #(sc/item-wrapper-style {:class [(when deleted :deleted)]} %)
        {:keys [start-date start-time end-time]} (preformat-dates date end-time-instant)]
    (when @selector
      [sc/zebra
       [:div.flex.w-full
        [logg-listitem-grid
         [g-area "badges" (when details? [agegroups-detail m])]
         [g-area "edit" [edit-bar @(rf/subscribe [:rent/common-show-deleted]) k m all-returned?]]
         [g-area "graph" (when (and details? (t/= (t/date date) (t/today)) (= :b @selector))
                           [timegraph {} settings date end-time-instant boats (t/date-time)])]

         [g-area "content"
          [sc/col
           [:div.flex.gap-1.flex-wrap.justify-end
            (map #(item-wrapper (badge-view-fn nil %)) (boat-badges db boats))]]]

         [g-area "start-date" [:div.flex.items-center.h-8
                               [sc/text1 {:style {:display      :flex
                                                  :align-items  :center
                                                  :height       "100%"
                                                  :padding-left "8px"}} start-date]]]
         (when details?
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
             (if-not ref-uid
               (if-let [uid (user.database/lookup-phone phone)]
                 [widgets/user-link uid]
                 [sc/text1 phone])
               [widgets/user-link ref-uid])
             [badges item-wrapper m]]])]]])))

;todo REFACTOR!
(defn render [loggedin-uid]
  (when-let [db @(rf/subscribe [:db/boat-db])]
    (let [details? @(rf/subscribe [:rent/common-show-details])
          show-deleted? @(rf/subscribe [:rent/common-show-deleted])
          ;show-timegraph? (= :b @selector) #_(rf/subscribe [:rent/common-show-timegraph])
          show-details? (= :b @selector) #_@(rf/subscribe [:rent/common-show-details])
          ;show-overview? (= :c @selector)
          data (rf/subscribe [:rent/list])
          data (if show-deleted? @data (remove (comp :deleted val) @data))]
      (into [:div.flex.flex-col]
            (for [[k {:keys [timestamp list deleted] :as m}] data
                  :let [boats list                          ;(sort-by (comp :number val) < list)
                        date (some-> timestamp t/instant t/date-time)
                        all-returned? (every? (comp not empty? val) boats)
                        ;what is the least convoluted way to write this?
                        end-time-instant (let [[dt _] (sort < (vals boats))]
                                           (when-not (empty? dt)
                                             (t/instant dt)))]
                  :when (cond
                          (= :b @selector)
                          (and (passed-date-test? date end-time-instant)
                               (not all-returned?))

                          ;show-overview?
                          ;(t/= (t/today) (t/date date))

                          show-details?
                          (not all-returned?)

                          :else true)]

              [item-line {:k                k
                          :m                m
                          :db               db
                          :all-returned?    all-returned?
                          #_#_:show-overview? show-overview?
                          :end-time-instant end-time-instant
                          :boats            boats
                          :deleted          deleted
                          :details?         details?
                          :date             date}])))))

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


(defn deleted? [[_ e]] (:deleted e))

(defn comp-> [& xs]
  (apply comp (reverse xs)))

(o/defstyled panelbuttonx :button
  [:& {:position       :relative
       :min-height     "12rem"
       :font-size      "200%"
       :outline        "1px solid red"
       :background     "blue"
       :color          "green"
       :padding-inline "1rem"
       :padding-block  "1rem"}])

;; common graph state

(def initial-st {:days-back-in-time 30
                 :client-width      0
                 :offset            0
                 :delta             0
                 :xpos              0
                 :xstart            0
                 :touchdown         false})

(defonce st (r/atom initial-st))

;;

(defn graph-1 []
  (let [activity-records @(db/on-value-reaction {:path ["activity-22"]})
        existing (remove deleted?)
        base (->> (transduce existing conj activity-records)
                  (group-by (comp-> val :timestamp t/instant t/date str)))
        dataset (into {}
                      (map
                        (juxt key
                              (juxt (comp count val)
                                    (comp
                                      #(reduce (fn [[a' b' c' d'] [a b c d]]
                                                 [(+ (or a 0) a')
                                                  (+ (or b 0) b')
                                                  (+ (or c 0) c')
                                                  (+ (or d 0) d')])
                                               [0 0 0 0] %)
                                      #(map (comp-> val (juxt :adults :juveniles :children (comp count :list))) %)
                                      second))))
                      base)]
    (r/with-let [days-back-in-time (r/cursor st [:days-back-in-time])
                 offset (r/cursor st [:offset])
                 reset-view #(reset! st initial-st)]
      (let [command-row [sc/row-field
                         [sc/co
                          [sc/row-sc-g2
                           [button/just-caption {:on-click #(reset! days-back-in-time 180)
                                                 :class    [(if (= 180 @days-back-in-time) :inverse :regular)
                                                            :round]} "16"]
                           [button/just-caption {:on-click #(reset! days-back-in-time 60)
                                                 :class    [(if (= 60 @days-back-in-time) :inverse :regular)
                                                            :round]} "8"]
                           [button/just-caption {:on-click #(reset! days-back-in-time 30)
                                                 :class    [(if (= 30 @days-back-in-time) :inverse :regular)
                                                            :round]} "4"]]
                          [sc/small2 {:class [:pl-1 :uppercase]} "Uker"]]

                         [:div.grow]

                         [button/just-caption {:on-click #(swap! offset inc)
                                               :class    [:regular
                                                          :narrow]} "Forrige"]

                         [button/just-caption {:on-click #(reset! offset 1)
                                               :class    [(if (= 1 @offset) :inverse :regular)
                                                          :narrow]} "I går"]

                         [button/just-icon {:on-click reset-view
                                            :class    [(if (= 0 @offset) :regular :cta)
                                                       :round]}
                          ico/undo]]
            get-data-fn (fn [dt c] (if-some [datum (get dataset dt)]
                                     (get datum c)
                                     nil))]
        [booking.graph/graph dataset command-row st reset-view
         nil
         (fn draw-func? [dt x _value-and-color-seq max-value]
           (let [weekend? (some #{(t/int (t/day-of-week dt))} [6 7])
                 cnt (get-data-fn dt 0)
                 _ (tap> (get-data-fn dt 1))
                 value-and-color-seq [[cnt (if weekend? "var(--text1)" "var(--text3)")]
                                      [(nth (get-data-fn dt 1) 1) "red"]
                                      [(nth (get-data-fn dt 1) 2) "green"]]]
             (for [[idx [value color]] (map-indexed vector value-and-color-seq)
                   :let [offset (* 0.5 (inc idx))]]
               [:line
                (conj {:stroke       color
                       :stroke-width 0.85
                       :x1           (- x offset) :y1 (- max-value value)
                       :x2           (- x offset) :y2 max-value}
                      (when (pos? idx)
                        {:stroke-width  3
                         :vector-effect :non-scaling-stroke}))])))]))))

(def weather-words
  [[0 "Tåke"]
   [2 "Pent vær"]
   [2 "Overskyet"]
   [2 "Lettskyet"]
   [2 "Skyet"]
   [1 "Vindstille"]
   [1 "Vind"]
   [1 "Bris"]
   [1 "Kraftig vind"]
   [3 "Yr"]
   [3 "Litt nedbør"]
   [3 "Styrtregn"]])


(defn temperatureform-content [{:keys [data on-close uid]}]
  [sc/dialog-dropdown
   [fork/form
    {:initial-values   {:luft "23"
                        :vann ""
                        :vær  nil}
     :prevent-default? true
     :keywordize-keys  true
     :on-submit        (fn [{:keys [values]}]
                         (let [data (assoc values
                                      :timestamp (str (t/now))
                                      :uid uid)]
                           (js/alert (str data))
                           (on-close)))}
    (fn [{:keys [handle-submit form-id values set-values handle-change] :as props}]


      [sc/col-space-8
       [sc/col-space-8
        {:style {:padding          "1rem"

                 :background-color "var(--floating)"}}
        [:div.px-1 [sc/dialog-title "Registrer luft og vanntemperatur"]]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col-space-8
          [sc/row-sc-g4 {:style {:gap "2rem"}
                         :class [:items-start]}
           [sc/col-space-4 {:class [:w-32]}
            [field/textinput (assoc props
                               ;:class [:on-bright]
                               #_#_:handle-change #(let [v (.. % -target -value)]
                                                     (tap> v)
                                                     (set-values :luft v))
                               :values {:luft (:luft values)} #_(values :luft)) "Lufttemp" :luft]
            [field/textinput props "Vanntemp" :vann]]

           [sc/checkbox-matrix
            {:style {:padding-topx "1rem"}}
            (into [:<>]
                  (for [[_ e] (group-by first weather-words)
                        [_ e] e]
                    ;[l/pre e]
                    [hoc.toggles/largeswitch-local''
                     {:get     #(values e)
                      :set     #(set-values {e %})
                      :view-fn (fn [t c v] [sc/row-sc-g2 t
                                            [(if v sc/text1 sc/text0) c]])
                      :caption e}]))]]
          [sc/row-field
           [:div.grow]
           [button/just-caption {:class    [:regular :normal]
                                 :on-click on-close
                                 :type     :button} "Avbryt"]
           [button/just-caption {:type  :submit
                                 :class [:cta :normal]} "Ok"]]]]]])]])

(defn add-temperature []
  (let [uid @(rf/subscribe [:lab/uid])
        data {}]
    (rf/dispatch [:modal.slideout/show
                  {:data       data
                   :uid        uid
                   :content-fn (fn [e] (temperatureform-content e))}])))

(defn graph-2 []
  (let [temperature-records @(db/on-value-reaction {:path ["temperature"]})
        existing (remove deleted?)
        base (->> (transduce (comp existing #_(filter (fn [[k v]] (= 2 (:version v))))) conj temperature-records)
                  (group-by (comp-> val :date str #(times.api/format "%s-%s-%s" (subs % 0 4) (subs % 4 6) (subs % 6 8)))))
        #_#_dataset (into {}
                          (map
                            (juxt key (comp-> val #(map (comp-> val (juxt (comp-> :luft js/parseInt)
                                                                          (comp-> :vann js/parseInt)
                                                                          (comp-> :vær js/parseInt))) %) first)))
                          base)]

    (r/with-let [
                 reset-view-fn #(reset! st initial-st)
                 days-back-in-time (r/cursor st [:days-back-in-time])
                 offset (r/cursor st [:offset])]
      (let [command-row [sc/row-field
                         [sc/co
                          [sc/small2 {:class [:pl-1 :uppercase]} "Antall uker vist"]
                          [sc/row-sc-g2
                           [button/just-caption {:on-click #(reset! days-back-in-time 180)
                                                 :class    [(if (= 180 @days-back-in-time) :inverse :regular)
                                                            :round]}

                            "16"]
                           [button/just-caption {:on-click #(reset! days-back-in-time 60)
                                                 :class    [(if (= 60 @days-back-in-time) :inverse :regular)
                                                            :round]}
                            "8"]
                           [button/just-caption {:on-click #(reset! days-back-in-time 30)
                                                 :class    [(if (= 30 @days-back-in-time) :inverse :regular)
                                                            :round]}

                            "4"]]]
                         [:div.grow]
                         [button/icon-and-caption
                          {:on-click add-temperature
                           :class    [:cta]}
                          ico/plus [:div.truncate "Ny temperatur"]]

                         [button/just-caption {:on-click #(reset! offset 1)
                                               ;:style {:box-shadow "var(--shadow-1)"}
                                               :class    [(if (= 1 @offset) :inverse :regular)
                                                          :narrow]} "I går"]
                         [button/just-icon {:on-click reset-view-fn
                                            :class    [(if (= 0 @offset) :regular :cta)
                                                       :round]} ico/undo]]
            dataset {"2022-04-11" [11 10]
                     "2022-04-12" [13 10]
                     "2022-05-20" [15 10]
                     "2022-05-29" [23 14]
                     "2022-05-30" [21 15]
                     "2022-05-31" [11 16]
                     "2022-06-01" [15 17]}
            get-data-fn (fn [dt] (when-some [datum (get dataset dt)]
                                   datum))]
        [booking.graph/graph
         dataset
         command-row
         st
         reset-view-fn
         nil
         (fn draw-func? [dt x _value-and-color-seq max-value]
           (let [weekend? (some #{(t/int (t/day-of-week dt))} [6 7])
                 elements-in-vector (get-data-fn dt)
                 [cnt a] elements-in-vector
                 value-and-color-seq [[cnt (if weekend? "var(--text1)" "var(--text3)")]
                                      [10 "blue"]
                                      [15 #_(rand-int 50) "red"]
                                      [20 #_(rand-int 50) "green"]
                                      [25 #_(rand-int 50) "orange"]]
                 offset (* (/ 1 (max 1 (count value-and-color-seq))))
                 half-offset (/ offset -2)]

             (for [[idx [value color]] (map-indexed vector value-and-color-seq)
                   :let [x' (if (pos? idx)
                                (+ x (* offset idx) half-offset)
                                x)]]
               [:line
                (conj {:stroke       color
                       :stroke-width 0.85
                       :x1           x' :y1 (- max-value value)
                       :x2           x' :y2 max-value}
                      (when (pos? idx)
                        {:vector-effect :non-scaling-stroke
                         :stroke-width  3}))])))]))))


(defn always-panel []
  [:<>
   [widgets/disclosure
    {}
    :aaaa
    "Statistikk"
    [graph-1]]
   [widgets/disclosure
    {}
    :bbbb
    "Temperatur"
    [graph-2]]

   [sc/row-center {:class [:sticky :pointer-events-none :noprint]
                   :style {:z-index 10
                           :top     "8rem"}}
    [sc/row-center
     [sc/col-space-8
      {:class [:pointer-events-auto]}
      [widgets/pillbar
       {:class [:large]
        :style {:box-shadow "var(--shadow-2)"}}
       selector
       [[:b "Ute"]
        [:a "Alle"]]]
      [sc/row-center

       [button/just-icon
        {:style    {:box-shadow "var(--shadow-2)"}
         :class    [:large :cta]
         :on-click #(rf/dispatch [:lab/toggle-boatpanel nil])}
        ico/plus]]]]]])

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
    [[button/just-large-icon
      {:class    [:round (if details-mode? :message :clear)]
       :on-click #(swap! (r/cursor settings [:rent/show-details]) not)}
      ico/more-details]
     [button/just-large-icon
      {:class    [:round
                  (if delete-mode? :danger-outline :clear)]
       :on-click #(swap! (r/cursor settings [:rent/show-deleted]) not)}
      ico/trash]]))
