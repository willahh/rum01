(ns rum01.front.dashboard.dashboard
  (:require [rum.core :as rum :refer [defc]]
            [rum01.front.todolist.todolist :as todolist]
            [rum01.front.todolist2.todolist2 :as todolist2]))

(def state #:state {:click-count 0})
(def state-key ::state)

(defc dashboard-view < rum/reactive [home-state todolist-state todolist2-state]
  [:div
   [:h3 "dashboard 5"]
   [:div "This view will load todolist view and home view and interact with them."]
   [:div.ui.grid.container
    [:div.six.wide.column
     (todolist/todolist-view todolist-state)]
    [:div.six.wide.column
     (todolist2/todolist2-view todolist2-state)]]])
