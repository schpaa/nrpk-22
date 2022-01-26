(ns schpaa.modal.msg
  (:require [schpaa.icon :as icon]
            [eykt.fsm-helpers]))

#_(defn form-action
    [{:keys [header form-fn on-primary primary secondary footer]}]
    [:e.show
     {:modal-config-fn
      (fn []
        (conj
          ;intent Hardwired
          {:style       {:final-position [:translate-y-0]}
           :dialog-type :form
           :flags       #{:wide}}
          ;intent Soft
          {:header     header
           :form-fn    form-fn
           :footer     footer
           ;;
           :on-primary on-primary
           :primary    primary
           :secondary  secondary}))}])

(defn form-action
  [{:keys [header form-fn footer title close-fn flags icon]
    :or   {close-fn (fn [] (eykt.fsm-helpers/send :e.hide))}}]
  [(cond
     (some #{:forced} flags) :e.show-locked
     (some #{:timeout} flags) :e.show-with-timeout
     :else :e.show)
   (if (some #{:weak-dim} flags) :weak-dim :normal-dim)
   {:modal-config-fn
    (fn []
      (conj
        ;intent Hardwired
        {:style       {:final-position [:translate-y-0]}
         :dialog-type :form
         :flags       (conj flags :wide)}
        ;intent Soft
        (if title
          {:header [:div.grid.gap-2.p-4.text-base.font-normal.w-full
                    {:style {:grid-template-columns "2rem 1fr min-content"}}
                    [:<>
                     (if icon
                       [:<>
                        [:div.self-center.justify-self-start [icon/small icon]]
                        [:div.self-center title]]
                       [:div.col-span-2.self-center.font-bold title])]
                    (when-not (some #{:no-crossout} flags)
                      [:div {:on-click close-fn} [icon/small :cross-out]])]}
          {:header header})
        (if footer
          {:footer (if (string? footer) [:div.py-4.px-2.text-sm footer] footer)})
        {:form-fn form-fn}))}])

#_(defn error-message
    "invoked from call-site"
    [{:keys [title text primary footer]}]
    [:e.show-with-timeout
     {:modal-config-fn (fn [] {:style     {:final-position [:translate-y-0]
                                           :type           #{:error :message :wide}}
                               :primary   primary
                               :secondary "Rapporter!"
                               :title     title
                               :footer    footer
                               :content   (fn [_context]
                                            [:div.p-4.space-y-4.inter
                                             [:div.font-normal.text-lg text]])})}])

#_(defn confirm-action
    "invoked from call-site"
    [{:keys [title text primary timeout footer]
      :or   {timeout 117500}}]
    [:e.show
     {:timeout timeout
      :modal-config-fn
      (fn [] {:style   {:final-position [:translate-y-0]
                        :type           #{:message}}
              :primary primary
              :footer  footer
              :title   title
              :content (fn [_context]
                         [:div.p-4.space-y-4.inter
                          [:div.font-normal.text-lg text]])})}])

#_(defn are-you-sure?
    "invoked from call-site"
    [{:keys [title text primary secondary on-primary timeout footer]
      :or   {timeout 7500}}]
    [:e.show-with-timeout
     {:timeout timeout
      :modal-config-fn
      (fn [] {:style      {:final-position [:translate-y-0]
                           :type           #{:title :confirm}}
              :primary    primary
              :secondary  secondary
              :footer     footer
              :on-primary on-primary
              :title      title
              :content    (fn [_context]
                            [:div.p-4.space-y-4.inter
                             [:div.font-normal.text-lg text]])})}])

#_(defn are-you-sure-forced?
    "invoked from call-site"
    [{:keys [title text primary secondary on-primary on-close footer]}]
    [:e.show-locked
     {:modal-config-fn
      (fn [] {:style      {:final-position [:translate-y-0]
                           :type           #{:title :confirm}}
              :primary    primary
              :secondary  secondary
              :footer     footer
              :on-primary on-primary
              :on-close   on-close
              :title      title
              :content    (fn [_context]
                            [:div.p-4.space-y-4.inter
                             [:div.font-normal.text-lg text]])})}])

#_(defn are-you-sure-cover-and-forced?
    "invoked from call-site"
    [{:keys [title text primary secondary on-primary on-close footer]}]
    [:e.show-locked
     {:modal-config-fn
      (fn [] {:style      {:final-position [:translate-y-0]
                           :type           #{:title :confirm :cover}}
              :primary    primary
              :secondary  secondary
              :footer     footer
              :on-primary on-primary
              :on-close   on-close
              :title      title
              :content    (fn [_context]
                            [:div.p-4.space-y-4.inter
                             [:div.font-normal.text-lg text]])})}])

#_(defn message-with-timeout [title content]
    [:e.show-with-timeout
     {:modal-config-fn (fn [] {:style   {:type #{:wide :message}}
                               :title   title
                               :content (fn [_] [:div content])})}])

#_(defn message [title content]
    [:e.show
     {:modal-config-fn (fn [] {:style   {:type #{:wide :message}}
                               :title   title
                               :content (fn [_] [:div content])})}])