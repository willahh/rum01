(ns rum01.front.user.user-form
  (:require [rum.core :as rum :refer [defc]]))

(def state #:state {:rows [{:id 1
                            :first-name "John"
                            :last-name "Doe"
                            :email "johnoe@mail.com"}
                           {:id 2
                            :first-name "Jack"
                            :last-name "Do"
                            :email "jackdo@mail.com"}]})
(def state-key ::state)

(defc user-view < rum/reactive [todolist-state]
  [:div
   [:h3 "User"]
   [:form.ui.form
    [:.field
     [:label "First name"]
     [:input {:type "text" :name "first-name" :placeholder "First name"}]]
    [:.field
     [:label "Last name"]
     [:input {:type "text" :name "last-name" :placeholder "Last name"}]]
    [:button.ui.button {:type "submit"} "Submit"]]])
