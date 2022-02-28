(ns booking.views.picker
  (:require [schpaa.modal.readymade]
            [schpaa.components.views :refer [number-view slot-view normalize-kind name-view]]
            [tick.alpha.interval :refer [relation]]
            [logg.database]
            [times.api :refer [day-name day-number-in-year format]]
            [tick.core :as t]
            [clojure.set :as set]
            [schpaa.state]
            [booking.time-navigator]
            [re-frame.core :as rf]
            [nrpk.hov :as hov]
            [schpaa.style :as st]
            [schpaa.button :as bu]
            [schpaa.modal.readymade :as readymade]
            [schpaa.style.ornament :as sc]))

;region utilities (pure)

(defn after-and-including [today {:keys [start]}]
  (let [p (relation today start)]
    ;(tap> p)
    (some #{p} [:equals :starts :during :meets :precedes :contains])))

(defn- available? [slot boat]
  (try
    (tick.alpha.interval/relation boat slot)
    (catch js/Error t (.-message t))))

;fixme Only considers the first in the list, should consider all
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
    [:div.flex.flex-col.relative.h-full.w-full.shadow-md
     ;:div.relative.z-0.h-full.space-y-0.overflow-clip.-debug.shrink-0

     [:svg
      {:class               [:h-full]
       :viewBox             (str "0 0 " width " 10")
       :width               "100%"
       :height              "auto"
       :preserveAspectRatio "none"}

      ;intent SOLID BACKGROUND
      [:g {:style {:color "var(--surface0)"}}
       [:rect {:fill :currentColor
               :x    0 :y 0 :width width :height 10}]]

      [:g {:stroke :none}
       [:rect {
               :style  {:color "var(--brand1)"}
               :fill   "currentColor"
               :x      from
               :width  (- to from)
               :y      5
               :height 4}]

       (into [:<>] (map (fn [{:keys [start end r?]}]
                          [:rect {:style  {:color (if r? "var(--surface-5)" "var(--red-5)")}
                                  :fill   :currentColor
                                  :x      start
                                  :width  (- end start)
                                  :y      0
                                  :height 4}])
                        list))

       ;intent REST OF THE DAY
       (let [n (inc (t/hour (t/time)))]
         [:path
          {:class         "text-amber-500"
           :vector-effect :non-scaling-stroke
           :fill          :currentColor
           :d             (apply str "M " d " 14 h " (- 24 n) " v -4 h -" (- 24 n) " z")}])]]

     (when-some [date date]
       [:div.relative.h-full
        [:div.absolute.inset-0
         {:style {:display               :grid
                  :grid-template-columns "repeat(4,1fr)"}}
         (for [e (range 4)
               :let [s (t/format "dd/MM" (t/>> date (t/new-period (dec e) :days)))]]
           [sc/small
            {:style {:display       :grid
                     :justify-items :center
                     :align-items   :end}}
            (if (zero? e) [sc/dim s] s)])]

        [:div.absolute.inset-0
         [:svg {:class               [:h-full]
                :style               {:color "var(--surface2)"}
                :stroke              :currentColor
                :viewBox             (str "0 0 " width " 10")
                :width               "100%"
                :height              "auto"
                :preserveAspectRatio "none"}
          [:path
           {:vector-effect :non-scaling-stroke
            :stroke-width  2
            :d             (str (apply str "M 0 10 " (repeat 3 " m 24 0 v -4 v 4 ")))}]]]])]))

(defn- ^:deprecated overlapping? [id time-slot offset]
  (let [booking-db (filter (partial has-selection id) (booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        overlapping? (pos? (count (filter (fn [{:keys [r?]}] (nil? r?)) status-list)))]
    overlapping?))

(defn- ^:deprecated expanded-view [{:keys [appearance offset time-slot id data fetch-bookingdata]}]
  (let [{:keys [bg bg+ hd fg fg- fg+ p p-]} (st/fbg' :listitem)
        fetch-bookingdata (if fetch-bookingdata
                            fetch-bookingdata
                            booking.database/read)
        window {:width  (* 24 4)
                :offset (* 24 offset)}
        offset (* 24 (dec offset))
        slot' (when time-slot
                {:from (- (convert (t/beginning time-slot)) offset)
                 :to   (- (convert (t/end time-slot)) offset)})
        booking-db (filter (partial has-selection id) (fetch-bookingdata) #_(booking.database/read))
        status-list (mapv (fn [{:keys [start end]}]
                            {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                             :start (- (convert start) offset)
                             :end   (- (convert end) offset)})
                          booking-db)
        {:keys [navn description number weight length width slot expert kind]} data]
    [:<>
     [:div.grid.gap-2.p-2.w-full.text-blackx
      {:style {:grid-template-columns "min-content min-content 1fr minmax(4rem,max-content)"
               :grid-auto-rows        "auto"}}

      [:div.self-center.justify-self-start
       {:class (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500])}
       (number-view number)]

      (when-not (some #{:hide-location} appearance)
        [:div.self-center.justify-self-end (slot-view slot)])

      (if (and (not (some #{:extra} appearance)) (some #{:tall} appearance))
        [:div.self-center.space-y-0.truncate
         {:class (concat p- (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500]))}
         (name-view navn)
         (normalize-kind kind)]
        [:div.self-center.truncate
         {:class
          (concat fg+
                  hd
                  (if (some #{:error} appearance) [:underline :decoration-wavy :decoration-rose-500]))}
         (name-view navn)])

      (when (some #{:timeline} appearance)
        [:div.col-span-1.h-10x.self-center.max-w-xs.justify-self-end.w-full
         (when booking-db
           (draw-graph
             {:date      (t/beginning time-slot)
              :window    window
              :time-slot slot'
              :list      status-list}))])

      (when (some #{:extra} appearance)
        [:<>
         [:div.col-span-2.self-start.justify-self-start.leading-6
          {:class p-}
          (normalize-kind kind)]
         [:div.col-span-2.self-start.leading-6 {:class (concat p fg)} description]
         [:div.col-span-2]
         [:div.col-span-2.flex.justify-start.flex-wrap
          {:class (concat p- fg-)}
          (interpose [:div.w-2 ", "]
                     (remove nil? [(when (seq weight) [:div.whitespace-nowrap weight])
                                   (when (seq length) [:div.whitespace-nowrap "lengde " length])
                                   (when (seq width) [:div.whitespace-nowrap "bredde " width])]))]])]]))

(defn normal-view [{:keys [id data time-slot offset fetch-bookingdata selected?]}]
  (let [{:keys [fg- fg fg+ p p-]} (st/fbg' :booking-listitem)
        {:keys [navn number slot kind]} data]
    [:div.grow
     [:div.h-full.gap-2.pr-4.pl-2.grow
      {:class (concat fg [:flex :items-center])}
      (number-view number)
      [:div.flex.flex-col.grow.space-y-1.truncate
       [:div.leading-none.flex.items-center.gap-2.truncate
        {:class (concat fg+ p)}
        (slot-view slot)
        (normalize-kind kind)]
       [:div.flex.items-center
        [:div.leading-none {:class (concat fg- p-)} navn]]]
      (when selected?
        (let [fetch-bookingdata (if fetch-bookingdata
                                  fetch-bookingdata
                                  booking.database/read)
              offset (* 24 (dec offset))
              window {:width  (* 24 4)
                      :offset (* 24 offset)}
              slot' (when time-slot
                      {:from (- (convert (t/beginning time-slot)) offset)
                       :to   (- (convert (t/end time-slot)) offset)})
              booking-db (filter (partial has-selection id) (fetch-bookingdata) #_(booking.database/read))
              status-list (mapv (fn [{:keys [start end]}]
                                  {:r?    (some #{(available? time-slot (tick.alpha.interval/bounds start end))} [:precedes :preceded-by])
                                   :start (- (convert start) offset)
                                   :end   (- (convert end) offset)})
                                booking-db)]
          (draw-graph
            {:date      (t/beginning time-slot)
             :window    window
             :time-slot slot'
             :list      status-list})))]]))

(defn list-line-classes [{:keys [overlap? selected? id on-click insert-before-line-item insert-before insert-after appearance] :as m}]
  (let [{:keys [bg bg+]} (if selected?
                           (st/fbg' :listitem-button-selected)
                           (st/fbg' :listitem-button-unselected))]
    (cond
      (or (some #{:unavailable} appearance)
          (some #{:error} appearance)
          overlap?)
      [:bg-red-300 :text-black :dark:bg-rose-500 :dark:text-white]
      selected?
      bg
      (some #{:clear} appearance)
      []

      :else bg)))

(defn list-line [{:keys [id insert-before-line-item insert-before insert-after] :as m}]
  [:div.flex.items-stretch.justify-between
   {:class (list-line-classes m)}
   (when (and insert-before-line-item (fn? insert-before-line-item))
     (insert-before-line-item id))
   (when (and insert-before (fn? insert-before))
     (insert-before id))
   [normal-view m]
   (when (and insert-after (fn? insert-after))
     (insert-after id))])

;endregion drawing

(defn select-button [selected]
  (fn [id] (let [selected? (some #{id} @selected)]
             (when-not selected?
               (bu/listitem-button-small-clear
                 {:class     [:mr-5]
                  :type      :button
                  :on-click  #(do
                                (readymade/popup {:dialog-type :message
                                                  :content     (str id)})
                                (swap! selected set/union #{id}))
                  :color-map (assoc (st/fbg' :listitem-button-unselected)
                               :fg [:text-info-200]
                               :bg [:bg-info-700]
                               :br [:border-info-700 :border-2 :ps-2])} :checked)))))

(defn unselect-button [selected]
  (fn [id]
    (let [selected? (some #{id} @selected)]
      (when selected?
        [:div.flex.items-center
         (bu/listitem-button-small-clear
           {:type      :button
            :on-click  #(do
                          (readymade/popup {:dialog-type :error
                                            :content     (str id)})
                          (swap! selected set/difference #{id}))
            :color-map (assoc (st/fbg' :listitem-button-selected)
                         :bg [:bg-gray-700]
                         :fg [:text-gray-200]
                         :br [:border-gray-700 :border-2 :ps-2])} :cross-out)]))))

(defn boat-picker
  "Find all entries with a relation of :precedes or preceded-by, all the
  other relations must fail. Since there isn't any allowance for abutting
  entries (1 hour minimum between each booking), :met-by and :meets are therefore
  rejected."
  [{:keys [values]} {:keys [boat-db selected not-available]}]
  (let [{:keys [bg fg-]} (st/fbg' :void)
        ;fixme You've done this many times now
        start (t/at (t/date (values :start-date)) (t/time (values :start-time)))
        end (t/at (t/date (values :end-date)) (t/time (values :end-time)))
        offset (if start (times.api/day-number-in-year (t/date start)) 0)
        slot (try
               (tick.alpha.interval/bounds start end)
               (catch js/Error _ nil))]
    [:div.flex.flex-col
     {:style {:min-height "calc(100vh - 22.9rem)"}
      :class bg}

     (if (seq boat-db)
       [:div.flex-1
        (let [only-show-selected? false]
          (into [:div.space-y-px.select-none.overflow-clip]
                (for [[id data] boat-db
                      :when (if only-show-selected?
                              (some #{id} @selected)
                              true)]
                  ^{:key (str id)}
                  [list-line
                   {:not-available           not-available
                    :offset                  offset
                    :time-slot               slot
                    :boat-db                 boat-db
                    :selected                selected
                    :insert-before-line-item hov/open-details
                    :insert-before           (unselect-button selected)
                    :insert-after            (select-button selected)
                    :overlap?                (some #{id} not-available)
                    :appearance              (set/union
                                               (if @(schpaa.state/listen :opt1) #{:extra})
                                               (if (some #{id} @selected) #{:timeline}))
                    :selected?               (some #{id} @selected)
                    :id                      id
                    :data                    data}])))]
       [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
        {:class fg-}
        [:div.text-2xl.font-black "Båt-listen er tom"]
        [:div.text-xl.font-semibold "Ta kontakt med administrator"]])]))

;todo rename "utvalg"
(rf/reg-sub :boatpickerlist/details :-> :boatpicker-list-details)

(rf/reg-event-db :boatpickerlist/set-details (fn [db [_ args]]
                                               (assoc db :boatpicker-list-details args)))

(defn boat-picker-footer []
  (let [{:keys [bg fg fg+ p p-]} (st/fbg' :surface)]
    [:div.flex.justify-end.items-center.gap-2.px-4.sticky.bottom-0.h-16.shadow
     {:class bg}
     (schpaa.components.views/modern-checkbox'
       {:set-details #(rf/dispatch [:boatpickerlist/set-details %])
        :get-details #(-> (rf/subscribe [:boatpickerlist/details]) deref)}
       (fn [checkbox]
         [:div.flex.items-center.gap-2
          [:div.flex.flex-col
           [:div.text-right {:class (concat p fg+)} "Utvalg"]
           [:div.hidden.xs:block.text-right {:class (concat p- fg)} "Begrens visning til utvalg"]]
          checkbox]))]))
