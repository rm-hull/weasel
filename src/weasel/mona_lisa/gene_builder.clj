(ns weasel.mona-lisa.gene-builder)
(defn rand-color []
  (rand-int 256))

(defn- make-color []
  (list
    (rand-color)   ; red
    (rand-color)   ; green
    (rand-color)   ; blue
    (rand-color))) ; alpha


(defn- make-vertex [max-x max-y]
  (fn []
    [(rand-int max-x)
     (rand-int max-y)]))

(defn- make-polygon [max-x max-y]
  (let [rand-vertex (make-vertex max-x max-y)]
    (fn []
      {:color (make-color)
       :points (repeatedly (+ 3 (rand-int 3)) rand-vertex)})))

(defn make-dna [num-genes max-x max-y]
  (repeatedly num-genes (make-polygon max-x max-y)))


