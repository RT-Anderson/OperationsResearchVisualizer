(ns optima.tsp.drawinghelper
  (:require [quil.core :as q]
            [optima.session :as s]))


(defn draw-vertices [vertex-list]
  (let [x-offset (/ @s/canvas-width 500)
        draw-vertex (fn [v] (q/ellipse (* x-offset (:x v)) (:y v) 15 15))]
    (mapv #(draw-vertex %) vertex-list)))

(defn compute-path-distance [edge-lookup selected-path]
  (let [shifted-path (cons (last selected-path) (pop selected-path))
        edge-id (map set (map vector selected-path shifted-path))
        total-dist (reduce + (map #(edge-lookup %) edge-id))]
    total-dist))

(defn draw-edges [edge-list vertex-list]
  (let [x-offset-ratio (/ @s/canvas-width 500)
        x-offset (fn [v] (* x-offset-ratio v))
        draw-edge (fn [e] (q/line (x-offset (:x (nth vertex-list (:a e))))
                                  (:y (nth vertex-list (:a e)))
                                  (x-offset (:x (nth vertex-list (:b e))))
                                  (:y (nth vertex-list (:b e)))))]
    (mapv #(draw-edge %) edge-list)))

(defn draw-path [vertex-list selected-path]
  (let [shifted-path (cons (last selected-path) (pop selected-path))
        edges (map #(into {} {:a %1 :b %2}) selected-path shifted-path)]
    (draw-edges edges vertex-list)))