(ns weasel.mona-lisa.cumulative-selection
  (:require
    [weasel.mona-lisa.image :as img]
    [weasel.mona-lisa.gene-builder :as gb]))

(defn calc-score [target-image]
  (let [dimensions (img/dimensions target-image)]
    (fn [dna]
      (img/diff target-image (img/draw dna dimensions)))))

(defn reproduce [copies dna]
  (repeat copies dna))

(defn cross-breed [generation]
  (take 10
    (for [parent1 generation
          parent2 generation
          :when (not= parent1 parent2)]
      (gb/crossover-dna parent1 parent2))))

(defn evolve [target-image probability num-genes]
  (let [dimensions (img/dimensions target-image)
        fitness    (calc-score target-image)
        mutate     (gb/mutate-dna dimensions probability)]
    (letfn [(init []
              (repeatedly 5 #(gb/make-dna dimensions num-genes)))
            (f [gen]
              (let [next-gen (->>
                               gen
                               (mapcat reproduce [50 10 5 3 2])
                               (map mutate)
                               (concat gen (cross-breed gen) (init)))
                    scores   (pmap #(vector (fitness %) %) next-gen)
                    fittest  (take 5 (map second (sort-by first scores)))]
                (cons
                  (first fittest)
                  (lazy-seq (f fittest)))))]
      (f (init)))))

(comment

  (def sydney-opera-house
    (img/resize
      (img/read-img "docs/sydney-opera-house.jpg")
      256))

  (def gens (evolve sydney-opera-house 0.1 30))

  (defn doit [n]
    (img/write-png
      (img/draw (nth gens n) [256 216])
      (format "%05d.png" n)))

  (dotimes [n 300]
    (doit n))
)

