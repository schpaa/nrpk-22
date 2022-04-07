(ns booking.keyboard
  (:require [re-frame.core :as rf]
            [goog.events.KeyCodes :as keycodes]
            [re-pressed.core :as rp]))

(defn define-shortcuts []
  (rf/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])
  (rf/dispatch
    [::rp/set-keydown-rules
     {:event-keys [#_[[:app/toggle-config] [{:metaKey true
                                             :keyCode keycodes/SEMICOLON}]]
                   [[:app/toggle-command-palette] [{:metaKey true
                                                    :keyCode keycodes/K}]]
                   [[:app/toggle-command-palette] [{:ctrlKey true
                                                    :keyCode keycodes/K}]]
                   #_[[:lab/toggle-search-mode] [{:metaKey true
                                                  :keyCode keycodes/F}]]
                   [[:lab/toggle-number-input] [{:metaKey true
                                                 :keyCode keycodes/E}]]
                   #_[[:lab/toggle-search-mode] [{:ctrlKey true
                                                  :keyCode keycodes/F}]]]
      :prevent-default-keys
      [{:metaKey true
        :keyCode keycodes/SEMICOLON}
       {:metaKey true
        :keyCode keycodes/E}
       {:ctrlKey true
        :keyCode keycodes/K}
       {:metaKey true
        :keyCode keycodes/F}
       {:metaKey true
        :keyCode keycodes/K}
       {:ctrlKey true
        :keyCode keycodes/G}]}]))