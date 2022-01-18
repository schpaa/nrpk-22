(ns booking.views.picker
  (:require [schpaa.components.views :as views]
            [schpaa.components.fields :as fields]
            [schpaa.components.views :as views :refer [goto-chevron general-footer]]
            [tick.alpha.interval :refer [relation]]
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

(defn normalize-kind [kind]
  (case kind
    "havkayak" "Havkayak"
    "sup" "Standup padlebrett"
    "grkayak" "Grønnlands\u00adkayak"
    "surfski" "Surfski"
    "?"))

(defn has-selection [id x]
  ;(tap> ["has-selection" id (first (:selected x))])
  (= id (first (:selected x))))

(defn- convert [dt]
  (when-let [dt (t/date-time dt)]
    (+ (* 24 (day-number-in-year (t/date dt)))
       (t/hour dt)
       #_(* 24 (f dt)))))

(defn number-view [slot]
  [:div.font-oswald.tracking-wider.text-xl.font-bold.leading-normal
   {:class ["dark:text-white" "text-black"]}
   slot])

(defn slot-view [slot]
  [:div.px-1.rounded-sm.text-base.py-px.font-oswald.font-normal.whitespace-nowrap.h-auto
   {:class ["dark:bg-amber-400" "dark:text-black"
            "bg-gray-900" "text-gray-300"]}
   slot])

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
        ["bg-gray-200"
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
      [:div.text-base.self-center.justify-self-start {:class ["dark:text-gray-100"]} navn]
      [:div.col-span-1.h-6.self-center.bg-gray-400
       (when booking-db
         (draw-graph
           {:window    window
            :list      status-list
            :time-slot slot'}))]

      [:div.col-span-2.self-start.text-sm.justify-self-start (normalize-kind kind)]
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
     {:style {:grid-template-columns "2.5rem 8rem 1fr"
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
      (number-view number)
      #_[:div.font-sans number]]
     [:div.text-base.font-sans.self-center.truncate
      {:class ["dark:text-gray-100"]}
      navn]

     [:div.bg-gray-400.h-6.self-center
      (when booking-db
        (draw-graph
          {:window    window
           :list      status-list
           :time-slot slot'}))]]))

(defn list-line [{:keys [selected?
                         offset time-slot details? data
                         id on-click remove? insert-before graph? compact?
                         insert-after]
                  :or   {graph? true}
                  :as   m}]
  [:div.flex
   {:class (if-not selected? [:ml-4])}
   (when insert-before insert-before)
   [:div
    {:on-click #(on-click id)}
    (if compact?
      (compact-view m)
      (expanded-view m))]])

(defn boat-list [{:keys [offset time-slot on-click boat-db selected] :as m}]
  (if (seq boat-db)
    [:div.space-y-px.select-none
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
  ;[l/ppre my-state]
  [:div.space-y-4
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

(defn toggle-favorite [id]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    (tap> ["toggle-fav" id (:uid @user-auth)])))

(defn modal-title
  "A view that is a presentation of the boat with some details and a Star to click on"
  [{:keys [toggle-favorite-fn read-db-fn]}]
  (let [star [:div {:on-click #(toggle-favorite-fn)
                    :class    ["text-amber-500"]} [icon/small :filled-star]]
        {:keys [slot number navn kind description]} (read-db-fn)]

    [:div.grid.gap-2.p-4.text-base.font-normal.w-full
     {:class ["dark:text-white" :text-black]
      :style {:grid-template-columns "3rem 1fr min-content"}}
     [:div.self-start.justify-self-center (number-view number)]
     [:div.leading-snug
      [:div.font-semibold.text-lg navn]
      [:div.text-sm.font-normal (normalize-kind kind)]]
     [:div (slot-view slot)]
     [:div.justify-self-center.self-start.pt-px star]
     [:div.col-span-2.font-normal.text-base description]]))

(defn modal-form [{:keys [on-close]}]
  [fork/form {:prevent-default?  true
              :initial-values    {:feilmelding ""}
              :clean-on-unmount? true
              :keywordize-keys   true
              :on-submit         (fn [{:keys [values]}]
                                   ;(on-close)
                                   (tap> ["save settings " values]))}
   (fn [{:keys [dirty handle-submit form-id values] :as props}]
     [:form
      {:id        form-id
       :on-submit handle-submit}
      [:div.space-y-4.p-4
       (fields/textarea (assoc props :placeholder "Kort beskrivelse") "Meld om feil" :feilmelding)
       ;(if dirty)
       [:div.flex.justify-end.gap-4
        [:button.btn.btn-free {:type     :button
                               :on-click on-close} "Avbryt"]
        [:button.btn.btn-danger {:on-click on-close
                                 :type     :submit} "Lagre"]]]])])

(defn insert-after-fn [id]
  ^{:key (str id)}
  [:div.w-10.flex.flex-center
   {:class    [:text-black "bg-gray-500/50"]
    :on-click #(do
                 ;intent Prevent selecting the item when clicking on the insert-after-button
                 (.stopPropagation %)
                 ;intent sends a config/declaration to the fsm to build the dialog and present it in a modal manner
                 (modal/form-action
                   {:primary   (fn [] [:button.btn.btn-danger {:type :submit} "Lagre"])
                    :secondary (fn [] [:button.btn.btn-free {:type :button} "Avbryt"])
                    :footer    [:div.text-xs.p-4 "hooter footer"]
                    :title     [modal-title {:read-db-fn         (fn [] (get (logg.database/boat-db) id))
                                             :toggle-favorite-fn (fn [] (toggle-favorite id))}]
                    :form-fn   modal-form}))}
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
    [:div.space-y-4
     [views/rounded-view
      {:flat 1}
      [time-navigator {:my-state my-state} props]]

     [views/rounded-view
      {:flat 1}
      [:div.space-y-4
       [:div.flex.justify-end.gap-2
        [:button.btn-small.btn-free {:type     :button
                                     :on-click #(reset! selected #{})} "Velg ingen"]
        [:button.btn-small.btn-free {:type     :button
                                     :on-click #(reset! selected (into #{} (keys boat-db)))} "Velg alle"]]
       [:div.-mx-4
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
                                                    (set/union sel #{e})))))})]]]]))
