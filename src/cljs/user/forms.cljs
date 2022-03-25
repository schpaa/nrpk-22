(ns user.forms
  (:require [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.input :as sci :refer [input]]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [booking.ico :as ico]
            [schpaa.debug :as l]
            [reagent.core :as r]
            [tick.core :as t]
            [times.api :as ta :refer [relative-time]]
            [eykt.content.rapport-side]
            [db.core :as db]))

(defn my-basics-form' [props]
  [togglepanel :a/a1 "Generelle opplysninger"
   (fn [] [sc/row-sc-g4-w
           [sc/row-sc-g4-w
            [input props :text [:w-56x] "Fullt navn" :navn]
            [input props :text [:w-32] "Alias" :alias]]
           [sc/row-sc-g4-w
            [input props :text [:w-32] "Telefon" :telefon]
            [input props :text [:w-64x] "E-post" :epost]]

           [sci/select props :våttkort [] "Våttkort" :våttkort "Velg"
            {"0" "Jeg har ikke våttkort"
             "1" "Introkurs 3 timer"
             "2" "Grunnkurs 16 timer"
             "3" "Teknikk, sikkerhet eller grunnkurs-2"
             "4" "Aktivitetsleder, trener-1 eller høyere"}]])])

(defn my-booking-form' [props]
  [togglepanel :a/a2 "Booking på sjøbasen"
   (fn []
     [:<>
      [:div.flex.gap-4.flex-wrap
       [sci/input props :text [:w-32] "Våttkort-nr" :våttkortnr]]
      [:div.flex.gap-4.flex-wrap
       [hoc.buttons/checkbox props [] "Jeg ønsker å bruke booking på sjøbasen" :request-booking]
       [hoc.buttons/checkbox props [] "Jeg ønsker tilgang til de utfordrende båtene" :booking-expert]]])])

(defn my-vakt-form' [{:keys [form-id handle-submit dirty readonly? values] :as props}]
  [togglepanel :a/a3 "Nøkkelvakt"
   (fn []
     [sc/col-space-8

      [sc/row-sc-g4-w
       [input props :text [:w-32] "Medlem fra år" :medlem-fra-år]
       [input props :text [:w-32] "Fødselsår" :fødselsår]
       [input props :text [:w-32] "Førstehjelp" :årstall-førstehjelpskurs]
       [input props :text [:w-32] "Livredning" :årstall-livredningskurs]]

      [sc/row-sc-g4-w
       [hoc.buttons/checkbox props [] "Jeg arbeider som instruktør for NRPK" :instruktør]
       [hoc.buttons/checkbox props [] "Foretrekker helgevakt" :helgevakt]
       [hoc.buttons/checkbox props [] "Kan stille som vikar" :vikar]
       [hoc.buttons/checkbox props [] "Kort reisevei" :kort-reisevei]]

      #_[:div.flex.gap-4.flex-wrap.justify-end
         [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Timekrav" :name :timekrav]
         [fields/text (-> props fields/small-field (assoc :readonly? true)) :label "Nøkkelnummer" :name :nøkkelnummer]
         [fields/date (-> props fields/date-field (assoc :readonly? true)) :label "Godkjent" :name :godkjent]]])])

(comment
  (empty? (eykt.content.rapport-side/map-difference
            initial-state
            (dissoc (:values @form-state) :style :created-time :created-date))))

;todo find a suitable namespace
(defn save-edit-changes [uid by-uid before-values after-values endringsbeskrivelse]
  (db/firestore-add
    {:path  ["users" uid "endringslogg"]
     :value {:timestamp (str (t/now))
             :reason    endringsbeskrivelse
             :by-uid    by-uid
             :before    before-values
             :after     after-values}}))

(defn- get-diff [values initial-state]
  (eykt.content.rapport-side/map-difference
    initial-state
    values))

;(defonce show-changelog (r/atom false))

(defn my-endrings-logg [{:keys [state initial-values values] :as props}]
  (let [show-changelog (r/atom false)
        toggle #(swap! show-changelog not)
        path ["users" (:uid values) "endringslogg"]]
    [togglepanel :a/a4 "Endringslogg"
     (fn []
       (when (:uid values)
         [sc/col-space-2
          [sci/textarea props :text {:rows 2} "Endringsbeskrivelse (valgfritt)" :endringsbeskrivelse]
          [sc/row-sc-g2-w [schpaa.style.hoc.toggles/small-switch-base {:type :button} "Vis tidligere endringer" show-changelog toggle]]
          (when @show-changelog
            (r/with-let [changelog (db.core/on-snapshot-docs-reaction {:path path})]
              (when @changelog
                ;[l/ppre-x @changelog]
                (into [sc/col-space-1]
                      (map (fn [e] (let [rt (some-> e :data (get "timestamp") .toDate t/date-time)]
                                     [sc/row-sc-g2-w {:style {:display "inline-flex"}}
                                      [sc/text1 {:style {:line-height   "var(--font-lineheight-2)"
                                                         :padding-block "var(--size-1)"}}
                                       [:span {:style {:color "var(--text1)"}} (relative-time rt)]
                                       " "
                                       [:span {:style {:color "var(--text2)"}} (some-> e :data (get "reason"))]]]))

                           @changelog)))))]))]))


(comment
  (do
    (db.core/on-snapshot-docs-reaction {:path ["users" "Ri0icn4bbffkwB3sQ1NWyTxoGmo1" "endringslogg"]})))