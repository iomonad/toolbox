(ns toolbox.maths.algebra.linear
  (:require [clojure.string :as str]))

(defn- rows [m] (count m))
(defn- cols [m] (if (empty? m) 0 (count (first m))))

(defn- get-cell
  [m i j]
  (if (and (< i (rows m)) (< j (cols m)) (get-in m [i j]))
    (get-in m [i j])
    0.0))

(defn- swap-rows
  [m i j]
  (if (= i j)
    m
    (let [row-i (get m i)
          row-j (get m j)]
      (-> m
          (assoc i row-j)
          (assoc j row-i)))))

(defn- scale-row
  [m i factor]
  (if (zero? factor)
    m
    (update m i (fn [row]
                  (mapv #(double (* factor %)) row)))))

(defn- add-multiple-row
  [m dest src multiple]
  (if (zero? multiple)
    m
    (update m dest
            (fn [dest-row]
              (mapv + dest-row
                    (mapv #(* multiple %) (get m src)))))))

(defn- find-pivot
  [m col row]
  (loop [r row]
    (if (>= r (rows m))
      -1
      (if (not= 0.0 (get-cell m r col))
        r
        (recur (inc r))))))

(defn rref
  "Reduced Row Echelon Form.

   Returns [rref-matrix rank pivots].
   pivots = {row-index: pivot-column-index}"
  {:added "0.1.7"}
  [matrix]
  (let [nrows (rows matrix)
        ncols (cols matrix)
        m (vec (map vec matrix))]

    (loop [mat m
           lead 0
           row 0
           rank 0
           pivots {}]
      (cond
        (>= lead ncols)
        [mat rank pivots]

        (>= row nrows)
        [mat rank pivots]

        :else
        (let [pivot-row (find-pivot mat lead row)]
          (if (= pivot-row -1)
            (recur mat (inc lead) row rank pivots)

            (let [pivot-val (get-cell mat pivot-row lead)
                  norm-factor (/ 1.0 pivot-val)
                  mat1 (swap-rows mat row pivot-row)
                  mat2 (scale-row mat1 row norm-factor)
                  mat3 (loop [m mat2 k 0]
                         (if (>= k nrows)
                           m
                           (if (= k row)
                             (recur m (inc k))
                             (let [factor (* -1.0 (get-cell m k lead))]
                               (recur (add-multiple-row m k row factor)
                                      (inc k))))))]
              (recur mat3
                     (inc lead)
                     (inc row)
                     (inc rank)
                     (assoc pivots row lead)))))))))

(defn rref-matrix
  "Just the RREF matrix."
  {:added "0.1.7"}
  [matrix] (first (rref matrix)))

(defn matrix-rank
  "Number of linearly independent rows."
  {:added "0.1.7"}
  [matrix] (second (rref matrix)))

(defn pivot-positions
  "Map of {row: pivot-column}."
  {:added "0.1.7"}
  [matrix] (peek (rref matrix)))

(defn is-identity?
  "Check if matrix is identity."
  {:added "0.1.7"}
  [matrix]
  (let [[rref _ _] (rref matrix)]
    (every? identity
            (for [i (range (rows rref))]
              (every? identity
                      (for [j (range (cols rref))]
                        (if (= i j)
                          (= 1.0 (get-cell rref i j))
                          (= 0.0 (get-cell rref i j)))))))))
