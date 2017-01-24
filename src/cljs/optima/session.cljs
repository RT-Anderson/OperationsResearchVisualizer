(ns optima.session
  (:require [reagent.core :as r]))



(defonce sidebar-expanded? (r/atom true))
(defonce current-page (r/atom "Operations Reseach Fundemental Problems"))


(defonce canvas-width (r/atom 500))
(defonce canvas-height (r/atom 500))