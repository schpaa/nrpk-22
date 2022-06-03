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

(defn write-styles-hook
  {:shadow.build/stage :flush}
  [build-state & args]
  (my.tokens/set-it)
  (let [content (str
                  (gc/compile-css {:pretty-print? false}
                                  (concat
                                    girouette-preflight/preflight
                                    global-styles))
                  "\n"
                  (o/defined-styles)
                  "\n"
                  (slurp "template/css/colors.css"))] 
    (produce "public/booking/css/ornament.css" content))
  (println "rebuild styles")
  build-state)

(comment
  (write-styles-hook))