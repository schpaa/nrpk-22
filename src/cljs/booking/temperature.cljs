(ns booking.temperature
  (:require [fork.reagent :as fork]
            [tick.core :as t]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as field]
            [schpaa.style.hoc.buttons :as button]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [re-frame.core :as rf]
            [db.core :as db]
            [reagent.core :as r]))

(def weather-words
  [[0 "Tåke"]
   [2 "Pent vær"]
   [2 "Overskyet"]
   [2 "Lettskyet"]
   [2 "Skyet"]
   [1 "Vindstille"]
   [1 "Vind"]
   [1 "Bris"]
   [1 "Kraftig vind"]
   [3 "Yr"]
   [3 "Litt nedbør"]
   [3 "Styrtregn"]])


(defonce gunk (r/atom {:gunk "test"
                       :luft "123"
                       :vann ""
                       :testfield "data"}))

(defn temperatureform-content [{:keys [data on-close uid write-fn]}]
  (let [
        validation-fn                                    
        (fn [{:keys [vann luft vær] :as values}]
          (tap> values)
          (into {}
                (remove (comp nil? val)
                        {:vann (cond-> nil
                                 (empty? vann) ((fnil conj []) "mangler")
                                 (not= vann (str (js/parseFloat vann))) ((fnil conj []) "er feil"))
                         :luft (cond-> nil
                                 (empty? luft) ((fnil conj []) "mangler")
                                 (not= luft (str (js/parseFloat luft))) ((fnil conj []) "er feil"))
                         :vær  (cond-> nil
                                 (empty? (filter (comp true? val) vær)) ((fnil conj []) "mangler"))})))]
    [fork/form
     {:initial-values      {}
      :prevent-default?    true
      :state               gunk
      :keywordize-keys     true
      :validation          validation-fn
      :component-did-mount (fn [{:keys [set-values set-untouched] :as v}]
                             (set-values @gunk)
                             (set-untouched :luft))
      :on-submit           (fn [{:keys [values]}]
                             (let [data (assoc values
                                          :timestamp (str (t/now))
                                          :uid uid)]
                               (when write-fn
                                 (write-fn data))
                               (on-close)))}
     (fn [{:keys [handle-submit form-id values set-values handle-change] :as props}]
       [sc/dialog-dropdown
        [sc/col-space-8
         [sc/col-space-8
          {:style {:padding          "1rem"
                   :background-color "var(--floating)"}}
          [:div.px-1 [sc/dialog-title "Registrer luft og vanntemperatur"]]
          [:form
           {:id        form-id
            :on-submit handle-submit}

           [sc/col-space-8
            [field/textinput props "A testfield" :testfield]

            [sc/row-sc-g4 {:style {:gap "2rem"}
                           :class [:items-start]}
             [sc/col-space-4 {:class [:w-32]}
              [field/textinput (assoc props
                                 :type "number"
                                 :values {:luft (:luft values)}) "Luft" :luft]
              [field/textinput (assoc props
                                 :type "number") "Vann" :vann]]
             [sc/checkbox-matrix
              {:style {:padding-topx "1rem"}}
              (into [:<>]
                    (for [[_ e] (group-by first weather-words)
                          [_ e] e]
                      [hoc.toggles/largeswitch-local''
                       {:get     #(get-in values [:vær e])
                        :set     #(do
                                    (tap> e)
                                    (set-values (assoc-in values [:vær e] %)))
                        :view-fn (fn [t c v]
                                   [sc/row-sc-g2 t
                                    [(if v sc/text1 sc/text0) c]])
                        :caption e}]))]]
            [sc/row-field
             [:div.grow]
             [button/just-caption {:class    [:regular :normal]
                                   :on-click on-close
                                   :type     :button} "Avbryt"]
             [button/just-caption {:type     :submit
                                   :disabled (not (empty? (validation-fn values)))
                                   :class    [:cta :normal]} "Lagre"]]]]]]])]))

(defn add-temperature []
  (let [uid @(rf/subscribe [:lab/uid])
        data {}]
    (rf/dispatch [:modal.slideout/show
                  {:data       data
                   :uid        uid
                   :write-fn   #(let [data (-> %
                                               (assoc :version booking.data/TEMPERATURE-VERSION)
                                               (update :luft js/parseFloat)
                                               (update :vann js/parseFloat))]
                                  (db/database-push {:path  ["temperature"]
                                                     :value data}))
                   :content-fn #(temperatureform-content %)}])))

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
