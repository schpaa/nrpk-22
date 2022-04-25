(ns booking.mine-dine-vakter
  (:require [schpaa.style.ornament :as sc]))

(defn header [{:keys [saldo timekrav z]}]
  [:div {:style {:display         :flex
                 :height          "var(--size-11)"
                 :justify-content :space-between
                 :width           "100%"
                 :gap             "var(--size-2)"}}
   [sc/surface-a {:style {:box-shadow "var(--shadow-1)"
                          :background "var(--floating)"
                          :flex       "1 0 0"}}
    [sc/col-space-2 {:style {:height          "100%"
                             :justify-content :space-between}}
     [sc/text2 "Overført '21"]
     [sc/title1 {:style {:text-align   :right
                         :xfont-weight "var(--font-weight-6)"}}
      (if (some? saldo)
        (str saldo " timer")
        "Kommer snart")]]]

   [sc/surface-a {:style {:flex       "1 0 0"
                          :box-shadow "var(--shadow-1)"
                          :background "var(--floating)"}}
    [sc/col-space-2 {:style {:height          "100%"
                             :justify-content :space-between}}
     [sc/text2 "Vaktkrav '22"]
     (when timekrav
       [sc/title1 {:style {:text-align   :right
                           :xfont-weight "var(--font-weight-7)"}}
        (str timekrav " timer")])]]

   [sc/surface-a {:style {:flex       "1 0 0"
                          :box-shadow "var(--shadow-3)"
                          :background (when (some? z) (if (<= 0 z) "var(--green-8)" "var(--red-8)"))
                          :color      (when (some? z) (if (<= 0 z) "var(--green-1)" "var(--red-1)"))}}
    [sc/col {:style {:height          "100%"
                     :justify-content :space-between}}
     [sc/col
      [sc/text2 {:style {:color (when (some? z) "unset")}} "Saldo"]
      (when (some? z)
        [sc/small {:style {:font-weight    "var(--font-weight-3)"
                           :text-transform :uppercase}} (if (<= 0 z) "I din favør" "I vår favør")])]
     [sc/title1 {:style {:color        (if (some? z) "unset")
                         :xfont-weight "var(--font-weight-7)"}
                 :class [:text-right]}
      (if (some? z)
        (str (Math/abs z) " timer")
        "Kommer snart")]]]])