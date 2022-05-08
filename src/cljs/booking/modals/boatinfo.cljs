(ns booking.modals.boatinfo
  (:require [re-frame.core :as rf]
            [fork.re-frame :as fork]
            [reagent.core :as r]
            [db.core :as db]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [schpaa.style.input :as sci]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.debug :as l]
            [tick.core :as t]))

(def debug 1)                                               ;; <- change me!

;; constants

(def damage-words ["Mangler"
                   "Løs"
                   "Liten sprekk"
                   "Stor sprek"
                   "Skrog"
                   "Strikk"
                   "Lokk"
                   "Liste"
                   "Ror"
                   "Styre\u00adpinne"
                   "Styre\u00ADwire"
                   "Kjøl"
                   "Cockpit"
                   "Sete"])

;; storage

(def -debug (#(if goog.DEBUG % false) debug))               ;; <- not me!

(defn write-to-disk [id data]
  (let [path ["boad-item" id "work-log"]
        datum {:path  path
               :value data}]
    (.then (db/database-push datum)
           #(tap> {:write-to-disk
                   {:result-of-push (.-key %)
                    :id             id
                    :path           path
                    :value          data}}))))

(defn prep-for-submit [[v st]]
  (let [;todo check timestamp-format for reading this
        timestamp (str (t/now))]
    (assoc v :timestamp timestamp
             :state st)))

(defn on-submit [id st {:keys [values]}]
  (write-to-disk
    (some-> id name)
    (prep-for-submit [values st])))

;; components

(defn insert-damage [st]
  [:fieldgroup.space-y-8
   [:div.gap-4
    {:style {:display               :grid
             :grid-template-columns "repeat(auto-fit,minmax(12ch,1fr))"}}
    (into [:<>] (for [e damage-words]
                  [hoc.toggles/largeswitch-local'
                   {:atoma   (r/cursor st [e])
                    :view-fn (fn [t c v] [sc/row-sc-g2 t
                                          [(if v sc/text1 sc/text2) c]])
                    :caption e}]))]

   [sci/textarea
    {:handle-change (fn [e] (swap! (r/cursor st [:description]) #(.. e -target -value)))
     :values        {:description @(r/cursor st [:description])}}
    nil {:class [:-mx-2]} "Annen beskrivelse" :description]])

(defn editor [props]
  (let [people (map (fn [[k v]] {:id (some-> k name) :name (:navn v)})
                    @(rf/subscribe [:db/boat-type]))]
    [sc/co
     [sc/col-space-1
      [sc/label "Sample Label"]
      [sci/combobox
       (conj props
             {:people       people
              :person-by-id #(zipmap (map :id people) people)})

       :kind? [:w-full] :lable :names]]
     [sci/textarea props :text [] "Sample Label" :description]]))

(defn header [{:keys [bt-data ex-data]}
              {:keys [id boat-type slot location description number on-star-click] :as data}]
  [sc/col-space-4
   [sc/row-sc-g2
    [sc/badge-2 {:class [:big]} number]
    [widgets/stability-name-category data]
    [:div.grow]
    [widgets/favourites-star
     {:bt-data       bt-data
      :ex-data       ex-data
      :on-star-click on-star-click} data]]
   [sc/col-space-1
    [widgets/dimensions-and-material data]
    [sc/col-space-4
     [sc/text0 description]
     [sc/col-space-1
      [sc/surface-ab
       [sc/row-sc-g2 [sc/small {:style {:user-select :all}} boat-type] [sc/small "(type)"]]
       [sc/row-sc-g2 [sc/small {:style {:user-select :all}} id] [sc/small "(id)"]]]]

     [sc/text1 "Plassert på " (widgets/location location) ", stativ " slot]]

    (when -debug [l/pre data])]])



;; react dialog (styling starts here)

(defn modal-boatinfo-windowcontent [{:keys [data on-close uid] :as input}]
  (let [{:keys [boat-type]} data
        id (:id data)]
    (r/with-let [bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
                 ex-data (db/on-value-reaction {:path ["users" uid "starred" boat-type]})
                 st (r/atom {})]
      (tap> {:modal-boatinfo-windowcontent data})
      [sc/dropdown-dialog'
       [header
        {:bt-data bt-data
         :ex-data ex-data}
        data]
       [fork/form {:prevent-default?    true
                   :form-id             "damage-form"
                   :initial-values      {}
                   :clean-on-unmount?   true
                   :component-did-mount (fn [_])
                   :keywordize-keys     true
                   :on-submit           (partial on-submit id @st)}
        (fn [{:keys [dirty handle-submit form-id] :as props}]
          [:form {:id        form-id
                  :on-submit handle-submit}
           [sc/col-space-8 {:style {:padding-top "2rem"}}
            (when -debug [l/pre input boat-type @st])
            ;i want something in editor to execute when the close button is pressed
            [togglepanel :boats/editor "Endringer" #(editor props)]
            [togglepanel :boats/damage "Trenger nærmere ettersyn" #(insert-damage st)]
            [sc/row-ec {:class [:pb-6]}
             [hoc.buttons/regular {:type     :submit
                                   :on-click on-close} "Lukk"]]]])]]
      (finally))))

;; main accessor

(defn open-modal-boatinfo [{:keys [data]}]
  (let [uid @(rf/subscribe [:lab/uid])]
    (rf/dispatch-sync [:modal.slideout/clear])
    (rf/dispatch [:modal.slideout/toggle
                  true
                  {:data          data
                   :uid           uid
                   :on-star-click (fn [boat-type value]
                                    (rf/dispatch [:star/write-star-change
                                                  {:boat-type boat-type
                                                   :value     value
                                                   :uid       uid}]))
                   :content-fn    #(modal-boatinfo-windowcontent %)}])))