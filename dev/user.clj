(ns user
  (:require [io.trosa.toolbox.jvm.java :as java]
            [io.trosa.toolbox.jvm.classpath :as cp]
            [clojure.tools.namespace.repl :refer [refresh]]))

(comment
  (java/get-properties-map)
  (java/java-informations)

  (cp/get-clojure-version)
  (cp/get-lein-project-version)
  (cp/get-lein-project-name))
