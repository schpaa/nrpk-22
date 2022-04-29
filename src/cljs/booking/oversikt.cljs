(ns booking.oversikt
  (:require [schpaa.style.ornament :as sc]
            [booking.common-widgets :as widgets]))

;todo generate links from the route instead of this laborious piece of eloquence
;https://english.stackexchange.com/questions/426378/rhetoric-vs-eloquence
(defn render [r]
  (let [f (fn [[a b]]
            (if (nil? b)
              (let [{:keys [link text]} (booking.common-views/lookup-page-ref-from-name a)]
                [sc/link {:href (kee-frame.core/path-for [link])} text])
              (if (vector? a)
                [sc/subtext-with-link {:href (kee-frame.core/path-for a)} b]
                [sc/subtext-with-link {:href (kee-frame.core/path-for [a])} b])))]
    (fn [r]
      [sc/col-space-1

       (-> "`Under arbeid – og du kan bidra! Send en tilbakemelding (se nederst på siden)`" schpaa.markdown/md->html sc/markdown)

       (widgets/disclosure {:links 1
                            :style {:padding-block "var(--size-2)"
                                    :margin-left   "var(--size-5)"}}
                           :oversikt/nrpk
                           "Nøklevann ro– og padleklubb"
                           [:div {:style {:color          "var(--text2)"
                                          :font-size      "var(--font-size-3)"
                                          :line-height    "var(--font-lineheight-4)"
                                          :font-weight    "var(--font-weight-3)"
                                          :-margin-bottom "var(--size-4)"}}
                            [:img.w-24.h-24.float-right.m-4.xmt-12 {:src "/img/logo-n.png"}]
                            [:span.clear-left "Medlemmer kan benytte klubbens materiell på Nøklevann. De som har våttkort grunnkurs hav har også tilgang til Sjøbasen som er selvbetjent og åpent året rundt. Nøklevann er betjent av nøkkelvakter og har derfor sesong\u00adbasert åpningstid."]]
                           [sc/row-sc-g4-w
                            (let [data [[3 :r.oversikt.organisasjon "Historie"]
                                        [5 [:r.dokumenter {:id "hms-håndbok"}] "HMS-Håndbok"]
                                        [4 :r.oversikt.styret "Styret"]]]

                              (map (comp f rest) (sort-by first data)))])

       #_[sc/fp-summary-detail-always-show-links
          :oversikt/nrpk
          ;todo: find a way to read the summary/details construct (without involving external state) and present [sc/icon ico/nextImage] instead
          "Nøklevann ro– og padleklubb"
          [:div
           [:img.w-24.h-24.float-right.m-4.xmt-12 {:src "/img/logo-n.png"}]
           [:span.clear-left "Medlemmer kan benytte klubbens materiell på Nøklevann. De som har våttkort grunnkurs hav har også tilgang til Sjøbasen som er selvbetjent og åpent året rundt. Nøklevann er betjent av nøkkelvakter og har derfor sesong\u00adbasert åpningstid."]]
          [sc/row-sc-g4-w
           (let [data [[3 :r.oversikt.organisasjon "Historie"]
                       [5 [:r.dokumenter {:id "hms-håndbok"}] "HMS-Håndbok"]
                       [4 :r.oversikt.styret "Styret"]]]

             (map (comp f rest) (sort-by first data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/bli-medlem
        "Bli medlem"
        [:div "Forslag til ingress?"]
        [sc/row-sc-g4-w
         (let [data [;[1 :r.forsiden "Registrer deg her"]
                     [2 :r.user "Mine opplysninger"]
                     #_[3 :r.forsiden "Hva årskontigenten dekker"]]]
           (map (comp f rest) (sort-by first data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/nøklevann
        "Nøklevann"
        "Forslag til ingress?"
        [sc/row-sc-g4-w
         (let [data [[1 [:r.dokumenter {:id "hms-håndbok"}] "HMS ved Nøklevann"]
                     [2 :r.båtliste.nøklevann]
                     [3 :r.utlan]
                     [4 [:r.dokumenter {:id "tidslinje-forklaring"}] "Ofte stilte spørsmål"]]]
           (map (comp f rest) (sort-by first data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/Sjøbasen
        "Sjøbasen"
        "Sjøbasen er for medlemmer som har «Våttkort grunnkurs hav». Sjøbasen er selvbetjent, holder til på Ormsund Roklub og du må booke utstyr her."
        [sc/row-sc-g4-w
         (let [data [[1 [:r.dokumenter {:id "kommer"}] "HMS ved Sjøbasen"]
                     [2 :r.booking]
                     [3 :r.booking.retningslinjer]
                     [4 :r.båtliste.sjøbasen]
                     [5 :r.booking.faq]]]
           (map (comp f rest) (sort-by first data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/Livredningskurs
        "Kurs"
        #_"NRPK arrangerer hvert år livredningskurs med instruktør fra Norges Livredningsselskap. Kurset varer ca 2,5 timer og holdes på Holmlia bad, hvor klubben også har sine bassengtreninger med kajakk."
        "NRPK arrangerer ulike kurs for å øke sikkerhet og opplevelsen med padling."
        [sc/row-sc-g4-w
         (let [data [[:r.forsiden "Innmeldingskurs for nye medlemmer"]
                     [:r.forsiden "Våttkortkurs"]
                     [:r.forsiden "Livredningskurs"]]]
           (map f (sort-by second data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/Nøkkelvakt
        "Nøkkelvakt"
        "Nøkkelvaktene er en gruppe med omtrent 130 frivillige medlemmer som betjener klubbens anlegg ved Nøklevann i klubbens åpningstider. Nøkkelvaktene hjelper medlemmene, sjekker medlemsskap og bidrar til sikkerheten i klubbens aktivitet."
        #_"Nøkkelvaktene er en gruppe frivillige medlemmer som betjener klubbens anlegg ved Nøklevann, hjelper medlemmer i åpningstiden og bidrar til sikkerheten i klubbens aktiviteter."
        [sc/row-sc-g4-w
         (let [data [[0 [:r.dokumenter {:id "huskeliste-ved-nøkkelvakt"}] "Huskeliste ved nøkkelvakt"]
                     [1 [:r.dokumenter {:id "sikkerhetsutstyr-ved-nøklevann"}] "Sikkerhetsutstyr ved Nøklevann"]
                     [2 [:r.dokumenter {:id "vaktinstruks"}] "Nøkkelvaktinstruks"]
                     [3 [:r.dokumenter {:id "regler-utenom-vakt"}] "Regler utenom vakt"]

                     [4 :r.conditions "Plikter som nøkkelvakt"]
                     [5 :r.utlan]
                     ;[6 :r.aktivitetsliste "Aktivitetsliste"]
                     ;[7 :r.terms "Betingelser"]
                     [8 :r.nokkelvakt]
                     ;(shortlink :r.nokkelvakt)
                     [9 :r.mine-vakter]]]
           (map (comp f rest) (sort-by first data)))]]

       [sc/fp-summary-detail-always-show-links
        :oversikt/Årshjul
        "Årshjulet i NRPK"
        "Forslag til ingress?"
        [sc/row-sc-g4-w
         (let [data [[:r.yearwheel "Oversikt 2022-23"]
                     [:r.yearwheel "Tidligere sesonger"]]]
           (map f (sort-by second data)))]]])))