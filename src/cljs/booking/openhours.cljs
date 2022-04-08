(ns booking.openhours
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [times.api]
            [tick.core :as t]))

(defn getCoordinatesForPercent [percent]
  {:x (Math/cos (* 2 Math/PI percent))
   :y (Math/sin (* 2 Math/PI percent))})

(defn pathData [{:keys [startX startY largeArcFlag endX endY]}]
  (str "M" startX " " startY " A 1 1 0 " largeArcFlag " 1 " endX " " endY " L 0 0 Z"))

(defn timerange [aktiv region r]
  (let [colors (mapv (fn [[idx e]] (if (= idx @aktiv) e "var(--text2)"))
                     (map-indexed vector [:green :blue :orange :brown]))
        show-start 10 show-end 22]
    [:<>
     [:svg
      {:style               {:grid-area     (str "graph" region)
                             :display       :inline-block
                             :height        "3rem"
                             :border-radius "var(--radius-0)"
                             :background    "var(--floating)"}
       :viewBox             (str show-start " " 0 " " (- show-end show-start) " " 5)
       :preserveAspectRatio "none" :width "100%" :height "auto"}
      (into [:<>] (for [idx (range 6 24 6)]
                    [:line {:vector-effect :non-scaling-stroke
                            :stroke-width  1
                            :stroke        "var(--toolbar)"
                            :x1            idx :y1 0 :x2 idx :y2 "100%"}]))
      (when (some? r)
        (into [:<>]
              (for [[idx [start end]] (map-indexed vector r)
                    :let [idx (+ idx 1)]]
                [:line {:vector-effect  :non-scaling-stroke
                        :stroke-linecap :round
                        :stroke-width   7
                        :opacity        (if (= (nth colors (- idx 1)) "var(--text2)") 0.5 1)
                        :stroke         (nth colors (- idx 1))
                        :x1             start :y1 idx :x2 end :y2 idx}])))]
     (sc/text1 {:style {:grid-area      (str "status" region)
                        :align-self     :center
                        :padding-inline "var(--size-2)"
                        :white-space    :nowrap}}
               (if (or (nil? r) (not (contains? r @aktiv)))
                 "Stengt"
                 (apply str "kl " (interpose "—" (nth r @aktiv)))))]))

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

(defn cell2 [aktiv r caption]
  [:div
   {:style {:display        :flex
            :width          "7rem"
            :min-width      "7rem"
            :flex-direction :column
            :grid-area      "graph"
            :gap            "var(--size-1)"
            :border-radius  "var(--radius-0)"
            :padding        "var(--size-1)"
            :background     "var(--text3)"}}
   [:svg
    {:viewBox             "-1 -1 2 2"
     :preserveAspectRatio "xMinYMin slice"
     :style               {:transform "rotate(90deg)"}}
    (into [:<>]
          (concat
            [[:circle {:cx 0 :cy 0 :r 1 :fill "var(--text2)"}]]
            (for [[n [start' end' color]] (map-indexed vector r)]
              (let [start (getCoordinatesForPercent start')
                    end (getCoordinatesForPercent (+ start' end'))
                    m {:startX       (:x start)
                       :startY       (:y start)
                       :endX         (:x end)
                       :endY         (:y end)
                       :largeArcFlag (if (<= 0.5 end') 1.0 0.0)}]
                [:path {:vector-effect :non-scaling-stroke
                        :stroke        "var(--text3)"
                        :stroke-width  1
                        :fill          (if (= n @aktiv) color "var(--text2)")
                        :d             (pathData m)}]))))]
   caption])

(o/defstyled openinghourgrid :div
  {:display             :grid
   :gap                 "var(--size-2) var(--size-2)"
   :grid-template-areas [["day0" "status0" "graph0" "period0"]
                         ["day1" "status1" "graph1" "period1"]
                         ["day2" "status2" "graph2" "period2"]
                         ["day3" "status3" "graph3" "period3"]
                         ["day4" "status4" "graph4" "graph"]
                         ["day5" "status5" "graph5" "graph"]
                         ["day6" "status6" "graph6" "date-range"]]})

(o/defstyled period-name :div
  :h-full :flex :items-center :justify-center :w-full
  {:background    "var(--text3)"
   :border-radius "var(--radius-0)"
   :cursor        :pointer
   :align-self    :center}
  [:&.active
   {:background "var(--text2)"}])

(defn opening-hours []
  (r/with-let [aktiv (r/atom 1)]
    (let [dag #(sc/text1 %1 %2)]

      [:div.space-y-4
       [sc/fp-header "Åpningstider 2022"]

       [:div.ml-4
        [openinghourgrid
         (let [f #(let [a (times.api/yearday-number (t/date %1))
                        b (times.api/yearday-number (t/date %2))]
                    [(/ a 365) (- (/ b 365) (/ a 365))])
               data [["2022-05-08" "2022-06-10" "Vår"]
                     ["2022-06-11" "2022-07-10" "Utvidet"]
                     ["2022-07-11" "2022-09-10" "Sommer"]
                     ["2022-09-11" "2022-10-12" "Høst"]]
               data' (map conj (map #(apply f %) data) [:green :blue :orange :brown])]

           [:<>
            (for [[idx [start end periodname]] (map-indexed vector data)
                  :let [active? (= idx @aktiv)]]
              [period-name
               {:class    [(if active? :active)]
                :style    {:grid-area (str "period" idx)}
                :on-click #(reset! aktiv idx)}
               [sc/text1 periodname]])
            (let [[start end] (when (contains? data @aktiv)
                                (nth data @aktiv))]
              [:<>
               (cell2 aktiv data'
                      (when (and start end)))
               [period-name
                {:style {:cursor    :default
                         :grid-area "date-range"}}
                [sc/text1
                 (times.api/short-date-format (t/date start))
                 "—" (times.api/short-date-format (t/date end))]]])])


         (into [:<>] (map (fn [[n d]] (vector dag {:style {:align-self :center
                                                           :grid-area  (str "day" n)}} d)))
               (map-indexed vector ["Mandag" "Tirsdag" "Onsdag" "Torsdag" "Fredag" "Lørdag" "Søndag"]))

         [timerange aktiv "0" nil]
         [timerange aktiv "1" [[18 21] [12 21] [18 21]]]
         [timerange aktiv "2" [[18 21] [18 21] [18 21] [17 20]]]
         [timerange aktiv "3" [[18 21] [12 21] [18 21]]]
         [timerange aktiv "4" nil]
         [timerange aktiv "5" [[11 17] [11 17] [11 17] [11 17]]]
         [timerange aktiv "6" [[11 17] [11 17] [11 17] [11 17]]]]]])))


