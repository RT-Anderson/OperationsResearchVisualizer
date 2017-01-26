(ns optima.canvas
  (:require [reagent.core :as r]
            [cljs.core.async :as a]
            [quil.core :as q]
            [quil.middleware :as m]
            [optima.session :as s]))

(defn create-rand-vertex-list [cnt x-max y-max margin]
  (let [adjusted-x-max (- x-max (* 2 margin))
        adjusted-y-max (- y-max (* 2 margin))]
    (repeatedly cnt (fn [] {:x (+ margin (rand-int adjusted-x-max))
                          :y (+ margin (rand-int adjusted-y-max)) :colorID 0}))))

(defn create-rand-edge-list [edge-cnt vertex-cnt]
  (repeatedly edge-cnt (fn [] {:a (rand-int vertex-cnt) :b (rand-int vertex-cnt)})))

(defn draw-vertices [vertex-list]
  (let [draw-vertex (fn [v] (q/ellipse (:x v) (:y v) 15 15))]
  (mapv #(draw-vertex %) vertex-list)))

(defn draw-edges [edge-list vertex-list]
  (let [draw-edge (fn [e] (q/line (:x (nth vertex-list (:a e)))
                                  (:y (nth vertex-list (:a e)))
                                  (:x (nth vertex-list (:b e)))
                                  (:y (nth vertex-list (:b e)))))]
    (mapv #(draw-edge %) edge-list)))

;;STATE SECTION
(def canvas-margin 15)
(def vertex-cnt (r/atom 100))
(def edge-cnt (r/atom 30))

(def vertices (r/atom (create-rand-vertex-list @vertex-cnt (* 0.8 @s/canvas-width)  @s/canvas-height canvas-margin)))
(def edges (create-rand-edge-list @edge-cnt @vertex-cnt ))

;;DRAWING SECTION
(defn draw-path []
  (q/background 255)
  (q/fill 0)
  (draw-vertices @vertices)
  (draw-edges edges @vertices))

(q/defsketch canvas-plot
             :draw   draw-path
             :host "foo"
             :no-start true
             :middleware [m/fun-mode]
             :size [(* 0.8 @s/canvas-width) @s/canvas-height])



(defn hello-world [width]
  (reset! s/canvas-width width)
  (reset! vertices (create-rand-vertex-list @vertex-cnt (* 0.8 @s/canvas-width)  @s/canvas-height canvas-margin))
  (r/create-class

    {:reagent-render      (fn [] [:canvas#foo {:width @s/canvas-width  :height @s/canvas-height}])
     :component-did-mount canvas-plot}) )
