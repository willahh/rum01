(ns rum01.todolist.todolist-view
  (:require [rum.core :as rum :refer [defc]]))

(def state #:state {:key :state
                    :rows [{:id 1 :name "Todo 1"}
                           {:id 2 :name "Todo 2"}]
                    :click-count 0
                    :nb 0})

(def state-key ::state)

(defc root-view < rum/reactive [todolist-state]
  [:div
   (let [todolist-rows (rum/cursor todolist-state :rows)
         click-count (rum/cursor todolist-state :click-count)]
     [:div
      [:h3 "todolist"]
      ;; [:div "todolis-state: " (pr-str (rum/react todolist-state))]
      [:div "Click count: "
       [:div {:on-click #(swap! click-count inc)} (pr-str (rum/react click-count))]]
      [:div (apply conj
                   [:div] (map (fn [m]
                                 [:span.ui.teal.horizontal.label
                                  {:style {:border "1px solid #ccc"
                                           :padding "2px"
                                           :margin "2px"}} (:name m)])
                               (rum/react (rum/cursor todolist-state :rows))))]
      [:button.ui.primary.button {:on-click
                                  (fn [m] (swap! todolist-rows
                                                 (fn [m]
                                                   (conj m {:id 3 :name "todolist item"}))))} "add todo"]])])



