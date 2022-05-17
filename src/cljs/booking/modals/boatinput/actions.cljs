(ns booking.modals.boatinput.actions
  (:require [re-frame.core :as rf :refer [reg-fx reg-event-fx]]
            [db.core]
            [tick.core :as t]))

(defn prepare-data [{:keys [uid timestamp state]}]
  (let [{:keys [litteral-key moon adults children juveniles]} state]
    {:uid       uid
     :ref-uid   (get-in state [:extra 2])
     :timestamp timestamp
     :adults    adults
     :juveniles juveniles
     :children  children
     :sleepover moon
     :havekey   litteral-key
     :phone     (or
                  (some-> state :extra second)
                  (some-> state :textfield :phone))
     :list      (into {}
                      (map (fn [{:keys [new id number]}]
                             (if (or new (nil? id))
                               [(str number) ""]
                               [id ""]))
                           (:list state)))}))

(defn upgrade-boat-numbers [data new-data]
  (let [list-data (into {} (map (fn [[k v]] [(or (get new-data k) k) v]) (:list data)))]
    (tap> [:upgrade-boat-numbers list-data])
    (assoc-in data [:list]
              (into {} (map (fn [[k v]] [(or (get new-data k) k) v]) list-data)))))

(reg-event-fx :rent/store
              (fn [_ [_ data new-boat-map]]
                {:fx [[:rent/write [data new-boat-map]]]}))

(defn- create-boat! [number]
  (let [n (str number)
        k (db.core/database-push
            {:path  ["boad-item"]
             :value {:number    n
                     :boat-type "undefined-brand"
                     :location  "0"}})]
    [n (.-key k)]))

(rf/reg-event-fx :create-boats
                 (fn [{db :db} [_ boat-numbers]]
                   (let [;{"999" "-SOMEIDENTITY1"}
                         new-data (into {} (map create-boat! boat-numbers))]
                     {:db (assoc db :boatnumber-to-uuid new-data)})))

(defn- confirm-command [st]
  (let [uid @(rf/subscribe [:lab/uid])
        prep (prepare-data {:uid       uid
                            :timestamp (str (t/now))
                            :state     @st})
        list-of-keys (-> prep :list keys)]
    (rf/dispatch [:create-boats list-of-keys])
    (when-let [new-data (rf/subscribe [:wait-for-the-things])]
      (rf/dispatch [:rent/store prep @new-data]))
    ;todo add counter increment update here?
    (rf/dispatch [:app/navigate-to [:r.utlan]])
    (rf/dispatch [:modal.boatinput/close])
    true))

(rf/reg-sub :wait-for-the-things
            :-> :boatnumber-to-uuid)

(reg-fx :rent/write
        (fn [[data new-boat-map]]
          (let [data (upgrade-boat-numbers data new-boat-map)]
            (tap> [:rent/write data new-boat-map])
            (db.core/database-push
              {:path  ["activity-22"]
               :value data}))))

(comment
  (do
    (let [d (prepare-data {:uid       "uid"
                           :timestamp (str (t/now))
                           :state     {:adults 1
                                       :list   [{:number "yyyyyyyyy"}
                                                {:number "xxxxxxxxxxxxx"}
                                                {:new true :number "123"}
                                                {:new true :number "124"}]}})]
      (rf/dispatch [:create-boats (-> d :list keys)])
      (when-let [x (rf/subscribe [:wait-for-the-things])]
        (tap> {:boatnumber-to-uuid @x})
        (rf/dispatch [:rent/store d @x])))))

(comment
  (do
    (let [new-data {"1" "zzz" "2" "xxx"}
          data {:list {"1" nil "2" nil "3" nil}}]
      (tap> (into {} (map (fn [[k v]] [(or (get new-data k) k) v])
                          (:list data)))))))