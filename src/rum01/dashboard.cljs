(ns rum01.dashboard
  (:require [rum.core :as rum :refer [defc]]
            [rum01.todolist.todolist-view :as todolist-view]))

(def state #:state {:click-count 0})
(def state-key ::state)

(defc root-view < rum/reactive [home-state todolist-state]
  [:div
   [:h3 "dashboard 5"]
   [:div "This view will load todolist view and home view and interact with them."]
   (todolist-view/root-view todolist-state) 
   ])
