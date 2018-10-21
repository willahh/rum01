(ns ^:figwheel-hooks rum01.core
  (:require
   [goog.dom :as gdom]
   [rum.core :as rum :refer [defc]]))

(println "This text is printed from src/rumggg01/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b]
  (* a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"
                          :current-view nil
                          :view-list {}
                          :todolist [
                                     {:id 1 :name "Todo 1"}
                                     {:id 2 :name "Todo 2"}
                                     ]}))

(defn get-app-element []
  (gdom/getElement "app"))

(defc input [label value]
  [:label label ": "
   [:input {:value value}]])

(defc todolist < rum/reactive [todolist-rows]
  [:div
   [:h3 "todolist"]
   [:div "todolist-rows cursor "(pr-str (rum/react todolist-rows))]
   [:div (apply conj
                [:ul] (map (fn [m]
                             [:li (:name m)]) (rum/react todolist-rows)))]

   [:button {:on-click
             (fn [m]
               (swap! todolist-rows (fn [m]
                                      (conj m {:id 3 :name "yo"}))))} "add todo"]])

(defc home < rum/reactive [home-state]
  [:div
   [:h4 "Home"]
   [:div "State:"
    (pr-str home-state)]])


;; (let [current-view (rum/cursor-in app-state [:current-view])
;;       view-list (rum/cursor-in app-state [:view-list])]
;;   (@current-view @view-list))

(defc root-component < rum/reactive [app-state]
  (let [todolist-rows (rum/cursor app-state [:todolist])
        current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (@current-view-key @view-list)
        current-view-fn (:fn current-view)
        ;; current-view-state (:state current-view)
        current-view-state (rum/cursor app-state [:view-list :todolist])
        
        ;; view-list (rum/cursor app-state [:view-list])
        ;; current-view-key (:current-view @app-state)
        ;; current-view-state (:state (current-view-key (:view-list @app-state)))
        ;; current-view-fn (:fn (current-view-key (:view-list @app-state)))
        ]
    [:div
     [:h1 "Root"]
     [:div "current-view-key: " (pr-str (rum/react current-view-key))]
     [:div "current-view-state: " (pr-str current-view-state)]
     [:div "current-view" (pr-str current-view)]
     [:div "View list:"
      (map (fn [m]
             [:span (str (first m) " ")]) (:view-list @app-state))]

     [:h2 "Current view fn"]
     [:div (current-view-fn current-view-state)]

     [:h2 "From root-component"]
     [:div "todolist-rows" (pr-str todolist-rows)]
     [:div (apply conj
                  [:ul] (map (fn [m]
                               [:li (:name m)]) (rum/react todolist-rows)))]
     [:div
      [:button {:on-click
                (fn [m]
                  (swap! todolist-rows
                         (fn [m]
                           (conj m {:id 3 :name "yo"}))))} "add todo"]]]))

(defonce init (do
                (swap! app-state update-in [:view-list]
                       (fn [m]
                         {:todolist
                          {:fn todolist
                           :state (atom {:todolist [{:id 1 :name "Todo 1"}
                                                    {:id 2 :name "Todo 2"}]})}
                          :home
                          {:fn home
                           :state (atom {})}}))
                (swap! app-state update-in [:current-view]
                       (fn [m]
                         :todolist))))


(defn mount [el]
  (rum/mount (root-component app-state) el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element )

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

