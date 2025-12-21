(ns toolbox.regexes-tests
  (:require [toolbox.regexes :as sut]
            [clojure.test :refer :all]))

(deftest regexes-tests
  (testing "e-mail regex"
    (is (some? (re-find sut/email-reg "foo@bar.com")))
    (is (nil? (re-find sut/email-reg "foo@barcom")))))
