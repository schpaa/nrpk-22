(ns eykt.modal
  (:require [schpaa.icon :as icon]
            [eykt.state]))

(def animation-duration :duration-300)

(defn are-you-sure?
  "invoked from call-site"
  [{:keys [title text yes no on-confirm]}]
  (eykt.state/send
    :e.show-with-timeout
    {:timeout         7500
     :modal-config-fn
     (fn [] {:style   {:final-position [:translate-y-0]
                       :type           #{:confirm :title :dialog :message}}
             :yes yes
             :no no
             :on-confirm on-confirm
             :title title
             ;:footer  [:div.grid.place-content-center [icon/small :cross-out]]
             :content (fn [context]
                        [:div.p-4.space-y-4.inter
                         ;[:div.font-bold.text-xl title]
                         [:div.font-normal.text-lg text]])})}))

(defn overlay-with [{:keys [modal? on-close]} content]
  [:div.relative.min-.h-full
   [:div.fixed.inset-0.z-400
    {:class [:undersized "bg-rose-400" :bg-opacity-0 :pointer-events-none]}]
   [:div.fixed.inset-0.cursor-default
    {:class    (concat
                 [:transition animation-duration :bg-black]
                 (if modal? [:z-300] [:z-10x])
                 (if modal?
                   [:bg-opacity-80 :pointer-events-auto]
                   [:bg-opacity-0 :pointer-events-none]))
     :on-click on-close}]
   content])

(defn render' [{:keys [show? config-fn on-close]}]
  (let [{:keys [title style context content footer yes no on-confirm]} (if (some? config-fn) (config-fn))
        {:keys [type final-position] :or {type #{:dialog}
                                          final-position [:translate-y-12]}} style
        is-confirm? (some #{:confirm} type)
        is-message? (some #{:message} type)
        is-error? (some #{:error} type)]
    [:div.fixed.inset-x-2.max-w-xs.print:hidden.top-0
     {:style (if-not show? {:transform "translate(0,-110%)"})
      :class (concat (if is-message? [:rounded-b] [:rounded])
                     [:overflow-hidden :transition :transform]
                     [animation-duration]
                     (if show? final-position)
                     [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])}
     (cond
       (some #{:dialog} type)
       [:div.relative.top-0
        {:class [:text-black :dark:text-gray-300
                 :bg-gray-100 :dark:bg-gray-900]}
        ;intent header
        (when title
          [:div.absolute.top-0.sticky.inset-x-0.z-110
           {:class (concat
                     [:h-12 :flex :items-center :justify-between :px-2]
                     [:text-black :dark:text-white])}
           [:h2.pl-1 title]])

        (when content
          [:div {:class [:bg-gray-200 :dark:bg-gray-800]}
           (content context)])

        (when is-confirm?
          [:div.flex.justify-end.gap-4.p-4
           (when no [:button.btn.btn-free {:on-click on-close} no])
           (when yes [:button.btn.btn-danger {:on-click #(do
                                                           (on-confirm)
                                                           (on-close))} yes])])

        (when footer
          [:div
           {:class (concat
                     [:h-12 :flex :items-center :justify-between :px-2]
                     [:text-white :dark:text-white])}
           [:div footer]])]
       (some #{:message} type) [:div.relative.top-0
                                {:class (concat
                                          [:px-1 :pb-1]
                                          (if is-error?
                                            [:text-white :bg-danger-800
                                             :dark:text-white :dark:bg-black]
                                            [:text-white :dark:text-white
                                             :bg-alt-600 :dark:bg-black]))}

                                (when content
                                  [:div {:class (concat [:rounded-b]
                                                        (if is-error?
                                                          [:text-white :dark:text-gray-300
                                                           :bg-danger-600 :dark:bg-gray-800]
                                                          [:text-white :dark:text-gray-300
                                                           :bg-alt-500 :dark:bg-gray-800]))}
                                   (content context)])

                                (when footer
                                  [:div
                                   {:on-click on-close
                                    :class (concat
                                             [:h-12 :flex :items-center :justify-between :px-4]
                                             [:text-white :dark:text-white])}
                                   [:div.w-full footer]])]
       :else [:div.relative.p-1.top-0.rounded-lg
              {:class (concat
                        [:text-black :dark:text-white]
                        [:bg-alt :dark:bg-black])}

              (when content
                [:div {:class (concat [:rounded]
                                      [:dark:text-gray-300 :text-gray-700]
                                      [:bg-gray-100 :dark:bg-gray-800])}
                 (content context)])

              (when footer
                [:div.h-12.flex.items-center.px-2
                 [:div footer]])])]))