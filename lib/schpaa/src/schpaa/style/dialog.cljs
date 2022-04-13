(ns schpaa.style.dialog
  (:require [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            [fork.re-frame :as fork]
            [db.core :as db]
            [reagent.core :as r]
            [schpaa.debug :as l]
            [schpaa.style.ornament :as sc]
            [schpaa.components.fields :as fields]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [clojure.string :as string]
            [re-frame.core :as rf]
            [schpaa.style.button :as scb]
            [booking.database :refer [bookers-details bookers-name fetch-boatdata-for]]
            [tick.core :as t]
            [times.api :as ta]
            [db.signin]
            [schpaa.style.booking]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.input :as sci :refer [input textarea select]]
            [schpaa.style.switch]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [user.forms]
            [eykt.content.rapport-side]
            [booking.ico :as ico]
            [eykt.content.rapport-side :refer [top-bottom-view map-difference]]))

(def zero-width-space-props
  {:dangerouslySetInnerHTML {:__html "&#8203;"}})

(defn standard-overlay []
  [ui/transition-child
   {:enter      "ease-in duration-100 transition-opacity"
    :enter-from "opacity-0"
    :enter-to   "opacity-100"
    :entered    ""
    :leave      "ease-in duration-200"
    :leave-from "opacity-100"
    :leave-to   "opacity-0"}

   [ui/dialog-overlay {:style {:background "black"
                               :opacity    0.5}
                       :class "fixed inset-0 backdrop-brightness-50 backdrop-grayscale"}]])

(declare open-modal-boatinfo)

(defn disp [{:keys [data on-close]}]
  (letfn [(f [stability expert]
            [:svg.w-4.inline-block {:viewBox "-2 -2 5 5"}
             [:circle {:cx 0 :cy 0 :r 2 :fill (get {0 :green 1 :orange 2 :black 3 :purple} (js/parseInt stability) :white)}]
             [:circle {:cx 0 :cy 0 :r 1 :fill (if expert :red :transparent)}]])]
    (let [{:keys [location slot material stability expert number navn kind description last-update weight length width aquired-year aquired-price]} data]
      [sc/dropdown-dialog
       [sc/col-space-8

        [sc/col-space-4
         [sc/row-sc-g2
          [sc/badge-2 number]
          (f stability expert)
          [sc/text1 (schpaa.components.views/normalize-kind kind)]]

         [sc/surface-b
          [sc/col-space-4
           (for [[location in-group] (group-by :location (map #(;todo Workaround since it seems the :id in the original data is wrong?
                                                                 let [id (booking.database/fetch-id-from-number (str %))]
                                                                 (assoc (-> %
                                                                            str
                                                                            booking.database/fetch-id-from-number
                                                                            booking.database/fetch-boatdata-for) :id id)) [601 178 498 484 497 500 486 428]))]
             [sc/col-space-2
              [sc/text1 (case location
                          "0" "Nøklevann"
                          "1" "Sjøbasen")]
              (into [sc/row-sc-g2 {:style {:flex-wrap :wrap}}]
                    (for [{:keys [location id number]} in-group
                          :let [id' (keyword id)]]
                      [sc/badge-2 {:class    [(if (= number (str (:number data))) :active)]
                                   :on-click (fn []
                                               (tap> {:id id'
                                                      :a  (booking.database/fetch-boatdata-for id')})
                                               (-> id'
                                                   booking.database/fetch-boatdata-for
                                                   open-modal-boatinfo))}
                       number]))])]]

         [sc/col-space-2
          [sc/title1 navn]
          (apply sc/text2 (interpose [:span ", "] [width length weight (case material
                                                                         "0" "Plast"
                                                                         "1" "Glassfiber"
                                                                         "2" "Polyetylen"
                                                                         "3" "Kevlar/Epoxy"
                                                                         (str "Annet:" material))]))
          [sc/text0 description]
          [sc/row-sc-g2
           (when (some? aquired-year) [sc/text2 aquired-year])
           (when (some? aquired-price) [sc/text2 aquired-price " kr"])]]]
        [sc/row-fields
         [:div.flex.gap-2
          (if (= "1" location)
            [hoc.buttons/cta {:on-click on-close} (str "Book " number)]
            [hoc.buttons/cta {:on-click on-close} (str "Lån " number)])]
         [:div.grow]

         [hoc.buttons/regular {:on-click on-close} "Lukk"]]]])))

(comment
  (let [last-update "20210709T124742"]
    (cljs-time.format/parse-local (cljs-time.format/formatters :basic-date-time-no-ms) last-update)))

(defn open-modal-boatinfo [data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data       data
                 :on-close   #(tap> "on-close")
                 :content-fn #(disp %)}]))

(defn open-modal-bookingdetails [data]
  (rf/dispatch [:modal.slideout/toggle true
                {:click-overlay-to-dismiss true
                 :type                     :bookingdetails
                 :data                     data
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                 :content-fn               (fn [context]
                                             [:div
                                              [:div "something"]
                                              [l/ppre context]])}]))

(defn open-modal-confirmdeletebooking [data]
  (rf/dispatch [:modal.slideout/toggle true
                {:data       data
                 :buttons    [[:cancel "Avbryt" nil] [:danger "Slett" (fn [] (tap> "slett"))]]
                 ;:on-primary-action (fn [] (tap> :on-primary-action))
                 ;:action            (fn [m] (tap> [:action m]))
                 :content-fn (fn [{:keys [start selected] :as m}]
                               [sc/col {:class [:space-y-4 :w-full]}
                                [sc/row [sc/title-p "Bekreft sletting"]]
                                [sc/text1 "Dette vil slette din bookingen. Du kan ikke angre, er du sikker?"]
                                (l/ppre-x start selected m)])}]))

(defn open-dialog-confirmbooking
  "the arguments here are of no consequence, it was just a matter of gathering enough data to present something reasonable"
  [time-state state card-data-v2 type-data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data       {:time-state time-state
                              :state      state
                              ;:card-data-v2 card-data-v2
                              #_#_:type-data type-data}
                 :buttons    [[:cancel "Avbryt" nil] [:cta "Bekreft" (fn [] (tap> "lagre booking"))]]
                 :content-fn (fn [context]
                               (let [cdv2 (group-by :id card-data-v2)]
                                 [sc/col {:class [:space-y-4]}
                                  [sc/row [sc/title-p "Bekreft booking"]]
                                  [l/ppre-x context]
                                  [sc/surface-e {:style {:max-height "16rem"}
                                                 :class [:p-2 :overflow-y-auto]}
                                   [sc/col {:class [:space-y-4]}

                                    [sc/grid-wide {:class [:gap-2]}
                                     (for [e (:selected @state)
                                           :let [type (:type (first (get cdv2 e)))]]
                                       [schpaa.style.booking/selection-with-badges
                                        {:content
                                         (conj (first (get cdv2 e))
                                               (first (get (group-by :type type-data) type)))}])]]]]))}]))

(defn open-dialog-logoutcommand []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss 5000
                 :content-fn               (fn [{:keys [] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Bekreft utlogging"]]
                                              [sc/text1 "Dette vil logge deg ut fra denne enheten."]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Avbryt" nil] [:danger "Logg ut" (fn [] (tap> "click"))]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

(defn open-dialog-sampleautomessage []
  (rf/dispatch [:modal.slideout/toggle true
                {:click-overlay-to-dismiss true
                 :auto-dismiss             5000
                 :content-fn               (fn [{:keys [start selected] :as context}]
                                             [sc/col {:class [:space-y-4 :w-full]}
                                              [sc/row [sc/title-p "Sample"]]
                                              [sc/text1 "Sample text for a autoclosing dialog - dismissed after 5000 ms"]
                                              [l/ppre context]])

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

;region drop-down-dialog settings

(declare open-dialog-confirmaccountdeletion)

(defn config-content [{:keys [start selected] :as context}]
  [sc/dropdown-dialog
   [sc/col {:class [:space-y-8]}
    [sc/col {:class [:space-y-4]}
     [sc/dialog-title "Innstillinger"]
     #_[schpaa.style.switch/large-switch
        {:tag     :toggle1
         :caption [sc/subtext "Innstillingene er globale"]}]
     #_[schpaa.style.switch/large-switch
        {:tag      :toggle2
         :disabled true
         :caption  [sc/subtext "Vis den originale forsiden på forsiden"]}]
     [sc/col-space-8
      [sc/col-space-4
       [schpaa.style.switch/large-switch
        {:tag     :lab/toggle-chrome
         :caption [sc/subtext "Vis ekstra knapperader"]}]

       [schpaa.style.switch/large-switch
        {:tag     :lab/hide-image-carousell
         :caption [sc/subtext "Ikke vis billed-karusellen på forsiden"]}]

       [schpaa.style.switch/large-switch
        {:tag      :lab/more-contrast
         :disabled true
         :caption  [sc/subtext "Bruk større kontrast på tekst"]}]]

      [sc/row-std {:style {:flex-wrap :wrap}}
       [hoc.buttons/danger
        {:on-click #(schpaa.style.dialog/open-dialog-confirmaccountdeletion)}
        (hoc.buttons/icon-with-caption ico/trash "Slett konto...")]

       [hoc.buttons/regular
        {:on-click #(do
                      (schpaa.state/change :lab/skip-easy-login false)
                      (:on-save context))} "Tilbakestill alle spørsmål"]]]]

    #_(r/with-let [el (-> js/document.documentElement .-style)
                   a (r/atom (.getPropertyValue el "--brand1"))
                   b (r/atom (.getPropertyValue el "--brand1-bright"))]
        [:<>
         ;[:div @a]
         ;[:div (.getPropertyValue el "--brand1")]
         [:input {:type      :color
                  :value     @a
                  :on-change #(let [color-hex (-> % .-target .-value)]
                                (reset! a color-hex)
                                (.setProperty el "--brand1" color-hex))}]
         [:input {:type      :color
                  :value     @b
                  :on-change #(let [color-hex (-> % .-target .-value)]
                                (reset! b color-hex)
                                (.setProperty el "--brand1-bright" color-hex))}]])

    #_[sc/subtext-p (if @(schpaa.state/listen :toggle1)
                      "Disse innstillingene vil gjelde for alle innlogginger"
                      "Disse innstillingene lagres lokalt og gjelder bare for denne innloggingen")]
    [sc/row-ec
     [hoc.buttons/regular {:on-click #((:on-save context))} "Lukk"]]]])

;endregion

(defn open-dialog-sampleformmessage []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:click-overlay-to-dismiss true
                 ;:auto-dismiss 5000
                 :data                     {:timestamp (t/now)}
                 :type                     :bookingdetails
                 :content-fn               (fn [{:keys [data type] :as context}]
                                             (cond
                                               (= :bookingdetails type)
                                               [sc/col {:class [:text-base :tracking-normal :w-full]}
                                                [sc/title-p "Booking details"]
                                                (when-let [tm (some-> (:timestamp data) (t/instant) (t/zoned-date-time))]
                                                  [sc/text1 (ta/datetime-format tm)])
                                                [sc/text1 (bookers-name data)]
                                                (for [e (:selected data)
                                                      :let [data (fetch-boatdata-for e)
                                                            {:keys [number aquired-year aquired-price]} (fetch-boatdata-for e)]]
                                                  #_[l/ppre-x data]
                                                  [sc/row {:class [:gap-4]}
                                                   [sc/text1 number]
                                                   [sc/text1 aquired-year]
                                                   [sc/text1 aquired-price]])
                                                [sc/col
                                                 [sc/text1 (:telefon (bookers-details (:uid data)))]
                                                 [sc/text1 (:epost (bookers-details (:uid data)))]]

                                                [l/ppre-x data #_(bookers-details (:uid data))]]
                                               (= :boatdetails type)
                                               [:div
                                                [sc/title-p "Boat details"]
                                                [l/ppre-x data]]
                                               :else
                                               [l/ppre context]))

                 :buttons                  [[:cancel "Lukk" nil]]
                 :action                   (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action        (fn [] (tap> "closing after save"))}]))

;(defn addpost-f [{:keys [data type] :as context}]
;    (fn [{:keys [form-id handle-submit] :as props}]
;      [:form
;       {:id        form-id
;        :on-submit handle-submit}
;       [sc/col-fields {:class [:space-y-2]}
;        [sc/col {:class [:px-2]}
;         [sc/title1 "Header title"]
;         #_[sc/text1 "Lang ledetekst som tvinger vinduet til å legge seg ut så bredt det kan"]]
;        [sc/row-wrap
;         [input props :text [:w-min] "Tittel" :stuff]
;         ;[input props :text [:w-32] "Tittel" :stuff]
;         ;[input props :date [:w-42] "Publiseres" :publish-date]
;         ;[input props :date [:w-42] "Publiseres" :publish-date]
;         [input props :date [:w-42] "Publiseres" :publish-date]]
;
;        [textarea props :text [] "Innhold (ekslusiv tittel)" :stuff3]]]))

(defn addpost-form [{:keys [data type on-close on-save] :as context}]
  [sc/dropdown-dialog
   [fork/form {:initial-values    {:title        ""
                                   :type         type
                                   :publish-date ""
                                   :content      ""}
               :form-id           "addpost-form"
               ;:validation       #(-> {:stuff2 ["for lite"]})
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit         (fn [{:keys [values]}]
                                    (db/database-push
                                      {:path  ["booking-posts" "articles"]
                                       :value {:created (str (t/now))
                                               :date    (str (t/now))
                                               :deleted false
                                               :visible true
                                               :title   (:title values)
                                               :content (:content values)}}))}
    (fn [{:keys [form-id handle-submit dirty values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col {:class [:space-y-8]}
          [sc/col-fields {:class [:space-y-2]}
           [sc/dialog-title "Lag et nytt innlegg"]
           [sc/row-sc-g2-w
            [sc/col {:class []}
             [sc/label "Kategori"]
             [sci/combobox-example {:value     (values :type)
                                    :on-change #(tap> %)
                                    :class     [:w-56]}]]

            [input props :date [:w-44] "Publiseres" :publish-date]

            [input props :text [:w-full] "Tittel" :title]]

           [textarea props :text {} "Innhold (ekslusiv tittel)" :content]]
          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click #(on-close)} "Avbryt"]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"
                             :on-click #(on-save)} "Lagre"]]]]))]])

(defn open-dialog-addpost []
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data              {:timestamp (t/now)}
                 :type              :bookingdetails
                 :content-fn        #(addpost-form %)
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn edituser-form [{:keys [data type on-close on-save] :as context}]
  [sc/dropdown-dialog
   [fork/form {:initial-values    data
               :form-id           "edituser-form"
               :prevent-default?  true
               :clean-on-unmount? true
               :keywordize-keys   true
               :on-submit
               (fn [{:keys [state values] :as x}]
                 (user.forms/save-edit-changes
                   (:uid values)
                   (:uid @(rf/subscribe [::db/user-auth]))
                   (select-keys (:initial-values @state) (keys (map-difference values (:initial-values @state))))
                   (map-difference values (:initial-values @state))
                   (or (:endringsbeskrivelse values) (apply str (interpose ", " (map name (keys (map-difference values (:initial-values @state))))))))
                 (db/database-update {:path  ["users" (:uid values)]
                                      :value (assoc values :timestamp (str (t/now)))})
                 (rf/dispatch [:save-this-form-test x]))
               #_(fn [{:keys [values]}]
                   ;(tap> {:hello (eykt.content.rapport-side/map-difference data values)})
                   (on-save {:values  values
                             :changes (eykt.content.rapport-side/map-difference values data)}))}
    (fn [{:keys [form-id handle-submit dirty values] :as props}]
      (let [changed? dirty]
        [:form
         {:id        form-id
          :on-submit handle-submit}
         [sc/col-space-4
          [sc/dialog-title "Rediger bruker-opplysninger"]
          [sc/col-space-4
           [sc/small0 (:navn values)]
           [sc/small0 (:uid values)]]

          [:div.pt-4 (interpose [:div.py-6]
                                [[user.forms/generalinformation-panel props]
                                 [user.forms/booking-panel props]
                                 [user.forms/nokkelvakt-panel props]
                                 [user.forms/status-panel props]
                                 [user.forms/changelog-panel (:uid values) props]])]]

         [:div.sticky.bottom-0.py-4.-mx-4.mt-8
          {:style {:background "var(--content)"
                   :xopacity   0.9}}
          [sc/row-ec
           [hoc.buttons/regular {:type     "button"
                                 :on-click on-close} "Avbryt"]
           [hoc.buttons/cta {:disabled (not changed?)
                             :type     "submit"
                             #_#_:on-click #(on-save)} "Lagre"]]]]))]])

(defn save-changes [uid by-uid values]
  (db/firestore-add
    {:path  ["users" uid "endringslogg"]
     :value {:timestamp (str (t/now))
             :by-uid    by-uid
             :changes   values}}))

(defn open-user-info-dialog [data]
  (rf/dispatch [:modal.slideout/toggle
                true
                {:data              data
                 ;:on-close   #(tap> "on-close")
                 :content-fn        edituser-form
                 :action            (fn [{:keys [carry] :as m}]
                                      (let [uid (-> carry :values :uid)
                                            by-uid (:uid @(rf/subscribe [::db/user-auth]))
                                            changes (:changes carry)]
                                        ;(js/alert (l/ppr [uid by-uid changes])))
                                        (save-changes uid by-uid (:changes carry))
                                        #_(tap> ["action:" {:carry   (:values carry)
                                                            :changes (:changes carry)}])))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn open-dialog-any [{:keys [form                         ; definition of form, f(context)
                               on-submit                    ; what is called when submitting, f(values)
                               data]}]                      ; existing fields

  (rf/dispatch [:modal.slideout/toggle
                true
                {;:click-overlay-to-dismiss false
                 :data              data
                 :on-submit         on-submit
                 :content-fn        #(form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

(defn open-dialog-newevent [{:keys [form                    ; definition of form, f(context)
                                    on-submit               ; what is called when submitting, f(values)
                                    data]}]                 ; existing fields
  (rf/dispatch [:modal.slideout/toggle
                true
                {;:click-overlay-to-dismiss false
                 :data              data                    ;{:timestamp nil #_(t/now)}
                 :on-submit         on-submit
                 :type              7                       ; annonsering-av-møte
                 :content-fn        #(form %)
                 ;:buttons           [[:cancel "Avbryt" nil] [:cta "Lagre"]]
                 :action            (fn [{:keys [start selected] :as m}] (tap> [">>" m]))
                 :on-primary-action (fn [] (tap> "closing after save"))}]))

;region account signin

(defn signin-content [{:keys [on-close on-save] :as context}]
  (let [user-auth (rf/subscribe [::db/user-auth])]
    [sc/dropdown-dialog
     [:div.pb-8
      ;[l/ppre-x @user-auth]
      [sc/col-space-4
       ;[sc/title1 "Velkommen"]
       [sc/title1 "Hvordan vil du logge inn?"]]

      [sc/col
       [db.signin/login]]

      [sc/col-space-4
       [sc/text2 "Når du logger inn kan vi med større sikkerhet vite hvem som står bak en identitet og med bakgrunn i dette regulere tilganger."]
       [sc/text1 "Vi kan også tilby deg noe mer funksjonalitet når du er innlogget."]
       #_[sc/text2 "Hvis alt dette bekymrer deg, er det greieste alternativet å logge inn med e-post."]]]

     [sc/row-ec
      [hoc.buttons/regular {:type     "button"
                            :on-click #(on-close)} "Lukk"]]]))

(defn open-dialog-signin []
  (rf/dispatch [:modal.slideout/toggle                      ;:lab/modaldialog-visible
                true
                {:click-overlay-to-dismiss false
                 :action                   (fn [{:keys [carry] :as m}] (js/alert m))
                 :content-fn               signin-content}]))

;endregion

