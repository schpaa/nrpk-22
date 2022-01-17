(ns eykt.fsm-helpers
  (:require [re-frame.core :as rf]
            [re-statecharts.core]))

(defn send [& event]
  (rf/dispatch (apply conj [:re-statecharts.core/transition :main-fsm] event)))