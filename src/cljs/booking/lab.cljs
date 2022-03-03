(ns booking.lab
  (:require [reagent.core :as r]
            [kee-frame.core]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.style.booking]
            [booking.data]
            [schpaa.style.popover]
            [db.core :as db]
            [booking.content.blog-support :refer [err-boundary]]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [schpaa.style.menu]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [booking.common-views :refer [page-boundry main-menu]]
            [schpaa.debug :as l]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog]
            [booking.views]
            [clojure.set :as set]))

(rf/reg-event-db :lab/show-popover (fn [db]
                                     (tap> (:lab/show-popover db))
                                     (update db :lab/show-popover (fnil not false))))
(rf/reg-sub :lab/show-popover (fn [db] (get db :lab/show-popover false)))

(defn popover-sample []
  (let [visible (rf/subscribe [:lab/show-popover])
        data [{:id      1
               :content [schpaa.style.booking/line
                         {:content  {:category    "Havkayakk"
                                     :number      "310"
                                     :location    "C3"
                                     :material    "Epoxy"
                                     :stability   4
                                     :description "Passer best for de som liker å padle på vann og land."
                                     :brand       "P3 Baffin Boreal"
                                     :weight      "23 kg"
                                     :width       "50 cm"
                                     :length      "490 cm"}

                          :on-click #()
                          :expanded true
                          :selected false}]}]]
    (fn []
      [:div.relative
       [schpaa.style.button/normal {:type     "button"
                                    :on-click #(do (rf/dispatch [:lab/show-popover]))} "Display popover"]
       [:div.z-20.absolute.max-w-sm.pointer-events-none.left-0.top-12.mt-2.w-full.h-full
        [schpaa.style.popover/popover-example @visible data]]])))



(defn time-input-validation [e]
  {:start-date (cond-> nil
                 (empty? (:start-date e)) ((fnil conj []) "mangler")
                 (and (not (empty? (:start-date e)))
                      (some #{(tick.alpha.interval/relation
                                (t/date (:start-date e)) (t/date))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))

   :start-time (cond-> nil
                 (empty? (:start-time e)) ((fnil conj []) "mangler")
                 (and (not (empty? (:start-date e)))
                      (not (empty? (:start-time e)))
                      (some #{(tick.alpha.interval/relation
                                (t/at (t/date (:start-date e)) (t/time (:start-time e)))
                                (t/date-time (t/now)))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))
   :end-time   (cond-> nil
                 (empty? (:end-time e)) ((fnil conj []) "mangler")
                 (and (not (empty? (:start-date e)))
                      (not (empty? (:end-time e)))
                      (some #{(tick.alpha.interval/relation
                                (t/at (t/date (:end-date e)) (t/time (:end-time e)))
                                (t/at (t/date (:start-date e)) (t/time (:start-time e))))} [:precedes :meets])) ((fnil conj []) "er før 'fra kl'"))})

(defn standard-menu-2 [data]
  [[(sc/icon-large [:> solid/TrashIcon]) "Fjern alle" #()]
   [(sc/icon-large [:> solid/CursorClickIcon]) "Siste valg" #()]
   nil
   [(sc/icon-large [:> solid/CogIcon]) "Book nå" #()]])

(defn complex-menu [settings]
  (let [show-1 #(swap! settings assoc :setting-1 %)
        select-2 #(swap! settings assoc :setting-2 %)]
    (concat [[(fn [v] (sc/icon-large (when v [:> solid/ShieldCheckIcon])))
              (fn [e] (if e "Long" "Short"))
              #(show-1 (not (:setting-1 @settings)))
              false
              #(:setting-1 @settings)]]
            [nil]
            (let [data [["small" 1]
                        ["medium" 2]
                        ["large" 3]]]
              (map (fn [[caption value]]
                     [(fn [v] (sc/icon-large (when (= value v) [:> outline/CheckIcon])))
                      caption
                      #(select-2 value)
                      false
                      #(:setting-2 @settings)])
                   data))
            [nil]
            [[(sc/icon-large [:> solid/BadgeCheckIcon])
              "Badge"
              nil
              true
              #()]
             [(sc/icon-large [:> solid/LightBulbIcon])
              "Bulba?"
              nil
              true
              #()]])))

(defn timeinput-shortcuts-definition [settings-atom]
  [[:header [sc/row {:class [:justify-between :items-end]}
             [sc/title "Top"]
             [sc/pill (or booking.data/VERSION "dev.x.y")]]]
   [:menuitem [(sc/icon-large [:> solid/BadgeCheckIcon])
               "Badge"
               nil
               true
               #()]]
   [:footer [sc/row-end {:class [:gap-4]} [sc/small "Terms"] [sc/small "Privacy"]]]])

(defn time-input-form []
  (r/with-let [lightning-visible (r/atom nil)]
    (let [toggle-lightning #(swap! lightning-visible (fnil not false))]
      [fork/form {:initial-values    {:start-date  (str (t/new-date))
                                      :start-time  (str (t/truncate (t/>> (t/time) (t/new-duration -2 :hours)) :hours))
                                      ;todo adjust for 22-23
                                      ;:end-time    (str (t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                      :end-date    (str (t/new-date))
                                      :id          0
                                      :description ""}
                  :form-id           "sample-form"
                  :prevent-default?  true
                  :state             (r/atom {})
                  :clean-on-unmount? true
                  :keywordize-keys   true
                  :on-submit         (fn [e] (tap> e))
                  :validation        time-input-validation}

       (fn [{:keys [errors form-id handle-submit handle-change values set-values] :as props}]
         [sc/surface-a {:class [:p-4 :min-w-fit]}
          [sc/col {:class [:space-y-4]}

           #_[:div.z-50.mb-10x
              [schpaa.style.menu/naked-menu-example-with-args
               {:dir    :down
                :data   (timeinput-shortcuts-definition (r/atom nil))
                :button (fn [open]
                          [scb/corner {:on-click toggle-lightning} [sc/icon [:> outline/LightningBoltIcon]]])}]]

           [sc/row
            [:form.space-y-1.w-full
             {:id        form-id
              :on-submit handle-submit}
             [sc/row {:class [:shrink-0]}
              [booking.views/time-input props false]
              [:div.relative.absolute.top-1.right-1.px-2.z-500]]]]]])])))


(def type-data
  [{:type        5000
    :category    "Grønnlandskayakk"
    :material    "Epoxy"
    :stability   4
    :description "Ufattelig lang beskrivelse som går over flere linjer og som sier en hel drøss om hva dette er godt for og at du burde prøve den!"
    :brand       "Rebel Naja"
    :weight      "33 kg"
    :width       "50 cm"
    :length      "490 cm"}
   {:type      3100
    :category  "Havkayakk"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}
   {:type      3200
    :category  "Tomahawk"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}
   {:type      4100
    :category  "Testthing"
    :material  "Plast"
    :stability 1
    ;:description ""
    :brand     "P3 Baffin Boreal"
    ;:weight      "23 kg"
    :width     "50 cm"
    :length    "490 cm"}])

(def card-data-v2
  [{:id 100 :loc "C3" :number "310" :type 5000}
   {:id 102 :loc "C4" :number "313" :type 5000}
   {:id 210 :loc "A2" :number "439" :type 3100}
   {:id 211 :loc "A2" :number "439" :type 3200}
   {:id 212 :loc "A2" :number "212" :type 3200}
   {:id 412 :loc "A2" :number "412" :type 4100}
   {:id 413 :loc "A2" :number "413" :type 4100}
   {:id 414 :loc "A2" :number "414" :type 4100}
   {:id 415 :loc "A2" :number "415" :type 4100}])

(comment
  (do
    (group-by :type (card-data-v2))))

(def card-data
  [{:content  {:category    "Grønnlandskayakk"
               :number      "600"
               :location    "E5"
               :material    "Epoxy"
               :stability   4
               :description "Ufattelig lang beskrivelse som går over flere linjer og som sier en hel drøss om hva dette er godt for og at du burde prøve den!"
               :brand       "Rebel Naja"
               :weight      "33 kg"
               :width       "50 cm"
               :length      "490 cm"}

    :on-click #()
    :expanded true
    :selected false}
   {:content  {:category  "Havkayakk"
               :number    "310"
               :location  "C3"
               :material  "Plast"
               :stability 1
               ;:description ""
               :brand     "P3 Baffin Boreal"
               ;:weight      "23 kg"
               :width     "50 cm"
               :length    "490 cm"}

    :on-click #()
    :expanded true
    :selected false}
   {:content  {:category    "Havkayakk"
               :number      "310"
               :location    "C3"
               :material    "Epoxy"
               :stability   4
               :description "Passer best for de som liker å padle på vann og land."
               :brand       "P3 Baffin Boreal"
               :weight      "23 kg"
               :width       "50 cm"
               :length      "490 cm"}

    :on-click #()
    :expanded true
    :selected false}])



(rf/reg-sub :lab/modal-example-dialog :-> (fn [db] (get db :lab/modal-example-dialog false)))

(rf/reg-event-db :lab/modal-example-dialog (fn [db [_ arg]] (if arg
                                                              (assoc db :lab/modal-example-dialog arg)
                                                              (update db :lab/modal-example-dialog (fnil not true)))))

(rf/reg-sub :lab/modal-example-dialog2 :-> (fn [db] (get db :lab/modal-example-dialog2 false)))

(rf/reg-event-db :lab/modal-example-dialog2 (fn [db [_ arg]] (if arg
                                                               (assoc db :lab/modal-example-dialog2 arg)
                                                               (update db :lab/modal-example-dialog2 (fnil not true)))))

(defn render [r]
  (let [mmm (rf/subscribe [:lab/modal-example-dialog])
        user-auth @(rf/subscribe [::db/user-auth])]
    (r/with-let [settings (r/atom {:setting-1 false
                                   :setting-2 1
                                   :selection #{2 5}})]
      [:<>
       [:div.p-2.space-y-4.relative
        ;[l/ppre-x @mmm @(rf/subscribe [:lab/modal-example-dialog2])]

        [schpaa.style.dialog/modal-example-with-timeout
         (rf/subscribe [:lab/modal-example-dialog2])
         #(rf/dispatch [:lab/modal-example-dialog2 false])
         [:<>
          [sc/title-p "Autoclosing message"]
          [sc/text-p "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]]

        [schpaa.style.dialog/modal-example
         (rf/subscribe [:lab/modal-example-dialog])
         #(rf/dispatch [:lab/modal-example-dialog false])]

        ;[popover-sample]

        [:div.sticky.top-0.z-50
         [time-input-form]]

        #_[sc/grid-wide {:class [:gap-1 :place-content-center]}
           (map schpaa.style.booking/line (map #(assoc % :expanded true) (flatten (repeat 11 card-data))))]

        (r/with-let [state (r/atom {:expanded #{5000}
                                    :selected #{413 414}})]
          [sc/grid-wide {:class [:gap-2 :place-content-center]}
           (doall (for [[type data] (group-by :type card-data-v2)]
                    [sc/surface-e {:class [:p-2]}
                     [:div.space-y-2
                      [schpaa.style.booking/collapsable-type-card
                       {:on-click #(swap! state update :expanded (fn [e] (if (some #{type} e)
                                                                           (set/difference e #{type})
                                                                           (set/union e #{type}))))
                        :expanded (some #{type} (:expanded @state))
                        :content  (first (get (group-by :type type-data) type))}]
                      (for [{:keys [id] :as each} data]
                        [:div.pl-4x [schpaa.style.booking/line-with-graph
                                     {:selected (some #{id} (:selected @state))
                                      :content  each
                                      :on-click #(swap! state update :selected (fn [e] (if (some #{id} e)
                                                                                         (set/difference e #{id})
                                                                                         (set/union e #{id}))))}]])]]))])]])))


(comment
  #_[:div.px-4.space-y-4
     [sc/grid-wide
      (doall (for [e (range 5)]
               [schpaa.style.booking/line {:content  {:category "Grønnlandskayakk"
                                                      :number   e
                                                      :location (str "A" e)}
                                           :on-click #(if (some #{e} (:selection @settings))
                                                        (swap! settings update :selection set/difference #{e})
                                                        (swap! settings update :selection set/union #{e}))
                                           :expanded (= 2 (:setting-2 @settings))
                                           :selected (some #{e} (:selection @settings))}]))]

     (let [section [:div.p-4.space-y-4
                    [:div.gap-1.flex.gap-4.flex-wrap

                     (scb/round-normal [:> solid/HeartIcon])
                     (scb/round-cta [:> solid/HeartIcon])
                     (scb/round-danger [:> solid/HeartIcon])

                     [scb/normal "normal button"]
                     [scb/button-cta "cta button"]
                     [scb/button-danger "danger button"]]

                    [sc/col
                     [sc/hero-p "Some Title Here"]
                     [sc/title-p "Some Title Here"]
                     [sc/row {:class [:gap-1]}
                      [sc/badge-author "Author of this post"]
                      [sc/badge-date {:class [:tabular-nums]} "1 uke siden"]]
                     [sc/subtitle "subtitle lorem ipsum dolores etceterum among many lines and so forth"]
                     [sc/text-p "text subtext subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum"]
                     [sc/subtext-p "subtext subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum subtitle lorem ipsum dolores etceterum"]]]]

       [:<>
        [sc/surface-a section]
        ;[sc/surface-b section]
        [sc/surface-c section]])]
  #_[:div.flex.justify-end.items-center

     [schpaa.style.menu/menu-example-with-args
      {:dir         :down
       :data        (complex-menu settings)
       :always-show false
       :button      (fn [open]
                      [scb/normal-floating
                       ;{:class [:w-32]}
                       [sc/row {:class [:gap-4 :px-2]}
                        [sc/text "Visning"]
                        [sc/icon [:> (if open outline/XIcon outline/ChevronDownIcon)]]]])}]]
  #_[:div.fixed.-absolute.bottom-2.right-4
     [:div.flex.justify-end.items-center
      [schpaa.style.menu/menu-example-with-args
       {:dir         :up
        :data        (complex-menu settings)                ;(standard-menu-2 (r/atom nil))
        :always-show false
        :button      (fn [open]
                       [scb/round-normal-floating
                        [sc/icon [:> (if open solid/DotsVerticalIcon
                                              solid/DotsHorizontalIcon)]]])}]]])