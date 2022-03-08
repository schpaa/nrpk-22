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
            [schpaa.style.input :as sci]
            [booking.database :refer [bookers-details bookers-name fetch-boatdata-for]]
            [tick.core :as t]
            [times.api :as ta]
            [db.signin]))

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
                {:content-fn (fn [context]
                               [sc/col
                                [db.signin/login]
                                [scb/normal {:type     "button"
                                             :on-click #(rf/dispatch [:app/successful-login "some-claims-here"])} "Dispatch :successful-login"]])}]))


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

              [sc/dialog {:class [:w-full]}
               (when auto-dismiss
                 [:svg.-ml-4.-mt-4.absolute {:style   {:width  :4px
                                                       :height "100%"}
                                             :viewBox "0 0 1 50" :preserveAspectRatio "none"}
                  [:line {:stroke       "var(--brand1)"
                          :stroke-width 4
                          :x1           0 :y1 0 :x2 1 :y2 50}
                   [:animate {:fill          "freeze"
                              :attributeName "y2" :from "50" :to "0" :dur (if auto-dismiss (str auto-dismiss "ms"))}]]])
               [fork/form {:keywordize-keys  true
                           :prevent-default? true
                           :initial-values   {}
                           :on-submit        (fn [v]
                                               (reset! write-success true)
                                               (when action
                                                 (action context))
                                               (close))}

                (fn [{:keys [form-id handle-submit] :as props}]
                  [:form.space-y-4.min-w-xs
                   {:id        form-id
                    :on-submit handle-submit}

                   (if content-fn
                     (content-fn context)
                     [:div "Forgot content-fn?"])

                   (when buttons
                     [:div.pt-4
                      [sc/row-wrap {:class [:justify-end]}
                       (->> buttons
                            (map (fn [[kind caption action]]
                                   (cond
                                     (= kind :danger)
                                     [scb/danger {:type "submit"} caption]

                                     (= kind :cta)
                                     [scb/cta {:type     "submit"
                                               :on-click #(do
                                                            (reset! write-success true)
                                                            (close))} caption]

                                     :else
                                     [scb/normal {:type     "button"
                                                  :on-click #(do
                                                               (reset! write-success false)
                                                               (close))} caption]))))]])])]]]]]]]]))))
