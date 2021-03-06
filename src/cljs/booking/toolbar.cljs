(ns booking.toolbar
  (:require [re-frame.core :as rf]
            [lambdaisland.ornament :as o]
            [db.core :as db]
            [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]
            [booking.ico :as ico]))

;; toolbars

(defn vertical-toolbar-definitions []
  (let [uid @(rf/subscribe [:lab/uid])
        {:keys [right-menu? menu-caption?]} @(rf/subscribe [:lab/screen-geometry])
        presence (rf/subscribe [::db/presence-status])
        admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        ipad? (= uid @(db/on-value-reaction {:path ["system" "active"]}))
        registered? (rf/subscribe [:lab/at-least-registered])
        booking? (rf/subscribe [:lab/booking])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]
    ;(tap> [ipad?])
    [#_(when goog.DEBUG
         {:on-click   #()
          :caption    "Lufttemperatur Vanntemperatur"
          :content-fn (fn []
                        [:div.w-full.h-full
                         {:style {:background-color "var(--floating)"}}
                         [widgets/temperature {:air 27.2 :water 23.5 :on-click #(rf/dispatch [:app/navigate-to [:r.temperature]])}]])})

     {:icon-fn      #(sc/icon-large ico/new-home)
      :default-page :r.forsiden

      :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class        #(if (= % :r.oversikt) :oversikt :selected)
      :page-name    #(some #{%} [:r.forsiden :r.oversikt])}

     (if ipad?
       {:icon-fn      (fn [] (sc/icon-large ico/vaktrapport))
        :caption      "Vaktrapport"
        :default-page :r.mine-vakter-ipad
        :class        #(if (= % :r.user) :oversikt :selected)
        :on-click     #(rf/dispatch [:app/navigate-to [:r.mine-vakter-ipad #_(if (= % :r.min-status) :r.user :r.min-status)]])
        #_#(rf/dispatch [:app/navigate-to [:r.min-status]])
        :page-name    :r.mine-vakter-ipad #_#(some #{%} [:r.min-status :r.user])}

       {:icon-fn      (fn [] (sc/icon-large ico/user))
        :caption      "Mine opplysninger"                                      
        :default-page :r.min-status
        :class        #(if (= % :r.user) :oversikt :selected)
        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.min-status) :r.user :r.min-status)]])
        #_#(rf/dispatch [:app/navigate-to [:r.min-status]])
        :page-name    #(some #{%} [:r.min-status :r.user])})

     (when (or goog.DEBUG @admin? @nokkelvakt ipad?) 
       {:icon-fn      #(-> ico/mystery1)
        :disabled     false
        :caption      "Utl??n N??klevann"
        :default-page :r.utlan
        :page-name    #(some #{%} [:r.utlan :r.b??tliste.n??klevann])
        :class        #(if (= % :r.utlan) :selected :oversikt)
        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.utlan) :r.b??tliste.n??klevann :r.utlan)]])})

     (when (or @admin? @nokkelvakt)
       {:icon-fn      (fn [] (sc/icon-large ico/nokkelvakt))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.nokkelvakt]])
        :default-page :r.nokkelvakt
        :page-name    :r.nokkelvakt})

     (when (or @admin? @booking?)
       {:icon-fn      (fn [] (sc/icon-large ico/booking))
        :caption      "Booking Sj??basen"
        :default-page :r.booking
        :on-click     #(rf/dispatch [:app/navigate-to [:r.booking]])
        :page-name    :r.booking})

     (when (or @member? @admin? @registered?)
       {:icon-fn      (fn [] (sc/icon-large ico/yearwheel))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
        :default-page :r.yearwheel
        :page-name    :r.yearwheel})

     #_(when (or @admin? @nokkelvakt)
         {:icon-fn   (fn [] (let [st (rf/subscribe [:lab/number-input])]
                              [centered-thing (sc/icon-large (if @left-side?
                                                               (if @st ico/panelOpen ico/panelClosed)
                                                               (if @st ico/panelClosed ico/panelOpen)))]))
          :special   true
          :centered? true
          :on-click  #(rf/dispatch [:lab/toggle-number-input])})
     :space
     (when @admin?
       {:tall-height  true
        :default-page :r.fileman-temporary
        :icon-fn      (fn [] (sc/icon-large ico/fileman))
        :on-click     #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])
        :page-name    :r.fileman-temporary})
     (when (or @admin?)
       {:icon-fn      (fn [] (sc/icon-large ico/users))
        :badge        (fn [] {:attr  {:style {:background-color "var(--yellow-7)"
                                              :color            "var(--gray-9)"}}
                              :value (let [c (count (:online @presence))]
                                       (when (pos? c) c))})

        :on-click     #(rf/dispatch [:app/navigate-to [(if (= % :r.users) :r.presence :r.users)]])
        :tall-height  true
        :class        #(if (= % :r.presence) :selected :oversikt)
        :page-name    #(some #{%} [:r.presence :r.users])})
     {:tall-height true
      :caption     "Hva kan jeg gj??re?"
      :icon-fn     (fn [] (let [st (rf/subscribe [:lab/modal-selector])]
                            (sc/icon-large
                              (if @st ico/commandPaletteClosed ico/commandPaletteOpen))))
      :on-click    #(rf/dispatch [:app/toggle-command-palette])}
     {:tall-height       true
      :opposite-icon-fn  (fn [] (sc/icon-large
                                  {:style {:color "var(--brand2)"}}
                                  (if right-menu? ico/arrowLeft ico/arrowRight)))
      :opposite-on-click (fn [] (schpaa.state/toggle :lab/menu-position-right))
      :icon-fn           (fn [] (sc/icon-large
                                  (if menu-caption?
                                    (if right-menu?
                                      ico/panelClosed
                                      ico/panelOpen)
                                    (if right-menu?
                                      ico/panelOpen
                                      ico/panelClosed))))

      :on-click          #(schpaa.state/toggle :app/toolbar-with-caption)}]))


(defn horizontal-toolbar-definitions []
  (let [;uid (rf/subscribe [:lab/uid])
        admin? (rf/subscribe [:lab/admin-access])
        member? (rf/subscribe [:lab/member])
        booking? (rf/subscribe [:lab/booking])
        registered? (rf/subscribe [:lab/at-least-registered])
        nokkelvakt (rf/subscribe [:lab/nokkelvakt])]

    [{:icon-fn       #(-> ico/new-home)
      :short-caption "Hjem"
      :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.forsiden) :r.oversikt :r.forsiden)]])
      :class         #(if (= % :r.oversikt) :oversikt :active)
      :page-name     #(some #{%} [:r.forsiden :r.oversikt])}

     (when (or @admin? @booking?)
       {:icon-fn      (fn [] ico/booking)
        :caption      "Booking Sj??basen"
        :default-page :r.booking
        :on-click     #(rf/dispatch [:app/navigate-to [:r.booking]])
        :page-name    :r.booking})

     #_(when booking?
         {:icon          ico/booking
          :short-caption "Booking"
          :icon-disabled ico/booking-disabled
          :disabled      false})

     {:icon-fn       (fn [_] (let [st @(rf/subscribe [:lab/modal-selector])]
                               (if st ico/commandPaletteOpen ico/commandPaletteClosed)))
      :special       true
      :short-caption "S??k"
      :on-click      #(rf/dispatch [:app/toggle-command-palette])
      :page-name     :r.debug}


     (when (or @admin? @nokkelvakt)
       {:icon-fn       #(-> ico/mystery1)
        :disabled      false
        :caption       "Utl??n N??klevann"
        :short-caption "Utl??n"
        :default-page  :r.utlan
        :page-name     #(some #{%} [:r.utlan :r.b??tliste.n??klevann])
        :class         #(if (= % :r.utlan) :selected :oversikt)
        :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.utlan) :r.b??tliste.n??klevann :r.utlan)]])})

     {:icon-fn       #(-> ico/user)
      :short-caption "Mine"
      :caption       "Mine opplysninger"
      :default-page  :r.min-status
      :class         #(if (= % :r.user) :oversikt :selected)
      :on-click      #(rf/dispatch [:app/navigate-to [(if (= % :r.min-status) :r.user :r.min-status)]])
      :page-name     #(some #{%} [:r.min-status :r.user])}]))

(comment
  (do
    (lookup-page-ref-from-name :r.forsiden)))

(defn vertical-toolbar [right-side?]
  (let [has-chrome? (rf/subscribe [:lab/has-chrome])
        with-caption? @(schpaa.state/listen :app/toolbar-with-caption)
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)]
    (when (and @has-chrome? @(rf/subscribe [:lab/at-least-registered]))
      [:div.shrink-0.h-full.sm:flex.hidden.relative.select-none
       {:class [(if with-caption? :w-56 :w-16)]
        :style {;:box-shadow "var(--inner-shadow-2)"
                :z-index 0
                
                :background "var(--toolbar)"}}
       ;; force the toolbar to stay on top when boat-panel is displayed (?)
       ;;

       (into [:div.absolute.right-0.inset-y-0.w-full.flex.flex-col.relative.items-start
              {:style {:padding-top "var(--size-0)"}}
              (when right-side?
                [:div.absolute.inset-y-0.right-0])]              
             (map (fn [{:keys [opposite-icon-fn] :as e}]
                    (let [cap (when with-caption?
                                (let [p (:page-name e)]
                                  (if-some [caption (or (some->
                                                          (if (fn? p) (p current-page) p)
                                                          widgets/lookup-page-ref-from-name
                                                          :text)
                                                        (:caption e))]
                                    caption
                                    (when-let [dp (:default-page e)]
                                      (:text (widgets/lookup-page-ref-from-name dp))))))]
                      (if (keyword? e)
                        [:div.grow]
                        [widgets/vertical-button (assoc e :right-side right-side?
                                                          :with-caption? with-caption?
                                                          :caption cap)])))
                  (remove nil? (vertical-toolbar-definitions))))])))

(o/defstyled outer-button :div
           [:& :flex-center :relative])

(o/defstyled toolbar :div
           {:display :none}
           [:at-media {:max-width "511px"}
            [:& :grid :gap-0 :w-full
             {:height                "6rem"
              :width                 :100vw
              :bottom                0
              :background-color      "var(--toolbar)"
              :grid-template-rows    "auto 0rem "
              :grid-template-columns "repeat(5,1fr)"}
             [:&.admin
              {:grid-template-columns "repeat(7,1fr)"}]]])

(o/defstyled round :div
           {:border-radius "var(--radius-round)"})

(o/defstyled button :button
           [:& :-mt-4 :flex-center :p-2 :outline-none :focus:outline-none :self-center
            {:aspect-ratio "1/1"
             ;:max-width    "4rem"
             :color        "var(--text3)"}
            [#{:&.normal :&.selected}
             {:opacity 1}]
            [:&.normal
             {:color             "var(--text2)"}]
            [:&.selected round
             {:color            "var(--text1)"
              ;:box-shadow       "var(--shadow-2)"
              :background-color "var(--floating)"}]])

(defn button-fn [idx d]
  [widgets/vertical-button (conj d {:right-side    false    ;right-side?
                                    :with-caption? nil})]   ;with-caption?
  #_(let [{:keys [page-name]} d
          current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
          disable? (:disabled d)
          active? (if (fn? page-name)
                    (page-name current-page)
                    (= current-page page-name))
          class (if active? :selected (if (not disable?) :normal))]
      [outer-button
       {:on-click #((:on-click d))}
       [button
        {:class [:relative
                 class
                 :flex :items-end]}
        [sc/col-c-space-2
         [sc/icon-huge
          {:class (:class d)
           :style {}}
          (let [f (or (:icon d) (:icon-fn d))]
            (if (fn? f) (f page-name) f))]]]
       #_[icontext {:class [class]} (:short-caption d)]]))

(defn bottom-toolbar []
  (let [switch? @(schpaa.state/listen :lab/menu-position-right)
        data ((if switch? reverse identity) (horizontal-toolbar-definitions))]
    [toolbar (map widgets/toolbar-button data)]))
