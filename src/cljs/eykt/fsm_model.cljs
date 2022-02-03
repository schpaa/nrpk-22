(ns eykt.fsm-model
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [user.views]
            [schpaa.modal :as modal]
            [eykt.content.rapport-side]))

(def main
  ^{:transition-opts {:ignore-unknown-event? true}}
  {:id      :main-fsm
   :type    :parallel
   :context {:modal false}
   :regions {:user    user.views/user-machine
             ;:booking booking-machine
             :rapport eykt.content.rapport-side/rapport-fsm
             :modal   modal/modal-machine}})

(def *st
  (let [_ (rf/dispatch [::rs/start main])
        st (rf/subscribe [::rs/state (:id main)])]
    st))