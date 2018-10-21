(ns rum01.todolist
  (:require [rum.core :as rum :refer [defc]]))

(defc todolist < rum/reactive [todolist-state]
  [:div
   [:h3 "todolist"]
   ;; [:div "c: " (pr-str (rum/react (rum/cursor todolist-state :todolist)))]
   (let [todolist-rows (rum/cursor todolist-state :todolist)]
     [:div
      [:div (apply conj
                   [:div] (map (fn [m]
                                 [:span {:style {:border "1px solid #ccc"
                                                 :padding "2px"
                                                 :margin "2px"}} (:name m)])
                               (rum/react (rum/cursor todolist-state :todolist))))]
      [:button {:on-click
                (fn [m] (swap! todolist-rows
                               (fn [m]
                                 (conj m {:id 3 :name "yo"}))))} "add todo"]])])


