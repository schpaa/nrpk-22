(ns booking.modals.boatinput
  (:require [reagent.core :as r]
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
            [schpaa.debug :as l]
            [booking.common-widgets :as widgets]
            [booking.modals.boatinput.commands :as cmd]
            [booking.modals.boatinput.actions :as actions]))

(declare boatpanel-window)

(defn window-content [{:keys [on-close]}]
  (let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
        right? (schpaa.state/listen :lab/menu-position-right)]
    [:div
     {:style {:background-color "var(--content)"
              :border-radius    "var(--radius-2)"
              :border-color     "var(--content)"
              :border-width     "0px"
              :display          :grid
              :overflow-y       :auto
              :place-content    :center}}
     [:div
      {:style {:padding-block "0rem"
               :width         "auto"
               :max-height    "90vh"}}
      [boatpanel-window @mobile? @right?]]]))

(rf/reg-event-fx :lab/toggle-boatpanel
                 (fn [_ _]
                   {:fx [[:dispatch
                          [:modal.boatinput/show
                           {:on-primary-action #(rf/dispatch [:modal.boatinput/clear])
                            :content-fn        #(window-content %)}]]]}))

;region state

(defonce st (r/atom {:focus :phone}))

(def c-children (r/cursor st [:children]))
(def c-juveniles (r/cursor st [:juveniles]))
(def c-adults (r/cursor st [:adults]))
(def c-moon (r/cursor st [:moon]))
(def c-litteral-key (r/cursor st [:litteral-key]))
(def c-lookup-result (r/cursor st [:item-data]))
(def c-textinput (r/cursor st [:item]))

;endregion

;region input-areas/buttons

(defn children [c]
  ;todo refactor
  [bis/up-down-button
   {:content  (fn [value]
                [:div.flex.flex-col.justify-end.items-center.h-full
                 [:img.h-12.object-fit.-ml-px {:src "/img/human.png"}]
                 (str value)])
    :value    (or @c 0)
    :increase #(cmd/increase-children c)
    :decrease #(cmd/decrease-children c)}])
(defn juveniles [c]
  [bis/up-down-button {:content  (fn [value]
                                   [:div.flex.flex-col.justify-end.items-center.h-full
                                    [:img.h-16.object-fit.-ml-px {:src "/img/teen.png"}]
                                    (str value)])
                       :value    (or @c 0)
                       :increase #(cmd/increase-juveniles c)
                       :decrease #(cmd/decrease-juveniles c)}])
(defn adults [c]
  [bis/up-down-button {:content  (fn [value]
                                   [:div.flex.flex-col.justify-end.items-center.h-full
                                    [:img.h-20.object-fit {:src "/img/human.png"}]
                                    (str value)])
                       :value    (or @c 0)
                       :increase #(cmd/increase-adults c)
                       :decrease #(cmd/decrease-adults c)}])
(defn- key-clicked [st e]
  (swap! st #(-> %
                 (update :item str e)
                 ((fn [st]
                    (if (some #{(:item st)} (map :number (:list st)))
                      (assoc st :selected (:item st))
                      st))))))
(defn number-pad [st]
  [:div.h-full
   {:style {:display               :grid
            :border-radius         "var(--radius-0)"
            :gap                   "var(--size-1)"
            :grid-template-columns "repeat(3,1fr)"
            :grid-auto-rows        "1fr"}}
   (doall (for [e '[7 8 9 4 5 6 1 2 3 :toggle 0 :del]]
            (if (number? e)
              [bis/numberpad-button {:on-click #(key-clicked st e)
                                     :enabled  (if (:phone @st)
                                                 (< (count (:phonenumber @st)) 8)
                                                 (< (count (:item @st)) 4))}
               [bis/button-caption e]]
              (cond
                (= :del e)
                [bis/numberpad-button
                 {:on-click (fn [] (if (:phone @st)
                                     (swap! st #(-> %
                                                    (update :phonenumber (fn [s] (subs s 0 (dec (count s)))))))
                                     (cmd/backspace-clicked st)))
                  :style    {:color (if (not (empty? (:item @st))) "var(--orange-5)" "var(--text2)")}
                  :enabled  (if (:phone @st)
                              (not (empty? (:phonenumber @st)))
                              (not (empty? (:item @st))))}
                 [sc/icon-huge ico/backspace]]
                (= :toggle e)
                [bis/numberpad-button
                 {:on-click #(swap! st update :phone (fnil not false))}
                 (if (:phone @st)
                   [sc/icon-large ico/tag]
                   [sc/icon-large ico/phone])]

                :else [:div]))))])
(defn havekey-button [c]
  [bis/toggle-button
   {:on-click  #(cmd/key-command c)
    :value     @c
    :off-style {:color      "var(--surface2)"
                :background "none"}
    :style     (if @c {:box-shadow "var(--shadow-1)"
                       :color      "var(--gray-10)"
                       :background "var(--yellow-5)"}
                      {:background "var(--content)"})
    :content   [sc/icon-large (if @c ico/key-filled ico/key-outline)]}])
(defn moon [c]
  [bis/toggle-button
   {:on-click  #(cmd/moon-command c)
    :value     @c
    :off-style {:color "var(--surface2)"}
    :style     (if @c {:box-shadow "var(--shadow-1)"
                       :color      "var(--gray-10)"
                       :background "var(--yellow-5)"}
                      {:background "var(--content)"})
    :content   [sc/icon-large (if @c ico/moon-filled ico/moon-outline)]}])
(defn add-button [st textinput-c lookup-result-c]
  [bis/add-button
   {:on-click #(cmd/add-command st)
    :value    true
    :enabled  (and (some? @lookup-result-c)                 ;(:item-data @st)
                   (not= @textinput-c (:selected @st))
                   (not (some #{@textinput-c} (mapv :number (:list @st))))
                   (<= 3 (count @textinput-c)))
    :on-style {:color      "var(--blue-5)"
               :background "var(--content)"}}
   (sc/icon-huge ico/plusCircle)])
(defn delete-button [st]
  [bis/delete-button
   {:on-click #(cmd/delete-clicked st)
    :value    true
    :on-style {:background "var(--toolbar)"}
    :enabled  (and (or (not (empty? (:selected @st)))
                       (not (empty? (:item @st)))))}
   (sc/icon-huge ico/trash)])
(defn reset-button [st]
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
                             (cmd/reset-command st)))
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

;endregion

(rf/reg-sub :rent/list
            #(sort-by (comp :timestamp val) >
                      (transduce
                        (comp
                          (map identity)
                          (filter (fn [[k v]]
                                    (when (:timestamp v)
                                      (t/<= (t/<< (t/today) (t/new-period 3 :days))
                                            (t/date (t/instant (:timestamp v))))))))
                        conj
                        []
                        @(db.core/on-value-reaction {:path ["activity-22"]}))))

(rf/reg-sub :rent/lookup
            :<- [:db/boat-db]
            :-> #(into {} (->> (remove (comp empty? :number val) %)
                               (map (fn [[k v]]
                                      [(:number v)
                                       (assoc (select-keys v [:number :navn :kind :star-count :stability :material :weigth :length :width]) :id k)])))))

(defn lookup [id]
  ;todo define as defonce
  (let [data @(rf/subscribe [:rent/lookup])]
    (get data id)))

(def focus-fields [[:phone "TLF"]
                   [:boat "BÅTNR"]
                   [:keyid "NØKKEL"]])

;region components

(defn velkomsttekst []
  [sc/co {:class [:px-2 :py-4]
          :style {:grid-area "welcome"}}
   [sc/co
    [:div.flex.justify-start.items-center.gap-2
     [schpaa.style.hoc.buttons/round' [sc/icon ico/closewindow]]
     [sc/title1 "User-welcome"]]
    [sc/text1 "Brief explanation of the user-interface, order of flow and so on"]]])
(defn hvem-er-du []
  [sc/surface-ab
   {:class [:h-full]}
   [sc/co {:class [:px-1]}
    [:div.col-span-4.h-10.flex.items-center.justify-start.-debugx
     [sc/title1 "Hvem er du?"]]
    [:div.col-span-4.h-10.flex.items-center.justify-start.-debugx
     [sc/text1 (cond
                 (= 3 (count (:phonenumber @st)))
                 (str "Nøkkelnummer " (subs (str (:phonenumber @st)) 0 3))
                 (= 8 (count (:phonenumber @st)))
                 (str "Telefon " (:phonenumber @st))
                 :else "Skriv telefon– eller nøkkelnummer")]]]])
(defn input-area [st c-textinput]
  (let [focus-c (r/cursor st [:focus])
        field (fn [v]
                [:div.inline-flex.items-center.justify-center.h-full.pt-4
                 [bis/input-caption v [:span.blinking-cursor.pb-1 "|"]]])
        next-field (fn [_] (swap! focus-c
                                  (fn [n] (mod ((fnil inc 0) n)
                                               (count focus-fields)))))]
    [:div.relative
     {:on-click next-field
      :style    {:padding-inline "var(--size-2)"
                 :border-radius  "var(--radius-1)"
                 :background     "var(--field1)"
                 :color          "var(--gray-9)"
                 :box-shadow     "var(--inner-shadow-1)"}}
     (field @c-textinput)]))
(defn selected-boats []
  (let [have-any? (-> (:list @st) count)
        search (seq (:item @st))
        kan-ikke-finne "Finnes ikke"
        venter "3 siffer "
        velg-båter "Skriv et båtnummer"]
    [sc/col

     [sc/surface-ab
      {:style {:border-radius         "var(--radius-1)"
               :box-shadow            "var(--shadow-1),var(--inner-shadow-2)"
               :column-gap            "var(--size-2)"
               :row-gap               "var(--size-2)"
               :display               :grid
               :grid-template-columns "repeat(4,1fr) "
               :grid-template-rows    "auto"}}

      (if (pos? have-any?)
        (for [e (sort (map :number (:list @st)))]
          [sc/badge
           {:class     [
                        (if (= e (:selected @st)) :selected :normal)
                        #_(when (not= e (:selected @st)) :selected)]
            :xselected (and (not= e (:selected @st)))
            :on-click  (fn [] (if (= e (:selected @st))
                                (swap! st dissoc :selected
                                       dissoc :item)
                                (swap! st #(-> %
                                               (assoc :selected e)
                                               (assoc :item e)
                                               (assoc :phone false)))))} e])

        [:div.col-span-4.h-12.flex.items-center.justify-start
         [sc/title1 "Du har ingen båter"]])

      [:div.col-span-4
       (if search
         (if (and (not (:phone @st))
                  (<= 3 (count (:item @st))))
           (if-let [boat (lookup (:item @st))]
             (do
               (swap! st assoc-in [:item-data] boat)
               [sc/row-sc-g2 {:class [:w-full :h-12]}
                [widgets/stability-name-category boat]])

             (do
               (swap! st assoc-in [:item-data] nil)
               [sc/row-center {:class [:w-full :h-12]} [sc/title kan-ikke-finne]]))
           [sc/row-center {:class [:w-full :h-12]} [sc/title venter (:item @st)]])

         [sc/row-center
          {:class [:w-full :h-12]}
          [sc/title1 (if (pos? have-any?) have-any? velg-båter)]])]]]))
(defn complete [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [bis/add-button
     {:on-click (when (actions/confirm-command st)
                  (cmd/reset-command st))
      :enabled  ok?
      :styles   (when ok? {:color      "var(--green-1)"
                           :background "var(--green-6)"})}
     (sc/icon-huge ico/checkCircle)]))

;endregion

(defn boatpanel-window [mobile? left-side?]
  (let [keydown-f (fn [event]
                    ;(tap> event)
                    ;(.stopPropagation event)
                    (let [kc (.-keyCode event)]
                      ;(tap> kc)
                      (cond
                        (= kc keycodes/S) (actions/confirm-command st)
                        (= kc keycodes/R) (cmd/reset-command st)
                        (= kc keycodes/N) (cmd/key-command c-litteral-key)
                        (= kc keycodes/O) (cmd/moon-command c-moon)
                        (= kc keycodes/F) (cmd/decrease-adults c-adults)
                        (= kc keycodes/V) (cmd/increase-adults c-adults)
                        (= kc keycodes/G) (cmd/decrease-children c-children)
                        (= kc keycodes/B) (cmd/increase-children c-children)
                        (= kc keycodes/ENTER) (cmd/add-command st)
                        (= kc keycodes/DELETE) (cmd/delete-clicked st)
                        (= kc keycodes/BACKSPACE) (cmd/backspace-clicked st)
                        (some #{kc} (range 48 58)) (key-clicked st (- kc 48)))))
        ref (r/atom nil)]

    (r/create-class
      {:reagent-render
       (fn [_]
         [sc/row
          (when goog.DEBUG
            [:div.bg-black
             {:style {:width "15rem"}}
             [l/pre @st]])
          [:div
           {:tab-index 0
            :class     (if mobile?
                         [:outline-none
                          :h-full :flex :flex-col :justify-end]
                         [:outline-none
                          :focus:outline-none])
            :ref       (fn [e]
                         (when-not @ref
                           (.addEventListener e "keydown" keydown-f)
                           (.focus e)
                           (reset! ref e)))}
           [bis/panel
            {:class [:p-4 :mobile]}
            (concat [[velkomsttekst]]
                    (let [f (fn [[gridarea component]]
                              [:div {:style {:grid-area gridarea}} component])]
                      (mapv f [["numpad" [number-pad st]]
                               ["child" [children c-children]]
                               ["juvenile" [juveniles c-juveniles]]
                               ["adult" [adults c-adults]]
                               ["moon" [moon c-moon]]
                               ["key" [havekey-button c-litteral-key]]
                               ["aboutyou" [hvem-er-du]]
                               ["input" [input-area st c-textinput]]
                               ["boats" [selected-boats]]
                               ["trash" [delete-button st]]
                               ["add" [add-button st c-textinput c-lookup-result]]
                               ["restart" [reset-button st]]
                               ["complete" [complete st]]])))]]])})))

;region dialog-related

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
  [_]
  {:fx [[:dispatch
         [:modal.boatinput/show
          {:on-primary-action #(rf/dispatch [:modal.boatinput/clear])
           :content-fn        #(window-content %)}]]]})

(defn render-boatinput
  "centered dialog used by this component"
  []
  (let [{:keys [context vis close]}
        {:context @(rf/subscribe [:modal.boatinput/get-context])
         :vis     (rf/subscribe [:modal.boatinput/is-visible])
         :close   #(rf/dispatch [:modal.boatinput/close])}
        {:keys [action on-primary-action click-overlay-to-dismiss content-fn]
         :or   {click-overlay-to-dismiss true}} context]
    (r/with-let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
                 right-side? (schpaa.state/listen :lab/menu-position-right)
                 write-success (r/atom false)]
      (let [open? @vis]
        [ui/transition
         {:after-enter #(tap> "modaldialog-centered after-enter")
          :show        open?}
         [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))} ;must press cancel to dismiss
          [:div.fixed
           {:class [:w-auto :top-0 :h-auto (cond
                                             @mobile? :inset-0
                                             (not @right-side?) :left-12
                                             :else :right-12)]}
           [:div.text-center
            [schpaa.style.dialog/standard-overlay]
            [:span.inline-block.h-screen.align-middle
             (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
            [ui/transition-child
             {
              :class       [:inline-block :align-middle :text-left :transform
                            (some-> (sc/inner-dlg) last :class first)]
              :enter       "ease-in-out duration-200"
              :enter-from  (cond
                             @mobile? "opacity-0  translate-y-16"
                             @right-side? "opacity-0  translate-x-16"
                             :else "opacity-0  -translate-x-32")
              :enter-to    "opacity-100 translate-x-0 translate-y-0"
              :entered     "drop-shadow-2xl"
              :leave       "ease-in-out duration-200"
              :leave-from  "opacity-100 translate-x-0"
              :leave-to    (cond
                             @mobile? "opacity-0 translate-y-32"
                             @right-side? "opacity-0  translate-x-32"
                             :else "opacity-0  -translate-x-32")
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

;endregion