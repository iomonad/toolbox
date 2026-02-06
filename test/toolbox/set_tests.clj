(ns toolbox.set-tests
  (:require [toolbox.set :as s]
            [clojure.test :refer :all]))

(deftest sets-tests
  (testing "powerset tests"
    (is (= (s/powerset #{1 2 3})
           #{#{} #{3} #{2} #{1} #{1 3 2} #{1 3} #{1 2} #{3 2}}))
    (is (= (s/powerset #{1})
           #{#{} #{1}}))))
