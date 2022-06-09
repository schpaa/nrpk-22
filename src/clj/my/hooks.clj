(ns my.hooks
  (:require
    [lambdaisland.ornament :as o]
    [garden.stylesheet :refer [at-font-face]]
    [garden.def :refer [defcssfn]]
    [garden.core :refer [css]]
    [garden.compiler :as gc]
    [girouette.tw.preflight :as girouette-preflight]
    [my.tokens]))

(defcssfn url)
(defcssfn format)

(def global-styles [[:body {:font-family           "Inter"
                            :font-weight           400
                            :font-size             "1rem"
                            :font-feature-settings "'cv10', 'salt', 'zero'"}]])

(defn produce [filename c]
  (spit filename c))

;;this is a commentary

(defn write-styles-hook
  {:shadow.build/stage :flush}
  [build-state & args]
  (my.tokens/set-it!)
  (-> "public/booking/css/ornament.css"
      (produce (apply str (interpose "\n")
                      [(gc/compile-css
                        {:pretty-print? false}
                        (concat  girouette-preflight/preflight global-styles))
                       (o/defined-styles)
                       (slurp "template/css/colors.css")])))
  (println "rebuild styles")
  build-state)

(comment
  (write-styles-hook))
