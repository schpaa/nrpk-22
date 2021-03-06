(ns booking.fileman
  (:require [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [reagent.core :as r]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [times.api :as ta]
            [tick.core :as t]
            [schpaa.style.button :as scb]
            [db.core :as db]
            [schpaa.debug :as l]
            [schpaa.icon :as icon]
            [re-frame.core :as rf]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as button]))

(o/defstyled listitem :div
  [:&
   :flex :items-center
   {:padding-block "var(--size-1)"}
   [:.selected :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :border-radius  "var(--radius-2)"
     :color          [:rgba 0 0 0 1]
     :xbackground    [:rgba 0 0 0 0.09]}]
   [:.normal :w-full
    {;:padding-block  "var(--size-1)"
     :padding-inline "var(--size-2)"
     :color          "var(--buttoncopy)" #_[:rgba 0 0 0 0.5]}]
   [:&:hover {:border-radius "var(--radius-1)"
              :background    [:rgba 0 0 0 0.1]}]]
  ([a c]
   ;^{:class [:selected]}
   [:div a c]))

(defn visible [k v]
  [scb/round-normal-listitem
   {:style    {:background (when (:visible v) "var(--floating)")}
    :on-click #(db/database-update
                 {:path  ["booking-posts" "articles" (name k)]
                  :value {:visible (not (:visible v))}})}
   [sc/icon-small
    {:style {
             :color (if (:visible v) "var(--brand1)" "var(--button)")}}
    [:> outline/EyeIcon]]])

(defn locked [k v]
  [scb/round-normal-listitem
   {:on-click #(db/database-update
                 {:path  ["booking-posts" "articles" (name k)]
                  :value {:lock (not (:lock v))}})}
   [sc/icon-small
    {:style {:color (if (:lock v) "var(--danger)" "var(--button)")}}
    [:> (if (:lock v) outline/LockClosedIcon outline/LockOpenIcon)]]])

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)

   {;:style {:background "red"}
    :on-click #(db/database-update
                 {:path  ["booking-posts" "articles" (name k)]
                  :value {:deleted (not deleted)}})}
   [sc/icon-small
    {:style {:color "var(--buttoncopy)"}}
    (if deleted (icon/adapt :rotate-left 1.5)
                [:> outline/TrashIcon])]])

#_(defn- toolbar []
    (r/with-let [show-deleted (r/atom false)
                 sort-by-created (r/atom true)]
      [:div.sticky.top-0.z-10
       {:style {:padding-inline "var(--size-2)"
                :color          :black
                :background     "var(--surface0)"}}
       [:div {:class [:h-12 :p-2 :flex :items-stretch :items-center :gap-2]}
        [scb2/normal-tight
         {:on-click #(rf/dispatch [:lab/just-create-new-blog-entry])}
         [sc/icon [:> outline/PlusIcon]]]
        [:div.grow]
        [scb2/normal-tight
         [sc/icon [:> outline/FolderIcon]]]

        [:div.relative
         {:on-click #(swap! sort-by-created (fnil not true))}
         [scb2/normal-tight
          [sc/icon {:class (if @sort-by-created [:text-black] [:opacity-30])} [:> outline/SortAscendingIcon]]]
         #_(if @sort-by-created
             [:div.absolute.inset-0
              [scb2/normal-tight-clear
               [sc/icon-large [:> outline/XIcon]]]])]

        [:div.relative
         {:class    []
          :on-click #(swap! show-deleted (fnil not true))}
         [scb2/normal-tight
          [sc/icon {:class (if @show-deleted [:text-black] [:opacity-30])} [:> outline/TrashIcon]]]
         #_(if @show-deleted
             [:div.absolute.inset-0
              [scb2/normal-tight-clear
               [sc/icon-large {:class [:text-white]} [:> outline/XIcon]]]])]]]))

(def page-state (r/atom {:show-deleted true}))

(defn render [st data]
  (r/with-let [show-deleted (schpaa.state/listen :filemanager/show-deleted)
               sort-by-created (r/atom true)]
    [:div #_{:class (into [:duration-200] (if @st [:h-64 :opacity-100]
                                                  [:h-0 :opacity-0]))}
     [:div {:style {:border-radius "var(--radius-0)"
                    :sbackground   "var(--surface000)"}}
      #_[toolbar]

      [:div {:class [:relative]
             :style {:xbackground "var(--surface00)"
                     :xbox-shadow "var(--inner-shadow-0)"}}

       [:div.px-2x {:class [:xoverflow-y-auto :-snap-y :xh-52]}
        (r/with-let [time (ta/short-date-format (t/now))
                     date (ta/time-format (t/now))
                     st (r/atom nil)
                     act (r/atom nil)
                     focus-field-id (r/atom nil)
                     get-toggle-fn #(schpaa.state/listen [:some-select %])
                     set-toggle-fn #(schpaa.state/toggle [:some-select %])]

          [:div
           ;[l/ppre-x (map (comp (if @sort-by-created :created :date) val) (sort-by (comp (if @sort-by-created :created :date) val) > data))]
           (into [:div.snap-center.select-none]
                 (for [[e [k {:keys [created deleted content title date] :as v :or {title (str "untitled-" e)}}]]
                       (map-indexed vector (filter (fn [[k v]] (if @show-deleted true (not (:deleted v false))))
                                                   (sort-by (comp (if @sort-by-created :created :date) val) > data)))]

                   ^{:key (str e)}
                   [:div
                    [listitem
                     {:class (into [] (if (= e @st) [:selected] [:normal]))}
                     [sc/row-sc-g2 {:style {:opacity         (if deleted 0.3 1)
                                            :text-decoration (if deleted :line-through)}}

                      #_[scb/round-normal-listitem
                         {:on-click #(set-toggle-fn k)}
                         [sc/icon-small {:class (into [:duration-200] (if @(get-toggle-fn k) [:rotate-90]))} ico/showdetails]]

                      (r/with-let [value (r/atom title)
                                   set-fn (fn [k v] (db/database-update
                                                      {:path  ["booking-posts" "articles" (name k)]
                                                       :value {:date  (str (t/now))
                                                               :title v}}))]
                        (if (= e @focus-field-id)
                          [:input {:auto-focus  true
                                   :ref         (fn [el] (if-not @act (reset! act el)))
                                   :type        :text
                                   :value       @value
                                   :on-key-down #(cond
                                                   (= 13 (.-keyCode %))
                                                   (do (set-fn k @value) (reset! focus-field-id nil))
                                                   (= 27 (.-keyCode %))
                                                   (do (reset! focus-field-id nil)))
                                   :on-change   #(do
                                                   (tap> {:key (.-keyCode %)})
                                                   (reset! value (-> % .-target .-value)))
                                   :on-blur     #(let [v (-> % .-target .-value)]
                                                   (if (empty? v)
                                                     (do #_(swap! light assoc-in [e :text] (str "uten-tittel-" e)))
                                                     (do (set-fn k @value) (reset! focus-field-id nil)))
                                                   (reset! act nil))
                                   :style       {:background    "white" #_"var(--surface000)"
                                                 :padding-block "var(--size-1)"
                                                 :height        "100%"}
                                   :class       [:px-1 :cursor-text :focus:outline-none :h-full :w-full]}]
                          [sc/text1 {:on-click #(do
                                                  (reset! focus-field-id e)
                                                  (reset! value title)
                                                  (when @act
                                                    (do
                                                      (tap> "Attempt focus")
                                                      (.focus @act)))
                                                  #_(.stopPropagation %))
                                     :class    [:px-1 :cursor-pointer :truncate :w-full (if deleted :line-through)]}
                           title]))

                      #_[scb/static-listitem
                         [sc/icon-small [:> (if (empty? (:description v)) outline/DocumentIcon outline/DocumentTextIcon)]]]

                      (let [source (if @sort-by-created date created)
                            date (or (some-> source (t/instant) (t/date) (ta/tech-date-format)))
                            time (or (some-> source (t/instant) (t/time) (ta/time-format)))]
                        [sc/row {:class [:tabular-nums :tracking-tighter]}
                         [sc/text-clear {:class [:w-24]} (or date "-")]
                         [sc/text-clear {:class [:w-12 (if time :text-right)]} (or time "-")]])
                      ;[:div.grow]

                      (visible k v)
                      (locked k v)
                      (trashcan k v)]]

                    [:div
                     {:class (into [:duration-200] (if @(get-toggle-fn k)
                                                     [:h-32 :opacity-100]
                                                     [:h-0 :opacity-0 :pointer-events-none]))}
                     [listitem {:style {:padding-inline "var(--size-1)"}
                                :class [:h-10]}
                      [sc/row'' {:class [:items-center :gap-2]}
                       [scb/static-listitem
                        [sc/icon-small [:> (if (empty? (:description v)) outline/DocumentIcon outline/DocumentTextIcon)]]]
                       [sc/text-clear {:class [:px-1]} (:description v)]]]]]))])]]]]))

;---------------------

(comment
  (if (get-in @light [e :edit])
    [:input {:auto-focus  true
             :ref         (fn [el] (if-not @act (reset! act el)))
             :type        :text
             :value       (get-in @light [e :text] "untitled")
             :on-key-down #(cond
                             (= 13 (.-keyCode %))
                             (swap! light assoc-in [e :edit] false)
                             (= 27 (.-keyCode %))
                             (swap! light assoc-in [e :edit] false))
             :on-change   #(do
                             (tap> {:key (.-keyCode %)})
                             (swap! light assoc-in [e :text] (-> % .-target .-value)))
             :on-blur     #(let [v (-> % .-target .-value)]
                             (if (empty? v)
                               (swap! light assoc-in [e :text] (str "uten-tittel-" e)))
                             (swap! light assoc-in [e :edit] false)
                             (reset! act nil))
             :style       {:background "white" #_"var(--surface000)"}
             :class       [:px-1 :cursor-text :focus:outline-none :h-full :w-full]}]
    [sc/text-clear {:on-click #(do
                                 (swap! light assoc-in [e :edit] true)
                                 (when @act
                                   (do
                                     (tap> "Attempt focus")
                                     (.focus @act)))
                                 #_(.stopPropagation %))
                    :class    [:px-1 :cursor-pointer :truncate :w-full]}
     title #_(get-in @light [e :text] "untitled")]))

(comment
  (defn lock-icon
    "docstring"
    []
    [scb/round-normal-listitem
     {:on-click #(do
                   (swap! light update-in [e :lock] (fnil not false))
                   (.stopPropagation %))}
     [sc/icon-small
      {:style {:color (if (get-in @light [e :lock]) "var(--red-8)" "var(--surface1)")}}
      [:> (if (get-in @light [e :lock]) outline/LockClosedIcon outline/LockOpenIcon)]]]))

(defn panel []
  (r/with-let [show-deleted (schpaa.state/listen :activitylist/show-deleted)
               show-content (schpaa.state/listen :activitylist/show-content)]
    [sc/row-sc-g2-w
     ;[hoc.toggles/switch :activitylist/show-editing "Rediger"]
     [hoc.toggles/ls-sm :filemanager/show-deleted "Vis slettede"]
     [hoc.toggles/ls-sm :activitylist/left-aligned "Bruk hele skjermbredden"]
     [hoc.toggles/ls-sm :activitylist/limit-timeline "Bare vis mine innlegg"]
     [hoc.toggles/ls-sm :activitylist/limit-active "Skjult historie"]]))

(defn always-panel []
  [sc/row-sc-g2-w
   [button/icon-and-caption {:on-click #(rf/dispatch [:lab/just-create-new-blog-entry])} ico/plus "Nytt innlegg"]
   [button/just-caption {:class [:regular]} "Annet?"]])