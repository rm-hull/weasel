(ns weasel.mona-lisa.gene-builder)

(defn- make-color []
  [(rand-int 256)   ; red
   (rand-int 256)   ; green
   (rand-int 256)   ; blue
   (rand-int 256)]) ; alpha

(defn- make-vertex [max-x max-y]
  (fn []
    [(rand-int max-x)
     (rand-int max-y)]))

(defn- make-triangle [max-x max-y]
  (let [vertex (make-vertex max-x max-y)]
    (fn []
      (apply concat
        (make-color)
        (repeatedly 3 vertex)))))

(defn make-gene [num-polygons max-x max-y]
  (apply concat (repeatedly num-polygons (make-triangle max-x max-y))))

