(ns nrpk.spa
  (:require [goog.events :as gevents]
            [lambdaisland.ornament :as o]
            [re-frame.core :as rf]
            [kee-frame.router :refer [make-route-component]]))

(defonce _ (let [source (. js/window matchMedia "(prefers-color-scheme: dark)")
                 type gevents/EventType.CHANGE
                 dispatch #(do
                             ;(tap> {:screen-mode (-> % .-target .-matches)})
                             (schpaa.state/change :app/dark-mode (-> % .-target .-matches)))]
             (gevents/listen source type dispatch)))

(o/defstyled sample-wrapper :div
  #_#_[:at-media {:min-width "512px"}
       {"--size-9" "3.4re m"
          [:at-media {:max-width "511px"}
           {"--font-size-1" "0.75rem"
            "--font-size-2" "1.0rem"
            "--font-size-3" "1.1rem"
            "--font-size-4" "1.25rem"
            "--font-size-5" "1.5rem"

            "--size-1"      ".125rem"
            "--size-2"      ".25rem"
            "--size-3"      ".5rem"
            "--size-4"      "1rem"
            "--size-5"      "1.25rem"
            "--size-6"      "1.5rem"
            "--size-7"      "1.75rem"
            "--size-8"      "2rem"
            "--size-9"      "3rem"}]}])

(defn app-wrapper-clean
  [route-table]
  (let [route-name (rf/subscribe [:app/route-name])
        route @(rf/subscribe [:kee-frame/route])
        user-screenmode (schpaa.state/listen :app/dark-mode)
        html (aget (.getElementsByTagName js/document "html") 0)
        body (aget (.getElementsByTagName js/document "body") 0)]
    (.setAttribute html "class" (if @user-screenmode "dark" "light"))
    (.setAttribute body "class" "font-sans")
    (.setAttribute body "style" "background-color:var(--toolbar)")
    (when-let [page (get route-table @route-name)]
      [sample-wrapper (make-route-component page route)])))



