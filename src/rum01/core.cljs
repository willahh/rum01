(ns ^:figwheel-hooks rum01.core
  (:require
   [goog.dom :as gdom]
   [rum.core :as rum :refer [defc]]
   [rum.cursor :as cursor]
   [rum01.todolist :refer [todolist]]
   [rum01.todolist2 :refer [todolist2]]
   [rum01.dashboard :refer [dashboard]]
   [rum01.home :refer [home]]))

(println "This text is printed from src/rumggg01/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b]
  (* a b))

(defonce app-state
  (atom {:current-view :todolist
         
         :home {}
         :todolist {:rows [{:id 1 :name "Todo 1"}
                           {:id 2 :name "Todo 2"}]
                    :click-count 0}
         :todolist2 {:rows [{:id 1 :name "Todo 1"}]
                     :click-count 0}
         
         :view-list {:home home
                     :todolist todolist
                     :todolist2 todolist2
                     :dashboard dashboard}}))

(defc swap-view < rum/reactive [app-state]
  [:div
   (apply conj [:div]
          (map (fn [m]
                 [:button
                  {:data-value m
                   :on-click (fn [event]
                               (let [view-name (.getAttribute event.target "data-value")]
                                 (js/console.log view-name)
                                 (println (keyword view-name))
                                 (swap! app-state update-in [:current-view] (fn []
                                                                              (keyword view-name)))
                                 ))} (str m)])
               (map first (:view-list @app-state))))])

(defc root-component < rum/reactive [app-state]
  (let [current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (@current-view-key @view-list)
        current-view-state (rum/cursor app-state (rum/react (rum/cursor app-state :current-view)))]
    [:div
     (swap-view app-state)
     [:div {:style {:border "1px solid #ccc"
                    :padding "4px"
                    :margin "0 0 12px 0"}}
      
      [:div "Current"]
      (current-view current-view-state)]

     
     [:div {:style {:border "1px solid #ccc"
                    :padding "4px"
                    :margin "4px"}}
      
      [:div "Root"]
      [:div
       [:div "Root todolist"]
       (todolist (rum/cursor app-state :todolist))

       [:div "Root todolist2"]
       (todolist2 (rum/cursor app-state :todolist2))]]]))

(defn mount [el]
  (rum/mount (root-component app-state) el))

(defn mount-app-element []
  (when-let [el (gdom/getElement "app")]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))

