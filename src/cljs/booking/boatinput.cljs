(ns booking.boatinput
  (:require [lambdaisland.ornament :as o]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.ornament :as sc]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.style.button2 :as scb2]
            [clojure.set :as set]
            [re-frame.core :as rf]
            [tick.core :as t]
            [booking.aktivitetsliste]
            [goog.events.KeyCodes :as keycodes]
            [schpaa.style.button :as scb]))

(o/defstyled input-caption :div

  {:font-family "Oswald"                                    ;"IBM Plex Sans"
   :font-size   "var(--font-size-4)"
   :font-weight "var(--font-weight-6)"})

(o/defstyled button-caption :div
  {:font-family "Inter"                                     ;"IBM Plex Sans"
   :font-size   "var(--font-size-4)"
   :font-weight "var(--font-weight-6)"})

(o/defstyled button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   :background    "var(--surface4)"
   :color         "var(--surface1)"}
  [:&:hover {:background "var(--surface5)"}]
  [:&:disabled {:color      "var(--surface2)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--surface3)"
                      :color      "var(--surface2)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled delete-button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   ;:background    "var(--red-6)"
   ;:color         "var(--surface1)"
   ;:background    "var(--surface1)"
   :color         "var(--red-6)"}
  [:&:hover {;:background    "var(--red-5)"
             :color "var(--red-8)"}]
  [:&:disabled {:color      "var(--surface2)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--red-7)"
                      :color      "var(--red-1)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled add-button :button
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-1)"
   ;:background    "var(--red-6)"
   ;:color         "var(--surface1)"
   :background    "var(--green-6)"
   :color         "var(--green-1)"}
  [:&:hover {;:background    "var(--red-5)"
             :color "var(--green-7)"}]
  [:&:disabled {:color      "var(--surface2)"
                :background "none" #_"var(--surface0)"}]
  [:&:active:enabled {:background "var(--green-7)"
                      :color      "var(--green-1)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled numberpad-button :button
  :w-full :h-full :duration-100
  {:display       :grid
   :place-content :center
   :border-radius "var(--radius-3)"
   :background    "var(--surface4)"
   :color         "var(--surface1)"}
  [:&:hover {;:background "var(--surface5)"
             :color "var(--surface000)"}]
  [:&:disabled {:color      "var(--surface3)"
                :background "var(--surface2)"}]
  [:&:active:enabled {:background "var(--surface3)"
                      :color      "var(--surface000)"}]
  ([{:keys [ref on-click disabled enabled style]} ch]
   ^{:ref      ref
     :on-click on-click
     :disabled (if (some? enabled) (not enabled) disabled)}
   [:<> [:div ch]]))

(o/defstyled up-down-button :div
  :h-full button-caption
  {:box-shadow "var(--inner-shadow-1)"}
  [:.base
   {:padding       "var(--size-2)"
    :background    "var(--surface00)"
    :color         "var(--surface5)"
    :border-radius "var(--radius-1)"}]
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
         [:div.overlay.absolute.top-0.inset-x-0.item.z-10.overflow-hiddenx {:on-click decrease :style {:height "50%"}}]
         [:div.overlay.absolute.bottom-0.inset-x-0.item.z-10.overflow-hiddenx {:on-click increase :style {:height "50%"}}]
         [:div.flex.items-end.justify-center.h-full.z-50.pointer-events-none.base]
         [:div.absolute.inset-0.z-20.pointer-events-none.mb-1
          {:class (if (pos? value) :some :zero)}
          (content value)]]]))

(o/defstyled toggle-button :div
  :w-full :h-full
  {:display       :grid
   :place-content :center
   :aspect-ratio  "1/1"
   :border-radius "var(--radius-1)"}
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
  {:display               :grid
   :column-gap            "var(--size-2)"
   :row-gap               "var(--size-1)"
   :grid-template-columns "repeat(4,1fr)"
   :grid-auto-rows        "4rem"})

(o/defstyled pad :div
  [:div :flex-center
   {:background   "var(--surface0)"
    :aspect-ratio "1/1"}]
  ([{:keys []} ch]
   [:<> [:div {:style {:border-radius (str "var(--radius-blob-" (inc (rand-int 5)) ")")}} ch]]))

(defn- decrease-children [st]
  (let [n (r/cursor st [:children])]
    (swap! n (fn [n] (if (pos? n) (dec n) 1)))))

(defn- increase-children [st]
  (let [n (r/cursor st [:children])]
    (swap! n inc)))

(defn children [st]
  (r/with-let [n (r/cursor st [:children])
               #_#_increase #(swap! n inc)
               #_#_decrease #(swap! n (fn [n] (if (pos? n) (dec n) 1)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-12.object-fit.-ml-px {:src "/img/human.png"}]
                                  (str value)])
                     :value    (or @n 0)
                     :increase #(increase-children st)
                     :decrease #(decrease-children st)}]))

(defn- decrease-juveniles [st]
  (let [n (r/cursor st [:juveniles])]
    (swap! n (fn [n] (if (pos? n) (dec n) 1)))))

(defn- increase-juveniles [st]
  (let [n (r/cursor st [:juveniles])]
    (swap! n inc)))

(defn juveniles [st]
  (r/with-let [n (r/cursor st [:juveniles])
               #_#_increase #(swap! n inc)
               #_#_decrease #(swap! n (fn [n] (if (pos? n) (dec n) 1)))]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-16.object-fit.-ml-px {:src "/img/teen.png"}]
                                  (str value)])
                     :value    (or @n 0)
                     :increase #(increase-juveniles st)
                     :decrease #(decrease-juveniles st)}]))

(defn- decrease-adults [st]
  (let [n (r/cursor st [:adults])]
    (swap! n (fn [n] (if (pos? n) (dec n) 1)))))

(defn- increase-adults [st]
  (let [n (r/cursor st [:adults])]
    (swap! n inc)))

(defn adults [st]
  (r/with-let [n (r/cursor st [:adults])]
    [up-down-button {:content  (fn [value]
                                 [:div.flex.flex-col.justify-end.items-center.h-full
                                  [:img.h-20.object-fit {:src "/img/human.png"}]
                                  (str value)])
                     :value    (or @n 0)
                     :increase #(increase-adults st)
                     :decrease #(decrease-adults st)}]))

(defn- key-clicked [st e]
  (when (<= (count (:item @st)) 3)
    (swap! st #(-> %
                   (update :item str e)
                   ((fn [z]
                      (tap> [z (some #{(:item z)} (:list z))])
                      (if (some #{(:item z)} (:list z))
                        (assoc z :selected (:item z))
                        z)))))))

(defn- backspace-clicked [st]
  (swap! st #(-> %
                 (update :item (fn [s] (subs s 0 (dec (count s)))))
                 (dissoc :selected))))

(defn numberinput [st]
  [:div.h-full
   [:div.p-3.h-full
    {:style {:display               :grid
             :border-radius         "var(--radius-1)"
             :box-shadow            "var(--inner-shadow-2)"
             :background            "var(--surface2)"
             :gap                   "var(--size-1)"
             :grid-template-columns "repeat(3,1fr)"
             :grid-auto-rows        "1fr"}}
    (doall (for [e '[7 8 9 4 5 6 1 2 3 :toggle 0 :del]]
             (if (number? e)
               [numberpad-button {:on-click
                                  (fn [] (if (:phone @st)
                                           (swap! st #(-> %
                                                          (update :phonenumber str e)))
                                           (key-clicked st e)))
                                  :enabled (if (:phone @st)
                                             (< (count (:phonenumber @st)) 8)
                                             (< (count (:item @st)) 4))}
                [button-caption e]]
               (cond
                 (= :del e)
                 [numberpad-button
                  {:on-click (fn [] (if (:phone @st)
                                      (swap! st #(-> %
                                                     (update :phonenumber (fn [s] (subs s 0 (dec (count s)))))))
                                      (backspace-clicked st)))
                   :style    {:color (if (not (empty? (:item @st))) "var(--surface0)" "var(--surface3)")}
                   :enabled  (if (:phone @st)
                               (not (empty? (:phonenumber @st)))
                               (not (empty? (:item @st))))}
                  [sc/icon-huge [:> solid/BackspaceIcon]]]
                 (= :toggle e)
                 [numberpad-button
                  {:on-click #(swap! st update :phone (fnil not false))
                   ;:value     (:phone @st)
                   #_#_:off-style {:color      "var(--surface2)"
                                   :background "none"}
                   :xstyle   (if (:phone @st) {:color       "var(--yellow-1)"
                                               :xbackground :#124})}
                  (if (:phone @st)
                    [sc/icon-large [:> solid/TagIcon]]
                    [sc/icon-large [:> solid/PhoneIcon]])]

                 :else [:div]))))]])

(defn- key-command [st]
  (swap! st update :key (fnil not false)))

(defn havekey [st]
  [toggle-button
   {:on-click  #(key-command st)
    :value     (:key @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:key @st) {:box-shadow "var(--shadow-3)"
                               :color      "var(--surface4)"
                               :background "var(--yellow-5)"})
    :content   [sc/icon-large [:> (if (:key @st) solid/KeyIcon outline/KeyIcon)]]}])

(defn- moon-command [st]
  (swap! st update :moon (fnil not false)))

(defn moon [st]
  [toggle-button
   {:on-click  #(moon-command st)
    :value     (:moon @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:moon @st) {:color      "var(--surface4)"
                                :background "var(--yellow-5)"})
    :content   [sc/icon-large [:> (if (:moon @st) solid/MoonIcon outline/MoonIcon)]]}])

(defn- add-command [st]
  (if (<= 3 (count (:item @st)))
    (swap! st #(-> %
                   ;(assoc :selected (:item %))
                   (update :list (fnil conj #{}) (:item %))
                   ((fn [e] (if true (dissoc e :item) identity)))))))

(defn add [st]
  [add-button
   {:on-click #(add-command st)
    :value    true
    :enabled  (and (not= (:item @st) (:selected @st))
                   (not (some #{(:item @st)} (:list @st)))
                   ;(not (some #{(:item @st)} (:list @st)))
                   ;(not (some? (:selected @st)))
                   (<= 3 (count (:item @st))))
    :on-style {:color      "var(--surface4)"
               :background "var(--surface1)"}}
   (sc/icon-huge [:> solid/PlusCircleIcon])])

(defn- delete-clicked [st]
  (swap! st #(-> %
                 (dissoc :item :selected)
                 (update :list set/difference #{(:selected %)}))))

(defn delete [st]
  [delete-button
   {:on-click #(delete-clicked st)
    :value    true
    :on-style {:background "var(--surface1)"}
    :enabled  (and (or (not (empty? (:selected @st)))
                       (not (empty? (:item @st)))))}
   (sc/icon-huge [:> outline/XCircleIcon])])

(defn- restart-command [st]
  (reset! st nil))

(defn restart [st]
  (r/with-let [form-dirty? #(or (pos? (+ (:adults @st)
                                         (:juveniles @st)
                                         (:children @st)))
                                (pos? (count (:list @st)))
                                (:moon @st)
                                (:key @st)
                                (not (empty? (:item @st))))
               a (r/atom nil)
               timer (r/atom nil)
               ontimeout (fn [e]
                           (tap> "reset!")
                           (when @timer
                             (reset! timer nil)
                             (restart-command st)))
               mousedown (fn [e]
                           (tap> "md")
                           (when (form-dirty?)
                             (reset! timer (js/setTimeout ontimeout 1100))))
               mouseup (fn [e]
                         (tap> "up")
                         (when @timer
                           (js/clearTimeout @timer))
                         (reset! timer nil))]
    (let [dirty? (form-dirty?)]
      [button
       {:ref     (fn [e]
                   (when-not @a
                     (.addEventListener e "touchstart" mousedown)
                     (.addEventListener e "mousedown" mousedown)
                     (.addEventListener e "mouseup" mouseup)
                     (.addEventListener e "touchend" mouseup)
                     (reset! a e)))
        :enabled dirty?
        :style   (if dirty?
                   (if @timer
                     {:transition-duration "0.5s"
                      :color               "var(--orange-9)"
                      :background          "var(--orange-1)"}
                     {:transition-duration "2s"
                      :color               "var(--orange-6)"
                      :background          "var(--surface000)" #_"var(--brand1-6)"})
                   {:transition-duration "0s"
                    :color               "var(--surface2)"
                    :background          "none"})}
       [:div {:style {:transition-duration "0.5s"
                      :transform           (if @timer "rotate(-360deg)")}}
        (sc/icon-large (icon/adapt :refresh 3))]])
    (finally (.removeEventListener @a "mousedown" mousedown)
             (.removeEventListener @a "touchstart" mousedown)
             (.removeEventListener @a "mouseup" mouseup)
             (.removeEventListener @a "touchend" mouseup))))

(defn- confirm-command [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]
    ;(js/alert "Dialogen blir værende men du blir flyttet (hvis du ikke allerede er der) til den siden som viser en liste over alle dine aktiviteter. Denne siste registreringen vil ligge øverst i listen.")
    (when ok?
      (rf/dispatch [:app/navigate-to [:r.aktivitetsliste]])
      (booking.aktivitetsliste/add-command (assoc @st :start (t/instant (t/now))))
      (restart-command st))))

(defn confirm [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [add-button
     {:on-click #(confirm-command st)
      :enabled  ok?
      :styles   (when ok? {:color      "var(--green-1)"
                           :background "var(--green-6)"})}
     (sc/icon-huge [:> solid/CheckCircleIcon])]))

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

(defonce st (r/atom {}))

(defn sample [mobile?]
  (let [keydown-f (fn [event]
                    (tap> event)
                    ;(.stopPropagation event)
                    (let [kc (.-keyCode event)]
                      (tap> kc)
                      (cond
                        (= kc keycodes/S) (confirm-command st)
                        (= kc keycodes/R) (restart-command st)
                        (= kc keycodes/N) (key-command st)
                        (= kc keycodes/O) (moon-command st)
                        (= kc keycodes/F) (decrease-adults st)
                        (= kc keycodes/V) (increase-adults st)
                        (= kc keycodes/G) (decrease-children st)
                        (= kc keycodes/B) (increase-children st)
                        (= kc keycodes/ENTER) (add-command st)
                        (= kc keycodes/DELETE) (delete-clicked st)
                        (= kc keycodes/BACKSPACE) (backspace-clicked st)
                        (some #{kc} (range 48 58)) (key-clicked st (- kc 48)))))
        ref (r/atom nil)
        toggle-numberinput #(rf/dispatch [:lab/close-number-input])]
    (r/create-class
      {:reagent-render
       (fn [_]
         [:div.relative
          {}
          #_[:div.absolute.top-0.right-0
             [scb/corner {:on-click toggle-numberinput} [sc/icon-large [:> solid/XIcon]]]]

          [:div                                             ;.focus:outline-nonex.focus:ring-black.focus:ring-offset-2.focus:ring-offset-white.p-4
           {:class     [:focus:outline-none
                        :focus:ring-4
                        ;:focus:ring-alt
                        :focus:ring-offset-2
                        :focus:ring-offset-clear]
            :style     (if mobile? {} {:padding                    "var(--size-4)"
                                       :border-top-left-radius     "var(--radius-2)"
                                       :border-bottom-left-radius  "var(--radius-2)"
                                       :border-top-right-radius    "none"
                                       :border-bottom-right-radius "none"
                                       :pointer-events             :auto})
            :tab-index 0
            :ref       (fn [e]
                         (when-not @ref
                           (tap> ["adding for " e])
                           (.addEventListener e "keydown" keydown-f)
                           (.focus e)
                           (reset! ref e)))}

           [panel
            [:div
             {:style {:grid-column "2/span 3"
                      :grid-row    "5/span 4"}}
             [numberinput st]]
            [:div.p-1x {:style {:grid-column "4"
                                :grid-row    "1/span 2"}}
             [children st]]

            [:div.p-1x {:style {:grid-column "3"
                                :grid-row    "1/span 2"}}
             [juveniles st]]

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
                      :padding-inline "var(--size-2)"
                      :border-radius  "var(--radius-1)"
                      :background     "var(--surface000)"
                      :box-shadow     "var(--inner-shadow-0)"}}
             [:div.flex.items-center.h-full
              {:style {:font-family "Oswald"
                       :font-weight 600
                       :font-size   "var(--font-size-4)"}}
              (if (:phone @st)
                [:div
                 {:style {:display     "flex"
                          :align-items "center"
                          :font-size   "var(--font-size-5)"
                          :color       "var(--surface5)"}}
                 (when (empty? (:phonenumber @st))
                   [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])
                 [input-caption (if (empty? (:phonenumber @st)) [:span.opacity-30 "telefonnr"] (:phonenumber @st))]
                 (when-not (empty? (:phonenumber @st))
                   [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])]
                [:div
                 {:style {:display     "flex"
                          :align-items "center"
                          :font-size   "var(--font-size-5)"
                          :color       "var(--surface5)"}}
                 (when (empty? (:item @st))
                   [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])
                 [input-caption (if (empty? (:item @st)) [:span.opacity-30 "båtnr"] (:item @st))]
                 (when-not (empty? (:item @st))
                   [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])])]]

            [:div
             {:style {:grid-row       "3"
                      :grid-column    "1/span 4"
                      :padding-inline "var(--size-2)"
                      :display        :flex
                      :align-items    :center
                      ;:border-radius "var(--radius-1)"
                      ;:background    "var(--brand1)"
                      #_#_:box-shadow "var(--inner-shadow-2)"}}
             (if (<= 3 (count (:item @st)))
               (lookup (:item @st))
               [sc/col {:class [:space-y-px :opacity-50]}
                [sc/title [sc/row-sba "Skriv båtnummeret og"
                           [sc/icon [:> solid/PlusCircleIcon]]]]
                [sc/subtext "Bruk 4 siffer for testing"]])]

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
                                 :grid-row              "9/span 2"
                                 :border-radius         "var(--radius-1)"
                                 :background            "var(--surface00)"
                                 :gap                   "var(--size-1)"
                                 :display               :grid
                                 :grid-template-columns "repeat(4,1fr)"
                                 :grid-template-rows    "repeat(3,1fr)"}}
               (concat
                 (for [e (take 11 (drop 3 (sort (:list @st))))]
                   [sc/badge
                    {:selected (and (= e (:selected @st)))
                     :on-click (fn [] (if (= e (:selected @st))
                                        (swap! st dissoc :selected
                                               dissoc :item)
                                        (swap! st #(-> %
                                                       (assoc :selected e)
                                                       (assoc :item e)))))} e])
                 (if (< 16 (count (:list @st))) [[sc/badge {} "..."]]))])]]])})))
