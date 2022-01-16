(ns eykt.modal
  (:require [schpaa.icon :as icon]
            [eykt.state]))

(def animation-duration [:duration-300])

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

(def color-map
  {:cover   {:bgt [:bg-gray-300 :text-black :font-bold :text-2xl]
             :bgp [:bg-gray-300 :text-gray-700]
             :bg  [:bg-gray-300 :text-gray-700]
             :bgf [:bg-gray-900 :text-gray-200]}
   :error   {:bgt [:bg-rose-400 :text-rose-900]
             :bg  [:bg-rose-500 :text-rose-200]
             :bgp [:bg-rose-400 :text-rose-900]
             :bgf [:bg-rose-900 :text-rose-200]}
   :message {:bgt [:bg-gray-50 :text-gray-700]
             :bg  [:bg-gray-50 :text-gray-700]
             :bgp [:bg-gray-500 :text-gray-100]
             :bgf [:text-white :bg-black]}
   :confirm {:bgt [:bg-gray-50 :text-gray-700]
             :bg  [:bg-gray-50 :text-gray-700]
             :bgp [:bg-gray-500 :text-gray-100]
             :bgf [:text-white :bg-alt]}})

(defn cover [{:keys [title style context content footer primary alternative on-confirm on-close]
              :or   {on-close #(eykt.state/send :e.hide)}}]
  (let [{:keys [type final-position] :or {type           #{:confirm}
                                          final-position [:translate-y-12]}} style
        t (cond (some #{:confirm} type) :cover
                (some #{:confirm} type) :confirm
                (some #{:error} type) :error
                :else :message)
        is-confirm? (= t :confirm)
        [bg bgp bgf bgt] ((juxt :bg :bgp :bgf :bgt) (get color-map t))]
    [:div.relative {:class bg}
     (when title
       [:div.absolute.top-0.sticky.inset-x-0.z-110
        {:class (concat
                  [:pt-4 :pb-3 :flex :items-center :justify-between :px-2]
                  bgt)}
        [:div.pl-2 title]])

     (when content
       [:div {:class bgp}
        (content context)])

     [:div.flex.justify-end.gap-4.p-4
      (when alternative [:button.btn.btn-free {:on-click #(on-close)} alternative])
      (when primary [:button.btn.btn-danger {:on-click #(do
                                                          (on-confirm)
                                                          (on-close))} primary])]

     (when footer
       [:div
        {:class (concat
                  [:p-4 :flex :items-center :justify-between]
                  bgf)}
        [:div footer]])]))

(defn confirm [{:keys [title style context content footer primary alternative on-confirm on-close]
                :or   {on-close #(eykt.state/send :e.hide)}}]
  (let [{:keys [type final-position] :or {type           #{:confirm}
                                          final-position [:translate-y-12]}} style
        t (cond (some #{:confirm} type) :confirm
                (some #{:error} type) :error
                :else :message)
        is-confirm? (= t :confirm)
        [bg bgp bgf bgt] ((juxt :bg :bgp :bgf :bgt) (get color-map t))]
    [:div.relative.top-0 {:class bg}
     (when title
       [:div.absolute.top-0.sticky.inset-x-0.z-110
        {:class (concat
                  [:h-12 :flex :items-center :justify-between :px-2]
                  bgt)}
        [:h2.pl-1 title]])

     (when content
       [:div {:class bgp}
        (content context)])

     (when is-confirm?
       [:div.flex.justify-end.gap-4.p-4
        (when alternative [:button.btn.btn-free {:on-click #(on-close)} alternative])
        (when primary [:button.btn.btn-danger {:on-click #(do
                                                            (on-confirm)
                                                            (on-close))} primary])])

     (when footer
       [:div
        {:class (concat
                  [:h-12 :flex :items-center :justify-between :px-2]
                  bgf)}
        [:div footer]])]))

(defn message [{:keys [title style context content footer primary on-close]
                :or   {on-close #(eykt.state/send :e.hide)}}]
  (let [{:keys [type final-position] :or {type           #{:message}
                                          final-position [:translate-y-12]}} style
        t (cond (some #{:confirm} type) :confirm
                (some #{:error} type) :error
                :else :message)
        [bg bgp bgf bgt] ((juxt :bg :bgp :bgf :bgt) (get color-map t))]
    [:div.relative.top-0
     {:class bg}
     (when title
       [:div.absolute.top-0.sticky.inset-x-0.z-110
        {:class (concat
                  [:h-12 :flex :items-center :justify-between :px-2]
                  bgt)}
        [:h2.pl-1.font-bold title]])

     (when content
       [:div {:class (concat bgp)}
        (content context)])

     [:div.p-4.flex.justify-end
      [:button.btn.btn-free {:on-click on-close} primary]]

     (when footer
       [:div
        {:on-click on-close
         :class    (concat
                     [:h-12 :flex :items-center :justify-between :px-4]
                     bgf)}
        [:div.w-full footer]])]))

(defn render [{:keys [show? config-fn]}]
  (let [{:keys [style] :as m} (if (some? config-fn) (config-fn))
        {:keys [type final-position] :or {type           #{:confirm}
                                          final-position [:translate-y-12]}} style
        is-confirm? (some #{:confirm} type)
        is-cover? (some #{:cover} type)
        is-message? (some #{:message} type)]
    [:div.fixed.inset-x-2.print:hidden
     {:style (if-not show? (if is-cover?
                             {:transform "translate(0,-150%)"}
                             {:transform "translate(0,-110%)"}))
      :class (concat (if is-cover? [:max-w-sm :top-32 :rounded] [:max-w-xs :top-0])
                     (if (or is-confirm? is-message?) [:rounded-b] [:rounded])
                     [:overflow-hidden :transition :transform]
                     animation-duration
                     (if show? final-position)
                     [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])}
     (cond
       is-cover?  (cover m)
       is-confirm? (confirm m)
       is-message? (message m)
       :else [:div "UNDEFINED"])]))