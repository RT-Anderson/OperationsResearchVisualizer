(ns optima.canvas
  (:require [reagent.core :as r :refer [atom]]
            [cljs.core.async :as a]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [optima.session :as s])
  (:require-macros [cljs.core.async.macros :as a]))

;; DRAWING HELPER FUNCTIONS
(defn create-rand-vertex-list [cnt x-max y-max margin]
  (let [adjusted-x-max (- x-max (* 2 margin))
        adjusted-y-max (- y-max (* 2 margin))]
    (repeatedly cnt (fn [] {:x (+ margin (rand-int adjusted-x-max))
                            :y (+ margin (rand-int adjusted-y-max)) :colorID 0}))))

(defn draw-vertices [vertex-list]
  (let [draw-vertex (fn [v] (q/ellipse (:x v) (:y v) 15 15))]
    (mapv #(draw-vertex %) vertex-list)))

(defn draw-edges [edge-list vertex-list]
  (let [draw-edge (fn [e] (q/line (:x (nth vertex-list (:a e)))
                                  (:y (nth vertex-list (:a e)))
                                  (:x (nth vertex-list (:b e)))
                                  (:y (nth vertex-list (:b e)))))]
    (mapv #(draw-edge %) edge-list)))

(defn basic [input-vertices]
  (let [edge-cnt (count input-vertices)
        edge-start (range edge-cnt)
        edge-end (drop-last 1 (cons (dec edge-cnt) edge-start))
        edges (map #(into {} {:a %1 :b %2}) edge-start edge-end)]
    (draw-edges edges input-vertices)
    ))

;;STATE SECTION
(def vertex-cnt (r/atom 100))
(def vertices (r/atom (create-rand-vertex-list @vertex-cnt @s/canvas-width @s/canvas-height s/canvas-margin)))
(defn redraw-vertices [vertex-cnt] (reset! vertices (create-rand-vertex-list vertex-cnt @s/canvas-width @s/canvas-height s/canvas-margin)))


;;CANVAS CONFIG SECTION
(defn draw-canvas []
  (q/background 255)
  (q/fill 0)
  (draw-vertices @vertices)
  (basic @vertices))

(q/defsketch canvas-config
               :draw   draw-canvas
               :host "canvas-config"
               :no-start true
               :middleware [m/fun-mode]
               :size [@s/canvas-width @s/canvas-height])

(defn tsp-canvas-frame []
  (r/create-class
    {:reagent-render (fn [] [:canvas#canvas-config {:width @s/canvas-width :height @s/canvas-height}])
     :component-did-mount canvas-config}))
