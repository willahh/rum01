(ns rum01.root
  (:require
   [goog.dom :as gdom]
   [rum.core :as rum :refer [defc]]
   [rum.cursor :as cursor]
   [rum01.todolist.todolist-view :as todolist-view]
   [rum01.todolist2 :as todolist2]
   [rum01.dashboard :as dashboard]
   [clojure.string :as str]))

(defc main-nav < rum/reactive [app-state]
  [:div
   (apply conj [:div.ui.secondary.pointing.menu]
          (map (fn [m]
                 [:button
                  {:fn (str/replace-first (-> m first str) #":" "")
                   :class (str "item " (when (= (:current-view @app-state) (-> m first))
                                         "active"))
                   :on-click (fn [event]
                               (let [view-name (.getAttribute event.target "fn")]
                                 (swap! app-state update-in [:current-view]
                                        (fn []
                                          (keyword view-name)))))}
                  (-> m last :name)
                  ])
               (:view-list @app-state)))])

(defc root-component < rum/reactive [app-state]
  (let [current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (:fn (@current-view-key @view-list))
        current-view-state (rum/cursor app-state (rum/react (rum/cursor app-state :current-view)))]
    [:div
     (main-nav app-state)
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
       (todolist-view/root-view (rum/cursor app-state ::todolist-view/state-key))

       [:div "Root todolist2"]
       (todolist2/root-view (rum/cursor app-state ::todolist2/state-key))]]]))

