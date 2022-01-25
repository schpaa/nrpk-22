(ns booking.time-navigator
  (:require [schpaa.components.fields :as fields]
            [times.api :refer [format]]
            [tick.core :as t]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]))

(defn naked [props]
  (assoc props :naked? true))

;region time-nav

(def cuty-color-map
  {:active-toggled [:active:bg-gray-700
                    :active:border-gray-600]
   :active         [:active:bg-gray-300
                    :active:border-gray-200]
   :normal         [:bg-gray-200
                    :border-gray-300
                    :dark:bg-gray-800
                    :dark:border-gray-800
                    :dark:text-gray-400]
   :toggled        [:bg-gray-800
                    :text-gray-200
                    :border-gray-800]
   :untoggled      [:bg-gray-200
                    :text-gray-700
                    :border-gray-200]
   :disabled       [:disabled:bg-gray-100
                    :disabled:text-gray-200
                    :disabled:border-gray-200]})

(defn cuty [icon & {:keys [action disabled state]}]
  (let [cm cuty-color-map]
    [:button.m-0.p-0.w-10.h-full.border-2.rounded.grid.place-content-center
     {:class    (concat
                  (:disabled cm)
                  (if (some? state)
                    (if state
                      (concat (:toggled cm) (:active-toggled cm))
                      (concat (:untoggled cm) (:active-toggled cm)))
                    (concat (:active cm) (:normal cm))))

      :disabled disabled
      :on-click action
      :type     :button}
     (if (string? icon)
       [:div.text-base.font-semibold.antialiased icon]
       [:div.w-6.h-6 (schpaa.icon/adapt icon 1.5)])]))

(defn- big-arrow [right?]
  [:div.mx-2.absolute.inset-y-0
   {:class (if right? :right-0 :left-0)}
   [:svg.w-4.h-full
    {:class            ["text-black/10"
                        "dark:text-white/10"]
     :viewBox          "0 0 6 20"
     :transform        (when-not right? "scale(-1, 1)")
     :transform-origin "center"}
    [:path {:vector-effect :non-scaling-stroke
            :fill          :none
            :stroke        :currentColor
            :stroke-width  4
            :d             "M 0 0 L 5 10 L 0 20"}]]])

(comment
  (do
    (let [a (atom {:vals {:start (str (t/date-time))}})]
      (when-let [x ((juxt (comp t/date-time :start)) (get @a :vals))]
        x))))

;region small helpers

(defn point [n complete]
  [:div {:class (concat
                  [:flex :items-center :justify-center :h-6 :aspect-square :rounded-full :font-bold :text-base]
                  (if complete [:text-white :bg-alt] [:text-white :bg-sky-500]))}
   (if complete
     (icon/adapt :checked 3)
     n)])

(defn step [n c & {:keys [complete on-click final active] :or {on-click #()}}]
  [:div.px-4.py-2
   {:sclass (if complete [:bg-alt :text-white] [:bg-rose-500 :text-white])
    :class (if active [:border-b-4 :border-alt])
    :on-click #(on-click) :type :button}
   [:div.flex.items-center.gap-2.col-span-1.border-none.outline-none.focus:outline-none.focus:ring-none
    ;(point n complete)
    [:div.text-lg c]]])

;endregion


