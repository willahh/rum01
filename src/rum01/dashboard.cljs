(ns rum01.dashboard
  (:require [rum.core :as rum :refer [defc]]
            [rum01.todolist.todolist-view :as todolist-view]))

(def state {:click-count 0})

(def state-key ::state)

(defc root-view < rum/reactive [home-state]
  [:div
   [:h3 "dashboard"]
   [:div "This view will load todolist view and home view and interact with them."]
   ;; (todolist-view/root-view (rum/cursor app-state ::todolist-view/state-key))
   ])
