(ns eykt.state
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [db.core :as db]))

(def fsm [::rs/transition :main-fsm])

(defn send [& event]
  (rf/dispatch (apply conj fsm event)))

(def user-machine
  {:initial :s.initial
   :on      {:e.restart {:target [:. :s.initial]}
             :e.edit    {:target [:. :s.editing]}
             :e.store   {:target  [:> :user :s.store]
                         :actions [(assign (fn [st {:keys [data] :as _event}]
                                             (let [input (-> data :values :input)
                                                   uid (-> data :values :uid)]
                                               (db/database-set {:path ["some-path" uid] :value {:input input}})
                                               (tap> (l/ppr (-> data :values :input)))
                                               st)))]}}

   :states  {:s.initial {}
             :s.editing {}
             :s.store   {:after [{:delay  1000
                                  :target :s.initial}]}
             :s.ready   {}
             :s.error   {}}})

(def main
  ^{:transition-opts {:ignore-unknown-event? true}}
  {:id      :main-fsm
   :type    :parallel
   :context {:modal false}
   :regions {:user user-machine}})

(def *st
  (let [_ (rf/dispatch [::rs/start main])
        st (rf/subscribe [::rs/state (:id main)])]
    st))