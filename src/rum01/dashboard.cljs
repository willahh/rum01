(ns rum01.dashboard
  (:require [rum.core :as rum :refer [defc]]
            [rum01.todolist :refer [todolist]]))

(defc dashboard < rum/reactive [home-state]
  [:div
   [:h3 "dashboard"]
   [:div "This view will load todolist view and home view and interact with them."]])
