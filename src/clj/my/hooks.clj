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

(defn produce [filename] 
  (spit filename
        (str
          (slurp "template/css/colors.css")
          "\n"                         
          (gc/compile-css {:pretty-print? true} (concat
                                                  girouette-preflight/preflight
                                                  global-styles))
          "\n"
          (o/defined-styles))))

(defn write-styles-hook
  {:shadow.build/stage :flush}
  [build-state & args]
  (my.tokens/set-it)
  (produce "public/booking/css/ornament.css")
  #_(produce "public/devcards/css/ornament.css")
  build-state)

(comment
  (write-styles-hook))