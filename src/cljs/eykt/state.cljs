(ns eykt.state
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [db.core :as db]
            [booking.database]
            [user.database]))

(def fsm [::rs/transition :main-fsm])

(defn send [& event]
  (rf/dispatch (apply conj fsm event)))

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
   :on      {:e.restart         {:target [:> :booking :s.initial]}
             :e.book-now        {:target [:. :s.booking]}
             :e.cancel-booking  {:actions [(assign (fn [st {:keys [data]}]
                                                     (js/alert (l/ppr ["@todo cancel booking"
                                                                       data]))
                                                     st))]
                                 :target  [:. :s.initial]}
             :e.confirm-booking {:target  [:> :booking :s.booking :s.boat-picker]
                                 :actions [(assign (fn [st {:keys [data] :as _event}]
                                                     (booking.database/write data #_{:uid   uid
                                                                                     :value values})
                                                     (assoc st :last-booking data)
                                                     #_(let [prepare-data (fn [values]
                                                                            (-> values
                                                                                (update :date str)
                                                                                (update :start-time str)
                                                                                (update :end-time str)
                                                                                ;fixme: deref before send
                                                                                (update :selected deref)))
                                                             v (-> data :values)
                                                             uid (-> v :uid)
                                                             values (dissoc (prepare-data v) :uid)]
                                                         ;(tap> [uid values])
                                                         (booking.database/write {:uid   uid
                                                                                  :value values})
                                                         ;(db/firestore-set {:path ["booking2" uid] :value values})
                                                         ;(db/database-set {:path ["booking2" uid] :value values})
                                                         (assoc st :last-booking values))))
                                           (fn [_ _]
                                             (rf/dispatch [:app/navigate-to [:r.common]]))]}}
   :states  {:s.initial         {}
             :s.booking         {:initial :s.boat-picker
                                 :on      {:e.edit-basics {:target [:> :booking :s.booking :s.initial]}
                                           :e.pick-boat   {:target [:> :booking :s.booking :s.boat-picker]}
                                           #_#_:e.confirm     {:target [:> :booking :s.booking :s.confirm]}}
                                 :states  {:s.edit-basics {}
                                           :s.initial     {}
                                           :s.boat-picker {}
                                           #_#_:s.confirm     {}}}
             :s.confirm-booking {}}})

(def main
  ^{:transition-opts {:ignore-unknown-event? true}}
  {:id      :main-fsm
   :type    :parallel
   :context {:modal false}
   :regions {:user    user-machine
             :booking booking-machine}})

(def *st
  (let [_ (rf/dispatch [::rs/start main])
        st (rf/subscribe [::rs/state (:id main)])]
    st))