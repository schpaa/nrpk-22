(ns booking.routes)

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

   ["/om-meg" {:name :r.user :shorttitle "Meg" :header [:r.oversikt "Mine opplysninger"]}]

   ["/tilstede" {:name       :r.presence
                 :shorttitle "Tilstede"
                 :header     [:r.forsiden "Tilstede"]
                 :access     [[:member] #{:admin}]}]
   ["/brukere" {:name       :r.users
                :shorttitle "Brukere"
                :header     [:r.forsiden "Brukere"]
                :access     [[:member] #{:admin}]}]

   ["/velkommen" {:name :r.welcome :header "Om meg" :subheader "Baksiden"}]
   ["/not-found" {:name :r.page-not-found :header [:r.oversikt "Finner ikke siden"]}]
   ["/designsprak" {:name :r.designlanguage :header "Designspråk - mal"}]

   ["/nokkelvakt" {:name :r.nokkelvakt :shorttitle "Vakt" :header [:r.oversikt "Nøkkelvakt"]}]
   ["/admin" {:name :r.admin :header [:r.oversikt "Admin"] :access [[:member] #{:admin}]}]
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

   ["/booking" {:name :r.booking :shorttitle "Sjøbasen" :header [:r.oversikt "Sjøbasen"]}]
   ["/booking/oversikt" {:name :r.booking.oversikt :header [:r.booking "Oversikt"]}]
   ["/booking/retningslinjer" {:name :r.booking.retningslinjer :header [:r.booking "Retningslinjer"]}]
   ["/booking/faq" {:name :r.booking.faq :header [:r.booking "Ofte stilte spørsmål"]}]

   ["/oversikt/organisasjon" {:name :r.oversikt.organisasjon :header [:r.oversikt "Om NRPK"]}]
   ["/oversikt/styret" {:name :r.oversikt.styret :header [:r.oversikt "Styret i NRPK"]}]])