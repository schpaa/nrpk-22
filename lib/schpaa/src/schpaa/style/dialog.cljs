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
            [schpaa.style.input :as sci]))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

(defn simple-form []
  [fork/form {:keywordize-keys  true
              :prevent-default? true
              :initial-values   {:a "A"
                                 :b "B"}
              :on-submit        (fn [v])}
   (fn [{:keys [form-id handle-submit] :as props}]
     [:form
      {:id        form-id
       :on-submit handle-submit}
      [schpaa.components.fields/text props :label "label 1" :name :a]
      [schpaa.components.fields/text props :label "label 2" :name :b]])])

(defn field [{:keys [errors values handle-change name type label]}]
  [sc/col {:class [(case type :time :w-32 :date :w-40 :tel :w-32 :w-48) :gap-1]}
   [:div.inline-block
    [sc/label label] " "
    (if (get errors name)
      [sc/label-error (first (get errors name))])]
   [sci/date {:type      type
              :value     (get values name)
              :on-change handle-change
              :errors    (get errors name)
              :name      name}]])

(defn modal-example [vis close]
  ;(r/with-let [close #(on-close)])
  (let [open? @vis]
    [:<>
     [:div
      [scb/cta
       {:type "button"
        #_#_:on-click open}
       "Open modal example"]]

     [ui/transition
      {:appear true
       :show   open?}
      [ui/dialog {:on-close #(-> false)}                    ;must press cancel to dismiss
       [:div.fixed.inset-x-1.overflow-y-autox

        ;{:style {:z-index 1000}}
        [:div.px-4x.text-center
         ;; NOTE: the structure of this HTML is delicate and has subtle
         ;; interactions to keep the modal centered. The structure we use is
         ;; slightly different from the headlessui.dev example. There the
         ;; Transition.Children are rendered as fragments. Here, since we
         ;; don't support fragments, we move some of the structural styles to
         ;; the Transition.Children, which seems to have the same effect.
         [ui/transition-child
          {:enter      "ease-out duration-300"
           :enter-from "opacity-0"
           :enter-to   "opacity-100"
           :leave      "ease-in duration-200"
           :leave-from "opacity-100"
           :leave-to   "opacity-0"}
          [:div {:class "fixed inset-0 bg-gray-900 bg-opacity-75"} [ui/dialog-overlay]]]
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
           :after-leave #(tap> "After leaving")}

          [sc/dialog
           [fork/form {:keywordize-keys  true
                       :prevent-default? true
                       :initial-values   {:a ""
                                          :b ""
                                          :c nil
                                          :d ""}
                       :on-submit        (fn [v]
                                           (tap> "Submittet")
                                           (tap> v)
                                           (close))}
            (fn [{:keys [form-id handle-submit errors] :as props}]
              [:form.space-y-4
               {:id        form-id
                :on-submit handle-submit}
               [sc/title-p "Dialog-eksempel"]
               [sc/text-p {:class [:x-debug]} [ui/dialog-description "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]
               [:div.space-y-4 {:class [:x-debug]}

                [sc/row-wrap
                 [field (assoc props :errors {:field-name ["er nødvendig"]}
                                     :label "Start"
                                     :name :field-name
                                     :type :date)]

                 [field (assoc props :errors {:text-1 ["er nødvendig"]}
                                     :label "Tekst"
                                     :name :text-1
                                     :type :text)]]

                [sc/row-wrap
                 [field (assoc props :label "Field for data A"
                                     :name :a
                                     :type :text)]

                 [field (assoc props :label "Field for data B"
                                     :name :b
                                     :type :text)]]

                [sc/row-wrap
                 [field (assoc props :label "Tid"
                                     :name :tid
                                     :type :time)]

                 [field (assoc props :errors {:phone ["er nødvendig"]}
                                     :label "Telefon"
                                     :name :phone
                                     :type :tel)]]


                #_[sc/text-p "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]
               [:div.pt-4
                [sc/row-wrap {:class [:justify-end]}
                 [scb/normal
                  {:type     "button"
                   :on-click close}
                  "Avbryt"]
                 [scb/cta
                  {:type "submit"}
                  "Lagre"]]]])]]]]]]]]))

(defn modal-example-from-event [{:keys [show]}]
  #_(r/with-let [!open? (r/atom false)
                 open #(reset! !open? true)
                 close #(reset! !open? false)])
  (let [open? (rf/subscribe [show])]
    (fn dummy [{:keys [ok cancel]}]
      (let [ok #(rf/dispatch [ok])
            cancel #(rf/dispatch [cancel])]
        [:<>
         [:div.flex                                         ;.items-center.justify-center
          #_[sc/button                                      ;:button.px-4.py-2.text-sm.font-medium.text-white.bg-black.rounded-md.bg-opacity-20.hover:bg-opacity-30.focus:outline-none.focus-visible:ring-2.focus-visible:ring-white.focus-visible:ring-opacity-75
             {:type     "button"
              :on-click open}
             "Open dialog"]]

         [ui/transition
          {:appear true
           :show   @open?}
          [ui/dialog {:on-close #(-> false)}                ;must press cancel to dismiss
           [:div.fixed.inset-0.overflow-y-autox

            ;{:style {:z-index 1000}}
            [:div.px-4.text-center
             ;; NOTE: the structure of this HTML is delicate and has subtle
             ;; interactions to keep the modal centered. The structure we use is
             ;; slightly different from the headlessui.dev example. There the
             ;; Transition.Children are rendered as fragments. Here, since we
             ;; don't support fragments, we move some of the structural styles to
             ;; the Transition.Children, which seems to have the same effect.
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
               :enter-from  "xopacity-0 xscale-95 -translate-y-full"
               :enter-to    "xopacity-100 xscale-100 translate-y-0"
               :leave       "ease-in duration-200"
               :leave-from  "opacity-100 xscale-100 translate-y-0"
               :leave-to    "opacity-0 xscale-95 -translate-y-full"
               :after-leave #(tap> "After leaving")}

              [sc/dialog
               [:div.pt-4 [sc/dialog-title "Payment successful!"]]
               [ui/dialog-description "Descriptions"]
               [fork/form {:keywordize-keys  true
                           :form-id          "id"
                           :prevent-default? true
                           #_#_:validation (fn [vs] {:a ["mangler noe her" "mangler noe her"]
                                                     :d ["hmm"]
                                                     :c ["mangler noe her" "mangler noe her"]})
                           :initial-values   {:a "A"
                                              :b "B"
                                              :c nil
                                              :d ""}
                           :on-submit        (fn [v]
                                               (tap> "Submittet")
                                               (tap> v)
                                               (ok))}
                (fn [{:keys [form-id handle-submit errors] :as props}]
                  [:form.space-y-4
                   {:id        form-id
                    :on-submit handle-submit}
                   [:div.py-2.space-y-4
















                    [sc/field-label "Label 1"
                     [fields/text props :name :a]
                     [sc/validation-error errors :a]]
                    [sc/field-label "Label 2"
                     [fields/text props :name :b]
                     [sc/validation-error errors :b]]

                    [:div.flex.gap-4

                     [sc/field-label-date "Some field"
                      [fields/date props :name :c]
                      [sc/validation-error errors :c]]

                     [sc/field-label-short "Label 4"
                      [fields/text (fields/small-field props) :name :d]
                      [sc/validation-error errors :d]]]

                    [:p.text-sm "Your payment has been successfully submitted. We’ve sent you an email with all of the details of your order."]]
                   [:div.mt-4 [sc/row
                               [scb/button-danger
                                {:type     "button"
                                 :on-click cancel}
                                "Avbryt"]
                               [scb/button-cta
                                {:type "submit"}
                                "Lagre"]]]])]]]]]]]]))))

(defn modal-example-with-timeout [!open? close' content]
  (r/with-let [;!open? vis' #_(r/atom @vis')
               tm (r/atom nil)
               counter (r/atom 0)
               close #(do
                        (tap> "CLOSE")
                        (js/clearInterval @tm)
                        (close')
                        #_(reset! !open? false))
               each #(do
                       (tap> "{:tick @tm}")
                       (if (pos? @counter)
                         (do
                           (tap> {:tick @tm})
                           (swap! counter dec))
                         (do
                           (tap> {:interval @tm})
                           (js/clearInterval @tm)
                           (close'))))
               open #(do
                       (tap> "STARTED?")
                       (js/clearInterval @tm)
                       (reset! counter 5)
                       (reset! tm (js/setInterval each 1000))
                       (reset! !open? true))]
    (let [open? @!open?]
      ;(if open? (open))
      [:<>
       [:div
        [scb/normal
         {:type     "button"
          :on-click open}
         "Open autoclosing dialog"]]

       [ui/transition
        {:appear      true
         :show        open?
         :after-enter #(do (open))}
        [ui/dialog {:on-close #(close)}
         [:div.fixed.inset-x-1

          ;{:style {:z-index 1000}}
          [:div.text-center                                 ;.min-h-screen.xpx-4.text-center
           ;; NOTE: the structure of this HTML is delicate and has subtle
           ;; interactions to keep the modal centered. The structure we use is
           ;; slightly different from the headlessui.dev example. There the
           ;; Transition.Children are rendered as fragments. Here, since we
           ;; don't support fragments, we move some of the structural styles to
           ;; the Transition.Children, which seems to have the same effect.
           [ui/transition-child
            {:enter       "ease-out duration-300"
             :enter-from  "opacity-0"
             :enter-to    "opacity-100"
             :leave       "ease-in duration-200"
             :leave-from  "opacity-100"
             :leave-to    "opacity-0"

             :after-leave #(do (tap> "After leaving")
                               #_(close))}

            [ui/dialog-overlay {:class "fixed inset-0 bg-gray-900 bg-opacity-75"}]]
           ;; trick browser into centering modal contents
           [:span.inline-block.xh-screen.align-middlex
            (assoc zero-width-space-props :aria-hidden true)]
           [ui/transition-child
            {:class      "inline-block align-middlex text-left transform"
             :enter      "ease-out duration-200"
             :enter-from "xopacity-0  -translate-y-full"
             :enter-to   "xopacity-100  translate-y-0"
             :leave      "ease-in duration-200"
             :leave-from "opacity-100  translate-y-0"
             :leave-to   "opacity-0  -translate-y-full"}


            [sc/dialog
             ;[:div @counter]
             [:svg.-ml-4.-mt-4.absolute {:style   {:width  :4px
                                                   :height "100%"}
                                         :viewBox "0 0 1 50" :preserveAspectRatio "none"}
              [:line {:stroke       "var(--brand1)"
                      :stroke-width 4
                      :x1           0 :y1 0 :x2 1 :y2 50}
               [:animate {:fill          "freeze"
                          :attributeName "y2" :from "50" :to "0" :dur "6s"}]]]

             [:div.w-full.space-y-4 content]

             [:div.mt-4 [sc/row {:class [:justify-end]}
                         #_[sc/button
                            {:type     "button"
                             :on-click #(do
                                          ;(js/clearInterval @tm)
                                          (open))}
                            "Reset"]
                         [scb/normal
                          {:type     "button"
                           :on-click #(do
                                        (js/clearInterval @tm)
                                        (close))}
                          "Close"]]]]]]]]]])))
