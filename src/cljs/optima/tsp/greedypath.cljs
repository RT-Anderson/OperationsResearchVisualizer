(ns optima.tsp.greedymodule
  (:require [optima.tsp.distmodule :as dist]))

(defn ^:private greedy-find-nearest-vertex[edge-lookup-table curr-path available-nodes]
  (let [ k (map #(edge-lookup-table (set [(last @curr-path) %])) @available-nodes)
        v @available-nodes]
    (last (apply min-key key (zipmap k v)))))

(defn greedy-update [edge-lookup selected-path available-nodes]
  (let [next-node (greedy-find-nearest-vertex edge-lookup selected-path available-nodes)]
    (swap! selected-path conj next-node)
    (swap! available-nodes disj next-node)))

(defn generate-greedy-path [vertex-list edge-lookup]
  (let [starting-node (rand-int (count vertex-list))
        selected-path (atom [starting-node])
        available-nodes (atom (disj (set (range (count vertex-list))) starting-node))]
    (dorun (repeatedly (count @available-nodes) #(greedy-update edge-lookup selected-path available-nodes)))
    {:best-path @selected-path
     :best-score (Math/round (dist/compute-path-distance edge-lookup @selected-path) 1)}))

