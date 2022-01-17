(ns schpaa.modal
  (:require [schpaa.icon :as icon]
            [schpaa.modal.msg :as msg]
            [eykt.fsm-helpers :refer [send]]
            [statecharts.core :refer [assign]]))

(def default-dialog-timeout 5000)
(def animation-duration [:duration-300])

(defn- clear-modal-state []
  (assign (fn [st _] (assoc st
                       :modal false
                       :modal-forced false))))

(defn- set-normal-modal-state []
  (assign (fn [st {:keys [data] :as _event}]
            (assoc st
              :modal-config-fn (:modal-config-fn data)
              :modal-forced false
              :modal true))))

(defn- set-forced-modal-state []
  (assign (fn [st {:keys [data] :as _event}]
            (assoc st
              :modal-config-fn (:modal-config-fn data)
              :modal-forced true))))

(def modal-machine
  {:initial :s.initial
   :states  {:s.initial {:entry (clear-modal-state)
                         :on    {:e.show-locked       :s.locked
                                 :e.show-with-timeout :s.timeout
                                 :e.show              :s.visible}}
             :s.visible {:entry (set-normal-modal-state)
                         :on    {:e.hide :s.initial
                                 ;intent Showing again will close it
                                 :e.show :s.initial}}
             :s.locked  {:entry (set-forced-modal-state)
                         :on    {:e.hide :s.initial
                                 ;intent Showing again will close it
                                 :e.show :s.initial}}
             :s.timeout {:entry (set-normal-modal-state)
                         :on    {:e.hide              :s.initial
                                 ;intent Showing again will close it
                                 :e.show-with-timeout :s.initial}
                         :after [{:delay  (fn [_ {:keys [data]}] (:timeout data default-dialog-timeout))
                                  :target :s.initial}]}}})

(def confirm-action (fn [m] (apply send (msg/confirm-action m))))

(def are-you-sure? (fn [m] (apply send (msg/are-you-sure? m))))
(def are-you-sure-forced? (fn [m] (apply send (msg/are-you-sure-forced? m))))
(def are-you-sure-cover-and-forced? (fn [m] (apply send (msg/are-you-sure-cover-and-forced? m))))

(def error-message (fn [m] (apply send (msg/error-message m))))
(def message-dialog (fn [& e] (apply send (apply msg/message e))))
(def message-with-timeout (fn [& e] (apply send (apply msg/message-with-timeout e))))

(defn overlay-with [{:keys [modal? on-close]} content]
  [:div.relative.min-.h-full
   #_[:div.fixed.inset-0.z-400
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
   :error   {:bgt [:bg-rose-500 :text-rose-300]
             :bg  [:bg-rose-500 :text-rose-200]
             :bgp [:bg-rose-500 :text-rose-100]
             :bgf [:bg-rose-900 :text-rose-200]}
   :message {:bgt [:bg-gray-50 :text-gray-700]
             :bg  [:bg-gray-50 :text-gray-700]
             :bgp [:bg-gray-500 :text-gray-100]
             :bgf [:text-white :bg-black]}
   :confirm {:bgt [:bg-gray-50 :text-gray-700]
             :bg  [:bg-gray-50 :text-gray-700]
             :bgp [:bg-gray-500 :text-gray-100]
             :bgf [:text-white :bg-alt]}})

(defn cover [{:keys [title style context content footer primary secondary on-confirm on-close]
              :or   {on-close #(send :e.hide)}}]
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
      (when secondary [:button.btn.btn-free {:on-click #(on-close)} secondary])
      (when primary [:button.btn.btn-danger {:on-click #(do
                                                          (on-confirm)
                                                          (on-close))} primary])]

     (when footer
       [:div
        {:class (concat
                  [:p-4 :flex :items-center :justify-between]
                  bgf)}
        [:div footer]])]))

(defn confirm [{:keys [title style context content footer primary secondary on-confirm on-close]
                :or   {on-close #(send :e.hide)}}]
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
        (when secondary [:button.btn.btn-free {:on-click #(on-close)} secondary])
        (when primary [:button.btn.btn-danger {:on-click #(do
                                                            (on-confirm)
                                                            (on-close))} primary])])

     (when footer
       [:div
        {:class (concat
                  [:h-12 :flex :items-center :justify-between :px-2]
                  bgf)}
        [:div footer]])]))

(defn message [{:keys [title style context content footer primary secondary on-close]
                :or   {title    "no title"
                       content  (fn [_] [:div.p-4 "no content"])
                       primary  "ok"
                       on-close #(send :e.hide)}}]
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

     [:div.p-4.flex.justify-end.gap-4
      (if secondary
        [:button.btn.btn-secondary {:on-click on-close} secondary])
      [:button.btn.btn-free {:class    (if secondary [:border-2 :border-black :font-semibold])
                             :on-click on-close} primary]]

     (when footer
       [:div
        {:on-click on-close
         :class    (concat
                     [:flex :items-center :justify-between :p-4]
                     bgf)}
        [:div.w-full footer]])]))

(defn render [{:keys [show? config-fn]}]
  (let [{:keys [style] :as m} (if (some? config-fn) (config-fn))
        {:keys [type final-position] :or {type           #{:message}
                                          final-position [:translate-y-0]}} style
        is-wide? (some #{:wide} type)
        is-confirm? (some #{:confirm} type)
        is-cover? (some #{:cover} type)
        is-message? (some #{:message} type)]
    [:div.fixed.inset-x-2.print:hidden
     {:style (if-not show? (if is-cover?
                             {:transform "translate(0,-150%)"}
                             {:transform "translate(0,-110%)"}))
      :class (concat (if is-cover? [:max-w-sm :top-32 :rounded] [:top-0])
                     (if is-wide? [:max-w-sm] [:max-w-xs])
                     (if (or is-confirm? is-message?) [:rounded-b] [:rounded])
                     [:overflow-hidden :transition :transform]
                     animation-duration
                     (if show? final-position)
                     [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])}
     (cond
       is-cover? (cover m)
       is-confirm? (confirm m)
       is-message? (message m)
       :else [:div "UNDEFINED"])]))