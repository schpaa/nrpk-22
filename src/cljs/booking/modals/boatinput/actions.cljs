(ns booking.modals.boatinput.actions
  (:require [re-frame.core :as rf]
            [db.core]
            [tick.core :as t]))

(defn prepare-data [loggedin-uid td data]
  {:timestamp (str td)
   :sleepover (:moon data)
   :adults    (:adults data)
   :havekey   (:litteral-key data)
   :phone     (or
                (some-> data :extra second)
                (some-> data :textfield :phone))
   :children  (:children data)
   :juveniles (:juveniles data)
   :ref-uid   (get-in data [:extra 2])
   :uid       loggedin-uid
   :list      (into {}
                    (map (fn [{:keys [id]}] [id ""])
                         (:list data)))})

(rf/reg-fx :rent/write (fn [data]
                         (let [now (t/now)]
                           (db.core/database-push
                             {:path  ["activity-22"]
                              :value data}))))

(rf/reg-event-fx :rent/store
                 (fn [_ [_ data]]
                   {:fx [[:rent/write data]]}))

(defn- confirm-command [st]
  (let [loggedin-uid @(rf/subscribe [:lab/uid])]
    ;todo add to db here
    (rf/dispatch [:rent/store (prepare-data loggedin-uid (t/now) @st)])

    ;todo add counter increment update here?
    (rf/dispatch [:app/navigate-to [:r.utlan]])
    (rf/dispatch [:modal.boatinput/close])

    #_(booking.aktivitetsliste/add-command (assoc @st :start (t/instant (t/now))))
    true))