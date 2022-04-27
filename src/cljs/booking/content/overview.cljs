; -[ ] collect views
(ns booking.content.overview
  (:require [nrpk.fsm-helpers :as state :refer [send]]
            [reagent.core :as r]
            [booking.hoc :as hoc]
            [logg.database]
            [schpaa.components.views]
            [booking.database]
            [booking.views]
            [schpaa.style :as st]
            [db.core :as db]
            [re-frame.core :as rf]
            [tick.core :as t]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.bookinglist]))

#_(defn boat-number-list [colormap {:keys [fetch-boatdata-for selected]}]
    (when fetch-boatdata-for
      (let [items (sort (map (fn [id] (:number (fetch-boatdata-for id))) selected))]
        (if (< 2 (count items))
          [:div.flex.gap-1.flex-wrap
           (schpaa.components.views/show-more-number-view)
           (for [number (take 2 items)]
             (schpaa.components.views/number-view number))]
          [:div.flex.gap-1.flex-wrap
           (for [number items]
             (schpaa.components.views/number-view number))]))))

#_(defn bookers-name [colormap {:keys [uid navn]}]
    [:div.truncate {:class (concat)}
     (if navn
       navn
       (let [user @(db.core/on-value-reaction {:path ["users" uid]})]
         (if (:alias user)
           (:alias user)
           (if (:navn user)
             (:navn user)
             uid))))])

#_(defn- booking-list-item-color-map [relation]
    (case relation
      ;past
      :precedes {:br  [:border-l-8 :border-gray-700]
                 :bg  ["dark:bg-black/30" "bg-gray-300" :text-gray-400]
                 :fg  ["dark:text-gray-500/50" "text-gray-500"]
                 :fg- ["dark:text-white/30"]}

      ;future
      :preceded-by {:br  [:border-l-8 :border-amber-500]
                    :bg  ["dark:bg-gray-800" "bg-gray-300"]
                    :fg  ["dark:text-gray-300" "text-gray-700"]
                    :fg- ["dark:text-gray-400" "text-gray-700/50"]}

      ;today
      {:br  [:border-l-8 :border-alt]
       :bg  ["dark:bg-alt-600" "bg-gray-100"]
       :fg  ["dark:text-black" "text-gray-700"]
       :fg- ["dark:text-white" :text-gray-400]}))

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

#_(defn- booking-list-item [{:keys [fetch-boatdata-for offset today on-click details? insert-before insert-below insert-after appearance time-slot]
                             :or   {today (t/new-date)}}
                            ;intent FOR EACH ITEM
                            {:keys [uid id navn insert-before-line-item description selected not-available start end] :as item}]
    (let [selected (map keyword selected)                   ;;selected must be a keyword
          relation (try (tick.alpha.interval/relation start today)
                        (catch js/Error _ nil))
          {:keys [bg bg- fg fg- fg+ p]} (st/fbg' :listitem)
          {:keys [br]} (booking-list-item-color-map relation)]

      [:div.flex

       (when insert-before
         (insert-before id))
       (if (some #{relation} [:precedes])

         ;past
         (let [{:keys [bg fg p] :as colormap} (st/fbg' :booking-listitem-past)]
           [:div.grid.gap-1.p-1.w-full
            {:style    {:grid-template-columns "1fr max-content"}
             :class    (concat fg bg br)
             :on-click #(when on-click (on-click item))}
            (bookers-name colormap item)
            (boat-number-list colormap {:fetch-boatdata-for fetch-boatdata-for
                                        :selected           selected})
            [:div.col-span-2
             [time-segment-display colormap item]]
            (if (some? description)
              [:div.col-span-3
               {:class (concat fg p)}
               description])])

         ;present and future
         (let [{:keys [bg fg fg+ p] :as colormap} (st/fbg' :booking-listitem)]
           [:div.grid.gap-1.p-1.w-full
            {:style    {:grid-template-columns "1fr max-content"}
             :class    (concat fg bg br)
             :on-click #(when on-click (on-click item))}

            (bookers-name colormap item)
            (if-not details?
              [:div.justify-self-end
               (boat-number-list colormap {:fetch-boatdata-for fetch-boatdata-for
                                           :selected           selected})]
              [:div])
            (when details?
              [:div.col-span-2.space-y-px
               (when-not (empty? selected)
                 (doall (for [id selected
                              :let [data (when fetch-boatdata-for (fetch-boatdata-for id))]
                              :while (some? data)]
                          [list-line
                           {;:insert-before-line-item (when insert-before-line-item insert-before-line-item) ;; for removal of items
                            ;:insert-before-line-item hov/open-details
                            :id         id
                            :data       data                ;(get (into {} boat-db) id)
                            :offset     offset
                            :time-slot  time-slot
                            :appearance (set/union #{:basic :xclear :xhide-location :xextra} appearance)
                            :overlap?   false}])))])

            [:div.col-span-2
             [time-segment-display colormap item]]
            (if (some? description)
              [:div.col-span-3
               {:class (concat fg+ p)}
               description])]))
       (when insert-below
         [:div.col-span-3 (insert-below)])
       (if insert-after
         (insert-after id)
         [:div])]))

#_(defn booking-list [{:keys [uid today booking-data accepted-user? class boat-db details?]}]
    (let [show-only-my-own? (-> (schpaa.state/listen :opt/show-only-my-own) deref)
          data (->> booking-data
                    ;(filter (comp (partial booking.views.picker/after-and-including today) val))
                    ;fixme BAD CODE
                    (filter (fn [[id data]] (if show-only-my-own?
                                              true
                                              (= uid (:uid data)))))
                    (sort-by (comp :start val) <))]
      (into [:div.space-y-px.bg-gray-500.dark:bg-gray-900
             {:class class}]
            (map (fn [[{:keys [start]} item]]
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
                                             (if (booking.views.picker/after-and-including today item)
                                               (if my-own?
                                                 (hov/remove-booking-details-button
                                                   id
                                                   (filter (fn [{:keys [] :as item}] (= (:id item) id)) (booking.database/read)))
                                                 [:div.w-10.shrink-0.flex])
                                               [:div.w-10.shrink-0.flex]))}
                      item]))
                 data))))

(defn overview [{:keys [uid data]}]
  (if (seq data)
    [booking.bookinglist/booking-list
     {:details?     @(rf/subscribe [:bookinglist/details])
      :booking-data (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read))
      :today        (t/date)
      :uid          uid}]
    [empty-list-message "Booking-listen er tom"]))

(defn- panel []
  [sc/col-space-4
   [sc/row-sc-g2-w
    [hoc.toggles/ls-sm :booking/editing "Rediger"]
    [hoc.toggles/ls-sm :booking/show-archived "Vis arkiverte"]
    [hoc.toggles/ls-sm :booking/show-description "Vis beskrivelse"]
    [hoc.toggles/ls-sm :booking/private-only "Bare vis mine"]]])
