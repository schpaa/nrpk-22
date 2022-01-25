(ns booking.bookinglist
  (:require [tick.core :as t]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [booking.views.picker :refer [list-line]]
            [eykt.hov :as hov]
            [clojure.set :as set]
            [schpaa.components.fields :as fields]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(defn- booking-list-item-color-map [relation]
  (case relation
    ;past
    :precedes {:br [:border-l-8 :border-gray-700]
               :bg  ["dark:bg-black/30" "bg-gray-300" :text-gray-400]
               :fg  ["dark:text-gray-500/50" "text-gray-500"]
               :fg- ["dark:text-white/30"]}

    ;future
    :preceded-by {:br [:border-l-8 :border-sky-500]
                  :bg  ["dark:bg-gray-100" "bg-gray-300"]
                  :fg  ["dark:text-black" "text-gray-700"]
                  :fg- ["dark:text-black/40" "text-gray-700/50"]}

    ;today
    {:br [:border-l-8 :border-alt]
     :bg  ["dark:bg-alt-600" "bg-gray-200"]
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
  (let [day-name (times.api/day-name (t/date-time start))
        end-day-name (times.api/day-name (t/date-time end))
        {:keys [bg fg fg-]} (booking-list-item-color-map relation)]
    [:div.grid.gap-2.w-full.px-2
     {:class (concat bg fg)
      :style {:grid-template-columns "min-content max-content 1fr min-content min-content"
              :grid-auto-rows        ""}}
     #_[:div.truncate.col-span-2
        (when-not hide-name?
          [:div.truncate {:class fg-} navn])]

     [:div.justify-self-end.whitespace-nowrap (t/format (str "'" day-name " 'd.MM") (t/date-time start))]
     [:div.debug.whitespace-nowrap (t/format "'kl.' H.mm" (t/time (t/date-time start)))]
     [:div.self-center.justify-self-center
      (when multiday
        [icon/small :moon-2])]
     ;[:div.col-span-3]
     [:div.self-start.justify-self-end.whitespace-nowrap
      (when multiday
        (t/format (str "'" end-day-name " 'd.MM") (t/date (t/date-time end))))]
     [:div.debug.whitespace-nowrap.self-start (t/format "'kl.' H.mm" (t/time (t/date-time end)))]]))

(defn- booking-list-item [{:keys [offset today hide-name? on-click
                                  insert-top-fn
                                  insert-below
                                  insert-above
                                  appearance
                                  time-slot
                                  boat-db]
                           :or   {today (t/new-date)}}
                          ;intent FOR EACH ITEM
                          {:keys [navn  insert-before-line-item description selected not-available start end] :as item}]
  (let [details @(rf/subscribe [:app/details])
        selected (map keyword selected) ;;selected must be a keyword
        relation (try (tick.alpha.interval/relation start today)

                      (catch js/Error _ nil))
        {:keys [bg fg fg- br]} (booking-list-item-color-map relation)
        multiday true]

    [:div.grid.w-full
     {:style    {:grid-template-columns "min-content 1fr min-content"}
      :class    (concat fg bg br)
      :on-click #(when on-click (on-click item))}
     (when insert-top-fn [:div.col-span-3 '(insert-top-fn item)])
     (when insert-above [:div.col-span-3 (insert-above)])
     [:<>
      [:div]
      [:div.py-1.space-y-1
       [time-segment-display {:start      start
                              :end        end
                              :relation   relation
                              :hide-name? hide-name?
                              :multiday   multiday
                              :navn       navn}]

       (if (or true (some #{:description} appearance) (< 1 details))
         (if (some? description)
           [:div.col-span-3.text-sm.px-2
            {:class (concat fg- bg ) } description]
           [:<>]))

       (when-not (empty? selected)
         [:div.col-span-5
          {:class (concat bg)}
          [:div.space-y-px.grid.xgap-1.bg-alt
           {:style {:grid-template-columns "repeat(auto-fill,minmax(15rem,1fr))"}
            :class [:first:rounded-t :overflow-clip :last:rounded-b]}
           (doall (for [id selected
                        :let [data (get (into {} boat-db) id)]
                        :while (some? data)]
                    [list-line
                     {;:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                      ;:insert-after            hov/open-details
                      :id                      id
                      :data                    data
                      :offset                  offset
                      :time-slot               time-slot
                      :appearance              (set/union #{:basic :xclear :hide-location} appearance)
                      :overlap?                false}]))]])]

      [:div]]

     (when insert-below
       [:div.col-span-3 (insert-below)])]))

(defn booking-list [{:keys [uid today booking-data accepted-user? class boat-db]}]
  (r/with-let [edit (r/atom false)
               markings (r/atom {})]
              (let [selected-keys (keep (fn [[k v]] (if v k)) @markings)
                    c (count selected-keys)
                    data (->> booking-data
                              (filter (comp (partial booking.views.picker/after-and-including today) val))
                              (sort-by (comp :start val) <))]
                [:div
                 (into [:div.space-y-px.bg-gray-500
                        {:class class}]
                       (map (fn [[k item]]
                              (let [idx (:id item)]
                                [booking-list-item
                                 {:boat-db        boat-db
                                  :accepted-user? accepted-user?
                                  :today          today
                                  :hide-name?     (not (some? uid))
                                  :on-click       (fn [e]
                                                    (swap! markings update idx (fnil not false))
                                                    (.stopPropagation e))
                                  :insert-before  (when @edit
                                                    [:div.flex.items-center.px-2.bg-gray-400
                                                     [fields/checkbox {:values        (fn [_] (get-in @markings [idx] false))
                                                                       :handle-change #(swap! markings update idx (fnil not false))}
                                                      "" nil]])
                                  :insert-after   hov/open-booking-details-button}
                                 item]))
                            data))])))
