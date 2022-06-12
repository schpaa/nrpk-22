(ns booking.account
  (:require [re-frame.core :as rf]
            [schpaa.style.ornament :as sc]
            [schpaa.debug :as l]
            [db.core :as db]
            [db.signin]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [clojure.string :as str]
            [lambdaisland.ornament :as o]
            [booking.common-widgets :as widgets]
            [user.database]))

(o/defstyled corner-logo' :img
  [:& :absolute :-top-20 :-right-12 :rotate-45 :opacity-20 :w-32])

(defn corner-logo []
  [corner-logo' {:src "/img/logo-n2.jpg"}])

(defn welcome-dlg [{:keys [on-close on-save context]}]
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
      [sc/dialog-title "NRPK"]

      [sc/col-space-2
       [sc/text1 (str "Velkommen " (str/trim (or (:navn user) "")) " — du er logget inn!")]
       (when (:nøkkelvakt user) [sc/text1 "Din nøkkelvaktstatus er " [sc/strong (if (:godkjent user) "Godkjent" "Ikke registrert")]])]

      (when-not (:godkjent user)
        [sc/col-space-2
         [sc/text1 "Hvis du vet at du er godkjent som nøkkelvakt har du sikkert logget med en annen konto. Hvis du vil kan vi slette denne kontoen for deg."]
         [sc/text2 "Kommer du ikke inn via din tidligere tilbyder (Facebook), må du registrere deg på nytt og sende en tilbakemelding til oss (se knapp nederst på alle sider) slik at vi kan godkjenne deg."]
         #_[sc/link {:target "_blank"
                     :href   "https://nrpk.no"} "Sak blir opprettet på nrpk.no"]])

      [:div.flex.justify-between.items-end
       [hoc.buttons/danger {:disabled true
                            :on-click #(on-save)} "Slett konto!"]
       [hoc.buttons/cta {:on-click on-close} "Lukk"]]]]))

(defn deleteaccount-form [{:keys [start selected on-close on-save] :as context}]
  (tap> {:deleteaccount-form context})
  [sc/centered-dialog                                       ;dropdown-dialog'
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
  (tap> "open-dialog-confirmaccountdeletion")
  ;(js/alert "!")
  ;(rf/dispatch-sync [:modal.slideout/close])
  ;(rf/dispatch-sync [:modal.slideout/clear])
  (rf/dispatch [:modal.slideout/toggle
                ;toggle
                true
                ;extra
                {:click-overlay-to-dismiss true
                 :content-fn               deleteaccount-form
                 :context                  (assoc {}
                                             :auto-dismiss false)

                 :action                   #(tap> ["after pressing the primary button"])
                 :on-primary-action        (fn [e] (tap> ["after closing the dialog" (keys e)]))}]))

(rf/reg-event-fx :app/successful-login
                 (fn [_ [_ args]]
                   (tap> {:app/successful-login args})
                   {:fx [[:dispatch [:app/navigate-to [(if args :r.min-status :r.forsiden)]]]
                         [:dispatch [:modal.slideout/toggle
                                     true
                                     {:auto-dismiss             false
                                      :content-fn               welcome-dlg
                                      :on-primary-action        (fn [_] (open-dialog-confirmaccountdeletion))
                                      :context                  args
                                      :action                   #(tap> "calling open-dialog-confirmaccountdeletion")
                                      :click-overlay-to-dismiss true
                                      :on-save                  #()}]]]}))

(rf/reg-event-fx :app/login
                 (fn [_ _] {:fx [[:lab/login-fx nil]]}))

(defn signed-out-message [{:keys [on-close]}]
  [sc/centered-dialog
   {:style {:position   :relative
            :overflow   :hidden
            :z-index    0
            :max-height "80vh"}}
   (corner-logo)
   [sc/col-space-4
    [sc/dialog-title "NRPK"]
    [sc/row-sc-g2
     [sc/col-space-4
      [sc/text1 "Du har logget ut!"]]
     [hoc.buttons/cta {} "Logg inn"]]]])

(rf/reg-event-fx :app/sign-out
                 (fn [_ _]
                   {:fx [[:lab/logout-fx nil]
                         [:dispatch [:modal.slideout/toggle
                                     true
                                     {:action     #(js/alert "!")
                                      :context    "args"
                                      :content-fn #(signed-out-message %)}]]
                         [:dispatch [:app/navigate-to [:r.forsiden]]]]}))


(defn signin-content [{:keys [on-close on-save] :as context}]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [widgets/dialog-template
     "Hvordan vil du logge inn?"
     [:<>
      [sc/surface-a [db.signin/login]]
      [sc/col-space-4
       [sc/text2 "Når du logger inn kan vi med større sikkerhet vite hvem som står bak en identitet og med bakgrunn i dette regulere tilganger."]
       [sc/text1 "Vi kan også tilby deg noe mer funksjonalitet når du er innlogget."]]]
     [sc/row-ec
      [hoc.buttons/regular {:type     "button"
                            :on-click #(on-close)} "Lukk"]]]))

(defn open-dialog-signin []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:click-overlay-to-dismiss false
                 :action                   (fn [{:keys [carry] :as m}] (js/alert m))
                 :content-fn               signin-content}]))

(rf/reg-fx :lab/login-fx
           (fn [_] (open-dialog-signin)))

(rf/reg-fx :lab/logout-fx
           (fn [_] (db.auth/sign-out)))