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
            [db.core :as db]
            [schpaa.debug :as l]))

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
;(def c-lookup-result (r/cursor st [:lookup-results]))
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

(defn input-area
  "caps size of input to length"
  [length caption fieldname]
  (let [c-field (r/cursor st [:textfield fieldname])]
    (reset! c-field (subs (str @c-field) 0 length))
    (let [has-focus? (= @c-focus fieldname)
          textinput (fn [v]
                      [:div.inline-flex.items-end.justify-center.h-full
                       [bis/input-caption v (when has-focus? [:span.blinking-cursor "|"])]])]
      [:div.relative.h-16.shrink-0
       {:on-click #()
        :style    {:padding-inline   "var(--size-2)"
                   :border-radius    "var(--radius-0)"
                   :background-color (if has-focus? "var(--field-focus)" "var(--field)")
                   :color            (if has-focus? "var(--fieldcopy)" "var(--text1)")
                   :box-shadow       "var(--inner-shadow-0)"}}
       [:div.absolute.top-px.left-2
        [sc/small {:style {:font-weight "var(--font-weight-5)"}} caption]]
       (textinput (subs (str @c-field) 0 length))])))

(defn lookup [boatnumber]
  ;todo define as defonce
  (let [data @(rf/subscribe [:rent/lookup])]
    (get data boatnumber)))

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
              :height    "100%"
              :overflow  :hidden}}
     [:div {:class [:flex :h-16 :items-end]} content]
     [:div.inset-x-0.inset-y-4.absolute.flex.flex-col.justify-between
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
               [:img.h-10.object-fit.-ml-px {:src "/img/human.png"}]]
    :value    (or @c 0)
    :increase #(cmd/increase-children c)
    :decrease #(cmd/decrease-children c)}])

(defn juveniles-slider [c]
  [up-down-button
   {:content  [:div.flex.items-end.h-full
               [:img.h-14.object-fit.-ml-px {:src "/img/teen.png"}]]

    :value    (or @c 0)
    :increase #(cmd/increase-juveniles c)
    :decrease #(cmd/decrease-juveniles c)}])

(defn adults-slider [c]
  [up-down-button
   {:content  [:div.flex.items-end.h-full
               [:img.h-16.object-fit {:src "/img/human.png"}]]
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

(defn plus-button [st source]
  [bis/push-button
   {:class    [:add2]
    :on-click #(cmd/new-boat c-boat-list c-selected source)
    :value    true
    :disabled (and                                          ;(not (number-not-in-list @source @c-boat-list))
                (not (= 3 (count @source))))}
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
     (doall (for [e '[7 8 9 4 5 6 1 2 3 :add 0 :del]]
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
                    :style    {:color (if (not (empty? @active-field)) "var(--orange-6)" "var(--text2)")}
                    :enabled  (not (empty? @active-field))}
                   [sc/icon-huge ico/backspace]]

                  (= :add e)
                  [plus-button st c-textinput-boat]

                  #_(= :toggle e
                       [bis/push-button
                        {:on-click #(swap! st update :phone (fnil not false))}
                        (if (:phone @st)
                          [sc/icon-large ico/tag]
                          [sc/icon-large ico/phone])

                        :else [:div]])))))]))

;endregion

(defn f [a & b]
  [sc/co
   [sc/ptitle1 a]
   (when b [sc/ptext b])])

;endregion

;region components

(defn hvem-er-du [st c-focus c-textinput-phone]
  (let [field c-textinput-phone
        has-focus? (= :phone @c-focus)]
    [sc/surface-p
     {:on-click #(reset! c-focus :phone)
      :class    [(when has-focus? :focus) :relative]
      :style    {

                 ;:margin-left           "-0.5rem"
                 :padding-block         "0.5rem"
                 :padding-left          "0.5rem"
                 :padding-right         "0rem"
                 :display               :grid
                 ;:column-gap            "var(--size-2)"
                 :row-gap               "var(--size-2)"
                 :grid-template-columns "repeat(4,1fr)"
                 :grid-auto-rows        "4rem"}}
     #_[:div {:style {:grid-column "1/-1"}
              :class [:flex :w-full :items-end :p-1 :h-16]}
        #_(if (= 3 (count @field))
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
                (f "Telefonnr"
                   #_"eller de 3 siste sifre i nøkkelnr")
                #_[sc/ptext "Bruk de siste 3 siffer av nøkkelnr. F.eks E18-"
                   [:span.font-bold {:style {:color "var(--brand2)"}} "987"]]
                (f "Telefonnr"
                   #_"eller de 3 siste sifre i nøkkelnr"))))]


     [:div {:style {:grid-column "1/4"
                    :overflow    :hidden}}
      [input-area 8 "Tlf– eller nøkkelnr (3 siste siffer)" :phone]]

     [:div
      {:style {:grid-column "4"
               :grid-row    "1"}}
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
  [:div.pt-1
   {:style (conj {:grid-column "2/-1"
                  :width       "100%"
                  :overflow    :clip}
                 #_(when (and has-focus? boat)
                     {:background-color "var(--selected)"
                      :color            "var(--selected-copy)"}))
    :class [:flex :items-end :h-16x]}
   [f "Båtnummer (3 siffer)" #_"Båtnr som ikke finnes blir laget"]
   #_(cond
       (some #{@c-textinput-boat} (map str (keep :number (filter :new @c-boat-list))))
       [f "Skriv et annet båtnr" (str @c-textinput-boat " finnes fra før i listen din")]

       ;(and (nil? boat) @c-textinput-boat (= 3 (count @c-textinput-boat)))
       ;[f (str @c-textinput-boat " finnes ikke") "Lage den nå?"]

       (nil? boat)
       [f "Båtnummer (3 siffer)" #_"Båtnr som ikke finnes blir laget"]

       ;new
       ;[f number navn]

       ;:else
       #_[sc/row-sc-g2 {:class [:truncate]
                        :style {:width           "auto"
                                :padding-inline  "4px"
                                :justify-content :space-between}}]
       ;[widgets/stability-name-category boat]
       #_(r/with-let [boat-type (:boat-type boat)
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
                                               :uid       @uid}]))}])
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

(defn boat-input [st c-focus c-textinput-boats]
  (let [has-focus? (= :boats @c-focus)
        boat (lookup (or (:selected @st) @c-textinput-boats))]
    [sc/surface-p
     {:on-click #(reset! c-focus :boats)
      :class    [:top
                 :col-span-4
                 (when has-focus? :focus)]
      :style    {;:margin-left           "-0.25rem"
                 :padding-block         "0.5rem"
                 :padding-left          "0.5rem"
                 :padding-right         "0.5rem"

                 :display               :grid
                 :width                 "auto"
                 :column-gap            "var(--size-2)"
                 ;:row-gap               "var(--size-2)"
                 :grid-template-columns "repeat(4,1fr)"}}

     [sc/co {:style {:padding     0
                     :margin      0
                     :grid-column "1/-1"
                     :grid-row    "2"}}
      [:div {:style {:width                 "100%"
                     :column-gap            "0.25rem"
                     :row-gap               "0.25rem"
                     :display               "grid"
                     :grid-template-columns "repeat(4,1fr)"}}
       [:div {:style {:grid-column "1/3"}}
        [input-area 3 "Båtnummer" :boats]]

       #_(if (and boat (= 3 (count @c-textinput-boats)))
           [add-button st c-textinput-boats boat]
           (if (= 3 (count @c-textinput-boats))
             [question-button st c-textinput-boats]
             [add-button st c-textinput-boats c-lookup-result]))
       [:div.flex.items-center.justify-center
        {:style {:grid-column "4"}}
        [delete-button st c-textinput-boats]]]
      #_[list-of-selected]]]))

(defn selected-boats [st c-focus c-textinput-boats]
  (let [has-focus? false;(= :boats @c-focus)
        boat (lookup (or (:selected @st) @c-textinput-boats))]
    [sc/surface-p
     {:on-click #(reset! c-focus :boats)
      :class    [:col-span-4
                 :bottom :top
                 (when has-focus? :focus)]
      :style    {;:margin-left           "-0.25rem"
                 :padding-block         "0.5rem"
                 :padding-left          "0.5rem"
                 :padding-right         "0.5rem"
                 :display               :grid
                 :column-gap            "var(--size-2)"
                 ;:row-gap               "var(--size-2)"
                 :grid-template-columns "repeat(4,1fr)"}}
     [:div
      {:style {:grid-column "1/-1"
               :grid-row    "2"}}
      [list-of-selected]]]))

(defn await [st]
  (let [ok? (rf/subscribe [::partial-complete])]
    [bis/push-button
     {:on-click #(js/alert "put registration on hold, clear the form") #_#(when @(rf/subscribe [::completed])
                                                                            (actions/confirm-command st)
                                                                            (cmd/reset-command st))
      :disabled (not @ok?)
      :class    [:await]
      :style    (conj {:border-radius "var(--radius-0)"
                       :width         "100%"
                       :height        "100%"})}

     [sc/row-sc-g2
      [:div {:style {:transform "rotate(-90deg)"}} (sc/icon-huge ico/storeForLater)]]]))

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

(defn preview-phone []
  [:div.flex.items-end.h-full
   (if (= 3 (count @c-textinput-phone))
     (if-let [[username phone uid :as m] (user.database/lookup-by-litteralkeyid @c-textinput-phone)]
       (do
         (reset! c-extra m)
         [sc/col-space-1 {:class [:truncate :justify-around]}
          [sc/ptitle1 phone]
          [sc/ptext {:class [:truncate]} username]]))
     (reset! c-extra nil))])

(defn preview-boatinfo []
  (let [admin? (rf/subscribe [:lab/admin-access])
        boatnumber (cond
                     (= 3 (count @c-textinput-boat)) @c-textinput-boat
                     (= 3 (count @c-selected)) @c-selected)

        #_#_data (some-> id lookup-id)]
    ;(tap> {:data data})
    (when boatnumber
      (when-let [{:keys [id] :as data} (some-> boatnumber lookup)]
        ;[l/pre id]

        [:div
         {:style {:min-width "0"
                  :padding       "0.25rem"
                  :box-shadow    "none"
                  :border-radius "var(--radius-0)"
                  :height        "100%"}}


         [sc/col
          [sc/row-sc-g2 {:style {:width "100%"
                                 :align-items "center"}}
           [widgets/badge {:class ['small]} (:number data) (:slot data)]
           [widgets/stability-name-category'
            {:hide-flag? true
             :reversed?  false
             :url?       @admin?
             :k          id}
            data]]
          ;[widgets/stability-name-category (assoc data :hide-flag? true)]
          ;[l/pre data]
          [sc/title (:description data)]]
         #_[:div.h-full {:class [:truncate :-debug]}
            [sc/co {:class []}
             [sc/row-sc {:class [:truncate :h-full]}
              [widgets/badge {:class ['small]} (:number data) (:slot data)]
              [:div.truncate "adjklasdjklasjkldjklasdjklasdjkl"]
              [widgets/stability-name-category'
               {:hide-flag? true
                :reversed?  true
                :url?       @admin?
                :k          id}
               data]]
             ;[widgets/stability-name-category (assoc data :hide-flag? true)]
             [sc/title (:description data)]]]]))))

(defn status []
  [sc/surface-b
   {:class [(if (or (and (= :boats @c-focus)
                         @c-boat-list
                         @c-selected)
                    (and (= :phone @c-focus)
                         (some? @c-extra))) :focus)]
    :style {:padding       "0.5rem"
            :height        "100%"
            :overflow-y    "auto"
            :overflow-x "hidden"
            :border-radius "var(--radius-0)"}}
   ;[sc/text "this is a very long text that spans several lines"]
   (if (= :phone @c-focus)
     [preview-phone]
     [preview-boatinfo])])

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

(rf/reg-sub ::partial-complete (fn [_]
                                 (or (seq? @c-textinput-boat)
                                     @(rf/subscribe [::completed-contact])
                                     @(rf/subscribe [::completed-users])
                                     @(rf/subscribe [::completed-boats]))))

(rf/reg-sub ::completed (fn [_]
                          (and (empty? @c-textinput-boat)
                               @(rf/subscribe [::completed-contact])
                               @(rf/subscribe [::completed-users])
                               @(rf/subscribe [::completed-boats]))))

;endregion

(defn set-boatpanel-fields [v]
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
    (reset! c-moon (:moon v))))

(defn boatpanel-window [datas]
  (let [ref (r/atom nil)
        geo (rf/subscribe [:lab/screen-geometry])
        user-uid (rf/subscribe [:lab/uid])
        nøkkelnummer (subs (str (:nøkkelnummer (user.database/lookup-userinfo @user-uid))) 4 7)
        ipad? (= @user-uid
                 @(db/on-value-reaction {:path ["system" "active"]}))]
    (r/create-class
      {:component-did-update
       (fn [this old-argv old-state snapshot])

       :component-did-mount
       (fn [_]
         (if-let [[_k v] datas]
           (set-boatpanel-fields v)
           (if ipad?
             (reset! c-textinput-phone nil)
             (do
               (reset! c-textinput-phone nøkkelnummer)
               (reset! c-focus (if ipad? :phone :boats))))))

       :reagent-render
       (fn [datas]
         (let [{:keys [right-menu? mobile?]} @geo
               layout-fn (fn [[area-name component]]
                           [:div {:style {:grid-area area-name}} component])
               pointer (fn pointer [complete?]
                         [:div.h-full
                          {:style (conj {:border-left "8px solid"}
                                        (when-not complete?
                                          {:border-color "var(--red-6)"}))}])
               recall-mode? false]
           [sc/row
            {:tab-index 0
             ;note:
             :style     (when-not mobile? {:height "calc(100dvh - 6rem)"})
             :class     (cond-> [:outline-none]
                          mobile? (conj [:h-full :flex :flex-col :justify-end])
                          (not mobile?) (conj [:pb-2 :focus:outline-none]))
             :ref       (fn [e]
                          (when-not @ref
                            (.addEventListener e "keydown" keydown-f)
                            (.focus e)
                            (reset! ref e)))}
            [bis/panel
             {:class [(if right-menu? :right :left) (if recall-mode? :alt)]}
             (map layout-fn
                  (if recall-mode?
                    [["in" [boat-input st c-focus c-textinput-boat]]
                     ["st" [status]]
                     ["np" [number-pad st]]
                     ["re" [reset-button st]]
                     ["aw" [await st]]
                     ["co" [complete st]]]
                    [["in" [boat-input st c-focus c-textinput-boat]]

                     ["ch" [children-slider c-children]]
                     ["ju" [juveniles-slider c-juveniles]]
                     ["mo" [moon-toggle c-moon]]
                     ["ky" [litteralkey-toggle c-litteral-key]]
                     ["ad" [adults-slider c-adults]]
                     ["ay" [hvem-er-du st c-focus c-textinput-phone]]

                     ["bo" [selected-boats st c-focus c-textinput-boat]]

                     ["st" [status]]

                     ["a1" (pointer (pos? (count @c-boat-list)))]
                     ["b1" (pointer @(rf/subscribe [::completed-contact]))]
                     ["c1" (pointer @(rf/subscribe [::completed-users]))]
                     ["d1" (pointer @(rf/subscribe [::partial-complete]))]
                     ["e1" (pointer @(rf/subscribe [::completed]))]

                     ["np" [number-pad st]]

                     ["re" [reset-button st]]
                     ["aw" [await st]]
                     ["co" [complete st]]]))]]))})))

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

(rf/reg-event-fx :lab/set-boatpanel-data
                 (fn [_ [_ args]]
                   (let [[k v] args]
                     (tap> v)
                     (set-boatpanel-fields v))
                   #_{:fx [[:dispatch
                            [:lab/set-boatpanel-data args]
                            #_[:modal.boatinput/show
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

