(ns rum01.todolist2
  (:require [rum.core :as rum :refer [defc]]))

(defc todolist2 < rum/reactive [todolist-state]
  [:div
   [:h3 "todolist2 - a"]
   ;; [:div "c: " (pr-str (rum/react (rum/cursor todolist-state :todolist)))]
   (let [todolist-rows (rum/cursor todolist-state :rows)]
     [:div
      [:div (apply conj
                   [:div] (map (fn [m]
                                 [:span {:style {:border "1px solid #ccc"
                                                 :padding "2px"
                                                 :margin "2px"}} (:name m)])
                               (rum/react (rum/cursor todolist-state :rows))))]
      [:button {:on-click
                (fn [m] (swap! todolist-rows
                               (fn [m]
                                 (conj m {:id 3 :name "todolist2 item"}))))} "add todo"]])])
