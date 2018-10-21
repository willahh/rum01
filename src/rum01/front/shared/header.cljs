(ns rum01.front.shared.header
  (:require [clojure.string :as str]
            [rum.core :as rum :refer [defc]]))

(defc header < rum/reactive [app-state]
  [:div
   (apply conj [:div.ui.secondary.pointing.menu]
          (map (fn [m]
                 (let [current-view (:current-view @app-state)]
                   [:a 
                    {:href (:uri (last m))
                     :class (str "item " (when (= (:current-view @app-state) (-> m first))
                                           "active"))}
                    (-> m last :name)]))
               (:view-list @app-state)))])

(defc front-page-wrapper < rum/reactive [app-state page]
  [:div.main
   (header app-state)
   page])
