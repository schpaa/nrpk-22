(ns eykt.content.pages
  (:require [shadow.resource :refer [inline]]
            [schpaa.markdown :refer [md->html]]
            [kee-frame.core :as k]
            [booking.hoc :as hoc]
            [re-frame.core :as rf]
            [schpaa.style :as st]
            [db.core :as db]
            [db.signin]
            [user.views]
            [times.api :as ta]
            [eykt.calendar.views]
            [eykt.calendar.core]
            [schpaa.components.views :as views :refer [rounded-view]]
            [schpaa.debug :as l]
            [eykt.content.oppsett]
            [eykt.calendar.fiddle]
            [schpaa.button :as bu]
            [schpaa.components.fields :as fields]
            [reagent.core :as r]
            [tick.core :as t]
            [eykt.calendar.actions :as actions]
            [eykt.content.mine-vakter :as content.mine-vakter]
            [eykt.content.uke-kalender :as content.uke-kalender]
            [schpaa.modal.readymade :as readymade]
            [eykt.content.rapport-side]
            [eykt.content.rapport-side :refer [top-bottom-view]]
            [nrpk.hov]
            [booking.views]
            [clojure.set :as set])
  (:import [goog.async Debouncer]))

(defn new-designed-content [{:keys [desktop?] :as m}]
  [:div
   {:class [:prose :prose-stone :dark:prose-invert :prose-h2:mb-2
            :prose-headings:font-black
            :prose-headings:text-alt
            :prose-h1:text-2xl
            :prose-h2:text-xl
            :prose-h3:text-lg
            :antialiased
            ;:md:mr-24
            ;:prose-h2:bg-alt
            ;"prose-headings:text-black/50"
            :prose-p:font-serif
            :prose-li:font-sans
            "prose-li:text-black/50"
            ;"prose-li:mr-8"
            "prose-li:italic"]}

   (md->html (inline "./about.md"))])

(defn register-back "to :active-front" [page]
  (rf/dispatch [:app/register-entry :active-back page]))

(defn render-back-tabbar []
  [:div.sticky.top-16.z-100.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.user "Om meg" register-back :icon :user]
    [:r.mine-vakter "Mine vakter" register-back :icon :chat-bubble]
    [:r.debug "Feilsøking" register-back :icon :eye]]])

(defn user [r]
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])]

    (if-not @user-auth
      [:div.p-4.max-w-md.mx-auto
       [rounded-view {:float 1} [db.signin/login]]]
      [:<>

       (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :surface)]
         [:div.w-full
          {:class bg}
          [:div.p-4.max-w-md.mx-auto
           [user.views/userstatus-form
            {:user-auth @user-auth
             :name      (:display-name @user-auth)}]]])

       [render-back-tabbar]

       [k/case-route (fn [route] (-> route :data :name))
        :r.user
        [user.views/my-info]

        :r.mine-vakter
        [content.mine-vakter/mine-vakter {:uid (:uid @user-auth)}]

        :r.debug
        (top-bottom-view
          [:div.z-100 [hoc/debug]]
          (let [{:keys [fg fg- br p p-]} (st/fbg' :tabbar)]
            (schpaa.components.views/modern-checkbox'
              {:set-details #(schpaa.state/change :app/sample-options %)
               :get-details #(-> (schpaa.state/listen :app/sample-options) deref)}
              (fn [checkbox]
                [:div.flex.items-center.justify-start.gap-4.w-full.px-4.h-12
                 checkbox
                 [:div {:class (concat fg p)} "Sample options here"]]))))

        [:div @route]]])))

(defn register-front "to :active-front" [page]
  (rf/dispatch [:app/register-entry :active-front page]))

(defn render-front-tabbar []
  [:div.sticky.top-16.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.forsiden "Vaktrapport" register-front :icon :document]
    [:r.kalender "Kalender" register-front :icon :calendar]
    [:r.annet "Lister" register-front :icon :list]]])

(rf/reg-sub :vakt/vis-inne :-> :vis-inne)
(rf/reg-sub :vakt/vis-ute :-> :vis-ute)

(rf/reg-event-db :vakt/vis-ute (fn [db [_ arg]] (assoc db :vis-ute arg)))
(rf/reg-event-db :vakt/vis-inne (fn [db [_ arg]] (assoc db :vis-inne arg)))

(defn tester [searchfield]
  (let [p (try
            (re-pattern (str "(?i)" searchfield))
            (catch js/SyntaxError x nil))]
    (fn [e]
      (and e p (or (some? (re-find p (or (:navn e " ") "")))
                   #_(and (:telefon e) (some? (re-find p (or (:telefon e " ") ""))))
                   #_(some? (re-find p (or (:epost e " ") ""))))))))

(defn debounce [f interval]
  (let [dbnc (Debouncer. f interval)]
    ;; We use apply here to support functions of various arities
    (fn [& args] (.apply (.-fire dbnc) dbnc (to-array args)))))

(defn std-person-listitem [selection v]
  (let [selected? (some #{(:uid v)} @selection)
        {:keys [bg bg- bg+ fg p- fg-]} (st/fbg' (if selected?
                                                  :listitem-button-selected
                                                  :listitem-button-unselected))]
    [:div.flex.w-full.gap-px.xs:pr-4.pr-2
     {:class (concat bg fg)}
     [nrpk.hov/open-user-details (:uid v)]
     [:div
      (when selected?
        (bu/listitem-button-small-clear {:on-click  #(do
                                                       (readymade/popup {:dialog-type :error
                                                                         :content     (:navn v)})
                                                       (swap! selection set/difference #{(:uid v)}))
                                         :color-map (assoc (st/fbg' :listitem-button-selected)
                                                      :bg [:bg-gray-700]
                                                      :fg [:text-gray-200]
                                                      :br [:border-gray-700 :border-2 :ps-2])} :cross-out))]
     [:div.h-12.flex.items-center.xs:px-4.px-2.grow.truncate
      [:div.flex.flex-col.space-y-0
       [:div.grow.truncate.leading-normal (:navn v)]
       [:div.flex.gap-4.grow.whitespace-nowrap
        [:div {:class (concat p- fg-)} (:telefon v)]]]]

     [:div
      (when-not selected?
        (bu/listitem-button-small-clear {:on-click  #(do
                                                       (readymade/popup {:dialog-type :message
                                                                         :content     (:navn v)})
                                                       (swap! selection set/union #{(:uid v)}))
                                         :color-map (assoc (st/fbg' :listitem-button-unselected)
                                                      :fg [:text-info-200]
                                                      :bg [:bg-info-700]
                                                      :br [:border-info-700 :border-2 :ps-2])} :checked))]]))

#_(defn std-person-listitem [v]
    (let [selected? (< 5 (rand-int 10))
          {:keys [bg bg- bg+ fg p- fg-]} (st/fbg' (if selected?
                                                    :listitem-button-selected
                                                    :listitem-button-unselected))]
      [:div.flex.w-full.gap-px.pr-px
       {:class (concat bg- fg)}
       [:div
        (when selected?
          (bu/listitem-button-small-clear {:color-map :listitem-button-selected} :cross-out))]
       [:div.h-12.flex.items-center.xs:px-4.px-2.grow.truncate
        [:div.flex.flex-col.space-y-0
         [:div.grow.truncate.leading-normal (:navn v)]
         [:div.flex.gap-4.grow.whitespace-nowrap
          [:div {:class (concat p- fg-)} (:medlem-fra-år v)]
          (if (:request-booking v)
            [:div {:class (concat p- fg-)} "Booking"]
            [:div {:class (concat p- fg-)} "Nope"])
          [:div {:class (concat p- fg-)} (:nøkkelnummer v)]
          [:div.w-16.truncate {:class (concat p- fg-)} (:uid v)]]]]
       [:div
        (when-not selected?
          (bu/listitem-button-small-clear {:color-map :listitem-button-unselected} :checked))]]))

(defonce selection (r/atom #{}))

(defn logo-type []
  [:div.text-center.inset-0
   {:class [
            :drop-shadow-md
            :leading-normal
            :font-oswald
            :font-normal
            :text-3xl
            :tracking-tight
            :dark:text-alt
            ;:bg-clip-text

            ;:bg-gradient-to-r :from-rose-400 :via-sky-600 :to-alt
            :xtext-transparent]}


   [:span "eykt"]
   [:span.text-gray-500 ".nrpk.no"]])

(defn common [r]
  (let [user-auth (rf/subscribe [::db/root-auth])
        v (db/on-value-reaction {:path ["report"]})]
    (fn [r]
      (if @user-auth
        [:<>


         (render-front-tabbar)
         [k/case-route (fn [route] (-> route :data :name))
          :r.forsiden
          [:div
           (if-let [v @v]
             [eykt.content.rapport-side/rapport-side]
             [eykt.content.rapport-side/no-content-message])]

          :r.annet
          (let [vis-inne (rf/subscribe [:vakt/vis-inne])
                vis-ute (rf/subscribe [:vakt/vis-ute])]
            (r/with-let [search-field (r/atom "")
                         search-field' (r/atom "")]
              (let [t (tester @search-field')
                    bouncer (debounce #(reset! search-field' %) 750)]
                (top-bottom-view
                  [:div
                   (let [{:keys [bg bg+ fg]} (st/fbg' :surface)]
                     [:div.sticky.top-28.h-16.justify-end.gap-4.grid.xs:px-4.px-2
                      {:style {:grid-template-columns "1fr 1fr"}
                       :class (concat bg+ fg)}
                      [:div]
                      #_[:div.grow (fields/select {:naked?      false
                                                   :class       []
                                                   :placeholder "søk"
                                                   :values      (fn [_])}
                                                  :items {"a" "first list"
                                                          "b" "second list"}
                                                  :default-text "velg liste"
                                                  :name :list)]
                      [:div.self-center.relative (fields/text
                                                   {:naked?        true
                                                    :class         (concat (fields/field-colors false)
                                                                           [:h-10 :bg-gray-100 :border-none :active:outline-none :active:ring-0
                                                                            :rounded-md])
                                                    :auto-focus    true
                                                    :placeholder   "søk"
                                                    :values        (fn [_] @search-field)
                                                    :handle-change #(let [v (-> % .-target .-value)]
                                                                      (bouncer v)
                                                                      (reset! search-field v))}
                                                   :name :search)
                       [:div.absolute.justify-self-center.inset-y-0.x-top-1.right-0
                        (bu/listitem-button-small-justclear {:disabled  (empty? @search-field)
                                                             :on-click  #(do
                                                                           (reset! search-field' nil)
                                                                           (reset! search-field nil))
                                                             :color-map (assoc (st/fbg' :listitem-button-unselected)
                                                                          ;:bg [:bg-alt]
                                                                          :bg []
                                                                          :bg- []
                                                                          :fg [:text-gray-700]
                                                                          :fg- [:text-gray-300]
                                                                          :br [:border-info-700x :border-2x])} :cross-out)]]])
                   (let [{:keys [bg fg]} (st/fbg' :void)
                         data (db/on-value-reaction {:path ["users"]})
                         data (into {} (comp
                                         (if @vis-inne (filter (fn [[k v]] (some #{(:uid v)} @selection))) (map identity))
                                         (filter (fn [[k v]]
                                                   (or
                                                     (some #{(:uid v)} @selection)
                                                     (t v))))) @data)
                         c (count data)]
                     [:div
                      {:class (concat bg fg)}
                      (let [{:keys [bg bg- bg+ fg p- fg-]} (st/fbg' :listitem)]
                        (into [:div.space-y-px]
                              (concat
                                [[:div.flex.flex-center.h-16 c]]
                                (for [[k v] (sort-by (comp :updated val) data)]
                                  ^{:key (str k)}
                                  [std-person-listitem selection v])
                                [[:div.flex.flex-center.h-16 c]])))])]
                  (let [{:keys [bg fg fg+ p p-]} (st/fbg' :tabbar)]
                    [:div.h-12.px-4.flex.items-center.justify-between.truncate
                     (schpaa.components.views/modern-checkbox'
                       {:set-details #(rf/dispatch [:vakt/vis-inne %])
                        :get-details #(-> @vis-inne)}
                       (fn [checkbox]
                         [:div.flex.items-center.gap-4
                          checkbox
                          [:div.space-y-0.truncate.shrink-0
                           [:div {:class (concat p fg+)} "Vis bare merkede"]
                           [:div.truncate.hidden.xs:block {:class (concat p- fg)} (if @vis-inne "Skjuler nå de markerte" "Viser nå alle")]]]))
                     (schpaa.components.views/modern-checkbox'
                       {:set-details #(rf/dispatch [:vakt/vis-ute %])
                        :get-details #(-> @vis-ute)}
                       (fn [checkbox]
                         [:div.flex.items-center.gap-4
                          [:div.space-y-0.truncate.shrink-0
                           [:div.text-right {:class (concat p fg+)} "Ute"]
                           [:div.truncate.hidden.xs:block {:class (concat p- fg)} (if @vis-ute "Bare de som er ute" "Viser alle")]]
                          checkbox]))])))))


          :r.kalender
          (let [{:keys [bg]} (st/fbg' :form)]
            [:div
             {:class bg}
             [:div.space-y-px.flex.flex-col
              {:style {:min-height "calc(100vh - 7rem)"}}
              (if (seq eykt.calendar.core/rules')
                [:div.flex-1
                 {:class bg}
                 ;[l/ppre @user-auth]
                 (when @user-auth
                   [content.uke-kalender/uke-kalender @user-auth])]
                '[empty-list-message "Booking-listen er tom"])
              [booking.views/last-bookings-footer {}]]])

          #_[:div.min-h-screen
             [content.uke-kalender/uke-kalender {:uid uid}]
             [booking.views/last-bookings-footer {}]]]]
        (let [{:keys [bg bg+ bg- fg]} (st/fbg' :surface)]

          [:<>
           [:div.px-4.pt-16x.space-y-8.relative.xs:hidden
            {:style {:min-height "calc(100vh - 3rem)"}
             :class (concat fg bg)}
            [:div.max-w-xs.mx-auto.opacity-75
             [:div.group.transition.space-y-2.sticky.top-12.pt-16.-mt-4
              {:class bg}
              [:div.flex.flex-center
               [:div.relative.w-24.h-24
                [:div.absolute.rounded-full.-inset-1.blur
                 {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                          :sgroup-hover:-inset-1 :duration-500]}]
                [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]
              (logo-type)]

             [:div.prose.max-w-xs.mx-auto
              {:class [:dark:prose-invert
                       :prose-h3:font-bold
                       :prose-h3:text-base
                       :prose-p:pt-0
                       :prose-p:pb-3
                       :prose-p:my-0
                       :prose-p:leading-relaxed
                       :prose-blockquote:text-gray-500
                       ;:prose-blockquote:text-gray-400
                       :prose-blockquote:pb-1
                       ;:prose-blockquote:py-0
                       :prose-blockquote:pl-4
                       :prose-blockquote:m-2
                       ;:prose-blockquote:-ml-4
                       :prose-blockquote:text-sm
                       :prose-blockquote:font-light
                       :prose-blockquote:leading-relaxed
                       :prose-blockquote:font-serif
                       :prose-blockquote:border-l-2
                       :prose-blockquote:border-alt
                       :prose-blockquote:border-dotted
                       :prose-stone]}
              (schpaa.markdown/md->html (shadow.resource/inline "./frontpage.md"))]]]
           [:div.px-4.pt-16.space-y-8.relative.hidden.xs:block
            {:style {:min-height "calc(100vh - 4rem)"}
             :class (concat fg bg)}

            [:div.max-w-md.mx-auto

             [:div.grid.gap-x-10.max-w-md.mx-auto.space-y-8
              {:style {:grid-template-columns "min-content 1fr"}}

              [:div.col-span-2
               (-> "./frontpage1.md"
                   inline
                   schpaa.markdown/md->html
                   st/prose-markdown-styles)]

              [:div.group.transition.space-y-2.self-center
               {:class bg}
               [:div.flex.flex-center
                [:div.relative.w-24.h-24
                 [:div.absolute.rounded-full.-inset-2.blur
                  {:class [:opacity-75 :bg-gradient-to-r :from-alt :to-sky-600
                           :sgroup-hover:-inset-1 :duration-500]}]
                 [:div.relative [:img.object-cover {:src "/img/logo-n.png"}]]]]
               (logo-type)]

              [:div.mx-auto.col-span-1.self-center
               (-> "./frontpage2.md"
                   inline
                   schpaa.markdown/md->html
                   st/prose-markdown-styles)]]]]])))))

(comment
  (comment
    (do
      (let [rules (group-by :group eykt.calendar.core/rules')
            lookup (fn [x] (reduce (fn [a e]
                                     (let [f (fn [itm]
                                               (t/hours (t/duration (tick.alpha.interval/new-interval
                                                                      (t/time (:starttime itm))
                                                                      (t/time (:endtime itm))))))]
                                       (assoc a (str (:starttime e)) (f e)))) {} x))
            uid :Ri0icn4bbffkwB3sQ1NWyTxoGmo1
            data (filter (fn [[k v]] (contains? v (keyword uid))) @(db/on-value-reaction {:path ["calendar"]}))
            straightened-rules (fn [a [k v]]
                                 (if (< 1 (count (seq v)))
                                   (reduce (fn [a [k' v]] (conj a [k k' v])) a v)
                                   (conj a (vec (flatten [k (first v)])))))
            prep-data (into {} (map (fn [[k v]] [k (reduce straightened-rules [] (first (vals v)))]) data))
            sum (reduce (fn [a [date rule-refs]]
                          (+ a (reduce (fn [a [group starttime clicked-time]]
                                         (tap> (name starttime))
                                         (+ a (get (lookup (:times (first (get rules group))))
                                                   (name starttime)))) 0 rule-refs)))
                        0 prep-data)]
        sum
        #_[prep-data
           rules]
        #_(lookup [{:starttime #time/time"11:00",
                    :endtime   #time/time"14:00",
                    :slots     3}
                   {:starttime #time/time"14:00",
                    :endtime   #time/time"17:00",
                    :slots     3}]))))

  (comment
    (comment
      ;intent target
      [[:c :06:00 "2022-02-01T22:02:37.320Z"]
       [:z2 :11:00 "2022-02-01T20:59:31.963Z"]
       [:z2 :14:00 "2022-02-01T20:59:33.875Z"]])

    (reduce (fn [a [k v]]
              (if (< 1 (count (seq v)))
                (reduce (fn [a [k' v]] (conj a [k [k' v]])) a v)
                (conj a [k (first v)])))
            []
            {:c  {:06:00 "2022-02-01T22:02:37.320Z"},
             :z3 {:11:00 "2022-02-01T20:59:31.963Z"
                  :12:00 "2022-02-01T20:59:31.963Z",
                  :14:00 "2022-02-01T20:59:33.875Z"}
             :z2 {:11:00 "2022-02-01T20:59:31.963Z"
                  :12:00 "2022-02-01T20:59:31.963Z",
                  :14:00 "2022-02-01T20:59:33.875Z"}}))

  (comment
    (let [data {:a  {:17:00 "2022-02-01T19:55:03.368Z"},
                :z2 {:11:00 "2022-02-01T19:54:57.755Z", :14:00 "2022-02-01T19:54:58.800Z"}}]
      (map (comp keys val) data)))

  (comment
    (let [data {:a {:17:00 "2022-02-01T19:55:03.368Z"},
                :z2
                {:11:00 "2022-02-01T19:54:57.755Z",
                 :14:00 "2022-02-01T19:54:58.800Z"}}]
      (select-keys data [:a :z2])))

  (comment
    (do
      (let [data {:2022-01-31
                  {:2Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:a
                    {:11:00 "2022-02-01T17:30:39.829Z",
                     :14:00 "2022-02-01T17:27:44.193Z"}},
                   :Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:a
                    {:11:00 "2022-02-01T17:33:19.556Z",
                     :14:00 "2022-02-01T17:33:21.289Z",
                     :17:00 "2022-02-01T17:33:22.200Z"},
                    :b {:07:00 "2022-02-01T17:32:48.789Z"},
                    :c {:06:00 "2022-02-01T17:31:51.795Z"}}},
                  :2022-02-01
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:37:52.236Z"}}},
                  :2022-02-02
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:37:53.169Z"}}},
                  :2022-02-03
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:37:53.731Z"}}},
                  :2022-02-04
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:37:54.252Z"}}},
                  :2022-02-05
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:37:55.094Z"}}},
                  :2022-02-06
                  {:2Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:b {:07:00 "2022-02-01T17:22:57.149Z"},
                    :c {:06:00 "2022-02-01T17:23:02.886Z"}},
                   :Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:a
                    {:11:00 "2022-02-01T17:32:54.334Z",
                     :14:00 "2022-02-01T17:09:25.267Z",
                     :17:00 "2022-02-01T17:09:23.783Z"}}},
                  :2022-02-07
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:b {:07:00 "2022-02-01T17:10:18.401Z"},
                    :c {:06:00 "2022-02-01T17:10:17.614Z"}}},
                  :2022-02-10
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:38:04.632Z"}}},
                  :2022-02-11
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:38:04.975Z"}}},
                  :2022-02-12
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:c {:06:00 "2022-02-01T17:38:05.525Z"}}},
                  :2022-03-22
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:46:53.756Z"}}},
                  :2022-03-23
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:46:54.274Z"}}},
                  :2022-03-26
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z2
                    {:11:00 "2022-02-01T17:47:45.112Z",
                     :14:00 "2022-02-01T17:47:02.632Z"}}},
                  :2022-03-27
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z2 {:14:00 "2022-02-01T17:47:01.395Z"}}},
                  :2022-05-03
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:48:38.301Z"}}},
                  :2022-05-04
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:48:38.841Z"}}},
                  :2022-05-10
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:48:35.951Z"}}},
                  :2022-05-11
                  {:Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                   {:z1 {:18:00 "2022-02-01T17:48:36.287Z"}}},
                  :Ri0icn4bbffkwB3sQ1NWyTxoGmo1
                  {:ONSDAGSGRUPPA {:2022-02-02T10:00 "2022-01-31T13:55:09.679Z"},
                   :a             {:11:00 "2022-02-01T13:56:17.780Z"},
                   :helg
                   {:2022-01-30T11:00 "2022-01-31T14:04:38.469Z",
                    :2022-01-30T14:00 "2022-01-31T14:01:59.134Z",
                    :2022-02-05T11:00 "2022-01-31T13:49:19.219Z",
                    :2022-02-05T14:00 "2022-01-31T13:49:19.870Z"},
                   :uke
                   {:2022-02-01T18:00 "2022-01-31T14:19:15.758Z",
                    :2022-02-03T18:00 "2022-01-31T13:49:16.619Z",
                    :2022-02-09T18:00 "2022-01-31T14:19:09.402Z",
                    :2022-02-10T18:00 "2022-01-31T14:19:11.146Z"}}}]
        (filter (fn [[k v]] (contains? v :Ri0icn4bbffkwB3sQ1NWyTxoGmo1)) data))))
  (comment
    (do
      (let [data {:Ri0...
                  {:a {:11:00 "1",
                       :14:00 "2",
                       :17:00 "3"},
                   :b {:07:00 "4"},
                   :c {:06:00 "5"}},
                  :b-person
                  {:a {:11:00 "6",
                       :14:00 "7"}
                   :c {:06:00 "8"}},
                  :uid
                  {:a {:11:00 "9",
                       :14:00 "10",
                       :17:00 "11"},
                   :c {:06:00 "12"}}}

            want {:a {:11:00 {:Ri0...   "..."
                              :b-person "..."
                              :uid      "..."}
                      :14:00 {:Ri0      "..."
                              :b-person "..."
                              :uid      "..."}
                      :17:00 {:Ri0... "..."
                              :uid    "..."}}
                  :b {:07:00 {:Ri0 "..."
                              :uid "..."}}
                  :c {:06:00 {:Ri0 "..."}}}
            #_(group-by (comp keys val) data)
            #_(reduce (fn [a [uid groups]]
                        (conj a (reduce (fn [a [group-name time-slots]]
                                          (assoc a group-name
                                                   (reduce (fn [a [time clicked-time]]
                                                             (update a time (fnil conj []) {uid clicked-time}))
                                                           a
                                                           time-slots)))
                                        {}
                                        groups)))
                      {}
                      data)

            f'' (fn [a' time-slots user-id]
                  (reduce (fn [a [time click-time]] (assoc a time (assoc a' user-id click-time))) {} time-slots))
            f' (fn [a' user-id groups]
                 (tap> a')
                 (reduce (fn [a [group-id time-slots]] (assoc a group-id (f'' {} time-slots user-id))) a' groups))]

        (reduce (fn [a [user-id groups]] (conj a (f' a user-id groups))) {} data)
        (defn routine
          "
          Takes input (from db) and dislocates the keys of the parent and the immediate child.
          Unsure where and how to use this, (if at all useful?)
          "
          [db-input]
          (loop [xs db-input
                 r {}]
            (if xs
              (let [[id vs] (first xs)]
                (recur (next xs)
                       (reduce-kv (fn [a k v] (update a k #(assoc % id v)))
                                  r vs)))
              r)))

        (let [source (routine data)]
          (reduce (fn [a group-id] (update a group-id routine)) source (keys source))
          #_(map (fn [[a e]] (conj e (routine e))) source)))))

  (comment
    (let [data {:a {:11:00 "2022-02-01T14:01:09.032Z",
                    :14:00 "2022-02-01T14:01:09.583Z",
                    :17:00 "2022-02-01T14:01:10.158Z"},
                :b {:07:00 "2022-02-01T14:01:08.301Z"},
                :c {:06:00 "2022-02-01T14:01:07.389Z"}}]
      (reduce (fn [a [group time-slots]] (assoc a group 1)) {} data))))



