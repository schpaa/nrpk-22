(ns booking.modals.boatinput
  (:require [reagent.core :as r]
            [schpaa.style.ornament :as sc]
            [schpaa.icon :as icon]
            [re-frame.core :as rf]
            [tick.core :as t]
            [booking.aktivitetsliste]
            [schpaa.style.dialog]
            [goog.events.KeyCodes :as keycodes]
            [booking.ico :as ico]
            [booking.common-widgets :refer [vertical-button]]
            [booking.modals.boatinput.styles :as bis]
            [headlessui-reagent.core :as ui]
            [booking.common-widgets :as widgets]
            [booking.modals.boatinput.commands :as cmd]
            [booking.modals.boatinput.actions :as actions]
            [lambdaisland.ornament :as o]
            [db.core :as db]))

;region machinery

(defn- equals-to [a b]
  (= a (str b)))

(defn- number-not-in-list [x xs]
  (empty? (filter
            (comp (partial equals-to x)
                  :number)
            xs)))

(def debug nil)

(declare input-area)

;; store

(def test-state
  {:litteral-key   false
   :lookup-results nil
   :adults         1
   :juveniles      0
   :children       0
   :selected       "999"
   :list           [{:new true :number "999"}
                    {:new true :number "991"}
                    {:new false :number "444"}]
   :textfield      {;:phone "123"
                    :boats nil}})

(defonce st (r/atom (if goog.DEBUG {} #_test-state {})))

(def c-focus (r/cursor st [:focus]))
(def c-extra (r/cursor st [:extra]))
(def c-children (r/cursor st [:children]))
(def c-juveniles (r/cursor st [:juveniles]))
(def c-adults (r/cursor st [:adults]))
(def c-moon (r/cursor st [:moon]))
(def c-litteral-key (r/cursor st [:litteral-key]))
(def c-lookup-result (r/cursor st [:lookup-results]))
(def c-boat-list (r/cursor st [:list]))
(def c-textinput-phone (r/cursor st [:textfield :phone]))
(def c-textinput-boat (r/cursor st [:textfield :boats]))
(def c-selected (r/cursor st [:selected]))
(defn focused-field [c-focus]
  (cond
    (= :boats @c-focus) c-textinput-boat
    :else c-textinput-phone))

(rf/reg-sub :rent/lookup
            :<- [:db/boat-db]
            :-> #(into {} (->> (remove (comp empty? :number val) %)
                               (map (fn [[k v]]
                                      [(:number v)
                                       (assoc (select-keys v [:boat-type :number :navn :kind :star-count :stability :material :weigth :length :width]) :id k)])))))

(rf/reg-sub :rent/id-lookup :<- [:db/boat-db] :-> identity)

(defn input-area [length caption fieldname]
  ;cap size of input to length
  (let [c-field (r/cursor st [:textfield fieldname])]
    (reset! c-field (subs (str @c-field) 0 length))
    (let [has-focus? (= @c-focus fieldname)
          textinput (fn [v]
                      [:div.tabular-nums.inline-flex.items-end.justify-center.h-full.pb-1
                       [bis/input-caption v (when has-focus? [:span.blinking-cursor.pb-1 "|"])]])]
      [:div.relative.h-16.shrink-0
       {:on-click #()
        :style    {:padding-inline   "var(--size-2)"
                   :border-radius    "var(--radius-0)"
                   :background-color (if has-focus? "var(--field-focus)" "var(--field)")
                   :color            (if has-focus? "var(--fieldcopy)" "var(--text1)")
                   :box-shadow       (if has-focus? "var(--inner-shadow-1)")}}
       [:div.absolute.top-1.left-2
        [sc/small {:style {:font-weight "var(--font-weight-5)"}} caption]]
       (textinput (subs (str @c-field) 0 length))])))

(defn lookup [id]
  ;todo define as defonce
  (let [data @(rf/subscribe [:rent/lookup])]
    (get data id)))

(defn lookup-id [id]
  (let [id (if (keyword? id) id (keyword id))
        data @(rf/subscribe [:db/boat-db])]
    (get data id)))

(comment
  (lookup-id :-MeAStzi0016B5sFIxvR))

(defn key-clicked [c-textinput value]
  ;if some in the list matches this number, make it selected
  (swap! st assoc :selected nil)
  (swap! c-textinput
         #((fn [st']
             (if (some #{st'} (map :number (:list st)))
               (assoc st' :selected @c-textinput)
               st'))
           (str % value))))

;region input-areas/buttons

(defn up-down-button [{:keys [increase decrease value content]}]
  (let [value (js/parseInt value)]
    [bis/up-down-button
     {:class [(if (pos? value) :some :zero)]
      :style {:position  :relative
              :display   :flex
              :flex-grow 1
              :height    :100%
              :overflow  :hidden}}
     [:div {:class [:flex :h-20 :items-end]} content]
     [:div.inset-0.absolute.flex.flex-col.justify-between
      {:style {:color "var(--text1)"}}
      [bis/center
       {:class    [:absolute :top-0 :pb-10]
        :on-click decrease}
       (when (pos? value)
         [sc/icon ico/minus])]
      [bis/center
       {:class    [:absolute :bottom-0 :pt-10]
        :on-click increase}
       (if (zero? value)
         [sc/icon ico/plus]
         [sc/ptitle {:style {:font-weight "var(--font-weight-7)"
                             :color       "var(--gray-9)"}} value])]]]))

(defn children-slider [c]
  ;todo refactor
  [up-down-button
   {:content  [:div.flex.items-end.h-full
               [:img.h-12.object-fit.-ml-px {:src "/img/human.png"}]]
    :value    (or @c 0)
    :increase #(cmd/increase-children c)
    :decrease #(cmd/decrease-children c)}])

(defn juveniles-slider [c]
  [up-down-button
   {:content  [:div.flex.items-end.h-full
               [:img.h-16.object-fit.-ml-px {:src "/img/teen.png"}]]

    :value    (or @c 0)
    :increase #(cmd/increase-juveniles c)
    :decrease #(cmd/decrease-juveniles c)}])

(defn adults-slider [c]
  [up-down-button
   {:content  [:div.flex.items-end.h-full
               [:img.h-20.object-fit {:src "/img/human.png"}]]
    :value    (or @c 0)
    :increase #(cmd/increase-adults c)
    :decrease #(cmd/decrease-adults c)}])

(defn litteralkey-toggle [c]
  [bis/toggle-button
   {:on-click #(cmd/litteral-key-command c)
    :value    @c
    :style    (if @c {:color            "var(--selected-copy)"
                      :background-color "var(--selected)"
                      :box-shadow       "var(--shadow-1)"}
                     {:color            "var(--text2"
                      :background-color "var(--floating)"})}
   [sc/icon-large (if @c ico/key-outline ico/key-filled)]])

(defn moon-toggle [c]
  [bis/toggle-button
   {:on-click #(cmd/moon-command c)
    :value    @c
    :style    (if @c {:color            "var(--selected-copy)"
                      :box-shadow       "var(--shadow-1)"
                      :background-color "var(--selected)"}
                     {:color            "var(--text2)"
                      :background-color "var(--floating)"})}
   [sc/icon-large (if @c ico/moon-filled ico/moon-outline)]])

(defn question-button [st source]
  [bis/push-button
   {:class    [:new]
    :on-click #(cmd/new-boat c-boat-list c-selected source)
    :value    true
    :disabled (and (not (number-not-in-list @source @c-boat-list))
                   (= 3 (count @source)))}
   (sc/icon-huge ico/plusplus)])

(comment
  (do
    (number-not-in-list @c-textinput-boat @c-boat-list)))

(defn add-button [st c-textinput lookup-result]
  [bis/push-button
   {:class    [:add]
    :on-click #(cmd/add-boat st c-textinput lookup-result)
    :value    true
    :disabled (or (< (count @c-textinput) 3)
                  (some? (first (filter (comp (partial equals-to @c-textinput) :number) @c-boat-list))))}
   (sc/icon-large ico/plus)])

(defn delete-button [st c-textinput]
  (let [nothing-selected (and (empty? @c-textinput)
                              (empty? @c-selected))]
    [bis/clear-field-button
     {:on-click (fn [_]
                  (if (some? @c-selected)
                    (do
                      (swap! c-boat-list
                             #(remove (comp (partial equals-to @c-selected) :number) %))
                      (reset! c-selected nil))
                    (do
                      (reset! c-textinput nil)
                      (reset! c-selected nil))))
      :disabled nothing-selected
      :style    {:color "var(--text)"}}
     (sc/icon-large ico/closewindow)]))

(comment
  (do
    (let [a (r/atom [{:a 1} {:b 2}])]
      (swap! a #(map (fn [kv] (update kv :aa inc)) %)))))

(defn reset-button [st]
  (r/with-let [form-dirty? #(or (pos? (+ (:adults @st)
                                         (:juveniles @st)
                                         (:children @st)))
                                (pos? (count (:list @st)))
                                @c-textinput-phone
                                @c-textinput-boat
                                (:moon @st)
                                (:key @st)
                                (not (empty? (:item @st))))
               a (r/atom nil)
               timer (r/atom nil)
               ontimeout (fn [_e]
                           (when @timer
                             (reset! timer nil)
                             (cmd/reset-command st)))
               mousedown (fn [_e]
                           (when (form-dirty?)
                             (reset! timer (js/setTimeout ontimeout 500))))
               mouseup (fn [_e]
                         (when @timer
                           (js/clearTimeout @timer))
                         (reset! timer nil))]
    (let [dirty? (form-dirty?)]
      [bis/push-button
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
                     {:color      "var(--text0)"
                      :background "var(--floating)"})
                   {:transition-duration "0s"
                    :color               "var(--text3)"
                    :background          "none"})}
       [:div {:style {:transition-duration "0.5s"
                      :transform           (if @timer "rotate(-360deg)")}}
        (sc/icon-large (icon/adapt :refresh 3))]])
    (finally (.removeEventListener @a "mousedown" mousedown)
             (.removeEventListener @a "touchstart" mousedown)
             (.removeEventListener @a "mouseup" mouseup)
             (.removeEventListener @a "touchend" mouseup))))

(defn number-pad [st]
  (let [active-field (focused-field c-focus)]
    [:div.h-full
     {:style {:display               :grid
              :border-radius         "var(--radius-0)"
              :gap                   "var(--size-1)"
              :grid-template-columns "repeat(3,1fr)"
              :grid-auto-rows        "1fr"}}
     (doall (for [e '[7 8 9 4 5 6 1 2 3 :reset 0 :del]]
              (if (number? e)
                [bis/push-button
                 {:class    [:button-pad]
                  :on-click #(key-clicked active-field e)
                  :disabled (not (< (count @active-field) (if (= :boats @c-focus) 3 8)))}
                 [bis/button-caption e]]
                (cond
                  (= :del e)
                  [bis/push-button
                   {:on-click #(cmd/backspace-clicked st active-field)
                    :style    {:color (if (not (empty? @active-field)) "var(--orange-5)" "var(--text2)")}
                    :enabled  (not (empty? @active-field))}
                   [sc/icon-huge ico/backspace]]

                  (= :reset e)
                  [reset-button st]

                  (= :toggle e)
                  [bis/push-button
                   {:on-click #(swap! st update :phone (fnil not false))}
                   (if (:phone @st)
                     [sc/icon-large ico/tag]
                     [sc/icon-large ico/phone])]

                  :else [:div]))))]))

;endregion

(defn f [a & b]
  [sc/co
   [sc/ptitle1 a]
   [sc/ptext b]])

;endregion

;region components

(defn hvem-er-du [st c-focus c-textinput-phone]
  (let [field c-textinput-phone
        has-focus? (= :phone @c-focus)]
    [sc/surface-ab
     {:on-click #(reset! c-focus :phone)
      :class    [:h-full (when has-focus? :focused) :relative]
      :style    {:display               :grid
                 :column-gap            "var(--size-2)"
                 :row-gap               "var(--size-2)"
                 :grid-template-columns "repeat(4,1fr)"
                 :grid-auto-rows        "4rem"}}
     [:div {:style {:grid-column "1/-1"}
            :class [:flex :w-full :items-end :p-1 :h-16]}
      (if (= 3 (count @field))
        (if-let [[username phone uid :as m] (user.database/lookup-by-litteralkeyid @field)]
          (do
            (reset! c-extra m)
            [sc/col-space-1 {:class [:truncate :h-full :justify-around]}
             [sc/ptitle1 phone]
             [sc/ptext {:class [:truncate]} username]])

          (do
            (reset! c-extra nil)
            [sc/ptitle
             (if (= 3 (count @field))
               (do
                 (reset! c-lookup-result nil)
                 (str @field " er ikke registrert")))]))
        (do
          (reset! c-extra nil)
          (if @c-litteral-key
            [sc/ptext "Bruk de siste 3 siffer av nøkkelnr. F.eks E18-"
             [:span.font-bold {:style {:color "var(--brand2)"}} "987"]]
            (f "Telefonnr"
               "eller de 3 siste sifre i nøkkelnr"))))]


     [:div {:style {:grid-column "1/4"
                    :overflow    :hidden}}
      [input-area 8 "Telefon– eller nøkkelnummer" :phone]]

     [:div
      {:style {:grid-column "4"
               :grid-row    "2"}}
      [delete-button st field]]]))

(defn- list-of-selected []
  [sc/col
   {:style {:border-radius         "var(--radius-0)"
            :column-gap            "var(--size-1)"
            :row-gap               "var(--size-1)"
            :display               :grid
            :grid-template-columns "repeat(4,1fr) "
            :grid-template-rows    "auto"}}
   (let [data (map :number @c-boat-list)
         m (or (mod (count data) 4) 0)]
     (doall (concat
              (for [{:keys [new number]} (reverse @c-boat-list)]
                [sc/badge {:class    [(when new :new)
                                      :truncate
                                      (when (equals-to @c-selected number) :selected)]
                           :on-click (fn []
                                       (if (= number @c-selected)
                                         (swap! st dissoc :selected)
                                         (reset! c-selected number)))}
                 (subs (str number) 0 3)])
              [(when (or (zero? (count data)) (not (= m 0)))
                 (repeat (- 4 m) [sc/badge {:class [:disabled]}]))])))])

(defn- info-panel [has-focus? {:keys [new number navn] :as boat}]
  [:div.p-1
   {:style (when (and has-focus? boat)
             {:background-color "var(--selected)"
              :color            "var(--selected-copy)"})
    :class [:flex :items-end :h-16]}
   (cond
     (some #{@c-textinput-boat} (map str (keep :number (filter :new @c-boat-list))))
     [f "Skriv et annet båtnr" (str @c-textinput-boat " finnes fra før i listen din")]

     (and (nil? boat) @c-textinput-boat (= 3 (count @c-textinput-boat)))
     [f (str @c-textinput-boat " finnes ikke") "Lage den nå?"]

     (nil? boat)
     [f "Båtnummer (3 siffer)" "Båtnr som ikke finnes blir laget"]

     new
     [f number navn]

     :else
     [sc/row-sc-g2 {:style {:width           "100%"
                            :padding-inline  "4px"
                            :justify-content :space-between}}
      [widgets/stability-name-category boat]
      (r/with-let [boat-type (:boat-type boat)
                   uid (rf/subscribe [:lab/uid])
                   bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
                   ex-data (db/on-value-reaction {:path ["users" @uid "starred" boat-type]})]
        #_[widgets/favourites-star
           {:bt-data       bt-data
            :ex-data       ex-data
            :on-flag-click (fn [boat-type value]
                             (rf/dispatch [:star/write-star-change
                                           {:boat-type boat-type
                                            :value     value
                                            :uid       @uid}]))}])]
     #_(if (or (:selected @st) boat)
         (do
           (if (:selected @st)
             (reset! c-lookup-result (lookup (:selected @st)))
             (reset! c-lookup-result boat))
           [:div.flex.justify-between.items-center.w-full
            [widgets/stability-name-category boat]
            (let [boat-type (:boat-type boat)
                  uid (rf/subscribe [:lab/uid])
                  bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
                  ex-data (db/on-value-reaction {:path ["users" @uid "starred" boat-type]})]
              [widgets/favourites-star
               {:bt-data       bt-data
                :ex-data       ex-data
                :on-flag-click (fn [boat-type value]
                                 (rf/dispatch [:star/write-star-change
                                               {:boat-type boat-type
                                                :value     value
                                                :uid       @uid}]))}])])

         [:div {:class [:flex :w-full :items-end :h-16]}
          [sc/ptitle1
           (if (= 3 (count @c-textinput-boat))
             (do
               (reset! c-lookup-result nil)
               (str @c-textinput-boat " er ikke registrert"))
             "Båtnummer (3 siffer)")]]))])

(defn selected-boats [st c-focus c-textinput-boats]
  (let [has-focus? (= :boats @c-focus)
        boat (lookup (or (:selected @st) @c-textinput-boats))]
    [sc/surface-ab
     {:on-click #(reset! c-focus :boats)
      :class    [:h-full (when has-focus? :focused) :overflow-clip]
      :style    {:display               :grid
                 :column-gap            "var(--size-4)"
                 :row-gap               "var(--size-4)"
                 :grid-template-columns "repeat(4,1fr)"}}
     [sc/co {:class [:col-span-4]
             :style {:grid-column "1/-1"
                     :grid-row    "1/2"}}
      [info-panel has-focus? (or boat
                                 (if @c-selected
                                   {:new    true
                                    :number @c-selected
                                    :navn   "Ny båt"}
                                   nil))]

      [:div {:style {:width                 :100%
                     :column-gap            "var(--size-2)"
                     :display               :grid
                     :grid-template-columns "repeat(4,1fr)"}}
       (if (and boat (= 3 (count @c-textinput-boats)))
         [add-button st c-textinput-boats boat]
         (if (= 3 (count @c-textinput-boats))
           [question-button st c-textinput-boats]
           [add-button st c-textinput-boats c-lookup-result]))
       [:div {:style {:grid-column "2/4"}}
        [input-area 3 "Båtnummer" :boats]]
       [:div.flex.items-center.justify-center
        {:style {:grid-column "4"}}
        [delete-button st c-textinput-boats]]]
      [list-of-selected]]]))

(defn move-to-prev [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [bis/push-button
     {
      :enabled  ok?
      :disabled true
      :class    [:disabled]
      :styles   (when ok? {:color      "var(--gray-1)"
                           :background "var(--gray-6)"})}
     (sc/icon-huge ico/prevImage)]))

(defn move-to-next [st]
  (let [ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (:list @st)))
                     (empty? (:item @st))))]

    [bis/push-button
     {
      :enabled  ok?
      :disabled true

      :styles   (when ok? {:color      "var(--gray-1)"
                           :background "var(--gray-6)"})}
     (sc/icon-huge ico/nextImage)]))

(defn complete [st]
  (let [ok? (rf/subscribe [::completed])]
    [bis/push-button
     {:on-click #(when @(rf/subscribe [::completed])
                   (actions/confirm-command st)
                   (cmd/reset-command st))
      :disabled (not @ok?)
      :class    [:add]
      :style    (conj {;:background "var(--selected)"
                       :border-radius "var(--radius-0)"
                       :width         "100%"
                       :height        "100%"}

                      #_(when @ok?
                          {:color      "var(--selected-copy)"
                           :background "var(--green-6)"}))}
     [sc/row-sc-g2
      (sc/icon-huge ico/check)]]))

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

(comment
  (do
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

(defn keydown-f [event]
  (let [kc (.-keyCode event)
        active (focused-field c-focus)]
    (tap> kc)
    (cond
      (= kc keycodes/S) (when @(rf/subscribe [::completed])
                          (actions/confirm-command st))
      (= kc keycodes/R) (cmd/reset-command st)
      (= kc keycodes/N) (cmd/litteral-key-command c-litteral-key)
      (= kc keycodes/O) (cmd/moon-command c-moon)
      (= kc keycodes/F) (cmd/decrease-adults c-adults)
      (= kc keycodes/V) (cmd/increase-adults c-adults)
      (= kc keycodes/G) (cmd/decrease-children c-children)
      (= kc keycodes/B) (cmd/increase-children c-children)
      (= kc keycodes/ENTER) (if (and (= 3 (count @active))
                                     (number-not-in-list @active @c-boat-list))
                              (let [l (lookup @active)]
                                (if (some? l)
                                  (cmd/add-boat st active l)
                                  (cmd/new-boat c-boat-list c-selected active))))
      (= kc keycodes/DELETE) (cmd/delete-clicked st active) ;only from keyboard
      (= kc keycodes/BACKSPACE) (cmd/backspace-clicked st active)
      (some #{kc} (range 48 58)) (key-clicked active (- kc 48)))))

;region validation

(defn sum-users
  "reducing"
  [a e]
  (+ a (let [n (or (some-> e js/parseInt) 0)
             n (if (js/isNaN n) 0 n)]
         (if (number? n) n 0))))

(rf/reg-sub ::completed-users
            :-> #(pos? (reduce sum-users 0 [@c-adults @c-children @c-juveniles])))

(rf/reg-sub ::completed-contact
            :-> #(or (and (= 8 (count @c-textinput-phone))
                          (= @c-textinput-phone
                             (str (js/parseInt @c-textinput-phone)))
                          (or @c-litteral-key (nil? @c-extra)))
                     (and (= 3 (count @c-textinput-phone))
                          (some? @c-extra))))

(rf/reg-sub ::completed-boats
            :-> #(seq @c-boat-list))

(rf/reg-sub ::completed (fn [_]
                          (and (empty? @c-textinput-boat)
                               @(rf/subscribe [::completed-contact])
                               @(rf/subscribe [::completed-users])
                               @(rf/subscribe [::completed-boats]))))

;endregion

(defn boatpanel-window [datas]
  (let [ref (r/atom nil)
        geo (rf/subscribe [:lab/screen-geometry])
        user-uid (rf/subscribe [:lab/uid])
        nøkkelnummer (subs (str (:nøkkelnummer (user.database/lookup-userinfo @user-uid))) 4 7)
        ipad? (= @user-uid
                 @(db/on-value-reaction {:path ["system" "active"]}))]
    (r/create-class
      {:component-did-update
       (fn [this old-argv old-state snapshot]
         (tap> {:component-did-update this
                :old-argv             old-argv
                :old-state            old-state
                :datas                datas
                :snapshot             snapshot}))

       :component-did-mount
       (fn [_]
         (if-let [[k v] datas]
           (do
             (reset! c-adults (:adults v 0))
             (reset! c-juveniles (:juveniles v 0))
             (reset! c-children (:children v 0))
             (reset! c-moon (:moon v))
             (reset! c-litteral-key (:havekey v))
             (reset! c-textinput-phone (:phone v))
             (reset! c-boat-list
                     (mapv (fn [[k _v]]
                             (if-some [r (lookup-id k)]
                               (select-keys r [:description :navn :number :kind :id])
                               {:new    true
                                :number (name k)})) (:list v)))
             (reset! c-moon (:moon v)))

           (if ipad?
             (do
               (reset! c-textinput-phone nil))
             (do
               (reset! c-textinput-phone nøkkelnummer)
               (reset! c-focus (if ipad? :phone :boats))))))

       :reagent-render
       (fn [datas]
         (let [{:keys [right-menu? mobile?]} @geo]
           [sc/row
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
             {:style {:padding "var(--size-2)"}
              :class [(cond
                        right-menu? :right-side
                        ;mobile? :mobile
                        :else :left-side)]}

             (doall (concat
                      (let [f (fn [[area-name component]]
                                [:div {:style {:grid-area area-name}} component])
                            pointer (fn [complete?]
                                      [:div.flex.items-center.justify-center
                                       {:class [:h-full]}
                                       [sc/icon-large
                                        {:style (conj {:color "var(--text1)"}
                                                      (when-not complete?
                                                        {;:animation-duration        "2s"
                                                         ;:animation-iteration-count "infinite"
                                                         ;:animation-delay           "4s"
                                                         :animation "2s var(--animation-shake-x) 2s infinite"}))}
                                        (if complete?
                                          ico/check
                                          (if right-menu?
                                            ico/arrowRight'
                                            ico/arrowLeft'))]])]
                        (mapv f [["child" [children-slider c-children]]
                                 ["juvenile" [juveniles-slider c-juveniles]]
                                 ["moon" [moon-toggle c-moon]]
                                 ["key" [litteralkey-toggle c-litteral-key]]
                                 ["adult" [adults-slider c-adults]]
                                 ["aboutyou" [hvem-er-du st c-focus c-textinput-phone]]
                                 ["boats" [selected-boats st c-focus c-textinput-boat]]
                                 ["numpad" [number-pad st]]
                                 ["check-a" (pointer @(rf/subscribe [::completed-users]))]
                                 ["check-b" (pointer @(rf/subscribe [::completed-contact]))]
                                 ["check-c" (pointer (pos? (count @c-boat-list)))]
                                 ["complete" [complete st]]
                                 #_["prev" [move-to-prev st]]
                                 #_["next" [move-to-next st]]]))))]]))})))

(defn window-content
  ([m]
   (window-content nil))
  ([{:keys [on-close]} args]
   [:div
    {:style {:overflow-y :auto
             :width      "auto"
             :max-height "90vh"}}

    [boatpanel-window args]]))

(rf/reg-event-fx :lab/toggle-boatpanel
                 (fn [_ [_ args]]
                   {:fx [[:dispatch
                          [:modal.boatinput/show
                           {:on-primary-action #(rf/dispatch [:modal.boatinput/clear])
                            :mode              (when args :edit)
                            :content-fn        (fn [e] (if args
                                                         (window-content e args)
                                                         (window-content e nil)))}]]]}))

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

(defn render-boatinput
  "centered dialog used by this component"
  []
  (let [{:keys [context vis close]} {:context @(rf/subscribe [:modal.boatinput/get-context])
                                     :vis     (rf/subscribe [:modal.boatinput/is-visible])
                                     :close   #(rf/dispatch [:modal.boatinput/close])}
        {:keys [action on-primary-action click-overlay-to-dismiss content-fn mode]
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
           {:style {:z-index 2000}
            :class [:w-auto :top-0 :h-auto (cond
                                             @mobile? :inset-0
                                             @right-side? :right-12
                                             :else :left-12)]}
           [:div.text-center
            [schpaa.style.dialog/standard-overlay]
            [:span.inline-block.h-screen.align-middle
             (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
            [ui/transition-child
             {:style       (conj
                             (cond
                               (= mode :edit) {:box-shadow
                                               (apply str (interpose ","
                                                                     [#_"0 0 0px calc(var(--size-2) * 1) var(--blue-9)"
                                                                      #_"0 0 0px calc(var(--size-2) * 2) var(--blue-8)"
                                                                      "0 0 0px calc(var(--size-2) * 1) var(--blue-7)"
                                                                      "0 0 0px calc(var(--size-2) * 2) var(--toolbar-)"
                                                                      #_"0 0 0px calc(var(--size-2) * 4) var(--blue-6)"]))}

                               :else {:box-shadow (apply str (interpose ","
                                                                        ["0 0 0px calc(var(--size-2) * 1) var(--brand1)"
                                                                         "0 0 0px calc(var(--size-1) * 3) var(--toolbar-)"
                                                                         "var(--shadow-5)"]))
                                      :outline    :none
                                      :border     :none})
                             {:border-radius    "var(--size-2)"
                              :background-color "var(--toolbar)"})
              :class       [:inline-block :align-middle :text-left :transform
                            (o/classname sc/inner-dlg)]
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

