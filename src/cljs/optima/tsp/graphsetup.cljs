(ns optima.tsp.graphsetup
  (:require [optima.tsp.distmodule :as dist]))

(defn create-rand-vertices [cnt x-max y-max margin]
  (let [adjusted-x-max (- x-max (* 2 margin))
        adjusted-y-max (- y-max (* 2 margin))]
    (repeatedly cnt (fn [] {:x (+ margin (rand-int adjusted-x-max))
                            :y (+ margin (rand-int adjusted-y-max)) :colorID 0}))))

(defn create-vertices [cnt x-max y-max]
  (repeatedly cnt (fn [] {:x (rand-int x-max)
                          :y (rand-int y-max)})))

(defn create-edge-lookup-table [vertex-list]
  (dist/create-edge-lookup-table vertex-list))

