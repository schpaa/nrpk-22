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
            [tick.core :as t]))

(comment
  (do
    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 10 0)))))

(defonce data
         (let [n (t/date (t/now)) #_(t/new-date 2022 3 21)
               tm 1]
           (r/atom {"A11" {:start  (t/instant (t/at n (t/new-time 12 0)))
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
                    "A4" {:start  (t/instant (t/at n (t/new-time 4 0)))
                          :end    (t/instant (t/at n (t/new-time 8 0)))
                          :list   #{"104"}
                          :adults 1
                          :data   1}
                    "B"  {:start (t/instant (t/at n (t/new-time 5 0)))
                          :end   (t/instant (t/at n (t/new-time 7 0)))
                          :list  #{"105"}
                          :data  1}
                    "C"  {:start  (t/instant (t/at n (t/new-time 10 30)))
                          :end    (t/instant (t/at n (t/new-time 12 0)))
                          :list   #{200}
                          :adults 1
                          :data   1}
                    "D"  {:start  (t/instant (t/at n (t/new-time 7 0)))
                          :list   #{200 220}
                          :adults 1
                          :data   1}
                    "E"  {:start    (t/instant (t/at n (t/new-time 14 0)))
                          :list     #{300 221}
                          :adults   2
                          :children 1
                          :data     1}
                    "F"  {:start (t/instant (t/at n (t/new-time 12 0)))
                          :list  #{141 132}
                          :data  1}
                    "overnattF"
                    {:key      true
                     :moon     true
                     :adults   10
                     :children 2
                     :start    (t/instant (t/at (t/new-date 2022 3 21) (t/new-time 15 0)))
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

(defn fancy-timeline [{:keys [item entries rows session-start session-end action-start action-end now]}]
  (let [start-hour (or (some-> action-start t/hour) 0)
        end-hour (some-> action-end t/hour)
        now-hour (t/hour now)
        y 4
        active-color :blue
        now-color :white
        complete-color :black
        background "var(--surface1)"
        period :#aa06 #_"var(--surface0)"]
    [:div.h-8.w-full.relative.first:rounded-t-md.last:rounded-b-md
     {:style {:background-color background}}

     [:div.absolute.inset-y-0.h-full.flex.items-center.left-1
      [sc/badge {:style    {:box-shadow    "var(--inner-shadow-1)"
                            :border        "var(--surface3) 1px solid"
                            :border-radius "var(--radius-1)"
                            :width         "2.5rem"}
                 :selected false}
       [:div
        {:style {:color       "var(--text2)"

                 :font-size   "var(--font-size-1)"
                 :font-weight "var(--font-weight-4)"}}
        item]]]

     [:svg.h-full.w-full.pl-12.pr-12
      {:viewBox             (str "0 0 23 " 12)
       :stroke-width        1
       :preserveAspectRatio "none"
       :width               "100%"
       :height              "auto"}

      [:rect {:vector-effect :non-scaling-stroke
              :fill          background
              :width         :100%
              :height        :100%}]

      [:rect {:vector-effect :non-scaling-stroke
              :fill          period
              :x             (t/hour session-start)
              :width         (- (t/hour session-end) (t/hour session-start))
              :height        :100%}]
      [:line {:stroke        now-color
              :stroke-width  2
              :vector-effect :non-scaling-stroke
              ;:stroke-dasharray "2 1"
              :x1            now-hour :y1 0 :x2 now-hour :y2 "100%"}]
      (let [w (or (some-> end-hour (- start-hour))
                  (- now-hour start-hour))]
        [:<>
         [:rect (if (nil? end-hour)
                  {:fill         active-color
                   :stroke-width 0
                   :y            (+ 1 y)
                   :x            start-hour
                   :width        w
                   :height       1}
                  {:fill         complete-color
                   :stroke-width 0
                   :y            (+ 1 y)
                   :x            start-hour
                   :width        w
                   :height       1})]
         (when (< 0 (- now-hour start-hour))
           (if (nil? end-hour)
             [:rect {:stroke        active-color
                     :fill          active-color
                     :vector-effect :non-scaling-stroke
                     :x             start-hour :y y
                     :width         "2%" :height 3}]
             [:rect {:stroke        complete-color
                     :fill          complete-color
                     :vector-effect :non-scaling-stroke
                     :x             start-hour :y y
                     :width         "2%" :height 3}]))
         (if (nil? end-hour)
           [:path {:vector-effect :non-scaling-stroke
                   :fill          active-color
                   :transform     (str "translate(" (+ start-hour w) "," y ")")
                   :d             "M0,0 L1,1.5 L0,3 z"}]
           [:rect {:stroke        complete-color
                   :fill          complete-color
                   :vector-effect :non-scaling-stroke
                   :x             end-hour :y y
                   :width         "2%" :height 3}])])]]))

(defn render [r]
  ;constants
  (r/with-let [show-editing (schpaa.state/listen :activitylist/show-editing)
               show-deleted (schpaa.state/listen :activitylist/show-deleted)]
    (let [time-now (t/now)
          session-start (t/at (t/new-date 2022 3 21) (t/new-time 11 0))
          session-end (t/at (t/new-date 2022 3 21) (t/new-time 20 0))]
      [:div.min-w-full.-debug3                              ;.max-w-lg.-debug.min-w-fullx.mx-auto

       (into [:div.flex.flex-col.space-y-px.-debug2.xmin-w-xl.xmd:min-w-lg]
             (for [[k {:keys [start end key moon adults children juveniles deleted list datetime] :as e}]
                   (->> (seq @data)
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
                [:div.w-full.-debug.flex.items-start.gap-2
                 {:style {:opacity (if deleted 0.2)}}
                 [:div.flex.items-center.justify-between.gap-2.w-fullx.h-8
                  {:style {:flex "0 1 auto"}}
                  (when (and @show-editing)
                    [:div.flex.-debug
                     {:style {:flex "0 1 auto"}}
                     (trashcan k e)
                     (edit k e)])

                  [sc/row-sc-g2
                   {:style {:flex "0 1 auto"}
                    :class [:w-32 :-debug2 :h-8]}
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
                  (for [e (sort list)]
                    [fancy-timeline
                     {:item          e
                      :rows          1
                      :session-start session-start
                      :session-end   session-end
                      :action-start  (if (t/< (t/date start) (t/date time-now)) nil (t/time start))
                      :action-end    (some-> end t/time)
                      :now           time-now}])]]]

               #_[listitem
                  [:div.flex.w-full.-debug
                   {:class (into [:justify-between
                                  :items-start :gap-2 :px-2]
                                 (if deleted [:line-through :opacity-50]))}

                   ;[:div.h-10.flex.items-center (trashcan k e)]
                   [:div.h-10.flex.items-center (edit k e)]


                   [:div.w-10.shrink-0.flex.flex-col.items-center.h-full
                    [:div.w-auto.flex.gap-1.items-baseline
                     [sc/text adults]
                     [sc/small children]]
                    [:div.w-auto.flex.gap-1
                     (when key (sc/icon-tiny [:> solid/KeyIcon]))
                     (when moon (sc/icon-tiny [:> solid/MoonIcon]))]]

                   (let [rows (case (count list)
                                (3 4) 20
                                (5 6) 30
                                (7 8) 40
                                10)]


                     [:div.shrink-1.h-6.shadow-sm.w-full
                      {:style {;:height        (str (* 4 rows) "px")
                               :flex-grow     1
                               :max-width     "30ch"
                               :overflow      :hidden
                               :border-radius "var(--radius-1)"}}])
                   [:div.w-full
                    (for [e list]
                      [:div.flex.w-full.h-6.space-y-1.-debug2
                       [:div.w-12.-debug2 [sc/small e]]
                       [fancy-timeline
                        {;:entries       list
                         :rows          1
                         :session-start session-start
                         :session-end   session-end
                         :action-start  (if (t/< (t/date start) (t/date time-now)) nil (t/time start))
                         :action-end    (some-> end t/time)
                         :now           time-now}]])]]]))])))