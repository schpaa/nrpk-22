(ns booking.style.table
  (:require [lambdaisland.ornament :as o]
            [schpaa.state]
            [schpaa.style.ornament :as sc]))

(defn table-controller-report' [& c]
  (let [narrow-toolbar (not @(schpaa.state/listen :app/toolbar-with-caption))]
    [:div ;table-controller-report
     {:style {:padding-inline "rem"}
      :class [(when narrow-toolbar :narrow-toolbar)]} c]))

(o/defstyled table-report :table 
             [:&
              {:width           "auto"
               :border-collapse :collapse}
              [:thead
               {}
               [:tr
                {:text-align :left}
                ["th:not(:first-child)"
                 {:background "var(--floating)"}]
                [:th
                 sc/text
                 {:padding-inline "var(--size-1)"
                  :padding-block  "var(--size-1)"}
                 [:&.narrow {:min-width "5rem"}]]]]
              [:tbody

               #_["tr:nth-child(odd)"
                  {:background "rgba(0,0,0,0.05)"}]

               [:tr



                {:color      "var(--text1)"
                 ;:vertical-align :middle
                 :background "var(--content)"}
                [:&.online sc/text0]
                [:&.offline sc/text2]
                [:td :whitespace-nowrap
                 {:height         "var(--size-8)"
                  :padding-block  "var(--size-1)"
                  :vertical-align :middle
                  :padding-inline "8px"}
                 [:&.narrow {:min-width "13rem"
                             :width     "13rem"}]
                 [:&.message-col
                  :whitespace-normal
                  {:width "100%"}]
                 [:&.hcenter {:padding-inline "1rem"}]
                 [:&.vcenter {:vertical-align :middle}]]]

               ["tr:last-of-type"
                {:padding-bottom "3rem"
                 :background     "red"}]]])
