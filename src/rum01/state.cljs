(ns rum01.state
  (:require [rum01.front.todolist.todolist :as todolist]
            [rum01.front.todolist2.todolist2 :as todolist2]
            [rum01.front.dashboard.dashboard :as dashboard]
            [rum01.front.home.home :as home]
            [rum01.front.user.user-list :as user-list]
            [rum.core :as rum]))

(defonce app-state
  (atom {:current-view ::todolist2/state-key
         ::home/state-key home/state
         ::todolist/state-key todolist/state
         ::todolist2/state-key todolist2/state
         ::user-list/state-key user-list/state
         
         :view-list {::home/state-key
                     {:name "Home"
                      :uri "/"
                      :fn home/home-view}

                     ::todolist/state-key
                     {:name "Todo list"
                      :uri "/todolist"
                      :fn todolist/todolist-view}

                     ::todolist2/state-key
                     {:name "Todo list 2"
                      :uri "/todolist2"
                      :fn todolist2/todolist2-view}

                     ::user-list/state-key
                     {:name "User list"
                      :uri "/user"
                      :fn user-list/user-list-view}
                     
                     ::dashboard/state-key
                     {:name "Dashboard"
                      :uri "/dashboard"
                      :fn (fn []
                            (dashboard/dashboard-view (rum/cursor app-state ::home/state-key)
                                                      (rum/cursor app-state ::todolist/state-key)
                                                      (rum/cursor app-state ::todolist2/state-key)))}}}))

