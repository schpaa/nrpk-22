(ns article.currentview
  (:require [schpaa.debug :as l]
            [schpaa.state :as state]

            [article.helpers :refer [open-close tag by-line]]
            [db.core :as db]
            [re-frame.core :as rf]
            [schpaa.markdown :as markdown]))


(defmulti current-view (fn [view-key _] view-key) :default :default-view)

#_(defmethod current-view :default-view [_ {:keys [id]} data]
    [:div
     [:div.p-4.bg-danger.text-white "Warning: Default view " id]
     [l/ppre data]])

(defmethod current-view :default-view [_
                                       {:keys [class id ingress-class summary-class]}
                                       {:strs [timestamp summary ingress details indent
                                               front-image] :as sss :or {indent true}}]
  [:article.space-y-4

   [:div.relative.-mx-4
    [:img.border-none.bg-white
     {:class [:w-full "aspect-[3/2]" :object-cover]
      :alt   front-image
      :src   (or front-image "/img/brygge.jpeg")}]

    [:div.absolute.inset-0.bg-gradient-to-t;.hidden.mob:block
     {:class ["dark:from-black"
              "from-white/90"]}]

    [:div.absolute.bottom-0.left-0.p-4.dark:text-gray-400.text-black;.hidden.mob:block

     [:div.space-y-2
      [tag "Artikkel"]
      [:div.flex.items-center.justify-between.flex-shrink-0.gap-2
       {:class summary-class}
       summary]
      [:div
       {:class (concat
                       ingress-class)} ingress]]]]

   [:div.space-y-4
    {:class class}
    [:div.cursor-pointer.mob:hidden.block
     [:div.flex.items-center.justify-between.flex-shrink-0.gap-2
      {:class summary-class}
      summary]]
    ;[:div.xmob:hidden.xblock {:class ingress-class} ingress]

    [by-line (some-> @(rf/subscribe [::db/user-auth]) :display-name) timestamp]

    [:div.columns-3xs.gap-x-10.prose.max-w-none

     ;text-black.dark:text-gray-400
     #_[:div {:class [:first-letter:text-7xl
                      :first-letter:float-left]}]
     (markdown/md->html details)]]])


(defmethod current-view :default-view-2 [_
                                         {:keys [class id ingress-class summary-class]}
                                         {:strs [timestamp summary ingress details indent
                                                 front-image] :as sss :or {indent true}}]
  (let [user-id (some-> @(rf/subscribe [::db/user-auth]) :display-name)]
    [:article.space-y-4
     [:img.border-none.bg-white
      {:class [:w-full "aspect-[3/2]" :object-cover]
       :alt   front-image
       :src   (or front-image "/img/brygge.jpeg")}]
     [:div.space-y-4
      {:class class}
      [:div.cursor-pointer
       {:class    [:hover:underline]
        :on-click #(state/toggle id)}
       [:div.flex.items-center.justify-between.flex-shrink-0.gap-2
        {:class summary-class}
        summary]]
      [:div {:class ingress-class} ingress]

      [by-line user-id timestamp]
      [:div.columns-3xs.gap-x-10
       [:div.prose
        (markdown/md->html details)]]]]))

#_(defmethod current-view :article-with-columns [_ _ data]
    [:div.text-white.p-4.bg-orange-500 (:text data)])

#_(defmethod current-view :expander-s [_ {:keys [class id ingress-class summary-class]}
                                       {:strs [timestamp summary ingress details indent] :or {indent true}}]
    (let [*open? @(state/listen id)]
      [:article
       {:class class}
       [:details.select-none.space-y-4.px-2
        {:class (if *open? :py-4)
         :open  *open?}
        [:summary.cursor-pointer
         {:class    [:hover:underline]
          :on-click #(state/toggle id)}
         [:div.flex.items-center.justify-between.flex-shrink-0.gap-2
          {:class summary-class}
          summary
          (open-close *open?)]]
        [:div {:class ingress-class} ingress]
        [:div
         [:div.text-xs.items-start.inline-flex.flex-col.bg-gray-300.text-gray-700.p-1.rounded-sm
          [:div "Skrevet av " (some-> @(rf/subscribe [::db/user-auth]) :display-name)]
          (when-let [tm (some-> timestamp .toDate t/ms->local-time)]
            (flex-date {} tm (fn [_i] (t/relative-local-time
                                        {:past-prefix           "oppdatert for"
                                         :progressive-rounding? true}
                                        tm))))]]
        [:div {:style {:column-gap   "2rem"
                       :column-width "17rem"}} (markdown/md->html details)]]]))

#_(defmethod current-view :expander [k {:keys [id class ingress-class summary-class]}
                                     {:strs [summary ingress details indent timestamp] :or {indent true}}]
    (let [*open? @(state/listen id)]
      [:article
       {:class class}
       [:div.inline.bg-alt.pill.py-1 (str k)]
       [:details.select-none.space-y-4.p-2
        {:class (if *open? :py-2)
         :open  *open?}
        [:summary.cursor-pointer
         {:class    [:hover:underline]
          :on-click #(state/toggle id)}
         [:div.flex.items-center.justify-between.flex-shrink-0.gap-2
          {:class summary-class}
          (or summary "no summary")
          (open-close *open?)]]
        [:div {:class ingress-class} (or ingress "no ingress")]
        [:div
         [:div.text-xs.items-start.inline-flex.flex-col.bg-gray-300.text-gray-700.p-1.rounded-sm
          [:div "Skrevet av " (some-> @(rf/subscribe [::db/user-auth]) :display-name)]
          (when-let [tm (some-> timestamp .toDate t/ms->local-time)]
            (flex-date {} tm (fn [_i] (t/relative-local-time
                                        {:past-prefix           "oppdatert for"
                                         :progressive-rounding? true}
                                        tm))))]]
        [:div {:style {:column-gap   "2rem"
                       :column-width "17rem"}} (markdown/md->html (or details "no details"))]]]))


#_(defmethod current-view :short-open-summary [_ {:keys [id ingress-class]} {:strs [front-image summary ingress details] :as data}]
    [:div
     {:class [:bg-gray-100 :text-gray-500 :dark:bg-gray-800 :dark:text-gray-300]}
     [:div.p-4.space-y-4.max-w-xl.mx-auto

      (if false
        [:div.grid.grid-cols-1.br:grid-cols-2.gap-2
         [:div.aspect-w-3.aspect-h-4.w-full.bg-alt
          [:img {:class [:object-cover]
                 :src   "img/frontpage.jpg"}]]

         [:div.space-y-2
          {:class [:place-self-center]}
          [:div
           [:div.flex.justify-center.py-2.w-full.gap-2
            [:div.hollow-pill "overnatting"]]]
          [:div.font-play.text-4xl.tracking-tight.text-center.font-black summary]]]

        [:div.grid.grid-cols-1.relative
         [:div.aspect-w-3.aspect-h-2.w-full
          [:img {:class [:object-cover]
                 :src   front-image}]]

         [:div.absolute.inset-0.grid.text-black.border-black
          {:class [:place-content-center]}

          [:div.absolute.inset-0.bg-white.dark:bg-black.dark:bg-opacity-80.bg-opacity-80]

          [:div.text-black.border-black.z-20
           [:div.flex.justify-center.py-2
            [:div.hollow-pill {:class [:text-gray-800 :border-gray-800
                                       :dark:text-gray-100 :dark:border-gray-100]}
             "overnatting"]]
           [:div.font-play.text-4xl.tracking-tight.text-center.font-black.filter.drop-shadow-lg
            {:class [:dark:text-gray-100 :text-gray-800]}
            summary]]]])

      [:div.line-clamp-5
       [:div.font-lorax.font-redacted.leading-relaxed.text-base {:class ingress-class} ingress]]

      ;[:div.text-center.py-2.w-full.font-play.italic.opacity-30 "(artikkelen fortsetter...)"]

      [:div.xfont-lora.text-base.leading-relaxed.font-redacted
       {:style {:column-gap   "2rem"
                :column-width "14rem"}}
       details]]])