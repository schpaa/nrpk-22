(ns eykt.state
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]))

(def fsm [::rs/transition :main-fsm])

(defn send [& event]
  (rf/dispatch (apply conj fsm event)))

(def user-machine
  {:initial :s.initial
   :on      {:e.restart        {:target [:. :s.initial]}
             :e.store          {:target [:. :s.store]}
             :e.read-user-pref [{:guard  #(pos? 1 #_(rand-int 2))
                                 :target [:. :s.reading-users-pref]}
                                {:target [:. :s.error]}]}
   :states  {:s.initial            {:entry #(send :e.hide)
                                    :after [{:delay   1000
                                             :actions #(send :e.read-user-pref)}]}
             :s.store              {:after [{:delay  1001
                                             :target :s.next-step}]}
             :s.reading-users-pref {:after [{:delay  1001
                                             :target :s.next-step}]}
             :s.next-step          {:after [{:delay  1001
                                             :target :s.last-step}]}
             :s.last-step          {:after [{:delay  1000
                                             :target :s.ready}]}
             :s.ready              {}
             :s.error              {}}})

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