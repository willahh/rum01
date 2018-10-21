(ns ^:figwheel-hooks rum01.core
  (:require
   [goog.dom :as gdom]
   [rum.core :as rum]))

(println "This text is printed from src/rumggg01/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"
                          :current-view nil
                          :view-list []
                          :todolist [
                                     {:id 1 :name "Todo 1"}
                                     {:id 2 :name "Todo 2"}
                                     ]}))

(defn get-app-element []
  (gdom/getElement "app"))

(rum/defc root-component < rum/reactive []
  [:div
   [:h1 "header"]
   [:div "Switch main view:"]
   (apply conj [:select
                {:on-change
                 (fn [event]
                   (let [view-name event.target.value
                         view-target ((keyword view-name) (:view-list @app-state))]
                     (swap! app-state assoc-in [:current-view] (:fn view-target))))}]
          (map (fn [m]
                 [:option {:value (:name (second m))} (:name (second m))])
               (:view-list @app-state)))
   
   [:div
    ((:current-view (rum/react app-state)))]])

(rum/defc hello-world < rum/reactive []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this i3223nn src/rum01/core.cljs and watch it 3 change!"]])

(rum/defc home []
  [:div
   [:h1 "Home"]
   ])

(defn add-todo []
  (swap! app-state update-in [:todolist]
         (fn [m]
           (conj m {:id (count (:todolist @app-state)) :name (rand)}))))

(rum/defc todo-component < rum/reactive []
  [:div
   [:h3 "Todo 6"]
   (apply conj [:ul]
          (map (fn [m]
                 [:li (:name m)])
               (:todolist (rum/react app-state))))
   [:button {:on-click (fn [m] (add-todo))}
    "add ddd" ]])

(defonce init (do
                (swap! app-state update-in [:current-view]
                       (fn [m]
                         todo-component))
                (swap! app-state update-in [:view-list]
                       (fn [v] {:hello-world
                                {:name "hello-world" :fn hello-world}
                                :home
                                {:name "home" :fn home}
                                :todo-component
                                {:name "todo-component" :fn todo-component}}))))

(defn mount [el]
  (rum/mount (root-component) el)
  ;; (rum/mount ((:current-view @app-state)) el)
  )

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

