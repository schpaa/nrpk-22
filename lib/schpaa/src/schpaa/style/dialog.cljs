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
            [schpaa.style.combobox]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [user.forms]
            [eykt.content.rapport-side]
            [booking.ico :as ico]))

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
                                [sc/text1 "Dette vil slette din bookingen. Du kan ikke angre, er du sikker?"]
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
                                              [sc/text1 "Dette vil logge deg ut fra denne enheten."]
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
                                              [sc/text1 "Sample text for a autoclosing dialog - dismissed after 5000 ms"]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

;region drop-down-dialog settings

(defn config-content [{:keys [start selected] :as context}]
  [sc/dropdown-dialog
   [sc/col {:class [:space-y-8]}
    [sc/col {:class [:space-y-4]}
     [sc/dialog-title "Innstillinger"]
     [schpaa.style.switch/small-switch-example
      {:tag     :toggle1
       :caption [sc/subtext "Innstillingene er globale"]}]
     [schpaa.style.switch/small-switch-example
      {:tag     :toggle2
       :caption [sc/subtext "Vis den originale forsiden på forsiden"]}]

     [schpaa.style.switch/small-switch-example
      {:tag     :lab/toggle-chrome
       :caption [sc/subtext "Vis ekstra krom (ekstra knapper)"]}]

     [schpaa.style.switch/small-switch-example
      {:tag     :lab/more-contrast
       :caption [sc/subtext "Mer kontrast (kommer)"]}]

     [hoc.buttons/regular {:on-click #((:on-save context))} "Tilbakestill alle paneler"]]

    (r/with-let [el (-> js/document.documentElement .-style)
                 a (r/atom (.getPropertyValue el "--brand1"))
                 b (r/atom (.getPropertyValue el "--brand1-bright"))]
      [:<>
       ;[:div @a]
       ;[:div (.getPropertyValue el "--brand1")]
       [:input {:type      :color
                :value     @a
                :on-change #(let [color-hex (-> % .-target .-value)]
                              (reset! a color-hex)
                              (.setProperty el "--brand1" color-hex))}]
       [:input {:type      :color
                :value     @b
                :on-change #(let [color-hex (-> % .-target .-value)]
                              (reset! b color-hex)
                              (.setProperty el "--brand1-bright" color-hex))}]])

    [sc/subtext-p (if @(schpaa.state/listen :toggle1)
                    "Disse innstillingene vil gjelde for alle innlogginger"
                    "Disse innstillingene lagres lokalt og gjelder bare for denne innloggingen")]
    [sc/row-ec
     [hoc.buttons/regular {:on-click #((:on-save context))} "Lukk"]]]])

(defn open-dialog-config []
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:click-overlay-to-dismiss true
                 :content-fn               #(config-content %)
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :xon-primary-action       (fn [] (tap> "closing after save"))}]))

;endregion

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
                                                  [sc/text1 (ta/datetime-format tm)])
                                                [sc/text1 (bookers-name data)]
                                                (for [e (:selected data)
                                                      :let [data (fetch-boatdata-for e)
                                                            {:keys [number aquired-year aquired-price]} (fetch-boatdata-for e)]]
                                                  #_[l/ppre-x data]
                                                  [sc/row {:class [:gap-4]}
                                                   [sc/text1 number]
                                                   [sc/text1 aquired-year]
                                                   [sc/text1 aquired-price]])
                                                [sc/col
                                                 [sc/text1 (:telefon (bookers-details (:uid data)))]
                                                 [sc/text1 (:epost (bookers-details (:uid data)))]]

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
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:data              {:timestamp (t/now)}
                 :type              :bookingdetails
                 :content-fn        #(addpost-form %)
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn edituser-form [{:keys [data type on-close on-save] :as context}]
  [sc/dropdown-dialog
   [fork/form {:initial-values    data
               :form-id           "edituser-form"
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit         (fn [{:keys [values]}]
                                    ;(tap> {:hello (eykt.content.rapport-side/map-difference data values)})
                                    (on-save {:values  values
                                              :changes (eykt.content.rapport-side/map-difference values data)}))}
    (fn [{:keys [form-id handle-submit dirty values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col-space-8
          [sc/dialog-title "Rediger bruker-opplysninger"]
          [sc/col-space-8
           [user.forms/generalinformation-panel props]
           [user.forms/booking-panel props]
           [user.forms/nokkelvakt-panel props]
           [user.forms/changelog-panel props]]]

         [:div.sticky.bottom-0.py-4.-mx-4.mt-8
          {:style {:background "var(--content)"
                   :xopacity   0.9}}
          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"
                             #_#_:on-click #(on-save)} "Lagre"]]]]))]])

(defn save-changes [uid by-uid values]
  (db/firestore-add
    {:path  ["users" uid "endringslogg"]
     :value {:timestamp (str (t/now))
             :by-uid    by-uid
             :changes   values}}))

(defn open-user-info-dialog [data]
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:data              data
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

  (rf/dispatch [:lab/modal-example-dialog2
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
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {;:click-overlay-to-dismiss false
                 :data              data                    ;{:timestamp nil #_(t/now)}
                 :on-submit         on-submit
                 :type              7                       ; annonsering-av-møte
                 :content-fn        #(form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn signin-content [{:keys [on-close on-save] :as context}]
  [sc/dropdown-dialog
   ;[l/ppre-x context]
   [sc/col
    [db.signin/login]]
   [sc/row-ec
    [hoc.buttons/regular {:type     "button"
                          :on-click #(on-close)} "Avbryt"]]])

(defn open-dialog-signin []
  (rf/dispatch [:lab/modal-example-dialog2
                true
                {:click-overlay-to-dismiss false
                 :action                   (fn [{:keys [carry] :as m}] (js/alert m))
                 :content-fn               signin-content}]))

(defn deleteaccount-form [{:keys [start selected on-close on-save] :as context}]
  [sc/dropdown-dialog
   [sc/col {:class [:space-y-8]}
    [sc/col {:class [:space-y-2 :w-full]}
     [sc/dialog-title "Slett konto"]
     [sc/subtext-p "Kontoen din blir markert som inaktiv og du vil bli logget ut på alle enheter. Etter 14 dager slettes alle data relatert deg."]
     [sc/text-p "Er du sikker på at du vil slette kontoen din?"]]
    [sc/row-ec                                              ;{:class [:transition-none]}
     [hoc.buttons/regular {:class    [:transition-none]
                           :type     "button"
                           :on-click #(on-close)} "Nei"]
     [hoc.buttons/danger {:class    [:transition-none]
                          :type     "button"
                          :on-click #(on-save)} "Ja, slett!"]]]])

(defn open-dialog-confirmaccountdeletion []
  (rf/dispatch [:lab/modal-example-dialog2 true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss             5000
                 :content-fn               deleteaccount-form
                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Slett konto"]]
                 :action                   #(tap> ["after pressing the primary button"])
                 :on-primary-action        (fn [e] (tap> ["after closing the dialog" (keys e)]))}]))

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
              [ui/dialog-overlay {:style {:background "black"
                                          :opacity    0.75}
                                  :class "fixed inset-0 "}]]

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
                                 (on-primary-action context)))}

              [:div {:class [:overflow-y-auto :max-h-screen :min-w-xs :max-w-md]}
               (when auto-dismiss
                 [:svg.x-ml-4.x-mt-4.absolute {:style {:width :4px :height "100%"} :viewBox "0 0 1 50" :preserveAspectRatio "none"}
                  [:line {:stroke "var(--brand1)" :stroke-width 4 :x1 0 :y1 0 :x2 1 :y2 50}
                   [:animate {:fill "freeze" :attributeName "y2" :from "50" :to "0" :dur (if auto-dismiss (str auto-dismiss "ms"))}]]])

               (if content-fn
                 (content-fn (assoc context
                               :on-close close
                               :on-save #(do
                                           (reset! write-success true)
                                           (when action
                                             (action {:context context
                                                      :carry   %}))
                                           ;todo remove comment to allow closing
                                           (close))
                               :action action))
                 [:div "Forgot content-fn?"])]]]]]]]))))

(defn open-selector []
  (rf/dispatch [:lab/modal-selector
                true
                {:content-fn schpaa.style.combobox/combobox-example}]))

(defn modal-selector [{:keys [context vis close]}]
  (let [{:keys [action on-primary-action buttons click-overlay-to-dismiss content-fn auto-dismiss]
         :or   {click-overlay-to-dismiss true}} context]
    (let [open? @vis]
      [ui/transition
       {:appear true
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
           [ui/dialog-overlay {;:class [:fixed :inset-0]
                               :style {:position   :fixed
                                       :inset      "0"
                                       :background "black"
                                       :opacity    0.3}}]]
          ;; trick browser into centering modal contents
          [:span.inline-block.h-screen.align-middle
           (assoc zero-width-space-props :aria-hidden true)]

          [ui/transition-child
           {:class      ["inline-block align-middle text-left transform overflow-hidden "
                         :outline-none :focus:outline-none]
            :style      {;:border        "1px solid var(--toolbar-)"
                         :border-radius "var(--radius-3)"
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

(defn modaldialog-centered [{:keys [context vis close]}]
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
           [:div.fixed.inset-x-0

            [:div.text-center
             [ui/transition-child
              {:enter      "ease-out duration-300"
               :enter-from "opacity-0"
               :enter-to   "opacity-100"
               :leave      "ease-in duration-200"
               :leave-from "opacity-100"
               :leave-to   "opacity-0"}
              [:<>
               [ui/dialog-overlay {:style {:background "black"
                                           :opacity    0.75}
                                   :class "fixed inset-0 "}]
               [:div.absolute.top-4.right-4.p-2
                {:style    {:background    "var(--toolbar)"
                            :border-radius "var(--radius-0)"
                            :color         "var(--text0)"}
                 :on-click close}
                (sc/icon-large ico/closewindow)]]]

             ;; trick browser into centering modal contents
             [:span.inline-block.h-screen.align-middle
              (assoc zero-width-space-props :aria-hidden true)]
             [ui/transition-child
              {:class       "inline-block align-middle text-left transform"
               :enter       "ease-out duration-200"
               :enter-from  "xopacity-0  -translate-y-full"
               :enter-to    "xopacity-100  translate-y-0"
               :leave       "ease-in duration-200"
               :leave-from  "opacity-100  translate-y-0"
               :leave-to    "opacity-0  -translate-y-full"
               :after-leave #(when @write-success
                               (when on-primary-action
                                 (on-primary-action context)))}

              [:div {:class [:overflow-y-auto :max-h-screen :min-w-xs :xmax-w-md]}

               (when auto-dismiss
                 [:svg.x-ml-4.x-mt-4.absolute {:style {:width :4px :height "100%"} :viewBox "0 0 1 50" :preserveAspectRatio "none"}
                  [:line {:stroke "var(--brand1)" :stroke-width 4 :x1 0 :y1 0 :x2 1 :y2 50}
                   [:animate {:fill "freeze" :attributeName "y2" :from "50" :to "0" :dur (if auto-dismiss (str auto-dismiss "ms"))}]]])

               (if content-fn
                 (content-fn (assoc context
                               :on-close close
                               :on-save #(do
                                           (reset! write-success true)
                                           (when action
                                             (action {:context context
                                                      :carry   %}))
                                           ;todo remove comment to allow closing
                                           (close))
                               :action action))
                 [:div "Forgot content-fn?"])]]]]]]]))))