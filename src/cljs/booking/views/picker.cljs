(ns booking.views.picker
  (:require [schpaa.components.views :as views]
            [schpaa.modal.readymade]
            [schpaa.components.fields :as fields :refer [save-ref placeholder]]
            [schpaa.components.views :as views :refer [goto-chevron general-footer]]
            [schpaa.components.views :refer [number-view slot-view normalize-kind name-view]]
            [tick.alpha.interval :refer [relation]]
            [logg.database]
            [reagent.core :as r]
            [times.api :refer [day-name day-number-in-year]]
            [tick.core :as t]
            [schpaa.icon :as icon]
            [clojure.set :as set]
            [schpaa.debug :as l]
            [schpaa.modal :as modal]
            [fork.re-frame :as fork]
            [re-frame.core :as rf]
            [db.core :as db]))

(defn- booking-list-item-color-map [relation]
  (case relation
    :preceded-by {:bg  ["dark:bg-gray-400" "bg-gray-300"]
                  :fg  ["dark:text-black" "text-black"]
                  :fg- ["dark:text-black/40" "text-black/40"]}

    :precedes {:bg  ["dark:bg-black/30" "bg-gray-300"]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}
    {:bg  ["dark:bg-sky-500" "bg-sky-200"]
     :fg  ["dark:text-black" "text-sky-600"]
     :fg- ["dark:text-white"]}))

;region utilities (pure)

(defn after-and-including [today {:keys [start]}]
  (let [p (relation today start)]
    (some #{p} [:equals :starts :during :meets :precedes])))

(defn- available? [slot boat]
  (try
    (tick.alpha.interval/relation boat slot)
    (catch js/Error t (.-message t))))



(defn has-selection [id x]
  ;(tap> ["has-selection" id (first (:selected x))])
  (= id (first (:selected x))))

(defn- convert [dt]
  (when-let [dt (t/date-time dt)]
    (+ (* 24 (day-number-in-year (t/date dt)))
       (t/hour dt)
       #_(* 24 (f dt)))))


;endregion

;region drawing (pure)

(defn- draw-graph [{:keys [window time-slot list]}]
  (let [{:keys [from to]} time-slot
        {:keys [width offset]} window
        d (+ 24 (- (+ 1 (* 24 (times.api/day-number-in-year (t/date)))
                      (t/hour (t/time)))
                   offset))]
    [:svg.w-full.rounded-sm.h-full
     {:viewBox             (str "0 0 " width " 10")
      :width               "auto"
      :height              "auto"
      ;xMinYMid meet
      :preserveAspectRatio "none"}
     [:g {:stroke :none}

      [:rect {:class  "text-gray-600"
              :fill   "currentColor"
              :x      from
              :width  (- to from)
              :y      0
              :height 5}]
      (into [:<>] (map (fn [{:keys [start end r?]}]
                         [:rect {:class  (if r? "text-black" "text-rose-500")
                                 :fill   :currentColor
                                 :x      start
                                 :width  (- end start)
                                 :y      5
                                 :height 5}])
                       list))
      [:path.text-white
       {:vector-effect :non-scaling-stroke
        :stroke-width  2
        :stroke        :currentColor
        :d             (apply str "M 0 10 " (repeat 4 " m 24 0 v -3 v 3 "))}]
      [:path.text-black
       {:vector-effect :non-scaling-stroke
        :stroke-width  2
        :stroke        :currentColor
        :d             (apply str "M " d #_(+ (t/hour (t/time)) (- from offset)) " 0 v 10")}]
      (let [n (inc (t/hour (t/time)))]
        [:path
         {:class         "text-amber-500/30"
          :vector-effect :non-scaling-stroke
          :fill          :currentColor
          :d             (apply str "M " (+ 24 n) " 10 h " (- 24 n) " v -12 h -" (- 24 n) " z")}])]]))

(defn- expanded-view [{:keys [selected?
                              offset time-slot id on-click remove? data insert-before graph? details? compact?
                              insert-after]}]
  (let [window {:width  (* 24 5)
                :offset (* 24 offset)}
        offset (* 24 (dec offset))
        slot' (when time-slot
                {:from (- (convert (t/beginning time-slot)) offset)
                 :to   (- (convert (t/end time-slot)) offset)})
        booking-db (filter (partial has-selection id) (booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        overlapping? (pos? (count (filter (fn [{:keys [r?]}] (nil? r?)) status-list)))

        {:keys [navn description location number
                slot expert kind]} data]

    [:div.grid
     {:class
      (if selected?
        (if overlapping?
          ["bg-rose-500/50"]
          ["bg-alt"
           "dark:bg-gray-700"
           "text-white"
           "hover:bg-alt/80"])
        ["bg-gray-50"
         "dark:bg-gray-500"
         "text-gray-700"
         "hover:bg-gray-200"])
      :style {:grid-template-columns "1fr min-content"}}
     [:div.grid.gap-2.p-2.w-full
      {
       :style {:grid-template-columns "min-content 3rem 1fr  1fr "
               :grid-auto-rows        "auto"}}

      [:div.self-center.justify-self-start
       {:class (if selected? :w-16 :w-12)}
       (number-view number)]
      [:div.self-center.justify-self-end (slot-view slot)]
      [:div.self-center.justify-self-start
       (name-view navn)]
      [:div.col-span-1.h-6.self-center.bg-gray-400
       (when booking-db
         (draw-graph
           {:window    window
            :list      status-list
            :time-slot slot'}))]

      (if details?
        [:div.col-span-2.self-start.text-sm.justify-self-start (normalize-kind kind)])
      (if details?
        [:div.col-span-2.self-start.text-sm description])]

     (when (and insert-after (fn? insert-after))
       (insert-after id))]))

(defn- compact-view [{:keys [selected?
                             offset time-slot id on-click remove? data insert-before graph? details? compact?
                             insert-after]}]
  (let [window {:width  (* 24 5)
                :offset (* 24 offset)}
        offset (* 24 (dec offset))
        slot' (when time-slot
                {:from (- (convert (t/beginning time-slot)) offset)
                 :to   (- (convert (t/end time-slot)) offset)})
        booking-db (filter (partial has-selection id) (booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        overlapping? (pos? (count (filter (fn [{:keys [r?]}] (nil? r?)) status-list)))
        {:keys [navn description location number
                slot expert kind]} data]
    [:div.grid.gap-x-2.h-10.w-full
     {:style {:grid-template-columns "3rem min-content 1fr 1fr 1fr min-content"
              :grid-auto-rows        "auto"}
      :class (if selected?
               (if overlapping?
                 ["bg-rose-500/50"]
                 ["bg-alt"
                  "dark:bg-gray-700"
                  "text-white"
                  "hover:bg-alt/80"])
               ["bg-gray-100"
                "dark:bg-gray-500"
                "text-gray-700"
                "hover:bg-gray-200"])}
     [:div.self-center.justify-self-end.font-oswald.text-xl.font-medium
      (number-view number)]
     [:div.self-center (slot-view slot)]
     [:div.text-base.font-sans.self-center.truncate
      {:class ["dark:text-gray-100"]}
      navn]

     [:div.self-center.truncate (normalize-kind kind)]

     [:div.bg-gray-400.h-6.self-center.max-w-xs
      (when booking-db
        (draw-graph
          {:window    window
           :list      status-list
           :time-slot slot'}))]
     (when (and insert-after (fn? insert-after))
       (insert-after id))]))

(defn list-line [{:keys [selected? id on-click  insert-before  compact?] :as m}]
  [:div
   {:class (concat [:first:rounded-t :overflow-clip]
                   ;fixme introduce a param to tell if we are to do selecting AT ALL
                   (if (some? selected?)
                     (if-not selected? [:ml-4] [:ml-2])))}
   (when insert-before insert-before)
   [:div
    {:on-click #(on-click id)}
    (if compact?
      (compact-view m)
      (expanded-view m))]])

(defn boat-list [{:keys [offset time-slot on-click boat-db selected] :as m}]
  (if (seq boat-db)
    [:div.space-y-px.select-none.overflow-clip

     (doall (for [[id {:keys [number] :as data}] boat-db]
              ^{:key (str id)}
              [list-line
               (conj m
                     {:selected? (some #{id} @selected)
                      :on-click  on-click
                      :id        id
                      :data      data})]))]

    [:div.h-12 "Alle er valgt"]))

;endregion drawing

(defn- button
  ([a disabled? c]
   [:button.bg-gray-200.shadow-inside
    {:class    (if disabled? [:text-gray-300])
     :type     :button
     :disabled disabled?
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)])
  ([a c]
   [:button.bg-gray-200.shadow-inside
    {:type     :button
     :disabled false
     :on-click a}
    (if (keyword? c) [schpaa.icon/small c] c)]))

(defn adjust [state f field d]
  (fn []
    (let [has-time? (try (t/time (get-in @state [:values field])) (catch js/Error _ false))]
      (if has-time?
        (swap! state
               #(-> %
                    (update-in [:values :start] (fn [e] (f (t/date-time e) (t/new-duration 1 d))))
                    (update-in [:values :end] (fn [e] (f (t/date-time e) (t/new-duration 1 d))))))
        (swap! state
               update-in [:values field] #(f (t/date %) (t/new-period 1 d)))))))

(defn time-navigator
  [{:keys [my-state]} props]
  [:div.space-y-4.p-4.bg-white
   [:div.grid.gap-4
    {:style {:grid-template-columns "3fr 2fr"
             :grid-auto-rows        "auto"}}
    [:div.grid.gap-1
     {:style {:grid-template-columns "repeat(4,1fr)"
              :grid-auto-rows        "3rem"}}
     (button (fn [] (let [dt (t/date-time)]
                      (tap> dt)
                      (swap! my-state #(-> %
                                           (update-in [:values :start] (constantly dt))
                                           (update-in [:values :end] (constantly dt))))))
             :rotate-left)
     [:div.col-span-3
      (fields/date
        (assoc props
          :class [:w-full :border-2 :border-black]
          :naked? true
          :values (fn [] (some-> @my-state :values :start t/date))
          :handle-change (fn [e] (let [date-str (-> e .-target .-value)
                                       sleepover (get-in @my-state [:values :sleepover])
                                       end (t/date-time (t/at (t/date date-str) (t/time (get-in @my-state [:values :end]))))
                                       dt (t/>> end (t/new-period (if sleepover 1 0) :days))
                                       _ (tap> end)
                                       ;
                                       time-str (try
                                                  (some-> @my-state
                                                          (get-in [:values :start])
                                                          t/time)
                                                  (catch js/Error _ nil))
                                       end-time-str (try
                                                      (some-> @my-state
                                                              (get-in [:values :end])
                                                              t/time)
                                                      (catch js/Error _ nil))]
                                   (swap! my-state #(-> %
                                                        (assoc-in [:values :start]
                                                                  (if time-str
                                                                    (t/at (t/date date-str) time-str)
                                                                    (t/date date-str)))
                                                        (assoc-in [:values :end]
                                                                  (if time-str
                                                                    (t/at (if sleepover
                                                                            (t/date dt)
                                                                            (t/date end)) end-time-str)
                                                                    (t/date dt))))))))

        "test" :name)]

     [:div]
     (button (adjust my-state t/<< :start :days) :chevron-left)
     (button (adjust my-state t/>> :start :days) :chevron-right)
     [:div]]


    (let [disabled? (not (some? (some-> @my-state :values :start)))]
      [:div.grid.gap-1
       {:style {:grid-template-columns "repeat(4,1fr)"
                :grid-auto-rows        "3rem"}}
       [:div.col-span-4 (fields/time {:naked?        true
                                      :class         [:w-full :border-2 :border-black]
                                      :values        (fn [] (some-> @views/my-state :values :start t/time))
                                      :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                                   date-time-str (get-in @views/my-state [:values :start])]
                                                               (swap! views/my-state assoc-in [:values :start]
                                                                      (t/at (t/date date-time-str) time-str))))}
                                     "" :name)]
       (let [
             f (fn [h] (let [time (t/new-time h 0)]
                         (swap! my-state update-in [:values :start] #(t/at (t/date %) time))))]
         [:<>
          (button #(f 9) disabled? "09")
          (button #(f 12) disabled? "12")
          (button #(f 15) disabled? "15")
          (button #(f 17) disabled? "17")])])

    [:div.flex.items-center
     (fields/checkbox
       (assoc props
         :handle-change (fn [evt]
                          (let [v (-> evt .-target .-checked)
                                end (t/date-time (get-in @my-state [:values :start]))
                                dt (t/>> end (t/new-period (if v 1 0) :days))]
                            (swap! my-state #(-> %
                                                 (assoc-in [:values :end] dt)
                                                 (assoc-in [:values :sleepover] v))))))

       "Overnatting" :sleepover)]
    (let [disabled? (not (some? (some-> @my-state :values :start)))]
      [:div.grid.gap-1
       {:style {:grid-template-columns "repeat(4,1fr)"
                :grid-auto-rows        "3rem"}}

       [:div.col-span-4 (fields/time {:naked?        true
                                      :disabled?     disabled?
                                      :class         [:w-full :border-2 :border-black]
                                      :values        (fn [] (some-> @views/my-state :values :end t/time))
                                      :handle-change (fn [e] (let [time-str (-> e .-target .-value)
                                                                   date-time-str (get-in @views/my-state [:values :end])]
                                                               (swap! views/my-state assoc-in [:values :end]
                                                                      (t/at (t/date date-time-str) time-str))))}
                                     "" :name)]
       (let [f (fn [h] (let [time (t/new-time h 0)
                             current-time (t/time (get-in @my-state [:values :end]))]
                         (swap! my-state update-in [:values :end] #(t/at (t/date %) time))))]
         [:<>
          (button #(f 9) disabled? "09")
          (button #(f 12) disabled? "12")
          (button #(f 15) disabled? "15")
          (button #(f 17) disabled? "17")])])]])

(defn insert-after-fn [id]
  ^{:key (str id)}
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-200"]
    :on-click #(do
                 ;intent Prevent selecting the item when clicking on the insert-after-button
                 (.stopPropagation %)
                 ;intent sends a config/declaration to the fsm to build the dialog and present it in a modal manner
                 (schpaa.modal.readymade/details-dialog-fn id))}
   (icon/small :three-vertical-dots)])

(defn boat-picker
  "Find all entries with a relation of :precedes or preceded-by, all the
  other relations must fail. Since there isn't any allowance for abutting
  entries (1 hour minimum between each booking), :met-by and :meets are therefore
  rejected."
  [{:keys [details? compact? graph?
           uid cancel on-submit booking-data'
           my-state boat-db selected]} props]
  (let [start (get-in @my-state [:values :start])
        end (get-in @my-state [:values :end])
        offset (if start (times.api/day-number-in-year (t/date start)) 0)
        slot (try
               (tick.alpha.interval/bounds start end)
               (catch js/Error _ nil))]
    [:div
     [time-navigator {:my-state my-state} props]

     [:div.flex.justify-end.gap-2.p-4.x-mx-4
      [:button.btn-small.btn-dim {:type     :button
                                  :on-click #(reset! selected #{})} "ingen"]
      [:button.btn-small.btn-dim {:type     :button
                                  :on-click #(reset! selected (into #{} (keys boat-db)))} "alle"]
      [:button.btn-small.btn-dim {:type     :button
                                  :on-click #(reset! selected (into #{} (keys boat-db)))} "siste"]]

     (boat-list
       {:graph?       graph?
        :compact?     compact?
        :details?     details?
        :offset       offset
        :time-slot    slot
        :boat-db      boat-db
        :selected     selected
        :insert-after insert-after-fn
        :on-click     (fn [e] (swap! selected
                                     (fn [sel] (if (some #{e} sel)
                                                 (set/difference sel #{e})
                                                 (set/union sel #{e})))))})]))
