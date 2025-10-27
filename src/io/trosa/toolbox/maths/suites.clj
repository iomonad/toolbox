(ns io.trosa.toolbox.maths.suites)

;;   _________     .__  __
;;  /   _____/__ __|__|/  |_  ____   ______
;;  \_____  \|  |  \  \   __\/ __ \ /  ___/
;;  /        \  |  /  ||  | \  ___/ \___ \
;; /_______  /____/|__||__|  \___  >____  >
;;         \/                    \/     \/

(def ^{:added "1.0.0"} fib
  "Generate the fibonnaci suite lazily.
   Consider using sequence building with "
  (cons 0 (cons 1 (lazy-seq (map + fib (rest fib))))))
