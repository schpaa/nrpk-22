(ns schpaa.darkmode
  (:require [goog.debug]
            [goog.events :as gevents]
            [re-frame.core :as rf]))

(defonce listeners* (atom {}))

(defn- stop-listener!
  [event-type id k]
  (when-let [key (get-in @listeners* [event-type k])]
    (gevents/unlistenByKey key)
    (swap! listeners* update event-type #(dissoc % k))))

(defn- setup-listener!
  [event-type callback source k]
  {:pre [(some? callback) (fn? callback)]}
  (let [key (gevents/listen source event-type (if (fn? callback)
                                                #(callback source)
                                                #(rf/dispatch [callback source])))]
    (when source
      (swap! listeners* assoc-in [event-type k] key))))

(defn- clear-all-listeners! [handler]
  (stop-listener! gevents/EventType.CHANGE handler "screenmode"))

;region events

(rf/reg-event-db
  :os-screenmode-changed (fn [db [_ arg]]
                           (tap> :os-screenmode-changed)
                           (-> db
                               (assoc :system-screenmode-setting arg)
                               (assoc-in [:settings :pref-screenmode] arg))))

(rf/reg-fx
  :listen-to-screenmode-change
  (fn [{:keys [dispatch source k]}]
    (setup-listener! gevents/EventType.CHANGE dispatch source k)))

;endregion

(rf/reg-sub :app/user-screenmode
            (fn [db]
              (get-in db [:settings :pref-screenmode] :auto)))

(rf/reg-event-fx :app/setup-handlers
  (fn [{db :db} _]
    (let [darkmode-handler (. js/window matchMedia "(prefers-color-scheme: dark)")]
      (clear-all-listeners! darkmode-handler)
      {:db (-> db (assoc :system-screenmode-setting
                         (if (.-matches darkmode-handler) :dark :light)))
       :fx [[:listen-to-screenmode-change
             {:source   darkmode-handler
              :k        "screenmode"
              :dispatch #(rf/dispatch [:os-screenmode-changed (if (.-matches %) :dark :light)])}]]})))
