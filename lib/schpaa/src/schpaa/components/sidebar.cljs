(ns schpaa.components.sidebar
  (:require [schpaa.style :as s]
            [schpaa.components.widgets :as widgets]
            [schpaa.components.sidebar-mock :as sidebar.mock]
            [re-frame.core :as rf]
            [schpaa.icon :as icon]
            [schpaa.debug :as l]
            [schpaa.components.sidebar.toolbar :as sidebar.toolbar]))

(def tabs-data
  {:list      {:local-tab-data sidebar.mock/content-tab-data
               :content-fn     sidebar.mock/content-content-fn
               :below-fn       sidebar.mock/content-below-fn}
   :bar-chart {:local-tab-data sidebar.mock/barchart-panel-data
               :content-fn     (fn [_] [:div "content"])
               :below-fn       (fn [_] [:div "Below"])}
   :cloud     {:local-tab-data sidebar.mock/brygge-panel-data
               :content-fn     (fn [_] [:div "content"])
               :below-fn       (fn [_] [:div "Below"])}
   :brygge    {:local-tab-data sidebar.mock/brygge-panel-data
               :content-fn     (fn [{:keys [selected-tab color-map]}]
                                 (case selected-tab
                                   :a
                                   [:div.text-xl "Active -> " selected-tab
                                    (for [e (range 20)]
                                      [:div e])]
                                   [:div "? " selected-tab]))
               :below-fn       (fn [_] [:div "Below"])}})

(defn tab-contents [selected-tab tabs-data]
  [:div.overflow-y-auto
   {:style {:height "calc(100vh - (4rem))"}
    ;intent apply color-scheme of selected tab
    :class (get-in s/color-map [selected-tab :selected :bg])}
   (let [tab-id selected-tab
         color-map (get s/color-map selected-tab)
         local-tab-data (get-in tabs-data [selected-tab :local-tab-data])
         content-fn (get-in tabs-data [selected-tab :content-fn])
         below-fn (get-in tabs-data [selected-tab :below-fn])
         tab (rf/subscribe [::widgets/get-tab tab-id])]
     [:div.flex.flex-col.min-h-full
      ;[:div (str @tab)]
      [:div.flex.flex-col.flex-grow
       [:div.sticky.top-0
        [widgets/tab-machine
         {:tabs-id          tab-id
          :class            (get-in color-map [:selected :bg])
          :selected-classes (get-in color-map [:selected :tab])
          :normal-classes   (get-in color-map [:normal :tab])
          :current-tab      @tab}
         local-tab-data]]
       [:div.p-4.overflow-y-auto.rounded
        {:class (get-in color-map [:selected :tab])}
        ;intent Draw indiviual content
        (when content-fn
          (content-fn {:selected-tab @tab
                       :color-map    color-map}))]]
      ;intent Draw common content below
      (when below-fn
        [:div.p-2 (below-fn {:selected-tab @tab
                             :color-map    color-map})])])])

(defn sidebar
  ""
  [{:keys [get-menuopen-fn get-selected-tab tab-content-fn] :as accessors}]
  (fn []
    [:div.fixed
     {:class (concat
               ;[:right-0x :bottom-0 :top-0 :xs:w-80 :w-full :left-0]
               [:inset-0]
               [:origin-top-right]
               [:z-200]
               [:duration-200 :opacity-100]
               [:transform-gpu]
               (if (get-menuopen-fn) ["translate-x-0"]
                                     ["translate-x-full"]))}
     [:div.mob:m-0.overflow-hidden
      {:class (concat s/sidebar-color
                      [:mob:rounded-none]
                      #_(when (get-menuopen-fn)
                          [:drop-shadow-2xl
                           :mob:drop-shadow-none
                           :mob:shadow-none
                           :shadow-2xl
                           "shadow-black/90"]))}
      [sidebar.toolbar/toolbar (conj {:color-map s/color-map} accessors)]
      [tab-content-fn (get-selected-tab) tabs-data]]]))

(defn render [{:keys [] :as accessors}]
  [sidebar
   (conj accessors
         {:uid              (fn [] 123)                     ;(rf/subscribe [::db/root-auth :uid])
          :set-selected-tab (fn [e] (rf/dispatch [::widgets/set-tab e]))
          :tab-content-fn   tab-contents
          :get-selected-tab (fn [] @(rf/subscribe [::widgets/get-tab]))
          :current-tab      (fn [] 0)})])