(ns eykt.state
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [db.core :as db]
            [eykt.msg :as msg]
            [booking.database]
            [user.database]))

(def fsm [::rs/transition :main-fsm])

(defn send [& event]
  (rf/dispatch (apply conj fsm event)))

(def modal-machine
  {:initial :s.hidden
   :states  {:s.hidden       {:entry (assign (fn [st _] (assoc st :modal false)))
                              :on    {:e.show-with-timeout :s.with-timeout
                                      ;intent :e.show is not a very unique name
                                      :e.show              :s.visible}}
             :s.with-timeout {:entry (assign (fn [st {:keys [data] :as _event}]
                                               (assoc st
                                                 :modal-config-fn (:modal-config-fn data)
                                                 :modal true)))
                              :on    {:e.hide              :s.hidden
                                      ;showing again will close it
                                      :e.show-with-timeout :s.hidden}
                              :after [{:delay  (fn [_st {:keys [data] :as _event}] (:timeout data))
                                       :target :s.hidden}]}
             :s.visible      {:entry (assign (fn [st {:keys [data] :as _event}]
                                               (assoc st
                                                 :modal-config-fn (:modal-config-fn data)
                                                 :modal true)))
                              :on    {:e.hide :s.hidden
                                      ;showing again will close it
                                      :e.show :s.hidden}}}})

(def user-machine
  {:initial :s.initial
   :on      {:e.restart         {:target [:> :user :s.initial]}
             :e.edit            {:target [:> :user :s.editing]}
             :e.cancel-useredit {:target [:> :user :s.initial]}
             :e.store           {:target  [:> :user :s.store]
                                 :actions [(fn [st {:keys [data] :as _event}]
                                             (user.database/write (:values data))
                                             #_(let [values (-> data :values)
                                                     uid (-> values :uid)
                                                     values (dissoc values :uid)]
                                                 (db/firestore-set {:path ["users2" uid] :value values})
                                                 (db/database-update {:path ["users" uid] :value values})
                                                 (tap> values)
                                                 st))]}}

   :states  {:s.initial {}
             :s.editing {}
             :s.store   {:after [{:delay  1000
                                  :target :s.initial}]}
             :s.ready   {}
             :s.error   {}}})

(def booking-machine
  {:initial :s.booking
   :on      {:e.restart        {:target [:> :booking :s.booking]}
             :e.cancel-booking {:actions [(assign (fn [st {:keys [data]}]
                                                    (js/alert (l/ppr ["@todo cancel booking" data]))
                                                    st))]
                                :target  [:> :booking :s.booking :s.boat-picker]}
             :e.confirm        [:> :booking]
             :e.complete       {:target  [:> :booking :s.booking :s.boat-picker]
                                :actions [(assign (fn [st {:keys [data] :as _event}]
                                                    (booking.database/write data)
                                                    (assoc st :last-booking data)))
                                          (fn [_ _]
                                            (rf/dispatch [:app/navigate-to [:r.common]]))]}}
   :states  {:s.booking {:initial :s.boat-picker
                         :on      {:e.pick-boat {:target [:> :booking :s.booking :s.boat-picker]}
                                   :e.complete  {:target  [:> :booking :s.booking :s.boat-picker]
                                                 :actions [(assign (fn [st {:keys [data] :as _event}]
                                                                     (booking.database/write data)
                                                                     (assoc st :last-booking data)))
                                                           (fn [_ _]
                                                             (send :e.show
                                                               (msg/confirm-action
                                                                 {:ok    "Ok"
                                                                  :title "Bekreftet"
                                                                  :text  [:div.leading-normal
                                                                          [:p "Bookingen er registrert. God tur!"]]})))
                                                           (fn [_ _]
                                                             (rf/dispatch [:app/navigate-to [:r.common]]))]}}
                         :states  {:s.boat-picker {:on {:e.confirm [:> :booking :s.booking :s.confirm]}}
                                   :s.confirm     {}
                                   :s.complete    {}}}


             :s.confirm {}}})

(def main
  ^{:transition-opts {:ignore-unknown-event? true}}
  {:id      :main-fsm
   :type    :parallel
   :context {:modal false}
   :regions {:user    user-machine
             :booking booking-machine
             :modal modal-machine}})

(def *st
  (let [_ (rf/dispatch [::rs/start main])
        st (rf/subscribe [::rs/state (:id main)])]
    st))