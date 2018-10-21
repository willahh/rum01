(ns rum01.todolist
  (:require [rum.core :as rum :refer [defc]]))

(defc todolist < rum/reactive [todolist-state]
  [:div
   
   ;; [:div "c: " (pr-str (rum/react (rum/cursor todolist-state :todolist)))]
   (let [todolist-rows (rum/cursor todolist-state :rows)
         click-count (rum/cursor todolist-state :click-count)]
     [:div
      [:h3 "todolist"]
      [:div "Click count: "
       [:div {:on-click #(swap! click-count inc)} (pr-str (rum/react click-count))]]
      [:div (apply conj
                   [:div] (map (fn [m]
                                 [:span {:style {:border "1px solid #ccc"
                                                 :padding "2px"
                                                 :margin "2px"}} (:name m)])
                               (rum/react (rum/cursor todolist-state :rows))))]
      [:button {:on-click
                (fn [m] (swap! todolist-rows
                               (fn [m]
                                 (conj m {:id 3 :name "todolist item"}))))} "add todo"]])])



