(ns schpaa.icon)

(def data
  {

   :save                      [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2"
                                       :d              "M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4"}]]
   :camera

   [:g {:transform "translate(2 3)"}
    [:path {:vector-effect :non-scaling-stroke
            :d             "M20 16a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5c0-1.1.9-2 2-2h3l2-3h6l2 3h3a2 2 0 0 1 2 2v11z"}] [:circle {:cx "10" :cy "10" :r "4"}]]

   :trash                     [:g
                               [:polyline {:vector-effect :non-scaling-stroke
                                           :points        "3 6 5 6 21 6"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "10" :y1 "11" :x2 "10" :y2 "17"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "14" :y1 "11" :x2 "14" :y2 "17"}]]

   :upload                    [:g {:fill "none" :stroke "#000000" :stroke-width "2" :stroke-linecap "round" :stroke-linejoin "round"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M3 15v4c0 1.1.9 2 2 2h14a2 2 0 0 0 2-2v-4M17 8l-5-5-5 5M12 4.2v10.3"}]]

   :download                  [:g {
                                   :fill "none" :stroke "#000000" :stroke-width "2" :stroke-linecap "round" :stroke-linejoin "round"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M3 15v4c0 1.1.9 2 2 2h14a2 2 0 0 0 2-2v-4M17 9l-5 5-5-5M12 12.8V2.5"}]]

   :chevron-left-2            [:g [:path {:vector-effect :non-scaling-stroke
                                          :fill-rule     "evenodd" :d "M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" :clip-rule "evenodd"}]]
   :chevron-right-2           [:g [:path {:vector-effect :non-scaling-stroke
                                          :fill-rule     "evenodd" :d "M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" :clip-rule "evenodd"}]]

   :double-chevron-right-2    [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill-rule     "evenodd" :d "M10.293 15.707a1 1 0 010-1.414L14.586 10l-4.293-4.293a1 1 0 111.414-1.414l5 5a1 1 0 010 1.414l-5 5a1 1 0 01-1.414 0z" :clip-rule "evenodd"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill-rule     "evenodd" :d "M4.293 15.707a1 1 0 010-1.414L8.586 10 4.293 5.707a1 1 0 011.414-1.414l5 5a1 1 0 010 1.414l-5 5a1 1 0 01-1.414 0z" :clip-rule "evenodd"}]]

   :double-chevron-left-2     [:g [:path {:vector-effect :non-scaling-stroke
                                          :fill-rule     "evenodd" :d "M15.707 15.707a1 1 0 01-1.414 0l-5-5a1 1 0 010-1.414l5-5a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 010 1.414zm-6 0a1 1 0 01-1.414 0l-5-5a1 1 0 010-1.414l5-5a1 1 0 011.414 1.414L5.414 10l4.293 4.293a1 1 0 010 1.414z" :clip-rule "evenodd"}]]

   :share                     [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "18" :cy "5" :r "3"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "6" :cy "12" :r "3"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "18" :cy "19" :r "3"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "8.59" :y1 "13.51" :x2 "15.42" :y2 "17.49"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "15.41" :y1 "6.51" :x2 "8.59" :y2 "10.49"}]]
   :double-chevron-down       [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M7 13l5 5 5-5M7 6l5 5 5-5"}]]
   :folder                    [:g [:path {:vector-effect :non-scaling-stroke
                                          ;:fill :currentColor
                                          :d             "M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"}]]
   :back-to-start             [:g [:polygon {:points        "19 20 9 12 19 4 19 20"
                                             :vector-effect :non-scaling-stroke}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "5" :y1 "19" :x2 "5" :y2 "5"}]]
   :pause                     [:g
                               [:rect {:vector-effect :non-scaling-stroke
                                       :x             "6" :y "4" :width "4" :height "16"}]
                               [:rect {:vector-effect :non-scaling-stroke
                                       :x             "14" :y "4" :width "4" :height "16"}]]
   :plays                     [:g [:polygon {:vector-effect :non-scaling-stroke
                                             :points        "5 3 19 12 5 21 5 3"}]]
   :delete                    [:g
                               {:transform "scale(1.4)translate(-1,-1)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       ;:stroke-width  0.15
                                       :fill          "currentColor"
                                       :fill-rule     "evenodd" :d "M6.707 4.879A3 3 0 018.828 4H15a3 3 0 013 3v6a3 3 0 01-3 3H8.828a3 3 0 01-2.12-.879l-4.415-4.414a1 1 0 010-1.414l4.414-4.414zm4 2.414a1 1 0 00-1.414 1.414L10.586 10l-1.293 1.293a1 1 0 101.414 1.414L12 11.414l1.293 1.293a1 1 0 001.414-1.414L13.414 10l1.293-1.293a1 1 0 00-1.414-1.414L12 8.586l-1.293-1.293z" :clip-rule "evenodd"}]]
   :chat-bubble-filled        [:g
                               {:transform "scale(1.2)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :stroke-width  1
                                       :fill          "currentColor"
                                       :fill-rule     "evenodd" :d "M18 13V5a2 2 0 00-2-2H4a2 2 0 00-2 2v8a2 2 0 002 2h3l3 3 3-3h3a2 2 0 002-2zM5 7a1 1 0 011-1h8a1 1 0 110 2H6a1 1 0 01-1-1zm1 3a1 1 0 100 2h3a1 1 0 100-2H6z" :clip-rule "evenodd"}]]
   :arrow-up                  [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M12 19V6M5 12l7-7 7 7"}]]

   :arrow-down                [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M12 19V6M5 12l7+7 7 -7"}]]

   :crown                     [:svg.text-amber {:viewBox "0 0 250 250"
                                                :width   "100%"}
                               [:g
                                [:path {:stroke-color  "black"
                                        :fill          "currentColor"
                                        :vector-effect :non-scaling-stroke
                                        :d             "M204.831,239c0,4.142-3.358,7.5-7.5,7.5h-147c-4.142,0-7.5-3.358-7.5-7.5v-16c0-4.142,3.358-7.5,7.5-7.5h147  c4.142,0,7.5,3.358,7.5,7.5V239z M63.542,187.5h120.073c-1.457-11-5.733-22.047-12.758-31.106l-47.314-60.986l-47.289,60.986  C69.229,165.453,64.999,176.5,63.542,187.5z M198.176,183.386l30.053-39.259c17.727-22.859,17.602-56.993-0.398-79.851v-0.006  c-10-13.805-25.142-20.711-40.01-20.719c-14.824,0.052-29.549,6.964-40.22,20.724l-14.569,18.842l49.704,64.067  C190.755,157.524,196.079,170.005,198.176,183.386z M58.652,43.546c-14.88,0-29.821,6.912-40.821,20.723v0.006  c-8,11.372-12.672,25.535-12.76,39.712c0.016,14.194,4.392,28.368,13.193,39.717l30.701,39.501  c2.115-13.317,7.43-25.73,15.415-36.027l49.654-64.015l-14.741-18.9C88.582,50.452,73.533,43.546,58.652,43.546z M107.164,32.5  h8.667v28.812l7.413,9.56l7.587-9.783V32.5h9.333c4.143,0,7.5-3.357,7.5-7.5s-3.357-7.5-7.5-7.5h-9.333v-10  c0-4.143-3.357-7.5-7.5-7.5s-7.5,3.357-7.5,7.5v10h-8.667c-4.143,0-7.5,3.357-7.5,7.5S103.022,32.5,107.164,32.5z"}]]]

   :circle-cake-a             [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M12 2a10 10 0 1 0 10 10H12V2zM21.18 8.02c-1-2.3-2.85-4.17-5.16-5.18"}]]
   :graph-up                  [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M3 3v18h18"}] [:path {:d "M18.7 8l-5.1 5.2-2.8-2.7L7 14.3"}]]
   :restore-window            [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M4 14h6v6M3 21l6.1-6.1M20 10h-6V4M21 3l-6.1 6.1"}]]
   :expand-window             [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M15 3h6v6M14 10l6.1-6.1M9 21H3v-6M10 14l-6.1 6.1"}]]
   :html-code                 [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4"}]]
   :refresh                   [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M21.5 2v6h-6M2.5 22v-6h6M2 11.5a10 10 0 0 1 18.8-4.3M22 12.5a10 10 0 0 1-18.8 4.2"}]]
   :light-bulb                [:g [:path {:stroke-linecap  "round"
                                          :vector-effect   :non-scaling-stroke
                                          :stroke-linejoin "round"
                                          :d               "M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"}]]
   :history-cards             [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"}]]
   :photo                     [:g
                               {:transform "translate(2 3)"}
                               [:path {:d "M20 16a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5c0-1.1.9-2 2-2h3l2-3h6l2 3h3a2 2 0 0 1 2 2v11z" :vector-effect :non-scaling-stroke}]
                               [:circle {:cx "10" :cy "10" :r "4" :vector-effect :non-scaling-stroke}]]
   :expand                    [:g [:path {:d "M3.8 3.8l16.4 16.4M20.2 3.8L3.8 20.2M15 3h6v6M9 3H3v6M15 21h6v-6M9 21H3v-6" :vector-effect :non-scaling-stroke}]]
   :circle-check              [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10"}]
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round"
                                       ;m6 2a9 9 0 11-18 0 9 9 0 0118 0z
                                       :d              "M9 12l2 2 4-4"}]]
   :fingerprint               [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M12 11c0 3.517-1.009 6.799-2.753 9.571m-3.44-2.04l.054-.09A13.916 13.916 0 008 11a4 4 0 118 0c0 1.017-.07 2.019-.203 3m-2.118 6.844A21.88 21.88 0 0015.171 17m3.839 1.132c.645-2.266.99-4.659.99-7.132A8 8 0 008 4.07M3 15.364c.64-1.319 1-2.8 1-4.364 0-1.457.39-2.823 1.07-4"}]]

   :ticket-filled             [:g

                               [:path {:fill           "orange"

                                       :vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round"
                                       :d              "M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"}]]

   :ticket                    [:g
                               {:transform "scale(1.25) rotate(180 12 12) translate(2 2)"}
                               [
                                :path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"}]]
   :commands                  [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M18 3a3 3 0 0 0-3 3v12a3 3 0 0 0 3 3 3 3 0 0 0 3-3 3 3 0 0 0-3-3H6a3 3 0 0 0-3 3 3 3 0 0 0 3 3 3 3 0 0 0 3-3V6a3 3 0 0 0-3-3 3 3 0 0 0-3 3 3 3 0 0 0 3 3h12a3 3 0 0 0 3-3 3 3 0 0 0-3-3z"}]]
   :view-mode                 [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M3 3h18v18H3zM21 9H3M21 15H3M12 3v18"}]]
   :sort-up                   [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M3 4h13M3 8h9m-9 4h6m4 0l4-4m0 0l4 4m-4-4v12"}]]
   :beaker                    [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M19.428 15.428a2 2 0 00-1.022-.547l-2.387-.477a6 6 0 00-3.86.517l-.318.158a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.806.547M8 4h8l-1 1v5.172a2 2 0 00.586 1.414l5 5c1.26 1.26.367 3.414-1.415 3.414H4.828c-1.782 0-2.674-2.154-1.414-3.414l5-5A2 2 0 009 10.172V5L8 4z"}]]

   :sort-down                 [:g {:fill "none" :stroke "currentColor" :viewBox "0 0 24 24" :xmlns "http://www.w3.org/2000/svg"}
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M3 4h13M3 8h9m-9 4h9m5-4v12m0 0l-4-4m4 4l4-4"}]]
   :filter                    [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"}]]
   :play                      [:g {:stroke-width "1.5"} [:polygon {:points "5 3 19 12 5 21 5 3"}]]
   :badge-check               [:g
                               [:path {:stroke-linecap "round" :stroke-linejoin "round" :d "M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"}]]

   :bar-chart                 [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M8 13v-1m4 1v-3m4 3V8M8 21l4-4 4 4M3 4h18M4 4h16v12a1 1 0 01-1 1H5a1 1 0 01-1-1V4z"}]]
   :chevron-up                [:g [:path {:stroke-linecap  "round"
                                          :stroke-linejoin "round"
                                          :d               "M4 14l7-7 7 7"
                                          :vector-effect   :non-scaling-stroke}]]

   :triangle-up               [:g [:path {:stroke-linecap "round" :stroke-linejoin "round" :fill :currentColor :d "M5 15l7-7 7 7 z" :vector-effect :non-scaling-stroke}]]

   :triangle-down             [:g [:path {:stroke-linecap "round" :stroke-linejoin "round" :fill :currentColor :d "M19 9l-7 7-7-7 z" :vector-effect :non-scaling-stroke}]]

   :triangle-right            [:g {:transform "rotate(270 12 12)translate(1 0)"}
                               [:path {:stroke-linecap  "round"
                                       :stroke-linejoin "round"
                                       :fill            :currentColor
                                       :d               "M19 9l-7 7-7-7 z"
                                       :vector-effect   :non-scaling-stroke}]]
   :triangle-left             [:g {:transform "rotate(90 12 12)translate(-1 0)"}
                               [:path {:stroke-linecap  "round"
                                       :stroke-linejoin "round"
                                       :fill            :currentColor
                                       :d               "M19 9l-7 7-7-7 z"
                                       :vector-effect   :non-scaling-stroke}]]

   :chevron-down              [:g [:path {:vector-effect   :non-scaling-stroke
                                          :stroke-linecap  "round"
                                          :stroke-linejoin "round" :d "M18 9l-7 7-7-7"}]]
   :rotate-left               [:g {:transform "scale(0.85) translate(2, 2)"}
                               [:path {:d "M2.5 2v6h6M2.66 15.57a10 10 0 1 0 .57-8.38" :vector-effect :non-scaling-stroke}]]
   :rotate-right              [:g [:path {:d "M21.5 2v6h-6M21.34 15.57a10 10 0 1 1-.57-8.38" :vector-effect :non-scaling-stroke}]]
   :arrow-up-right            [:g
                               [:path {:vector-effect :non-scaling-stroke :d "M14 16l6-6-6-6"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M4 21v-7a4 4 0 0 1 4-4h11"}]]

   :alert                     [:g [:path {:vector-effect :non-scaling-stroke :d "M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "12" :y1 "9" :x2 "12" :y2 "13"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "12" :y1 "17" :x2 "12.01" :y2 "17"}]]
   :video                     [:g
                               [:rect {:vector-effect :non-scaling-stroke
                                       :x             "2" :y "2" :width "20" :height "20" :rx "2.18" :ry "2.18"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "7" :y1 "2" :x2 "7" :y2 "22"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "17" :y1 "2" :x2 "17" :y2 "22"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "2" :y1 "12" :x2 "22" :y2 "12"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "2" :y1 "7" :x2 "7" :y2 "7"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "2" :y1 "17" :x2 "7" :y2 "17"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "17" :y1 "17" :x2 "22" :y2 "17"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "17" :y1 "7" :x2 "22" :y2 "7"}]]

   :user-alt                  [:g [:path {:stroke-linecap "round" :stroke-linejoin "round" :d "M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"}]]
   :users-alt                 [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"}]]

   :eye-off                   [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21"}]]

   :index                     [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round"
                                          :d              "M17 14v6m-3-3h6M6 10h2a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v2a2 2 0 002 2zm10 0h2a2 2 0 002-2V6a2 2 0 00-2-2h-2a2 2 0 00-2 2v2a2 2 0 002 2zM6 20h2a2 2 0 002-2v-2a2 2 0 00-2-2H6a2 2 0 00-2 2v2a2 2 0 002 2z"}]]
   :hamburger-dense           [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M4 6h16M4 10h16M4 14h16M4 18h16"}]]
   :filled-thumbs-down-alt    [:g {:transform "scale(1.24) translate(-1 -1)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill          "currentColor"
                                       :stroke-width  "0.1"
                                       :d             "M18 9.5a1.5 1.5 0 11-3 0v-6a1.5 1.5 0 013 0v6zM14 9.667v-5.43a2 2 0 00-1.105-1.79l-.05-.025A4 4 0 0011.055 2H5.64a2 2 0 00-1.962 1.608l-1.2 6A2 2 0 004.44 12H8v4a2 2 0 002 2 1 1 0 001-1v-.667a4 4 0 01.8-2.4l1.4-1.866a4 4 0 00.8-2.4z"}]]
   :filled-thumbs-up-alt      [:g {:transform "scale(1.24) translate(-1 -1)"}
                               [:path {:fill          "currentColor"
                                       :vector-effect :non-scaling-stroke

                                       :d             "M2 10.5a1.5 1.5 0 113 0v6a1.5 1.5 0 01-3 0v-6zM6 10.333v5.43a2 2 0 001.106 1.79l.05.025A4 4 0 008.943 18h5.416a2 2 0 001.962-1.608l1.2-6A2 2 0 0015.56 8H12V4a2 2 0 00-2-2 1 1 0 00-1 1v.667a4 4 0 01-.8 2.4L6.8 7.933a4 4 0 00-.8 2.4z"}]]
   :thumbs-down-alt           [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round"
                                       :d              "M10 14H5.236a2 2 0 01-1.789-2.894l3.5-7A2 2 0 018.736 3h4.018a2 2 0 01.485.06l3.76.94m-7 10v5a2 2 0 002 2h.096c.5 0 .905-.405.905-.904 0-.715.211-1.413.608-2.008L17 13V4m-7 10h2m5-10h2a2 2 0 012 2v6a2 2 0 01-2 2h-2.5"}]]

   :thumbs-up-alt             [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke         "currentColor"
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"}]]


   :filled-chat-annot         [:g {:transform "scale(1.4) translate(-2 -1)"}
                               [:path {:stroke-width "0.1" :fill-rule "evenodd" :d "M18 13V5a2 2 0 00-2-2H4a2 2 0 00-2 2v8a2 2 0 002 2h3l3 3 3-3h3a2 2 0 002-2zM5 7a1 1 0 011-1h8a1 1 0 110 2H6a1 1 0 01-1-1zm1 3a1 1 0 100 2h3a1 1 0 100-2H6z" :clip-rule "evenodd" :fill "currentcolor"}]]

   :chat-annot                [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"}]]

   :filled-heart              [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill          "currentcolor"
                                       :stroke        "currentcolor"
                                       :fill-rule     "evenodd"
                                       :d             "M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                                       :clip-rule     "evenodd"}]]


   :globe                     [:g
                               [:path {:vector-effect  :non-scaling-stroke
                                       :stroke-linecap "round" :stroke-linejoin "round" :d "M21 12a9 9 0 01-9 9m9-9a9 9 0 00-9-9m9 9H3m9 9a9 9 0 01-9-9m9 9c1.657 0 3-4.03 3-9s-1.343-9-3-9m0 18c-1.657 0-3-4.03-3-9s1.343-9 3-9m-9 9a9 9 0 019-9"}]]
   :undo                      [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M2.5 2v6h6M2.66 15.57a10 10 0 1 0 .57-8.38"}]]
   :arrow-down-left           [:g
                               [:path {:vector-effect :non-scaling-stroke :d "M10 9l-6 6 6 6"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M20 4v7a4 4 0 0 1-4 4H5"}]]

   :phone                     [:path {:vector-effect :non-scaling-stroke :d "M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"}]
   :id-key                    [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"}]]
   :id-badge
   [:g {:transform "scale(1.2) translate(-2,-2)"}
    [:path {:vector-effect   :non-scaling-stroke
            :stroke-linecap  "round"
            :stroke-linejoin "round"
            :d               "M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2"}]]

   :move-right                [:g [:path {:stroke-linecap "round" :stroke-linejoin "round" :d "M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"}]]
   :move-left-wtf
   [:g
    {;:transform-origin "50% 0%"
     :transform "rotate(180 12 12)"}
    ;[:rect {:x 0 :y 0 :width 10 :height 10}]
    [:path {:vector-effect  :non-scaling-stroke
            :stroke-linecap "round" :stroke-linejoin "round" :d "M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"}]]

   :variable                  [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M4.871 4A17.926 17.926 0 003 12c0 2.874.673 5.59 1.871 8m14.13 0a17.926 17.926 0 001.87-8c0-2.874-.673-5.59-1.87-8M9 9h1.246a1 1 0 01.961.725l1.586 5.55a1 1 0 00.961.725H15m1-7h-.08a2 2 0 00-1.519.698L9.6 15.302A2 2 0 018.08 16H8"}]]
   :switch/horisontal         [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"}]]

   :filled-cog                [:g {:fill "currentColor"} [:path {:fill-rule "evenodd" :d "M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z" :clip-rule "evenodd"}]]
   :spinner                   [:g
                               [:circle.opacity-25 {:cx "12" :cy "12" :r "10" :stroke "currentColor" :stroke-width "3px"}]
                               [:path.opacity-75 {:stroke-width "0.15px"
                                                  :fill         "currentColor" :d "M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"}]]


   :sun-2                     [:g {:transform "translate(2,2)" :fill "currentColor"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill-rule     "evenodd"
                                       :d             "M10 2a1 1 0 011 1v1a1 1 0 11-2 0V3a1 1 0 011-1zm4 8a4 4 0 11-8 0 4 4 0 018 0zm-.464 4.95l.707.707a1 1 0 001.414-1.414l-.707-.707a1 1 0 00-1.414 1.414zm2.12-10.607a1 1 0 010 1.414l-.706.707a1 1 0 11-1.414-1.414l.707-.707a1 1 0 011.414 0zM17 11a1 1 0 100-2h-1a1 1 0 100 2h1zm-7 4a1 1 0 011 1v1a1 1 0 11-2 0v-1a1 1 0 011-1zM5.05 6.464A1 1 0 106.465 5.05l-.708-.707a1 1 0 00-1.414 1.414l.707.707zm1.414 8.486l-.707.707a1 1 0 01-1.414-1.414l.707-.707a1 1 0 011.414 1.414zM4 11a1 1 0 100-2H3a1 1 0 000 2h1z"
                                       :clip-rule     "evenodd"
                                       :stroke-width  "0.3px"}]]

   :moon-2                    [:g {:transform "rotate(20 12 12) translate(2,2)" #_" translate(4,2)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M17.293 13.293A8 8 0 016.707 2.707a8.001 8.001 0 1010.586 10.586z " :fill "currentColor"}]]

   :cake                      [:g [:path {:stroke-linecap "round" :stroke-linejoin "round" :d "M21 15.546c-.523 0-1.046.151-1.5.454a2.704 2.704 0 01-3 0 2.704 2.704 0 00-3 0 2.704 2.704 0 01-3 0 2.704 2.704 0 00-3 0 2.704 2.704 0 01-3 0 2.701 2.701 0 00-1.5-.454M9 6v2m3-2v2m3-2v2M9 3h.01M12 3h.01M15 3h.01M21 21v-7a2 2 0 00-2-2H5a2 2 0 00-2 2v7h18zm-3-9v-2a2 2 0 00-2-2H8a2 2 0 00-2 2v2h12z"}]]
   :lock-closed2              [:path {:vector-effect  :non-scaling-stroke
                                      :stroke-linecap "round" :stroke-linejoin "round" :d "M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"}]

   :lock-open2                [:path {:vector-effect   :non-scaling-stroke
                                      :stroke-linecap  "round"
                                      :stroke-linejoin "round"
                                      :d               "M8 11V7a4 4 0 118 0m-4 8v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2z"}]

   :lightbulb                 [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"}]]

   :layout-4                  [:g
                               [:rect {:x "3" :y "3" :width "18" :height "18" :rx "2" :fill 'white}]
                               [:rect {:x "3" :y "15" :width "18" :height "6" :rx "2" :fill 'black}]]

   :layout-4-off              [:g [:rect {:x "3" :y "3" :width "18" :height "18" :rx "2" :xfill 'white}] [:path {:d "M3 15h18"}]]

   :circle-axclam-filled      [:g {:fill "currentColor"}
                               [:circle {:cx "12" :cy "12" :r "10"}]
                               [:line {:x1 "12" :y1 "8" :x2 "12" :y2 "12" :stroke :white :stroke-width "2px"}]
                               [:line {:x1 "12" :y1 "16" :x2 "12.01" :y2 "16" :stroke :white :stroke-width "2px"}]]

   :pin                       [:g [:circle {:vector-effect :non-scaling-stroke
                                            :cx            "11.5" :cy "8.5" :r "5.5"}] [:path {:vector-effect :non-scaling-stroke
                                                                                               :d             "M11.5 14v7"}]]
   :tag                       [:g [:path {:vector-effect  :non-scaling-stroke
                                          :stroke-linecap "round" :stroke-linejoin "round" :d "M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"}]]
   :hand                      [:g {:transform "scale(1.1)"}
                               [:path {:vector-effect   :non-scaling-stroke
                                       :stroke-linecap  "round"
                                       :stroke-linejoin "round"
                                       :d               "M7 11.5V14m0-2.5v-6a1.5 1.5 0 113 0m-3 6a1.5 1.5 0 00-3 0v2a7.5 7.5 0 0015 0v-5a1.5 1.5 0 00-3 0m-6-3V11m0-5.5v-1a1.5 1.5 0 013 0v1m0 0V11m0-5.5a1.5 1.5 0 013 0v3m0 0V11"}]]
   :arrow-out                 [:g [:path {:d "M16 17l5-5-5-5M19.8 12H9M13 22a10 10 0 1 1 0-20"}]]
   :arrow-in                  [:g
                               [:path {:vector-effect :non-scaling-stroke :d "M10 17l5-5-5-5"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M13.8 12H3m9 10a10 10 0 1 0 0-20"}]]
   :user                      [:g {:transform "scale(0.95)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "7" :r "4"}]]

   :compass                   [:g [:circle {:cx "12" :cy "12" :r "10"}] [:path {:d "M16.2 7.8l-2 6.3-6.4 2.1 2-6.3z"}]]
   :chevron-left              [:path {
                                      :d             "M15 18l-6-6 6-6"
                                      :vector-effect :non-scaling-stroke}]
   :chevron-left-end          [:path {
                                      :d             "M15 18l-6-6 6-6m -9 0 v12"
                                      :vector-effect :non-scaling-stroke}]
   :chevron-left-left-aligned [:path {:vector-effect :non-scaling-stroke
                                      :d             "M8 18l-6-6 6-6"}]
   :chevron-right             [:path {:vector-effect :non-scaling-stroke
                                      :d             "M9 18l6-6-6-6"}]
   :chevron-right-end         [:path {:vector-effect :non-scaling-stroke
                                      :d             "M9 18l6-6-6-6m9 0v12"}]
   :maximize-2                [:g {:transform "scale(0.8)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M3.8 3.8l16.4 16.4M20.2 3.8L3.8 20.2M15 3h6v6M9 3H3v6M15 21h6v-6M9 21H3v-6"}]]

   :minus                     [:line {:vector-effect :non-scaling-stroke
                                      :x1            "5" :y1 "12" :x2 "19" :y2 "12"}]
   :plus                      [:g

                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "12" :y1 "3" :x2 "12" :y2 "21"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "3" :y1 "12" :x2 "21" :y2 "12"}]]

   :square-round              [:rect {:x "3" :y "3" :width "18" :height "18" :rx "2" :ry "2"}]


   :center-dot                [:g [:circle {:cx "4" :cy "4" :r "2" :fill "currentColor"}]]
   :three-horisontal-dots     [:g [:circle {:cx "12" :cy "12" :r "1"}] [:circle {:cx "19" :cy "12" :r "1"}] [:circle {:cx "5" :cy "12" :r "1"}]]
   :three-vertical-dots       [:g [:circle {:cx "12" :cy "12" :r "1"}] [:circle {:cx "12" :cy "5" :r "1"}] [:circle {:cx "12" :cy "19" :r "1"}]]

   :document                  [:g
                               [:path {
                                       :d "M14 2H6a2 2 0 0 0-2 2v16c0 1.1.9 2 2 2h12a2 2 0 0 0 2-2V8l-6-6z" :vector-effect :non-scaling-stroke}]
                               [:path {
                                       :d "M14 3v5h5M16 13H8M16 17H8M10 9H8" :vector-effect :non-scaling-stroke}]]

   :double-chevron-left       [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M11 17l-5-5 5-5M18 17l-5-5 5-5"}]]
   :double-chevron-right      [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M13 17l5-5-5-5M6 17l5-5-5-5"}]]
   :history                   [:g
                               [:path {:d "M12 20v-6M6 20V10M18 20V4"}]]
   :patch                     [:g
                               [:rect {:vector-effect :non-scaling-stroke :x "4" :y "4" :width "16" :height "16" :rx "2" :ry "2"}]
                               [:rect {:vector-effect :non-scaling-stroke :x "9" :y "9" :width "6" :height "6"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "9" :y1 "1" :x2 "9" :y2 "4"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "15" :y1 "1" :x2 "15" :y2 "4"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "9" :y1 "20" :x2 "9" :y2 "23"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "15" :y1 "20" :x2 "15" :y2 "23"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "20" :y1 "9" :x2 "23" :y2 "9"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "20" :y1 "14" :x2 "23" :y2 "14"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "1" :y1 "9" :x2 "4" :y2 "9"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "1" :y1 "14" :x2 "4" :y2 "14"}]]

   :sun                       [:g {:transform "scale(1) translate(0,1)"}
                               [:circle {:vector-effect :non-scaling-stroke
                                         :fill          :#fa0
                                         :stroke        "currentColor"
                                         :cx            "12" :cy "12" :r "5"}]
                               [:path {:stroke        "currentColor"
                                       :vector-effect :non-scaling-stroke
                                       :d             "M12 1v2M12 21v2M4.2 4.2l1.4 1.4M18.4 18.4l1.4 1.4M1 12h2M21 12h2M4.2 19.8l1.4-1.4M18.4 5.6l1.4-1.4"}]]

   :dot                       [:g {:fill :currentColor :stroke :currentColor}
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "2"}]]

   :cloud                     [:path {
                                      :vector-effect :non-scaling-stroke
                                      :fill          :none  ;:#fff
                                      ;:stroke-width 2.5
                                      :stroke        "currentColor"
                                      :transform     "translate(1,2) scale(0.25) "
                                      :d             "M 20,24 a 20,20 1 0,0 0,40 h 50 a 20,20 1 0,0 0,-40 a 10,10 1 0,0 -15,-10 a 15,15 1 0,0 -35,10 z"}]

   :sun-cloud2                [:g
                               [:g {:transform "scale(0.8) translate(0,4)"}
                                (:sun data)]
                               [:g {:transform "scale(0.7) translate(4,6)"}
                                (:cloud data)]]

   :sun-cloud                 [:g
                               [:g {:transform "scale(0.8) translate(2,7)"}
                                (:sun data)]
                               [:g {:transform "scale(0.9)"}
                                (:cloud data)]]

   :brygge                    [:g {:transform "translate(1,0)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :fill          "none"
                                       :stroke        "currentColor"
                                       :d
                                       "M1 18  v-18h4v15h4v-15h4v15h4v-15h4v18l-3 1v5h-14v-5z"}]]

   :cloud-sun                 [:g
                               [:g {:transform "scale(0.8) translate(2,-2)"}
                                (:sun data)]
                               [:g {:transform "scale(0.9) translate(0,9)" :fill :#fa0 :stroke :#fa0}
                                (:cloud data)]]

   :thumbs-down               [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17"}]]

   :thumbs-up                 [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"}]]
   :eye                       [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"}] [:circle {:cx "12" :cy "12" :r "3"}]]

   :circle-chevron-up         [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10" #_#_:sfill "var(--label-fg-signal)"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M16 12l-4-4-4 4M12 16"}]]
   :circle-arrow-up           [:g
                               [:circle {:vector-effect :non-scaling-stroke :cx "12" :cy "12" :r "10"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M16 12l-4-4-4 4M12 16V9"}]]
   :circle-chevron-down       [:g
                               [:circle {:vector-effect :non-scaling-stroke :cx "12" :cy "12" :r "10"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M16 12l-4 4-4-4M12 8"}]]
   :circle-arrow-down         [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M16 12l-4 4-4-4M12 8v7"}]]
   :circle-arrow-right        [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M12 8l4 4-4 4M8 12h7"}]]
   :circle-arrow-left         [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M12 8l-4 4 4 4M16 12H9"}]]
   :circle-cross              [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "15" :y1 "9" :x2 "9" :y2 "15"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "9" :y1 "9" :x2 "15" :y2 "15"}]]
   :circle-question           [:g
                               [:circle {:cx "12" :cy "12" :r "10" :vector-effect :non-scaling-stroke}]
                               [:path {:d "M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "12" :y1 "17" :x2 "12" :y2 "17" :vector-effect :non-scaling-stroke}]]
   :circle-question-filled    [:g
                               {:class     [:text-amber-200]
                                :transform "translate(0.5,0)"}
                               [:circle {:cx "12" :cy "12" :r "10" :fill "currentColor" :stroke "black" :vector-effect :non-scaling-stroke}]
                               [:path {:d "M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3" :stroke "black" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "12" :y1 "17" :x2 "12" :y2 "17" :stroke :black :vector-effect :non-scaling-stroke}]]

   :edit                      [:g
                               [:polygon {:vector-effect :non-scaling-stroke
                                          :points        "14 2 18 6 7 17 3 17 3 13 14 2"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "3" :y1 "22" :x2 "21" :y2 "22"}]]

   :circle-user-filled        [:g
                               ;[:circle {:cx "12" :cy "12" :r "10" :fill "blue"}]
                               [:path {:d "M22,17 a11,11 0 1,0 -20,0" :fill "lightblue" :stroke "none"}]
                               [:path {:d "M2,17 a11,11 0 0,0 20,0" :fill "blue" :stroke "none"}]
                               [:path {:d "M5.52 19c.64-2.2 1.84-3 3.22-3h6.52 c 1.38 0 2.58 0.8 3.22 4 c 0 0  -7 6 -12.58 0  z " :fill "currentColor"}]
                               [:circle {:cx "12" :cy "10" :r "3.5" :fill "currentColor"}]]

   :circle-user               [:g {:transform "scale(1.15) translate(-1.5,-1.5)"}
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "10" :xfill "white"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M5.52 19c.64-2.2 1.84-3 3.22-3h6.52c1.38 0 2.58.8 3.22 3"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "10" :r "3.5" :xfill :white}]]
   :calendar-add              [:g
                               {:transform "translate(0.5,0.5)"}
                               [:rect {:vector-effect :non-scaling-stroke :y "4" :x "3" :width "18" :height "18" :rx "2" :ry "2"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "16" :y1 "2" :x2 "16" :y2 "6"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "8" :y1 "2" :x2 "8" :y2 "6"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "3" :y1 "10" :x2 "21" :y2 "10"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M12,13v6m-3-3h6"}]]

   :calendar                  [:g
                               [:rect {:vector-effect :non-scaling-stroke :x "3" :y "4" :width "18" :height "18" :rx "2" :ry "2"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "16" :y1 "2" :x2 "16" :y2 "6"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "8" :y1 "2" :x2 "8" :y2 "6"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "3" :y1 "10" :x2 "21" :y2 "10"}]
                               #_[:circle {:cx 12 :cy 16 :r 3 :fill :red :stroke :none}]]

   :house                     [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M20 9v11a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V9"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M9 22 V12 h6 v10 M2 10.6L12 2l10 8.6"}]]
   :lock                      [:g {:vector-effect :non-scaling-stroke
                                   :transform     "scale(0.85) translate(1 0)"}
                               [:rect {:vector-effect :non-scaling-stroke :x "3" :y "11" :width "18" :height "11" :rx "2" :ry "2" :fill "currentColor"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M7 11V7a5 5 0 0 1 10 0v4"}]]
   :lock-open                 [:g {:vector-effect :non-scaling-stroke
                                   :transform     "scale(0.85) translate(1 0)"}
                               [:rect {:vector-effect :non-scaling-stroke :x "3" :y "11" :width "18" :height "11" :rx "2" :ry "2"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M7 11V7a5 5 0 0 1 9.9-1"}]]
   :squares                   [:g
                               [:rect {:vector-effect :non-scaling-stroke :x "3" :y "3" :width "7" :height "7"}]
                               [:rect {:vector-effect :non-scaling-stroke :x "14" :y "3" :width "7" :height "7"}]
                               [:rect {:vector-effect :non-scaling-stroke :x "14" :y "14" :width "7" :height "7"}]
                               [:rect {:vector-effect :non-scaling-stroke :x "3" :y "14" :width "7" :height "7"}]]

   :arrow-left-up             [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M15 10L9 4l-6 6"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M20 20h-7a4 4 0 0 1-4-4V5"}]]
   :arrow-up-left             [:g
                               [:path {:vector-effect :non-scaling-stroke :d "M10 16l-6-6 6-6"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M20 21v-7a4 4 0 0 0-4-4H5"}]]

   :heart                     [:path {:fill "currentColor" :d "M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"}]
   :chat-square               [:g
                               [:path
                                {:vector-effect :non-scaling-stroke
                                 :d             "M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"}]]
   :chat-bubble               [:path {:vector-effect :non-scaling-stroke
                                      :d             "M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"}]
   :paperclip                 [:g {:transform "translate(1 1) scale(0.9)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M21.44 11.05l-9.19 9.19a6 6 0 0 1-8.49-8.49l9.19-9.19a4 4 0 0 1 5.66 5.66l-9.2 9.19a2 2 0 0 1-2.83-2.83l8.49-8.48"}]]

   :star                      [:g
                               {:transform "translate(0.5,0.5)"}
                               [:polygon {:points "12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"}]]
   :question                  [:g
                               ;[:circle {:cx "12" :cy "12" :r "10"}]
                               {:stroke-width 1.5 :transform "translate(-5 -5) scale(1.4)"}
                               [:path {:d "M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"}]
                               [:line {:x1 "12" :y1 "17" :x2 "12" :y2 "17"}]]
   :circle-info               [:g
                               [:circle {:vector-effect :non-scaling-stroke :cx "12" :cy "12" :r "10"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "12" :y1 "16" :x2 "12" :y2 "12"}]
                               [:line {:vector-effect :non-scaling-stroke :x1 "12" :y1 "8" :x2 "12" :y2 "8"}]]
   :circle                    [:circle {:vector-effect :non-scaling-stroke :cx "12" :cy "12" :r "10" :fill "none"}]
   :circle-filled             [:circle {:cx "12" :cy "12" :r "10" :fill "currentColor"}]
   :envelope                  [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"}]
                               [:polyline {:vector-effect :non-scaling-stroke
                                           :points        "22,6 12,13 2,6"}]]

   :at-sign                   [:g
                               [:path {:vector-effect :non-scaling-stroke :d "M16 7v5a3 3 0 0 0 6 0v-1a10 10 0 1 0-3.92 7.94"}]
                               [:circle {:vector-effect :non-scaling-stroke :cx "12" :cy "11" :r "4"}]]

   :out-of-box                [:path
                               {:vector-effect :non-scaling-stroke
                                :d             "M3 15v4c0 1.1.9 2 2 2h14a2 2 0 0 0 2-2v-4M17 8l-5-5-5 5M12 4.2v10.3"}]

   :moon                      [:path {:vector-effect :non-scaling-stroke
                                      :d             "M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" :fill "currentColor"}]
   :arrow-right-up            [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M9 10l6-6 6 6"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M4 20h7a4 4 0 0 0 4-4V5"}]]

   :notebook                  [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M20 14.66V20a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h5.34"}]
                               [:polygon {:vector-effect :non-scaling-stroke
                                          :points        "18 2 22 6 12 16 8 16 8 12 18 2"}]]

   :circle-minus              [:g
                               [:circle {:vector-effect :non-scaling-stroke
                                         :r             "10", :cy "12", :cx "12"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :y2            "12", :x2 "16", :y1 "12", :x1 "8"}]]

   :circle-plus               [:g {:transform "scale(0.90) translate(1,0.5)"}
                               [:circle {;:fill          "currentColor"
                                         :stroke        "currentColor"
                                         :vector-effect :non-scaling-stroke
                                         :r             "12", :cy "12", :cx "12"}]
                               [:g                          ;.text-signal
                                [:line {:vector-effect :non-scaling-stroke
                                        :stroke        "currentColor"
                                        :stroke-width  2
                                        :fill          "none"
                                        :y2            "18", :x2 "12", :y1 "6", :x1 "12"}]
                                [:line {:vector-effect :non-scaling-stroke
                                        :fill          "none"
                                        :stroke        "currentColor"
                                        :y2            "12", :x2 "18", :y1 "12", :x1 "6"}]]]

   :magnifier                 [:g {:transform "scale(0.90) translate(1,0)"
                                   #_#_:stroke-width "2.1"}
                               [:circle {:vector-effect :non-scaling-stroke
                                         :r             "8", :cy "11", :cx "11" :xfill "white"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :y2            "16.65", :x2 "16.65", :y1 "21", :x1 "21"}]]

   :clock                     [:g {:transform "translate(-3.5,-3.5) scale(1.2)"
                                   :stroke    "currentColor"
                                   :fill      "none"}
                               [:circle {:vector-effect :non-scaling-stroke :r "10", :cy "13", :cx "13"}]
                               [:polyline {:vector-effect :non-scaling-stroke
                                           :points        "13 7 13 13 17 15"}]]

   :spark                     [:g
                               [:line {:x1 "12" :y1 "2" :x2 "12" :y2 "6"}]
                               [:line {:x1 "12" :y1 "18" :x2 "12" :y2 "22"}]
                               [:line {:x1 "4.93" :y1 "4.93" :x2 "7.76" :y2 "7.76"}]
                               [:line {:x1 "16.24" :y1 "16.24" :x2 "19.07" :y2 "19.07"}]
                               [:line {:x1 "2" :y1 "12" :x2 "6" :y2 "12"}]
                               [:line {:x1 "18" :y1 "12" :x2 "22" :y2 "12"}]
                               [:line {:x1 "4.93" :y1 "19.07" :x2 "7.76" :y2 "16.24"}]
                               [:line {:x1 "16.24" :y1 "7.76" :x2 "19.07" :y2 "4.93"}]]

   :in-to-box                 [:path
                               {:vector-effect :non-scaling-stroke
                                :d
                                "M3 15v4c0 1.1.9 2 2 2h14a2 2 0 0 0 2-2v-4M17 9l-5 5-5-5M12 12.8V2.5"}]

   :cross-out                 [:g {:transform "translate(-1,0)"}
                               ;[:circle {:cx 13 :cy 12 :r 10}]
                               [:line {:x1 "21" :y1 "4" :x2 "5" :y2 "21" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "5" :y1 "4" :x2 "21" :y2 "21" :vector-effect :non-scaling-stroke}]]

   :hamburger                 [:g {:transform "translate(0,1)"}
                               [:line {:vector-effect :non-scaling-stroke :y2 "6", :x2 "20", :y1 "6", :x1 "4"}]
                               [:line {:vector-effect :non-scaling-stroke :y2 "12", :x2 "20", :y1 "12", :x1 "4"}]
                               [:line {:vector-effect :non-scaling-stroke :y2 "18", :x2 "20", :y1 "18", :x1 "4"}]]
   :square                    [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M2 2 h 20 v 20 h -20 z"}]]

   :arrow-right               [:path {:vector-effect :non-scaling-stroke
                                      :d             "M3 12 h 17 M 15 5 l 7 7 -7 7"}]
   :arrow-left                [:path {:vector-effect :non-scaling-stroke
                                      :transform     "rotate(180 12 12)"
                                      :d             "M3 12 h 17 M 15 5 l 7 7 -7 7"}]
   :cog                       [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "12" :cy "12" :r "4" :fill "transparent"}]]
   :arrow-corner-up-right     [:g
                               [:path {:d "M7 17l9.2-9.2M17 17V7H7"}]]

   :info                      [:g
                               [:path {:d "M10 16l-6-6 6-6"}]
                               [:path {:d "M20 21v-7a4 4 0 0 0-4-4H5"}]]
   :maximize                  [:g {:transform " rotate(-45 12 12) translate(8 8) scale(0.65) translate(-7 -6)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M15 3h6v6M14 10l6.1-6.1M9 21H3v-6M10 14l-6.1 6.1"}]]
   :minimize                  [:g {
                                   :transform " rotate(-45 12 12) translate(6 6) scale(0.75) translate(-4 -6)"}
                               [:path
                                {:vector-effect :non-scaling-stroke
                                 :d             "M4 14h6v6M3 21l6.1-6.1M20 10h-6V4M21 3l-6.1 6.1"}]]

   :maximize'                 [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M15 3h6v6M14 10l6.1-6.1M9 21H3v-6M10 14l-6.1 6.1"}]]
   :minimize'                 [:g [:path {:d "M4 14h6v6M3 21l6.1-6.1M20 10h-6V4M21 3l-6.1 6.1"}]]

   :contents                  [:g
                               [:ellipse {:vector-effect :non-scaling-stroke :cx "12" :cy "5" :rx "9" :ry "3"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M21 12c0 1.66-4 3-9 3s-9-1.34-9-3"}]
                               [:path {:vector-effect :non-scaling-stroke :d "M3 5v14c0 1.66 4 3 9 3s9-1.34 9-3V5"}]]

   :list                      [:g
                               [:line {:x1 "8" :y1 "6" :x2 "21" :y2 "6" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "8" :y1 "12" :x2 "21" :y2 "12" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "8" :y1 "18" :x2 "21" :y2 "18" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "3" :y1 "6" :x2 "3.01" :y2 "6" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "3" :y1 "12" :x2 "3.01" :y2 "12" :vector-effect :non-scaling-stroke}]
                               [:line {:x1 "3" :y1 "18" :x2 "3.01" :y2 "18" :vector-effect :non-scaling-stroke}]]
   :checked-circle            [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M22 11.08V12a10 10 0 1 1-5.93-9.14" :xfill "white"}]
                               [:polyline {:vector-effect :non-scaling-stroke :points "22 4 12 14.01 9 11.01"}]]
   :checked                   [:g
                               {:transform "translate(-2,3)"}
                               [:polyline {:vector-effect :non-scaling-stroke

                                           :points        "22 2 12 14 7 9"}]]

   :warning-sign              [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "12" :y1 "9" :x2 "12" :y2 "13"}]
                               [:line {:vector-effect :non-scaling-stroke
                                       :x1            "12" :y1 "17" :x2 "12.01" :y2 "17"}]]

   ;;[:line {:x1 "12" :y1 "9" :x2 "12" :y2 "13"}]
   ;;[:line {:x1 "12" :y1 "17" :x2 "12.01" :y2 "17"}]]


   :wind-direction            [:g {:transform "scale(0.8) translate(8,5)"}
                               [:path {:d "M3 23 h10 L8 4z" :fill "currentColor"}]]
   :clipboard                 [:g {:transform "rotate(-12 12 12)"}
                               [:path {:d "M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"}] [:rect {:x "8" :y "2" :width "8" :height "4" :rx "1" :ry "1"}]]
   :users-many                [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"}]
                               [:circle {:vector-effect :non-scaling-stroke
                                         :cx            "9" :cy "7" :r "4"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M23 21v-2a4 4 0 0 0-3-3.87"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M16 3.13a4 4 0 0 1 0 7.75"}]]

   :none                      [:g]
   :logo-apple                [:g {:fill :black :stroke-width 1}
                               [:path {:d "M16.46 5.79l.3.01a5.6 5.6 0 0 1 4.38 2.38c-.1.07-2.62 1.53-2.59 4.57.04 3.63 3.19 4.84 3.22 4.86-.02.08-.5 1.72-1.66 3.41-1 1.46-2.04 2.92-3.67 2.95-1.6.03-2.13-.96-3.96-.96-1.84 0-2.42.93-3.94.99-1.57.06-2.78-1.58-3.78-3.04-2.07-2.98-3.64-8.42-1.53-12.1a5.87 5.87 0 0 1 4.97-3c1.55-.03 3.01 1.04 3.96 1.04.95 0 2.73-1.29 4.6-1.1zM16.78 0a5.3 5.3 0 0 1-1.25 3.83 4.46 4.46 0 0 1-3.56 1.7 5.03 5.03 0 0 1 1.27-3.71A5.38 5.38 0 0 1 16.78 0z"}]]
   :wind                      [:g {:fill         "currentColor"
                                   :stroke       0
                                   :stroke-width 0
                                   :transform    "scale(1) translate(0,3)"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M21.5882 10.4619H2.80784C1.34806 10.4619 0.114746 9.20491 0.114746 7.71704C0.114746 6.20353 1.32089 4.97217 2.80342 4.97217C4.2848 4.97217 5.48993 6.19545 5.48993 7.69904C5.48993 8.10887 5.15767 8.4411 4.74786 8.4411C4.33806 8.4411 4.0058 8.10887 4.0058 7.69904C4.0058 7.02542 3.45519 6.4563 2.80342 6.4563C2.15049 6.4563 1.59887 7.03364 1.59887 7.71704C1.59887 8.40041 2.15252 8.97776 2.80784 8.97776H21.5882C21.998 8.97776 22.3302 9.30998 22.3302 9.71982C22.3302 10.1297 21.998 10.4619 21.5882 10.4619Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M25.3727 7.27097H10.1706C8.30607 7.27097 6.78918 5.73912 6.78918 3.85621C6.78918 1.97329 8.29918 0.441406 10.1552 0.441406C12.007 0.441406 13.5136 1.96713 13.5136 3.84251C13.5136 4.25235 13.1813 4.58457 12.7715 4.58457C12.3617 4.58457 12.0294 4.25235 12.0294 3.84251C12.0294 2.7855 11.1887 1.92553 10.1552 1.92553C9.11756 1.92553 8.27331 2.79163 8.27331 3.85621C8.27331 4.9027 9.14212 5.78684 10.1706 5.78684H25.3727C25.7825 5.78684 26.1147 6.11907 26.1147 6.5289C26.1147 6.93874 25.7825 7.27097 25.3727 7.27097Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M5.21558 18.7599C4.31482 18.7599 3.47014 18.4181 2.83707 17.7974C2.19776 17.1707 1.84564 16.3289 1.84564 15.4272C1.84564 13.5896 3.36079 12.0945 5.22312 12.0945H23.7402C24.15 12.0945 24.4822 12.4267 24.4822 12.8365C24.4822 13.2463 24.15 13.5786 23.7402 13.5786H5.22312C4.1614 13.5786 3.32977 14.3906 3.32977 15.4272C3.32977 16.4638 4.15814 17.2758 5.21558 17.2758C6.27092 17.2758 7.09763 16.4578 7.09763 15.4136C7.09763 15.0038 7.42989 14.6715 7.83969 14.6715C8.2495 14.6715 8.58176 15.0038 8.58176 15.4136C8.58176 17.2588 7.07169 18.7599 5.21558 18.7599Z"}]]
   :rain                      [:g {:stroke-width 0.3
                                   :stroke       "currentColor"
                                   :fill         "currentColor"}
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M20.4329 17.5975L19.7366 17.5972L4.58663 17.5908C2.05756 17.5908 0 15.2601 0 12.3953C0 9.92233 1.51226 7.85505 3.57097 7.32933C3.53753 7.12133 3.5207 6.90905 3.5207 6.69336C3.5207 4.74235 4.92583 3.15508 6.65299 3.15508C7.22672 3.15508 7.77652 3.32795 8.25623 3.65203C9.57086 1.68824 11.5993 0.538086 13.8129 0.538086C17.2724 0.538086 20.1885 3.41623 20.7062 7.20914C23.101 7.39109 25 9.64923 25 12.3989C25 15.2375 22.9542 17.5696 20.4396 17.5975C20.4374 17.5975 20.4351 17.5975 20.4329 17.5975ZM19.7371 16.1109L20.4299 16.1112C22.2247 16.0893 23.6842 14.4247 23.6842 12.3989C23.6842 10.3503 22.2081 8.68363 20.3936 8.68363C20.3161 8.68363 20.2332 8.68933 20.1624 8.69488C19.9879 8.70874 19.8154 8.6431 19.6836 8.51287C19.5518 8.38263 19.4714 8.19844 19.4602 8.00107C19.2699 4.64967 16.7893 2.02441 13.8129 2.02441C11.8169 2.02441 10.0065 3.17707 8.96986 5.10772C8.86494 5.30316 8.6862 5.43318 8.48527 5.46029C8.28427 5.48703 8.08356 5.4085 7.94087 5.24641C7.59737 4.85625 7.13999 4.6414 6.65299 4.6414C5.65137 4.6414 4.83649 5.56189 4.83649 6.69336C4.83649 7.01617 4.90135 7.32555 5.02926 7.61295C5.12972 7.83865 5.12178 8.10656 5.00819 8.32421C4.8946 8.54182 4.69091 8.67935 4.46742 8.68933C2.70016 8.76815 1.31579 10.396 1.31579 12.3953C1.31579 14.4405 2.78307 16.1045 4.58663 16.1045H5.26316L19.7371 16.1109Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M10.1946 25.5381C10.0261 25.5381 9.85766 25.4654 9.72917 25.3202C9.47237 25.0298 9.47259 24.5592 9.72965 24.2692L11.3771 22.4101C11.6342 22.12 12.0508 22.1202 12.3075 22.4106C12.5643 22.7009 12.5641 23.1715 12.3071 23.4615L10.6596 25.3207C10.5311 25.4656 10.3628 25.5381 10.1946 25.5381Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M4.47093 21.3021C4.30244 21.3021 4.13398 21.2294 4.00549 21.0841C3.74869 20.7938 3.74892 20.3233 4.00597 20.0332L5.04492 18.8608C5.30197 18.5707 5.71852 18.5709 5.97532 18.8613C6.23212 19.1516 6.23189 19.6221 5.97484 19.9122L4.93589 21.0846C4.80746 21.2296 4.63919 21.3021 4.47093 21.3021Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M13.6157 21.3021C13.4472 21.3021 13.2787 21.2294 13.1502 21.0841C12.8934 20.7938 12.8936 20.3233 13.1507 20.0332L14.1896 18.8608C14.4467 18.5707 14.8632 18.5709 15.12 18.8613C15.3768 19.1516 15.3766 19.6221 15.1196 19.9122L14.0806 21.0846C13.9522 21.2296 13.7839 21.3021 13.6157 21.3021Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M16.5762 23.1721C16.4077 23.1721 16.2392 23.0994 16.1107 22.9541C15.8539 22.6637 15.8542 22.1931 16.1113 21.9031L18.8081 18.8607C19.0652 18.5706 19.4818 18.5709 19.7386 18.8613C19.9953 19.1517 19.995 19.6223 19.7379 19.9123L17.0411 22.9547C16.9126 23.0997 16.7444 23.1721 16.5762 23.1721Z"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M6.64198 23.9896C6.47349 23.9896 6.30503 23.9169 6.17654 23.7716C5.91974 23.4813 5.91997 23.0108 6.17702 22.7207L9.59756 18.8608C9.85461 18.5707 10.2712 18.5709 10.528 18.8613C10.7848 19.1516 10.7845 19.6221 10.5275 19.9122L7.10694 23.7721C6.97851 23.9171 6.81021 23.9896 6.64198 23.9896Z"}]]
   :line-chart                [:g
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M3 3v18h18"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M18.7 8l-5.1 5.2-2.8-2.7L7 14.3"}]]
   :arrow-down-right          [:g [:path {:vector-effect :non-scaling-stroke
                                          :d             "M14 9l6 6-6 6"}]
                               [:path {:vector-effect :non-scaling-stroke
                                       :d             "M4 4v7a4 4 0 0 0 4 4h11"}]]})












(defn adapt
  ([icon]
   (adapt icon 2))
  ([icon width]
   [:svg.w-full.h-full
    {:viewBox        "-1 -1 26 26" :fill "none"
     :height         "100%"
     :stroke         "currentColor"
     :stroke-width   width
     :stroke-linecap "round" :stroke-linejoin "square"}
    (get data icon (get data :square))]))

(defn small [icon]
  [:div.w-5.h-5                                             ;.mt-px.small-icon
   [:svg
    {:xmlns          "http://www.w3.org/2000/svg"
     :viewBox        "0 0 26 26" :fill "none"
     ;:style          {:min-width "1.2rem"}
     :height         "100%"
     :stroke-width   "2"
     :stroke         "currentColor"
     :stroke-linecap "round" :stroke-linejoin "square"}
    [:g {:transform "translate(0 0)"}
     (get data icon #_(get data :square))]]])

(defn inline [icon]
  [:svg.inline.w-6.h-6.mb-1
   {:xmlns          "http://www.w3.org/2000/svg"
    :viewBox        "0 0 26 26" :fill "none"
    ;:style          {:min-width "1.2rem"}
    :height         "100%"
    :stroke-width   "1.5"
    :stroke         "currentColor"
    :stroke-linecap "round" :stroke-linejoin "square"}
   [:rect {:stroke :black
           :fill   :lightgray
           :x      1 :y 1 :width 24 :height 24 :rx 2}]
   [:g {:transform "translate(0 0)"}
    (get data icon #_(get data :square))]])

(defn spinning
  ([icon]
   (spinning :w-8 icon))
  ([class icon]
   [:div
    {:class class}
    [:svg.animate-spin                                      ;.svg-baselinex.xw-6.xh-6.xsvg-icon.pl-px.animate-spin
     {:xmlns               "http://www.w3.org/2000/svg"
      :viewBox             "0 0 24 24"
      :fill                "none"
      ;:width          "1.2rem"
      :width               "100%"
      ;:width               "1.6rem"
      :stroke              "currentColor"
      :stroke-width        2
      :preserveAspectRatio "none"
      :stroke-linecap      "round" :stroke-linejoin "square"}
     (get data icon (get data :square))]]))

(defn adapt-spinning
  ([icon]
   (adapt-spinning icon 2))
  ([icon width]
   [:div
    [:svg.animate-spin                                      ;.svg-baselinex.xw-6.xh-6.xsvg-icon.pl-px.animate-spin
     {:xmlns               "http://www.w3.org/2000/svg"
      :viewBox             "0 0 24 24"
      :fill                "none"
      :width               "100%"
      :stroke              "currentColor"
      :stroke-width        width
      :preserveAspectRatio "none"
      :stroke-linecap      "round" :stroke-linejoin "square"}
     (get data icon (get data :square))]]))

(defn touch [icon]
  [:svg.w-8
   {:xmlns               "http://www.w3.org/2000/svg"
    :viewBox             "0 0 26 26"
    :width               "100%"
    :preserveAspectRatio "none"
    :stroke-width        "2"
    :stroke              "currentColor"
    :fill                :none
    :stroke-linecap      "round" :stroke-linejoin "square"}
   [:g {:transform "translate(1 1)"}
    (get data icon)]])

(defn touch-large [icon]
  [:svg.w-10
   {:xmlns               "http://www.w3.org/2000/svg"
    :viewBox             "0 0 26 26"
    :width               "100%"
    :preserveAspectRatio "none"
    :stroke-width        "3"
    :stroke              "currentColor"
    :fill                :none
    :stroke-linecap      "round" :stroke-linejoin "square"}
   [:g {:transform "translate(1 1)"}
    (get data icon)]])



