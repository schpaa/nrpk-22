(ns booking.modals.slideout
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            [schpaa.style.ornament :as sc]
            [schpaa.style.dialog]
            [schpaa.debug :as l]))

(rf/reg-sub :modal.slideout/visible? :-> (fn [db] (get db :modal.slideout/toggle false)))

(rf/reg-sub :modal.slideout/extra :-> (fn [db] (get db :modal.slideout/extra)))

(rf/reg-event-db :modal.slideout/toggle
                 (fn [db [_ arg extra]] (if (some? arg)
                                          (assoc db :modal.slideout/toggle arg
                                                    :modal.slideout/extra extra)
                                          (update db :modal.slideout/toggle (fnil not true)))))

(rf/reg-event-db :modal.slideout/close
                 (fn [db _] (assoc db :modal.slideout/toggle false)))

(rf/reg-event-db :modal.slideout/clear
                 (fn [db _]
                   (tap> :modal.slideout/clear)
                   (assoc db :modal.slideout/extra nil)))

;todo core
(defn render []
  (let [{:keys [context vis close]} {:context @(rf/subscribe [:modal.slideout/extra])
                                     :vis     (rf/subscribe [:modal.slideout/visible?])
                                     :close   #(rf/dispatch [:modal.slideout/close])}
        {:keys [action on-primary-action click-overlay-to-dismiss content-fn auto-dismiss on-close]
         :or   {click-overlay-to-dismiss true}} context]
    (r/with-let [write-success (r/atom false)
                 tm (r/atom nil)
                 counter (r/atom 0)
                 close-fn #(do
                             (js/clearInterval @tm)
                             (close))
                 each #(do
                         (if (pos? @counter)
                           (swap! counter dec)
                           (do
                             (js/clearInterval @tm)
                             (close-fn))))
                 open #(do (tap> "opening3")
                           (js/clearInterval @tm)
                           (reset! counter 0)
                           (reset! tm (js/setInterval each auto-dismiss))
                           (reset! vis true))]
      (let [open? @vis]
        [:<>
         [ui/transition
          {:after-enter #(do
                           (tap> ["after-enter" auto-dismiss open?])
                           (when auto-dismiss (open)))
           :show        open?}
          [ui/dialog
           {;:unmount  true
            :static   true
            :on-close #(if click-overlay-to-dismiss (close))} ;must press cancel to dismiss
           [:div.xfixed.xinset-x-1
            [:div.text-center
             [schpaa.style.dialog/standard-overlay]
             ;; trick browser into centering modal contents
             [:span.inline-block                            ;.h-screen.align-middle
              (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
             [ui/transition-child
              {:class       (conj ["inline-block align-middle text-left"]
                                  [:w-screen :xsm:w-96]
                                  (some-> (sc/inner-dlg) last :class first))
               ;:style       {:transform-origin "0 0"}
               :enter       "ease-out transition-opacity transition-transform duration-200"
               :entered     "drop-shadow-xl -translate-y-12"
               :enter-from  "opacity-0 -translate-y-full"
               :enter-to    "opacity-100 -translate-y-4"
               :leave       "ease-in duration-300"
               :leave-from  "opacity-100 translate-y-0"
               :leave-to    "opacity-0 -translate-y-full"
               :after-leave #(do
                               (tap> :after-leave)
                               (when @write-success
                                 (when on-primary-action
                                   (on-primary-action context))
                                 ;REMEMBER TO RESET STATE!!!
                                 (reset! write-success false))
                               (rf/dispatch [:modal.slideout/clear]))}


              [:div {:style {:width     "512px"
                             :max-width "90vw"}
                     :class [:overflow-y-auto]}

               (when auto-dismiss
                 [:svg.absolute {:style {:width :4px :height "100%"} :viewBox "0 0 1 50" :preserveAspectRatio "none"}
                  [:line {:stroke "var(--brand1)" :stroke-width 4 :x1 0 :y1 0 :x2 1 :y2 50}
                   [:animate {:fill "freeze" :attributeName "y2" :from "50" :to "0" :dur (if auto-dismiss (str auto-dismiss "ms"))}]]])

               (if content-fn
                 (content-fn (assoc context
                               :on-close #(do
                                            (when on-close (on-close))
                                            (close-fn))
                               ;called on primary button in dialog with some arguments
                               ;the arguments are placed in the :carry field in the map below
                               :on-save #(do
                                           ;(tap> "on-save -1-")
                                           (reset! write-success true)
                                           (when action
                                             (action {:context context
                                                      :carry   %}))
                                           ;todo remove comment to allow closing
                                           (close-fn))
                               :action action))
                 [:div.text-alt.inset-0.fixed [l/pre "Forgot content-fn?" content-fn close context vis]])]]]]]]]))))
