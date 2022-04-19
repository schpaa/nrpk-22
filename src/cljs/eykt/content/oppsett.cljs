(ns eykt.content.oppsett
  (:require [eykt.calendar.core]
            [schpaa.style :as st]
            [schpaa.debug :as l]
            [tick.core :as t]))

(defn item [{:keys [description startdate enddate]}]
  (let [{:keys [fg fg+ p+ bg hd he p fg-]} (st/fbg' :form)]
    [:div
     [:div {:class p+} (str description)]
     [:div {:class p}
      (t/format "d. " startdate) (times.api/month-name startdate)
      " — "
      (t/format "d. " enddate) (times.api/month-name enddate)]]))

(defn render [r]
  (let [{:keys [fg fg+ bg hd he p fg-]} (st/fbg' :form)]
    [:div.p-4
     {:class (concat fg bg)}
     [:div "..."]
     (into [:div.space-y-4]
           (map item eykt.calendar.core/short-rules))
     [:div.space-y-1
      (for [e eykt.calendar.core/short-rules]
        [l/ppre-x e])]]))
