(ns my.tokens
  (:require [lambdaisland.ornament :as o]
            [garden.selectors :as s]))


(o/set-tokens! {:components [{:id     :flex-center
                              :garden [:& {:display         :flex
                                           :justify-content :center
                                           :align-items     :center}]}
                             {:id     :font-oswald
                              :garden [:& {
                                           :font-family "oswald"}]}
                             {:id     :tab
                              :garden [:& {:background-color "var(--button1)"
                                           :color            "var(--button0)"}]}
                             {:id     :batton
                              :garden [:&
                                       ;(s/attr= :type "submit")
                                       {:background-color "var(--button1)"
                                        :color            "var(--button0)"}
                                       [:&:hover {:background-color "var(--button2)"}]]}
                             {:id     :button-danger
                              :garden [:& {:background-color "var(--buttondanger1)"
                                           :color            "var(--buttondanger0)"}
                                       [:&:hover {:background-color "var(--buttondanger2)"}]]}
                             #_{:id     :button-cta
                                :garden [:& {:background-color "var(--buttoncta1)"
                                             :color            "var(--buttoncta0)"}
                                         [:&:hover {:background-color "var(--buttoncta2)"}]]}]
                :colors     {:ugh      "AA0099"
                             :primary  "FFAA00"
                             :bleu-900 "112123"
                             :bleu-800 "132c31"
                             :bleu-700 "143841"
                             :bleu-600 "164351"
                             :bleu-500 "1a4f63"
                             :bleu-400 "215a76"
                             :bleu-300 "2d6589"
                             :bleu-200 "3c709c"
                             :bleu-100 "4e7baf"}})