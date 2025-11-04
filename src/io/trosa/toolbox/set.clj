(ns io.trosa.toolbox.set
  (:require [clojure.set :as set]))

(defn powerset
  [items]
  (reduce
   (fn [s x]
     (set/union s (into #{} (map #(conj % x) s))))
   (hash-set #{})
   items))
