(ns booking.boatlist
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.toggles :as toggle]
            [re-frame.core :as rf]
            [booking.common-widgets :as widgets]
            [schpaa.style.dialog :as dlg]))

(defn card [group {:keys [description] :as m}]
  (let [horisontal? @(schpaa.state/listen ::beskrivelse)]
    [sc/zebra
     [sc/col-space-8

      (if-not horisontal?
        [sc/col
         [sc/col-space-2
          [sc/row-sc-g2-w
           [sc/row [widgets/stability-name-category m]]]

          (when @(schpaa.state/listen ::beskrivelse)
            [sc/col-space-2
             {:class [:-debug]
              :style {:flex "1 0 auto"}}
             [widgets/dimensions-and-material m]
             [sc/text1 {:style {:font-size "unset"}} description]])

          (when @(schpaa.state/listen ::båtnummer)
            (into [sc/row-sc-g1-w' {:style {:align-items     :start
                                            :justify-content :start
                                            :flex-wrap       :wrap}}]

                  (for [[k v] (sort-by (comp :number val) < group)]
                    [sc/badge-2 {:class    [:small (when (pos? (:location v)) :invert)]
                                 :style    {:font-size "unset"
                                            :transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                 :on-click #(dlg/open-modal-boatinfo
                                              (let [loggedin-uid @(rf/subscribe [:lab/uid])]
                                                {:uid  loggedin-uid
                                                 :data (get @(rf/subscribe [:db/boat-db]) (keyword k))}))} (:number v)])))]]
        [:div.flex.justify-between.gap-4

         [sc/col-space-8
          [sc/row-sc-g2-w
           [sc/row [widgets/stability-name-category m]]]

          (when @(schpaa.state/listen ::beskrivelse)
            [sc/col-space-2
             {:style {:flex "1 0 auto"}}
             [widgets/dimensions-and-material m]
             [sc/text1 {:style {:font-size "unset"}} description]])]

         (when @(schpaa.state/listen ::båtnummer)
           (into [sc/row-sc-g1-w' {:style {:align-items     :start
                                           :justify-content :end
                                           :flex-wrap       :wrap
                                           :flex            "1 1 4rem"}}]
                 (for [[k v] (sort-by (comp :number val) < group)]
                   [sc/badge-2 {:class    [:small (when (pos? (:location v)) :invert)]
                                :style    {:font-size "unset"
                                           :transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                :on-click #(dlg/open-modal-boatinfo
                                             (let [loggedin-uid @(rf/subscribe [:lab/uid])]
                                               {:uid  loggedin-uid
                                                :data (get @(rf/subscribe [:db/boat-db]) (keyword k))}))} (:number v)])))])]]))

(rf/reg-sub ::fontsize :-> #(:boatlist/fontsize % "90%"))

(defn canvas []
  (let [loggedin-uid (rf/subscribe [:lab/uid])]
    [sc/col
     {:style {:font-size @(rf/subscribe [::fontsize])}}
     (into [:div.grid.gap-1 {:style {:place-content         :center
                                     :grid-template-columns "repeat(auto-fit,minmax(20em,1fr))"}}]
           (for [[[boat-type-id xxx] group]
                 (sort-by (comp second first) <
                          (group-by (juxt (comp :boat-type val)
                                          (comp :kind val))
                                    (remove (comp nil? :boat-type val)
                                            @(rf/subscribe [:db/boat-db]))))
                 :let [m (get-in @(rf/subscribe [:db/boat-type])
                                 [(keyword boat-type-id)])]]
             (card group m)))]))

(defn page [r]
  {:always-panel     (fn []
                       [sc/row-sc-g2-w
                        [toggle/ls-sm ::beskrivelse "vis beskrivelse"]
                        [toggle/ls-sm ::båtnummer "vis båtnummer"]])
   :render-fullwidth canvas})