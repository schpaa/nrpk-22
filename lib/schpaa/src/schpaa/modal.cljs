(ns schpaa.modal
  (:require [schpaa.icon :as icon]
            [reagent.core :as r]
            [reagent.dom]
            [schpaa.modal.msg :as msg]
            [eykt.fsm-helpers :refer [send]]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [goog.events :as gevents]))

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

(def form-action (fn [m] (apply send (msg/form-action m))))
(def confirm-action (fn [m] (apply send (msg/confirm-action m))))

(def are-you-sure? (fn [m] (apply send (msg/are-you-sure? m))))
(def are-you-sure-forced? (fn [m] (apply send (msg/are-you-sure-forced? m))))
(def are-you-sure-cover-and-forced? (fn [m] (apply send (msg/are-you-sure-cover-and-forced? m))))

(def error-message (fn [m] (apply send (msg/error-message m))))
(def message-dialog (fn [& e] (apply send (apply msg/message e))))
(def message-with-timeout (fn [& e] (apply send (apply msg/message-with-timeout e))))

(defn overlay-with [{:keys [modal? on-close]} content]
  [:div.relative.h-full
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
  {:form    {:bgt [:bg-gray-200 :text-black
                   "dark:bg-gray-600"]
             :bgp [:bg-gray-300 :text-gray-700
                   "dark:bg-gray-500"]
             ;:bg  [:bg-gray-200 :text-gray-700]
             :bgf [:bg-gray-600 :text-gray-200
                   "dark:bg-gray-400" "dark:text-black"]}
   :cover   {:bgt [:bg-gray-300 :text-black :font-bold :text-2xl]
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

(defn cover [{:keys [title style context content footer primary secondary on-primary on-close]
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
                                                          (on-primary)
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

(defn form [{:keys [title style form-fn footer primary secondary on-primary on-close submit-button]
             :or   {on-close #(send :e.hide)}}]
  (let [{:keys [type final-position] :or {type           #{:confirm}
                                          final-position [:translate-y-12]}} style
        t (cond (some #{:confirm} type) :confirm
                (some #{:error} type) :error
                (some #{:form} type) :form
                :else :message)
        is-confirm? (= t :confirm)
        [bg bgp bgf bgt] ((juxt :bg :bgp :bgf :bgt) (get color-map t))]
    [:div.relative.top-0 {:class bg}
     (when title
       [:div.absolute.top-0.sticky.inset-x-0.z-110
        {:style {:min-height "3rem"}
         :class (concat
                  [:flex :items-center :justify-between]
                  bgt)}
        title])

     (when form-fn
       [:div
        {:class bgp}
        (form-fn {:on-close on-close})])

     #_(when is-confirm?
         [:div.flex.justify-end.gap-4.p-4
          (when secondary [:button.btn.btn-free {:on-click #(on-close)} secondary])
          (when primary [:button.btn.btn-danger {:on-click #(do
                                                              (on-primary)
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

(defn render
  "add facilities to unmount a component when a transition is completed and
  the component is (supposedly) off-screen after the animation."
  [{:keys [show? config-fn]}]
  (let [node (atom nil)
        show-status (atom nil)
        end-transition (atom false)]
    (r/create-class
      {:display-name
       "modal-presenting-layer"

       :component-did-mount
       (fn [c]
         (let [_ (reset! node (reagent.dom/dom-node c))]
           ;(tap> ["mounted" node])
           (.addEventListener @node "transitioncancel" (fn []
                                                         ;intent ?
                                                         (reset! end-transition @show-status)
                                                         #_(tap> "CANCELLED")))
           (.addEventListener @node "transitionend"
                              (fn []
                                (reset! end-transition (not @show-status))
                                #_(tap> {:show-status    @show-status
                                         :end-transition @end-transition})))))

       :component-did-update
       (fn [c]
         (tap> ["DID UPDATE" @node]))

       :reagent-render
       (fn [{:keys [show? config-fn]}]
         (let [;todo Possibly move this into the reagent-render method
               {:keys [style] :as m} (if (some? config-fn) (config-fn))
               {:keys [type final-position]
                :or   {type           #{:message}           ;fixme
                       final-position [:translate-y-0]}} style
               is-wide? (some #{:wide} type)
               is-confirm? (some #{:confirm} type)
               is-form? (some #{:form} type)
               is-cover? (some #{:cover} type)
               is-message? (some #{:message} type)]
           (reset! show-status show?)
           [:div.fixed.inset-x-1.select-none
            {:class    (concat (if is-cover? [:max-w-sm :top-32 :rounded] [:top-0])
                               (if is-wide? [:max-w-sm] [:max-w-xs])
                               [:overflow-hidden :transition :transform]
                               animation-duration
                               (if show? final-position)
                               [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])
             :on-click #(send :e.show {})
             :style    (when-not @show-status
                         (if is-cover?
                           {:transform "translate(0,-150%)"}
                           {:transform "translate(0,-110%)"}))}
            (if-not (and (not @show-status) @end-transition)
              (cond
                is-form? (form m)
                is-cover? (cover m)
                is-confirm? (confirm m)
                is-message? (message m)
                :else [:div "UNDEFINED"]))]))})))

#_[:div#stuffed
   (r/with-let [elem (.getElementById js/document "stuffed")]
     ;_ (.addEventListener,,,)

     [:div "HEY" (l/ppr elem)]
     (finally #_(.removeEventListener)))]

#_(defn render [{:keys [show? config-fn]}]
    (let [!thing (r/atom nil)
          !elem (r/atom nil)
          {:keys [style] :as m} (if (some? config-fn) (config-fn))
          {:keys [type final-position] :or {type           #{:message}
                                            final-position [:translate-y-0]}} style
          is-wide? (some #{:wide} type)
          is-confirm? (some #{:confirm} type)
          is-form? (some #{:form} type)
          is-cover? (some #{:cover} type)
          is-message? (some #{:message} type)
          #_#_#_#_elem (.getElementById js/document "stuffed")
              old (gevents/listen elem "transitionend" (fn [] (tap> "TOOUCHC............ ")))]
      (fn [{:keys [show? config-fn]}]
        [:div#stuffed.fixed.inset-x-1
         {:ref    (fn [e]
                    (when e
                      #_(tap> {;:a "SET THE THING"
                               :e e
                               :t @!thing})
                      (reset! !elem (.getElementById js/document "stuffed"))
                      (reset! !thing e)))
          :astyle (if-not show? (if is-cover?
                                  {:transform "translate(0,-150%)"}
                                  {:transform "translate(0,-110%)"}))
          :class  (concat (if is-cover? [:max-w-sm :top-32 :rounded] [:top-0])
                          (if is-wide? [:max-w-sm] [:max-w-xs])
                          (if (or is-confirm? is-message?) [:rounded-b] [:rounded])
                          [:overflow-hidden :transition :transform]
                          animation-duration
                          (if show? final-position)
                          [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])}
         (when-let [e @!elem]
           [:div.fixed.top-0.right-0.text-white.bg-black.h-12.w-64.z-300
            [l/ppre e]
            [:div "THE THANG"] #_(tap> "!!")])

         (when-let [v @!thing]
           [:div.static.top-0.left-0.text-white.bg-black.h-12.w-64.z-300
            [l/ppre v]
            [:div "THE THING"] #_(tap> "!!")])


         (cond
           is-form? (form m)
           is-cover? (cover m)
           is-confirm? (confirm m)
           is-message? (message m)
           :else [:div "UNDEFINED"])])))