(ns optima.templates
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.icons :as ic]
    [reagent.core :as r]
    [optima.session :as s]
    [optima.pages :as page]))


(defn select-page []
  (case @s/current-page
    "Operations Reseach Fundemental Problems" [page/main]
    "Knapsack Problem" [page/knapsack]
    "Graph Coloring Problem" [page/graph-coloring]
    "Traveling Salesman Problem" [page/tsp]
    "About Me" [page/about-me]
    ))

(defn switch [] (swap! s/sidebar-expanded? not))
(defn expand-navbar [] (if @s/sidebar-expanded? 280 24))


(defn header []
  [:div
   [ui/app-bar {:title @s/current-page
                :zIndex 0
                :style {:paddingLeft (expand-navbar)}
                :icon-element-left (r/as-element
                                     [ui/icon-button (ic/navigation-menu) { :on-touch-tap #(swap! s/sidebar-expanded? not)}])

                :on-left-icon-button-touch-tap switch}]]
  )

(defn sidebar []
  [:div

   [ui/drawer {:docked true
               :zIndex 0
               :open @s/sidebar-expanded?
               :on-request-change switch}

    [:li {:on-touch-tap #(reset! s/current-page "Operations Reseach Fundemental Problems")}
     [:div {:class "logo-wrapper waves-light waves-effect waves-light"}

       [:img {:id "logo" :src "img/logo2.svg" :class "img-fluid flex-center" :alt "Logo" :style {:height 100 :margin-left 75} :on-touch-tap #(reset! s/current-page "Operations Research Fundemental Problems")}]]
     [:h3 {:style {:text-align "center"}}  "Operations Research"]]

    [ui/divider]

    [:li
     [:br]
     [ui/menu {:desktop true :style {:text-align "center"}}
      [ui/menu-item {:on-touch-tap #(reset! s/current-page "Knapsack Problem")} "Knapsack Problem"]
      [ui/menu-item {:on-touch-tap #(reset! s/current-page "Graph Coloring Problem")}"Graph Coloring Problem"]
      [ui/menu-item {:on-touch-tap #(reset! s/current-page "Traveling Salesman Problem")} "Traveling Salesman Problem"]
      [:br]
      [ui/divider]
      [:br]
      [ui/menu-item {:on-touch-tap #(reset! s/current-page "About Me")} "About Me"]]]




    ]])


(defn footer []
  [:div                 {:style {:paddingLeft (expand-navbar)}}
   [:h1 "Footer"]])





(defn wireframe []
  [:div
   [ui/mui-theme-provider
    {:mui-theme (get-mui-theme
                  {:palette       {:primary1-color (color :blueGrey700)}
                   :appBar        {:height 70 }})}
    [:div
     [header]
     [sidebar]
     [:div {:id "body" :style {:paddingLeft (expand-navbar)}} (select-page)]
     ;;[footer]
     ]]])









