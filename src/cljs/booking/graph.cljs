(ns booking.graph
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [schpaa.debug :as l]
            [reagent.core :as r]
            [tick.core :as t]
            [re-frame.core :as rf]))

(defn date-iter [start n]
  (lazy-seq
    (when-let [a (t/>> start (t/new-period n :days))]
      (cons a (date-iter start (inc n))))))

(o/defstyled svg-graph :svg
  [:& :h-32 :w-full :m-0])

(defn set-ref [a el]
  (when-not @a
    (reset! a el)))

(defn render-graph [{:keys [draw-fn
                            indexed-data first-day mode element days-back-in-time max-value
                            get-data-fn]}]
  (let [days-back-in-time @days-back-in-time]
    [svg-graph
     {:viewBox             (l/strp 0 0 days-back-in-time (+ max-value 15))
      :width               "100%"
      :height              "auto"
      :preserveAspectRatio "none"
      :ref                 (partial set-ref element)}
     (for [[x [dt datums]] indexed-data]
       [:<>
        (if (= dt first-day)
          [:line
           {:stroke           "var(--green-7)"
            :vector-effect    :non-scaling-stroke
            :stroke-width     2
            :stroke-dasharray "3 2"
            :x1               (- x 1)
            :y1               0
            :x2               (- x 1)
            :y2               max-value}])
        (when draw-fn
          (draw-fn dt x max-value get-data-fn))])

     ;arrow pointing to now
     [:path {:stroke        "var(--blue-5)"
             :fill          "var(--blue-5)"
             :vector-effect :non-scaling-stroke
             :d             (l/strp "m" (- days-back-in-time 1.5) (+ max-value 5) "l" 0.5 9 "l" -1 0 "z")}]]))

(defn lower-left [mode data]
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
     (str "(totalt=>" (reduce + 0 (map (comp first second) data)) ")")]]])

(defn corner-stats [get-data-fn end-date]
  (if get-data-fn
    (when-let [end-date (some-> end-date str)]
      [sc/row-sc-g4 
       [sc/small1-inline
        {:style {:font-weight "var(--font-weight-6)"}}
        (str (get-data-fn end-date))]
       [sc/small1-inline
        {:style {:color       "var(--blue-6)"
                 :cursor      :default
                 :font-weight "var(--font-weight-6)"}}
        (str (get-data-fn end-date))]])
    [sc/small {:class [:error]} "get-data-fn?"]))

(defn corner-day [end-date]
  (when-let [dt (some-> end-date t/date)]
    (let [future? (t/< (t/today) dt)]
      [sc/text
       {:style {;:text-transform "uppercase"
                :line-height    "1"
                :color          (if future? "var(--red-5)" "var(--blue-5)")
                :font-weight    "var(--font-weight-6)"}}
       (times.api/date-format dt)])))

(defn upper-left [first-day max-value]
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
     (str "(topp=" max-value ")")]]])

(defn graph [_dataset
             {:keys [c-days-back-in-time
                     c-xpos
                     c-offset
                     c-xstart
                     touchdown
                     mousedown
                     client-width]}
             reset-view-fn
             _get-data-fn
             _draw-fn]
  (let [{:keys [right-menu?]} @(rf/subscribe [:lab/screen-geometry])
        mode (r/atom 3)
        element (r/atom nil)]
    (r/create-class
      {:component-did-mount
       (fn [_]
         (let [round Math/round]
           (when @element
             (.addEventListener @element "touchstart"
                                #(let [x (.-pageX (aget (.-targetTouches %) 0))]
                                   (when @element
                                     (reset! client-width (.-clientWidth @element)))
                                   (reset! touchdown true)
                                   (reset! c-xstart (round x))))
             (.addEventListener @element "touchend"
                                (fn []
                                  (reset! c-offset (+ @c-offset @c-xpos))
                                  (reset! c-xpos 0)
                                  (reset! touchdown false)
                                  (if (t/< (t/today)
                                           (t/<< (t/today) (t/new-period (+ @c-offset @c-xpos) :days)))
                                    (reset-view-fn))))
             (.addEventListener @element "touchmove"
                                #(let [seg-width (when (pos? @client-width)
                                                   (/ @client-width @c-days-back-in-time))
                                       touches (aget (.-targetTouches %) 0)
                                       rel-pos-to-startx (- (.-pageX touches) @c-xstart)
                                       rel-x (when (pos? seg-width)
                                               (round (/ rel-pos-to-startx seg-width)))]
                                   (reset! c-xpos rel-x))))))
       :reagent-render
       (fn [dataset _ _reset-view-fn get-data-fn draw-fn]
         (let [end-date (t/<< (t/today) (t/new-period (+ (or @c-offset 0) (or @c-xpos 0)) :days))
               start-date (t/<< end-date (t/new-period @c-days-back-in-time :days))
               lookup (map str (date-iter start-date 1))
               data (take @c-days-back-in-time
                          (map #(vector % [(first (get dataset % [0 0]))
                                           (second (get dataset % [0 0]))])
                               lookup))
               max-value (max 50 (reduce max 0 (map (comp :boats val) dataset)))
               first-day (when-some [data data] (drop-while (comp zero? first second) data))]
           [:<>
            ;[l/pre-s (take 5 dataset)]
            [:div.w-full
             {:style {:background-color "var(--floating)"}}
             [sc/row {:style {:flex-direction (if right-menu? :row :row-reverse)}}
              [:div.relative.h-full.w-full
               {:style {:margin "0.5rem"}}
               [:div
                {:style {:background-color (when (or (when touchdown @touchdown)
                                                     (when mousedown @mousedown)) "var(--content)")}}
                (render-graph
                  {:draw-fn           draw-fn
                   :get-data-fn       get-data-fn
                   :indexed-data      (map-indexed vector data)
                   :element           element               ;;current selected data-item
                   :days-back-in-time c-days-back-in-time
                   :first-day         (ffirst first-day)
                   :max-value         max-value
                   :mode              mode})]]]]]))})))
