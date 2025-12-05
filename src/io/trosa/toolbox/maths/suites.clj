(ns io.trosa.toolbox.maths.suites)

(def ^{:added "1.0.0"}
  fib
  "Generate the fibonnaci suite lazily.
   Consider using sequence building with `take`"
  (cons 0 (cons 1 (lazy-seq (map + fib (rest fib))))))
