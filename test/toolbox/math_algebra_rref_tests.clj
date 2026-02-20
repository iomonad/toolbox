(ns toolbox.math-algebra-rref-tests
  (:require [clojure.test :refer [deftest is are testing use-fixtures]]
            [toolbox.maths.algebra.linear :refer :all]))

(def identity-2x2 [[1.0 0.0] [0.0 1.0]])
(def identity-3x3 [[1.0 0.0 0.0] [0.0 1.0 0.0] [0.0 0.0 1.0]])
(def singular-2x2 [[1.0 2.0] [2.0 4.0]])
(def zero-2x2 [[0.0 0.0] [0.0 0.0]])
(def rectangular-3x2 [[1.0 2.0] [3.0 4.0] [5.0 6.0]])
(def full-rank-3x3 [[1.0 2.0 3.0] [0.0 1.0 4.0] [5.0 6.0 0.0]])

(deftest basic-rref
  (testing "Full rank invertible matrix"
    (let [[rref-mat rank pivots] (rref full-rank-3x3)]
      (is (= 3 rank))
      (is (= {0 0, 1 1, 2 2} pivots))
      (is (is-identity? rref-mat))))

  (testing "Singular matrix"
    (let [[rref-mat rank pivots] (rref singular-2x2)]
      (is (= 1 rank))
      (is (= {0 0} pivots))
      (is (= [[1.0 2.0] [0.0 0.0]] rref-mat))))

  (testing "Identity matrix unchanged"
    (let [[rref-mat rank pivots] (rref identity-3x3)]
      (is (= 3 rank))
      (is (= identity-3x3 rref-mat)))))

(deftest edge-cases
  (testing "Empty matrix"
    (let [[rref-mat rank pivots] (rref [])]
      (is (= 0 (count rref-mat)))
      (is (= 0 rank))
      (is (= {} pivots))))

  (testing "Single row non-zero"
    (let [[rref-mat rank pivots] (rref [[1.0 2.0 3.0]])]
      (is (= 1 rank))
      (is (= {0 0} pivots))
      (is (= [[1.0 2.0 3.0]] rref-mat))))

  (testing "Single zero row"
    (let [[rref-mat rank pivots] (rref [[0.0 0.0]])]
      (is (= 0 rank))
      (is (= {} pivots))
      (is (= [[0.0 0.0]] rref-mat))))

  (testing "All zero matrix"
    (let [[rref-mat rank pivots] (rref zero-2x2)]
      (is (= 0 rank))
      (is (= {} pivots))
      (is (= zero-2x2 rref-mat))))

  (testing "Rectangular more rows than columns"
    (let [[rref-mat rank pivots] (rref rectangular-3x2)]
      (is (= 2 rank))
      (is (= {0 0, 1 1} pivots)))))

(deftest api-functions
  (testing "matrix-rank"
    (is (= 3 (matrix-rank full-rank-3x3)))
    (is (= 1 (matrix-rank singular-2x2)))
    (is (= 0 (matrix-rank zero-2x2))))

  (testing "pivot-positions"
    (is (= {0 0, 1 1, 2 2} (pivot-positions full-rank-3x3)))
    (is (= {0 0} (pivot-positions singular-2x2))))

  (testing "is-identity?"
    (is (is-identity? identity-2x2))
    (is (is-identity? identity-3x3))
    (is (not (is-identity? singular-2x2)))))

(deftest numerical-stability
  (testing "Pivot selection works"
    (let [needs-pivot [[0.0 1.0] [2.0 3.0]]
          [rref-mat rank pivots] (rref needs-pivot)]
      (is (= 2 rank))
      (is (= [[1.0 0.0] [0.0 1.0]] rref-mat))))

  (testing "Floating point precision"
    (let [fp-test [[1e-10 1.0] [1.0 2.0]]
          [rref-mat rank _] (rref fp-test)]
      (is (= 2 rank))
      (is (< (Math/abs (- 1.0 (get-in rref-mat [0 0]))) 1e-9)))))

(deftest embedded-systems
  (testing "Control system matrices"
    (let [[rref-mat rank pivots] (rref [[1.0 0.0 0.0]
                                        [0.0 1.0 0.0]
                                        [1.0 1.0 1.0]])]
      (is (= 3 rank))))

  (testing "State space matrices (4x4)"
    (let [A [[0.0 1.0 0.0 0.0]
             [0.0 0.0 1.0 0.0]
             [0.0 0.0 0.0 1.0]
             [-2.0 -3.0 -4.0 -5.0]]
          [rref-mat rank _] (rref A)]
      (is (= 4 rank))))

  (testing "Singular due to identical sensors"
    (let [[rref-mat rank _] (rref [[1.0 2.0] [1.0 2.0]])]
      (is (= 1 rank)))))
