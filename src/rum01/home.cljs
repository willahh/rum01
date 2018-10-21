(ns rum01.home
  (:require [rum.core :as rum :refer [defc]]))

(def state {:click-count 0})
(def state-key ::state)

(defc root-view < rum/reactive [home-state]
  [:div
   [:h4 "Home"]
   [:div "State:" (pr-str (rum/react home-state))]])

