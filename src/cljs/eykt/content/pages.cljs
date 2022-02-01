(ns eykt.content.pages
  (:require [shadow.resource :refer [inline]]
            [schpaa.markdown :refer [md->html]]
            [kee-frame.core :as k]
            [booking.hoc :as hoc]
            [re-frame.core :as rf]
            [schpaa.style :as st]
            [db.core :as db]
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
            [eykt.calendar.actions :as actions]))

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

(defn user [r]
  (let [route (rf/subscribe [:app/current-page])
        user-auth (rf/subscribe [::db/user-auth])
        {:keys [bg fg- fg+ hd p p- he]} (st/fbg' :surface)]
    (if-not @user-auth
      [:div.p-4.max-w-md.mx-auto
       [rounded-view {:float 1} [db.signin/login]]]
      [:<>
       [:div.w-full
        {:class bg}
        [:div.p-4.max-w-md.mx-auto
         [user.views/userstatus-form
          {:user-auth @user-auth
           :name      (:display-name @user-auth)}]]]

       [:div.sticky.top-16.z-100.z-200
        [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
         [:r.user "Om meg" nil :icon :user]
         [:r.mine-vakter "Mine vakter" nil :icon :calendar]
         [:r.debug "Feilsøking" nil :icon :eye]]]

       [k/case-route (fn [route] (-> route :data :name))
        :r.user
        (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
          [:div.space-y-4
           {:class bg}
           [user.views/my-info]])

        :r.mine-vakter
        (let [{:keys [bg bg+ fg- fg fg+ hd p p- p+ he]} (st/fbg' :form)
              source (db/on-value-reaction {:path ["calendar"]})
              uid (:uid @user-auth)
              data (filter (fn [[k v]] (contains? v (keyword uid))) @source)
              rules (group-by :group eykt.calendar.core/rules')

              straightened-rules (fn [a [k v]]
                                   (if (< 1 (count (seq v)))
                                     (reduce (fn [a [k' v]] (conj a [k k' v])) a v)
                                     (conj a (vec (flatten [k (first v)])))))
              prep-data (into {} (map (fn [[k v]] [k (reduce straightened-rules [] (first (vals v)))]) data))
              lookup (fn [x] (reduce (fn [a e]
                                       (let [f (fn [itm]
                                                 (t/hours (t/duration (tick.alpha.interval/new-interval
                                                                        (t/time (:starttime itm))
                                                                        (t/time (:endtime itm))))))]
                                         (assoc a (str (:starttime e)) (f e)))) {} x))
              sum (reduce (fn [a [date rule-refs]]
                            (+ a (reduce (fn [a [group starttime clicked-time]]
                                           (tap> (name starttime))
                                           (+ a (get (lookup (:times (first (get rules group))))
                                                     (name starttime)))) 0 rule-refs)))
                          0 prep-data)]
          [:div.p-4
           {:class bg}
           [:div "en liste over mine vakter"]

           [:div {:class hd} sum " timer"]
           ;(l/ppre-x (filter (fn [[k v]] (= k uid) ) listener))

           #_(let [sum (reduce (fn [a e] a) 0 data)] [l/ppre-x data])

           (into [:div] (for [[date vs] data]
                          [:div
                           [:div date]

                           ;[l/ppre-x ">> " (vals vs)]

                           #_[:div.flex.gap-2 (map #(vector :div %) (keys (first (vals vs))))]

                           (for [e (keys (first (vals vs)))
                                 z (vals vs)
                                 :let [x (:times (first (get-in rules [e])))
                                       lookup (reduce (fn [a e]
                                                        (let [f (fn [itm]
                                                                  (t/hours (t/duration (tick.alpha.interval/new-interval
                                                                                         (t/time (:starttime itm))
                                                                                         (t/time (:endtime itm))))))]
                                                          (assoc a (str (:starttime e)) (f e)))) {} x)]]
                             [:<>
                              ;[l/ppre-x x]
                              ;[l/ppre-x (get lookup "11:00" "?")]
                              ;[:div.h-1]
                              ;[l/ppre-x lookup]
                              ;[:div.text-sky-500  "+" e]
                              [:div.text-sky-500 (reduce (fn [a e]
                                                           (+ a (get lookup (name e)))) 0
                                                         (map key (into {} (vals z))))]
                              #_[:div.text-red-500 "k " (str (mapv keys (vals z)))]])]))])


        :r.debug
        [:div.z-100 [hoc/debug]]

        [:div @route]]])))

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


(defn render-tab-bar []
  [:div.sticky.top-16.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.common "Måned" nil :icon :calendar]
    [:r.common2 "Uke" nil :icon :calendar]
    [:r.oppsett "Oppsett" nil :icon :cog]]])

#_(defn config-for [b]
    (eykt.calendar.core/grab-for-graph b))

(defn status-for [dt]
  (get {"2022-02-02" {"11:00" {:slots 3
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["abe" "2022-01-02T12:31:50"]
                                       ["pop" "2022-01-02T12:31:50"]
                                       ["eve" "2021-01-02T12:31:50"]]}
                      "15:00" {:slots 3
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["abe" "2022-01-02T12:31:50"]
                                       ["pop" "2022-01-02T12:31:51"]
                                       ["eve" "2022-01-02T12:31:50"]]}
                      "06:00" {:slots 1
                               :items [["cps" "2022-01-02T12:31:50"]
                                       ["eve" "2022-01-02T12:31:50"]]}}
        "2022-02-04" {"18:00" {:slots 2
                               :items [["pop" "2022-01-02T12:31:50"]
                                       ["eve" "2022-01-02T12:31:50"]]}}
        "2022-04-27" {}} (str dt))

  #_(cond-> {:x "12:20"}
      (some #{(t/int (t/day-of-week dt))} #{2 3 4}) (assoc :rows 1)
      (some #{(t/int (t/day-of-week dt))} #{6 7}) (assoc :rows 2)))

(defn common [r]
  (let [uid @(rf/subscribe [::db/root-auth :uid])]
    [:<>
     (render-tab-bar)

     [k/case-route (fn [route] (-> route :data :name))
      :r.oppsett
      [eykt.content.oppsett/render r]

      :r.common (let [listener (db/on-value-reaction {:path ["calendar"]})
                      {:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
                  [:div.p-2
                   {:class bg}
                   [eykt.calendar.views/calendar
                    {:base (eykt.calendar.core/routine @listener)
                     :data (eykt.calendar.core/expand-date-range)}]])
      :r.common2
      (let [
            {:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :form)]
        (r/with-let [week (r/atom (ta/week-number (t/date)))]
          [:div.p-4.space-y-2
           {:class bg}
           [:div.flex.gap-1
            [bu/regular-button-small {:on-click #(swap! week dec)} :chevron-left]
            [fields/text (-> {:naked?        true
                              :values        #(-> @week)
                              :handle-change #(reset! week (-> % .-target .-value))}
                             fields/number-field) :label "" :name :week]
            [bu/regular-button-small {:on-click #(swap! week inc)} :chevron-right]]
           [:div (ta/week-number (t/date))]

           (into [:div.grid.gap-px.place-content-centerx.sticky.top-28.bg-white
                  {:style {:grid-template-columns "2rem repeat(7,1fr)"}}]
                 (map #(vector :div %) '(u ma ti on to fr lø sø)))

           (let [{:keys [fg fg+ bg- bg+ bg hd he p p- p+ fg-]} (st/fbg' :calender-table)
                 first-date-of-week (ta/calc-first-day-at-week @week)]
             (into [:div.grid.gap-px
                    {:style {:grid-template-columns "repeat(auto-fit,minmax(20rem,1fr))"}}]
                   (for [i (range 1)]
                     (let [the-week-interval (tick.alpha.interval/new-interval
                                               first-date-of-week
                                               (t/>> first-date-of-week (t/new-period 7 :days)))
                           this-weeks-config (eykt.calendar.core/grab-for-graph the-week-interval)]

                       [:div
                        [l/ppre-x
                         the-week-interval]

                        (count this-weeks-config)
                        (into [:div.grid.gap-px.place-content-centerx
                               {:style {:grid-template-columns "2rem repeat(7,minmax(6rem,min-content))"}}]
                              (concat [[:div.flex.justify-center
                                        {:class (concat [:border-r :border-black :p-1] p- fg-)}
                                        (str (+ (js/parseInt @week) i))]]

                                      (for [e (range 7)
                                            :let [e (+ (* i 7) e)
                                                  dt (t/>> first-date-of-week (t/new-period e :days))]]
                                        (let [s (status-for dt)
                                              listener (db/on-value-reaction {:path ["calendar" (str dt)]})
                                              roo (eykt.calendar.core/rooo @listener)]
                                          (when (zero? e) (tap> @listener))
                                          [:div
                                           ;[l/ppre-x roo]
                                           [:div (str (t/day-of-week dt))]
                                           [:div
                                            (for [[group-id e'] (get this-weeks-config (str dt))]
                                              [:div
                                               ;[l/ppre (:slots (first e'))]
                                               [:div (:description (first e'))]
                                               (for [each (sort-by :starttime < e')]
                                                 (let [r' (get-in roo [group-id (keyword (str (:starttime each)))])
                                                       render (fn [e]
                                                                [:div.-debugx
                                                                 ;[l/ppre-x group-id (:starttime each) r']
                                                                 [:div {:class fg-} (str (:starttime e))]
                                                                 [:div.space-y-1
                                                                  [:div.space-y-px

                                                                   (concat
                                                                     (for [[idx [k v]] (map-indexed vector (sort-by second < r')) #_(range (:slots e))]
                                                                       [:div.flex.flex-col.gap-2
                                                                        {:class (if (< idx (:slots (first e')))
                                                                                  (concat bg-)
                                                                                  (concat bg- [:text-red-500]))}

                                                                        [:div.w-16.truncate {:class p-} (str k)]
                                                                        [:div {:class p-} (ta/time-format (t/time (t/instant v)))]])

                                                                     (let [c (- (:slots (first e')) (count r'))]
                                                                       (when (pos? c)
                                                                         (map (fn [e] [:div
                                                                                       {:class (concat bg-)}
                                                                                       (inc e)]) (range c)))))]

                                                                  (if (get r' (keyword uid))
                                                                    [bu/danger-button-small
                                                                     {:on-click
                                                                      #(actions/delete' {:uid      uid ; ;"b-person"
                                                                                         :group    (name group-id)
                                                                                         :timeslot (str (:starttime e))
                                                                                         :dateslot dt})} "fjern"]

                                                                    [bu/regular-button-small
                                                                     {:on-click
                                                                      #(actions/add' {:uid      uid ; ;"b-person"
                                                                                      :group    (name group-id)
                                                                                      :timeslot (str (:starttime e))
                                                                                      :dateslot dt})} "legg til"])]])]

                                                   (render each)))])]]))))]))))]))]]))


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
    (reduce (fn [a [group time-slots]] (assoc a group 1)) {} data)))
