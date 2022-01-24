(ns booking.time-navigator
  (:require [schpaa.components.fields :as fields]
            [times.api :refer [format]]
            [tick.core :as t]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(defn naked [props]
  (assoc props :naked? true))

;region time-nav

(def cuty-color-map
  {:active-toggled [:active:bg-gray-700
                    :active:border-gray-600]
   :active         [:active:bg-gray-300
                    :active:border-gray-200]
   :normal         [:bg-gray-200
                    :border-gray-300
                    :dark:bg-gray-800
                    :dark:border-gray-800
                    :dark:text-gray-400]
   :toggled        [:bg-gray-800
                    :text-gray-200
                    :border-gray-800]
   :untoggled      [:bg-gray-200
                    :text-gray-700
                    :border-gray-200]
   :disabled       [:disabled:bg-gray-100
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
       [:div.text-base.font-semibold.antialiased icon]
       [:div.w-6.h-6 (schpaa.icon/adapt icon 1.5)])]))

(defn- big-arrow [right?]
  [:div.mx-2.absolute.inset-y-0
   {:class (if right? :right-0 :left-0)}
   [:svg.w-4.h-full
    {:class            ["text-black/10"
                        "dark:text-white/10"]
     :viewBox          "0 0 6 20"
     :transform        (when-not right? "scale(-1, 1)")
     :transform-origin "center"}
    [:path {:vector-effect :non-scaling-stroke
            :fill          :none
            :stroke        :currentColor
            :stroke-width  4
            :d             "M 0 0 L 5 10 L 0 20"}]]])

(defn nav-1 [{:keys [my-state] :as m}
             {:keys [set-values values] :as props}]
  [:div
   [l/ppre-x (some-> props :state deref :values)]
   [:div.flex.justify-center.w-full.flex-shrink-0
    {:xclass [:bg-gray-100 :dark:bg-gray-700]}

    [:div.grid.gap-2.xplace-content-center.w-fullx.h-fullx
     {:style {:grid-template-columns "min-content min-content"}}
     [:div.justify-self-end
      (fields/date (-> props
                       ;fields/date-field
                       ;naked
                       #_(assoc :values (fn [] (some-> (get-in @my-state [:values :start]) t/date)))
                       #_(assoc :handle-change
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
     #_[:div.flex.gap-1.items-center.h-full
        (when-let [cap (some-> :start values t/time t/hour dec)]
          (cuty (format "%02d:" cap)
                :disabled (< cap 1)
                :action #(set-values {:start (t/at (t/date (values :start)) (t/new-time cap 0))})))
        (fields/time (-> props
                         naked
                         fields/time-field
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
        (when-let [cap (some-> :start values t/time t/hour (+ 2))]
          (cuty (format "%02d:" cap)
                :disabled (< 23 cap)
                :action #(set-values {:start (t/at (t/date (values :start)) (t/new-time cap 0))})))]
     #_[:div.self-center.justify-self-end.flex.gap-1.items-center.h-full
        (cuty "Nå" :action
              #(set-values {:start (t/at (t/date) (t/>> (t/truncate (t/time) :hours) (t/new-duration 1 :hours)))
                            :end   (when-let [end (values :end)]
                                     (t/at (t/date) (t/time end)))})
              #_#(swap! my-state assoc-in [:values :start] (t/date-time)))
        (cuty "-1d"
              :disabled (not (some-> @my-state :values :start))
              :action (fn []
                        (swap! my-state
                               #(-> %
                                    (update-in [:values :start]
                                               (fn [e]
                                                 (let [time (t/time e)
                                                       v (t/<< (t/date e) (t/new-period 1 :days))]
                                                   (t/at v time))))
                                    (update-in [:values :end]
                                               (fn [e]
                                                 (let [time (t/time e)
                                                       v (t/<< (t/date e) (t/new-period 1 :days))]
                                                   (t/at v time))))))))
        (cuty "+1d"
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
                                                 (when-let [time (t/time e)]
                                                   (let [v (t/>> (t/date e) (t/new-period 1 :days))]
                                                     (t/at v time)))))))))

        (cuty "+5d"
              :action #(set-values {:start (->>
                                             (t/time (values :start))
                                             (t/at (t/>> (t/date (values :start)) (t/new-period 5 :days))))
                                    :end   (when-let [end (values :end)]
                                             (->>
                                               (t/time end)
                                               (t/at (t/>> (t/date end) (t/new-period 5 :days)))))}))
        #_(let [start (some-> :start values t/date)
                end (some-> :end values t/date)]
            (when (and start end)
              (let [days (some-> (tick.alpha.interval/new-interval start end)
                                 (t/duration)
                                 (t/days))]
                (cuty :moon-2
                      :state (< 1 days)
                      :action #(set-values {:end (if (< 1 days)
                                                   (t/at (t/date (values :start)) (t/time (values :end)))
                                                   (t/at (t/>> (t/date (values :start)) (t/new-period 1 :days))
                                                         (t/time (values :end))))})))))]
     #_[:div.flex.gap-1.items-center.h-full
        (when-let [cap (some-> :end (values) (t/time) (t/hour) (dec))]
          (cuty (format "%02d:" cap)
                :disabled (< cap 6)
                :action #(set-values {:end (t/at (t/date (values :end)) (t/new-time cap 0))})))
        (fields/time (-> props
                         naked
                         fields/time-field
                         (assoc :values (fn [] (some-> (get-in @my-state [:values :end]) (t/time))))
                         (assoc :handle-change (fn [e]
                                                 (let [vt (-> e .-target .-value)]
                                                   (tap> vt)
                                                   (if (empty? vt)
                                                     (when-let [from-time (some-> (get-in @my-state [:values :start]) (t/time))]
                                                       (let [time (t/>> (t/truncate from-time :hours) (t/new-duration 1 :hours))]
                                                         (when-let [date (some-> (get-in @my-state [:values :start]) (t/date))]
                                                           (swap! my-state assoc-in [:values :end]
                                                                  (t/at date time)))))
                                                     (let [time (t/time vt)]
                                                       (when-let [date (t/date (get-in @my-state [:values :end]))]
                                                         (swap! my-state assoc-in [:values :end]
                                                                (t/at date time))))))))) "" :end)
        (when-let [cap (some-> :end (values) (t/time) (t/hour) (+ 2))]
          (cuty (format "%02d:" cap)
                :disabled (< 23 cap)
                :action #(set-values {:end (t/at (t/date (values :end)) (t/new-time cap 0))})))]]]])

(defn- push-button
  ([a disabled? & {:keys [icon caption type swap]}]
   [:button.btn.btn-free.flex.gap-2
    {:class    (if disabled? [:text-gray-300])
     :type     (or type :button)
     :disabled disabled?
     :on-click a}
    (if swap
      [:<>
       (when icon [schpaa.icon/small icon])
       (when caption [:div caption])]
      [:<>
       (when caption [:div caption])
       (when icon [schpaa.icon/small icon])])]))

(comment
  (do
    (let [a (atom {:vals {:start (str (t/date-time))}})]
      (when-let [x ((juxt (comp t/date-time :start)) (get @a :vals))]
        x))))

;region small helpers

(defn point [n complete]
  [:div {:class (concat
                  [:flex :items-center :justify-center :h-6 :aspect-square :rounded-full :font-bold :text-base]
                  (if complete [:text-white :bg-alt] [:text-white :bg-sky-500]))}
   (if complete
     (icon/adapt :checked 3)
     n)])

(defn step [n c & {:keys [complete on-click final active] :or {on-click #()}}]
  [:div.px-4.py-2;.rounded-full.w-full
   {:sclass (if complete [:bg-alt :text-white] [:bg-rose-500 :text-white])
    :class (if active [:border-b-4 :border-alt])
    :on-click #(on-click) :type :button}
   [:div.flex.items-center.gap-2.col-span-1.border-none.outline-none.focus:outline-none.focus:ring-none
    (point n complete)
    [:div c]]])

;endregion

(defn time-navigator [{:keys [my-state selected not-available ] :as m}
                      {:keys [set-values values] :as props}]
  (let [menu-open? (rf/subscribe [:app/menu-open?])]
    (fn [{:keys [my-state selected not-available]}
         {:keys [set-values values] :as props}]
      (let [width (str "calc(100vw - " (if @menu-open? 20 0) "rem)")]
        [:div.sticky.top-28.space-y-2x
         [:div.xh-44.overflow-x-auto.grid.snap-x.snap-mandatory.z-10.bg-gray-100.dark:bg-gray-600.hide-scrollbar
          {:style {:width                 width
                   :max-width             width
                   :grid-template-columns "max-content max-content"}}
          [:div.snap-start.flex.justify-between.relative
           {:style {:width width}
            :class ["bg-amber-300"]}]]
         [:<>
          [:div.grid.grid-cols-3.py-4.px-4.gap-x-4.gap-y-2.bg-gray-100.z-10
           ;[step 1 "Tidspunkt" :complete (nil? (:errors props))]
           [step 2 "Båter" :complete (not (empty? @selected)) :on-click #(eykt.fsm-helpers/send :e.pick-boat)]
           [step 3 "Oversikt"
            :final true
            :complete (and (nil? (:errors props)) (nil? (some not-available @selected)))
            :on-click #(eykt.fsm-helpers/send :e.confirm)]]]]))))
