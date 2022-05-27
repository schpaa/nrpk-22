(ns booking.news
  (:require [lambdaisland.ornament :as o]
            [re-frame.core :as rf]
            [tick.core :as t]
            [schpaa.style.ornament :as sc]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [booking.ico :as ico]
            [times.api :as ta]
            [booking.common-widgets :as widgets]
            [schpaa.debug :as l]))

;; styles

(o/defstyled block-text :div
  {:flex                 "3"})

(o/defstyled block-image :div
  {:flex         "1"
   :aspect-ratio "4/3"
   :width        "100%"
   :xmax-width   "20rem"})

(o/defstyled outer-image :div
  {:padding-top   "0rem"
   :margin-inline "0rem"
   :width         "33%"})

(o/defstyled frame :div
  {:box-shadow "0 0 0 4px red"})

(o/defstyled portrait :img
  {:object-fit   :cover
   :aspect-ratio "2/3"})

(o/defstyled landscape :img
  {:object-fit   :cover
   :aspect-ratio "3/2"})

(o/defstyled p :p
  sc/fp-text
  {:line-height "1.7"})

;; data

(def frontpage-images
  (repeat 3 ["img/caro/img-1.jpg"])
  #_(map #(str "/img/caros/" %)
         ["img-0.jpg"
          "img-1.jpg"
          "img-13.jpg"
          "img-16.jpg"
          "img-12.jpg"
          "img-4.jpg"]))

(defn news-data []
  [#_[:id0 {:author  @(rf/subscribe [:lab/uid])
            :date    (str (t/instant (t/at (t/yesterday) (t/noon))))
            :title   "uten tittel"
            :content [p "Sesongen er godt i gang. "
                      [widgets/auto-link {:caption "Utlånsloggen"} :r.utlan]
                      " er i bruk. Håper dere finner den enklere i bruk enn fjorårets."
                      " Vann— og lufttemperatur registreres ved å klikke på temperaturene du ser øverst på siden."]}]
   ["id0" {:title "uten-tittel"
           :content [:div
                     [p "Hele vaktlisten er nå tilgjengelig. Saldo fra fjoråret registreres fortløpende i dagene fram til nøkkelvaktmøtet."]
                     [p "Er det noe som ikke stemmer, send tilbakemelding fra nettsiden det gjelder, se "
                      [widgets/auto-link {} :r.min-status]]]}]
   ["id1" {:title   "uten-tittel"
           :content "markdown content 1"
           :author  @(rf/subscribe [:lab/uid])
           :date    (str (t/instant (t/at (t/yesterday) (t/noon))))}]
   ["id2" {:title   "title2"
           :content "markdown content 2"
           :author  @(rf/subscribe [:lab/uid])
           :date    (str (t/instant (t/at (t/yesterday) (t/noon))))}]])

;; items

(defn header-listitem [idx {:keys [date images title content]}]
  [:div {:class [:flex :items-center :gap-4]
         :style {:min-height "3rem"}}
   [hoc.buttons/pill {:class [:round :cta]}
    [sc/icon-small ico/check]]

   [sc/col
    [sc/title1 {:style {:line-height "auto"}
                :class []} title]
    (when-let [date (some-> date t/instant)]
      [booking.flextime/flex-datetime
       date
       (fn [type d]
         (if (= :date type)
           [sc/datetimelink (ta/date-format-sans-year d)]
           [sc/datetimelink d]))])]])

(defn news-listitem [idx {:keys [date images title content] :as m}]
  (let [direction false]
    [:div
     {:style
      {:background-color (if (odd? idx) "rgba(0,0,0,0.05)")
       :margin-inline-start "32px"
       :padding "1rem"
       ;:margin-left      "32px"
       :border-radius    "var(--radius-0)"
       :z-index          0}}
     [:div.flex.w-full
      {:class [:gap-4 (when direction :flex-row-reverse)]}
      ;content
      [block-text
       [sc/co
        [header-listitem idx m]
        (if (string? content)
          (-> content schpaa.markdown/md->html sc/markdown)
          content)]]
      ;images
      (when-not (empty? images)
        [outer-image
         [block-image
          {:class [(when direction :ml-7)]}
          (case (count images)
            1
            [landscape
             {:src   (first images)
              :style {:width         "100%"
                      :box-shadow    "var(--shadow-2)"
                      :border-radius "0.5rem"
                      :transform     (if direction
                                       "rotate(-2deg)"
                                       "rotate(2deg)")}}]
            2
            [:div.flex.gap-1
             (for [e (take 2 images)]
               [landscape {:style {:width "50%"}
                           :src   e}])]
            3
            [:div.flex.gap-1
             (for [e (take 3 images)]
               [portrait {:style {:width "33%"}
                          :src   e}])]

            [:div.grid.w-full.gap-1
             {:style {:grid-template-columns "repeat(2,1fr)"
                      :grid-auto-rows        "auto"}}
             (for [e (take 4 images)]
               [landscape {:class []
                           :style {:width "100%"}
                           :src   e}])])]])]]))

(defn news-feed []
  (letfn [(item [idx {:keys [date] :as m}]
            (news-listitem idx
                           (assoc m
                             :date (some-> date t/instant t/date-time)
                             :images- (take (rand-int (count frontpage-images))
                                            (shuffle frontpage-images)))))]

    (let [{right-menu? :right-menu?} @(rf/subscribe [:lab/screen-geometry])
          er-nokkelvakt? (rf/subscribe [:lab/nokkelvakt])]
      [sc/col-space-4
       (for [[idx [_k v]] (map-indexed vector (news-data))]
         [item idx v])

       #_[:div {:style {:display               "grid"
                        :gap                   "var(--size-4) var(--size-2)"
                        :grid-template-columns "1fr"}}
          (when goog.DEBUG
            [news-listitem 0 (t/at (t/date "2022-05-18") (t/time "18:00"))

             [p "Sesongen er godt i gang. "
              [widgets/auto-link {:caption "Utlånsloggen"} :r.utlan]
              " er i bruk. Håper dere finner den enklere i bruk enn fjorårets."
              " Vann— og lufttemperatur registreres ved å klikke på temperaturen øverst til " (if right-menu? "høyre" "venstre")]
             [sc/row
              [widgets/auto-link
               nil
               [:r.booking-blog-doc {:id "utlånsloggen"}]
               [{:id      "utlånsloggen"
                 :name    "id123"
                 :caption "Mer her"
                 :action  #(rf/dispatch [:app/navigate-to :r.utlan])
                 :f       (fn [_] [:div "ugh"])}]]]])

          [news-listitem 1 (t/at (t/date "2022-04-24") (t/time "18:00"))

           [p "Hele vaktlisten er nå tilgjengelig. Saldo fra fjoråret registreres fortløpende i dagene fram til nøkkelvaktmøtet."]
           [p "Er det noe som ikke stemmer, send tilbakemelding fra nettsiden det gjelder, se "
            [widgets/auto-link {} :r.min-status]]]

          [news-listitem 2 (t/at (t/date "2022-04-20") (t/time "10:00"))

           [p "Nå kan du velge vakter som går fram til og med 3. juli. Etter 29.
            april fyller vi på med rest-vakter for de med utestående saldo."]
           [p "Resten av vaktlisten åpner når vi ser at alt virker som det skal (2-3 dager)."]
           [p "Vaktlisten finner du "
            [sc/link {:style {:display :inline-block}
                      :href  (kee-frame.core/path-for [:r.nokkelvakt])} "her!"]]]

          (when-not @er-nokkelvakt?
            [news-listitem 3 (t/at (t/date "2022-04-19") (t/time "19:00"))
             [sc/col-space-2
              [p "Er du nøkkelvakt med nylaget konto (fordi Facebook ikke vil) og ser at du ikke er godkjent som nøkkelvakt?"]
              [p "Dette må du "
               [sc/link {:style  {:display :inline-block}
                         :target "_blank"
                         :href   "https://nrpk.no"} "gjøre!"]]]])

          [news-listitem 3 (t/at (t/date "2022-04-18") (t/time "15:00"))
           [p "Hva skjer til hvilken tid? Oversikten finner du under Planlagt!"]]

          (when @er-nokkelvakt?
            [news-listitem 4 (t/at (t/date "2022-04-14") (t/time "18:00"))
             [sc/col-space-2
              [:div "Fordi du er nøkkelvakt må du foreta den årlige sjekken om at informasjonen vi har om deg er oppdatert."]
              [:span "Dine opplysninger finner du "
               [sc/link {:style {:display :inline-block}
                         :href  (kee-frame.core/path-for [:r.user])} "her."]]]])
          (when @er-nokkelvakt?
            [news-listitem 5 (t/date "2022-04-13")
             [:span "Bruk utlånsloggen for å registrere "
              [sc/link {:style {:display :inline-block}
                        :href  (kee-frame.core/path-for [:r.utlan])} "utlån av båt."]]])]])))
