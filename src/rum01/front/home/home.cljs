(ns rum01.front.home.home
  (:require [rum.core :as rum :refer [defc]]))

(def state #:state {:click-count 0})
(def state-key ::state)

(defc home-view < rum/reactive [home-state]
  [:div
   [:h4 "Home"]
   [:div "State:" (pr-str (rum/react home-state))]])

