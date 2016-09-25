(ns weasel.mona-lisa.gene-builder
  (:refer-clojure :exclude [remove]))

(defn rand-color []
  (rand-int 256))


;; gene mutation

(defn- mutate [lst probability rand-fn]
  (for [a lst]
    (if (<= (rand) probability)
      (rand-fn)
      a)))

(defn insert [lst probability rand-fn]
  (if (<= (rand) probability)
    (let [n (rand-int (count lst))
          [before after] (split-at n lst)]
      (concat before [(rand-fn)] after))
    lst))

(defn remove [lst probability]
  (if (and (<= (rand) probability) (> (count lst) 3))
    (let [n (rand-int (count lst))]
      (concat (take n lst) (drop (inc n) lst)))
    lst))

(defn- mutate-polygon [max-x max-y]
  (let [rand-vertex (make-vertex max-x max-y)]
    (fn [polygon probability]
      (->
        polygon
        (update :color mutate probability rand-color)
        (update :points mutate probability rand-vertex)
        (update :points insert probability rand-vertex)
        (update :points remove probability)))))

(defn mutate-dna [max-x max-y dna probability]
  (let [mutate (mutate-polygon max-x max-y)]
    (for [gene dna]
      (mutate gene probability))))

(defn crossover-dna [dna1 dna2]
  (for [[gene1 gene2] (map list dna1 dna2)]
    (if (zero? (rand-int 2))
      gene1
      gene2)))


;; gene creation

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
