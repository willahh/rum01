(ns ^:figwheel-hooks rum01.core
  (:require
   [goog.dom :as gdom]
   [rum.core :as rum :refer [defc]]
   [rum.cursor :as cursor]
   [rum01.todolist :refer [todolist]]
   [rum01.todolist2 :refer [todolist2]]
   [rum01.home :refer [home]]))

(println "This text is printed from src/rumggg01/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b]
  (* a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state
  (atom {:current-view :todolist

         :home {}
         :todolist {:todolist [{:id 1 :name "Todo 1"}
                               {:id 2 :name "Todo 2"}]}
         :todolist2 {:todolist [{:id 1 :name "Todo 1"}]}
         

         :view-list {:home {:fn home
                            }

                     :todolist {:fn todolist
                                }
                     :todolist2 {:fn todolist2
                                 }}}))

(defn get-app-element []
  (gdom/getElement "app"))

(defc input [label value]
  [:label label ": "
   [:input {:value value}]])

(defc debug < rum/reactive [app-state]
  (let [current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (@current-view-key @view-list)
        current-view-fn (:fn current-view)
        current-view-state (rum/cursor app-state [:view-list :todolist])]
    [:div [:div "current-view-key: " (pr-str (rum/react current-view-key))]
     [:div "current-view-state: " (pr-str current-view-state)]
     [:div "current-view" (pr-str current-view)]
     [:div "View list:"
      (map (fn [m]
             [:span (str (first m) " ")]) (:view-list @app-state))]]))

(defc swap-view < rum/reactive [app-state]
  [:div
   [:h3 "Swap view"]
   (apply conj [:div]
          (map (fn [m]
                 [:button
                  {:data-value m
                   :on-click (fn [event]
                               (let [view-name (.getAttribute event.target "data-value")
                                     ]
                                 (js/console.log view-name)
                                 (println (keyword view-name))
                                 (swap! app-state update-in [:current-view] (fn []
                                                                              (keyword view-name)))
                                 ))} (str m)])
               (map first (:view-list @app-state))))
   ])

(defc root-component < rum/reactive [app-state]
  (let [current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (@current-view-key @view-list)
        current-view-fn (:fn current-view)

        ;; current-view-state (rum/cursor app-state [:view-list (rum/react current-view-key)])
        ;; current-view-state (rum/cursor app-state (get @app-state :current-view))
        current-view-state (rum/cursor app-state (rum/react (rum/cursor app-state :current-view)))]
    [:div
     ;; (debug app-state)
     (swap-view app-state)
     [:div "From current"]
     [:div (current-view-fn current-view-state)]

     [:div "From root"]
     [:div [:strong "current-view-state:"]
      [:br] (pr-str current-view-state)]
     (todolist current-view-state)]))

(defn mount [el]
  (rum/mount (root-component app-state) el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))

