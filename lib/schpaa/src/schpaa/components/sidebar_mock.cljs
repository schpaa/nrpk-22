(ns schpaa.components.sidebar-mock
  (:require [schpaa.style :as s]
            [re-frame.core :as rf]
            [schpaa.debug :as l]
            [schpaa.markdown :as markdown]
            [shadow.resource :refer [inline]]
            [schpaa.time]
            [times.api :as t]
            [tick.core :as t']))


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
  [:div.space-y-8.m-3

   [:div.space-y-4
    [:div.px-2.-mx-2.rounded.shadow-sm.space-y-4.text-xl.font-thin
     ;{:class "bg-black/20"}
     ;[:div {:class "text-white/50"} "Temperatur"]
     [:div.text-xl.flex.justify-between.gap-4

      [:div.flex.items-baseline.py-2.px-4.rounded.flex-grow
       {:class "bg-black/20"}
       [:div.flex.flex-col.space-y-2.items-center.flex-grow.slashed-zero
        [:div "vann"]
        [:div.text-4xl.font-bold.relative.filter.drop-shadow-md "0.2" [:sup "ºc"]]]]

      [:div.flex.items-baseline.py-2.px-4.rounded.flex-grow
       {:class "bg-black/20"}
       [:div.flex.flex-col.space-y-2.items-center.flex-grow
        [:div "luft"]
        [:div.text-4xl.font-bold.relativex.filter.drop-shadow-md "5.0" [:sup "ºc"]]]]]]

    [:div.truncate.flex.justify-between {:class "text-white/50"}
     (schpaa.time/y' (-> (t'/date "2021-11-11") (t'/at "11:01")))
     "-->"]

    [:div.space-y-2
     [:div.text-base
      {:class "text-white/50"}
      "Registrerte utlån i dag"]
     [:div.p-4.rounded.justify-around.flex
      {:class "bg-black/20"}
      [:div.text-4xl.font-bold "–"]
      [:div.text-4xl.font-bold "–"]
      [:div.text-4xl.font-bold "–"]
      [:div.text-4xl.font-bold "–"]]]]

   [:div.prose.overflow-y-auto
    {:class [:prose-h3:text-base
             :prose-h3:font-bold
             :prose-h3:uppercase
             :prose-p:leading-relaxed
             "prose-p:text-white/90"
             "prose-a:text-white/50"
             :prose-a:leading-snug
             :prose-a:text-lg
             :prose-p:m-0
             :prose-p:mb-2
             :prose-p:p-0

             :prose-ul:list-decimal
             :prose-ul:list-outside]}
    (markdown/md->html (inline "./list.md"))]])

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