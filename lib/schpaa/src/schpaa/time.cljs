(ns schpaa.time
  (:require [reagent.core :as r]
            [tick.core :as t']
            [times.api :as t]
            [re-frame.core :as rf]))

(defn flex-date
  ([{:keys [time-class if-never]} dt time-fn]
   (r/with-let [current-time (r/atom (t'/now))
                state (rf/subscribe [:app/show-relative-time])
                timer (js/setInterval #(reset! current-time (t'/now)) 1000)]
               [:div.truncate.inline
                {:on-click #(do
                              (.stopPropagation %)
                              (rf/dispatch [:app/show-relative-time-toggle]))}
                [:span {:class (concat
                                 [:cursor-pointer :hover:underline]
                                 time-class)}
                 (if (some? dt)
                   (if @state
                     (t'/format "YYYY-MM-dd 'kl' HH:mm:SS" dt)
                     (time-fn @current-time))
                   if-never)]]
               (finally js/clearInterval timer)))
  ([dt]
   [flex-date {} dt]))

(defn x [timestamp]
  (when-let [tm (some-> timestamp .toDate t/ms->local-time)]
    (schpaa.time/flex-date {} tm (fn [_i] (t/relative-local-time
                                            {:past-prefix           ""
                                             ;:rounding-at :hours
                                             :progressive-rounding? true}
                                            tm)))))

(defn y [timeinstant]
  (when-let [tm timeinstant]
    (schpaa.time/flex-date {} tm (fn [_i] (t/relative-local-time
                                            {:past-prefix           "for"
                                             :progressive-rounding? true}
                                            tm)))))