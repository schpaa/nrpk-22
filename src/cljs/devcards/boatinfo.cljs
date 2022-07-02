(ns devcards.boatinfo
  (:require-macros [devcards.core :refer [defcard-rg]])
  (:require [booking.modals.boatinfo :as sud]
            [tick.core :as t]
            [fork.reagent :as fork]
            [reagent.core :as r]
            [schpaa.style.input :as sci]
            [booking.styles :as b]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as button]))


(def uid "RX12902139012309123")

(defcard-rg incomplete
  [:div
   (sud/worklog-card {} {:description "Description"
                         :uid         uid
                         :complete    false
                         :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg incomplete-long-description
  ;"-long-description"
  [:div
   (sud/worklog-card {}
                     {:description "Description -long-description -long-description -long-description -long-description -long-description -long-description -long-description"
                      :uid         uid
                      :extra       false
                      :keywords    false
                      :complete    false
                      :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defcard-rg completed
  [:div
   (sud/worklog-card {} {:description "Description"
                         :uid         uid
                         :complete    true
                         :timestamp   (t/now)} 'boat-item-id 'worklog-entry-id)])

(defonce st (r/atom {}))

(defn default-form-wrapper [{:keys [content-fn on-submit validation-fn current-values state]}]
  [fork/form
   {:prevent-default?    true
    :keywordize-keys     true         
    :form-id             "form-id-100"
    ;:initial-values current-values        
    :validation          #(validation-fn %)
    ;:component-will-unmount (fn [_] (js/alert "!"))
    :component-did-mount (fn [{:keys [set-values set-touched set-untouched] :as f}]
                           ;(tap> [:component-did-mount
                           ;       current-values
                           ;       (keys f)])
                           (set-untouched current-values))
    :on-submit           (fn [{:keys [values]}]
                           (let [data (assoc values
                                        :timestamp (str (t/now))
                                        :uid uid)]
                             (on-submit data)))
    :state               state}
   (fn [{:keys [values form-id handle-submit] :as props}]
     [:form.p-0 {:id        form-id
                 :on-submit handle-submit}
      ;(tap> values)
      [b/co
       (when content-fn (content-fn props))
       [l/pre-s values]]])])

(def state (r/atom {:boat-type 1}))

(defn prepare-for-combobox [ds]
  (into []
        (comp
          (map (fn [[k {:keys [navn]}]] {:id k :name navn})))
        ds))

(defcard-rg data
  (fn [state _]
    (r/with-let [boat-types #_(r/atom {"id1" {:navn "name"}}) (db.core/on-value-reaction {:path ["boat-brand"]})]
      [:div {:style {:background-color "var(--pink-2)"
                     :padding          "1rem"}}
       ;[l/pre-s @boat-types]    
       (default-form-wrapper
         {:state          state
          :on-submit      (fn [values] (js/alert values))
          :current-values {:boat-type (name :-Me9qgkllZSqK9rccZxe)
                           :textfield "abasdc"}
          :validation-fn  (fn [{:keys [textfield boat-type]}]
                            (into {} (remove (comp nil? val)
                                             {:boat-type (cond-> nil
                                                           (nil? boat-type)
                                                           ((fnil conj []) "mangler"))
                                              :textfield (cond-> nil
                                                           (empty? textfield)
                                                           ((fnil conj []) "mangler"))})))

          :content-fn     (fn [{:keys [values errors dirty reset] :as props}]
                            [b/co
                             ;[l/pre-s values]
                             [sci/combobox
                              props
                              {:items     (prepare-for-combobox @boat-types)
                               :on-add    #(swap! boat-types assoc :new {:navn %})
                               :class     [:w-full]
                               :label     "Båt-type"
                               :fieldname :boat-type}]

                             [sci/combobox
                              props
                              {:items     (prepare-for-combobox {:add-item {:navn "Legg til"}
                                                                 :new-item {:navn "Ny"}})
                               :on-add    #()
                               :class     [:w-full]
                               :label     "Selector"
                               :fieldname :selector}]

                             #_(sud/boat-form
                                 {:props  props
                                  :values {:boat-type  "sample-boat-type"
                                           :id         "sample-id"
                                           :boat-types @(db.core/on-value-reaction {:path ["boat-brand"]})}})
                             [sci/input props :text [] "Test" :textfield]

                             (let [ok? (nil? errors)]
                               [b/roe
                                [button/just-caption {:type  :button
                                                      :class [:normal :frame]} "Help"]
                                [:div.grow]
                                [button/just-caption {:type     :reset
                                                      :disabled (not dirty)
                                                      :on-click #(reset)
                                                      :class    [:normal :regular]} "Reset"]
                                [button/just-caption {:type     :submit
                                                      :disabled (not ok?)
                                                      :class    [:normal :cta]} "Save"]])])})]))
  (r/atom {:test "abc"})
  {:inspect-values true})


