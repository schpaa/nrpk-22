(ns booking.temperature
  (:require [db.core :as db]
            [booking.style.table :as tsc]
            [tick.core :as t]
            [reagent.core :as r]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [re-frame.core :as rf]))

(defn field [idx k value focus-field-id act]
  (r/with-let [value (r/atom value)
               set-fn (fn [k v] (db/database-update
                                 {:path  ["users" (name k)]
                                  :value {:timestamp (str (t/now))
                                          :saldo     (js/parseInt v)}}))]
    [:div
     {:style {:overflow      :hidden
              :width         "4rem"
              :height        "2rem"
              :margin        0
              :padding       0
              :border-radius "var(--radius-0)"
              :border        "2px solid var(--toolbar)"}}
     (if (= idx @focus-field-id)
       [:input {:auto-focus  true
                :ref         (fn [el] (if-not @act (reset! act el)))
                :type        :text
                :value       @value
                :on-key-down #(cond
                                (= 13 (.-keyCode %))
                                (do (set-fn k @value) (reset! focus-field-id nil))
                                (= 27 (.-keyCode %))
                                (do (reset! focus-field-id nil)))
                :on-change   #(do
                                (tap> {:key (.-keyCode %)})
                                (reset! value (-> % .-target .-value)))
                :on-blur     #(let [v (-> % .-target .-value)]
                                (if (empty? v)
                                  (do #_(swap! light assoc-in [e :text] (str "uten-tittel-" e)))
                                  (do (set-fn k @value) (reset! focus-field-id nil)))
                                (reset! act nil))
                :style       {;:background    "white"
                              :overflow       :hidden
                              :padding-inline "var(--size-2)"
                                        ;:padding-block "var(--size-2)"
                                        ;:border        "2px solid var(--toolbar)"
                              :height         "100%"}
                :class       [:-debug :m-0 :xpy-0 :cursor-text
                              :focus:outline-none :w-full]}]
       [sc/text1 {:on-click #(do
                               (reset! focus-field-id idx)
                               (reset! value value)
                               (when @act
                                 (do
                                   (tap> "Attempt focus")
                                   (.focus @act)))
                               #_(.stopPropagation %))
                  :style    {;:padding-block     "var(--size-2)"
                                        ;:overflow :clip
                                        ;:background-color  "white"
                                        ;:vertical-align :middle
                             :padding-inline    "var(--size-2)"
                             :display           :flex
                             :align-items       :center
                             :height            "100%"
                                        ;:height            "2rem"

                             :xbackground-color "var(--toolbar)"}
                  :class    [:tabular-nums :tracking-tight :cursor-text
                             :w-full]}
        (or (when value (str value "t")) "—")])]))

(defn field2 [focus k field-name current-value]
  (let [ref (r/atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [_]
        (tap> [:component-did-mount @ref])
        (when @ref
          (.setSelectionRange @ref 0 12)))

      :reagent-render
      (fn [focus k field-name current-value]
        (let [have-focus? (= @focus {:k          k
                                     :field-name field-name})]
          [:td.h-full.w-24
           {:style {:background-color (when have-focus? "white")
                    :padding-inline   "8px"
                    :padding-block    0
                    :margin           0}}
           (if have-focus?
             [:input
              {:ref        #(when-not ref
                              (reset! ref %))
               :style      {;:background "green"
                            :margin  0
                            :padding 0
                            :height  "100%"
                            :width   "100%"}
               :class      []
               :auto-focus 1
               :type       "text"
               :value      current-value}]
             [:div {:on-click #(reset! focus {:k          k
                                              :field-name field-name})} current-value])]))})))

(defonce st (r/atom {}))

(defn header [attr]
  [:thead attr
   [:tr
    [:th.w-32 "Når?"]
    [:th.w-24 "Vann"]
    [:th.w-24 "Luft"]
    [:th.w-32 "Vær"]]])

(def local-items (r/cursor st [:some]))

(defn new-data [st]
  [sc/co
   [tsc/table-report
    (header {})
    [:tbody
     (let [k "new" vann 0 luft 0 vær 0]
       (for [[k {:keys [date vann luft vær]}] @local-items]
         [:tr {:class [:today]}
          [:td (some-> (t/date-time) t/date-time times.api/logg-date-format)]
          [field2 st k :vann vann]
          [field2 st k :luft luft]
          [field2 st k :vær vær]]))]]

   [sc/surface-ab
    [sc/row-center [hoc.buttons/pill {:on-click #(rf/dispatch [:lab/toggle-boatpanel nil])
                                      :class    [:large :cta]} [sc/icon ico/plus]]]]])

(defn old-data [st data]
  [sc/co
   [tsc/table-report
    (header {:style {:display :invisible}})
    [:tbody.archive
     (for [[k {:keys [date vann luft vær]}] data]
       [:tr
        [:td "date" #_(some-> date t/date-time times.api/date-format-sans-year)]
        [field2 st k :vann vann]
        [field2 st k :luft luft]
        [field2 st k :vær vær]])]]])

(defn render [r]
  (r/with-let []
    (let [data (db/on-value-reaction {:path ["temperature"]})]
      [tsc/table-controller-report'
       [sc/col-space-8
        [l/pre data]
        [new-data st]
        [old-data st (take 5 @data)]]])))
