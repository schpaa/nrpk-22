(ns my.hooks
  (:require
    [lambdaisland.ornament :as o]
    [garden.stylesheet :refer [at-font-face]]
    [garden.def :refer [defcssfn]]
    [garden.core :refer [css]]
    [garden.compiler :as gc]
    [girouette.tw.preflight :as girouette-preflight]
    [my.tokens]))

;(defcssfn url)
;(defcssfn format)

(def global-styles [[:body {:font-family           "Inter"
                            :font-weight           400
                            :font-size             "1rem" 
                            :font-feature-settings "'cv10', 'salt', 'zero'"}]])


;;this is a commentary

(defn write-styles-hook
  {:shadow.build/stage :flush}
  [build-state & args]
  (require 'my.tokens :reload)
  (my.tokens/set-it!)
  (spit "public/booking/css/ornament.css"
           (apply str (interpose "/* test */\n")
                  [(slurp "template/css/colors.css")
                   (gc/compile-css
                     {:pretty-print? false}
                     (concat girouette-preflight/preflight global-styles))
                   (o/defined-styles)]))
  (println "(eykt) corebuilt styles")
  build-state)

(comment
  (write-styles-hook))
