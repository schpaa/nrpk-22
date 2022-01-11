(ns db.signin
  (:require [schpaa.debug :as l]
            [reagent.core :as r]
            ;["react" :as react]
            [db.core :as db]
            [db.auth]
            [db.state :refer [app]]
            [cljs-bean.core :refer [->clj]]
            ["firebase/auth" :refer [getAuth]]
            ["react-firebaseui/StyledFirebaseAuth" :default StyledFirebaseAuth]
            ["firebase/compat/auth"]
            ["firebase/compat/app" :default firebase]
            ["firebaseui"]

            [cljs-bean.core :refer [->clj]]
            [re-frame.core :as rf]
            [applied-science.js-interop :as j]))


(def uiConfig
  {:signInFlow       "popup"
   ;:credentialHelper js/firebase.auth.CredentialHelper.GOOGLE_YOLO
   ;:credentialHelper js/firebaseui.auth.CredentialHelper.GOOGLE_YOLO

   ;:languageCode "no"
   :signInOptions    [(.. (.-auth firebase) -GoogleAuthProvider -PROVIDER_ID)
                      (.. (.-auth firebase) -FacebookAuthProvider -PROVIDER_ID)
                      (.. (.-auth firebase) -EmailAuthProvider -PROVIDER_ID)
                      {:provider             (.. #_(getAuth) (.-auth firebase) -PhoneAuthProvider -PROVIDER_ID)
                       :defaultCountry       "NO"
                       :whitelistedCountries ["NO", "+47"]}]
   :tosUrl           "terms"
   :privacyPolicyUrl "privacy"
   :callbacks        {:signInSuccessWithAuthResult
                      (fn callbakky [authResult x]
                        (some-> authResult
                                (.-user)
                                ^js (.getIdTokenResult)
                                (.then
                                  (fn [tokenResult]
                                    (tap> ["login " (->clj tokenResult)])
                                    (rf/dispatch [:successful-login (->clj tokenResult.claims)]))))
                        false)}})

(defn login []
  [:div
   ;[l/ppre @(rf/subscribe [::db/user-auth])]
   ;[:div "LOGIN"]
   ;[l/ppre (.-config @app)]
   (when @app
     [:> StyledFirebaseAuth
      {:firebaseAuth (getAuth);(.auth firebase)
       ;(getAuth); (.auth @app) ;(.-auth firebase)
       :uiCallback   (fn [ui]
                       ^js (.disableAutoSignIn ui)
                       ;(log/info "ui.isPendingRedirect " ^js (.isPendingRedirect ui))
                       false)
       :uiConfig     uiConfig}])])

