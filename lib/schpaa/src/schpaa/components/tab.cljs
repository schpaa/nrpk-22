(ns schpaa.components.tab
  (:require [re-frame.core :as rf]))

(def select-bar-top-config
  {:class     [:flex
               ;:border-2
               :border-sky-900
               :last:rounded-br
               :first:rounded-bl]
   :on-class  [:btn-on :bg-sky-900 :font-bold]
   :off-class [:btn-off :font-normal]})

(def select-bar-config
  {:class     [:flex
               :border-2
               :border-sky-900
               :last:rounded-r
               :first:rounded-l]
   :on-class  [:btn-on :h-10 :w-12 :bg-sky-900 :font-bold]
   :off-class [:btn-off :h-10 :w-12 :font-normal]})

(def select-bar-bottom-config
  {:class     [:flex
               :border-2
               :border-black :dark:border-gray-400
               :last:rounded-r
               :first:rounded-l]
   :on-class  [:btn-on :h-10 :w-8 :bg-black-900 :dark:bg-gray-400 :dark:text-black :font-bold]
   :off-class [:btn-off :h-10 :w-8 :font-normal]})

(defn tab
  [{class      :class
    on-class   :on-class
    off-class  :off-class
    bottom?    :bottom?
    locked?    :locked?
    item-class :item
    selected   :selected
    select     :select} & m]
  (let [on-class (or on-class [:btn-tab])
        off-class (or off-class [:btn-tab-inactive])
        class (or class [:flex :sticky :top-16 :h-16])
        o (or select #(rf/dispatch [:app/navigate-to [%]]))]
    [:div {:class class}
     (for [[page title action] m]
       [:button.flex-grow.shadow-none
        (conj {:class (concat
                        item-class
                        (conj
                          (if bottom?
                            (if (= selected page) [:btn-tab-b] [:btn-tab-inactive-b])
                            (if (= selected page) on-class off-class))))}
              (when-not locked?
                {:on-click #(do (o page)
                                (when action
                                  (action)))}))
        title])]))