(ns booking.common-widgets
  (:require [schpaa.style.ornament :as sc]
            [kee-frame.core]
            [reagent.core :as r]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]
            [schpaa.icon :as icon]
            [booking.routes]
            [headlessui-reagent.core :as ui]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.hoc.toggles :as hoc.toggles]
            [db.core :as db]
            [clojure.string :as str]
            [schpaa.style.hoc.buttons :as button]
            [booking.styles]
            [schpaa.debug :as l]))

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

;todo refactor on right-side
(defn vertical-button
  [{:keys [right-side caption opposite-on-click with-caption? opposite-icon-fn tall-height
           special icon icon-fn content-fn class on-click page-name badge disabled]}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        selected? (if (fn? page-name)
                    (page-name current-page)
                    (= current-page page-name))
        screen-side (if right-side :right-side :left-side)]
    [sc/toolbar-button-with-caption
     {:style    {:justify-content :space-between
                 :align-items     :center}
      :class    [:gap-2 :w-full :flex :flex-row-reverse :justify-center (when selected? :selected)]
      :on-click #(on-click current-page)}

     (when (and right-side caption)
       [sc/text2 {:style {:width       "auto"
                          :white-space :normal
                          :color       "unset"
                          :flex-grow   1
                          :flex        "1 1 auto"}} caption])

     (when (and right-side opposite-icon-fn caption)
       [:div.w-16.h-20.flex.justify-center.items-center
        {:on-click opposite-on-click}
        [sc/icon (opposite-icon-fn)]])

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

       (cond
         content-fn
         (content-fn)

         icon-fn
         (sc/toolbar-button
           {:disabled  disabled
            :tab-index (when selected? "-1")
            :class     [screen-side
                        (if selected? (or (when class (class current-page)) :selected))
                        (if special :special)]}
           [sc/icon-large {:class [:shrink-0]} (icon-fn current-page)]))]]

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
   [:svg.w-5.inline-block {:viewBox "-3 -3 6 6"}
    [:circle {:cx 0 :cy 0 :r 2
              :stroke-width 0.5
              :stroke (get {0 "var(--brand1)"
                            1 "var(--blue-7)"
                            2 "var(--orange-6)"
                            3 "var(--red-7)"} (js/parseInt stability) :black)
              :fill (get {0 "var(--brand1)"
                          1 "var(--blue-7)"
                          2 "var(--orange-6)"
                          3 "var(--red-7)"} (js/parseInt stability) :white)}]
    [:circle {:cx 0 :cy 0 :r 1 :fill (if expert :red :transparent)}]]])

(defn favourites-star' [{:keys [on-star-click]}
                        {:keys [id] :as m}]
  (let [uid (some-> @(rf/subscribe [:lab/uid]) name)
        ;_ (tap> {:boat-type id})
        boat-type (or (some-> id name) nil)
        bt-data (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]})
        ;_ (tap> ["users" uid "starred" boat-type])
        ex-data (db/on-value-reaction {:path ["users" uid "starred" boat-type]})]
    [sc/row {:style {:column-gap  "var(--size-1)"
                     :align-items :center
                     :color       (if (and id @ex-data) "var(--yellow-6)"
                                                        (if-not id "var(--toolbar-)"
                                                                   "var(--red-6)"))}}
     (when (pos? @bt-data)
       [:div {:line-height "var(--font-lineheight-2)"
              :font-size   "var(--font-size-1)"
              :font-weight "var(--font-weight-4)"} @bt-data])
     (if id
       [sc/icon-small
        {:on-click #(on-star-click boat-type (not @ex-data) uid)}
        (if @ex-data ico/stjerne ico/ikkeStjerne)]
       [sc/icon-small
        {:on-click #(do
                      (tap> ["clack" boat-type id])
                      (on-star-click boat-type (or (not @ex-data) true) uid))}
        ico/ikkeStjerne])]))

(defn stability-name-category [{:keys [stability expert navn kind id] :as m}]
  [sc/col {:class [:truncate :space-y-px :w-full]}
   (when kind
     [sc/title1 {:class []} (schpaa.components.views/normalize-kind kind)])

   [:div.flex.items-center.justify-between.gap-1

    (when (or stability expert) [stability-expert m])
    [sc/text2 {:style {:overflow      :hidden
                       :text-overflow :ellipsis
                       :white-space   :nowrap}} navn]
    [:div.grow]
    [favourites-star' {:on-star-click (fn [boat-type value uid]
                                        (rf/dispatch [:star/write-star-change
                                                      {:boat-type boat-type
                                                       :value     value
                                                       :uid       uid}]))} m]]])

(declare data-url)

(defn stability-name-category' [{:keys [url? k reversed?]}
                                {:keys [stability expert navn kind] :as m}]
  [sc/col {:style {:flex-direction (if reversed? :column-reverse)}
           :class [:truncate :space-y-px :w-full]}
   (when kind
     [sc/text1 {:class []} (schpaa.components.views/normalize-kind kind)])

   [:div.flex.items-center.justify-between.gap-1
    (when (or stability expert) [stability-expert m])
    (if url?
      (data-url {:caption      navn
                 :checked-path ["boat-brand" k]
                 :text         #(vector sc/title1 {:style {:overflow      :hidden
                                                           :text-overflow :ellipsis
                                                           :white-space   :nowrap}} %)}))

    [:div.grow]
    [favourites-star' {:on-star-click (fn [boat-type value uid]
                                        (rf/dispatch [:star/write-star-change
                                                      {:boat-type boat-type
                                                       :value     value
                                                       :uid       uid}]))}
     m]]])


(defn favourites-star [{:keys [ex-data bt-data on-star-click]}
                       {:keys [boat-type] :as m}]
  (let [uid @(rf/subscribe [:lab/uid])
        bt-data (or nil (db/on-value-reaction {:path ["boat-brand" boat-type "star-count"]}))
        ex-data (or nil (db/on-value-reaction {:path ["users" uid "starred" boat-type]}))]
    [sc/row {:style {:column-gap  "var(--size-2)"
                     :align-items :center}}
     (when (pos? @bt-data)
       [sc/text1 @bt-data])
     [sc/icon-large
      {:on-click #(on-star-click boat-type (not @ex-data))
       :style    {:color (if @ex-data "var(--yellow-6)" "rgba(0,0,0,0.5)")}}
      (if @ex-data ico/stjerne ico/stjerne)]]))

;region

(defn trashcan' [{:keys [on-click deleted?]} caption]
  (let [{mobile? :mobile?} @(rf/subscribe [:lab/screen-geometry])]
    (if mobile?
      [button/reg-icon
       {:on-click #(on-click)
        :class    [(if deleted? :frame :danger) :round]
        :disabled false}
       (if deleted? ico/rotate-left ico/trash)]

      [button/reg-pill-icon
       {:on-click #(on-click)
        :class    [(if deleted? :frame :danger)]
        :disabled false}
       (if deleted? ico/rotate-left ico/trash)
       caption])))

(defn in-out' [{:keys [on-click deleted?]} caption]
  (let [{:keys [mobile?] :as geo} @(rf/subscribe [:lab/screen-geometry])]
    (if mobile?
      [button/reg-icon
       {:on-click #(on-click)
        :class    [(if deleted? :outline2 :cta) :round]
        :disabled false}
       ico/status]

      [button/reg-pill-icon
       {:on-click #(on-click)
        :class    [(if deleted? :outline2 :cta)]
        :disabled false}
       ico/status
       caption])))

(defn trashcan [on-click {:keys [deleted id]}]
  [(if deleted scb/round-undo-listitem scb/round-danger-listitem)
   {:on-click #(on-click id)}
   [sc/icon-small
    (if deleted
      (icon/adapt :rotate-left)
      ico/trash)]])

(defn edit [attr on-click {:keys [deleted] :as m}]
  (let [{:keys [mobile?] :as geo} @(rf/subscribe [:lab/screen-geometry])
        attr (conj
               attr
               {:disabled true
                :class    [:frame]
                :style    {:text-transform "uppercase"
                           :border-color   "var(--blue-7)"
                           :background     "var(--blue-6)"
                           :color          "var(--blue-0)"}
                :on-click #(on-click m)})]
    (if deleted
      [:div.w-8]
      (if mobile?
        [hoc.buttons/reg-icon attr
         [sc/icon-small
          {:style {:xcolor "var(--blue-0)"}}
          ico/pencil]]

        [hoc.buttons/reg-pill-icon attr
         [sc/icon-small
          {:style {:xcolor "var(--blue-0)"}}
          ico/pencil]
         "Endre"]))))

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
   {:class [:truncate]
    :style {:white-space :nowrap}
    :href  (kee-frame.core/path-for [:r.dine-vakter {:id uid}])}
   (user.database/lookup-alias uid)])

(defn auto-link
  ([{:keys [caption] :as opts} link]
   (cond
     caption
     [sc/link {:href (kee-frame.core/path-for [link])} caption]

     :else
     (if (vector? link)
       [sc/link {:href (kee-frame.core/path-for link)} (second link)]
       (let [{:keys [link text]} (lookup-page-ref-from-name link)]
         [sc/link {:href (kee-frame.core/path-for [link])} text]))))
  ([opts [_ l :as link] resolver]
   (if (vector? link)
     [sc/link
      {:href (kee-frame.core/path-for link)}
      ((fn [{:keys [id]}]
         (:caption (first (filter (fn [m] (= id (:id m))) resolver))
           {:caption (str id)})) l)]
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

;; logo

(defn circular-logo-thing [{:keys [dark-mode? clear-map?] :as opts}]
  [:div {:style {:width        "6em"
                 :height       "6em"
                 :transform    "rotate(6deg)"
                 :aspect-ratio "1/1"}}
   [booking.styles/logothing {:class [(if dark-mode? :dark :light)]}
    [:img {:src   "/img/logo-n.png"
           :style {:position                  :absolute
                   :inset                     0
                   :object-fit                :contain
                   :border-radius             "var(--radius-round)"
                   :animation-name            "spin3"
                   :animation-timing-function "var(--ease-4)"
                   :animation-delay           "0s"
                   :animation-duration        "1.1s"
                   :animation-iteration-count 1
                   :animation-direction       :forward
                   :transform-origin          "center"}}]
    [:div
     {:style {:position         :absolute
              :inset            0
              :clip-path        "circle(35% at 50% 50%)"
              :background-color (if clear-map? "var(--toolbar-)")}}]]])

(defn logo-graph
  ([]
   (logo-graph {}))
  ([opts]
   [:div {:style {:align-self   :center
                  :justify-self :start}}
    (let [dark-mode? @(schpaa.state/listen :app/dark-mode)]
      [circular-logo-thing (conj opts {:dark-mode? dark-mode?})])]))

(defn logo-embedded [icon]
  [:div.relative
   [logo-graph {:clear-map? true}]
   [:div.absolute.inset-0.grid.place-content-center
    {:style {:color "var(--gray-5)"}}
    [sc/icon-huge icon]]])

;;

(defn no-access-view [r]
  ;todo start a timeout on mount and show no access after 2 seconds
  (let [show-error (r/atom false)]
    (r/create-class
      {:display-name        "no-access-view"
       :component-did-mount (fn [_]
                              (js/setTimeout #(reset! show-error true) 2000))
       :reagent-render
       (fn [r]
         (if @show-error
           (let [required-access (-> r :data :access)]
             [sc/container
              {:style {;:min-height     "calc(100vh - 4rem)"
                       ;:padding-inline "var(--size-4)"
                       ;:padding-top    "var(--size-10)"
                       :margin-inline "auto"}}

              [sc/col-space-8 {:class [:items-center]}
               [sc/hero {:style {:white-space :nowrap
                                 :text-align  :center
                                 :color       "var(--text2)"}} "Ingen tilgang"]

               [sc/row-center [logo-embedded ico/stengt]]
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
                [auto-link nil :r.forsiden]
                [auto-link nil :r.oversikt]]]])
           [:div.mt-32
            [:div
             {:style {:min-height "calc(100vh -  4rem)"}}
             [:div.flex.justify-center.items-center.h-full.w-full
              [sc/title (icon/spinning :spinner)]]]]))})))

(defn disclosure
  ([m]
   (apply disclosure m))
  ([attr tag question answer]
   (disclosure attr tag question answer nil))
  ([{:keys [padded-heading] :as attr} tag question answer extra-section]
   (let [open? @(schpaa.state/listen tag)
         attr (conj {:style {:padding-block "var(--size-2)"}} attr)]
     (let [disclojure-button
           (fn [{:keys [open]}]
             [sc/row-sc-g2
              {:class    (when padded-heading padded-heading)
               :style    {:cursor :pointer}
               :on-click #(schpaa.state/toggle tag)}
              (sc/icon {:style {:color "var(--brand2)"}
                        :class [:duration-200 :w-12 :h-12
                                :transform
                                (when open :rotate-90)]}
                       ico/showdetails)
              [:span [sc/fp-headersmaller {:class [:text-left
                                                   :pointer-events-none (when (:large attr) :large)]
                                           :style {:color (if open "var(--text0)" "var(--text2)")}}
                      question]]])]
       [ui/disclosure {:style {:cursor :default}
                       #_#_:defaultOpen true}
        (fn [_]
          [sc/col

           [ui/disclosure-button
            {:on-click #(schpaa.state/toggle tag)
             :style    {:cursor :default}
             :class    [:flex :justify-start :items-center :gap-2
                        :w-full
                        :focus:outline-none :focus-visible:ring :focus-visible:ring-purple-500 :focus-visible:ring-opacity-75]}
            disclojure-button]

           [ui/disclosure-panel
            (merge-with into
                        {:static  true
                         :unmount false}
                        (dissoc attr :links))
            (fn [{:keys [open]}]
              (if (:links attr)
                (if open?
                  [sc/col-space-4 answer extra-section]
                  extra-section)
                (if open?
                  (if answer
                    [sc/text2 answer]
                    [sc/text2 extra-section])
                  (when-not answer
                    [sc/text2 extra-section]))))]])]))))

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
   [sc/dropdown-dialog' {:style {:padding "1rem"}}
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

(defn location [l]
  (if (= "1" (str l)) "Sjøbasen" "Nøklevann"))

(defn badge [attr n v]
  [:div.flex
   [sc/badge-2 (update attr :class conj :right-square :regular) n]
   [sc/badge-2 (update attr :class conj :slot) (if v (str/trim v) "—")]])

(defn pillbar
  ([c vs]
   (pillbar {:class [:narrow :outline2 :normal]} c vs))
  ([attr c vs]
   (let [f (fn [[k v]]
             [schpaa.style.hoc.toggles/pillbar
              (let [s (merge-with into {:class    [:normal (when (= k @c) :inverse)]
                                        :on-click #(if (= k @c)
                                                     (reset! c nil)
                                                     (reset! c k))}
                                  attr)
                    _ (tap> {:style s})]
                s)
              v])]
     [sc/row (into [:<>] (map f vs))])))

(defn open-slideout [dialog-def]
  (rf/dispatch [:modal.slideout/show {:content-fn dialog-def}]))

(defn waves
  ([base]
   (let [colors (map #(str "#" base %) ["44" "66" "88" "ff"])]
     [:svg#svg.transition.duration-300.ease-in-out.delay-150
      {:width               "100%" :height "100%" :viewBox "0 0 1440 400"
       :preserveAspectRatio "none"
       :xmlns               "http://www.w3.org/2000/svg"}
      [:defs
       [:linearGradient#gradienta
        {:x1 "89%" :y1 "19%" :x2 "11%" :y2 "81%"}
        [:stop {:offset "5%" :stop-color (nth colors 0)}]
        [:stop {:offset "95%" :stop-color (nth colors 0)}]]
       [:linearGradient#gradientb
        {:x1 "89%" :y1 "19%" :x2 "11%" :y2 "81%"}
        [:stop {:offset "5%" :stop-color (nth colors 1)}]
        [:stop {:offset "95%" :stop-color (nth colors 1)}]]
       [:linearGradient#gradientc
        {:x1 "89%" :y1 "19%" :x2 "11%" :y2 "81%"}
        [:stop {:offset "5%" :stop-color (nth colors 2)}]
        [:stop {:offset "95%" :stop-color (nth colors 2)}]]
       [:linearGradient#gradientd
        {:x1 "89%" :y1 "19%" :x2 "11%" :y2 "81%"}
        [:stop {:offset "5%" :stop-color (nth colors 3)}]
        [:stop {:offset "95%" :stop-color (nth colors 3)}]]]
      (into [:<>]
            [[:path.transition-all.duration-300.ease-in-out.delay-150.path-0
              {:d    "M 0,400 C 0,400 0,80 0,80 C 90.82296650717703,72.28708133971293 181.64593301435406,64.57416267942584 271,59 C 360.35406698564594,53.42583732057416 448.23923444976083,49.99043062200957 531,62 C 613.7607655502392,74.00956937799043 691.3971291866028,101.46411483253587 810,108 C 928.6028708133972,114.53588516746413 1088.1722488038276,100.1531100478469 1200,92 C 1311.8277511961724,83.8468899521531 1375.9138755980862,81.92344497607655 1440,80 C 1440,80 1440,400 1440,400 Z" :stroke "none" :stroke-width "0"
               :fill "url(#gradienta)"}]
             [:path.transition-all.duration-300.ease-in-out.delay-150.path-1
              {:d "M 0,400 C 0,400 0,160 0,160 C 92.00956937799043,157.21531100478467 184.01913875598086,154.43062200956936 275,154 C 365.98086124401914,153.56937799043064 455.93301435406704,155.49282296650716 558,162 C 660.066985645933,168.50717703349284 774.2488038277512,179.5980861244019 884,172 C 993.7511961722488,164.4019138755981 1099.071770334928,138.11483253588517 1191,133 C 1282.928229665072,127.88516746411483 1361.4641148325359,143.94258373205741 1440,160 C 1440,160 1440,400 1440,400 Z" :stroke "none" :stroke-width "0" :fill "url(#gradientb)"}]
             [:path.transition-all.duration-300.ease-in-out.delay-150.path-2
              {:d "M 0,400 C 0,400 0,240 0,240 C 98.25837320574163,227.42583732057415 196.51674641148327,214.85167464114832 291,219 C 385.48325358851673,223.14832535885168 476.1913875598086,244.01913875598083 558,245 C 639.8086124401914,245.98086124401917 712.7177033492824,227.07177033492823 826,230 C 939.2822966507176,232.92822966507177 1092.9377990430621,257.69377990430627 1202,263 C 1311.0622009569379,268.30622009569373 1375.531100478469,254.15311004784687 1440,240 C 1440,240 1440,400 1440,400 Z" :stroke "none" :stroke-width "0" :fill "url(#gradientc)"}]
             [:path.transition-all.duration-300.ease-in-out.delay-150.path-3
              {:d "M 0,400 C 0,400 0,320 0,320 C 82.33492822966508,327.18660287081343 164.66985645933016,334.3732057416268 249,331 C 333.33014354066984,327.6267942583732 419.6555023923445,313.6937799043062 528,312 C 636.3444976076555,310.3062200956938 766.7081339712919,320.85167464114835 881,329 C 995.2918660287081,337.14832535885165 1093.511961722488,342.8995215311005 1184,341 C 1274.488038277512,339.1004784688995 1357.244019138756,329.55023923444975 1440,320 C 1440,320 1440,400 1440,400 Z" :stroke "none" :stroke-width "0" :fill "url(#gradientd)"}]])])))

(defn temperature [{:keys [air water wind on-click]}]
  (letfn [(goto [link]
            (rf/dispatch [:app/navigate-to [link]]))
          (degrees-celsius [attr c]
            [:div
             (merge-with into
                         {:style {:font-size   "100%"
                                  :font-weight "var(--font-weight-6)"
                                  :align-items :baseline
                                  :xtext-shadow "#FC0 1px 0 10px"}}
                         attr)
             (times.api/format "%0.1f" c) [:sup "°c"]])
          (centerline [value]
            [sc/row-end {:class [:text-right :mx-auto]
                         :style {:overflow        :hidden
                                 :align-items     :baseline
                                 :justify-content :end
                                 :width           "auto"}}
             value])]
    [:div.relative.w-full.h-full
     {:on-click on-click}
     ;bg
     [:div.flex.flex-col.h-full
      {:style {:justify-content :end}}
      [:div.grow]
      [:div {:style {:height (if wind (case wind
                                        :high  "50%"
                                        :mid  "40%"
                                        :low "30%"
                                        "20%"))}} (waves "0693e3")]
      [:div
       {:style {:height           "30%"
                :width            "100%"
                :background-color "#0693e3"}}]]
     ;text
     [:div.absolute.inset-0
      [:div.flex.flex-col.h-full.items-center.min-w-fit
       {:style {:justify-content :space-around}}

       (centerline (degrees-celsius {:class []
                                     :style {:z-index 1
                                             :color "var(--text1)"}} air))
       (centerline (degrees-celsius {:style {:color "white"}} water))]]]))

(defn after-content []
  (let [route @(rf/subscribe [:kee-frame/route])
        screenmode (rf/subscribe [:app/user-screenmode])
        user-uid (rf/subscribe [:lab/uid])
        ipad? (= @user-uid @(db/on-value-reaction {:path ["system" "active"]}))]
    (fn []
      (let [base (if (= :dark @screenmode)
                   (apply str (map #(.toString % 16) [146 186 110]))
                   "0693e3")]
        [:div.p-2.noprint
         ;[l/pre @screenmode]
         [:div.h-20 [waves base]]
         [:div.z-1.rounded-top
          {:style {:border-radius           "var(--radius-1)"
                   :border-top-right-radius "0"
                   :border-top-left-radius  "0"
                   :background              (str "#" base "FF") #_"#0693e3ff"}}
          [:div.mx-auto.max-w-xl.pt-4.pb-16
           [:div.mx-4
            [sc/col-space-4

             [sc/row-center
              [logo-graph]]

             [sc/row-fields
              [sc/col-space-1
               [sc/title {:class [:bold :pt-1]
                          :style {:color "var(--gray-9)"}} "Postadresse"]
               [sc/col-space-4
                [sc/col-space-1
                 {:style {:user-select :contain
                          :color       "var(--gray-9)"}}
                 [sc/text {:style {:color "var(--gray-9)"}} "Nøklevann ro- og padleklubb"]
                 [sc/text {:style {:color "var(--gray-9)"}} "Postboks 37, 0621 Bogerud"]]

                [:div.flex.justify-start.flex-wrap.gap-4
                 [sc/subtext-with-link {:class [:neutral]
                                        :style {:color "var(--gray-1)"}
                                        :href  "mailto:medlem@nrpk.no"} "medlem@nrpk.no"]
                 [sc/subtext-with-link {:class [:neutral]
                                        :style {:color "var(--gray-1)"}
                                        :href  "mailto:styret@nrpk.no"} "styret@nrpk.no"]]]]
              [sc/col-space-1
               {:style {:align-items :end :justify-content :start}}
               (when-not ipad?
                 [:div
                  [hoc.buttons/round-pill
                   {:class    [:message :narrow]
                    :on-click #(do
                                 (rf/dispatch [:app/navigate-to [:r.mine-vakter-ipad]])
                                 (db/database-update {:path  ["system"]
                                                      :value {"active" @user-uid}}))}
                   "Bli til Båtlogg"]])

               (when @(rf/subscribe [:lab/admin-access])
                 [:div
                  [hoc.buttons/round-pill
                   {:class    [:danger :narrow]
                    :on-click #(db/database-update {:path  ["system"]
                                                    :value {"timestamp" booking.data/DATE}})}
                   "Aktiver!"]])

               [:div
                [hoc.buttons/pill-icon-caption
                 {:class    [:narrow :frame]
                  :style    {:color        "var(--gray-1)"
                             :border-color "var(--gray-1)"}
                  :on-click #(rf/dispatch [:app/give-feedback {:source (some-> route :path)}])}
                 ico/tilbakemelding
                 "Tilbakemelding"]]]]

             [sc/row-fields
              [sc/small (or booking.data/VERSION "version")]
              [sc/small (or booking.data/DATE "date")]]]]]]]))))

(defn data-url [{:keys [checked-path text caption]}]
  (let [path (apply str (interpose "/" (mapv #(if (keyword? %) (name %) %) checked-path)))]
    (sc/link {:class  [:truncate]
              :style {:text-decoration "none"}
              :target "_blank"
              :href   (if goog.DEBUG
                        (str "http://localhost:4000/database/nrpk-vakt/data/" path)
                        (str "https://nrpk-vakt.firebaseio.com/" path))}
             (if text
               (text caption)
               "url"))))