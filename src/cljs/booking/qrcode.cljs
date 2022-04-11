(ns booking.qrcode
  (:require ["react-qr-code" :default QRCode]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.dialog]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

(o/defstyled qr-code :div
  ;:space-y-4 :flex :justify-center :flex-col :items-center
  ;:border-2 :border-gray-500 :p-4 :rounded-sm
  [:>a :w-full :px-4 {:overflow      :hidden
                      :text-overflow :ellipsis
                      :white-space   :nowrap}]
  ([active-item size]
   (let [addr active-item                                   ;(kee-frame.core/path-for [:r.topic {:id active-item}])
         path (str (.-protocol js/window.location) "//" (.-host js/window.location) addr)]
     [:<>
      [:> QRCode {:title "Whatever"
                  :size  size
                  :level "Q"
                  :value path}]

      ;[:div addr]
      ;[:div (.-host js/window.location)]
      ;[:div (.-protocol js/window.location)]
      #_[:a {:href path} path]])))

(defn show [link]
  (schpaa.style.dialog/open-dialog-any
    {:form (fn [{:keys [on-close]}]
             [sc/dropdown-dialog                            ;dialog
              [:div.p-4
               [sc/col {:class [:space-y-8]}
                [sc/col-space-4
                 (when-let [page-title (some-> link :data :header)]
                   [sc/title-p (if (seq? page-title) (last page-title) page-title)])
                 [sc/row-center [booking.qrcode/qr-code :r.forsiden 192]]]
                ;[l/ppre-x link]
                (when-some [page-path (some-> link :path)]
                  (let [;page-addr (kee-frame.core/path-for [page-name])
                        url (str (.-protocol js/window.location) "//" (.-host js/window.location) page-path)]
                    [sc/text1 {:class [:truncate]
                               :style {:user-select :all
                                       :white-space :nowrap}} url]))
                [sc/row-ec
                 [hoc.buttons/regular {:on-click on-close} "Lukk"]]]]])}))