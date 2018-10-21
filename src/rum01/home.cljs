(ns rum01.home
  (:require [rum.core :as rum :refer [defc]]))

(defc home < rum/reactive [home-state]
  [:div
   [:h4 "Home"]
   [:div "State:" (pr-str (rum/react home-state))]])

