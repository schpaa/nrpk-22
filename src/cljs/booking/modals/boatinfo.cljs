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
            [clojure.string :as str]
            [schpaa.style.hoc.buttons :as button]
            [booking.styles :as b]))

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
                   "Fotstøtte"
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

    (db/database-update datum)
    #(tap> {:delete-worklog-entry
            {:id   id
             :path path}})))

(defn complete-worklog-entry [boat-item-id id complete]
  (let [path ["boad-item" (some-> boat-item-id name) "work-log" id]
        datum {:path  path
               :value {:complete complete}}]

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
  (hoc.buttons/just-icon
    (merge-with into {:class    [:round]
                      :type     "button"
                      :on-click #(action boat-item-id worklog-entry-id)} attr)
    icon))

(defn worklog-card
  [{:keys [class]}
   {:keys [deleted complete timestamp description uid] :as worklog-entry}
   boat-item-id worklog-entry-id]
  (let [;todo use a map, not slots!
        q-button (partial q-button boat-item-id worklog-entry-id)]
    [sc/surface-a
     {:sstyle {;:margin-inline "4rem"
               :margin-before "4rem"
               :border-bottom "1px dashed var(--text2)"}}

     ;[l/pre worklog-entry]
     [b/co
      [sc/row-sc-g4-w {:class [:pl-2]
                       :style {:width "100%"}}
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
         (hoc.buttons/just-icon
           {:type     :button
            :on-click #(complete-worklog-entry boat-item-id (some-> worklog-entry-id name) false)
            :class    [:inverse]}
           ico/check)
         (hoc.buttons/just-icon
           {:type     :button
            :on-click #(complete-worklog-entry boat-item-id (some-> worklog-entry-id name) true)
            :class    []} ico/check))

       ;todo compress!
       [b/co0 {:style {:flex "1"}}
        (some-> timestamp t/instant t/date-time booking.flextime/relative-time)
        [b/small (or uid "Nøkkelvakt")]]]

      (when-not (empty? description)
        [:div.px-2 [b/text {:style {:text-decoration-line (when complete "line-through")}} description]])

      (let [data (dissoc worklog-entry :description :timestamp :complete :deleted :uid)]
        (when-not (empty? data)
          [sc/co
           ;[l/pre data (some? data) (empty? data)]
           [sc/row-sc-g1-w
            (->> data
                 (map (comp name key))
                 (map b/keyword-tag))]]))]]))

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
  [b/co
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
         :class       [:w-full]} "" :description]])

(defn editor [props]
  (let [people (map (fn [[k v]] {:id (some-> k name) :name (:navn v)})
                    @(rf/subscribe [:db/boat-type]))]
    [sc/co
     [sc/col-space-1
      [sc/label "Sample Label"]
      [sci/combobox
       props
       {:values      people
        :value-by-id #(zipmap (map :id people) people)
        :class [:w-full]
        :label :names}]]
     [sci/textarea props :text [] "Sample Label" :description]]))

(defn header [_uid {:keys [id work-log slot location description number] :as data}]
  [sc/col-space-4 {:style {:padding "1rem"}}
   (let [n (->> work-log (remove (comp :deleted val)) vals count)]
     [sc/row-sc-g2
      [widgets/badge
       {:class [:big (if (pos? n) :work-log)
                :right-square (when-not number :active)]}
       (or number id)
       (or slot "ny")]
      [widgets/stability-name-category data]
      [:div.grow]])
   [sc/co
    [sc/title description]
    [sc/row-end [sc/text1 (widgets/location location)]]
    [widgets/dimensions-and-material data]]
   (when -debug [l/pre data])])

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

(defn prepare-for-combobox [ds]
  (transduce
    (comp
      (map (fn [[k v]] (assoc v :id k)))
      (map (fn [{:keys [id navn]}] {:id id :name navn})))
    conj [] ds))

(defn boat-form [{:keys [props values]}]
  (let [{:keys [id boat-type boat-types]} values
        boat-types (->> boat-types
                        prepare-for-combobox)]
    [sc/surface-a
     [b/co {:class [:frame :p-4]}
      [b/rof
       [b/co
        [sci/combobox
         props
         {:items     boat-types
          #_#_:sidecar-right
          [b/ro
           [button/icon-and-caption
            {:style {:border-radius "var(--radius-1)"
                     :border        "2px solid white"}
             :class [:field :flip]}
            ico/arrowRight'
            "Gå til"]
           [button/just-icon {:class [:frame]} ico/trash]
           [button/icon-and-caption
            {:xstyle {:border-radius "var(--radius-1)"
                      :border        "2px solid white"}
             :class  [:field :light :left-square :right-squarex]}
            ico/arrowRight'
            "Gå fra"]]
          :class     [:w-full]
          :fieldname :boat-type
          :label     "Båt-type"}]

        [:div.flex.justify-end.w-full
         {:style {:user-select :all}}
         [b/small
          [b/rof
           [b/co0
            [b/ro
             (l/strp "TYPE:" boat-type)
             (widgets/data-url {:checked-path ["boat-brand" boat-type]})]
            [:div.self-end
             [b/ro {:class [:text-right]}
              [b/small (l/strp "ID:" id)]
              (widgets/data-url {:checked-path ["boad-item" id]})]]]]]]]]]]))

(defn modal-boatinfo-windowcontent [{:keys [data on-close uid admin?]}]
  (let [{:keys [id boat-type]} data
        boat-item-id (some-> id name)
        boat-item-id (when (< 3 (count boat-item-id))
                       boat-item-id)]
    [sc/dialog-dropdown
     [:div
      {:style {:padding-bottom   "2rem"
               :z-index          10
               :background-color "var(--toolbar)"}}
      [header uid data]]

     [:div.sticky.top-4.z-10
      {:style {:margin-top "-1.4rem"}}
      [sc/row-center
       (widgets/pillbar {:class [:small]} selected-tab
                        [[:feil "Mangler"]
                         (if admin? [:arbeidsliste "Arbeidsliste"])
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
                    :padding-block-end "1rem"
                    :padding-inline    "1rem"}}

          (if @selected-tab
            (into [:div.px-8.py-4]
                  [(case @selected-tab
                     :feil [insert-damage props]

                     :arbeidsliste (when boat-item-id
                                     [insert-worklog
                                      boat-item-id
                                      #_(r/atom [[1 {:id          1
                                                     :description "desc"
                                                     :state       "state"
                                                     :deleted     false}]])
                                      (db/on-value-reaction {:path ["boad-item" boat-item-id "work-log"]})])

                     [boat-form props])])
            [:div.h-8])]
         (if dirty
           [bottom-button-panel
            [sc/row-sc-g2]
            [:div {:style {:flex "1"}}]
            [button/just-caption
             {:type     :button
              :class    [:regular :normal]
              :xstyle   {:background-color "var(--toolbar)"
                         :color            "var(--buttoncopy)"}
              :on-click on-close}
             "Avbryt"]
            [button/just-caption
             {:type     :submit
              :disabled false
              :class    [:normal :cta]}
             "Lagre"]]
           [bottom-button-panel
            [:div {:style {:flex "1"}}]
            [button/just-caption
             {:type     :button
              :class    [:normal]
              :style    {:background-color "var(--toolbar)"
                         :color            "var(--buttoncopy)"}
              :on-click on-close}
             "Lukk"]])])]]))

;; main accessor

(defn open-modal-boatinfo [{:keys [data]}]
  (let [uid @(rf/subscribe [:lab/uid])
        admin? @(rf/subscribe [:lab/admin-access])]
    (rf/dispatch-sync [:modal.slideout/clear])
    (rf/dispatch [:modal.slideout/show
                  {:data          data
                   :uid           uid
                   :admin?        admin?
                   :on-flag-click (fn [boat-type value]
                                    (rf/dispatch [:star/write-star-change
                                                  {:boat-type boat-type
                                                   :value     value
                                                   :uid       uid}]))
                   :content-fn    #(modal-boatinfo-windowcontent %)}])))
