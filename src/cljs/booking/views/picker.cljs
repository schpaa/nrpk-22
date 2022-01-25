(ns booking.views.picker
  (:require [schpaa.components.views :as views]
            [schpaa.modal.readymade]

            [schpaa.components.fields :as fields :refer [save-ref placeholder]]
            [schpaa.components.views :as views :refer [goto-chevron general-footer]]
            [schpaa.components.views :refer [number-view slot-view normalize-kind name-view]]
            [tick.alpha.interval :refer [relation]]
            [logg.database]
            [reagent.core :as r]
            [times.api :refer [day-name day-number-in-year format]]
            [tick.core :as t]
            [schpaa.icon :as icon]
            [clojure.set :as set]
            [schpaa.debug :as l]
            [schpaa.modal :as modal]
            [fork.re-frame :as fork]
            [booking.time-navigator]
            [re-frame.core :as rf]
            [db.core :as db]
            [eykt.hov :as hov]))

(defn- booking-list-item-color-map [relation]
  (case relation
    ;past
    :precedes {:bg  ["dark:bg-black/30" "bg-gray-500" :text-gray-400]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}

    ;future
    :preceded-by {:bg  ["dark:bg-gray-100" "bg-gray-200"]
                  :fg  ["dark:text-black" "text-gray-700"]
                  :fg- ["dark:text-black/40" "text-gray-700/50"]}

    ;today
    {:bg  ["dark:bg-alt-600" "bg-green-100"]
     :fg  ["dark:text-black" "text-green-600"]
     :fg- ["dark:text-white"]}))

;region utilities (pure)

(defn after-and-including [today {:keys [start]}]
  (let [p (relation today start)]
    (tap> p)
    (some #{p} [:equals :starts :during :meets :precedes :contains])))

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
    [:div.relative.z-0.h-8.space-y-0.overflow-clip
     (when-some [date date]
       [:div.absolute.-bottom-1.left-0
        {:style {:left "25%" :width "25%"}}
        [:div.flex.flex-col.items-center
         [:svg.text-sky-500.h-2 {:viewBox "0 0 6 6"}
          ;[:g {:transform "translate(-10,0)"}]
          [:path {
                  :fill :currentColor
                  :d    (str "M " 3 " 0 l 3 6 l -6 0 z")}]]
         [:div.text-gray-700.text-xs.mb-px (t/format "dd/MM" (t/date date))]]])

     [:svg.w-full.rounded-sm.h-fullx.h-4
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

(defn- overlapping? [id time-slot offset]
  (let [booking-db (filter (partial has-selection id) (booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        overlapping? (pos? (count (filter (fn [{:keys [r?]}] (nil? r?)) status-list)))]
    overlapping?))

(defn- expanded-view [{:keys [appearance offset time-slot id data]}]
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


        {:keys [navn description location number
                slot expert kind]} data]
    [:<>
     [:div.grid.gap-2.py-2.px-2.w-full.text-blackx
      {:style {:grid-template-columns "min-content 1fr max-content min-content"
               :grid-auto-rows        "auto"}}

      [:div.self-center.justify-self-start
       {:class (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500])}
       (number-view number)]

      (if (some #{:tall} appearance)
        [:div.self-center.justify-self-start.space-y-0.truncate
         {:class (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500])}
         (name-view navn)
         [:div.text-xs (normalize-kind kind)]]

        [:div.self-center.justify-self-start.line-clamp-1.whitespace-nowrap.truncate
         {:class (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500])}
         (name-view navn)])

      [:div.col-span-1.h-6.self-center.max-w-xs
       (when (some #{:timeline} appearance)
         (when booking-db
           (draw-graph
             {:date      (t/beginning time-slot)
              :window    window
              :time-slot slot'
              :list      status-list})))]
      (when-not (some #{:hide-location} appearance)
        [:div.self-center.justify-self-end (slot-view slot)])

      (when (some #{:extra} appearance)
        [:<>
         [:div.col-span-2.self-start.text-sm.justify-self-start (normalize-kind kind)]
         [:div.col-span-2.self-start.text-sm.line-clamp-2 description]])]]))

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

    [:div.grid.gap-x-2.h-10x.w-fullx
     {:style {:grid-template-columns "3rem min-content 1fr 1fr 1fr"
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
     [:div.self-center.justify-self-center (number-view number)]
     [:div.self-center (slot-view slot)]
     [:div.self-center.truncate (name-view navn)]
     [:div.self-center.truncate (normalize-kind kind)]
     [:div.h-6.self-center.max-w-xs
      (when booking-db
        (draw-graph
          {:window    window
           :list      status-list
           :time-slot slot'}))]]))

(def list-color-map {:bg [:bg-gray-300 "dark:bg-gray-800"]})

(defn list-line [{:keys [overlap? selected? id on-click insert-before-line-item insert-after appearance] :as m}]
  [:div.grid.gap-px
   {:class
    (cond
      (or (some #{:unavailable} appearance)
          (some #{:error} appearance)
          overlap?) [:bg-red-200 :text-black :dark:bg-rose-500 :dark:text-white]
      selected? [:bg-alt :text-white]
      (some #{:clear} appearance) []
      :else [:bg-gray-100 :dark:bg-gray-700])
    :style {:grid-template-columns "min-content 1fr min-content"}}

   (if (and insert-before-line-item)
     [:div.flex.items-center.debug [insert-before-line-item id]]
     [:div])

   [:div
    {:on-click #(on-click id)}
    (expanded-view (assoc m :appearance (conj appearance #{:timeline})))]

   (if (and insert-after (fn? insert-after))
     (insert-after id)
     [:div])])

(defn boat-list [{:keys [boat-db selected only-show-selected? offset time-slot] :as m}]
  [:div.space-y-px.select-none.overflow-clip
   {:class (:bg list-color-map)}
   (doall (for [[id data] boat-db
                :when (if only-show-selected?
                        (some #{id} @selected)
                        true)]
            ^{:key (str id)}
            [list-line
             (conj m
                   {:appearance (if (some #{id} @selected) #{:timeline})
                    :selected?  (some #{id} @selected)
                    :id         id
                    :data       data})]))])

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

(defn values' [props path f]
  (assoc-in props path f))

(defn boat-picker
  "Find all entries with a relation of :precedes or preceded-by, all the
  other relations must fail. Since there isn't any allowance for abutting
  entries (1 hour minimum between each booking), :met-by and :meets are therefore
  rejected."
  [{:keys [values] :as props} {:keys [details? compact? graph? my-state boat-db selected]}]
  (let [;fixme You've done this many times now
        start (t/at (t/date (values :start-date)) (t/time (values :start-time)))
        end (t/at (t/date (values :end-date)) (t/time (values :end-time)))
        offset (if start (times.api/day-number-in-year (t/date start)) 0)
        slot (try
               (tick.alpha.interval/bounds start end)
               (catch js/Error _ nil))]
    [:div
     {:style {:min-height "calc(100vh - 22rem)"}
      :class ["dark:bg-gray-900" "bg-gray-500"]}

     [boat-list
      {:offset              offset
       :time-slot           slot
       :boat-db             boat-db
       :only-show-selected? @(rf/subscribe [:boatpickerlist/details])
       :selected            selected
       :insert-before       (fn [id]
                              (let [id #{id}]
                                (hov/toggle-selected'
                                  {:on?      (some id @selected)
                                   :on-click #(swap! selected
                                                     (fn [sel] (if (some id sel)
                                                                 (set/difference sel id)
                                                                 (set/union sel id))))})))

       :insert-after        hov/open-details
       :on-click            (fn [e] (swap! selected
                                           (fn [sel] (if (some #{e} sel)
                                                       (set/difference sel #{e})
                                                       (set/union sel #{e})))))}]]))


;todo rename "utvalg"
(rf/reg-sub :boatpickerlist/details :-> :boatpicker-list-details)

(rf/reg-event-db :boatpickerlist/set-details (fn [db [_ args]]
                                               (assoc db :boatpicker-list-details args)))

(defn boat-picker-footer []
  [:div.flex.justify-between.items-center.gap-2.px-4.sticky.bottom-0.h-16.shadow
   {:class [:bg-gray-400 :dark:bg-gray-800 :dark:text-white :text-black]}
   (schpaa.components.views/modern-checkbox'
     {:set-details #(rf/dispatch [:boatpickerlist/set-details %])
      :get-details #(-> (rf/subscribe [:boatpickerlist/details]) deref)}
     (fn [checkbox]
       [:div.flex.items-center.gap-4
        checkbox
        [:div.flex.flex-col
         [:div.font-medium "Utvalg"]
         [:div.text-xs "Begrens visning til utvalg"]]]))])


