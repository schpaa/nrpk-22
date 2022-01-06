(ns eykt.spa
  (:require [cljs.reader :refer [read-string]]
            [re-frame.core :as rf]))

(def app-routes
  [["/" {:name :r.common :header "Forsiden"}]])

(defn- initialize
  [db ls-key]
  (conj db
        (into (sorted-map)
              (some->> (.getItem js/localStorage ls-key)
                       (read-string)))))

(def screen-breakpoints
  {:breakpoints [:mobile 640 :tablet 992 :small-monitor 1200 :large-monitor],
   :debounce-ms 166})

(def start-db
  {:startup true,
   :app/show-help {1 false, 2 false, 5 false, 6 false, 9 true}})

(def route-table
  {:r.common               [:div "EYKT home"]})

(defn dispatch-main
  []
  (let [route (rf/subscribe [:kee-frame/route])
        route-name (-> @route
                       :data
                       :name)
        web-content (when-let [page (get route-table route-name)]
                      (kee-frame.router/make-route-component page @route))]
    #_[writing-mode? (rf/subscribe [:writing-mode?])
       mobile? (rf/subscribe [:breaking-point.core/mobile?])
       menu-open? (rf/subscribe [:menu-open?])
       route (rf/subscribe [:kee-frame/route])
       route-name (-> @route
                      :data
                      :name)
       web-content (when-let [page (get torv.routes/route-table route-name)]
                     (kee-frame.router/make-route-component page @route))
       editor (torv.screen/my-editor (r/atom nil))]
    #_(forced-scroll-lock (and @mobile? @menu-open?))
    [:div web-content]))

(defn app-wrapper
  "takes care of light/dark-mode and loading-states"
  [content]
  (let [user-screenmode :dark #_@(rf/subscribe [:user-screenmode])
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