(ns booking.yearwheel
  (:require
    [lambdaisland.ornament :as o :refer [defstyled]]
    [booking.ico :as ico]
    [arco.react]
    [schpaa.style.ornament :as sc]
    [tick.core :as t]
    [times.api :as ta]
    [schpaa.time]
    [schpaa.style.button :as scb]
    [schpaa.style.input :as sci]
    [fork.re-frame :as fork]
    [db.core :as db]
    [schpaa.state]
    [schpaa.icon :as icon]
    [schpaa.style.dialog]
    [schpaa.markdown]
    [schpaa.style.hoc.toggles :as hoc.toggles]
    [booking.qrcode]
    [re-frame.core :as rf]
    [booking.data]
    [booking.yearwheel-testdata]
    [schpaa.style.hoc.buttons :as hoc.buttons]
    [booking.access]
    [booking.styles :as b]
    [booking.flextime :refer [flex-datetime]]
    [schpaa.debug :as l]
    [booking.common-widgets :as widgets]
    [reagent.core :as r]
    [schpaa.style.hoc.buttons :as button]))

(o/defstyled listitem :div
  [:& :p-1
   {;:background :#fff1
    :--thing     "var(--size-4)"
    :text-indent "calc(var(--thing) * -1)"
    :min-height  "var(--size-4)"}
   [:.deleted {:color           "var(--text0)"
               :text-decoration :line-through
               :opacity         0.3}]
   [:&:hover {:background    "var(--surface00)"
              :border-radius "var(--radius-1)"}]]
  ([{:keys [class on-click] :as attr} & children]
   ^{:on-click on-click}
   [:<> [:div {:class class} children]]))

(defn form-validation [e]
  (into {}
        (remove (comp nil? val)
                {:date (cond-> nil
                         (empty? (:date e)) ((fnil conj []) "mangler")
                         (and (not (empty? (:date e)))
                              (some #{(tick.alpha.interval/relation
                                        (t/date (:date e)) (t/date))} [:precedes :meets])) ((fnil conj []) "er i fortiden"))

                 :type (cond-> nil
                         (not (some? (:type e))) ((fnil conj []) "mangler"))})))

(defn addpost-form [{:keys [data type on-close on-save on-submit] :as context}]
  [sc/dialog-dropdown {:style {:paddingx "1rem"}}
   [fork/form {:initial-values    data
               :form-id           "addpost-form"
               :validation        form-validation
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit         (fn [{:keys [values]}]
                                    (on-submit values))}
    (fn form-fn [{:keys [errors form-id handle-submit dirty values set-values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/co {:class [ :space-y-8 :w-full]}
          [sc/col-space-4
           [sc/dialog-title "Ny aktivitet"]
           [booking.styles/co4 {:class [:px-4]}
            [sc/row-sc-g2-w
             [button/combobox
              "Kategori"

              #_[sci/combobox-example
               {}
               {:value     (values :type)
                :on-change #(tap> %)
                :class     [:w-auto]}]
              #_{:value     (values :type)
                            :on-change #(tap> %)
                            :class     [:w-auto]}]
             #_[sci/combobox (conj props {:people       sci/people
                                          :person-by-id #(zipmap (map :id sci/people) sci/people)}) :kind? [:w-56] "Kategori" :type]
             [sci/input props :date [:w-40] "Dato" :date]]
            [sci/input props :text {:class [:w-full]} "Beskrivelse" :tldr]
            [sci/textarea props :text {:class [:w-full]} "Innhold (markdown)" :content]]]

          [l/pre errors]
          [sc/row-ec
           [button/just-caption {:type     "button"
                                 :class [:normal]
                                 :on-click #(on-close)} "Avbryt"]
           [button/just-caption {:disabled (or (not (empty? errors)) (not changed?))
                                 :type     "submit"
                                 :class [:normal]
                                 :on-click #(on-save)} "Lagre"]]]]))]])

(defn submit-fn [{:keys [date content id type tldr] :as values}]
  (if id
    ;update
    (let [item {:date    (when date (str (t/date date)))
                :type    type
                :tldr    tldr
                :updated (str (t/now))
                :content content}]
      (db/database-set {:path ["yearwheel" (name id)] :value item}))

    ;push
    (let [item {:date    (when date (str (t/date date)))
                :type    type
                :tldr    tldr
                :created (str (t/now))
                :content content}]
      (db/database-push {:path ["yearwheel"] :value item}))))

(defn edit-event [m]
  (schpaa.style.dialog/open-dialog-newevent
    {:form      #(addpost-form %)
     :data      m
     :on-submit submit-fn}))

;; panels

(defn headline-plugin []
  [[button/just-icon {:class [:large]
                      :on-click #(edit-event nil)} ico/plus]
   [button/just-icon {:class [:large]} ico/trash]]
  #_[[:div.h-12 [sc/text2 "Stuff"]]
   [button/just-icon {:class [:large :cta]} ico/check]
   [:div.h-12 [sc/text1 "Stuff"]]])

(defn always-panel
  ([]
   (always-panel false))
  ([modify?]
   [sc/row-center
    (when modify?
      [:div.relative.cursor-default
       [sc/col-space-2
        (hoc.buttons/just-icon {:class    [:cta :large]
                                :on-click #(edit-event nil)}
                               ico/plus)
        [sc/small2 "Ny aktivitet"]]])


    #_[hoc.buttons/pill
       {:class    [:regular :pad-right]
        :on-click #(rf/dispatch [:lab/qr-code-for-current-page])}
       (hoc.buttons/icon-and-caption ico/qrcode "QR-kode")]
    #_[hoc.buttons/pill
       {:on-click #(js/alert "wat")
        :class    [:regular :pad-right]
        :disabled true}
       (hoc.buttons/icon-and-caption ico/nullstill "Nullstill")]]))

(defonce settings (r/atom {}))

(defn panel
  ([]
   (panel false))
  ([modify?]
   [sc/col-space-8
    [sc/row-sc-g2-w
     [widgets/auto-link nil :r.oversikt.styret]
     [widgets/auto-link nil :r.oversikt.organisasjon]]

    [sc/row-sc-g2-w
     (when modify? [hoc.toggles/switch-local (r/cursor settings [:yearwheel/show-editing]) "Rediger"])
     (when modify? [hoc.toggles/switch-local (r/cursor settings [:yearwheel/show-deleted]) "Vis Slettede"])
     [hoc.toggles/switch-local (r/cursor settings [:yearwheel/show-content]) "Vis innhold"]]]))

;;

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(o/defstyled line :li
  {:--thing      "var(--size-4)"
   :list-style   [:outside :none :disc]
   :padding-left "var(--thing)"
   :text-indent  "calc(var(--thing) * -1)"
   :color        "var(--text2)"
   :line-height  "var(--font-lineheight-5)"})

(o/defstyled strong :span
  [:& {;:display :inline-block
       :color "var(--text1)"}])

(o/defstyled weak :span
  {;:display :inline-block
   :text-indent 0
   :color       "var(--text2)"
   :font-size   "var(--font-size-1)"
   :font-weight "var(--font-weight-3)"})

(defn- listitem-softwrap [can-edit? {:keys [id date content tldr created deleted type] :as m}]
  (let [show-editing @(r/cursor settings [:yearwheel/show-editing])
        show-content (r/cursor settings [:yearwheel/show-content])]
    [listitem {:class     [(when deleted :deleted)]
               :-on-click #(edit-event m)}
     [:div
      {:style {:display               :grid
               :grid-template-columns "min-content min-content 1fr"}}

      (if (and can-edit? show-editing)
        [:<>
         [:div.pr-3.pl-1 (widgets/trashcan (fn [id] (db/database-update
                                                      {:path  ["yearwheel" (name id)]
                                                       :value {:deleted (not deleted)}})) m)]
         [:div.pr-3 (widgets/edit {} (fn [m] (edit-event m)) m)]]
        [:<>
         [:div]
         [:div]])

      (into [line {:style {:displayd :inline-block}}]
            (interpose [:span ", "]
                       (remove nil?
                               [(when date
                                  (weak (str "uke " (ta/week-number (t/date date)))))
                                (when date
                                  (booking.flextime/flex-datetime date (fn [format content]
                                                                         (if (= :text format)
                                                                           [sc/datetimelink content]
                                                                           [sc/datetimelink (ta/date-format-sans-year content)]))))
                                (when type
                                  (strong (->> type (get sci/person-by-id) :name)))
                                (when tldr
                                  tldr)
                                (when (and content @show-content)
                                  (-> (schpaa.markdown/md->html content)
                                      (sc/markdown)))])))]]))

(defn get-all-events [show-deleted]
  (let [xf (comp (if show-deleted
                   identity
                   (remove (fn [[_k v]] (:deleted v))))
                 (filter (fn [[_k v]] (when (:date v)
                                        (and
                                          (t/<= (t/date (t/now)) (t/date (:date v)))
                                          (t/<= (t/year (t/now))
                                                (t/year (t/date (:date v))))))))
                 (map (fn [[k v]] [(name k) (assoc v :type-text (:name (get sci/person-by-id (:type v))))])))]
    (transduce xf conj [] @(db/on-value-reaction {:path ["yearwheel"]}))))

(defn render [r]
  (let [show-deleted (r/cursor settings [:yearwheel/show-deleted])
        data (get-all-events @show-deleted)
        users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        modify? (booking.access/can-modify? r users-access-tokens)]
    (into [sc/col-space-4 {:class [:w-full :overflow-x-auto]}]
          (for [[g data] (sort-by first < (group-by (comp #(if % (t/year %) (t/year (t/now)))
                                                          #(some-> % t/date)
                                                          :date
                                                          second)
                                                    data))]
            [sc/col-space-4
             [sc/hero (str "'" (subs (str (t/int g)) 2 4))]
             (into [:ol.space-y-1]
                   (concat
                     (for [[id data] (sort-by (comp :content last) < (remove (comp :date last) data))]
                       (listitem-softwrap modify? (assoc data :id id)))
                     (for [[id data] (sort-by (comp :date last) < (filter (comp :date last) data))]
                       (listitem-softwrap modify? (assoc data :id id)))))]))))

(comment
  (get-all-events))

;; feed

(defn yearwheel-feed []
  (let [data (take 5 (sort-by (comp :date second) < (booking.yearwheel/get-all-events false)))]
    [sc/col-space-4
     {:style {:padding-left "24px"}}
     (when (seq data)
       [:ol
        (for [[_id {:keys [date type tldr]}] data]
          [line [:div (interpose [:span ", "]
                                 (remove nil? [(when date
                                                 [:span " "
                                                  [flex-datetime date
                                                   (fn [type d]
                                                     (if (= :date type)
                                                       [sc/datetimelink {:style {:text-decoration :none}} (ta/date-format-sans-year d)]
                                                       [sc/datetimelink d]))]])
                                               (when type
                                                 (strong [sc/text-inline (->> type (get sci/person-by-id) :name)]))
                                               (when tldr
                                                 (weak tldr))]))]])])
     #_[sc/text1 "Se flere planlagte hendelser i " (sc/header-accomp-link {:href (kee-frame.core/path-for [:r.yearwheel])} "årshjulet")]]))
