(ns schpaa.modal
  (:require [schpaa.icon :as icon]
            [reagent.core :as r]
            [reagent.dom]
            [schpaa.modal.msg :as msg]
            [nrpk.fsm-helpers :refer [send]]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [schpaa.button :as bu]
            [schpaa.style :as st]
            [clojure.set :as set]))

(def default-dialog-timeout 5000)
(def animation-duration [:duration-300])

;region fsm-modal-helpers

(defn- clear-modal-state []
  (assign (fn [st _] (assoc st
                       :modal false
                       :modal-forced false))))
(defn- set-dirty-modal-state []
  (assign (fn [st _]
            (tap> "set-dirty-modal-state")
            (assoc st :modal-dirty true))))

(defn- set-clean-modal-state []
  (assign (fn [st _]
            (tap> "set-clean-modal-state")
            (assoc st :modal-dirty false))))

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

;endregion

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



(defn overlay-with [{:keys [modal? on-close modal-dim]} & content]
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
  {:form    {:bgt [:bg-gray-300 :text-gray-700
                   :dark:bg-gray-600 :dark:text-gray-100]
             :bgp [:bg-gray-300 :text-gray-700
                   :dark:bg-gray-600 :dark:text-100]
             :bg  [:bg-gray-100 :text-gray-700
                   :dark:bg-gray-600 :dark:text-100]
             :bgf [:bg-gray-600 :text-gray-200
                   :dark:bg-gray-700 :dark:text-gray-100]}
   :cover   {:bgt [:bg-gray-300 :text-black :font-bold :text-2xl]
             :bgp [:bg-gray-300 :text-gray-700]
             :bg  [:bg-gray-300 :text-gray-700]
             :bgf [:bg-gray-900 :text-gray-200]}
   :error   {:bgt [:bg-rose-500 :text-rose-300]
             :bg  [:bg-rose-500 :text-rose-200]
             :bgp [:bg-rose-500 :text-rose-100]
             :bgf [:bg-rose-900 :text-rose-200]}
   :message {:bgt [:bg-sky-500 :text-sky-50]
             :bg  [:bg-alt :text-gray-100]
             :bgp [:bg-alt :text-gray-100 :dark:bg-gray-500 :dark:text-gray-100]
             :bgf [:text-white :bg-black]}
   :confirm {:bgt [:bg-gray-50 :text-gray-700]
             :bg  [:bg-gray-50 :text-gray-700]
             :bgp [:bg-gray-500 :text-gray-100]
             :bgf [:text-white :bg-alt]}})

(defn form-action
  [{:keys [type header form-fn footer title close-fn flags icon button-bar]
    :as   m
    :or   {close-fn (fn [] (send :e.hide))}}]
  (let [flags (cond-> (into #{} flags)
                (= :message type) (conj :timeout))]
    (apply send [(cond
                   (:forced flags) :e.show-locked
                   (:timeout flags) :e.show-with-timeout
                   :else :e.show)
                 (if (some #{:weak-dim} flags) :weak-dim :normal-dim)
                 {:modal-config-fn
                  (fn []
                    (conj
                      ;intent Hardwired
                      {:style       {:final-position [:-translate-y-100]}
                       :dialog-type (or type :form)
                       :flags       (conj flags :wide)}
                      ;intent carry over everything
                      m
                      ;intent Soft
                      (if title
                        {:header [:div.grid.gap-2.p-4.text-base.font-normal.w-full
                                  {:style {:grid-template-columns "2rem 1fr min-content"}}
                                  (if icon
                                    [:<>
                                     [:div.self-center.justify-self-start [icon/touch icon]]
                                     [:div.self-center title]]
                                    [:div.col-span-2.self-center.font-bold title])
                                  (when (some #{:close-button} flags)
                                    [:div {:on-click close-fn} [icon/small :cross-out]])]}
                        (if (string? header)
                          {:header [:div.p-4 {:class (mapcat val (select-keys (st/fbg' :dialog) [:bg :fg+ :hd]))} header]}
                          {:header header}))
                      (if footer
                        {:footer (cond
                                   (string? footer) [:div.px-4.py-4.text-sm.space-y-2 footer]
                                   :else (into [:div.px-4.py-4.text-sm.space-y-2] footer))})
                      {:button-bar button-bar
                       :form-fn    form-fn}))}])))

(defn form [{:keys [type header form-fn footer dialog-type flags] :as m}]
  (let [{:keys [bg bgp bgf bgt]} (get color-map dialog-type)]
    [:div.relative.top-0
     {:class bg}
     (when header
       header)

     (when form-fn
       [:div.p-4
        {:class bgp}
        (form-fn m)])

     (when footer
       [:div
        {:class (concat bgf [:flex :items-center :justify-between :xpx-2])}
        footer])]))

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
               {:keys [dialog-type flags style source] :as m} (if (some? config-fn) (config-fn))
               {:keys [final-position]
                :or   {final-position [:translate-y-100]}} style]

           (reset! show-status show?)

           [:div.fixed.inset-x-1.select-none
            {:class (concat (if (some #{:cover} flags) [:max-w-sm :top-32 :rounded])
                            (if (= :bottom source)
                              [:bottom-0 :rounded-t-lg]
                              [:top-0 :rounded-b-lg])
                            (if (some #{:wide} flags) [:max-w-sm] [:max-w-xs])
                            [:overflow-hidden :transition :transform]
                            animation-duration
                            (if (or (= :showing @end-transition) @show-status) [:z-400 :h-auto :mx-auto :shadow :drop-shadow-xl :filter])
                            (if @show-status final-position))

             :style (when-not @show-status
                      (if (some #{:cover} flags)
                        {:transform "translate(0,-150%)"}
                        (if (= :bottom source)
                          {:transform "translate(0,110%)"}
                          {:transform "translate(0,-110%)"})))}

            ;intent ...picked up here
            (when-not (and (= false @show-status) (= :hiding @end-transition))
              (form m))]))})))
