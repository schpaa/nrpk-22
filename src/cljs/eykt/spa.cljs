(ns eykt.spa
  (:require [re-frame.core :as rf]
            [kee-frame.router]
            [cljs.pprint :refer [pprint]]
            [eykt.data :as data :refer [screen-breakpoints start-db app-routes]]))

(def route-table
  {:r.common (fn [_]
               [:div
                [:div.text-red-500.text-2xl "EYKT"]
                [:pre.text-base.text-yellow-500.bg-black (with-out-str (pprint @re-frame.db/app-db))]])})

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
    ;(.setAttribute html "style" "height: 100%")
    ;(.setAttribute body "style" "height: 100%")
    ;{:class (get-in s/color-map [:main :content :bg])}
    (.setAttribute body
                   "class"
                   "font-sans inter bg-gray-100 dark:bg-gray-800 min-h-screen")
    content))

(def root-component
  [app-wrapper [dispatch-main]])