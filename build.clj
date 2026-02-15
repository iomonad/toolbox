(ns build
  (:require [clojure.tools.build.api :as b]))

(def project-name 'io.trosa/toolbox)
(def version      "1.1.6-SNAPSHOT")
(def class-dir    "target/classes")
(def jar-file (format "target/%s-%s.jar" (name project-name) version))
(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean
  [_params]
  (b/delete {:path "target"}))

(defn jar [_]
  (b/write-pom
   {:class-dir class-dir
    :lib project-name
    :version version
    :basis @basis
    :src-dirs ["src"]})
  (b/copy-dir
   {:src-dirs ["src" "resources"]
    :target-dir class-dir})
  (b/jar
   {:class-dir class-dir
    :jar-file jar-file}))
