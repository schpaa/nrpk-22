(ns booking.common-widgets
  (:require [schpaa.style.ornament :as sc]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.routes]
            [schpaa.debug :as l]
            [headlessui-reagent.core :as ui]))

(defn horizontal-button [{:keys [right-side
                                 centered? tall-height special icon icon-fn class style on-click page-name badge disabled]
                          :or   {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [:div.w-16
     {:style (if centered?                                  ;'vertically
               {:pointer-events :none
                :inset          0
                :display        :grid
                :place-content  :center
                :position       :absolute}
               {:display         :flex
                :align-items     :center
                :justify-content :center
                :height          (if tall-height "var(--size-10)" "var(--size-9)")})}
     [:div.w-full.h-full.flex.flex-col.items-center.justify-around.relative
      {:style    {:pointer-events :auto

                  :height         "var(--size-9)"}
       :on-click #(on-click current-page)}

      (when badge
        (let [{value :value attr :attr} (badge)]
          (if right-side
            [sc/top-right-badge attr value]
            [sc/top-left-badge attr value])))

      [sc/toolbar-button
       {:disabled  disabled
        :tab-index (when active? "-1")
        :style     style
        :class     [(if right-side :right-side :left-side)
                    (if active? (or (when class (class current-page)) :selected))
                    (if special :special)]}
       [sc/icon-large
        (if icon-fn
          (icon-fn current-page)
          icon)]]]]))

(defn vertical-button [{:keys [right-side caption
                               centered? tall-height special icon icon-fn class style on-click page-name badge disabled]
                        :or   {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [sc/toolbar-button-with-caption
     {:style    {:justify-content :space-between
                 :display         :flex
                 :align-items     :center}
      :class    [:gap-0 :w-full]
      :on-click #(on-click current-page)}

     (when right-side
       (if caption
         [sc/text2 {:style {:text-align :right
                            :flex-grow  1
                            :color      "unset"
                            :flex       "1 0 1"}} caption]
         [:div]))

     [:div.w-16
      {:style {:display         :flex
               :align-items     :center
               :justify-content :center
               :flex-shrink     0
               ;:flex "0 2 52px"
               :height          (if tall-height "var(--size-10)" "var(--size-9)")}}
      [:div.w-full.h-full.flex.flex-col.items-center.justify-around.relative
       {:style {:pointer-events :auto

                :height         "var(--size-9)"}}


       (when badge
         (let [{value :value attr :attr} (badge)]
           (if right-side
             [sc/top-right-badge attr value]
             [sc/top-left-badge attr value])))

       [sc/toolbar-button
        {:disabled  disabled
         :tab-index (when active? "-1")
         :style     style
         :class     [(if right-side :right-side :left-side)
                     (if active? (or (when class (class current-page)) :selected))
                     (if special :special)]}
        [sc/icon-large
         (if icon-fn
           (icon-fn current-page)
           icon)]]]]
     (when-not right-side
       (when caption [sc/text2 {:style {:color      "unset"
                                        :flex-grow  1
                                        :text-align :left
                                        :flex       "1 0 1"}} caption]))]))

(defn stability-expert [{:keys [stability expert]}]
  [:div.w-8.flex.justify-center.items-center
   [:svg.w-4.inline-block {:viewBox "-2 -2 5 5"}
    [:circle {:cx 0 :cy 0 :r 2 :fill (get {0 :green 1 :orange 2 :pink 3 :black} (js/parseInt stability) :white)}]
    [:circle {:cx 0 :cy 0 :r 1 :fill (if expert :red :transparent)}]]])

(defn stability-name-category [{:keys [boat-type star-count location slot material
                                       stability expert number navn kind description
                                       last-update weight length width aquired-year aquired-price] :as m}]

  [:<>
   [stability-expert m]
   [sc/col
    [sc/small0 {:style {:font-size "0.85em"}} navn]
    [sc/text1 {:style {:font-size "1.2em"}} (schpaa.components.views/normalize-kind kind)]]])

(defn favourites-star [{:keys [ex-data bt-data on-star-click]}
                       {:keys [boat-type] :as m}]
  [:<>
   (when (pos? @bt-data)
     [sc/text1 @bt-data])
   [sc/icon-large
    {:on-click #(on-star-click boat-type (not @ex-data))
     :style    {:color (if @ex-data "var(--yellow-6)" "var(--text2)")}}
    (if @ex-data ico/stjerne ico/ikkeStjerne)]])

;region

(defn trashcan [on-click {:keys [deleted id]}]
  [(if deleted scb/round-undo-listitem scb/round-danger-listitem)
   {:on-click #(on-click id)}
   [sc/icon-small
    (if deleted
      (icon/adapt :rotate-left)
      ico/trash)]])

(defn edit [attr on-click {:keys [deleted] :as m}]
  (if deleted
    [:div.w-8]
    [scb/round-normal-listitem
     (conj
       attr
       {:on-click #(on-click m)})
     [sc/icon-small
      {:style {:color "var(--text1)"}}
      ico/pencil]]))

(defn lookup-page-ref-from-name [link]
  {:pre [(keyword? link)]}
  {:link link
   :text (or (some->> link
                      (reitit/match-by-name (reitit/router booking.routes/routes))
                      :data
                      :header)
             "wtf?")})

(defn user-link [uid]
  [sc/link
   {:href (kee-frame.core/path-for [:r.dine-vakter {:id uid}])}
   (or (user.database/lookup-username uid) uid)])

(defn auto-link
  ([link]
   (if (vector? link)
     ;(let [{:keys [link text]} (lookup-page-ref-from-name (first link))])
     [sc/link {:href (kee-frame.core/path-for link)} (second link)]
     (let [{:keys [link text]} (lookup-page-ref-from-name link)]
       [sc/link {:href (kee-frame.core/path-for [link])} text])))
  ([link resolver]
   (if (vector? link)
     ;(let [{:keys [link text]} (lookup-page-ref-from-name (first link))])
     [sc/link
      {:href (kee-frame.core/path-for link)}
      ((fn [{:keys [id]}]
         (:caption (first (filter (fn [m]
                                    (= id (:id m)))
                                  resolver))
           {:caption (str id)})) (second link))]
     (let [{:keys [link text]} (lookup-page-ref-from-name link)]
       [sc/link {:href (kee-frame.core/path-for [link])} text]))))

(defn material->text [material]
  (case (str material)
    "0" "Plast"
    "1" "Glassfiber"
    "2" "Polyetylen"
    "3" "Kevlar/Epoxy"
    "" ""
    (str "Annet:" material)))

(defn dimensions-and-material [{:keys [width length weight material]}]
  [sc/text2 {:style {:font-size "unset"}}
   [:span (interpose [:span ", "]
                     (remove #(or (empty? %) (nil? %))
                             [weight
                              (material->text material)
                              (and width (str width " bred"))
                              (and length (str length " lang"))]))]])


(comment
  (dimensions-and-material {:width 1 :length 2 :weight "" :material 2}))

(defn no-access-view [r]
  (let [required-access (-> r :data :access)]
    ;fix:
    [:div                                                   ;.max-w-lg.mx-4
     {:style {:min-height     "calc(100vh - 4rem)"
              :padding-inline "var(--size-4)"
              :padding-top    "var(--size-10)"
              :margin-inline  "auto"}}
     [:div.absolute.inset-0.pointer-events-none
      {:style {:opacity          0.3
               :background-color "var(--brand1)"}}]
     [sc/col-space-8
      [sc/hero {:style {:white-space :nowrap
                        :text-align  :center
                        :color       "var(--text2)"}} "Ingen tilgang"]
      [sc/row-center [sc/icon {:style {:text-align :center
                                       :color      "var(--text2)"}} ico/stengt]]
      ;[sc/small1 {:style {:white-space :nowrap}} "Du har --> " (str @(rf/subscribe [:lab/all-access-tokens]))]
      ;[sc/small1 {:style {:white-space :nowrap}} "Du trenger --> " (str required-access)]
      #_(if goog.DEBUG [l/pre
                        (matches-access r @(rf/subscribe [:lab/all-access-tokens]))
                        (-> r :data :access)
                        @(rf/subscribe [:lab/all-access-tokens])])

      [sc/text2 "For å se dette må du"]
      [sc/title1x
       (cond
         (some #{:registered} (first required-access)) "Være innlogget og ha registrert deg med grunn\u00adleggende infor\u00ADmasjon om deg selv."
         (some #{:waitinglist} (first required-access)) "Være påmeldt inn\u00ADmeldingskurs."
         :else #_(some #{:member} (first required-access)) "Være medlem i NRPK.")]
      [sc/title1x
       (cond
         (some #{:nøkkelvakt} (last required-access)) "Være godkjent nøkkelvakt (ifølge aktiv konto)."
         (some #{:admin} (last required-access)) "Være administrator.")]

      [sc/row-sc-g4-w
       [sc/text2 "Gå til"]
       [auto-link :r.forsiden]
       [auto-link :r.oversikt]]]]))

(defn disclosure
  ([tag question answer]
   (disclosure {:style {:padding-block "var(--size-2)"
                        :margin-left   "var(--size-5)"}} tag question answer "empty-message"))
  ([tag question answer empty-message]
   (disclosure {:style {:padding-block "var(--size-2)"
                        :margin-left   "var(--size-5)"}} tag question answer empty-message))
  ([attr tag question answer empty-message]
   (let [open @(schpaa.state/listen tag)]
     [ui/disclosure {:as       :div
                     :on-click #(schpaa.state/toggle tag)}
      (fn [{:keys []}]
        (let []
          [:<>
           [ui/disclosure-button
            {:class "flex justify-start items-center gap-2 w-full focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"}
            (sc/icon-tiny {:style {:color "var(--brand1)"}
                           :class (concat [:duration-100 :w-5 :h-5]
                                          (when open
                                            [:transform :rotate-90]))}
                          ico/showdetails)
            [:span [sc/fp-headersmaller {:class [:pointer-events-none]
                                         :style {:color (if open "var(--text2)" "var(--text0)")}} question]]]
           [ui/disclosure-panel
            (merge {:static true} (dissoc attr :links))
            (if (:links attr)
              (if open [:div answer empty-message] empty-message)
              (if open [sc/text (or answer empty-message)]))]]))])))