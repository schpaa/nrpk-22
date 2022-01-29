(ns schpaa.components.views
  (:require
    [schpaa.debug :as l]
    [re-frame.db]
    [tick.core :as t']
    [times.api :as ta]
    [fork.re-frame :as fork]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [schpaa.components.fields :as fields]
    [schpaa.modal :as modal]
    [tick.alpha.interval]
    [eykt.fsm-helpers :refer [send]]
    [schpaa.icon :as icon]
    ;todo remove dep
    [re-statecharts.core :as rs]
    [schpaa.style :as st]))


(defn- dark-rounded-view [& content]
  [:div.xrounded.px-4.py-2.space-y-4.-mx-4
   {:class ["bg-gray-400"]}
   (into [:<>] (map identity content))])

(defn- tab-rounded-view [& content]
  [:div.rounded-t-lg.px-3.py-4.space-y-4
   {:class ["bg-gray-100" "dark:bg-gray-700"]}
   (into [:<>] (map identity content))])

(defn- tab-info-view [& content]
  [:div.rounded-t-lg.px-3.py-4.space-y-4
   {:class ["bg-orange-200" "dark:bg-orange-700"]}
   (into [:<>] (map identity content))])

(defn- tab-float-view [& content]
  [:div.rounded.px-3.py-4.space-y-4.shadow.panel
   #_{:class ["bg-orange-200" "dark:bg-orange-700"]}
   (into [:<>] (map identity content))])

(defn rounded-view [{:keys [float info tab dark flat] :as opt} & content]
  (cond
    flat [:div.xp-4.xspace-y-4.panel
          #_{:class ["bg-orange-200" "dark:bg-orange-700"]}
          (into [:<>] (map identity content))]
    float [:div.rounded.p-4.space-y-4.shadow.panel
           ;{:class ["bg-orange-200" "dark:bg-orange-700"]}
           (into [:<>] (map identity content))]

    :else (if (:float opt)
            [tab-float-view content]
            (if (:info opt)
              [tab-info-view content]
              (if (:tab opt)
                [tab-rounded-view content]
                (if (:dark opt)
                  [dark-rounded-view content]
                  [:div.rounded-b.p-3.space-y-4.debug
                   {:class [:panel

                            :shadow]}
                   (into [:<>] (map identity content))]))))))

(defonce my-state (r/atom {}))

;(defn booking-header [{:keys [book-now]}]
;  (rounded-view
;    {}
;    [:h2.h-10.flex.items-center "Booking-header med betingelser etc"]
;    [:div.flex.justify-end
;     [:button.btn.btn-free.btn-cta {:on-click #(book-now)} "Book båt"]]))

(rf/reg-sub :exp/filter (fn [db]
                          (ta/instant->str (:date-filter db))))

(rf/reg-event-db :exp/set-date-filter (fn [db [_ arg]]
                                        (tap> arg)
                                        (assoc db :date-filter arg)))

;(defn booking-confirmed [{:keys [values]}]
;  (rounded-view
;    {}
;    [:div "Confirmed"]
;    [l/ppre values]
;
;    [:div.flex.gap-4.justify-between
;     ;(fields/button #(state/send :e.restart) nil "s")
;     [:button.btn.btn-danger
;      {:type     :button
;       :on-click #(send :e.cancel-booking values)} "Annuler"]
;     [:button.btn.btn-cta.text-black
;      {:type     :button
;       :on-click #(send :e.restart)} "Bekreft"]]))


(defn general-header [& content]
  [:div.h-16x.flex.p-3.items-center.sticky.top-32x.mt-3.z-10
   {:class [:panel]}
   content])

(defn general-footer
  "a footer for all lists where editing of some sort makes sense"
  [{:keys [insert-before accepted-user? edit-state markings c data key-fn show-all]}]
  [:div.flex.justify-between.p-4.sticky.bottom-0.z-0
   {:class ["bg-gray-500/90"]}
   [:div.gap-2.flex
    (when insert-before
      (insert-before))
    [:button.btn-small {:on-click #(swap! edit-state not)} (if @edit-state "Ferdig" "Endre")]
    (when @edit-state
      [:button.btn-small
       {:on-click #(reset! markings (reduce (fn [a e] (assoc a e true)) {} (map key-fn data)))}
       "Merk alt"])
    (when @edit-state
      [:button.btn-small
       {:disabled (zero? c)
        :on-click #(reset! markings {})}
       "Merk ingen"])
    (if @edit-state
      (let []
        [:button.btn-small.btn-menu
         {:disabled (zero? c)
          :on-click #(js/alert "!") #_#(do
                                         (booking.database/delete selected-keys)
                                         (reset! markings {}))}
         [:div.flex.gap-2.items-center (icon/small :chevron-down) (str "Operasjon " (when (pos? c) c))]])
      [:div])]
   (when accepted-user?
     (when-not @show-all
       [:button.btn-small.btn-free {:on-click #(reset! show-all true)} "Vis alle"]))])

(defn goto-chevron [location]
  [:div.w-12.flex.items-center.justify-center
   {:class    ["hover:bg-gray-500" "bg-black/50" "text-white" "dark:bg-gray-700"]
    #_#_:on-click #(modal/message-with-timeout "test" location)}
   [icon/touch :chevron-right]])

; region

(defn normalize-kind [kind]
  (case kind
    "havkayak" "Havkayak"
    "sup" "Standup padlebrett"
    "grkayak" "Grønnlands\u00adkayak"
    "surfski" "Surfski"
    "?"))

(defn name-view [name]
  (let [{:keys [hd fg+]} (st/fbg' :listitem)]
    [:div.truncate.leading-6
     {:class (concat hd fg+)}
     name]))

(defn number-view [n]
  [:div.font-oswald.tracking-wider.text-xl.font-semibold.leading-normal.h-full.w-10.flex.justify-center.items-center.rounded-sm.h-8
   {:class ["dark:text-gray-300" "text-gray-700" :dark:bg-gray-900 :bg-white]}
   n])

(defn show-more-number-view []
  (number-view "..."))

(defn slot-view [slot]
  [:div.flex.items-center.justify-center.w-8.rounded-sm.h-8.mb-pxx
   {:class [
            "dark:bg-gray-600"
            "dark:text-gray-200"
            :border-2
            :border-gray-600
            :dark:border-gray-400
            "bg-clear"
            "text-gray-700"]}
   [:div.font-oswald.font-normal.whitespace-nowrap.text-base slot]])

(defn modern-checkbox [st label]
  [:div.flex.items-center.gap-4.text-black
   [:button {:on-click #(swap! st not)
             :class    (concat
                         (if @st [:bg-green-600] [:bg-gray-200])
                         [:relative
                          :inner-shadow
                          :bg-gray-300
                          :inline-flex :shrink-0
                          :focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400
                          :rounded-full :border-2
                          :transition-colors :ease-in-out :duration-100
                          :h-7 :w-12])}
    [:span {:class (concat
                     (if @st [:translate-x-5] [:translate-x-0])
                     [:bg-white
                      :transition :duration-100
                      :inline-block :w-6 :h-6
                      :ease-in-out
                      :shadow :transform :ring-0
                      :rounded-full])}
     #_[:div.relative
        {:class [:ease-in-out :transition]}
        [:div.absolute.inset-0 {:class (concat (if @st [:opacity-0] [:opacity-100]) [:duration-100 :xtransition])}
         [:div.flex.items-center.justify-center (icon/adapt :cross-out 1)]]
        [:div.absolute.inset-0 {:class (concat (if @st [:opacity-100] [:opacity-0]) [:duration-100 :xtransition])}
         [:div.flex.items-center.justify-center (icon/adapt :checked 1)]]]]]
   [:div label]])

(defn modern-checkbox' [{:keys [get-details set-details disabled?]} label-fn]
  (let [value (get-details)
        checkbox [:button {:on-click #(set-details (not value))
                           :disabled disabled?
                           :type :button
                           :class    (concat
                                       (if value [:bg-alt] [:bg-gray-400 :dark:bg-gray-700])
                                       [:relative
                                        :inner-shadow


                                        :dark:border-gray-900  :border-gray-500

                                        :inline-flex :shrink-0
                                        :focus:outline-none :focus:ring-2 :focus:ring-offset-2 :focus:ring-cyan-400
                                        :rounded-full :border-2
                                        :transition-colors :ease-in-out :duration-100
                                        :h-7 :w-12])}
                  [:span {:class (concat
                                   (if value [:translate-x-5] [:translate-x-0])
                                   [:bg-white
                                    :transition :duration-100
                                    :inline-block :w-6 :h-6
                                    :ease-in-out
                                    :shadow :transform :ring-0
                                    :rounded-full])}]]]
    [:div.flex.items-center.gap-4.select-none
     (when-not disabled? {:on-click #(set-details (not value))})
     (label-fn checkbox)]))

;;

(defn empty-list-message [msg & r]
  (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :void)]
    [:div.grow.flex.flex-col.items-center.justify-center.mb-32.mt-8
     {:class (concat fg+ bg+)}
     [:div {:class hd} msg]
     (for [e r] [:div.text-xlx.font-semiboldx {:class p+} e])]))