(ns booking.qrcode
  (:require ["react-qr-code" :default QRCode]
            [lambdaisland.ornament :as o]
            [schpaa.style.ornament :as sc]
            [schpaa.style.button2 :as scb2]
            [schpaa.style.dialog]
            [schpaa.debug :as l]
            [schpaa.style.hoc.buttons :as hoc.buttons]))

(o/defstyled qr-code :div
  [:a :w-full :px-4 {:overflow      :hidden
                     :text-overflow :ellipsis
                     :white-space   :nowrap}])


(defn show [link]
  (schpaa.style.dialog/open-dialog-any
    {:form (fn form-thing [{:keys [on-close]}]
             (when-let [page-path (some-> link :path)]
               (let [page-title (some-> link :data :header)
                     size 192
                     url (str (.-protocol js/window.location)
                              "//"
                              (.-host js/window.location)
                              page-path)]
                 [sc/dialog-dropdown {:style {:padding "1rem"}}
                  [:div
                   [sc/col {:class [:space-y-8]}
                    [sc/col-space-4
                     ;[l/pre page-title]
                     [sc/title-p (if (vector? page-title) (last page-title) page-title)]
                     [sc/row-center
                      [booking.qrcode/qr-code [:> QRCode {:title "untitled"
                                                          :size  size
                                                          :level "Q"
                                                          :value url}]]]]
                    [sc/link {:href   url
                              :target "_blank"
                              :class  [:truncate]
                              :style  {:user-select :all
                                       :white-space :nowrap}} url]
                    [sc/row-ec
                     [hoc.buttons/regular {:on-click #(on-close)} "Lukk"]]]]])))}))