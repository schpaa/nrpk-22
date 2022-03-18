(ns booking.content.blog-support
  (:require [reagent.core :as r]
            [schpaa.debug :as l]))

(defn err-boundary
  [& children]
  (let [err-state (r/atom nil)]
    (r/create-class
      {:display-name        "ErrBoundary"
       :component-did-catch (fn [err info]
                              (reset! err-state [err info]))
       :reagent-render      (fn [& children]
                              (if (nil? @err-state)
                                (into [:<>] children)
                                (let [[_ info] @err-state]
                                  [l/ppre-x info])))})))
