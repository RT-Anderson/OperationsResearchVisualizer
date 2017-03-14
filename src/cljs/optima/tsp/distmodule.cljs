(ns optima.tsp.distmodule)

(defn  ^:private calculate-vertex-distance [vertex-list v1 v2]
  (let [dx (- (:x (nth vertex-list v1)) (:x (nth vertex-list v2)))
        dy (- (:y (nth vertex-list v1)) (:y (nth vertex-list v2)))]
    (Math/pow (+ (Math/pow dx 2) (Math/pow dy 2)) 0.5)))

(defn ^:private create-single-edge-id[n]
  (->> (map vector (repeat n n) (range n))
       (map #(set %))))

(defn ^:private create-edge-list [vertex-list]
  (->> (count vertex-list)
       (range)
       (map create-single-edge-id)
       (flatten)))

(defn ^:private add-edge-distance-keyvalue [vertex-list edge-set]
  (let [dist (calculate-vertex-distance vertex-list (first edge-set) (last edge-set))]
    [edge-set dist]))

(defn create-edge-lookup-table [vertex-list]
  (let [edges (create-edge-list vertex-list)
        kv-pair (mapv #(add-edge-distance-keyvalue vertex-list %) edges)]
    (into {} kv-pair)))

(defn compute-path-distance [edge-lookup selected-path]
  (let [shifted-path (cons (last selected-path) (pop selected-path))
        edge-id (map set (map vector selected-path shifted-path))
        total-dist (reduce + (map #(edge-lookup %) edge-id))]
    total-dist))

