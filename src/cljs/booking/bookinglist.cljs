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
            [schpaa.debug :as l]
            [schpaa.style :as st]))

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

(defn time-segment-display [{:keys [hide-name? navn start end relation]}]
  (let [{:keys [fg p-]} (st/fbg' :listitem)
        multiday (< 0 (t/days (t/duration (tick.alpha.interval/new-interval start end))))
        day-name (times.api/day-name (t/date-time start))
        end-day-name (times.api/day-name (t/date-time end))
        {:keys []} (booking-list-item-color-map relation)]
    (if multiday
      [:div.grid.gap-1.tabular-nums
       {:class (concat fg p-)
        :style {:grid-template-columns "min-content min-content min-content 1fr"}}
       [:div day-name]
       [:div.justify-self-startx.whitespace-nowrap (t/format "d.MM" (t/date-time start))]
       [:div.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]
       [:div]
       [:div end-day-name]
       [:div.self-start.justify-self-start.whitespace-nowrap

        (t/format "d.MM" (t/date (t/date-time end)))]
       [:div.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time end)))]
       [:div]]

      ;;----------------------
      [:div.grid.gap-2.tabular-nums
       {:class (concat fg p-)
        :style {:grid-template-columns "min-content min-content"
                :grid-auto-rows        ""}}
       [:div.justify-self-end.whitespace-nowrap

        (t/format (str "'" day-name " 'd.MM") (t/date-time start))
        " "
        (t/format "'kl. 'H.mm" (t/time (t/date-time start)))
        (t/format "'—'H.mm" (t/time (t/date-time end)))]])))


(defn- booking-list-item [{:keys [fetch-boatdata-for offset today on-click details? insert-before insert-below insert-after appearance time-slot]
                           :or   {today (t/new-date)}}
                          ;intent FOR EACH ITEM
                          {:keys [id navn insert-before-line-item description selected not-available start end] :as item}]
  (let [selected (map keyword selected)                     ;;selected must be a keyword
        relation (try (tick.alpha.interval/relation start today)
                      (catch js/Error _ nil))
        {:keys [bg fg fg+ p]} (st/fbg' :listitem)
        {:keys [br]} (booking-list-item-color-map relation)]

    [:div.flex
     (when insert-before
       (insert-before id))
     [:div.grid.w-full.p-1.gap-1
      {:style    {:grid-template-columns "1fr max-content"}
       :class    (concat fg bg br)
       :on-click #(when on-click (on-click item))}

      [:div 'navn]

      [:div.justify-self-end
       (when-not (empty? selected)
         [:div.col-span-5
          {:class (concat bg)}
          (if false                                         ; details?
            [:div.grid
             {:style {:grid-template-columns "repeat(auto-fill,minmax(15rem,1fr))"}
              :class [:first:rounded-t :overflow-clip :last:rounded-b]}
             (doall (for [id selected
                          :let [data (when fetch-boatdata-for (fetch-boatdata-for id))]
                          :while (some? data)]
                      [list-line
                       {;:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                        :insert-before-line-item hov/open-details
                        :id                      id
                        :data                    {:number "A"} ; fixme data ;(get (into {} boat-db) id)
                        :offset                  offset
                        :time-slot               time-slot
                        :appearance              (set/union #{:basic :xclear :hide-location :extra} appearance)
                        :overlap?                false}]))]
            (when fetch-boatdata-for
              (let [items (sort (map (fn [id] (:number (fetch-boatdata-for id))) selected))]
                (if (< 2 (count items))
                  [:div.flex.gap-1.flex-wrap.px-1
                   (schpaa.components.views/show-more-number-view)
                   (for [number (take 2 items)]
                     (schpaa.components.views/number-view number))]
                  [:div.flex.gap-1.flex-wrap.px-1
                   (for [number items]
                     (schpaa.components.views/number-view number))]))))])]

      [:div.col-span-2
       [time-segment-display item]]
      (if details?
        (if (some? description)
          [:div.col-span-3
           {:class (concat fg+ p)}
           description]))]

     (when insert-below
       [:div.col-span-3 (insert-below)])
     (if insert-after
       (insert-after id)
       [:div])]))

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
                       {:fetch-boatdata-for (fn [id] (get (into {} boat-db) id))
                        :details?           details?
                        :boat-db            boat-db
                        :accepted-user?     accepted-user?
                        :today              today
                        :hide-name?         (not (some? uid))
                        :insert-before      hov/open-booking-details-button
                        :insert-after       (fn [id]
                                              [:div.flex
                                               (when my-own?
                                                 (hov/remove-booking-details-button
                                                   id
                                                   (filter (fn [{:keys [] :as item}] (= (:id item) id)) (booking.database/read))))])}

                       item]))
                  data))])))