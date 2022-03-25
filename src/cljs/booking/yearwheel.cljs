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
    [schpaa.style.button2 :as scb2]
    [schpaa.style.input :as sci]
    [fork.re-frame :as fork]
    [db.core :as db]
    [reagent.core :as r]
    [schpaa.state]
    [schpaa.icon :as icon]
    [schpaa.style.dialog]
    [schpaa.markdown]
    [schpaa.style.hoc.toggles :as hoc.toggles]
    [booking.qrcode]
    [re-frame.core :as rf]
    [booking.data]
    [booking.yearwheel-testdata]
    [schpaa.style.hoc.buttons :as hoc.buttons]))

(o/defstyled listitem :div
  [:& :cursor-pointer :p-1
   {:min-height "var(--size-4)"}
   [:.deleted {:color           "red"
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
           [sc/dialog-title "Ny hendelse"]
           [sc/row-sc-g2-w
            [sci/combobox props :kind? [:w-56] "Kategori" :type]
            [sci/input props :date [:w-40] "Dato" :date]
            [sci/input props :text [:w-full] "TL;DR (too long, didn't read)" :tldr]
            [sci/textarea props :text {:class [:w-full]} "Beskrivelse (blog)" :content]]]

          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           [hoc.buttons/cta {:disabled false                ;(not changed?)
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
    (swap! booking.yearwheel-testdata/data assoc (str id) {:date    (when date (t/date date))
                                                           :type    type
                                                           :tldr    tldr
                                                           :updated (str (t/now))
                                                           :content content})
    ;push
    (swap! booking.yearwheel-testdata/data assoc (subs (str (random-uuid)) 0 5) {:date    (when date (t/date date))
                                                                                 :type    type
                                                                                 :tldr    tldr
                                                                                 :created (str (t/now))
                                                                                 :content content})))

(defn edit-event [m]
  (schpaa.style.dialog/open-dialog-newevent
    {:form      addpost-form
     :data      m
     :on-submit submit-fn}))

(defn trashcan [k {:keys [deleted] :as v}]
  [(if deleted scb/round-normal-listitem scb/round-danger-listitem)

   {;:style {:background "red"}
    :on-click #(do
                 (.stopPropagation %)
                 (tap> ["DELETE" k v])
                 (swap! booking.yearwheel-testdata/data update k assoc :deleted (not deleted)))}
   #_#(db/database-update
        {:path  ["booking-posts" "articles" (name k)]
         :value {:deleted (not deleted)}})
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    (if deleted (icon/small :rotate-left) ico/trash)]])

(defn always-panel []
  [sc/row-sc-g2-w
   [hoc.buttons/pill
    {:class    [:cta :pad-right]
     :on-click #(edit-event nil)}
    (hoc.buttons/icon-with-caption (sc/icon-small ico/plus) "Nytt event")]
   [hoc.buttons/pill
    {:class    [:regular :pad-right]
     :on-click #(rf/dispatch [:lab/qr-code-for-current-page])}
    (hoc.buttons/icon-with-caption ico/qrcode "QR-kode")]
   [hoc.buttons/pill
    {:on-click #(js/alert "wat")
     :class    [:regular :pad-right]
     :disabled true}
    (hoc.buttons/icon-with-caption (sc/icon-small ico/nullstill) "Nullstill")]])


(defn- header []
  (r/with-let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
               show-content (schpaa.state/listen :yearwheel/show-content)]

    [sc/col-space-2

     [sc/row-sc-g2-w
      [hoc.toggles/switch :yearwheel/show-content "Vis innhold"]
      [hoc.toggles/switch :yearwheel/show-editing "Rediger"]
      [hoc.toggles/switch :yearwheel/show-deleted "Vis Slettede"]]]))

(defn- toggle-relative-time []
  (schpaa.state/toggle :app/show-relative-time-toggle))

(defn flex-datetime [date formatted]
  (let [relative-time? (schpaa.state/listen :app/show-relative-time-toggle)
        on-click {:on-click #(do
                               (tap> ["toggle" @relative-time?])
                               (.stopPropagation %)
                               (toggle-relative-time))}]
    (when date
      (if @relative-time?
        [sc/link on-click (formatted (ta/date-format-sans-year date))]
        [arco.react/time-to {:times  [(if (t/date-time? date)
                                        (t/date-time date)
                                        (t/at (t/date date) (t/midnight)))
                                      (t/now)]
                             :config booking.data/arco-datetime-config}
         (fn [formatted-t]
           [sc/link on-click (formatted formatted-t)])]))))

(defn- listitem-softwrap [{:keys [id date content tldr created deleted type] :as m}]
  (let [show-editing @(schpaa.state/listen :yearwheel/show-editing)
        show-content (schpaa.state/listen :yearwheel/show-content)]
    [listitem {:class    [:px-4x (when deleted :deleted)]
               :on-click #(edit-event m)}
     [:div
      {:style {:display               :grid
               :grid-template-columns "min-content 1fr"}}

      (if show-editing
        [:div.pr-2 (trashcan id m)]
        [:div])

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
         (interpose ", "
                    (remove nil?
                            [(when date
                               (weak (str "uke " (ta/week-number (t/date date)))))
                             (when date
                               (weak (flex-datetime date #(vector :span %))))
                             (when type
                               (weak (->> type (get sci/person-by-id) :name)))
                             (when tldr
                               (strong tldr))
                             (when (and content @show-content)
                               (-> (schpaa.markdown/md->html content)
                                   (sc/markdown)))]))])]]))

(defn render [r]
  (r/with-let [open? (r/atom true)]
    (let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
          show-editing (schpaa.state/listen :yearwheel/show-editing)]

      [sc/col-space-2 {:class [:w-full :overflow-x-auto]}
       (doall (for [[g data] (sort-by first < (group-by (comp #(if % (t/year %) (t/year (t/now))) #(some-> % t/date) :date val)
                                                        (if (and @show-deleted @show-editing)
                                                          @booking.yearwheel-testdata/data
                                                          (remove (comp :deleted val) @booking.yearwheel-testdata/data))))]
                [sc/col-space-2
                 [sc/hero "'" (subs (str (t/int g)) 2 4)]
                 (into [:div]
                       (concat
                         (for [[id data] (sort-by (comp :content last) < (remove (comp :date last) data))]
                           (listitem-softwrap (assoc data :id id)))
                         (for [[id data] (sort-by (comp :date last) < (filter (comp :date last) data))]
                           (listitem-softwrap (assoc data :id id)))))]))])))
