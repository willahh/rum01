(ns rum01.state
  (:require [rum01.todolist.todolist-view :as todolist-view]
            [rum01.todolist2 :as todolist2]
            [rum01.dashboard :as dashboard]
            [rum01.home :as home]))

(defonce app-state
  (atom {:current-view ::state.todolist2
         
         ::state.home home/state
         ::state.todolist-view todolist-view/state
         ::state.todolist2 rum01.todolist2/state
         
         :view-list {
                     ::state.home home/root-view
                     ::state.todolist-view todolist-view/root-view
                     ::state.todolist2 todolist2/root-view
                     ::state.dashboard dashboard/root-view}}))