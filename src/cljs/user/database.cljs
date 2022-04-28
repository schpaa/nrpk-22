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
        path ["users" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date (str removal-date)}})))

(defn mark-account-for-restore [uid]
  (let [removal-date (t/>> (t/date) (t/new-period 14 :days))
        path ["users" uid]]
    (db/database-update
      {:path  path
       :value {:removal-date nil}})))


(defn write [diff {:keys [uid values] :as m}]
  (let [tm (str (t/instant))
        uid (-> m :uid)
        values (-> m
                   (dissoc :uid)
                   (assoc :timestamp tm
                          :version 2))]
    (db/firestore-set {:path ["users3" uid "log" tm] :value (walk/stringify-keys diff)})
    (db/database-update {:path ["users" uid] :value values}))
  #_(let [id (.-key (db/database-push {:path  path
                                       :value (assoc value
                                                :uid uid
                                                :version 2
                                                :timestamp (str (t'/instant)))}))]
      ;intent Perform a backup to the users doc
      (db/firestore-set {:path (conj path uid "trans" id) :value value})
      id))


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
  ;(tap> ["U"])
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))
    nil))

#_(def lookup-username (memoize +memo-lookup-username))
(defn lookup-username [uid]
  ;(tap> ["U"])
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:navn @(db/on-value-reaction {:path ["users" uid]}))
    nil))

(defn lookup-alias [uid]
  ;(tap> ["U"])
  (if-some [_ @(rf/subscribe [::db/user-auth])]
    (:alias @(db/on-value-reaction {:path ["users" uid]}))
    nil))

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
