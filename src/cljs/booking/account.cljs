(ns booking.account
  (:require [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [db.core :as db]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

(declare xx signed-out-message open-dialog-signin)

(rf/reg-fx :lab/login-fx
           (fn [_] (open-dialog-signin)))

(rf/reg-fx :lab/logout-fx
           (fn [_] (db.auth/sign-out)))

(rf/reg-event-fx :app/successful-login
                 (fn [_ [_ args]]
                   {:fx [[:dispatch [:modal.slideout/toggle
                                     true
                                     {:action     #()
                                      :context    args
                                      :content-fn #(xx %)}]]]}))

(rf/reg-event-fx :app/login
                 (fn [_ _] {:fx [[:lab/login-fx nil]]}))

(rf/reg-event-fx :app/sign-out
                 (fn [_ _]
                   {:fx [[:lab/logout-fx nil]
                         [:dispatch [:modal.slideout/toggle
                                     true
                                     {:action     #(js/alert "!")
                                      :context    "args"
                                      :content-fn #(signed-out-message %)}]]
                         [:dispatch [:app/navigate-to [:r.forsiden]]]]}))

;;;

(defn corner-logo []
  [:div.absolute.-top-8.-right-8.rotate-45.opacity-20
   [:img.w-32.h-32 {:src "/img/logo-n.png"}]])

(defn signed-out-message [{:keys [on-close]}]
  [sc/centered-dialog
   {:style {:position   :relative
            :overflow   :hidden
            :z-index    10
            :max-height "80vh"}}
   (corner-logo)
   [sc/col-space-8
    [sc/dialog-title' "NRPK"]
    [sc/col-space-4
     [sc/text1 "Du har logget ut!"]]]])

(defn xx [{:keys [on-close context]}]
  (let [uid @(rf/subscribe [:lab/uid])
        user (user.database/lookup-userinfo uid)]
    [sc/centered-dialog
     {:style {:position   :relative
              :overflow   :hidden
              :z-index    10
              :max-height "80vh"}
      :class []}
     (corner-logo)
     [sc/col-space-8
      [sc/dialog-title' "NRPK"]

      #_[sc/col-space-4
         [sc/text1 {:style {:font-family "Merriweather"
                            :line-height "var(--font-lineheight-4)"}
                    :class [:clear-left]} "Alle presanger som tenkes kan, ble laget den kvelden. Snart har han fødselsdag, tenkte dyrene mens de jobbet. Snart ... Hvis de kunne kvekke eller synge, så kvekket eller sang de, men veldig veldig stille: \u00abSnart, snart, ja, snart ...\u00bb Slik var kvelden før ekornets fødselsdag."]
         [sc/subtext1 "Toon Tellegen"]]

      [sc/col-space-2
       [sc/text1 "Du er logget inn, velkommen!"]
       [sc/text1 "Din nøkkelvaktstatus er " [sc/strong (if (:godkjent user) "Godkjent" "Ikke godkjent")]]]

      #_[l/ppre-x uid user]
      (when-not (:godkjent user)
        [sc/col-space-2
         [sc/text1 "Hvis du vet at du er godkjent som nøkkelvakt har du sikkert logget med en annen konto."]
         [sc/text1 "Kommer du ikke inn via din tidligere tilbyder (Facebook), må du registrere deg på nytt og sende en tilbakemelding (nederst på siden) slik at vi raskt kan godkjenne deg."]
         [sc/link {:target "_blank"
                   :href   "https://nrpk.no"} "Sak blir opprettet på nrpk.no"]])

      [sc/row-ec
       [hoc.buttons/cta {:on-click on-close} "Ok"]]]]))

;delete account

(defn deleteaccount-form [{:keys [start selected on-close on-save] :as context}]
  [sc/dropdown-dialog
   [sc/col {:class [:space-y-8]}
    [sc/col {:class [:space-y-2 :w-full]}
     [sc/dialog-title "Slett konto"]
     [sc/subtext-p "Kontoen din blir markert som inaktiv og du vil bli logget ut på alle enheter. Etter 14 dager slettes alle data relatert deg."]
     [sc/text-p "Er du sikker på at du vil slette kontoen din?"]]
    [sc/row-ec
     [hoc.buttons/regular {:class    [:transition-none]
                           :type     "button"
                           :on-click #(on-close)} "Nei"]
     [hoc.buttons/danger {:class    [:transition-none]
                          :type     "button"
                          :on-click #(on-save)} "Ja, slett!"]]]])

(defn open-dialog-confirmaccountdeletion []
  (rf/dispatch [:modal.slideout/toggle true
                {:click-overlay-to-dismiss true
                 :content-fn               deleteaccount-form
                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Slett konto"]]
                 :action                   #(tap> ["after pressing the primary button"])
                 :on-primary-action        (fn [e] (tap> ["after closing the dialog" (keys e)]))}]))

(defn signin-content [{:keys [on-close on-save] :as context}]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [sc/dropdown-dialog
     [:div.pb-8
      [sc/col-space-4
       [sc/title1 "Hvordan vil du logge inn?"]]
      [sc/col
       [db.signin/login]]
      [sc/col-space-4
       [sc/text2 "Når du logger inn kan vi med større sikkerhet vite hvem som står bak en identitet og med bakgrunn i dette regulere tilganger."]
       [sc/text1 "Vi kan også tilby deg noe mer funksjonalitet når du er innlogget."]]]
     [sc/row-ec
      [hoc.buttons/regular {:type     "button"
                            :on-click #(on-close)} "Lukk"]]]))

(defn open-dialog-signin []
  (rf/dispatch [;:lab/modaldialog-visible
                :modal.slideout/toggle
                true
                {:click-overlay-to-dismiss false
                 :action                   (fn [{:keys [carry] :as m}] (js/alert m))
                 :content-fn               signin-content}]))
