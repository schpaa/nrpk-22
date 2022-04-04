(ns user.forms
  (:require [schpaa.style.hoc.buttons :as hoc.buttons :refer [checkbox]]
            [schpaa.style.input :as sci :refer [input]]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [reagent.core :as r]
            [tick.core :as t]
            [times.api :refer [relative-time]]
            [eykt.content.rapport-side]
            [booking.flextime]
            [db.core :as db]))

(defn generalinformation-panel [props]
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

(defn booking-panel [{:keys [handle-change enable values disable disabled?] :as props}]
  [togglepanel :a/a2 "Booking på sjøbasen"
   (fn []
     [:<>
      [:div.flex.gap-4.flex-wrap
       [sci/input props :text [:w-32] "Våttkort-nr" :våttkortnr]]
      [:div.flex.gap-4.flex-wrap
       [hoc.buttons/checkbox (assoc props :handle-change
                                          #(let [value (-> % .-target .-checked)]
                                             (if value
                                               (enable :booking-expert)
                                               (disable :booking-expert))
                                             (handle-change %))) [] "Jeg ønsker å bruke booking på sjøbasen" :request-booking]
       [hoc.buttons/checkbox (assoc props :disabled (not (values :request-booking))) [] "Jeg ønsker også tilgang til de utfordrende båtene" :booking-expert]]])])

(defn nokkelvakt-panel [{:keys [form-id handle-submit dirty readonly? values] :as props}]
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

(defn status-panel [{:keys [handle-change set-values disable enable] :as props}]
  [togglepanel :a/a5 "Status"
   (fn []
     [sc/col-space-8
      [sc/row-sc-g4-w
       ;todo Just a hack to make text-fields and checkboxes visually aligned
       [sc/row-sc-g2
        [hoc.buttons/checkbox (assoc props
                                :handle-change #(let [value (-> % .-target .-checked)]
                                                  (when value
                                                    (set-values {:dato-godkjent-booking (str (t/date))}))
                                                  (handle-change %))) [:w-44] "Godkjent booking" :booking-godkjent]
        [input props :date [:w-36] "Godkjent booking" :dato-godkjent-booking]]

       [sc/row-sc-g2
        [hoc.buttons/checkbox (assoc props
                                :handle-change #(let [value (-> % .-target .-checked)]
                                                  (if value
                                                    (do (enable :dato-godkjent-nøkkelvakt)
                                                        (set-values {:dato-godkjent-nøkkelvakt (str (t/date))}))
                                                    (disable :dato-godkjent-nøkkelvakt))
                                                  (handle-change %))) [:w-44] "Godkjent nøkkelvakt" :godkjent]
        [input props :date [:w-36] "Godkjent nøkkelvakt" :dato-godkjent-nøkkelvakt]]
       [input props :text [:w-24] "Nøkkelnr" :nøkkelnummer]
       [input props :date [:w-36] "Mottatt nøkkel" :dato-mottatt-nøkkel]

       [input props :text [:w-24] "Timekrav" :timekrav]]])])

;[hoc.buttons/checkbox props [] "Foretrekker helgevakt" :helgevakt]]])])
;[hoc.buttons/checkbox props [] "Kan stille som vikar" :vikar]
;[hoc.buttons/checkbox props [] "Kort reisevei" :kort-reisevei]]])])


(comment
  (empty? (eykt.content.rapport-side/map-difference
            initial-state
            (dissoc (:values @form-state) :style :created-time :created-date))))

;region

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

(defn changelog-panel [{:keys [state initial-values values] :as props}]
  (let [show-changelog (r/atom false)
        toggle #(swap! show-changelog not)
        path ["users" (:uid values) "endringslogg"]]
    [togglepanel :a/a4 "Endringslogg"
     (fn []
       (when (:uid values)
         [sc/col-space-2
          ;(tap> props)
          [sci/textarea props :text {:rows 2} "Endringsbeskrivelse (valgfritt)" :endringsbeskrivelse]
          [sc/row-sc-g2-w [schpaa.style.hoc.toggles/small-switch-base {:type :button} "Vis tidligere endringer" show-changelog toggle]]
          (when @show-changelog
            (r/with-let [changelog (db.core/on-snapshot-docs-reaction {:path path})]
              (when @changelog
                (into [sc/col-space-1]
                      ;todo
                      (map (fn [e]
                             (let [rt (some-> e :data (get "timestamp") .toDate t/date-time)
                                   reason (some-> e :data (get "reason"))]
                               [sc/row-sc-g2-w {:style {:display "inline-flex"}}
                                [sc/text1 {:style {:line-height   "var(--font-lineheight-2)"
                                                   :padding-block "var(--size-1)"}}
                                 [:span {:style {:color "var(--text1)"}} (booking.flextime/relative-time rt times.api/tech-date-format)]
                                 " "
                                 [:span {:style {:color "var(--text2)"}} reason]]]))

                           @changelog)))))]))]))

;endregion

(comment
  (do
    (db.core/on-snapshot-docs-reaction {:path ["users" "Ri0icn4bbffkwB3sQ1NWyTxoGmo1" "endringslogg"]})))