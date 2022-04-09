(ns booking.design-debug
  (:require [schpaa.style.ornament :as sc]
            [lambdaisland.ornament :as o]
            [reagent.core :as r]
            [times.api :as ta]
            [shadow.resource :refer [inline]]
            [schpaa.style.hoc.page-controlpanel :refer [togglepanel]]
            [schpaa.style.hoc.page-controlpanel :as hoc.panel]
            [booking.ico :as ico]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [tick.core :as t]))


(defn details-summary [tag header content]
  (r/with-let [st (schpaa.state/listen tag)]
    [:details.select-none {:style {:display :none}
                           :open  @st}
     [:summary {:on-click #(schpaa.state/toggle tag)
                :style    {:outline :none
                           :cursor  :pointer
                           :display :inline-flex}} header]
     content]))

(defn color-table []
  [:<>
   [sc/surface-a
    (togglepanel :debug/color-table.surface000 "surface000"
                 (fn [] [sc/col-space-4
                         [sc/text1 "surface000 - brukes som primær-bakgrunn for all informasjon"]
                         [sc/text2 "normal tekst, men litt svakere (--text2) som standard subtext"]
                         [sc/text3 "normal tekst, men litt svakere (--text3) som standard small"]
                         [sc/subtext "subtext: sekundær informasjon med litt svakere trykk, nok til at den er klart synlig, teksten er også litt mindre enn primærteksten."]
                         [sc/small1 "small: knapt synlig, kun for kontrollere og som vedheng med ekstra stor avstand mellom bokstavene. Gjerne i kapitaler."]
                         [sc/small1 {:style {:text-transform :uppercase}} "small: knapt synlig, kun for kontrollere og som vedheng med ekstra stor avstand mellom bokstavene. Gjerne i kapitaler."]
                         [sc/small1 {:style {:text-transform :uppercase :color "var(--text2)"}}
                          "small: fremdeles small, men litt sterkere (--text2)"]
                         [sc/small1 {:style {:text-transform :lowercase :color "var(--text2)"}}
                          "her går det faktisk an å kutte ut kapitalene"]
                         [sc/small1 {:style {:text-transform :lowercase :color "var(--text1)"}}
                          "hvordan ser det ut med (--text1) ??"]]))]
   [sc/surface-b
    (togglepanel :debug/color-table.surface00 "surface00"
                 (fn [] [sc/col-space-4
                         [sc/text1 "surface00 - brukes i toolbars og andre ikke-primære flater"]
                         [sc/text1 {:style {:color "var(--text1)"}} "Her er det kun to toner som gjelder, dette er (--text1) og brukes på aktive elementer"]
                         [sc/text1 {:style {:color "var(--text3)"}} "standardtonene er litt svakere (--text3) og brukes blant annet på ikonene til høyre"]
                         [sc/row-sc-g2-w [sc/icon {:style {:color "var(--text3)"}} ico/new-home] [sc/text1 {:style {:color "var(--text3)"}} "Brukes som standard"]]
                         [sc/row-sc-g2-w [sc/icon {:style {:color "var(--text2)"}} ico/new-home] [sc/text1 {:style {:color "var(--text2)"}} "Brukes ikke, eller alternativt i interaksjon som :hover"]]
                         [sc/row-sc-g2-w [sc/icon {:style {:color "var(--text1)"}} ico/new-home] [sc/text1 {:style {:color "var(--text1)"}} "Brukes når noe er aktivt"]]
                         [sc/subtext "subtext: har ikke vurdert om denne skal brukes, eller..."]
                         [sc/small1 "small: ...om denne passer bedre (gjerne som ledetekst etc)"]
                         [sc/small1 {:style {:color "var(--text1)"}} "small: ...om denne passer bedre (gjerne som ledetekst etc)"]
                         [sc/small1 {:style {:color "var(--text3)"}} "small: ...om denne passer bedre (gjerne som ledetekst etc)"]]))]
   [sc/surface-c
    (togglepanel :debug/color-table.surface0 "surface0"
                 (fn [] [sc/col-space-4
                         [sc/text1 "surface0 - brukes i dialoger og andre modeless popups (menyer etc) der bakgrunnen ikke blir mørkere"]
                         [sc/text2 "her skal jeg undersøke hvordan knapper i ulike fasonger finner plass"]

                         [sc/row-sc-g2-w
                          [hoc.buttons/regular "scb2/normal"]]

                         [sc/row-sc-g2-w
                          [hoc.buttons/cta "scb2/cta"]]

                         [sc/row-sc-g2-w
                          [hoc.buttons/danger "scb2/danger"]]

                         [sc/text3 "fremdeles på surface0, men nå med (--text3)"]
                         [sc/title1 "Titler er alltids nyttige i en popup"]
                         [sc/text3 "Disse kommer i to varianter, en sc/title1 og en sc/title2, følger vanlig skjema, 1 er primær, 2 er sekundær"]
                         [sc/small1 "Disse kommer i to varianter, en sc/title1 og en sc/title2, følger vanlig skjema, 1 er primær, 2 er sekundær"]
                         [sc/row-sc-g2-w
                          [sc/title1 "Tittel 1"]
                          [sc/text "og"]
                          [sc/title2 "Tittel 2"]]
                         [sc/subtext "Over ser vi at standard-tekst alltid er primær (se og)"]
                         [sc/small1 "Titler skal aldri være i (--text1) siden de allerede tar mye plass visuelt"]]))]
   [sc/surface-a
    (togglepanel :debug/color-table.brand "brand - surface000"
                 (fn [] (do
                          (o/defstyled testx :div
                            :p-4)
                          [sc/col-space-4
                           [sc/text1 "Brand-color"]
                           [testx {:style {:background "var(--brand1)"}} "brand1"]
                           [testx {:style {:background "var(--brand1)"
                                           :color      "var(--brand1-copy)"}} "brand1 on brand-copy"]
                           [testx {:style {:background "var(--brand1-copy)"
                                           :color      "var(--brand1)"}} "brand-copy as background, never used"]])))]

   ;[sc/text1 {:style {:color "var(--text1)"}} "normal tekst"]
   ;[sc/text1 {:style {:color "var(--text2)"}} "normal tekst med sterk"]
   ;[sc/subtext "subtext er litt mindre men ikke mye"]
   ;[sc/small "small on surface-a"]]))]
   [:div 'panel]
   [:div 'panel]])

(defn time-and-dates []
  [sc/col-space-8
   [sc/text1 (ta/datetime-format (t/date-time))]
   [sc/ingress "Klikkbare med relativ tid"]

   (let [dt (t/at (t/today) (t/noon))]
     [sc/text1 {:style {:display :inline-block}}
      [:span (ta/datetime-format dt) " er "]
      [booking.flextime/relative-time dt]])

   (let [dt (t/at (t/tomorrow) (t/midnight))]
     [sc/text1 {:style {:display :inline-block}}
      [:span (ta/datetime-format dt) " er "]
      [booking.flextime/relative-time dt]])

   (let [dt (t/at (t/today) (t/noon))]
     [sc/text1 {:style {:display :inline-block}}
      [:span (ta/datetime-format dt) " er "]
      [booking.flextime/relative-time dt]])

   (let [dt (t/at (t/yesterday) (t/>> (t/midnight) (t/new-duration 10 :hours)))]
     [sc/text1 {:style {:display :inline-block}}
      [:span (ta/datetime-format dt) " er "]
      [booking.flextime/relative-time dt]])

   [sc/col-space-1
    [sc/title1 "Klikkbare med relativ klokke"]
    (into [sc/col-space-1] (for [e '(-60 -30 1 30 60 120 200 300)]
                             (let [dt (t/<< (t/date-time) (t/new-duration e :seconds))]
                               [sc/small0
                                [:span (ta/datetime-format dt) " er "]
                                [sc/small1-inline [booking.flextime/relative-time dt]]])))]

   [sc/col-space-1
    [sc/title1 "Klikkbare med relativ dato"]
    (into [sc/col-space-1] (for [e '(-10 -24 1 24 60 120 200 400 1000 10000 305000)]
                             (let [dt (t/<< (t/date-time) (t/new-duration e :hours))]
                               [sc/small0
                                [:span (ta/date-format dt) " er "]
                                [sc/small1-inline [booking.flextime/relative-time (t/date dt)]]])))]])


(defn render []
  [:div
   (interpose [:div.py-6]
              [[togglepanel :debug/color-table "farger" color-table]
               [togglepanel :debug/dates "Tid og Dato" time-and-dates]
               [details-summary
                :color-palette
                [sc/separator "Color and grayscale palette"]
                (into [:div.grid.gap-2
                       {:style {:grid-template-columns "repeat(auto-fit,minmax(6rem,1fr))"}}]
                      (map (fn [[styles tag]]
                             [sc/surface-b-sans-bg {:class []
                                                    :style (conj styles {:box-shadow   "var(--inner-shadow-2)"
                                                                         :font-size    "var(--font-size-0)"
                                                                         :aspect-ratio "1/1"})} tag])
                           [[{:color      "var(--green-0)"
                              :background "var(--green-5)"} "call to action"]


                            [{:color      "var(--red-0)"
                              :background "var(--red-5)"} "dangerous and irreversible actions"]


                            [{:color      "var(--surface5)"
                              :background "var(--brand0)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--brand1)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--brand2)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--yellow-2)"} :highlighter]
                            [{:color      "var(--surface5)"
                              :background "var(--surface1)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface2)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface3)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface4)"} :tag]
                            [{:color      "var(--surface0)"
                              :background "var(--surface5)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface0)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface00)"} :tag]
                            [{:color      "var(--surface5)"
                              :background "var(--surface000)"} :tag]]))]
               [details-summary
                :fonts
                [sc/separator "Fonts"]
                (let [f (fn [font-name font-size line-height e]
                          (let [line-height (or line-height "auto")
                                e (ta/format e font-name font-size line-height)]
                            [:div
                             {:style {:font-weight "400"
                                      :font-family font-name
                                      :font-size   font-size
                                      :line-height line-height}}
                             [:div e]
                             [:div {:style {:font-style :italic}} e]
                             [:div {:style {:font-weight "100"}} e " 100"]
                             [:div {:style {:font-weight "900"}} e " 900"]
                             [:div (apply str (range 10))]
                             [:div [:strong (apply str (range 10))]]]))]

                  [:<>
                   [sc/col {:class [:space-y-8]}
                    [sc/col {:class [:space-y-4]}
                     (f "Inter" "16px" "20px" "%s %s/%s sans-serif")
                     (f "Inter" "24px" "26px" "%s %s/%s sans-serif")
                     (f "Montserrat" "18px" "22px" "%s %s/%s sans-serif")
                     (f "Montserrat" "24px" "28px" "%s %s/%s sans-serif")
                     (f "Montserrat" "32px" "38px" "%s %s/%s sans-serif")
                     (f "Lora" "16px" "18px" "%s %s/%s serif")
                     (f "Lora" "24px" "30px" "%s %s/%s serif")
                     (f "Lora" "32px" "38px" "%s %s/%s serif")]]])]
               [details-summary
                :markdown
                [sc/separator "Markdown styles"]
                [sc/surface-a
                 (-> (inline "./content/markdown-example.md") schpaa.markdown/md->html sc/markdown)]]
               [details-summary
                :styles
                [sc/separator "Styles"]
                (into [:div.space-y-1]
                      (map (fn [c] [sc/surface-a {:class [:p-2]} c])
                           [[sc/small1 "sc/small"]
                            [sc/separator "sc/separator"]
                            [sc/col-fields
                             [sc/header-title "sc/header-title"]
                             [sc/header-title "sc/header-title"]
                             [sc/header-title "sc/header-title"]]
                            [sc/col
                             [sc/dialog-title "sc/dialog-title"]
                             [sc/dialog-title "sc/dialog-title"]
                             [sc/dialog-title "sc/dialog-title"]]
                            [sc/subtitle "sc/subtitle"]
                            [sc/subtext "sc/subtext"]
                            [sc/text1 "sc/text"]
                            [sc/field-label "sc/field-label"]
                            [sc/title1 "sc/title"]
                            [sc/pill "sc/pill"]
                            [sc/text1 "sc/row-fields"
                             [sc/row-fields
                              [sc/text1 "field"]
                              [sc/text1 "field"]
                              [sc/text1 "field"]]]
                            [sc/text1 "sc/row-stretch"
                             [sc/row-stretch
                              [sc/text1 "field"]
                              [sc/text1 "field"]
                              [sc/text1 "field"]]]
                            [sc/text1 "sc/row-end"
                             [sc/row-end
                              [sc/text1 "field"]
                              [sc/text1 "field"]
                              [sc/text1 "field"]]]
                            [sc/text1 "sc/row-wrap (gap-4)"
                             [sc/row-wrap
                              [sc/text1 "field"]
                              [sc/text1 "field"]
                              [sc/text1 "field"]]]
                            [sc/text1 "sc/row-gap (gap-2)"
                             [sc/row-gap
                              [sc/text1 "field"]
                              [sc/text1 "field"]
                              [sc/text1 "field"]]]]))]])])
