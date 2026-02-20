(ns toolbox.tests
  (:require  [clojure.test :as t]))

(defmethod t/assert-expr 'not-thrown? [msg form]
  ;; Custom implementation of complementary "thrown?" utils
  ;; (is (not-thrown? expr))
  (let [body (nthnext form 1)]
    `(try ~@body
          (t/do-report {:type :pass :message ~msg :expected nil :actual nil})
          (catch Throwable e#
            (t/do-report {:type :fail :message ~msg
                          :expected "No exception thrown" :actual e#})
            e#))))
