(ns eykt.msg)

(defn error-message
  "invoked from call-site"
  [{:keys [title text ok timeout footer]
    :or   {timeout 117500}}]
  [:e.show
   {:timeout timeout
    :modal-config-fn
    (fn [] {:style   {:final-position [:translate-y-0]
                      :type           #{:error :message}}
            :ok      ok
            :title   title
            :footer footer
            :content (fn [_context]
                       [:div.p-4.space-y-4.inter
                        [:div.font-normal.text-lg text]])})}])

(defn confirm-action
  "invoked from call-site"
  [{:keys [title text ok timeout footer]
    :or   {timeout 117500}}]
  [:e.show
   {:timeout timeout
    :modal-config-fn
    (fn [] {:style   {:final-position [:translate-y-0]
                      :type           #{:message}}
            :ok      ok
            :footer footer
            :title   title
            :content (fn [_context]
                       [:div.p-4.space-y-4.inter
                        [:div.font-normal.text-lg text]])})}])

(defn are-you-sure?
  "invoked from call-site"
  [{:keys [title text yes no on-confirm timeout footer]
    :or   {timeout 7500}}]
  [:e.show-with-timeout
   {:timeout timeout
    :modal-config-fn
    (fn [] {:style      {:final-position [:translate-y-0]
                         :type           #{:title :confirm :message}}
            :yes        yes
            :no         no
            :footer footer
            :on-confirm on-confirm
            :title      title
            :content    (fn [_context]
                          [:div.p-4.space-y-4.inter
                           [:div.font-normal.text-lg text]])})}])