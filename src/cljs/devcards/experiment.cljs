(ns devcards.experiment
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require #_[devcards.core :as dc :include-macros true]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [booking.styles :as b]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]
            [schpaa.debug :as l]
            [reagent.core :as r]))

(defprotocol Functionality
  (compute [this _])
  (draw [this _]))

(deftype ProgConstruct [st]
  IEquiv
  (-equiv [o other]
    (prn (:st other))
    (= (:st o) (:st other)))
  Functionality
  (compute [this _])
  (draw [this _]
    (prn "THIS is " this)))

(reify Functionality
  (compute [x z])
  (draw [y z]))

(comment
  (let [x (->ProgConstruct {})
        y (->ProgConstruct {:z 1})]
    (= x y)))

(defrecord Component [m]
  Functionality
  (compute [_ _])
  (draw [this x]
    [sc/surface-a {:class (o/classname sc/sample-123)}
     [sc/co
      [b/title (str m)]
      [b/ro-jb {:class [:ps-1]
                :style    {:overflow "hidden"
                           :height   "min-content"}}
       [b/text-truncate 'aa-undermined]
       [button/just-icon {:class [:selected :square-top :square-bottom]} ico/chart]]
      [l/pre this]
      [b/small 'aa-small]
      [sc/row-field
       [:div 'b]
       [:div 'c]]]]))

(defrecord Simplex [state]
  Functionality
  (compute [this data]
    (swap! state assoc :data data)
    this)
  (draw [this n]
    [l/pre-s (if state @state) (str "n=" n)]))

#_(defcard-rg example-1
    (fn [data]
      [:div
       (let [c (->Component {:orig 'opts2})]
         [sc/grid {:style {:color "var(--gray-4)"}}
          [draw c @data]
          [draw c @data]
          [draw c @data]
          [draw c @data]
          [draw c @data]
          [draw c nil]
          [sc/row-field {:class [:justify-between]}
           [draw c @data]
           [draw c @data]]])])
    {:value :data1})

;^{:doc "external state"}
(def  st (r/atom {}))

#_(do
    (let [s (->Component {:orig 'opts2})
          x (->Simplex (r/atom {:st 'initial-state}))
          x (compute x {:new 'data1})
          x (compute x {:new 'data2})]
      (reset! st {:series (iterate (fn [_] (rand-nth [s x])) nil)})))


(comment
  (take 4 (:series @st)))

(defn initial-st []
  (let [s (->Component {:orig 'opts2})
        x (->Simplex (r/atom {:st 'initial-state}))
        x (compute x {:new 'data1})
        x (compute x {:new 'data2})]
    {:dummy 1231
     :series #(iterate (fn [_] (rand-nth [s x])) nil)}))

(defcard-rg example-1
  (fn [st]
    [sc/co
     (draw (->Simplex (r/atom {:desc "This is just too abstract"})) 2)
     (draw (->Simplex (r/atom {})) 1)
     [l/pre @st]]
    #_(for [e (take 5 (:series @st))]
        [l/pre e #_(draw e 1)]))
  (initial-st)
  {:padding false
   :projection (fn [st] st)
   :watch-atom true
   :inspect-data true})

(defcard-rg example-2
  ;"some docs"
  (fn [d]
    [:div
     [l/pre d]
     [:div '{:edn 'this-is}]])
  {:stuff (r/atom {:data :ugh})}
  {:inspect-data true})

(def some-data (r/atom {:data :ugh2}))

(defcard-rg example-3
  (fn [d]
    [sc/co {:class [:m-4]}
     (draw (:s @d) 1)
     [:div.error
      {:class (o/classname sc/error)}
      (-> d deref :stuff :data deref :data)]

     #_[:div '{:edn 'this-is}]])
  {:s (Simplex. (r/atom {:atom 123}))
   :stuff {:data some-data}}
  {;:classname (o/classname sc/error)
   :watch-atom true
   ;:heading false
   :frame false
   :padding false})


