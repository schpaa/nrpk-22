(ns example.devcards
  (:require-macros [devcards.core :as dc :refer [defcard-rg]])
  (:require [devcards.core :as dc :include-macros true]
            ["highlight.js" :as hljs]
            [reagent.core]
            [schpaa.modal.readymade :refer []]
            [schpaa.modal :as modal :refer [form]]
            [booking.bookinglist]
            [schpaa.state]
            [tick.core :as t]
            [booking.views.picker]
            [booking.views]
            [fork.re-frame :as fork]
            [schpaa.debug :as l]           
            [schpaa.button :as bu]
            [example.buttons]))

(js/goog.exportSymbol "hljs" hljs)
(js/goog.exportSymbol "DevcardsSyntaxHighlighter" hljs)

(defn ^:export init! []
  (dc/start-devcard-ui!))

(defn reload! []
  (init!))



;(defcard-rg three
;  [:div.w-96
;   [form {:header      ":dialog-type :message title"
;          :dialog-type :message
;          :form-fn     (fn [] [:div
;                               [:div.p-4 "Contents"]
;                               [bu/just-buttons
;                                [["Ok" :button-cta (fn [])]]]])
;          :footer      "footer"}]])
;
;(defcard-rg three
;  [:div.w-96
;   [form {:header      [:h2.p-4 "header"]
;          :dialog-type :form
;          :form-fn     (bu/just-buttons
;                         [["Avbryt" :button (fn [])]
;                          ["Ok" :button-danger (fn [])]])
;          :footer      [:div.p-4 "footer"]}]])
;
;(defcard-rg three
;  [:div.w-96
;   [form {:header      [:h2.p-4 "header"]
;          :dialog-type :error
;          :form-fn     (bu/just-buttons
;                         [["Avbryt" :button (fn [])]
;                          ["Ok" :button-danger (fn [])]])
;          :footer      [:div.p-4 "footer"]}]])
;
;;(defcard-rg four
;;  (let [my-own? true]
;;    [:div [booking.bookinglist/booking-list-item
;;           {:fetch-boatdata-for (fn [id] (get {:a1 {:number "A"}
;;                                               :a2 {:number "B"}} id {:number id})) ;(get (into {} boat-db) id)
;;            :today              (t/new-date 2022 1 21)
;;            :insert-after       (fn [id]
;;                                  [:div.flex
;;                                   (when my-own? (hov/remove-booking-details-button id []))
;;                                   (hov/open-booking-details-button id)])
;;            :details?           true}
;;           {:start       (t/>> (t/now) (t/new-duration 1 :hours))
;;            :end         (t/>> (t/now) (t/new-duration 114 :hours))
;;            :description "sample"
;;            :selected    ["a1" "a2"]}]]))
;
;(defcard-rg list-line
;  [:div
;   [booking.views.picker/list-line
;    {:selected?         true
;     :fetch-bookingdata (fn [] [{:id          :xyz
;                                 :description "test"
;                                 :selected    [:-Me9qQ8av-rpbV1utrgS]
;                                 :start       (str (t/at (t/new-date 2022 1 26) (t/new-time 10 0)) #_(t/date-time))
;                                 :end         (str (t/at (t/new-date 2022 1 26) (t/new-time 13 0)) #_(t/date-time)) #_(str (t/>> (t/date-time) (t/new-duration 1 :hours)))}])
;     :appearance        #{:extra :timeline}
;     :offset            0
;     :data              {:description   "Senkekjøl og knekkspant. For personer 65-100 kg.",
;                         :boat-type     "-Me9qQ8av-rpbV1utrgS",
;                         :slot          "B 1",
;                         :stability     "1",
;                         :number        "484",
;                         :aquired-year  "2017",
;                         :width         "56,5 cm",
;                         :type          "",
;                         :aquired-price "9990",
;                         :last-update   "20210709T124742",
;                         :weight        "28 kg",
;                         :status        "0",
;                         :id            :-Me9qQ8av-rpbV1utrgS
;                         :kind          "havkayak",
;                         :comment       "",
;                         :material      "0",
;                         :length        "518 cm",
;                         :location      "1",
;                         :navn          "Baffin P2"}
;
;     :time-slot         (tick.alpha.interval/bounds (t/at (t/new-date 2022 1 26) (t/new-time 10 0))
;                                                    (t/at (t/new-date 2022 1 27) (t/new-time 12 0)))}]
;   [booking.views.picker/list-line
;    {:selected?         false
;     :fetch-bookingdata (fn [] [{:id          :xyz
;                                 :description "test"
;                                 :selected    [:-Me9qQ8av-rpbV1utrgS]
;                                 :start       (str (t/at (t/new-date 2022 1 26) (t/new-time 10 0)) #_(t/date-time))
;                                 :end         (str (t/at (t/new-date 2022 1 26) (t/new-time 13 0)) #_(t/date-time)) #_(str (t/>> (t/date-time) (t/new-duration 1 :hours)))}])
;     :insert-after      (fn [id] (hov/open-details id))
;     :appearance        #{:extrax :xtimeline :tall}
;     :offset            0
;     :data              {:description   "Senkekjøl og knekkspant. For personer 65-100 kg.",
;                         :boat-type     "-Me9qQ8av-rpbV1utrgS",
;                         :slot          "B 1",
;                         :stability     "1",
;                         :number        "484",
;                         :aquired-year  "2017",
;                         :width         "56,5 cm",
;                         :type          "",
;                         :aquired-price "9990",
;                         :last-update   "20210709T124742",
;                         :weight        "28 kg",
;                         :status        "0",
;                         :id            :-Me9qQ8av-rpbV1utrgS
;                         :kind          "havkayak",
;                         :comment       "",
;                         :material      "0",
;                         :length        "518 cm",
;                         :location      "1",
;                         :navn          "Baffin P2"}
;
;     :time-slot         (tick.alpha.interval/bounds (t/at (t/new-date 2022 1 26) (t/new-time 10 0))
;                                                    (t/at (t/new-date 2022 1 27) (t/new-time 12 0)))}]])
;
;(defcard-rg time-navigator
;  [:div
;   [fork/form {:initial-touched   {} #_{:start-date  (str (t/date "2022-01-27"))
;                                        :start-time  (str (t/time "08:00" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours)))
;                                        :end-time    (str (t/time "08:30" #_(t/truncate (t/>> (t/time) (t/new-duration 1 :hours)) :hours)))
;                                        :end-date    (str (t/date "2022-01-28"))
;                                        ;:sleepover true
;                                        :description ""}
;               :validation        booking.views/booking-validation
;               ;:state             my-state
;               :prevent-default?  true
;               :clean-on-unmount? true
;               :keywordize-keys   true
;               :on-submit         #(tap> {:on-submit2 %})}
;    (fn [{:keys [state form-id handle-submit values errors] :as props}]
;      [:form.space-y-1
;       {:id        form-id
;        :on-submit handle-submit}
;       [booking.views/time-input props false]
;       [l/ppre (-> @state :values)]
;       [l/ppre errors]
;       #_[l/ppre (t/days (t/duration (tick.alpha.interval/new-interval (t/at (values :start-date)
;                                                                             (values :start-time))
;                                                                       (t/at (values :end-date)
;                                                                             (values :end-time)))))]])]])