(ns eykt.spa
  (:require [re-frame.core :as rf]
            [kee-frame.router]
            [re-statecharts.core]
            [cljs.pprint :refer [pprint]]
            [eykt.data :as data :refer [screen-breakpoints start-db routes]]
            [kee-frame.core :as k]))

(defn header []
  [:div
   [:div.text-red-500.text-2xl "EYKT-22"]
   [:div.flex.gap-4
    [:a {:href (k/path-for [:r.common])} "Forsiden"]
    [:a {:href (k/path-for [:r.back])} "Baksiden"]
    [:a {:href (k/path-for [:r.init])} "Restart"]]])

(def route-table
  {:r.init (fn [_]
             [:div "INIT"])
   :r.common (fn [_]
               [:div.space-y-4.p-4
                [header]
                [:pre.text-base.text-yellow-500.bg-black.overflow-auto.-mx-4.p-2
                 (with-out-str (pprint @re-frame.db/app-db))]])
   :r.back   (fn [_]
               [:div.space-y-4.p-4
                [:div.text-red-500.text-2xl "BAKSIDEN"]
                [:a {:href (k/path-for [:r.common])} "Forsiden"]
                [:pre.text-base.text-yellow-500.bg-black.overflow-auto.-mx-4.p-2
                 (with-out-str (pprint @re-frame.db/app-db))]])})

(defn dispatch-main
  []
  (let [route (rf/subscribe [:kee-frame/route])
        route-name (-> @route
                       :data
                       :name)
        web-content (when-let [page (get route-table route-name)]
                      (kee-frame.router/make-route-component page @route))]
    [:div web-content]))

(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode @(rf/subscribe [::data/user-screenmode])
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if (= :dark user-screenmode) "dark" ""))
    (.setAttribute body
                   "class"
                   "font-sans inter bg-gray-100 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])