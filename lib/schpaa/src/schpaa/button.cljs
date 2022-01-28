(ns schpaa.button
  (:require [schpaa.style :as st]))

(defn cta-button [attr content]
  (let [{:keys [bg- fg- bg fg- fg fg+ hd p p- he]} (st/fbg' :button-cta)]
    [:button.btn (conj attr
                       {:class (if (:disabled attr) (concat bg- fg-) (concat bg fg))})

     content]))

(defn regular-button [attr content]
  (let [{:keys [bg- fg- bg fg- fg fg+ hd p p- he]} (st/fbg' :button)]
    [:button.btn (conj attr
                       {:class (if (:disabled attr) (concat bg- fg-) (concat bg fg))})

     content]))

(defn danger-button [attr content]
  (let [{:keys [bg- bg fg- fg]} (st/fbg' :button-danger)]
    [:button.btn (conj attr
                       {:class (if (:disabled attr)
                                 (concat bg- fg-)
                                 (concat bg fg))})
     content]))

(defn just-buttons [vbuttons]
  (fn []
    [:div.flex.gap-2.justify-end
     (for [e vbuttons]
       (cond
         (vector? e)
         (let [[caption color-class action] e]
           (case color-class
             :button-danger
             (danger-button {:on-click action} caption)
             :button-cta
             (cta-button {:on-click action} caption)
             (regular-button {:on-click action} caption)))
         :else (case e
                 :grow [:div.flex-grow]
                 [:div])))]))