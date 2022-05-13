(ns booking.routes
  (:require [booking.ico :as ico]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [reitit.core :as reitit]
            [re-frame.core :as rf]
            [user.database]))

(def routes
  [["/"
    {:name       :r.forsiden
     :shorttitle [:r.oversikt "Forsiden"]
     :header     [[:r.oversikt] "Forsiden"]}]

   ["/signed-out"
    {:name   :r.signedout
     :header [[:r.forsiden] "Du har logget ut"]}]

   ["/oversikt"
    {:name       :r.oversikt
     :shorttitle "Oversikt"
     :header     [[:r.forsiden] "Oversikt"]}]

   ["/wix"
    {:name   :r.forsiden-iframe
     :header [[:r.forsiden] "NRPK (wix)"]}]

   ["/nytt"
    {:name   :r.booking-blog
     :access [[:member] nil]
     :header [[:r.forsiden] "Hva er nytt?"]}]

   ["/nytt/:id"
    {:name     :r.booking-blog-doc
     :xpath-fn (fn [r] (some-> r :path-params :id))
     :header   [[:r.booking-blog] "Eksempel"]}]

   ["/nytt-innlegg"
    {:name   :r.booking-blog-new
     :header [[:r.forsiden] "Nytt innlegg"]}]

   ["/ny"
    {:name      :r.new-booking
     :subheader "Forsiden"
     :header    [[:r.forsiden] "Ny booking"]}]

   ["/debug"
    {:name   :r.debug
     :header [[:r.booking] "Booking av båt"]}]

   ["/turlogg"
    {:name   :r.logg
     :header [[:r.forsiden] "Min logg"]}]

   ["/mine-opplysninger"
    {:name       :r.user
     :shorttitle "Meg"
     :header     [[:r.min-status] "Mine opplysninger"]}]

   ["/dine-vakter/:id"
    {:name       :r.dine-vakter
     :shorttitle "Dine vakter"
     :access     [[:member] #{:admin :nøkkelvakt}]
     :header     [[:r.nokkelvakt] (fn [{:keys [id] :as m}]
                                    (:navn (user.database/lookup-userinfo id) "not found"))]}]

   ["/min-status"
    {:name   :r.min-status :shorttitle "Min status"
     :header [[:r.user] "Min status"]}]

   ["/tilstede"
    {:name       :r.presence
     :shorttitle "Tilstede"
     :access     [[:member] #{:admin}]
     :header     [[:r.users] "Tilstede"]}]

   ["/brukere"
    {:name       :r.users
     :shorttitle "Brukere"
     :access     [[:member] #{:admin}]
     :header     [[:r.presence] "Brukere"]}]

   ["/velkommen"
    {:name   :r.welcome
     :header [[:r.forsiden] "Om meg"]}]

   ["/vaktkalender"
    {:name   :r.nokkelvakt :shorttitle "Vaktkalender"
     :access [[:registered :waitinglist :member] #{:admin :nøkkelvakt}]
     :header [[:r.min-status] "Vaktkalender"]}]

   ["/conditions"
    {:name   :r.conditions
     :header [[:r.nokkelvakt] "Vilkår"]}]

   ["/terms"
    {:name   :r.terms
     :header [[:r.forsiden] "Betingelser"]}]

   ["/filer"
    {:name       :r.fileman-temporary
     :shorttitle "Filer"
     :header     [[:r.booking-blog] "Filer"]}]

   ["/kalender"
    {:name   :r.calendar
     :access [[:member2] #{:admin2}]
     :header [[:r.forsiden] "Kalender"]}]

   ["/aktivitetsliste"
    {:name   :r.aktivitetsliste
     :access [[:member] #{:nøkkelvakt}]
     :header [[:r.oversikt] "Aktivitetsliste"]}]

   ["/arshjul"
    {:name       :r.yearwheel
     :shorttitle "Årshjul"
     :modify     [nil #{:admin}]
     :access     [[:registered :waitinglist :member] #{}]
     :header     [[:r.forsiden] "Årshjul"]}]

   ["/utlan"
    {:name       :r.utlan
     :shorttitle "Utlånslogg"
     :access     [[:member] #{}]
     :header     [[:r.båtliste.nøklevann] "Utlånslogg på Nøklevann"]}]

   ["/booking"
    {:name       :r.booking
     :shorttitle "Sjøbasen"
     :icon       ico/booking
     :access     [[:member] #{:admin}]
     :header     [[:r.båtliste.sjøbasen] "Utlånslogg på sjøbasen"]}]

   ["/booking/oversikt"
    {:name   :r.booking.oversikt
     :header [[:r.booking] "Oversikt"]}]

   ["/booking/retningslinjer"
    {:name   :r.booking.retningslinjer
     :header [[:r.booking] "Retningslinjer"]}]

   ["/booking/faq"
    {:name   :r.booking.faq
     :header [[:r.booking] "Ofte stilte spørsmål"]}]

   ["/oversikt/organisasjon"
    {:name   :r.oversikt.organisasjon
     :header [[:r.oversikt] "Om NRPK"]}]

   ["/oversikt/styret"
    {:name   :r.oversikt.styret
     :header [[:r.oversikt] "Styret i NRPK"]}]

   ["/dokumenter/:id"
    {:name       :r.dokumenter
     :shorttitle "Dokumenter"
     ;:access     [[] #{}]
     :header     [[:r.oversikt] "Dokumenter"]}]

   ["/rapporter/:id"
    {:name       :r.reports
     :shorttitle "Rapporter"
     :access     [[:member] #{:admin}]
     :header     [[:r.users] "Rapporter"]}]

   ["/batliste/noklevann"
    {:name   :r.båtliste.nøklevann
     :header [[:r.utlan] "Båtliste"]}]

   ["/batliste/sjobasen"
    {:name   :r.båtliste.sjøbasen
     :header [[:r.booking] "Båtliste"]}]

   ["/vaktrapport"
    {:name   :r.mine-vakter-ipad
     :header [[:r.båtliste.nøklevann] "Vaktrapport"]}]

   ["/not-found"
    {:name   :r.page-not-found
     :header [[:r.oversikt] "Finner ikke siden"]}]])

(defn shortcut-link [route-name]
  (let [{icon :icon caption :shorttitle} (some->> route-name
                                                  (reitit/match-by-name (reitit/router routes))
                                                  :data)]
    [hoc.buttons/reg-pill-icon
     {:on-click #(rf/dispatch [:app/navigate-to [:r.booking]])}
     icon
     caption]))

(comment
  (do
    (map (comp :header second) routes)))