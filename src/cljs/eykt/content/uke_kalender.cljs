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

(defn render-day-column [{:keys [e' r' group-id uid e dt]}]
  (let [{:keys [bg- bg+ bg hd he fg+ fg p p- p+ fg-]} (st/fbg' :calender-table)
        c (- (:slots (first e')) (count r'))]
    [:div.-debugx
     ;[l/ppre-x group-id (:starttime each) r']
     [:div {:class (concat fg- p-)} (str (:starttime e))]
     [:div.space-y-1
      [:div.space-y-px

       (concat
         (for [[idx [k v]] (map-indexed vector (sort-by second < r'))]
           [:div.flex.flex-col.gap-2
            {:class (if (< idx (:slots (first e')))
                      (concat bg-)
                      (concat bg- [:text-red-500]))}

            [:div.w-12.truncate {:class p-} (str k)]
            #_[:div {:class p-} (ta/time-format (t/time (t/instant v)))]])

         (when (pos? c)
           (map (fn [e] [:div {:class (concat bg- p-)} "-" #_(inc e) " ledig"]) (range c))))]

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
          [bu/regular-button-small {} "Full"]))]]))

#_(defn uke-kalender [{:keys [uid]}]
    (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :form)]
      (r/with-let [week (r/atom (ta/week-number (t/date)))]
        [:div.p-4.space-y-2
         {:class bg}
         [:div.flex.gap-1
          [bu/regular-button-small {:on-click #(swap! week dec)} :chevron-left]
          #_[fields/text (-> {:naked?        true
                              :values        #(-> @week)
                              :handle-change #(reset! week (-> % .-target .-value))}
                             fields/number-field)
             :label "" :name :week]
          [bu/regular-button-small {:on-click #(swap! week inc)} :chevron-right]]
         [:div (ta/week-number (t/date))]

         #_(into [:div.grid.gap-px.place-content-centerx.sticky.top-28.bg-white
                  {:style {:grid-template-columns "2rem repeat(7,1fr)"}}
                  (map #(vector :div %) '(u ma ti on to fr lø sø))])

         (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :calender-table)
               first-date-of-week (ta/calc-first-day-at-week @week)]
           (into [:div.grid.gap-px
                  {:style {:grid-template-columns "repeat(auto-fit,minmax(20rem,1fr))"}}]
                 (for [i (range 1)]
                   (let [the-week-interval (tick.alpha.interval/new-interval
                                             first-date-of-week
                                             (t/>> first-date-of-week (t/new-period 7 :days)))
                         this-weeks-config (eykt.calendar.core/grab-for-graph the-week-interval)]
                     (into [:div.grid.gap-px.place-content-centerx]
                           {:style {:grid-template-columns "2rem repeat(7,minmax(min-content,1fr))"}})
                     (concat [[:div.flex.justify-center
                               {:class (concat [:border-r :border-black :p-1] p- fg-)}
                               (str (+ (js/parseInt @week) i))]

                              (for [e (range 7)
                                    :let [e (+ (* i 7) e)
                                          dt (t/>> first-date-of-week (t/new-period e :days))]]
                                (let [listener (db/on-value-reaction {:path ["calendar" (str dt)]})
                                      roo (eykt.calendar.core/rooo @listener)]
                                  (when (zero? e) (tap> @listener))
                                  [:div
                                   ;[l/ppre-x roo]
                                   [:div (ta/day-name dt :length 3)]
                                   [:div
                                    (for [[group-id e'] (get this-weeks-config (str dt))]
                                      [:div
                                       ;[l/ppre (:slots (first e'))]
                                       [:div {:class (concat p- [:truncate])} (:description (first e'))]
                                       (for [each (sort-by :starttime < e')]
                                         (let [r' (get-in roo [group-id (keyword (str (:starttime each)))])]
                                           (render-day-column
                                             {:e        each
                                              :e'       e'
                                              :r'       r'
                                              :group-id group-id
                                              :uid      uid
                                              :dt       dt})))])]]))])))))])))

(defn uke-kalender [{:keys [uid]}]
  (let [
        {:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :form)]
    (r/with-let [week (r/atom (ta/week-number (t/date)))]
      [:div.p-4.space-y-2
       {:class bg}
       [:div.flex.gap-1
        [bu/regular-button-small {:on-click #(swap! week dec)} :chevron-left]
        [fields/text (-> {:naked?        true
                          :values        #(-> @week)
                          :handle-change #(reset! week (-> % .-target .-value))}
                         fields/number-field) :label "" :name :week]
        [bu/regular-button-small {:on-click #(swap! week inc)} :chevron-right]]
       [:div (ta/week-number (t/date))]

       (into [:div.grid.gap-px.place-content-centerx.sticky.top-28.bg-white
              {:style {:grid-template-columns "2rem repeat(7,1fr)"}}]
             (map #(vector :div %) '(u ma ti on to fr lø sø)))

       (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :calender-table)
             first-date-of-week (ta/calc-first-day-at-week @week)]
         (into [:div.grid.gap-px
                {:style {:grid-template-columns "repeat(auto-fit,minmax(20rem,1fr))"}}]
               (for [i (range 1)]
                 (let [the-week-interval (tick.alpha.interval/new-interval
                                           first-date-of-week
                                           (t/>> first-date-of-week (t/new-period 7 :days)))
                       this-weeks-config (eykt.calendar.core/grab-for-graph the-week-interval)]
                   (into [:div.grid.gap-px.place-content-centerx
                          {:style {:grid-template-columns "2rem repeat(7,minmax(min-content,1fr))"}}]
                         (concat [[:div.flex.justify-center
                                   {:class (concat [:border-r :border-black :p-1] p- fg-)}
                                   (str (+ (js/parseInt @week) i))]]
                                 (for [e (range 7)
                                       :let [e (+ (* i 7) e)
                                             dt (t/>> first-date-of-week (t/new-period e :days))]]
                                   (let [listener (db/on-value-reaction {:path ["calendar" (str dt)]})
                                         roo (eykt.calendar.core/rooo @listener)]
                                     (when (zero? e) (tap> @listener))
                                     [:div.space-y-12.flex.flex-col.justify-between
                                      (for [[group-id e'] (get this-weeks-config (str dt))]
                                        [:div {:class (conj p-)}
                                         [:div {:class (concat fg+ bg+)} (:description (first e'))]
                                         (for [each (sort-by :starttime < e')
                                               :let [r' (get-in roo [group-id (keyword (str (:starttime each)))])]]
                                           (render-day-column
                                             {:e        each
                                              :e'       e'
                                              :r'       r'
                                              :group-id group-id
                                              :uid      uid
                                              :dt       dt}))])]))))))))])))