(ns user.database
  (:require [db.core :as db]
            [tick.core :as t']
            [schpaa.debug :as l]
            [tick.core :as t]
            [re-frame.core :as rf]
            [clojure.walk :as walk]
    ;extract!
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.ico :as ico]
            [schpaa.style.button :as scb]))

(defn mark-account-for-removal [uid]
  (let [removal-date (t/>> (t/date) (t/new-period 14 :days))
        path ["beskjeder" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date (str removal-date)}})))

(defn mark-account-for-restore [uid]
  (let [removal-date (t/>> (t/date) (t/new-period 14 :days))
        path ["beskjeder" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date nil}})))

(comment
  (let [values (-> data :values)
        uid (-> values :uid)
        values (dissoc values :uid)]
    (db/firestore-set {:path ["users2" uid] :value values})
    (db/database-update {:path ["users" uid] :value values})
    (tap> values)
    st))

(defn lookup-userinfo [uid]
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    @(db/on-value-reaction {:path ["users" uid]})
    nil))

(defn +memo-lookup-username [uid]

  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))
    nil))

#_(def lookup-username (memoize +memo-lookup-username))
(defn lookup-username [uid]
  (when-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))))

(defn lookup-by-litteralkeyid [id]
  (when-some [_ @(rf/subscribe [::db/user-auth])]
    (get (into {}
               (map (fn [[a b c d]] [a [b c d]])
                    (map (comp (juxt (comp #(subs % 4 7) str :nøkkelnummer)
                                     :navn
                                     :telefon
                                     :uid) val)
                         (filter (fn [[k v]] (and (:godkjent v) (not (:utmeldt v)) (seq (:nøkkelnummer v)) #_(:nøkkelvakt v)))
                                 @(db/on-value-reaction {:path ["users"]}))))) id)))

(defn lookup-alias [uid]
  (when-some [_ @(rf/subscribe [::db/user-auth])]
    (let [u @(db/on-value-reaction {:path ["users" uid]})
          alias (if (empty? (:alias u)) nil (:alias u))
          navn (if (empty? (:navn u)) nil (:navn u))]

      (or alias navn uid (str "failed<lookup-alias>'" uid "'")))))


;region

(def request-booking-xf
  (filter (fn [[_ {:keys [request-booking]}]] (some? request-booking))))

(def not-yet-accepted-booking-xf
  (filter (fn [[_ {:keys [booking-godkjent]}]] (true? booking-godkjent))))

(def accepted-booking-xf
  (filter (fn [[_ {:keys [booking-godkjent]}]] (false? booking-godkjent))))

(defn booking-users [& xf]
  (transduce
    (apply comp xf)
    conj
    []
    @(db/on-value-reaction {:path ["users"]})))

(defn locked [field icon-fn path-fn k v]
  [scb/round-normal-listitem
   {:on-click #(db/database-update
                 {:path  (path-fn (name k))
                  :value {field (not (field v))}})}
   [apply sc/icon (icon-fn (field v))]])

(o/defstyled listitem :div
  :flex :justify-between :p-1
  {:padding-block  "var(--size-2)"
   :padding-inline "var(--size-3)"
   :border-radius  "var(--radius-0)"
   :-background    [:rgba 0 0 0 0.05]})

(defn booking-users-listitem [[k {:keys [navn ekspert våttkort booking-godkjent telefon våttkortnr] :as v}]]
  [listitem
   [sc/row-std {:style {:align-items :center}}
    [scb/round-normal-listitem {:on-click #(rf/dispatch [:lab/show-userinfo v])} [sc/icon ico/showdetails]]
    [sc/text1 navn]
    [:div.grow]
    [sc/text1 telefon]
    [locked
     :booking-godkjent
     #(-> [{:style {:color (if % "var(--button)" :blue)}} (if % ico/member ico/member)])
     #(-> ["users" %]) k v]
    [locked
     :ekspert
     #(-> [{:style {:color (if % "var(--button)" :red)}} (if % ico/admin ico/admin)])
     #(-> ["users" %]) k v]]])
