(ns booking.boatinput
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]

            [schpaa.style.ornament :as sc]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.style.button2 :as scb2]
            [clojure.set :as set]))

(o/defstyled button-caption :div
  {:font-family "Inter"                                     ;"IBM Plex Sans"
   :font-size   "var(--font-size-4)"
   :font-weight "var(--font-weight-6)"})

(o/defstyled button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   ;:background    "var(--surface0)"
   :color         "var(--surface4)"}
  [:&:disabled {
                :color       "var(--surface0)"
                :xbackground "var(--surface00)"}]
  [:&:active {:background "var(--surface00)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled circle-button :div
  [:div :flex :flex-center :shrink-0
   {:aspect-ratio "1/1"
    :font-family  "Inter"
    :font-size    "var(--font-size-5)"}
   [:&:actives
    {:box-shadow "var(--inner-shadow-3)"}]]
  [:.normal
   {:box-shadow    "var(--shadow-3)"
    :background    "var(--surface000)"
    :color         "var(--surface4)"
    :border-radius "var(--radius-round)"}
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
  :h-full button-caption
  {:box-shadow "var(--inner-shadow-1)"}
  [:.base
   {:padding       "var(--size-2)"
    :background    "var(--surface00)"
    :color         "var(--surface5)"
    :border-radius "var(--radius-2)"}]
  [:.some {:opacity 1}]
  [:.zero {:opacity 0.3}]
  [:.item:active {:background-color "var(--surface1)"}]
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
         [:div.absolute.inset-0.z-20.pointer-events-none.mb-1
          {:class (if (pos? value) :some :zero)}
          (content value)]]]))

(o/defstyled toggle-button :div
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :aspect-ratio  "1/1"
   :border-radius "var(--radius-1)"
   :xbackground   "var(--surface0)"}
  #_[:.aaa
     {:aspect-ratio  "1/1"
      :border-radius "var(--radius-1)"}]
  #_[:.icons
     #_{:display       :grid
        :place-content :center}]
  #_[:div.on {:color      "var(--green-0)"
              :background "var(--green-9)"}
     [:div.off {:background "var(--surface1)"
                :color      "var(--surface0)"}]]
  ([{:keys [on-click value on-style off-style content]}]
   ^{:on-click on-click}
   [:<> [:div (if value
                {:style (when on-style on-style)
                 :class (when-not on-style :on)}
                {:style (when off-style off-style)
                 :class [(when-not off-style :off)]})
         content]]))

(o/defstyled panel :div
  :w-64 :h-auto
  {;:outline               "1px dotted red"
   :display               :grid
   :gap                   "var(--size-1)"
   :grid-template-columns "repeat(4,1fr)"
   :grid-auto-rows        "1fr"})

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(defn children [st]
  (r/with-let [n (r/cursor st [:children])
               increase #(swap! n inc)
               decrease #(swap! n (fn [n] (if (pos? n) (dec n) 1)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-12.object-fit.-ml-px {:src "/img/human.png"}]
                                  (str value)])
                     :value    (or @n 0)
                     :increase increase
                     :decrease decrease}]))

(defn adults [st]
  (r/with-let [n (r/cursor st [:adults])
               increase #(swap! n inc)
               decrease #(swap! n (fn [n] (if (pos? n) (dec n) 1)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-20.object-fit {:src "/img/human.png"}]
                                  (str value)])
                     :value    (or @n 0)
                     :increase increase
                     :decrease decrease}]))

(defn numberinput [st]
  (r/with-let [a (r/atom "123")]
    [:div.bgs-black.p-2
     {:style {:display               :grid
              :background-color      "var(--brand1o)"
              :border-radius         "var(--radius-1)"
              :gap                   "var(--size-1)"
              :grid-template-columns "repeat(3,1fr)"
              :grid-auto-rows        "4rem"}}
     (for [e '[7 8 9 4 5 6 1 2 3 nil 0 :del]]
       (if (number? e)
         [button {:on-click
                  (fn [] (if (:phone @st)
                           (swap! st #(-> %
                                          (update :phonenumber str e)))
                           (swap! st #(-> %
                                          (update :item str e)
                                          ((fn [z]
                                             (tap> [z (some #{(:item z)} (:list z))])
                                             (if (some #{(:item z)} (:list z))
                                               (assoc z :selected (:item z))
                                               z)))))))
                  #_#_#_#_:on-style {:background :red}
                          :style {:border-radius "var(--radius-2)"
                                  :xbox-shadow   "var(--shadow-2)"}

                  :enabled (if (:phone @st)
                             (< (count (:phonenumber @st)) 8)
                             (< (count (:item @st)) 4))}
          [button-caption e]]
         (cond
           (= :del e)
           [button
            {:on-click (fn [] (if (:phone @st)
                                (swap! st #(-> %
                                               (update :phonenumber (fn [s] (subs s 0 (dec (count s)))))))
                                (swap! st #(-> %
                                               (update :item (fn [s] (subs s 0 (dec (count s)))))
                                               (dissoc :selected)))))
             :style    {:color       (if (not (empty? (:item @st))) "var(--surface4)" "var(--surface0)")
                        ;:border-radius "var(--radius-round)"
                        :xbackground "none" #_"var(--surface00)"}
             :enabled  (if (:phone @st)
                         (not (empty? (:phonenumber @st)))
                         (not (empty? (:item @st))))}
            [sc/icon-large [:> solid/BackspaceIcon #_ArrowNarrowLeftIcon]]]
           (= :phone e)
           [toggle-button
            {:on-click  #(swap! st update :phone (fnil not false))
             :value     (:phone @st)
             :off-style {:color      "var(--surface2)"
                         :background "none"}
             :style     (if (:phone @st) {:color      "var(--yellow-1)"
                                          :background :#124})
             :content   [sc/icon-large [:> solid/DeviceMobileIcon]]}]

           (= :other e)
           [circle-button
            {:caption (sc/icon-large (icon/adapt :rotate-left))
             :type    :other}]
           :else [:div])))]))

(defn havekey [st]
  [toggle-button
   {:on-click  #(swap! st update :key (fnil not false))
    :value     (:key @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:key @st) {:box-shadow "var(--shadow-3)"
                               :color      "var(--yellow-2)"
                               :background "var(--surface4)"})
    :content   [sc/icon-large [:> solid/KeyIcon]]}])

(defn moon [st]
  [toggle-button
   {:on-click  #(swap! st update :moon (fnil not false))
    :value     (:moon @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:moon @st) {:color      "var(--yellow-1)"
                                :background :#124})
    :content   [sc/icon-large [:> solid/MoonIcon]]}])

(defn add [st]
  [button
   {:on-click (fn [] (swap! st #(-> %
                                    ;(assoc :selected (:item %))
                                    (update :list (fnil conj #{}) (:item %))
                                    ((fn [e] (if true (dissoc e :item) identity))))))
    :value    true
    :enabled  (and (not= (:item @st) (:selected @st))
                   (not (some #{(:item @st)} (:list @st)))
                   ;(not (some #{(:item @st)} (:list @st)))
                   ;(not (some? (:selected @st)))
                   (<= 3 (count (:item @st))))
    :on-style {:color      "var(--surface4)"
               :background "var(--surface1)"}}
   (sc/icon-large [:> solid/PlusIcon])])

(defn delete [st]
  [button
   {:on-click (fn [] (swap! st #(-> %
                                    (dissoc :item :selected)
                                    (update :list set/difference #{(:selected %)}))))
    :value    true
    ;:style {:color       "var(--red-7)"}
    :on-style {:scolor     "var(--surface4)"

               :background "var(--surface1)"}
    :enabled  (and (or (not (empty? (:selected @st)))
                       (not (empty? (:item @st))))
                   #_(some? (:selected @st)))}
   (sc/icon-large [:> solid/XIcon])])

(defn confirm [st]
  (let [ok? (and (pos? (+ (:adults @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [button
     {:on-click #(js/alert "Dialogen blir værende men du blir flyttet (hvis du ikke allerede er der) til den siden som viser en liste over alle dine aktiviteter. Denne siste registreringen vil ligge øverst i listen.")
      :enabled  ok?
      :style    (when ok? {:color      "white"
                           :background "var(--green-5)"})}
     (sc/icon-large [:> solid/CheckIcon])]))

(defn restart [st]
  (let [ok? (or (pos? (+ (:adults @st) (:children @st)))
                (pos? (count (:list @st)))
                (:moon @st)
                (:key @st)
                (not (empty? (:item @st))))]
    (r/with-let [clicks (r/atom 0)
                 a (r/atom nil)
                 timer (r/atom nil)
                 ontimeout (fn [e]
                             (when @timer (reset! st nil)))
                 mousedown (fn [e]
                             (when ok? (reset! timer (js/setTimeout ontimeout 1100))))

                 mouseup (fn [e]
                           (tap> "up")
                           (js/clearTimeout @timer)
                           (reset! timer false))
                 holding (r/atom false)]
      (let []
        [button
         {:ref     (fn [e]
                     (when-not @a
                       (.addEventListener e "touchstart" mousedown)
                       (.addEventListener e "mousedown" mousedown)
                       (.addEventListener e "mouseup" mouseup)
                       (.addEventListener e "touchend" mouseup)
                       (reset! a e)))
          :enabled ok?
          :style   (if ok?
                     (if @timer
                       {:transition-duration "1s"
                        :color               "white"
                        :background          "var(--red-2)"}
                       {:background "none"
                        :color      "var(--red-5)"})
                     {:color "var(--surface0)"})}

         [:div {:style {:transition-duration "1s"
                        ;:transition-property "transform"
                        :transform           (if @timer "rotate(-360deg)")}}

          (sc/icon-large (icon/adapt :refresh 2.5))]])
      (finally (.removeEventListener @a "mousedown" mousedown)
               (.removeEventListener @a "touchstart" mousedown)
               (.removeEventListener @a "mouseup" mouseup)
               (.removeEventListener @a "touchend" mouseup)))))

(defn lookup [id]
  (if (< 3 (count id))
    [sc/col {:class [:space-y-1]}
     [sc/title "Test-navn"]
     [sc/subtext "Test-kategori"]]
    (case id
      "400" [sc/col {:class [:space-y-1]}
             [sc/header-title "Rebel Roy"]
             [sc/subtext "Grønnlandskajakk"]]
      "401" [sc/col {:class [:space-y-1]}
             [sc/header-title "Rebel Joy"]
             [sc/subtext "Canadakajakk"]]
      [sc/col {:class [:space-y-1]}
       [sc/header-title "Rebel Soy"]
       [sc/subtext "Canadasnoozegoose"]])))

(defonce st (r/atom {} #_{:adults   1
                          :list     (into #{} (map str (range 100 120)))
                          :selected "300"
                          :item     "UGH"}))

(defn sample []
  [[:div
    [panel
     [:div {:style {:grid-column "2/span 3"
                    :grid-row    "5/span 4"}}
      [numberinput st]]
     [:div {:style {:grid-column "3"
                    :grid-row    "1/span 2"}}
      [children st]]
     [:div {:style {:grid-column "2"
                    :grid-row    "1/span 2"}}
      [adults st]]
     [:div {:style {:grid-row    "1"
                    :grid-column "1"}}
      [moon st]]
     [:div {:style {:grid-row    "2"
                    :grid-column "1"}}
      [havekey st]]

     [:div
      {:style {:grid-row       "4"
               :grid-column    "2/span 2"
               :padding-inline "var(--size-3)"
               :border-radius  "var(--radius-1)"
               :background     "var(--surface00)"
               :-box-shadow    "var(--inner-shadow-1)"}}
      [:div.flex.items-center.h-full
       {:style {:font-family "Inter"
                :font-weight 600
                :font-size   "var(--font-size-4)"}}
       (if (:phone @st)
         [:div.flex
          {:style {:color "var(--surface4)"}}
          (when (empty? (:phonenumber @st))
            [:div.animate-blink.opacity-100 "|"])
          [button-caption (if (empty? (:phonenumber @st)) [:span.opacity-30 "telefonnr"] (:phonenumber @st))]
          (when-not (empty? (:phonenumber @st))
            [:div.animate-blink.opacity-100 "|"])]
         [:div.flex
          {:style {:color "var(--surface4)"}}
          (when (empty? (:item @st))
            [:div.animate-blink.opacity-100 "|"])
          [button-caption (if (empty? (:item @st)) [:span.opacity-30 "båtnr"] (:item @st))]
          (when-not (empty? (:item @st))
            [:div.animate-blink.opacity-100 "|"])])]]

     [:div
      {:style {:grid-row       "3"
               :grid-column    "1/span 4"
               :padding-inline "var(--size-2)"
               :display        :flex
               :align-items    :center
               :-border-radius "var(--radius-1)"
               :-background    "var(--surface00)"
               :-box-shadow    "var(--inner-shadow-0)"}}
      (when (<= 3 (count (:item @st)))
        (lookup (:item @st)))]

     [:div.p-1
      {:style {:grid-column           "1"
               :grid-row              "5/span 2"
               :border-radius         "var(--radius-1)"
               :background            "var(--surface00)"
               :gap                   "var(--size-1)"
               :display               :grid
               :grid-template-columns "1fr"
               :grid-template-rows    "repeat(3,1fr)"}}
      (for [e (take 3 (sort (:list @st)))]
        [sc/badge
         {:selected (and (= e (:selected @st)))
          :on-click (fn [] (if (= e (:selected @st))
                             (swap! st dissoc :selected
                                    dissoc :item)
                             (swap! st #(-> %
                                            (assoc :selected e)
                                            (assoc :item e)))))} e])]
     [:div {:style {:grid-row    "4"
                    :grid-column "1"}}
      [delete st]]
     [:div {:style {:grid-row    "4"
                    :grid-column "4"}}
      [add st]]
     [:div {:style {:grid-row    "8"
                    :grid-column "1"}}
      [confirm st]]
     [:div {:style {:grid-row    "7"
                    :grid-column "1"}}
      [restart st]]

     (when (< 3 (count (:list @st)))
       [:div.p-1 {:style {:grid-column           "1/span 4"
                          :grid-row              "9/span 4"
                          :border-radius         "var(--radius-1)"
                          :background            "var(--surface00)"
                          :gap                   "var(--size-1)"
                          :display               :grid
                          :grid-template-columns "repeat(4,1fr)"
                          :grid-auto-rows        "2rem" #_"repeat(3,1fr)"}}
        (for [e (drop 3 (sort (:list @st)))]
          [sc/badge
           {:selected (and (= e (:selected @st)))
            :on-click (fn [] (if (= e (:selected @st))
                               (swap! st dissoc :selected
                                      dissoc :item)
                               (swap! st #(-> %
                                              (assoc :selected e)
                                              (assoc :item e)))))} e])])]]])
