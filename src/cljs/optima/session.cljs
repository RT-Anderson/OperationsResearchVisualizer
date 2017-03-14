(ns optima.session
  (:require [reagent.core :as r]))

;;SIDEBAR STATE
(def sidebar-expanded? (r/atom true))
(defn sidebar-offset [] (if @sidebar-expanded? 280 24))

;;CANVAS OBJECT STATE
(def vertex-cnt (r/atom 50))
(def canvas-width (r/atom (- (.-innerWidth js/window) (sidebar-offset)) ))
(def canvas-height (r/atom 500))
(def canvas-margin 15)

;;CANVAS RESIZE LOGIC
(defn toggle-sidebar []
  (swap! sidebar-expanded? not)
  (if @sidebar-expanded? (reset! canvas-width (- (.-innerWidth js/window) (sidebar-offset)))
                         (reset! canvas-width (- (.-innerWidth js/window) (sidebar-offset)))))

;;PATH OBJECT STATE
(def greedy-length (r/atom ""))
(def aco-length (r/atom ""))


;;PAGE NAVIGATION STATE
(def current-page (r/atom "Operations Reseach Fundemental Problems"))
