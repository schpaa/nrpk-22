(ns booking.modals.boatinput.actions
  (:require [re-frame.core :as rf]
            [db.core]
            [tick.core :as t]))

(rf/reg-fx :rent/write (fn [data]
                         (let [list (into {}
                                          (map (fn [{:keys [id]}] [id ""])
                                               (:list data)))]
                           (tap> {:list list
                                  :data data})
                           (db.core/database-push
                             {:path  ["activity-22"]
                              :value {:timestamp (str (t/now))
                                      :sleepover (:moon data)
                                      :adults    (:adults data)
                                      :havekey   (:key data)
                                      :children  (:children data)
                                      :juveniles (:juveniles data)
                                      :uid       (:uid data)
                                      :list      list}}))))

(rf/reg-event-fx :rent/store
                 (fn [_ [_ data]]
                   {:fx [[:rent/write data]]}))


(defn- confirm-command [st]
  (let [loggedin-uid @(rf/subscribe [:lab/uid])
        ok? (and (pos? (+ (:adults @st) (:juveniles @st) (:children @st)))
                 (pos? (count (:list @st)))
                 (or (and (pos? (count (:item @st)))
                          (some #{(:item @st)} (map :number (:list @st))))
                     (empty? (:item @st))))]
    ;(js/alert "Dialogen blir værende men du blir flyttet (hvis du ikke allerede er der) til den siden som viser en liste over alle dine aktiviteter. Denne siste registreringen vil ligge øverst i listen.")
    (when ok?
      ;todo add to db here
      (rf/dispatch [:rent/store (assoc @st :uid loggedin-uid)])
      ;todo add counter increment update here?
      (rf/dispatch [:app/navigate-to [:r.utlan]])
      (rf/dispatch [:modal.boatinput/close])

      #_(booking.aktivitetsliste/add-command (assoc @st :start (t/instant (t/now))))
      true)))