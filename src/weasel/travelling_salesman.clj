(ns weasel.travelling-salesman)

(defn random-point []
  [(+ 20 (rand-int 760)) (+ 20 (rand-int 560))])

(defn make-points [n]
  (repeatedly n random-point))

(defn make-circle [x y n r]
  (for [i (range n)]
    (let [theta (/ (* i 2 Math/PI) n)]
      [(int (+ x (* r (Math/sin theta)))) (int (+ y (* r (Math/cos theta))))])))

(defn make-donut [n]
  (shuffle
    (concat
      (make-circle 400 300 n 280)
      (make-circle 400 300 n 180))))

(circle 400 300 20 280)
(make-donut 10)

(defn distance [[ax ay] [bx by]]
  (let [dx (- ax bx)
        dy (- ay by)]
    (Math/sqrt (+ (* dx dx) (* dy dy)))))

(defn calc-score [gene]
  (->>
    (partition 2 1 (conj gene (first gene)))
    (map #(apply distance %))
    (reduce +)))

(defn reproduce [copies gene]
  (repeat copies gene))

(defn swap [gene i j]
  (assoc gene
         j (nth gene i)
         i (nth gene j)))

(defn mutate [probability gene]
  (let [n (count gene)]
    (loop [i 0
           g (vec gene)]
      (if (>= i n)
        g
        (recur
          (inc i)
          (if (<= (rand) probability)
            (swap g i (rand-int n))
            g))))))

(defn evolve [genes t]
  (let [mutations (->>
                    (mapcat reproduce [100 10 5 3 2] genes)
                    (map (partial mutate 0.05))
                    (concat genes))
        scores    (map #(vector (calc-score %) %) mutations)
        fittest   (map second (sort-by first scores))
          ]
    (cons
      (first fittest)
      (lazy-seq
        (evolve
          (take 5 fittest)
          (inc t))))))

(take 10 (evolve [(make-points 10)] 0))

(sort-by calc-score (map (partial mutate 0.05) (reproduce 10 x)))

(mapcat (partial reproduce 10) [x])
