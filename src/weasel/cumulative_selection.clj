(ns weasel.cumulative-selection)

(def dictionary "ABCDEFGHIJKLMNOPQRSTUVWXYZ ")

(def target "METHINKS IT IS LIKE A WEASEL")

(defn calc-score [target gene]
  (->>
    (map = gene target)
    (filter true?)
    count))

(defn random-phrase []
  (apply str (repeatedly 28 #(rand-nth dictionary))) )

(defn reproduce [copies gene]
  (repeat copies gene))

(defn mutate [probability gene]
  (apply str
    (for [g gene]
      (if (<= (rand) probability)
        (rand-nth dictionary)
        g))))

(defn evolve [target gene & history]
  (if (= (calc-score target gene) (count target))
    history
    (let [mutations (->> (reproduce 100 gene) (map (partial mutate 0.05)))
          fittest (apply max-key (partial calc-score target) mutations)]
      (recur
        target
        fittest
        (cons fittest history)))))

(evolve target (random-phrase))
