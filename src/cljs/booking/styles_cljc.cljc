(ns booking.styles-cljc
  (:require [lambdaisland.ornament :as o]))

(def popup-borders
 (apply str (interpose ","
                       ["0 0 0 8px var(--floating)"
                        "0 0 0 16px var(--brand2)"
                        "0 0 0 24px var(--floating)"
                        "var(--shadow-6)"])))

