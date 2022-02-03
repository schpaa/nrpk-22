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
            [eykt.calendar.actions :as actions]
            [eykt.content.mine-vakter :as content.mine-vakter]

    ;;
            [eykt.content.uke-kalender :as content.uke-kalender]
            [schpaa.modal.readymade :as readymade]
            [eykt.content.rapport-side]))

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

(defn render-back-tabbar []
  [:div.sticky.top-16.z-100.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.user "Om meg" nil :icon :user]
    [:r.mine-vakter "Mine vakter" nil :icon :calendar]
    [:r.debug "Feilsøking" nil :icon :eye]]])

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

       [render-back-tabbar]

       [k/case-route (fn [route] (-> route :data :name))
        :r.user
        (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' :form)]
          [:div.space-y-4
           {:class bg}
           [user.views/my-info]])

        :r.mine-vakter
        [content.mine-vakter/mine-vakter {:uid (:uid @user-auth)}]

        :r.debug
        [:div.z-100 [hoc/debug]]

        [:div @route]]])))

(defn render-front-tabbar []
  [:div.sticky.top-16.z-200
   [schpaa.components.tab/tab {:selected @(rf/subscribe [:app/current-page])}
    [:r.forsiden "Forsiden" nil :icon :document]
    [:r.mine-vakter "Mine vakter" nil :icon :calendar]
    [:r.common2 "Kalender" nil :icon :calendar]]])

(defn common [r]
  (let [uid @(rf/subscribe [::db/root-auth :uid])]
    [:<>
     (render-front-tabbar)

     [k/case-route (fn [route] (-> route :data :name))
      :r.forsiden
      [eykt.content.rapport-side/rapport-side]
      ;[eykt.content.oppsett/render r]
      ;[new-designed-content]

      :r.mine-vakter
      [content.mine-vakter/mine-vakter {:uid uid}]
      #_(let [listener (db/on-value-reaction {:path ["calendar"]})
              {:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
          [:div.p-2
           {:class bg}
           [eykt.calendar.views/calendar
            {:base (eykt.calendar.core/routine @listener)
             :data (eykt.calendar.core/expand-date-range)}]])
      :r.common2
      (let [{:keys [bg]} (st/fbg' :form)]
        [:div
         {:class bg}
         [:div.space-y-px.flex.flex-col
          {:style {:min-height "calc(100vh - 7rem)"}}
          (if (seq eykt.calendar.core/rules')
            [:div.flex-1
             {:class bg}
             [content.uke-kalender/uke-kalender {:uid uid}]]
            '[empty-list-message "Booking-listen er tom"])
          [booking.views/last-bookings-footer {}]]])

      #_[:div.min-h-screen
         [content.uke-kalender/uke-kalender {:uid uid}]
         [booking.views/last-bookings-footer {}]]]]))

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