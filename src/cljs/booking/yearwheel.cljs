(ns booking.yearwheel
  (:require [booking.common-views :refer [page-boundary]]
            [lambdaisland.ornament :as o :refer [defstyled]]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]

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

            [booking.page-controlpanel]))



#_(defonce st (reduce (fn [a e] (assoc a (subs (str (random-uuid)) 0 5) (dissoc e :id)))
                      {}
                      [{:date nil :content "a"}
                       {:date nil :content "d"}
                       {:date nil :content "d"}
                       {:date (str (t/at (t/new-date 2023 3 16) (t/noon))) :content "Styremøte"}
                       {:date (t/now) :content "b"}
                       {:date nil :content "c"}]))
#_(def data (r/atom st))

(defonce ui-state (r/atom {}))

(defonce data (r/atom (reduce (fn [a {:keys [id tldr type date content] :as e}]
                                (tap> date)
                                (tap> (some-> date t/date-time t/date))
                                (update a id assoc
                                        :type type
                                        :tldr tldr
                                        :id id
                                        :date (some-> date t/date-time t/date)
                                        :content content))
                              {}
                              (map #(assoc % :created (str (t/now))
                                             :id (subs (str (random-uuid)) 0 5))
                                   [{:date nil :content "c"}
                                    {:type 8
                                     :date nil}
                                    {:type 11
                                     :date nil}
                                    {:type 9
                                     :tldr "En lang tekst som viser hvordan bryting fungerer, dvs når setningene strekker seg over flere linjer"
                                     :date (str (t/at (t/new-date 2022 4 28) (t/noon)))}
                                    {:date (str (t/at (t/new-date 2022 5 11) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 5 31) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 6 14) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 8 10) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 8 30) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 9 21) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 10 10) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 11 1) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 11 23) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 12 6) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 4) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 2 15) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 3 8) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 3 16) (t/noon))) :type 8}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 6 20) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 6 24) (t/noon)))}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 8 8) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 8 12) (t/noon)))}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 8 12) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 8 19) (t/noon)))}]))))

(o/defstyled listitem :div
  :cursor-pointer :py-1
  {:min-height "var(--size-4)"}
  [:&
   [:.deleted {:color           "red"
               :text-decoration :line-through
               :opacity         0.3}]
   [:&:hover {:background "var(--surface00)"}]]
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
          [sc/col-fields {:class [:space-y-2]}
           [sc/dialog-title "Ny hendelse"]
           [sc/row-wrap
            [sc/col {:class []}
             [sc/label "Kategori"]
             [sci/combobox-example {:value     (values :type)
                                    :on-change #(set-values {:type %})
                                    :class     [:w-56]}]]

            [sci/input props :date [:w-48] "Dato" :date]

            [sci/input props :text [:w-full] "TL;DR (too long, didn't read)" :tldr]

            [sci/textarea props :text [:w-full] "Beskrivelse (blog)" :content]]

           #_[sci/textarea props :text [] "Innhold (ekslusiv tittel)" :content]]
          [sc/row-ec
           [scb2/normal-regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           [scb2/cta-narrow {:disabled false                ;(not changed?)
                             :type     "submit"
                             :on-click #(do
                                          (set-values {:id nil})
                                          (on-save))} "+"]
           [scb2/cta-regular {:disabled (not changed?)
                              :type     "submit"
                              :on-click #(on-save)} "Lagre"]]]]))]])

(defn submit-fn [{:keys [date content id type tldr] :as values}]
  (if id
    ;update
    (swap! data assoc (str id) {:date    (when date (t/date date))
                                :type    type
                                :tldr    tldr
                                :updated (str (t/now))
                                :content content})
    ;push
    (swap! data assoc (subs (str (random-uuid)) 0 5) {:date    (when date (t/date date))
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
                 (swap! data update k assoc :deleted (not deleted)))}
   #_#(db/database-update
        {:path  ["booking-posts" "articles" (name k)]
         :value {:deleted (not deleted)}})
   [sc/icon-small
    {:style {:color "var(--surface5)"}}
    (if deleted (icon/small :rotate-left)
                [:> outline/TrashIcon])]])

(o/defstyled icon-with-caption :button
  {:background     "var(--surface00)"
   :color          "var(--surface3)"
   :padding-inline "var(--size-2)"
   :padding-block  "var(--size-1)"
   :border-radius  "var(--radius-round)"
   :font-size      "var(--font-size-0)"}
  [:&:hover {:background "var(--surface00)"
             :color      "var(--surface5)"}]
  ([{:keys [class on-click alternate icon caption] :as attr} & ch]
   ^{:on-click on-click
     :class    class}
   [:<>
    [sc/row-sc-g2
     (when icon
       [sc/icon-small {:class (if alternate [] [])} (icon alternate)])
     (when caption
       [:div (caption alternate)])]]))

(defn- header []
  (r/with-let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
               show-editing (schpaa.state/listen :yearwheel/show-editing)
               show-content (schpaa.state/listen :yearwheel/show-content)
               sort-by-created (r/atom true)]
    [sc/row-sc-g2
     [icon-with-caption
      {:on-click  #(schpaa.state/toggle :yearwheel/show-deleted)
       :alternate @show-deleted
       :icon      (fn [_] [:> outline/TrashIcon])
       :caption   (fn [e] (if e "Skjul slettede" "Vis slettede"))}]
     [icon-with-caption
      {:on-click  #(schpaa.state/toggle :yearwheel/show-content)
       :alternate @show-content
       :icon      (fn [e] (if e [:> outline/CheckIcon] [:> outline/EyeIcon]))
       :caption   (fn [e] (if e "A" "B"))}]
     [icon-with-caption
      {:on-click  #(schpaa.state/toggle :yearwheel/show-editing)
       :alternate @show-editing
       :icon      (fn [e] (if e [:> outline/CheckIcon] [:> outline/PencilIcon]))
       :caption   (fn [e] (if e "Ferdig" "Endre"))}]]))

(defn- header' []
  (r/with-let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
               show-editing (schpaa.state/listen :yearwheel/show-editing)
               show-content (schpaa.state/listen :yearwheel/show-content)
               sort-by-created (r/atom true)]
    #_[:div.w-full.bg-alt.truncate                          ;.xsticky.top-0.z-10.xinset-x-0.bg-alt.w-fullx
       [:div.overflow-x-aduto.truncate]
       [:div.flex.truncate
        (for [_ (range 20)]
          [:div.mr-2 "Xxx "])]]
    [:div.sticky.top-0.z-10.max-w-xsx
     {:style {:overflow-x     :auto
              :padding-inline "var(--size-2)"
              :background     "var(--surface0)"}}
     [:div {:class [:h-12 :p-2 :flex :items-stretch :items-center :gap-2]}

      [icon-with-caption
       {:on-click #(edit-event nil)
        :icon     #(-> [:> solid/PlusIcon])
        :caption  #(-> "Ny hendelse")}]

      [icon-with-caption
       {:on-click  #(schpaa.state/toggle :yearwheel/show-editing)
        :alternate @show-editing
        :icon      (fn [e] (if e [:> outline/CheckIcon] [:> outline/PencilIcon]))
        :caption   (fn [e] (if e "Ferdig" "Endre"))}]

      [icon-with-caption
       {:on-click  #(schpaa.state/toggle :yearwheel/show-content)
        :alternate @show-content
        :icon      (fn [e] (if e [:> outline/CheckIcon] [:> outline/EyeIcon]))
        :caption   (fn [e] (if e "A" "B"))}]

      [:div.grow]

      (when @show-editing
        [icon-with-caption
         {:icon #(-> [:> outline/FolderIcon])}])

      (when @show-editing
        [icon-with-caption
         {:on-click  #(swap! sort-by-created not)
          :icon      #(if % [:> outline/SortAscendingIcon] [:> outline/SortDescendingIcon])
          :alternate @sort-by-created}])

      (when @show-editing
        [icon-with-caption
         {:class     [:snap-start]
          :on-click  #(schpaa.state/toggle :yearwheel/show-deleted)
          :alternate @show-deleted
          :icon      (fn [_] [:> outline/TrashIcon])
          :caption   (fn [e] (if e "Skjul slettede" "Vis slettede"))}])]]))

(def arco-datetime-config
  {:refresh-rate 1000
   :vocabulary   {:in     "om"
                  :ago    "siden"
                  :now    "nå"
                  :second ["sekund" "sekunder"]
                  :minute ["minutt" "minutter"]
                  :hour   ["time" "timer"]
                  :day    ["dag" "dager"]
                  :week   ["uke" "uker"]
                  :month  ["måned" "måneder"]
                  :year   ["år" "år"]}})

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
                             :config arco-datetime-config}
         (fn [formatted-t]
           [sc/link on-click (formatted formatted-t)])]))))

#_(defn- my-list-item [{:keys [id date content created deleted type] :as m}]
    (let [show-editing @(schpaa.state/listen :yearwheel/show-editing)]
      [listitem
       {:class    [:px-4 (when deleted :deleted)]
        :on-click #(edit-event m)}
       [sc/col-space-2
        [sc/row-sc {:class [:h-8]}

         (when show-editing
           (trashcan id m))

         [:div.w-6.shrink-0 (if date
                              [sc/subtext "u" (ta/week-number (t/date date))]
                              [sc/subtext "—"])]

         [:div.whitespace-nowrap (flex-datetime date #(sc/subtext %))]

         [sc/subtext {:class []}
          (->> type (get sci/person-by-id) :name)
          (when content
            [:span ", " content])]

         #_[sc/subtext (ta/short-time-format (t/instant created))]
         #_[sc/link "link"]]
        #_[sc/row-sc
           [:div.w-48]
           [sc/subtext {:class [:truncate :w-32 :select-all]} (str (:id m))]]]]))

(defn- listitem-softwrap [{:keys [id date content tldr created deleted type] :as m}]
  (let [show-editing @(schpaa.state/listen :yearwheel/show-editing)
        show-content (schpaa.state/listen :yearwheel/show-content)]
    [listitem {:class    [:px-4x (when deleted :deleted)]
               :on-click #(edit-event m)}
     [:div
      {:style {:display               :grid
               :grid-template-columns "min-content 1fr"}}

      (if show-editing
        (trashcan id m)
        [:div])

      (do
        (o/defstyled line :div
          {:margin-top  "var(--size-1)"
           :color       "var(--text3)"
           :line-height "var(--font-lineheight-3)"})
        (o/defstyled text :span
          {:color "var(--text3)"})
        (o/defstyled emph :span
          {:color "var(--text1)"})
        (o/defstyled dim :span
          {:color       "var(--text3)"
           :font-size   "var(--font-size-1)"
           :font-weight "var(--font-weight-3)"})

        [line
         (interpose ", "
                    (remove nil?
                            [(when date
                               (dim (str "uke " (ta/week-number (t/date date)))))
                             (when date
                               (dim (flex-datetime date #(vector :span %))))
                             (when type
                               (emph (->> type (get sci/person-by-id) :name)))
                             (when tldr
                               tldr)
                             (when (and content @show-content)
                               (-> (schpaa.markdown/md->html content)
                                   (sc/markdown)))]))

         #_(text "aljksd ;alskdq;oawdj ioa;jowidjoasj di;oasijk;ojias jl;asjdo; awjikio; ja4d;oi jhiod j;asdoi; jasodjia o;sidq aso;idj aosdjaio;sodi")])]]

    #_[listitem
       {:class    [:px-4 (when deleted :deleted)]
        :on-click #(edit-event m)}
       [sc/col-space-2
        [sc/row-sc {:class [:h-8]}

         (when show-editing
           (trashcan id m))

         [:div.w-6.shrink-0 (if date
                              [sc/subtext "u" (ta/week-number (t/date date))]
                              [sc/subtext "—"])]

         [:div.whitespace-nowrap (flex-datetime date #(sc/subtext %))]

         [sc/subtext {:class []}
          (->> type (get sci/person-by-id) :name)
          (when content
            [:span ", " content])]

         #_[sc/subtext (ta/short-time-format (t/instant created))]
         #_[sc/link "link"]]
        #_[sc/row-sc
           [:div.w-48]
           [sc/subtext {:class [:truncate :w-32 :select-all]} (str (:id m))]]]]))

(defn render [r]
  (r/with-let [open? (r/atom true)]
    (let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
          show-editing (schpaa.state/listen :yearwheel/show-editing)]

      [page-boundary r
       [sc/col-space-2 {:class [:w-full :overflow-x-auto]}
        (doall (for [[g data] (sort-by first < (group-by (comp #(if % (t/year %) (t/year (t/now))) #(some-> % t/date) :date val)
                                                         (if (and @show-deleted @show-editing)
                                                           @data
                                                           (remove (comp :deleted val) @data))))]
                 [sc/col-space-2
                  [:div.h-1]
                  [sc/header-title {:class [:px-2]} (t/int g)]
                  (into [:div.space-y-px]
                        (concat
                          (for [[id data] (sort-by (comp :content last) < (remove (comp :date last) data))]
                            (listitem-softwrap (assoc data :id id)))
                          (for [[id data] (sort-by (comp :date last) < (filter (comp :date last) data))]
                            (listitem-softwrap (assoc data :id id)))))]))]])))


(comment
  (ta/week-number (t/now)))