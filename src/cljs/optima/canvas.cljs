(ns optima.canvas
  (:require [reagent.core :as r]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [optima.session :as s]
            [optima.tsp.init :as tsp-init]
            [optima.tsp.drawinghelper :as helper]
            [optima.tsp.distmodule :as lookup]
            [optima.tsp.greedymodule :as greedy]
            [optima.tsp.acomodule :as aco]))

(enable-console-print!)

;;STATE SECTION
(def update-canvas (r/atom 0))
(def vertices (r/atom (tsp-init/create-rand-vertices 20 500 500 s/canvas-margin)))
(defn redraw-vertices [vertex-cnt]
  (reset! vertices (tsp-init/create-rand-vertices vertex-cnt 500 500 s/canvas-margin))
  (swap! update-canvas inc)
  (println @update-canvas))

(def tmp-distlookup (r/atom (lookup/create-edge-lookup-table @vertices)))
(defn redraw-distlookup []  (do (reset! tmp-distlookup (lookup/create-edge-lookup-table @vertices))))
(def tmp-path (r/atom []))


(defn generate-greedy-path [nodes dist-lookup-table]
  (let [solution-map (greedy/generate-greedy-path nodes dist-lookup-table)]
    (do (reset! s/greedy-length (solution-map :best-score))
        (reset! tmp-path (solution-map :best-path)))))

(defn generate-aco-path [nodes dist-lookup-table]
  (let [solution-map (aco/generate-aco-path nodes dist-lookup-table)]
    (do (reset! s/aco-length (solution-map :best-score))
        (reset! tmp-path (solution-map :best-path)))))

(defn redraw-path [solver-type]
  (case solver-type
    "greedy-search" (generate-greedy-path @vertices @tmp-distlookup)
    "aco" (generate-aco-path @vertices @tmp-distlookup)))


;;CANVAS CONFIG SECTION
(defn draw-canvas []
  (println @update-canvas)
  (println "canvas redrawn")
  (q/background 255)
  ;;(q/fill 0)
  ;;  (helper/draw-path @vertices @tmp-path)
  (helper/draw-vertices @vertices)
  (helper/draw-path @vertices @tmp-path))

(defn setup []
  {:t true})

(defn update [state]
  (update-in state [:t] not))

(defn draw [state]
  (q/background 255)
  ;;(q/fill 0)
  (q/rect -20 -20 (+ 20 @s/canvas-width) (+ 20 @s/canvas-height))
  (helper/draw-vertices @vertices)
  (if (= (count @vertices) (count @tmp-path))
    (helper/draw-path @vertices @tmp-path)))

(q/defsketch foo
             :setup  setup
             :update update
             :draw   draw
             :host "foo"
             :no-start true
             :middleware [m/fun-mode]
             :size [@s/canvas-width @s/canvas-height])

(defn tsp-canvas []
  (r/create-class
    {:reagent-render (fn [] [:canvas#foo {:width @s/canvas-width :height @s/canvas-height}])
     :component-did-mount foo}))











