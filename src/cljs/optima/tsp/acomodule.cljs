(ns optima.tsp.acomodule
  (:require [optima.tsp.distmodule :as dist]))

(defn ^:private create-pheremone-lookup-table [edge-lookup]
  (zipmap (keys edge-lookup) (repeat 1)))

(defn ^:private update-local-weights [edge-lookup pheremone-lookup alpha beta edges]
  "Given a list of acceptable edges; return an incremented probabilistic distribution of the liklihood that edge n will be selected. (range is from 0-1)"
  (let [pheremone-list (map #(pheremone-lookup %) edges)
        distance-list (map #(edge-lookup %) edges)
        numerator (map #(* (Math/pow %1 alpha) (Math/pow (/ 1 %2) beta)) pheremone-list distance-list)
        denominator (reduce + numerator)
        weights (map #(/ % denominator) numerator)
        weighted-distribution (reductions #(+ % %2) weights)]
    weighted-distribution))

(defn ^:private select-next-node [edge-lookup pheremone-lookup alpha beta current-path available-nodes]
  "select the next vertex to take; selected using a probabilistic distribution heuristic"
  (let [available-edges (map set (map vector (repeat (last current-path)) available-nodes))
        weighted-edge-distribution (update-local-weights edge-lookup pheremone-lookup alpha beta available-edges)
        rand-value (rand 1)
        selected-index (count (take-while #(<= % rand-value) weighted-edge-distribution))
        selected-edge (nth available-edges selected-index)
        selected-node (if (= (last current-path) (first selected-edge)) (last selected-edge) (first selected-edge))]
    selected-node))

(defn ^:private generate-single-ant-path [nodes pheremone-lookup edge-lookup alpha beta]
  "generate entire path for individual ant; return object containing: vector of in-order nodes & distance of total circuit"
  (loop [path (vector (rand-int (count nodes)))
         available-nodes (disj (set (range (count nodes))) (first path))]
    (if (empty? available-nodes)
      {:path path :length (dist/compute-path-distance edge-lookup path)}
      (let [selected-node (select-next-node edge-lookup pheremone-lookup alpha beta path available-nodes)]
        (recur (conj path selected-node) (disj available-nodes selected-node))))))

(defn ^:private generate-multiple-ant-paths [nodes pheremone-lookup edge-lookup ant-cnt alpha beta]
  (repeatedly ant-cnt #(generate-single-ant-path nodes pheremone-lookup edge-lookup alpha beta)))

(defn ^:private single-ant-pheremone-update [edge-lookup qval ant-path]
  (let [impacted-edges (map #(set %) (map vector (:path ant-path) (cons (last (:path ant-path)) (pop (:path ant-path)))))
        pheremone-weight (/ qval (ant-path :length))
        pheremone-path (zipmap impacted-edges (repeat pheremone-weight))]
    pheremone-path))

(defn ^:private multiple-ant-pheremone-update [edge-lookup qval ant-paths]
  (let [pheremone-maps (map #(single-ant-pheremone-update edge-lookup qval %) ant-paths)]
    (apply merge-with + pheremone-maps)))

(defn ^:private pheremone-decay-update [pheremone-lookup rho]
  (zipmap (keys pheremone-lookup) (map #(* (- 1 rho) %) (vals pheremone-lookup))))

(defn ^:private update-colony-pheremones [pheremone-decay pheremone-deposit]
  (merge-with + pheremone-decay pheremone-deposit))

(defn ^:private complete-tour [nodes edge-lookup pheremone-lookup ant-cnt alpha beta rho qval]
  (let [ant-paths (generate-multiple-ant-paths nodes pheremone-lookup edge-lookup ant-cnt alpha beta)
        pheremone-deposit (multiple-ant-pheremone-update edge-lookup qval ant-paths)
        pheremone-decay (pheremone-decay-update pheremone-lookup rho)
        updated-colony-pheremones (update-colony-pheremones pheremone-decay pheremone-deposit)
        best-local-path (apply min-key :length ant-paths)]
    {:pheremone-lookup updated-colony-pheremones :best-local-path best-local-path}))

(defn complete-n-tours [nodes edge-lookup tour-cnt ant-cnt alpha beta rho qval]
  (loop [pheremone-lookup (create-pheremone-lookup-table edge-lookup)
         completed-tours 0
         best-score 999999999999999999999999999999
         history []]
    (if (<= tour-cnt completed-tours)
      history
      (let [tour-result (complete-tour nodes edge-lookup pheremone-lookup ant-cnt alpha beta rho qval)
            history-output {:tour completed-tours :score ((tour-result :best-local-path) :length) :path ((tour-result :best-local-path) :path)}
            improvementDetected? (or (empty? history)
                                     (> ((last history) :score) (history-output :score)))]
        (recur (tour-result :pheremone-lookup)
               (inc completed-tours)
               ((tour-result :best-local-path) :length)
               (if improvementDetected? (conj history history-output) history))))))


(defn generate-aco-path [nodes edge-lookup]
  (let [history (complete-n-tours nodes edge-lookup 40 10 3.5 3.5 0.2 0.2)
        best-tour ((last history) :path)]
    {:best-path best-tour
     :best-score (Math/round ((last history) :score) 1)}))
