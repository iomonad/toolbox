(ns toolbox.fs
  "Function to work with filesystem"
  (:require [clojure.java.io :as io]))

(def ^:dynamic *cwd* (.getCanonicalFile (io/file ".")))
