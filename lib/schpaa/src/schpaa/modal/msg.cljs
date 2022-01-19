(ns schpaa.modal.msg)

(defn form-action
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