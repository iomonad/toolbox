(ns io.trosa.toolbox.maths.algos)

;;    _____  .__
;;   /  _  \ |  |    ____   ____
;;  /  /_\  \|  |   / ___\ /  _ \
;; /    |    \  |__/ /_/  >  <_> )
;; \____|__  /____/\___  / \____/
;;         \/     /_____/

(defn is-palindrome?
  "Check if the number is a palindrome"
  {:added "1.0.0"}
  [n]
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
