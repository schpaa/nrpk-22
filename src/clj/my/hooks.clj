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

(defn write-styles-hook
  {:shadow.build/stage :flush}
  [build-state & args]
  (my.tokens/set-it)
  (spit "public/booking/css/ornament.css"
        (str
          (slurp "template/css/colors.css")
          "\n"
          (gc/compile-css {:pretty-print? true} (concat
                                                  girouette-preflight/preflight
                                                  global-styles))
          "\n"
          #_(css (at-font-face {:font-family           "'Inter var experimental', sans-serif"
                                :font-weight           "100 900"
                                :font-display          :swap
                                :font-style            "oblique 0deg 10deg"
                                :font-feature-settings "'salt', 'zero'"
                                :src                   [(url "'/fonts/Inter.var.woff2?v=3.19'")
                                                        (format "'woff2'")]}))
          "\n"
          (o/defined-styles)))
  build-state)
