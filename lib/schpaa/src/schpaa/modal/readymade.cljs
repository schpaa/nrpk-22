(ns schpaa.modal.readymade
  (:require [schpaa.modal :as modal]
            [schpaa.components.fields :as fields :refer [save-ref placeholder]]
            [schpaa.components.views :refer [number-view slot-view normalize-kind name-view]]
            [reagent.core :as r]
            [fork.re-frame :as fork]
            [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [db.core :as db]
            [schpaa.debug :as l]))

(defn modal-form [& {:keys [store]}]
  (let [ref (r/atom nil)]
    (fn []
      [fork/form {:prevent-default?    true
                  :initial-values      {:feilmelding ""}
                  :clean-on-unmount?   true
                  :component-did-mount (fn [_]
                                         (when-let [r @ref]
                                           (.focus r)))
                  :keywordize-keys     true
                  :on-submit           (fn [{:keys [values]}]
                                         (store values)
                                         (eykt.fsm-helpers/send :e.hide))}
       (fn [{:keys [dirty handle-submit form-id values] :as props}]
         (if dirty
           (eykt.fsm-helpers/send :e.dirty)
           (eykt.fsm-helpers/send :e.clean))
         [:form
          {:id        form-id
           :on-submit handle-submit}

          [:div.space-y-4.p-4
           (fields/textarea (-> props
                                (save-ref ref)
                                (placeholder "Kort beskrivelse")) "Meld om feil" :feilmelding)

           [:div.flex.justify-end.gap-4
            [:button.btn.btn-free
             {:type     :button
              :on-click #(eykt.fsm-helpers/send :e.hide)} "Avbryt"]
            [:button.btn.btn-danger
             {:disabled (not (some? dirty))
              :type     :submit} "Lagre"]]]])])))

(defn toggle-favorite [id]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    (tap> ["toggle-fav" id (:uid @user-auth)])))

(defn modal-header
  "A view that is a presentation of the boat with some details and a Star to click on"
  [{:keys [toggle-favorite-fn read-db-fn]}]
  (let [star [:div {:on-click #(toggle-favorite-fn)
                    :class    ["text-amber-500"]} [icon/small :star]]
        {:keys [slot number navn kind description]} (read-db-fn)]

    [:div.grid.gap-2.p-4.text-base.font-normal.w-full
     {:class ["dark:text-white" :text-white]
      :style {:grid-template-columns "3rem 1fr min-content"}}
     [:div.self-start.justify-self-center (number-view number)]
     [:div.leading-snug
      (name-view navn)
      [:div.text-sm
       {}                                                   ;:class ["dark:text-gray-400" "text-gray-500"]}
       (normalize-kind kind)]]
     [:div (slot-view slot)]
     [:div.justify-self-center.self-start.pt-px star]
     [:div.col-span-2.font-normal.text-base
      ;{:class ["dark:text-gray-400" "text-gray-500"]}
      description]]))

;todo move modal-title outside and pass it as an arg: `[modal-title, id]`
(defn details-dialog-fn [id]
  (modal/form-action
    {:flags   #{:weak-dim}
     :header  (modal-header
                {:read-db-fn         (fn [] (get (logg.database/boat-db) id))
                 :toggle-favorite-fn (fn [] (toggle-favorite id))})
     :form-fn (modal-form :store (fn [values] (tap> ["save settings " values])))
     :footer  "Trykk på stjernen for å markere som favoritt"}))

;region booking

(defn modal-booking-details-dialogcontent
  ""
  [id]
  (let [read-db-fn (fn [] (get (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) id))
        {:keys [start end selected slot number navn kind description] :as m} (read-db-fn)]

    [:div.grid.gap-2.p-4.text-base.font-normal.w-full
     {:class ["dark:text-white" :text-black]
      :style {:grid-template-columns "3rem 1fr min-content"}}
     [:div.col-span-3 "modal booking title"]

     [:div.col-span-3 [l/ppre m]]

     [:div.col-span-3
      [:div start]
      [:div end]
      [:div (l/ppr selected)]]]))

(defn simple-ok-form []
  (let [ref (r/atom nil)]
    (fn []
      [fork/form {:prevent-default?    true
                  :initial-values      {}
                  :clean-on-unmount?   true
                  :component-did-mount (fn [_]
                                         (when-let [r @ref]
                                           (.focus r)))
                  :keywordize-keys     true
                  :on-submit           (fn [{:keys [values]}]
                                         (eykt.fsm-helpers/send :e.hide))}
       (fn [{:keys [dirty handle-submit form-id values] :as props}]
         (if dirty
           (eykt.fsm-helpers/send :e.dirty)
           (eykt.fsm-helpers/send :e.clean))
         [:form
          {:id        form-id
           :on-submit handle-submit}
          [:div.space-y-4.p-4
           [:div.flex.justify-end.gap-4
            [:button.btn.btn-free
             {:type :submit} "Ok"]]]])])))

(defn booking-details-dialog-fn [title-fn]
  (modal/form-action
    {:flags   #{:weak-dim}
     :header  title-fn
     :form-fn (simple-ok-form)}))

;endregion

(defn are-you-sure [{:keys [action] :as m}]
  (modal/form-action
    (conj m
          {:flags   #{:weak-dim}
           :form-fn (modal/ok-cancel-buttons
                      {:on-ok     #(do
                                     (eykt.fsm-helpers/send :e.hide)
                                     (action))
                       :on-cancel #(eykt.fsm-helpers/send :e.hide)})})))


(defn modal-booking-title2
  ""
  [m]
  #_(let [read-db-fn (fn [] (get (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) id))
          {:keys [start end selected slot number navn kind description] :as m} (read-db-fn)])

  [:div.grid.gap-2.p-4.text-base.font-normal.w-full
   {:class ["dark:text-white" :text-black]
    :style {:grid-template-columns "3rem 1fr min-content"}}
   [:div.col-span-3 "modal booking title"]

   [:div.col-span-3 [l/ppre m]]

   #_[:div.col-span-3
      [:div start]
      [:div end]
      [:div (l/ppr selected)]]])

(defn modal-form2
  "provide a store function"
  [{:keys [store]}]
  (fn []
    [fork/form {:prevent-default?  true
                :initial-values    {:description "sample"}

                :clean-on-unmount? true
                :keywordize-keys   true
                :on-submit         (fn [{:keys [values]}]
                                     (store values)
                                     (eykt.fsm-helpers/send :e.hide))}
     (fn [{:keys [dirty handle-submit form-id values] :as props}]
       (if dirty
         (eykt.fsm-helpers/send :e.dirty)
         (eykt.fsm-helpers/send :e.clean))
       [:form
        {:id        form-id
         :on-submit handle-submit}

        [:div.space-y-4.p-4
         [fields/textarea (-> props (assoc :auto-focus 1)) :label "Melding til turkamerater" :name :description]

         [:div.flex.justify-end.gap-4
          [:button.btn.btn-free
           {:type     :button
            :on-click #(eykt.fsm-helpers/send :e.hide)} "Avbryt"]
          [:button.btn.btn-cta
           {;:disabled (not (some? dirty))
            :type :submit} "Bekreft"]]]])]))

(defn confirm-booking [{:keys [start end selected] :as m}]
  (let [uid (:uid @(rf/subscribe [::db/user-auth]))]
    (modal/form-action
      {:flags   #{}
       :header  (modal-booking-title2 m)
       :form-fn (modal-form2 {:store (fn [values]
                                       (let [values (conj m values {:uid uid})]
                                         (tap> ["save settings " values])
                                         (booking.database/write {:uid uid :value values})))})})))

