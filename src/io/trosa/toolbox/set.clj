(ns io.trosa.toolbox.set
  "Functions intended to work with set(s)"
  (:require [clojure.set :as set]))

(defn powerset
  "Generate a set S is the set of all subsets of S,
   including the empty set and S itself."
  {:added "1.1.0"}
  [items]
  (reduce (fn [s x]
            (set/union s (into #{} (map #(conj % x) s))))
          (hash-set #{})
          items))
