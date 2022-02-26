(ns nrpk.core
  (:require [re-frame.core :as rf]))

(def screen-breakpoints
  {:breakpoints [:mobile 768 :tablet 992 :small-monitor 1200 :large-monitor],
   :debounce-ms 166})

;region events and subs

(rf/reg-sub :app/route-name
            :<- [:kee-frame/route]
            (fn [route _]
              (-> route :data :name)))

(rf/reg-sub :app/menu-open? :-> :menu-open?)

(rf/reg-sub :app/current-page
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :name)))

(rf/reg-sub :app/current-page-title
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :header)))

(rf/reg-sub :app/current-page-subtitle
            :<- [:kee-frame/route]
            (fn [r _]
              (some-> r :data :subheader)))

(rf/reg-event-db :toggle-menu-open
                 (fn [db]
                   (update db :menu-open? (fnil not false))))

(rf/reg-event-db :app/open-menu-at
                 (fn [db [_ tab]]
                   (assoc db :menu-open? true
                             :tab tab)))

(rf/reg-event-fx :app/navigate-to
                 [rf/trim-v]
                 (fn [_ [page]]
                   {:fx [[:navigate-to page]]}))

;endregion

(rf/reg-sub :app/menu-direction (fn [db]
                                  (get db :menu-direction true)))

;region tab-memory for front and back

(rf/reg-sub :app/previous
            (fn [db [_ context default]]
              {:pre [(some #{context} [:active-front :active-back])]}
              [(get db context default)]))

(rf/reg-event-db :app/register-entry
                 (fn [db [_ context page]]
                   {:pre [(some #{context} [:active-front :active-back])]}
                   (assoc db context page)))

;endregion