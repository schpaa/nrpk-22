(ns my.tokens
  (:require [lambdaisland.ornament :as o]
            [garden.selectors :as s]))


(defn set-it! []
  (o/set-tokens! {:components [{:id     :debug
                                :garden [:& {:opacity    1
                                             :outline      "2px solid red"}]}
                               {:id     :disabled-button
                                :garden [:& {:opacity    0.5
                                             :color      "var(--surface000)"
                                             :background "var(--surface00)"}]}
                               {:id     :bg-focused-field
                                :garden [:&
                                         [:&:focus {:background-color "var(--surface000)"}]]}
                               {:id     :bg-unfocused-field
                                :garden [:& {:background "var(--surface0)"}]}
                               {:id     :flex-center
                                :garden [:& {;:color :red
                                             :display         :flex
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
                               :bleu-100 "4e7baf"}}))

#_(do
    (defmacro try-some-> [& forms]
      `(try
        (some-> ~@(forms))
        (catch Error _ nil)))

    (macroexpand-1 '(try-some-> z str))

    (defn try-some-> [& x]
      (try (apply some-> x) (catch js/Error _ nil))))
