(ns booking.common-widgets
  (:require [schpaa.style.ornament :as sc]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.routes]))

(defn vertical-button [{:keys [right-side
                               centered? tall-height special icon icon-fn class style on-click page-name badge disabled]
                        :or   {style {}}}]
  (let [
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [:div
     {:style (if centered?                                  ;'vertically
               {:pointer-events :none
                :inset          0
                :display        :grid
                :place-content  :center
                :position       :absolute}
               {
                :display         :flex
                :align-items     :start
                :justify-content :center
                :height          (if tall-height "var(--size-10)" "var(--size-9)")})}
     [:div.w-full.h-full.flex.flex-col.items-center.justify-around.relative
      {:style    {:pointer-events :auto

                  :height         "var(--size-9)"}
       :on-click #(on-click current-page)}

      (when badge
        (when-some [b (badge)]
          (if right-side
            [sc/top-right-badge b]
            [sc/top-left-badge b])))

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
    [sc/text1 navn]
    [sc/small2 (schpaa.components.views/normalize-kind kind)]]])

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

(defn trashcan [on-click {:keys [deleted id] :as v}]
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
