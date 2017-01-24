(ns optima.canvas
  (:require [reagent.core :as r]
            [cljs.core.async :as a]
            [quil.core :as q]
            [quil.middleware :as m]
            [optima.session :as s]))


(def vertices ((10 40 0) (55 70 10) (15 76 20)))
(def edges ((0 1) (0 2) (2 3)))



(defn setup []
  {:v vertices :e edges})

(defn update-canvas [state]
  (update-in state [:v] inc))

(defn draw-vertex [v]
  (q/fill (last v))
  (q/ellipse (first v) (next v) 55 55))

(defn aaadraw [state]
  (q/background 255)
  (map #(draw-vertex %) vertices))



(q/defsketch foo
             :setup setup
             :update update-canvas
             :draw draw
             :host "foo"
             :no-start true
             :middleware [m/fun-mode]
             :size [@s/canvas-width @s/canvas-height])

(defn hello-world []
  (r/create-class
    {:reagent-render      (fn [] [:canvas#foo {:width @s/canvas-width :height @s/canvas-height}])
     :component-did-mount foo}))
