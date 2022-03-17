(ns booking.aktivitetsliste
  (:require [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [db.core :as db]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [reagent.core :as r]
            [schpaa.debug :as l]
            [tick.core :as t]))

(comment
  (do
    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 10 0)))))

(def data
  (r/atom {"A" {:start    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 10 0)))
                :list     #{123}
                :children 1
                :data     1}
           "B" {:start (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 15 0)))
                :end   (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 17 0)))
                :list  #{100 120}
                :data  1}
           "C" {:start  (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 14 0)))
                :end    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 20 0)))
                :list   #{100}
                :adults 1
                :data   1}
           "D" {:start  (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 7 0)))
                :list   #{100 120}
                :adults 1
                :data   1}
           "E" {:start    (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 14 0)))
                :list     #{100 120}
                :adults   2
                :children 1
                :data     1}
           "F" {:start (t/instant (t/at (t/new-date 2022 3 17) (t/new-time 12 0)))
                :list  #{100 121}
                :data  1}
           "overnattF"
           {:key      true
            :moon     true
            :adults   10
            :children 2
            :start    (t/instant (t/at (t/new-date 2022 3 16) (t/new-time 12 0)))
            :list     #{100 120 130 150 160 164 142}
            :data     1}}))

(o/defstyled listitem :div
  :py-1
  [:&
   :flex :items-end
   [:.selected :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :border-radius  "var(--radius-2)"
     :color          [:rgba 0 0 0 1]
     :background     [:rgba 0 0 0 0.09]}]
   [:.normal :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :color          [:rgba 0 0 0 0.5]}]
   [:&:hover {:border-radius "var(--radius-1)"
              :background    [:rgba 0 0 0 0.1]}]])

(defn delete-command [k st]
  (swap! st update-in [k] assoc :deleted true)
  #_(db/database-update
      {:path  ["booking-posts" "articles" (name k)]
       :value {:deleted true}}))

(defn undelete-command [k st]
  (swap! st update-in [k] assoc :deleted false)
  #_(db/database-update
      {:path  ["booking-posts" "articles" (name k)]
       :value {:deleted false}}))

(defn add-command [d]
  (swap! data assoc (random-uuid) d))

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)
   {:on-click #((if deleted undelete-command delete-command) k data)}
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    (if deleted (icon/small :rotate-left)
                [:> outline/TrashIcon])]])

(defn edit [k {:keys []}]
  [scb/round-normal-listitem
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    [:> outline/PencilIcon]]])

(defn fancy-timeline [start end now]
  (let [start-hour (or (some-> start t/hour) 0)
        end-hour (some-> end t/hour)
        now-hour (t/hour now)]
    [:svg
     {:class               [:h-full]
      :xstyle              {:min-width "4rem"
                            :max-width "20ch"}

      :viewBox             "0 0 23 10"
      :preserveAspectRatio "none"
      :width               "100%"
      :height              "auto"}

     [:rect {:vector-effect :non-scaling-stroke
             :fill          :white
             :width         :100%
             :height        :100%}]

     (let [w (or (some-> end-hour (- start-hour))
                 (- now-hour start-hour))]
       [:<>
        [:rect {:fill         :0
                :stroke-width 0
                :y            1
                :x            (- start-hour 2)
                :width        (+ w 2)
                :height       2}]
        [:path {:fill      :white
                :stroke    :none
                :transform (str "translate(" (- start-hour 2) "," 1 ")")
                :d         "M0,0 L1,1 L0,2 z"}]
        (tap> {:end-hour end-hour})
        (when (nil? end-hour)
          [:path {;:stroke    :red
                  :vector-effect :non-scaling-stroke
                  :fill          "red"
                  :transform     (str "translate(" (+ start-hour w) "," 1 ")")
                  :d             "M0,0 L2,1 L0,2 z"}])])
     ;[:text {:x 0 :y 4 :font-size "2px"} start-hour]
     ;[:text {:x 0 :y 7 :font-size "2px"} (or end-hour)]
     ;[:text {:x 3 :y 7 :font-size "2px"} (or now-hour)]
     [:rect {:vector-effect :non-scaling-stroke
             :fill          "rgba(255, 255,0, 0.5)"
             :x             11
             :width         :6
             :height        :100%}]]))

(defn render [r]
  (into [:div.space-y-1.p-1]
        (for [[k {:keys [start end key moon adults children deleted list datetime] :as e}]
              (sort-by (comp :start val) < (filter (fn [[_ {:keys [start end]}]] (when (and (some? start))
                                                                                   (or
                                                                                     (and (t/< (t/date start) (t/date (t/now))) (nil? end))
                                                                                     (= (t/date (t/now)) (t/date start))))) (seq @data)))]
          [listitem
           [:div.flex.w-full
            {:class (into [:justify-between
                           :items-start :gap-2 :px-2]
                          (if deleted [:line-through :opacity-50]))}
            (trashcan k e)
            (edit k e)
            [:div.w-10.shrink-0
             [:div.w-auto.flex.gap-1.items-baseline
              [sc/text adults]
              [sc/small children]]
             [:div.w-auto.flex.gap-1
              (when key (sc/icon-tiny [:> solid/KeyIcon]))
              (when moon (sc/icon-tiny [:> solid/MoonIcon]))]]

            (into [sc/surface-b-sans-bg {:class [:gap-1 :shrink-1 :w-full]
                                         :style {:padding               "var(--size-1)"
                                                 :max-width             "17ch"
                                                 :min-width             :4rem
                                                 ;:background            :#aaf
                                                 :display               :grid
                                                 :grid-template-columns "repeat(auto-fit,minmax(2.53rem,min-content))"
                                                 :grid-auto-rows        "2rem"}}]
                  (map #(vector sc/badge {:on-click (fn [e] (tap> %))
                                          :selected false} (str %))) list)

            [:div.shrink-1.h-10.shadow-sm.w-full
             {:style {:flex-grow     1
                      :overflow      :hidden
                      :border-radius "var(--radius-1)"}}
             [fancy-timeline
              (if (t/< (t/date start) (t/date (t/now))) nil (t/time start))
              (some-> end t/time)
              (t/new-time 21 0)]]]])))
