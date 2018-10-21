(ns rum01.todolist2
  (:require [rum.core :as rum :refer [defc]]))

(defc todolist2 < rum/reactive [todolist-rows]
  [:div
   [:h3 "todolist2-"]
   [:div "todolist-rows cursor "(pr-str (rum/react todolist-rows))]
   [:div (apply conj
                [:ul] (map (fn [m]
                             [:li (:name m)]) (rum/react todolist-rows)))]

   [:button {:on-click
             (fn [m]
               (swap! todolist-rows (fn [m]
                                      (conj m {:id 3 :name "yo"}))))} "add todo"]])