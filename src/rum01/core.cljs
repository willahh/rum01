(ns ^:figwheel-hooks rum01.core
  (:require [goog.dom :as gdom]
            [rum.core :as rum]
            [rum01.root :as root-view]
            [rum01.state :as state :refer [app-state]]))

(defn multiply [a b]
  (* a b))

(defn mount [app-state el]
  (rum/mount (root-view/root-component app-state) el))

(defn mount-app-element [app-state]
  (when-let [el (gdom/getElement "app")]
    (mount app-state el)))

(mount-app-element app-state)

(defn ^:after-load on-reload []
  (mount-app-element app-state))

