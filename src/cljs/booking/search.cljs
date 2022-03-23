(ns booking.search
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [goog.events.KeyCodes :as keycodes]
            [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]))

;region search

(rf/reg-event-db :lab/set-search-expr (fn [db [_ search-expr]]
                                        (assoc db :lab/search-expr search-expr)))

(rf/reg-event-db :lab/set-search-mode (fn [db [_ searchmode?]]
                                        (assoc db :lab/set-search-mode searchmode?)))

(rf/reg-event-db :lab/toggle-search-mode (fn [db]
                                           (update db :lab/set-search-mode (fnil not false))))

(rf/reg-event-db :lab/start-search (fn [db]
                                     (assoc db :lab/run-search true)))

(rf/reg-event-db :lab/stop-search (fn [db]
                                    (assoc db :lab/run-search false)))

(rf/reg-sub :lab/is-search-running? :-> :lab/run-search)

(rf/reg-sub :lab/in-search-mode? :-> :lab/set-search-mode)

(rf/reg-sub :lab/search-expression :-> :lab/search-expr)

;endregion

(o/defstyled experiment :div
  [:&
   :rounded-full
   {:color "var(--text3)"}
   [:&:hover {:color      "var(--text2)"
              :background "var(--surface0)"}]])

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
                       (rf/dispatch [:lab/set-search-mode false])
                       (rf/dispatch [:lab/stop-search])
                       (rf/dispatch [:lab/set-search-expr ""]))
        set-refs #(when @a
                    (.addEventListener @a "keydown"
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
      {:display-name         "search-widget"
       :component-did-update (fn [_] (when @a (.focus @a)))
       :reagent-render
       (fn []
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
            [experiment
             [:div.flex.items-center.justify-center.h-full (conj
                                                             {:class [:w-10 :shrink-1]}
                                                             (when-not @search
                                                               {:on-click enter-search}))
              [sc/icon [:> solid/SearchIcon]]]])

          (when @search
            [:input.w-full.h-full.outline-none.focus:outline-none
             {:style       {:xflex "1 1 100%"}
              :placeholder "Søk (<linjeskift> å starte)"
              :type        :text
              :on-blur     #(let [s (-> % .-target .-value)]
                              (if (empty? s)
                                (exit-search)))
              :ref         #(when-not @a
                              ;(set-focus % a)
                              (reset! a %)
                              (set-refs))}])

          (when @search
            [experiment2
             [:div.flex.items-center.justify-center.h-full {:class    [:w-10]
                                                            :on-click exit-search}
              [sc/icon [:> solid/XIcon]]]])])})))
