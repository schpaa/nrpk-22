(ns schpaa.debug
  (:require [clojure.pprint :as p]
            [lambdaisland.ornament :as o]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [booking.styles :as b]))

(rf/reg-event-db :debug/in-debug-cancel (fn [db _]
                                          (assoc db :in-debug false)))

(rf/reg-sub :debug/in-debug? (fn [db] (:in-debug db false)))

(defn ppr [x]
  (p/with-pprint-dispatch
    p/code-dispatch
    (-> x p/pprint with-out-str)))

(defn pre [& p]
  [:div {:class ["bg-black/90" :text-amber-300  :p-1]}
   (if (seq p)
     (for [e p]
       [:pre {:style {:white-space :pre-wrap}}
        (with-out-str (p/pprint e))])
     [:div
      {:style {:white-space :pre-wrap}}
      (with-out-str (p/pprint p))])])

(o/defstyled small :div
  {:font-size "75%"})

(defn pre-s [& p]
  [small
   [pre p]])

(def ppre pre)

(defn strp
  "Leverage the fact that printing will put a space between each entry.
  This is useful for things that accepts arguments as strings, like

  (defn pathData [{:keys [startX startY largeArcFlag endX endY]}]
    (strp \"M\" startX startY \"A\" 1 1 0 largeArcFlag 1 endX endY \"L\" 0 0)"
  [& s]
  (with-out-str (apply print s)))

(defn default-error-body [[err info]]
  (js/console.log "An error occurred: " err)
  [b/co {:style {:color :black}}
   [b/title "Error"]
   [:div (.-message info)]])

(defn boundary
  ([body]
   [boundary default-error-body body])
  ([_ _]
   (let [err-state (r/atom nil)]
     (r/create-class
       {:display-name        "ErrBoundary"
        :component-did-catch (fn [err info]
                               (reset! err-state [err info]))
        :reagent-render      (fn [error-body body]
                               (assert (fn? error-body) "Error body comp must be a function")
                               (if (nil? @err-state)
                                 body
                                 [error-body @err-state]))}))))
