(ns rum01.home
  (:require [rum.core :as rum :refer [defc]]))

(defc home < rum/reactive [home-state]
  [:div
   [:h4 "Home 2 3 4"]
   [:div "State:"
    (pr-str (rum/react home-state))]])