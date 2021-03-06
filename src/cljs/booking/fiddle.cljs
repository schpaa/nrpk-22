(ns booking.fiddle
  (:require [lambdaisland.ornament :refer [defstyled]]
            [schpaa.style.ornament :as sc]
            [re-frame.core :as rf]
            [booking.styles :as b]
            [schpaa.style.hoc.buttons :as button]
            [booking.ico :as ico]))

(defstyled debug :div
  {:outline "1px solid yellow"})

(defstyled outer-button :div
  [:& :flex-center :relative])

(defstyled round :div
  {:border-radius "var(--radius-round)"})

(defstyled button :button
  [:& :-mt-4 :flex-center :p-2 :outline-none :focus:outline-none :self-center
   {:aspect-ratio "1/1"
    ;:max-width    "4rem"
    :color        "var(--text3)"}
   [#{:&.normal :&.selected}
    {:opacity 1}]
   [:&.normal
    {:color             "var(--text2)"}]
   [:&.selected round
    {:color            "var(--text1)"
     ;:box-shadow       "var(--shadow-2)"
     :background-color "var(--floating)"}]])


(defstyled icontext :p
  [:& :absolute :top-1 :text-center
   [#{:&.normal :&.selected}
    {:opacity 1}]
   {:font-size      "var(--font-size-1)"
    :font-weight    "var(--font-weight-4)"
    :letter-spacing "var(--font-letterspacing-1)"
    :color          "var(--text3)"}
   [:&.normal {:color "var(--text1)"}]
   [:&.selected {:color "var(--text1)"}]])

(defn button-fn [idx d]
  (let [{:keys [page-name]} d
        current-page (some-> (rf/subscribe [:kee-frame/route]) deref :data :name)
        disable? (:disabled d)
        active? (if (fn? page-name)
                  (page-name current-page)
                  (= current-page page-name))
        class (if active? :selected (if (not disable?) :normal))]
    [outer-button
     {:on-click #((:on-click d))} 
     [button
      {:class [:relative
               class
               :flex :items-end]}
      [sc/col-c-space-2
       [sc/icon-huge
        {:class (:class d)
         :style {}}
        (let [f (or (:icon d) (:icon-fn d))]
          (if (fn? f) (f page-name) f))]]]
     #_[icontext {:class [class]} (:short-caption d)]]))

(defstyled toolbar :div
  {:display :none}
  [:at-media {:max-width "511px"}
   [:& :grid :gap-0 :w-full :px-4 :pb-4
    {:height                "6rem"
     :width                 :100vw
     :bottom                0
     :background-color      "var(--toolbar)"
     :grid-template-rows    "auto 0rem "
     :grid-template-columns "repeat(5,1fr)"}
    [:&.admin
     {:grid-template-columns "repeat(7,1fr)"}]]])

(defn render [d]
  [sc/surface-ab {:class [:p-1]}
   [:p "test"]
   ;[button/icon-and-caption {:class [:small :cta]} ico/check "caption"]
   ;[button/just-icon {:class [:small :message]} ico/check]
   [b/text "test"]
   [:div {:style {:background "var(--red-4)"}} "hei"]
   #_[toolbar (into [:<>]
                    (map-indexed button-fn d))]])
