(ns booking.lab
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.style.booking]
            [schpaa.style.popover]
            [db.core :as db]
            [booking.content.blog-support :refer [err-boundary]]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [schpaa.debug :as l]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog]
            [booking.views]))

(defn page-boundry [& c]
  [err-boundary
   [:div.max-w-mdx.mr-auto
    c]])

(rf/reg-event-db :lab/show-popover (fn [db]
                                     (tap> (:lab/show-popover db))
                                     (update db :lab/show-popover (fnil not true))))
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
      [:div
       [schpaa.style.button/normal {:type     "button"
                                    :on-click #(do (rf/dispatch [:lab/show-popover]))} "Popover"]
       ;[l/ppre visible]
       [:div.z-20.fixed.inset-10.top-10.max-w-xs.mx-auto.x-debug.pointer-events-none
        [schpaa.style.popover/popover-example @visible data]]

       #_[:div.z-50                                         ;.fixed.inset-4.z-50
          [schpaa.style.popover/popover-example2]
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

(defn time-input-form []
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
     [sc/surface-a {:class [:w-full :overflow-x-auto]}
      [:form.space-y-1
       {:id        form-id
        :on-submit handle-submit}]
      [sc/row {:class [:shrink-0]}
       [:div.grow [booking.views/time-input props false]]
       [:div.relative.absolute.top-1.right-1.px-2.z-500]]])])

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

(defn render [r]
  (let [user-auth @(rf/subscribe [::db/user-auth])]
    (r/with-let [settings (r/atom {:setting-1 false
                                   :setting-2 1
                                   :selection #{2 5}})]
      [page-boundry
       [:div.p-2.space-y-4
        [scb/cta {:type "button"} "Open dialog2"]

        [schpaa.style.dialog/modal-example-with-timeout
         [:<>
          [sc/title-p "Autoclosing message"]
          [sc/text-p "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]]

        [schpaa.style.dialog/modal-example false]

        [popover-sample]

        [time-input-form]

        [sc/grid-wide {:class [:gap-4]}
         (map schpaa.style.booking/line card-data)]]])))

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