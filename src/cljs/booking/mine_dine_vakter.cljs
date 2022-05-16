(ns booking.mine-dine-vakter
  (:require [schpaa.style.ornament :as sc]))

;region

(defn path-endringslogg [uid]
  ["beskjeder" uid "endringslogg"])

(defn path-beskjederinbox [uid]
  ["beskjeder" uid "inbox"])

(defn path-beskjedersent [uid]
  ["beskjeder" uid "sent" uid "posts"])

;endregion

(def regular-style
  {:style
   {:box-shadow "var(--shadow-1)"
    :background "var(--floating)"
    :flex       "1 0 0"
    :color      "var(--text1)"}})

(def saldo-yourfavour-style
  (let [[fg bg] ["var(--green-1)" "var(--green-8)"]]
    {:style {:flex       "1 0 0"
             :background bg
             :color      fg}}))

(def saldo-ourfavour-style
  (let [[fg bg] ["var(--orange-1)" "var(--orange-8)"]]
    {:style {:flex       "1 0 0"
             :background bg
             :color      fg}}))

(defn panel
  ([header content]
   (panel regular-style header content))
  ([attr header content]
   [sc/surface-a (merge-with into {:style {:padding-inline "var(--size-3)"
                                           :padding-block  "var(--size-2)"}} attr)
    [sc/col-space-2 {:style {:height          "100%"
                             :justify-content :space-between}}
     [sc/small2 header]
     [sc/title1 {:style {:color      "unset"
                         :text-align :right}}
      (if (some? content)
        (str content " timer")
        "Kommer snart")]]]))

(defn saldo-header [saldo timekrav fullførte-timer]
  [:div {:style {:display         :flex
                 :height          "auto "
                 :justify-content :space-between
                 :width           "100%"
                 :gap             "var(--size-2)"}}
   [panel "Overført '21" saldo]
   [panel "Vaktkrav '22" timekrav]
   (let [saldo-style #(if (neg? %) saldo-ourfavour-style saldo-yourfavour-style)
         result (- (+ saldo fullførte-timer) timekrav)
         style (saldo-style result)]
     [panel
      style
      [sc/col
       [sc/small2 style "Saldo"]
       [sc/small2 {:style (conj {:text-transform "uppercase"}
                                (:style style))}
        (if (neg? saldo) "I vår favør" "I din favør")]]
      result])])
