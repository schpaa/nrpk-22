(ns schpaa.modal.readymade
  (:require [schpaa.modal :as modal]
            [schpaa.components.fields :as fields :refer [save-ref placeholder]]
            [schpaa.components.views :refer [number-view slot-view normalize-kind name-view]]
            [reagent.core :as r]
            [fork.re-frame :as fork]
            [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [nrpk.fsm-helpers]
            [db.core :as db]
            [schpaa.debug :as l]
            [logg.database]
            [booking.database]
            [schpaa.button :as bu]))

(defn modal-form [{:keys [form-id ok]}]
  (let [ref (r/atom nil)]
    [fork/form {:prevent-default?    true
                :form-id             form-id
                :initial-values      {:feilmelding ""}
                :clean-on-unmount?   true
                :component-did-mount (fn [_]
                                       (when-let [r @ref]
                                         (.focus r)))
                :keywordize-keys     true
                :on-submit           (fn [{:keys [values]}]
                                       (ok values)
                                       (nrpk.fsm-helpers/send :e.hide))}
     (fn [{:keys [dirty handle-submit form-id] :as props}]
       (if dirty
         (nrpk.fsm-helpers/send :e.dirty)
         (nrpk.fsm-helpers/send :e.clean))
       [:form
        {:id        form-id
         :on-submit handle-submit}
        [:div.space-y-4
         (fields/textarea (-> props
                              (assoc :auto-focus 1)
                              (save-ref ref)
                              (placeholder "Kort beskrivelse")) :label "Meld om feil" :name :feilmelding)

         [:div.flex.justify-end.gap-4
          (bu/danger-button {:type     :submit
                             :disabled (not (some? dirty))} "Lagre")
          (bu/regular-button {:type     :button
                              :on-click #(nrpk.fsm-helpers/send :e.hide)} "Avbryt")]]])]))

(defn modal-form2
  "provide a store function"
  [{:keys [form-id ok]}]
  [fork/form {:prevent-default?  true
              :initial-values    {:description ""}
              :form-id           form-id
              :clean-on-unmount? true
              :keywordize-keys   true
              :on-submit         (fn [{:keys [values]}]
                                   (ok values)
                                   (nrpk.fsm-helpers/send :e.hide))}
   (fn [{:keys [dirty handle-submit form-id] :as props}]
     (if dirty
       (nrpk.fsm-helpers/send :e.dirty)
       (nrpk.fsm-helpers/send :e.clean))
     [:form
      {:id        form-id
       :on-submit handle-submit}
      [:div.space-y-4
       (fields/textarea (-> props
                            (assoc :auto-focus 1)
                            (placeholder "Kort beskrivelse")) :label "Melding til turkamerater" :name :description)

       [:div.flex.justify-end.gap-4
        (bu/regular-button {:type     :button
                            :on-click #(nrpk.fsm-helpers/send :e.hide)} "Avbryt")
        (bu/danger-button {:type     :submit
                           :disabled false #_(not (some? dirty))} "Lagre")]]])])

(defn toggle-favorite [id]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    (js/alert (l/ppr ["toggle-fav" id (:uid @user-auth)]))))

(defn modal-header
  "A view that is a presentation of the boat with some details and a Star to click on"
  [{:keys [toggle-favorite-fn read-db-fn]}]
  (let [star [:div {:on-click #(toggle-favorite-fn)
                    :class    ["text-amber-500"]} [icon/small :star]]
        {:keys [slot number navn kind description]} (read-db-fn)]

    [:div.grid.gap-2.p-4.w-full.bg-gray-300                 ;.bg-gray-500.text-base.font-normal
     {:class []                                             ; ["dark:text-white" :text-white]
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

(defn user-modal-header
  "A view that is a presentation of a user with some details and a Star to click on?"
  [{:keys [toggle-favorite-fn read-db-fn]}]
  (let [star [:div {:on-click #(toggle-favorite-fn)
                    :class    ["text-amber-500"]} [icon/small :star]]
        {:keys [nøkkelnummer telefon navn kind description] :as m} (read-db-fn)]
    [:div.p-4
     [:div navn]
     [:div telefon]
     [:div nøkkelnummer]]

    #_[:div.grid.gap-2.p-4.w-full.bg-gray-300               ;.bg-gray-500.text-base.font-normal
       {:class []                                           ; ["dark:text-white" :text-white]
        :style {:grid-template-columns "3rem 1fr min-content"}}

       [:div.leading-snug

        [:div.text-sm
         {}                                                 ;:class ["dark:text-gray-400" "text-gray-500"]}
         (normalize-kind kind)]]
       [:div (slot-view slot)]
       [:div.justify-self-center.self-start.pt-px star]
       [:div.col-span-2.font-normal.text-base
        ;{:class ["dark:text-gray-400" "text-gray-500"]}
        description]]))

;region booking

(defn modal-booking-details-dialogcontent
  ""
  [id]
  (let [read-db-fn (fn [] (get (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) id))
        {:keys [start end selected slot number navn kind description] :as m} (read-db-fn)]

    [:div.grid.gap-2.text-base.font-normal.w-full
     {:class ["dark:text-white" :text-black]
      :style {:grid-template-columns "3rem 1fr min-content"}}
     [:div.col-span-3 "modal booking title"]

     [:div.col-span-3 [l/ppre m]]

     [:div.col-span-3
      [:div start]
      [:div end]
      [:div (l/ppr selected)]]]))

;endregion

(defn modal-booking-title2
  ""
  [m]
  #_(let [read-db-fn (fn [] (get (reduce (fn [a e] (assoc a (:id e) e)) {} (booking.database/read)) id))
          {:keys [start end selected slot number navn kind description] :as m} (read-db-fn)])

  [:div.grid.gap-2.text-base.font-normal.w-full.p-4
   {:class ["dark:text-white" :text-black]
    :style {:grid-template-columns "3rem 1fr min-content"}}
   [:div.col-span-3 "modal booking title"]

   [:div.col-span-3 [l/ppre m]]

   #_[:div.col-span-3
      [:div start]
      [:div end]
      [:div (l/ppr selected)]]])

;;;;;;;;;;;;;;;;;;;;;; NEW CLASSES, prefer these:

(defn confirm-booking [{:keys [start end selected] :as m}]
  (let [uid (:uid @(rf/subscribe [::db/user-auth]))]
    (modal/form-action
      {:flags   #{}
       :header  (modal-booking-title2 m)
       :form-id "modal-form"
       :ok      (fn [values]
                  (let [values (conj m values {:uid uid})]
                    (tap> ["save settings " values])
                    (booking.database/write {:uid uid :value values})))
       :form-fn (fn [m] (modal-form2 m))})))

(defn details-dialog-fn [id]
  (modal/form-action
    {:flags   #{:wide}
     :ok      #(js/alert %)
     :header  (modal-header
                {:read-db-fn         (fn [] (get (logg.database/boat-db) id))
                 :toggle-favorite-fn (fn [] (toggle-favorite id))})
     :form-fn modal-form
     :footer  "Trykk på stjernen for å markere som favoritt"}))

(defn user-details-dialog-fn [id]
  (modal/form-action
    {:flags   #{:wide}
     :ok      #(js/alert %)
     :header  (user-modal-header
                {:read-db-fn         (fn [] (user.database/lookup-userinfo id))
                 :toggle-favorite-fn (fn [] (toggle-favorite id))})
     :form-fn modal-form
     :footer  (str id)}))

(defn popup [{:keys [content] :as m}]
  (modal/form-action
    (conj
      {:flags   #{:short-timeout}
       :form-fn (fn [_]
                  [:div.space-y-4.p-4x
                   (into [:div] content)])}
      m)))

(defn message
  "high-level"
  [{:keys [content] :as m}]
  (modal/form-action
    (conj
      {:flags   #{:weak-dim}
       :form-fn (fn [x]
                  [:div.space-y-4
                   (into [:div] content)
                   (bu/build-buttonbar-content {:button-captions (fn [_] "Ok")
                                                :buttons         (or (:buttons m) #{:close})})])}
      m)))

(defn ok-cancel
  "high-level"
  [{:keys [content button-captions ok] :as m}]
  (modal/form-action
    (conj (dissoc m :content :button-captions :ok)
          {:form-fn (fn [_]
                      [:div.space-y-8
                       (into [:div] content)
                       (bu/build-buttonbar-content {:button-captions button-captions
                                                    :ok              ok
                                                    :buttons         #{:cancel :ok}})])})))

(defn open-complex []
  (let [ok-fn #(js/alert (l/ppr ["OK" %]))
        m {:flags   #{:weak-dim :-timeout}
           :footer  "footer"
           :form-id "modal-form"
           :ok      ok-fn
           :form-fn (fn [m] [:div.space-y-4
                             [:div "Above"]
                             (modal-form m)
                             [:div "Below"]])
           :header  (modal-header
                      {:toggle-favorite-fn #(js/alert "!")
                       :read-db-fn         #(-> {:slot        "XY"
                                                 :number      "123"
                                                 :navn        "Boatname"
                                                 :kind        "grkayak"
                                                 :description "Fin og fjong"})})}]
    (modal/form-action m)))
