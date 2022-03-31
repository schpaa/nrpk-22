(ns booking.carousell
  (:require

    [re-frame.core :as rf]

    [reagent.core :as r]
    ["react-slick" :default Slider]
    [schpaa.icon :as icon]))

(defn- dot [{:keys [border] :as view-config} active]
  [:svg.w-5.h-16
   {:class   :currentColor
    :viewBox "0 0 10 10"}
   [:circle {:vector-effect :non-scaling-stroke
             :fill          :currentColor
             ;:stroke        :black
             :stroke-width  1
             :cx            5
             :cy            5
             :r             (if active 3 1)}]])

(def react-slick (r/adapt-react-class Slider))

(defn widget [{:keys [icon on-click disabled]}]
  [:div.w-16.h-full.flex-shrink-0
   {:on-click on-click
    :disabled disabled
    :class    (concat
                (if disabled [:text-opacity-20]
                             [:active:bg-gray-400
                              :active:bg-opacity-30
                              :active:text-gray-600]))}
   [:div.center
    [icon/touch icon]]])

(defn- page-scroll-button [{:keys [view-config class on-click icon icon-disabled component disabled]}]
  (let [inside? (:inside? view-config)
        hide-left-right? (:hide-left-right? view-config)]
    [:div.absolute.flex.bottom-0.h-16
     {:class (concat
               (if-not inside? [:-mb-16] [])

               class
               [:bg-opacity-10]
               (if disabled [:text-opacity-25]
                            [:active:bg-gray-400
                             :active:bg-opacity-30
                             :active:text-gray-600]))}
     [widget (merge {:disabled disabled
                     :on-click #(when-let [c @component]
                                  (on-click c))}
                    (when-not hide-left-right? {:icon (if disabled icon-disabled icon)}))]]))

(defn slick-config [{:keys [inside? border fg bg] :as view-config} state]
  {:dots             true
   :infinite         true
   :slidesToShow     1
   :pauseOnHover     true
   :autoplay         true
   :autoplaySpeed    4000
   :touchMove        true
   :hide-left-right? false
   :speed            500
   :initialSlide     0
   :arrows           false
   :adaptiveHeight   false
   :fade             false
   :pauseOnDotsHover true
   :customPaging     (fn [e] (r/as-element
                               [:div {:class (if inside? [:-mt-16 :pt-1] [:mt-1])}
                                [:a [dot view-config (= e @state)]]]))
   :beforeChange     (fn [current next] (reset! state next))})

(defn slide
  "helper to present slides with a certain height or aspectratio"
  [{:keys [on-click content]}]
  [:div.w-full.h-full                                       ;.aspect-w-16.aspect-h-9
   {:on-click on-click}
   content])

(defn slide-aspect-16-9
  "helper to present slides with a certain height or aspectratio"
  [{:keys [on-click content class]}]
  [:div.sw-full.xh-full.aspect-w-16.aspect-h-9.overflow-hidden.leading-snug.space-y-4
   {:class    class
    :on-click on-click}
   content])

(defn slide-a
  "helper to present slides with a certain height or aspectratio"
  [{:keys [on-click content class]}]
  [:div.xw-full.xh-full.aspect-w-4.aspect-h-3.overflow-hidden.leading-snug.space-y-4x
   {:class    class
    :on-click on-click}
   content])

(defn render-carousell [{:keys [view-config carousell-config contents class]}]
  (let [*active-page (r/atom 0)
        *com (r/atom nil)
        view-config (or view-config {:fg :text-amber-100
                                     :bg :bg-amber-400})
        inside? (:inside? view-config)
        carousell-config (if carousell-config
                           (merge (slick-config view-config *active-page) carousell-config)
                           (slick-config view-config *active-page))]

    (fn []
      [:div
       {:class (concat class
                       (if inside? [] [:pb-16])
                       ;[:mb-16]
                       [:w-full]
                       (:bg view-config)
                       (:fg view-config))}
       [:div.relative
        [:div {:class (concat
                        (:bg view-config)
                        (:ctrls view-config))}
         (into [react-slick (assoc carousell-config :ref #(reset! *com %))] contents)]
        [:<>
         (page-scroll-button
           {:view-config   view-config
            :component     *com
            :on-click      #(.slickPrev ^js %)
            :icon          :chevron-left
            :icon-disabled :chevron-left-end
            :disabled      (= @*active-page 0)
            :class         (concat [:left-0] (:ctrls view-config))})
         (page-scroll-button
           {:view-config   view-config
            :component     *com
            :on-click      #(.slickNext ^js %)
            :icon          :chevron-right
            :icon-disabled :chevron-right-end
            :disabled      (= @*active-page (dec (count contents)))
            :class         (concat [:right-0] (:ctrls view-config))})]]])))


(defn- slideshow-text []
  [:div.inter.w-screen.h-32.bg-white
   [render-carousell
    {:carousell-config {:autoplay      true
                        :autoplaySpeed 4000
                        :speed         1000}
     :class            []
     :view-config      {:inside?          true
                        :hide-left-right? true
                        :bg               [:bg-dangerx]
                        :fg               [:text-gray-400]}
     :contents         (into [] [[slide-aspect-16-9
                                  {:class   [:bg-green-200
                                             :text-black
                                             :dark:text-green-700
                                             :dark:bg-green-800]
                                   :content [:div.center.font-black

                                             "a1"]}]
                                 [slide-aspect-16-9
                                  {:class   [:bg-pink-300 :text-black
                                             :dark:text-pink-700
                                             :dark:bg-pink-800]
                                   :content [:div.center.font-black

                                             "b2"]}]
                                 [slide-aspect-16-9
                                  {:class   [:bg-amber-200 :text-amber-500
                                             :dark:text-amber-700
                                             :dark:bg-amber-800]
                                   :content [:div.center.font-black
                                             "c3"]}]])}]])