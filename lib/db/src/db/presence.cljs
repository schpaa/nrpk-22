(ns db.presence
  (:require [re-frame.core :as rf]
            ["firebase/database" :as rtdb :refer [onDisconnect getDatabase ref onValue push set goOffline goOnline serverTimestamp]]
            ;[app.config :refer [version]]
            ;[app.system.database.rtdb :as rtdb]
            ;[app.system.database.common :refer [database-ref]]
            [cljs-time.core :as t]
            #_[schpaa.lib.debug :as l]))

(defn presence [uid]
  (let [myConnectionsRef (ref (getDatabase) (str "presence/" uid "/connections"))
        lastOnlineRef (ref (getDatabase) (str "presence/" uid "/lastOnline"))
        connectedRef (ref (getDatabase) ".info/connected")]
    (onValue connectedRef
             (fn [^js snap]
               (tap> (.val snap))
               (when (.val snap)
                 (let [con (push myConnectionsRef)]
                   (-> (onDisconnect con) .remove)
                   (rtdb/set con (serverTimestamp))
                   (-> (onDisconnect lastOnlineRef)
                       (.set (serverTimestamp)))))))))

;region discarded
