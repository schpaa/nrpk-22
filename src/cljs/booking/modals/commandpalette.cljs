(ns booking.modals.commandpalette
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
            [booking.ico :as ico]
            [schpaa.style.dialog]))

(def commands
  (mapv (fn [[idx {:keys [id] :as m}]] (assoc m :id (str idx)))
        (map-indexed vector
                     [{:name   "Utlån på Nøklevann"
                       :icon   ico/ticket
                       :action #(rf/dispatch [:lab/toggle-boatpanel])
                       :group  1}
                      {:name     "Årshjul"
                       :keywords "aktiviteter nrpk plan"
                       :action   #(rf/dispatch [:app/navigate-to [:r.yearwheel]])
                       :icon     ico/yearwheel
                       :group    1}
                      {:name     "Organisasjonen NRPK"
                       :keywords "nrpk organisasjon styret"
                       :action   #(rf/dispatch [:app/navigate-to [:r.oversikt.organisasjon]])}
                      {:name     "Hvem sitter i styret"
                       :keywords "nrpk styret"
                       :action   #(rf/dispatch [:app/navigate-to [:r.oversikt.styret]])}
                      {:name     "Logg ut"
                       :keywords ""
                       :action   #(rf/dispatch [:app/sign-out])}
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
                      {:group    1
                       :name     "Utlån på sjøbasen (booking)"
                       :keywords "utlån booking sjøbase"
                       :icon     ico/ticket :action #(rf/dispatch [:app/navigate-to [:r.debug]])}
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

                      {:id       "22", :name "Booking: Ofte stilte spørsmål"
                       :keywords "faq"
                       :icon     [:> solid/QuestionMarkCircleIcon] :action #(rf/dispatch [:app/navigate-to [:r.booking.faq]])}
                      {:group    1
                       :name     "Send en tilbakemelding"
                       :keywords "hvordan hjelp"
                       :icon     ico/tilbakemelding
                       :action   #(rf/dispatch [:app/give-feedback {:source :command-palette}])}
                      {:id     "24" :name "Gå til feilmelding" :keywords "feil"
                       :action #(rf/dispatch [:app/navigate-to [:r.page-not-found]])}
                      {:id       "25" :name "Vis QR-kode hit"
                       :icon     ico/qrcode
                       :keywords "lenke link"
                       :action   #(rf/dispatch [:lab/qr-code-for-current-page])}])))

(defn command-by-id [] (zipmap (map :id commands) commands))

(defn string->query [s]
  (string/replace (string/lower-case s) #"\s+" ""))

(defn listitem [{:keys [group active disabled icon]} value]
  [:div.gap-3.select-none.truncate.w-full
   {:class [:snap-start]
    :style {:cursor          (if disabled :default :pointer)
            :display         :flex
            :color           (cond
                               active (if (pos? group)
                                        "var(--brand1-bright)"
                                        "var(--gray-1)")
                               disabled "var(--gray-9)"
                               :elses (if (pos? group)
                                        "var(--brand1-bright)"
                                        "var(--gray-0)"))
            :align-items     :center
            :justify-content :start
            :border-left     (str "6px solid " (if active "var(--brand1)" "transparent"))
            :height          :3rem
            :padding-inline  "var(--size-3)"
            :font-family     "Inter"
            :font-weight     400
            :background      (cond
                               disabled "var(--gray-8)"
                               active "var(--gray-9)"
                               :else (if (pos? group)
                                       "var(--gray-7)"
                                       "var(--gray-8)"))}}
   [sc/icon-small {:class [:shrink-0]} (when-not (or disabled) icon)]
   [:div.truncate value]])

(o/defstyled placeholder :div
  [:&
   {:display     :relative
    :font-family "Inter"
    :color       "var(--text0)"}
   [:input
    {
     :font-weight "var(--font-weight-4)"
     :font-size   "var(--font-size-3)"
     :color       "var(--text0)"}
    ["&::placeholder"
     {:color "var(--text2)"}]]])

(defn commandpalette-window [context]
  (r/with-let [!query (r/atom "")
               !filtered-commands
               (r/reaction
                 (let [query @!query]
                   (sort-by (juxt :group :name) <
                            (if (string/blank? query)
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

            #_[:div.absolute.right-0.inset-y-0
               [sc/row'
                {:style {:padding-inline "var(--size-2)"}}
                [sc/subtext [sc/dim (str activeOption)]]]]
            [:div.flex.items-center.justify-between.w-full
             [placeholder
              [ui/combobox-input
               {:style         {:width            "25rem"
                                :height           :4rem
                                :padding-inline   "var(--size-8)"
                                :color            "var(--text1)"
                                :background-color "var(--field)"}
                :class         [:outline-none :focus:outline-none]
                :placeholder   "Søk"
                :display-value (fn [id] "" #_(:name (get command-by-id id)))
                :on-change     #(let [v (-> % .-target .-value)]
                                  (reset! !query (string->query v)))}]
              [:div.absolute.inset-0
               {:style {:top            :50%
                        :padding-inline "var(--size-3)"
                        :padding-top    "3px"
                        :transform      "translate(0,-50%)"}}
               [sc/icon ico/commandPaletteClosed]]]]]

           [ui/combobox-options
            {:static true
             ;:hold   true
             :style  {:scroll-behavior :smooth
                      :overflow-y      :auto
                      :height          "21rem"
                      :max-height      "21rem"
                      ;:height "auto"
                      :max-width       "28rem"}
             :class  [:snap-y :truncate]}

            [:<> (if (seq filtered-commands)
                   (for [command filtered-commands]
                     ;^{:key (:id command)}
                     [ui/combobox-option
                      {:disabled (:disabled command)
                       :value    (:id command)
                       :class    [:outline-none]}
                      (fn [{:keys [] :as attr}]
                        (listitem
                          (assoc attr
                            :group (:group command)
                            :icon (command :icon))
                          (:name command)))])

                   (listitem
                     {:notfound true :icon [sc/icon {:style {:color "var(--red-8)"}} ico/fill-heart]}
                     [:div "Finner ikke " [:span {:style {:font-weight "var(--font-weight-6)"}} quops]]))]]])]])))

(defn commandpalette [{db :db}]
  {:fx [[:dispatch [:lab/modal-selector
                    (-> db :lab/modal-selector not)
                    {:content-fn commandpalette-window}]]]})

(rf/reg-event-fx :app/toggle-command-palette [rf/trim-v] commandpalette)

(rf/reg-sub :lab/modal-selector-extra :-> (fn [db] (get db :lab/modal-selector-extra)))
(rf/reg-sub :lab/modal-selector :-> (fn [db] (get db :lab/modal-selector false)))
(rf/reg-event-db :lab/modal-selector
                 (fn [db [_ arg extra]]
                   (if (some? arg)
                     (if (true? arg)
                       (assoc db :lab/modal-selector true
                                 :lab/modal-selector-extra extra)
                       (assoc db :lab/modal-selector false
                                 #_#_:-lab/modal-selector-extra nil))
                     (update db :lab/modal-selector (fnil not true)))))
(rf/reg-event-db :lab/modal-selector-clear (fn [db _]
                                             (assoc db :lab/modal-selector-extra nil)))

(defn window-anchor
  "Why this name? This function waits for a signal, subscribes to `vis` which when turned
  true will show a window. There are some transition styles that are defined here which
  I feel is not in the correct place nor shape/form.

  This anchor ONLY handles the commandpalette"
  []
  (let [{:keys [context vis close]} {:context @(rf/subscribe [:lab/modal-selector-extra])
                                     :vis     (rf/subscribe [:lab/modal-selector])
                                     :close   #(rf/dispatch [:lab/modal-selector false])}
        {:keys [click-overlay-to-dismiss content-fn] :or {click-overlay-to-dismiss true}} context]
    (let [open? @vis]
      [ui/transition
       {:appear true
        :show   open?}
       [ui/dialog {:on-close #(if click-overlay-to-dismiss (close))}
        [:div.fixed.inset-0
         [:div.text-center
          [schpaa.style.dialog/standard-overlay]
          [:span.inline-block.h-screen.align-middle
           (assoc schpaa.style.dialog/zero-width-space-props :aria-hidden true)]
          [ui/transition-child
           {:class      (conj ["inline-block align-middle text-left transform overflow-hidden "
                               :outline-none :focus:outline-none]
                              (some-> (sc/inner-dlg) last :class first))
            :style      {:border-radius "var(--radius-3)"
                         :max-width     "90vw"}
            :enter      "ease-out duration-100"
            :enter-from "translate-y-32 opacity-0"
            :enter-to   "translate-y-0 opacity-100"
            :entered    "translate-y-0"
            :leave      "ease-out duration-300"
            :leave-from "opacity-100  translate-y-0"
            :leave-to   "opacity-0  translate-y-16"
            :after-leave #(rf/dispatch [:lab/modal-selector-clear])}
           [:div {:style {:overflow         :hidden
                          :background-color "var(--toolbar)"}
                  :class [:max-h-screen :outline-none :focus:outline-none]}
            (if content-fn
              (content-fn (assoc context :on-click (fn [{:keys [action]}]
                                                     (do
                                                       (when action
                                                         (action))
                                                       (close)))))
              #_[:div "Forgot content-fn?"])]]]]]])))