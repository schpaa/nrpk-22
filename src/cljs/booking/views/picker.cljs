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
            [db.core :as db]
            [eykt.hov :as hov]))

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

(defn- draw-graph [{:keys [date window time-slot list]}]
  (let [{:keys [from to]} time-slot
        {:keys [width offset]} window
        d (+ 24 (- (+ 1 (* 24 (times.api/day-number-in-year (t/date)))
                      (t/hour (t/time)))
                   offset))]
    [:div.relative.z-0.h-8.space-y-0
     (when-some [date date]
       [:div.absolute.-bottom-1.left-0
        {:style {:left "25%" :width "25%"}}
        [:div.flex.flex-col.items-center
         [:svg.text-sky-500.h-2 {:viewBox "0 0 6 6"}
          [:path {:fill :currentColor
                  :d    "M 3 0 l 3 6 l -6 0 z"}]]
         [:div.text-gray-700.text-xs (t/format "dd/MM" (t/date date))]]])

     [:svg.w-full.rounded-sm.overflow-clip.h-fullx.h-4
      {:class               ["bg-gray-300" "dark:bg-gray-800"]
       :viewBox             (str "0 0 " width " 10")
       :width               "auto"
       :height              "100%"
       :preserveAspectRatio "none"}

      [:g {:stroke :none}
       [:rect {:class  ["text-gray-600" "dark:text-gray-500"]
               :fill   "currentColor"
               :x      from
               :width  (- to from)
               :y      5
               :height 4}]
       (into [:<>] (map (fn [{:keys [start end r?]}]
                          [:rect {:class  (if r? ["text-alt"] ["text-rose-500" "dark:text-rose-600"])
                                  :fill   :currentColor
                                  :x      start
                                  :width  (- end start)
                                  :y      0
                                  :height 4}])
                        list))
       [:path
        {:class         ["dark:text-gray-600"
                         "text-gray-100"]
         :vector-effect :non-scaling-stroke
         :stroke-width  2
         :stroke        :currentColor
         :d             (str
                          (apply str "M 0 0 " (repeat 3 " m 24 0 v 3 v -3 "))
                          (apply str "M 0 10 " (repeat 3 " m 24 0 v -3 v 3 ")))}]
       ;intent TODAY
       [:path
        {:class         ["dark:text-amber-500" "text-amber-700"]
         :vector-effect :non-scaling-stroke
         :stroke-width  2
         :stroke        :currentColor
         :d             (apply str "M " d " 0 v 10")}]
       ;intent REST OF THE DAY
       (let [n (inc (t/hour (t/time)))]
         [:path
          {:class         "text-amber-500/30"
           :vector-effect :non-scaling-stroke
           :fill          :currentColor
           :d             (apply str "M " d " 10 h " (- 24 n) " v -12 h -" (- 24 n) " z")}])]
      #_[:text {:x 0 :y 5 :style {:font "normal 7px sans-serif"}} (t/format "dd/MM" (t/date date))]]]))

(defn- expanded-view [{:keys [selected?
                              offset time-slot id on-click remove? data insert-before graph? details? compact?
                              insert-after]}]
  (let [window {:width  (* 24 4)
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
        ["bg-gray-100"
         "dark:bg-gray-700"
         "text-gray-400"
         "hover:bg-gray-50"])
      :style {:grid-template-columns "1fr min-content"}}
     [:div.grid.gap-2.p-2.w-full
      {
       :style {:grid-template-columns "min-content 3rem 1fr  1fr "
               :grid-auto-rows        "auto"}}

      [:div.self-center.justify-self-start
       {:class (if selected? :w-16 :w-12)}
       (number-view number)]
      [:div.self-center.justify-self-end (slot-view slot)]
      [:div.self-center.justify-self-start.line-clamp-1 (name-view navn)]
      [:div.col-span-1.h-6.self-center.max-w-xs
       (when booking-db
         (draw-graph
           {:date (t/beginning time-slot)
            :window    window
            :list      status-list
            :time-slot slot'}))]

      (if details?
        [:div.col-span-2.self-start.text-sm.justify-self-start (normalize-kind kind)])
      (if details?
        [:div.col-span-2.self-start.text-sm.line-clamp-2 description])]

     (when (and insert-after (fn? insert-after))
       (insert-after id))]))

(defn- compact-view [{:keys [selected?
                             offset time-slot id on-click remove? data insert-before graph? details? compact?
                             insert-after]}]
  (let [window {:width  (* 24 4)
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
                "dark:bg-gray-700"
                "text-gray-400"
                "hover:bg-gray-200"])}
     [:div.self-center.justify-self-center
      (number-view number)]
     [:div.self-center (slot-view slot)]
     [:div.self-center.truncate
      (name-view navn)]

     [:div.self-center.truncate (normalize-kind kind)]

     [:div.h-6.self-center.max-w-xs
      (when booking-db
        (draw-graph
          {:window    window
           :list      status-list
           :time-slot slot'}))]
     (when (and insert-after (fn? insert-after))
       (insert-after id))]))

(defn list-line [{:keys [selected? id on-click insert-before compact?] :as m}]
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

(defn naked [props]
  (assoc props :naked? true))

(defn values' [props path f]
  (assoc-in props path f))

(def cuty-color-map
  {:active-toggled [:active:bg-gray-700
                    :active:border-gray-600]
   :active [:active:bg-gray-300
            :active:border-gray-200]
   :normal    [:bg-gray-200
               :border-gray-300
               :dark:bg-gray-800
               :dark:border-gray-800
               :dark:text-gray-400]
   :toggled   [:bg-gray-800
               :text-gray-200
               :border-gray-800]
   :untoggled [:bg-gray-200
               :text-gray-700
               :border-gray-200]
   :disabled  [:disabled:bg-gray-100
               :disabled:text-gray-200
               :disabled:border-gray-200]})

(defn cuty [icon & {:keys [action disabled state]}]
  (let [cm cuty-color-map]
    [:button.m-0.p-0.w-10.h-full.border-2.rounded.grid.place-content-center
     {:class    (concat
                  (:disabled cm)
                  (if (some? state)
                    (if state
                      (concat (:toggled cm) (:active-toggled cm))
                      (concat (:untoggled cm) (:active-toggled cm)))
                    (concat (:active cm) (:normal cm))))


      :disabled disabled
      :on-click action
      :type     :button}
     (if (string? icon)
       [:div.text-xl.font-semibold.antialiased icon]
       [:div.w-6.h-6 (schpaa.icon/adapt icon 1.5)])]))

(defn time-navigator
  [{:keys [my-state]} {:keys [set-values values] :as props}]
  [:div.sticky.top-32.h-32.w-full.flex.items-center.justify-between.z-50.shadow
   {:class [:bg-gray-100 :dark:bg-gray-700]}
   [:div.grid.w-full.p-4.gap-2
    {:style {:grid-template-columns "1fr 1fr"}}
    [:div.justify-self-end (fields/date (-> props
                                            fields/normal-field
                                            naked
                                            (assoc :values (fn [] (t/date (get-in @my-state [:values :start]))))
                                            (assoc :handle-change
                                                   (fn [e]
                                                     (let [length (t/days (t/duration (tick.alpha.interval/bounds (values :start) (values :end))))
                                                           _ (tap> length)
                                                           v (-> e .-target .-value)
                                                           date (t/date v)
                                                           end-date (values :end)
                                                           time (t/time (get-in @my-state [:values :start]))
                                                           n (t/at date time)]

                                                       (when (t/<= (t/date) (t/date n))
                                                         (swap! my-state #(-> %
                                                                              (assoc-in [:values :start] n)
                                                                              (assoc-in [:values :end]
                                                                                        (t/at (t/>> date (t/new-period length :days)) (t/time end-date))))))))))
                                        "a" :start)]
    [:div.flex.gap-2.items-center
     (fields/time (-> props
                      naked
                      fields/small-field
                      (assoc :values (fn [] (some-> (get-in @my-state [:values :start]) t/time)))
                      (assoc :handle-change (fn [e]
                                              (let [vt (-> e .-target .-value)]
                                                (if (empty? vt)
                                                  (let [time (t/>> (t/truncate (t/time) :hours) (t/new-duration 1 :hours))
                                                        date (t/date (get-in @my-state [:values :start]))]
                                                    (swap! my-state assoc-in [:values :start]
                                                           (t/at date time)))
                                                  (let [time (t/time vt)
                                                        date (t/date (get-in @my-state [:values :start]))]
                                                    (swap! my-state assoc-in [:values :start]
                                                           (t/at date time))))))))
                  "b" :start)
     (cuty :none)
     (cuty :none)]
    [:div.self-center.justify-self-end.flex.gap-2.items-center.h-full
     (cuty "Nå" :action
           #(set-values {:start (t/at (t/date) (t/>> (t/truncate (t/time) :hours) (t/new-duration 1 :hours)))
                         :end   (t/at (t/date)
                                      (t/time (values :end)))})
           #_#(swap! my-state assoc-in [:values :start] (t/date-time)))
     (cuty "+1"
           :disabled (not (some-> @my-state :values :start))
           :action (fn []
                     (swap! my-state
                            #(-> %
                                 (update-in [:values :start]
                                            (fn [e]
                                              (let [time (t/time e)
                                                    v (t/>> (t/date e) (t/new-period 1 :days))]
                                                (t/at v time))))
                                 (update-in [:values :end]
                                            (fn [e]
                                              (let [time (t/time e)
                                                    v (t/>> (t/date e) (t/new-period 1 :days))]
                                                (t/at v time))))))))

     (cuty "+5"
           :action #(set-values {:start (->>
                                          (t/time (values :start))
                                          (t/at (t/>> (t/date (values :start)) (t/new-period 5 :days))))
                                 :end (->>
                                        (t/time (values :end))
                                        (t/at (t/>> (t/date (values :end)) (t/new-period 5 :days))))}))
     (let [on-state (t/duration (tick.alpha.interval/new-interval
                                  (t/date (values :start))
                                  (t/date (values :end))))]
       (cuty :moon-2
             :state (< 1 (t/days on-state))
             :action #(set-values {:end (if (< 1 (t/days on-state))
                                          (t/at (t/date (values :start)) (t/time (values :end)))
                                          (t/at (t/>> (t/date (values :start)) (t/new-period 1 :days))
                                                (t/time (values :end))))})))]


    [:div.flex.gap-2.items-center
     (fields/time (-> props
                      naked
                      fields/small-field
                      (assoc :values (fn [] (t/time (get-in @my-state [:values :end]))))
                      (assoc :handle-change (fn [e]
                                              (let [vt (-> e .-target .-value)]
                                                (if (empty? vt)
                                                  (let [from-time (t/time (get-in @my-state [:values :start]))
                                                        time (t/>> (t/truncate from-time :hours) (t/new-duration 1 :hours))

                                                        date (t/date (get-in @my-state [:values :start]))]
                                                    (swap! my-state assoc-in [:values :end]
                                                           (t/at date time)))
                                                  (let [vt (-> e .-target .-value)
                                                        time (t/time vt)
                                                        date (t/date (get-in @my-state [:values :end]))]
                                                    (swap! my-state assoc-in [:values :end]
                                                           (t/at date time)))))))) "" :end)
     (cuty "A")
     (cuty "B")]]]



  #_[:div.space-y-4.p-4
     {:class ["bg-gray-100"]}
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
     {:class ["dark:bg-gray-800" "bg-gray-300"]}

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
        :insert-after hov/open-details
        :on-click     (fn [e] (swap! selected
                                     (fn [sel] (if (some #{e} sel)
                                                 (set/difference sel #{e})
                                                 (set/union sel #{e})))))})]))
