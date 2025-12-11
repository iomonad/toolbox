(ns toolbox.intervals
  "Function intended to work with intervals ")

(defn merge-overlapping-intervals
  "Given a list of intervals, merge all the overlapping intervals into one.

  Example:

    (merge-overlapping-intervals [[1 3] [2 4] [6 8] [9 10]])
    ;; => [[1 4] [6 8] [9 10]]"
  {:added "1.1.0"}
  [interval-seq]
  (->> (sort-by first interval-seq)
       (reduce
        (fn [acc [s e]]
          (if-let [[a b] (peek acc)]
            (if (<= s b)
              (conj (pop acc) [a (max b e)])
              (conj acc [s e]))
            (conj acc [s e])))
        [])))
