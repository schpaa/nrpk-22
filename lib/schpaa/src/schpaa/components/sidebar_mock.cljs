(ns schpaa.components.sidebar-mock
  (:require [schpaa.style :as s]
            [re-frame.core :as rf]))


;region

(defn gr [attr & content]
  [:div.font-lora.text-base.truncate.space-y-1
   attr
   content])

(defn re [s]
  [:div.font-redacted.text-xl s])

(defn tr
  ([s]
   [:div.truncate.font-redacted.font-sansx.text-base s])
  ([s [route]]
   (let [r @(rf/subscribe [:kee-frame/route])]
     [:div.truncate.font-sans.text-base
      {:class    (concat
                   (if (= route (some-> r :data :name))
                     []
                     [:cursor-pointer :underline]))
       :on-click #(rf/dispatch [:app/navigate-to [route]])}
      (if (= route (some-> r :data :name)) "-> ")
      s])))

(defn h2 [attr s {:keys [group ingress]}]
  [:div.space-y-1
   [:h2
    attr                                                    ;{:class s/sidebar-header-color}
    s]
   (cond
     ingress [:div {:class s/std-header-color} [re ingress]]
     group [:div.space-y-4 [gr group]])])

(defn link [s]
  [:a
   {:class (concat [:font-sans :text-sm :underline]
                   s/sidebar-header-color)}
   s])

;endregion

(defn content-content-fn [{:keys [selected-tab color-map]}]
  (case selected-tab
    :a [:div.space-y-8
        [re "Liste med over\u00adskrifter og div annet av de siste og her kommer litt mer tekst"]

        [h2 {:class (-> color-map :content :h2)} "Ofte lest"
         {:group [gr
                  {:class (-> color-map :content :fg)}
                  [gr
                   {}
                   #_[:div.flex.flex-col.items-start.w-full.space-y-1
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:set-tab :list])} "Innhold"]
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:app/navigate-to [:r.calendar]])} "Vaktkalender"]
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:set-tab :bar-chart])} "Oversikt"]
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:set-tab :cloud])} "Vær"]
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:set-tab :brygge])} "Brygge"]
                      [:button.hover:underline.text-base.font-normal {:on-click #(rf/dispatch [:app/navigate-to [:r.status]])} "Status"]]

                   [tr "Forsiden" [:r.common]]
                   [tr "Vaktkalender" [:r.calendar]]
                   [tr "Sikkerhet"]
                   [tr "Klubbregler"]
                   [tr "Nøkkelvakter"]
                   [tr "Utstyr"]
                   [tr "Sjøbasen"]
                   [tr "Status" [:r.status]]
                   [tr "Mine innlegg" [:r.personal-articlelist]]]]}]

        [h2
         {:class (-> color-map :content :h2)}
         [:div.hover:underline.cursor-default.uppercase "Siste saker"]
         {:group [gr
                  {:class (-> color-map :content :fg)}
                  [tr "5 tips til en overnatting ved Nøklevann"]
                  [tr "Er ville dyr i skogen farlige?"]
                  [tr "Barn i båt"]]}]

        [h2
         {:class (-> color-map :content :h2)}
         [:div.hover:underline.cursor-default.uppercase "Kurs"]
         {:group [gr
                  {:class (-> color-map :content :fg)}
                  [tr "Påmeldingskurs"]
                  [tr "Livredning"]]}]]
    :b [:div '[eykt-data color-map]]

    [:div "?"]))

;region data

(def content-tab-data
  {:a {:text "Innhold"
       :data nil}
   :b {:text "Økter"}
   :c {:text "Ekstra"
       :data {:display-name "Lord Blimey"}
       :uid  "second-fake-user"}})

(defn content-below-fn
  "docstring"
  [arglist]
  [:div.text-xs
   '[torv.pages.calendar.views/summary-proper
     {:base (torv.pages.calendar/routine @(db/on-value-reaction {:path ["calendar"]}))
      :data (torv.pages.calendar/expand-date-range)}]])

(def barchart-panel-data
  {:a {:text "Config"
       :data nil}
   :b {:text "B"
       :data {:display-name "Fakey Usery"}
       :uid  "sample-uid-fake-user"}
   :c {:text "C"
       :data {:display-name "Lord Blimey"}
       :uid  "second-fake-user"}
   :d {:text "Extra"
       :data {:display-name "Chris"}
       :uid  "CHRIS"}})

(def brygge-panel-data
  {:a {:text "A"}
   :b {:text "B"}
   :c {:text "C"}})

;endregion data