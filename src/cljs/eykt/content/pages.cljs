(ns eykt.content.pages
  (:require [shadow.resource :refer [inline]]
            [schpaa.markdown :refer [md->html]]
            [kee-frame.core :as k]
            [booking.hoc :as hoc]
            [re-frame.core :as rf]
            [schpaa.style :as st]
            [db.core :as db]
            [user.views]
            [times.api :as ta]
            [eykt.calendar.views]
            [eykt.calendar.core]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.debug :as l]
            [eykt.content.oppsett]
            [eykt.calendar.fiddle]
            [schpaa.button :as bu]
            [schpaa.components.fields :as fields]
            [reagent.core :as r]
            [tick.core :as t]
            [eykt.calendar.actions :as actions]))

(defn new-designed-content [{:keys [desktop?] :as m}]
  [:div
   {:class [:prose :prose-stone :dark:prose-invert :prose-h2:mb-2
            :prose-headings:font-black
            :prose-headings:text-alt
            :prose-h1:text-2xl
            :prose-h2:text-xl
            :prose-h3:text-lg
            :antialiased
            ;:md:mr-24
            ;:prose-h2:bg-alt
            ;"prose-headings:text-black/50"
            :prose-p:font-serif
            :prose-li:font-sans
            "prose-li:text-black/50"
            ;"prose-li:mr-8"
            "prose-li:italic"]}

   (md->html (inline "./about.md"))])

(defn user [r]
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])
        {:keys [bg fg- fg+ hd p p- he]} (st/fbg' :surface)]
    (if-not @user-auth
      [:div.p-4.max-w-md.mx-auto
       [rounded-view {:float 1} [db.signin/login]]]
      [:<>
       [:div.w-full
        {:class bg}
        [:div.p-4.max-w-md.mx-auto
         [user.views/userstatus-form
          {:user-auth @user-auth
           :name      (:display-name @user-auth)}]]]

       [:div.sticky.top-16.z-100.z-200
        [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
         [:r.user "Om meg" nil :icon :user]
         [:r.mine-vakter "Mine vakter" nil :icon :calendar]
         [:r.debug "Feilsøking" nil :icon :eye]]]

       [k/case-route (fn [route] (-> route :data :name))
        :r.user
        (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
          [:div.space-y-4
           {:class bg}
           [user.views/my-info]])

        :r.mine-vakter
        (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :form)]
          [:div.p-4
           {:class bg}
           [:div "en liste over mine vakter"]
           (l/ppre-x @(db/on-value-reaction {:path ["calendar" (:uid @user-auth)]}))])


        :r.debug
        [:div.z-100 [hoc/debug]]

        [:div @route]]])))

(defn render-tab-bar []
  [:div.sticky.top-16.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.common "Måned" nil :icon :calendar]
    [:r.common2 "Uke" nil :icon :calendar]
    [:r.oppsett "Oppsett" nil :icon :cog]]])

#_(defn config-for [b]
    (eykt.calendar.core/grab-for-graph b))

(defn status-for [dt]
  (get {"2022-02-02" {"11:00" {:slots 3
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["abe" "2022-01-02T12:31:50"]
                                       ["pop" "2022-01-02T12:31:50"]
                                       ["eve" "2021-01-02T12:31:50"]]}
                      "15:00" {:slots 3
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["abe" "2022-01-02T12:31:50"]
                                       ["pop" "2022-01-02T12:31:51"]
                                       ["eve" "2022-01-02T12:31:50"]]}
                      "06:00" {:slots 1
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["eve" "2022-01-02T12:31:50"]]}}
        "2022-02-04" {"18:00" {:slots 2
                               :items [["pop" "2022-01-02T12:31:50"]
                                       ["eve" "2022-01-02T12:31:50"]]}}
        "2022-04-27" {}} (str dt))

  #_(cond-> {:x "12:20"}
      (some #{(t/int (t/day-of-week dt))} #{2 3 4}) (assoc :rows 1)
      (some #{(t/int (t/day-of-week dt))} #{6 7}) (assoc :rows 2)))

(defn common [r]
  (let [uid @(rf/subscribe [::db/root-auth :uid])]
    [:<>
     (render-tab-bar)

     [k/case-route (fn [route] (-> route :data :name))
      :r.oppsett
      [eykt.content.oppsett/render r]

      :r.common (let [listener (db/on-value-reaction {:path ["calendar"]})
                      {:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
                  [:div.p-2
                   {:class bg}
                   [eykt.calendar.views/calendar
                    {:base (eykt.calendar.core/routine @listener)
                     :data (eykt.calendar.core/expand-date-range)}]])
      :r.common2
      (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :form)]
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
                       [:div
                        [l/ppre-x
                         the-week-interval]

                        (count this-weeks-config)
                        (into [:div.grid.gap-px.place-content-centerx
                               {:style {:grid-template-columns "2rem repeat(7,minmax(6rem,min-content))"}}]
                              (concat [[:div.flex.justify-center
                                        {:class (concat [:border-r :border-black :p-1] p- fg-)}
                                        (str (+ (js/parseInt @week) i))]]

                                      (for [e (range 7)
                                            :let [e (+ (* i 7) e)
                                                  dt (t/>> first-date-of-week (t/new-period e :days))]]
                                        (let [s (status-for dt)]

                                          [:div
                                           [:div (str (t/day-of-week dt))]
                                           [:div
                                            (for [[k e] (get this-weeks-config (str dt))]
                                              [:div
                                               [:div (:description (first e))]
                                               (for [each (sort-by :starttime < e)]
                                                 (let [render (fn [e]
                                                                [:div
                                                                 [:div {:class fg-} (str (:starttime e))]
                                                                 [:div.space-y-1
                                                                  [:div.space-y-px
                                                                   (for [e (range (:slots e))]
                                                                     [:div {:class bg-} (str e)])]
                                                                  [bu/regular-button-small
                                                                   {:on-click
                                                                    #(actions/add' {:uid      uid
                                                                                    :group    (name k)
                                                                                    :timeslot (str (:starttime e))
                                                                                    :dateslot dt})}
                                                                   "legg til"]]])]

                                                   (render each)))])]

                                           #_(for [[k groups] (into {} (get this-weeks-config (str dt)))
                                                   ;e (sort-by :starttime < groups)
                                                   :while (some? k)]
                                               (let [render (fn [e]
                                                              [:div.-debug
                                                               [:div (str (:starttime e))]
                                                               (for [e (range (:slots e))]
                                                                 [:div (str e)])])]
                                                 [:div.space-y-1

                                                  (for [e groups]
                                                    (do
                                                      (tap> groups)
                                                      [:div.p-1 (count groups) #_[l/ppre e]]))

                                                  #_(render e)]

                                                 #_(do
                                                     ;(tap> groups)
                                                     ;[l/ppre-x e]
                                                     [:div
                                                      [:div k]
                                                      (:description e)
                                                      " "
                                                      (str (:starttime e))
                                                      " "
                                                      (str (:slots e))])))

                                           #_(if (empty? s)
                                               [:div
                                                {:class (concat p- bg fg+)}
                                                [:div.flex.justify-start
                                                 {:class (concat [:hover:bg-gray-50] bg fg)} (ta/short-date-format dt)]]

                                               [:div
                                                {:class (concat p- bg fg)}

                                                [:div.flex.justify-start
                                                 {:class (concat [:hover:bg-gray-50] bg fg)} (ta/short-date-format dt)]

                                                (for [[k {:keys [slots items]}] (sort-by key < s)]
                                                  [:div.space-y-1
                                                   [:div {:class fg-} k]
                                                   (for [[idx [a b]] (map-indexed vector (sort-by second < items))]
                                                     [:div.ml-1 {:class (concat (if (<= slots idx) [:text-red-500] fg) bg-)} a])])

                                                #_[:div.space-y-px

                                                   [:div.px-1 {:class (concat p bg- fg-)}
                                                    [:div (if (= 1 (:rows s)) "18:00" "11:00")]
                                                    #_[:div.space-y-px
                                                       (for [e (range 3)]
                                                         [:div {:class (concat p bg- fg)} e])]]

                                                   (when (= 2 (:rows s))
                                                     [:div.px-1 {:class (concat p bg- fg-)}
                                                      [:div "14:00"]
                                                      #_[:div.space-y-px
                                                         (for [e (range 3)]
                                                           [:div {:class (concat p bg- fg)} e])]])]])]))))]))))]))


      #_(let [{:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
          [:div.p-4.space-y-1
           {:class (concat fg bg)}
           [:div.p-4 [eykt.calendar.core/render r]]])]]))