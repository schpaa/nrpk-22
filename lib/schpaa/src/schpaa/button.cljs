(ns schpaa.button
  (:require [schpaa.style :as st]
            [nrpk.fsm-helpers]
            [schpaa.icon :as icon]))

(defn cta-button [attr content]
  (let [{:keys [bg- fg- bg fg- fg fg+ hd p p- he]} (st/fbg' :button-cta)]
    [:button.btn.rounded.shadow-sm (conj attr
                                         {:class (if (:disabled attr) (concat bg- fg-) (concat bg fg))})

     (if (keyword? content) (icon/small content) content)]))

(defn regular-hollow-button [attr content]
  (let [{:keys [bgt bgb bg fg- fg bgc br]} (st/fbg' :button)]
    [:button.xbtn (merge-with into attr
                              {:class (if (:disabled attr)
                                        (concat fg bgt)
                                        (concat fg bg))})
     (if (keyword? content) (icon/touch content) content)]))

(defn regular-button [attr content]
  (let [{:keys [bg- bg fg- fg brb br]} (st/fbg' :button)]
    [:button.btn.rounded.shadow-sm (conj attr
                                         {:class (if (:disabled attr) (concat bg- fg-) (concat bg fg))})
     (if (keyword? content) (icon/small content) content)]))

(defn listitem-button-danger-small [attr content]
  (let [{:keys [bg- bg bg+ fg- fg brb]} (st/fbg' :listitem-button-danger)]
    [:button.btnx.btn-smallx.w-12.h-12.shrink-0
     (conj attr
           {:class (if (:disabled attr) (concat bg- fg- brb)
                                        (concat bg fg))})
     (if (keyword? content) (icon/small content) content)]))

(defn listitem-button-small-clear [attr content]
  (let [{:keys [bg- bg bg+ fg- fg br brb]} (:color-map attr)]
    [:button.w-10.h-10.shrink-0.m-1.rounded-md
     (conj (dissoc attr :color-map)
           {:class (if (:disabled attr) (concat bg- fg- brb)
                                        (concat bg fg br))})
     (if (keyword? content) (icon/small content) content)]))

(defn listitem-button-small-justclear [attr content]
  (let [{:keys [bg- bg bg+ fg- fg br brb]} (:color-map attr)]
    [:button.w-10.h-10.shrink-0.rounded-md
     (conj (dissoc attr :color-map)
           {:class (if (:disabled attr) (concat bg- fg- brb)
                                        (concat bg fg br))})
     (if (keyword? content) (icon/small content) content)]))

(defn listitem-button-small [attr content]
  (let [{:keys [bg- bg bg+ fg- fg brb]} (st/fbg' :listitem-button)]
    [:button.btnx.btn-smallx.w-12.h-12.shrink-0
     (conj attr
           {:class (if (:disabled attr) (concat bg- fg- brb)
                                        (concat bg fg))})
     (if (keyword? content) (icon/small content) content)]))

(defn regular-button-small [attr content]
  (let [{:keys [bg- bg bg+ fg- fg brb]} (st/fbg' :button)]
    [:button.btnx.btn-small.w-12.h-12.shrink-0
     (conj attr
           {:class (if (:disabled attr) (concat bg- fg- brb)
                                        (concat bg+ fg brb))})
     (if (keyword? content) (icon/small content) content)]))

(defn regular-button-small-toggled-off [attr content]
  (let [{:keys [bg- bg bg+ fg- fg brb bgc]} (st/fbg' :button)]
    [:button.btnx.btn-small.shrink-0.w-12.h-12
     (conj attr
           {:class (if (:disabled attr) (concat bg- fg- brb) (concat bgc fg- brb))})
     (if (keyword? content) (icon/small content) content)]))

(defn hollow-button-small [attr content]
  (let [{:keys [bg- bg fg- fg br brb bgc]} (st/fbg' :button)]
    [:button.xbtn.btn-small.bg-transparent.shrink-0.w-12.h-12
     (conj attr
           {:class (if (:disabled attr)
                     (concat bg- fg- brb)
                     (concat bgc fg br))})
     (if (keyword? content) (icon/small content) content)]))

(defn danger-button-small [attr content]
  (let [{:keys [bg- fg- bg fg- fg fg+ hd p p- he brb]} (st/fbg' :button-danger)]
    [:button.btnx.btn-small.w-12.h-12.shrink-0
     (conj attr
           {:class (if (:disabled attr) (concat bg- fg- brb) (concat brb bg fg+))})
     (if (keyword? content) (icon/small content) content)]))

(defn danger-button [attr content]
  (let [{:keys [bg- bg fg- fg]} (st/fbg' :button-danger)]
    [:button.btn (conj attr
                       {:class (if (:disabled attr)
                                 (concat bg- fg-)
                                 (concat bg fg))})
     (if (keyword? content) (icon/small content) content)]))

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