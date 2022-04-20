(ns db.signin
  (:require [schpaa.debug :as l]
            [reagent.core :as r]
    ;["react" :as react]
            [db.core :as db]
            [db.auth]
            [db.state]
            [cljs-bean.core :refer [->clj]]
            [re-frame.core :as rf]
            ["firebaseui"]
            ["firebase/auth" :refer [getAuth RecaptchaVerifier]]
            ["react-firebaseui/StyledFirebaseAuth" :default StyledFirebaseAuth]
    ;["firebase/compat/app$default" :as app]
            ["firebase/compat/app$default" :default firebase]
            ["firebase/compat/auth"]
            [applied-science.js-interop :as j]))


(def uiConfig
  (clj->js {:signInFlow         "popup"
            ;:credentialHelper "googleyolo";js/firebase.auth.CredentialHelper.GOOGLE_YOLO
            ;:credentialHelper js/firebaseui.auth.CredentialHelper.GOOGLE_YOLO

            ;:languageCode "no"
            :signInOptions      [(.. (.-auth firebase) -GoogleAuthProvider -PROVIDER_ID)
                                 ;(.. (.-auth firebase) -FacebookAuthProvider -PROVIDER_ID)
                                 (.. (.-auth firebase) -EmailAuthProvider -PROVIDER_ID)
                                 #_(.. (.-auth firebase) -PhoneAuthProvider -PROVIDER_ID)
                                 {:provider
                                  (.. (.-auth firebase) -PhoneAuthProvider -PROVIDER_ID)
                                  :recaptchaParameters  {:type  "image"
                                                         :size  "invisible"
                                                         :badge "bottomleft"
                                                         #_#_:callback (fn [response]
                                                                         (js/alert response))}
                                  :defaultCountry       "NO"
                                  :whitelistedCountries ["NO", "+47"]}]
            :tosUrl             "terms"
            :privacyPolicyUrl   "privacy"
            :container-selector "#firebaseui-auth-container"
            :callbacks          {:signInSuccessWithAuthResult
                                 (fn callbakky [authResult x]
                                   (some-> authResult
                                           (.-user)
                                           ^js (.getIdTokenResult)
                                           (.then
                                             (fn [tokenResult]
                                               (tap> ["login " (->clj tokenResult)])
                                               (rf/dispatch [:app/successful-login (->clj tokenResult.claims)]))))
                                   false)}}))
;RecaptchaVerifier
(defn login []
  [:div #_{:class [:-debug]
           :style {:height "600px"}}
   ;[l/ppre @(rf/subscribe [::db/user-auth])]
   ;[:div "LOGIN"]
   ;[l/ppre (.-config @app)]
   (when @db.state/app
     (let [a (getAuth)]

       #_(set! (.. js/window -recaptchaVerifier)
               (new RecaptchaVerifier "firebaseui-recaptcha-container"
                    {:size "normal"
                     #_#_:callback (fn [response])}
                    a))

       #_(set! (.. a -languageCode) "no")
       ;(js/alert (.-languageCode a))
       [:> StyledFirebaseAuth
        {:firebaseAuth a                                    ;(getAuth)                             ;(.auth firebase)
         ;(getAuth); (.auth @app) ;(.-auth firebase)
         :uiCallback   (fn [ui]
                         ^js (.disableAutoSignIn ui)
                         ;(log/info "ui.isPendingRedirect " ^js (.isPendingRedirect ui))
                         false)
         :uiConfig     uiConfig}]))])

(comment
  (do
    (new RecaptchaVerifier "test" {} (getAuth))))