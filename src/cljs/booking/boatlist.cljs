(ns booking.boatlist
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.toggles :as toggle]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [booking.common-widgets :as widgets]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]

            [schpaa.debug :as l]
            [lambdaisland.ornament :as o]
            [schpaa.style.input :as sci]
            [clojure.string :as str]))

(defn card [group {:keys [description] :as m}]
  (let [horisontal? @(schpaa.state/listen ::beskrivelse)]
    [sc/surface-a
     [sc/col-space-8
      (if-not horisontal?
        [sc/col
         [sc/col-space-2
          [sc/row-sc-g2-w
           [widgets/stability-name-category m]]

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
           [sc/co
            [sc/row-sc-g1-w'
             {:style {:align-items     :start
                      :justify-content :end
                      :flex-wrap       :wrap
                      :flex            "1 1 4rem"}}
             (into [:<>]
                   (for [[k v] (sort-by (comp :number val) < group)]
                     [sc/badge-2 {:class    [(when (pos? (:location v)) :invert)]
                                  :style    {:transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                  :on-click #(open-modal-boatinfo
                                               {:data (assoc (get @(rf/subscribe [:db/boat-db]) (keyword k)) :id (keyword k))})} (:number v)]))]])])]]))

(rf/reg-sub ::fontsize :-> #(:boatlist/fontsize % "100%"))

(defn canvas []
  (let [loggedin-uid (rf/subscribe [:lab/uid])
        data (sort-by (comp second first) <
                      (group-by (comp (juxt :boat-type :kind) val)
                                (remove (comp nil? :boat-type val)
                                        @(rf/subscribe [:db/boat-db]))))]
    [:div
     {:style {:font-size @(rf/subscribe [::fontsize])}}
     [:div.grid.gap-1.p-4
      {:style {:place-content         :center
               :grid-template-columns "repeat(auto-fill,minmax(20em,1fr))"}}
      (into [:<>]
            (for [[[boat-type-id _xxx] group] data
                  :let [m (get-in @(rf/subscribe [:db/boat-type])
                                  [(keyword boat-type-id)]
                                  ;the null-object
                                  {:empty       true
                                   :number      "999"
                                   :navn        "ukjent merke"
                                   :kind        "ukjent type"
                                   :description "Nyregistrert"})]]
              [card group m]))]]))

(o/defstyled small-grid :div
  :grid
  {:row-gap               "0.25rem"
   :column-gap            "0.25rem"
   :grid-template-columns "repeat(auto-fill,minmax(30ch,1fr))"})

(defn other []
  (let [card (fn [[boat-item-id
                   {:keys [number boat-type work-log] :as v}]]
               [sc/co {:class []}
                [sc/row
                 [widgets/badge
                  {:on-click #(open-modal-boatinfo {:data v})
                   :class    [:big]}
                  (or number "ingen")]]

                [small-grid
                 (for [[worklog-entry-id {:keys [complete deleted] :as v}] work-log]
                   [sc/co {:style {:background "var(--floating)"}
                           :class []}
                    [booking.modals.boatinfo/worklog-card {:class []} v
                     boat-item-id worklog-entry-id]])]])

        simplify (fn [[boat-item-id v]]
                   (let [{:keys [number work-log boat-type]} v]
                     [(some-> boat-item-id name) v]))
        data @(rf/subscribe [:db/boat-db])]
    (into [sc/co {:class [:p-4]}]
          (->> data
               (filter (comp some? :work-log val))
               (map simplify)
               (map card)))))


(defn first-completion [search completions]
  (or (when-not (empty? search)
        (when-let [result (->> completions
                               (filter #(or (str/starts-with? (str/upper-case %) (str/upper-case search))
                                            (str/includes? (str/upper-case %) (str/upper-case search))))
                               first)]
          {:whole result})) ""))

(defn page [r]
  {:always-panel     (fn []
                       [sc/row-sc-g2-w
                        [toggle/ls-sm ::beskrivelse "vis beskrivelse"]
                        [toggle/ls-sm ::båtnummer "vis båtnummer"]])
   :render-fullwidth (fn []
                       (let [completions (remove nil? (map (comp :navn val) (user.database/booking-users)))]
                         (r/with-let [search (r/atom "Larsen")]
                           [sc/co {:class [:p-4]}

                            [:div.h-auto
                             [sc/surface-a {:style {:background "black"
                                                    :height     "100%"
                                                    :heights    164}}
                              [sc/co {:style {:height "100%"}}
                               [sc/label "Autocompletion test"]
                               (let [{:keys [before whole]} (first-completion @search completions)]
                                 [:div.relative
                                  [sci/input'' {:type      :text
                                                :style     {:inset 0}
                                                :tab-index -1
                                                :value     whole}]

                                  [sci/input'' {:class       [:absolute :inset-0]
                                                :auto-focus  1
                                                :style       {:background "transparent"
                                                              :color      "white"}
                                                :type        :text
                                                :on-key-down #(let [c (.-keyCode %)]
                                                                (case c
                                                                  13 (reset! search (first-completion @search completions))
                                                                  nil))
                                                :value       @search
                                                :on-change   #(reset! search (.. % -target -value))}]])]]]])))})




