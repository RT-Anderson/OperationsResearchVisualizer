(ns optima.session
  (:require [reagent.core :as r]))

(def sidebar-expanded? (r/atom true))

(defn sidebar-offset [] (if @sidebar-expanded? 280 24))

(def canvas-width (r/atom (- (.-innerWidth js/window) (sidebar-offset)) ))
(def canvas-height (r/atom 500))
(def canvas-margin 15)


(defn toggle-sidebar []
  (swap! sidebar-expanded? not)
  (if @sidebar-expanded? (reset! canvas-width (- (.-innerWidth js/window) (sidebar-offset)))
                         (reset! canvas-width (- (.-innerWidth js/window) (sidebar-offset))))
  )


(def current-page (r/atom "Operations Reseach Fundemental Problems"))


;;(defonce canvas-margin 15)
