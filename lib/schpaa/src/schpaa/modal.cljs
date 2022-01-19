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
(defn- set-dirty-modal-state []
  (assign (fn [st _]
            (tap> "set-dirty-modal-state")
            (assoc st :modal-dirty true)

            #_(assoc st
                :modal false
                :modal-forced false))))
(defn- set-clean-modal-state []
  (assign (fn [st _]
            (tap> "set-clean-modal-state")
            (assoc st :modal-dirty false)

            #_(assoc st
                :modal false
                :modal-forced false))))
(defn- set-normal-modal-state []
  (assign (fn [st {:keys [data more-data] :as _event}]
            (assoc st
              :modal-dim data
              :modal-config-fn (:modal-config-fn (first more-data))
              :modal-forced false
              :modal true))))
(defn- set-forced-modal-state []
  (assign (fn [st {:keys [data more-data] :as _event}]
            (assoc st
              :modal-dim data
              :modal-config-fn (:modal-config-fn (first more-data))
              :modal-forced true))))

(def modal-machine
  {:initial :s.initial
   :states  {:s.initial {:entry (clear-modal-state)
                         :on    {:e.show-locked       :s.locked
                                 :e.show-with-timeout :s.timeout
                                 :e.show              :s.visible}}
             :s.visible {:entry (set-normal-modal-state)
                         :on    {:e.dirty {:actions (set-dirty-modal-state)}
                                 :e.clean {:actions (set-clean-modal-state)}
                                 :e.hide :s.initial}}
             ;intent Showing again will close it
             ;fixme BUG
             ;:e.show :s.initial}}
             :s.locked  {:entry (set-forced-modal-state)
                         :on    {:e.hide :s.initial}}
             ;intent Showing again will close it
             ;fixme BUG
             ;:e.show :s.initial}}
             :s.timeout {:entry (set-normal-modal-state)
                         :on    {:e.hide :s.initial}
                         ;intent Showing again will close it
                         ;fixme BUG
                         ;:e.show-with-timeout :s.initial}
                         :after [{:delay  (fn [_ {:keys [data]}] (:timeout data default-dialog-timeout))
                                  :target :s.initial}]}}})

(defn form-action
  [{:keys [header form-fn footer title close-fn flags icon]
    :or   {close-fn (fn [] (send :e.hide))}}]
  (apply send [(cond
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
                                    [:div.self-center.justify-self-start [icon/touch icon]]
                                    [:div.self-center title]]
                                   [:div.col-span-2.self-center.font-bold title])]
                                (when (some #{:close-button} flags)
                                  [:div {:on-click close-fn} [icon/small :cross-out]])]}
                      {:header header})
                    (if footer
                      {:footer (cond
                                 (string? footer) [:div.px-2.py-4.text-sm.space-y-2 footer]
                                 :else (into [:div.px-2.py-4.text-sm.space-y-2] footer))})
                    {:form-fn form-fn}))}]))

;(def form-action (fn [m] (apply send (msg/form-action m))))
;(def form-action (fn [m] (apply send (msg/form-action m))))
;(def are-you-sure? (fn [m] (apply send (msg/are-you-sure? m))))
;(def are-you-sure-forced? (fn [m] (apply send (msg/are-you-sure-forced? m))))
;(def are-you-sure-cover-and-forced? (fn [m] (apply send (msg/are-you-sure-cover-and-forced? m))))
;(def error-message (fn [m] (apply send (msg/error-message m))))
;(def message-dialog (fn [& e] (apply send (apply msg/message e))))
;(def message-with-timeout (fn [& e] (apply send (apply msg/message-with-timeout e))))

(defn just-buttons [vbuttons]
  (fn []
    [:div.p-4.flex.gap-2.justify-end
     (for [e vbuttons]
       (cond
         (vector? e)
         (let [[caption class action] e]
           [:button.btn
            {:class    class
             :on-click action}
            caption])
         :else (case e
                 :grow [:div.flex-grow]
                 [:div])))]))

(defn ok-cancel-buttons [{:keys [on-ok on-cancel]}]
  (fn []
    [:div.p-4.flex.gap-2.justify-end
     [:button.btn.btn-free
      {:on-click on-cancel}
      "Avbryt"]
     [:button.btn.btn-danger
      {:on-click on-ok}
      "Ok"]]))

(defn overlay-with [{:keys [modal? on-close modal-dim]} content]
  [:div.relative.h-full.cursor-default
   [:div.fixed.inset-0
    {:class    (concat
                 animation-duration
                 [:transition :bg-black]
                 (if modal?
                   [(case modal-dim
                      :weak-dim :bg-opacity-30
                      :bg-opacity-80) :pointer-events-auto :z-300]
                   [:bg-opacity-0 :pointer-events-none :z-300]))
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
   :message {:bgt [:bg-sky-500 :text-sky-50]
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

(defn form [{:keys [header form-fn footer dialog-type flags] :as m}]
  (let [{:keys [bg bgp bgf bgt]} (get color-map (cond
                                                  (some #{:error} flags) :error
                                                  (some #{:message} flags) :message
                                                  :else  dialog-type))]
    [:div.relative.top-0
     {:class bg}
     (when header
       [:div.absolute.top-0.sticky.inset-x-0.z-110
        {:style {:min-height "3rem"}
         :class (concat bgt [:flex :items-center :justify-between])}
        header])

     ;[l/ppre flags]

     (when form-fn
       [:div
        {:class bgp}
        (form-fn)])

     (when footer
       [:div
        {:class (concat bgf [#_:h-12 :flex :items-center :justify-between :px-2])}
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
        end-transition (r/atom :hiding)]
    (r/create-class
      {:display-name
       "modal-presenting-layer"

       :component-did-mount
       (fn [c]
         (let [_ (reset! node (reagent.dom/dom-node c))]
           (.addEventListener @node "transitioncancel"
                              (fn []
                                ;intent ?
                                (if (= :hiding @end-transition)
                                  (reset! show-status false)
                                  (reset! show-status true))))

           (.addEventListener @node "transitionend"
                              (fn []
                                (if @show-status
                                  (reset! end-transition :showing)
                                  (reset! end-transition :hiding))))))

       :reagent-render
       ;intent This is embedding a smaller component...
       (fn [{:keys [show? config-fn]}]
         (let [;todo Possibly move this into the reagent-render method
               {:keys [dialog-type flags style] :as m} (if (some? config-fn) (config-fn))
               {:keys [type final-position]
                :or   {;type           #{:message}           ;fixme
                       final-position [:translate-y-0]}} style]

           (reset! show-status show?)

           [:div.fixed.inset-x-1.select-none.rounded-b-lg
            {:class (concat (if (some #{:cover} flags) [:max-w-sm :top-32 :rounded] [:top-0])
                            (if (some #{:wide} flags) [:max-w-sm] [:max-w-xs])
                            [:overflow-hidden :transition :transform]
                            animation-duration
                            (if @show-status final-position)
                            [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])

             :style (when-not @show-status
                      (if (some #{:cover} flags)
                        {:transform "translate(0,-150%)"}
                        {:transform "translate(0,-110%)"}))}

            ;intent ...picked up here
            (cond
              (= dialog-type :form) (when-not (and (= false @show-status) (= :hiding @end-transition))
                                      (form m))
              :else [:div.bg-red-500.text-white "UNDEFINED"])]))})))
