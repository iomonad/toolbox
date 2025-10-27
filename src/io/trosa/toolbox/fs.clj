(ns io.trosa.toolbox.fs
  (:require [clojure.java.io :as io]))

;; ___________
;; \_   _____/_____
;;  |    __)/  ___/
;;  |     \ \___ \
;;  \___  //____  >
;;      \/      \/

(def ^:dynamic *cwd* (.getCanonicalFile (io/file ".")))
