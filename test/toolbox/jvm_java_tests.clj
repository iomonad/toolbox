(ns toolbox.jvm-java-tests
  (:require [toolbox.jvm.java :as java]
            [clojure.test :refer :all]))

(deftest jvm-java-test
  (testing "java utils"
    (is (= #{clojure.reflect.Method clojure.reflect.Constructor}
           (into #{} (map type (java/class-methods String))))))
  (testing "properties test"
    (is (map? (java/get-properties-map))))
  (testing "java informations"
    (=
     #{:vendor :home :io :vm :library :class :runtime :version :specification}
     (into #{} (keys (java/java-informations))))))
