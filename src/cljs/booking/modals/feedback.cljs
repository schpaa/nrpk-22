(ns booking.modals.feedback
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [db.core :as db]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.ornament :as sc]
            [schpaa.style.input :as sci]
            [booking.ico :as ico]))

(def max-comment-length 160)

(defn feedback-window [{:keys [title on-close on-save persona caption navn comment-length] :as ctx}]
  (let [max-comment-length (or comment-length max-comment-length)]
    [sc/centered-dialog
     {:style {:background-color "var(--content)"
              :z-index          10
              ;:width            "100%"
              :max-height       "80vh"
              :min-width        "18rem"
              :width            "24rem"}
      :class [:min-w-smx]}
     (r/with-let [content (r/atom {})]
       (let [f (fn [tag [on-color off-color] [on-icon off-icon]]
                 (let [state (tag @content)]
                   [sc/icon {:on-click #(swap! content update-in [tag] (fnil not false))
                             :style    {:cursor :pointer
                                        :color  (if state on-color off-color)}}
                    (if state on-icon off-icon)]))]
         [sc/col-space-8
          [sc/dialog-title (or title (str "Tilbakemelding" (when navn (str " til " navn))))]
          [:div.flex.justify-between {:class [:w-full]}
           [sc/col-space-4 #_{:style {:width "100%"}}
            [sc/text1 {:style {:line-height "var(--font-lineheight-4)"}} caption]
            [sc/col-space-4
             [:div.relative
              [sci/textarea {:rows          4
                             :values        {:tilbakemelding (:text @content)}
                             :placeholder   (str "Skriv her (max " max-comment-length " tegn)")
                             :handle-change #(swap! content assoc :text (-> % .-target .-value))}
               :text {:class [:min-w-full :relative]} "Skriv her" :tilbakemelding]
              [:div.absolute.-top-1.right-1
               [sc/label (if (zero? (count (:text @content)))
                           (str "Maks " max-comment-length " tegn")
                           (if (< max-comment-length (count (:text @content)))
                             "Prøv å forkorte litt"
                             (str (- max-comment-length (count (:text @content))) " tegn igjen")))]]]

             (let [off-color "var(--text2)"
                   on-color "var(--text1)"]
               [sc/row-sc-g4-w
                (f :heart ["var(--red-6)" off-color] [ico/fill-heart ico/heart])
                (f :thumbsup [on-color off-color] [ico/fill-thumbsup ico/thumbsup])
                (f :thumbsdown [on-color off-color] [ico/fill-thumbsdown ico/thumbsdown])
                (f :smiley ["var(--yellow-6)" off-color] [ico/fill-smileyface ico/smileyface])
                (f :frowny ["var(--orange-6)" off-color] [ico/fill-frownyface ico/frownyface])])]]]

          [sc/row-ec
           [hoc.buttons/regular {:on-click #(do
                                              (on-close)
                                              (reset! content {}))} "Avbryt"]
           [hoc.buttons/cta {:disabled (and (empty? (:text @content))
                                            (every? #(false? (get @content % false)) [:heart :thumbsup :thumbsdown :smiley :frowny]))
                             :on-click #(do
                                          (on-save @content)
                                          (reset! content {}))} "Send"]]]))]))

(defn feedback-map [_ [{:keys [navn caption source comment-length]}]]
  (letfn [(write-to-db [{:keys [carry]}]
            (db/firestore-add
              {:path  ["tilbakemeldinger"]
               :value (conj {:til-navn navn
                             :kilde    source
                             :uid      (:uid @(rf/subscribe [::db/user-auth]))}
                            carry)}))]
    {:fx [[:dispatch [:lab/modaldialog-visible
                      true
                      {:navn           navn
                       :comment-length comment-length
                       :source         source
                       :caption        (or caption
                                           "Tenker du at noe kan gjøres bedre? Har du sett noe som bør korrigeres, eller er det noe du ikke har fått svar på?")
                       :content-fn     #(feedback-window %)
                       :action         write-to-db}]]]}))

(defn send-message [_ [uid]]
  (letfn [(write-to-db [uid {:keys [carry]}]
            (db.core/firestore-add
              {:path  ["users" uid "inbox"]
               :value (conj {:til-navn "navn"
                             :kilde    "source"
                             :uid      (:uid @(rf/subscribe [:db.core/user-auth]))}
                            carry)}))]
    {:fx [[:dispatch [:lab/modaldialog-visible
                      true
                      {:comment-length 1000
                       :title          "Send en melding"
                       :caption        "caption"
                       :content-fn     #(feedback-window %)
                       :action         #(partial write-to-db uid)}]]]}))

(rf/reg-event-fx :app/give-feedback [rf/trim-v] feedback-map)

(rf/reg-event-fx :app/open-send-message [rf/trim-v] send-message)