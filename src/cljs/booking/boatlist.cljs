(ns booking.boatlist
  (:require [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.toggles :as toggle]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [booking.common-widgets :as widgets]
            [booking.modals.boatinfo :refer [open-modal-boatinfo]]
            [lambdaisland.ornament :as o]
            [schpaa.style.input :as sci]
            [clojure.string :as str]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.debug :as l]))

;; redefinitions

(def button hoc.buttons/pill)

(rf/reg-sub ::fontsize :-> #(:boatlist/fontsize % "100%"))

(o/defstyled small-grid :div
  :grid
  {:row-gap               "0.25rem"
   :column-gap            "0.25rem"
   :grid-template-columns "repeat(auto-fill,minmax(30ch,1fr))"})

;; store

(defonce store
         (r/atom
           {:new-boats true
            :nøklevann true
            :sjøbasen  true
            :selector  :a}))

;; helpers

(def colors
  (for [e (concat ["00"] (map str (range 10)) ["99"])]
    (str "var(--gray-" e ")")))

(defn color-map [c]
  (into [:<>]
        (for [e (or c colors)]
          [:div.w-10.h-10 {:style {:background-color e}}])))

(defn color-map-text [c co]
  (into [:<>]
        (for [e (or c colors)]
          [co {:style {:color e}} "Some text " e])))

;; completion

(defn first-completion [search completions]
  (tap> search)
  (or (when-not (empty? search)
        (when-let [result (->> completions
                               (filter #(or (str/starts-with? (str/upper-case %) (str/upper-case (str search)))
                                            #_(str/includes? (str/upper-case %) (str/upper-case search))))
                               first)]
          result)) ""))

(defn completion-textfield [{:keys [placeholder label] :as attr}]
  (let [completions (remove nil? (map (comp :navn val) (user.database/booking-users)))]
    (r/with-let [search (r/atom "")]
      [sc/co
       [:div.h-auto
        [sc/co-field {:style {:text-transform :uppercase
                              :height         "100%"}}
         [sc/label (or label "Autocompletion test")]

         (let [whole (first-completion @search completions)]
           [:div.relative
            [sci/input'' {:type      :text
                          :style     {:inset          0
                                      :text-transform :capitalize}
                          :tab-index -1
                          :value     (if (and
                                           (not (empty? @search))
                                           (= (str/upper-case @search)
                                              (str/upper-case (subs (str whole) 0 (count @search)))))
                                       whole
                                       "")}]
            [sci/input'' {:class       [:absolute :inset-0]
                          :placeholder placeholder
                          :style       {:text-transform :capitalize
                                        :background     "transparent"}
                          :type        :text
                          :on-key-down #(let [c (.-keyCode %)]
                                          (case c
                                            27 (reset! search nil)
                                            13 (reset! search whole)
                                            nil))
                          :value       @search
                          :on-change   #(reset! search (.. % -target -value))}]])]]])))

;; components

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
                     [widgets/badge
                      {:class    [:big (when (pos? (:location v)) :invert)]
                       :style    {:font-size "unset"
                                  :transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                       :on-click #(open-modal-boatinfo
                                    {:data (assoc (get @(rf/subscribe [:db/boat-db]) (keyword k)) :id (keyword k))})}
                      (:number v)
                      (:slot v)]))])]]
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
                                  ;:style    {:transform (str "rotate(" (- 1.5 (rand-int 3)) "deg)")}
                                  :on-click #(open-modal-boatinfo
                                               {:data (assoc (get @(rf/subscribe [:db/boat-db]) (keyword k)) :id (keyword k))})} (:number v)]))]])])]]))



(defn toggle [c caption]
  [button {:on-click #(swap! c not)
           :class    [:narrow :outline2 (when @c :inverse)]} caption])

;; UI

(defn control-panel []
  (r/with-let [new-boats (r/cursor store [:new-boats])
               sjøbasen (r/cursor store [:sjøbasen])
               nøklevann (r/cursor store [:nøklevann])
               selector (r/cursor store [:selector])]
    [:<>
     [:div {:class [:sticky :top-16]
            :style {:z-index 0}}
      [sc/row-center {:class [:py-8]}
       [widgets/pillbar selector [[:a "Båter"]
                                  [:b "Arbeidsliste"]]]]]

     #_[sc/row {:class [:w-full]}
        [sc/row-g3
         [toggle/ls-sm ::beskrivelse "vis beskrivelse"]
         [toggle/ls-sm ::båtnummer "vis båtnummer"]]
        [:div.grow]
        #_[sc/row-g3 {:style {:justify-content :end}}
           [toggle new-boats "Nye båter"]
           [toggle sjøbasen "Sjøbasen"]
           [toggle nøklevann "Nøklevann"]]]]))

(defn canvas []
  (let [loggedin-uid (rf/subscribe [:lab/uid])
        data (sort-by (comp second first) <
                      (group-by (comp (juxt :boat-type :kind) val)
                                (remove (comp nil? :boat-type val)
                                        @(rf/subscribe [:db/boat-db]))))]
    [:div
     {:style {:font-size @(rf/subscribe [::fontsize])}}
     [:div.grid.gap-1
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

(defn arbeidsliste []
  (let [card (fn [[boat-item-id
                   {:keys [number slot work-log] :as v}]]
               [sc/co
                [sc/row-sc-g4-w
                 [widgets/badge
                  {:on-click #(open-modal-boatinfo {:data v})
                   :class    [:big]}
                  (or number boat-item-id "ingen")
                  slot]
                 [widgets/stability-name-category v]]
                [small-grid
                 (for [[worklog-entry-id {:keys [deleted complete] :as v}] work-log
                       :when (not (or deleted complete))]
                   [booking.modals.boatinfo/worklog-card
                    {} v boat-item-id worklog-entry-id])]])
        data (->> @(rf/subscribe [:db/boat-db])
                  (filter (fn [[k v]]
                            (if (map? v)
                              (let [{:keys [work-log]} v]
                                (and (some? work-log)))
                              false))))]
    [sc/col-space-4
     (into [:<>] (map card data))]))

(defn content []
  [:div.p-2
   (case (:selector @store)
     :a [canvas]
     :b [arbeidsliste]
     [sc/co
      [completion-textfield
       {:label       "Alle registrerte nøkkelvakter"
        :placeholder "Søk"}]
      [sc/col
       (-> colors (color-map) (sc/row))
       [sc/co [color-map-text colors sc/text1]]]])])

;; machinery

(defn page [r]
  {:always-panel     control-panel
   :render-fullwidth content})
