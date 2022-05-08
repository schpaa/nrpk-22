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
            [tick.core :as t]
            [lambdaisland.ornament :as o]
            [booking.ico :as ico]))

(def debug nil)

;; constants

(def damage-words ["Mangler"
                   "Løs"
                   "Liten sprekk"
                   "Stor sprekk"
                   "Skrog"
                   "Strikk"
                   "Lokk"
                   "Lister"
                   "Ror"
                   "Styre\u00adpinne"
                   "Styre\u00ADwire"
                   "Kjøl"
                   "Cockpit"
                   "Sete"
                   "Merking"
                   "Skilt"])

;; storage

(def -debug
  (#(if goog.DEBUG % false) debug))

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

(defn delete-worklog-entry [id]
  (let [path ["boad-item" id "work-log"]
        datum {:path  path
               :value {:deleted true}}]
    ;(tap> datum)
    (db/database-update datum)
    #(tap> {:delete-worklog-entry
            {:id   id
             :path path}})))


(defn prep-for-submit [[v st]]
  (let [;todo check timestamp-format for reading this
        timestamp (str (t/now))]
    (assoc v :timestamp timestamp
             :state st)))

(defn on-submit [id st {:keys [values]}]
  (tap> "on-submit")
  (write-to-disk
    (some-> id name)
    (prep-for-submit [values st])))

;; input

;; components

(o/defstyled checkbox-matrix :div
  [:& :gap-4
   {:display               "grid"
    :align-items           "center"
    :grid-auto-rows        "min-content"
    :grid-template-columns "repeat(auto-fit,minmax(12ch,1fr))"}])

;; worklog

(defn insert-worklog [work-log]
  (let [data (->> work-log
                  (filter (fn [[k v]] (tap> k) (or (some? (:description v))
                                                   (some? (:state v)))))
                  reverse
                  (take 5))]
    [sc/col-space-4
     [l/pre data]
     (into [:<>]
           (for [e data
                 :let [[k {:keys [deleted uid timestamp state description]}] e]]
             [sc/zebra {:style {:border-radius "var(--radius-1)"}}
              [sc/col-space-2 {:style {:align-items :start}}

               ;header
               [:button {:type "button" :on-click #(tap> "clack")} "clack"]

               [sc/row-sc-g2 {:style {:width "100%"}}
                (hoc.buttons/round-danger-pill
                  {;:type     "button"
                   :on-click #(do (delete-worklog-entry (some-> k name))
                                  (.stopPropagation %))}
                  [sc/icon ico/trash])
                [sc/small
                 {:class [:flex-grow]}
                 (some-> timestamp t/instant t/date-time booking.flextime/relative-time)]
                (hoc.buttons/round-cta-pill
                  {:type     "button"
                   :on-click #(tap> "CLICK OK")
                   :class    []} [sc/icon ico/check])]

               ;content
               [sc/text1 description]
               [l/pre uid e deleted]

               ;signature
               [sc/text1 {:style {:text-transform :lowercase}}
                (apply str (interpose ", " (map (comp name key) state)))]
               [sc/small (or uid "Nøkkelvakt")]]
              (when -debug [l/pre e])]))
     [sc/small "Vi har mer men viser det ikke her"]]))

(defn insert-damage [{:keys [values set-values] :as props}]
  [sc/col-space-4
   [checkbox-matrix
    (into [:<>]
          (for [e damage-words]
            [sc/co
             [hoc.toggles/largeswitch-local'
              {:get     #(values (keyword e))
               :set     #(set-values {(keyword e) %})
               :view-fn (fn [t c v] [sc/row-sc-g2 t
                                     [(if v sc/text1 sc/text2) c]])
               :caption e}]]))]

   [sci/textarea props
    #_{:handle-change (fn [e] (swap! (r/cursor st [:description]) #(.. e -target -value)))
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

(defn just-panel [& content]
  [:div
   {:style {:box-shadow       "var(--shadow-1)"
            :background-color "var(--toolbar)"}
    :class [:pt-4 :pb-6 :sticky :bottom-0 :-mx-8]}
   [sc/row-ec {:class [:px-8]}
    content]])

;; react dialog (styling starts here)

(defn modal-boatinfo-windowcontent [{:keys [data on-close uid] :as input}]
  (let [{:keys [boat-type]} data
        id (:id data)]
    (r/with-let [bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
                 ex-data (db/on-value-reaction {:path ["users" uid "starred" boat-type]})
                 st (r/atom {})]
      ;(tap> {:modal-boatinfo-windowcontent data})
      [sc/dropdown-dialog'
       [:div.sticky.top-0
        {:style {
                 ;:position         :sticky
                 ;:top "-4rem"
                 ;:margin-top       "132px"
                 :margin-inline    -24
                 :padding          24
                 :z-index          10
                 :background-color "var(--toolbar)"}}
        [header
         {:bt-data bt-data
          :ex-data ex-data}
         data]]
       [fork/form {:prevent-default?  true
                   :form-id           "damage-form2"
                   :initial-values    {:description ""}
                   :clean-on-unmount? true
                   ;:component-did-mount (fn [_])
                   :keywordize-keys   true
                   :xon-submit        #(on-submit id @st %)}
        (fn [{:keys [dirty handle-submit form-id] :as props}]
          [:form {:id         form-id
                  :osn-submit handle-submit}
           [sc/col-space-8 {:style {:padding-top "2rem"}}
            (when -debug [l/pre input boat-type @st])
            ;i want something in editor to execute when the close button is pressed
            [togglepanel {:open     0
                          :disabled true} :boats/editor "Endringer" #(editor props) false]
            ;[l/pre (:values props)]
            ;[togglepanel  :boats/damage "Trenger nærmere ettersyn" (fn [] [insert-damage props])]
            [togglepanel :boats/worklog "Arbeidsliste" (fn [] [insert-worklog (:work-log data)])]
            [just-panel
             [hoc.buttons/attn
              {:type     "submit"
               :disabled (not dirty)
               :on-click on-close}
              "Lagre"]
             [hoc.buttons/regular
              {:type     "submit"
               :style    {:background-color "var(--toolbar)"
                          :color            "var(--buttoncopy)"}
               :on-click on-close}
              "Lukk"]]]])]]

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
