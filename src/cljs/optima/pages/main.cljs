(ns optima.pages
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [optima.canvas :as canvas]))


(defn button [title]
  [ui/raised-button {:label title :primary true :style { :height 75 :width 150 :text-align "center" :primary1-color (color :red500)} :zDepth 4 :class "waves-effect waves-light black-text" }])



(defn dashboard []
  [:div {:class "row" }
   [:div {:class "col s4 m4 m4 container"} (button "Button 10")]
   [:div {:class "col s4 m4 m4 container"} (button "Button 2") ]
   [:div {:class "col s4 m4 m4 container"} (button "Button 3") ]
   ])


(defn main []
  [:div
   [:div {:class "row"}
    [:div {:class "col s12 m6"}
     [:div {:class "card blue-grey darken-1"}
      [:div {:class "card-content white-text"}
       [:span {:class "card-title"} "Card Title"]
       [:p "This is a simple card"]]]]]
   (dashboard)
   [canvas/hello-world]
   ])


(defn about-me []
  [:div
   [:h1 "about me"]])

(defn knapsack []
  [:div [:h1 "knapsack problem"]])

(defn graph-coloring []
  [:div [:h1 "graph coloring"]])

(defn tsp []
  [:div [:h1 "traveling salesman problem"]])

