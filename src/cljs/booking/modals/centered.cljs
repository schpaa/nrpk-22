(ns booking.modals.centered
  (:require [re-frame.core :as rf]
            [lambdaisland.ornament :as o]
            [headlessui-reagent.core :as ui]
            [schpaa.style.dialog]
            [reagent.core :as r]
            [schpaa.style.ornament :as sc]))

;todo This just seems overly complicated, can you simplify this?
(rf/reg-sub :lab/modaldialog-visible :-> #(get % :lab/modaldialog-visible false))
(rf/reg-sub :lab/modaldialog-context :-> #(get % :lab/modaldialog-context))
(rf/reg-event-db :lab/modaldialog-visible
                 (fn [db [_ arg extra]] (if (true? arg)
                                          (assoc db :lab/modaldialog-visible true
                                                    :lab/modaldialog-context extra)
                                          (assoc db :lab/modaldialog-visible false
                                                    :lab/modaldialog-context extra #_(fnil not true)))))

(rf/reg-event-db :lab/modaldialog-close
                 (fn [db _] (assoc db :lab/modaldialog-visible false)))

(o/defstyled experimental :div
  {:display          :grid
   :place-content    :end
   :background-color "var(--toolbar-)"
   :border-radius    "var(--radius-0)"
   :width            "100%"
   :height           "100%"}
  [:at-media {:max-width "511px"}
   {:width  "100vw"
    :height "100vh"}]
  [:at-media {:min-width "512px"}])

(defn render
  "centered dialog used by boatinput and feedback"
  []
  (let [{:keys [context vis close]} {:context @(rf/subscribe [:lab/modaldialog-context])
                                     :vis     (rf/subscribe [:lab/modaldialog-visible])
                                     :close   #(rf/dispatch [:lab/modaldialog-close])}
        {:keys [action on-primary-action click-overlay-to-dismiss content-fn auto-dismiss]
         :or   {click-overlay-to-dismiss true}} context]
    (r/with-let [mobile? (rf/subscribe [:breaking-point.core/mobile?])
                 write-success (r/atom false)
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
         (if @mobile?
           [ui/transition
            {:appear true
             :show   open?}
            [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))}
             [:div.fixed.inset-0
              [:div
               [schpaa.style.dialog/standard-overlay]
               [:span (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
               [ui/transition-child
                {:style       {}
                 :class       [:inline-block :align-middle :text-left :transform
                               (some-> (sc/inner-dlg) last :class first)]
                 :enter       "ease-out duration-100"
                 :enter-from  "opacity-0  translate-y-16"
                 :enter-to    "opacity-100  translate-y-0"
                 :leave       "ease-in duration-200"
                 :leave-from  "opacity-100  translate-y-0"
                 :leave-to    "opacity-0  translate-y-32"
                 :after-leave #(when @write-success
                                 (when on-primary-action
                                   (on-primary-action context)))}
                [experimental {:class [:sm:w-96 :m-auto]}

                 (when content-fn
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
                   #_[:div "Forgot content-fn?"])]]]]]]
           [ui/transition
            {:appear      true
             :after-enter #(do
                             (tap> "modaldialog-centered after-enter")
                             (when auto-dismiss (open)))
             :show        open?}
            [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))} ;must press cancel to dismiss
             [:div.fixed.inset-0
              ;{:class [(if @(schpaa.state/listen :lab/menu-position-right) :sm:left-16 :sm:right-16)]}
              [:div.text-center
               [schpaa.style.dialog/standard-overlay]
               [:span.inline-block.h-screen.align-middle
                (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
               [ui/transition-child
                {:style       {}
                 :class       [:inline-block :align-middle :text-left :transform
                               (some-> (sc/inner-dlg) last :class first)]
                 :enter       "ease-out duration-100"
                 :enter-from  "opacity-0  translate-y-16"
                 :enter-to    "opacity-100  translate-y-0"
                 :leave       "ease-in duration-200"
                 :leave-from  "opacity-100  translate-y-0"
                 :leave-to    "opacity-0  translate-y-32"
                 :after-leave #(when @write-success
                                 (when on-primary-action
                                   (on-primary-action context)))}

                [experimental {:style  {:-background-color "red"}
                               :xclass [:sm:w-96]}
                 (when content-fn
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
                   #_[:div "Forgot content-fn?"])]]]]]])]))))