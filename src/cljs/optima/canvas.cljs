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


(defn draw-vertices [vertex-list]
  (let [draw-vertex (fn [v] (q/ellipse (:x v) (:y v) 15 15))]
  (mapv #(draw-vertex %) vertex-list)))




;;STATE SECTION
(def canvas-margin 15)
(def vertex-cnt (r/atom 100))
(def vertices (r/atom (create-rand-vertex-list @vertex-cnt @s/canvas-width @s/canvas-height canvas-margin)))


;;DRAWING SECTION
(defn draw-path []
  (q/background 255)
  (q/fill 0)
  (draw-vertices @vertices))

(q/defsketch canvas-plot
             :draw   draw-path
             :host "foo"
             :no-start true
             :middleware [m/fun-mode]
             :size [@s/canvas-width @s/canvas-height])



(defn hello-world []
  (r/create-class
    {:reagent-render      (fn [] [:canvas#foo {:width @s/canvas-width :height @s/canvas-height}])
     :component-did-mount canvas-plot}))
