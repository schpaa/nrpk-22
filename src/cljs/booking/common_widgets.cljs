(ns booking.common-widgets
  (:require [schpaa.style.ornament :as sc]
            [reagent.core :as r]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.routes]
            [schpaa.debug :as l]
            [headlessui-reagent.core :as ui]

            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [db.core :as db]
            [clojure.string :as str]))

(defn horizontal-button [{:keys [right-side
                                 tall-height
                                 special
                                 icon-fn
                                 class
                                 style
                                 on-click
                                 page-name
                                 badge
                                 disabled]
                          :or   {style {}}
                          :as   m}]
  (let [{:keys [icon-disabled icon]} m
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        ic [sc/icon-huge
            (if icon-fn
              (icon-fn current-page)
              (if disabled icon-disabled icon))]
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [sc/col
     {:on-click #(on-click current-page)
      :style    {:outline         "1px solid yellow"
                 :margin-bottom   "1rem"
                 :width           "100%"
                 ;:heigth          "100%"
                 :display         :flex
                 :align-items     :center
                 :justify-content :space-between}}

     [:div.flex.items-center.justify-around.relative
      {:style {;:width          "2rem"
               ;:height         "2rem"
               :aspect-ratio   "1/1"
               :pointer-events :auto}}
      (when badge
        (let [{value :value attr :attr} (badge)]
          (if right-side
            [sc/top-right-badge attr value]
            [sc/top-left-badge attr value])))

      [sc/toolbar-button
       {:disabled  disabled
        :tab-index (when active? "-1")
        :class     [
                    ;:self-center
                    ;:w-full
                    ;:-debug
                    (if active? (or (when class (class current-page)) :selected))
                    (if special :special)]}
       ic]]

     [sc/small "this is"]]))

(defn vertical-button [{:keys [right-side caption
                               opposite-on-click
                               with-caption?
                               opposite-icon-fn
                               tall-height special
                               icon icon-fn class style on-click
                               page-name badge disabled]
                        :or   {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [sc/toolbar-button-with-caption
     {:style    {:justify-content :space-between
                 :align-items     :center}
      :class    [:gap-2 :w-full :flex :flex-row-reverse :justify-center]
      :on-click #(on-click current-page)}

     (when right-side
       (when caption
         [sc/text2 {:style {:width       "auto"
                            :white-space :normal
                            :color       "unset"
                            :flex-grow   1
                            :flex        "1 1 auto"}} caption]))

     (when right-side
       (when with-caption?
         (when opposite-icon-fn
           [:div.w-16.h-20.flex.justify-center.items-center
            {:on-click opposite-on-click}
            [sc/icon (opposite-icon-fn)]])))

     [:div.w-16
      {:style {:display         :flex
               :align-items     :center
               :justify-content :center
               :flex-shrink     0
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
         ;:style     {:aspect-ratio "1/1"}
         :class     [(if right-side :right-side :left-side)
                     (if active? (or (when class (class current-page)) :selected))
                     (if special :special)]}
        [:div.shrink-0 #_{:style {:border "1px solid blue"}}
         [sc/icon-large
          (if icon-fn
            (icon-fn current-page)
            icon)]]]]]

     (when-not right-side
       (when with-caption?
         (when opposite-icon-fn
           [:div.w-16.h-20.flex.justify-center.items-center
            {:on-click opposite-on-click}
            [sc/icon (opposite-icon-fn)]])))

     (when-not right-side
       (when caption
         [sc/text2 {:style {:text-align :right
                            :flex-grow  1
                            :color      "unset"
                            :flex       "1 0 1"}} caption]))]))

(defn stability-expert [{:keys [stability expert]}]
  [:div.px-px.flex.justify-center.items-center
   [:svg.w-4.inline-block {:viewBox "-2 -2 4 4"}
    [:circle {:cx 0 :cy 0 :r 2 :fill (get {0 "var(--brand1)"
                                           1 "var(--blue-7)"
                                           2 "var(--orange-6)"
                                           3 "var(--red-7)"} (js/parseInt stability) :white)}]
    [:circle {:cx 0 :cy 0 :r 1 :fill (if expert :red :transparent)}]]])

(defn stability-name-category [{:keys
                                [boat-type star-count location slot material
                                 stability expert number navn kind description
                                 last-update weight length width aquired-year aquired-price] :as m}]

  [sc/row-sc-g4

   [sc/col {:class [:truncate :space-y-1]}
    [sc/row-sba
     (when (or stability expert) [stability-expert m])
     [sc/ptext {:style {:overflow      :hidden
                        :text-overflow :ellipsis
                        :white-space   :nowrap
                        :font-size     "small"}} navn]]

    [sc/ptitle1 (schpaa.components.views/normalize-kind kind)]]])

(defn favourites-star [{:keys [ex-data bt-data on-star-click]}
                       {:keys [boat-type] :as m}]
  [sc/row {:style {:column-gap  "var(--size-2)"
                   :align-items :center}}
   (when (pos? @bt-data)
     [sc/text1 @bt-data])
   [sc/icon-large
    {:on-click #(on-star-click boat-type (not @ex-data))
     :style    {:color (if @ex-data "var(--yellow-6)" "rgba(0,0,0,0.5)")}}
    (if @ex-data ico/stjerne ico/stjerne)]])

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
                      :header
                      second)
             "wtf?")})

(defn user-link [uid]
  [sc/link
   {:href (kee-frame.core/path-for [:r.dine-vakter {:id uid}])}
   (user.database/lookup-alias uid)])

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
  ;todo start a timeout on mount and show no access after 2 seconds
  (let [show-error (r/atom false)]
    (r/create-class
      {:display-name        "no-access-view"
       :component-did-mount (fn [_]
                              (js/setTimeout #(reset! show-error true) 2000))
       :reagent-render      (fn [r]
                              (if @show-error
                                (let [required-access (-> r :data :access)]
                                  [sc/container
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
                                    [sc/text2 "For å se dette må du"]
                                    [sc/title1x
                                     (cond
                                       (some #{:registered} (first required-access)) "Være innlogget og ha registrert deg med grunn\u00adleggende infor\u00ADmasjon om deg selv."
                                       (some #{:waitinglist} (first required-access)) "Være påmeldt inn\u00ADmeldingskurs."
                                       :else "Være medlem i NRPK.")]
                                    [sc/title1x
                                     (cond
                                       (some #{:nøkkelvakt} (last required-access)) "Være godkjent nøkkelvakt (ifølge aktiv konto)."
                                       (some #{:admin} (last required-access)) "Være administrator.")]

                                    ;fix: routes and links
                                    [sc/row-sc-g4-w
                                     [sc/text2 "Gå til"]
                                     [auto-link :r.forsiden]
                                     [auto-link :r.oversikt]]]])
                                [:div.mt-24
                                 [:div
                                  {:style {
                                           ;:width          "100%"
                                           ;:ouline         "1px solid red"
                                           :min-height     "calc(100vh -  4rem)"
                                           ;:padding-inline "var(--size-4)"
                                           ;:padding-top    "var(--size-10)"
                                           :xmargin-inline "auto"}}
                                  [:div.flex.justify-center.items-center.h-full.w-full
                                   [sc/title (icon/spinning :spinner)]]]]))})))

(defn disclosure
  ([m]
   (apply disclosure m))
  ([attr tag question answer]
   (disclosure attr tag question answer nil))
  ([attr tag question answer empty-message]
   (let [open @(schpaa.state/listen tag)
         attr (conj {:style {:padding-block "var(--size-2)"}} attr)]
     [ui/disclosure {:as    :div
                     :style {:cursor :default}}
      (fn [{:keys []}]
        [sc/col
         [ui/disclosure-button
          {:on-click #(schpaa.state/toggle tag)
           :style    {:cursor :default}
           :class    "flex justify-start items-center gap-2 w-full focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"}

          [sc/row-sc-g2
           {:style    {:cursor :pointer}
            :on-click #(schpaa.state/toggle tag)}
           (sc/icon {:style {:color "var(--brand2)"}
                     :class (concat [:duration-100 :w-12 :h-12]
                                    (when open
                                      [:transform :rotate-90]))}
                    ico/showdetails)
           [:span [sc/fp-headersmaller {:class [:text-left
                                                :pointer-events-none (when (:large attr) :large)]
                                        :style {:color (if open "var(--text0)" "var(--text2)")}}
                   question]]]]
         [ui/disclosure-panel
          (merge-with into
                      {:static true}
                      (dissoc attr :links)
                      {:style (if open {:margin-block "var(--size-2)"})})

          (if (:links attr)
            (if open [sc/col-space-4 answer empty-message] empty-message)
            (if open
              (if answer
                [sc/text2 answer]
                [sc/text2 empty-message])
              (when-not answer [sc/text2 empty-message])))]])])))

(defn send-msg [uid-reciever]
  (rf/dispatch [:app/open-send-message uid-reciever]))

(defn message-pill [uid-reciever]
  (schpaa.style.hoc.buttons/pill
    {:class    [:message :round]
     :on-click #(send-msg uid-reciever)} (sc/icon ico/melding)))

(defn personal [{:keys [telefon epost uid] :as user} & [extra]]
  #_(r/with-let [triggered (r/atom false)])
  (if user
    [sc/surface-a {;:on-click #(swap! triggered not)
                   :style {:padding "var(--size-2)"}}
     [sc/row-sc-g2-w
      {:style {:gap            "var(--size-2)"
               :color          "var(--text1)"
               :padding-inline "var(--size-2)"
               :padding-block  "var(--size-2)"}}
      [sc/row-sc-g4-w
       (message-pill uid)
       [sc/link {:href (str "tel:" telefon)} telefon]
       [:span "/"]
       [sc/link {:href (str "sms:" telefon)} "SMS"]
       [:span "/"]
       (when (seq epost)
         [sc/link {:href (str "mailto:" epost)} epost])]]]
    (let [username-or-fakename (rf/subscribe [:lab/username-or-fakename])
          epost @username-or-fakename]
      [sc/surface-a
       [sc/row-sc-g2-w
        [sc/text1 "Logget inn som " [sc/link {:href (str "mailto:" epost)} epost]]]])))

(defn dialog-template
  ([header content footer]
   [dialog-template header content footer nil])
  ([header content footer buttons]
   [sc/dropdown-dialog' {:style {:padding-bottom "1rem"}}
    [sc/col-space-4
     [sc/col-space-4
      [sc/row {:class []
               :style {:display     :flex
                       :align-items :center
                       :height      "3rem"}}
       [sc/title {:class [:bold]} header]]
      content]
     footer
     (when buttons
       buttons)]]))

(defn pre [& p]
  (let [item (fn [e] [:div.bg-black.text-white.h-full.w-auto e])]
    [:div.space-y-1
     [l/pre p]
     (into [:div.flex.justify-end.h-12.items-center.gap-1]
           (mapv item ["a1" "b2" "b3"]))]))

(defn location [l]
  (if (= "0" (str l)) "Nøklevann" "Sjøbasen"))

(defn badge [attr n]
  [sc/badge-2 attr n])

(defn pillbar
  ([c vs]
   (pillbar {} c vs))
  ([_attr c vs]
   (let [f (fn [[k v]]
             [schpaa.style.hoc.toggles/pillbar
              {:on-click #(reset! c k)
               :class    [:narrow :outline2 :normals (if (= k @c) :inverse)]} v])]
     [sc/row (into [:<>] (map f vs))])))

(defn open-slideout [dialog-def]
  (rf/dispatch [:modal.slideout/show {:content-fn dialog-def}]))

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])
        user-uid (rf/subscribe [:lab/uid])
        ipad? (= @user-uid @(db/on-value-reaction {:path ["system" "active"]}))]
    [:div.p-2.noprint {:style {:background-color "var(--toolbar-)"}}
     [:div.z-1.rounded-top
      {:style {:border-radius "var(--radius-1)"
               ;:border-top-right-radius "var(--radius-2)"
               :background    "var(--gray-9)"}}
      [:div.mx-auto.max-w-xl.pt-8.pb-16
       [:div.mx-4
        [sc/col-space-4
         [sc/col-space-1
          [sc/title {:style {:color "var(--gray-4)"}} "Postadresse"]
          [sc/col-space-1
           {:style {:user-select :contain
                    :color       "var(--gray-5)"}}
           [sc/text1-cl "Nøklevann ro- og padleklubb"]
           [sc/text1-cl "Postboks 37, 0621 Bogerud"]
           [:div.flex.justify-start.flex-wrap.gap-4
            [sc/subtext-with-link {:class [:dark]
                                   :href  "mailto:styret@nrpk.no"} "styret@nrpk.no"]
            [sc/subtext-with-link {:class [:dark]
                                   :href  "mailto:medlem@nrpk.no"} "medlem@nrpk.no"]]]]
         [sc/row-ec
          [hoc.buttons/reg-pill-icon
           {:on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
           ico/tilbakemelding "Tilbakemelding"]]
         [sc/row-sc-g4-w
          [sc/col
           [sc/small1 (or booking.data/VERSION "version")]
           [sc/small1 (or booking.data/DATE "date")]]

          (when-not ipad?
            [hoc.buttons/reg-pill
             {:on-click #(do
                           (rf/dispatch [:app/navigate-to [:r.mine-vakter-ipad]])
                           (db/database-update {:path  ["system"]
                                                :value {"active" @user-uid}}))}
             "Bli til Båtlogg"])

          (when @(rf/subscribe [:lab/admin-access])
            [hoc.buttons/danger-pill
             {:on-click #(db/database-update {:path  ["system"]
                                              :value {"timestamp" booking.data/DATE}})}
             "Aktiver versjon"])]]]]]]))