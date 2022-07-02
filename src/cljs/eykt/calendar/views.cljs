(ns eykt.calendar.views
  (:require [schpaa.debug :as l]
            [lambdaisland.ornament :as o]
            [eykt.calendar.actions :as actions]
            [times.api :as ta]
            [tick.core :as t]
            [re-frame.core :as rf]
            [times.api :refer [format]]
            [booking.ico :as ico]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as button]
            [booking.styles :as b]
            [clojure.string :as str]))

;; styles
                             
(o/defstyled user-cell :div
  [:&
   :flex
   :w-24
   :items-center
   :truncate
   :h-10
   
   {:user-select   :none
    :border-radius "var(--radius-0)"}])

(o/defstyled avail-user-slot' :div
  [:&
   {:color            "green"
    :box-shadow       "var(--inner-shadow-1)"
    :background-color "var(--red-4)"}]

  #_[:&
     :text-red-500
     :bg-red-500
     ;user-cell
     :h-full
     :truncate
     {:outline "2px solid red"
      :box-shadow       "var(--shadow-1)"
      ;:color            "var(--text1)"
      :background-color "red" #_"var(--toolbar-)"
      :cursor           :pointer}
     [:&.cancelled
      {;:background-color     "transparent"
       :text-decoration-line :line-through
       :border               "2px solid var(--toolbar-)"
       :color                "var(--toolbar-)"}]
     [:&.owner {;:background "var(--brand1)"
                :color      "var(--brand1-copy)"}]
     [:&:active {:xbackground "var(--toolbar-)"}
      [:&.owner {:xbackground "var(--brand2)"
                 :color      "var(--brand2-copy)"}]]])

(o/defstyled time-slot :div
  :w-32
  :h-10
  :flex :items-end)

;;

(defn invert [data]
  (->> (vals data)
       (reduce (fn [a kvs]
                 (let [z (reduce (fn [a' [k v]]
                                   (update a' k (fnil conj {}) v)) a kvs)]
                   (conj a z))) {})
       (map (fn [[k v]] [k (into [] (sort-by val < v))]))))

;region calendar elements/components

(defn- week-component [dt description]
  [sc/row-field {:style {:width            "100%"
                         :align-items     :end
                         :justify-content :between}}
   [sc/title2 (booking.flextime/relative-time dt ta/calendar-date-format)]
   [sc/row-ec
    [sc/small1 description]
    [sc/small0 (str "UKE " (times.api/week-number dt))]]])

(defn- badge-text [this-uid owner? cancelled?]
  (let [{:keys [alias navn]} (user.database/lookup-userinfo (name this-uid))
        navn (cond
               (not (empty? alias)) alias
               (not (empty? navn)) navn
               :else "...")
        fornavn (butlast (str/split " " badge-text))
        etternavn (last (str/split " " badge-text))
        xs (str/split navn #" ")
        etternavn (last xs)
        fornavn (apply str (str/join " " (butlast xs)))]
    [b/co0 {:class [:truncate]
            :style {:justify-content :space-between}}
     [b/text
      {:class [:bold :truncate]
       :style {:line-height 1.1
               :color (if cancelled? :unset (when owner? :unset))}}
      etternavn]
     [b/small {:class [:truncate]
               :style {}}
      fornavn]]))

(comment                                 
  (let [navn "peter johsnen-iversen"
        xs (str/split navn #" ")
        etternavn (last xs)
        fornavn (apply str (butlast xs))]
    {:fornavn fornavn
     :etternavn etternavn}))

;endregion

(defn avail-user-slot [ uid section starttime-key]
  [b/co0 {:class [:truncate]
          :style {:justify-content :space-between}
          :sstyle    {:background-color "var(--floating)"
                      :padding          "0.25rem"}
          :on-click (fn [] (let [args {:uid uid :section section :timeslot starttime-key}]
                             (actions/add args)))}
   [b/text
    {:class [:bold]
     :style {:line-height 1.1}}
    "Ledig"]])


(defn occupied-slot [uid [this-uid status]]
  (let [owner? (= this-uid uid)
        cancelled? (and (map? status) (:cancel status))
        path (if owner? [:r.min-status] [:r.dine-vakter {:id this-uid}])]
    [sc/taken-user-slot2
     {:class    [:h-12 (if cancelled? :cancelled (when owner? :owner))]
      :style    {:color "var(--text1)"
                 :background-color (if owner?
                                     "var(--brand1)"
                                     "var(--toolbar)")}
      :on-click #(rf/dispatch [:app/navigate-to path])}
     (badge-text this-uid owner? cancelled?)]))

(defn command [mobile? uid base section slots-free starttime-key]
  [:div.h-10.flex.items-center
   {:class [(if mobile? :w-8 :w-28)]}
   ;fix: duplicated owner?

   (let [owner? (get-in base [section uid starttime-key])
         path {:uid uid :section section :timeslot starttime-key}]
     (if (or owner? (pos? slots-free))
       (if owner?
         (if (actions/check-can-change? path)
           [(if mobile? button/just-icon button/icon-and-caption)
            {:class    [:danger :padded]
             :on-click #(actions/delete path)}
            ico/trash
            "Avlys"]
           [(if mobile? button/just-icon button/icon-and-caption)
            {:class    [:message :padded]
             :on-click #(actions/frafall path)}
            ico/bytte
            "Bytte"])

         (if mobile?
           [button/just-icon
            {:class    [:cta]
             ;:type     :button
             :on-click #(actions/add path)}
            ico/bytte]
           [button/icon-and-caption
            {:class    [:cta  :flip]
             ;:type     :button
             :on-click #(actions/add path)}
            ico/plus
            "Velg"]))))])



;test

(defn prepare-data [data]
  data)

(defn open-reminder [data]
  (js/alert data))

(defn- send-reminder-action [{:keys [base data]}]
  (let [f (second data)
        [{:keys [dt starttime section]} _group] (first (group-by #(select-keys % [:dt :starttime :section]) f))
        date (t/date dt)
        alle-regs-i-denne-periodegruppen (invert (get base section))
        starttime' (t/at date (t/time starttime))
        distinct-ids (->> alle-regs-i-denne-periodegruppen
                          (filter (fn [[k _v]]
                                    (t/= (t/date (t/date-time (name k))) (t/date date))))
                          (map second)
                          (flatten1)
                          (into {})
                          (remove (comp map? val))
                          (map (comp name key))
                          (distinct))
        phone-numbers-to-call (map (comp :telefon user.database/lookup-userinfo) distinct-ids)

        text-date (str (times.api/day-name starttime') " " (times.api/date-format-sans-year starttime'))
        sms-message (str "sms:/open?addresses="
                         (apply str (interpose "," phone-numbers-to-call))
                         "?&body="
                         (js/encodeURI (str "Husk at du har nøkkelvakt " text-date "...")))]

    [sc/co
     [sc/row-sc-g2
      {:style {:align-items :center
               :height      "3rem"}}
      [sc/co
       [sc/link {:href sms-message} "Send påminnelse om nøkkelvakt " text-date]
       [sc/small "iPad'en kan ikke sende tekstmeldinger, bruk egen telefon."]]]]))

(defn table [{:keys [base data] :as m}]
  (let [show-only-available? @(schpaa.state/listen :calendar/show-only-available)
        mobile? (:mobile? @(rf/subscribe [:lab/screen-geometry]))
        uid @(rf/subscribe [:lab/uid])
        kald-periode? (:kald-periode (user.database/lookup-userinfo uid))
        uid (keyword uid)
        data (filter (fn [[{:keys [slots kald-periode]}]] (and (pos? slots)
                                                               (if kald-periode kald-periode? true))) data)]
    [:<>
     [send-reminder-action m]

     (into [:div.space-y-4]
           (for [[idx each] (map-indexed vector data)
                 ;todo superslow
                 [{:keys [description dt slots section]} group]
                 (group-by #(select-keys % [:dt :description :section :slots :kald-periode]) each)
                 :let [dt (t/date dt)
                       alle-regs-i-denne-periodegruppen (invert (get base section))]]
             ^{:key dt}
             [sc/co
              [week-component dt description]
              (into [:<>]
                    (for [{:keys [starttime endtime]} (sort-by :starttime < group)
                          :let [starttime-key (-> (t/at dt starttime) str keyword)
                                starttime' (str (t/at dt (t/time starttime)))
                                [_ slots-on-this-eykt] (first (filter (fn [[k _v]] (= (name k) starttime'))
                                                                      alle-regs-i-denne-periodegruppen))
                                slots-free (- slots (count #_slots-on-this-eykt
                                                      (remove (comp :cancel second) slots-on-this-eykt)))]
                          :when (if (and (not (= idx 1))
                                         show-only-available?)
                                  (or (pos? slots-free) (get-in base [section uid starttime-key]))
                                  true)]
                      [:div
                       {:style {:margin-left "1rem"
                                :display "flex"
                                :align-items "stretch"}}
                       ;[l/pre slots-on-this-eykt]
                       [sc/row-sc-g4-w {:class []
                                        :style {:width       "100%"
                                                :flex-wrap   :nowrap
                                                :align-items "start"}}
                        [b/co
                         {:class [:w-16]
                          :style {:white-space :nowrap}}

                         [b/ro {:style {:justify-content "space-between"}}
                          [b/text  (t/hour starttime)]
                          [b/text  "—"]
                          [b/text (t/hour endtime)]]]

                        [command mobile? uid base section slots-free starttime-key]

                        ; for every line
                        [:div {;:class [:-debug3 :h-full]
                               :style {:flex "1 0 auto"
                                       :height "100%"
                                       :column-gap "0.5rem"
                                       :row-gap "0.5rem"
                                       :display "grid"
                                       ;:grid-auto-rows "2.5rem"
                                       :grid-template-columns "repeat(auto-fill,minmax(6rem,1fr))"}}
                         (mapcat identity
                                 [(mapv (fn [e] [occupied-slot uid e]) slots-on-this-eykt)
                                  (mapv (fn [] [avail-user-slot uid section starttime-key])
                                        (range slots-free))])]]]))]))]))
