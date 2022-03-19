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
            [schpaa.style.combobox]))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

#_(defn field [{:keys [errors values handle-change name type label]}]
    [sc/col {:class [(case type :time :w-32 :date :w-40 :tel :w-32 :text :w-full :w-48) :gap-1]}
     [:div.inline-block
      [sc/label label] " "
      (if (get errors name)
        [sc/label-error (first (get errors name))])]
     [sci/date {:type      type
                :value     (get values name)
                :on-change handle-change
                :errors    (get errors name)
                :name      name}]])

(defn open-modal-boatinfo [data]
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:buttons    [[:cancel "Lukk"]]
                 :data       data
                 :content-fn (fn [ctx]
                               [l/ppre-x ctx])}]))

(defn open-modal-bookingdetails [data]
  (rf/dispatch [:lab/modal-example-dialog2 true
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
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:data       data
                 :buttons    [[:cancel "Avbryt" nil] [:danger "Slett" (fn [] (tap> "slett"))]]
                 ;:on-primary-action (fn [] (tap> :on-primary-action))
                 ;:action            (fn [m] (tap> [:action m]))
                 :content-fn (fn [{:keys [start selected] :as m}]
                               [sc/col {:class [:space-y-4 :w-full]}
                                [sc/row [sc/title-p "Bekreft sletting"]]
                                [sc/text "Dette vil slette din bookingen. Du kan ikke angre, er du sikker?"]
                                (l/ppre-x start selected m)])}]))

(defn open-dialog-confirmbooking
  "the arguments here are of no consequence, it was just a matter of gathering enough data to present something reasonable"
  [time-state state card-data-v2 type-data]
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:data       {:time-state time-state
                              :state      state
                              ;:card-data-v2 card-data-v2
                              #_#_:type-data type-data}
                 :buttons    [[:cancel "Avbryt" nil] [:cta "Bekreft" (fn [] (tap> "lagre booking"))]]
                 :content-fn (fn [context]
                               (let [cdv2 (group-by :id card-data-v2)]
                                 [sc/col {:class [:space-y-4]}
                                  [sc/row [sc/title-p "Bekreft booking"]]
                                  [l/ppre-x context]
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
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss 5000
                 :content-fn               (fn [{:keys [] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Bekreft utlogging"]]
                                              [sc/text "Dette vil logge deg ut fra denne enheten."]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

(defn open-dialog-sampleautomessage []
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:click-overlay-to-dismiss true
                 :auto-dismiss             5000
                 :content-fn               (fn [{:keys [start selected] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Sample"]]
                                              [sc/text "Sample text for a autoclosing dialog - dismissed after 5000 ms"]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))


(defn config-content [{:keys [start selected] :as context}]
  [:div.p-4.select-none
   {:style {}}
   [sc/col {:class [:space-y-8]}
    [sc/col {:class [:space-y-4]}
     [sc/title-p "Innstillinger"]
     [schpaa.style.switch/small-switch-example
      {:tag     :toggle1
       :caption [sc/subtext "Innstillingene er globale"]}]
     [schpaa.style.switch/small-switch-example
      {:tag     :toggle2
       :caption [sc/subtext "Vis forsiden ved innlogging"]}]
     [schpaa.style.switch/small-switch-example
      {:tag     :lab/toggle-chrome
       :caption [sc/subtext "Vis omriss ved innlogging"]}]]

    [sc/subtext-p (if @(schpaa.state/listen :toggle1)
                    "Disse innstillingene vil gjelde for alle innlogginger"
                    "Disse innstillingene lagres lokalt og gjelder bare for denne innloggingen")]
    [sc/row-ec
     ;[scb2/normal-regular {:on-click #((:on-close context))} "Avbryt"]
     [scb2/normal-regular {:on-click #((:on-save context))} "Lukk"]]]])

(defn open-dialog-config []
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:click-overlay-to-dismiss true
                 :content-fn               config-content
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :xon-primary-action       (fn [] (tap> "closing after save"))}]))

(defn open-dialog-sampleformmessage []
  (rf/dispatch [:lab/modal-example-dialog2
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
                                                  [sc/text (ta/datetime-format tm)])
                                                [sc/text (bookers-name data)]
                                                (for [e (:selected data)
                                                      :let [data (fetch-boatdata-for e)
                                                            {:keys [number aquired-year aquired-price]} (fetch-boatdata-for e)]]
                                                  #_[l/ppre-x data]
                                                  [sc/row {:class [:gap-4]}
                                                   [sc/text number]
                                                   [sc/text aquired-year]
                                                   [sc/text aquired-price]])
                                                [sc/col
                                                 [sc/text (:telefon (bookers-details (:uid data)))]
                                                 [sc/text (:epost (bookers-details (:uid data)))]]

                                                [l/ppre-x data #_(bookers-details (:uid data))]]
                                               (= :boatdetails type)
                                               [:div
                                                [sc/title-p "Boat details"]
                                                [l/ppre-x data]]
                                               :else
                                               [l/ppre context]))

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

#_(defn addpost-f [{:keys [data type] :as context}]
    (fn [{:keys [form-id handle-submit] :as props}]
      [:form
       {:id        form-id
        :on-submit handle-submit}
       [sc/col-fields {:class [:space-y-2]}
        [sc/col {:class [:px-2]}
         [sc/title "Header title"]
         #_[sc/text "Lang ledetekst som tvinger vinduet til å legge seg ut så bredt det kan"]]
        [sc/row-wrap
         [input props :text [:w-min] "Tittel" :stuff]
         ;[input props :text [:w-32] "Tittel" :stuff]
         ;[input props :date [:w-42] "Publiseres" :publish-date]
         ;[input props :date [:w-42] "Publiseres" :publish-date]
         [input props :date [:w-42] "Publiseres" :publish-date]]

        [textarea props :text [] "Innhold (ekslusiv tittel)" :stuff3]]]))

(defn addpost-form [{:keys [data type on-close on-save] :as context}]
  [fork/form {:initial-values    {:title        ""
                                  :type         3
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
        [sc/col-fields {:class [:space-y-2 :p-4]}
         [sc/title-p "Lag et nytt innlegg"]
         [sc/row-wrap
          [sc/col {:class []}
           [sc/label "Kategori"]
           [sci/combobox-example {:value     (values :type)
                                  :on-change #(tap> %)
                                  :class     [:w-56]}]]

          [input props :date [:w-44] "Publiseres" :publish-date]

          [input props :text [:w-full] "Tittel" :title]]

         [textarea props :text [] "Innhold (ekslusiv tittel)" :content]]
        [:div.px-4.pb-4                                     ;.-mx-4
         ;{:style {:background "var(--surface0)"}}
         [sc/row-end {:style {:padding-top "var(--size-3)"
                              :gap         "var(--size-3)"}}
          [scb2/normal-regular {:type     "button"
                                :on-click #(on-close)} "Avbryt"]
          [scb2/cta-regular {:disabled (not changed?)
                             :type     "submit"
                             :on-click #(on-save)} "Lagre"]]]]))])

(defn open-dialog-addpost []
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {;:click-overlay-to-dismiss false
                 :data              {:timestamp (t/now)}
                 :type              :bookingdetails
                 :content-fn        #(addpost-form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(rf/reg-event-fx :app/successful-login (fn [{db :db} [_ args]]
                                         {:fx [[:dispatch [:lab/modal-example-dialog2
                                                           true
                                                           {:auto-dismiss 5000
                                                            :action       #()
                                                            :context      args
                                                            :content-fn   (fn [context]
                                                                            [l/ppre context])}]]]}))

(defn open-dialog-signin []
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:click-overlay-to-dismiss false
                 :buttons                  [[:cancel "Lukk"]]
                 :content-fn               (fn [context]
                                             [sc/col
                                              [db.signin/login]
                                              #_[scb/normal {:type     "button"
                                                             :on-click #(rf/dispatch [:app/successful-login "some-claims-here"])} "Lukk"]
                                              #_[scb/normal {:type     "button"
                                                             :on-click #(rf/dispatch [:app/successful-login "some-claims-here"])} "Lukk"]])}]))

;Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter."]
;                               [:div "Etter 14 dager slettes alle data."]]
;             :content         "Er du sikker på at du vil slette booking-kontoen din?"})}
;        "Slett konto"))))

(defn open-dialog-confirmaccountdeletion []
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss             5000
                 :content-fn               (fn [{:keys [start selected] :as context}]
                                             [sc/col {:class [:space-y-2 :w-full]}
                                              [sc/row [sc/title "Advarsel"]]
                                              ;[sc/text "Du er i ferd med å slette kontoen din."]
                                              [sc/text "Kontoen din blir markert som slettet og du vil bli logget ut på alle enheter. Etter 14 dager slettes alle data relatert deg."]
                                              [sc/text "Er du sikker på at du vil slette kontoen din?"]
                                              #_[l/ppre context]])

                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Slett konto"]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

(defn modal-generic [{:keys [context vis close]}]
  (let [{:keys [action on-primary-action buttons click-overlay-to-dismiss content-fn auto-dismiss]
         :or   {click-overlay-to-dismiss true}} context]
    (r/with-let [write-success (r/atom false)
                 tm (r/atom nil)
                 counter (r/atom 0)
                 close #(do
                          (js/clearInterval @tm)
                          (close))
                 each #(do
                         (if (pos? @counter)
                           (swap! counter dec)
                           (do
                             (js/clearInterval @tm)
                             (close))))
                 open #(if auto-dismiss
                         (do (tap> "opening3")
                             (js/clearInterval @tm)
                             (reset! counter 0)
                             (reset! tm (js/setInterval each auto-dismiss))
                             (reset! vis true)))]
      (let [open? @vis]
        [:<>
         [ui/transition
          {:appear      true
           :after-enter #(do
                           (tap> "opening2")
                           (when auto-dismiss (open)))
           :show        open?}
          [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))} ;must press cancel to dismiss
           [:div.fixed.inset-x-1

            [:div.text-center
             [ui/transition-child
              {:enter      "ease-out duration-300"
               :enter-from "opacity-0"
               :enter-to   "opacity-100"
               :leave      "ease-in duration-200"
               :leave-from "opacity-100"
               :leave-to   "opacity-0"}
              [ui/dialog-overlay {:class "fixed inset-0 bg-gray-900 bg-opacity-75"}]]

             ;; trick browser into centering modal contents
             [:span.inline-block.xh-screen.align-middlex
              (assoc zero-width-space-props :aria-hidden true)]
             [ui/transition-child
              {:class       "inline-block align-middlex text-left transform"
               :enter       "ease-out duration-200"
               :enter-from  "xopacity-0  -translate-y-full"
               :enter-to    "xopacity-100  translate-y-0"
               :leave       "ease-in duration-200"
               :leave-from  "opacity-100  translate-y-0"
               :leave-to    "opacity-0  -translate-y-full"
               :after-leave #(when @write-success
                               (when on-primary-action
                                 (on-primary-action)))}

              [sc/dialog {:class [:overflow-y-auto :max-h-screen :min-w-xs]}
               (when auto-dismiss
                 [:svg.-ml-4.-mt-4.absolute {:style   {:width  :4px
                                                       :height "100%"}
                                             :viewBox "0 0 1 50" :preserveAspectRatio "none"}
                  [:line {:stroke       "var(--brand1)"
                          :stroke-width 4
                          :x1           0 :y1 0 :x2 1 :y2 50}
                   [:animate {:fill          "freeze"
                              :attributeName "y2" :from "50" :to "0" :dur (if auto-dismiss (str auto-dismiss "ms"))}]]])

               (if content-fn
                 (content-fn (assoc context
                               :on-close close
                               :on-save #(do
                                           (reset! write-success true)
                                           (when action
                                             (action context))
                                           (close))
                               :action action))
                 [:div "Forgot content-fn?"])]]]]]]]))))

(defn open-selector []
  (rf/dispatch [:lab/modal-selector
                true
                {:content-fn (fn [c]
                               [schpaa.style.combobox/combobox-example c])}]))

(defn modal-selector [{:keys [context vis close]}]
  (let [{:keys [action on-primary-action buttons click-overlay-to-dismiss content-fn auto-dismiss]
         :or   {click-overlay-to-dismiss true}} context]
    (let [open? @vis]
      [ui/transition
       {:appear true
        ;:after-enter #(tap> "modal-selector enter")
        ;:after-leave #(tap> "modal-selector leave")
        :show   open?}
       [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))}
        [:div.fixed.inset-x-1

         [:div.text-center

          [ui/transition-child
           {:enter      "ease-out duration-300"
            :enter-from "opacity-0"
            :enter-to   "opacity-100"
            :leave      "ease-in duration-200"
            :leave-from "opacity-100"
            :leave-to   "opacity-0"}
           [ui/dialog-overlay {:class [:fixed :inset-0]
                               :style {:background "var(--surface5)"
                                       :opacity    0.3}}]]
          ;; trick browser into centering modal contents
          [:span.inline-block.h-screen.align-middle
           (assoc zero-width-space-props :aria-hidden true)]

          [ui/transition-child
           {:class      "inline-block align-middle text-left transform overflow-hidden outline-none focus:outline-none"
            :style      {:border-radius "var(--radius-3)"
                         :box-shadow    "var(--shadow-6)"
                         :max-width     "90vw"}
            ;:after-enter #(tap> "modal-selector child enter")
            :enter      "ease-out duration-200"
            :enter-from "-translate-y-4 scale-95"
            :enter-to   "-translate-y-10 scale-100"
            :entered    "-translate-y-10"
            :leave      "ease-in duration-200"
            :leave-from "opacity-100  -translate-y-10 scale-100"
            :leave-to   "opacity-0  -translate-y-4 scale-95"}
           [sc/selector {:style {:overflow      :hidden
                                 :border-radius "var(--radius-3)"}
                         :class [:max-h-screen :outline-none :focus:outline-none]}
            (if content-fn
              (content-fn (assoc context :on-click (fn [data]
                                                     (do
                                                       (when (:action data)
                                                         ((:action data)))
                                                       (close)))))
              [:div "Forgot content-fn?"])]]]]]])))