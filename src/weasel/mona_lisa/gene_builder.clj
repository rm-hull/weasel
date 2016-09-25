(ns weasel.mona-lisa.gene-builder
  (:refer-clojure :exclude [remove]))

(defn rand-color []
  (rand-int 256))


;; gene creation

(defn- make-color []
  (list
    (rand-color)   ; red
    (rand-color)   ; green
    (rand-color)   ; blue
    (rand-color))) ; alpha

(defn- make-vertex [[max-width max-height :as dimensions]]
  (fn []
    [(rand-int max-width)
     (rand-int max-height)]))

(defn- make-polygon [dimensions]
  (let [rand-vertex (make-vertex dimensions)]
    (fn []
      {:color (make-color)
       :points (repeatedly (+ 3 (rand-int 3)) rand-vertex)})))

(defn make-dna [dimensions num-genes]
  (repeatedly num-genes (make-polygon dimensions)))


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

(defn- mutate-polygon [dimensions]
  (let [rand-vertex (make-vertex dimensions)]
    (fn [polygon probability]
      (->
        polygon
        (update :color mutate probability rand-color)
        (update :points mutate probability rand-vertex)
        (update :points insert probability rand-vertex)
        (update :points remove probability)))))

(defn mutate-dna [dimensions probability]
  (let [mutate (mutate-polygon dimensions)]
    (fn [dna]
      (for [gene dna]
        (mutate gene probability)))))


;; gene uniform crossover

(defn crossover-dna [dna1 dna2]
  (for [[gene1 gene2] (map list dna1 dna2)]
    (if (zero? (rand-int 2))
      gene1
      gene2)))


