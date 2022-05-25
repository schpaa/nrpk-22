(ns booking.lab
  (:require [reagent.core :as r]
            [kee-frame.core]
            [re-frame.core :as rf]
            [db.core :as db]
            [db.auth]
            [schpaa.style.ornament :as sc]
            [booking.styles-cljc :as sc-cljc]
            [booking.styles :as bs]
            [schpaa.style.booking]
            [booking.data]
            [schpaa.style.menu]
            [fork.re-frame :as fork]
            [tick.core :as t]
            [schpaa.style.dialog :refer [open-dialog-confirmbooking]]
            [booking.views]
            [clojure.set :as set]
            [booking.qrcode]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.common-widgets :as widgets]
            [schpaa.debug :as l]
            [clojure.string :as str]
            [booking.access]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]
            [booking.timegraph :refer [timegraph-multi]]
            [lambdaisland.ornament :as o]
            [schpaa.style.button2 :as scb2]
            [schpaa.icon :as icon]
            [headlessui-reagent.core :as ui]))

;; store

(defonce store
         (r/atom {:selector            :sjøbasen
                  :show-flagged        true
                  :previous-step-value nil
                  :step                0
                  :current-filter      nil
                  :category            #{"kano" "surfski" "sup"}}))

;; accessors/cursors

(def selector (r/cursor store [:selector]))
(def selection (r/cursor store [:selection]))
(def category (r/cursor store [:category]))
(def current-filter (r/cursor store [:current-filter]))
(def step (r/cursor store [:step]))
(def previous-step-value (r/cursor store [:previous-step-value]))
(def show-flagged (r/cursor store [:show-flagged]))

;; selection

(defn- toggle [id]
  (swap! store
         update-in [:selection]
         (fn [e]
           (if (some #{id} e)
             (set/difference e #{id})
             (set/union e #{id})))))

(defn- clear-selection [r]
  (tap> {:r r})
  (when r
    (let [x (set (map (comp :id val) r))
          _ (tap> [(count x) x])
          this-group (remove nil? (map @selection x))
          _ (tap> this-group)]
      (button/reg-pill-icon
        {:disabled (zero? (count this-group))
         :on-click #(reset! selection (set/difference @selection x))
         :class    [:round :inverse :shrink-0]}
        ico/closewindow))))

(defn- select-all-in-group [r]
  (button/reg-pill-icon
    {:disabled (every? (set @selection) (vals r))
     :on-click #(doseq [e (map :id (vals r))]
                  (toggle e))
     :class    [:round :cta :shrink-0]}
    ico/check))


#_(defn time-input-validation [e]
    (into {} (remove (comp nil? val)
                     {:start-date (cond-> nil
                                    (empty? (:start-date e)) ((fnil conj []) "mangler")
                                    (and (not (empty? (:start-date e)))
                                         (some #{(tick.alpha.interval/relation
                                                   (t/date (:start-date e)) (t/date))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))

                      :start-time (cond-> nil
                                    (empty? (:start-time e)) ((fnil conj []) "mangler")
                                    (and (not (empty? (:start-date e)))
                                         (not (empty? (:start-time e)))
                                         (some #{(tick.alpha.interval/relation
                                                   (t/at (t/date (:start-date e)) (t/time (:start-time e)))
                                                   (t/date-time (t/now)))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))
                      :end-time   (cond-> nil
                                    (empty? (:end-time e)) ((fnil conj []) "mangler")
                                    (and (not (empty? (:start-date e)))
                                         (not (empty? (:end-time e)))
                                         (some #{(tick.alpha.interval/relation
                                                   (t/at (t/date (:end-date e)) (t/time (:end-time e)))
                                                   (t/at (t/date (:start-date e)) (t/time (:start-time e))))} [:precedes :meets])) ((fnil conj []) "er før 'fra kl'"))})))

#_(defn time-input-form [time-state]
    (r/with-let [lightning-visible (r/atom nil)]
      (let [toggle-lightning #(swap! lightning-visible (fnil not false))]
        [fork/form {:initial-values    {:start-date  (str (t/new-date))
                                        :start-time  (str (t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours))
                                        ;todo adjust for 22-23
                                        :end-time    (str (t/truncate (t/>> (t/time) (t/new-duration 3 :hours)) :hours))
                                        :end-date    (str (t/new-date))
                                        :id          0
                                        :description ""}
                    :form-id           "sample-form"

                    :prevent-default?  true
                    :state             time-state
                    :clean-on-unmount? true
                    :keywordize-keys   true
                    :on-submit         (fn [e] (tap> {:on-submit e}))
                    :validation        time-input-validation}

         (fn [{:keys [errors form-id handle-submit handle-change values set-values] :as props}]
           [:form.space-y-1.w-full
            {:class     [(when @lightning-visible :shadow-sm)]
             :id        form-id
             :on-submit handle-submit}
            [booking.views/time-input props false]])])))

;; styles

(def start-time (r/cursor store [:booking/start]))

(def end-time (r/cursor store [:booking/end]))

;; utils

(defn digits->time [[a b]]
  (let [ref (t/at (t/today) (t/midnight))]
    [(t/>> ref (t/new-duration (* 15 a) :minutes))
     (t/>> ref (t/new-duration (* 15 b) :minutes))]))

;; actions

(defn cancel-booking [cancel]
  [button/regular
   {:class    [:large :narrow :danger]
    :on-click cancel}
   "Avbryt"])

(defn cancel-booking' [cancel]
  [button/pill
   {:class    [:large :round :danger]
    :on-click cancel}
   [sc/icon ico/closewindow]])

(defn confirm-booking [complete]
  [button/cta
   {:class    [:large :narrow :cta]
    :on-click complete}
   "Bekreft"])

(defn confirm-booking' [complete]
  [button/pill {:class    [:large :round :cta]
                :on-click complete}
   [sc/icon ico/check]])

(defn previous-step [prev-step]
  [button/pill {:class    [:large :round :regular]
                :on-click prev-step}
   [sc/icon ico/prevStep]])

(defn next-step' [next-step]
  [button/pill {:class    [:large :round :cta]
                :disabled (empty? @selection)
                :on-click next-step}
   [sc/icon ico/nextStep]])

;; user-interface

(defn headline-plugin []
  (let [delete-mode? @(rf/subscribe [:rent/common-show-deleted])
        details-mode? @(rf/subscribe [:rent/common-show-details])]
    (remove nil?
            [(when-let [uid @(rf/subscribe [:lab/uid])]
               (when-let [data @(db/on-value-reaction {:path ["users" uid "starred"]})]
                 (when-let [c (remove (comp false? val) data)]
                   (when (pos? (count c))
                     [sc/row-sc-g2
                      [:div.relative
                       [button/reg-pill
                        {:class    [:large (if details-mode? :message :clear)]
                         :style    {:background-color (if @show-flagged "var(--yellow-6)" "transparent")}
                         :on-click #(swap! show-flagged (fnil not false))}

                        [sc/icon-large {:style {:color (when @show-flagged "var(--gray-9)")}}
                         ico/stjerne]]
                       [:div.absolute.-bottom-1.right-1.pointer-events-none
                        [:div.px-1x.h-4.grid.place-content-center
                         {:style {:min-width     "1.3rem"
                                  :background    "var(--text1)"
                                  :border-radius "var(--radius-round)"}}
                         [sc/small {:style {:color "var(--floating)"}
                                    :class [:bold]} (count c)]]]]]))))
             #_[button/reg-pill
                {:class    [:large
                            (if delete-mode? :danger :clear)]
                 :on-click #(swap! (r/cursor store [:rent/show-deleted]) not)}
                [sc/icon-large ico/trash]]])))

(defn plus-minus-time [time]
  (let [has-time (some? @time)]
    [sc/row-sc-g1

     [button/reg-pill-icon
      {:on-click #(do
                    (reset! time (-> (t/<< (t/time @time) (t/new-duration 1 :hours))
                                     (t/truncate :hours)))
                    (tap> [@time (some-> @time t/time)]))
       :disabled (not has-time)
       :style    {:min-width "3rem"
                  :border    "2px solid var(--text2)"}
       :class    [:regular :right-square :narrow]}
      [sc/icon-small ico/minus]]

     [:input {:type "time" :value @time :on-change #(reset! time (.. % -target -value))}]
     [button/reg-pill-icon
      {:on-click #(do
                    (reset! time (-> (t/>> (some-> @time t/time) (t/new-duration 1 :hours))
                                     (t/truncate :hours)))
                    (tap> [@time (some-> @time t/time)]))
       :disabled (not has-time)
       :style    {:min-width "3rem"
                  :border    "2px solid var(--text2)"}
       :class    [:inverse :left-square :narrow]}
      [sc/icon-small ico/plus]]]))

;; forms

(defn form-date-and-time [{:action/keys [action/next-step action/cancel]}]
  [:<>
   [bs/popup-frame
    [sc/co
     [sc/row-fields
      [:div]
      (next-step' next-step)]

     [sc/row-center
      [bs/timenav
       {:class [:w-full]}
       [:div.grid.place-content-center.gap-2.w-full
        {:style {:grid-template-columns "repeat(4,min-content)"
                 :grid-auto-rows        "auto"}}
        [sc/col {:class [:justify-self-start :self-end]
                 :style {:grid-column   "1/3"

                         :grid-row      "1/1"
                         :border-radius "var(--radius-0)"}}

         [:input {:type "date" :value "2022-05-23"}]
         [sc/field-label "Start"]]

        [:div.justify-self-center.self-center.w-full
         {:style {:grid-column "1/-1"
                  :grid-row    "2"}}
         [plus-minus-time start-time]]

        [:div.justify-self-center.self-center.w-full
         {:style {:grid-column "1/-1"
                  :grid-row    "3"}}
         [plus-minus-time end-time]]
        [sc/col {:class [:self-start :justify-self-end]
                 :style {:grid-column   "1/-1"
                         :grid-row      "4"
                         :border-radius "var(--radius-0)"}}

         [sc/field-label {:class [:text-right]} "Slutt"]
         [:input {:type  "date"
                  :value "2022-05-23"}]]
        [:div]]]]

     [sc/row-fields
      (cancel-booking' cancel)]]]])

(defn form-confirm [{:action/keys [prev-step complete action/cancel]}]
  [:<>
   [bs/popup-frame 
    [sc/co
     [sc/row-fields
      (previous-step prev-step)
      (confirm-booking' complete)]

     [sc/row-sc-g2 {:style {:align-items :start
                            :height      "100%"}}
      [sc/col
       {:class [:justify-center]
        :style {:height "100%"
                :width  "100%"}}
       [:div.w-full.h-full
        [sc/title1 "Bekreft booking"]
        [l/pre (count @selection)]]]]

     [sc/row-fields
      (cancel-booking' cancel)]]]])

;;

(defn- transition-map [p]
  {:class      [:absolute :top-0 :w-fullx :mx-auto :-mt-16  :-debug2]
   :enter      "ease-in-out duration-300 transform -mt-16"
   :enter-from (if p
                 "translate-x-full"
                 "-translate-x-full")
   :enter-to   "translate-x-0 -mt-16"
   :entered    "-mt-16  pointer-events-auto"
   :leave      "ease-in-out duration-300 -mt-16"
   :leave-from "translate-x-0 opacity-200"
   :leave-to   (if p
                 "-mt-16 translate-x-full opacity-0 pointer-events-none"
                 "-mt-16 -translate-x-full opacity-0 pointer-events-none")})

(defn always-panel []
  (let [next-step #(do
                     (reset! previous-step-value @step)
                     (swap! step (fn [e] (mod (inc e) 3))))
        prev-step #(do
                     (reset! previous-step-value @step)
                     (swap! step (fn [e] (mod (dec e) 3))))
        complete #(do
                    (reset! selector :sjøbasen)
                    (reset! step 0))
        cancel #(do (reset! selector :sjøbasen)
                    (reset! step 0))]
    [:div.pointer-events-none
     {:class [:sticky :top-24 :z-100 :space-y-8]}
     ;[l/pre @store]
     [sc/row-center {:class [:z-100]}
      [sc/col-space-8

       [widgets/pillbar {:on-click nil #_#(if (= :booking @selector)
                                            (reset! step 0))
                         :class    [:large]}
        selector
        [[:nøklevann "Nøklevann"]
         [:sjøbasen "Sjøbasen"]]]

       [sc/row-center
        (if (= :sjøbasen @selector)
          [button/pill-icon-caption
           {:style    {:box-shadow "var(--shadow-2)"}
            :class    [:large :cta :narrow]
            :on-click #(case @selector
                         :nøklevann (rf/dispatch [:lab/toggle-boatpanel nil])
                         :sjøbasen (reset! step 1))}
           ico/plus
           "Ny booking"]
          [button/pill-icon-caption
           {:style    {:box-shadow "var(--shadow-2)"}
            :class    [:large :cta :narrow]
            :on-click #(case @selector
                         :nøklevann (rf/dispatch [:lab/toggle-boatpanel nil])
                         :sjøbasen (reset! step 1))}
           ico/plus
           "Nytt utlån"])]]]

     [:div.relative.pointer-events-nonex
      {:class [:z-100 :w-full]}

      ;spacer
      [ui/transition
       {:show       (and                                    ;(= :sjøbasen @selector)
                      ;(= 2 @previous-step-value)
                      (= 1 @step))
        :class      [:w-full :h-64 :pointer-events-nonex]
        :enter      "ease-in-out duration-500 transform transition-height"
        :enter-from "h-0"
        :enter-to   "h-64"
        :entered    "h-64 pointer-events-nonex"
        :leave      "ease-in-out duration-500 transform transition-height"
        :leave-from "h-64"
        :leave-to   "h-0"}
       [:<> [:div.h-full.w-full]]]

      [ui/transition
       {:show   (and (= :sjøbasen @selector) (= 1 @step))
        :appear true}
       [ui/transition-child
        (transition-map (zero? @previous-step-value))
        [form-date-and-time
         {:action/next-step next-step
          :action/cancel    cancel}]]]

      [ui/transition
       {:show   (and (= :sjøbasen @selector) (= 2 @step))
        :appear true}
       [ui/transition-child
        (transition-map (zero? @previous-step-value))
        [form-confirm
         {:action/cancel    cancel
          :action/complete  complete
          :action/prev-step prev-step}]]]]]))

(defmulti render-list (fn [a r] a))

(defmethod render-list :default [_ r]
  [sc/surface-ab
   {:style {:width         "100%"
            :max-width     "768px"
            :margin-inline "auto"}}
   [sc/row-center
    [sc/text "Hjelp"]]])

(defmethod render-list :nøklevann [_ r]
  (let [keys (when-let [uid @(rf/subscribe [:lab/uid])]
               (map (comp name key) (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))]
    (let [starred-keys (when-let [uid @(rf/subscribe [:lab/uid])]
                         (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))

          data (sort-by (comp second first) <
                        (group-by (comp :kind val)
                                  (remove (comp nil? :boat-type val)
                                          (into {} (map (fn [[k v]] [k (assoc v :id (some-> k name))]))
                                                (filter (fn [[k {:keys [boat-type kind] :as v}]]
                                                          (and (= "0" (:location v))
                                                               (if (and @show-flagged (pos? (count keys)))
                                                                 (some #{(some-> boat-type name)} keys)
                                                                 (some? kind))))
                                                        @(rf/subscribe [:db/boat-db]))))))]
      [:div
       (into [:div]
             (for [[kind & r] data
                   :let [undefined? (empty? kind)]]
               [widgets/disclosure
                {:padded-heading true}
                (or kind "noname")
                [sc/title1
                 (or (schpaa.components.views/normalize-kind kind)
                     "Udefinert")]
                (for [{:keys [navn] :as z} r]
                  [:div.gap-1.w-full
                   {:style {:display               :grid
                            :grid-auto-rows        "auto"
                            :grid-template-columns (if undefined?
                                                     "1fr"
                                                     "repeat(auto-fill,minmax(16rem,1fr)")}}
                   (for [[[_boat-type _navn] r] (sort-by (comp last first) (group-by (comp (juxt :boat-type :navn) val) z))]
                     [:div.flex.flex-col.gap-2.w-full
                      {:style {:padding          "var(--size-2)"
                               :background-color "var(--floating)"
                               :border-radius    "var(--radius-0)"}}
                      (when-not undefined?
                        [widgets/stability-name-category (dissoc (-> r first val) :kind)])
                      ;todo toggle visibility via headerplugin
                      [:div.flex.flex-wrap.gap-1
                       (for [v (sort-by :number (map val r))
                             :let [{:keys [number work-log slot _navn _description]} v
                                   work-log (count (remove (fn [[k v]] (or (:complete v)
                                                                           (:deleted v))) work-log))]]
                         [widgets/badge
                          {:class    [:small]
                           :on-click #(booking.modals.boatinfo/open-modal-boatinfo {:data v})}
                          (when (pos? work-log)
                            work-log)
                          (subs (str number) 0 3)
                          slot])]])])]))])))

(defn make-series-of-abutting-elements [ra rb]
  (iterate (fn [[a' _]]
             (let [a (+ a' (ra))
                   b (+ a (rb))]
               [a b])) nil))

(defn svg-time-graph [idx ok! data start end]
  (let [settings (r/atom {:rent/graph-view-mode 0})
        now (t/date-time)]
    [sc/col
     ;[l/pre (map #(mapv (comp str t/time) %) data)]
     [timegraph-multi
      idx
      {:total-hours   (* 4 96)                              ; 48 hours
       :settings      settings
       :now           now
       :ok            ok!
       :session-start start
       :session-end   end}
      data]]))

(o/defstyled boat-group-block :div
  [:& :flex :flex-col :gap-2 :w-full
   {:padding          "var(--size-2)"
    :background-color "var(--floating)"
    :border-radius    "var(--radius-0)"}])

(defn- item [idx v]
  (let [{:keys [id number slot work-log _navn _description]} v
        selected? (some? (some #{id} (:selection @store)))
        work-log-count (count (remove (fn [[_k v]] (or (:complete v) (:deleted v))) work-log))
        has-work-log? (pos? work-log-count)
        inhibit false ;nil ;(rand-nth [true false])
        data nil #_(->> (make-series-of-abutting-elements #(-> 10) #(-> 10))
                        ;#(rand-int 96) #(+ 20 (rand-int 12)))
                        (drop 1)
                        (take 4)
                        ;(mapv digits->time)  !!!
                        (into []))
        starts (some-> @start-time t/time t/hour)
        ends (some-> @end-time t/time t/hour)]
    [sc/col
     ;[l/pre starts ends]
     [sc/row-sc-g2 {:style {:align-items "start"}}
      [widgets/badge
       {:class    [:small]
        :on-click #(booking.modals.boatinfo/open-modal-boatinfo {:data v})}
       (when has-work-log?
         work-log-count)
       (subs (str number) 0 3)
       slot]
      (when-not has-work-log?
        [svg-time-graph idx inhibit data
         (* 8 (or starts 12))
         (* 8 (or ends 18))])
      ;intent Device to accept/select boat for booking
      (when (pos? @step)
        (when-not has-work-log?
          [:div.grid.place-content-center.shrink-0
           (if inhibit
             (button/reg-pill-icon
               {:on-click #(toggle id)
                :class    [:round :large]
                :style    {:border-color "var(--red-6)"
                           :color        "var(--red-6)"}}
               [:div.w-8.h-8.grid.place-content-center
                (schpaa.icon/adapt :circle-filled)])
             (button/reg-pill-icon
               {:on-click #(toggle id)
                :class    [:frame :round]
                :style    {:border-color (if selected? "var(--green-6)" "var(--gray-6)")
                           :background-color (when selected? "var(--green-6)")
                           :color        (if selected? "var(--green-0)" "var(--gray-6)")}}
               (when selected? ico/check)))]))]]))

(defn- list-of-boats [{:keys [slot number] :as r}]
  (into [:div.flex.flex-col.gap-1]
        (for [[idx v] (map-indexed vector (sort-by (juxt :slot :number) (map val r)))]
          (item idx v))))

(defmethod render-list :sjøbasen [_ r]
  (let [starred-keys (when-let [uid @(rf/subscribe [:lab/uid])]
                       (map (comp name key) (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))
        has-stars? (when starred-keys
                     (pos? (count starred-keys)))
        data (some->> @(rf/subscribe [:db/boat-db])
                      (filter (comp #(= % "1") :location val))
                      (filter (fn [[_k {:keys [boat-type kind] :as v}]]
                                (if (and @show-flagged has-stars?)
                                  (some #{(some-> boat-type name)} starred-keys)
                                  ; new entries doesn't have ~kind~, so exclude them
                                  (some? kind))))
                      (into {} (map (fn [[k v]] [k (assoc v :id (some-> k name))])))
                      (remove (comp nil? :boat-type val))
                      (group-by (comp :kind val))
                      (sort-by (comp second first) <))]
    (into [:div.space-y-2]
          (for [[kind & r] data]
            [widgets/disclosure
             {:padded-heading true}
             (or kind "noname")
             [sc/title1 (or (schpaa.components.views/normalize-kind kind) "Udefinert")]

             (into [:<>]
                   (for [z r]
                     [:div.gap-1.w-full
                      {:style {:display               :grid
                               :grid-auto-rows        "auto"
                               :grid-template-columns "repeat(auto-fill,minmax(16rem,1fr)"}}
                      (into [:<>]
                            (for [[[_boat-type _navn] r]
                                  (sort-by (comp last first) <
                                           (group-by (comp (juxt :boat-type :navn) val) z))]
                              [boat-group-block
                               [sc/row-sc-g2
                                {:style {:height "auto"}}
                                [widgets/stability-name-category-front-flag
                                 (dissoc (-> r first val) :kind)]
                                (clear-selection r)
                                (select-all-in-group r)]
                               (list-of-boats r)]))]))]))))

(defmethod render-list :b [_ r]
  (let [data (sort-by (comp (juxt first last) #(str/split % #" ") first) <
                      (group-by (comp first #(str/split % #" ") :slot val)
                                (into {}
                                      (map (fn [[k v]] [k v])
                                           (remove (comp nil? :boat-type val)
                                                   (filter (fn [[k v]] (not= "1" (:location v)))
                                                           @(rf/subscribe [:db/boat-db])))))))]
    [:<>
     (for [[[a] b] data]
       [sc/co
        [sc/row {:style {:min-height  "var(--size-10)"
                         :align-items :end}}
         [sc/title1 (if a (str
                            (str "Stativ " a)
                            (case (str a)
                              "1" ", nederst"
                              "4" ", øverst"
                              "")) "Ukjent")]]
        [:div
         {:style {:display               :grid
                  :grid-gap              "var(--size-1)"
                  :grid-template-columns "repeat(auto-fill,minmax(18rem,1fr)"}}
         (for [[k {:keys [navn number slot description kind] :as v}] (sort-by (comp :slot val) b)]
           [sc/row-sc-g2 {:style {:padding          "var(--size-1)"
                                  :background-color "var(--floating)"}}
            [widgets/badge {:class    [:small]
                            :on-click #(booking.modals.boatinfo/open-modal-boatinfo {:data v})}
             number
             slot]

            [widgets/stability-name-category v]])]])]))

(defmethod render-list :c [_ r]
  (let [starred-keys (when-let [uid @(rf/subscribe [:lab/uid])]
                       (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))
        data (sort-by (comp :navn val) <
                      (into {} (map (fn [[k v]] [k (assoc v :id (some-> k name))])
                                    (filter (fn [[k {:keys [kind]}]]
                                              (if @show-flagged
                                                (some #{k} starred-keys)
                                                (some? kind)))
                                            @(rf/subscribe [:db/boat-type])))))]
    [:div.grid.gap-1 {:style {:grid-template-columns "repeat(auto-fill,minmax(17rem,1fr))"}}
     (for [[k v] data]
       [sc/co
        {:style {:padding          "var(--size-1)"
                 :background-color "var(--floating)"
                 :border-radius    "var(--radius-0)"}}
        [widgets/stability-name-category' {:reversed? true
                                           :url?      true
                                           :k         k} v]
        [widgets/dimensions-and-material v]
        [sc/text1 (:description v)]
        #_[l/pre k]])]))

(defn render [r]
  (render-list @selector r))

(comment
  (do
    (let [keys (when-let [uid @(rf/subscribe [:lab/uid])]
                 (map key (filter (comp val) @(db/on-value-reaction {:path ["users" uid "starred"]}))))])))

(rf/reg-fx :lab/open-new-blog-entry-dialog (fn [_] (schpaa.style.dialog/open-dialog-addpost)))

(rf/reg-event-fx :lab/just-create-new-blog-entry
                 (fn [_ _]
                   (tap> :lab/new-blog-entry)
                   {:fx [[:lab/open-new-blog-entry-dialog nil]]}))

(rf/reg-event-fx :lab/new-blog-entry
                 (fn [_ _]
                   (tap> :lab/new-blog-entry)
                   {:fx [[:dispatch [:app/navigate-to [:r.fileman-temporary]]]
                         [:lab/open-new-blog-entry-dialog nil]]}))

(rf/reg-sub :lab/has-chrome
            (fn [db]
              (get-in db [:settings :state :lab/toggle-chrome] true)))

(rf/reg-event-db :lab/toggle-chrome
                 (fn [db _]
                   (update-in db [:settings :state :lab/toggle-chrome] (fnil not false))))

;region access

; status -> anonymous, registered, waitinglist, member
; access -> admin, booking, nøkkelvakt

;at-least-registered
;(some #{(:role @user-state)} [:member :waitinglist :registered])

;:app/master-state-emulation

(rf/reg-sub :lab/master-state-emulation :-> :lab/master-state-emulation)
(rf/reg-event-db :lab/master-state-emulation (fn [db _]
                                               (update db :lab/master-state-emulation (fnil not false))))

(rf/reg-sub :lab/at-least-registered
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid] :as sim}] _]
              (if master-switch
                (and uid (some #{status} [:registered :waitinglist :member]))
                (some? (:uid ua)))))

(rf/reg-sub :lab/username-or-fakename
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid] :as sim}] _]
              (if master-switch
                uid
                (:email ua))))


(rf/reg-event-db :lab/set-sim-type (fn [db [_ arg]]
                                     (tap> {:lab/set-sim-type arg})
                                     (assoc-in db [:lab/sim :status] arg)))

(rf/reg-event-db :lab/set-sim (fn [db [_ arg opt]]
                                (cond
                                  (= :uid arg)
                                  (assoc-in db [:lab/sim :uid] (if (seq opt) opt nil))

                                  (= :booking arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:booking})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:booking}))

                                  (= :admin arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:admin})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:admin}))

                                  (= :nøkkelvakt arg)
                                  (if opt
                                    (update-in db [:lab/sim :access] (fnil set/union #{}) #{:nøkkelvakt})
                                    (update-in db [:lab/sim :access] (fnil set/difference #{}) #{:nøkkelvakt}))
                                  :else
                                  db)))

(rf/reg-sub :lab/sim? :-> #(get % :lab/sim))

;deprecated
(rf/reg-sub :lab/user-state
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[ua sim] _]
              (if-let [s sim]
                (if (some #{(:status s)} [:member]) s s)
                ua)))

(rf/reg-sub :lab/uid
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [uid] :as sim}] _]
              (if master-switch
                uid
                (:uid ua))))

;todo: Assume we are always simulating

(rf/reg-sub :lab/status-is-none
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[_ {:keys [status access] :as sim}] _]))

;todo
(rf/reg-sub :lab/member
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[_ {:keys [status] :as sim}] _]
              (when sim (= :member status))))

(rf/reg-sub :lab/admin-access
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch {:keys [uid] :as ua} {:keys [status access] :as m}] _]
              (if master-switch
                (some? (some #{:admin} (or access [])))
                (let [u @(db/on-value-reaction {:path ["users" uid]})]
                  (if-let [x (booking.access/build-access-tuple u)]
                    (let [[s a] x]
                      (and (= s :member)
                           (= :admin (some #{:admin} (or a [])))))
                    false)))))

(rf/reg-sub :lab/booking
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch {:keys [uid] :as ua} {:keys [status access] :as m}] _]
              (if master-switch
                (and (= status :member)
                     (some? (some #{:booking} (or access []))))
                (let [u @(db/on-value-reaction {:path ["users" uid]})]
                  (if-let [x (booking.access/build-access-tuple u)]
                    (let [[s a] x]
                      (and (= s :member)
                           (= :booking (some #{:booking} (or a [])))))
                    false)))))

(rf/reg-sub :user-data
            (fn [[_ uid]]
              (when-let [id @(db.auth/user-info)]
                (db/on-value-reaction {:path ["users" id]})))
            (fn [u _]
              u))

(rf/reg-sub :lab/nokkelvakt
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access] :as m}] _]
              (if master-switch
                (and (= status :member)
                     (some? (some #{:nøkkelvakt} (or access []))))
                (let [uid (:uid ua)
                      u @(db/on-value-reaction {:path ["users" uid]})]
                  (tap> {:user u})
                  (if-let [[s a _ :as x] (booking.access/build-access-tuple u)]
                    (and (= s :member)
                         (= :nøkkelvakt (some #{:nøkkelvakt} (or a []))))
                    false)))))

(rf/reg-sub :lab/all-access-tokens
            :<- [:lab/master-state-emulation]
            :<- [::db/user-auth]
            :<- [:lab/sim?]
            (fn [[master-switch ua {:keys [status access uid]}] _]
              (if master-switch
                [status access uid]
                (let [u @(db/on-value-reaction {:path ["users" (:uid ua)]})]
                  (booking.access/build-access-tuple u)))))

;endregion

(comment
  (let [status #{:bookings}]
    (some status [:booking])))

(rf/reg-sub :lab/toggle-userstate-panel :-> :lab/toggle-userstate-panel)

(rf/reg-event-db :lab/toggle-userstate-panel (fn [db _] (update db :lab/toggle-userstate-panel (fnil not false))))

(rf/reg-fx :lab/showuserinfo-fx (fn [data]
                                  (schpaa.style.dialog/open-user-info-dialog data)))

(rf/reg-event-fx :lab/show-userinfo (fn [_ [_ uid]] {:fx [[:lab/showuserinfo-fx uid]]}))

(rf/reg-event-fx :lab/qr-code-for-current-page
                 (fn [_ _]
                   (when-let [link @(rf/subscribe [:kee-frame/route])]
                     (booking.qrcode/show link))))

;region temporary, perhaps for later

(rf/reg-event-db :lab/show-popover (fn [db]
                                     (tap> (:lab/show-popover db))
                                     (update db :lab/show-popover (fnil not false))))
(rf/reg-sub :lab/show-popover (fn [db] (get db :lab/show-popover false)))

;endregion

(comment
  (do
    (take 4 (make-series-of-abutting-elements (rand-int 10) (rand-int 10)))))

(comment
  (do
    (digits->time 20 0)))

