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

(def data
  (let [n (t/new-date 2022 3 18)
        tm 1]
    (r/atom {"A1" {:start  (t/instant (t/at n (t/new-time 8 0)))
                   :list   #{"JEG"}
                   :adults 1
                   :data   1}
             "A2" {:start  (t/instant (t/at n (t/new-time 7 0)))
                   :list   #{"JEG"}
                   :adults 1
                   :data   1}
             "A3" {:start  (t/instant (t/at n (t/new-time 6 0)))
                   :list   #{"JEG"}
                   :adults 1
                   :data   1}
             "A4" {:start  (t/instant (t/at n (t/new-time 4 0)))
                   :end    (t/instant (t/at n (t/new-time 8 0)))
                   :list   #{"NOEN"}
                   :adults 1
                   :data   1}
             "B"  {:start (t/instant (t/at n (t/new-time 5 0)))
                   :end   (t/instant (t/at n (t/new-time 7 0)))
                   :list  #{"DEG"}
                   :data  1}
             "C"  {:start  (t/instant (t/at n (t/new-time 14 0)))
                   :end    (t/instant (t/at n (t/new-time 20 0)))
                   :list   #{100}
                   :adults 1
                   :data   1}
             "D"  {:start  (t/instant (t/at n (t/new-time 7 0)))
                   :list   #{100 120}
                   :adults 1
                   :data   1}
             "E"  {:start    (t/instant (t/at n (t/new-time 14 0)))
                   :list     #{100 120}
                   :adults   2
                   :children 1
                   :data     1}
             "F"  {:start (t/instant (t/at n (t/new-time 12 0)))
                   :list  #{100 121}
                   :data  1}
             "overnattF"
             {:key      true
              :moon     true
              :adults   10
              :children 2
              :start    (t/instant (t/at (t/new-date 2022 3 18) (t/new-time 7 0)))
              :list     #{100 120 130 150 160 164 142}
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
              :background    [:rgba 0 0 0 0.1]}]])

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

(defn- calc-rows [rows]
  (case rows
    (4 5 6) 40
    (7 8 9) 60
    20))

(defn fancy-timeline [{:keys [entries rows session-start session-end action-start action-end now]}]
  (let [start-hour (or (some-> action-start t/hour) 0)
        end-hour (some-> action-end t/hour)
        now-hour (t/hour now)]
    [:div.relative
     {:style {:height (* 2 (calc-rows rows))}}
     [:svg.h-full
      {:viewBox             (str "0 0 23 " (calc-rows rows))
       :stroke-width        1

       :preserveAspectRatio "none"
       :width               "100%"
       :height              "auto"}

      [:rect {:vector-effect :non-scaling-stroke
              :fill          :white
              :width         :100%
              :height        :100%}]

      [:rect {:vector-effect :non-scaling-stroke
              :fill          "var(--surface00)"
              :x             (t/hour session-start)
              :width         (- (t/hour session-end) (t/hour session-start))
              :height        :100%}]
      (let [w (or (some-> end-hour (- start-hour))
                  (- now-hour start-hour))]
        [:<>
         [:rect {:fill         :0
                 :stroke-width 0
                 :y            2
                 :x            start-hour
                 :width        w
                 :height       1}]
         (when (< 0 (- now-hour start-hour))
           [:rect {:stroke        :black
                   :vector-effect :non-scaling-stroke
                   :x             start-hour :y 1
                   :width         "2%" :height 3}])
         #_[:ellipse {:vector-effect :non-scaling-stroke
                      :fill          :black
                      :stroke        :none
                      :cx            start-hour
                      :cy            2
                      :rx            "0.1vh"
                      :ry            "0.1vw"
                      :transform     (str "translate(" (- start-hour 2) "," 1 ")")
                      :d             "M0,0 L1,1.5 L0,3 z"}]
         (if (nil? end-hour)
           [:path {:vector-effect :non-scaling-stroke
                   :fill          "black"
                   :transform     (str "translate(" (+ start-hour w) "," 1 ")")
                   :d             "M0,0 L1,1.5 L0,3 z"}]
           [:rect {:stroke        :black
                   :vector-effect :non-scaling-stroke
                   :x             end-hour :y 1
                   :width         "2%" :height 3}])])
      [:line {:stroke           :red
              :vector-effect    :non-scaling-stroke
              :stroke-dasharray "2"
              :x1               now-hour :y1 0 :x2 now-hour :y2 "100%"}]]

     (for [[idx e] (map-indexed vector entries)]
       [:div.absolute.top-0.left-0
        {:style {:top (str (* idx 20) "px")}}
        [mini-badge {} e]])]))

(defn render [r]
  ;constants
  (let [time-now (t/now)
        session-start (t/at (t/new-date 2022 3 18) (t/new-time 7 0))
        session-end (t/at (t/new-date 2022 3 18) (t/new-time 12 0))]
    (into [:div.space-y-px.xp-1]
          (for [[k {:keys [start end key moon adults children deleted list datetime] :as e}]
                (->> (seq @data)
                     (filter (fn [[_ {:keys [start end]}]] (when (and (some? start))
                                                             (or
                                                               (and (t/< (t/date start) (t/date time-now)) (nil? end))
                                                               (= (t/date time-now) (t/date start))))))
                     (sort-by (comp :start val) >))]

            [listitem
             [:div.flex.w-full
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

              #_(into [sc/surface-b-sans-bg {:class [:gap-1 :shrink-1 :w-full]
                                             :style {:padding               "var(--size-1)"
                                                     :max-width             "17ch"
                                                     :min-width             :4rem
                                                     ;:background            :#aaf
                                                     :display               :grid
                                                     :grid-template-columns "repeat(auto-fit,minmax(2.53rem,min-content))"
                                                     :grid-auto-rows        "2rem"}}]
                      (map #(vector mini-badge {:on-click (fn [e] (tap> %))
                                                :selected false} (str %))) list)

              (let [rows (case (count list)
                           (3 4) 20
                           (5 6) 30
                           (7 8) 40
                           10)]
                [:div.shrink-1.h-10x.shadow-sm.w-full
                 {:style {:height        (str (* 4 rows) "px")
                          :flex-grow     1
                          :max-width     "30ch"
                          :overflow      :hidden
                          :border-radius "var(--radius-1)"}}
                 [fancy-timeline
                  {:entries       list
                   :rows          (count list)
                   :session-start session-start
                   :session-end   session-end
                   :action-start  (if (t/< (t/date start) (t/date time-now)) nil (t/time start))
                   :action-end    (some-> end t/time)
                   :now           time-now}]])]]))))