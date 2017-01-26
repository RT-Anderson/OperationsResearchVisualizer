(ns optima.pages
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [optima.canvas :as canvas]))


(def more-info (r/atom false))
(def open-tsp-settings (r/atom false))

(defn button [title action]
  [ui/raised-button {:label title :primary true :style { :height 75 :max-width 250 :text-align "center" }  :zDepth 4 :class "waves-effect waves-light black-text" :on-touch-tap action}])


(defn more-info-modal []
   [ui/dialog {:title "Additional Information"
               :modal "false"
               :open @more-info
               :on-request-change #(reset! more-info false)}
    [:div
     [:h1 (if @more-info "A" "B") ]
     [ui/raised-button {:label "close" :on-touch-tap #(reset! more-info false)}]]])

(defn settings-modal []
  [ui/dialog {:title "Settings"
              :modal "false"
              :open @open-tsp-settings
              :on-request-change #(reset! open-tsp-settings false)}
   [:div
    [:h1 ]
    [ui/raised-button {:label "close" :on-touch-tap #(reset! open-tsp-settings false)}]]])




(defn dashboard []
  [:div {:class "row" }
   [:div {:class "col s4 m4 m4 container"} (button "Additional Information" #(reset! more-info true))]
   [:div {:class "col s4 m4 m4 container"} (button "Configure Optimizer" #(reset! open-tsp-settings true)) ]
   [:div {:class "col s4 m4 m4 container"} (button "Run Optimizer" #(reset! more-info true)) ]
   ])



(defn main []
  [:div {:id "test22"}
   [:h4 "Objective: Select the shortest path that connects all vertices"]
   [canvas/hello-world (.-innerWidth js/window)]
   (dashboard)
   (more-info-modal)
   (settings-modal)

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

