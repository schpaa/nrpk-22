(ns booking.yearwheel
  (:require [booking.common-views :refer [page-boundary]]
            [lambdaisland.ornament :as o :refer [defstyled]]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [arco.core :as arco]
            [arco.react]
            [schpaa.style.ornament :as sc]
            [tick.core :as t]
            [times.api :as ta]
            [schpaa.time]
            [schpaa.style.button :as scb]
            [schpaa.style.button2 :as scb2]
            [re-frame.core :as rf]
            [schpaa.style.input :as sci]
            [fork.re-frame :as fork]
            [db.core :as db]
            [reagent.core :as r]
            [schpaa.state]
            [schpaa.debug :as l]
            [schpaa.icon :as icon]
            [schpaa.style.dialog]))


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

(defonce data (r/atom (reduce (fn [a {:keys [id type date content] :as e}]
                                (tap> date)
                                (tap> (some-> date t/date-time t/date))
                                (update a id assoc
                                        :type type
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

            [sci/input props :text [:w-full] "Beskrivelse" :content]]

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

(defn submit-fn [{:keys [date content id type] :as values}]
  (if id
    ;update
    (swap! data assoc (str id) {:date    (when date (t/date date))
                                :type    type
                                :updated (str (t/now))
                                :content content})
    ;push
    (swap! data assoc (subs (str (random-uuid)) 0 5) {:date    (when date (t/date date))
                                                      :type    type
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
  ([{:keys [class on-click alternate icon caption] :as attr} & ch]
   ^{:on-click on-click
     :class    class}
   [scb2/normal-tight
    [sc/row-sc-g2
     (when icon
       [sc/icon {:class (if alternate [] [])} (icon alternate)])
     (when caption
       [:div (caption alternate)])]]))

(defn- header []
  (r/with-let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
               show-editing (schpaa.state/listen :yearwheel/show-editing)
               sort-by-created (r/atom true)]
    [:div.sticky.top-0.z-10.snap-x
     {:style {:overflow-x     :auto
              :padding-inline "var(--size-2)"
              :background     "var(--surface0)"}}
     [:div {:class [:h-12 :p-2 :flex :items-stretch :items-center :gap-2]}

      [icon-with-caption
       {:on-click #(edit-event nil)
        :icon     #(-> [:> solid/PlusIcon])
        :caption  #(-> "Ny hendelse")}]

      [icon-with-caption
       {:class     [:snap-start]
        :on-click  #(schpaa.state/toggle :yearwheel/show-editing)
        :alternate @show-editing
        :icon      (fn [e] (if e [:> outline/CheckIcon] [:> outline/PencilIcon]))
        :caption   (fn [e] (if e "Ferdig" "Endre"))}]

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


(defn- my-list-item [{:keys [id date content created deleted type] :as m}]
  (let [show-editing @(schpaa.state/listen :yearwheel/show-editing)]
    [listitem
     {:class    [:px-4 (when deleted :deleted)]
      :on-click #(edit-event m)}
     [sc/col-space-2
      #_[arco.react/time-since {:times  [(str (t/now))
                                         (str (t/instant (t/at (t/new-date 2022 2 2) (t/midnight))))]
                                :config {:refresh-rate 1000
                                         :vocabulary   {:in     "om"
                                                        :now    "nå"
                                                        :second ["sekund" "sekunder"]
                                                        :minute ["minutt" "minutter"]
                                                        :hour   ["time" "timer"]
                                                        :day    ["dag" "dager"]
                                                        :week   ["uke" "uker"]
                                                        :month  ["måned" "måneder"]
                                                        :year   ["år" "år"]}}}
         (fn [formatted-t]
           [:div "my formatted-t: " formatted-t])]
      [sc/row-sc {:class [:h-8]}

       (when show-editing
         (trashcan id m))

       [:div.w-6.shrink-0 (if date
                            [sc/subtext "u" (ta/week-number (t/date date))]
                            [sc/subtext "—"])]

       ;(str (t/instant (t/at (t/date "2023-01-01") (t/midnight))))
       #_(str (t/instant (t/at (t/date date) (t/midnight))))


       [sc/subtext
        {:class [:w-32 :shrink-0 :truncate]}
        (when date
          (arco/time-to
            [(str (t/instant (t/at (t/date date) (t/midnight))))]
            {:vocabulary {:in     "om"
                          :now    "nå"
                          :second ["sekund" "sekunder"]
                          :minute ["minutt" "minutter"]
                          :hour   ["time" "timer"]
                          :day    ["dag" "dager"]
                          :week   ["uke" "uker"]
                          :month  ["måned" "måneder"]
                          :year   ["år" "år"]}
             :order      [:in :time :interval]}))]
       #_[sc/subtext {:class [:w-32 :shrink-0 :truncate]}
          (if date
            [schpaa.time/flex-date
             {:format-fn (fn [dt]
                           (sc/row-sc-g2
                             [:div {:class [:w-8 :text-right]} (t/format "d. " dt)]
                             (ta/month-name dt :length 3)))}
             (t/date date)
             (fn [i]
               (ta/relative-local-time
                 {:past-prefix           "for"
                  :future-prefix         "om"
                  :progressive-rounding? true}
                 (t/at (t/date date) (t/midnight))))]
            [:div.w-8.text-right "—"])]
       [sc/subtext {:class [:truncate :shrink-0 :w-56]} (->> type (get sci/person-by-id) :name)]
       [sc/subtext {:class [:truncate :shrink-0 :w-64]} (or content "—")]

       #_[sc/subtext (ta/short-time-format (t/instant created))]
       #_[sc/link "link"]]
      #_[sc/row-sc
         [:div.w-48]
         [sc/subtext {:class [:truncate :w-32 :select-all]} (str (:id m))]]]]))

(defn render [r]
  (let [show-deleted (schpaa.state/listen :yearwheel/show-deleted)
        show-editing (schpaa.state/listen :yearwheel/show-editing)]
    [page-boundary r
     {:whole
      [:div
       [header]
       [sc/col-space-2 {:class [:py-8 :w-full :overflow-x-auto]}
        (doall (for [[g data] (sort-by first < (group-by (comp #(if % (t/year %) (t/year (t/now))) #(some-> % t/date) :date val)
                                                         (if (and @show-deleted @show-editing)
                                                           @data
                                                           (remove (comp :deleted val) @data))))]
                 [sc/col-space-2
                  [:div.h-1]
                  [sc/header-title {:class [:px-4]} (t/int g)]
                  (into [:div.space-y-px]
                        (concat
                          (for [[id data] (sort-by (comp :content last) < (remove (comp :date last) data))]
                            [:div
                             (my-list-item (assoc data :id id))])
                          (for [[id data] (sort-by (comp :date last) < (filter (comp :date last) data))]
                            (my-list-item (assoc data :id id)))))]))]]}]))


(comment
  (ta/week-number (t/now)))