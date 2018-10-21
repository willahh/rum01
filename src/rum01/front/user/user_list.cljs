(ns rum01.front.user.user-list
  (:require [rum.core :as rum :refer [defc]]
            ))

(def state #:state {:rows [{:id 1
                            :first-name "John"
                            :last-name "Doe"
                            :email "johnoe@mail.com"}
                           {:id 2
                            :first-name "Jack"
                            :last-name "Do"
                            :email "jackdo@mail.com"}]})
(def state-key ::state)

(defc user-list-view < rum/reactive [todolist-state]
  [:div
   [:h3 "User list"]
   ;; (pr-str (@todolist-state))

   ])



