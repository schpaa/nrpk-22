(ns schpaa.button
  (:require [schpaa.style :as st]
            [nrpk.fsm-helpers]))

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

(defn build-buttonbar-content [{:keys [buttons button-captions ok form-id]}]
  (cond-> [:div.flex.justify-end.gap-4]
    (:close buttons) (conj (regular-button {:on-click #(nrpk.fsm-helpers/send :e.hide)} (or (and button-captions (button-captions :close)) "Ok")))
    (:submit buttons) (conj (regular-button {:type :submit :form form-id :on-click #(nrpk.fsm-helpers/send :e.hide)} (or (and button-captions (button-captions :submit)) "Submit")))
    (:ok buttons) (conj (danger-button {:on-click #(do (ok) (nrpk.fsm-helpers/send :e.hide))} (or (and button-captions (button-captions :ok)) "Ok")))
    (:cancel buttons) (conj (regular-button {:on-click #(nrpk.fsm-helpers/send :e.hide)} (or (and button-captions (button-captions :cancel)) "Avbryt")))))