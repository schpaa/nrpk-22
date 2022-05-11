(ns db.auth
  (:require ["firebase/auth" :refer [getAuth onAuthStateChanged signOut
                                     onIdTokenChanged]]
            [db.presence :refer [presence]]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn- user->data [^js user]
  (when user
    {:email        (.-email user)
     :photo-url    (.-photoURL user)
     :uid          (.-uid user)
     :display-name (.-displayName user)}))

(defn user-info []
  ;works!
  (let [auth-state (r/atom nil)
        callback (fn [user]

                   (let [user-data (user->data user)]
                     ;todo update presence data just as a sideeffect to aquiring the users status
                     (when (not= user-data @auth-state)
                       (if (nil? user-data)
                         (do
                           (presence (:uid @auth-state))
                           (reset! auth-state user-data))
                         (if (nil? @auth-state)
                           (do
                             (reset! auth-state user-data)
                             (presence (:uid user-data))))))))


        error-callback (fn [x] (reset! auth-state x))]
    (onAuthStateChanged  #_onIdTokenChanged (getAuth) callback error-callback)
    auth-state))

(defn sign-out []
  (signOut (getAuth))
  #_(.reload js/window.location true)
  #_(rf/dispatch [:app/navigate-to [:r.forsiden]]))


