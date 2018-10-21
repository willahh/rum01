(ns rum01.route
  (:require [goog.dom :as gdom]
            [rum.core :as rum :refer [defc]]
            [accountant.core :as accountant]
            [secretary.core :as secretary :include-macros true]
            [rum01.state :as state :refer [app-state]]
            [rum01.front.shared.header :as header])
  (:require-macros [secretary.core :refer [defroute]]))

(defc mounted-page < rum/reactive []
  (let [current-view-key (rum/cursor-in app-state [:current-view])
        view-list (rum/cursor-in app-state [:view-list])
        current-view (:fn (@current-view-key @view-list))
        current-view-state (rum/cursor app-state (rum/react (rum/cursor app-state :current-view)))
        page (current-view current-view-state)
        current-page (header/front-page-wrapper app-state page)]
    current-page))

(defn mount [el]
  (rum/mount (mounted-page) el))

(defn mount-app-element []
  (when-let [el (gdom/getElement "app")]
    (mount el)))

(defroute "/" []
  (swap! app-state update-in [:current-view]
         (fn [m]
           :rum01.front.home.home/state-key)))

(defroute "/todolist" []
  (swap! app-state update-in [:current-view]
         (fn [m]
           :rum01.front.todolist.todolist/state-key)))

(defroute "/todolist2" []
  (swap! app-state update-in [:current-view]
         (fn [m]
           :rum01.front.todolist2.todolist2/state-key)))

(defroute "/user" []
  (swap! app-state update-in [:current-view]
         (fn [m]
           :rum01.front.user.user-list/state-key)))

(defroute "/dashboard" []
  (swap! app-state update-in [:current-view]
         (fn [m]
           :rum01.front.dashboard.dashboard/state-key)))


;; (defmacro defcustomroutes [view-list]
;;   (let [r (map (fn [view] (let [uri (-> view last :uri)
;;                                 key (-> view first)]
;;                             `(~'defroute ~uri []
;;                               (swap! app-state update-in [:current-view]
;;                                      (~'fn [~'m]
;;                                       ~key)))))
;;                (:view-list @app-state))])
;;   )
;; (defcustomroutes )

;; (for [view (:view-list @app-state)]
;;   (let [uri (-> view last :uri)
;;         k (-> view first)]
;;     (defroute uri []
;;       (swap! app-state update-in [:current-view]
;;              (fn [m]
;;                k))))
;;   ;; [(-> view last :uri)
;;   ;;  (-> view first)]
;;   )


(do (accountant/configure-navigation! {:nav-handler
                                       (fn [path]
                                         (secretary/dispatch! path))
                                       :path-exists?
                                       (fn [path]
                                         (secretary/locate-route path))})
    (accountant/dispatch-current!)
    (mount-app-element))

;; (mount-app-element)
;; (defn ^:after-load on-reload []
;;   (mount-app-element))
