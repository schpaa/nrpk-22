(ns schpaa.markdown
  (:require [shadow.resource :refer [inline]]
            [markdown.core :as m]
            [markdown.transformers :as mt]))

(defn escape-html
  "Change special characters into HTML character entities."
  [text state]
  [(if-not (or (:code state) (:codeblock state))
     (clojure.string/escape
       text
       {\& "&amp;"
        \< "&lt;"
        ;;\> "&gt;"
        \" "&quot;"
        \' "&#39;"})
     text) state])

(defn capitalize [text state]
  [(.toUpperCase text) state])

(defn md->html' [content]
  (m/md->html-with-meta content
                        :footnotes? true
                        :custom-transformers [capitalize]
                        :replacement-transformers (into [escape-html] mt/transformer-vector)
                        :heading-anchors true))

(defn md->html [md]
  [:div {;:style {:white-space :pre-wrap}
         :dangerouslySetInnerHTML {:__html (m/md->html md)}}])
