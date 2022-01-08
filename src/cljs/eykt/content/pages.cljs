(ns eykt.content.pages
  (:require [shadow.resource :refer [inline]]
            [schpaa.markdown :refer [md->html]]))

(defn new-designed-content [{:keys [desktop?] :as m}]
  [:div
   {:class [:prose :prose-stone :dark:prose-invert :prose-h2:mb-2
            :prose-headings:font-black
            :prose-headings:text-alt
            :prose-h1:text-2xl
            :prose-h2:text-xl
            :prose-h3:text-lg
            :antialiased
            ;:md:mr-24
            ;:prose-h2:bg-alt
            ;"prose-headings:text-black/50"
            :prose-p:font-serif
            :prose-li:font-sans
            "prose-li:text-black/50"
            ;"prose-li:mr-8"
            "prose-li:italic"]}

   (md->html (inline "./about.md"))])
