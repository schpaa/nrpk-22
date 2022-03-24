(ns booking.routes)

(def routes
  [["/" {:name :r.forsiden :header "NRPK"}]
   ["/oversikt" {:name :r.oversikt :header [:r.forsiden "Oversikt"]}]
   ["/wix" {:name :r.forsiden-iframe :header "NRPK (wix)"}]
   ["/nytt" {:name :r.booking-blog :header "Hva er nytt?"}]
   ["/nytt/:id" {:name    :r.booking-blog-doc
                 :header  "Hva er nytt?"
                 :path-fn (fn [r] (some-> r :path-params :id))}]
   ["/nytt-innlegg" {:name :r.booking-blog-new :header "Nytt innlegg"}]
   ["/ny" {:name :r.new-booking :header "Ny booking" :subheader "Forsiden"}]
   ["/debug" {:name :r.debug :header [:r.booking "Booking av båt"]}]
   ["/turlogg" {:name :r.logg :header "Min logg" :subheader "Baksiden"}]
   ["/om-meg" {:name :r.user :header [:r.forsiden "Mine opplysninger"]}]
   ["/velkommen" {:name :r.welcome :header "Om meg" :subheader "Baksiden"}]
   ["/not-found" {:name :r.page-not-found :header [:r.forsiden "Finner ikke siden"]}]
   ["/designsprak" {:name :r.designlanguage :header "Designspråk - mal"}]
   ["/nokkelvakt" {:navn :r.nokkelvakt :header [:r.forsiden "Nøkkelvakt"]}]
   ["/conditions" {:name :r.conditions :header [:r.nokkelvakt "Vilkår"]}]
   ["/terms" {:name :r.terms :header "Betingelser"}]
   ["/filer" {:name :r.fileman-temporary :header "Filer"}]
   ["/kalender" {:name :r.calendar :header "Kalender"}]
   ["/aktivitetsliste" {:name :r.aktivitetsliste :header [:r.forsiden "Aktivitetsliste"]}]

   ["/arshjul" {:name   :r.yearwheel
                :header [:r.forsiden "Årshjul"]}]

   ["/booking" {:name :r.booking :header [:r.forsiden "Sjøbasen"]}]
   ["/booking/oversikt" {:name :r.booking.oversikt :header [:r.booking "Oversikt"]}]
   ["/booking/retningslinjer" {:name :r.booking.retningslinjer :header [:r.booking "Retningslinjer"]}]
   ["/booking/faq" {:name :r.booking.faq :header [:r.booking "Ofte stilte spørsmål"]}]])