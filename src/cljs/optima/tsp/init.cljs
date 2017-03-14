(ns optima.tsp.init)

(defn create-rand-vertices [cnt x-max y-max margin]
  (let [adjusted-x-max (- x-max (* 2 margin))
        adjusted-y-max (- y-max (* 2 margin))]
    (repeatedly cnt (fn [] {:x (+ margin (rand-int adjusted-x-max))
                            :y (+ margin (rand-int adjusted-y-max))}))))

