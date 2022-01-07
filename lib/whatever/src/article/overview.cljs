(ns article.overview
  (:require [article.definitions :refer []]
            [schpaa.style :as s]
            [db.core :as db]
            [re-frame.core :as rf]))

(defmulti overview-view (fn [view-key _] view-key) :default :art/default-view)

(defmethod overview-view :art/default-view [_ {:keys [id]}  {:strs [summary ingress front-image timestamp] :as data}]
  [:div.xbg-gray-50.p-1.space-y-1
   {:on-click #(rf/dispatch [:app/navigate-to [:r.personal-article {:uid (:uid @(rf/subscribe [::db/user-auth])) :id id}]])}
   [:div.flex.items-baseline.gap-4.justify-between.w-full
    [:div {:class s/std-header-color} (or summary "no title")]
    [:div.text-xs {:class s/std-text-color}
     (schpaa.time/x timestamp)]]
   [:div.font-redacted.text-xs {:class s/std-text-color} id]
   [:div.font-redacted.text-lg.line-clamp-4
    {:class s/std-text-color}
    ingress]]
  #_[:div.text-base
     {:class [:hover:bg-gray-200 :transition :duration-200 :p-2]}
     [:div.flex.items-baseline.gap-4
      [:div.text-sm {:class s/std-text-color} (t/timestamp->local-datetime-str timestamp)]
      [:div.font-lora {:class s/std-header-color} summary]]
     [:div.font-redacted {:class s/std-text-color}  ingress]])

#_(defmethod overview-view :art/default-view [_ _ {:strs [summary ingress front-image] :as data}]
    [:div.row-span-2
     [:div.bg-gray-400.dark:bg-gray-900.aspect-w-4.aspect-h-3.rounded-sm
      (when front-image
        [:img.object-cover {:src front-image}])]
     [:div.text-base.py-1.line-clamp-1
      {:class (concat [:font-friz :font-medium]
                      s/std-header-color)}
      summary]
     [:div.line-clamp-3.font-redacted.text-lg
      {:class s/std-text-color}
      ingress]])

#_(defmethod overview-view :art/feature-view [_ _ {:strs [summary ingress front-image] :as data}]
    [:div.col-span-4.row-span-5
     [:div.bg-gray-300.dark:bg-gray-700.aspect-w-4.aspect-h-3.rounded-sm.relative
      (when front-image
        [:img.object-cover {:src front-image}])
      [:div
       [:div.absolute.inset-0.bg-gradient-to-t.from-black]
       [:div.block.absolute.bottom-2.left-2.inset-x-0.h-auto
        {:class (concat
                  [:font-friz-bold :text-3xl :br:text-4xl :mob:text-5xl]
                  [:filter :drop-shadow-md]
                  [:leading-relaxed :mr-20]
                  s/std-header-color-inverted)}
        summary]]]

     [:div.line-clamp-4.font-redacted.text-lg
      {:class s/std-text-color}
      ingress]])

(defmethod overview-view :art/text-only [_ {:keys [id]} {:strs [summary ingress front-image timestamp] :as data}]
  [:div.p-1.space-y-1
   {:class s/std-header-color
    :on-click #(rf/dispatch [:app/navigate-to [:r.public-article {:id id}]])}
   [:div.flex.items-baseline.gap-4.justify-between.w-full
    [:div.font-sans.text-base.uppercase.tracking-tight.antialiased {:class s/std-header-color} summary]
    [:div.text-xs {:class s/std-text-color}
     (try
       (schpaa.time/x timestamp)
       (catch js/Error e timestamp))]]
   [:div.text-xs id]
   [:div.font-redacted.text-lg.line-clamp-2
    {:class s/std-text-color}
    ingress]])

#_(defmethod overview-view :fancy-view [_ _ {:strs [summary ingress front-image  ] :as  data}]
    [:div.col-span-2.row-span-3
     [:div.bg-gray-400.dark:bg-gray-900.aspect-w-4.aspect-h-3.rounded-sm
      (when front-image
        [:img.object-cover {:src front-image}])]
     [:div.font-lora.text-base.br:text-base.py-1.line-clamp-1
      {:class (concat
                [:font-friz :font-medium]
                s/std-header-color)}
      summary]
     [:div.line-clamp-3.font-redacted.text-lg
      {:class s/std-text-color}
      ingress]])

#_(defmethod overview-view :art/embedded [_ _ {:strs [orientation contrast summary ingress front-image  ] :as  data}]
    [:div.col-span-full
     {:on-click #(rf/dispatch [:app/navigate-to [:r.personal-articlelist]])}
     [:div.bg-gray-300.dark:bg-gray-700.aspect-w-4.aspect-h-3.rounded-sm.relative.overflow-hidden
      (when front-image
        [:img.object-cover
         {:class [:opacity-80
                  :transform
                  :ease-in-out
                  :transition
                  :hover:delay-200
                  ;:hover:rotate-3
                  :hover:opacity-100
                  :hover:scale-101
                  :duration-1000]
          :src front-image}])
      [:div.pointer-events-none
       ;[:div.absolute.inset-0.bg-gradient-to-t.from-blue-700]
       {:class (if (= "black" contrast)
                 s/std-header-color-inverted
                 s/std-header-color)}
       [:div.block.absolute.bottom-8.left-8.right-8.h-auto.space-y-4
        {:class (concat
                  [:font-sans :text-4xl]
                  [:filter :drop-shadow-md])}

        [:div
         {:class "w-2/3"}
         [:div.leading-12  summary]]

        [:div.line-clamp-2.font-sans.text-xs.font-normal.tracking-wide

         ingress]]]]])

#_(defmethod overview-view :art/portrait [_ _ {:strs [orientation contrast summary ingress front-image  ] :as  data}]
    [:div ;.col-span-full.col-row-3x
     {:class s/hdr-color
      :on-click #(rf/dispatch [:app/navigate-to [:r.personal-articlelist]])}]
    [:div.col-span-full.grid.grid-cols-2
     {:class s/sidebar-color}
     [:div
      {:class []}
      (when front-image
        [:img.object-cover
         {:class (concat

                   ;[:aspect-w-3 :aspect-h-4]
                   [:opacity-80
                    :transform
                    :ease-in-out
                    :transition
                    :hover:delay-200
                    :hover:opacity-100
                    :hover:scale-101
                    :duration-1000])
          :src   front-image}])]

     [:div.grid.grid-rows-2.gap-2
      [:div.mx-4.self-end {:class [:leading-snug :text-3xl]} "Her er noen linjer som tar litt plass"]
      [:div.mx-4.self-start {:class [:leading-relaxed :text-base]} "Her er underteksten som bruker mindre plass med mindre skrift"]]
     #_[:div.pointer-events-none.m-4
        ;[:div.absolute.inset-0.bg-gradient-to-t.from-blue-700]
        {:class (concat [:self-center]
                        (if (= "black" contrast)
                          s/std-header-color
                          s/std-header-color-inverted))}
        [:div.space-y-4 ;.block.absolutex.bottom-8.left-8.right-8.h-auto.space-y-4
         {:class (concat
                   [:font-sans :text-4xl]
                   [:filter :drop-shadow-md])}

         [:div
          ;{:class "w-2/3"}
          [:div.leading-12 summary]]

         [:div.line-clamp-2.font-sans.text-xs.font-normal.tracking-wide

          ingress]]]])
