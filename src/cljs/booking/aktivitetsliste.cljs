(ns booking.aktivitetsliste
  (:require [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [db.core :as db]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [reagent.core :as r]
            [schpaa.debug :as l]
            [tick.core :as t]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [re-frame.core :as rf]
            [booking.ico :as ico]))

(comment
  (do
    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 10 0)))))

(def data
  (let [n (t/date (t/now)) #_(t/new-date 2022 3 21)
        tm 1]
    #_(r/atom (let [n (t/date (t/now))]
                (conj (reduce (fn [a e] (assoc a (str (+ 100 e))
                                                 {:list  #{(+ 100 e)}
                                                  :start (t/at n (t/new-time e 0))
                                                  :end   (t/at n (t/new-time (inc e) 0))})) {} (range 0 23))
                      {"096" {:start (t/at n (t/new-time 0 0))
                              :end   (t/at n (t/new-time 14 0))
                              :list  #{96}}}
                      {"097" {:start (t/at n (t/new-time 1 0))
                              :end   (t/at n (t/new-time 14 0))
                              :list  #{97}}}
                      {"098" {:start (t/at n (t/new-time 2 0))
                              :end   (t/at n (t/new-time 12 0))
                              :list  #{98}}}
                      {"099" {:start (t/at n (t/new-time 3 0))
                              :end   (t/at n (t/new-time 12 0))
                              :list  #{99}}})))
    #_(r/atom {
               "overnattF"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/instant (t/at n (t/new-time 12 0)))
                :end      (t/instant (t/at n (t/new-time 13 0)))
                :list     #{600}
                :data     1}
               "overnattA"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/at (t/new-date 2022 3 21) (t/new-time 10 0))
                :end      (t/at (t/new-date 2022 3 22) (t/new-time 22 30))
                :list     #{1100}
                :data     1}
               "x"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/at (t/new-date 2022 3 22) (t/new-time 11 0))
                :end      (t/at (t/new-date 2022 3 22) (t/new-time 22 30))
                :list     #{2100}
                :data     1}
               "y"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/at (t/new-date 2022 3 21) (t/new-time 11 0))
                ;:end      (t/at (t/new-date 2022 3 22) (t/new-time 22 30))
                :list     #{2102}
                :data     1}
               "z"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/at (t/new-date 2022 3 22) (t/new-time 11 0))
                :end      (t/at (t/new-date 2022 3 23) (t/new-time 22 30))
                :list     #{2101}
                :data     1}
               "w"
               {:key      true
                :moon     true
                :adults   10
                :children 2
                :start    (t/at (t/new-date 2022 3 22) (t/new-time 11 0))
                :end      (t/at (t/new-date 2022 3 22) (t/new-time 15 30))
                :list     #{2101}
                :data     1}})
    (r/atom {"A11" {:start  (t/instant (t/at (t/yesterday) (t/new-time 12 0)))
                    :end    (t/instant (t/at n (t/new-time 15 0)))
                    :list   #{"331"}
                    :adults 1
                    :data   1}
             "A12" {:start  (t/instant (t/at n (t/new-time 11 0)))
                    :end    (t/instant (t/at n (t/new-time 13 0)))
                    :list   #{"425"}
                    :adults 1
                    :data   1}
             "A13" {:start  (t/instant (t/at n (t/new-time 12 0)))
                    :end    (t/instant (t/at n (t/new-time 14 0)))
                    :list   #{"489" "512"}
                    :adults 2
                    :data   1}
             "A1"  {:start  (t/instant (t/at n (t/new-time 8 0)))
                    :list   #{"101"}
                    :adults 1
                    :data   1}
             "A2"  {:start  (t/instant (t/at n (t/new-time 7 0)))
                    :list   #{"102"}
                    :adults 1
                    :data   1}
             "A3"  {:start  (t/instant (t/at n (t/new-time 6 0)))
                    :list   #{"103"}
                    :adults 1
                    :data   1}
             "A4"  {:start  (t/instant (t/at n (t/new-time 4 0)))
                    :end    (t/instant (t/at n (t/new-time 8 0)))
                    :list   #{"104"}
                    :adults 1
                    :data   1}
             "B"   {:start (t/instant (t/at n (t/new-time 5 0)))
                    :end   (t/instant (t/at n (t/new-time 7 0)))
                    :list  #{"105"}
                    :data  1}
             "C"   {:start  (t/instant (t/at n (t/new-time 10 30)))
                    :end    (t/instant (t/at n (t/new-time 12 0)))
                    :list   #{200}
                    :adults 1
                    :data   1}
             "D"   {:start  (t/instant (t/at n (t/new-time 7 0)))
                    :list   #{200 220}
                    :adults 1
                    :data   1}
             "E"   {:start    (t/instant (t/at n (t/new-time 14 0)))
                    :list     #{300 221}
                    :adults   2
                    :children 1
                    :data     1}
             "F"   {:start (t/instant (t/at n (t/new-time 12 0)))
                    :list  #{141 132}
                    :data  1}
             "overnattF"
             {:key      true
              :moon     true
              :adults   10
              :children 2
              :start    (t/instant (t/at (t/new-date 2022 3 22) (t/new-time 10 0)))
              :list     #{600 620 630 650 660 664 642}
              :data     1}})))

(o/defstyled listitem :div
  :py-1 :bg-yellow-100x
  [:&
   :flex :items-end
   [:.selected :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :border-radius  "var(--radius-2)"
     :color          [:rgba 0 0 0 1]
     :background     [:rgba 0 0 0 0.09]}]
   [:.normal :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :color          [:rgba 0 0 0 0.5]}]
   [:&:hover {:border-radius "var(--radius-1)"
              :background    [:rgba 0 0 0 0.035]}]])

(defn delete-command [k st]
  (swap! st update-in [k] assoc :deleted true)
  #_(db/database-update
      {:path  ["booking-posts" "articles" (name k)]
       :value {:deleted true}}))

(defn undelete-command [k st]
  (swap! st update-in [k] assoc :deleted false)
  #_(db/database-update
      {:path  ["booking-posts" "articles" (name k)]
       :value {:deleted false}}))

(defn add-command [d]
  (swap! data assoc (random-uuid) d))

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)
   {:on-click #((if deleted undelete-command delete-command) k data)}
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    (if deleted (icon/small :rotate-left)
                [:> outline/TrashIcon])]])

(defn edit [k {:keys []}]
  [scb/round-normal-listitem
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    [:> outline/PencilIcon]]])

(o/defstyled mini-badge :button
  {:box-shadow "var(--shadow-2)"}
  [:div :font-oswald :h-full :w-full
   {:display         :inline-flex
    :justify-content :center
    :align-items     :center
    :border-radius   "var(--radius-0)"
    :font-size       "var(--font-size-0)"
    :font-weight     "var(--font-weight-5)"}]

  [:div.normal {:color          "var(--text1)"
                :letter-spacing "var(--font-letterspacing-2)"
                :background     "var(--surface1)"}]
  [:div.selected {:background "var(--yellow-5)"}]
  ([{:keys [selected on-click]} ch]
   ^{:on-click on-click}
   [:<> [:div {:class (if selected :selected :normal)} ch]]))

(defn- prepare-time [window-start window-end start end now]
  (let [limit-start 0
        limit-end 23
        n-now (* 4 (t/hour now))
        n-start (if (not= :before start) (* 4 (t/hour start)) 0)
        n-end (if (nil? end) n-now (* 4 (t/hour end)))
        data {:color      (if (nil? end) :blue :black)

              :start-sign (if (= :before start)
                            nil
                            (if (< n-start window-start)
                              false
                              true))

              :start      (if (= :before start)
                            0
                            (let [x (- n-start window-start)]
                              (if (neg? x) 0 x)))

              :end-sign   (if (nil? end) :arrow :stop)
              :end        n-end}]

    data #_(-> data
               (update :start * 4)
               ;(update :start - offset-x)
               (update :end * 4))))


(defn- draw-badge [item badge-fg badge-bg testbadge-bg]
  [:div.absolute.inset-y-0.h-full.flex.items-center.left-0
   [:div {:style    {;:box-shadow    "var(--inner-shadow-1)"
                     :background    (if (= 4 (count (str item))) testbadge-bg badge-bg)
                     ;:border        "var(--surface3) 1px solid"
                     :border-radius "var(--radius-1)"
                     :width         "2.5rem"}
          :selected false}
    [:div
     {:style {:display         "flex"
              :justify-content "center"
              :color           badge-fg                     ;(if (= 4 (count (str item))) "var(--surface000)" "var(--surface000)")
              :font-size       "var(--font-size-1)"
              :font-family     "Oswald"
              :letter-spacing  (if (= 4 (count (str item))) "var(--font-letterspacing-0)" "var(--font-letterspacing-2)")
              :font-weight     "var(--font-weight-6)"}}
     item]]])


(defn fancy-timeline [{:keys [window-end item entries rows session-start session-end action-start action-end now]}]
  (tap> [:action-start action-start])
  (let [period-view-only true
        window [(* 0 4) (* 23 4)]
        session-start (let [x (- session-start (first window))]
                        (if (neg? x) 0 x))
        session-end (- session-end (first window))
        y 3
        background "var(--surface0)"
        period "var(--yellow-5)"
        badge-bg "var(--surface5)"
        testbadge-bg "var(--blue-5)"
        badge-fg "var(--surface00)"
        tm (prepare-time (first window) (last window) action-start action-end now)]
    [:div
     [:div.h-8.w-full.relative.first:rounded-t-sm.last:rounded-b-sm
      ;{:style {:background-color :red #_background}}

      [draw-badge item badge-fg badge-bg testbadge-bg]

      [:svg.h-full.w-fullx.pl-12
       {:style               {:overflow :hidden}
        :viewBox             (str 0 " " 0 " " (- 96 (- 96 (- (last window) (first window)))) " " 11)
        :stroke-width        1
        :preserveAspectRatio "none"
        :width               "100%"
        :height              "auto"}

       [:rect {:vector-effect :non-scaling-stroke
               :fill          background
               :width         :100%
               :height        :100%}]

       [:rect {:style         {:opacity 0.2}
               :vector-effect :non-scaling-stroke
               :fill          period
               :x             session-start
               :width         (- session-end session-start)
               :height        :100%}]
       #_[:line {:stroke        now-color
                 :stroke-width  2
                 :vector-effect :non-scaling-stroke
                 ;:stroke-dasharray "2 1"
                 :x1            now-hour :y1 0 :x2 now-hour :y2 "100%"}]
       [:<>
        ;start sign
        [:rect (if (:start-sign tm)
                 {:fill         (:color tm)
                  :stroke-width 0
                  :y            (+ 2 y)
                  :x            (:start tm)
                  :width        (- (:end tm) (:start tm))
                  :height       1}
                 {:fill         (:color tm)
                  :stroke-width 0
                  :y            (+ 2 y)
                  :x            (:start tm)
                  :width        (- (:end tm) (:start tm))
                  :height       1})]

        (if (:start-sign tm)
          [:rect {:stroke        (:color tm)
                  :fill          (:color tm)
                  :vector-effect :non-scaling-stroke
                  :x             (:start tm)
                  :y             y
                  :width         "1%" :height 2.5}])

        ;end sign
        (if (:end-sign tm)
          (if (= :arrow (:end-sign tm))
            [:path {:vector-effect :non-scaling-stroke
                    :fill          (:color tm)
                    :transform     (str "translate(" (:end tm) "," y ")")
                    :d             (str "M0,0 L" (* 1 4) ",2.5 L0,5 z")}]
            [:rect {:stroke        (:color tm)
                    :fill          (:color tm)
                    :vector-effect :non-scaling-stroke
                    :x             (:end tm)
                    :y             (+ 2.25 y)
                    :width         "1%"
                    :height        2.5}]))]]]
     #_[:<>
        [sc/small "start-hour" (or (some-> action-start t/hour (* 4)) 0)]
        [sc/small "end-hour" (or (some-> action-end t/hour (* 4)) 0)]
        [sc/small "session-start--hour" session-start]
        [sc/small "end-hour" session-end]]]))

(defn render [r]
  ;constants
  (r/with-let [show-editing (schpaa.state/listen :activitylist/show-editing)
               show-deleted (schpaa.state/listen :activitylist/show-deleted)
               left-aligned (schpaa.state/listen :activitylist/left-aligned)]

    (let [time-now (t/now)
          session-start (* 4 12)
          session-end (* 4 17)]
      [:div

       (into [:div.flex.flex-col.space-y-px]
             (for [[k {:keys [start end key moon adults children juveniles deleted list datetime] :as e}]
                   (sort-by key < @data) #_(->> (seq @data)
                                                (remove (fn [[k {:keys [deleted]}]] (if @show-editing
                                                                                      (if @show-deleted
                                                                                        false
                                                                                        deleted)
                                                                                      deleted)))
                                                (filter (fn [[_ {:keys [start end]}]]
                                                          (when (and (some? start))
                                                            (or
                                                              (and (t/< (t/date start) (t/date time-now)) (nil? end))
                                                              (= (t/date time-now) (t/date start))))))
                                                (sort-by (comp :start val) >))]

               [listitem
                [:div.w-full.-xdebug.flex.items-start.gap-2
                 {:style {:opacity (if deleted 0.2)}}
                 [:div.flex.items-center.justify-between.gap-2.w-fullx.h-8
                  {:style {:flex "0 1 auto"}}
                  (when (and @show-editing)
                    [:div.flex.-xdebug
                     {:style {:flex "0 1 auto"}}
                     (trashcan k e)
                     (edit k e)])

                  [sc/row-sc-g2
                   {:style {:flex "0 1 auto"}
                    :class [:w-32 :-xdebug2 :h-8]}
                   [sc/text {:class [:w-4]} (or adults "")]
                   [sc/text {:class [:w-4]} (or juveniles "")]
                   [sc/text {:class [:w-4]} (or children "")]
                   [:div.grow]
                   (when key (sc/icon-tiny [:> solid/KeyIcon]))
                   (when moon (sc/icon-tiny [:> solid/MoonIcon]))]]

                 [sc/col {:class  [:w-full
                                   :py-x2 :space-y-px]
                          :xstyle {:min-width "20ch"
                                   :max-width "30ch"}}
                  (for [e list]
                    [fancy-timeline
                     {:item          e
                      :session-start session-start
                      :session-end   session-end
                      :action-start  (if (t/< (t/date start) (t/date time-now))
                                       :before
                                       (t/time start))
                      :action-end    (some-> end t/date-time)
                      :now           (t/date-time time-now)}])]]]))])))

(defn always-panel []
  [sc/row-sc-g2-w
   #_[hoc.toggles/button-reg
      #(rf/dispatch [:lab/qr-code-for-current-page])
      (sc/row-sc-g2 (sc/icon-small ico/qrcode) "QR-kode")]
   #_[hoc.toggles/button-cta
      #()
      (sc/row-sc-g2 (sc/icon-small ico/plus) "Utlån")]
   [hoc.toggles/button-cta
    #()
    (sc/row-sc-g2 (sc/icon-small ico/plus) "Skade på materiell")]
   [hoc.toggles/button-cta
    #()
    (sc/row-sc-g2 (sc/icon-small ico/plus) "HMS-hendelse")]])

(defn panel []
  (r/with-let [show-deleted (schpaa.state/listen :activitylist/show-deleted)
               show-content (schpaa.state/listen :activitylist/show-content)]
    [sc/row-sc-g2-w
     [hoc.toggles/switch :activitylist/show-editing "Rediger"]

     [hoc.toggles/twostate
      {:on-click  #(schpaa.state/toggle :activitylist/show-deleted)
       :alternate @show-deleted
       :icon      (fn [_] [:> outline/TrashIcon])
       :caption   (fn [e] (if e "Skjul slettede" "Vis slettede"))}]
     [hoc.toggles/switch :activitylist/show-narrow-scope "Vis skjulte"]
     [hoc.toggles/switch :activitylist/left-aligned "Venstre-juster listen"]
     [hoc.toggles/switch :activitylist/limit-timeline "Bare økt-perioden"]
     [hoc.toggles/switch :activitylist/limit-active "Bare aktive utlån"]]))