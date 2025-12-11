(ns toolbox.intervals-tests
  (:require [clojure.test :refer :all]
            [toolbox.intervals :refer [merge-overlapping-intervals]]))

(deftest intervals-tests
  (testing "merge interval is working"
    (is (= (merge-overlapping-intervals [[1 3] [2 4] [6 8] [9 10]])
           [[1 4] [6 8] [9 10]]))))
