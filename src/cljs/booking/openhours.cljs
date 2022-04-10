(ns booking.openhours
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [times.api]
            [tick.core :as t]
            [schpaa.debug :as l :refer [strp]]))

(defn getCoordinatesForPercent
  ([percent]
   (getCoordinatesForPercent percent 1))
  ([percent k]
   {:x (* k (Math/cos (* 2 Math/PI percent)))
    :y (* k (Math/sin (* 2 Math/PI percent)))}))

(defn pathData [{:keys [startX startY largeArcFlag endX endY]}]
  (strp "M" startX startY "A" 1 1 0 largeArcFlag 1 endX endY "L" 0 0))

(def colors
  [["var(--brand1)" "var(--lime-1)"]
   ["var(--cyan-7)" "var(--cyan-1)"]
   ["var(--yellow-7)" "var(--yellow-1)"]
   ["var(--orange-9)" "var(--orange-1)"]])

(defn timelane [colors aktiv region r]
  (let [colors (mapv (fn [[idx e]] (if (= idx @aktiv) e ["var(--text2)"]))
                     (map-indexed vector colors))
        show-start 6 show-end 24]
    [:svg
     {:class               [:timelane]
      :style               {:grid-area   (str "graph" region)
                            :display     :inline-block
                            :height      "100%"
                            ;:max-height  "3rem"
                            :xbackground "var(--toolbar)"}
      :viewBox             (str show-start " " 0 " " (- show-end show-start) " " 5)
      :preserveAspectRatio "none" :width "100%" :height "auto"}
     [:rect {:x     show-start
             :width (- show-end show-start) :height :100% :opacity 0.51
             :fill  "var(--toolbar-)"
             :rx    "0.25"}]
     (into [:<>] (for [idx (range 12 24 6)]
                   [:line {:vector-effect :non-scaling-stroke
                           :stroke-width  1
                           :stroke        "var(--floating)"
                           :x1            idx :y1 0 :x2 idx :y2 "100%"}]))
     (when (some? r)
       (into [:<>]
             (for [[idx [start end]] (map-indexed vector r)
                   :let [idx (+ idx 1)]]
               [:line {:vector-effect  :non-scaling-stroke
                       :stroke-linecap :round
                       :stroke-width   7
                       ;:opacity        (if (= (nth colors (- idx 1)) ["var(--text2)"]) 0.5 1)
                       :stroke         (first (nth colors (- idx 1)))
                       :x1             start :y1 idx :x2 end :y2 idx}])))]))


(defn cell [aktiv n color caption start' end']
  [:div {:on-click #(reset! aktiv n)
         :style    {:border-radius "var(--radius-0)"
                    :padding       "1rem"
                    :background    "var(--text3)"}}
   [:svg.w-16.h-16
    {:viewBox "-1 -1 2 2" :preserveAspectRatio "none"
     :style   {:transform "rotate(90deg)"}}
    (let [start (getCoordinatesForPercent start')
          end (getCoordinatesForPercent (+ start' end'))
          m {:startX       (:x start)
             :startY       (:y start)
             :endX         (:x end)
             :endY         (:y end)
             :largeArcFlag (if (<= 0.5 end') 1.0 0.0)}]
      [:path {:vector-effect :non-scaling-stroke
              :stroke        :white
              :stroke-width  1
              :fill          (if (= n @aktiv) color "var(--text2)")
              :d             (pathData m)}])]
   [sc/small1 caption]])

(defn mini-yearwheel [aktiv r]
  (r/with-let [st (r/atom false)]
    [:div
     {:on-click #(swap! st not)
      :class    [:wheels]
      :style    {:grid-area      "wheels"
                 :border-radius  "var(--radius-0)"
                 :padding-block  "var(--size-3)"
                 :padding-inline "var(--size-2)"
                 :background     "var(--toolbar)"}}
     [:svg
      {:viewBox             "-1 -1 2 2"
       :preserveAspectRatio "xMidYMid meet"
       :class               [:duration-500]
       :style               {:height    "100%"
                             :width     "100%"
                             :max-width "10rem"
                             :gap       "var(--size-1)"
                             :transform (if @st "rotate(-90deg)" "rotate(90deg)")}}
      (into [:<>]
            (concat
              [[:circle {:cx 0 :cy 0 :r 1 :fill "var(--content)"}]]
              (for [[n [start' end' [bg fg]]] (map-indexed vector r)]
                (let [start (getCoordinatesForPercent start')
                      end (getCoordinatesForPercent (+ start' end'))
                      m {:startX       (:x start)
                         :startY       (:y start)
                         :endX         (:x end)
                         :endY         (:y end)
                         :largeArcFlag (if (<= 0.5 end') 1.0 0.0)}]
                  [:path {:vector-effect :non-scaling-stroke
                          ;:stroke        "var(--text3)"
                          :stroke-width  1
                          :opacity       (if (= n @aktiv) 1 0.125)
                          :fill          bg                 ;(if (= n @aktiv) bg "var(--text2)")
                          :d             (pathData m)}]))
              [(let [{x1 :x y1 :y} (getCoordinatesForPercent (/ (times.api/yearday-number (t/date)) 365) 1)
                     {x2 :x y2 :y} (getCoordinatesForPercent (/ (times.api/yearday-number (t/date)) 365) 0.7)]
                 [:line {:vector-effect  :non-scaling-stroke
                         :stroke-width   4
                         :stroke-linecap :round
                         :stroke         "var(--toolbar)"
                         :x1             x1 :y1 y1 :x2 x2 :y2 y2}])]))]]))

(o/defstyled openinghourgrid :div
  [:&.wrapper
   {:display          :grid
    :grid-auto-column "1fr"
    :grid-auto-rows   "3rem"
    :gap              "var(--size-1) var(--size-2)"}
   [:at-media {:max-width "499px"}
    [:.timelane {:display    :none
                 :visibility :hidden}]
    {:grid-template-areas [["day0" "period0"]
                           ["day1" "period1"]
                           ["day2" "period2"]
                           ["day3" "period3"]
                           ["day4" "wheels"]
                           ["day5" "wheels"]
                           ["day6" "wheels"]]}]
   [:at-media {:min-width "500px"}
    [:.timelane {:display    :block
                 :visibility :visible}]
    {:grid-template-areas [
                           ["day0" "graph0" "period0"]
                           ["day1" "graph1" "period1"]
                           ["day2" "graph2" "period2"]
                           ["day3" "graph3" "period3"]
                           ["day4" "graph4" "wheels"]
                           ["day5" "graph5" "wheels"]
                           ["day6" "graph6" "wheels"]]}]])

(o/defstyled period-name :div
  [:& :flex :items-center :justify-between :w-full :h-full
   {:padding       "var(--size-2)"
    :background    "var(--toolbar)"
    :border-radius "var(--radius-0)"
    :cursor        :pointer}
   [:&.active
    {:background "var(--text2)"}]])

(defn day-names-with-status [aktiv n day r]
  [sc/col {:style {
                   :grid-area (str "day" n)}}
   [sc/text2 day]
   [sc/text1 {:style {:white-space :nowrap}}
    (if (or (nil? r) (not (contains? r @aktiv)))
      "Stengt"
      (apply str "kl " (interpose "—" (nth r @aktiv))))]])

(defn opening-hours []
  (r/with-let [aktiv (r/atom nil)]
    [openinghourgrid {:class [:wrapper]}
     (let [f #(let [a (times.api/yearday-number (t/date %1))
                    b (times.api/yearday-number (t/date %2))]
                [(/ a 365) (- (/ b 365) (/ a 365))])
           data [["2022-05-08" "2022-06-10" "Vår"]
                 ["2022-06-11" "2022-07-10" "Utvidet"]
                 ["2022-07-11" "2022-09-10" "Sommer"]
                 ["2022-09-11" "2022-10-12" "Høst"]]
           data' (map conj (map #(apply f %) data) colors)]
       [:<>
        (for [[idx [start end periodname [bg fg]]] (map-indexed vector (map conj data colors))
              :let [active? (= idx @aktiv)]]
          [period-name
           {:class    [(if active? :active) :wheels :w-full]
            :style    {:color            (if active? fg "var(--text1)")
                       :background-color (when active? bg)
                       :grid-area        (str "period" idx)}
            :on-click #(reset! aktiv idx)}
           [sc/col {:style {:width "100%"}}
            [sc/text1-cl {:style {:text-weight "var(--font-weight-6)"}} periodname]
            [:div.flex.justify-between.w-full.relative
             [sc/subtext-cl {:xclass [:tabular-nums :tracking-tighter]} (times.api/short-date-format (t/date start))]
             [sc/subtext-cl {:style {:font-family "Inter"
                                     :text-align  :center}
                             :class [:absolute :inset-0 :opacity-50]} "-->"]
             [sc/subtext-cl {:xclass [:tabular-nums :tracking-tighter]} (times.api/short-date-format (t/date end))]]]])

        (mini-yearwheel aktiv data')])

     #_(into [:<>] (map (fn [[n d]] (vector dag {:style {:align-self :center
                                                         :color      "var(--text2)"
                                                         :grid-area  (str "day" n)}} d)))
             (map-indexed vector ["Mandag" "Tirsdag" "Onsdag" "Torsdag" "Fredag" "Lørdag" "Søndag"]))
     (let [data [["Mandag" nil]
                 ["Tirsdag" [[18 21] [12 21] [18 21]]]
                 ["Onsdag" [[18 21] [18 21] [18 21] [17 20]]]
                 ["Torsdag" [[18 21] [12 21] [18 21]]]
                 ["Fredag" nil]
                 ["Lørdag" [[11 17] [11 17] [11 17] [11 17]]]
                 ["Søndag" [[11 17] [11 17] [11 17] [11 17]]]]]
       (for [[idx [day r]] (map-indexed vector data)]
         [day-names-with-status aktiv idx day r]))

     (let [f (partial timelane colors aktiv)]
       [:<>
        [f "0" nil]
        [f "1" [[18 21] [12 21] [18 21]]]
        [f "2" [[18 21] [18 21] [18 21] [17 20]]]
        [f "3" [[18 21] [12 21] [18 21]]]
        [f "4" nil]
        [f "5" [[11 17] [11 17] [11 17] [11 17]]]
        [f "6" [[11 17] [11 17] [11 17] [11 17]]]])]))