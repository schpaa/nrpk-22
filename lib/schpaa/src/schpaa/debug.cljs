(ns schpaa.debug
  (:require [clojure.pprint :as p  #_#_:refer [with-out-str]]
            [re-frame.core :as rf]))

(rf/reg-event-db :debug/in-debug-cancel (fn [db _]
                                          (assoc db :in-debug false)))

(rf/reg-sub :debug/in-debug? (fn [db] (:in-debug db false)))

(defn ppr [x]
  (p/with-pprint-dispatch
    p/code-dispatch
    (-> x p/pprint with-out-str)))

(defn ppre [& p]
  (if (seq p)
    [:pre.pre-wrap.text-xs.bg-black.text-yellow-300
     {:style {:white-space :pre-wrap}}
     (ppr (first p))]
    [:pre.text-xs.bg-blackblack.text-yellow-300
     {:style {:white-space :pre-wrap}}
     (ppr p)]))

(defn ppre-x [& p]
  [:div {:class [:bg-black :text-yellow-300 :text-xs :p-1]}
   (if (seq p)
     [:pre.pre-wrap
      {:style {:white-space :pre-wrap}}
      (doall (for [e p] (ppr e)))]
     [:pre
      {:style {:white-space :pre-wrap}}
      (ppr p)])])

