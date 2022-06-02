(ns booking.graph
  (:require [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [reagent.core :as r]
            [tick.core :as t]))

(defn date-iter [start n]
  (lazy-seq
    (when-let [a (t/>> start (t/new-period n :days))]
      (cons a (date-iter start (inc n))))))

(defn graph [dataset top-row st reset-view]
  (let [mode (r/atom 3)
        a (r/atom nil)
        client-width (r/cursor st [:client-width])
        touchdown (r/cursor st [:touchdown])
        mousedown (r/cursor st [:mousedown])
        xstart (r/cursor st [:xstart])
        offset (r/cursor st [:offset])
        xpos (r/cursor st [:xpos])
        days-back-in-time (r/cursor st [:days-back-in-time])]
    (r/create-class
      {:component-did-mount
       (fn [_]
         (let [round Math/round]
           (when @a
             (.addEventListener @a "touchstart"
                                #(let [x (.-pageX (aget (.-targetTouches %) 0))]
                                   (when @a
                                     (reset! client-width (.-clientWidth @a)))
                                   (reset! touchdown true)
                                   (reset! xstart (round x))))
             (.addEventListener @a "touchend"
                                (fn []
                                  (reset! offset (+ @offset @xpos))
                                  (reset! xpos 0)
                                  (reset! touchdown false)
                                  (if (t/< (t/today)
                                           (t/<< (t/today) (t/new-period (+ @offset @xpos) :days)))
                                    (reset-view))))
             (.addEventListener @a "touchmove"
                                #(let [seg-width (when (pos? @client-width)
                                                   (/ @client-width @days-back-in-time))
                                       touches (aget (.-targetTouches %) 0)
                                       rel-pos-to-startx (- (.-pageX touches) @xstart)
                                       rel-x (when (pos? seg-width)
                                               (round (/ rel-pos-to-startx seg-width)))]
                                   ;(tap> :touchmove)
                                   (reset! xpos rel-x)
                                   #_(.preventDefault %)))
             #_(.addEventListener @a "mousedown"
                                  #(let [x (.-offsetX %)]
                                     (when @a
                                       (reset! clientWidth (.-clientWidth @a)))
                                     (reset! mousedown true)
                                     (reset! xstart (round x))
                                     (if (< (/ @clientWidth 2) x)
                                       (swap! xpos dec)
                                       (swap! xpos inc))))
             #_(.addEventListener @a "mouseup"
                                  (fn []
                                    (reset! mousedown false))))))

       :reagent-render
       (fn [dataset top-row st reset-view]
         (let [end-date (t/<< (t/today) (t/new-period (+ @offset @xpos) :days))
               start-date (t/<< end-date (t/new-period @days-back-in-time :days))
               lookup (map str (date-iter start-date 1))
               data (take @days-back-in-time (map #(vector % [(first (get dataset % [0]))
                                                              (second (get dataset % [0]))]) lookup))
               max-value (max 50 (reduce max 0 (map (comp first second) dataset)))
               first-day (when-some [data data] (drop-while (comp zero? first second) data))
               w @days-back-in-time]
           [sc/surface-a
            {:style {:padding       "0.5rem"
                     :border-radius "var(--radius-0)"
                     :box-shadow    "var(--shadow-2)"}}
            [sc/co
             [:div.relative.h-full

              [:div.absolute.left-0.top-0
               [sc/col
                (when-some [dt (some-> first-day ffirst t/date)]
                  (let [future? (t/< dt (t/today))]
                    [sc/small1-inline
                     {:style {:font-weight "var(--font-weight-6)"
                              :color       (if future? "var(--green-9)" "var(--red-6)")}}
                     (times.api/date-format dt)]))
                [sc/small1-inline
                 {:style {:font-weight "var(--font-weight-6)"}}
                 (str "(topp=" max-value ")")]]]

              [:div.absolute.right-0.top-0
               (when-let [dt (some-> end-date t/date)]
                 (let [future? (t/< (t/today) dt)]
                   [sc/small1
                    {:style {:line-height 1
                             :align-self  :start
                             :color       (if future? "var(--red-5)" "var(--blue-5)")
                             :font-weight "var(--font-weight-6)"}}
                    (times.api/date-format dt)]))]

              [:div.absolute.right-0.bottom-0
               [sc/row-sc-g4
                [sc/small1-inline
                 {:style {:font-weight "var(--font-weight-6)"}}
                 (let [[a [_ _ _ e]] (get dataset (-> end-date str))]
                   a
                   #_(apply str (interpose ", " [a (str e)])))]
                [sc/small1-inline
                 {;:on-click #(swap! mode (fn [e] (mod (inc e) 4)))
                  :style {:color       "var(--blue-6)"
                          :cursor      :default
                          :font-weight "var(--font-weight-6)"}}
                 (let [[a [b c d _]] (get dataset (-> end-date str))]
                   (when a
                     [:div.flex.gap-1 (map (fn [e] [:div.w-6 (if (zero? e) "—" e)]) [b c d])]))]]]

              [:div.absolute.left-0.bottom-0
               [sc/col
                [sc/small1-inline
                 {:on-click #(do
                               (swap! mode (fn [e] (mod (inc e) 4)))
                               (.preventDefault %)
                               (.stopPropagation %))
                  :style    {:cursor      :default
                             :color       "var(--orange-6)"
                             :font-weight "var(--font-weight-6)"}}
                 (case @mode
                   0 "antall utlån"
                   1 "antall voksne"
                   2 "antall ungdom"
                   3 "antall barn")]
                [sc/small1-inline
                 {:style {:font-weight "var(--font-weight-6)"}}
                 (str "(totalt=>" (reduce + 0 (map (comp first second) data)) ")")]]]

              [:div
               {:style {:background-color (when (or @mousedown @touchdown) "var(--content)")}}
               [:svg.h-32.w-full.m-0.py-8
                {:ref                 (fn [el]
                                        (when-not @a
                                          (reset! a el)
                                          (tap> ["ref-set" @a])))
                 :viewBox             (l/strp 0 0 @days-back-in-time (+ max-value 20))
                 :width               "100%"
                 :height              "auto"

                 :preserveAspectRatio "none"}

                (for [[idx [dt [cnt [adults juveniles children b]]]] (map-indexed vector data)
                      :let [x idx
                            weekend? (some #{(t/int (t/day-of-week dt))} [6 7])]]
                  (if (= dt (ffirst first-day))
                    [:line
                     {:stroke           "var(--green-7)"
                      :vector-effect    :non-scaling-stroke
                      :stroke-width     2
                      :stroke-dasharray "2 2"
                      :x1               (- x 0.5) :y1 0
                      :x2               (- x 0.5) :y2 max-value}]
                    [:<>
                     [:line
                      {:stroke       (if weekend? "var(--text1)" "var(--text3)")
                       :stroke-width 0.85
                       :x1           (- x 0.5) :y1 (- max-value cnt)
                       :x2           (- x 0.5) :y2 max-value}]

                     [:line
                      {:vector-effect :non-scaling-stroke
                       :stroke        "var(--orange-6)"
                       :stroke-width  4
                       :x1            (- x 0.5)
                       :x2            (- x 0.5)
                       :y1            (- max-value (case @mode
                                                     0 b
                                                     1 adults
                                                     2 juveniles
                                                     3 children))
                       :y2            max-value}]]))

                ;arrow pointing to now
                [:path {:stroke        "var(--blue-5)"
                        :fill          "var(--blue-5)"
                        :vector-effect :non-scaling-stroke
                        :d             (l/strp "m" (- w 1.5) (+ max-value 5) "l" 0.5 9 "l" -1 0 "z")}]]]]
             [sc/col
              [:div.p-2.-mx-2.-mb-2
               {:style {:background-color "rgba(0,0,0,0.06)"}}
               top-row]]]]))})))
