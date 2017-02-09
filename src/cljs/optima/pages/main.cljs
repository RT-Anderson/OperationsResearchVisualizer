(ns optima.pages
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [optima.session :as s]
            [optima.canvas :as canvas]))

(def forcer (r/atom true))
(def more-info (r/atom false))
(def open-tsp-settings (r/atom false))
(def settings-dropdown (r/atom "m"))
(def settings-dropdown-algo (r/atom "aco"))
(defn update-vertices []
  (case @settings-dropdown
    "s" 20
    "m" 50
    "l" 100
    "xl" 500))


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

    [:div
     [:p {:class "flow-text"} "Select number of vertices:  "
      [ui/drop-down-menu {:value @settings-dropdown }
       [ui/menu-item {:value "s" :primary-text "Small" :on-touch-tap #(reset! settings-dropdown "s") }]
       [ui/menu-item {:value "m" :primary-text "Medium" :on-touch-tap #(reset! settings-dropdown "m") }]
       [ui/menu-item {:value "l" :primary-text "Large" :on-touch-tap #(reset! settings-dropdown "l") }]
       [ui/menu-item {:value "xl" :primary-text "Extra Large" :on-touch-tap #(reset! settings-dropdown "xl") }]]]]

    [:div
     [:p {:class "flow-text"} "Select solver technique:  "
      [ui/drop-down-menu {:value @settings-dropdown-algo }
       [ui/menu-item {:value "basic" :primary-text "Basic (No Optimization)" :on-touch-tap #(reset! settings-dropdown-algo "basic") }]
       [ui/menu-item {:value "aco" :primary-text "Ant Colony Optimization" :on-touch-tap #(reset! settings-dropdown-algo "aco") }]]]]


    [ui/raised-button {:label "Accept" :on-touch-tap #((canvas/redraw-vertices (update-vertices))
                                                       (reset! open-tsp-settings false))}]]])




(defn dashboard []
  [:div {:class "row" }
   [:div {:class "col s4 m4 m4 container"} (button "Additional Information" #(reset! more-info true))]
   [:div {:class "col s4 m4 m4 container"} (button "Configure Optimizer" #(reset! open-tsp-settings true)) ]
   [:div {:class "col s4 m4 m4 container"} (button "Run Optimizer" #(reset! more-info true)) ]
   ])



(defn main []
  [:div
   [:h4 "Objective: Select the shortest path that connects all vertices" "Test:  " @s/canvas-width "  " @forcer]
   ;;[canvas/hello-world (.-innerWidth js/window)]
   [canvas/tsp-canvas-frame]
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

