(ns schpaa.components.tab
  (:require [re-frame.core :as rf]
            [reagent.dom]
            [reagent.core :as r]
            [schpaa.icon :as icon]
    #_[schpaa.components.tab :refer-macros [tw]
       [schpaa.style :as st]]
            [schpaa.style :as st])
  (:require-macros [schpaa.components.tab :refer [tw]]))


(def select-bar-top-config
  {:class     [:flex
               ;:border-2
               :border-sky-900
               :last:rounded-br
               :first:rounded-bl]
   :on-class  [:btn-on :bg-sky-900 :font-bold]
   :off-class [:btn-off :font-normal]})

(def select-bar-config
  {:class     [:flex
               :border-2
               :border-sky-900
               :last:rounded-r
               :first:rounded-l]
   :on-class  [:btn-on :h-10 :w-12 :bg-sky-900 :font-bold]
   :off-class [:btn-off :h-10 :w-12 :font-normal]})

(def select-bar-bottom-config
  {:class     [:flex
               :border-2
               :border-black :dark:border-gray-400
               :last:rounded-r
               :first:rounded-l]
   :on-class  [:btn-on :h-8 :w-6 :bg-black-900 :dark:bg-gray-400 :dark:text-black :font-bold]
   :off-class [:btn-off :h-8 :w-6 :font-normal :bg-gray-300 :text-gray-900]})

(defn- big-arrow [right?]
  [:div.mx-1.absolute.inset-y-2.mb-2.-debug
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

(defn tab
  [_ & m]
  (let [node (atom nil)
        at-edge (r/atom false)
        scroll-pos (r/atom 0)
        items (count m)
        limit 455
        limit-items 3]
    (r/create-class
      {:display-name
       "test"

       :component-did-mount
       (fn [c]
         (let [_ (reset! node (reagent.dom/dom-node c))
               clientWidth (.-clientWidth @node)]
           (doto @node
             (.addEventListener
               "scroll"
               (fn [x]
                 (let [sl (-> x .-target .-scrollLeft)
                       clientWidth (.-clientWidth @node)
                       items' (if (< clientWidth limit) limit-items 4)]
                   (reset! at-edge (< (- (/ clientWidth items') 10) sl))
                   (reset! scroll-pos sl)
                   (tap> ["INNER " (/ clientWidth items') sl (< (/ clientWidth items') sl)])))))))

       :component-did-update
       (fn []
         (tap> ":component-did-update"))
       :reagent-render
       (fn [{locked?  :locked?
             selected :selected
             select   :select}]
         (let [{:keys [bg p p+ fg fg+ hd br br+]} (st/fbg' :tabbar)
               on-class (concat hd bg fg+ br+ [])
               off-class (concat hd bg fg)
               o (or select #(rf/dispatch [:app/navigate-to [%]]))]
           [:div.flex.snap-x.snap-mandatory.overflow-x-auto.hide-scrollbar.z-50.h-12
            {:class (concat bg [:border-b] br)
             :style {:scrollbar-width :none}}
            (for [[page title action & {:keys [icon]}] m
                  :let [selected? (= selected page)]]
              [:div.shrink-0.flex.justify-center.snap-start.h-12x ;<-- WTF? h-12x?
               {:on-click (when-not locked? #(do (o page)
                                                 (when (some? action)
                                                   (action page))))
                :class    (concat
                            ;[:hover:bg-gray-200  :rounded-sm]
                            ["w-1/3"]
                            (if selected? on-class off-class))}
               [:button.justify-self-center.self-start.rounded.mt-3
                (conj {:class (concat [
                                       :outline-none :focus:outline-none :focus:ring-offset-transparent :focus:ring-0])}
                      #_(when-not locked?
                          {:on-click #(do (o page)
                                          (when (some? action)
                                            (action)))}))
                [:div.flex.gap-2.items-center.justify-center.w-full.whitespace-nowrap.overflow-clip
                 (if icon [:div.w-6.h-6.hidden.xs:block {:class fg+} [icon/adapt icon 1.6]])
                 [:div
                  {:class p}
                  title]]]])]))})))
