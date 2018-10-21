(ns rum01.state
  (:require [rum01.todolist.todolist-view :as todolist-view]
            [rum01.todolist2 :as todolist2]
            [rum01.dashboard :as dashboard]
            [rum01.home :as home]
            [rum.core :as rum]))

(defonce app-state
  (atom {:current-view ::rum01.todolist2/state-key

         ::home/state-key home/state
         ::todolist-view/state-key todolist-view/state
         ::rum01.todolist2/state-key rum01.todolist2/state
         
         :view-list {
                     ::home/state-key home/root-view
                     ::todolist-view/state-key todolist-view/root-view
                     ::rum01.todolist2/state-key todolist2/root-view
                     ::dashboard/state-key
                     (fn []
                       (dashboard/root-view (rum/cursor app-state ::home/state-key)
                                            (rum/cursor app-state ::todolist-view/state-key)))}}))