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
            [booking.flextime]
            [clojure.string :as str]))

;; styles

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
  (hoc.buttons/pill
    (merge-with into {:class [:round]
                      :type     "button"
                      :on-click #(action boat-item-id worklog-entry-id)} attr)
    [sc/icon icon]))

(defn worklog-card
  [{:keys [class]} {:keys [deleted complete timestamp description uid] :as worklog-entry}
   boat-item-id worklog-entry-id]
  (let [q-button (partial q-button boat-item-id worklog-entry-id)]
    [sc/zebra'
     {:sstyle {;:margin-inline "4rem"
               :margin-before "4rem"
               :border-bottom "1px dashed var(--text2)"}}
     [sc/col-space-4
      [sc/row-sc-g4-w {:style {:width "100%"}}
       (if deleted
         (q-button
           {:class [:frame]}
           ico/undo
           (fn [a b] (delete-worklog-entry a (some-> b name) false)))
         (q-button
           {:class [:danger]}
           ico/trash
           (fn [a b] (delete-worklog-entry a (some-> b name) true))))

       (if complete
         (hoc.buttons/pill
           {:type :button
            :on-click #(complete-worklog-entry boat-item-id (some-> worklog-entry-id name) false)
            :class    [:round :frame :inverse]} [sc/icon ico/check])
         (hoc.buttons/pill
           {:type :button
            :on-click #(complete-worklog-entry boat-item-id (some-> worklog-entry-id name) true)
            :class    [:round :frame]} [sc/icon ico/check]))

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
    [sc/col-space-1
     (into [:<>]
           (for [[worklog-entry-id m] data]
             [worklog-card {:class [:opacity-100]} m boat-item-id worklog-entry-id]))

     (r/with-let [more? (r/atom false)]
       [:<>
        (when-not @more?
          [:div.flex.flex-col.items-center.space-y-4 
           [sc/text1 "Dette er slutten av listen"]
           [hoc.buttons/just-caption
            {:type     "button"
             :on-click #(reset! more? true)}
            "oppdater"]])
        (when @more?
          (for [[worklog-entry-id m] all-data]
            [worklog-card {:class [:opacity-50]} m boat-item-id worklog-entry-id]))])]))

;; components

(defn insert-damage [{:keys [values set-values] :as props}]     
  [sc/col-space-4 {:style {:padding "0"}}
   [sc/checkbox-matrix
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
    nil {:placeholder "Annen beskrivelse"
         :class [:-mx-2]} "" :description]])

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

(defn header [_uid {:keys [id work-log slot location description number] :as data}]
  [sc/col-space-4 {:style {:padding "1rem"}}
   (let [n (->> work-log (remove (comp :deleted val)) vals count)]
     [sc/row-sc-g2
      ;[l/pre n (->> work-log (remove (comp :deleted val)) keys)]
      [widgets/badge
       {:class [:big (if (pos? n) :work-log)
                :right-square (when-not number :active)]}
       (or number id)
       (or slot "ny")]
      [widgets/stability-name-category data]
      [:div.grow]])
   [sc/col-space-1
    [widgets/dimensions-and-material data]
    [sc/col-space-2
     [sc/text0 description]

     [sc/row-end [sc/text1 (widgets/location location)]]]



    (when -debug [l/pre data])]])

(defn bottom-button-panel [& content]
  [:div
   {:style {:height                     "100%"
            :position                   :sticky
            :bottom                     0
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

(defn modal-boatinfo-windowcontent [{:keys [data on-close uid]}]
  (let [admin? (rf/subscribe [:lab/admin-access])
        {:keys [id boat-type]} data
        boat-item-id (some-> id name)
        boat-item-id (when (< 3 (count boat-item-id))
                       boat-item-id)]
    [sc/dropdown-dialog' 
     [:div
      {:style {:padding-bottom  "2rem"
               :z-index          10
               :background-color "var(--toolbar)"}}
      [header uid data]]

     [:div.sticky.top-4.z-10
      {:style {:margin-top "-1.4rem"}}
      [sc/row-center
       (widgets/pillbar {:class [:small]} selected-tab
                        [[:feil "Mangler"]
                         (if @admin? [:arbeidsliste "Arbeidsliste"])
                         [:data "Data"]])]]

     [fork/form {:prevent-default?  true
                 :clean-on-unmount? true
                 :keywordize-keys   true
                 :path              :form-damange
                 :form-id           "damage-form2"
                 :initial-values    (reduce (fn [a e] (assoc a e false)) {:description ""} damage-words)
                 :on-submit         #(do
                                       (on-submit boat-item-id %)
                                       (on-close))}
      (fn [{:keys [dirty handle-submit form-id] :as props}]
        [:form {:id        form-id
                :class     []
                :on-submit handle-submit}
         [sc/co
          {:xstyle {;:background     (if @selected-tab "var(--floating)" "var(--content)")
                    :padding-block-end  "1rem"
                    :padding-inline "1rem"}}

          (if @selected-tab
            (into [:div.px-8.pt-8.pb-1]
                  [(case @selected-tab
                     :feil [insert-damage props]

                     :arbeidsliste (when boat-item-id
                                     [insert-worklog
                                      boat-item-id
                                      (db/on-value-reaction {:path ["boad-item" boat-item-id "work-log"]})])

                     [sc/co
                      ;;urls to firebase
                      [sc/row-sc-g2 [sc/text1 {:style {:user-select :all}} boat-type] [sc/text1 "(type)"]
                       (widgets/data-url {:checked-path ["boat-brand" boat-type]})]
                      [sc/row-sc-g2 [sc/text1 {:style {:user-select :all}} id] [sc/text1 "(id)"]
                       (widgets/data-url {:checked-path ["boad-item" id]})]])])
            [:div.h-8])]
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
            [:div {:style {:flex "1"}}]
            [hoc.buttons/regular
             {:type     :button
              :style    {:background-color "var(--toolbar)"
                         :color            "var(--buttoncopy)"}
              :on-click on-close}
             "Lukk"]])])]]))

;; main accessor

(defn open-modal-boatinfo [{:keys [data]}]
  (let [uid @(rf/subscribe [:lab/uid])]
    (rf/dispatch-sync [:modal.slideout/clear])
    (rf/dispatch [:modal.slideout/toggle
                  true
                  {:data          data
                   :uid           uid
                   :on-flag-click (fn [boat-type value]
                                    (rf/dispatch [:star/write-star-change
                                                  {:boat-type boat-type
                                                   :value     value
                                                   :uid       uid}]))
                   :content-fn    #(modal-boatinfo-windowcontent %)}])))
