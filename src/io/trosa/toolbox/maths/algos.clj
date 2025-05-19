(ns io.trosa.toolbox.maths.algos)

(defn is-palindrome?
  "Check if the number is a palindrome"
  [n]
  {:added "1.0.0"}
  (= (seq (str n)) (reverse (str n))))

(defn palindromes
  "Generate a sequence of palindromes"
  {:added "1.0.0"}
  ([] (palindromes 1000))
  ([max-commutation]
   (filter is-palindrome?
           (for [a (range 100 max-commutation)
                 b (range a   max-commutation)]
             (* a b)))))
