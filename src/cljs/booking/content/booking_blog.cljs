(ns booking.content.booking-blog
  (:require [schpaa.style :as st]
            [db.core :as db]
            [reagent.core :as r]
            [lambdaisland.ornament :as o]
            ["@heroicons/react/solid" :as solid]
            ["@heroicons/react/outline" :as outline]
            [nrpk.database]
            [tick.core :as t]
            [times.api :as ta]
            [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [nrpk.fsm-helpers :refer [send]]
            [schpaa.debug :as l]
            [eykt.content.rapport-side]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.menu :as scm]
            [schpaa.style.button :as scb]
            [schpaa.style.dialog]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

(defn article-menu-definition [settings-atom]
  (let [uid (:uid @settings-atom)]
    [#_[:header [sc/row {:class [:justify-between :items-end]}
                 [sc/title1 "Top"]
                 [sc/pill (or booking.data/VERSION "dev.x.y")]]]
     ;[icon label action disabled value]
     #_[:menuitem [nil "Slett kvitteringer" #() false 0]]
     [:menuitem {:icon     (sc/icon [:> solid/TrashIcon])
                 :label    "Slett kvitteringer"
                 :color    "var(--blue-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 ;:action   #(remove-receipts uid)
                 :disabled false
                 :value    #()}]
     [:menuitem {:icon     (sc/icon [:> solid/PlusIcon])
                 :label    "Ny artikkel"
                 :color    "var(--green-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 ;:action   #(add-article)
                 :disabled false
                 :value    #()}]
     [:menuitem {:icon     (sc/icon [:> solid/BeakerIcon])
                 :label    "Generer"
                 :color    "var(--red-4)"
                 ;:highlight (= :r.retningslinjer :current-page)
                 ;:action   #()
                 :disabled true
                 :value    #()}]]))

(defn not-yet-seen' [last-visit-dt {:keys [date]}]
  (if last-visit-dt
    (try
      (if-let [article-dt (some-> date t/instant)]
        (not (t/<= article-dt last-visit-dt))
        false)
      (catch js/Error e (prn (.-message e)) false))
    true))

(defn last-seen [uid]
  (let [path ["booking-posts" "receipts" (name uid)]
        last-seen (db/on-value-reaction {:path path})]
    @last-seen
    #_(some-> @last-seen (last) (val) (t/instant))))

(defn all-articles-by-date [all]
  (let [f (fn [a [k v]]
            (conj a {:date    (:date v)
                     :content (:content v)
                     :id      (name k)}))]
    (sort-by :date > (reduce f [] all))))

(defn count-unseen [uid]                                    ;"piH3WsiKhFcq56lh1q37ijiGnqX2"
  (when uid
    (let [last-visit (-> (last-seen uid) :articles :date)
          all (db/on-value-reaction {:path ["booking-posts" "articles"]})
          unseen (remove (fn [m] (not-yet-seen' last-visit m)) (all-articles-by-date @all))]
      (- (count @all) (count unseen)))))

(comment
  (do
    (let [last-visit (-> (last-seen "piH3WsiKhFcq56lh1q37ijiGnqX2") :articles :date)
          all (db/on-value-reaction {:path ["booking-posts" "articles"]})
          unseen (filter (fn [m] (not-yet-seen' last-visit m)) (all-articles-by-date @all))]

      (- (count @all) (count unseen))
      #_{:all     (all-articles-by-date @all)
         :not-yet unseen})))

(comment
  (do
    (not-yet-seen
      (-> (last-seen "piH3WsiKhFcq56lh1q37ijiGnqX2") :articles :date)
      ;(t/at (t/new-date 2021 1 1) (t/new-time 1 1 1))
      [:x {:date (t/at (t/new-date 2023 1 1) (t/new-time 1 1 1))}])))

(defn empty-list-message [msg]
  (let [{:keys [bg fg- fg+ hd p p- he]} (st/fbg' 0)]
    [:div.grow.flex.items-center.justify-center.xpt-8.mb-32.mt-8.flex-col
     {:class (concat fg-)}
     [:div.text-2xl.font-black msg]
     [:div.text-xl.font-semibold "Ta kontakt med administrator"]]))

(defn mark-last-seen [uid]
  (when-not (nil? uid)
    (let [path ["booking-posts" "receipts" uid "articles"]
          last-seen (last @(db/on-value-reaction {:path ["booking-posts" "articles"]}))]
      (when last-seen
        (db/database-update {:path  path
                             :value {:id   (key last-seen)
                                     :date (str (t/now))}})))))

(o/defstyled top-right-date :div
  :top-0 :right-0
  {:position :absolute})

(defn lineitem [_]
  ;-[ ] issue: tabbing into elements that are overflow-hidden will give them the focus
  (let [visible? (r/atom false)]
    (fn [{:keys [title content item-id uid' kv date-of-last-seen id-of-last-seen date uid] :as m}]
      [:div
       [sc/text1 title]
       [sc/text2 date]
       [sc/small2 item-id]

       #_[:div                                              ;sc/surface-a
          #_{:style {:xpadding      "1rem"
                     :border-radius "var(--radius-1)"
                     :background    "var(--content)"
                     :xbox-shadow   "var(--inner-shadow-2)"}}
          [:div.relative.space-y-s4.overflow-hidden
           (when-not @visible?
             {:style {:max-height "25rem"}})
           ;intent bottom fadeout
           [:div.absolute.bottom-0.inset-x-0.pointer-events-none
            (when-not @visible?
              {:class [:bg-gradient-to-t :from-current]
               :style {:height            "50%"
                       :color             "var(--surface000)"
                       :abackground-image "linear-gradient(transparent,var(--surface00))"}})]
           [:div
            (if @visible?
              [:div (-> content schpaa.markdown/md->html sc/markdown)]
              [:div (-> content (subs 0 256) (str "...") schpaa.markdown/md->html sc/markdown)])]
           #_[:div.absolute.bottom-0.inset-x-0
              [:div.grid.gap-2
               {:style {:grid-template-columns "1fr min-content  min-content  min-content 1fr"}}
               [:div]
               [hoc.buttons/regular {:on-click #(rf/dispatch [:app/navigate-to [:r.booking-blog-doc {:id item-id}]])} [sc/icon [:> outline/DocumentDuplicateIcon]]]
               (if-not @visible?
                 [hoc.buttons/regular {:on-click #(swap! visible? not)} "Les mer"]
                 [:div])
               [hoc.buttons/regular [sc/icon [:> outline/PencilIcon]]]
               [:div]]]]]])))

(defn initial-page [{:keys [data date-of-last-seen id uid pointer]}]
  (into [:div.space-y-20]
        (concat
          #_[[sc/row {:class [:justify-center]}
              [scb2/cta-small {:on-click #(schpaa.style.dialog/open-dialog-addpost)} "Nytt innlegg"]]]

          (for [[k {:keys [content date] :as e} :as kv] (take @pointer (reverse data))
                :let [uid' (:uid e)]]
            ^{:key (str k)}
            [lineitem (assoc e
                        :content content
                        :item-id (name k)
                        :date-of-last-seen date-of-last-seen
                        :id-of-last-seen id
                        :uid uid
                        :kv kv
                        :date date
                        :uid' uid' #_(:uid e))])

          [[sc/row {:class [:justify-center]}
            (if (< @pointer (count data))
              [hoc.buttons/regular {:disabled (zero? (count data))
                                    :on-click #(swap! pointer (fn [e] (+ e 5)))} "Vis flere"]
              (when (pos? (count data))
                [sc/subtext "Ingen flere innlegg"]))]])))

(def fsm
  {:initial :s.startup
   :on      {:e.empty {:target [:. :s.empty]}
             :e.run   {:target [:. :s.initial]}}
   :states  {:s.startup {} #_{:on {:e.startup [{:guard  (fn [st ev]
                                                          (let [data (-> ev :data :data)]
                                                            (tap> [:A data (some? data)])
                                                            (some? data)))
                                                :target :s.initial}
                                               {:target :s.empty}]}}
             :s.initial {}
             :s.empty   {}}})

(defn remove-receipts [uid]
  (db/database-set {:path  ["booking-posts" "receipts" uid "articles"]
                    :value {}}))

(defn add-article' [content]
  (let [add-func #(db/database-push {:path  ["booking-posts" "articles"]
                                     :value {:date (str (t/now)) :content %}})]
    (add-func content)))

(defn add-article [kind]
  (let [add-func #(db/database-push {:path  ["booking-posts" "articles"]
                                     :value {:date (str (t/now)) :content %}})]
    (add-func (case kind
                :e (shadow.resource/inline "./intro.md")
                :d (shadow.resource/inline "./frontpage.md")
                :c (shadow.resource/inline "./frontpage2.md")
                :b (shadow.resource/inline "./test.md")
                :a "# Overskrift nr 2\n
> Jeg kan tenke meg en ingress her
### Første del
Her kommer litt brødtekst, en lenke til [forsiden](/) og noen ord om det som jeg skulle ha sagt. Dette er selvfølgelig kun for demonstrasjonens skyld.

Her kommer litt brødtekst i et nytt avsnitt, en lenke til [forsiden](/) og noen ord om det som jeg skulle ha sagt. Dette er selvfølgelig kun for demonstrasjonens skyld.
### Avsluttende del
Helt til slutt, en kinaputt"
                "some content for the new stuff"))))

(defn generate [uid]
  (db/database-push {:path  ["booking-posts" "receipts" uid "articles"]
                     :value {:date (str (t/now)) :id "A"}})
  (db/database-push {:path  ["booking-posts" "articles"]
                     :value {:date (str (t/now)) :content "A"}}))

(defn bottom-menu-definition [settings-atom]
  (let [uid (:uid @settings-atom)]
    [[:small-menuitem {:icon   (sc/icon [:> solid/TrashIcon])
                       :label  "Slett alle kvitteringer"
                       :action #(remove-receipts uid)}]
     [:small-menuitem {:icon   (sc/icon [:> solid/PlusIcon])
                       :label  "Ny artikkel A"
                       :action #(add-article :a)}]
     [:small-menuitem {:icon   (sc/icon [:> solid/PlusIcon])
                       :label  "Ny artikkel B"
                       :action #(add-article :b)}]
     [:small-menuitem {:label  "Ny artikkel C"
                       :action #(add-article :c)}]
     [:small-menuitem {:label  "Ny artikkel D"
                       :action #(add-article :d)}]
     [:small-menuitem {:label  "Ny artikkel E"
                       :action #(add-article :e)}]
     [:small-menuitem {:label  "./markdown-example.md"
                       :action #(add-article' (shadow.resource/inline "./markdown-example.md"))}]
     [:small-menuitem {:icon     (sc/icon [:> solid/BeakerIcon])
                       :label    "Generer"
                       :color    "var(--red-4)"
                       :action   #()
                       :disabled true}]]))

(defn bottom-menu [uid]
  (r/with-let [main-visible (r/atom false)]
    (let [toggle-mainmenu #(swap! main-visible (fnil not false))]
      [scm/small-menu
       {:showing      @main-visible
        :close-button #()
        :dir          #{:up :left}
        :data         (bottom-menu-definition (r/atom {:uid uid}))
        :button       (fn [open]
                        [scb/round-dark {:on-click toggle-mainmenu}
                         [sc/icon [:> outline/FingerPrintIcon]]])}])))

(defn render [{:keys [path uid fsm]}]
  ;intent When opening this section, mark as read!
  (let [pointer (r/atom 5)
        datas (db/on-value-reaction {:path path})]
    (r/create-class
      {:display-name
       "booking-blog"

       :component-did-update
       (fn [this old-argv old-state snapshot]
         #_(let [datas (db/on-value-reaction {:path path})]
             (tap> [:component-did-update @datas])
             (if (empty? @datas)
               (send :e.empty)
               (send :e.initial))))

       :component-did-mount
       (fn [_]
         ;-[ ] set receipts/uid/articles/date and id
         (mark-last-seen uid)
         ;(js/alert "!")
         ;(tap> {:datas @datas})
         ;(rf/dispatch [::rs/start])
         #_(tap> ":component-did-mount"))

       :reagent-render
       (fn [{:keys [path uid]}]
         (let [data (sort-by (comp :number val) < (logg.database/boat-db))
               {date-of-last-seen :date id :id} @(db/on-value-reaction {:path ["booking-posts" "receipts" uid "articles"]})
               date-of-last-seen (some-> date-of-last-seen t/instant t/date-time)
               list-of-posts (db/on-value-reaction {:path path})
               *fsm-rapport (:blog @(rf/subscribe [::rs/state :main-fsm]) :s.startup)]
           [sc/col {:class [:space-y-4 :xmax-w-lg :xmx-auto :xpx-4]}


            #_[sc/row {:class [:justify-center]}
               [scb2/cta-small {:on-click #(schpaa.style.dialog/open-dialog-addpost)} "Nytt innlegg"]
               [scb2/cta-small {:on-click #(schpaa.style.dialog/open-dialog-addpost)} "Oversikt"]]

            (rs/match-state *fsm-rapport
              [:s.startup]
              [initial-page
               {:data              (filter (fn [[k v]] (:visible v)) @(db/on-value-reaction {:path path}))
                :uid               uid
                :id                id
                :pointer           pointer
                :date-of-last-seen date-of-last-seen}]

              [:s.empty]
              [sc/col
               [l/ppre-x @list-of-posts]
               [:div "EMPTY"]]

              [:s.initial]
              [initial-page {:path              path
                             :uid               uid
                             :id                id
                             :pointer           pointer
                             :date-of-last-seen date-of-last-seen}]

              [:div "other " *fsm-rapport])

            [:div.absolute.bottom-24.sm:bottom-4.left-4.sm:left-20
             [sc/row-end {:class [:pt-4]}
              (bottom-menu uid)]]]))})))

(defn always-panel []
  [sc/row-sc-g2-w
   [schpaa.style.hoc.buttons/pill {:class [:cta :narrow]} "fsm"]
   [schpaa.style.hoc.buttons/pill {:class    [:regular :narrow]
                                   :on-click #(rf/dispatch [:app/navigate-to [:r.fileman-temporary]])} "liste over innlegg"]])