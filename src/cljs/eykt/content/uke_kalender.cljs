(ns eykt.content.uke-kalender
  (:require [reagent.core :as r]
            [times.api :as ta]
            [tick.core :as t]
            [schpaa.style :as st]
            [tick.alpha.interval]
            [schpaa.button :as bu]
            [schpaa.components.fields :as fields]
            [db.core :as db]
            [eykt.calendar.core]
            [eykt.calendar.actions :as actions]
            [schpaa.debug :as l]))

(defn render-block-column [{:keys [e' r' group-id uid e dt]}]
  (let [{:keys [bg- bg+ bg hd he fg+ fg p p- p+ fg-]} (st/fbg' :calender-table)
        c (- (:slots (first e')) (count r'))]
    [:div                                                   ;.border.border-alt
     ;[l/ppre-x group-id (:starttime each) r']
     [:div {:class (concat fg+ p-)} (str (:starttime e))]
     [:div.space-y-px
      (if (get r' (keyword uid))
        [bu/danger-button-small
         {:on-click
          #(actions/delete' {:uid      uid                  ; ;"b-person"
                             :group    (name group-id)
                             :timeslot (str (:starttime e))
                             :dateslot dt})} "fjern"]

        (if (pos? c)
          [bu/hollow-button-small
           {:on-click
            #(actions/add' {:uid      uid                   ; ;"b-person"
                            :group    (name group-id)
                            :timeslot (str (:starttime e))
                            :dateslot dt})} "velg"]
          [:div.h-6.flex.flex-center {:class bg-} "Full"]))

      [:div.space-y-px

       (concat
         (for [[idx [k v]] (map-indexed vector (sort-by second < r'))]
           [:div.flex.flex-col.gap-2
            {:class (if (< idx (:slots (first e')))
                      (concat bg-)
                      (concat bg- [:text-red-500]))}

            [:div.w-12.truncate.h-6 {:class p-} (str k)]
            #_[:div {:class p-} (ta/time-format (t/time (t/instant v)))]])

         (when (pos? c)
           (map (fn [e] [:div.h-6 {:class (concat bg- p-)} " " ""]) (range c))))]]]))

(defn uke-kalender [{:keys [uid]}]
  (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :form)]
    (r/with-let [week (r/atom (ta/week-number (t/date)))]
      [:div.space-y-2
       {:class (concat bg fg)}

       [:div.flex.gap-1.justify-start.p-4
        {:class bg-}
        [bu/regular-button {:on-click #(swap! week dec)} :chevron-left]
        [fields/text (-> {:naked?        true
                          :values        #(-> @week)
                          :handle-change #(reset! week (-> % .-target .-value))}
                         fields/number-field) :label "" :name :week]
        [bu/regular-button {:on-click #(swap! week inc)} :chevron-right]]

       (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :calender-table)]

         (into [:div.grid.gap-4.px-2.place-content-center
                {:style {:grid-template-columns "repeat(auto-fit,minmax(16rem,40rem) )"
                         :grid-auto-rows        "1fr"}}]
               (doall (for [i (range 2)]
                        (into [:div.grid.gap-px.min-w-screen
                               {:style {:grid-template-columns "1.2rem repeat(7,minmax(1rem,1fr ))"
                                        :grid-auto-rows        "1fr"}}]
                              (concat [
                                       ;intent WEEKS
                                       [:div.flex.flex-col.flex-center.h-20.sticky.top-32.bg-white
                                        {:class (concat p- fg)}
                                        (str "u" (+ (js/parseInt @week) i))]]

                                      (doall (for [e (range 7)
                                                   :let [first-date-of-week (ta/calc-first-day-at-week (+ (js/parseInt @week) i))
                                                         the-week-interval (tick.alpha.interval/new-interval
                                                                             first-date-of-week
                                                                             (t/>> first-date-of-week (t/new-period 7 :days)))
                                                         this-weeks-config (eykt.calendar.core/grab-for-graph the-week-interval)
                                                         day-offset (+ (* i 7) e)
                                                         dt (t/>> first-date-of-week (t/new-period e :days))
                                                         listener (db/on-value-reaction {:path ["calendar" (str dt)]})
                                                         roo (eykt.calendar.core/rooo @listener)]]
                                               [:div.flex.flex-col.justify-start.h-full.space-y-1
                                                {:class (concat p fg)}

                                                ;intent CALENDAR-STRIP
                                                (let [first-in-month? (or (zero? e) (= 1 (t/day-of-month dt)))]
                                                  [:div.flex.flex-col.justify-between.items-center.h-20.truncate.sticky.top-28.bg-white
                                                   {:class (if (= 1 (t/day-of-month dt)) [:border-l-2 :border-gray-500] [:border-l])}
                                                   [:div.shrink-0.grow-1.h-6 {:class (concat p fg+)}
                                                    (if first-in-month?
                                                      [:div (ta/month-name dt :length 3)]
                                                      [:div ""])]
                                                   [:div.grow.shrink-0
                                                    [:div (t/format "d." dt)]
                                                    [:div.text-center {:class (concat p- fg)} (ta/day-name dt :length 3)]]])

                                                (doall (for [[group-id e'] (get this-weeks-config (str dt))
                                                             #_#_:while (some? group-id)]
                                                         [:div {:class (conj p-)}

                                                          ;[:div.text-red-500 group-id]
                                                          [:div.text-red-500 (ta/short-date-format first-date-of-week)]
                                                          [:div.text-red-500 (ta/short-date-format dt)]
                                                          ;[:div.text-red-500 day-offset]

                                                          [:div {:class (concat fg bg [:truncate])}
                                                           (:description (first e'))]
                                                          (doall (for [each (sort-by :starttime < e')
                                                                       :let [r' (get-in roo [group-id (keyword (str (:starttime each)))])]]
                                                                   [render-block-column
                                                                    {:uid      uid
                                                                     :dt       dt
                                                                     :e        each
                                                                     :e'       e'
                                                                     :r'       r'
                                                                     :group-id group-id}]))]))]))))))))])))