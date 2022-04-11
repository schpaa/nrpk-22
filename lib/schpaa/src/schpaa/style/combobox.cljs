(ns schpaa.style.combobox
  (:require [reagent.core :as r]
            [headlessui-reagent.core :as ui]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [db.core :as db]
            [clojure.string :as string]
            [schpaa.debug :as l]
            [schpaa.style.ornament :as sc]
            [re-frame.core :as rf]
            [booking.ico :as ico]))

(def commands
  (mapv (fn [[idx {:keys [id] :as m}]] (assoc m :id (str idx)))
        (map-indexed vector [{:name     "Årshjulet"
                              :keywords "aktiviteter nrpk plan"
                              :action   #(rf/dispatch [:app/navigate-to [:r.yearwheel]])}
                             {:name     "Organisasjonen NRPK"
                              :keywords "nrpk"
                              :action   #(rf/dispatch [:app/navigate-to [:r.oversikt.organisasjon]])}
                             {:name     "Hvem sitter i styret"
                              :keywords "nrpk styret"
                              :action   #(rf/dispatch [:app/navigate-to [:r.oversikt.styret]])}
                             {:name     "Logg ut"
                              :keywords ""
                              :action   #(rf/dispatch [:app/sign-out])}

                             #_{:name     "APP DIALOG"
                                :keywords ""
                                :action   #(rf/dispatch [:app/successful-login])}

                             {:name     "Administrasjon"
                              :keywords ""
                              :action   #(rf/dispatch [:app/navigate-to [:r.admin]])}

                             {:name     "Logg inn"
                              :keywords ""
                              :action   #(rf/dispatch [:app/login])}

                             {:name     "Oversikt"
                              :keywords "lenke link innhold nrpk"
                              :action   #(rf/dispatch [:app/navigate-to [:r.oversikt]])}

                             {:name     "Sjøbasen oversikt"
                              :keywords "lenke link innhold booking"
                              :action   #(rf/dispatch [:app/navigate-to [:r.booking]])}

                             {:id "0", :name "Registrer skade på utstyr" :icon [:> outline/PencilIcon] :disabled true}
                             {:id "1", :name "Registrer hms-hendelse" :icon [:> outline/PencilIcon] :disabled true}
                             {:id "2", :name "Avlys booking" :disabled true}
                             {:id "3", :name "Kok kaffe" :disabled true}
                             {:id "3", :name "Lage vafler" :keywords "mat" :disabled true}
                             {:id "4", :name "Avlys kurs" :disabled true :icon [:> outline/LockClosedIcon]}
                             {:id "5", :name "Reserver båter for kurs" :disabled true :icon [:> outline/ArrowRightIcon]}
                             {:id "6", :name "Vis båtlisten på Nøklevann" :disabled true}
                             {:id "7", :name "Vis båtlisten på Sjøbasen" :disabled true}
                             {:id "8", :name "Endre mine opplysninger" :icon [:> outline/UserCircleIcon] :action #(rf/dispatch [:app/navigate-to [:r.user]])}
                             {:id   "9",
                              :name "Skriv nytt innlegg"
                              :icon ico/pencil
                              :action
                              ;#(js/alert "!!!")
                              #(rf/dispatch [:lab/new-blog-entry])}
                             {:id "10", :name "Book båt" :disabled true :icon [:> outline/TicketIcon] :action #(rf/dispatch [:app/navigate-to [:r.debug]])}
                             {:id "11", :name "Lage nytt båtnummer" :disabled true}
                             {:id "12", :name "Gå til designspråk/mal" :icon [:> outline/ColorSwatchIcon] :action #(rf/dispatch [:app/navigate-to [:r.designlanguage]])}
                             {:id "13", :name "Gå til forsiden" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.forsiden]])}
                             {:id "14", :name "Hvilke betingelser gjelder?" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.terms]])}
                             {:id "15", :name "Plikter som nøkkelvakt" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.conditions]])}
                             {:id "16", :name "Hvilke retningslinjer på sjøbasen?" :icon [:> outline/LightBulbIcon] :action #(rf/dispatch [:app/navigate-to [:r.booking.retningslinjer]])}
                             {:id "17", :name "Vaktkalender" :icon [:> outline/CalendarIcon] :action #(rf/dispatch [:app/navigate-to [:r.calendar]])}
                             {:id "18", :name "Veksle visning av krom" :icon [:> outline/LightningBoltIcon] :action #(rf/dispatch [:lab/toggle-chrome])}
                             {:id "19", :name "Vis filer" :icon [:> outline/DocumentIcon] :action #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])}
                             {:id "20", :name "Aktivitetsliste (utlån)" :icon [:> outline/DocumentIcon] :action #(rf/dispatch [:app/navigate-to [:r.aktivitetsliste]])}
                             {:id "21", :name "Utlån på Nøklevann" :icon [:> outline/DocumentIcon] :action #(rf/dispatch [:lab/open-number-input])}
                             {:id       "22", :name "Booking: Ofte stilte spørsmål"
                              :keywords "faq"
                              :icon     [:> solid/QuestionMarkCircleIcon] :action #(rf/dispatch [:app/navigate-to [:r.booking.faq]])}
                             {:id "23" :name "Gi oss en tilbakemelding" :keywords "hjelp" :action #(rf/dispatch [:app/give-feedback {:source :command-palette}])}
                             {:id     "24" :name "Gå til feilmelding" :keywords "feil"
                              :action #(rf/dispatch [:app/navigate-to [:r.page-not-found]])}
                             {:id       "25" :name "Vis QR-kode hit"
                              :icon     ico/qrcode
                              :keywords "lenke link"
                              :action   #(rf/dispatch [:lab/qr-code-for-current-page])}])))

(comment
  commands)


(defn command-by-id [] (zipmap (map :id commands) commands))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(defn listitem [{:keys [notfound selected action active disabled icon] :as attr} value]
  [:div.gap-3.select-none.truncate.w-full
   {:class [:snap-start]
    :style {:display         :flex
            :color           (if disabled "var(--text3)" "var(--text0)")
            :align-items     :center
            :justify-content :start
            :border-left     (str "6px solid " (if active "var(--brand1)" "transparent"))
            :height          :3rem
            :padding-inline  "var(--size-3)"
            :font-family     "Inter"
            :font-weight     400
            :background      (if active "var(--content)" "var(--toolbar)")}}
   [sc/icon-small {:class [:shrink-0]} (when-not (or disabled) icon)]
   [:div.truncate value]])

(o/defstyled placeholder :div
  [:&
   {:font-family "Inter"
    :color       "var(--text1)"}

   [:input
    {
     :font-weight "var(--font-weight-4)"
     :font-size   "var(--font-size-3)"
     :color       "var(--text0)"}
    ["&::placeholder"
     {:color "var(--text3)"}]]])

(defn combobox-example [context]
  (r/with-let [!query (r/atom "")
               !filtered-commands
               (r/reaction
                 (let [query @!query]
                   (sort-by :name (if (string/blank? query)
                                    commands
                                    (filter #(or (string/includes? (string->query (:name %)) query)
                                                 (when (:keywords %)
                                                   (string/includes? (string->query (:keywords %)) query)))
                                            commands)))))
               !selected (r/atom (first @!filtered-commands))]
    (let [selected @!selected
          filtered-commands @!filtered-commands
          quops @!query]
      [:div
       {:class [:truncate]
        :style {:max-width "25rem"
                :overflow  "hidden"
                ;:border-radius "var(--radius-3)"
                :height    :100%}}
       [ui/combobox {:value     (:id selected)
                     :disabled  false
                     :on-change #(let [action (:on-click context)]
                                   (if-not (:disabled (get (command-by-id) %))
                                     (reset! !selected (get (command-by-id) %)))
                                   (tap> "ACTION")
                                   (when action
                                     (action (get (command-by-id) %))))}
        (fn [{:keys [activeOption]}]
          [:div.relative.w-full
           [:div.relative.w-full
            [:div.absolute.right-0.inset-y-0
             [sc/row'
              {:style {:padding-inline "var(--size-2)"}}
              [sc/subtext [sc/dim (str activeOption)]]]]
            [:div.flex.items-center.justify-between.w-full
             [placeholder
              [ui/combobox-input
               {:style         {:width            "25rem"
                                ;:font-family      "Inter"
                                ;:font-weight      "100"
                                ;:font-size        "var(--font-size-3)"
                                :height           :4rem
                                ;:border-top-radius "var(--radius-2)"
                                :padding-inline   "var(--size-4)"
                                :color            "var(--text1)"
                                :background-color "var(--field)"}
                :class         [:outline-none :focus:outline-none]
                :placeholder   "Søk"
                :display-value (fn [id] "" #_(:name (get command-by-id id)))
                :on-change     #(let [v (-> % .-target .-value)]
                                  (reset! !query (string->query v)))}]]]]

           [ui/combobox-options
            {:static  true
             ;:hold   true
             :unmount false
             :style   {:scroll-behavior :smooth
                       :overflow-y      :auto
                       :height          "18rem"
                       :max-width       "28rem"}
             :class   [:snap-y :truncate]}

            [:<> (if (seq filtered-commands)
                   (for [command filtered-commands]
                     ;^{:key (:id command)}
                     [ui/combobox-option
                      {:disabled (:disabled command)
                       :value    (:id command)
                       :class    [:outline-none]}
                      (fn [{:keys [] :as attr}]
                        (listitem
                          (assoc attr :icon (command :icon))
                          (:name command)))])

                   (listitem
                     {:notfound true :icon [sc/icon {:style {:color "var(--red-8)"}} ico/fill-heart]}
                     [:div "Finner ikke " [:span {:style {:font-weight "var(--font-weight-6)"}} quops]]))]]])]])))
