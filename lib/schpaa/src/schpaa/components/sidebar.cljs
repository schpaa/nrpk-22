(ns schpaa.components.sidebar
  (:require [schpaa.style :as s]
            [schpaa.components.widgets :as widgets]
            [schpaa.components.sidebar-mock :as sidebar.mock]
            [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.components.sidebar.toolbar :as sidebar.toolbar]
            [eykt.fsm-helpers :refer [send]]
            [schpaa.components.tab :refer [tab]]
            [schpaa.modal :as modal]
            [reagent.core :as r]))


(declare brygge-content)

(def tabs-data
  {:list      {:local-tab-data sidebar.mock/content-tab-data
               :content-fn     sidebar.mock/content-content-fn
               :below-fn       (fn [_] [:div "Below"]) #_sidebar.mock/content-below-fn}
   :bar-chart {:local-tab-data sidebar.mock/barchart-panel-data
               :content-fn
               (fn [_]
                 [:div.grid.gap-y-2x.-my-4x
                  {:style {:height             "calc(100vh - 4rem)"
                           :grid-template-rows "5rem 1fr 5rem"}}
                  [:div.rounded.p-2.-mx-2x.mx-3.mt-3
                   {:class "bg-black/20"}
                   "HEAD"]
                  [:div.overflow-y-scroll.px-4.py-16
                   (for [e (range 100)]
                     [:div e])]
                  [:div.rounded-t.p-2.mx-3
                   {:class "bg-black/20"}
                   "FOOTER"]]
                 #_[:div.flex.flex-col

                    [:div.flex.items-center.bg-pink-500.w-full.py-4.shrink-0
                     [:div.h-32.flex.items-center.w-full.px-4.mx-4
                      {:class "bg-black/20"} "content"]]

                    [:div.flex-grow-1.overflow-y-auto
                     (for [e (range 100)]
                       [:div e])]])
               :abelow-fn      (fn [_] [:div.bg-alt.h-32.flex-shrink-0 "Below"])}
   :cloud     {:local-tab-data sidebar.mock/brygge-panel-data
               :content-fn     (fn [_] [:div "content"])
               :below-fn       (fn [_] [:div "Below"])}
   :brygge    {:local-tab-data sidebar.mock/brygge-panel-data
               :content-fn     (fn [{:keys [selected-tab]}]
                                 (case selected-tab
                                   :a
                                   [:div.text-xl "Active -> " selected-tab
                                    (for [e (range 20)]
                                      [:div e])]
                                   (brygge-content)))

               :below-fn       (fn [_] [:div "Below"])}})

(defn tab-content-fn [selected-tab tabs-data]
  [:div.overflow-y-auto
   {:style {:height "calc(100vh - (4rem))"}
    ;intent apply color-scheme of selected tab
    :class (get-in s/color-map [selected-tab :selected :bg])}
   (let [tab-id selected-tab
         color-map (get s/color-map selected-tab)
         local-tab-data (get-in tabs-data [selected-tab :local-tab-data])
         content-fn (get-in tabs-data [selected-tab :content-fn])
         below-fn (get-in tabs-data [selected-tab :below-fn])
         tab (rf/subscribe [::widgets/get-tab tab-id])]
     [:div.flex.flex-col.min-h-full
      ;[:div (str @tab)]
      [:div.flex.flex-col.flex-grow
       ;intent this is for the local tabs, which I will remove for now
       #_[:div.sticky.top-0
          [widgets/tab-machine
           {:tabs-id          tab-id
            :class            (get-in color-map [:selected :bg])
            :selected-classes (get-in color-map [:selected :tab])
            :normal-classes   (get-in color-map [:normal :tab])
            :current-tab      @tab}
           local-tab-data]]
       [:div.xp-4.overflow-y-auto.rounded
        {:class (get-in color-map [:selected :tab])}
        ;intent Draw indiviual content
        (when content-fn
          (content-fn {:selected-tab @tab
                       :color-map    color-map}))]]
      ;intent Draw common content below
      (when below-fn
        [:div.p-2 (below-fn {:selected-tab @tab
                             :color-map    color-map})])])])

(defn sidebar
  ""
  [{:keys [get-menuopen-fn get-selected-tab] :as accessors}]
  (fn []
    [:div.fixed.ml-2
     {:class (concat
               ;[:right-0x :bottom-0 :top-0 :xs:w-80 :w-full :left-0]
               [:inset-0]
               [:origin-top-right]
               [:z-200]
               [:duration-200 :opacity-100]
               [:transform-gpu]
               (if (get-menuopen-fn) ["translate-x-0"]
                                     ["translate-x-full"]))}
     [:div.mob:m-0.overflow-hidden
      {:class (concat s/sidebar-color
                      [:mob:rounded-none]
                      #_(when (get-menuopen-fn)
                          [:drop-shadow-2xl
                           :mob:drop-shadow-none
                           :mob:shadow-none
                           :shadow-2xl
                           "shadow-black/90"]))}
      [sidebar.toolbar/toolbar (conj {:color-map s/color-map} accessors)]
      [tab-content-fn (get-selected-tab) tabs-data]]]))

(defn render [{:keys [] :as accessors}]
  [sidebar
   (conj accessors
         {:uid              (fn [] 123)                     ;(rf/subscribe [::db/root-auth :uid])
          :set-selected-tab (fn [e] (rf/dispatch [::widgets/set-tab e]))
          ;:tab-content-fn   tab-contents
          :get-selected-tab (fn [] @(rf/subscribe [::widgets/get-tab]))
          :current-tab      (fn [] 0)})])

(defn takk-for-tilbakemelding! []
  (js/setTimeout
    (fn [] (modal/form-action {:icon  :heart
                               :flags #{:time-out :message :weak-dim}
                               :title "Takk for tilbakemelding!"}))
    2000))

(defn brygge-content []
  [:div.space-y-4.p-3
   (let [admin
         (fn [] (modal/form-action
                  {:flags   #{:weak-dim}
                   :title   "state"
                   :form-fn (fn []
                              [:div
                               [l/ppre-x (:re-statecharts.core/fsm-state @re-frame.db/app-db)]
                               [modal/just-buttons [["Report" [:btn-free] #(do
                                                                             (send :e.hide)
                                                                             (tap> "Send report home")
                                                                             (takk-for-tilbakemelding!))]]]])}))



         regular #(modal/form-action {:title "app-db" :form-fn (fn [] [l/ppre-x @re-frame.db/app-db])})]

     (r/with-let [s (r/atom 1)]
       [:div (schpaa.components.tab/tab
               (conj schpaa.components.tab/select-bar-config
                     {:item     ["w-1/2"]
                      :select   #(reset! s %)
                      :selected (deref s)})
               [0 "Administrator" #(admin)]
               [1 "Vanlig" #(regular)]
               #_[2 "Gaus?" #(regular)])]))

   [:button.btn.btn-free
    {:on-click #(modal/form-action
                  {:flags   #{:timeout :weak-dim}
                   :title   "Overskrift"
                   :icon    :circle-question
                   :form-fn (fn [] [:div.space-y-1.p-4
                                    [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                    [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]])
                   :footer  "Lukkes automatisk etter 5 sekunder"})}
    "Trigger 3 - error"]])

#_(defn brygge-content []
    [:div.space-y-4.p-3
     (let [admin #(modal/message-with-timeout "adm" [l/ppre-x re-frame.db/app-db])
           regular #(modal/message-dialog "reg" [l/ppre-x re-frame.db/app-db])]

       (r/with-let [s (r/atom 1)]
         [:div (schpaa.components.tab/tab
                 (conj schpaa.components.tab/select-bar-config
                       {:item     ["w-1/2"]
                        :select   #(reset! s %)
                        :selected (deref s)})
                 [0 "Administrator" #(admin)]
                 [1 "Vanlig" #(regular)]
                 #_[2 "Gaus?" #(regular)])]))
     [:div "BRYGGE"]

     [:div.space-y-1.flex.flex-col
      [:button.btn.btn-free
       {:on-click #(modal/error-message
                     {:title     "Overskrift"
                      :footer    [:div.text-xs "Du trenger ikke trykke på knappen for å fjerne denne meldingen. Trykk hvor som helst utenfor..."]
                      :text      [:div.space-y-1
                                  [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                  [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]]
                      :primary   "Ok"
                      :secondary "Rapporter"})}
       "Trigger 3 - error"]
      [:button.btn.btn-free
       {:on-click #(modal/form-action
                     {:title   "Overskrift"
                      :footer  [:p.text-xs "Du trenger ikke trykke på knappen for å fjerne denne meldingen. Trykk hvor som helst utenfor..."]
                      :text    [:div.space-y-1
                                [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]]

                      :primary "Enig"})}
       "Trigger 1"]
      [:button.btn.btn-free
       {:on-click #(modal/are-you-sure?
                     {:secondary "Ok"
                      :title     "Overskrift"
                      :footer    [:p.text-xs "Du trenger ikke trykke på knappen for å fjerne denne meldingen. Trykk hvor som helst utenfor..."]
                      :text      [:div.space-y-1
                                  [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                  [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]
                                  [:p.italic "Du trenger ikke trykke på knappen for å fjerne denne meldingen. Trykk hvor som helst utenfor..."]]})}
       "Trigger 2"]
      [:button.btn.btn-free
       {:on-click #(modal/are-you-sure-forced?
                     {:on-primary (fn [] (js/alert "confirm"))
                      :on-close   (fn [] (send :e.hide))
                      :secondary  "Ok"
                      :title      "Overskrift"
                      :footer     [:p.text-xs "Her må du trykke på ok for å bekrefte!"]
                      :text       [:div.space-y-1
                                   [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                   [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]]})}

       "Trigger 3 - forced"]
      [:button.btn.btn-free
       {:on-click #(modal/are-you-sure-forced?
                     {:on-primary (fn [] (js/alert "confirm"))
                      :on-close   (fn [] (send :e.hide))
                      :secondary  "Nei"
                      :primary    "Ja"
                      :title      "Overskrift"
                      :footer     [:p.text-xs "Her må du trykke på en av knappene for å bekrefte!"]
                      :text       [:div.space-y-1
                                   [:p "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                   [:p "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]]})}

       "Trigger 3 - forced"]
      [:button.btn.btn-danger
       {:on-click #(modal/are-you-sure-cover-and-forced?
                     {:on-primary (fn [] (js/alert "confirm"))
                      :on-close   (fn [] (send :e.hide))
                      :secondary  "Nei"
                      :primary    "Ja"
                      :title      "Overskrift"
                      :footer     [:p.text-xs.leading-relaxed "Her må du trykke på en av knappene for å bekrefte! Husk at du må trykke Ja hvis du vil at vi ikke skal slette harddisken din."]
                      :text       [:div.space-y-2.antialiased
                                   [:div "Vi prøver oss på en lang melding som de færreste kommer til å lese"]
                                   [:div "I tillegg til å ha flere avsnitt garanterer du at ingen kommer til å lese dette."]
                                   [:div "Trykk Nei hvis du ikke vil at vi ikke skal slette hardisken din"]]})}

       "Trigger 3 - cover"]]])
