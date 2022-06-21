(ns booking.temperature
  (:require [fork.reagent :as fork]
            [tick.core :as t]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as field]
            [schpaa.style.hoc.buttons :as button]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [re-frame.core :as rf]
            [db.core :as db]
            [reagent.core :as r]
            [schpaa.debug :as l]
            [booking.styles :as b :refer [ro surface text title co0 co]]
            [booking.ico :as ico]))

(def weather-words
  [#_[0 "Tåke"
      0 "Dårlig sikt"]
   [1 "Klart vær"]
   [1 "Lettskyet"]
   [1 "Delvis skyet"]
   [1 "Skyet"]
   [2 "Vindstille"]
   [2 "Svak vind"]
   [2 "Bris"]
   [2 "Frisk bris"]
   [3 "Opphold"]
   [3 "Litt nedbør"]
   [3 "Regn"]])

(def gunk (r/atom {:vær       {"Klart vær" true}
                   :luft      ""
                   :vann      ""
                   :date      (t/date (t/now))
                   :time      (t/truncate (t/time (t/now)) :minutes)
                   :testfield "data"}))

(defn validation-fn [{:keys [vann luft vær date time] :as values}]
  (tap> values)
  (into {}
        (remove (comp nil? val)
                {:date (cond-> nil
                         (nil? (try (some-> date t/date) (catch js/Error _ nil))) ((fnil conj []) "mangler"))

                 :time (cond-> nil
                         (nil? (try (some-> time t/time) (catch js/Error _ nil))) ((fnil conj []) "mangler"))

                 :vann (cond-> nil
                         (empty? vann) ((fnil conj []) "mangler")
                         (not= vann (str (js/parseFloat vann))) ((fnil conj []) "har feil format"))
                 :luft (cond-> nil
                         (empty? luft) ((fnil conj []) "mangler")
                         (not= luft (str (js/parseFloat luft))) ((fnil conj []) "har feil format"))
                 :vær  (cond-> nil
                         (empty? (filter (comp true? val) vær)) ((fnil conj []) "mangler"))})))

(comment
  (do
    (validation-fn {:vann "1"})))

(defn ensure [uid component]
  (if-not uid
    [sc/surface-a [b/text {:class []} "Ingen tilgang"]]
    component))

(defn is-this-today? [dt])

(defn temperature-form-content [{:keys [on-close uid write-fn]}]
  (ensure uid
          [fork/form
           {:prevent-default?    true
            :keywordize-keys     true
            :validation          #(validation-fn %)
            :component-did-mount (fn [{:keys [set-values set-untouched set-touched]}]
                                   (set-values @gunk))
            :on-submit           (fn [{:keys [values]}]
                                   (let [data (assoc values
                                                :timestamp (str (t/now))
                                                :uid uid)]
                                     (when write-fn
                                       (write-fn data))
                                     (on-close)))}
           (fn [{:keys [handle-submit form-id values set-values handle-change] :as props}]
             #_[:div.bg-white "test"]
             [sc/dialog-dropdown
              [co0
               {:style {:background-color "var(--floating)"}}

               [sc/dialog-title "Registrer vær"]
               [:form
                {:id        form-id
                 :on-submit handle-submit}
                [b/co
                 [surface {:style {:width          "100%"
                                   :padding-inline "1rem"}
                           :class [:emboss]}
                  [ro {:class [:space-x-4]
                       :style {:align-items     :start
                               :justify-content :start}}
                   [co
                    [field/dateinput props "Dato" :date]
                    [ro {:style {:align-items :start}}
                     [button/just-caption
                      {:disabled (t/= (t/today) (values :date))
                       :on-click #(set-values {:time (t/truncate (t/time (t/now)) :minutes)
                                               :date (t/date (t/now))})
                       :class    [:regular]} "Nå"]

                     [button/just-icon
                      {:type     "button"
                       :on-click #(set-values {:time (t/truncate (t/time (t/now)) :minutes)
                                               :date (t/<< (t/date (values :date)) (t/new-period 1 :days))})
                       :class    [:round :message]
                       :disabled (empty? (str (values :date)))}
                      [sc/icon-small ico/arrowLeft']]

                     [button/just-icon
                      {:on-click #(set-values {:time (t/truncate (t/time (t/now)) :minutes)
                                               :date (t/>> (t/date (values :date)) (t/new-period 1 :days))})
                       :disabled
                       (or
                         (empty? (str (values :date)))
                         (when-not (empty? (str (values :date)))
                           (when-let [dt (some-> (values :date) t/date)]
                             (t/< (t/yesterday) dt))))
                       :class    [:round :message]}
                      [sc/icon-small ico/arrowRight']]]]
                   [co
                    [field/timeinput props "Klokke" :time]
                    [ro
                     [button/just-caption
                      {:type     "button"
                       :on-click #(set-values {:time (t/time "11:00")})
                       :class    [:frame]} "11:00"]
                     [button/just-caption
                      {:type     "button"
                       :on-click #(set-values {:time (t/time "14:00")})
                       :class    [:frame]} "14:00"]
                     [button/just-caption
                      {:type     "button"
                       :on-click #(set-values {:time (t/time "18:00")})
                       :class    [:frame]} "18:00"]]]]]
                 [sc/row-sc-g4 {:style {:gap            "2rem"

                                        :padding-inline "1rem"}
                                :class [:items-start]}
                  [b/co {:style   {:flex "1"}
                         :class [:self-start]}
                   [field/textinput (assoc props
                                      :type "text"
                                      :autofocus true
                                      :values {:luft (:luft values)}) [:span "Luft" [:sup "ºC"]] :luft]
                   [field/textinput (assoc props
                                      :type "text") [:span "Vann" [:sup "ºC"]] :vann]]
                  [sc/surface-a {:style {:flex "3"}}
                   [sc/checkbox-matrix
                    (into [:<>]
                          (for [[_ e] (group-by first weather-words)]
                            [:div.pb-1
                             (for [[_ e] e]
                               [:div.pb-1
                                [hoc.toggles/largeswitch-squared
                                 {:get     #(get-in values [:vær e])
                                  :set     #(set-values (assoc-in values [:vær e] %))
                                  :view-fn (fn [t c v]
                                             [sc/row-sc-g2 t
                                              [(if v sc/text1 sc/text0) c]])
                                  :caption e}]])]))]]]
                 [sc/row-field {:style {:padding "1rem"
                                        :width   "100%"}}
                  [:div.grow]
                  [button/just-caption {:class    [:regular :normal]
                                        :on-click on-close
                                        :type     :button} "Avbryt"]
                  [button/just-caption {:type     :submit
                                        :disabled (not (empty? (validation-fn values)))
                                        :class    [:cta :normal]} "Lagre"]]]]]])]))

(defn add-temperature [uid]
  (let [data {}]
    (rf/dispatch [:modal.slideout/show
                  {:data       data
                   :uid        uid
                   :write-fn   #(let [data (-> %
                                               (assoc :version booking.data/TEMPERATURE-VERSION)
                                               (update :luft js/parseFloat)
                                               (update :vann js/parseFloat))]
                                  (db/database-push {:path  ["temperature"]
                                                     :value data}))
                   :content-fn #(temperature-form-content %)}])))

;(ns booking.temperature
;  (:require [booking.ico :as ico]
;            [booking.style.table :as tsc]
;            [db.core :as db]
;            [re-frame.core :as rf]
;            [reagent.core :as r]
;            [schpaa.debug :as l]
;            [schpaa.style.hoc.buttons :as hoc.buttons]
;            [schpaa.style.ornament :as sc]
;            [tick.core :as t]))
;
;(defn field [idx k value focus-field-id act]
;  (r/with-let [value (r/atom value)
;               set-fn (fn [k v] (db/database-update
;                                 {:path  ["users" (name k)]
;                                  :value {:timestamp (str (t/now))
;                                          :saldo     (js/parseInt v)}}))]
;    [:div
;     {:style {:overflow      :hidden
;              :width         "4rem"
;              :height        "2rem"
;              :margin        0
;              :padding       0
;              :border-radius "var(--radius-0)"
;              :border        "2px solid var(--toolbar)"}}
;     (if (= idx @focus-field-id)
;       [:input {:auto-focus  true
;                :ref         (fn [el] (if-not @act (reset! act el)))
;                :type        :text
;                :value       @value
;                :on-key-down #(cond
;                                (= 13 (.-keyCode %))
;                                (do (set-fn k @value) (reset! focus-field-id nil))
;                                (= 27 (.-keyCode %))
;                                (do (reset! focus-field-id nil)))
;                :on-change   #(do
;                                (tap> {:key (.-keyCode %)})
;                                (reset! value (-> % .-target .-value)))
;                :on-blur     #(let [v (-> % .-target .-value)]
;                                (if (empty? v)
;                                  (do #_(swap! light assoc-in [e :text] (str "uten-tittel-" e)))
;                                  (do (set-fn k @value) (reset! focus-field-id nil)))
;                                (reset! act nil))
;                :style       {;:background    "white"
;                              :overflow       :hidden
;                              :padding-inline "var(--size-2)"
;                                        ;:padding-block "var(--size-2)"
;                                        ;:border        "2px solid var(--toolbar)"
;                              :height         "100%"}
;                :class       [:-debug :m-0 :xpy-0 :cursor-text
;                              :focus:outline-none :w-full]}]
;       [sc/text1 {:on-click #(do
;                               (reset! focus-field-id idx)
;                               (reset! value value)
;                               (when @act
;                                 (do
;                                   (tap> "Attempt focus")
;                                   (.focus @act)))
;                               #_(.stopPropagation %))
;                  :style    {;:padding-block     "var(--size-2)"
;                                        ;:overflow :clip
;                                        ;:background-color  "white"
;                                        ;:vertical-align :middle
;                             :padding-inline    "var(--size-2)"
;                             :display           :flex
;                             :align-items       :center
;                             :height            "100%"
;                                        ;:height            "2rem"
;
;                             :xbackground-color "var(--toolbar)"}
;                  :class    [:tabular-nums :tracking-tight :cursor-text
;                             :w-full]}
;        (or (when value (str value "t")) "—")])]))
;
;(defn field2 [focus k field-name current-value]
;  (let [ref (r/atom nil)]
;    (r/create-class
;     {:component-did-mount
;      (fn [_]
;        (tap> [:component-did-mount @ref])
;        (when @ref
;          (.setSelectionRange @ref 0 12)))
;
;      :reagent-render
;      (fn [focus k field-name current-value]
;        (let [have-focus? (= @focus {:k          k
;                                     :field-name field-name})]
;          [:td.h-full.w-24
;           {:style {:background-color (when have-focus? "white")
;                    :padding-inline   "8px"
;                    :padding-block    0
;                    :margin           0}}
;           (if have-focus?
;             [:input
;              {:ref        #(when-not ref
;                              (reset! ref %))
;               :style      {;:background "green"
;                            :margin  0
;                            :padding 0
;                            :height  "100%"
;                            :width   "100%"}
;               :class      []
;               :auto-focus 1
;               :type       "text"
;               :value      current-value}]
;             [:div {:on-click #(reset! focus {:k          k
;                                              :field-name field-name})} current-value])]))})))
;
;(defonce st (r/atom {}))
;
;(defn header [attr]
;  [:thead attr
;   [:tr
;    [:th.w-32 "Når?"]
;    [:th.w-24 "Vann"]
;    [:th.w-24 "Luft"]
;    [:th.w-32 "Vær"]]])
;
;(def local-items (r/cursor st [:some]))
;
;(defn new-data [st]
;  [sc/co
;   [tsc/table-report
;    (header {})
;    [:tbody
;     (let [k "new" vann 0 luft 0 vær 0]
;       (for [[k {:keys [date vann luft vær]}] @local-items]
;         [:tr {:class [:today]}
;          [:td (some-> (t/date-time) t/date-time times.api/logg-date-format)]
;          [field2 st k :vann vann]
;          [field2 st k :luft luft]
;          [field2 st k :vær vær]]))]]
;
;   [sc/surface-ab
;    [sc/row-center [hoc.buttons/pill {:on-click #(rf/dispatch [:lab/toggle-boatpanel nil])
;                                      :class    [:large :cta]} [sc/icon ico/plus]]]]])
;
;(defn old-data [st data]
;  [sc/co
;   [tsc/table-report
;    (header {:style {:display :invisible}})
;    [:tbody.archive
;     (for [[k {:keys [date vann luft vær]}] data]
;       [:tr
;        [:td "date" #_(some-> date t/date-time times.api/date-format-sans-year)]
;        [field2 st k :vann vann]
;        [field2 st k :luft luft]
;        [field2 st k :vær vær]])]]])
;
;(defn render [r]
;  (r/with-let []
;    (let [data (db/on-value-reaction {:path ["temperature"]})]
;      [tsc/table-controller-report'
;       [sc/col-space-8
;        [l/pre data]
;        [new-data st]
;        [old-data st (take 5 @data)]]])))
