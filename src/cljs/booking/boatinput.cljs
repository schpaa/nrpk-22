(ns booking.boatinput
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]

            [schpaa.style.ornament :as sc]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.style.button2 :as scb2]))

(o/defstyled circle-button :div
  [:div :flex :flex-center :shrink-0
   {:aspect-ratio "1/1"
    :font-family  "Inter"
    :font-size    "var(--font-size-5)"}
   [:&:actives
    {:box-shadow "var(--inner-shadow-3)"}]]
  [:.normal
   {:box-shadow    "var(--inner-shadow-2)"
    :background    "var(--surface00)"
    :color         "var(--surface4)"
    :border-radius "var(--radius-blob-1)"}
   [:&:active
    {:background "var(--surface0)"}]]
  [:.disabled :opacity-20]
  [:.delete
   :w-full :h-full :flex :flex-center
   {:box-shadow    "var(--inner-shadow-2)"
    :background    "var(--red-6)"
    :color         "var(--surface000)"
    :border-radius "var(--radius-blob-3)"}
   [:&:active
    {:background "var(--orange-6)"}]]
  [:.other
   {:box-shadow    "var(--inner-shadow-2)"
    :background    "var(--yellow-5)"
    :color         "var(--surface000)"
    :border-radius "var(--radius-blob-4)"}
   [:&:active
    {:background "var(--yellow-6)"}]]
  [:.confirm
   {:box-shadow    "var(--inner-shadow-1)"
    :padding       "var(--size-2)"
    :background    "var(--green-5)"
    :color         "var(--surface000)"
    :border-radius "var(--radius-blob-5)"}
   [:&:active
    {:background "var(--green-6)"}]]
  ([{:keys [caption type on-click value]}]
   [:<> (cond
          (= type :delete) [:div.delete {:class    [(if (empty? value) :disabled)]
                                         :on-click on-click} caption]
          (= type :other) [:div.other caption]
          (= type :confirm) [:div.confirm {:class [(if (empty? value) :disabled)]} caption]
          :esel [:div.normal {:on-click on-click} caption])]))

(o/defstyled up-down-button :div
  ;:h-32
  :h-full
  {:box-shadow "var(--inner-shadow-1)"}
  [:.base
   {:padding       "var(--size-2)"
    :background    "var(--surface1)"
    :color         "var(--surface5)"
    :border-radius "var(--radius-2)"}]
  [:.some {:opacity 1}]
  [:.zero {:opacity 0.3}]
  [:.item:active {:background-color "var(--surface2)"}]
  [:.overlay {:overflow :hidden}]
  ([{:keys [increase decrease value content]}]
   [:<> [:div.base
         {:style {:position  :relative
                  :flex-grow 1
                  :height    :100%
                  :overflow  :hidden}}
         [:div.overlay.absolute.top-0.inset-x-0.item.z-10.overflow-hiddenx {:on-click increase :style {:height "50%"}}]
         [:div.overlay.absolute.bottom-0.inset-x-0.item.z-10.overflow-hiddenx {:on-click decrease :style {:height "50%"}}]
         [:div.flex.items-end.justify-center.h-full.z-50.pointer-events-none.base]
         [:div.absolute.inset-0.z-20.pointer-events-none.text-black
          {:class (if (pos? value) :some :zero)}
          (content value)]]]))

(o/defstyled toggle-button :div
  [:.aaa                                                    ; :w-full :h-full :flex :flex-center
   {:aspect-ratio  "1/1"
    :border-radius "var(--radius-2)"}]
  [:.icons :w-full :h-full :flex-center]
  #_[:&
     :flex :items-center :justify-center                    ;:flex-center
     ;:w-full :h-full
     {:aspect-ratio "1/1"}
     {
      :border-radius "var(--radius-round)"
      ;:padding       "var(--size-2)"
      :color         :red #_"var(--surface000)"}]
  [:div.on {:color      "var(--green-0)"
            :background "var(--green-9)"}]
  [:div.off {:background "var(--surface1)"
             :color      "var(--surface0)"}]
  [:div.items {:width  "90%"
               :height "90%"}]
  ([{:keys [on-click value on-style off-style content]}]
   ^{:on-click on-click}
   [:<> [:div.aaa (if value
                    {:style (when on-style on-style)
                     :class (when-not on-style :on)}
                    {:style (when off-style off-style)
                     :class [(when-not off-style :off)]})
         [:div.icons content]]]))

(o/defstyled panel :div
  :w-64 :h-auto
  {;:outline               "1px dotted red"
   :display               :grid
   :gap                   "var(--size-1)"
   :grid-template-columns "repeat(4,1fr)"
   :grid-template-rows    "repeat(8,1fr)"})

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(defn children [st]
  (r/with-let [n (r/cursor st [:children])
               increase #(swap! n inc)
               decrease #(swap! n (fn [n] (if (pos? n) (dec n) n)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-8.object-fit {:src "/img/human.png"}]
                                  [sc/title-p (str value)]])
                     :value    (or @n 0)
                     :increase increase
                     :decrease decrease}]))

(defn adults [st]
  (r/with-let [n (r/cursor st [:adults])
               increase #(swap! n inc)
               decrease #(swap! n (fn [n] (if (pos? n) (dec n) n)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-12.object-fit {:src "/img/human.png"}]
                                  [sc/title-p (str value)]])
                     :value    (or @n 0)
                     :increase increase
                     :decrease decrease}]))

(defn numberinput [st]
  (r/with-let [a (r/atom "123")]
    [:div.bgs-black
     {:style {:display               :grid
              :gap                   "var(--size-2)"
              :grid-template-columns "repeat(3,1fr)"}}
     (for [e '[1 2 3 4 5 6 7 8 9 nil 0 :del]]
       (if (number? e)
         [circle-button {:on-click #(swap! st update :item str e)
                         :caption  e}]
         (cond
           (= :prev e)
           [toggle-button
            {:on-click #(swap! st update :moon (fnil not false))
             :value    (:moon @st)
             :on-style {:color      "var(--yellow-3)"
                        :background :#124}
             :content  [sc/icon-large [:> solid/ArrowUpIcon]]}]
           (= :next e)
           [toggle-button
            {:on-click #(swap! st update :moon (fnil not false))
             :value    (:moon @st)
             :on-style {:color      "var(--yellow-3)"
                        :background :#124}
             :content  [sc/icon-large [:> solid/ArrowDownIcon]]}]

           (= :del e)
           [circle-button
            {:type     :delete
             :on-click #(swap! st update :item (fn [s] (subs s 0 (dec (count s)))))
             :value    (:item @st)
             :caption  [:div.xw-16.xh-16.p-0.flex [sc/icon-large [:> outline/BackspaceIcon]]]
             :on-style {:color      "var(--orange-2)"
                        :background "var(--orange-5)"}}]

           (= :other e)
           [circle-button
            {:caption (sc/icon-large (icon/adapt :rotate-left))
             :type    :other}]
           :else [:div])))]))

(defn havekey [st]
  [toggle-button
   {:on-click #(swap! st update :key (fnil not false))
    :value    (:key @st)
    :on-style {:color      "var(--green-0)"
               :background "var(--green-9)"}
    :content  [sc/icon-large [:> solid/KeyIcon]]}])

(defn moon [st]
  [toggle-button
   {:on-click #(swap! st update :moon (fnil not false))
    :value    (:moon @st)
    :on-style {:color      "var(--yellow-3)"
               :background :#124}
    :content  [sc/icon-large [:> solid/MoonIcon]]}])

(defn add [st]
  [toggle-button
   {:on-click #(swap! st update :moon (fnil not false))
    :value    true
    :on-style {:color      "var(--surface4)"
               :background "var(--surface1)"}
    :content  (sc/icon [:> solid/PlusIcon])}])

(defn delete [st]
  [toggle-button
   {:on-click #(swap! st update :moon (fnil not false))
    :value    true
    :on-style {:color      "var(--surface4)"
               :background "var(--surface1)"}
    :content  (sc/icon [:> solid/MinusIcon])}])

(defn confirm [st]
  [toggle-button
   {:on-click #(swap! st update :moon (fnil not false))
    :value    true
    :on-style {:color      "white"
               :background "var(--brand1)"}
    :content  "Ok"}])

(defn sample []
  (r/with-let [st (r/atom {})]
    [[:div
      [panel
       ;(repeat 10 [pad {} "·"])
       [:div {:style {:grid-column "1/span 3"
                      :grid-row    "5/span 4"}}
        [numberinput st]]
       [:div {:style {:grid-column "3"
                      :grid-row    "1/span 2"}}
        [children st]]
       [:div {:style {:grid-column "2"
                      :grid-row    "1/span 2"}}
        [adults st]]
       [:div {:style {:grid-column "1"
                      :grid-row    "2/span 1"}}
        [moon st]]
       [:div {:style {:grid-column "1"
                      :grid-row    "1/span 1"}}
        [havekey st]]
       [:div.bg-gray-200.white {:style {:grid-area "3/1/5/4"}} @st]
       [:div {:style {:grid-column "4"
                      :grid-row    "4/span 1"}}
        [delete st]]
       [:div {:style {:grid-column "4"
                      :grid-row    "3/span 1"}}
        [add st]]
       [:div {:style {:grid-column "4"
                      :grid-row    "8/span 1"}}
        [confirm st]]]]]))

#_(defn sample []
    (r/with-let [a (r/atom "123")
                 st (r/atom {:items ["130" "512" "800"]
                             :key   false})]
      [[:space]
       [:space]
       [:space]
       [:div
        (if
          (= 3 (count @a))
          [:div.space-y-2

           [:div.px-2
            {:style {:display               :grid
                     :gap                   "var(--size-2)"
                     :grid-template-columns "repeat(4,1fr)"}}]

           [:div.px-2
            {:style {:display               :grid
                     :gap                   "var(--size-2)"
                     :grid-template-columns "repeat(4,1fr)"
                     :grid-auto-rows        "auto"}}
            [:div.col-span-2.p-2
             {:style {:box-shadow    "var(--inner-shadow-1)"
                      ;:display       "grid"
                      ;:place-content "center"
                      :font-size     "var(--font-size-3)"
                      :background    "var(--surface1)"
                      :color         "var(--surface00)"
                      :border-radius "var(--radius-1)"}}
             [sc/col-space-2 {:class [:truncate]}
              ;[sc/header-title @a]
              [:div
               [sc/title "Rebel Ninja"]
               [sc/subtext "Grønnlandskajakk"]
               [sc/subtext "kayaks'R'us"]]]]
            [:div]

            [:div.row-span-2.col-span-2.space-y-1
             {:style {:box-shadow    "var(--inner-shadow-1)"
                      ;:display       "grid"
                      ;:place-content "center"
                      :font-size     "var(--font-size-3)"
                      :background    "var(--surface00)"
                      :color         "var(--surface1)"
                      :border-radius "var(--radius-1)"}}
             #_[scb2/normal-large [sc/icon-large [:> outline/PlusIcon]]]
             (for [e (:items @st)]
               [sc/row
                [sc/badge-big {:class [:grow]} e]
                [scb2/normal-tight {:class [:shrink-0]} [sc/icon-tiny [:> outline/XIcon]]]])]


            [toggle-button
             {:on-click #(swap! st update :key (fnil not false))
              :value    (:key @st)
              :on-style {:color      "var(--green-0)"
                         :background "var(--green-9)"}
              :content  [sc/icon-large [:> solid/KeyIcon]]}]

            [toggle-button
             {:on-click #(swap! st update :moon (fnil not false))
              :value    (:moon @st)
              :on-style {:color      "var(--yellow-3)"
                         :background :#124}
              :content  [sc/icon-large [:> solid/MoonIcon]]}]

            (r/with-let [n (r/cursor st [:adults])
                         increase #(swap! n inc)
                         decrease #(swap! n (fn [n] (if (pos? n) (dec n) n)))]
              [up-down-button
               {:content  (fn [value]
                            [:div.flex.flex-col.justify-end.items-center.h-32
                             [:img.h-16.object-fit {:src "/img/human.png"}]
                             [sc/title-p (str value)]])
                :value    (or @n 0)
                :increase increase
                :decrease decrease}])

            (r/with-let [n (r/cursor st [:children])
                         increase #(swap! n inc)
                         decrease #(swap! n (fn [n] (if (pos? n) (dec n) n)))]
              [up-down-button {:content  (fn [value]
                                           [:div.flex.flex-col.justify-end.items-center.h-full
                                            [:img.h-8.object-fit {:src "/img/human.png"}]
                                            [sc/title-p (str value)]])
                               :value    (or @n 0)
                               :increase increase
                               :decrease decrease}])]])]
       [:div
        [:div.bgs-black.p-2.w-64
         {:style {:display               :grid
                  :gap                   "var(--size-2)"
                  :grid-template-columns "repeat(4,1fr)"}}
         (for [e '[:prev 7 8 9 nil 4 5 6 nil 1 2 3 :next 0 nil :del]]
           (if (number? e)
             [circle-button {:on-click #(swap! a str e)
                             :caption  e}]
             (cond
               (= :prev e)
               [toggle-button
                {:on-click #(swap! st update :moon (fnil not false))
                 :value    (:moon @st)
                 :on-style {:color      "var(--yellow-3)"
                            :background :#124}
                 :content  [sc/icon-large [:> solid/ArrowUpIcon]]}]
               (= :next e)
               [toggle-button
                {:on-click #(swap! st update :moon (fnil not false))
                 :value    (:moon @st)
                 :on-style {:color      "var(--yellow-3)"
                            :background :#124}
                 :content  [sc/icon-large [:> solid/ArrowDownIcon]]}]

               (= :del e)
               [circle-button
                {:type     :delete
                 :on-click #(swap! a (fn [s] (subs s 0 (dec (count s)))))
                 :value    @a
                 :caption  [:div.w-16.h-16.p-0.flex [sc/icon-large [:> outline/BackspaceIcon]]]
                 :on-style {:color      "var(--orange-2)"
                            :background "var(--orange-5)"}}]

               (= :other e)
               [circle-button
                {:caption (sc/icon-large (icon/adapt :rotate-left))
                 :type    :other}]
               :else [:div])))]]]))