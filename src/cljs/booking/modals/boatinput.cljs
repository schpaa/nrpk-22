(ns booking.modals.boatinput
  (:require [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.icon :as icon]
            [clojure.set :as set]
            [re-frame.core :as rf]
            [tick.core :as t]
            [booking.aktivitetsliste]
            [goog.events.KeyCodes :as keycodes]
            [booking.ico :as ico]
            [booking.common-widgets :refer [vertical-button]]
            [booking.modals.boatinput.styles :as bis]
            [headlessui-reagent.core :as ui]
            [schpaa.debug :as l]))

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
    [bis/up-down-button {:content  (fn [value]
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
    [bis/up-down-button {:content  (fn [value]
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
    [bis/up-down-button {:content  (fn [value]
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
                      ;(tap> [z (some #{(:item z)} (:list z))])
                      (if (some #{(:item z)} (map :number (:list z)))
                        (assoc z :selected (:item z))
                        z)))))))

(defn- backspace-clicked [st]
  (swap! st #(-> %
                 (update :item (fn [s] (subs s 0 (dec (count s)))))
                 (dissoc :selected))))

(defn numberinput [st]
  [:div.h-full
   [:div.xp-3.h-full
    {:style {:display               :grid
             :border-radius         "var(--radius-0)"
             ;:box-shadow            "var(--inner-shadow-2)"
             ;:background            "var(--floating)"
             :gap                   "var(--size-1)"
             :grid-template-columns "repeat(3,1fr)"
             :grid-auto-rows        "1fr"}}
    (doall (for [e '[7 8 9 4 5 6 1 2 3 :toggle 0 :del]]
             (if (number? e)
               [bis/numberpad-button {:on-click
                                      (fn [] (if (:phone @st)
                                               (swap! st #(-> %
                                                              (update :phonenumber str e)))
                                               (key-clicked st e)))
                                      :enabled (if (:phone @st)
                                                 (< (count (:phonenumber @st)) 8)
                                                 (< (count (:item @st)) 4))}
                [bis/button-caption e]]
               (cond
                 (= :del e)
                 [bis/numberpad-button
                  {:on-click (fn [] (if (:phone @st)
                                      (swap! st #(-> %
                                                     (update :phonenumber (fn [s] (subs s 0 (dec (count s)))))))
                                      (backspace-clicked st)))
                   :style    {:color (if (not (empty? (:item @st))) "var(--red-8)" "var(--text2)")}
                   :enabled  (if (:phone @st)
                               (not (empty? (:phonenumber @st)))
                               (not (empty? (:item @st))))}
                  [sc/icon-huge [:> solid/BackspaceIcon]]]
                 (= :toggle e)
                 [bis/numberpad-button
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
  [bis/toggle-button
   {:on-click  #(key-command st)
    :value     (:key @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:key @st) {:box-shadow "var(--shadow-1)"
                               :color      "var(--gray-10)"
                               :background "var(--yellow-5)"}
                              {:background "var(--content)"})
    :content   [sc/icon-large [:> (if (:key @st) solid/KeyIcon outline/KeyIcon)]]}])

(defn- moon-command [st]
  (swap! st update :moon (fnil not false)))

(defn moon [st]
  [bis/toggle-button
   {:on-click  #(moon-command st)
    :value     (:moon @st)
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if (:moon @st) {:box-shadow "var(--shadow-1)"
                                :color      "var(--gray-10)"
                                :background "var(--yellow-5)"}
                               {:background "var(--content)"})
    :content   [sc/icon-large [:> (if (:moon @st) solid/MoonIcon outline/MoonIcon)]]}])

(defn- add-command [st]
  (if (<= 3 (count (:item @st)))
    (swap! st #(-> %
                   ;(assoc :selected (:item %))
                   (update :list (fnil conj #{}) (:item-data %))
                   ;;clear the item
                   ((fn [e] (if true (dissoc e :item) identity)))))))

(defn add [st]
  [bis/add-button
   {:on-click #(add-command st)
    :value    true
    :enabled  (and (some? (:item-data @st))
                   (not= (:item @st) (:selected @st))
                   (not (some #{(:item @st)} (mapv :number (:list @st))))
                   ;(not (some #{(:item @st)} (:list @st)))
                   ;(not (some? (:selected @st)))
                   (<= 3 (count (:item @st))))
    :on-style {:color      "var(--surface4)"
               :background "var(--surface1)"}}
   (sc/icon-huge ico/plusCircle)])

(defn- delete-clicked [st]
  (swap! st #(-> %
                 (dissoc :item :selected)
                 (update :list set/difference #{(:selected %)}))))

(defn delete [st]
  [bis/delete-button
   {:on-click #(delete-clicked st)
    :value    true
    :on-style {:background "var(--toolbar)"}
    :enabled  (and (or (not (empty? (:selected @st)))
                       (not (empty? (:item @st)))))}
   (sc/icon-huge ico/trash)])

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
                           ;(tap> "reset!")
                           (when @timer
                             (reset! timer nil)
                             (restart-command st)))
               mousedown (fn [e]
                           ;(tap> "md")
                           (when (form-dirty?)
                             (reset! timer (js/setTimeout ontimeout 1100))))
               mouseup (fn [e]
                         ;(tap> "up")
                         (when @timer
                           (js/clearTimeout @timer))
                         (reset! timer nil))]
    (let [dirty? (form-dirty?)]
      [bis/button
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
                     {:transition-duration "2s"
                      :color               "var(--gray-0)"
                      :background          "var(--red-8)"}
                     {;:transition-duration "2s"
                      :color      "var(--text0)"
                      :background "var(--content)" #_"var(--brand1-6)"})
                   {:transition-duration "0s"
                    :color               "var(--text3)"
                    :background          "none"})}
       [:div {:style {:transition-duration "1s"
                      :transform           (if @timer "rotate(-360deg)")}}
        (sc/icon-large (icon/adapt :refresh 3))]])
    (finally (.removeEventListener @a "mousedown" mousedown)
             (.removeEventListener @a "touchstart" mousedown)
             (.removeEventListener @a "mouseup" mouseup)
             (.removeEventListener @a "touchend" mouseup))))

;region core

(rf/reg-sub :rent/lookup
            :<- [:db/boat-db]
            :-> #(into {} (->> (remove (comp empty? :number val) %)
                               (map (fn [[k v]]
                                      [(:number v)
                                       (assoc (select-keys v [:number :navn :kind :star-count :stability :material]) :id k)])))))

(rf/reg-sub :rent/list
            (fn [_ _]
              (sort-by (comp :timestamp val) >
                       (transduce
                         (comp
                           (map identity)
                           (filter (fn [[k v]]
                                     (t/<= (t/<< (t/today) (t/new-period 3 :days))
                                           (t/date (t/instant (:timestamp v)))))))
                         conj
                         []
                         @(db.core/on-value-reaction {:path ["activity-22"]})))))

(rf/reg-fx :rent/write (fn [data]
                         (let [list (into {} (map (fn [{:keys [id]}] [id ""]) (:list data)))]
                           (tap> {:list list
                                  :data data})
                           (db.core/database-push
                             {:path  ["activity-22"]
                              :value {:timestamp (str (t/now))
                                      :sleepover (:moon data)
                                      :adults    (:adults data)
                                      :havekey   (:key data)
                                      :children  (:children data)
                                      :juveniles (:juveniles data)
                                      :uid       (:uid data)
                                      :list      list}}))))


(rf/reg-event-fx :rent/store
                 (fn [_ [_ data]]
                   {:fx [[:rent/write data]]}))

(defn- confirm-command [st]
  (let [loggedin-uid @(rf/subscribe [:lab/uid])
        ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (map :number (:list @st))))
                     (empty? (:item @st))))]
    ;(js/alert "Dialogen blir værende men du blir flyttet (hvis du ikke allerede er der) til den siden som viser en liste over alle dine aktiviteter. Denne siste registreringen vil ligge øverst i listen.")
    (when ok?
      ;todo add to db here
      (rf/dispatch [:rent/store (assoc @st :uid loggedin-uid)])
      ;todo add counter increment update here?
      (rf/dispatch [:app/navigate-to [:r.utlan]])
      (rf/dispatch [:modal.boatinput/close])

      #_(booking.aktivitetsliste/add-command (assoc @st :start (t/instant (t/now))))
      (restart-command st))))

(defn lookup [id]
  (let [data @(rf/subscribe [:rent/lookup])]
    (get data id)))

;endregion

(defn confirm [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [bis/add-button
     {:on-click #(confirm-command st)
      :enabled  ok?
      :styles   (when ok? {:color      "var(--green-1)"
                           :background "var(--green-6)"})}
     (sc/icon-huge [:> outline/CheckCircleIcon])]))

(defonce st (r/atom {}))

(defn boatpanel-window [mobile? left-side?]
  (let [keydown-f (fn [event]
                    ;(tap> event)
                    ;(.stopPropagation event)
                    (let [kc (.-keyCode event)]
                      ;(tap> kc)
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
         [:div
          {:tab-index 0
           :class     (if mobile?
                        [:outline-none
                         :h-full :flex :flex-col :justify-end]
                        [:outline-none
                         :focus:outline-none])
           :style     (if mobile? {}                        ;:padding-block "1rem"
                                  ;:position :absolute
                                  ;:top 0 :left 0 :right 0 :bottom 0
                                  ;:overflow-y :auto
                                  ;:max-height     "calc(100vh - 5rem)"

                                  {:padding        "var(--size-1)"
                                   :pointer-events :auto})
           :ref       (fn [e]
                        (when-not @ref
                          ;(tap> ["adding for " e])
                          (.addEventListener e "keydown" keydown-f)
                          (.focus e)
                          (reset! ref e)))}

          ;(tap> @st)
          [bis/panel
           {:class [:p-4
                    (if mobile? :mobile (if left-side? :left-side :right-side))]}
           [:div {:style {:grid-area "numpad"}} [numberinput st]]
           [:div {:style {:grid-area "child"}} [children st]]
           [:div {:style {:grid-area "juvenile"}} [juveniles st]]
           [:div {:style {:grid-area "adult"}} [adults st]]
           [:div {:style {:grid-area "moon"}} [moon st]]
           [:div {:style {:grid-area "key"}} [havekey st]]

           ;input
           [:div
            {:style {:grid-area      "input"
                     ;:grid-row       "5"
                     ;:grid-column    "2/span 2"
                     :padding-inline "var(--size-2)"
                     :border-radius  "var(--radius-1)"
                     :background     "var(--field2)"
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
                         :color       "var(--text1)"}}
                (when (empty? (:phonenumber @st))
                  [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])
                [bis/input-caption (if (empty? (:phonenumber @st)) [:span.opacity-30 "telefonnr"] (:phonenumber @st))]
                (when-not (empty? (:phonenumber @st))
                  [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])]
               [:div
                {:style {:display     "flex"
                         :align-items "center"
                         :font-size   "var(--font-size-5)"
                         :color       "var(--text1)"}}
                (when (empty? (:item @st))
                  [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"])
                [bis/input-caption (if (empty? (:item @st)) [:span.opacity-30 "båtnr"] (:item @st))]
                (when-not (empty? (:item @st))
                  ;[:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "["]
                  (if (< 3 (count (:item @st)))
                    [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"]
                    [:span.blinking-cursor.pb-2 {:style {:font-size :120%}} "|"]))])]]

           [:div
            {:style {:grid-area      "boat"
                     :padding-inline "var(--size-2)"
                     :display        :flex
                     :align-items    :center}}

            (cond
              (= 4 (count (:item @st)))
              (let [n (subs (str (:item @st)) 0 3)]
                [sc/col
                 [sc/text1 "for testing " n]
                 (if-let [boat (lookup n)]
                   (do
                     (swap! st assoc-in [:item-data] boat)
                     [l/ppre-x boat])
                   (do
                     (swap! st assoc-in [:item-data] nil)
                     [sc/text1 "Finner ikke " n]))])

              (<= 3 (count (:item @st)))
              (if-let [boat (lookup (:item @st))]
                (do
                  (swap! st assoc-in [:item-data] boat)
                  [l/ppre-x boat])
                (do
                  (swap! st assoc-in [:item-data] nil)
                  [sc/text1 "Finner ikke " (:item @st)]))

              :else (do
                      (swap! st assoc-in [:item-data] nil)
                      [sc/col {:class [:space-y-px :opacity-50]}
                       [sc/title1 [sc/row-sba "Skriv båtnummeret og"
                                   [sc/icon [:> outline/PlusCircleIcon]]]]
                       [sc/subtext "Bruk 4 siffer for testing"]]))]

           ;boats
           [:div.p-1
            {:style {:grid-area             "boats"
                     :border-radius         "var(--radius-1)"
                     :background            "var(--field1)"
                     :gap                   "var(--size-1)"
                     :display               :grid
                     :grid-template-columns "1fr"
                     :grid-template-rows    "repeat(3,1fr)"}}
            (for [e (take 3 (sort (map :number (:list @st))))]
              [sc/badge
               {:selected (and (= e (:selected @st)))
                :on-click (fn [] (if (= e (:selected @st))
                                   (swap! st dissoc :selected
                                          dissoc :item)
                                   (swap! st #(-> %
                                                  (assoc :selected e)
                                                  (assoc :item e)))))} e])]
           [:div {:style {:grid-area "trash"}} [delete st]]
           [:div {:style {:grid-area "add"}} [add st]]
           [:div {:style {:grid-area "complete"}} [confirm st]]
           [:div {:style {:grid-area "restart"}} [restart st]]

           ;boats for all items above 3
           (when (< 3 (count (:list @st)))
             [:div.p-1 {:style {:grid-column           "1/span 4"
                                :grid-row              "10/span 2"
                                :border-radius         "var(--radius-1)"
                                :background            "var(--surface00)"
                                :gap                   "var(--size-1)"
                                :display               :grid
                                :grid-template-columns "repeat(4,1fr)"
                                :grid-template-rows    "repeat(3,1fr)"}}
              (concat
                (for [e (take 11 (drop 3 (sort (map :number (:list @st)))))]
                  [sc/badge
                   {:selected (and (= e (:selected @st)))
                    :on-click (fn [] (if (= e (:selected @st))
                                       (swap! st dissoc :selected
                                              dissoc :item)
                                       (swap! st #(-> %
                                                      (assoc :selected e)
                                                      (assoc :item e)))))} e])
                (if (< 16 (count (:list @st))) [[sc/badge {} "..."]]))])]])})))

(defn window-content [{:keys [on-close]}]
  (let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
        right? (schpaa.state/listen :lab/menu-position-right)] ;[sc/centered-dialog]
    [:div
     {:style {:background-color "var(--toolbar)"
              :display          :grid
              :overflow-y       :auto
              :place-content    :center}}
     [:div
      {:style {:width      "18rem"
               :max-height "90vh"}}
      [boatpanel-window @mobile? @right?]]]))

(rf/reg-sub :modal.boatinput/is-visible :-> #(get % :modal.boatinput/visible false))
(rf/reg-sub :modal.boatinput/get-context :-> :modal.boatinput/context)

(rf/reg-event-db :modal.boatinput/show (fn [db [_ context]]
                                         (assoc db :modal.boatinput/visible true
                                                   :modal.boatinput/context context)))

(rf/reg-event-db :modal.boatinput/close (fn [db _]
                                          (assoc db :modal.boatinput/visible false)))

(rf/reg-event-db :modal.boatinput/clear (fn [db _]
                                          ;dont clear the state
                                          ;(reset! st {})
                                          (assoc db :modal.boatinput/context nil)))

(defn open-boatpanel
  ""
  [{db :db}]
  {:fx [[:dispatch
         [:modal.boatinput/show                             ;lab/modaldialog-visible ;:lab/modal-selector
          ;(-> db :modal.boatinput/visible not)
          {:on-primary-action #(rf/dispatch [:modal.boatinput/clear])
           :content-fn        #(window-content %)
           #_(fn [{:keys [on-close]}]
               #_[:div
                  {:style {:align-self       :start
                           :border-radius    "var(--radius-2)"
                           ;:height           "100%"
                           :width            "18rem"        ;"100%"

                           ;:xmax-width       "18rem"
                           :background-color "var(--toolbar)"}}
                  ;[boatpanel-window true false]
                  [:div.bg-altx.xw-full.overflow-auto       ;{:style {:height "150vh"}}
                   ;[:div.grid {:style {:height "150vh"}}
                   #_[boatpanel-window true false]
                   [:div {:style {:max-height "90vh"
                                  :height     "100%" :xwidth "20rem"}}
                    #_(for [e (range 50)]
                        [sc/text1 e])
                    [boatpanel-window true false]

                    [sc/bottom-toolbar-style {:-class [:sticky :bottom-0]}

                     [vertical-button
                      {:icon     ico/closewindow
                       :on-click on-close}]

                     [vertical-button
                      {:xicon     [:> outline/DotsHorizontalIcon]
                       :disabled  true
                       :xon-click on-close}]
                     [vertical-button
                      {:xicon     [:> outline/EmojiSadIcon]
                       :disabled  true
                       :xon-click on-close}]
                     [vertical-button
                      {:xicon     [:> solid/FingerPrintIcon]
                       :disabled  true
                       :xon-click on-close}]
                     [vertical-button
                      {:icon     ico/checkCircle
                       :on-click on-close}]

                     #_[sc/row-center {:xclass [:shrink-0 :xpx-4 :z-100]
                                       :style  {
                                                :height            "5rem"
                                                :xmin-height       "5rem"
                                                :xbackground-color "var(--toolbar)"
                                                :xalign-items      :center}}
                        [sc/toolbar-button {:sclass   [:h-8
                                                       :rounded-full]
                                            :on-click on-close} [sc/icon-large ico/closewindow]]]]]]])}]]]})

(rf/reg-event-fx :lab/toggle-boatpanel [rf/trim-v] open-boatpanel)

(rf/reg-sub :lab/number-input2 :-> :lab/number-input2)

;region number-input

;todo ultimately remove

(rf/reg-event-db :lab/toggle-number-input (fn [db]
                                            (update db :lab/number-input (fnil not false))))

(rf/reg-event-db :lab/open-number-input (fn [db]
                                          (assoc db :lab/number-input true)))

(rf/reg-event-db :lab/close-number-input (fn [db]
                                           (assoc db :lab/number-input false)))

(rf/reg-sub :lab/number-input :-> :lab/number-input)

;endregion

;endregion

(o/defstyled experimental :div
  {:display          :grid
   :place-content    :end
   :background-color "var(--toolbar-)"
   :border-radius    "var(--radius-2)"
   :width            "18rem"
   :height           "100%"}
  [:at-media {:max-width "511px"}
   {:width  "100vw"
    :height "100vh"}]
  [:at-media {:min-width "512px"}])

(defn render-boatinput
  "centered dialog used by this component"
  []
  (let [{:keys [context vis close]} {:context @(rf/subscribe [:modal.boatinput/get-context])
                                     :vis     (rf/subscribe [:modal.boatinput/is-visible])
                                     :close   #(rf/dispatch [:modal.boatinput/close])}
        {:keys [action on-primary-action click-overlay-to-dismiss content-fn auto-dismiss]
         :or   {click-overlay-to-dismiss true}} context]
    (r/with-let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
                 write-success (r/atom false)]
      (let [open? @vis]
        [ui/transition
         {:appear      true
          :after-enter #(tap> "modaldialog-centered after-enter")
          :show        open?}
         [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))} ;must press cancel to dismiss
          [:div.fixed
           {:class (cond
                     @mobile? :inset-0
                     (not @(schpaa.state/listen :lab/menu-position-right)) :right-8
                     :else :left-8)}
           [:div.text-center
            [schpaa.style.dialog/standard-overlay]
            [:span.inline-block.h-screen.align-middle
             (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
            [ui/transition-child
             {
              :class       [:inline-block :align-middle :text-left :transform
                            (some-> (sc/inner-dlg) last :class first)]
              :enter       "ease-out duration-100"
              :enter-from  "opacity-0  translate-y-16"
              :enter-to    "opacity-100  translate-y-0"
              :leave       "ease-in duration-200"
              :leave-from  "opacity-100  translate-y-0"
              :leave-to    "opacity-0  translate-y-32"
              :after-leave #(when on-primary-action
                              (on-primary-action context))}
             (when content-fn
               (content-fn (assoc context
                             :on-close close
                             :on-save #(do
                                         (reset! write-success true)
                                         (when action
                                           (action {:context context
                                                    :carry   %}))
                                         ;todo remove comment to allow closing
                                         (close))
                             :action action)))]]]]]))))

