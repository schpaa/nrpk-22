(ns eykt.content.pages
  (:require [shadow.resource :refer [inline]]
            [schpaa.markdown :refer [md->html]]
            [kee-frame.core :as k]
            [booking.hoc :as hoc]
            [re-frame.core :as rf]
            [schpaa.style :as st]
            [db.core :as db]
            [user.views]
            [eykt.calendar.views]
            [eykt.calendar.core]
            [schpaa.components.views :as views :refer [rounded-view]]))

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
           [:div "en liste over mine vakter"]]
          #_[:div.w-screenx
             [:div.space-y-px.flex.flex-col.w-full
              {:class  :min-h-screen
               :-style {:min-height "calc(100vh + 3rem)"}}
              [:div.flex-1.w-fullx
               {:class bg}
               #_[hoc/user-logg]]]
             #_[hoc/all-boats-footer {}]])

        :r.debug
        [:div.z-100 [hoc/debug]]

        [:div @route]]])))

(defn common [r]
  [:<>                                                      ;<> ;div ;.flex.flex-col.w-screen.h-full.overflow-clip.sticky;.<> ;div.sticky.top-16


   #_[:div.sticky.top-16
      [:div.z-300.bg-pink-400.h-12
       [:div.xpt-32 "Hey 1"]]]

   #_[:div.sticky.top-16
      [:div.z-300.bg-alt.h-12
       [:div.xpt-32 "Hey 2"]]]

   ;[:div.h-64.bg-pink-500.sticky.top-16.z-300 "x"]

   [:div.sticky.top-16.z-200                                ;32.z-100.bg-alt.h-96.z-200 "."
    [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
     [:r.common "Kalender" nil :icon :square]
     [:r.common2 "Kalender 2" nil :icon :square]
     [:r.common3 "Uke" nil :icon :square]]]

   [k/case-route (fn [route] (-> route :data :name))
    :r.common3
    (let [{:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
      [:div.p-4.space-y-1
       {:class (concat fg bg)}
       [:div {:class (concat he fg-)} "Kalender"]
       [:div {:class (concat hd fg+)} "Kalender"]
       [:div {:class (concat p fg+)} (interpose " " (take 25 (repeatedly #(rand-nth '(her er litt av hvert)))))]
       [:div {:class (concat p fg-)} (interpose " " (take 15 (repeatedly #(rand-nth '(her er litt av hvert)))))]
       [:div {:class (concat p fg+)} (interpose " " (take 35 (repeatedly #(rand-nth '(her er litt av hvert)))))]])
    :r.common (let [listener (db/on-value-reaction {:path ["calendar"]})
                    {:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
                [:div.p-2
                 {:class bg}
                 [eykt.calendar.views/calendar
                  {:base (eykt.calendar.core/routine @listener)
                   :data (eykt.calendar.core/expand-date-range)}]])
    :r.common2
    (let [{:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
      [:div.p-4.space-y-1
       {:class (concat fg bg)}
       [:div.p-4 [eykt.calendar.core/render r]]])]])