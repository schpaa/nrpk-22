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
            [booking.ico :as ico]
            [clojure.string :as str]))

;; styles

(o/defstyled checkbox-matrix :div
  [:& :gap-x-4 :gap-y-2
   {:display               "grid"
    :align-items           "center"
    :grid-auto-rows        "min-content"
    :grid-template-columns "repeat(auto-fit,minmax(14ch,1fr))"}])

;; constants

(def debug nil)

(def damage-words ["Mangler"
                   "Løs"
                   "Liten sprekk"
                   "Stor sprekk"
                   "Skrog"
                   "Strikk"
                   "Lokk"
                   "Lister"
                   "Ror"
                   "Styrepinne"
                   "Styrewire"
                   "Feste"
                   "Pedaler"
                   "Kjøl"
                   "Cockpit"
                   "Sete"
                   "Merking"
                   "Skilt på stativ"
                   "Rettelse"])

;; storage

(def -debug
  (#(if goog.DEBUG % false) debug))

(defn write-to-disk [id data]
  (let [path ["boad-item" id "work-log"]
        datum {:path  path
               :value data}]
    (.then (db/database-push datum)
           #(tap> {:push-to-database
                   {:result-of-push (.-key %)
                    :id             id
                    :path           path
                    :value          data}}))))

(defn delete-worklog-entry [boat-item-id id delete]
  (let [path ["boad-item" boat-item-id "work-log" id]
        datum {:path  path
               :value {:deleted delete}}]
    (tap> datum)
    (db/database-update datum)
    #(tap> {:delete-worklog-entry
            {:id   id
             :path path}})))

(defn complete-worklog-entry [boat-item-id id complete]
  (let [path ["boad-item" (some-> boat-item-id name) "work-log" id]
        datum {:path  path
               :value {:complete complete}}]
    (tap> datum)
    (db/database-update datum)
    #(tap> {:delete-worklog-entry
            {:id   id
             :path path}})))

(defn prep-for-submit [v]
  (let [;todo check timestamp-format for reading this
        timestamp (str (t/now))
        r (assoc v :timestamp timestamp)]
    (reduce (fn [a [k v]]
              (if (and (some #{k} damage-words)
                       (false? v))
                (dissoc a k)
                a))
            r
            r)))

(defn on-submit [id {:keys [values]}]
  (tap> {"on-submit " id})
  (write-to-disk
    (some-> id name)
    (prep-for-submit values)))

;; worklog

(defn q-button [boat-item-id worklog-entry-id attr icon action]
  (hoc.buttons/round-pill
    (merge-with into {:on-click #(action boat-item-id worklog-entry-id)} attr)
    [sc/icon icon]))

(defn worklog-card
  [{:keys [class]} {:keys [deleted complete timestamp description uid] :as worklog-entry}
   boat-item-id worklog-entry-id]
  (let [q-button (partial q-button boat-item-id worklog-entry-id)]
    [sc/zebra'
     {;:class [:divider]
      :sstyle {;:margin-inline "4rem"
               :margin-before "4rem"
               :border-bottom "1px dashed var(--text2)"}}
     [sc/col-space-4
      [sc/row-sc-g4-w {:style {:width "100%"}}
       (if deleted
         (q-button
           {:class [:outline2]}
           ico/rotate-left
           (fn [a b] (delete-worklog-entry a (some-> b name) false)))
         (q-button
           {:class [:danger]}
           ico/trash
           (fn [a b] (delete-worklog-entry a (some-> b name) true))))

       (if complete
         (hoc.buttons/round-pill
           {:on-click #(do (complete-worklog-entry boat-item-id (some-> worklog-entry-id name) false)
                           #_(.stopPropagation %))
            :class    [:outline2 :inverse]} [sc/icon ico/check])
         (hoc.buttons/round-pill
           {:on-click #(do (complete-worklog-entry boat-item-id (some-> worklog-entry-id name) true)
                           #_(.stopPropagation %))
            :class    [:outline2]} [sc/icon ico/check]))

       ;todo compress!
       [sc/col-space-1 {:style {:flex "1"}}
        [sc/small
         (some-> timestamp t/instant t/date-time booking.flextime/relative-time)]
        [sc/small0 (or uid "Nøkkelvakt")]]]

      (when-not complete
        [sc/col-space-2 {:class class}
         [sc/row-sc-g1-w
          (->> (dissoc worklog-entry :description :timestamp :complete :deleted)
               (map (comp name key))
               (map sc/pill2))]

         [sc/text1 {:style {:text-decoration-line (when complete "line-through")}} description]])]]))


(defn insert-worklog [boat-item-id work-log]
  (let [all-data (->> @work-log
                      (filter (fn [[k v]] (:deleted v)))
                      #_(filter (fn [[k v]]
                                  (or
                                    (some? (:description v))
                                    (some? (:state v)))))

                      #_(take 5))
        data (->> @work-log
                  (remove (fn [[k v]] (:deleted v)))
                  (filter (fn [[k v]]
                            (or
                              (some? (:description v))
                              (some? (:state v)))))
                  reverse
                  (take 5))]
    [sc/col-space-4
     (into [:<>]
           (for [[worklog-entry-id m] data]
             [worklog-card {:class [:opacity-100]} m boat-item-id worklog-entry-id]))

     (r/with-let [more? (r/atom false)]
       [sc/co
        (when-not @more?
          [hoc.buttons/regular
           {:type     "button"
            :on-click #(reset! more? true)}
           [sc/co
            [sc/small "Du har kommet til slutten av listen"]
            [sc/text1 "Vis tidligere"]]])
        (when @more?
          (for [[worklog-entry-id m] all-data]
            [worklog-card {:class [:opacity-50]} m boat-item-id worklog-entry-id]))])]))

;; components

(defn insert-damage [{:keys [values set-values] :as props}]
  [sc/col-space-4
   [checkbox-matrix
    (into [:<>]
          (for [e (sort damage-words)]
            [sc/co
             [hoc.toggles/largeswitch-local'
              {:get     #(values e)
               :set     #(set-values {e %})
               :view-fn (fn [t c v] [sc/row-sc-g2 t
                                     [(if v sc/text1 sc/text2) c]])
               :caption e}]]))]
   [sci/textarea props
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
  [sc/col-space-4 {:style {:padding "1rem"}}
   [sc/row-sc-g2
    [widgets/badge
     {:class [:big :right-square]}
     number
     slot]
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

     [sc/text1 "Plassert på " (widgets/location location)]

     [sc/col-space-1
      [sc/surface-ab
       [sc/row-sc-g2 [sc/small {:style {:user-select :all}} boat-type] [sc/small "(type)"]]
       [sc/row-sc-g2 [sc/small {:style {:user-select :all}} id] [sc/small "(id)"]]]]]

    (when -debug [l/pre data])]])

(defn bottom-button-panel [& content]
  [:div
   {:style {:height                     "100%"
            :position                   :sticky
            :bottom                     4
            ;:padding-inline "1rem"
            ;:margin-inline              -24
            :z-index                    10
            :border-bottom-left-radius  "var(--radius-1)"
            :border-bottom-right-radius "var(--radius-1)"
            :background-color           "var(--content)"}}
   [sc/row-ec {:style {
                       :padding-block 16}
               :class [:px-6]}
    content]])

;; react dialog (styling starts here)

(defonce selected-tab (r/atom :feil))

(defn modal-boatinfo-windowcontent [{:keys [data on-close uid] :as input}]
  (let [admin? (rf/subscribe [:lab/admin-access])
        {:keys [boat-type]} data
        boat-item-id (some-> (:id data) name)]
    (r/with-let [bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
                 ex-data (db/on-value-reaction {:path ["users" uid "starred" boat-type]})]
      [sc/dropdown-dialog'
       ;header
       [:div.sticky.top-0
        {:style {;:margin-inline    -24
                 ;:padding          24
                 :padding-bottom   0
                 :xmargin-bottom   -32
                 :z-index          10
                 :background-color "var(--toolbar)"}}
        [header
         {:bt-data bt-data
          :ex-data ex-data}
         data]

        [sc/row-center {:style {:position       :relative
                                :top            12
                                :xmargin-bottom -32}}
         ;pills
         (widgets/pillbar {:class [:small]} selected-tab
                          [[:feil "Feil"]
                           (if @admin? [:arbeidsliste "Arbeidsliste"])
                           [:data "Data"]])]]

       ;body
       [fork/form {:prevent-default?  true
                   :clean-on-unmount? true
                   :keywordize-keys   true
                   :path              :form-damange
                   :form-id           "damage-form2"
                   :initial-values    (reduce (fn [a e] (assoc a e false)) {:description ""} damage-words)
                   :on-submit         #(on-submit boat-item-id %)}
        (fn [{:keys [dirty handle-submit form-id] :as props}]
          [:form {:id        form-id
                  :class     []
                  :on-submit handle-submit}
           [sc/col-space-8 {:style {:background     (if @selected-tab "var(--floating)" "var(--content)")
                                    :padding-block  "2rem"
                                    :padding-inline "1rem"}}

            (case @selected-tab
              :data [sc/title1 "wip"]
              :feil [insert-damage props]
              :arbeidsliste [insert-worklog
                             boat-item-id
                             (db/on-value-reaction {:path ["boad-item" boat-item-id "work-log"]})]
              nil)]
           (if dirty
             [bottom-button-panel
              [sc/row-sc-g2]
              [:div {:style {:flex "1"}}]
              [hoc.buttons/regular
               {:type     :button
                :style    {:background-color "var(--toolbar)"
                           :color            "var(--buttoncopy)"}
                :on-click on-close}
               "Avbryt"]
              [hoc.buttons/attn
               {:type     :submit
                :disabled false}
               "Lagre"]]
             [bottom-button-panel
              [sc/row-sc-g2
               #_[hoc.buttons/cta-outline
                  {:type     :button
                   :on-click #()
                   :disabled true}
                  [sc/icon ico/arrowLeft']]
               #_[hoc.buttons/cta-outline
                  {:type     :button
                   :on-click #()
                   :disabled true}
                  [sc/icon ico/arrowRight']]]
              [:div {:style {:flex "1"}}]
              [hoc.buttons/regular
               {:type     :button
                :style    {:background-color "var(--toolbar)"
                           :color            "var(--buttoncopy)"}
                :on-click on-close}
               "Lukk"]])])]]

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
