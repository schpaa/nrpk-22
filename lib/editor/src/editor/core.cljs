(ns editor.core
  (:require [applied-science.js-interop :as j]
            [reagent.core :as r]
            ["@codemirror/gutter" :refer [lineNumbers]]
            ["@codemirror/highlight" :as highlight :refer [tags HighlightStyle]]
            ["@codemirror/lang-markdown" :refer [markdown, markdownLanguage]]
            ["@codemirror/view" :refer [EditorView keymap]]
            ["@codemirror/state" :refer [EditorState StateEffect]]))

(def midiaccess (aget js/navigator "requestMIDIAccess"))

(comment
  (do
    (.then (.call midiaccess js/navigator #js {:sysex true})
           (fn [e] (tap> (. e)))))
  (do
    (-> (.-requestMIDIAccess js/navigator)
        (.then (fn [e] 1) (fn [e] 1))))
  (do
    (-> (.call (aget js/navigator "requestMIDIAccess"))
        (.then (fn [e] (js/console.log "OK")) (fn [e] (js/console.log "ERROR "))))))


(def theme
  (.theme EditorView
          (j/lit {
                  ".cm-o-replacement"       {:display      "inline-block",
                                             :width        ".5em",
                                             :height       ".5em",
                                             :borderRadius ".25em"}

                  "&.cm-o-replacement"      {:backgroundColor "#04c"}
                  ;".cm-cursor, .cm-dropCursor" {:borderLeftWidth "5rem"},
                  ;".CodeMirror-insert .CodeMirror-cursor" {:width "4px"}
                  ".cm-content"             {:white-space "pre-wrap"
                                             :caretColor "currentColor"
                                             :caret-width "4px"
                                             :color       "currentColor"
                                             :padding     "10px 0"}
                  ;".cm-content, .cm-gutter" {:height "50%"}
                  ".cm-scroller"            {:overflow "auto"}
                  "&"                       {;:margin-top "4rem"
                                             ;:bottom "4rem"
                                             :height "calc(100vh - 8rem)"
                                             :xbackground "currentColor"}
                  "&.cm-focused"            {:outline "none"}
                  ;".cm-line" {:color "red"}
                  ".cm-line"                {:padding     "0 9px"
                                             ;:color       "red"
                                             :padding-bottom "0.5rem"
                                             :padding-right "4rem"
                                             :line-height "1.7"
                                             :font-size   "1rem"
                                             ;:font-weight "light"
                                             :font-family "sans-serif"}
                  ".cm-matchingBracket"     {:border-bottom "1px solid var(--teal-color)"
                                             :color         "inherit"}
                  ".cm-gutters"             {:background "transparent"
                                             :color "#bbb"
                                             :line-height "1.7"
                                             :font-size   "1rem"
                                             :font-family "sans-serif"
                                             ;:margin-right "1rem"
                                             :border     "none"}
                  ".cm-gutterElement"       {:margin-left "5px"}
                  ;; only show cursor when focused
                  ".cm-cursor"              {:border "1rem"
                                             :visibility "hidden"}
                  ;"&.cm-focused .cm-selectionBackground, ::selection" {:backgroundColor "#074"}

                  "&.cm-focused .cm-cursor" {:color "red"
                                             :visibility "visible"}})
          {:dark true}))

(def view (atom nil))

(def my-ext
  #js [theme
       highlight/defaultHighlightStyle
       (.define HighlightStyle
                (clj->js [{:tag (.-strong tags)
                           ;:color "black"
                           :fontWeight "600"
                           :xstyle "text-decoration:underline"}
                          {:tag (.-heading tags)
                           ;:style "text-decoration:underline"
                           :fontWeight "600"
                           :fontSize "1.2rem"}
                          {:tag (.-list tags)
                           :color "green"}]))
       (lineNumbers)
       (markdown (clj->js {:base markdownLanguage}))
       (.of keymap (clj->js [{:key "Ctrl-Space"
                              :run (fn [st]
                                     (js/console.log @view)
                                     (.dispatch @view {})
                                     (js/console.log st))}]))])

(defn editor [source {:keys [!view]}]
  (reset! view !view)
  (r/with-let [text (r/atom nil)
               editorState (.create EditorState #js {:extensions my-ext
                                                     :doc        source})
               mount! (fn [el]
                        (when el
                          (reset! !view (new EditorView
                                             (j/obj :state editorState
                                                    :parent el)))))]
    [:div.text-gray-800.dark:text-gray-300
     [:div {:class  [:max-w-xl :mx-auto
                     "mb-0 text-sm monospacex overflow-y-auto"]
            :ref    mount!}]]
    (finally
      (j/call @!view :destroy))))