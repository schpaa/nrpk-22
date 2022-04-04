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
    [booking.flextime :refer [flex-datetime]]))

(o/defstyled listitem :div
  [:& :p-1
   {;:background :#fff1
    :min-height "var(--size-4)"}
   [:.deleted {:color           "var(--text0)"
               :text-decoration :line-through
               :opacity         0.3}]
   [:&:hover {:background    "var(--surface00)"
              :border-radius "var(--radius-1)"}]]
  ([{:keys [class on-click] :as attr} & children]
   ^{:on-click on-click}
   [:<> [:div {:class class} children]]))

(defn addpost-form [{:keys [data type on-close on-save on-submit] :as context}]
  [sc/dropdown-dialog
   [fork/form {:initial-values    data #_{:title   ""
                                          :type    type
                                          :date    nil
                                          :content ""}
               :form-id           "addpost-form"
               ;:validation       #(-> {:stuff2 ["for lite"]})
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit         (fn [{:keys [values]}]
                                    (on-submit values))}

    (defn form-fn [{:keys [form-id handle-submit dirty values set-values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col {:class [:space-y-8]}
          [sc/col-space-2                                   ;{:class [:space-y-2]}
           [sc/dialog-title "Ny aktivitet"]
           [sc/row-sc-g2-w
            [sci/combobox props :kind? [:w-56] "Kategori" :type]
            [sci/input props :date [:w-40] "Dato" :date]
            [sci/input props :text [:w-full] "TL;DR (too long, didn't read)" :tldr]
            [sci/textarea props :text {:class [:w-full]} "Beskrivelse (blog)" :content]]]

          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           #_[hoc.buttons/cta {:disabled false              ;(not changed?)
                               :style    {:min-width 0}
                               :type     "submit"
                               :on-click #(do
                                            (set-values {:id nil})
                                            (on-save))} (sc/icon ico/plusplus)]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"
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
    {:form      addpost-form
     :data      m
     :on-submit submit-fn}))

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)
   {:on-click #(db/database-update
                 {:path  ["yearwheel" (name k)]
                  :value {:deleted (not deleted)}})}
   [sc/icon-small
    {:style {:color "var(--text1)"}}
    (if deleted (icon/small :rotate-left) ico/trash)]])

(defn edit [k {:keys [deleted] :as v}]
  (if deleted
    [:div.w-8]
    [scb/round-danger-listitem
     {:on-click #(do
                   (.stopPropagation %)
                   (edit-event v))}
     [sc/icon-small
      {:style {:color "var(--text1)"}}
      ico/pencil]]))

(defn always-panel
  ([]
   (always-panel false))
  ([modify?]
   [sc/row-sc-g2-w
    (when modify?
      [hoc.buttons/pill
       {:class    [:cta :pad-right]
        :on-click #(edit-event nil)}
       (hoc.buttons/icon-with-caption (sc/icon-small ico/plus) "Ny aktivitet")])
    [hoc.buttons/pill
     {:class    [:regular :pad-right]
      :on-click #(rf/dispatch [:lab/qr-code-for-current-page])}
     (hoc.buttons/icon-with-caption ico/qrcode "QR-kode")]
    [hoc.buttons/pill
     {:on-click #(js/alert "wat")
      :class    [:regular :pad-right]
      :disabled true}
     (hoc.buttons/icon-with-caption (sc/icon-small ico/nullstill) "Nullstill")]]))

(defn- header
  ([]
   (header false))
  ([modify?]
   [sc/col-space-2
    [sc/row-sc-g2-w
     (when modify? [hoc.toggles/switch :yearwheel/show-editing "Rediger"])
     (when modify? [hoc.toggles/switch :yearwheel/show-deleted "Vis Slettede"])
     [hoc.toggles/switch :yearwheel/show-content "Vis innhold"]]]))

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(defn- listitem-softwrap [can-edit? {:keys [id date content tldr created deleted type] :as m}]
  (let [show-editing @(schpaa.state/listen :yearwheel/show-editing)
        show-content (schpaa.state/listen :yearwheel/show-content)]
    [listitem {:class     [:px-4x (when deleted :deleted)]
               :-on-click #(edit-event m)}
     [:div
      {:style {:display               :grid
               :grid-template-columns "min-content min-content 1fr"}}

      (if (and can-edit? show-editing)
        [:<>
         [:div.pr-2 (trashcan id m)]
         [:div.pr-2 (edit id m)]]
        [:<>
         [:div]
         [:div]])

      (do
        (o/defstyled line :div
          {:color       "var(--text3)"
           :margin-top  "var(--size-1)"
           :line-height "var(--font-lineheight-3)"})
        (o/defstyled strong :span
          {:color "var(--text1)"})
        (o/defstyled weak :span
          {:color       "var(--text2)"
           :font-size   "var(--font-size-1)"
           :font-weight "var(--font-weight-3)"})

        [line
         (interpose [:span ", "]
                    (remove nil?
                            [(when date
                               (weak (str "uke " (ta/week-number (t/date date)))))
                             (when date
                               (weak (flex-datetime date (fn [format content]
                                                           (if (= :text format)
                                                             [sc/subtext-inline content]
                                                             [sc/subtext-inline {:style {:text-decoration :none}} (ta/date-format-sans-year content)])))))
                             (when type
                               (strong (->> type (get sci/person-by-id) :name)))
                             (when tldr
                               (sc/text-inline tldr))
                             (when (and content @show-content)
                               (-> (schpaa.markdown/md->html content)
                                   (sc/markdown)))]))])]]))

(defn get-all-events [show-deleted]
  (let [xf (comp (if show-deleted
                   identity
                   (remove (fn [[_k v]] (:deleted v))))
                 (filter (fn [[_k v]] (when (:date v)
                                        (and
                                          (t/<= (t/date (t/now)) (t/date (:date v)))
                                          (= (t/year (t/now))
                                             (t/year (t/date (:date v))))))))
                 (map (fn [[k v]] [(name k) (assoc v :type-text (:name (get sci/person-by-id (:type v))))])))]
    (transduce xf conj [] @(db/on-value-reaction {:path ["yearwheel"]}))))

(defn render [r]
  (let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
        data (get-all-events @show-deleted)
        users-access-tokens @(rf/subscribe [:lab/all-access-tokens])
        modify? (booking.access/can-modify? r users-access-tokens)]
    (into [sc/col-space-2 {:class [:w-full :overflow-x-auto]}]
          (for [[g data] (sort-by first < (group-by (comp #(if % (t/year %) (t/year (t/now)))
                                                          #(some-> % t/date)
                                                          :date
                                                          second)
                                                    data))]

            [sc/col-space-2
             [sc/hero (or (t/int g) (str "'" (subs (str (t/int g)) 2 4)))]
             (into [:div.space-y-px]
                   (concat
                     (for [[id data] (sort-by (comp :content last) < (remove (comp :date last) data))]
                       (listitem-softwrap modify? (assoc data :id id)))
                     (for [[id data] (sort-by (comp :date last) < (filter (comp :date last) data))]
                       (listitem-softwrap modify? (assoc data :id id)))))]))))

(comment
  (get-all-events))