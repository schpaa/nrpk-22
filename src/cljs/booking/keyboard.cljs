(ns booking.keyboard
  (:require [re-frame.core :as rf]
            [goog.events.KeyCodes :as keycodes]
            [re-pressed.core :as rp]))

(rf/reg-event-fx :app/toggle-dark-mode (fn [_ _]
                                         (schpaa.state/toggle :app/dark-mode)))

(defn define-shortcuts []
  (rf/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])
  (rf/dispatch
    [::rp/set-keydown-rules
     {:event-keys [
                   [[:app/toggle-command-palette] [{:metaKey true
                                                    :keyCode keycodes/K}]]
                   [[:app/toggle-command-palette] [{:ctrlKey true
                                                    :keyCode keycodes/K}]]
                   [[:app/toggle-dark-mode] [{:ctrlKey true
                                              :keyCode keycodes/M}]]
                   [[:app/toggle-dark-mode] [{:metaKey true
                                              :keyCode keycodes/M}]]
                   [[:lab/toggle-boatpanel] [{:ctrlKey true
                                              :keyCode keycodes/E}]]
                   #_[[:lab/toggle-number-input] [{:metaKey true
                                                   :keyCode keycodes/E}]]
                   #_[[:lab/toggle-search-mode] [{:ctrlKey true
                                                  :keyCode keycodes/F}]]]
      :prevent-default-keys
      [{:metaKey true
        :keyCode keycodes/SEMICOLON}
       {:ctrlKey true
        :keyCode keycodes/M}
       {:metaKey true
        :keyCode keycodes/M}
       {:metaKey true
        :keyCode keycodes/E}
       {:ctrlKey true
        :keyCode keycodes/E}
       {:ctrlKey true
        :keyCode keycodes/K}
       #_{:metaKey true
          :keyCode keycodes/F}
       {:metaKey true
        :keyCode keycodes/K}
       {:ctrlKey true
        :keyCode keycodes/G}]}]))