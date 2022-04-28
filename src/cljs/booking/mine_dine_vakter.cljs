(ns booking.mine-dine-vakter
  (:require [schpaa.style.ornament :as sc]))

(def regular-style
  {:style
   {:box-shadow "var(--shadow-1)"
    :background "var(--floating)"
    :flex       "1 0 0"}})

(def saldo-yourfavour-style
  (let [[fg bg] ["var(--green-1)" "var(--green-8)"]]
    {:style {;:transform  "rotate(-3deg)"
             :flex       "1 0 0"
             :background bg
             :color      fg}}))

(def saldo-ourfavour-style
  (let [[fg bg] ["var(--orange-1)" "var(--orange-8)"]]
    {:style {:flex       "1 0 0"
             ;:transform  "rotate(3deg)"
             :background bg
             :color      fg}}))

(defn panel
  ([header content]
   (panel regular-style header content))
  ([attr header content]
   [sc/surface-a (merge-with into {:style {:padding "var(--size-2)"}} attr)
    [sc/col-space-2 {:style {:height          "100%"
                             :justify-content :space-between}}
     [sc/small2 header]
     [sc/title1 {:style {:color      "unset"
                         :text-align :right}}
      (if (some? content)
        (str content " timer")
        "Kommer snart")]]]))

(defn header [{:keys [saldo timekrav z]}]
  [:div {:style {:display         :flex
                 :height          "var(--size-10)"
                 :justify-content :space-between
                 :width           "100%"
                 :gap             "var(--size-2)"}}
   [panel "Overført '21" saldo]
   [panel "Vaktkrav '22" timekrav]
   (let [saldo-style (if (neg? z) saldo-ourfavour-style saldo-yourfavour-style)]
     [panel
      saldo-style
      [sc/col
       [sc/small2 saldo-style "Saldo"]
       [sc/small2 {:style (conj {:color          "unset"
                                 :text-transform "uppercase"}
                                (:style saldo-style))}
        (if (neg? z) "I vår favør" "I din favør")]]
      z])])
