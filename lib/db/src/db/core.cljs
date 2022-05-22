(ns db.core
  (:require [re-frame.core :as rf]
            [db.rtdb]
            [db.auth]
            [db.state]
            [db.fsdb]
            [cljs-time.core :as t]
            [cljs-time.coerce]
            ["firebase/app" :refer [initializeApp]]
            ["firebase/firestore" :refer [getFirestore connectFirestoreEmulator]]
            ["firebase/storage" :refer [getStorage connectStorageEmulator]]
            ["firebase/database" :refer [getDatabase connectDatabaseEmulator]]
            [reagent.core :as r]))


(def debug? ^boolean js/goog.DEBUG)

(def database-set db.rtdb/ref-set)
(def database-update db.rtdb/ref-update)
(def database-update-increment db.rtdb/database-update-increment)
(def database-push db.rtdb/ref-push)
(def database-get db.rtdb/ref-get)
(def on-value-reaction db.rtdb/on-value-reaction)
(def on-snapshot-docs-reaction db.fsdb/on-snapshot-docs-reaction)
(def on-snapshot-doc-reaction db.fsdb/on-snapshot-doc-reaction)
(def sign-out db.auth/sign-out)
(def firestore-set db.fsdb/firestore-set)
(def firestore-add db.fsdb/firestore-add)

(defn timestamp [m] (assoc m :timestamp (str (t/now))))

(defn init! [{:keys [config]}]
  (.log js/console "db/init!")
  (reset! db.state/app (initializeApp (clj->js config)))
  (when debug?
    (connectDatabaseEmulator (getDatabase) "localhost" 9009)
    (connectStorageEmulator (getStorage) "localhost" 9199)
    (connectFirestoreEmulator (getFirestore) "localhost" 8080)))

(rf/reg-sub ::presence-status
            (fn [_]
              (on-value-reaction {:path ["presence"]}))
            (fn [input _]
              (let [data (-> (fn [a [k v]]
                               (if-let [c (:connections v)]
                                 (assoc a k
                                          {:connections (count c)
                                           ;todo wtf ugh?
                                           :ugh         (val (first c))})
                                 (assoc a k {:lastOnline (cljs-time.coerce/from-long (:lastOnline v))})))
                             (reduce {} (seq input)))]
                {:offline (filterv (comp :lastOnline val) data)
                 :online  (filterv (comp :connections val) data)})))

(rf/reg-event-db ::set-fake-user (fn [db [_ arg]]
                                   {:pre [(or (nil? arg) (map? arg))]}
                                   (assoc db ::fake-user arg)))

(rf/reg-sub ::fake-user (fn [db] (get db ::fake-user)))

(rf/reg-sub ::user-auth
            db.auth/user-info
            (fn [user _]
              (if (or (not user)
                      (instance? js/Error user))
                nil
                user)))

(rf/reg-sub ::root-auth
            :<- [::user-auth]
            :<- [::fake-user]
            (fn [[ua fu] [_ field]]
              (let [u (or fu ua)]
                (case field
                  :uid (:uid u)
                  u))))
