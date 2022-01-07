(ns article.core
  (:require [article.definitions :refer [register-view! map-current-view registry]]
            [article.overview :refer [overview-view]]
            [article.helpers :refer [open-close]]
            [article.currentview :refer [current-view]]
            [times.api :as t]
            [schpaa.debug :as l]
            [schpaa.icon :as icon]
            [schpaa.state :as state]
            [schpaa.markdown :as markdown]
            [db.core :as db]
            [re-frame.core :as rf]
            [schpaa.time :refer [flex-date]]
            [torv.state]
            [schpaa.style :as s]))

;region sample-article

(register-view!
  :art/feature-view
  (fn [{:strs [feature-article]}] feature-article))

(register-view!
  :short-open-summary
  (fn [{:strs [kind]}] (= kind "a")))

(register-view!
  :article-with-columns
  (fn [{:keys [columns]}] columns))

(register-view!
  :expander
  (fn [{:strs [tag]}] (some? tag)))

(register-view!
  :expander-s
  (fn [{:strs [tag]}] false #_(some? tag)))

;endregion

(def f (comp (partial apply current-view)
             (juxt map-current-view identity)))

(defn doc-header
  "intent `fsm-send-fn` to avoid coupling"
  [{:keys [path document fsm-send-fn create-sample remove keep]}]
  [:div.text-xs
   [:div.flex.justify-between.gap-2.flex-wrap.px-2
    [:div.flex.gap-2
     [:button.btn.btn-emb {:on-click #(fsm-send-fn :e.edit-document {:path path :document document})} "edit"]
     [:button.btn.btn-emb {:on-click create-sample} "create sample"]]
    [:div.flex.gap-2
     (if (get document "active")
       [:button.btn.btn-emb {:on-click remove} "remove"]
       [:button.btn.btn-emb {:on-click keep} "keep"])
     #_[:button.btn.btn-emb {:disabled true} "stash"]]]

   [:div.flex.justify-between.items-center.px-4.pt-2
    (when-let [tm (get document "timestamp")]
      (let [tm' (t/ms->local-time (.toDate tm))]
        (flex-date {} tm' (fn [_i] (t/relative-local-time
                                     {:past-prefix           "endret for"
                                      :progressive-rounding? true}
                                     (.toDate tm))))))
    (last path)]])

(defn render
  "using a transducer to fill the client"
  [{:keys [base-path fsm-send-fn class-fn data+id]}]
  (let [u (rf/subscribe [::db/user-auth])]
    (->> data+id
         (into [:div.space-y-1.xbg-gray-400.xpx-2.x-mx-2]
               (comp
                 (map (fn [{:keys [data id]}]
                        (let [{:strs [class-type]} data]
                          [:div.bg-gray-300x
                           #_(when @(rf/subscribe [:xxx :toggle-ui-2])
                               [doc-header
                                {:document      data          ;; note the name-change !
                                 :path          (conj base-path id)
                                 :fsm-send-fn   fsm-send-fn
                                 :create-sample #(db/firestore-add {:path  base-path
                                                                    :value {:summary (str "Title " id)
                                                                            :details (case (rand-int 2)
                                                                                       0 (str "#Header 1")
                                                                                       1 (str "###Header 2"))
                                                                            :tag     id
                                                                            :active  true}})
                                 :remove        #(db/firestore-set {:path  (conj base-path id)
                                                                    :value {:active false}})
                                 :keep          #(db/firestore-set {:path  (conj base-path id)
                                                                    :value {:active true}})}])
                           [:div
                            {:class (if @(rf/subscribe [:xxx :toggle-ui-2]) [:p-2])}
                            [current-view
                             (map-current-view data)
                             {:id            id
                              :summary-class [:text-3xl :font-thin :font-dosis]
                              :ingress-class [:text-xl :font-medium :font-inter]
                              :class         (concat
                                               [:rounded-sm]
                                               (if (ifn? class-fn)
                                                 (class-fn class-type)
                                                 []))}
                             data]]]))))))))

(def appearance
  [:div.grid.gap-x-4.py-2x.gap-y-8
   {:style {:grid-template-columns "repeat(auto-fit,minmax(8rem,1fr))"}}])

(defn article-overview [{:keys [base-path fsm-send-fn class-fn data+id]}]
  (->> data+id
       (into [:div.space-y-1.-mx-1]
             (comp (map (fn [{:keys [data id]}]
                          [overview-view
                           (map-current-view data)
                           {:id id}
                           data]))))))