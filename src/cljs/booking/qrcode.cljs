(ns booking.qrcode
  (:require ["react-qr-code" :default QRCode]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [schpaa.debug :as l]))

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
             [sc/dialog
              [:div.p-4
               [sc/col {:class [:space-y-8]}
                [sc/col-space-2
                 [sc/title-p "Tittel"]
                 [sc/row-center [booking.qrcode/qr-code :r.forsiden 256]]]
                [l/ppre-x link]
                [sc/row-ec
                 [scb2/normal-regular {:on-click on-close} "Lukk"]]]]])}))