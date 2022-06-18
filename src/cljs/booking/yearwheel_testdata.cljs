(ns booking.yearwheel-testdata
  (:require [reagent.core :as r]
            [tick.core :as t]))

(comment
  (defonce st (reduce (fn [a e] (assoc a (subs (str (random-uuid)) 0 5) (dissoc e :id)))
                      {}
                      [{:date nil :content "a"}
                       {:date nil :content "d"}
                       {:date nil :content "d"}
                       {:date (str (t/at (t/new-date 2023 3 16) (t/noon))) :content "Styremøte"}
                       {:date (t/now) :content "b"}
                       {:date nil :content "c"}]))
  (def data (r/atom st)))

(defonce data (r/atom (reduce (fn [a {:keys [id tldr type date content] :as e}]
                                (update a id assoc
                                        :type type
                                        :tldr tldr
                                        :id id
                                        :date (some-> date t/date-time t/date)
                                        :content content))
                              {}
                              (map #(assoc % :created (str (t/now))
                                             :id (subs (str (random-uuid)) 0 5))
                                   [

                                    {:type 9
                                     :tldr "En lang tekst som viser hvordan bryting fungerer, dvs når setningene strekker seg over flere linjer"
                                     :date (str (t/at (t/new-date 2022 4 28) (t/noon)))}
                                    {:date (str (t/at (t/new-date 2022 5 11) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 5 31) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 6 14) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 8 10) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 8 30) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 9 21) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 10 10) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 11 1) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 11 23) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2022 12 6) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 4) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 1 24) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 2 15) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 3 8) (t/noon))) :type 8}
                                    {:date (str (t/at (t/new-date 2023 3 16) (t/noon))) :type 8}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 6 20) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 6 24) (t/noon)))}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 8 8) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 8 12) (t/noon)))}
                                    {:type 10

                                     :date (str (t/at (t/new-date 2022 8 12) (t/noon)))
                                     :to   (str (t/at (t/new-date 2022 8 19) (t/noon)))}]))))