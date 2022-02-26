(ns booking.fsm-model
  (:require [re-frame.core :as rf]
            [re-statecharts.core :as rs]
            [statecharts.core :refer [assign]]
            [schpaa.debug :as l]
            [schpaa.modal :as modal]
            [nrpk.fsm-helpers :refer [send]]
            [booking.database]
            [user.database]
            [user.views]
            [booking.content.blog-support]
            [booking.content.booking-blog]))

(defn confirm-booking []
  #_(apply send
           (modal/form-action
             {:primary "Ok"
              :title   "Bekreftet"
              :text    [:div.leading-normal
                        [:p "Bookingen er registrert. God tur!"]]})))

(def booking-machine
  {:initial :s.booking
   :on      {:e.restart        {:target [:> :booking :s.booking]}
             :e.cancel-booking {:actions [(assign (fn [st {:keys [data]}]
                                                    (js/alert (l/ppr ["@todo cancel booking" data]))
                                                    st))]
                                :target  [:> :booking :s.booking :s.basic-booking-info]}
             ;:e.confirm        [:> :booking]
             :e.complete       {:target  [:> :booking :s.booking :s.basic-booking-info]
                                :actions [(assign (fn [st {:keys [data] :as _event}]
                                                    (booking.database/write data)
                                                    (assoc st :last-booking data)))
                                          (fn [_ _]
                                            (rf/dispatch [:app/navigate-to [:r.forsiden]]))]}}
   :states  {:s.booking {:initial :s.basic-booking-info
                         :on      {:e.pick-boat {:target [:> :booking :s.booking :s.basic-booking-info]}
                                   :e.complete  {:target  [:> :booking :s.booking :s.basic-booking-info]
                                                 :actions [(assign (fn [st {:keys [data] :as _event}]
                                                                     (booking.database/write data)
                                                                     (assoc st :last-booking data)))
                                                           (fn [_ _]
                                                             (confirm-booking))
                                                           (fn [_ _]
                                                             (rf/dispatch [:app/navigate-to [:r.forsiden]]))]}}
                         :states  {:s.basic-booking-info {:on {:e.confirm [:> :booking :s.booking :s.confirm]}}
                                   :s.confirm            {}
                                   :s.complete           {}}}


             :s.confirm {}}})

(def main
  ^{:transition-opts {:ignore-unknown-event? true}}
  {:id      :main-fsm
   :type    :parallel
   :context {:modal false}
   :regions {:user    user.views/user-machine
             :booking booking-machine
             :blog    booking.content.booking-blog/fsm
             :modal   modal/modal-machine}})

(def *st
  (let [_ (rf/dispatch [::rs/start main])
        st (rf/subscribe [::rs/state (:id main)])]
    st))