(ns booking.search
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [goog.events.KeyCodes :as keycodes]
            [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [schpaa.style.button :as scb]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.debug :as l]))

;region search

(rf/reg-event-db :lab/set-search-expr (fn [db [_ search-expr]]
                                        (assoc db :lab/search-expr search-expr)))

(rf/reg-event-db :lab/set-search-mode (fn [db [_ searchmode?]]
                                        (assoc db :lab/in-search-mode searchmode?)))

(rf/reg-event-db :lab/toggle-search-mode (fn [db]
                                           (update db :lab/in-search-mode (fnil not false))))

(rf/reg-event-db :lab/start-search (fn [db]
                                     (assoc db :lab/run-search true)))

(rf/reg-event-db :lab/stop-search (fn [db]
                                    (assoc db :lab/run-search false)))

(rf/reg-event-db :lab/quit-search (fn [db _]
                                    (-> db (assoc :lab/in-search-mode false
                                                  :lab/run-search false
                                                  :lab/search-expr ""))))

(rf/reg-sub :lab/is-search-running? :-> :lab/run-search)

(rf/reg-sub :lab/in-search-mode? :-> :lab/in-search-mode)

(rf/reg-sub :lab/search-expression :-> :lab/search-expr)

;endregion

(o/defstyled experiment2 :div
  [:&
   :rounded-full
   {:color "var(--text1)"}
   [:&:hover {:color "var(--red-5)"}]])

(o/defstyled experiment3 :div
  [:&
   {:color "var(--text1)"}])

(defn search-menu []
  (let [a (r/atom nil)
        value (rf/subscribe [:lab/search-expression])
        enter-search #(rf/dispatch [:lab/set-search-mode true])
        exit-search #(do
                       (reset! a nil)
                       (rf/dispatch [:lab/quit-search]))
        ;(rf/dispatch [:lab/set-search-mode false])
        ;(rf/dispatch [:lab/stop-search])
        ;(rf/dispatch [:lab/set-search-expr ""]))
        set-refs #(when %
                    (.addEventListener % "keydown"
                                       (fn [event]
                                         (cond
                                           (= keycodes/ESC event.keyCode)
                                           (do
                                             (.stopPropagation event)
                                             (exit-search))
                                           (= keycodes/ENTER event.keyCode)
                                           (rf/dispatch [:lab/start-search])))))
        search (rf/subscribe [:lab/in-search-mode?])]

    (r/create-class
      {:display-name           "search-widget"
       :component-did-update   (fn [new-props prev-props]
                                 (if @search
                                   (do
                                     (tap> {":component-did-update" @search})
                                     (when @a (.focus @a)))
                                   (reset! a nil)))
       :component-will-unmount (fn [_]
                                 ;(tap> ":component-will-unmount")
                                 (reset! a nil))
       :reagent-render
       (fn []
         [:div
          ;[l/ppre-x @a]
          [:div.h-10.duration-200.rounded-full
           {:class (into [:flex :gap-1]
                         [(if @search :bg-white)
                          (if @search :px-x1)
                          (if @search :w-full :w-10)])}

           (if @search
             [experiment3
              [:div.flex.items-center.justify-center.h-full {:class    [:w-10 :shrink-1]
                                                             :on-click #(.stopPropagation %)}
               [sc/icon [:> solid/SearchIcon]]]]
             [hoc.buttons/round
              {:on-click #(when-not @search (enter-search))}
              [sc/icon [:> solid/SearchIcon]]])

           (when @search
             [:input.w-full.h-full.outline-none.focus:outline-none
              {:style       {:xflex "1 1 100%"}
               :placeholder "Søk (<linjeskift> å starte)"
               :type        :text
               :on-blur     #(let [s (-> % .-target .-value)]
                               (if (empty? s)
                                 (exit-search)))
               :ref         (fn [el] (when-not @a
                                       (reset! a el)
                                       ;(set-focus % a)
                                       (set-refs @a)))}])

           (when @search
             [experiment2
              [:div.flex.items-center.justify-center.h-full {:class    [:w-10]
                                                             :on-click exit-search}
               [sc/icon [:> solid/XIcon]]]])]])})))
