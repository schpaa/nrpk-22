(ns booking.bookinglist
  (:require [tick.core :as t]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [booking.views.picker :refer [list-line]]
            [schpaa.components.views :refer [number-view]]
            [eykt.hov :as hov]
            [clojure.set :as set]
            [schpaa.components.fields :as fields]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(defn- booking-list-item-color-map [relation]
  (case relation
    ;past
    :precedes {:br  [:border-l-8 :border-gray-700]
               :bg  ["dark:bg-black/30" "bg-gray-300" :text-gray-400]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}

    ;future
    :preceded-by {:br  [:border-l-8 :border-sky-500]
                  :bg  ["dark:bg-gray-800" "bg-gray-300"]
                  :fg  ["dark:text-gray-300" "text-gray-700"]
                  :fg- ["dark:text-gray-400" "text-gray-700/50"]}

    ;today
    {:br  [:border-l-8 :border-alt]
     :bg  ["dark:bg-alt-600" "bg-gray-100"]
     :fg  ["dark:text-black" "text-gray-700"]
     :fg- ["dark:text-white" :text-gray-400]}))

(defn time-segment-display'old [{:keys [hide-name? multiday navn start end relation]}]
  (let [day-name (times.api/day-name (t/date-time start))
        {:keys [bg fg fg-]} (booking-list-item-color-map relation)]
    [:div.grid.gap-2.w-full.px-2
     {:class (concat bg fg)
      :style {:grid-template-columns "1.5rem 1fr min-content max-content 3rem"
              :grid-auto-rows        ""}}
     [:div.truncate.col-span-2
      (when-not hide-name?
        [:div.truncate {:class fg-} navn])]
     [:<>
      [:div.justify-self-end.whitespace-nowrap (t/format (str "'" day-name " 'd.MM") (t/date-time start))]
      [:div.debug.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]]
     [:div.self-center.justify-self-end
      (when multiday
        [icon/small :moon-2])]
     [:div.col-span-3]
     [:div.debug.whitespace-nowrap.self-start (t/format "'kl.' H.mm" (t/time (t/date-time end)))]
     [:div.self-start.justify-self-end
      (when multiday
        (t/format "d.MM" (t/date (t/date-time end))))]]))

(defn time-segment-display [{:keys [hide-name? multiday navn start end relation]}]
  (let [multiday (< 0 (t/days (t/duration (tick.alpha.interval/new-interval start end))))
        day-name (times.api/day-name (t/date-time start))
        end-day-name (times.api/day-name (t/date-time end))
        {:keys [bg fg fg-]} (booking-list-item-color-map relation)]
    (if multiday
      [:div.grid.gap-2.w-full.px-2
       {;:class (concat bg fg)
        :style {:grid-template-columns "min-content min-content min-content"}}
       [:div.justify-self-end.whitespace-nowrap (t/format (str "'" day-name " 'd.MM") (t/date-time start))]
       [:div.debug.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]
       [:div.self-start.justify-self-end.whitespace-nowrap
        (t/format (str "'" end-day-name " 'd.MM") (t/date (t/date-time end)))

        (t/format "' — kl.' H.mm" (t/time (t/date-time end)))]]
      ;;----------------------
      [:div.grid.gap-2.w-full.px-2
       {;:xclass (concat bg fg)
        :style {:grid-template-columns "min-content min-content"
                :grid-auto-rows        ""}}
       #_[:div.truncate.col-span-2
          (when-not hide-name?
            [:div.truncate {:class fg-} navn])]

       [:div.justify-self-end.whitespace-nowrap
        (t/format (str "'" day-name " 'd.MM") (t/date-time start))
        " "
        (t/format "'kl.' H.mm" (t/time (t/date-time start)))]

       [:div.debug.whitespace-nowrap.self-start (t/format "'—' H.mm" (t/time (t/date-time end)))]])))

(defn- booking-list-item [{:keys [offset today hide-name? on-click
                                  details?
                                  insert-top-fn
                                  insert-before
                                  insert-below
                                  insert-above
                                  insert-after
                                  appearance
                                  time-slot
                                  boat-db]
                           :or   {today (t/new-date)}}
                          ;intent FOR EACH ITEM
                          {:keys [id navn insert-before-line-item description selected not-available start end] :as item}]
  (let [selected (map keyword selected)                     ;;selected must be a keyword
        relation (try (tick.alpha.interval/relation start today)
                      (catch js/Error _ nil))
        {:keys [bg fg fg- br]} (booking-list-item-color-map relation)]


    [:div.flex
     (when insert-before
       (insert-before id))
     [:div.grid.w-full
      {:style    {:grid-template-columns "min-content 1fr min-content min-content"}
       :class    (concat fg bg br)
       :on-click #(when on-click (on-click item))}

      (when insert-top-fn [:div.col-span-3 '(insert-top-fn item)])
      (when insert-above [:div.col-span-3 (insert-above)])
      [:<>

       [:div]
       [:div.py-1.space-y-1
        [time-segment-display item]
        (if details?
          (if (some? description)
            [:div.col-span-3.text-sm.px-2
             {:class (concat fg- bg)} description]
            [:<>]))

        (when-not (empty? selected)
          [:div.col-span-5
           {:class (concat bg)}
           (if false; details?
             [:div.space-y-px.grid
              {:style {:grid-template-columns "repeat(auto-fill,minmax(15rem,1fr))"}
               :class [:first:rounded-t :overflow-clip :last:rounded-b]}
              (doall (for [id selected
                           :let [data (get (into {} boat-db) id)]
                           :while (some? data)]
                       [list-line
                        {;:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                         :insert-after            hov/open-details
                         :id         id
                         :data       data
                         :offset     offset
                         :time-slot  time-slot
                         :appearance (set/union #{:basic :xclear :hide-location :extra} appearance)
                         :overlap?   false}]))]
             [:div
              (let [items (sort (map (fn [id] (:number (get (into {} boat-db) id))) selected))]
                (cond
                  (< 2 (count items)) [:div.flex.gap-1.flex-wrap.px-2
                                       (for [number (take 2 items)]
                                         [:div (schpaa.components.views/number-view number)])
                                       (schpaa.components.views/show-more-number-view)]
                  :else [:div.flex.gap-1.flex-wrap.px-2
                         (for [number items]
                           [:div (schpaa.components.views/number-view number)])]))])])]
       [:div]]

      (when insert-below
        [:div.col-span-3 (insert-below)])
      (if insert-after
        (insert-after id)
        [:div])]]))

(defn booking-list [{:keys [uid today booking-data accepted-user? class boat-db details?]}]
  (r/with-let [edit (r/atom false)
               markings (r/atom {})]
    (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
          c (count selected-keys)
          show-only-my-own? @(schpaa.state/listen :opt/show-only-my-own)
          data (->> booking-data
                    (filter (comp (partial booking.views.picker/after-and-including today) val))
                    ;fixme BAD CODE
                    (filter (fn [[id data]] (if show-only-my-own?
                                              true
                                              (= uid (:uid data)))))
                    (sort-by (comp :start val) <))]
      [:<>
       (into [:div.space-y-px.bg-gray-500.dark:bg-gray-900
              {:class class}]
             (map (fn [[{:keys [id]} item]]
                    (let [my-own? (= uid (:uid item))]
                      [booking-list-item
                       {:details?       details?
                        :boat-db        boat-db
                        :accepted-user? accepted-user?
                        :today          today
                        :hide-name?     (not (some? uid))
                        :on-click       (fn [e]
                                          (swap! markings update id (fnil not false))
                                          (.stopPropagation e))
                        ;:insert-before  (when my-own? hov/remove-booking-details-button)
                        ;hov/remove-from-list-actions
                        ;(hov/remove-from-list-actions clicks-on-remove selected)
                        #_(when true                         ;@edit
                            (fn [_] [:div.flex.items-center.px-2.bg-gray-400
                                     [fields/checkbox {:values        (fn [_] (get-in @markings [id] false))
                                                       :handle-change #(swap! markings update id (fnil not false))}
                                      "" nil]]))
                        :insert-after   (fn [id]
                                          [:div.flex
                                           (when my-own? (hov/remove-booking-details-button id))
                                           (hov/open-booking-details-button id)])}
                       item]))
                  data))])))
