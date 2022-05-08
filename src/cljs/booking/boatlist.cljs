(ns booking.boatlist
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.toggles :as toggle]
            [re-frame.core :as rf]
            [booking.common-widgets :as widgets]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]

            [schpaa.debug :as l]))

(defn card [group {:keys [description] :as m}]
  (let [horisontal? @(schpaa.state/listen ::beskrivelse)]

    [sc/zebra
     ;[l/pre group]
     [sc/col-space-8
      (if-not horisontal?
        [sc/col
         [sc/col-space-2
          [sc/row-sc-g2-w
           [sc/row {:style {:color "red"}}
            [widgets/stability-name-category m]]]

          (when @(schpaa.state/listen ::beskrivelse)
            [sc/col-space-2
             {:class [:-debug]
              :style {:flex "1 0 auto"}}

             [widgets/dimensions-and-material m]
             [sc/text1 {:style {:font-size "unset"}} description]])

          (when @(schpaa.state/listen ::båtnummer)
            [sc/row-sc-g1-w' {:style {:align-items     :start
                                      :justify-content :start
                                      :flex-wrap       :wrap}}
             (into [:<>]
                   (for [[k v] (sort-by (comp :number val) < group)]
                     [sc/badge-2 {:class    [:big (when (pos? (:location v)) :invert)]
                                  :style    {:font-size "unset"
                                             :transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                  :on-click #(open-modal-boatinfo
                                               {:data (assoc (get @(rf/subscribe [:db/boat-db]) (keyword k)) :id (keyword k))})}
                      (:number v)]))])]]
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
                                :style    {;:font-size "unset"
                                           :transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                :on-click #(open-modal-boatinfo
                                             {:data (assoc (get @(rf/subscribe [:db/boat-db]) (keyword k)) :id (keyword k))})} (:number v)])))])]]))

(rf/reg-sub ::fontsize :-> #(:boatlist/fontsize % "90%"))

(defn canvas []
  (let [loggedin-uid (rf/subscribe [:lab/uid])]
    [sc/col
     {:style {:font-size @(rf/subscribe [::fontsize])}}
     (into [:div.grid.gap-1 {:style {:place-content         :center
                                     :grid-template-columns "repeat(auto-fit,minmax(20em,1fr))"}}]
           (for [[[boat-type-id xxx] group]
                 (sort-by (comp second first) <
                          (group-by (comp (juxt :boat-type :kind) val)
                                    ;(remove (comp nil? :boat-type val))
                                    @(rf/subscribe [:db/boat-db])))
                 :let [m (get-in @(rf/subscribe [:db/boat-type])
                                 [(keyword boat-type-id)]
                                 ;the null-object
                                 {:empty       true
                                  :number      "999"
                                  :navn        "ukjent merke"
                                  :kind        "ukjent type"
                                  :description "Nyregistrert"})]]
             [card group m]))]))

(defn page [r]
  {:always-panel     (fn []
                       [sc/row-sc-g2-w
                        [toggle/ls-sm ::beskrivelse "vis beskrivelse"]
                        [toggle/ls-sm ::båtnummer "vis båtnummer"]])
   :render-fullwidth canvas})