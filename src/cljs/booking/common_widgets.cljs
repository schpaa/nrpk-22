(ns booking.common-widgets
  (:require [schpaa.style.ornament :as sc]
            [re-frame.core :as rf]))

(defn vertical-button [{:keys [centered? tall-height special icon icon-fn class style on-click page-name badge disabled]
                        :or   {style {}}}]
  (let [current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))]
    [:div
     {:style (if centered?                                  ;'vertically
               {:pointer-events :none
                :inset          0
                :display        :grid
                :place-content  :center
                :position       :absolute}
               {:display         :flex
                :align-items     :start
                :justify-content :center
                :height          (if tall-height "var(--size-10)" "var(--size-9)")})}
     [:div.w-full.h-full.flex.flex-col.items-center.justify-around ;.-debug2
      {:style    {:pointer-events :auto
                  :height         "var(--size-9)"}
       :on-click #(on-click current-page)}

      (when badge
        (when-some [b (badge)]
          [sc/top-left-badge b]))

      [sc/toolbar-button
       {:disabled disabled
        :style    style
        :class    [(if active? (or (when class (class current-page)) :active))
                   (if special :special)]}
       [sc/icon-large
        (if icon-fn
          (icon-fn current-page)
          icon)]]]]))