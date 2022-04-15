(ns booking.utlan
  (:require [tick.core :as t]
            [shadow.resource :refer [inline]]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [schpaa.style.dialog :as dlg :refer [open-modal-boatinfo]]
            [re-frame.core :as rf]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.database]
            [booking.routes :refer [shortcut-link]]
            [booking.ico :as ico]
            [logg.database]
            [reagent.core :as r]
            [db.core :as db]
            [reitit.core :as reitit]
            [clojure.set :as set]))

;region
;(logg.database/boat-db)

(defonce data (r/atom {:utlån []}))

(defn- util:prep [{:keys [boats] :as m}]
  (assoc m :boats (mapv booking.database/fetch-id-from-number- boats)))

(defn prepare []
  (swap! data update :utlån (comp vec concat)
         (mapv util:prep [{:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [484 481 195 601 702 140 146 155 433 707]}
                          {:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [484 481]}
                          {:id    (str (random-uuid))
                           :date  (t/at (t/today) (t/time "03:45"))
                           :boats [477]}])))

(comment
  (do
    (reset! data {:utlån []})

    (swap! data update :utlån (comp vec concat)
           (mapv util:prep [{:id    (str (random-uuid))
                             :date  (t/at (t/today) (t/time "03:45"))
                             :boats [484 481 195 601 702 140 146 155 433 707]}
                            {:id    (str (random-uuid))
                             :date  (t/at (t/today) (t/time "03:45"))
                             :boats [484 481]}]))))

#_(defonce data
           (r/atom {:utlån   (map (fn [{:keys [boats] :as m}]
                                    (assoc m :boats (mapv booking.database/fetch-id-from-number- boats)))
                                  [{:id          (str (random-uuid))
                                    :date        (t/at (t/today) (t/time "03:45"))
                                    :in-progress true
                                    :boats       [484 481 195 601 702 140 146 155 433 707]}
                                   {:id          (str (random-uuid))
                                    :date        (t/at (t/date "2022-04-03") (t/noon))
                                    :in-progress true
                                    :boats       [495 491 428]}
                                   {:id    (str (random-uuid))
                                    :date  (t/at (t/date "2022-04-04") (t/noon))
                                    :boats [496 492]}])
                    :booking [{:id          (str (random-uuid))
                               :date-out    (t/at (t/date "2022-04-02") (t/noon))
                               :date-in     (t/at (t/date "2022-04-03") (t/noon))
                               :in-progress true
                               :boats       [123]}
                              {:id          (str (random-uuid))
                               :date-out    (t/at (t/date "2022-05-03") (t/noon))
                               :date-in     (t/at (t/date "2022-05-04") (t/noon))
                               :in-progress true
                               :boats       [123 124]}]}))

;endregion

(o/defstyled listitem' :div
  [:& :gap-2
   {:line-height  "var(--font-lineheight-4)"

    :display      :inline-flex
    :text-indent  "-1rem"
    :padding-left "1rem"
    :flex-wrap    :wrap
    :align-items  :baseline
    :color        "var(--text1)"}])

(comment
  (softlistwrapper
    {:data  (:utlån data)
     :items (fn [{:keys [date]}]
              [[sc/link {:on-click #(tap> "inn")} "Innlever"]
               (into [:<>]
                     (->> (remove nil? boats)
                          (mapv (fn [id]
                                  (let [data (booking.database/fetch-boatdata-for id)
                                        number (:number data)]
                                    (sc/badge-2 {:on-click #(schpaa.style.dialog/open-modal-boatinfo data)} number))))))
               [:div.inline-block date]])}))

;region innlevering

(defn disp [{:keys [data on-close on-save]}]
  (let [{:keys [initial]} data
        db (rf/subscribe [:db/boat-db])]
    (r/with-let [st (r/atom initial)]
      [sc/dropdown-dialog
       [sc/col-space-8
        [sc/col-space-4
         (for [[k _v] @st
               :let [{:keys [number kind navn]} (get @db k)]]

           #_[sc/row-sc-g2

              (schpaa.style.hoc.toggles/largeswitch-local
                {:atoma   (r/cursor st [k])
                 :view-fn (fn [t c] [sc/badge-2 {:class [:big]} t [:div.w-64 c]])
                 :caption number})

              [sc/col {:style {:flex    "1"
                               :opacity (if @(r/cursor st [k]) 1 0.25)}}
               [sc/text1 (schpaa.components.views/normalize-kind kind)]
               [sc/small1 navn]]]

           (schpaa.style.hoc.toggles/largeswitch-local
             {:atoma   (r/cursor st [k])
              :view-fn (fn [t c] [:div {:style {:gap             "var(--size-2)"
                                                :display         :flex
                                                :align-items     :center
                                                :justify-content :between
                                                :width           "100%"}}
                                  t
                                  [sc/badge-2 {:class [:big]} number]
                                  [sc/col {:style {:flex    "1"
                                                   :opacity (if @(r/cursor st [k]) 1 0.25)}}
                                   [sc/text1 (schpaa.components.views/normalize-kind kind)]
                                   [sc/small1 navn]]])}))]
        [sc/row-ec
         #_[hoc.buttons/regular {:on-click (fn [] (swap! st #(reduce (fn [a [k _v]] (update a k (fnil not false))) % %)))} "Omvendt"]
         [:div.grow]
         [hoc.buttons/regular {:on-click on-close} "Avbryt"]
         [hoc.buttons/danger {:disabled (not (some true? (vals @st)))
                              :on-click #(on-save @st)} "Bekreft"]]]]
      (finally))))

(defn innlevering
  "presents a window with a list of all entries, all selected by default and
  a confirmation or cancel to click. Each entry can be deselected to avoid ..."
  [data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data       {:initial (reduce (fn [a e] (assoc a e true)) {} (:boats data))}
                 :action     #(tap> {"action (carry)" (:carry %)})
                 ;called after the dialog has closed and after `action` is called (if defined)
                 ;todo Rename to `after-close`, action above is the primary
                 ;:on-primary-action #(tap> {"on-primary-action" %})
                 ;:on-close   #(tap> "on-close")
                 ;placed in function to allow displaying changes when hot-reloaded
                 :content-fn #(disp %)}]))

(comment
  (do
    (let [data {:a false :b false :c false}
          st (atom data)]
      #_(swap! st #(reduce (fn [a [k v]] (update a k (fnil not false))) % %))
      ;(not-any? true? (vals @st))
      (some true? (vec (vals @st))))))

;endregion

(defn render [uid]
  (when-let [db @(rf/subscribe [:db/boat-db])]
    (let [data (rf/subscribe [:rent/list])
          lookup-id->number (into {} (->> (remove (comp empty? :number val) db)
                                          (map (juxt key (comp :number val)))))]
      [sc/col-space-8
       ;[l/ppre-x @data]

       [sc/row-sc-g2
        [hoc.buttons/cta-pill-icon {:on-click #(rf/dispatch [:lab/toggle-boatpanel])} ico/plus "Nytt utlån"]
        ;[shortcut-link :r.booking]
        [hoc.buttons/reg-pill {:on-click prepare} "Prep!"]
        #_[sc/link {:on-click #(rf/dispatch [:lab/toggle-boatpanel])} "Nytt utlån"]]


       (when @(rf/subscribe [:lab/nokkelvakt])
         [sc/col-space-8
          [sc/col
           (-> (inline "./oversikt/nøklevann.md") schpaa.markdown/md->html sc/markdown)

           [sc/row-sc-g4-w
            #_[sc/link {:href      (kee-frame.core/path-for [:r.aktivitetsliste])
                        :-on-click #(rf/dispatch [:app/navigate-to [:r.aktivitetsliste]])} "Aktivitet i dag"]
            [sc/link {:class    [:disabled]
                      :on-click #(rf/dispatch [])} "Båtoversikt"]

            [sc/link {:class    [:disabled]
                      :on-click #(rf/dispatch [])} "Mine utlån"]]]])

       [sc/col-space-8
        (into [:<>]
              (doall (for [[_k {:keys [timestamp list id] :as m}] @data
                           :let [boats list
                                 date (t/instant timestamp)
                                 date (some-> date booking.flextime/relative-time)]]
                       [apply listitem'
                        (doall (concat
                                 [[hoc.buttons/reg-pill {:style    {:display    :inline-block
                                                                    :align-self :center}
                                                         :on-click #(innlevering {:boats list})} "Innlever"]]
                                 [(into [:<>]
                                        (mapv (fn [id]
                                                (let [number (get lookup-id->number id (str "?" id))]
                                                  (sc/badge-2 {:on-click #(dlg/open-modal-boatinfo
                                                                            {:uid  uid
                                                                             :data (get db id)})} number)))
                                              (remove nil? boats)))]
                                 [[:div {:style {:text-indent 0}} date]]))])))]

       #_(when @(rf/subscribe [:lab/booking])
           [sc/col-space-8
            [sc/col
             (-> (inline "./oversikt/sjøbasen.md") schpaa.markdown/md->html sc/markdown)
             [sc/row-sc-g4-w
              [sc/link {:on-click #(rf/dispatch [:app/navigate-to [:r.booking.oversikt]])} "Aktivitet i dag og framover"]
              [sc/link {:on-click #(rf/dispatch []) :class [:disabled]} "Båtoversikt"]
              [sc/link {:on-click #(rf/dispatch [])} "Ny booking"]
              [sc/link {:on-click #(rf/dispatch []) :class [:disabled]} "Mine bookinger"]]]
            [:div (for [e (:booking @data)]
                    [l/ppre-x e])]])])))