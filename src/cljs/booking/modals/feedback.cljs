(ns booking.modals.feedback
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [db.core :as db]
            [schpaa.style.hoc.buttons :as hoc.buttons]
            [schpaa.style.ornament :as sc]
            [schpaa.style.input :as sci]
            [booking.ico :as ico]
            [tick.core :as t]))

(def max-comment-length 160)

(defn feedback-window [{:keys [reply? title on-close on-save caption comment-length] :as ctx}]
  (let [uid (rf/subscribe [:lab/uid])
        max-comment-length (or comment-length max-comment-length)]
    [:div
     {:style {:background-color "var(--toolbar)"
              ;:background-color "var(--content)"
              :display          :grid
              :overflow-y       :auto
              :place-content    :center}}
     [:div.p-4
      {:style {:width      "24rem"
               :min-width  "24rem"
               :max-height "90vh"}}

      (r/with-let [content (r/atom {})]

        (let [f (fn [tag [on-color off-color] [on-icon off-icon]]
                  (let [state (tag @content)]
                    [sc/icon {:on-click #(swap! content update-in [tag] (fnil not false))
                              :style    {:cursor :pointer
                                         :color  (if state on-color off-color)}}
                     (if state on-icon off-icon)]))]
          [sc/col-space-4
           (when title [sc/dialog-title title])
           (if reply? [:div "Svar til"])
           [:div.flex.justify-between {:class [:w-full]}
            [sc/col-space-4 #_{:style {:width "100%"}}
             (when caption
               [sc/text1 {:style {:line-height "var(--font-lineheight-4)"}} caption])
             (when-not @uid
               [sci/input {:type          :email
                           :handle-change #(swap! content assoc :email (-> % .-target .-value))
                           :values        {:email (:email @content)}}
                :email {:class [:min-w-full :relative]} "E-post (hvis du vil ha svar)" :email])
             [sc/col-space-4
              [:div.relative
               [sci/textarea {:rows          5
                              :values        {:tilbakemelding (:text @content)}
                              :handle-change #(swap! content assoc :text (-> % .-target .-value))}
                :text {:class [:min-w-full :relative]} "Skriv her" :tilbakemelding]
               [:div.absolute.-top-1.right-1
                [sc/label (if (zero? (count (:text @content)))
                            ;todo
                            (str "Helst under " 165 #_(Math/round (/ max-comment-length 6)) " ord")
                            (if (< max-comment-length (count (:text @content)))
                              "For langt"
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
            [hoc.buttons/regular {:class    [:small]
                                  :on-click #(do
                                               (on-close)
                                               (reset! content {}))} "Avbryt"]
            [hoc.buttons/cta {:class    [:small]
                              :disabled (and (empty? (:text @content))
                                             (every? #(false? (get @content % false)) [:heart :thumbsup :thumbsdown :smiley :frowny]))
                              :on-click #(do
                                           (on-save @content)
                                           (reset! content {}))} "Send"]]]))]]))

(defn feedback-map [_ [{:keys [navn caption source comment-length]}]]
  (let [uid (:uid @(rf/subscribe [::db/user-auth]))]
    (letfn [(write-to-db [{:keys [carry]}]
              (db/database-push
                {:path  ["cache-tilbakemeldinger"]
                 :value (conj {:til-navn  navn
                               :timestamp (str (t/now))
                               :kilde     source
                               :uid       uid}
                              carry)})
              (db/firestore-add
                {:path  ["tilbakemeldinger" uid "feedback"]
                 :value (conj {:til-navn navn
                               :kilde    source
                               :uid      uid}
                              carry)}))]
      {:fx [[:dispatch [:lab/modaldialog-visible
                        true
                        {:navn           navn
                         :comment-length comment-length
                         :source         source
                         :caption        (or caption
                                             "Har du sett noe som bør korrigeres, eller er det noe du ikke har fått svar på?")
                         :content-fn     #(feedback-window %)
                         :action         write-to-db}]]]})))

(rf/reg-event-fx :app/give-feedback [rf/trim-v] feedback-map)

(defn send-message [_ [uid-receiver]]
  ;(js/alert uid)
  (letfn [(write-to-db [reci-uid {:keys [carry]}]
            (let [active-uid (:uid @(rf/subscribe [:db.core/user-auth]))
                  post (conj {:to  reci-uid
                              :uid active-uid}
                             carry)]
              (db.core/firestore-add
                {:path  ["beskjeder" active-uid "sent" reci-uid "posts"]
                 :value post})
              (db.core/firestore-add
                {:path  ["beskjeder" reci-uid "inbox"]
                 :value post})))]
    {:fx [[:dispatch [:lab/modaldialog-visible
                      true
                      {:comment-length 1000
                       ;:title          (str "Til " (user.database/lookup-username uid))
                       ;:caption        (str "Til " (user.database/lookup-username uid))
                       :content-fn     #(feedback-window %)
                       :action         #(write-to-db uid-receiver %)}]]]}))

(defn send-reply [_ [loggedin-uid uid msg-id]]
  ;(js/alert uid)
  (letfn [(write-to-db [reci-uid {:keys [carry]}]
            (let [active-uid (:uid @(rf/subscribe [:db.core/user-auth]))
                  post (conj {:to  reci-uid
                              :uid active-uid}
                             carry)]
              (db.core/firestore-add
                {:path  ["beskjeder" active-uid "sent" reci-uid "posts"]
                 :value post})
              (db.core/firestore-add
                {:path  ["beskjeder" reci-uid "inbox"]
                 :value post})))]
    {:fx [[:dispatch [:lab/modaldialog-visible
                      true
                      {:comment-length 1000
                       :title          (str "Svar til " (user.database/lookup-username uid))
                       ;:caption        (str "Til " (user.database/lookup-username uid))
                       :content-fn     #(feedback-window %)
                       :action         #(write-to-db uid %)}]]]}))

(rf/reg-event-fx :app/open-send-message [rf/trim-v] send-message)
(rf/reg-event-fx :app/open-send-reply [rf/trim-v] send-reply)