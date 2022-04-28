(ns booking.routes
  (:require [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [user.database]
            [schpaa.debug :as l]))

(def routes
  [["/" {:name       :r.forsiden
         :header     [:r.oversikt "Forsiden"]
         :shorttitle "Forsiden"}]

   ["/signed-out" {:name   :r.signedout
                   :header [:r.forsiden "Du har logget ut"]}]

   ["/oversikt" {:name       :r.oversikt
                 :header     [:r.forsiden "Oversikt"]
                 :shorttitle "Oversikt"}]

   ["/wix" {:name :r.forsiden-iframe :header "NRPK (wix)"}]
   ["/nytt" {:name :r.booking-blog :header [:r.forsiden "Hva er nytt?"] :access [[:member] nil]}]
   ["/nytt/:id" {:name     :r.booking-blog-doc
                 :header   [:r.booking-blog "Eksempel"]
                 :xpath-fn (fn [r] (some-> r :path-params :id))}]
   ["/nytt-innlegg" {:name :r.booking-blog-new :header "Nytt innlegg"}]
   ["/ny" {:name :r.new-booking :header "Ny booking" :subheader "Forsiden"}]
   ["/debug" {:name :r.debug :header [:r.booking "Booking av båt"]}]
   ["/turlogg" {:name :r.logg :header "Min logg" :subheader "Baksiden"}]

   ["/om-meg" {:name   :r.user :shorttitle "Meg"
               :access [[:waitinglist :member :registered] #{}]
               :header [:r.oversikt "Mine opplysninger"]}]
   ["/dine-vakter/:id" {:name       :r.dine-vakter
                        :shorttitle "Dine vakter"
                        :access     [[:member] #{:admin :nøkkelvakt}]
                        :header     [:r.nokkelvakt (fn [{:keys [id] :as m}]
                                                     (let [user (user.database/lookup-userinfo id)]
                                                       (or (:navn user) (str m))))]}]
   ["/mine-vakter" {:name :r.mine-vakter :shorttitle "Mine vakter" :header [:r.user "Mine vakter"]}]

   ["/tilstede" {:name       :r.presence
                 :shorttitle "Tilstede"
                 :header     [:r.forsiden "Tilstede"]
                 :access     [[:member] #{:admin}]}]
   ["/admin" {:name       :r.users
              :shorttitle "Admin"
              :header     [:r.oversikt "Administrasjon"]
              :access     [[:member] #{:admin}]}]

   ["/velkommen" {:name :r.welcome :header "Om meg" :subheader "Baksiden"}]
   ["/not-found" {:name :r.page-not-found :header [:r.oversikt "Finner ikke siden"]}]
   ["/designsprak" {:name :r.designlanguage :header "Designspråk - mal"}]

   ["/vaktkalender" {:name   :r.nokkelvakt :shorttitle "Vaktkalender"
                     :access [[:registered :waitinglist :member] #{:admin :nøkkelvakt}]
                     :header [:r.mine-vakter "Vaktkalender"]}]
   ;["/admin" {:name :r.admin :header [:r.oversikt "Admin"] :access [[:member] #{:admin}]}]
   ["/conditions" {:name :r.conditions :header [:r.nokkelvakt "Vilkår"]}]
   ["/terms" {:name :r.terms :header "Betingelser"}]
   ["/filer" {:name       :r.fileman-temporary
              :shorttitle "Filer"
              :header     [:r.booking-blog "Filer"]}]
   ["/kalender" {:name :r.calendar :header "Kalender" :access [[:member] #{:nøkkelvakt}]}]
   ["/aktivitetsliste" {:name   :r.aktivitetsliste
                        :header [:r.oversikt "Aktivitetsliste"]
                        :access [[:member] #{:nøkkelvakt}]}]

   ["/arshjul" {:name       :r.yearwheel
                :header     [:r.forsiden "Årshjul"]
                :shorttitle "Årshjul"
                :modify     [nil #{:admin}]
                :access     [[:registered :waitinglist :member] #{}]}]

   ;["/nokkelvakt" {:name :r.nøkkelvakt :header [:r.oversikt "Nøkkelvakt"]}]

   ["/utlan" {:name       :r.utlan
              :shorttitle "Utlånslogg"
              :access     [[:member] #{}]
              :header     [:r.båtliste.nøklevann "Utlånslogg på Nøklevann"]}]

   ["/booking" {:name       :r.booking
                :shorttitle "Sjøbasen"
                :icon       ico/booking
                :access     [[:member] #{:admin}]
                :header     [:r.båtliste.sjøbasen "Utlånslogg på sjøbasen"]}]
   ["/booking/oversikt" {:name   :r.booking.oversikt
                         :header [:r.booking "Oversikt"]}]
   ["/booking/retningslinjer" {:name :r.booking.retningslinjer :header [:r.booking "Retningslinjer"]}]
   ["/booking/faq" {:name :r.booking.faq :header [:r.booking "Ofte stilte spørsmål"]}]

   ["/oversikt/organisasjon" {:name :r.oversikt.organisasjon :header [:r.oversikt "Om NRPK"]}]
   ["/oversikt/styret" {:name :r.oversikt.styret :header [:r.oversikt "Styret i NRPK"]}]
   ["/dokumenter/:id"
    {:name       :r.dokumenter
     :shorttitle "Dokumenter"
     ;:access     [[] #{}]
     :header     [:r.oversikt "Dokumenter"]}]
   ["/rapporter/:id"
    {:name       :r.reports
     :shorttitle "Rapporter"
     :access     [[:member] #{:admin}]
     :header     [:r.users "Rapporter"]}]

   ["/batliste/noklevann"
    {:name   :r.båtliste.nøklevann
     :header [:r.utlan "Båtliste"]}]
   ["/batliste/sjobasen"
    {:name   :r.båtliste.sjøbasen
     :header [:r.booking "Båtliste"]}]
   ["/vaktrapport"
    {:name   :r.mine-vakter-ipad
     :header [:r.båtliste.nøklevann "Vaktrapport"]}]])


(defn shortcut-link [route-name]
  (let [{icon :icon caption :shorttitle} (some->> route-name
                                                  (reitit/match-by-name (reitit/router routes))
                                                  :data)]
    [hoc.buttons/reg-pill-icon
     {:on-click #(rf/dispatch [:app/navigate-to [:r.booking]])}
     icon
     caption]))