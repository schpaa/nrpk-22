(ns schpaa.style.dialog
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            [fork.re-frame :as fork]
            [db.core :as db]
            [reagent.core :as r]
            [schpaa.debug :as l]
            [schpaa.style.ornament :as sc]
            [schpaa.components.fields :as fields]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [clojure.string :as string]
            [re-frame.core :as rf]
            [schpaa.style.button :as scb]
            [booking.database :refer [bookers-details bookers-name fetch-boatdata-for]]
            [tick.core :as t]
            [times.api :as ta]
            [db.signin]
            [schpaa.style.booking]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.input :as sci :refer [input textarea select]]
            [schpaa.style.switch]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [user.forms]
            [eykt.content.rapport-side]
            [booking.ico :as ico]
            [eykt.content.rapport-side :refer [top-bottom-view map-difference]]
            [booking.common-widgets :as widgets]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [booking.modals.boatinfo]))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

(defn standard-overlay []
  [ui/transition-child
   {:enter      "ease-in duration-100 transition-opacity"
    :enter-from "opacity-0"
    :enter-to   "opacity-100"
    :entered    ""
    :leave      "ease-in duration-100"
    :leave-from "opacity-100"
    :leave-to   "opacity-0"}

   [ui/dialog-overlay
    {:style {:background-color "black"
             ;:z-index 10
             :opacity          0.3}
     :class [:backdrop-grayscale
             :fixed :inset-0 :filter :xbackdrop-brightness-30]}]])

;region stars and boat-info

#_(def people (map (fn [[k v]] [{:id k :name (:navn v)}]) @(rf/subscribe [:db/boat-type]))
    #_(map-indexed (fn [idx e] {:id idx :name e})
                   ["a" "b" "c" "Dasdasd"]))

(comment
  (do
    (let [people (take 10 (map (fn [[k v]] {:id k :name (:navn v)}) @(rf/subscribe [:db/boat-type])))]
      (zipmap (map :id people) people))))

(rf/reg-fx :star/update
           (fn [{:keys [boat-type value uid] :as kvs}]
             (db/database-update
               {:path  ["users" uid "starred"]
                :value {boat-type value}})

             (db/database-update-increment
               {:path  ["boat-brand" boat-type]
                :field "star-count"
                :delta (if value 1 -1)})))

(rf/reg-event-fx :star/write-star-change
                 [rf/unwrap]
                 (fn [_ kvs]
                   {:fx [[:star/update kvs]]}))



;endregion

(comment
  (let [last-update "20210709T124742"]
    (cljs-time.format/parse-local (cljs-time.format/formatters :basic-date-time-no-ms) last-update)))

(defn open-modal-bookingdetails [data]
  (rf/dispatch [:modal.slideout/toggle true
                {:click-overlay-to-dismiss true
                 :type                     :bookingdetails
                 :data                     data
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                 :content-fn               (fn [context]
                                             [:div
                                              [:div "something"]
                                              [l/ppre context]])}]))

(defn open-modal-confirmdeletebooking [data]
  (rf/dispatch [:modal.slideout/toggle true
                {:data       data
                 :buttons    [[:cancel "Avbryt" nil] [:danger "Slett" (fn [] (tap> "slett"))]]
                 :content-fn (fn [{:keys [start selected] :as m}]
                               [sc/col {:class [:space-y-4 :w-full]}
                                [sc/row [sc/title-p "Bekreft sletting"]]
                                [sc/text1 "Dette vil slette din bookingen. Du kan ikke angre, er du sikker?"]
                                (l/pre start selected m)])}]))

(defn open-dialog-confirmbooking
  "the arguments here are of no consequence, it was just a matter of gathering enough data to present something reasonable"
  [time-state state card-data-v2 type-data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data       {:time-state time-state
                              :state      state}
                 :buttons    [[:cancel "Avbryt" nil]
                              [:cta "Bekreft" (fn [] (tap> "lagre booking"))]]
                 :content-fn (fn [context]
                               (let [cdv2 (group-by :id card-data-v2)]
                                 [sc/col {:class [:space-y-4]}
                                  [sc/row [sc/title-p "Bekreft booking"]]
                                  [l/pre context]
                                  [sc/surface-e {:style {:max-height "16rem"}
                                                 :class [:p-2 :overflow-y-auto]}
                                   [sc/col {:class [:space-y-4]}

                                    [sc/grid-wide {:class [:gap-2]}
                                     (for [e (:selected @state)
                                           :let [type (:type (first (get cdv2 e)))]]
                                       [schpaa.style.booking/selection-with-badges
                                        {:content
                                         (conj (first (get cdv2 e))
                                               (first (get (group-by :type type-data) type)))}])]]]]))}]))

(defn open-dialog-logoutcommand []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss 5000
                 :content-fn               (fn [{:keys [] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Bekreft utlogging"]]
                                              [sc/text1 "Dette vil logge deg ut fra denne enheten."]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

(defn open-dialog-sampleautomessage []
  (rf/dispatch [:modal.slideout/toggle true
                {:click-overlay-to-dismiss true
                 :auto-dismiss             5000
                 :content-fn               (fn [{:keys [start selected] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Sample"]]
                                              [sc/text1 "Sample text for a autoclosing dialog - dismissed after 5000 ms"]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

;region drop-down-dialog settings

(declare open-dialog-confirmaccountdeletion)
;endregion

(defn open-dialog-sampleformmessage []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss 5000
                 :data                     {:timestamp (t/now)}
                 :type                     :bookingdetails
                 :content-fn               (fn [{:keys [data type] :as context}]
                                             (cond
                                               (= :bookingdetails type)
                                               [sc/col {:class [:text-base :tracking-normal :w-full]}
                                                [sc/title-p "Booking details"]
                                                (when-let [tm (some-> (:timestamp data) (t/instant) (t/zoned-date-time))]
                                                  [sc/text1 (ta/datetime-format tm)])
                                                [sc/text1 (bookers-name data)]
                                                (for [e (:selected data)
                                                      :let [data (fetch-boatdata-for e)
                                                            {:keys [number aquired-year aquired-price]} (fetch-boatdata-for e)]]
                                                  #_[l/pre data]
                                                  [sc/row {:class [:gap-4]}
                                                   [sc/text1 number]
                                                   [sc/text1 aquired-year]
                                                   [sc/text1 aquired-price]])
                                                [sc/col
                                                 [sc/text1 (:telefon (bookers-details (:uid data)))]
                                                 [sc/text1 (:epost (bookers-details (:uid data)))]]

                                                [l/pre data #_(bookers-details (:uid data))]]
                                               (= :boatdetails type)
                                               [:div
                                                [sc/title-p "Boat details"]
                                                [l/pre data]]
                                               :else
                                               [l/ppre context]))

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

;(defn addpost-f [{:keys [data type] :as context}]
;    (fn [{:keys [form-id handle-submit] :as props}]
;      [:form
;       {:id        form-id
;        :on-submit handle-submit}
;       [sc/col-fields {:class [:space-y-2]}
;        [sc/col {:class [:px-2]}
;         [sc/title1 "Header title"]
;         #_[sc/text1 "Lang ledetekst som tvinger vinduet til å legge seg ut så bredt det kan"]]
;        [sc/row-wrap
;         [input props :text [:w-min] "Tittel" :stuff]
;         ;[input props :text [:w-32] "Tittel" :stuff]
;         ;[input props :date [:w-42] "Publiseres" :publish-date]
;         ;[input props :date [:w-42] "Publiseres" :publish-date]
;         [input props :date [:w-42] "Publiseres" :publish-date]]
;
;        [textarea props :text [] "Innhold (ekslusiv tittel)" :stuff3]]]))

(defn addpost-form [{:keys [data type on-close on-save] :as context}]
  [sc/dropdown-dialog
   [fork/form {:initial-values    {:title        ""
                                   :type         type
                                   :publish-date ""
                                   :content      ""}
               :form-id           "addpost-form"
               ;:validation       #(-> {:stuff2 ["for lite"]})
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit         (fn [{:keys [values]}]
                                    (db/database-push
                                      {:path  ["booking-posts" "articles"]
                                       :value {:created (str (t/now))
                                               :date    (str (t/now))
                                               :deleted false
                                               :visible true
                                               :title   (:title values)
                                               :content (:content values)}}))}
    (fn [{:keys [form-id handle-submit dirty values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col {:class [:space-y-8]}
          [sc/col-fields {:class [:space-y-2]}
           [sc/dialog-title "Lag et nytt innlegg"]
           [sc/row-sc-g2-w
            [sc/col {:class []}
             [sc/label "Kategori"]
             [sci/combobox-example {:value     (values :type)
                                    :on-change #(tap> %)
                                    :class     [:w-56]}]]

            [input props :date [:w-44] "Publiseres" :publish-date]

            [input props :text [:w-full] "Tittel" :title]]

           [textarea props :text {} "Innhold (ekslusiv tittel)" :content]]
          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"
                             :on-click #(on-save)} "Lagre"]]]]))]])

(defn open-dialog-addpost []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data              {:timestamp (t/now)}
                 :type              :bookingdetails
                 :content-fn        #(addpost-form %)
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn edituser-form [{:keys [data type on-close on-save] :as context}]
  [sc/dropdown-dialog'
   [fork/form {:initial-values    data
               :form-id           "edituser-form"
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit
               (fn [{:keys [state values] :as x}]
                 (user.forms/save-edit-changes
                   (:uid values)
                   (:uid @(rf/subscribe [::db/user-auth]))
                   (select-keys (:initial-values @state) (keys (map-difference values (:initial-values @state))))
                   (map-difference values (:initial-values @state))
                   (or (:endringsbeskrivelse values) (apply str (interpose ", " (map name (keys (map-difference values (:initial-values @state))))))))
                 (db/database-update {:path  ["users" (:uid values)]
                                      :value (assoc values :timestamp (str (t/now)))})
                 (rf/dispatch [:save-this-form-test x]))}
    (fn [{:keys [form-id handle-submit dirty values] :as props}]
      (let [changed? dirty]
        [:form.pt-4
         {:id        form-id
          :on-submit handle-submit}

         [sc/col
          [sc/title (:navn values)]
          [sc/small (:uid values)]]

         [sc/col-space-4
          [:div.pt-4
           (interpose [:div.py-2]
                      [[user.forms/generalinformation-panel props]
                       [user.forms/booking-panel props]
                       [user.forms/nokkelvakt-panel props]
                       [user.forms/status-panel props]
                       [user.forms/changelog-panel (:uid values) props]])]]

         [:div.sticky.bottom-0.py-4.-mx-4
          {:style {:background     "var(--content)"
                   :opacity        0.8
                   :padding-inline "var(--size-3)"}}
          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click on-close} (if (not changed?) "Lukk" "Avbryt")]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"} "Lagre"]]]]))]])

(defn save-changes [uid by-uid values]
  (db/firestore-add
    {:path  ["users" uid "endringslogg"]
     :value {:timestamp (str (t/now))
             :by-uid    by-uid
             :changes   values}}))

(defn open-user-info-dialog [data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data              data
                 ;:on-close   #(tap> "on-close")
                 :content-fn        edituser-form
                 :action            (fn [{:keys [carry] :as m}]
                                      (let [uid (-> carry :values :uid)
                                            by-uid (:uid @(rf/subscribe [::db/user-auth]))
                                            changes (:changes carry)]
                                        ;(js/alert (l/ppr [uid by-uid changes])))
                                        (save-changes uid by-uid (:changes carry))
                                        #_(tap> ["action:" {:carry   (:values carry)
                                                            :changes (:changes carry)}])))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn open-dialog-any [{:keys [form                         ; definition of form, f(context)
                               on-submit                    ; what is called when submitting, f(values)
                               data]}]                      ; existing fields

  (rf/dispatch [:modal.slideout/toggle
                true
                {;:click-overlay-to-dismiss false
                 :data              data
                 :on-submit         on-submit
                 :content-fn        #(form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn open-dialog-newevent [{:keys [form                    ; definition of form, f(context)
                                    on-submit               ; what is called when submitting, f(values)
                                    data]}]                 ; existing fields
  (rf/dispatch [:modal.slideout/toggle
                true
                {;:click-overlay-to-dismiss false
                 :data              data                    ;{:timestamp nil #_(t/now)}
                 :on-submit         on-submit
                 :type              7                       ; annonsering-av-møte
                 :content-fn        #(form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

;region account signin


;endregion

